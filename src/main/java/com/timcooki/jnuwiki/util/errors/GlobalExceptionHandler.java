package com.timcooki.jnuwiki.util.errors;

import com.timcooki.jnuwiki.util.ApiResult;
import com.timcooki.jnuwiki.util.ApiUtils;
import com.timcooki.jnuwiki.util.errors.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception400.class)
    public ResponseEntity<?> badRequest(Exception400 e){
        log.warn("[BAD REQUEST] {}", e.getMessage());
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception401.class)
    public ResponseEntity<?> unAuthorized(Exception401 e){
        log.warn("[UNAUTHORIZED] {}", e.getMessage());
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception403.class)
    public ResponseEntity<?> forbidden(Exception403 e){
        log.warn("[UNAUTHORIZED] {}", e.getMessage());
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception404.class)
    public ResponseEntity<?> notFound(Exception404 e){
        log.warn("[NOT FOUND] {}", e.getMessage());
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception500.class)
    public ResponseEntity<?> serverError(Exception500 e){
        log.error("[SERVER ERROR] {}", e.getMessage());
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validationError(
            MethodArgumentNotValidException e) {
        log.error("[VALIDATION ERROR] {}", e.getMessage());
        ApiResult<?> apiResult = ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(apiResult);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unknownServerError(Exception e){
        ApiResult<?> apiResult = ApiUtils.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        log.error("[UNKNOWN SERVER ERROR] {}", e.getMessage());
        return new ResponseEntity<>(apiResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}