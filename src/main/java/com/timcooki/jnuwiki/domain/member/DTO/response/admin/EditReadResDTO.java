package com.timcooki.jnuwiki.domain.member.DTO.response.admin;

import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequestType;
import lombok.Builder;

@Builder
public record EditReadResDTO(
        Long docsRequestId,
        Long docsId,
        DocsRequestType docsRequestType,
        String docsRequestCategory,
        String docsRequestName,
        DocsLocation docsRequestLocation
) {
}
