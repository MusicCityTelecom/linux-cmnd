/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

import org.quartz.SchedulerException;

public class UnableToInterruptJobException
extends SchedulerException {
    private static final long serialVersionUID = -490863760696463776L;

    public UnableToInterruptJobException(String msg) {
        super(msg);
    }

    public UnableToInterruptJobException(Throwable cause) {
        super(cause);
    }
}

