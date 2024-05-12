package com.timcooki.jnuwiki.util.errors.exception;

import com.timcooki.jnuwiki.util.ApiResult;
import com.timcooki.jnuwiki.util.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;


// 유효성 검사 실패, 잘못된 파라메터 요청
@Getter
public class Exception400 extends RuntimeException {

    public static final String NOT_FOUND_MEMBER = "존재하지 않는 회원입니다.";
    public static final String NOT_FOUND_DOCS = "존재하지 않는 문서입니다.";

    public Exception400(String message) {
        super(message);
    }

    public ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.BAD_REQUEST);
    }

    public HttpStatus status(){
        return HttpStatus.BAD_REQUEST;
    }
}