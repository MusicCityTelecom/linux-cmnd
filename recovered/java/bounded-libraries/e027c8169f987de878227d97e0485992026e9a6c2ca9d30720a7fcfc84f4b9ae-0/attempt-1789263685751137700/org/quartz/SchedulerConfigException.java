/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

import org.quartz.SchedulerException;

public class SchedulerConfigException
extends SchedulerException {
    private static final long serialVersionUID = -5921239824646083098L;

    public SchedulerConfigException(String msg) {
        super(msg);
    }

    public SchedulerConfigException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

