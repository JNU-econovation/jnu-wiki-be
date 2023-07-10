package com.timcooki.jnuwiki.domain.docs.dto;

import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;

import java.time.LocalDateTime;

public record DocsCreateDto (
    Long id,
    String docsRequestName,
    DocsCategory docsRequestCategory,
    DocsLocation docsRequestLocation,
    LocalDateTime docsCreatedAt
){
}
