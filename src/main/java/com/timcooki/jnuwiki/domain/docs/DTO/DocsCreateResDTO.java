package com.timcooki.jnuwiki.domain.docs.DTO;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public record DocsCreateResDTO(
        Long docsId,
        String docsName,
        String docsCategory,
        List<Integer> docsLocation,
        String docsContent,
        String docsCreatedBy,
        LocalDateTime docsCreatedAt
) {
    @Builder
    public DocsCreateResDTO {
    }
}
