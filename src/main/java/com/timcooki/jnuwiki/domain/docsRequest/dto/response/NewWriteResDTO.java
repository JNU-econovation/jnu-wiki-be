package com.timcooki.jnuwiki.domain.docsRequest.dto.response;

import lombok.Builder;

public record NewWriteResDTO(
        String message
) {
    @Builder
    public NewWriteResDTO {

    }
}
