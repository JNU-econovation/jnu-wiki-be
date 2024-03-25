package com.timcooki.jnuwiki.configuration.log;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class RequestLogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             @NonNull final HttpServletResponse response,
                             @NonNull final Object handler) {

        MDC.put("correlationId", UUID.randomUUID()
            .toString()
            .substring(0, 8));

        log.info("Request : {} {}{}",
            request.getMethod(),
            request.getRequestURI(),
            getQueryParameters(request));

        return true;
    }

    @Override
    public void afterCompletion(@NonNull final HttpServletRequest request,
                                @NonNull final HttpServletResponse response,
                                @NonNull final Object handler,
                                final Exception ex) {
        MDC.remove("correlationId");
    }

    private String getQueryParameters(final HttpServletRequest request) {
        final String queryString = request.getQueryString();

        if (queryString == null) return StringUtils.EMPTY;
        return "?" + queryString;
    }
}
