/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.AttributeAccessor
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessagingException
 *  org.springframework.retry.RecoveryCallback
 *  org.springframework.retry.RetryContext
 */
package org.springframework.integration.handler.advice;

import org.springframework.core.AttributeAccessor;
import org.springframework.integration.core.ErrorMessagePublisher;
import org.springframework.integration.support.DefaultErrorMessageStrategy;
import org.springframework.integration.support.ErrorMessageStrategy;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryContext;

public class ErrorMessageSendingRecoverer
extends ErrorMessagePublisher
implements RecoveryCallback<Object> {
    public ErrorMessageSendingRecoverer() {
        this(null);
    }

    public ErrorMessageSendingRecoverer(MessageChannel channel) {
        this(channel, null);
    }

    public ErrorMessageSendingRecoverer(MessageChannel channel, ErrorMessageStrategy errorMessageStrategy) {
        this.setChannel(channel);
        this.setErrorMessageStrategy(errorMessageStrategy == null ? new DefaultErrorMessageStrategy() : errorMessageStrategy);
    }

    public Object recover(RetryContext context) {
        this.publish(context.getLastThrowable(), (AttributeAccessor)context);
        return null;
    }

    @Override
    protected Throwable payloadWhenNull(AttributeAccessor context) {
        Message message = (Message)context.getAttribute("message");
        String description = "No retry exception available; this can occur, for example, if the RetryPolicy allowed zero attempts to execute the handler; RetryContext: " + context.toString();
        return message == null ? new RetryExceptionNotAvailableException(description) : new RetryExceptionNotAvailableException(message, description);
    }

    public static class RetryExceptionNotAvailableException
    extends MessagingException {
        private static final long serialVersionUID = 1L;

        RetryExceptionNotAvailableException(String description) {
            super(description);
        }

        public RetryExceptionNotAvailableException(Message<?> message, String description) {
            super(message, description);
        }
    }
}

