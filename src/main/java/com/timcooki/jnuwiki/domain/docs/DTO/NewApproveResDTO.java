package com.timcooki.jnuwiki.domain.docs.DTO;

import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import lombok.Builder;

import java.time.LocalDateTime;

public record NewApproveResDTO(
    Long id,
    String docsName,
    DocsCategory docsCategory,
    DocsLocation docsLocation,
    LocalDateTime docsCreatedAt
){
    @Builder
    public NewApproveResDTO {

    }
}
