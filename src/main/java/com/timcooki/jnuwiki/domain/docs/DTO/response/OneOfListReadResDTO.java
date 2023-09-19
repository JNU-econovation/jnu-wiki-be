package com.timcooki.jnuwiki.domain.docs.DTO.response;

import lombok.Builder;

public record OneOfListReadResDTO(
        Long docsId,
        String docsName,
        String docsCategory,
        boolean scrap

) {
    @Builder
    public OneOfListReadResDTO {
    }
}
