package com.joboffers.domain.usersmanagement;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class UsersRepositoryTestImpl implements UsersRepository {
    
    private final Map<Long, User> inMemoryDatabase = new ConcurrentHashMap<>();
    
    @Override
    public List<User> findAll() {
        return inMemoryDatabase.values()
                               .stream()
                               .toList();
    }
    
    @Override
    public Optional<User> findById(final Long id) {
        return Optional.ofNullable(inMemoryDatabase.get(id));
    }
}
