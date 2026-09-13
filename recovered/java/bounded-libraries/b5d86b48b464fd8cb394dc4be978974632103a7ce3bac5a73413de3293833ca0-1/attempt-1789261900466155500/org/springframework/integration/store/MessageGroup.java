/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.store;

import java.util.Collection;
import java.util.stream.Stream;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;

public interface MessageGroup {
    public boolean canAdd(Message<?> var1);

    public void add(Message<?> var1);

    public boolean remove(Message<?> var1);

    public Collection<Message<?>> getMessages();

    default public Stream<Message<?>> streamMessages() {
        return this.getMessages().stream();
    }

    public Object getGroupId();

    public int getLastReleasedMessageSequenceNumber();

    public void setLastReleasedMessageSequenceNumber(int var1);

    public boolean isComplete();

    public void complete();

    public int getSequenceSize();

    public int size();

    public Message<?> getOne();

    public long getTimestamp();

    public long getLastModified();

    public void setLastModified(long var1);

    public void setCondition(String var1);

    @Nullable
    public String getCondition();

    public void clear();
}

