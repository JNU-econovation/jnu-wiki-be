package com.timcooki.jnuwiki.domain.member.DTO.response;

import lombok.Builder;

public record ReadResDTO(
        Long id,
        String nickName,
        String password
) {
    @Builder
    public ReadResDTO{

    }
}
