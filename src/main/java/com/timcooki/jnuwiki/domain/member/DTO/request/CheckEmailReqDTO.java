package com.timcooki.jnuwiki.domain.member.DTO.request;


import lombok.Builder;

public record CheckEmailReqDTO(
        String email
) {
    @Builder
    public CheckEmailReqDTO{

    }
}