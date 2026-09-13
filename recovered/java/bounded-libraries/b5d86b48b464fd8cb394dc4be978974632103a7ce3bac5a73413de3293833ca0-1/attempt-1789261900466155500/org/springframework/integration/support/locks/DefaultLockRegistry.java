/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.integration.support.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.util.Assert;

public final class DefaultLockRegistry
implements LockRegistry {
    private final Lock[] lockTable;
    private final int mask;

    public DefaultLockRegistry() {
        this(255);
    }

    public DefaultLockRegistry(int mask) {
        String bits = Integer.toBinaryString(mask);
        Assert.isTrue((bits.length() < 32 && (mask == 0 || bits.lastIndexOf(48) < bits.indexOf(49)) ? 1 : 0) != 0, (String)"Mask must be a power of 2 - 1");
        this.mask = mask;
        int arraySize = this.mask + 1;
        this.lockTable = new ReentrantLock[arraySize];
        for (int i = 0; i < arraySize; ++i) {
            this.lockTable[i] = new ReentrantLock();
        }
    }

    @Override
    public Lock obtain(Object lockKey) {
        Assert.notNull((Object)lockKey, (String)"'lockKey' must not be null");
        Integer lockIndex = lockKey.hashCode() & this.mask;
        return this.lockTable[lockIndex];
    }
}

