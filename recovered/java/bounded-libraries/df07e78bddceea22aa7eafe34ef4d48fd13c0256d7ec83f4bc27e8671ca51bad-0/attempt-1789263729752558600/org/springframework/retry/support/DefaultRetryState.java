/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.support;

import org.springframework.classify.Classifier;
import org.springframework.retry.RetryState;

public class DefaultRetryState
implements RetryState {
    private final Object key;
    private final boolean forceRefresh;
    private final Classifier<? super Throwable, Boolean> rollbackClassifier;

    public DefaultRetryState(Object key, boolean forceRefresh, Classifier<? super Throwable, Boolean> rollbackClassifier) {
        this.key = key;
        this.forceRefresh = forceRefresh;
        this.rollbackClassifier = rollbackClassifier;
    }

    public DefaultRetryState(Object key, Classifier<? super Throwable, Boolean> rollbackClassifier) {
        this(key, false, rollbackClassifier);
    }

    public DefaultRetryState(Object key, boolean forceRefresh) {
        this(key, forceRefresh, null);
    }

    public DefaultRetryState(Object key) {
        this(key, false, null);
    }

    @Override
    public Object getKey() {
        return this.key;
    }

    @Override
    public boolean isForceRefresh() {
        return this.forceRefresh;
    }

    @Override
    public boolean rollbackFor(Throwable exception) {
        if (this.rollbackClassifier == null) {
            return true;
        }
        return this.rollbackClassifier.classify(exception);
    }

    public String toString() {
        return String.format("[%s: key=%s, forceRefresh=%b]", this.getClass().getSimpleName(), this.key, this.forceRefresh);
    }
}

