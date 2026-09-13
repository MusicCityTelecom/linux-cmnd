/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.logging.LogLevel
 *  org.springframework.boot.logging.LoggerConfiguration
 *  org.springframework.boot.logging.LoggerGroup
 *  org.springframework.boot.logging.LoggerGroups
 *  org.springframework.boot.logging.LoggingSystem
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.logging;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggerConfiguration;
import org.springframework.boot.logging.LoggerGroup;
import org.springframework.boot.logging.LoggerGroups;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

@Endpoint(id="loggers")
public class LoggersEndpoint {
    private final LoggingSystem loggingSystem;
    private final LoggerGroups loggerGroups;

    public LoggersEndpoint(LoggingSystem loggingSystem, LoggerGroups loggerGroups) {
        Assert.notNull((Object)loggingSystem, (String)"LoggingSystem must not be null");
        Assert.notNull((Object)loggerGroups, (String)"LoggerGroups must not be null");
        this.loggingSystem = loggingSystem;
        this.loggerGroups = loggerGroups;
    }

    @ReadOperation
    public Map<String, Object> loggers() {
        List configurations = this.loggingSystem.getLoggerConfigurations();
        if (configurations == null) {
            return Collections.emptyMap();
        }
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("levels", this.getLevels());
        result.put("loggers", this.getLoggers(configurations));
        result.put("groups", this.getGroups());
        return result;
    }

    private Map<String, LoggerLevels> getGroups() {
        LinkedHashMap<String, LoggerLevels> groups = new LinkedHashMap<String, LoggerLevels>();
        this.loggerGroups.forEach(group -> {
            LoggerLevels cfr_ignored_0 = groups.put(group.getName(), new GroupLoggerLevels(group.getConfiguredLevel(), group.getMembers()));
        });
        return groups;
    }

    @ReadOperation
    public LoggerLevels loggerLevels(@Selector String name) {
        Assert.notNull((Object)name, (String)"Name must not be null");
        LoggerGroup group = this.loggerGroups.get(name);
        if (group != null) {
            return new GroupLoggerLevels(group.getConfiguredLevel(), group.getMembers());
        }
        LoggerConfiguration configuration = this.loggingSystem.getLoggerConfiguration(name);
        return configuration != null ? new SingleLoggerLevels(configuration) : null;
    }

    @WriteOperation
    public void configureLogLevel(@Selector String name, @Nullable LogLevel configuredLevel) {
        Assert.notNull((Object)name, (String)"Name must not be empty");
        LoggerGroup group = this.loggerGroups.get(name);
        if (group != null && group.hasMembers()) {
            group.configureLogLevel(configuredLevel, (arg_0, arg_1) -> ((LoggingSystem)this.loggingSystem).setLogLevel(arg_0, arg_1));
            return;
        }
        this.loggingSystem.setLogLevel(name, configuredLevel);
    }

    private NavigableSet<LogLevel> getLevels() {
        Set levels = this.loggingSystem.getSupportedLogLevels();
        return new TreeSet(levels).descendingSet();
    }

    private Map<String, LoggerLevels> getLoggers(Collection<LoggerConfiguration> configurations) {
        LinkedHashMap<String, LoggerLevels> loggers = new LinkedHashMap<String, LoggerLevels>(configurations.size());
        for (LoggerConfiguration configuration : configurations) {
            loggers.put(configuration.getName(), new SingleLoggerLevels(configuration));
        }
        return loggers;
    }

    public static class SingleLoggerLevels
    extends LoggerLevels {
        private String effectiveLevel;

        public SingleLoggerLevels(LoggerConfiguration configuration) {
            super(configuration.getConfiguredLevel());
            this.effectiveLevel = this.getName(configuration.getEffectiveLevel());
        }

        public String getEffectiveLevel() {
            return this.effectiveLevel;
        }
    }

    public static class GroupLoggerLevels
    extends LoggerLevels {
        private List<String> members;

        public GroupLoggerLevels(LogLevel configuredLevel, List<String> members) {
            super(configuredLevel);
            this.members = members;
        }

        public List<String> getMembers() {
            return this.members;
        }
    }

    public static class LoggerLevels {
        private String configuredLevel;

        public LoggerLevels(LogLevel configuredLevel) {
            this.configuredLevel = this.getName(configuredLevel);
        }

        protected final String getName(LogLevel level) {
            return level != null ? level.name() : null;
        }

        public String getConfiguredLevel() {
            return this.configuredLevel;
        }
    }
}

