/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.classify;

import org.springframework.classify.Classifier;

public class ClassifierSupport<C, T>
implements Classifier<C, T> {
    private final T defaultValue;

    public ClassifierSupport(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public T classify(C throwable) {
        return this.defaultValue;
    }
}

