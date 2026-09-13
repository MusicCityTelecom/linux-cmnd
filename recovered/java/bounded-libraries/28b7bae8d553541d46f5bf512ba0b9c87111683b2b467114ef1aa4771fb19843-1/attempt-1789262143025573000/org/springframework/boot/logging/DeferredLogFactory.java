/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.springframework.boot.logging;

import java.util.function.Supplier;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FunctionalInterface
public interface DeferredLogFactory {
    default public Log getLog(Class<?> destination) {
        return this.getLog(() -> LogFactory.getLog((Class)destination));
    }

    default public Log getLog(Log destination) {
        return this.getLog(() -> destination);
    }

    public Log getLog(Supplier<Log> var1);
}

