/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.core;

import org.quartz.SchedulerException;
import org.quartz.listeners.SchedulerListenerSupport;

class ErrorLogger
extends SchedulerListenerSupport {
    ErrorLogger() {
    }

    @Override
    public void schedulerError(String msg, SchedulerException cause) {
        this.getLog().error(msg, (Throwable)cause);
    }
}

