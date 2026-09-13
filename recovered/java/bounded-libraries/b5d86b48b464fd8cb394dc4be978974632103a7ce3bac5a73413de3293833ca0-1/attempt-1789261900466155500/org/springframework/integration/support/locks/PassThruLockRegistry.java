/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.support.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import org.springframework.integration.support.locks.LockRegistry;

public final class PassThruLockRegistry
implements LockRegistry {
    @Override
    public Lock obtain(Object lockKey) {
        return new PassThruLock();
    }

    private static final class PassThruLock
    implements Lock {
        PassThruLock() {
        }

        @Override
        public void unlock() {
        }

        @Override
        public boolean tryLock(long time, TimeUnit unit) {
            return true;
        }

        @Override
        public boolean tryLock() {
            return true;
        }

        @Override
        public Condition newCondition() {
            throw new UnsupportedOperationException("This method is not supported for this implementation of Lock");
        }

        @Override
        public void lockInterruptibly() {
        }

        @Override
        public void lock() {
        }
    }
}

