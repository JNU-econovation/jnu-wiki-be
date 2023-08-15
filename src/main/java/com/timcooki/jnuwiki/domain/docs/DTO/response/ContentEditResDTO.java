package com.timcooki.jnuwiki.domain.docs.DTO.response;

import lombok.Builder;

import java.time.LocalDateTime;

public record ContentEditResDTO(
        Long id,
        String docsContent,
        String docsModifiedAt
) {
    @Builder
    public ContentEditResDTO {

    }
}

