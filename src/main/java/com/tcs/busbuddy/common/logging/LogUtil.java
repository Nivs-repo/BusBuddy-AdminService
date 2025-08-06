package com.tcs.busbuddy.common.logging;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class LogUtil {

    // Private constructor to prevent instantiation
    private LogUtil() {
    }

    /**
     * Log informational messages
     */
    public static void info(String message, Object... args) {
        log.info(message, args);
    }

    /**
     * Log warning messages
     */
    public static void warn(String message, Object... args) {
        log.warn(message, args);
    }

    /**
     * Log error messages
     */
    public static void error(String message, Object... args) {
        log.error(message, args);
    }

    /**
     * Log debug messages
     */
    public static void debug(String message, Object... args) {
        log.debug(message, args);
    }
}
