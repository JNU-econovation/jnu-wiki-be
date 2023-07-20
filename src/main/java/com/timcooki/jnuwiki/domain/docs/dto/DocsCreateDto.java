package com.timcooki.jnuwiki.domain.docs.DTO;

import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import lombok.Builder;

import java.time.LocalDateTime;

public record DocsCreateDTO(
    Long id,
    String docsRequestName,
    DocsCategory docsRequestCategory,
    DocsLocation docsRequestLocation,
    LocalDateTime docsCreatedAt
){
    @Builder
    public DocsCreateDTO{

    }
}
