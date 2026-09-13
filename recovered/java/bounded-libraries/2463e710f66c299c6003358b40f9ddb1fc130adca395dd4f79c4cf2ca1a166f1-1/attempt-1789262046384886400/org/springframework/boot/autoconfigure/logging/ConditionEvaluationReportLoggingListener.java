/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.boot.context.event.ApplicationFailedEvent
 *  org.springframework.boot.logging.LogLevel
 *  org.springframework.context.ApplicationContextInitializer
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ApplicationListener
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.event.ApplicationContextEvent
 *  org.springframework.context.event.ContextRefreshedEvent
 *  org.springframework.context.event.GenericApplicationListener
 *  org.springframework.context.support.GenericApplicationContext
 *  org.springframework.core.ResolvableType
 *  org.springframework.util.Assert
 */
package org.springframework.boot.autoconfigure.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportMessage;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.util.Assert;

public class ConditionEvaluationReportLoggingListener
implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private final Log logger = LogFactory.getLog(this.getClass());
    private ConfigurableApplicationContext applicationContext;
    private ConditionEvaluationReport report;
    private final LogLevel logLevelForReport;

    public ConditionEvaluationReportLoggingListener() {
        this(LogLevel.DEBUG);
    }

    public ConditionEvaluationReportLoggingListener(LogLevel logLevelForReport) {
        Assert.isTrue((boolean)this.isInfoOrDebug(logLevelForReport), (String)"LogLevel must be INFO or DEBUG");
        this.logLevelForReport = logLevelForReport;
    }

    private boolean isInfoOrDebug(LogLevel logLevelForReport) {
        return LogLevel.INFO.equals((Object)logLevelForReport) || LogLevel.DEBUG.equals((Object)logLevelForReport);
    }

    public LogLevel getLogLevelForReport() {
        return this.logLevelForReport;
    }

    public void initialize(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        applicationContext.addApplicationListener((ApplicationListener)new ConditionEvaluationReportListener());
        if (applicationContext instanceof GenericApplicationContext) {
            this.report = ConditionEvaluationReport.get(this.applicationContext.getBeanFactory());
        }
    }

    protected void onApplicationEvent(ApplicationEvent event) {
        ConfigurableApplicationContext initializerApplicationContext = this.applicationContext;
        if (event instanceof ContextRefreshedEvent) {
            if (((ApplicationContextEvent)event).getApplicationContext() == initializerApplicationContext) {
                this.logAutoConfigurationReport();
            }
        } else if (event instanceof ApplicationFailedEvent && ((ApplicationFailedEvent)event).getApplicationContext() == initializerApplicationContext) {
            this.logAutoConfigurationReport(true);
        }
    }

    private void logAutoConfigurationReport() {
        this.logAutoConfigurationReport(!this.applicationContext.isActive());
    }

    public void logAutoConfigurationReport(boolean isCrashReport) {
        if (this.report == null) {
            if (this.applicationContext == null) {
                this.logger.info((Object)"Unable to provide the conditions report due to missing ApplicationContext");
                return;
            }
            this.report = ConditionEvaluationReport.get(this.applicationContext.getBeanFactory());
        }
        if (!this.report.getConditionAndOutcomesBySource().isEmpty()) {
            if (this.getLogLevelForReport().equals((Object)LogLevel.INFO)) {
                if (this.logger.isInfoEnabled()) {
                    this.logger.info((Object)new ConditionEvaluationReportMessage(this.report));
                } else if (isCrashReport) {
                    this.logMessage("info");
                }
            } else if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)new ConditionEvaluationReportMessage(this.report));
            } else if (isCrashReport) {
                this.logMessage("debug");
            }
        }
    }

    private void logMessage(String logLevel) {
        this.logger.info((Object)String.format("%n%nError starting ApplicationContext. To display the conditions report re-run your application with '" + logLevel + "' enabled.", new Object[0]));
    }

    private class ConditionEvaluationReportListener
    implements GenericApplicationListener {
        private ConditionEvaluationReportListener() {
        }

        public int getOrder() {
            return Integer.MAX_VALUE;
        }

        public boolean supportsEventType(ResolvableType resolvableType) {
            Class type = resolvableType.getRawClass();
            if (type == null) {
                return false;
            }
            return ContextRefreshedEvent.class.isAssignableFrom(type) || ApplicationFailedEvent.class.isAssignableFrom(type);
        }

        public boolean supportsSourceType(Class<?> sourceType) {
            return true;
        }

        public void onApplicationEvent(ApplicationEvent event) {
            ConditionEvaluationReportLoggingListener.this.onApplicationEvent(event);
        }
    }
}

