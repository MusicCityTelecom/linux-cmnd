/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public interface ClientExecutor {
    public <T> Future<T> submit(Callable<T> var1);

    public Future<?> submit(Runnable var1);

    public <T> Future<T> submit(Runnable var1, T var2);

    public <T> ScheduledFuture<T> schedule(Callable<T> var1, long var2, TimeUnit var4);

    public ScheduledFuture<?> schedule(Runnable var1, long var2, TimeUnit var4);
}

