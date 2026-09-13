/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.ConnectionFactory
 *  javax.jms.Destination
 *  javax.jms.JMSException
 *  javax.jms.Message
 *  javax.jms.Session
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessagingException
 *  org.springframework.messaging.converter.MessageConversionException
 *  org.springframework.messaging.core.AbstractMessagingTemplate
 *  org.springframework.messaging.core.DestinationResolutionException
 *  org.springframework.messaging.core.MessagePostProcessor
 *  org.springframework.util.Assert
 */
package org.springframework.jms.core;

import java.util.Map;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.InvalidDestinationException;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsMessageOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessagingMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.core.AbstractMessagingTemplate;
import org.springframework.messaging.core.DestinationResolutionException;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.util.Assert;

public class JmsMessagingTemplate
extends AbstractMessagingTemplate<Destination>
implements JmsMessageOperations,
InitializingBean {
    @Nullable
    private JmsTemplate jmsTemplate;
    private MessageConverter jmsMessageConverter = new MessagingMessageConverter();
    private boolean converterSet;
    @Nullable
    private String defaultDestinationName;

    public JmsMessagingTemplate() {
    }

    public JmsMessagingTemplate(ConnectionFactory connectionFactory) {
        this.jmsTemplate = new JmsTemplate(connectionFactory);
    }

    public JmsMessagingTemplate(JmsTemplate jmsTemplate) {
        Assert.notNull((Object)jmsTemplate, (String)"JmsTemplate must not be null");
        this.jmsTemplate = jmsTemplate;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        if (this.jmsTemplate != null) {
            this.jmsTemplate.setConnectionFactory(connectionFactory);
        } else {
            this.jmsTemplate = new JmsTemplate(connectionFactory);
        }
    }

    @Nullable
    public ConnectionFactory getConnectionFactory() {
        return this.jmsTemplate != null ? this.jmsTemplate.getConnectionFactory() : null;
    }

    public void setJmsTemplate(@Nullable JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Nullable
    public JmsTemplate getJmsTemplate() {
        return this.jmsTemplate;
    }

    public void setJmsMessageConverter(MessageConverter jmsMessageConverter) {
        Assert.notNull((Object)jmsMessageConverter, (String)"MessageConverter must not be null");
        this.jmsMessageConverter = jmsMessageConverter;
        this.converterSet = true;
    }

    public MessageConverter getJmsMessageConverter() {
        return this.jmsMessageConverter;
    }

    public void setDefaultDestinationName(@Nullable String defaultDestinationName) {
        this.defaultDestinationName = defaultDestinationName;
    }

    @Nullable
    public String getDefaultDestinationName() {
        return this.defaultDestinationName;
    }

    public void afterPropertiesSet() {
        Assert.notNull((Object)this.jmsTemplate, (String)"Property 'connectionFactory' or 'jmsTemplate' is required");
        if (!this.converterSet && this.jmsTemplate.getMessageConverter() != null) {
            ((MessagingMessageConverter)this.jmsMessageConverter).setPayloadConverter(this.jmsTemplate.getMessageConverter());
        }
    }

    private JmsTemplate obtainJmsTemplate() {
        Assert.state((this.jmsTemplate != null ? 1 : 0) != 0, (String)"No JmsTemplate set");
        return this.jmsTemplate;
    }

    public void send(Message<?> message) {
        Destination defaultDestination = (Destination)this.getDefaultDestination();
        if (defaultDestination != null) {
            this.send(defaultDestination, message);
        } else {
            this.send(this.getRequiredDefaultDestinationName(), message);
        }
    }

    public void convertAndSend(Object payload) throws MessagingException {
        this.convertAndSend(payload, null);
    }

    public void convertAndSend(Object payload, @Nullable MessagePostProcessor postProcessor) throws MessagingException {
        Destination defaultDestination = (Destination)this.getDefaultDestination();
        if (defaultDestination != null) {
            this.convertAndSend(defaultDestination, payload, postProcessor);
        } else {
            this.convertAndSend(this.getRequiredDefaultDestinationName(), payload, postProcessor);
        }
    }

    @Override
    public void send(String destinationName, Message<?> message) throws MessagingException {
        this.doSend(destinationName, message);
    }

    @Override
    public void convertAndSend(String destinationName, Object payload) throws MessagingException {
        this.convertAndSend(destinationName, payload, (Map<String, Object>)null);
    }

    @Override
    public void convertAndSend(String destinationName, Object payload, @Nullable Map<String, Object> headers) throws MessagingException {
        this.convertAndSend(destinationName, payload, headers, null);
    }

    @Override
    public void convertAndSend(String destinationName, Object payload, @Nullable MessagePostProcessor postProcessor) throws MessagingException {
        this.convertAndSend(destinationName, payload, null, postProcessor);
    }

    @Override
    public void convertAndSend(String destinationName, Object payload, @Nullable Map<String, Object> headers, @Nullable MessagePostProcessor postProcessor) throws MessagingException {
        Message message = this.doConvert(payload, headers, postProcessor);
        this.send(destinationName, message);
    }

    @Nullable
    public Message<?> receive() {
        Destination defaultDestination = (Destination)this.getDefaultDestination();
        if (defaultDestination != null) {
            return this.receive(defaultDestination);
        }
        return this.receive(this.getRequiredDefaultDestinationName());
    }

    @Nullable
    public <T> T receiveAndConvert(Class<T> targetClass) {
        Destination defaultDestination = (Destination)this.getDefaultDestination();
        if (defaultDestination != null) {
            return (T)this.receiveAndConvert(defaultDestination, targetClass);
        }
        return this.receiveAndConvert(this.getRequiredDefaultDestinationName(), targetClass);
    }

    @Override
    @Nullable
    public Message<?> receive(String destinationName) throws MessagingException {
        return this.doReceive(destinationName);
    }

    @Override
    @Nullable
    public <T> T receiveAndConvert(String destinationName, Class<T> targetClass) throws MessagingException {
        Message<?> message = this.doReceive(destinationName);
        if (message != null) {
            return (T)this.doConvert(message, targetClass);
        }
        return null;
    }

    @Nullable
    public Message<?> sendAndReceive(Message<?> requestMessage) {
        Destination defaultDestination = (Destination)this.getDefaultDestination();
        if (defaultDestination != null) {
            return this.sendAndReceive(defaultDestination, requestMessage);
        }
        return this.sendAndReceive(this.getRequiredDefaultDestinationName(), requestMessage);
    }

    @Override
    @Nullable
    public Message<?> sendAndReceive(String destinationName, Message<?> requestMessage) throws MessagingException {
        return this.doSendAndReceive(destinationName, requestMessage);
    }

    @Override
    @Nullable
    public <T> T convertSendAndReceive(String destinationName, Object request, Class<T> targetClass) throws MessagingException {
        return this.convertSendAndReceive(destinationName, request, null, targetClass);
    }

    @Nullable
    public <T> T convertSendAndReceive(Object request, Class<T> targetClass) {
        return this.convertSendAndReceive(request, targetClass, null);
    }

    @Override
    @Nullable
    public <T> T convertSendAndReceive(String destinationName, Object request, @Nullable Map<String, Object> headers, Class<T> targetClass) throws MessagingException {
        return this.convertSendAndReceive(destinationName, request, headers, targetClass, null);
    }

    @Nullable
    public <T> T convertSendAndReceive(Object request, Class<T> targetClass, @Nullable MessagePostProcessor postProcessor) {
        Destination defaultDestination = (Destination)this.getDefaultDestination();
        if (defaultDestination != null) {
            return (T)this.convertSendAndReceive(defaultDestination, request, targetClass, postProcessor);
        }
        return this.convertSendAndReceive(this.getRequiredDefaultDestinationName(), request, targetClass, postProcessor);
    }

    @Override
    @Nullable
    public <T> T convertSendAndReceive(String destinationName, Object request, Class<T> targetClass, @Nullable MessagePostProcessor requestPostProcessor) throws MessagingException {
        return this.convertSendAndReceive(destinationName, request, null, targetClass, requestPostProcessor);
    }

    @Override
    @Nullable
    public <T> T convertSendAndReceive(String destinationName, Object request, @Nullable Map<String, Object> headers, Class<T> targetClass, @Nullable MessagePostProcessor postProcessor) {
        Message requestMessage = this.doConvert(request, headers, postProcessor);
        Message<?> replyMessage = this.sendAndReceive(destinationName, requestMessage);
        return (T)(replyMessage != null ? this.getMessageConverter().fromMessage(replyMessage, targetClass) : null);
    }

    protected void doSend(Destination destination, Message<?> message) {
        try {
            this.obtainJmsTemplate().send(destination, (MessageCreator)this.createMessageCreator(message));
        }
        catch (JmsException ex) {
            throw this.convertJmsException(ex);
        }
    }

    protected void doSend(String destinationName, Message<?> message) {
        try {
            this.obtainJmsTemplate().send(destinationName, (MessageCreator)this.createMessageCreator(message));
        }
        catch (JmsException ex) {
            throw this.convertJmsException(ex);
        }
    }

    @Nullable
    protected Message<?> doReceive(Destination destination) {
        try {
            javax.jms.Message jmsMessage = this.obtainJmsTemplate().receive(destination);
            return this.convertJmsMessage(jmsMessage);
        }
        catch (JmsException ex) {
            throw this.convertJmsException(ex);
        }
    }

    @Nullable
    protected Message<?> doReceive(String destinationName) {
        try {
            javax.jms.Message jmsMessage = this.obtainJmsTemplate().receive(destinationName);
            return this.convertJmsMessage(jmsMessage);
        }
        catch (JmsException ex) {
            throw this.convertJmsException(ex);
        }
    }

    @Nullable
    protected Message<?> doSendAndReceive(Destination destination, Message<?> requestMessage) {
        try {
            javax.jms.Message jmsMessage = this.obtainJmsTemplate().sendAndReceive(destination, (MessageCreator)this.createMessageCreator(requestMessage));
            return this.convertJmsMessage(jmsMessage);
        }
        catch (JmsException ex) {
            throw this.convertJmsException(ex);
        }
    }

    @Nullable
    protected Message<?> doSendAndReceive(String destinationName, Message<?> requestMessage) {
        try {
            javax.jms.Message jmsMessage = this.obtainJmsTemplate().sendAndReceive(destinationName, (MessageCreator)this.createMessageCreator(requestMessage));
            return this.convertJmsMessage(jmsMessage);
        }
        catch (JmsException ex) {
            throw this.convertJmsException(ex);
        }
    }

    private MessagingMessageCreator createMessageCreator(Message<?> message) {
        return new MessagingMessageCreator(message, this.getJmsMessageConverter());
    }

    protected String getRequiredDefaultDestinationName() {
        String name = this.getDefaultDestinationName();
        if (name == null) {
            throw new IllegalStateException("No 'defaultDestination' or 'defaultDestinationName' specified. Check configuration of JmsMessagingTemplate.");
        }
        return name;
    }

    @Nullable
    protected Message<?> convertJmsMessage(@Nullable javax.jms.Message message) {
        if (message == null) {
            return null;
        }
        try {
            return (Message)this.getJmsMessageConverter().fromMessage(message);
        }
        catch (Exception ex) {
            throw new MessageConversionException("Could not convert '" + message + "'", (Throwable)ex);
        }
    }

    protected MessagingException convertJmsException(JmsException ex) {
        if (ex instanceof org.springframework.jms.support.destination.DestinationResolutionException || ex instanceof InvalidDestinationException) {
            return new DestinationResolutionException(ex.getMessage(), (Throwable)((Object)ex));
        }
        if (ex instanceof org.springframework.jms.support.converter.MessageConversionException) {
            return new MessageConversionException(ex.getMessage(), (Throwable)((Object)ex));
        }
        return new MessagingException(ex.getMessage(), (Throwable)((Object)ex));
    }

    private static class MessagingMessageCreator
    implements MessageCreator {
        private final Message<?> message;
        private final MessageConverter messageConverter;

        public MessagingMessageCreator(Message<?> message, MessageConverter messageConverter) {
            this.message = message;
            this.messageConverter = messageConverter;
        }

        @Override
        public javax.jms.Message createMessage(Session session) throws JMSException {
            try {
                return this.messageConverter.toMessage(this.message, session);
            }
            catch (Exception ex) {
                throw new MessageConversionException("Could not convert '" + this.message + "'", (Throwable)ex);
            }
        }
    }
}

