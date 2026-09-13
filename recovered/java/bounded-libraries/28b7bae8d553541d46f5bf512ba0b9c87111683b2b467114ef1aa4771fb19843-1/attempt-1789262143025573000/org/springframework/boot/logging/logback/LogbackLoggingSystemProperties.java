/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ch.qos.logback.core.util.FileSize
 *  org.springframework.core.convert.ConversionFailedException
 *  org.springframework.core.convert.ConverterNotFoundException
 *  org.springframework.core.env.Environment
 *  org.springframework.core.env.PropertyResolver
 *  org.springframework.util.unit.DataSize
 */
package org.springframework.boot.logging.logback;

import ch.qos.logback.core.util.FileSize;
import java.nio.charset.Charset;
import java.util.function.BiConsumer;
import org.springframework.boot.logging.LogFile;
import org.springframework.boot.logging.LoggingSystemProperties;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.util.unit.DataSize;

public class LogbackLoggingSystemProperties
extends LoggingSystemProperties {
    public static final String ROLLINGPOLICY_FILE_NAME_PATTERN = "LOGBACK_ROLLINGPOLICY_FILE_NAME_PATTERN";
    public static final String ROLLINGPOLICY_CLEAN_HISTORY_ON_START = "LOGBACK_ROLLINGPOLICY_CLEAN_HISTORY_ON_START";
    public static final String ROLLINGPOLICY_MAX_FILE_SIZE = "LOGBACK_ROLLINGPOLICY_MAX_FILE_SIZE";
    public static final String ROLLINGPOLICY_TOTAL_SIZE_CAP = "LOGBACK_ROLLINGPOLICY_TOTAL_SIZE_CAP";
    public static final String ROLLINGPOLICY_MAX_HISTORY = "LOGBACK_ROLLINGPOLICY_MAX_HISTORY";

    public LogbackLoggingSystemProperties(Environment environment) {
        super(environment);
    }

    public LogbackLoggingSystemProperties(Environment environment, BiConsumer<String, String> setter) {
        super(environment, setter);
    }

    @Override
    protected Charset getDefaultCharset() {
        return Charset.defaultCharset();
    }

    @Override
    protected void apply(LogFile logFile, PropertyResolver resolver) {
        super.apply(logFile, resolver);
        this.applyRollingPolicy(resolver, ROLLINGPOLICY_FILE_NAME_PATTERN, "logging.logback.rollingpolicy.file-name-pattern", "logging.pattern.rolling-file-name");
        this.applyRollingPolicy(resolver, ROLLINGPOLICY_CLEAN_HISTORY_ON_START, "logging.logback.rollingpolicy.clean-history-on-start", "logging.file.clean-history-on-start");
        this.applyRollingPolicy(resolver, ROLLINGPOLICY_MAX_FILE_SIZE, "logging.logback.rollingpolicy.max-file-size", "logging.file.max-size", DataSize.class);
        this.applyRollingPolicy(resolver, ROLLINGPOLICY_TOTAL_SIZE_CAP, "logging.logback.rollingpolicy.total-size-cap", "logging.file.total-size-cap", DataSize.class);
        this.applyRollingPolicy(resolver, ROLLINGPOLICY_MAX_HISTORY, "logging.logback.rollingpolicy.max-history", "logging.file.max-history");
    }

    private void applyRollingPolicy(PropertyResolver resolver, String systemPropertyName, String propertyName, String deprecatedPropertyName) {
        this.applyRollingPolicy(resolver, systemPropertyName, propertyName, deprecatedPropertyName, String.class);
    }

    private <T> void applyRollingPolicy(PropertyResolver resolver, String systemPropertyName, String propertyName, String deprecatedPropertyName, Class<T> type) {
        T value = this.getProperty(resolver, propertyName, type);
        if (value == null) {
            value = this.getProperty(resolver, deprecatedPropertyName, type);
        }
        if (value != null) {
            String stringValue = String.valueOf(value instanceof DataSize ? Long.valueOf(((DataSize)value).toBytes()) : value);
            this.setSystemProperty(systemPropertyName, stringValue);
        }
    }

    private <T> T getProperty(PropertyResolver resolver, String key, Class<T> type) {
        try {
            return (T)resolver.getProperty(key, type);
        }
        catch (ConversionFailedException | ConverterNotFoundException ex) {
            if (type != DataSize.class) {
                throw ex;
            }
            String value = resolver.getProperty(key);
            return (T)DataSize.ofBytes((long)FileSize.valueOf((String)value).getSize());
        }
    }
}

