/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.AttributeAccessor
 *  org.springframework.core.AttributeAccessorSupport
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.support;

import org.springframework.core.AttributeAccessor;
import org.springframework.core.AttributeAccessorSupport;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;

public final class ErrorMessageUtils {
    public static final String FAILED_MESSAGE_CONTEXT_KEY = "message";
    public static final String INPUT_MESSAGE_CONTEXT_KEY = "inputMessage";

    private ErrorMessageUtils() {
    }

    public static AttributeAccessor getAttributeAccessor(@Nullable Message<?> inputMessage, @Nullable Message<?> failedMessage) {
        ErrorMessageAttributes attributes = new ErrorMessageAttributes();
        if (inputMessage != null) {
            attributes.setAttribute(INPUT_MESSAGE_CONTEXT_KEY, inputMessage);
        }
        if (failedMessage != null) {
            attributes.setAttribute(FAILED_MESSAGE_CONTEXT_KEY, failedMessage);
        }
        return attributes;
    }

    private static class ErrorMessageAttributes
    extends AttributeAccessorSupport {
        ErrorMessageAttributes() {
        }
    }
}

