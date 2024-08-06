package com.joboffers.domain.offer;

import java.util.List;
import java.util.Optional;

interface OffersRepository {
    
    List<Offer> findAll();
    
    Optional<Offer> findById(String id);
}
