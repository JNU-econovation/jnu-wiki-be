package com.timcooki.jnuwiki.domain.docs.dto;

import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;

import java.time.LocalDateTime;

public record DocsUpdateInfoDto(
        Long id,
        String docsRequestName,
        DocsCategory docsRequestCategory,
        DocsLocation docsRequestLocation,
        String docsContent,
        LocalDateTime docsCreatedAt
) {
}
