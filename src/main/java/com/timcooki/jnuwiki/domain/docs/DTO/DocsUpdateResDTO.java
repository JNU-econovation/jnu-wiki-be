package com.timcooki.jnuwiki.domain.docs.DTO;

import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

public record DocsUpdateResDTO(
        Long docsId,
        String docsName,
        // TODO - ENUM으로 변경
        String docsCategory,
        DocsLocation docsLocation,
        String docsContent,
        String docsModifiedBy,
        LocalDateTime docsModifiedAt
) {
    @Builder
    public DocsUpdateResDTO{

    }
}


