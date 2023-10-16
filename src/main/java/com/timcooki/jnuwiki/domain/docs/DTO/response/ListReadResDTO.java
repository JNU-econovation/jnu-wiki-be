package com.timcooki.jnuwiki.domain.docs.DTO.response;

import lombok.Builder;

import java.util.List;

public record ListReadResDTO(
        List<OneOfListReadResDTO> docsList,
        int totalPages
) {
    @Builder
    public ListReadResDTO {
    }
}

