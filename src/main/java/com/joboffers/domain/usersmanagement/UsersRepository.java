package com.joboffers.domain.usersmanagement;

import java.util.List;
import java.util.Optional;

interface UsersRepository {
    
    List<User> findAll();
    
    Optional<User> findByName(String name);
    
    Optional<User> findById(Long id);
    
    User save(User user);
    
    boolean existsByEmail(String email);
    
    boolean existsByName(String name);
}
