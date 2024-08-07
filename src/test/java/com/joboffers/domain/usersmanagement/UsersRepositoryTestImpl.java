package com.joboffers.domain.usersmanagement;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class UsersRepositoryTestImpl implements UsersRepository {
    
    private final Map<Long, User> inMemoryDatabase = new ConcurrentHashMap<>();
    
    @Override
    public List<User> findAll() {
        return inMemoryDatabase.values()
                               .stream()
                               .toList();
    }
}
