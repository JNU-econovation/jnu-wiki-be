package com.timcooki.jnuwiki.domain.member.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginReqDTO {
    private String email;
    private String password;
}
