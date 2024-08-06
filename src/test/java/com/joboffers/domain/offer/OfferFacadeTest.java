package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.OfferDto;
import com.joboffers.domain.offer.error.OfferNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

class OfferFacadeTest {
    
    OffersRepository repository = new OffersRepositoryTestImpl();
    
    @Test
    void should_find_no_offer_when_there_are_no_offers() {
        //given
        OfferFacade facade = OfferFacadeConfig.createForTest(repository);
        //when
        List<OfferDto> response = facade.findAllOffers();
        //then
        assertThat(response).isEmpty();
    }
    
    @Test
    void should_throw_an_error_and_find_no_offer_by_id_when_there_are_no_offers() {
        //given
        Long id = 1L;
        OfferFacade facade = OfferFacadeConfig.createForTest(repository);
        //when
        Exception caught = catchException(() -> facade.findOfferById(id));
        //then
        assertThat(caught).isInstanceOf(OfferNotFoundException.class);
        assertThat(caught.getMessage()).isEqualTo("No offer found with id " + id);
    }
    
    @Test
    void should_save_and_return_offer_properly() {
        //given
        OfferDto offerDto = OfferDto.builder()
                                    .position("testPosition")
                                    .company("testCompany")
                                    .salary("0")
                                    .url("https://example.com")
                                    .build();
        OfferFacade facade = OfferFacadeConfig.createForTest(repository);
        //when
        OfferDto response = facade.saveOffer(offerDto);
        //then
        OfferDto expected = OfferDto.builder()
                                    .id(1L)
                                    .position("testPosition")
                                    .company("testCompany")
                                    .salary("0")
                                    .url("https://example.com")
                                    .build();
        assertThat(response).isEqualTo(expected);
    }
}