package com.timcooki.jnuwiki.domain.member.valid;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


@Slf4j
public class NicknameValidator implements ConstraintValidator<Nickname, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        log.info("벨리데이션 진행중");
        if (value == null) {
            return false;
        }
        return value.matches(".{1,8}");
    }
}
