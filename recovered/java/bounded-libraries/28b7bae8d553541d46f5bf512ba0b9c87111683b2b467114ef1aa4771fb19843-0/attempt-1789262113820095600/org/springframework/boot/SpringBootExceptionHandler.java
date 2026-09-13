/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class SpringBootExceptionHandler
implements Thread.UncaughtExceptionHandler {
    private static final Set<String> LOG_CONFIGURATION_MESSAGES;
    private static LoggedExceptionHandlerThreadLocal handler;
    private final Thread.UncaughtExceptionHandler parent;
    private final List<Throwable> loggedExceptions = new ArrayList<Throwable>();
    private int exitCode = 0;

    SpringBootExceptionHandler(Thread.UncaughtExceptionHandler parent) {
        this.parent = parent;
    }

    void registerLoggedException(Throwable exception) {
        this.loggedExceptions.add(exception);
    }

    void registerExitCode(int exitCode) {
        this.exitCode = exitCode;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            if (this.isPassedToParent(ex) && this.parent != null) {
                this.parent.uncaughtException(thread, ex);
            }
        }
        finally {
            this.loggedExceptions.clear();
            if (this.exitCode != 0) {
                System.exit(this.exitCode);
            }
        }
    }

    private boolean isPassedToParent(Throwable ex) {
        return this.isLogConfigurationMessage(ex) || !this.isRegistered(ex);
    }

    private boolean isLogConfigurationMessage(Throwable ex) {
        if (ex instanceof InvocationTargetException) {
            return this.isLogConfigurationMessage(ex.getCause());
        }
        String message = ex.getMessage();
        if (message != null) {
            for (String candidate : LOG_CONFIGURATION_MESSAGES) {
                if (!message.contains(candidate)) continue;
                return true;
            }
        }
        return false;
    }

    private boolean isRegistered(Throwable ex) {
        if (this.loggedExceptions.contains(ex)) {
            return true;
        }
        if (ex instanceof InvocationTargetException) {
            return this.isRegistered(ex.getCause());
        }
        return false;
    }

    static SpringBootExceptionHandler forCurrentThread() {
        return (SpringBootExceptionHandler)handler.get();
    }

    static {
        HashSet<String> messages = new HashSet<String>();
        messages.add("Logback configuration error detected");
        LOG_CONFIGURATION_MESSAGES = Collections.unmodifiableSet(messages);
        handler = new LoggedExceptionHandlerThreadLocal();
    }

    private static class LoggedExceptionHandlerThreadLocal
    extends ThreadLocal<SpringBootExceptionHandler> {
        private LoggedExceptionHandlerThreadLocal() {
        }

        @Override
        protected SpringBootExceptionHandler initialValue() {
            SpringBootExceptionHandler handler = new SpringBootExceptionHandler(Thread.currentThread().getUncaughtExceptionHandler());
            Thread.currentThread().setUncaughtExceptionHandler(handler);
            return handler;
        }
    }
}

