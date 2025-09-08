package com.busbuddy.adminservice.common.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.busbuddy.adminservice..controller..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        MethodSignature method = (MethodSignature) joinPoint.getSignature();
        log.info(" Incoming request: {}.{} args={}",
                method.getDeclaringTypeName(),
                method.getName(),
                joinPoint.getArgs());
    }

    @AfterReturning(value = "execution(* com.busbuddy.adminservice..controller..*(..))", returning = "response")
    public void logAfterReturning(JoinPoint joinPoint, Object response) {
        MethodSignature method = (MethodSignature) joinPoint.getSignature();
        log.info(" Response from {}.{}: {}",
                method.getDeclaringTypeName(),
                method.getName(),
                response);
    }

    @AfterThrowing(value = "execution(* com.busbuddy.adminservice..*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        MethodSignature method = (MethodSignature) joinPoint.getSignature();
        log.error(" Exception in {}.{}: {}",
                method.getDeclaringTypeName(),
                method.getName(),
                ex.getMessage(), ex);
    }
}
