package com.timcooki.jnuwiki.domain.member.DTO.request;

import lombok.Builder;

public record EditNicknameReqDTO(String nickname) {
    @Builder
    public EditNicknameReqDTO {
    }
}
