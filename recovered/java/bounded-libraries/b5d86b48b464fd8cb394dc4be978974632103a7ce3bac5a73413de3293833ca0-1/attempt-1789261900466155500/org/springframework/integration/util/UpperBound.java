/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.util;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public final class UpperBound {
    private final Semaphore semaphore;

    public UpperBound(int capacity) {
        this.semaphore = capacity > 0 ? new Semaphore(capacity, true) : null;
    }

    public int availablePermits() {
        if (this.semaphore == null) {
            return Integer.MAX_VALUE;
        }
        return this.semaphore.availablePermits();
    }

    public boolean tryAcquire(long timeoutInMilliseconds) {
        if (this.semaphore != null) {
            try {
                if (timeoutInMilliseconds < 0L) {
                    this.semaphore.acquire();
                    return true;
                }
                return this.semaphore.tryAcquire(timeoutInMilliseconds, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return true;
    }

    public void release() {
        if (this.semaphore != null) {
            this.semaphore.release();
        }
    }

    public void release(int permits) {
        if (this.semaphore != null) {
            this.semaphore.release(permits);
        }
    }

    public String toString() {
        return super.toString() + "[Permits = " + (this.semaphore != null ? Integer.valueOf(this.semaphore.availablePermits()) : "UNLIMITED") + "]";
    }
}

