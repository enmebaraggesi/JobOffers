package com.joboffers.http.error;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.joboffers.infrastructure.offer.client.OfferClient;
import com.joboffers.infrastructure.offer.client.OfferClientProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.web.server.ResponseStatusException;
import wiremock.org.apache.hc.core5.http.HttpStatus;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class OfferClientErrorIntegrationTest {
    
    private static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
    private static final String JSON_CONTENT_TYPE_VALUE = "application/json";
    
    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
                                                                      .options(wireMockConfig().dynamicPort())
                                                                      .build();
    
    OfferClient offerClient = new OfferClientTestConfig(new OfferClientProperties("http://localhost",
                                                                                  "/offers",
                                                                                  wireMockServer.getPort(),
                                                                                  1500,
                                                                                  1500)).testOfferClient();
    
    @Test
    void should_throw_500_internal_server_error_when_service_connection_is_reset_by_peer() {
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                                       .willReturn(WireMock.aResponse()
                                                           .withStatus(HttpStatus.SC_OK)
                                                           .withHeader(CONTENT_TYPE_HEADER_KEY, JSON_CONTENT_TYPE_VALUE)
                                                           .withFault(Fault.CONNECTION_RESET_BY_PEER))
        );
        //when
        Throwable throwable = catchThrowable(() -> offerClient.fetchNewOffers());
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).contains("500 INTERNAL_SERVER_ERROR");
    }
    
    @Test
    void should_throw_500_internal_server_error_when_service_returns_empty_response() {
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                                       .willReturn(WireMock.aResponse()
                                                           .withStatus(HttpStatus.SC_OK)
                                                           .withHeader(CONTENT_TYPE_HEADER_KEY, JSON_CONTENT_TYPE_VALUE)
                                                           .withFault(Fault.EMPTY_RESPONSE))
        );
        //when
        Throwable throwable = catchThrowable(() -> offerClient.fetchNewOffers());
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }
    
    @Test
    void should_throw_204_no_content_when_service_returns_status_no_content() {
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                                       .willReturn(WireMock.aResponse()
                                                           .withStatus(HttpStatus.SC_NO_CONTENT)
                                                           .withHeader(CONTENT_TYPE_HEADER_KEY, JSON_CONTENT_TYPE_VALUE)
                                                           .withBody("""
                                                                     {}
                                                                     """))
        );
        //when
        Throwable throwable = catchThrowable(() -> offerClient.fetchNewOffers());
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("204 NO_CONTENT");
    }
    
    @Test
    void should_throw_500_internal_server_error_when_service_returns_malformed_response_chunk() {
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                                       .willReturn(WireMock.aResponse()
                                                           .withStatus(HttpStatus.SC_OK)
                                                           .withHeader(CONTENT_TYPE_HEADER_KEY, JSON_CONTENT_TYPE_VALUE)
                                                           .withFault(Fault.MALFORMED_RESPONSE_CHUNK))
        );
        //when
        Throwable throwable = catchThrowable(() -> offerClient.fetchNewOffers());
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }
    
    @Test
    void should_throw_500_internal_server_error_when_service_returns_random_data_then_close() {
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                                       .willReturn(WireMock.aResponse()
                                                           .withStatus(HttpStatus.SC_OK)
                                                           .withHeader(CONTENT_TYPE_HEADER_KEY, JSON_CONTENT_TYPE_VALUE)
                                                           .withFault(Fault.RANDOM_DATA_THEN_CLOSE))
        );
        //when
        Throwable throwable = catchThrowable(() -> offerClient.fetchNewOffers());
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }
    
    @Test
    void should_throw_401_unauthorized_when_service_returns_unauthorized_status() {
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                                       .willReturn(WireMock.aResponse()
                                                           .withHeader(CONTENT_TYPE_HEADER_KEY, JSON_CONTENT_TYPE_VALUE)
                                                           .withStatus(HttpStatus.SC_UNAUTHORIZED))
        );
        //when
        Throwable throwable = catchThrowable(() -> offerClient.fetchNewOffers());
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("401 UNAUTHORIZED");
    }
    
    @Test
    void should_throw_404_not_found_when_service_returns_not_found_status() {
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                                       .willReturn(WireMock.aResponse()
                                                           .withHeader(CONTENT_TYPE_HEADER_KEY, JSON_CONTENT_TYPE_VALUE)
                                                           .withStatus(HttpStatus.SC_NOT_FOUND))
        );
        //when
        Throwable throwable = catchThrowable(() -> offerClient.fetchNewOffers());
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("404 NOT_FOUND");
    }
    
    @Test
    void should_return_500_external_server_error_when_service_response_delay_is_higher_than_read_timeout() {
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                                       .willReturn(WireMock.aResponse()
                                                           .withStatus(HttpStatus.SC_OK)
                                                           .withHeader(CONTENT_TYPE_HEADER_KEY, JSON_CONTENT_TYPE_VALUE)
                                                           .withBody("""
                                                                     {}
                                                                     """)
                                                           .withFixedDelay(5000))
        );
        //when
        Throwable throwable = catchThrowable(() -> offerClient.fetchNewOffers());
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }
}
