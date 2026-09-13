/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.messaging.simp.annotation.support;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

public class MissingSessionUserException
extends MessagingException {
    public MissingSessionUserException(Message<?> message) {
        super(message, "No \"user\" header in message");
    }
}

