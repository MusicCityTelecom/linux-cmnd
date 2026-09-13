/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.support.locks;

import org.springframework.integration.support.locks.LockRegistry;

public interface RenewableLockRegistry
extends LockRegistry {
    public void renewLock(Object var1);
}

