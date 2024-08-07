package com.joboffers.domain.usersmanagement;

import java.util.List;
import java.util.Optional;

interface UsersRepository {
    
    List<User> findAll();
    
    Optional<User> findById(Long id);
}
