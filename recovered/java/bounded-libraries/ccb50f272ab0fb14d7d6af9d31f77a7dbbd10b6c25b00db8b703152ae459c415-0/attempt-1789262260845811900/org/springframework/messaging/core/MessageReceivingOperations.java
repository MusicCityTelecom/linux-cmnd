/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.core;

import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

public interface MessageReceivingOperations<D> {
    @Nullable
    public Message<?> receive() throws MessagingException;

    @Nullable
    public Message<?> receive(D var1) throws MessagingException;

    @Nullable
    public <T> T receiveAndConvert(Class<T> var1) throws MessagingException;

    @Nullable
    public <T> T receiveAndConvert(D var1, Class<T> var2) throws MessagingException;
}

