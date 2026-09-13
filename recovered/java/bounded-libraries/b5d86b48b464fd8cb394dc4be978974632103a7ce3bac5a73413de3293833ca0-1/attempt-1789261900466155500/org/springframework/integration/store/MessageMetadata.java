/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.store;

import java.io.Serializable;
import java.util.UUID;

public class MessageMetadata
implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID messageId;
    private volatile long timestamp;

    private MessageMetadata() {
    }

    public MessageMetadata(UUID messageId) {
        this.messageId = messageId;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public UUID getMessageId() {
        return this.messageId;
    }

    public long getTimestamp() {
        return this.timestamp;
    }
}

