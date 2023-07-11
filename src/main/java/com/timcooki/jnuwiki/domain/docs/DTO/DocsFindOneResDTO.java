package com.timcooki.jnuwiki.domain.docs.DTO;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public record DocsFindOneResDTO(
        Long id,
        String docsName,
        // TODO 변경 - ENUM
         String docsCategory,
         List<Integer> docsLocation,
         String docsContent,
         String docsCreatedBy,
         LocalDateTime docsCreatedAt
) {
    @Builder
    public DocsFindOneResDTO{

    }
}




