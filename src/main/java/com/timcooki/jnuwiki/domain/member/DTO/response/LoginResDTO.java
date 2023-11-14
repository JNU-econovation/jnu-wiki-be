package com.timcooki.jnuwiki.domain.member.DTO.response;

import lombok.Builder;

public record LoginResDTO(
        Long id,
        String role,
        Long expiration
) {
    @Builder
    public LoginResDTO {

    }
}
