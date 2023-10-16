package com.timcooki.jnuwiki.domain.member.DTO.response;

import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import lombok.Builder;

public record ScrapResDTO(
        Long docsId,
        String docsName,
        String docsCategory,
        DocsLocation docsRequestLocation,
        String member
) {
    @Builder
    public ScrapResDTO {
    }
}
