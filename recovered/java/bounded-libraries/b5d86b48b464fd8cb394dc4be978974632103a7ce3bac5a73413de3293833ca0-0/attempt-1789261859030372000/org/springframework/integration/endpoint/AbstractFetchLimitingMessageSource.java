/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.endpoint;

import org.springframework.integration.endpoint.AbstractMessageSource;
import org.springframework.integration.support.management.MessageSourceManagement;

public abstract class AbstractFetchLimitingMessageSource<T>
extends AbstractMessageSource<T>
implements MessageSourceManagement {
    private volatile int maxFetchSize = Integer.MIN_VALUE;

    @Override
    public void setMaxFetchSize(int maxFetchSize) {
        this.maxFetchSize = maxFetchSize;
    }

    @Override
    public int getMaxFetchSize() {
        return this.maxFetchSize;
    }

    @Override
    protected Object doReceive() {
        return this.doReceive(this.maxFetchSize);
    }

    protected abstract Object doReceive(int var1);
}

