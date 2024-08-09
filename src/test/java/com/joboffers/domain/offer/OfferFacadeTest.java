package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.OfferRequestDto;
import com.joboffers.domain.offer.dto.OfferResponseDto;
import com.joboffers.domain.offer.error.DuplicateOfferUrlException;
import com.joboffers.domain.offer.error.OfferNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

class OfferFacadeTest {
    
    @Test
    void should_find_no_offer_when_there_are_no_offers() {
        //given
        OfferFacade facade = OfferFacadeTestConfig.createForTest();
        //when
        List<OfferResponseDto> response = facade.findAllOffers();
        //then
        assertThat(response).isEmpty();
    }
    
    @Test
    void should_throw_an_error_and_find_no_offer_by_id_when_there_are_no_offers() {
        //given
        Long id = 1L;
        OfferFacade facade = OfferFacadeTestConfig.createForTest();
        assertThat(facade.findAllOffers()).isEmpty();
        //when
        Exception caught = catchException(() -> facade.findOfferById(id));
        //then
        assertThat(caught).isInstanceOf(OfferNotFoundException.class);
        assertThat(caught.getMessage()).isEqualTo("Offer not found");
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
        OfferFacade facade = OfferFacadeTestConfig.createForTest();
        assertThat(facade.findAllOffers()).isEmpty();
        //when
        OfferResponseDto response = facade.saveOffer(requestDto);
        //then
        assertThat(response).isEqualTo(OfferResponseDto.builder()
                                                       .id(1L)
                                                       .position("testPosition")
                                                       .company("testCompany")
                                                       .salary("0")
                                                       .url("https://example.com")
                                                       .build());
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
        OfferFacade facade = OfferFacadeTestConfig.createForTest();
        assertThat(facade.findAllOffers()).isEmpty();
        //when
        OfferResponseDto response1 = facade.saveOffer(requestDto1);
        OfferResponseDto response2 = facade.saveOffer(requestDto2);
        //then
        assertThat(response1).isEqualTo(OfferResponseDto.builder()
                                                        .id(1L)
                                                        .position("testPosition1")
                                                        .company("testCompany1")
                                                        .salary("1")
                                                        .url("https://example1.com")
                                                        .build());
        assertThat(response2).isEqualTo(OfferResponseDto.builder()
                                                        .id(2L)
                                                        .position("testPosition2")
                                                        .company("testCompany2")
                                                        .salary("2")
                                                        .url("https://example2.com")
                                                        .build());
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
        OfferFacade facade = OfferFacadeTestConfig.createForTest();
        facade.saveOffer(requestDto1);
        //when
        Exception caught = catchException(() -> facade.saveOffer(requestDto2));
        //then
        assertThat(caught).isInstanceOf(DuplicateOfferUrlException.class);
        assertThat(caught.getMessage()).isEqualTo("There is already an offer with such url");
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
        OfferFacade facade = OfferFacadeTestConfig.createForTest();
        facade.saveOffer(requestDto1);
        facade.saveOffer(requestDto2);
        //when
        List<OfferResponseDto> response = facade.findAllOffers();
        //then
        assertThat(response).isNotEmpty()
                            .hasSize(2)
                            .contains(OfferResponseDto.builder()
                                                      .id(1L)
                                                      .position("testPosition1")
                                                      .company("testCompany1")
                                                      .salary("1")
                                                      .url("https://example1.com")
                                                      .build(),
                                      OfferResponseDto.builder()
                                                      .id(2L)
                                                      .position("testPosition2")
                                                      .company("testCompany2")
                                                      .salary("2")
                                                      .url("https://example2.com")
                                                      .build());
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
        OfferFacade facade = OfferFacadeTestConfig.createForTest();
        assertThat(facade.findAllOffers()).isEmpty();
        facade.saveOffer(offerDto);
        //when
        OfferResponseDto response = facade.findOfferById(id);
        //then
        assertThat(response).isEqualTo(OfferResponseDto.builder()
                                                       .id(1L)
                                                       .position("testPosition")
                                                       .company("testCompany")
                                                       .salary("0")
                                                       .url("https://example.com")
                                                       .build());
    }
    
    @Test
    void should_fetch_new_offers_from_external_if_there_are_no_offers_in_db() {
        //given
        String firstOfferUrl = "https://example1.com";
        String secondOfferUrl = "https://example2.com";
        String thirdOfferUrl = "https://example3.com";
        OfferFacade facade = OfferFacadeTestConfig.createForTestWithExternalThreeOffers();
        assertThat(facade.findAllOffers()).isEmpty();
        //when
        facade.fetchNewOffers();
        List<OfferResponseDto> response = facade.findAllOffers();
        //then
        assertThat(response).hasSize(3);
        assertThat(List.of(response.get(0).url(),
                           response.get(1).url(),
                           response.get(2).url())).containsAll(List.of(firstOfferUrl,
                                                                       secondOfferUrl,
                                                                       thirdOfferUrl));
    }
    
    @Test
    void should_save_only_2_from_fetched_offers_if_there_is_already_one_of_them_in_db() {
        //given
        OfferRequestDto existing = OfferRequestDto.builder()
                                                  .position("testPosition1")
                                                  .company("testCompany1")
                                                  .salary("1")
                                                  .url("https://example1.com")
                                                  .build();
        OfferFacade facade = OfferFacadeTestConfig.createForTestWithExternalThreeOffers();
        facade.saveOffer(existing);
        assertThat(facade.findAllOffers()).hasSize(1);
        //when
        facade.fetchNewOffers();
        List<OfferResponseDto> response = facade.findAllOffers();
        //then
        assertThat(response).hasSize(3);
    }
}