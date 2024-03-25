package com.timcooki.jnuwiki.domain.member.DTO.response;

import com.timcooki.jnuwiki.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record ReadResDTO(
        Long id,
        String nickName,
        String password
) {
    public static ReadResDTO of(Member member) {
        return ReadResDTO.builder()
                .id(member.getMemberId())
                .nickName(member.getNickName())
                .password(member.getPassword())
                .build();
    }
}
