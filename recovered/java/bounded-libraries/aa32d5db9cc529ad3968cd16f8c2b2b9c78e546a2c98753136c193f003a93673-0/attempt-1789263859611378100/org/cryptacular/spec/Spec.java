/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.spec;

public interface Spec<T> {
    public String getAlgorithm();

    public T newInstance();
}

