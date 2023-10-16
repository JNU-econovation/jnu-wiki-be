package com.timcooki.jnuwiki.domain.member.DTO.response.admin;

import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequestType;
import lombok.Builder;

import java.time.LocalDateTime;

public record EditReadResDTO(
        Long docsRequestId,
        Long docsId, // 수정하려는 문서
        DocsRequestType docsRequestType,
        String docsRequestCategory,
        String docsRequestName,
        DocsLocation docsRequestLocation
) {
    @Builder
    public EditReadResDTO {

    }

}
