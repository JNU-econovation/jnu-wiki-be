package com.timcooki.jnuwiki.domain.docsRequest.dto.request;

import lombok.Builder;

public record CreatedResponseWriteDTO(
        String message
) {
    @Builder
    public CreatedResponseWriteDTO{

    }
}
