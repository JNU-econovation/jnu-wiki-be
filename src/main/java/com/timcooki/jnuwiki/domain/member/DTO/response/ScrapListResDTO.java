package com.timcooki.jnuwiki.domain.member.DTO.response;

import lombok.Builder;

import java.util.List;

public record ScrapListResDTO(
        List<ScrapResDTO> scrapList,
        int totalPages
) {

    @Builder
    public ScrapListResDTO {
    }
}

