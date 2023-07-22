package com.timcooki.jnuwiki.domain.member.DTO.response.admin;

import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequestType;
import lombok.Builder;

import java.time.LocalDateTime;

public record NewReadResDTO(
        DocsRequestType docsRequestType,
        DocsCategory docsRequestCategory,
        String docsRequestName,
        DocsLocation docsRequestLocation,
        LocalDateTime docsRequestedAt
) {
    @Builder
    public NewReadResDTO {

    }
}
