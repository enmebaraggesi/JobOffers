package com.joboffers.domain.usersmanagement;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

class UsersRepositoryTestImpl implements UsersRepository {
    
    private final Map<Long, User> inMemoryDatabase = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    
    @Override
    public List<User> findAll() {
        return inMemoryDatabase.values()
                               .stream()
                               .toList();
    }
    
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
}
