/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ch.qos.logback.classic.Level
 *  ch.qos.logback.classic.encoder.PatternLayoutEncoder
 *  ch.qos.logback.classic.spi.ILoggingEvent
 *  ch.qos.logback.core.Appender
 *  ch.qos.logback.core.ConsoleAppender
 *  ch.qos.logback.core.Context
 *  ch.qos.logback.core.encoder.Encoder
 *  ch.qos.logback.core.rolling.RollingFileAppender
 *  ch.qos.logback.core.rolling.RollingPolicy
 *  ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy
 *  ch.qos.logback.core.spi.LifeCycle
 *  ch.qos.logback.core.spi.PropertyContainer
 *  ch.qos.logback.core.util.FileSize
 *  ch.qos.logback.core.util.OptionHelper
 */
package org.springframework.boot.logging.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.encoder.Encoder;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.RollingPolicy;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.spi.LifeCycle;
import ch.qos.logback.core.spi.PropertyContainer;
import ch.qos.logback.core.util.FileSize;
import ch.qos.logback.core.util.OptionHelper;
import java.nio.charset.Charset;
import org.springframework.boot.logging.LogFile;
import org.springframework.boot.logging.logback.ColorConverter;
import org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter;
import org.springframework.boot.logging.logback.LogbackConfigurator;
import org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter;

class DefaultLogbackConfiguration {
    private final LogFile logFile;

    DefaultLogbackConfiguration(LogFile logFile) {
        this.logFile = logFile;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void apply(LogbackConfigurator config) {
        Object object = config.getConfigurationLock();
        synchronized (object) {
            this.defaults(config);
            Appender<ILoggingEvent> consoleAppender = this.consoleAppender(config);
            if (this.logFile != null) {
                Appender<ILoggingEvent> fileAppender = this.fileAppender(config, this.logFile.toString());
                config.root(Level.INFO, consoleAppender, fileAppender);
            } else {
                config.root(Level.INFO, consoleAppender);
            }
        }
    }

    private void defaults(LogbackConfigurator config) {
        config.conversionRule("clr", ColorConverter.class);
        config.conversionRule("wex", WhitespaceThrowableProxyConverter.class);
        config.conversionRule("wEx", ExtendedWhitespaceThrowableProxyConverter.class);
        config.getContext().putProperty("CONSOLE_LOG_PATTERN", this.resolve(config, "${CONSOLE_LOG_PATTERN:-%clr(%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"));
        String defaultCharset = Charset.defaultCharset().name();
        config.getContext().putProperty("CONSOLE_LOG_CHARSET", this.resolve(config, "${CONSOLE_LOG_CHARSET:-" + defaultCharset + "}"));
        config.getContext().putProperty("FILE_LOG_PATTERN", this.resolve(config, "${FILE_LOG_PATTERN:-%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}} ${LOG_LEVEL_PATTERN:-%5p} ${PID:- } --- [%t] %-40.40logger{39} : %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"));
        config.getContext().putProperty("FILE_LOG_CHARSET", this.resolve(config, "${FILE_LOG_CHARSET:-" + defaultCharset + "}"));
        config.logger("org.apache.catalina.startup.DigesterFactory", Level.ERROR);
        config.logger("org.apache.catalina.util.LifecycleBase", Level.ERROR);
        config.logger("org.apache.coyote.http11.Http11NioProtocol", Level.WARN);
        config.logger("org.apache.sshd.common.util.SecurityUtils", Level.WARN);
        config.logger("org.apache.tomcat.util.net.NioSelectorPool", Level.WARN);
        config.logger("org.eclipse.jetty.util.component.AbstractLifeCycle", Level.ERROR);
        config.logger("org.hibernate.validator.internal.util.Version", Level.WARN);
        config.logger("org.springframework.boot.actuate.endpoint.jmx", Level.WARN);
    }

    private Appender<ILoggingEvent> consoleAppender(LogbackConfigurator config) {
        ConsoleAppender appender = new ConsoleAppender();
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setPattern(this.resolve(config, "${CONSOLE_LOG_PATTERN}"));
        encoder.setCharset(this.resolveCharset(config, "${CONSOLE_LOG_CHARSET}"));
        config.start((LifeCycle)encoder);
        appender.setEncoder((Encoder)encoder);
        config.appender("CONSOLE", (Appender<?>)appender);
        return appender;
    }

    private Appender<ILoggingEvent> fileAppender(LogbackConfigurator config, String logFile) {
        RollingFileAppender appender = new RollingFileAppender();
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setPattern(this.resolve(config, "${FILE_LOG_PATTERN}"));
        encoder.setCharset(this.resolveCharset(config, "${FILE_LOG_CHARSET}"));
        appender.setEncoder((Encoder)encoder);
        config.start((LifeCycle)encoder);
        appender.setFile(logFile);
        this.setRollingPolicy((RollingFileAppender<ILoggingEvent>)appender, config);
        config.appender("FILE", (Appender<?>)appender);
        return appender;
    }

    private void setRollingPolicy(RollingFileAppender<ILoggingEvent> appender, LogbackConfigurator config) {
        SizeAndTimeBasedRollingPolicy rollingPolicy = new SizeAndTimeBasedRollingPolicy();
        rollingPolicy.setContext((Context)config.getContext());
        rollingPolicy.setFileNamePattern(this.resolve(config, "${LOGBACK_ROLLINGPOLICY_FILE_NAME_PATTERN:-${LOG_FILE}.%d{yyyy-MM-dd}.%i.gz}"));
        rollingPolicy.setCleanHistoryOnStart(this.resolveBoolean(config, "${LOGBACK_ROLLINGPOLICY_CLEAN_HISTORY_ON_START:-false}"));
        rollingPolicy.setMaxFileSize(this.resolveFileSize(config, "${LOGBACK_ROLLINGPOLICY_MAX_FILE_SIZE:-10MB}"));
        rollingPolicy.setTotalSizeCap(this.resolveFileSize(config, "${LOGBACK_ROLLINGPOLICY_TOTAL_SIZE_CAP:-0}"));
        rollingPolicy.setMaxHistory(this.resolveInt(config, "${LOGBACK_ROLLINGPOLICY_MAX_HISTORY:-7}"));
        appender.setRollingPolicy((RollingPolicy)rollingPolicy);
        rollingPolicy.setParent(appender);
        config.start((LifeCycle)rollingPolicy);
    }

    private boolean resolveBoolean(LogbackConfigurator config, String val) {
        return Boolean.parseBoolean(this.resolve(config, val));
    }

    private int resolveInt(LogbackConfigurator config, String val) {
        return Integer.parseInt(this.resolve(config, val));
    }

    private FileSize resolveFileSize(LogbackConfigurator config, String val) {
        return FileSize.valueOf((String)this.resolve(config, val));
    }

    private Charset resolveCharset(LogbackConfigurator config, String val) {
        return Charset.forName(this.resolve(config, val));
    }

    private String resolve(LogbackConfigurator config, String val) {
        return OptionHelper.substVars((String)val, (PropertyContainer)config.getContext());
    }
}

