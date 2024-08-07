package com.joboffers.domain.offer;

import lombok.Builder;

@Builder
record Offer(Long id,
             String position,
             String company,
             String salary,
             String url) {
    
}
