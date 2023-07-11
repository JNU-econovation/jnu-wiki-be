package com.timcooki.jnuwiki.domain.member.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InfoResDTO {
    private Long id;
    private String nickname;
    private String password;
}
