package com.joboffers.domain.offer;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

class OffersRepositoryTestImpl implements OffersRepository {
    
    private final Map<Long, Offer> inMemoryDatabase = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    
    @Override
    public Offer save(final @NotNull Offer offer) {
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
    
    @Override
    public Optional<Offer> findByUrl(final String url) {
        return inMemoryDatabase.values()
                               .stream()
                               .filter(offer -> offer.url()
                                                     .equals(url))
                               .findFirst();
    }
    
    @Override
    public List<Offer> findAll() {
        return inMemoryDatabase.values()
                               .stream()
                               .toList();
    }
    
    @Override
    public <S extends Offer> List<S> saveAll(final Iterable<S> entities) {
        return List.of();
    }
    
    @Override
    public Optional<Offer> findById(final String s) {
        return Optional.empty();
    }
    
    @Override
    public boolean existsById(final String s) {
        return false;
    }
    
    @Override
    public List<Offer> findAllById(final Iterable<String> strings) {
        return List.of();
    }
    
    @Override
    public long count() {
        return 0;
    }
    
    @Override
    public void deleteById(final String s) {
    
    }
    
    @Override
    public void delete(final Offer entity) {
    
    }
    
    @Override
    public void deleteAllById(final Iterable<? extends String> strings) {
    
    }
    
    @Override
    public void deleteAll(final Iterable<? extends Offer> entities) {
    
    }
    
    @Override
    public void deleteAll() {
    
    }
    
    @Override
    public Optional<Offer> findById(final Long id) {
        return Optional.ofNullable(inMemoryDatabase.get(id));
    }
    
    @Override
    public <S extends Offer> S insert(final S entity) {
        return null;
    }
    
    @Override
    public <S extends Offer> List<S> insert(final Iterable<S> entities) {
        return List.of();
    }
    
    @Override
    public <S extends Offer> Optional<S> findOne(final Example<S> example) {
        return Optional.empty();
    }
    
    @Override
    public <S extends Offer> List<S> findAll(final Example<S> example) {
        return List.of();
    }
    
    @Override
    public <S extends Offer> List<S> findAll(final Example<S> example, final Sort sort) {
        return List.of();
    }
    
    @Override
    public <S extends Offer> Page<S> findAll(final Example<S> example, final Pageable pageable) {
        return null;
    }
    
    @Override
    public <S extends Offer> long count(final Example<S> example) {
        return 0;
    }
    
    @Override
    public <S extends Offer> boolean exists(final Example<S> example) {
        return false;
    }
    
    @Override
    public <S extends Offer, R> R findBy(final Example<S> example, final Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
    
    @Override
    public List<Offer> findAll(final Sort sort) {
        return List.of();
    }
    
    @Override
    public Page<Offer> findAll(final Pageable pageable) {
        return null;
    }
}

