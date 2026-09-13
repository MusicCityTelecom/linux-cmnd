/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot;

import org.springframework.boot.BootstrapRegistry;

@FunctionalInterface
public interface BootstrapRegistryInitializer {
    public void initialize(BootstrapRegistry var1);
}

