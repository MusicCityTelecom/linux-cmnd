/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogDelegateFactory
 */
package org.springframework.messaging.simp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogDelegateFactory;

public abstract class SimpLogging {
    private static final Log fallbackLogger = LogFactory.getLog((String)("org.springframework.web." + SimpLogging.class.getSimpleName()));

    public static Log forLogName(Class<?> primaryLoggerClass) {
        Log primaryLogger = LogFactory.getLog(primaryLoggerClass);
        return SimpLogging.forLog(primaryLogger);
    }

    public static Log forLog(Log primaryLogger) {
        return LogDelegateFactory.getCompositeLog((Log)primaryLogger, (Log)fallbackLogger, (Log[])new Log[0]);
    }
}

