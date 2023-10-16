package com.timcooki.jnuwiki.domain.member.DTO.request;

import lombok.Builder;

public record EditPasswordReqDTO(String password) {
    @Builder
    public EditPasswordReqDTO {
    }
}
