package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.OfferRequestDto;
import com.joboffers.domain.offer.dto.OfferResponseDto;
import com.joboffers.domain.offer.error.OfferNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;

import static com.joboffers.domain.offer.ResponseMessage.OFFER_NOT_FOUND;

@AllArgsConstructor
public class OfferFacade {
    
    private final OffersRepository repository;
    private final ExternalFetchable offerFetcher;
    private final OfferInspector offerInspector;
    
    public List<OfferResponseDto> findAllOffers() {
        List<Offer> offers = repository.findAll();
        return OfferMapper.mapOfferListToOfferResponseDtoList(offers);
    }
    
    public int fetchNewOffers() {
        List<OfferRequestDto> fetchedOffers = offerFetcher.fetchNewOffers();
        List<OfferResponseDto> allOffers = this.findAllOffers();
        List<OfferRequestDto> filteredRequests = offerInspector.filterOutExistingOffers(fetchedOffers, allOffers);
        filteredRequests.forEach(this::saveOffer);
        return filteredRequests.size();
    }
    
    public OfferResponseDto findOfferById(final String id) {
        return repository.findById(id)
                         .map(OfferMapper::mapOfferToOfferResponseDto)
                         .orElseThrow(() -> new OfferNotFoundException(OFFER_NOT_FOUND.format(id)));
    }
    
    public OfferResponseDto saveOffer(final OfferRequestDto requestDto) {
        offerInspector.inspectUrl(requestDto);
        Offer offer = OfferMapper.mapOfferRequestDtoToOffer(requestDto);
        Offer saved = repository.save(offer);
        return OfferMapper.mapOfferToOfferResponseDto(saved);
    }
}
