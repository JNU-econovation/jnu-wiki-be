package com.timcooki.jnuwiki.domain.member.DTO.request;

import com.timcooki.jnuwiki.domain.member.valid.Nickname;
import lombok.Builder;

public record JoinReqDTO(
        String email,
        @Nickname String nickName,
        String password
) {

    @Builder
    public JoinReqDTO{}
}
