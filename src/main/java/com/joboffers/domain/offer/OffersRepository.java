package com.joboffers.domain.offer;

import java.util.List;

interface OffersRepository {
    
    List<Offer> findAll();
}
