package com.timcooki.jnuwiki.domain.docs.DTO;

import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import lombok.Builder;
import java.time.LocalDateTime;

public record InfoEditResDTO(
        Long docsId,
        String docsName,
        DocsCategory docsCategory,
        DocsLocation docsLocation,
        String docsContent,
        LocalDateTime docsModifiedAt
) {
    @Builder
    public InfoEditResDTO {

    }
}


