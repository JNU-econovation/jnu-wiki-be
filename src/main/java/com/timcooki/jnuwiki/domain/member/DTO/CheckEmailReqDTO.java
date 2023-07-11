package com.timcooki.jnuwiki.domain.member.DTO;


import lombok.Builder;

public record CheckEmailReqDTO(
        String email
) {
    @Builder
    public CheckEmailReqDTO{

    }
}