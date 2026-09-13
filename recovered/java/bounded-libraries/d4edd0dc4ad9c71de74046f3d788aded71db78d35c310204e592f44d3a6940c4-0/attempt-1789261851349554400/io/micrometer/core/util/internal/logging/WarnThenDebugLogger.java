/*
 * Decompiled with CFR 0.152.
 */
package io.micrometer.core.util.internal.logging;

import io.micrometer.core.util.internal.logging.InternalLogLevel;
import io.micrometer.core.util.internal.logging.InternalLogger;
import io.micrometer.core.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.atomic.AtomicBoolean;

public class WarnThenDebugLogger {
    private final InternalLogger logger;
    private final AtomicBoolean warnLogged = new AtomicBoolean();

    public WarnThenDebugLogger(Class<?> clazz) {
        this.logger = InternalLoggerFactory.getInstance(clazz);
    }

    public void log(String message, Throwable ex) {
        String finalMessage;
        InternalLogLevel level;
        if (this.warnLogged.compareAndSet(false, true)) {
            level = InternalLogLevel.WARN;
            finalMessage = message + " Note that subsequent logs will be logged at debug level.";
        } else {
            level = InternalLogLevel.DEBUG;
            finalMessage = message;
        }
        if (ex != null) {
            this.logger.log(level, finalMessage, ex);
        } else {
            this.logger.log(level, finalMessage);
        }
    }

    public void log(String message) {
        this.log(message, null);
    }
}

