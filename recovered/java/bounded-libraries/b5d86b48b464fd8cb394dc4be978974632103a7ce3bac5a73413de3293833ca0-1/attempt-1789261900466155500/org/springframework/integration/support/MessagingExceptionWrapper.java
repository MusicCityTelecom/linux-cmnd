/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessagingException
 */
package org.springframework.integration.support;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

public class MessagingExceptionWrapper
extends MessagingException {
    private static final long serialVersionUID = 1L;

    public MessagingExceptionWrapper(Message<?> originalMessage, MessagingException cause) {
        super(originalMessage, (Throwable)cause);
    }
}

