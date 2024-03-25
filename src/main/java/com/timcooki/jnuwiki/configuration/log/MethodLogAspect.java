package com.timcooki.jnuwiki.configuration.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class MethodLogAspect {
    @Around("execution(* com.timcooki.jnuwiki..*(..)) && " +
            "!execution(* com.timcooki.jnuwiki.configuration.log.MethodLogAspect.*.*(..))" +
            "!execution(* com.timcooki.jnuwiki.util.errors.*.*(..))")
    public Object logExecutionTime(final ProceedingJoinPoint joinPoint) throws Throwable {
        if (!log.isInfoEnabled()) {
            return joinPoint.proceed();
        }

        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();

        long totalTimeMillis = stopWatch.getTotalTimeMillis();

        final String className = joinPoint.getSignature()
                .getDeclaringType()
                .getSimpleName();
        final String methodName = joinPoint.getSignature()
                .getName();

        log.info("{}.{}, 실행시간 {}ms", className, methodName, totalTimeMillis);

        return result;
    }
}
