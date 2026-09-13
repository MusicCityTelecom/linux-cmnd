/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.schedule;

public abstract class Job {
    public abstract void execute();

    public abstract String description();

    public abstract ExecuteType getExecuteType();

    public abstract boolean isExecuteOnce();

    public static enum ExecuteType {
        BYHAND,
        AUTOMATICALLY,
        ALL;

    }
}

