package com.joboffers.infrastructure.offer.client;

import com.joboffers.domain.offer.ExternalFetchable;
import com.joboffers.domain.offer.dto.OfferRequestDto;
import com.joboffers.infrastructure.offer.client.dto.OfferExternalResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
public class OfferClient implements ExternalFetchable {
    
    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final int port;
    
    @Override
    public List<OfferRequestDto> fetchNewOffers() {
        String url = setupRequestUri();
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                                         .toUriString();
        ResponseEntity<List<OfferExternalResponseDto>> response = restTemplate.exchange(uri,
                                                                               HttpMethod.GET,
                                                                               null,
                                                                               new ParameterizedTypeReference<>() {
                                                                               });
        List<OfferExternalResponseDto> offerRequestDtoList = response.getBody();
        return OfferExternalMapper.mapOfferExternalResponseDtoListToOfferRequestDtoList(offerRequestDtoList);
    }
    
    private String setupRequestUri() {
        return baseUrl + ":" + port + "/offers";
    }
}
