/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Connection
 *  javax.jms.ConnectionFactory
 *  javax.jms.Destination
 *  javax.jms.ExceptionListener
 *  javax.jms.JMSException
 *  javax.jms.Message
 *  javax.jms.MessageConsumer
 *  javax.jms.Session
 *  org.springframework.lang.Nullable
 *  org.springframework.transaction.support.TransactionSynchronizationManager
 *  org.springframework.util.Assert
 */
package org.springframework.jms.listener;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import org.springframework.jms.listener.AbstractMessageListenerContainer;
import org.springframework.jms.listener.LocallyExposedJmsResourceHolder;
import org.springframework.jms.support.JmsUtils;
import org.springframework.lang.Nullable;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

public class SimpleMessageListenerContainer
extends AbstractMessageListenerContainer
implements ExceptionListener {
    private boolean connectLazily = false;
    private boolean recoverOnException = true;
    private int concurrentConsumers = 1;
    @Nullable
    private Executor taskExecutor;
    @Nullable
    private Set<Session> sessions;
    @Nullable
    private Set<MessageConsumer> consumers;
    private final Object consumersMonitor = new Object();

    public void setConnectLazily(boolean connectLazily) {
        this.connectLazily = connectLazily;
    }

    public void setRecoverOnException(boolean recoverOnException) {
        this.recoverOnException = recoverOnException;
    }

    @Override
    public void setConcurrency(String concurrency) {
        try {
            int separatorIndex = concurrency.indexOf(45);
            if (separatorIndex != -1) {
                this.setConcurrentConsumers(Integer.parseInt(concurrency.substring(separatorIndex + 1)));
            } else {
                this.setConcurrentConsumers(Integer.parseInt(concurrency));
            }
        }
        catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid concurrency value [" + concurrency + "]: only single maximum integer (e.g. \"5\") and minimum-maximum combo (e.g. \"3-5\") supported. Note that SimpleMessageListenerContainer will effectively ignore the minimum value and always keep a fixed number of consumers according to the maximum value.");
        }
    }

    public void setConcurrentConsumers(int concurrentConsumers) {
        Assert.isTrue((concurrentConsumers > 0 ? 1 : 0) != 0, (String)"'concurrentConsumers' value must be at least 1 (one)");
        this.concurrentConsumers = concurrentConsumers;
    }

    public void setTaskExecutor(Executor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @Override
    protected void validateConfiguration() {
        super.validateConfiguration();
        if (this.isSubscriptionDurable() && this.concurrentConsumers != 1) {
            throw new IllegalArgumentException("Only 1 concurrent consumer supported for durable subscription");
        }
    }

    @Override
    protected final boolean sharedConnectionEnabled() {
        return true;
    }

    @Override
    protected void doInitialize() throws JMSException {
        if (!this.connectLazily) {
            try {
                this.establishSharedConnection();
            }
            catch (JMSException ex) {
                this.logger.debug((Object)"Could not connect on initialization - registering message consumers lazily", (Throwable)ex);
                return;
            }
            this.initializeConsumers();
        }
    }

    @Override
    protected void doStart() throws JMSException {
        super.doStart();
        this.initializeConsumers();
    }

    @Override
    protected void prepareSharedConnection(Connection connection) throws JMSException {
        super.prepareSharedConnection(connection);
        connection.setExceptionListener((ExceptionListener)this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void onException(JMSException ex) {
        this.invokeExceptionListener(ex);
        if (this.recoverOnException) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("Trying to recover from JMS Connection exception: " + (Object)((Object)ex)));
            }
            try {
                Object object = this.consumersMonitor;
                synchronized (object) {
                    this.sessions = null;
                    this.consumers = null;
                }
                this.refreshSharedConnection();
                this.initializeConsumers();
                this.logger.debug((Object)"Successfully refreshed JMS Connection");
            }
            catch (JMSException recoverEx) {
                this.logger.debug((Object)"Failed to recover JMS Connection", (Throwable)recoverEx);
                this.logger.error((Object)"Encountered non-recoverable JMSException", (Throwable)ex);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void initializeConsumers() throws JMSException {
        Object object = this.consumersMonitor;
        synchronized (object) {
            if (this.consumers == null) {
                this.sessions = new HashSet<Session>(this.concurrentConsumers);
                this.consumers = new HashSet<MessageConsumer>(this.concurrentConsumers);
                Connection con = this.getSharedConnection();
                for (int i = 0; i < this.concurrentConsumers; ++i) {
                    Session session = this.createSession(con);
                    MessageConsumer consumer = this.createListenerConsumer(session);
                    this.sessions.add(session);
                    this.consumers.add(consumer);
                }
            }
        }
    }

    protected MessageConsumer createListenerConsumer(Session session) throws JMSException {
        Destination destination = this.getDestination();
        if (destination == null) {
            String destinationName = this.getDestinationName();
            Assert.state((destinationName != null ? 1 : 0) != 0, (String)"No destination set");
            destination = this.resolveDestinationName(session, destinationName);
        }
        MessageConsumer consumer = this.createConsumer(session, destination);
        if (this.taskExecutor != null) {
            consumer.setMessageListener(message -> this.taskExecutor.execute(() -> this.processMessage(message, session)));
        } else {
            consumer.setMessageListener(message -> this.processMessage(message, session));
        }
        return consumer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void processMessage(Message message, Session session) {
        boolean exposeResource;
        ConnectionFactory connectionFactory = this.getConnectionFactory();
        boolean bl = exposeResource = connectionFactory != null && this.isExposeListenerSession();
        if (exposeResource) {
            TransactionSynchronizationManager.bindResource((Object)connectionFactory, (Object)((Object)new LocallyExposedJmsResourceHolder(session)));
        }
        try {
            this.executeListener(session, message);
        }
        finally {
            if (exposeResource) {
                TransactionSynchronizationManager.unbindResource((Object)this.getConnectionFactory());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void doShutdown() throws JMSException {
        Object object = this.consumersMonitor;
        synchronized (object) {
            if (this.consumers != null) {
                this.logger.debug((Object)"Closing JMS MessageConsumers");
                for (MessageConsumer consumer : this.consumers) {
                    JmsUtils.closeMessageConsumer(consumer);
                }
                if (this.sessions != null) {
                    this.logger.debug((Object)"Closing JMS Sessions");
                    for (Session session : this.sessions) {
                        JmsUtils.closeSession(session);
                    }
                }
            }
        }
    }
}

