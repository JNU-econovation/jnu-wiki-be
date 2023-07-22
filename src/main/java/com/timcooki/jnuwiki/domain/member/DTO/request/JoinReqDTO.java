package com.timcooki.jnuwiki.domain.member.DTO.request;

import lombok.Builder;

public record JoinReqDTO(
        String email,
        String nickName,
        String password
) {
    @Builder
    public JoinReqDTO{

    }
}
