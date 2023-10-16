package com.timcooki.jnuwiki.domain.member.DTO.request;

import lombok.Builder;

public record LoginReqDTO(
        String email,
        String password
) {
    @Builder
    public LoginReqDTO{

    }
}
