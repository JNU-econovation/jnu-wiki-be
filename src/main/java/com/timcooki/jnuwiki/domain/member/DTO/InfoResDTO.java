package com.timcooki.jnuwiki.domain.member.DTO;

import lombok.Builder;

public record InfoResDTO(
         Long id,
         String nickname,
         String password
) {
    @Builder
    public InfoResDTO{
        
    }
}
