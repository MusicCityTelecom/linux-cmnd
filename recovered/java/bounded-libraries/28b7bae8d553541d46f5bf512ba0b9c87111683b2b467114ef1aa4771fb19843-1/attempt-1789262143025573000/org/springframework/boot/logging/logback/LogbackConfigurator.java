/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ch.qos.logback.classic.Level
 *  ch.qos.logback.classic.Logger
 *  ch.qos.logback.classic.LoggerContext
 *  ch.qos.logback.classic.spi.ILoggingEvent
 *  ch.qos.logback.core.Appender
 *  ch.qos.logback.core.Context
 *  ch.qos.logback.core.pattern.Converter
 *  ch.qos.logback.core.spi.ContextAware
 *  ch.qos.logback.core.spi.LifeCycle
 *  org.springframework.util.Assert
 */
package org.springframework.boot.logging.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.pattern.Converter;
import ch.qos.logback.core.spi.ContextAware;
import ch.qos.logback.core.spi.LifeCycle;
import java.util.HashMap;
import org.springframework.util.Assert;

class LogbackConfigurator {
    private LoggerContext context;

    LogbackConfigurator(LoggerContext context) {
        Assert.notNull((Object)context, (String)"Context must not be null");
        this.context = context;
    }

    LoggerContext getContext() {
        return this.context;
    }

    Object getConfigurationLock() {
        return this.context.getConfigurationLock();
    }

    void conversionRule(String conversionWord, Class<? extends Converter> converterClass) {
        Assert.hasLength((String)conversionWord, (String)"Conversion word must not be empty");
        Assert.notNull(converterClass, (String)"Converter class must not be null");
        HashMap<String, String> registry = (HashMap<String, String>)this.context.getObject("PATTERN_RULE_REGISTRY");
        if (registry == null) {
            registry = new HashMap<String, String>();
            this.context.putObject("PATTERN_RULE_REGISTRY", registry);
        }
        registry.put(conversionWord, converterClass.getName());
    }

    void appender(String name, Appender<?> appender) {
        appender.setName(name);
        this.start((LifeCycle)appender);
    }

    void logger(String name, Level level) {
        this.logger(name, level, true);
    }

    void logger(String name, Level level, boolean additive) {
        this.logger(name, level, additive, null);
    }

    void logger(String name, Level level, boolean additive, Appender<ILoggingEvent> appender) {
        Logger logger = this.context.getLogger(name);
        if (level != null) {
            logger.setLevel(level);
        }
        logger.setAdditive(additive);
        if (appender != null) {
            logger.addAppender(appender);
        }
    }

    @SafeVarargs
    final void root(Level level, Appender<ILoggingEvent> ... appenders) {
        Logger logger = this.context.getLogger("ROOT");
        if (level != null) {
            logger.setLevel(level);
        }
        for (Appender<ILoggingEvent> appender : appenders) {
            logger.addAppender(appender);
        }
    }

    void start(LifeCycle lifeCycle) {
        if (lifeCycle instanceof ContextAware) {
            ((ContextAware)lifeCycle).setContext((Context)this.context);
        }
        lifeCycle.start();
    }
}

