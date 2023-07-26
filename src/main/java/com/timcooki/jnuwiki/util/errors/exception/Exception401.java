package com.timcooki.jnuwiki.util.errors.exception;


import com.timcooki.jnuwiki.util.ApiResult;
import com.timcooki.jnuwiki.util.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;


// 인증 안됨
@Getter
public class Exception401 extends RuntimeException {
    public Exception401(String message) {
        super(message);
    }

    public ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.UNAUTHORIZED);
    }

    public HttpStatus status(){
        return HttpStatus.UNAUTHORIZED;
    }
}