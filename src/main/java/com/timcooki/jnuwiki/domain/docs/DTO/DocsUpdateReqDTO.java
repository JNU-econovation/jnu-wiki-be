package com.timcooki.jnuwiki.domain.docs.DTO;


import lombok.Builder;

import java.util.List;

public record DocsUpdateReqDTO(
        String docsName,
        // TODO - ENUM으로 변경할 것
        String docsCategory,
        List<Integer> docsLocation,
        String docsContent,
        String docsModifiedBy)
{
    @Builder
    public DocsUpdateReqDTO{
    }
}
