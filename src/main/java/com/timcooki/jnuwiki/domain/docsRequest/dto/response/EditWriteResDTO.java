package com.timcooki.jnuwiki.domain.docsRequest.dto.response;

import lombok.Builder;

public record EditWriteResDTO(
        String message
) {
    @Builder
    public EditWriteResDTO {
    }
}