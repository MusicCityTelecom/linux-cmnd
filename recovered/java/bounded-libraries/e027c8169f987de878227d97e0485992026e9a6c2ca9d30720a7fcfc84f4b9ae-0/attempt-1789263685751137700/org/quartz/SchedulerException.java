/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

public class SchedulerException
extends Exception {
    private static final long serialVersionUID = 174841398690789156L;

    public SchedulerException() {
    }

    public SchedulerException(String msg) {
        super(msg);
    }

    public SchedulerException(Throwable cause) {
        super(cause);
    }

    public SchedulerException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public Throwable getUnderlyingException() {
        return super.getCause();
    }

    @Override
    public String toString() {
        Throwable cause = this.getUnderlyingException();
        if (cause == null || cause == this) {
            return super.toString();
        }
        return super.toString() + " [See nested exception: " + cause + "]";
    }
}

