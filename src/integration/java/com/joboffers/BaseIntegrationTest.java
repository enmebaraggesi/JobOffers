package com.joboffers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(classes = JobOffersApplication.class)
@ActiveProfiles("integration")
@Testcontainers
@AutoConfigureMockMvc
public class BaseIntegrationTest {
    
    public static final String WIRE_MOCK_HOST = "http://localhost";
    
    @Autowired
    public MockMvc mockMvc;
    
    @Autowired
    public ObjectMapper objectMapper;
    
    @Container
    public static final MongoDBContainer MONGO_DB_CONTAINER = new MongoDBContainer(DockerImageName.parse("mongo:5.0"));
    
    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance()
                                                                      .options(WireMockConfiguration.wireMockConfig().dynamicPort())
                                                                      .build();
    
    @DynamicPropertySource
    public static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.host", MONGO_DB_CONTAINER::getHost);
        registry.add("job-offers.offer.http.client.url", () -> WIRE_MOCK_HOST);
        registry.add("job-offers.offer.http.client.port", () -> wireMockServer.getPort());
    }
}
