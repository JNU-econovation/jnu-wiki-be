package com.timcooki.jnuwiki.domain.docs.DTO.response;

import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import lombok.Builder;

import java.time.LocalDateTime;

public record ListReadResDTO(
        Long docsId,
        String docsName,
        // TODO Type 변경 - ENUM
        String docsCategory,
         DocsLocation docsLocation,
         String docsContent,
         String docsCreatedBy,
        LocalDateTime docsCreatedAt,
        LocalDateTime docsModifiedAt
) {
    @Builder
    public ListReadResDTO {
    }
}
