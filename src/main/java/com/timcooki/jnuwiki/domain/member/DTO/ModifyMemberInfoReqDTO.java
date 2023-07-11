package com.timcooki.jnuwiki.domain.member.DTO;

import lombok.Builder;

public record ModifyMemberInfoReqDTO(
        String nickname,
        String password
) {
    @Builder
    public ModifyMemberInfoReqDTO{

    }
}



