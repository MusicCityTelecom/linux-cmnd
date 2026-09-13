/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogAdapter;

public abstract class LogFactory {
    public static Log getLog(Class<?> clazz) {
        return LogFactory.getLog(clazz.getName());
    }

    public static Log getLog(String name) {
        return LogAdapter.createLog(name);
    }

    @Deprecated
    public static LogFactory getFactory() {
        return new LogFactory(){};
    }

    @Deprecated
    public Log getInstance(Class<?> clazz) {
        return LogFactory.getLog(clazz);
    }

    @Deprecated
    public Log getInstance(String name) {
        return LogFactory.getLog(name);
    }
}

