package com.joboffers.domain.usersmanagement;

import lombok.Builder;

@Builder
record User(Long id,
            String name,
            String email,
            String password) {
    
}
