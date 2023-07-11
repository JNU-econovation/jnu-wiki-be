package com.timcooki.jnuwiki.domain.docs.DTO;


import lombok.Builder;

public record ModifyDocsReqDTO(
        String docsContent
) {
    @Builder
    public ModifyDocsReqDTO{

    }
}

