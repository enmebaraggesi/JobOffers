package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.OfferDto;
import com.joboffers.domain.offer.dto.OfferRequestDto;
import com.joboffers.domain.offer.error.DuplicateOfferUrlException;
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
        OfferRequestDto requestDto = OfferRequestDto.builder()
                                                    .position("testPosition")
                                                    .company("testCompany")
                                                    .salary("0")
                                                    .url("https://example.com")
                                                    .build();
        OfferFacade facade = OfferFacadeConfig.createForTest(repository);
        //when
        OfferDto response = facade.saveOffer(requestDto);
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
    
    @Test
    void should_save_two_offers_and_return_them_properly() {
        //given
        OfferRequestDto requestDto1 = OfferRequestDto.builder()
                                                     .position("testPosition1")
                                                     .company("testCompany1")
                                                     .salary("1")
                                                     .url("https://example1.com")
                                                     .build();
        OfferRequestDto requestDto2 = OfferRequestDto.builder()
                                                     .position("testPosition2")
                                                     .company("testCompany2")
                                                     .salary("2")
                                                     .url("https://example2.com")
                                                     .build();
        OfferFacade facade = OfferFacadeConfig.createForTest(repository);
        //when
        OfferDto response1 = facade.saveOffer(requestDto1);
        OfferDto response2 = facade.saveOffer(requestDto2);
        //then
        OfferDto expected1 = OfferDto.builder()
                                     .id(1L)
                                     .position("testPosition1")
                                     .company("testCompany1")
                                     .salary("1")
                                     .url("https://example1.com")
                                     .build();
        OfferDto expected2 = OfferDto.builder()
                                     .id(2L)
                                     .position("testPosition2")
                                     .company("testCompany2")
                                     .salary("2")
                                     .url("https://example2.com")
                                     .build();
        assertThat(response1).isEqualTo(expected1);
        assertThat(response2).isEqualTo(expected2);
    }
    
    @Test
    void should_throw_an_error_while_saving_offer_with_duplicate_url() {
        //given
        String url = "https://example.com";
        OfferRequestDto requestDto1 = OfferRequestDto.builder()
                                                     .position("testPosition1")
                                                     .company("testCompany1")
                                                     .salary("1")
                                                     .url(url)
                                                     .build();
        OfferRequestDto requestDto2 = OfferRequestDto.builder()
                                                     .position("testPosition2")
                                                     .company("testCompany2")
                                                     .salary("2")
                                                     .url(url)
                                                     .build();
        OfferFacade facade = OfferFacadeConfig.createForTest(repository);
        facade.saveOffer(requestDto1);
        //when
        Exception caught = catchException(() -> facade.saveOffer(requestDto2));
        //then
        assertThat(caught).isInstanceOf(DuplicateOfferUrlException.class);
        assertThat(caught.getMessage()).isEqualTo("There is already offer with url " + url);
    }
    
    @Test
    void should_find_two_offers_when_there_are_two_offers_in_db() {
        //given
        OfferRequestDto requestDto1 = OfferRequestDto.builder()
                                                     .position("testPosition1")
                                                     .company("testCompany1")
                                                     .salary("1")
                                                     .url("https://example1.com")
                                                     .build();
        OfferRequestDto requestDto2 = OfferRequestDto.builder()
                                                     .position("testPosition2")
                                                     .company("testCompany2")
                                                     .salary("2")
                                                     .url("https://example2.com")
                                                     .build();
        OfferFacade facade = OfferFacadeConfig.createForTest(repository);
        facade.saveOffer(requestDto1);
        facade.saveOffer(requestDto2);
        //when
        List<OfferDto> response = facade.findAllOffers();
        //then
        OfferDto expected1 = OfferDto.builder()
                                     .id(1L)
                                     .position("testPosition1")
                                     .company("testCompany1")
                                     .salary("1")
                                     .url("https://example1.com")
                                     .build();
        OfferDto expected2 = OfferDto.builder()
                                     .id(2L)
                                     .position("testPosition2")
                                     .company("testCompany2")
                                     .salary("2")
                                     .url("https://example2.com")
                                     .build();
        assertThat(response).isNotEmpty();
        assertThat(response).hasSize(2);
        assertThat(response).contains(expected1, expected2);
    }
    
    @Test
    void should_find_an_offer_by_id() {
        //given
        Long id = 1L;
        OfferRequestDto offerDto = OfferRequestDto.builder()
                                                  .position("testPosition")
                                                  .company("testCompany")
                                                  .salary("0")
                                                  .url("https://example.com")
                                                  .build();
        OfferFacade facade = OfferFacadeConfig.createForTest(repository);
        facade.saveOffer(offerDto);
        //when
        OfferDto response = facade.findOfferById(id);
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