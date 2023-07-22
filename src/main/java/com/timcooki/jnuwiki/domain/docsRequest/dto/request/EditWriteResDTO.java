package com.timcooki.jnuwiki.domain.docsRequest.dto.request;

import lombok.Builder;

public record EditWriteResDTO(
        String message
) {
    @Builder
    public EditWriteResDTO {
    }
}