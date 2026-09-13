/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.time.Duration;
import java.util.concurrent.Callable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.boot.system.ApplicationPid;
import org.springframework.context.ApplicationContext;
import org.springframework.core.log.LogMessage;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

class StartupInfoLogger {
    private static final Log logger = LogFactory.getLog(StartupInfoLogger.class);
    private static final long HOST_NAME_RESOLVE_THRESHOLD = 200L;
    private final Class<?> sourceClass;

    StartupInfoLogger(Class<?> sourceClass) {
        this.sourceClass = sourceClass;
    }

    void logStarting(Log applicationLog) {
        Assert.notNull((Object)applicationLog, (String)"Log must not be null");
        applicationLog.info((Object)LogMessage.of(this::getStartingMessage));
        applicationLog.debug((Object)LogMessage.of(this::getRunningMessage));
    }

    void logStarted(Log applicationLog, Duration timeTakenToStartup) {
        if (applicationLog.isInfoEnabled()) {
            applicationLog.info((Object)this.getStartedMessage(timeTakenToStartup));
        }
    }

    private CharSequence getStartingMessage() {
        StringBuilder message = new StringBuilder();
        message.append("Starting ");
        this.appendApplicationName(message);
        this.appendVersion(message, this.sourceClass);
        this.appendJavaVersion(message);
        this.appendOn(message);
        this.appendPid(message);
        this.appendContext(message);
        return message;
    }

    private CharSequence getRunningMessage() {
        StringBuilder message = new StringBuilder();
        message.append("Running with Spring Boot");
        this.appendVersion(message, this.getClass());
        message.append(", Spring");
        this.appendVersion(message, ApplicationContext.class);
        return message;
    }

    private CharSequence getStartedMessage(Duration timeTakenToStartup) {
        StringBuilder message = new StringBuilder();
        message.append("Started ");
        this.appendApplicationName(message);
        message.append(" in ");
        message.append((double)timeTakenToStartup.toMillis() / 1000.0);
        message.append(" seconds");
        try {
            double uptime = (double)ManagementFactory.getRuntimeMXBean().getUptime() / 1000.0;
            message.append(" (JVM running for ").append(uptime).append(")");
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return message;
    }

    private void appendApplicationName(StringBuilder message) {
        String name = this.sourceClass != null ? ClassUtils.getShortName(this.sourceClass) : "application";
        message.append(name);
    }

    private void appendVersion(StringBuilder message, Class<?> source) {
        this.append(message, "v", () -> source.getPackage().getImplementationVersion());
    }

    private void appendOn(StringBuilder message) {
        long startTime = System.currentTimeMillis();
        this.append(message, "on ", () -> InetAddress.getLocalHost().getHostName());
        long resolveTime = System.currentTimeMillis() - startTime;
        if (resolveTime > 200L) {
            logger.warn((Object)LogMessage.of(() -> {
                StringBuilder warning = new StringBuilder();
                warning.append("InetAddress.getLocalHost().getHostName() took ");
                warning.append(resolveTime);
                warning.append(" milliseconds to respond.");
                warning.append(" Please verify your network configuration");
                if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                    warning.append(" (macOS machines may need to add entries to /etc/hosts)");
                }
                warning.append(".");
                return warning;
            }));
        }
    }

    private void appendPid(StringBuilder message) {
        this.append(message, "with PID ", ApplicationPid::new);
    }

    private void appendContext(StringBuilder message) {
        StringBuilder context = new StringBuilder();
        ApplicationHome home = new ApplicationHome(this.sourceClass);
        if (home.getSource() != null) {
            context.append(home.getSource().getAbsolutePath());
        }
        this.append(context, "started by ", () -> System.getProperty("user.name"));
        this.append(context, "in ", () -> System.getProperty("user.dir"));
        if (context.length() > 0) {
            message.append(" (");
            message.append((CharSequence)context);
            message.append(")");
        }
    }

    private void appendJavaVersion(StringBuilder message) {
        this.append(message, "using Java ", () -> System.getProperty("java.version"));
    }

    private void append(StringBuilder message, String prefix, Callable<Object> call) {
        this.append(message, prefix, call, "");
    }

    private void append(StringBuilder message, String prefix, Callable<Object> call, String defaultValue) {
        String value;
        Object result = this.callIfPossible(call);
        String string = value = result != null ? result.toString() : null;
        if (!StringUtils.hasLength((String)value)) {
            value = defaultValue;
        }
        if (StringUtils.hasLength((String)value)) {
            message.append(message.length() > 0 ? " " : "");
            message.append(prefix);
            message.append(value);
        }
    }

    private Object callIfPossible(Callable<Object> call) {
        try {
            return call.call();
        }
        catch (Exception ex) {
            return null;
        }
    }
}

