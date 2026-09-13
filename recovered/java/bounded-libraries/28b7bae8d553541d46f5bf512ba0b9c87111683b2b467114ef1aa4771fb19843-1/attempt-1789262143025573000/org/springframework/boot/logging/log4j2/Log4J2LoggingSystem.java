/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.Level
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Marker
 *  org.apache.logging.log4j.core.Filter
 *  org.apache.logging.log4j.core.Filter$Result
 *  org.apache.logging.log4j.core.LogEvent
 *  org.apache.logging.log4j.core.Logger
 *  org.apache.logging.log4j.core.LoggerContext
 *  org.apache.logging.log4j.core.config.AbstractConfiguration
 *  org.apache.logging.log4j.core.config.Configuration
 *  org.apache.logging.log4j.core.config.ConfigurationFactory
 *  org.apache.logging.log4j.core.config.ConfigurationSource
 *  org.apache.logging.log4j.core.config.LoggerConfig
 *  org.apache.logging.log4j.core.config.composite.CompositeConfiguration
 *  org.apache.logging.log4j.core.filter.AbstractFilter
 *  org.apache.logging.log4j.core.util.NameUtil
 *  org.apache.logging.log4j.message.Message
 *  org.springframework.core.annotation.Order
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.ResourceUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.logging.log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.AbstractConfiguration;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.composite.CompositeConfiguration;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.core.util.NameUtil;
import org.apache.logging.log4j.message.Message;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.logging.AbstractLoggingSystem;
import org.springframework.boot.logging.LogFile;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggerConfiguration;
import org.springframework.boot.logging.LoggingInitializationContext;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.boot.logging.LoggingSystemFactory;
import org.springframework.boot.logging.Slf4JLoggingSystem;
import org.springframework.core.annotation.Order;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

public class Log4J2LoggingSystem
extends Slf4JLoggingSystem {
    private static final String FILE_PROTOCOL = "file";
    private static final AbstractLoggingSystem.LogLevels<Level> LEVELS = new AbstractLoggingSystem.LogLevels();
    private static final Filter FILTER;

    public Log4J2LoggingSystem(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected String[] getStandardConfigLocations() {
        return this.getCurrentlySupportedConfigLocations();
    }

    private String[] getCurrentlySupportedConfigLocations() {
        ArrayList<String> supportedConfigLocations = new ArrayList<String>();
        this.addTestFiles(supportedConfigLocations);
        supportedConfigLocations.add("log4j2.properties");
        if (this.isClassAvailable("com.fasterxml.jackson.dataformat.yaml.YAMLParser")) {
            Collections.addAll(supportedConfigLocations, "log4j2.yaml", "log4j2.yml");
        }
        if (this.isClassAvailable("com.fasterxml.jackson.databind.ObjectMapper")) {
            Collections.addAll(supportedConfigLocations, "log4j2.json", "log4j2.jsn");
        }
        supportedConfigLocations.add("log4j2.xml");
        return StringUtils.toStringArray(supportedConfigLocations);
    }

    private void addTestFiles(List<String> supportedConfigLocations) {
        supportedConfigLocations.add("log4j2-test.properties");
        if (this.isClassAvailable("com.fasterxml.jackson.dataformat.yaml.YAMLParser")) {
            Collections.addAll(supportedConfigLocations, "log4j2-test.yaml", "log4j2-test.yml");
        }
        if (this.isClassAvailable("com.fasterxml.jackson.databind.ObjectMapper")) {
            Collections.addAll(supportedConfigLocations, "log4j2-test.json", "log4j2-test.jsn");
        }
        supportedConfigLocations.add("log4j2-test.xml");
    }

    protected boolean isClassAvailable(String className) {
        return ClassUtils.isPresent((String)className, (ClassLoader)this.getClassLoader());
    }

    @Override
    public void beforeInitialize() {
        LoggerContext loggerContext = this.getLoggerContext();
        if (this.isAlreadyInitialized(loggerContext)) {
            return;
        }
        super.beforeInitialize();
        loggerContext.getConfiguration().addFilter(FILTER);
    }

    @Override
    public void initialize(LoggingInitializationContext initializationContext, String configLocation, LogFile logFile) {
        LoggerContext loggerContext = this.getLoggerContext();
        if (this.isAlreadyInitialized(loggerContext)) {
            return;
        }
        loggerContext.getConfiguration().removeFilter(FILTER);
        super.initialize(initializationContext, configLocation, logFile);
        this.markAsInitialized(loggerContext);
    }

    @Override
    protected void loadDefaults(LoggingInitializationContext initializationContext, LogFile logFile) {
        if (logFile != null) {
            this.loadConfiguration(this.getPackagedConfigFile("log4j2-file.xml"), logFile, this.getOverrides(initializationContext));
        } else {
            this.loadConfiguration(this.getPackagedConfigFile("log4j2.xml"), logFile, this.getOverrides(initializationContext));
        }
    }

    private List<String> getOverrides(LoggingInitializationContext initializationContext) {
        BindResult<List<String>> overrides = Binder.get(initializationContext.getEnvironment()).bind("logging.log4j2.config.override", Bindable.listOf(String.class));
        return overrides.orElse(Collections.emptyList());
    }

    @Override
    protected void loadConfiguration(LoggingInitializationContext initializationContext, String location, LogFile logFile) {
        super.loadConfiguration(initializationContext, location, logFile);
        this.loadConfiguration(location, logFile, this.getOverrides(initializationContext));
    }

    @Deprecated
    protected void loadConfiguration(String location, LogFile logFile) {
        this.loadConfiguration(location, logFile, Collections.emptyList());
    }

    protected void loadConfiguration(String location, LogFile logFile, List<String> overrides) {
        Assert.notNull((Object)location, (String)"Location must not be null");
        try {
            ArrayList<Configuration> configurations = new ArrayList<Configuration>();
            LoggerContext context = this.getLoggerContext();
            configurations.add(this.load(location, context));
            for (String override : overrides) {
                configurations.add(this.load(override, context));
            }
            CompositeConfiguration configuration = configurations.size() > 1 ? this.createComposite(configurations) : (Configuration)configurations.iterator().next();
            context.start((Configuration)configuration);
        }
        catch (Exception ex) {
            throw new IllegalStateException("Could not initialize Log4J2 logging from " + location, ex);
        }
    }

    private Configuration load(String location, LoggerContext context) throws IOException {
        URL url = ResourceUtils.getURL((String)location);
        ConfigurationSource source = this.getConfigurationSource(url);
        return ConfigurationFactory.getInstance().getConfiguration(context, source);
    }

    private ConfigurationSource getConfigurationSource(URL url) throws IOException {
        InputStream stream = url.openStream();
        if (FILE_PROTOCOL.equals(url.getProtocol())) {
            return new ConfigurationSource(stream, ResourceUtils.getFile((URL)url));
        }
        return new ConfigurationSource(stream, url);
    }

    private CompositeConfiguration createComposite(List<Configuration> configurations) {
        return new CompositeConfiguration(configurations.stream().map(AbstractConfiguration.class::cast).collect(Collectors.toList()));
    }

    @Override
    protected void reinitialize(LoggingInitializationContext initializationContext) {
        List<String> overrides = this.getOverrides(initializationContext);
        if (!CollectionUtils.isEmpty(overrides)) {
            this.reinitializeWithOverrides(overrides);
        } else {
            LoggerContext context = this.getLoggerContext();
            context.reconfigure();
        }
    }

    private void reinitializeWithOverrides(List<String> overrides) {
        LoggerContext context = this.getLoggerContext();
        Configuration base = context.getConfiguration();
        ArrayList<AbstractConfiguration> configurations = new ArrayList<AbstractConfiguration>();
        configurations.add((AbstractConfiguration)base);
        for (String override : overrides) {
            try {
                configurations.add((AbstractConfiguration)this.load(override, context));
            }
            catch (IOException ex) {
                throw new RuntimeException("Failed to load overriding configuration from '" + override + "'", ex);
            }
        }
        CompositeConfiguration composite = new CompositeConfiguration(configurations);
        context.reconfigure((Configuration)composite);
    }

    @Override
    public Set<LogLevel> getSupportedLogLevels() {
        return LEVELS.getSupported();
    }

    @Override
    public void setLogLevel(String loggerName, LogLevel logLevel) {
        this.setLogLevel(loggerName, LEVELS.convertSystemToNative(logLevel));
    }

    private void setLogLevel(String loggerName, Level level) {
        LoggerConfig logger = this.getLogger(loggerName);
        if (level == null) {
            this.clearLogLevel(loggerName, logger);
        } else {
            this.setLogLevel(loggerName, logger, level);
        }
        this.getLoggerContext().updateLoggers();
    }

    private void clearLogLevel(String loggerName, LoggerConfig logger) {
        if (logger instanceof LevelSetLoggerConfig) {
            this.getLoggerContext().getConfiguration().removeLogger(loggerName);
        } else {
            logger.setLevel(null);
        }
    }

    private void setLogLevel(String loggerName, LoggerConfig logger, Level level) {
        if (logger == null) {
            this.getLoggerContext().getConfiguration().addLogger(loggerName, (LoggerConfig)new LevelSetLoggerConfig(loggerName, level, true));
        } else {
            logger.setLevel(level);
        }
    }

    @Override
    public List<LoggerConfiguration> getLoggerConfigurations() {
        ArrayList<LoggerConfiguration> result = new ArrayList<LoggerConfiguration>();
        this.getAllLoggers().forEach((name, loggerConfig) -> result.add(this.convertLoggerConfig((String)name, (LoggerConfig)loggerConfig)));
        result.sort(CONFIGURATION_COMPARATOR);
        return result;
    }

    @Override
    public LoggerConfiguration getLoggerConfiguration(String loggerName) {
        LoggerConfig loggerConfig = this.getAllLoggers().get(loggerName);
        return loggerConfig != null ? this.convertLoggerConfig(loggerName, loggerConfig) : null;
    }

    private Map<String, LoggerConfig> getAllLoggers() {
        LinkedHashMap<String, LoggerConfig> loggers = new LinkedHashMap<String, LoggerConfig>();
        for (Logger logger : this.getLoggerContext().getLoggers()) {
            this.addLogger(loggers, logger.getName());
        }
        this.getLoggerContext().getConfiguration().getLoggers().keySet().forEach(name -> this.addLogger((Map<String, LoggerConfig>)loggers, (String)name));
        return loggers;
    }

    private void addLogger(Map<String, LoggerConfig> loggers, String name) {
        Configuration configuration = this.getLoggerContext().getConfiguration();
        while (name != null) {
            loggers.computeIfAbsent(name, arg_0 -> ((Configuration)configuration).getLoggerConfig(arg_0));
            name = this.getSubName(name);
        }
    }

    private String getSubName(String name) {
        if (!StringUtils.hasLength((String)name)) {
            return null;
        }
        int nested = name.lastIndexOf(36);
        return nested != -1 ? name.substring(0, nested) : NameUtil.getSubName((String)name);
    }

    private LoggerConfiguration convertLoggerConfig(String name, LoggerConfig loggerConfig) {
        boolean isLoggerConfigured;
        if (loggerConfig == null) {
            return null;
        }
        LogLevel level = LEVELS.convertNativeToSystem(loggerConfig.getLevel());
        if (!StringUtils.hasLength((String)name) || "".equals(name)) {
            name = "ROOT";
        }
        LogLevel configuredLevel = (isLoggerConfigured = loggerConfig.getName().equals(name)) ? level : null;
        return new LoggerConfiguration(name, configuredLevel, level);
    }

    @Override
    public Runnable getShutdownHandler() {
        return () -> this.getLoggerContext().stop();
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
        LoggerContext loggerContext = this.getLoggerContext();
        this.markAsUninitialized(loggerContext);
        loggerContext.getConfiguration().removeFilter(FILTER);
    }

    private LoggerConfig getLogger(String name) {
        boolean isRootLogger = !StringUtils.hasLength((String)name) || "ROOT".equals(name);
        return this.findLogger(isRootLogger ? "" : name);
    }

    private LoggerConfig findLogger(String name) {
        Configuration configuration = this.getLoggerContext().getConfiguration();
        if (configuration instanceof AbstractConfiguration) {
            return ((AbstractConfiguration)configuration).getLogger(name);
        }
        return (LoggerConfig)configuration.getLoggers().get(name);
    }

    private LoggerContext getLoggerContext() {
        return (LoggerContext)LogManager.getContext((boolean)false);
    }

    private boolean isAlreadyInitialized(LoggerContext loggerContext) {
        return LoggingSystem.class.getName().equals(loggerContext.getExternalContext());
    }

    private void markAsInitialized(LoggerContext loggerContext) {
        loggerContext.setExternalContext((Object)LoggingSystem.class.getName());
    }

    private void markAsUninitialized(LoggerContext loggerContext) {
        loggerContext.setExternalContext(null);
    }

    static {
        LEVELS.map(LogLevel.TRACE, Level.TRACE);
        LEVELS.map(LogLevel.DEBUG, Level.DEBUG);
        LEVELS.map(LogLevel.INFO, Level.INFO);
        LEVELS.map(LogLevel.WARN, Level.WARN);
        LEVELS.map(LogLevel.ERROR, Level.ERROR);
        LEVELS.map(LogLevel.FATAL, Level.FATAL);
        LEVELS.map(LogLevel.OFF, Level.OFF);
        FILTER = new AbstractFilter(){

            public Filter.Result filter(LogEvent event) {
                return Filter.Result.DENY;
            }

            public Filter.Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
                return Filter.Result.DENY;
            }

            public Filter.Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
                return Filter.Result.DENY;
            }

            public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object ... params) {
                return Filter.Result.DENY;
            }
        };
    }

    private static class LevelSetLoggerConfig
    extends LoggerConfig {
        LevelSetLoggerConfig(String name, Level level, boolean additive) {
            super(name, level, additive);
        }
    }

    @Order(value=0x7FFFFFFF)
    public static class Factory
    implements LoggingSystemFactory {
        private static final boolean PRESENT = ClassUtils.isPresent((String)"org.apache.logging.log4j.core.impl.Log4jContextFactory", (ClassLoader)Factory.class.getClassLoader());

        @Override
        public LoggingSystem getLoggingSystem(ClassLoader classLoader) {
            if (PRESENT) {
                return new Log4J2LoggingSystem(classLoader);
            }
            return null;
        }
    }
}

