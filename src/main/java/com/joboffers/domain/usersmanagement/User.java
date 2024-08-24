package com.joboffers.domain.usersmanagement;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document
record User(
        @Id String id,
        @Indexed(unique = true) String name,
        @Indexed(unique = true) String email,
        String password) {
    
}
