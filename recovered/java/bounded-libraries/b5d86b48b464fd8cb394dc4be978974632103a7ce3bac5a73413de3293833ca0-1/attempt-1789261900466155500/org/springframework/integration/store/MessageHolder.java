/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.store;

import java.io.Serializable;
import org.springframework.integration.store.MessageMetadata;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class MessageHolder
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Message<?> message;
    private MessageMetadata messageMetadata;

    private MessageHolder() {
    }

    public MessageHolder(Message<?> message) {
        Assert.notNull(message, (String)"'message' must not be null.");
        this.message = message;
        this.messageMetadata = new MessageMetadata(message.getHeaders().getId());
        this.messageMetadata.setTimestamp(System.currentTimeMillis());
    }

    public void setTimestamp(long timestamp) {
        this.messageMetadata.setTimestamp(timestamp);
    }

    public Message<?> getMessage() {
        return this.message;
    }

    public MessageMetadata getMessageMetadata() {
        return this.messageMetadata;
    }
}

