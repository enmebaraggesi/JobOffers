package com.joboffers.domain.usersmanagement;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document
record User(
        @Id
        Long id,
        String name,
        String email,
        String password) {
    
}
