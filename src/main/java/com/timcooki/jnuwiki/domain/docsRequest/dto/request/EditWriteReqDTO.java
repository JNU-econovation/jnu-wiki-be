package com.timcooki.jnuwiki.domain.docsRequest.dto.request;

import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequestType;
import lombok.Builder;

public record EditWriteReqDTO(
        Long docsId,
        DocsRequestType docsRequestType,
        DocsCategory docsRequestCategory,
        String docsRequestName,
        DocsLocation docsRequestLocation
) {
    @Builder
    public EditWriteReqDTO {

    }
}
