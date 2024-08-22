package com.joboffers.domain.offer;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document
record Offer(
        @Id
        Long id,
        String position,
        String company,
        String salary,
        String url) {
    
}
