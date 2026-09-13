/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.spi;

public interface ThreadExecutor {
    public void execute(Thread var1);

    public void initialize();
}

