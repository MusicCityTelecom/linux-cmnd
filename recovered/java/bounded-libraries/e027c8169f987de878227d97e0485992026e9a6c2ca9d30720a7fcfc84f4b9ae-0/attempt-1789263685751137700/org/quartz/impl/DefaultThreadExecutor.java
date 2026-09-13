/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl;

import org.quartz.spi.ThreadExecutor;

public class DefaultThreadExecutor
implements ThreadExecutor {
    @Override
    public void initialize() {
    }

    @Override
    public void execute(Thread thread) {
        thread.start();
    }
}

