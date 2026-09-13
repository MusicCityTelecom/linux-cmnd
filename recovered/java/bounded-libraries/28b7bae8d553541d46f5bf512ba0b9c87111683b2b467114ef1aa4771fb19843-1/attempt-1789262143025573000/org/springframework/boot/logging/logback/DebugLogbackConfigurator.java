/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ch.qos.logback.classic.Level
 *  ch.qos.logback.classic.LoggerContext
 *  ch.qos.logback.classic.spi.ILoggingEvent
 *  ch.qos.logback.core.Appender
 *  ch.qos.logback.core.pattern.Converter
 *  ch.qos.logback.core.spi.LifeCycle
 *  ch.qos.logback.core.status.InfoStatus
 *  ch.qos.logback.core.status.Status
 */
package org.springframework.boot.logging.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.pattern.Converter;
import ch.qos.logback.core.spi.LifeCycle;
import ch.qos.logback.core.status.InfoStatus;
import ch.qos.logback.core.status.Status;
import org.springframework.boot.logging.logback.LogbackConfigurator;

class DebugLogbackConfigurator
extends LogbackConfigurator {
    DebugLogbackConfigurator(LoggerContext context) {
        super(context);
    }

    @Override
    public void conversionRule(String conversionWord, Class<? extends Converter> converterClass) {
        this.info("Adding conversion rule of type '" + converterClass.getName() + "' for word '" + conversionWord + "'");
        super.conversionRule(conversionWord, converterClass);
    }

    @Override
    public void appender(String name, Appender<?> appender) {
        this.info("Adding appender '" + appender + "' named '" + name + "'");
        super.appender(name, appender);
    }

    @Override
    public void logger(String name, Level level, boolean additive, Appender<ILoggingEvent> appender) {
        this.info("Configuring logger '" + name + "' with level '" + level + "'. Additive: " + additive);
        if (appender != null) {
            this.info("Adding appender '" + appender + "' to logger '" + name + "'");
        }
        super.logger(name, level, additive, appender);
    }

    @Override
    public void start(LifeCycle lifeCycle) {
        this.info("Starting '" + lifeCycle + "'");
        super.start(lifeCycle);
    }

    private void info(String message) {
        this.getContext().getStatusManager().add((Status)new InfoStatus(message, (Object)this));
    }
}

