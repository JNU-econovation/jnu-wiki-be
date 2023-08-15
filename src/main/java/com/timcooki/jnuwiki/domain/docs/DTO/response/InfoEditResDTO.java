package com.timcooki.jnuwiki.domain.docs.DTO.response;

import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import lombok.Builder;
import java.time.LocalDateTime;

public record InfoEditResDTO(
        Long docsId,
        String docsName,
        String docsCategory,
        DocsLocation docsLocation,
        String docsContent,
        String docsModifiedAt
) {
    @Builder
    public InfoEditResDTO {

    }
}


