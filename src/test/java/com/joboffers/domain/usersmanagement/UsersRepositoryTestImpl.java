package com.joboffers.domain.usersmanagement;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

class UsersRepositoryTestImpl implements UsersRepository {
    
    private final Map<Long, User> inMemoryDatabase = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    
    @Override
    public Optional<User> findByName(final String name) {
        return inMemoryDatabase.values()
                               .stream()
                               .filter(user -> user.name().equals(name))
                               .findFirst();
    }
    
    @Override
    public Optional<User> findById(final Long id) {
        return Optional.ofNullable(inMemoryDatabase.get(id));
    }
    
    @Override
    public User save(final User user) {
        long id = idCounter.getAndIncrement();
        User saved = User.builder()
                         .id(id)
                         .name(user.name())
                         .email(user.email())
                         .password(user.password())
                         .build();
        inMemoryDatabase.put(id, saved);
        return saved;
    }
    
    @Override
    public boolean existsByEmail(final String email) {
        return inMemoryDatabase.values()
                               .stream()
                               .anyMatch(user -> Objects.equals(user.email(), email));
    }
    
    @Override
    public boolean existsByName(final String name) {
        return inMemoryDatabase.values()
                               .stream()
                               .anyMatch(user -> Objects.equals(user.name(), name));
    }
    
    @Override
    public <S extends User> List<S> saveAll(final Iterable<S> entities) {
        return List.of();
    }
    
    @Override
    public Optional<User> findById(final String s) {
        return Optional.empty();
    }
    
    @Override
    public boolean existsById(final String s) {
        return false;
    }
    
    @Override
    public List<User> findAll() {
        return inMemoryDatabase.values()
                               .stream()
                               .toList();
    }
    
    @Override
    public List<User> findAllById(final Iterable<String> strings) {
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
    public void delete(final User entity) {
    
    }
    
    @Override
    public void deleteAllById(final Iterable<? extends String> strings) {
    
    }
    
    @Override
    public void deleteAll(final Iterable<? extends User> entities) {
    
    }
    
    @Override
    public void deleteAll() {
    
    }
    
    @Override
    public <S extends User> S insert(final S entity) {
        return null;
    }
    
    @Override
    public <S extends User> List<S> insert(final Iterable<S> entities) {
        return List.of();
    }
    
    @Override
    public <S extends User> Optional<S> findOne(final Example<S> example) {
        return Optional.empty();
    }
    
    @Override
    public <S extends User> List<S> findAll(final Example<S> example) {
        return List.of();
    }
    
    @Override
    public <S extends User> List<S> findAll(final Example<S> example, final Sort sort) {
        return List.of();
    }
    
    @Override
    public <S extends User> Page<S> findAll(final Example<S> example, final Pageable pageable) {
        return null;
    }
    
    @Override
    public <S extends User> long count(final Example<S> example) {
        return 0;
    }
    
    @Override
    public <S extends User> boolean exists(final Example<S> example) {
        return false;
    }
    
    @Override
    public <S extends User, R> R findBy(final Example<S> example, final Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
    
    @Override
    public List<User> findAll(final Sort sort) {
        return List.of();
    }
    
    @Override
    public Page<User> findAll(final Pageable pageable) {
        return null;
    }
}
