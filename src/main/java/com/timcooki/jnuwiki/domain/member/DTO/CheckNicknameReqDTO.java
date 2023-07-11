package com.timcooki.jnuwiki.domain.member.DTO;

import lombok.Builder;

public record CheckNicknameReqDTO(
        String nickname
) {
    @Builder
    public CheckNicknameReqDTO{

    }
}
