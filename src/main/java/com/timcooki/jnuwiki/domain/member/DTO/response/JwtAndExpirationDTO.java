package com.timcooki.jnuwiki.domain.member.DTO.response;

import lombok.Builder;

import java.time.Instant;

public record JwtAndExpirationDTO(
        String jwt,
        Instant Expiration
) {
    @Builder
    public JwtAndExpirationDTO{
    }
}
