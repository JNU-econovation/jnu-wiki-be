package com.timcooki.jnuwiki._core.util;

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
