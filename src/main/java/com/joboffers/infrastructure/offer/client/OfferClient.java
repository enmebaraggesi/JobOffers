package com.joboffers.infrastructure.offer.client;

import com.joboffers.domain.offer.ExternalFetchable;
import com.joboffers.domain.offer.dto.OfferRequestDto;
import com.joboffers.infrastructure.offer.client.dto.OfferExternalResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Log4j2
@AllArgsConstructor
public class OfferClient implements ExternalFetchable {
    
    private final RestTemplate restTemplate;
    private final OfferClientProperties properties;
    
    @Override
    public List<OfferRequestDto> fetchNewOffers() {
        log.info("Fetching new offers...");
        String uri = UriComponentsBuilder.fromHttpUrl(setupRequestUrl())
                                         .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<HttpHeaders> httpEntity = new HttpEntity<>(headers);
        try {
            ResponseEntity<List<OfferExternalResponseDto>> response = restTemplate.exchange(uri,
                                                                                            HttpMethod.GET,
                                                                                            httpEntity,
                                                                                            new ParameterizedTypeReference<>() {
                                                                                            });
            List<OfferExternalResponseDto> externalOfferList = response.getBody();
            if (externalOfferList == null) {
                log.warn("Response body was null. Returning empty list.");
                return Collections.emptyList();
            }
            log.info("Successfully fetched {} offers", externalOfferList.size());
            return OfferExternalMapper.mapOfferExternalResponseDtoListToOfferRequestDtoList(externalOfferList);
        } catch (ResourceAccessException e) {
            log.error("Error while fetching offers: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
    
    private String setupRequestUrl() {
        return properties.url() + ":" + properties.port() + properties.service();
    }
}
