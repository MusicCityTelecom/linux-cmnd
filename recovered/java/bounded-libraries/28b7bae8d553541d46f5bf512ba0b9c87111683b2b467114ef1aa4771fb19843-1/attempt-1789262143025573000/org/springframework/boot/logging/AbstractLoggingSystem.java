/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.ClassPathResource
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.StringUtils
 *  org.springframework.util.SystemPropertyUtils
 */
package org.springframework.boot.logging;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.boot.logging.LogFile;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggerConfiguration;
import org.springframework.boot.logging.LoggerConfigurationComparator;
import org.springframework.boot.logging.LoggingInitializationContext;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.boot.logging.LoggingSystemProperties;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;

public abstract class AbstractLoggingSystem
extends LoggingSystem {
    protected static final Comparator<LoggerConfiguration> CONFIGURATION_COMPARATOR = new LoggerConfigurationComparator("ROOT");
    private final ClassLoader classLoader;

    public AbstractLoggingSystem(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void beforeInitialize() {
    }

    @Override
    public void initialize(LoggingInitializationContext initializationContext, String configLocation, LogFile logFile) {
        if (StringUtils.hasLength((String)configLocation)) {
            this.initializeWithSpecificConfig(initializationContext, configLocation, logFile);
            return;
        }
        this.initializeWithConventions(initializationContext, logFile);
    }

    private void initializeWithSpecificConfig(LoggingInitializationContext initializationContext, String configLocation, LogFile logFile) {
        configLocation = SystemPropertyUtils.resolvePlaceholders((String)configLocation);
        this.loadConfiguration(initializationContext, configLocation, logFile);
    }

    private void initializeWithConventions(LoggingInitializationContext initializationContext, LogFile logFile) {
        String config = this.getSelfInitializationConfig();
        if (config != null && logFile == null) {
            this.reinitialize(initializationContext);
            return;
        }
        if (config == null) {
            config = this.getSpringInitializationConfig();
        }
        if (config != null) {
            this.loadConfiguration(initializationContext, config, logFile);
            return;
        }
        this.loadDefaults(initializationContext, logFile);
    }

    protected String getSelfInitializationConfig() {
        return this.findConfig(this.getStandardConfigLocations());
    }

    protected String getSpringInitializationConfig() {
        return this.findConfig(this.getSpringConfigLocations());
    }

    private String findConfig(String[] locations) {
        for (String location : locations) {
            ClassPathResource resource = new ClassPathResource(location, this.classLoader);
            if (!resource.exists()) continue;
            return "classpath:" + location;
        }
        return null;
    }

    protected abstract String[] getStandardConfigLocations();

    protected String[] getSpringConfigLocations() {
        String[] locations = this.getStandardConfigLocations();
        for (int i = 0; i < locations.length; ++i) {
            String extension = StringUtils.getFilenameExtension((String)locations[i]);
            locations[i] = locations[i].substring(0, locations[i].length() - extension.length() - 1) + "-spring." + extension;
        }
        return locations;
    }

    protected abstract void loadDefaults(LoggingInitializationContext var1, LogFile var2);

    protected abstract void loadConfiguration(LoggingInitializationContext var1, String var2, LogFile var3);

    protected void reinitialize(LoggingInitializationContext initializationContext) {
    }

    protected final ClassLoader getClassLoader() {
        return this.classLoader;
    }

    protected final String getPackagedConfigFile(String fileName) {
        String defaultPath = ClassUtils.getPackageName(this.getClass());
        defaultPath = defaultPath.replace('.', '/');
        defaultPath = defaultPath + "/" + fileName;
        defaultPath = "classpath:" + defaultPath;
        return defaultPath;
    }

    protected final void applySystemProperties(Environment environment, LogFile logFile) {
        new LoggingSystemProperties(environment).apply(logFile);
    }

    protected static class LogLevels<T> {
        private final Map<LogLevel, T> systemToNative = new EnumMap<LogLevel, T>(LogLevel.class);
        private final Map<T, LogLevel> nativeToSystem = new HashMap<T, LogLevel>();

        public void map(LogLevel system, T nativeLevel) {
            this.systemToNative.putIfAbsent(system, nativeLevel);
            this.nativeToSystem.putIfAbsent(nativeLevel, system);
        }

        public LogLevel convertNativeToSystem(T level) {
            return this.nativeToSystem.get(level);
        }

        public T convertSystemToNative(LogLevel level) {
            return this.systemToNative.get((Object)level);
        }

        public Set<LogLevel> getSupported() {
            return new LinkedHashSet<LogLevel>(this.nativeToSystem.values());
        }
    }
}

