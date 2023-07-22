package com.timcooki.jnuwiki.domain.member.DTO;

import lombok.Builder;

public record EditReqDTO(
        String nickname,
        String password
) {
    @Builder
    public EditReqDTO {

    }
}



