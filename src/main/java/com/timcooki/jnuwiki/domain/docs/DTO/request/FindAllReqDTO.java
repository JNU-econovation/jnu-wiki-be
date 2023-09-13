package com.timcooki.jnuwiki.domain.docs.DTO.request;

import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import lombok.Builder;

public record FindAllReqDTO(
        DocsLocation rightUp,
        DocsLocation leftDown
) {
    @Builder
    public FindAllReqDTO{

    }
}
