package com.timcooki.jnuwiki.domain.docsRequest.dto;

import lombok.Builder;

public record DocsMessage(
        Long docsId,
        Long memberId
) {
    @Builder
    public DocsMessage{}
}
