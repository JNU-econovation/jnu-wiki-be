package com.timcooki.jnuwiki.domain.member.DTO.response;

import lombok.Builder;

public record LoginResDTO(
        Long id,
        String role,
        Long accessTokenExpiration,
        String accessTokenFormattedExpiration,
        Long refreshTokenExpiration,
        String refreshTokenFormattedExpiration
) {
    @Builder
    public LoginResDTO {

    }
}
