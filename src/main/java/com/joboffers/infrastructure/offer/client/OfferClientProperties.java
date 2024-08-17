package com.joboffers.infrastructure.offer.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "job-offers.offer.http.client")
public record OfferClientProperties(String url,
                                    String service,
                                    int port,
                                    int connectionTimeout,
                                    int readTimeout) {

}
