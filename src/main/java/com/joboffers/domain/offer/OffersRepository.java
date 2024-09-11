package com.joboffers.domain.offer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface OffersRepository extends MongoRepository<Offer, String> {
    
    boolean existsByUrl(String url);
}
