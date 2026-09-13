/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.BytesMessage
 *  javax.jms.Destination
 *  javax.jms.InvalidDestinationException
 *  javax.jms.JMSException
 *  javax.jms.Message
 *  javax.jms.MessageListener
 *  javax.jms.MessageProducer
 *  javax.jms.Session
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.util.Assert
 */
package org.springframework.jms.listener.adapter;

import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.jms.listener.adapter.JmsResponse;
import org.springframework.jms.listener.adapter.ReplyFailureException;
import org.springframework.jms.support.JmsHeaderMapper;
import org.springframework.jms.support.JmsUtils;
import org.springframework.jms.support.QosSettings;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessagingMessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.jms.support.converter.SmartMessageConverter;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.Assert;

public abstract class AbstractAdaptableMessageListener
implements MessageListener,
SessionAwareMessageListener<javax.jms.Message> {
    protected final Log logger = LogFactory.getLog(this.getClass());
    @Nullable
    private Object defaultResponseDestination;
    private DestinationResolver destinationResolver = new DynamicDestinationResolver();
    @Nullable
    private MessageConverter messageConverter = new SimpleMessageConverter();
    private final MessagingMessageConverterAdapter messagingMessageConverter = new MessagingMessageConverterAdapter();
    @Nullable
    private QosSettings responseQosSettings;

    public void setDefaultResponseDestination(Destination destination) {
        this.defaultResponseDestination = destination;
    }

    public void setDefaultResponseQueueName(String destinationName) {
        this.defaultResponseDestination = new DestinationNameHolder(destinationName, false);
    }

    public void setDefaultResponseTopicName(String destinationName) {
        this.defaultResponseDestination = new DestinationNameHolder(destinationName, true);
    }

    public void setDestinationResolver(DestinationResolver destinationResolver) {
        Assert.notNull((Object)destinationResolver, (String)"DestinationResolver must not be null");
        this.destinationResolver = destinationResolver;
    }

    protected DestinationResolver getDestinationResolver() {
        return this.destinationResolver;
    }

    public void setMessageConverter(@Nullable MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    @Nullable
    protected MessageConverter getMessageConverter() {
        return this.messageConverter;
    }

    public void setHeaderMapper(JmsHeaderMapper headerMapper) {
        Assert.notNull((Object)headerMapper, (String)"HeaderMapper must not be null");
        this.messagingMessageConverter.setHeaderMapper(headerMapper);
    }

    protected final MessagingMessageConverter getMessagingMessageConverter() {
        return this.messagingMessageConverter;
    }

    public void setResponseQosSettings(@Nullable QosSettings responseQosSettings) {
        this.responseQosSettings = responseQosSettings;
    }

    @Nullable
    protected QosSettings getResponseQosSettings() {
        return this.responseQosSettings;
    }

    public void onMessage(javax.jms.Message message) {
        try {
            this.onMessage(message, (Session)null);
        }
        catch (Throwable ex) {
            this.handleListenerException(ex);
        }
    }

    @Override
    public abstract void onMessage(javax.jms.Message var1, @Nullable Session var2) throws JMSException;

    protected void handleListenerException(Throwable ex) {
        this.logger.error((Object)"Listener execution failed", ex);
    }

    protected Object extractMessage(javax.jms.Message message) {
        try {
            MessageConverter converter = this.getMessageConverter();
            if (converter != null) {
                return converter.fromMessage(message);
            }
            return message;
        }
        catch (JMSException ex) {
            throw new MessageConversionException("Could not convert JMS message", ex);
        }
    }

    protected void handleResult(Object result, javax.jms.Message request, @Nullable Session session) {
        if (session != null) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("Listener method returned result [" + result + "] - generating response message for it"));
            }
            try {
                javax.jms.Message response = this.buildMessage(session, result);
                this.postProcessResponse(request, response);
                Destination destination = this.getResponseDestination(request, response, session, result);
                this.sendResponse(session, destination, response);
            }
            catch (Exception ex) {
                throw new ReplyFailureException("Failed to send reply with payload [" + result + "]", ex);
            }
        } else if (this.logger.isWarnEnabled()) {
            this.logger.warn((Object)("Listener method returned result [" + result + "]: not generating response message for it because of no JMS Session given"));
        }
    }

    protected javax.jms.Message buildMessage(Session session, Object result) throws JMSException {
        Object content = this.preProcessResponse(result instanceof JmsResponse ? ((JmsResponse)result).getResponse() : result);
        MessageConverter converter = this.getMessageConverter();
        if (converter != null) {
            if (content instanceof Message) {
                return this.messagingMessageConverter.toMessage(content, session);
            }
            return converter.toMessage(content, session);
        }
        if (!(content instanceof javax.jms.Message)) {
            throw new MessageConversionException("No MessageConverter specified - cannot handle message [" + content + "]");
        }
        return (javax.jms.Message)content;
    }

    protected Object preProcessResponse(Object result) {
        return result;
    }

    protected void postProcessResponse(javax.jms.Message request, javax.jms.Message response) throws JMSException {
        String correlation = request.getJMSCorrelationID();
        if (correlation == null) {
            correlation = request.getJMSMessageID();
        }
        response.setJMSCorrelationID(correlation);
    }

    private Destination getResponseDestination(javax.jms.Message request, javax.jms.Message response, Session session, Object result) throws JMSException {
        JmsResponse jmsResponse;
        Destination destination;
        if (result instanceof JmsResponse && (destination = (jmsResponse = (JmsResponse)result).resolveDestination(this.getDestinationResolver(), session)) != null) {
            return destination;
        }
        return this.getResponseDestination(request, response, session);
    }

    protected Destination getResponseDestination(javax.jms.Message request, javax.jms.Message response, Session session) throws JMSException {
        Destination replyTo = request.getJMSReplyTo();
        if (replyTo == null && (replyTo = this.resolveDefaultResponseDestination(session)) == null) {
            throw new InvalidDestinationException("Cannot determine response destination: Request message does not contain reply-to destination, and no default response destination set.");
        }
        return replyTo;
    }

    @Nullable
    protected Destination resolveDefaultResponseDestination(Session session) throws JMSException {
        if (this.defaultResponseDestination instanceof Destination) {
            return (Destination)this.defaultResponseDestination;
        }
        if (this.defaultResponseDestination instanceof DestinationNameHolder) {
            DestinationNameHolder nameHolder = (DestinationNameHolder)this.defaultResponseDestination;
            return this.getDestinationResolver().resolveDestinationName(session, nameHolder.name, nameHolder.isTopic);
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void sendResponse(Session session, Destination destination, javax.jms.Message response) throws JMSException {
        MessageProducer producer = session.createProducer(destination);
        try {
            this.postProcessProducer(producer, response);
            QosSettings settings = this.getResponseQosSettings();
            if (settings != null) {
                producer.send(response, settings.getDeliveryMode(), settings.getPriority(), settings.getTimeToLive());
            } else {
                producer.send(response);
            }
        }
        finally {
            JmsUtils.closeMessageProducer(producer);
        }
    }

    protected void postProcessProducer(MessageProducer producer, javax.jms.Message response) throws JMSException {
    }

    private static class DestinationNameHolder {
        public final String name;
        public final boolean isTopic;

        public DestinationNameHolder(String name, boolean isTopic) {
            this.name = name;
            this.isTopic = isTopic;
        }
    }

    private class MessagingMessageConverterAdapter
    extends MessagingMessageConverter {
        private MessagingMessageConverterAdapter() {
        }

        @Override
        public Object fromMessage(javax.jms.Message message) throws JMSException, MessageConversionException {
            return new LazyResolutionMessage(message);
        }

        @Override
        protected Object extractPayload(javax.jms.Message message) throws JMSException {
            Object payload = AbstractAdaptableMessageListener.this.extractMessage(message);
            if (message instanceof BytesMessage) {
                try {
                    ((BytesMessage)message).reset();
                }
                catch (JMSException ex) {
                    AbstractAdaptableMessageListener.this.logger.debug((Object)"Failed to reset BytesMessage after payload extraction", (Throwable)ex);
                }
            }
            return payload;
        }

        @Override
        protected javax.jms.Message createMessageForPayload(Object payload, Session session, @Nullable Object conversionHint) throws JMSException {
            MessageConverter converter = AbstractAdaptableMessageListener.this.getMessageConverter();
            if (converter == null) {
                throw new IllegalStateException("No message converter, cannot handle '" + payload + "'");
            }
            if (converter instanceof SmartMessageConverter) {
                return ((SmartMessageConverter)converter).toMessage(payload, session, conversionHint);
            }
            return converter.toMessage(payload, session);
        }

        protected class LazyResolutionMessage
        implements Message<Object> {
            private final javax.jms.Message message;
            @Nullable
            private Object payload;
            @Nullable
            private MessageHeaders headers;

            public LazyResolutionMessage(javax.jms.Message message) {
                this.message = message;
            }

            public Object getPayload() {
                if (this.payload == null) {
                    try {
                        this.payload = this.unwrapPayload();
                    }
                    catch (JMSException ex) {
                        throw new MessageConversionException("Failed to extract payload from [" + this.message + "]", ex);
                    }
                }
                return this.payload;
            }

            private Object unwrapPayload() throws JMSException {
                Object payload = MessagingMessageConverterAdapter.this.extractPayload(this.message);
                if (payload instanceof Message) {
                    return ((Message)payload).getPayload();
                }
                return payload;
            }

            public MessageHeaders getHeaders() {
                if (this.headers == null) {
                    this.headers = MessagingMessageConverterAdapter.this.extractHeaders(this.message);
                }
                return this.headers;
            }
        }
    }
}

