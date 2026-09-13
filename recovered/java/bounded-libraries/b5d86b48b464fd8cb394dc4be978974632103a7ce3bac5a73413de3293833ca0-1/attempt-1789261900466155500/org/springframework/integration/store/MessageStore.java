/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.store;

import java.util.UUID;
import org.springframework.integration.store.MessageMetadata;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.messaging.Message;

public interface MessageStore {
    public Message<?> getMessage(UUID var1);

    public MessageMetadata getMessageMetadata(UUID var1);

    public <T> Message<T> addMessage(Message<T> var1);

    public Message<?> removeMessage(UUID var1);

    @ManagedAttribute
    public long getMessageCount();
}

