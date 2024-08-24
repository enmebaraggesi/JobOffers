package com.joboffers.domain.usersmanagement;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UsersRepository extends MongoRepository<User, String> {
    
    Optional<User> findByName(String name);
    
    boolean existsByEmail(String email);
    
    boolean existsByName(String name);
}
