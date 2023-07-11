package com.timcooki.jnuwiki.domain.member.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinReqDTO {

    private String email;
    private String nickname;
    private String password;
}
