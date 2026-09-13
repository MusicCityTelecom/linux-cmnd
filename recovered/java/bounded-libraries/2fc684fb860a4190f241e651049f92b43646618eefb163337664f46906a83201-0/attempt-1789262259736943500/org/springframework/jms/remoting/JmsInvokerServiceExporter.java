/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.JMSException
 *  javax.jms.Message
 *  javax.jms.MessageFormatException
 *  javax.jms.MessageProducer
 *  javax.jms.Session
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.lang.Nullable
 *  org.springframework.remoting.support.RemoteInvocation
 *  org.springframework.remoting.support.RemoteInvocationBasedExporter
 *  org.springframework.remoting.support.RemoteInvocationResult
 */
package org.springframework.jms.remoting;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageFormatException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.jms.support.JmsUtils;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationBasedExporter;
import org.springframework.remoting.support.RemoteInvocationResult;

@Deprecated
public class JmsInvokerServiceExporter
extends RemoteInvocationBasedExporter
implements SessionAwareMessageListener<Message>,
InitializingBean {
    private MessageConverter messageConverter = new SimpleMessageConverter();
    private boolean ignoreInvalidRequests = true;
    @Nullable
    private Object proxy;

    public void setMessageConverter(@Nullable MessageConverter messageConverter) {
        this.messageConverter = messageConverter != null ? messageConverter : new SimpleMessageConverter();
    }

    public void setIgnoreInvalidRequests(boolean ignoreInvalidRequests) {
        this.ignoreInvalidRequests = ignoreInvalidRequests;
    }

    public void afterPropertiesSet() {
        this.proxy = this.getProxyForService();
    }

    @Override
    public void onMessage(Message requestMessage, Session session) throws JMSException {
        RemoteInvocation invocation = this.readRemoteInvocation(requestMessage);
        if (invocation != null) {
            RemoteInvocationResult result = this.invokeAndCreateResult(invocation, this.proxy);
            this.writeRemoteInvocationResult(requestMessage, session, result);
        }
    }

    @Nullable
    protected RemoteInvocation readRemoteInvocation(Message requestMessage) throws JMSException {
        Object content = this.messageConverter.fromMessage(requestMessage);
        if (content instanceof RemoteInvocation) {
            return (RemoteInvocation)content;
        }
        return this.onInvalidRequest(requestMessage);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void writeRemoteInvocationResult(Message requestMessage, Session session, RemoteInvocationResult result) throws JMSException {
        Message response = this.createResponseMessage(requestMessage, session, result);
        MessageProducer producer = session.createProducer(requestMessage.getJMSReplyTo());
        try {
            producer.send(response);
        }
        finally {
            JmsUtils.closeMessageProducer(producer);
        }
    }

    protected Message createResponseMessage(Message request, Session session, RemoteInvocationResult result) throws JMSException {
        Message response = this.messageConverter.toMessage(result, session);
        String correlation = request.getJMSCorrelationID();
        if (correlation == null) {
            correlation = request.getJMSMessageID();
        }
        response.setJMSCorrelationID(correlation);
        return response;
    }

    @Nullable
    protected RemoteInvocation onInvalidRequest(Message requestMessage) throws JMSException {
        if (this.ignoreInvalidRequests) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("Invalid request message will be discarded: " + requestMessage));
            }
            return null;
        }
        throw new MessageFormatException("Invalid request message: " + requestMessage);
    }
}

