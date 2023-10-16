package com.timcooki.jnuwiki.domain.member.DTO.request;

import lombok.Builder;

public record EditReqDTO(
        // TODO 유효성 검증 : password
        String nickname,
        String password
) {
    @Builder
    public EditReqDTO {

    }
}



