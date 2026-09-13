/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.Environment
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.logging;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import org.springframework.boot.logging.LogFile;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggerConfiguration;
import org.springframework.boot.logging.LoggingInitializationContext;
import org.springframework.boot.logging.LoggingSystemFactory;
import org.springframework.boot.logging.LoggingSystemProperties;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

public abstract class LoggingSystem {
    public static final String SYSTEM_PROPERTY = LoggingSystem.class.getName();
    public static final String NONE = "none";
    public static final String ROOT_LOGGER_NAME = "ROOT";
    private static final LoggingSystemFactory SYSTEM_FACTORY = LoggingSystemFactory.fromSpringFactories();

    public LoggingSystemProperties getSystemProperties(ConfigurableEnvironment environment) {
        return new LoggingSystemProperties((Environment)environment);
    }

    public abstract void beforeInitialize();

    public void initialize(LoggingInitializationContext initializationContext, String configLocation, LogFile logFile) {
    }

    public void cleanUp() {
    }

    public Runnable getShutdownHandler() {
        return null;
    }

    public Set<LogLevel> getSupportedLogLevels() {
        return EnumSet.allOf(LogLevel.class);
    }

    public void setLogLevel(String loggerName, LogLevel level) {
        throw new UnsupportedOperationException("Unable to set log level");
    }

    public List<LoggerConfiguration> getLoggerConfigurations() {
        throw new UnsupportedOperationException("Unable to get logger configurations");
    }

    public LoggerConfiguration getLoggerConfiguration(String loggerName) {
        throw new UnsupportedOperationException("Unable to get logger configuration");
    }

    public static LoggingSystem get(ClassLoader classLoader) {
        String loggingSystemClassName = System.getProperty(SYSTEM_PROPERTY);
        if (StringUtils.hasLength((String)loggingSystemClassName)) {
            if (NONE.equals(loggingSystemClassName)) {
                return new NoOpLoggingSystem();
            }
            return LoggingSystem.get(classLoader, loggingSystemClassName);
        }
        LoggingSystem loggingSystem = SYSTEM_FACTORY.getLoggingSystem(classLoader);
        Assert.state((loggingSystem != null ? 1 : 0) != 0, (String)"No suitable logging system located");
        return loggingSystem;
    }

    private static LoggingSystem get(ClassLoader classLoader, String loggingSystemClassName) {
        try {
            Class systemClass = ClassUtils.forName((String)loggingSystemClassName, (ClassLoader)classLoader);
            Constructor constructor = systemClass.getDeclaredConstructor(ClassLoader.class);
            constructor.setAccessible(true);
            return (LoggingSystem)constructor.newInstance(classLoader);
        }
        catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    static class NoOpLoggingSystem
    extends LoggingSystem {
        NoOpLoggingSystem() {
        }

        @Override
        public void beforeInitialize() {
        }

        @Override
        public void setLogLevel(String loggerName, LogLevel level) {
        }

        @Override
        public List<LoggerConfiguration> getLoggerConfigurations() {
            return Collections.emptyList();
        }

        @Override
        public LoggerConfiguration getLoggerConfiguration(String loggerName) {
            return null;
        }
    }
}

