package com.timcooki.jnuwiki.domain.member.dto.response.admin;

import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequestType;

import java.time.LocalDateTime;

public record CreatedRequestFindByIdDto(
        Long id,
        DocsRequestType docsRequestType,
        DocsCategory docsRequestCategory,
        String docsRequestName,
        DocsLocation docsRequestLocation,
        LocalDateTime docsRequestedAt
) {
}
