package com.joboffers.domain.offer;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface OffersRepository extends MongoRepository<Offer, String> {
    
    Optional<Offer> findById(Long id);
    
    Optional<Offer> findByUrl(String url);
}
