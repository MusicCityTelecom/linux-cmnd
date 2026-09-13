/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.JMSException
 *  javax.jms.Message
 *  javax.jms.Session
 *  org.springframework.core.MethodParameter
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessagingException
 *  org.springframework.messaging.handler.invocation.InvocableHandlerMethod
 *  org.springframework.messaging.support.MessageBuilder
 *  org.springframework.util.Assert
 */
package org.springframework.jms.listener.adapter;

import javax.jms.JMSException;
import javax.jms.Session;
import org.springframework.core.MethodParameter;
import org.springframework.jms.listener.adapter.AbstractAdaptableMessageListener;
import org.springframework.jms.listener.adapter.ListenerExecutionFailedException;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.Assert;

public class MessagingMessageListenerAdapter
extends AbstractAdaptableMessageListener {
    @Nullable
    private InvocableHandlerMethod handlerMethod;

    public void setHandlerMethod(InvocableHandlerMethod handlerMethod) {
        this.handlerMethod = handlerMethod;
    }

    private InvocableHandlerMethod getHandlerMethod() {
        Assert.state((this.handlerMethod != null ? 1 : 0) != 0, (String)"No HandlerMethod set");
        return this.handlerMethod;
    }

    @Override
    public void onMessage(javax.jms.Message jmsMessage, @Nullable Session session) throws JMSException {
        Object result;
        Message<?> message = this.toMessagingMessage(jmsMessage);
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)("Processing [" + message + "]"));
        }
        if ((result = this.invokeHandler(jmsMessage, session, message)) != null) {
            this.handleResult(result, jmsMessage, session);
        } else {
            this.logger.trace((Object)"No result object given - no result to handle");
        }
    }

    @Override
    protected Object preProcessResponse(Object result) {
        MethodParameter returnType = this.getHandlerMethod().getReturnType();
        if (result instanceof Message) {
            return MessageBuilder.fromMessage((Message)((Message)result)).setHeader("conversionHint", (Object)returnType).build();
        }
        return MessageBuilder.withPayload((Object)result).setHeader("conversionHint", (Object)returnType).build();
    }

    protected Message<?> toMessagingMessage(javax.jms.Message jmsMessage) {
        try {
            return (Message)this.getMessagingMessageConverter().fromMessage(jmsMessage);
        }
        catch (JMSException ex) {
            throw new MessageConversionException("Could not convert JMS message", ex);
        }
    }

    @Nullable
    private Object invokeHandler(javax.jms.Message jmsMessage, @Nullable Session session, Message<?> message) {
        InvocableHandlerMethod handlerMethod = this.getHandlerMethod();
        try {
            return handlerMethod.invoke(message, new Object[]{jmsMessage, session});
        }
        catch (MessagingException ex) {
            throw new ListenerExecutionFailedException(this.createMessagingErrorMessage("Listener method could not be invoked with incoming message"), ex);
        }
        catch (Exception ex) {
            throw new ListenerExecutionFailedException("Listener method '" + handlerMethod.getMethod().toGenericString() + "' threw exception", ex);
        }
    }

    private String createMessagingErrorMessage(String description) {
        InvocableHandlerMethod handlerMethod = this.getHandlerMethod();
        StringBuilder sb = new StringBuilder(description).append('\n').append("Endpoint handler details:\n").append("Method [").append(handlerMethod.getMethod()).append("]\n").append("Bean [").append(handlerMethod.getBean()).append("]\n");
        return sb.toString();
    }
}

