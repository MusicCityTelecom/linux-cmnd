/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.transformer;

@FunctionalInterface
public interface GenericTransformer<S, T> {
    public T transform(S var1);
}

