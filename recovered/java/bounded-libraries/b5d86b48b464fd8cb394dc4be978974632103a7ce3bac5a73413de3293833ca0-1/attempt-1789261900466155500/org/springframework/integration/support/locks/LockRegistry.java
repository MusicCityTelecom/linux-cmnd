/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.support.locks;

import java.util.concurrent.locks.Lock;

@FunctionalInterface
public interface LockRegistry {
    public Lock obtain(Object var1);
}

