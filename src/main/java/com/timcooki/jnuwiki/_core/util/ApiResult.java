package com.timcooki.jnuwiki._core.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public
class ApiResult<T> {
    private final boolean success;
    private final T response;
    private final ApiError error;
}