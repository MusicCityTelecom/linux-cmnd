/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

import org.quartz.SchedulerException;

public class JobPersistenceException
extends SchedulerException {
    private static final long serialVersionUID = -8924958757341995694L;

    public JobPersistenceException(String msg) {
        super(msg);
    }

    public JobPersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

