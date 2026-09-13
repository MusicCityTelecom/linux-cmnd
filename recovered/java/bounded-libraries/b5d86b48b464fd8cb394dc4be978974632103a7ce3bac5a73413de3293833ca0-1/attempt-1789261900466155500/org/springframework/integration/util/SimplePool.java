/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.util.Assert
 */
package org.springframework.integration.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.util.Pool;
import org.springframework.integration.util.PoolItemNotAvailableException;
import org.springframework.util.Assert;

public class SimplePool<T>
implements Pool<T> {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private final PoolSemaphore permits = new PoolSemaphore(0);
    private final AtomicInteger poolSize = new AtomicInteger();
    private final AtomicInteger targetPoolSize = new AtomicInteger();
    private long waitTimeout = Long.MAX_VALUE;
    private final BlockingQueue<T> available = new LinkedBlockingQueue<T>();
    private final Set<T> allocated = Collections.synchronizedSet(new HashSet());
    private final Set<T> inUse = Collections.synchronizedSet(new HashSet());
    private final PoolItemCallback<T> callback;
    private volatile boolean closed;

    public SimplePool(int poolSize, PoolItemCallback<T> callback) {
        if (poolSize <= 0) {
            this.poolSize.set(Integer.MAX_VALUE);
            this.targetPoolSize.set(Integer.MAX_VALUE);
            this.permits.release(Integer.MAX_VALUE);
        } else {
            this.poolSize.set(poolSize);
            this.targetPoolSize.set(poolSize);
            this.permits.release(poolSize);
        }
        this.callback = callback;
    }

    public synchronized void setPoolSize(int poolSize) {
        int delta = poolSize - this.poolSize.get();
        this.targetPoolSize.addAndGet(delta);
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)String.format("Target pool size changed by %d, now %d", delta, this.targetPoolSize.get()));
        }
        if (delta > 0) {
            this.poolSize.addAndGet(delta);
            this.permits.release(delta);
        } else {
            Object item;
            this.permits.reducePermits(-delta);
            int inUseSize = this.inUse.size();
            int newPoolSize = Math.max(poolSize, inUseSize);
            this.poolSize.set(newPoolSize);
            for (int i = this.available.size(); i > newPoolSize - inUseSize && (item = this.available.poll()) != null; --i) {
                this.doRemoveItem(item);
            }
            int inUseDelta = poolSize - inUseSize;
            if (inUseDelta < 0 && this.logger.isDebugEnabled()) {
                this.logger.debug((Object)String.format("Pool is overcommitted by %d; items will be removed when returned", -inUseDelta));
            }
        }
    }

    @Override
    public synchronized int getPoolSize() {
        return this.poolSize.get();
    }

    @Override
    public int getIdleCount() {
        return this.available.size();
    }

    @Override
    public int getActiveCount() {
        return this.inUse.size();
    }

    @Override
    public int getAllocatedCount() {
        return this.allocated.size();
    }

    public void setWaitTimeout(long waitTimeout) {
        this.waitTimeout = waitTimeout;
    }

    @Override
    public T getItem() {
        Assert.state((!this.closed ? 1 : 0) != 0, (String)"Pool has been closed");
        boolean permitted = false;
        try {
            try {
                permitted = this.permits.tryAcquire(this.waitTimeout, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new PoolItemNotAvailableException("Interrupted awaiting a pooled resource", e);
            }
            if (!permitted) {
                throw new PoolItemNotAvailableException("Timed out while waiting to acquire a pool entry.");
            }
            return this.doGetItem();
        }
        catch (Exception e) {
            if (permitted) {
                this.permits.release();
            }
            if (e instanceof PoolItemNotAvailableException) {
                throw (PoolItemNotAvailableException)e;
            }
            throw new PoolItemNotAvailableException("Failed to obtain pooled item", e);
        }
    }

    private T doGetItem() {
        Object item = this.available.poll();
        if (item != null && this.logger.isDebugEnabled()) {
            this.logger.debug((Object)("Obtained " + item + " from pool."));
        }
        if (item == null) {
            item = this.callback.createForPool();
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("Obtained new " + item + "."));
            }
            this.allocated.add(item);
        } else if (this.callback.isStale(item)) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("Received a stale item " + item + ", will attempt to get a new one."));
            }
            this.doRemoveItem(item);
            item = this.doGetItem();
        }
        this.inUse.add(item);
        return (T)item;
    }

    @Override
    public synchronized void releaseItem(T item) {
        Assert.notNull(item, (String)"Item cannot be null");
        Assert.isTrue((boolean)this.allocated.contains(item), (String)"You can only release items that were obtained from the pool");
        if (this.inUse.contains(item)) {
            if (this.poolSize.get() > this.targetPoolSize.get() || this.closed) {
                this.poolSize.decrementAndGet();
                this.doRemoveItem(item);
            } else {
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug((Object)("Releasing " + item + " back to the pool"));
                }
                this.available.add(item);
                this.inUse.remove(item);
                this.permits.release();
            }
        } else if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)("Ignoring release of " + item + " back to the pool - not in use"));
        }
    }

    @Override
    public synchronized void removeAllIdleItems() {
        Object item;
        while ((item = this.available.poll()) != null) {
            this.doRemoveItem(item);
        }
    }

    private void doRemoveItem(T item) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)("Removing " + item + " from the pool"));
        }
        this.allocated.remove(item);
        this.inUse.remove(item);
        this.callback.removedFromPool(item);
    }

    @Override
    public synchronized void close() {
        this.closed = true;
        this.removeAllIdleItems();
    }

    private static class PoolSemaphore
    extends Semaphore {
        PoolSemaphore(int permits) {
            super(permits);
        }

        @Override
        public void reducePermits(int reduction) {
            super.reducePermits(reduction > this.availablePermits() ? this.availablePermits() : reduction);
        }
    }

    public static interface PoolItemCallback<T> {
        public T createForPool();

        public boolean isStale(T var1);

        public void removedFromPool(T var1);
    }
}

