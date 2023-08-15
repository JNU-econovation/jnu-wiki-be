package com.timcooki.jnuwiki.domain.docs.DTO.response;

import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import lombok.Builder;

import java.time.LocalDateTime;

public record NewApproveResDTO(
    Long id,
    String docsName,
    String docsCategory,
    DocsLocation docsLocation,
    String docsCreatedAt
){
    @Builder
    public NewApproveResDTO {

    }
}
