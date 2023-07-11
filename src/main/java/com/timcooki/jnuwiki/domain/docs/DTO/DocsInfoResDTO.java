package com.timcooki.jnuwiki.domain.docs.DTO;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public record DocsInfoResDTO(
        Long id,
        String docsName,
        // TODO Type 변경 - ENUM
        String docsCategory,
         List<Integer> docsLocation,
         String docsContent,
         String docsCreatedBy,
         LocalDateTime docsCreatedAt
) {
    @Builder
    public DocsInfoResDTO{
    }
}
