/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.core;

@FunctionalInterface
public interface GenericSelector<S> {
    public boolean accept(S var1);
}

