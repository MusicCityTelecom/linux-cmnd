/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.logging;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.logging.ClientLoggingFilter;
import org.glassfish.jersey.logging.LoggingInterceptor;
import org.glassfish.jersey.logging.ServerLoggingFilter;

public class LoggingFeature
implements Feature {
    public static final String DEFAULT_LOGGER_NAME = LoggingFeature.class.getName();
    public static final String DEFAULT_LOGGER_LEVEL = Level.FINE.getName();
    public static final int DEFAULT_MAX_ENTITY_SIZE = 8192;
    public static final Verbosity DEFAULT_VERBOSITY = Verbosity.PAYLOAD_TEXT;
    private static final String LOGGER_NAME_POSTFIX = ".logger.name";
    private static final String LOGGER_LEVEL_POSTFIX = ".logger.level";
    private static final String VERBOSITY_POSTFIX = ".verbosity";
    private static final String MAX_ENTITY_POSTFIX = ".entity.maxSize";
    private static final String LOGGING_FEATURE_COMMON_PREFIX = "jersey.config.logging";
    public static final String LOGGING_FEATURE_LOGGER_NAME = "jersey.config.logging.logger.name";
    public static final String LOGGING_FEATURE_LOGGER_LEVEL = "jersey.config.logging.logger.level";
    public static final String LOGGING_FEATURE_VERBOSITY = "jersey.config.logging.verbosity";
    public static final String LOGGING_FEATURE_MAX_ENTITY_SIZE = "jersey.config.logging.entity.maxSize";
    private static final String LOGGING_FEATURE_SERVER_PREFIX = "jersey.config.server.logging";
    public static final String LOGGING_FEATURE_LOGGER_NAME_SERVER = "jersey.config.server.logging.logger.name";
    public static final String LOGGING_FEATURE_LOGGER_LEVEL_SERVER = "jersey.config.server.logging.logger.level";
    public static final String LOGGING_FEATURE_VERBOSITY_SERVER = "jersey.config.server.logging.verbosity";
    public static final String LOGGING_FEATURE_MAX_ENTITY_SIZE_SERVER = "jersey.config.server.logging.entity.maxSize";
    private static final String LOGGING_FEATURE_CLIENT_PREFIX = "jersey.config.client.logging";
    public static final String LOGGING_FEATURE_LOGGER_NAME_CLIENT = "jersey.config.client.logging.logger.name";
    public static final String LOGGING_FEATURE_LOGGER_LEVEL_CLIENT = "jersey.config.client.logging.logger.level";
    public static final String LOGGING_FEATURE_VERBOSITY_CLIENT = "jersey.config.client.logging.verbosity";
    public static final String LOGGING_FEATURE_MAX_ENTITY_SIZE_CLIENT = "jersey.config.client.logging.entity.maxSize";
    private final Logger filterLogger;
    private final Verbosity verbosity;
    private final Integer maxEntitySize;
    private final Level level;

    public LoggingFeature() {
        this(null, null, null, null);
    }

    public LoggingFeature(Logger logger) {
        this(logger, null, null, null);
    }

    public LoggingFeature(Logger logger, Verbosity verbosity) {
        this(logger, null, verbosity, null);
    }

    public LoggingFeature(Logger logger, Integer maxEntitySize) {
        this(logger, null, DEFAULT_VERBOSITY, maxEntitySize);
    }

    public LoggingFeature(Logger logger, Level level, Verbosity verbosity, Integer maxEntitySize) {
        this.filterLogger = logger;
        this.level = level;
        this.verbosity = verbosity;
        this.maxEntitySize = maxEntitySize;
    }

    @Override
    public boolean configure(FeatureContext context) {
        boolean enabled = false;
        if (context.getConfiguration().getRuntimeType() == RuntimeType.CLIENT) {
            ClientLoggingFilter clientLoggingFilter = (ClientLoggingFilter)this.createLoggingFilter(context, RuntimeType.CLIENT);
            context.register(clientLoggingFilter);
            enabled = true;
        }
        if (context.getConfiguration().getRuntimeType() == RuntimeType.SERVER) {
            ServerLoggingFilter serverClientFilter = (ServerLoggingFilter)this.createLoggingFilter(context, RuntimeType.SERVER);
            context.register(serverClientFilter);
            enabled = true;
        }
        return enabled;
    }

    private LoggingInterceptor createLoggingFilter(FeatureContext context, RuntimeType runtimeType) {
        Map<String, Object> properties = context.getConfiguration().getProperties();
        String filterLoggerName = CommonProperties.getValue(properties, runtimeType == RuntimeType.SERVER ? LOGGING_FEATURE_LOGGER_NAME_SERVER : LOGGING_FEATURE_LOGGER_NAME_CLIENT, CommonProperties.getValue(properties, LOGGING_FEATURE_LOGGER_NAME, DEFAULT_LOGGER_NAME));
        String filterLevel = CommonProperties.getValue(properties, runtimeType == RuntimeType.SERVER ? LOGGING_FEATURE_LOGGER_LEVEL_SERVER : LOGGING_FEATURE_LOGGER_LEVEL_CLIENT, CommonProperties.getValue(context.getConfiguration().getProperties(), LOGGING_FEATURE_LOGGER_LEVEL, DEFAULT_LOGGER_LEVEL));
        Verbosity filterVerbosity = CommonProperties.getValue(properties, runtimeType == RuntimeType.SERVER ? LOGGING_FEATURE_VERBOSITY_SERVER : LOGGING_FEATURE_VERBOSITY_CLIENT, CommonProperties.getValue(properties, LOGGING_FEATURE_VERBOSITY, DEFAULT_VERBOSITY));
        int filterMaxEntitySize = CommonProperties.getValue(properties, runtimeType == RuntimeType.SERVER ? LOGGING_FEATURE_MAX_ENTITY_SIZE_SERVER : LOGGING_FEATURE_MAX_ENTITY_SIZE_CLIENT, CommonProperties.getValue(properties, LOGGING_FEATURE_MAX_ENTITY_SIZE, 8192));
        Level loggerLevel = Level.parse(filterLevel);
        if (runtimeType == RuntimeType.SERVER) {
            return new ServerLoggingFilter(this.filterLogger != null ? this.filterLogger : Logger.getLogger(filterLoggerName), this.level != null ? this.level : loggerLevel, this.verbosity != null ? this.verbosity : filterVerbosity, this.maxEntitySize != null ? this.maxEntitySize : filterMaxEntitySize);
        }
        return new ClientLoggingFilter(this.filterLogger != null ? this.filterLogger : Logger.getLogger(filterLoggerName), this.level != null ? this.level : loggerLevel, this.verbosity != null ? this.verbosity : filterVerbosity, this.maxEntitySize != null ? this.maxEntitySize : filterMaxEntitySize);
    }

    public static enum Verbosity {
        HEADERS_ONLY,
        PAYLOAD_TEXT,
        PAYLOAD_ANY;

    }
}

