package com.joboffers.feature;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.joboffers.BaseIntegrationTest;
import com.joboffers.SampleJobOffersTestRequest;
import com.joboffers.SampleJobOffersTestResponse;
import com.joboffers.domain.offer.ExternalFetchable;
import com.joboffers.domain.offer.OfferFacade;
import com.joboffers.domain.offer.dto.OfferRequestDto;
import com.joboffers.domain.offer.dto.OfferResponseDto;
import com.joboffers.domain.usersmanagement.dto.UserRegistrationResponseDto;
import com.joboffers.infrastructure.offer.scheduler.OfferScheduler;
import com.joboffers.infrastructure.security.jwtauthenticator.dto.JwtResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TypicalPathUserRegisteredAndFoundOffersIntegrationTest extends BaseIntegrationTest implements SampleJobOffersTestResponse, SampleJobOffersTestRequest {
    
    @Autowired
    ExternalFetchable externalFetchable;
    
    @Autowired
    OfferScheduler offerScheduler;
    
    @Autowired
    OfferFacade offerFacade;
    
    @Container
    public static final MongoDBContainer MONGO_DB_CONTAINER = new MongoDBContainer(DockerImageName.parse("mongo:5.0"));
    
    @DynamicPropertySource
    public static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", MONGO_DB_CONTAINER::getReplicaSetUrl);
    }
    
    @Test
    void user_have_to_register_to_find_offers_and_external_source_should_have_some_offers() throws Exception {
//    1. There are no offers to fetch from external source
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                                       .willReturn(WireMock.aResponse()
                                                           .withStatus(HttpStatus.OK.value())
                                                           .withHeader("Content-Type", "application/json")
                                                           .withBody(zeroOffersResponseJson())));
        //when
        offerFacade.fetchNewOffers();
        List<OfferResponseDto> zeroFetchedOffers = offerFacade.findAllOffers();
        //then
        assertThat(zeroFetchedOffers).isEmpty();


//    2. Scheduler runs 1st time making GET request to external source adding 0 offers to database
        //given & when
        offerScheduler.scheduledOfferUpdate();
        List<OfferResponseDto> zeroOffersFound = offerFacade.findAllOffers();
        //then
        assertThat(zeroOffersFound).isEmpty();


//    3. User tries to obtain JWT token making POST request to /token, but system returns UNAUTHORIZED(401)
        //given
        MockHttpServletRequestBuilder postFailedTokenRequest = post("/token").content(tokenRequestJson())
                                                                             .contentType(MediaType.APPLICATION_JSON);
        //when
        ResultActions performPostFailedTokenRequest = mockMvc.perform(postFailedTokenRequest);
        //then
        performPostFailedTokenRequest.andExpect(status().isUnauthorized())
                                     .andExpect(content().json(unauthorizedResponseJson()));


//    4. User tries to find offers with no JWT token making GET request to /offers, but system returns FORBIDDEN(403)
        //given
        MockHttpServletRequestBuilder getFailedOffersRequest = get("/offers");
        //when
        ResultActions performGetFailedOffersRequest = mockMvc.perform(getFailedOffersRequest);
        //then
        performGetFailedOffersRequest.andExpect(status().isForbidden());


//    5. User registers successfully making POST request to /register giving username, password and email
        //given
        String userName = "test";
        MockHttpServletRequestBuilder postUserRegistration = post("/register")
                .content(userRegistrationRequestJson())
                .contentType(MediaType.APPLICATION_JSON);
        //when
        MvcResult postUserRegistrationResult = mockMvc.perform(postUserRegistration).andExpect(status().isCreated()).andReturn();
        //then
        String postRegistrationUserJson = postUserRegistrationResult.getResponse().getContentAsString();
        UserRegistrationResponseDto registeredDto = objectMapper.readValue(postRegistrationUserJson, UserRegistrationResponseDto.class);
        assertAll(
                () -> assertThat(registeredDto.id()).isNotNull(),
                () -> assertThat(registeredDto.name()).isEqualTo(userName),
                () -> assertThat(registeredDto.created()).isTrue()
        );


//    6. User tries to obtain JWT token making POST request to /token with username and password successfully
        //given
        MockHttpServletRequestBuilder postTokenRequest = post("/token")
                .content(tokenRequestJson())
                .contentType(MediaType.APPLICATION_JSON);
        //when
        ResultActions performPostTokenRequest = mockMvc.perform(postTokenRequest);
        //then
        MvcResult postTokenRequestResult = performPostTokenRequest.andExpect(status().isOk()).andReturn();
        String postTokenRequestJson = postTokenRequestResult.getResponse().getContentAsString();
        JwtResponseDto postTokenRequestResponseDto = objectMapper.readValue(postTokenRequestJson, JwtResponseDto.class);
        String token = postTokenRequestResponseDto.token();
        assertAll(
                () -> assertThat(token).isNotNull(),
                () -> assertThat(token).matches(Pattern.compile("^[\\w-]+\\.[\\w-]+\\.[\\w-]+$")),
                () -> assertThat(postTokenRequestResponseDto.username()).isEqualTo(userName)
        );


//    7. Registered and authorized user makes GET request to /offers with header "Authorization: Bearer {token}" and system returns OK(200) with 0 offers
        //given
        MockHttpServletRequestBuilder getAllOffersRequest = get("/offers")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON);
        //when
        ResultActions performGetAllOffers = mockMvc.perform(getAllOffersRequest);
        //then
        MvcResult getAllOffersResult = performGetAllOffers.andExpect(status().isOk()).andReturn();
        String getAllOffersJson = getAllOffersResult.getResponse().getContentAsString();
        List<OfferResponseDto> getAllOffersResponseDtos = objectMapper.readValue(getAllOffersJson, new TypeReference<>() {
        });
        assertThat(getAllOffersResponseDtos).isEmpty();


//    8. There are 2 new offers to fetch from external source
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                                       .willReturn(WireMock.aResponse()
                                                           .withStatus(HttpStatus.OK.value())
                                                           .withHeader("Content-Type", "application/json")
                                                           .withBody(twoOffersResponseJson()))
        );
        //when
        List<OfferRequestDto> twoOffersList = externalFetchable.fetchNewOffers();
        //then
        assertThat(twoOffersList).hasSize(2);


//    9. Scheduler runs 2nd time making GET request to external source adding 2 offers to database
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                                       .willReturn(WireMock.aResponse()
                                                           .withStatus(HttpStatus.OK.value())
                                                           .withHeader("Content-Type", "application/json")
                                                           .withBody(twoOffersResponseJson()))
        );
        //when
        offerScheduler.scheduledOfferUpdate();
        List<OfferResponseDto> twoOffersFound = offerFacade.findAllOffers();
        //then
        assertThat(twoOffersFound).hasSize(2);


//    10. User makes GET request to /offers with header “Authorization: Bearer {token}” and system returns OK(200) with 2 new offers
        //given
        MockHttpServletRequestBuilder getTwoOffers = get("/offers")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON);
        //when
        ResultActions performGetTwoOffers = mockMvc.perform(getTwoOffers);
        //then
        MvcResult getTwoOffersResult = performGetTwoOffers.andExpect(status().isOk()).andReturn();
        String getTwoOffersJson = getTwoOffersResult.getResponse().getContentAsString();
        List<OfferResponseDto> getTwoOffersResponse = objectMapper.readValue(getTwoOffersJson, new TypeReference<>() {
        });
        assertThat(getTwoOffersResponse).hasSize(2);


//    11. User makes GET request to /offers/{id} with authorization header and nonExistingId and system returns NOT_FOUND(404) with message "Offer with ID {id} not found"
        //given
        String nonExistingId = "nonExistingId";
        MockHttpServletRequestBuilder getOfferByNonExistingId = get("/offers/" + nonExistingId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON);
        //when
        ResultActions performGetOfferByNonExistingId = mockMvc.perform(getOfferByNonExistingId);
        //then
        performGetOfferByNonExistingId.andExpect(status().isNotFound())
                                      .andExpect(content().json(nonExistingOfferResponseJson()));


//    12. User makes GET request to /offers/{id} with authorization header and existing ID and system returns OK(200) with exact offer
        //given
        String existingId = getTwoOffersResponse.get(0)
                                                .id();
        MockHttpServletRequestBuilder getOfferByExistingId = get("/offers/" + existingId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON);
        //when
        ResultActions performGetOfferByExistingId = mockMvc.perform(getOfferByExistingId);
        //then
        MvcResult getOfferByExistingIdResult = performGetOfferByExistingId.andExpect(status().isOk()).andReturn();
        String getOfferByExistingIdJson = getOfferByExistingIdResult.getResponse().getContentAsString();
        OfferResponseDto getOfferByExistingIdResponse = objectMapper.readValue(getOfferByExistingIdJson, OfferResponseDto.class);
        assertThat(getOfferByExistingIdResponse.id()).isEqualTo(existingId);


//    13. There are 2 new offers to fetch from external source
        //given & when & then
        wireMockServer.stubFor(WireMock.get("/offers")
                                       .willReturn(WireMock.aResponse()
                                                           .withStatus(HttpStatus.OK.value())
                                                           .withHeader("Content-Type", "application/json")
                                                           .withBody(fourOffersResponseJson()))
        );


//    14. Scheduler runs 3rd time making GET request to external source adding 2 offers to database
        //given & when
        offerScheduler.scheduledOfferUpdate();
        List<OfferResponseDto> offers = offerFacade.findAllOffers();
        //then
        assertThat(offers).hasSize(4);


//    15. User makes GET request to /offers with authorization header and system returns OK(200) with 4 offers
        //given
        MockHttpServletRequestBuilder getFourOffers = get("/offers")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON);
        //when
        ResultActions performGetFourOffers = mockMvc.perform(getFourOffers);
        //then
        MvcResult getFourOffersResult = performGetFourOffers.andExpect(status().isOk()).andReturn();
        String getFourOffersJson = getFourOffersResult.getResponse().getContentAsString();
        List<OfferResponseDto> getFourOffersResponse = objectMapper.readValue(getFourOffersJson, new TypeReference<>() {
        });
        assertThat(getFourOffersResponse).hasSize(4);


//    16. User makes POST request to /offers with authorization header and system returns OK(200) with posted offer
        //given
        MockHttpServletRequestBuilder postOffer = post("/offers")
                .content(offerRequestJson())
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON);
        //when
        ResultActions performPostOffer = mockMvc.perform(postOffer);
        //then
        MvcResult postOfferResult = performPostOffer.andExpect(status().isCreated()).andReturn();
        String postOfferJson = postOfferResult.getResponse().getContentAsString();
        OfferResponseDto postOfferResponseDto = objectMapper.readValue(postOfferJson, OfferResponseDto.class);
        assertAll(
                () -> assertThat(postOfferResponseDto.id()).isNotNull(),
                () -> assertThat(postOfferResponseDto.position()).isEqualTo("Test position"),
                () -> assertThat(postOfferResponseDto.company()).isEqualTo("Test company"),
                () -> assertThat(postOfferResponseDto.salary()).isEqualTo("9999 USD"),
                () -> assertThat(postOfferResponseDto.url()).isEqualTo("https://joboffers.com")
        );


//    17. User makes GET request to /offers/{id} with authorization header and posted offer and system returns OK(200) with offer
        //given
        String postedOfferId = postOfferResponseDto.id();
        MockHttpServletRequestBuilder getOfferByPostedOfferId = get("/offers/" + postedOfferId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON);
        //when
        ResultActions performGetOfferByPostedOfferId = mockMvc.perform(getOfferByPostedOfferId);
        //then
        MvcResult getOfferByPostedOfferIdResult = performGetOfferByPostedOfferId.andExpect(status().isOk()).andReturn();
        String getOfferByPostedOfferIdJson = getOfferByPostedOfferIdResult.getResponse().getContentAsString();
        OfferResponseDto getOfferByPostedOfferIdResponseDto = objectMapper.readValue(getOfferByPostedOfferIdJson, OfferResponseDto.class);
        assertThat(getOfferByPostedOfferIdResponseDto.id()).isEqualTo(postedOfferId);
    }
}
