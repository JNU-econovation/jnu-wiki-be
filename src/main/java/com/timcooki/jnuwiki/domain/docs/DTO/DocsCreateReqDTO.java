package com.timcooki.jnuwiki.domain.docs.DTO;

import lombok.Builder;

import java.util.List;


public record DocsCreateReqDTO (

    String docsName,
    // TODO - ENUM으로 변경
    String docsCategory,
    List<Integer> docsLocation,
    String docsContent,
    String docsCreatedBy
){
    @Builder
    public DocsCreateReqDTO {
    }
}

