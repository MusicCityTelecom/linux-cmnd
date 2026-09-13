/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.store;

import org.springframework.integration.store.MessageGroup;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.messaging.Message;

public interface BasicMessageGroupStore {
    @ManagedAttribute
    public int messageGroupSize(Object var1);

    public MessageGroup getMessageGroup(Object var1);

    public MessageGroup addMessageToGroup(Object var1, Message<?> var2);

    public Message<?> pollMessageFromGroup(Object var1);

    public void removeMessageGroup(Object var1);
}

