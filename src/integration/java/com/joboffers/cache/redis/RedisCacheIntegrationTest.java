package com.joboffers.cache.redis;

import com.joboffers.BaseIntegrationTest;
import com.joboffers.domain.offer.OfferFacade;
import com.joboffers.domain.usersmanagement.dto.UserRegistrationResponseDto;
import com.joboffers.infrastructure.security.jwtauthenticator.dto.JwtResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class RedisCacheIntegrationTest extends BaseIntegrationTest {
    
    @Container
    private static final GenericContainer<?> REDIS;
    
    static {
        REDIS = new GenericContainer<>("redis").withExposedPorts(6379);
        REDIS.start();
    }
    
    @SpyBean
    OfferFacade offerFacade;
    
    @Autowired
    CacheManager cacheManager;
    
    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", MONGO_DB_CONTAINER::getReplicaSetUrl);
        registry.add("spring.data.redis.port", () -> REDIS.getFirstMappedPort().toString());
        registry.add("spring.cache.type", () -> "redis");
        registry.add("spring.cache.redis.time-to-live", () -> "PT1S");
    }
    
    @Test
    public void should_save_offers_to_cache_then_invalidate_by_time_to_live() throws Exception {
        //given
        //step 1. user registration
        MvcResult mvcResult1 = mockMvc.perform(post("/register")
                                                       .content("""
                                                                {
                                                                "name": "test",
                                                                "email": "test@test.com",
                                                                "password": "testpassword"
                                                                }
                                                                """.trim())
                                                       .contentType(MediaType.APPLICATION_JSON)).andReturn();
        String registrationUserJson = mvcResult1.getResponse().getContentAsString();
        UserRegistrationResponseDto registeredDto = objectMapper.readValue(registrationUserJson, UserRegistrationResponseDto.class);
        assertAll(
                () -> assertThat(registeredDto.id()).isNotNull(),
                () -> assertThat(registeredDto.created()).isTrue()
        );
        
        //step 2. obtaining jwt token
        MvcResult mvcResult2 = mockMvc.perform(post("/token")
                                                       .content("""
                                                                {
                                                                "username": "test",
                                                                "password": "testpassword"
                                                                }
                                                                """.trim())
                                                       .contentType(MediaType.APPLICATION_JSON)).andReturn();
        String tokenRequestJson = mvcResult2.getResponse().getContentAsString();
        JwtResponseDto postTokenRequestResponseDto = objectMapper.readValue(tokenRequestJson, JwtResponseDto.class);
        String token = postTokenRequestResponseDto.token();
        assertAll(
                () -> assertThat(token).isNotNull(),
                () -> assertThat(token).matches(Pattern.compile("^[\\w-]+\\.[\\w-]+\\.[\\w-]+$"))
        );
        
        //when
        //step 3. making /offers request
        mockMvc.perform(get("/offers")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        
        //then
        //step 4. cache is verified
        verify(offerFacade, times(1)).findAllOffers();
        assertThat(cacheManager.getCacheNames().contains("jobOffers")).isTrue();
    }
}
