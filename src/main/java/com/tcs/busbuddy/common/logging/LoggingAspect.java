package com.tcs.busbuddy.common.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect //tells Spring:This class contains cross-cutting logic (like logging, security, transaction handling) that should run alongside your regular code
@Component
@Slf4j //shortcut that automatically generates a Logger instance in your class
public class LoggingAspect {

    /**
     * Pointcut to capture all methods in com.tcs.busbuddy packages
     * except logging package itself to avoid circular calls
     */
    @Pointcut("within(com.tcs.busbuddy..*) " +
              "&& !within(com.tcs.busbuddy.common.logging..*) " +
              "&& !within(com.tcs.busbuddy.security.JwtAuthenticationFilter)")
    public void applicationPackagePointcut() {
        // Pointcut for entire application
    }

    /**
     * Logs method entry with arguments
     */
    @Before("applicationPackagePointcut()")
    public void logMethodEntry(JoinPoint joinPoint) {
        log.info("➡️ Entering: {}.{}() with arguments = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * Logs method exit with return value
     */
    @AfterReturning(pointcut = "applicationPackagePointcut()", returning = "result")
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        log.info("✅ Exiting: {}.{}() with result = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                result);
    }

    /**
     * Logs exceptions thrown from methods
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut()", throwing = "ex")
    public void logExceptions(JoinPoint joinPoint, Throwable ex) {
        log.error("❌ Exception in {}.{}() with message = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                ex.getMessage(), ex);
    }
}
