/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Connection
 *  javax.jms.ConnectionFactory
 *  javax.jms.Destination
 *  javax.jms.IllegalStateException
 *  javax.jms.JMSException
 *  javax.jms.Message
 *  javax.jms.MessageConsumer
 *  javax.jms.MessageFormatException
 *  javax.jms.MessageProducer
 *  javax.jms.Queue
 *  javax.jms.Session
 *  javax.jms.TemporaryQueue
 *  org.aopalliance.intercept.MethodInterceptor
 *  org.aopalliance.intercept.MethodInvocation
 *  org.springframework.aop.support.AopUtils
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.lang.Nullable
 *  org.springframework.remoting.RemoteAccessException
 *  org.springframework.remoting.RemoteInvocationFailureException
 *  org.springframework.remoting.RemoteTimeoutException
 *  org.springframework.remoting.support.DefaultRemoteInvocationFactory
 *  org.springframework.remoting.support.RemoteInvocation
 *  org.springframework.remoting.support.RemoteInvocationFactory
 *  org.springframework.remoting.support.RemoteInvocationResult
 *  org.springframework.util.Assert
 */
package org.springframework.jms.remoting;

import java.lang.reflect.Method;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.IllegalStateException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageFormatException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.connection.ConnectionFactoryUtils;
import org.springframework.jms.support.JmsUtils;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.lang.Nullable;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.remoting.RemoteInvocationFailureException;
import org.springframework.remoting.RemoteTimeoutException;
import org.springframework.remoting.support.DefaultRemoteInvocationFactory;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationFactory;
import org.springframework.remoting.support.RemoteInvocationResult;
import org.springframework.util.Assert;

@Deprecated
public class JmsInvokerClientInterceptor
implements MethodInterceptor,
InitializingBean {
    @Nullable
    private ConnectionFactory connectionFactory;
    @Nullable
    private Object queue;
    private DestinationResolver destinationResolver = new DynamicDestinationResolver();
    private RemoteInvocationFactory remoteInvocationFactory = new DefaultRemoteInvocationFactory();
    private MessageConverter messageConverter = new SimpleMessageConverter();
    private long receiveTimeout = 0L;

    public void setConnectionFactory(@Nullable ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Nullable
    protected ConnectionFactory getConnectionFactory() {
        return this.connectionFactory;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public void setQueueName(String queueName) {
        this.queue = queueName;
    }

    public void setDestinationResolver(@Nullable DestinationResolver destinationResolver) {
        this.destinationResolver = destinationResolver != null ? destinationResolver : new DynamicDestinationResolver();
    }

    public void setRemoteInvocationFactory(@Nullable RemoteInvocationFactory remoteInvocationFactory) {
        this.remoteInvocationFactory = remoteInvocationFactory != null ? remoteInvocationFactory : new DefaultRemoteInvocationFactory();
    }

    public void setMessageConverter(@Nullable MessageConverter messageConverter) {
        this.messageConverter = messageConverter != null ? messageConverter : new SimpleMessageConverter();
    }

    public void setReceiveTimeout(long receiveTimeout) {
        this.receiveTimeout = receiveTimeout;
    }

    protected long getReceiveTimeout() {
        return this.receiveTimeout;
    }

    public void afterPropertiesSet() {
        if (this.getConnectionFactory() == null) {
            throw new IllegalArgumentException("Property 'connectionFactory' is required");
        }
        if (this.queue == null) {
            throw new IllegalArgumentException("'queue' or 'queueName' is required");
        }
    }

    @Nullable
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        RemoteInvocationResult result;
        if (AopUtils.isToStringMethod((Method)methodInvocation.getMethod())) {
            return "JMS invoker proxy for queue [" + this.queue + "]";
        }
        RemoteInvocation invocation = this.createRemoteInvocation(methodInvocation);
        try {
            result = this.executeRequest(invocation);
        }
        catch (JMSException ex) {
            throw this.convertJmsInvokerAccessException(ex);
        }
        try {
            return this.recreateRemoteInvocationResult(result);
        }
        catch (Throwable ex) {
            if (result.hasInvocationTargetException()) {
                throw ex;
            }
            throw new RemoteInvocationFailureException("Invocation of method [" + methodInvocation.getMethod() + "] failed in JMS invoker remote service at queue [" + this.queue + "]", ex);
        }
    }

    protected RemoteInvocation createRemoteInvocation(MethodInvocation methodInvocation) {
        return this.remoteInvocationFactory.createRemoteInvocation(methodInvocation);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected RemoteInvocationResult executeRequest(RemoteInvocation invocation) throws JMSException {
        Connection con = this.createConnection();
        Session session = null;
        try {
            session = this.createSession(con);
            Queue queueToUse = this.resolveQueue(session);
            Message requestMessage = this.createRequestMessage(session, invocation);
            con.start();
            Message responseMessage = this.doExecuteRequest(session, queueToUse, requestMessage);
            if (responseMessage != null) {
                RemoteInvocationResult remoteInvocationResult = this.extractInvocationResult(responseMessage);
                return remoteInvocationResult;
            }
            RemoteInvocationResult remoteInvocationResult = this.onReceiveTimeout(invocation);
            return remoteInvocationResult;
        }
        finally {
            JmsUtils.closeSession(session);
            ConnectionFactoryUtils.releaseConnection(con, this.getConnectionFactory(), true);
        }
    }

    protected Connection createConnection() throws JMSException {
        ConnectionFactory connectionFactory = this.getConnectionFactory();
        Assert.state((connectionFactory != null ? 1 : 0) != 0, (String)"No ConnectionFactory set");
        return connectionFactory.createConnection();
    }

    protected Session createSession(Connection con) throws JMSException {
        return con.createSession(false, 1);
    }

    protected Queue resolveQueue(Session session) throws JMSException {
        if (this.queue instanceof Queue) {
            return (Queue)this.queue;
        }
        if (this.queue instanceof String) {
            return this.resolveQueueName(session, (String)this.queue);
        }
        throw new IllegalStateException("Queue object [" + this.queue + "] is neither a [javax.jms.Queue] nor a queue name String");
    }

    protected Queue resolveQueueName(Session session, String queueName) throws JMSException {
        return (Queue)this.destinationResolver.resolveDestinationName(session, queueName, false);
    }

    protected Message createRequestMessage(Session session, RemoteInvocation invocation) throws JMSException {
        return this.messageConverter.toMessage(invocation, session);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    protected Message doExecuteRequest(Session session, Queue queue, Message requestMessage) throws JMSException {
        Message message;
        TemporaryQueue responseQueue = null;
        MessageProducer producer = null;
        MessageConsumer consumer = null;
        try {
            responseQueue = session.createTemporaryQueue();
            producer = session.createProducer((Destination)queue);
            consumer = session.createConsumer((Destination)responseQueue);
            requestMessage.setJMSReplyTo((Destination)responseQueue);
            producer.send(requestMessage);
            long timeout = this.getReceiveTimeout();
            message = timeout > 0L ? consumer.receive(timeout) : consumer.receive();
        }
        catch (Throwable throwable) {
            JmsUtils.closeMessageConsumer(consumer);
            JmsUtils.closeMessageProducer(producer);
            if (responseQueue != null) {
                responseQueue.delete();
            }
            throw throwable;
        }
        JmsUtils.closeMessageConsumer(consumer);
        JmsUtils.closeMessageProducer(producer);
        if (responseQueue != null) {
            responseQueue.delete();
        }
        return message;
    }

    protected RemoteInvocationResult extractInvocationResult(Message responseMessage) throws JMSException {
        Object content = this.messageConverter.fromMessage(responseMessage);
        if (content instanceof RemoteInvocationResult) {
            return (RemoteInvocationResult)content;
        }
        return this.onInvalidResponse(responseMessage);
    }

    protected RemoteInvocationResult onReceiveTimeout(RemoteInvocation invocation) {
        throw new RemoteTimeoutException("Receive timeout after " + this.receiveTimeout + " ms for " + invocation);
    }

    protected RemoteInvocationResult onInvalidResponse(Message responseMessage) throws JMSException {
        throw new MessageFormatException("Invalid response message: " + responseMessage);
    }

    @Nullable
    protected Object recreateRemoteInvocationResult(RemoteInvocationResult result) throws Throwable {
        return result.recreate();
    }

    protected RemoteAccessException convertJmsInvokerAccessException(JMSException ex) {
        return new RemoteAccessException("Could not access JMS invoker queue [" + this.queue + "]", (Throwable)ex);
    }
}

