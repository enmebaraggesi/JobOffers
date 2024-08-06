package com.joboffers.domain.offer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class OffersRepositoryTestImpl implements OffersRepository {
    
    private final Map<String, Offer> inMemoryDatabase = new ConcurrentHashMap<>();
    
    @Override
    public List<Offer> findAll() {
        return inMemoryDatabase.values()
                               .stream()
                               .toList();
    }
    
    @Override
    public Optional<Offer> findById(final String id) {
        return Optional.ofNullable(inMemoryDatabase.get(id));
    }
}
