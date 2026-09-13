/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHandler
 */
package org.springframework.integration.dispatcher;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;

public interface MessageDispatcher {
    public boolean addHandler(MessageHandler var1);

    public boolean removeHandler(MessageHandler var1);

    public boolean dispatch(Message<?> var1);

    public int getHandlerCount();
}

