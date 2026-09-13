/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.exception.ExceptionUtils
 *  org.apereo.cas.util.LogMessageSummarizer
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.util;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apereo.cas.util.DefaultLogMessageSummarizer;
import org.apereo.cas.util.LogMessageSummarizer;
import org.apereo.cas.util.function.FunctionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoggingUtils {
    private static final LogMessageSummarizer LOG_MESSAGE_SUMMARIZER = ServiceLoader.load(LogMessageSummarizer.class).findFirst().orElseGet(DefaultLogMessageSummarizer::new);
    private static final int CHAR_REPEAT_ACCOUNT = 60;

    public static void protocolMessage(String title, Map<String, Object> context) {
        LoggingUtils.protocolMessage(title, context, "");
    }

    public static void protocolMessage(String title, Map<String, Object> context, Object message) {
        StringBuilder builder = new StringBuilder();
        builder.append('\n');
        builder.append(StringUtils.repeat((char)'=', (int)60));
        builder.append(String.format("\n%s\n", title));
        builder.append(StringUtils.repeat((char)'=', (int)60));
        builder.append('\n');
        context.forEach((key, value) -> {
            String toLog = value.toString();
            if (StringUtils.isNotBlank((CharSequence)toLog)) {
                builder.append(String.format("%s: %s\n", key, toLog));
            }
        });
        builder.append(StringUtils.repeat((char)'=', (int)60));
        if (message != null && StringUtils.isNotBlank((CharSequence)message.toString())) {
            builder.append(String.format("\n%s\n", message));
            builder.append(StringUtils.repeat((char)'=', (int)60));
        }
        LoggerFactory.getLogger((String)"PROTOCOL_MESSAGE").info(builder.toString());
    }

    public static void error(Logger logger, String msg) {
        logger.error(msg);
    }

    public static void error(Logger logger, String msg, Throwable throwable) {
        FunctionUtils.doIf(LOG_MESSAGE_SUMMARIZER.shouldSummarize(logger), unused -> logger.error(LOG_MESSAGE_SUMMARIZER.summarizeStackTrace(msg, throwable)), unused -> logger.error(msg, throwable)).accept(throwable);
    }

    public static void error(Logger logger, Throwable throwable) {
        LoggingUtils.error(logger, LoggingUtils.getMessage(throwable), throwable);
    }

    public static void warn(Logger logger, Throwable throwable) {
        LoggingUtils.warn(logger, LoggingUtils.getMessage(throwable), throwable);
    }

    public static void warn(Logger logger, String message, Throwable throwable) {
        FunctionUtils.doIf(LOG_MESSAGE_SUMMARIZER.shouldSummarize(logger), unused -> logger.warn(LOG_MESSAGE_SUMMARIZER.summarizeStackTrace(message, throwable)), unused -> logger.warn(message, throwable)).accept(throwable);
    }

    static String getMessage(Throwable throwable) {
        Optional<String> message;
        if (StringUtils.isEmpty((CharSequence)throwable.getMessage()) && (message = ExceptionUtils.getThrowableList((Throwable)throwable).stream().map(Throwable::getMessage).filter(Objects::nonNull).findFirst()).isPresent()) {
            return message.get();
        }
        return (String)StringUtils.defaultIfEmpty((CharSequence)throwable.getMessage(), (CharSequence)throwable.getClass().getSimpleName());
    }

    @Generated
    private LoggingUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

