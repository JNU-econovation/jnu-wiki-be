package com.timcooki.jnuwiki.domain.scrap.DTO.request;

import lombok.Builder;

public record DeleteScrapReqDTO(
        Long memberId,
        Long docsId
) {
    @Builder
    public DeleteScrapReqDTO{

    }
}
