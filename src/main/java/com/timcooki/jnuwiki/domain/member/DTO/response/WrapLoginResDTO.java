package com.timcooki.jnuwiki.domain.member.DTO.response;

import com.timcooki.jnuwiki.util.ApiResult;
import lombok.Builder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public record WrapLoginResDTO<T>(
        HttpStatus status,
        HttpHeaders headers,
        ApiResult<? extends T> body

) {
    @Builder
    public WrapLoginResDTO{
    }
}
