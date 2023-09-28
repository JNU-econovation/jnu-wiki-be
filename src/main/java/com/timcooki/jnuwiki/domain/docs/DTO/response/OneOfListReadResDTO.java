package com.timcooki.jnuwiki.domain.docs.DTO.response;

import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import lombok.Builder;

public record OneOfListReadResDTO(
        Long docsId,
        String docsName,
        String docsCategory,
        DocsLocation docsLocation,
        boolean scrap

) {
    @Builder
    public OneOfListReadResDTO {
    }
}
