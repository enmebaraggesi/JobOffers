package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.OfferRequestDto;
import com.joboffers.domain.offer.dto.OfferResponseDto;
import com.joboffers.domain.offer.error.DuplicateOfferUrlException;
import com.joboffers.domain.offer.error.OfferNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

import static com.joboffers.domain.offer.ResponseMessage.OFFER_NOT_FOUND;

@AllArgsConstructor
public class OfferFacade {
    
    private final OffersRepository repository;
    private final ExternalFetchable offerFetcher;
    private final OfferInspector offerInspector;
    
    @Cacheable("jobOffers")
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
        Offer offer = OfferMapper.mapOfferRequestDtoToOffer(requestDto);
        if (offerInspector.inspectOfferRequest(offer)) {
            Offer saved = repository.save(offer);
            return OfferMapper.mapOfferToOfferResponseDto(saved);
        } else {
            String message = offerInspector.getError();
            throw new DuplicateOfferUrlException(message);
        }
    }
}
