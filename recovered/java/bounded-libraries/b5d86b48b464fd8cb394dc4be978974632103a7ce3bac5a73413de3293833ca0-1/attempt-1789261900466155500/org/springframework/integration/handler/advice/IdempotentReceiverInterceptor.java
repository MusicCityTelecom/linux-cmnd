/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.intercept.MethodInvocation
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.util.Assert
 */
package org.springframework.integration.handler.advice;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.integration.MessageRejectedException;
import org.springframework.integration.core.MessageSelector;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.handler.advice.AbstractHandleMessageAdvice;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.Assert;

public class IdempotentReceiverInterceptor
extends AbstractHandleMessageAdvice {
    private final MessagingTemplate messagingTemplate = new MessagingTemplate();
    private final MessageSelector messageSelector;
    private MessageChannel discardChannel;
    private String discardChannelName;
    private boolean throwExceptionOnRejection;

    public IdempotentReceiverInterceptor(MessageSelector messageSelector) {
        Assert.notNull((Object)messageSelector, (String)"'messageSelector' must not be null");
        this.messageSelector = messageSelector;
    }

    public void setTimeout(long timeout) {
        this.messagingTemplate.setSendTimeout(timeout);
    }

    public void setThrowExceptionOnRejection(boolean throwExceptionOnRejection) {
        this.throwExceptionOnRejection = throwExceptionOnRejection;
    }

    public void setDiscardChannel(MessageChannel discardChannel) {
        this.discardChannel = discardChannel;
    }

    public void setDiscardChannelName(String discardChannelName) {
        this.discardChannelName = discardChannelName;
    }

    @Override
    public String getComponentType() {
        return "idempotent-receiver-interceptor";
    }

    @Override
    protected Object doInvoke(MethodInvocation invocation, Message<?> message) throws Throwable {
        boolean accept = this.messageSelector.accept(message);
        if (!accept) {
            boolean discarded = false;
            MessageChannel theDiscardChannel = this.obtainDiscardChannel();
            if (theDiscardChannel != null) {
                this.messagingTemplate.send(theDiscardChannel, message);
                discarded = true;
            }
            if (this.throwExceptionOnRejection) {
                throw new MessageRejectedException(message, "IdempotentReceiver '" + this + "' rejected duplicate Message: " + message);
            }
            if (!discarded) {
                invocation.getArguments()[0] = this.getMessageBuilderFactory().fromMessage(message).setHeader("duplicateMessage", true).build();
            } else {
                return null;
            }
        }
        return invocation.proceed();
    }

    private MessageChannel obtainDiscardChannel() {
        if (this.discardChannel == null && this.discardChannelName != null) {
            this.discardChannel = (MessageChannel)this.getChannelResolver().resolveDestination(this.discardChannelName);
        }
        return this.discardChannel;
    }
}

