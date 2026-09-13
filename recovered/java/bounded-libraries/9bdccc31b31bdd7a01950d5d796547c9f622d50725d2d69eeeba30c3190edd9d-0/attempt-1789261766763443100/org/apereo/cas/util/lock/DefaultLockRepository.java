/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.jooq.lambda.Unchecked
 *  org.springframework.integration.support.locks.LockRegistry
 */
package org.apereo.cas.util.lock;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;
import lombok.Generated;
import org.apereo.cas.util.lock.LockRepository;
import org.jooq.lambda.Unchecked;
import org.springframework.integration.support.locks.LockRegistry;

public class DefaultLockRepository
implements LockRepository {
    private static final int LOCK_TIMEOUT_SECONDS = 3;
    private final LockRegistry lockRegistry;

    @Override
    public <T> Optional<T> execute(Object lockKey, Supplier<T> consumer) {
        return (Optional)Unchecked.supplier(() -> {
            Lock lock = this.lockRegistry.obtain(lockKey);
            boolean lockFound = lock.tryLock(3L, TimeUnit.SECONDS);
            return Optional.of(lockFound).filter(Boolean::booleanValue).map(arg_0 -> DefaultLockRepository.lambda$execute$0((Supplier)consumer, lock, arg_0));
        }).get();
    }

    @Generated
    public DefaultLockRepository(LockRegistry lockRegistry) {
        this.lockRegistry = lockRegistry;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static /* synthetic */ Object lambda$execute$0(Supplier consumer, Lock lock, Boolean result) {
        try {
            Object t = consumer.get();
            return t;
        }
        finally {
            lock.unlock();
        }
    }
}

