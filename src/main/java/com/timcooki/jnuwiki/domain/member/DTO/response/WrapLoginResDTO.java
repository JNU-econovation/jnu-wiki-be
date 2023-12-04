package com.timcooki.jnuwiki.domain.member.DTO.response;

import lombok.Builder;
import org.springframework.http.HttpHeaders;

@Builder
public record WrapLoginResDTO<T>(
        HttpHeaders headers,
        LoginResDTO body

) {
}
