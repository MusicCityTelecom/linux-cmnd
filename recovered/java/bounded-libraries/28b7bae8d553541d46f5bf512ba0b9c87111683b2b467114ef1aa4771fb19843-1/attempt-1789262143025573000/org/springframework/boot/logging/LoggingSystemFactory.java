/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.io.support.SpringFactoriesLoader
 */
package org.springframework.boot.logging;

import org.springframework.boot.logging.DelegatingLoggingSystemFactory;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.core.io.support.SpringFactoriesLoader;

public interface LoggingSystemFactory {
    public LoggingSystem getLoggingSystem(ClassLoader var1);

    public static LoggingSystemFactory fromSpringFactories() {
        return new DelegatingLoggingSystemFactory(classLoader -> SpringFactoriesLoader.loadFactories(LoggingSystemFactory.class, (ClassLoader)classLoader));
    }
}

