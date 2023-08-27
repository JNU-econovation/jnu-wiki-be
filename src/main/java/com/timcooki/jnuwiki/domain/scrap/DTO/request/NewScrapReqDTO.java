package com.timcooki.jnuwiki.domain.scrap.DTO.request;

import lombok.Builder;

public record NewScrapReqDTO(
        Long memberId,
        Long docsId
) {
    @Builder
    public NewScrapReqDTO{

    }
}
