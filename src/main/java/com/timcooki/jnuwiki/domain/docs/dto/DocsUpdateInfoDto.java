package com.timcooki.jnuwiki.domain.docs.DTO;

import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import lombok.Builder;

import java.time.LocalDateTime;

public record DocsUpdateInfoDTO(
        Long id,
        String docsRequestName,
        DocsCategory docsRequestCategory,
        DocsLocation docsRequestLocation,
        String docsContent,
        LocalDateTime docsCreatedAt
) {
    @Builder
    public DocsUpdateInfoDTO {

    }
}
