package com.joboffers.domain.usersmanagement;

import java.util.List;

interface UsersRepository {
    
    List<User> findAll();
}
