/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.logging;

import java.util.List;
import java.util.function.Function;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.boot.logging.LoggingSystemFactory;

class DelegatingLoggingSystemFactory
implements LoggingSystemFactory {
    private final Function<ClassLoader, List<LoggingSystemFactory>> delegates;

    DelegatingLoggingSystemFactory(Function<ClassLoader, List<LoggingSystemFactory>> delegates) {
        this.delegates = delegates;
    }

    @Override
    public LoggingSystem getLoggingSystem(ClassLoader classLoader) {
        List<LoggingSystemFactory> delegates;
        List<LoggingSystemFactory> list = delegates = this.delegates != null ? this.delegates.apply(classLoader) : null;
        if (delegates != null) {
            for (LoggingSystemFactory delegate : delegates) {
                LoggingSystem loggingSystem = delegate.getLoggingSystem(classLoader);
                if (loggingSystem == null) continue;
                return loggingSystem;
            }
        }
        return null;
    }
}

