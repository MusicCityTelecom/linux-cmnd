/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.io;

import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

final class ConcurrencyUtils {
    public static <T> ImmutableList<T> waitForAll(Iterable<ListenableFuture<T>> futures) {
        return ImmutableList.copyOf((Collection)ConcurrencyUtils.waitFor(Futures.allAsList(futures)));
    }

    public static <K, V> ImmutableMap<K, V> waitForAll(Map<K, ListenableFuture<V>> futures) {
        ImmutableMap.Builder finishedMap = ImmutableMap.builder();
        for (Map.Entry<K, ListenableFuture<V>> entry : futures.entrySet()) {
            finishedMap.put(entry.getKey(), ConcurrencyUtils.waitFor((Future)entry.getValue()));
        }
        return finishedMap.build();
    }

    public static <T> T waitFor(Future<T> future) {
        try {
            return future.get();
        }
        catch (ExecutionException e2) {
            if (e2.getCause() instanceof IOException) {
                throw new UncheckedIOException(e2.getCause().getMessage(), (IOException)e2.getCause());
            }
            if (e2.getCause() instanceof UncheckedIOException) {
                throw new UncheckedIOException(e2.getCause().getMessage(), ((UncheckedIOException)e2.getCause()).getCause());
            }
            if (e2.getCause() instanceof CommandExecutionException) {
                throw new CommandExecutionException(e2.getCause().getMessage(), e2.getCause());
            }
            throw new RuntimeException(e2.getMessage(), e2);
        }
        catch (InterruptedException e3) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("One operation was interrupted.", e3);
        }
    }

    private ConcurrencyUtils() {
    }
}

