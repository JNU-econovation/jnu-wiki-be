package com.timcooki.jnuwiki.domain.member.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckNicknameReqDTO {

    @Builder
    public CheckNicknameReqDTO(String nickname){
        this.nickname = nickname;
    }

    private String nickname;
}
