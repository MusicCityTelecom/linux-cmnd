/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.store;

import java.util.Collection;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.store.MessageGroupStore;
import org.springframework.messaging.Message;

public interface MessageGroupFactory {
    public MessageGroup create(Object var1);

    public MessageGroup create(Collection<? extends Message<?>> var1, Object var2);

    public MessageGroup create(Collection<? extends Message<?>> var1, Object var2, long var3, boolean var5);

    public MessageGroup create(MessageGroupStore var1, Object var2);

    public MessageGroup create(MessageGroupStore var1, Object var2, long var3, boolean var5);
}

