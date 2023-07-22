package com.timcooki.jnuwiki.domain.docsRequest.dto.request;

import lombok.Builder;

public record NewWriteResDTO(
        String message
) {
    @Builder
    public NewWriteResDTO {

    }
}
