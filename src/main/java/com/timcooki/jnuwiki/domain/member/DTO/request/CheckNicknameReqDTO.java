package com.timcooki.jnuwiki.domain.member.DTO.request;

import lombok.Builder;

public record CheckNicknameReqDTO(
        String nickname
) {
    @Builder
    public CheckNicknameReqDTO{

    }
}
