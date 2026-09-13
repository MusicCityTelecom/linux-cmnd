/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.integration.support.locks.DefaultLockRegistry
 *  org.springframework.integration.support.locks.LockRegistry
 *  org.springframework.integration.support.locks.PassThruLockRegistry
 */
package org.apereo.cas.util.lock;

import java.util.Optional;
import java.util.function.Supplier;
import org.apereo.cas.util.lock.DefaultLockRepository;
import org.springframework.integration.support.locks.DefaultLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.integration.support.locks.PassThruLockRegistry;

@FunctionalInterface
public interface LockRepository {
    public static final String BEAN_NAME = "casTicketRegistryLockRepository";
    public static final int DEFAULT_MASK_ARRAY_LENGTH = 1023;

    public static DefaultLockRepository noOp() {
        return new DefaultLockRepository((LockRegistry)new PassThruLockRegistry());
    }

    public static DefaultLockRepository asDefault() {
        return new DefaultLockRepository((LockRegistry)new DefaultLockRegistry(1023));
    }

    public <T> Optional<T> execute(Object var1, Supplier<T> var2);
}

