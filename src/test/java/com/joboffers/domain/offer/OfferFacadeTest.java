package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.OfferDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
}