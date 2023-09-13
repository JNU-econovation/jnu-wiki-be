package com.timcooki.jnuwiki.domain.member.DTO.response;

import lombok.Builder;

public record ScrapResDTO(
        Long docsId,
        String docsName,
        String docsCategory) {
    @Builder
    public ScrapResDTO {
    }
}
