package com.timcooki.jnuwiki.domain.member.DTO.response.admin;

import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequestType;
import lombok.Builder;


public record NewReadResDTO(
        Long docsRequestId,
        DocsRequestType docsRequestType,
        String docsRequestCategory,
        String docsRequestName,
        DocsLocation docsRequestLocation
) {
    @Builder
    public NewReadResDTO {

    }
}
