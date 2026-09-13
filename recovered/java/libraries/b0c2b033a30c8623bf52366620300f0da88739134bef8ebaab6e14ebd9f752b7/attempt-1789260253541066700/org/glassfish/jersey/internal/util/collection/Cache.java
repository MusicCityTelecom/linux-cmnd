/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.util.collection;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

public class Cache<K, V>
implements Function<K, V> {
    private static final CycleHandler<Object> EMPTY_HANDLER = key -> {};
    private final CycleHandler<K> cycleHandler;
    private final ConcurrentHashMap<K, OriginThreadAwareFuture> cache = new ConcurrentHashMap();
    private final Function<K, V> computable;

    public Cache(Function<K, V> computable) {
        this(computable, EMPTY_HANDLER);
    }

    public Cache(Function<K, V> computable, CycleHandler<K> cycleHandler) {
        this.computable = computable;
        this.cycleHandler = cycleHandler;
    }

    @Override
    public V apply(K key) {
        OriginThreadAwareFuture f = this.cache.get(key);
        if (f == null) {
            OriginThreadAwareFuture ft = new OriginThreadAwareFuture(key);
            f = this.cache.putIfAbsent(key, ft);
            if (f == null) {
                f = ft;
                ft.run();
            }
        } else {
            long tid = f.threadId;
            if (tid != -1L && Thread.currentThread().getId() == f.threadId) {
                this.cycleHandler.handleCycle(key);
            }
        }
        try {
            return f.get();
        }
        catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        catch (ExecutionException ex) {
            this.cache.remove(key);
            Throwable cause = ex.getCause();
            if (cause == null) {
                throw new RuntimeException(ex);
            }
            if (cause instanceof RuntimeException) {
                throw (RuntimeException)cause;
            }
            throw new RuntimeException(cause);
        }
    }

    public void clear() {
        this.cache.clear();
    }

    public boolean containsKey(K key) {
        return this.cache.containsKey(key);
    }

    public void remove(K key) {
        this.cache.remove(key);
    }

    public int size() {
        return this.cache.size();
    }

    private class OriginThreadAwareFuture
    implements Future<V> {
        private final FutureTask<V> future;
        private volatile long threadId = Thread.currentThread().getId();

        OriginThreadAwareFuture(K key) {
            Callable<Object> eval = () -> {
                try {
                    Object r = Cache.this.computable.apply(key);
                    return r;
                }
                finally {
                    this.threadId = -1L;
                }
            };
            this.future = new FutureTask<Object>(eval);
        }

        public int hashCode() {
            return this.future.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            OriginThreadAwareFuture other = (OriginThreadAwareFuture)obj;
            return this.future == other.future || this.future != null && this.future.equals(other.future);
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return this.future.cancel(mayInterruptIfRunning);
        }

        @Override
        public boolean isCancelled() {
            return this.future.isCancelled();
        }

        @Override
        public boolean isDone() {
            return this.future.isDone();
        }

        @Override
        public V get() throws InterruptedException, ExecutionException {
            return this.future.get();
        }

        @Override
        public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return this.future.get(timeout, unit);
        }

        public void run() {
            this.future.run();
        }
    }

    public static interface CycleHandler<K> {
        public void handleCycle(K var1);
    }
}

