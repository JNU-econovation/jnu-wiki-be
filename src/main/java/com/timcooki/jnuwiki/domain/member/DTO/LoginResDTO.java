package com.timcooki.jnuwiki.domain.member.DTO;

import lombok.Builder;

public record LoginResDTO(
        Long id,
        String role
) {
    @Builder
    public LoginResDTO{

    }
}
