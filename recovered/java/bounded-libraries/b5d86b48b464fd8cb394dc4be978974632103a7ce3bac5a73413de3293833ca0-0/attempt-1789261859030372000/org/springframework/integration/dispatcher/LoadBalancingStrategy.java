/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHandler
 */
package org.springframework.integration.dispatcher;

import java.util.Collection;
import java.util.Iterator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;

@FunctionalInterface
public interface LoadBalancingStrategy {
    public Iterator<MessageHandler> getHandlerIterator(Message<?> var1, Collection<MessageHandler> var2);
}

