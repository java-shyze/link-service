package com.links.linkservice.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;;

@Component
public class CustomLogger {

    private final Logger logger = LoggerFactory.getLogger(CustomLogger.class);

    public void info(String action, String message, Object... args) {
        logger.info("[{}] {}", action, String.format(message, args));
    }

    public void debug(String action, String message, Object... args) {
        logger.debug("[{}] {}", action, String.format(message, args));
    }

    public void error(String action, String message, Object... args) {
        logger.error("[{}] {}", action, String.format(message, args));
    }

    public void warn(String action, String message, Object... args) {
        logger.warn("[{}] {}", action, String.format(message, args));
    }

    public void fatal(String action, String message, Object... args) {
        logger.error("[FATAL] [{}] {}", action, String.format(message, args));
    }
}