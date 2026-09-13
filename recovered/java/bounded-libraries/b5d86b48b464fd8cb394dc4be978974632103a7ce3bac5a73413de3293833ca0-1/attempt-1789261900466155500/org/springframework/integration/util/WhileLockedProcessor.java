/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessagingException
 */
package org.springframework.integration.util;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.messaging.MessagingException;

public abstract class WhileLockedProcessor {
    private final Object key;
    private final LockRegistry lockRegistry;

    public WhileLockedProcessor(LockRegistry lockRegistry, Object key) {
        this.key = key;
        this.lockRegistry = lockRegistry;
    }

    public final void doWhileLocked() throws IOException {
        Lock lock = this.lockRegistry.obtain(this.key);
        try {
            lock.lockInterruptibly();
            try {
                this.whileLocked();
            }
            finally {
                lock.unlock();
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MessagingException("Thread was interrupted while performing task", (Throwable)e);
        }
    }

    protected abstract void whileLocked() throws IOException;
}

