/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.core.MessagePostProcessor
 *  org.springframework.util.Assert
 */
package org.springframework.integration.history;

import org.springframework.integration.history.MessageHistory;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.integration.support.management.TrackableComponent;
import org.springframework.messaging.Message;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.util.Assert;

public class HistoryWritingMessagePostProcessor
implements MessagePostProcessor {
    private volatile TrackableComponent trackableComponent;
    private volatile boolean shouldTrack;
    private volatile MessageBuilderFactory messageBuilderFactory = new DefaultMessageBuilderFactory();

    public HistoryWritingMessagePostProcessor() {
    }

    public HistoryWritingMessagePostProcessor(TrackableComponent trackableComponent) {
        Assert.notNull((Object)trackableComponent, (String)"trackableComponent must not be null");
        this.trackableComponent = trackableComponent;
    }

    public void setMessageBuilderFactory(MessageBuilderFactory messageBuilderFactory) {
        Assert.notNull((Object)messageBuilderFactory, (String)"'messageBuilderFactory' cannot be null");
        this.messageBuilderFactory = messageBuilderFactory;
    }

    public void setTrackableComponent(TrackableComponent trackableComponent) {
        this.trackableComponent = trackableComponent;
    }

    public void setShouldTrack(boolean shouldTrack) {
        this.shouldTrack = shouldTrack;
    }

    public Message<?> postProcessMessage(Message<?> message) {
        if (this.shouldTrack && this.trackableComponent != null) {
            return MessageHistory.write(message, this.trackableComponent, this.messageBuilderFactory);
        }
        return message;
    }
}

