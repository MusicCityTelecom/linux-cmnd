/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.jmx.export.annotation.ManagedOperation
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.store;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;
import org.springframework.integration.store.BasicMessageGroupStore;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.store.MessageGroupMetadata;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.messaging.Message;

public interface MessageGroupStore
extends BasicMessageGroupStore {
    @ManagedAttribute
    public int getMessageCountForAllMessageGroups();

    @ManagedAttribute
    public int getMessageGroupCount();

    public void removeMessagesFromGroup(Object var1, Collection<Message<?>> var2);

    public void removeMessagesFromGroup(Object var1, Message<?> ... var2);

    public void registerMessageGroupExpiryCallback(MessageGroupCallback var1);

    @ManagedOperation
    public int expireMessageGroups(long var1);

    public void setLastReleasedSequenceNumberForGroup(Object var1, int var2);

    public void setGroupCondition(Object var1, String var2);

    public Iterator<MessageGroup> iterator();

    public void completeGroup(Object var1);

    public MessageGroupMetadata getGroupMetadata(Object var1);

    public Message<?> getOneMessageFromGroup(Object var1);

    public void addMessagesToGroup(Object var1, Message<?> ... var2);

    public Collection<Message<?>> getMessagesForGroup(Object var1);

    default public Stream<Message<?>> streamMessagesForGroup(Object groupId) {
        return this.getMessagesForGroup(groupId).stream();
    }

    @FunctionalInterface
    public static interface MessageGroupCallback {
        public void execute(MessageGroupStore var1, MessageGroup var2);
    }
}

