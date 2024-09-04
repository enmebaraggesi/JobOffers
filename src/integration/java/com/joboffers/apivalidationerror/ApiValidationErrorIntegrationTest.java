package com.joboffers.apivalidationerror;

import com.joboffers.BaseIntegrationTest;
import com.joboffers.SampleJobOffersTestRequest;
import com.joboffers.infrastructure.apivalidation.dto.ApiValidationResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApiValidationErrorIntegrationTest extends BaseIntegrationTest implements SampleJobOffersTestRequest {
    
    @Test
    public void should_return_bad_request_and_messages_when_some_data_in_offer_request_is_empty() throws Exception {
        //given & when
        ResultActions perform = mockMvc.perform(post("/offers")
                                                        .content(offerWithEmptyPositionAndSalaryRequestJson())
                                                        .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        MvcResult mvcResult = perform.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationResponseDto responseDto = objectMapper.readValue(json, ApiValidationResponseDto.class);
        assertThat(responseDto.errors()).containsExactlyInAnyOrder("position must not be empty",
                                                                   "salary must not be empty");
    }
    
    @Test
    public void should_return_bad_request_and_messages_when_company_in_offer_request_is_null() throws Exception {
        //given & when
        ResultActions perform = mockMvc.perform(post("/offers")
                                                        .content(offerWithNullCompanyRequestJson())
                                                        .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        MvcResult mvcResult = perform.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationResponseDto responseDto = objectMapper.readValue(json, ApiValidationResponseDto.class);
        assertThat(responseDto.errors()).containsExactlyInAnyOrder("company must not be empty",
                                                                   "company must not be null");
    }
    
    @Test
    public void should_return_conflict_message_when_provided_offer_with_already_existing_url() throws Exception {
        //given
        String url = "https://joboffers.com";
        mockMvc.perform(post("/offers")
                                .content(offerRequestJson())
                                .contentType(MediaType.APPLICATION_JSON)
        );
        //when
        ResultActions perform = mockMvc.perform(post("/offers")
                                                        .content(offerWithExistingUrlRequestJson())
                                                        .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        MvcResult mvcResult = perform.andExpect(status().isConflict()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationResponseDto responseDto = objectMapper.readValue(json, ApiValidationResponseDto.class);
        assertThat(responseDto.errors()).containsExactly("Offer URL: " + url + " already exists");
    }
    
    @Test
    public void should_return_bad_request_and_messages_when_some_data_in_user_registration_request_is_empty() throws Exception {
        //given && when
        ResultActions perform = mockMvc.perform(post("/register")
                                                        .content(userRegistrationEmptyNameAndPasswordRequestJson())
                                                        .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        MvcResult mvcResult = perform.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationResponseDto responseDto = objectMapper.readValue(json, ApiValidationResponseDto.class);
        assertThat(responseDto.errors()).containsExactlyInAnyOrder("name must not be empty",
                                                                   "password must not be empty");
    }
    
    @Test
    public void should_return_bad_request_and_messages_when_some_data_in_user_registration_request_is_null() throws Exception {
        //given && when
        ResultActions perform = mockMvc.perform(post("/register")
                                                        .content(userRegistrationNullEmailRequestJson())
                                                        .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        MvcResult mvcResult = perform.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationResponseDto responseDto = objectMapper.readValue(json, ApiValidationResponseDto.class);
        assertThat(responseDto.errors()).containsExactlyInAnyOrder("email must not be empty",
                                                                   "email must not be null");
    }
    
    @Test
    public void should_return_conflict_message_when_provided_user_registration_request_with_already_existing_name_and_email() throws Exception {
        //given
        String name = "test";
        String email = "test@test.com";
        mockMvc.perform(post("/register")
                                .content(userRegistrationRequestJson())
                                .contentType(MediaType.APPLICATION_JSON)
        );
        //when
        ResultActions perform = mockMvc.perform(post("/register")
                                                        .content(userRegistrationRequestJson())
                                                        .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        MvcResult mvcResult = perform.andExpect(status().isConflict()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationResponseDto responseDto = objectMapper.readValue(json, ApiValidationResponseDto.class);
        assertThat(responseDto.errors()).containsExactly("Username: " + name + " already exists, " +
                                                                 "Email: " + email + " already exists");
    }
}
