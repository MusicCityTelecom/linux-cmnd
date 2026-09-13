/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.PlatformTransactionManager
 */
package org.springframework.boot.autoconfigure.transaction;

import org.springframework.transaction.PlatformTransactionManager;

@FunctionalInterface
public interface PlatformTransactionManagerCustomizer<T extends PlatformTransactionManager> {
    public void customize(T var1);
}

