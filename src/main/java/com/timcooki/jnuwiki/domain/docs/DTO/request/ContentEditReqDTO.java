package com.timcooki.jnuwiki.domain.docs.DTO.request;


import lombok.Builder;

public record ContentEditReqDTO(
        String docsContent
) {
    @Builder
    public ContentEditReqDTO {

    }
}

