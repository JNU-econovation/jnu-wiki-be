package com.timcooki.jnuwiki.domain.member.service;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Validator {

    private final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";

    private final String PASSWORD_REGEX =
            "^((?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W]).{8,20})$";

    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidPassword(String pw) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(pw);
        return matcher.matches();
    }

}
