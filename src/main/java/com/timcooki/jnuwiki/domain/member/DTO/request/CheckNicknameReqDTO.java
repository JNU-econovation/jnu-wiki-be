package com.timcooki.jnuwiki.domain.member.DTO.request;

import lombok.Builder;
@Builder
public record CheckNicknameReqDTO(
        String nickname
) {
}
