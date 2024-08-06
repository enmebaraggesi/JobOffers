package com.joboffers.domain.offer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

class OffersRepositoryTestImpl implements OffersRepository {
    
    private final Map<Long, Offer> inMemoryDatabase = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    
    @Override
    public List<Offer> findAll() {
        return inMemoryDatabase.values()
                               .stream()
                               .toList();
    }
    
    @Override
    public Optional<Offer> findById(final Long id) {
        return Optional.ofNullable(inMemoryDatabase.get(id));
    }
    
    @Override
    public Offer save(final Offer offer) {
        long id = idCounter.getAndIncrement();
        Offer toSave = Offer.builder()
                            .id(id)
                            .position(offer.position())
                            .company(offer.company())
                            .salary(offer.salary())
                            .url(offer.url())
                            .build();
        inMemoryDatabase.put(id, toSave);
        return toSave;
    }
}
