/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.jsr166;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import org.glassfish.jersey.internal.jsr166.Flow;

public interface SubmittableFlowPublisher<T>
extends Flow.Publisher<T>,
AutoCloseable {
    public CompletableFuture<Void> consume(Consumer<? super T> var1);

    @Override
    public void close();

    public void closeExceptionally(Throwable var1);

    public long estimateMinimumDemand();

    public int estimateMaximumLag();

    public Throwable getClosedException();

    public int getMaxBufferCapacity();

    public int offer(T var1, long var2, TimeUnit var4, BiPredicate<Flow.Subscriber<? super T>, ? super T> var5);

    public int offer(T var1, BiPredicate<Flow.Subscriber<? super T>, ? super T> var2);

    public int submit(T var1);

    @Override
    public void subscribe(Flow.Subscriber<? super T> var1);
}

