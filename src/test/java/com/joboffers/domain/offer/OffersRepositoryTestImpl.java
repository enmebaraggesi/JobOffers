package com.joboffers.domain.offer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class OffersRepositoryTestImpl implements OffersRepository {
    
    private final Map<String, Offer> inMemoryDatabase = new ConcurrentHashMap<>();
}
