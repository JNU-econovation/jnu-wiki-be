package com.timcooki.jnuwiki.domain.member.DTO;

import com.timcooki.jnuwiki.domain.member.DTO.Dummy.Token;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResDTO {
    private Token token;
}
