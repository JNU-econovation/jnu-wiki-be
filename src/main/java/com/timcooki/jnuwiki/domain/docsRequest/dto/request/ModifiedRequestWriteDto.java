package com.timcooki.jnuwiki.domain.docsRequest.dto.request;

import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequestType;

public record ModifiedRequestWriteDto(
        Long id,
        DocsRequestType docsRequestType,
        DocsCategory docsRequestCategory,
        String docsRequestName,
        DocsLocation docsRequestLocation
) {
}
