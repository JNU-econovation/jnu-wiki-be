package com.timcooki.jnuwiki.domain.docsRequest.dto.response;

import lombok.Builder;

public record GetDocsStatusResDTO(
        boolean docsStatus
) {
    @Builder
    public GetDocsStatusResDTO{}
}
