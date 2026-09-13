/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.converter;

import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

public class MessageConversionException
extends MessagingException {
    public MessageConversionException(String description) {
        super(description);
    }

    public MessageConversionException(@Nullable String description, @Nullable Throwable cause) {
        super(description, cause);
    }

    public MessageConversionException(Message<?> failedMessage, String description) {
        super(failedMessage, description);
    }

    public MessageConversionException(Message<?> failedMessage, @Nullable String description, @Nullable Throwable cause) {
        super(failedMessage, description, cause);
    }
}

