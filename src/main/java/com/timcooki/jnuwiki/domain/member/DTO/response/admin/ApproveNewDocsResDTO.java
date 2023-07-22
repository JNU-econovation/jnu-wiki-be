package com.timcooki.jnuwiki.domain.member.DTO.response.admin;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public record ApproveNewDocsResDTO(
        Long id,
        String docsName,
        String docsCategory,
        List<Integer> docsLocation,
        LocalDateTime docsCreatedAt
) {
    @Builder
    public ApproveNewDocsResDTO{

    }
}
