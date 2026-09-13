/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.integration.store;

import org.springframework.integration.store.BasicMessageGroupStore;
import org.springframework.integration.store.MessageGroupFactory;
import org.springframework.integration.store.SimpleMessageGroupFactory;
import org.springframework.util.Assert;

public abstract class AbstractBatchingMessageGroupStore
implements BasicMessageGroupStore {
    private static final int DEFAULT_REMOVE_BATCH_SIZE = 100;
    private volatile int removeBatchSize = 100;
    private volatile MessageGroupFactory messageGroupFactory = new SimpleMessageGroupFactory();

    public void setRemoveBatchSize(int removeBatchSize) {
        this.removeBatchSize = removeBatchSize;
    }

    public int getRemoveBatchSize() {
        return this.removeBatchSize;
    }

    public void setMessageGroupFactory(MessageGroupFactory messageGroupFactory) {
        Assert.notNull((Object)messageGroupFactory, (String)"'messageGroupFactory' must not be null");
        this.messageGroupFactory = messageGroupFactory;
    }

    protected MessageGroupFactory getMessageGroupFactory() {
        return this.messageGroupFactory;
    }
}

