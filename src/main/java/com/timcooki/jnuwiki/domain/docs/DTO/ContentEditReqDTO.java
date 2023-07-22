package com.timcooki.jnuwiki.domain.docs.DTO;


import lombok.Builder;

public record ContentEditReqDTO(
        String docsContent
) {
    @Builder
    public ContentEditReqDTO {

    }
}

