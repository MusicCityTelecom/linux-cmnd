/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ch.qos.logback.classic.Level
 *  ch.qos.logback.classic.Logger
 *  ch.qos.logback.classic.LoggerContext
 *  ch.qos.logback.classic.jul.LevelChangePropagator
 *  ch.qos.logback.classic.spi.LoggerContextListener
 *  ch.qos.logback.classic.turbo.TurboFilter
 *  ch.qos.logback.classic.util.ContextInitializer
 *  ch.qos.logback.core.Context
 *  ch.qos.logback.core.joran.spi.JoranException
 *  ch.qos.logback.core.spi.FilterReply
 *  ch.qos.logback.core.status.OnConsoleStatusListener
 *  ch.qos.logback.core.status.Status
 *  ch.qos.logback.core.util.StatusListenerConfigHelper
 *  org.slf4j.ILoggerFactory
 *  org.slf4j.Marker
 *  org.slf4j.bridge.SLF4JBridgeHandler
 *  org.slf4j.impl.StaticLoggerBinder
 *  org.springframework.core.SpringProperties
 *  org.springframework.core.annotation.Order
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.Environment
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ResourceUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.logging.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.jul.LevelChangePropagator;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.core.status.OnConsoleStatusListener;
import ch.qos.logback.core.status.Status;
import ch.qos.logback.core.util.StatusListenerConfigHelper;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import org.slf4j.ILoggerFactory;
import org.slf4j.Marker;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.boot.logging.AbstractLoggingSystem;
import org.springframework.boot.logging.LogFile;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggerConfiguration;
import org.springframework.boot.logging.LoggingInitializationContext;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.boot.logging.LoggingSystemFactory;
import org.springframework.boot.logging.LoggingSystemProperties;
import org.springframework.boot.logging.Slf4JLoggingSystem;
import org.springframework.boot.logging.logback.DebugLogbackConfigurator;
import org.springframework.boot.logging.logback.DefaultLogbackConfiguration;
import org.springframework.boot.logging.logback.LogbackConfigurator;
import org.springframework.boot.logging.logback.LogbackLoggingSystemProperties;
import org.springframework.boot.logging.logback.SpringBootJoranConfigurator;
import org.springframework.core.SpringProperties;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

public class LogbackLoggingSystem
extends Slf4JLoggingSystem {
    private static final boolean XML_ENABLED = !SpringProperties.getFlag((String)"spring.xml.ignore");
    private static final String CONFIGURATION_FILE_PROPERTY = "logback.configurationFile";
    private static final AbstractLoggingSystem.LogLevels<Level> LEVELS = new AbstractLoggingSystem.LogLevels();
    private static final TurboFilter FILTER;

    public LogbackLoggingSystem(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public LoggingSystemProperties getSystemProperties(ConfigurableEnvironment environment) {
        return new LogbackLoggingSystemProperties((Environment)environment);
    }

    @Override
    protected String[] getStandardConfigLocations() {
        return new String[]{"logback-test.groovy", "logback-test.xml", "logback.groovy", "logback.xml"};
    }

    @Override
    public void beforeInitialize() {
        LoggerContext loggerContext = this.getLoggerContext();
        if (this.isAlreadyInitialized(loggerContext)) {
            return;
        }
        super.beforeInitialize();
        loggerContext.getTurboFilterList().add((Object)FILTER);
    }

    @Override
    public void initialize(LoggingInitializationContext initializationContext, String configLocation, LogFile logFile) {
        LoggerContext loggerContext = this.getLoggerContext();
        if (this.isAlreadyInitialized(loggerContext)) {
            return;
        }
        super.initialize(initializationContext, configLocation, logFile);
        loggerContext.getTurboFilterList().remove((Object)FILTER);
        this.markAsInitialized(loggerContext);
        if (StringUtils.hasText((String)System.getProperty(CONFIGURATION_FILE_PROPERTY))) {
            this.getLogger(LogbackLoggingSystem.class.getName()).warn("Ignoring 'logback.configurationFile' system property. Please use 'logging.config' instead.");
        }
    }

    @Override
    protected void loadDefaults(LoggingInitializationContext initializationContext, LogFile logFile) {
        LoggerContext context = this.getLoggerContext();
        this.stopAndReset(context);
        boolean debug = Boolean.getBoolean("logback.debug");
        if (debug) {
            StatusListenerConfigHelper.addOnConsoleListenerInstance((Context)context, (OnConsoleStatusListener)new OnConsoleStatusListener());
        }
        Environment environment = initializationContext.getEnvironment();
        new LogbackLoggingSystemProperties(environment, (arg_0, arg_1) -> ((LoggerContext)context).putProperty(arg_0, arg_1)).apply(logFile);
        LogbackConfigurator configurator = debug ? new DebugLogbackConfigurator(context) : new LogbackConfigurator(context);
        new DefaultLogbackConfiguration(logFile).apply(configurator);
        context.setPackagingDataEnabled(true);
    }

    @Override
    protected void loadConfiguration(LoggingInitializationContext initializationContext, String location, LogFile logFile) {
        super.loadConfiguration(initializationContext, location, logFile);
        LoggerContext loggerContext = this.getLoggerContext();
        this.stopAndReset(loggerContext);
        try {
            this.configureByResourceUrl(initializationContext, loggerContext, ResourceUtils.getURL((String)location));
        }
        catch (Exception ex) {
            throw new IllegalStateException("Could not initialize Logback logging from " + location, ex);
        }
        List statuses = loggerContext.getStatusManager().getCopyOfStatusList();
        StringBuilder errors = new StringBuilder();
        for (Status status : statuses) {
            if (status.getLevel() != 2) continue;
            errors.append(errors.length() > 0 ? String.format("%n", new Object[0]) : "");
            errors.append(status.toString());
        }
        if (errors.length() > 0) {
            throw new IllegalStateException(String.format("Logback configuration error detected: %n%s", errors));
        }
    }

    private void configureByResourceUrl(LoggingInitializationContext initializationContext, LoggerContext loggerContext, URL url) throws JoranException {
        if (XML_ENABLED && url.toString().endsWith("xml")) {
            SpringBootJoranConfigurator configurator = new SpringBootJoranConfigurator(initializationContext);
            configurator.setContext((Context)loggerContext);
            configurator.doConfigure(url);
        } else {
            new ContextInitializer(loggerContext).configureByResource(url);
        }
    }

    private void stopAndReset(LoggerContext loggerContext) {
        loggerContext.stop();
        loggerContext.reset();
        if (this.isBridgeHandlerInstalled()) {
            this.addLevelChangePropagator(loggerContext);
        }
    }

    private boolean isBridgeHandlerInstalled() {
        if (!this.isBridgeHandlerAvailable()) {
            return false;
        }
        java.util.logging.Logger rootLogger = LogManager.getLogManager().getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        return handlers.length == 1 && handlers[0] instanceof SLF4JBridgeHandler;
    }

    private void addLevelChangePropagator(LoggerContext loggerContext) {
        LevelChangePropagator levelChangePropagator = new LevelChangePropagator();
        levelChangePropagator.setResetJUL(true);
        levelChangePropagator.setContext((Context)loggerContext);
        loggerContext.addListener((LoggerContextListener)levelChangePropagator);
    }

    @Override
    public void cleanUp() {
        LoggerContext context = this.getLoggerContext();
        this.markAsUninitialized(context);
        super.cleanUp();
        context.getStatusManager().clear();
        context.getTurboFilterList().remove((Object)FILTER);
    }

    @Override
    protected void reinitialize(LoggingInitializationContext initializationContext) {
        this.getLoggerContext().reset();
        this.getLoggerContext().getStatusManager().clear();
        this.loadConfiguration(initializationContext, this.getSelfInitializationConfig(), null);
    }

    @Override
    public List<LoggerConfiguration> getLoggerConfigurations() {
        ArrayList<LoggerConfiguration> result = new ArrayList<LoggerConfiguration>();
        for (Logger logger : this.getLoggerContext().getLoggerList()) {
            result.add(this.getLoggerConfiguration(logger));
        }
        result.sort(CONFIGURATION_COMPARATOR);
        return result;
    }

    @Override
    public LoggerConfiguration getLoggerConfiguration(String loggerName) {
        String name = this.getLoggerName(loggerName);
        LoggerContext loggerContext = this.getLoggerContext();
        return this.getLoggerConfiguration(loggerContext.exists(name));
    }

    private String getLoggerName(String name) {
        if (!StringUtils.hasLength((String)name) || "ROOT".equals(name)) {
            return "ROOT";
        }
        return name;
    }

    private LoggerConfiguration getLoggerConfiguration(Logger logger) {
        if (logger == null) {
            return null;
        }
        LogLevel level = LEVELS.convertNativeToSystem(logger.getLevel());
        LogLevel effectiveLevel = LEVELS.convertNativeToSystem(logger.getEffectiveLevel());
        String name = this.getLoggerName(logger.getName());
        return new LoggerConfiguration(name, level, effectiveLevel);
    }

    @Override
    public Set<LogLevel> getSupportedLogLevels() {
        return LEVELS.getSupported();
    }

    @Override
    public void setLogLevel(String loggerName, LogLevel level) {
        Logger logger = this.getLogger(loggerName);
        if (logger != null) {
            logger.setLevel(LEVELS.convertSystemToNative(level));
        }
    }

    @Override
    public Runnable getShutdownHandler() {
        return () -> this.getLoggerContext().stop();
    }

    private Logger getLogger(String name) {
        LoggerContext factory = this.getLoggerContext();
        return factory.getLogger(this.getLoggerName(name));
    }

    private LoggerContext getLoggerContext() {
        ILoggerFactory factory = StaticLoggerBinder.getSingleton().getLoggerFactory();
        Assert.isInstanceOf(LoggerContext.class, (Object)factory, () -> String.format("LoggerFactory is not a Logback LoggerContext but Logback is on the classpath. Either remove Logback or the competing implementation (%s loaded from %s). If you are using WebLogic you will need to add 'org.slf4j' to prefer-application-packages in WEB-INF/weblogic.xml", factory.getClass(), this.getLocation(factory)));
        return (LoggerContext)factory;
    }

    private Object getLocation(ILoggerFactory factory) {
        try {
            ProtectionDomain protectionDomain = factory.getClass().getProtectionDomain();
            CodeSource codeSource = protectionDomain.getCodeSource();
            if (codeSource != null) {
                return codeSource.getLocation();
            }
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        return "unknown location";
    }

    private boolean isAlreadyInitialized(LoggerContext loggerContext) {
        return loggerContext.getObject(LoggingSystem.class.getName()) != null;
    }

    private void markAsInitialized(LoggerContext loggerContext) {
        loggerContext.putObject(LoggingSystem.class.getName(), new Object());
    }

    private void markAsUninitialized(LoggerContext loggerContext) {
        loggerContext.removeObject(LoggingSystem.class.getName());
    }

    static {
        LEVELS.map(LogLevel.TRACE, Level.TRACE);
        LEVELS.map(LogLevel.TRACE, Level.ALL);
        LEVELS.map(LogLevel.DEBUG, Level.DEBUG);
        LEVELS.map(LogLevel.INFO, Level.INFO);
        LEVELS.map(LogLevel.WARN, Level.WARN);
        LEVELS.map(LogLevel.ERROR, Level.ERROR);
        LEVELS.map(LogLevel.FATAL, Level.ERROR);
        LEVELS.map(LogLevel.OFF, Level.OFF);
        FILTER = new TurboFilter(){

            public FilterReply decide(Marker marker, Logger logger, Level level, String format, Object[] params, Throwable t) {
                return FilterReply.DENY;
            }
        };
    }

    @Order(value=0x7FFFFFFF)
    public static class Factory
    implements LoggingSystemFactory {
        private static final boolean PRESENT = ClassUtils.isPresent((String)"ch.qos.logback.classic.LoggerContext", (ClassLoader)Factory.class.getClassLoader());

        @Override
        public LoggingSystem getLoggingSystem(ClassLoader classLoader) {
            if (PRESENT) {
                return new LogbackLoggingSystem(classLoader);
            }
            return null;
        }
    }
}

