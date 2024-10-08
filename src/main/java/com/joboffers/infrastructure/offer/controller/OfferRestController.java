package com.joboffers.infrastructure.offer.controller;

import com.joboffers.domain.offer.OfferFacade;
import com.joboffers.domain.offer.dto.OfferRequestDto;
import com.joboffers.domain.offer.dto.OfferResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("offers")
@AllArgsConstructor
class OfferRestController {
    
    private final OfferFacade offerFacade;
    
    @GetMapping
    public ResponseEntity<List<OfferResponseDto>> getAllOffers() {
        List<OfferResponseDto> offers = offerFacade.findAllOffers();
        return ResponseEntity.ok(offers);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<OfferResponseDto> getOfferById(@PathVariable String id) {
        OfferResponseDto offerById = offerFacade.findOfferById(id);
        return ResponseEntity.ok(offerById);
    }
    
    @PostMapping
    public ResponseEntity<OfferResponseDto> createOffer(@RequestBody @Valid OfferRequestDto requestDto) {
        OfferResponseDto saved = offerFacade.saveOffer(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
