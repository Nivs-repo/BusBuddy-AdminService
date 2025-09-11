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

    // ----------- CONTROLLER -----------
    @Before("execution(* com.busbuddy.adminservice..controller..*(..))")
    public void logControllerBefore(JoinPoint joinPoint) {
        logMethodEntry(joinPoint, "Controller");
    }

    @AfterReturning(value = "execution(* com.busbuddy.adminservice..controller..*(..))", returning = "response")
    public void logControllerAfterReturning(JoinPoint joinPoint, Object response) {
        logMethodExit(joinPoint, "Controller", response);
    }

    // ----------- SERVICE -----------
    @Before("execution(* com.busbuddy.adminservice..service..*(..))")
    public void logServiceBefore(JoinPoint joinPoint) {
        logMethodEntry(joinPoint, "Service");
    }

    @AfterReturning(value = "execution(* com.busbuddy.adminservice..service..*(..))", returning = "response")
    public void logServiceAfterReturning(JoinPoint joinPoint, Object response) {
        logMethodExit(joinPoint, "Service", response);
    }

    // ----------- REPOSITORY -----------
    @Before("execution(* com.busbuddy.adminservice..repository..*(..))")
    public void logRepositoryBefore(JoinPoint joinPoint) {
        logMethodEntry(joinPoint, "Repository");
    }

    @AfterReturning(value = "execution(* com.busbuddy.adminservice..repository..*(..))", returning = "response")
    public void logRepositoryAfterReturning(JoinPoint joinPoint, Object response) {
        logMethodExit(joinPoint, "Repository", response);
    }

    // ----------- EXCEPTIONS (all layers) -----------
    @AfterThrowing(value = "execution(* com.busbuddy.adminservice..*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        MethodSignature method = (MethodSignature) joinPoint.getSignature();
        log.error("❌ Exception in {}.{}: {}", 
                method.getDeclaringTypeName(), 
                method.getName(), 
                ex.getMessage(), ex);
    }

    // ----------- Helpers -----------
    private void logMethodEntry(JoinPoint joinPoint, String layer) {
        MethodSignature method = (MethodSignature) joinPoint.getSignature();
        log.info("➡ [{}] Entering {}.{} args={}", 
                layer, 
                method.getDeclaringTypeName(), 
                method.getName(), 
                joinPoint.getArgs());
    }

    private void logMethodExit(JoinPoint joinPoint, String layer, Object response) {
        MethodSignature method = (MethodSignature) joinPoint.getSignature();
        // For repositories, avoid logging full list/objects
        if ("Repository".equals(layer) && response instanceof java.util.Collection) {
            log.info("⬅ [{}] Exiting {}.{} returned {} records", 
                    layer, 
                    method.getDeclaringTypeName(), 
                    method.getName(), 
                    ((java.util.Collection<?>) response).size());
        } else {
            log.info("⬅ [{}] Exiting {}.{} response={}", 
                    layer, 
                    method.getDeclaringTypeName(), 
                    method.getName(), 
                    response);
        }
    }
}
