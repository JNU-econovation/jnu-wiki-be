package com.timcooki.jnuwiki.domain.docs.DTO;

import lombok.Builder;

import java.time.LocalDateTime;

public record ModifyDocsResDTO(
        Long id,
        String docsContent,
        String docsModifiedBy,
        LocalDateTime docsModifiedAt
) {
    @Builder
    public ModifyDocsResDTO{

    }
}

