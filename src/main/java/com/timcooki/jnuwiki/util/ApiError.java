package com.timcooki.jnuwiki.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
class ApiError {

    private final String message;
    private final int status;

}
