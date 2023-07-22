package com.timcooki.jnuwiki.domain.member.DTO.request;

import lombok.Builder;

public record EditReqDTO(
        String nickname,
        String password
) {
    @Builder
    public EditReqDTO {

    }
}



