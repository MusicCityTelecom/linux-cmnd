/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.support.ResourceHolder
 *  org.springframework.transaction.support.ResourceHolderSynchronization
 */
package org.springframework.integration.transaction;

import org.springframework.integration.transaction.IntegrationResourceHolder;
import org.springframework.transaction.support.ResourceHolder;
import org.springframework.transaction.support.ResourceHolderSynchronization;

public class IntegrationResourceHolderSynchronization
extends ResourceHolderSynchronization<IntegrationResourceHolder, Object> {
    protected final IntegrationResourceHolder resourceHolder;
    private boolean shouldUnbindAtCompletion = true;

    public IntegrationResourceHolderSynchronization(IntegrationResourceHolder resourceHolder, Object resourceKey) {
        super((ResourceHolder)resourceHolder, resourceKey);
        this.resourceHolder = resourceHolder;
    }

    public IntegrationResourceHolder getResourceHolder() {
        return this.resourceHolder;
    }

    public void setShouldUnbindAtCompletion(boolean shouldUnbindAtCompletion) {
        this.shouldUnbindAtCompletion = shouldUnbindAtCompletion;
    }

    protected boolean shouldUnbindAtCompletion() {
        return this.shouldUnbindAtCompletion;
    }
}

