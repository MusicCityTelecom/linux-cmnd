/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import java.util.concurrent.Executor;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ImmediateController {
    public Executor getExecutor();

    public void setExecutor(Executor var1) throws IllegalStateException;

    public long getThreadInactivityTimeout();

    public void setThreadInactivityTimeout(long var1) throws IllegalArgumentException;

    public ImmediateServiceState getImmediateState();

    public void setImmediateState(ImmediateServiceState var1);

    public static enum ImmediateServiceState {
        SUSPENDED,
        RUNNING;

    }
}

