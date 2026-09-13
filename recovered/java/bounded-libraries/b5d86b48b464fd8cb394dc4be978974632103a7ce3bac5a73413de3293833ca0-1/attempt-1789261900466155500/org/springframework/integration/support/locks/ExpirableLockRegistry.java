/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.support.locks;

import org.springframework.integration.support.locks.LockRegistry;

public interface ExpirableLockRegistry
extends LockRegistry {
    public void expireUnusedOlderThan(long var1);
}

