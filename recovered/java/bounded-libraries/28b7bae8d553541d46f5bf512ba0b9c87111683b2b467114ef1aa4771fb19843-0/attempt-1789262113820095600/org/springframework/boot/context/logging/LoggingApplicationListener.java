/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.SmartLifecycle
 *  org.springframework.context.event.ContextClosedEvent
 *  org.springframework.context.event.GenericApplicationListener
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.Environment
 *  org.springframework.core.env.PropertyResolver
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.LinkedMultiValueMap
 *  org.springframework.util.MultiValueMap
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.context.logging;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.logging.LogFile;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggerGroup;
import org.springframework.boot.logging.LoggerGroups;
import org.springframework.boot.logging.LoggingInitializationContext;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.boot.logging.LoggingSystemProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.log.LogMessage;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

public class LoggingApplicationListener
implements GenericApplicationListener {
    private static final ConfigurationPropertyName LOGGING_LEVEL = ConfigurationPropertyName.of("logging.level");
    private static final ConfigurationPropertyName LOGGING_GROUP = ConfigurationPropertyName.of("logging.group");
    private static final Bindable<Map<String, LogLevel>> STRING_LOGLEVEL_MAP = Bindable.mapOf(String.class, LogLevel.class);
    private static final Bindable<Map<String, List<String>>> STRING_STRINGS_MAP = Bindable.of(ResolvableType.forClassWithGenerics(MultiValueMap.class, (Class[])new Class[]{String.class, String.class}).asMap());
    public static final int DEFAULT_ORDER = -2147483628;
    public static final String CONFIG_PROPERTY = "logging.config";
    public static final String REGISTER_SHUTDOWN_HOOK_PROPERTY = "logging.register-shutdown-hook";
    public static final String LOGGING_SYSTEM_BEAN_NAME = "springBootLoggingSystem";
    public static final String LOG_FILE_BEAN_NAME = "springBootLogFile";
    public static final String LOGGER_GROUPS_BEAN_NAME = "springBootLoggerGroups";
    private static final String LOGGING_LIFECYCLE_BEAN_NAME = "springBootLoggingLifecycle";
    private static final Map<String, List<String>> DEFAULT_GROUP_LOGGERS;
    private static final Map<LogLevel, List<String>> SPRING_BOOT_LOGGING_LOGGERS;
    private static final Class<?>[] EVENT_TYPES;
    private static final Class<?>[] SOURCE_TYPES;
    private static final AtomicBoolean shutdownHookRegistered;
    private final Log logger = LogFactory.getLog(this.getClass());
    private LoggingSystem loggingSystem;
    private LogFile logFile;
    private LoggerGroups loggerGroups;
    private int order = -2147483628;
    private boolean parseArgs = true;
    private LogLevel springBootLogging = null;

    public boolean supportsEventType(ResolvableType resolvableType) {
        return this.isAssignableFrom(resolvableType.getRawClass(), EVENT_TYPES);
    }

    public boolean supportsSourceType(Class<?> sourceType) {
        return this.isAssignableFrom(sourceType, SOURCE_TYPES);
    }

    private boolean isAssignableFrom(Class<?> type, Class<?> ... supportedTypes) {
        if (type != null) {
            for (Class<?> supportedType : supportedTypes) {
                if (!supportedType.isAssignableFrom(type)) continue;
                return true;
            }
        }
        return false;
    }

    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationStartingEvent) {
            this.onApplicationStartingEvent((ApplicationStartingEvent)event);
        } else if (event instanceof ApplicationEnvironmentPreparedEvent) {
            this.onApplicationEnvironmentPreparedEvent((ApplicationEnvironmentPreparedEvent)event);
        } else if (event instanceof ApplicationPreparedEvent) {
            this.onApplicationPreparedEvent((ApplicationPreparedEvent)event);
        } else if (event instanceof ContextClosedEvent) {
            this.onContextClosedEvent((ContextClosedEvent)event);
        } else if (event instanceof ApplicationFailedEvent) {
            this.onApplicationFailedEvent();
        }
    }

    private void onApplicationStartingEvent(ApplicationStartingEvent event) {
        this.loggingSystem = LoggingSystem.get(event.getSpringApplication().getClassLoader());
        this.loggingSystem.beforeInitialize();
    }

    private void onApplicationEnvironmentPreparedEvent(ApplicationEnvironmentPreparedEvent event) {
        SpringApplication springApplication = event.getSpringApplication();
        if (this.loggingSystem == null) {
            this.loggingSystem = LoggingSystem.get(springApplication.getClassLoader());
        }
        this.initialize(event.getEnvironment(), springApplication.getClassLoader());
    }

    private void onApplicationPreparedEvent(ApplicationPreparedEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        if (!beanFactory.containsBean(LOGGING_SYSTEM_BEAN_NAME)) {
            beanFactory.registerSingleton(LOGGING_SYSTEM_BEAN_NAME, (Object)this.loggingSystem);
        }
        if (this.logFile != null && !beanFactory.containsBean(LOG_FILE_BEAN_NAME)) {
            beanFactory.registerSingleton(LOG_FILE_BEAN_NAME, (Object)this.logFile);
        }
        if (this.loggerGroups != null && !beanFactory.containsBean(LOGGER_GROUPS_BEAN_NAME)) {
            beanFactory.registerSingleton(LOGGER_GROUPS_BEAN_NAME, (Object)this.loggerGroups);
        }
        if (!beanFactory.containsBean(LOGGING_LIFECYCLE_BEAN_NAME) && applicationContext.getParent() == null) {
            beanFactory.registerSingleton(LOGGING_LIFECYCLE_BEAN_NAME, (Object)new Lifecycle());
        }
    }

    private void onContextClosedEvent(ContextClosedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        if (applicationContext.getParent() != null || applicationContext.containsBean(LOGGING_LIFECYCLE_BEAN_NAME)) {
            return;
        }
        this.cleanupLoggingSystem();
    }

    void cleanupLoggingSystem() {
        if (this.loggingSystem != null) {
            this.loggingSystem.cleanUp();
        }
    }

    private void onApplicationFailedEvent() {
        this.cleanupLoggingSystem();
    }

    protected void initialize(ConfigurableEnvironment environment, ClassLoader classLoader) {
        this.getLoggingSystemProperties(environment).apply();
        this.logFile = LogFile.get((PropertyResolver)environment);
        if (this.logFile != null) {
            this.logFile.applyToSystemProperties();
        }
        this.loggerGroups = new LoggerGroups(DEFAULT_GROUP_LOGGERS);
        this.initializeEarlyLoggingLevel(environment);
        this.initializeSystem(environment, this.loggingSystem, this.logFile);
        this.initializeFinalLoggingLevels(environment, this.loggingSystem);
        this.registerShutdownHookIfNecessary((Environment)environment, this.loggingSystem);
    }

    private LoggingSystemProperties getLoggingSystemProperties(ConfigurableEnvironment environment) {
        return this.loggingSystem != null ? this.loggingSystem.getSystemProperties(environment) : new LoggingSystemProperties((Environment)environment);
    }

    private void initializeEarlyLoggingLevel(ConfigurableEnvironment environment) {
        if (this.parseArgs && this.springBootLogging == null) {
            if (this.isSet(environment, "debug")) {
                this.springBootLogging = LogLevel.DEBUG;
            }
            if (this.isSet(environment, "trace")) {
                this.springBootLogging = LogLevel.TRACE;
            }
        }
    }

    private boolean isSet(ConfigurableEnvironment environment, String property) {
        String value = environment.getProperty(property);
        return value != null && !value.equals("false");
    }

    private void initializeSystem(ConfigurableEnvironment environment, LoggingSystem system, LogFile logFile) {
        String logConfig = StringUtils.trimWhitespace((String)environment.getProperty(CONFIG_PROPERTY));
        try {
            LoggingInitializationContext initializationContext = new LoggingInitializationContext(environment);
            if (this.ignoreLogConfig(logConfig)) {
                system.initialize(initializationContext, null, logFile);
            } else {
                system.initialize(initializationContext, logConfig, logFile);
            }
        }
        catch (Exception ex) {
            Throwable exceptionToReport;
            for (exceptionToReport = ex; exceptionToReport != null && !(exceptionToReport instanceof FileNotFoundException); exceptionToReport = exceptionToReport.getCause()) {
            }
            exceptionToReport = exceptionToReport != null ? exceptionToReport : ex;
            System.err.println("Logging system failed to initialize using configuration from '" + logConfig + "'");
            exceptionToReport.printStackTrace(System.err);
            throw new IllegalStateException(ex);
        }
    }

    private boolean ignoreLogConfig(String logConfig) {
        return !StringUtils.hasLength((String)logConfig) || logConfig.startsWith("-D");
    }

    private void initializeFinalLoggingLevels(ConfigurableEnvironment environment, LoggingSystem system) {
        this.bindLoggerGroups(environment);
        if (this.springBootLogging != null) {
            this.initializeSpringBootLogging(system, this.springBootLogging);
        }
        this.setLogLevels(system, environment);
    }

    private void bindLoggerGroups(ConfigurableEnvironment environment) {
        if (this.loggerGroups != null) {
            Binder binder = Binder.get((Environment)environment);
            binder.bind(LOGGING_GROUP, STRING_STRINGS_MAP).ifBound(this.loggerGroups::putAll);
        }
    }

    protected void initializeSpringBootLogging(LoggingSystem system, LogLevel springBootLogging) {
        BiConsumer<String, LogLevel> configurer = this.getLogLevelConfigurer(system);
        SPRING_BOOT_LOGGING_LOGGERS.getOrDefault((Object)springBootLogging, Collections.emptyList()).forEach(name -> this.configureLogLevel((String)name, springBootLogging, configurer));
    }

    protected void setLogLevels(LoggingSystem system, ConfigurableEnvironment environment) {
        BiConsumer<String, LogLevel> customizer = this.getLogLevelConfigurer(system);
        Binder binder = Binder.get((Environment)environment);
        Map levels = binder.bind(LOGGING_LEVEL, STRING_LOGLEVEL_MAP).orElseGet(Collections::emptyMap);
        levels.forEach((name, level) -> this.configureLogLevel((String)name, (LogLevel)((Object)level), customizer));
    }

    private void configureLogLevel(String name, LogLevel level, BiConsumer<String, LogLevel> configurer) {
        LoggerGroup group;
        if (this.loggerGroups != null && (group = this.loggerGroups.get(name)) != null && group.hasMembers()) {
            group.configureLogLevel(level, configurer);
            return;
        }
        configurer.accept(name, level);
    }

    private BiConsumer<String, LogLevel> getLogLevelConfigurer(LoggingSystem system) {
        return (name, level) -> {
            try {
                name = name.equalsIgnoreCase("ROOT") ? null : name;
                system.setLogLevel((String)name, (LogLevel)((Object)level));
            }
            catch (RuntimeException ex) {
                this.logger.error((Object)LogMessage.format((String)"Cannot set level '%s' for '%s'", (Object)level, (Object)name));
            }
        };
    }

    private void registerShutdownHookIfNecessary(Environment environment, LoggingSystem loggingSystem) {
        Runnable shutdownHandler;
        if (((Boolean)environment.getProperty(REGISTER_SHUTDOWN_HOOK_PROPERTY, Boolean.class, (Object)true)).booleanValue() && (shutdownHandler = loggingSystem.getShutdownHandler()) != null && shutdownHookRegistered.compareAndSet(false, true)) {
            this.registerShutdownHook(shutdownHandler);
        }
    }

    void registerShutdownHook(Runnable shutdownHandler) {
        SpringApplication.getShutdownHandlers().add(shutdownHandler);
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return this.order;
    }

    public void setSpringBootLogging(LogLevel springBootLogging) {
        this.springBootLogging = springBootLogging;
    }

    public void setParseArgs(boolean parseArgs) {
        this.parseArgs = parseArgs;
    }

    static {
        LinkedMultiValueMap loggers = new LinkedMultiValueMap();
        loggers.add((Object)"web", (Object)"org.springframework.core.codec");
        loggers.add((Object)"web", (Object)"org.springframework.http");
        loggers.add((Object)"web", (Object)"org.springframework.web");
        loggers.add((Object)"web", (Object)"org.springframework.boot.actuate.endpoint.web");
        loggers.add((Object)"web", (Object)"org.springframework.boot.web.servlet.ServletContextInitializerBeans");
        loggers.add((Object)"sql", (Object)"org.springframework.jdbc.core");
        loggers.add((Object)"sql", (Object)"org.hibernate.SQL");
        loggers.add((Object)"sql", (Object)"org.jooq.tools.LoggerListener");
        DEFAULT_GROUP_LOGGERS = Collections.unmodifiableMap(loggers);
        loggers = new LinkedMultiValueMap();
        loggers.add((Object)LogLevel.DEBUG, (Object)"sql");
        loggers.add((Object)LogLevel.DEBUG, (Object)"web");
        loggers.add((Object)LogLevel.DEBUG, (Object)"org.springframework.boot");
        loggers.add((Object)LogLevel.TRACE, (Object)"org.springframework");
        loggers.add((Object)LogLevel.TRACE, (Object)"org.apache.tomcat");
        loggers.add((Object)LogLevel.TRACE, (Object)"org.apache.catalina");
        loggers.add((Object)LogLevel.TRACE, (Object)"org.eclipse.jetty");
        loggers.add((Object)LogLevel.TRACE, (Object)"org.hibernate.tool.hbm2ddl");
        SPRING_BOOT_LOGGING_LOGGERS = Collections.unmodifiableMap(loggers);
        EVENT_TYPES = new Class[]{ApplicationStartingEvent.class, ApplicationEnvironmentPreparedEvent.class, ApplicationPreparedEvent.class, ContextClosedEvent.class, ApplicationFailedEvent.class};
        SOURCE_TYPES = new Class[]{SpringApplication.class, ApplicationContext.class};
        shutdownHookRegistered = new AtomicBoolean();
    }

    private class Lifecycle
    implements SmartLifecycle {
        private volatile boolean running;

        private Lifecycle() {
        }

        public void start() {
            this.running = true;
        }

        public void stop() {
            this.running = false;
            LoggingApplicationListener.this.cleanupLoggingSystem();
        }

        public boolean isRunning() {
            return this.running;
        }

        public int getPhase() {
            return -2147483647;
        }
    }
}

