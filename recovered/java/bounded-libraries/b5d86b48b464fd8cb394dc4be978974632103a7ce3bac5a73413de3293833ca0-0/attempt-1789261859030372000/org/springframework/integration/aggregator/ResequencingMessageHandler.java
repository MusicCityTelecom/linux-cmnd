/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.aggregator;

import java.util.Collection;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.aggregator.AbstractCorrelatingMessageHandler;
import org.springframework.integration.aggregator.CorrelationStrategy;
import org.springframework.integration.aggregator.MessageGroupProcessor;
import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.store.MessageGroupStore;
import org.springframework.integration.store.SimpleMessageStore;
import org.springframework.messaging.Message;

public class ResequencingMessageHandler
extends AbstractCorrelatingMessageHandler {
    public ResequencingMessageHandler(MessageGroupProcessor processor, MessageGroupStore store, CorrelationStrategy correlationStrategy, ReleaseStrategy releaseStrategy) {
        super(processor, store, correlationStrategy, releaseStrategy);
        this.setExpireGroupsUponTimeout(false);
    }

    public ResequencingMessageHandler(MessageGroupProcessor processor, MessageGroupStore store) {
        super(processor, store);
        this.setExpireGroupsUponTimeout(false);
    }

    public ResequencingMessageHandler(MessageGroupProcessor processor) {
        super(processor);
        this.setExpireGroupsUponTimeout(false);
    }

    @Override
    public final void setExpireGroupsUponTimeout(boolean expireGroupsUponTimeout) {
        super.setExpireGroupsUponTimeout(expireGroupsUponTimeout);
    }

    @Override
    public String getComponentType() {
        return "resequencer";
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.resequencer;
    }

    @Override
    protected boolean shouldCopyRequestHeaders() {
        return false;
    }

    @Override
    protected void afterRelease(MessageGroup messageGroup, Collection<Message<?>> completedMessages) {
        this.afterRelease(messageGroup, completedMessages, false);
    }

    @Override
    protected void afterRelease(MessageGroup messageGroup, Collection<Message<?>> completedMessages, boolean timeout) {
        int size = messageGroup.size();
        int sequenceSize = messageGroup.getSequenceSize();
        if (sequenceSize > 0 && sequenceSize == size) {
            this.remove(messageGroup);
        } else {
            Object groupId = messageGroup.getGroupId();
            MessageGroupStore messageStore = this.getMessageStore();
            if (completedMessages != null) {
                int lastReleasedSequenceNumber = this.findLastReleasedSequenceNumber(groupId, completedMessages);
                messageStore.setLastReleasedSequenceNumberForGroup(groupId, lastReleasedSequenceNumber);
                if (messageStore instanceof SimpleMessageStore && completedMessages.size() == messageGroup.size()) {
                    ((SimpleMessageStore)messageStore).clearMessageGroup(groupId);
                } else {
                    messageStore.removeMessagesFromGroup(groupId, completedMessages);
                }
            }
            if (timeout) {
                messageStore.completeGroup(groupId);
            }
        }
    }
}

