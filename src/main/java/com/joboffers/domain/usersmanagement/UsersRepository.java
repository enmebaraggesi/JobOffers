package com.joboffers.domain.usersmanagement;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface UsersRepository extends MongoRepository<User, String> {
    
    Optional<User> findByName(String name);
    
    Optional<User> findById(Long id);
    
    boolean existsByEmail(String email);
    
    boolean existsByName(String name);
}
