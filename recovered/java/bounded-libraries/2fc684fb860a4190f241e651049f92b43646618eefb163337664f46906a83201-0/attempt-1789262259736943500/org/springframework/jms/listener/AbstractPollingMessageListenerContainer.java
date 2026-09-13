/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Connection
 *  javax.jms.Destination
 *  javax.jms.JMSException
 *  javax.jms.Message
 *  javax.jms.MessageConsumer
 *  javax.jms.Session
 *  org.springframework.lang.Nullable
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.TransactionDefinition
 *  org.springframework.transaction.TransactionException
 *  org.springframework.transaction.TransactionStatus
 *  org.springframework.transaction.support.DefaultTransactionDefinition
 *  org.springframework.transaction.support.ResourceTransactionManager
 *  org.springframework.transaction.support.TransactionSynchronizationManager
 *  org.springframework.transaction.support.TransactionSynchronizationUtils
 *  org.springframework.util.Assert
 */
package org.springframework.jms.listener;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import org.springframework.jms.connection.ConnectionFactoryUtils;
import org.springframework.jms.connection.JmsResourceHolder;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.listener.AbstractMessageListenerContainer;
import org.springframework.jms.listener.LocallyExposedJmsResourceHolder;
import org.springframework.jms.support.JmsUtils;
import org.springframework.lang.Nullable;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.ResourceTransactionManager;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionSynchronizationUtils;
import org.springframework.util.Assert;

public abstract class AbstractPollingMessageListenerContainer
extends AbstractMessageListenerContainer {
    public static final long DEFAULT_RECEIVE_TIMEOUT = 1000L;
    private final MessageListenerContainerResourceFactory transactionalResourceFactory = new MessageListenerContainerResourceFactory();
    private boolean sessionTransactedCalled = false;
    @Nullable
    private PlatformTransactionManager transactionManager;
    private DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
    private long receiveTimeout = 1000L;

    @Override
    public void setSessionTransacted(boolean sessionTransacted) {
        super.setSessionTransacted(sessionTransacted);
        this.sessionTransactedCalled = true;
    }

    public void setTransactionManager(@Nullable PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Nullable
    protected final PlatformTransactionManager getTransactionManager() {
        return this.transactionManager;
    }

    public void setTransactionName(String transactionName) {
        this.transactionDefinition.setName(transactionName);
    }

    public void setTransactionTimeout(int transactionTimeout) {
        this.transactionDefinition.setTimeout(transactionTimeout);
    }

    public void setReceiveTimeout(long receiveTimeout) {
        this.receiveTimeout = receiveTimeout;
    }

    protected long getReceiveTimeout() {
        return this.receiveTimeout;
    }

    @Override
    public void initialize() {
        String beanName;
        if (!this.sessionTransactedCalled && this.transactionManager instanceof ResourceTransactionManager && !TransactionSynchronizationUtils.sameResourceFactory((ResourceTransactionManager)((ResourceTransactionManager)this.transactionManager), (Object)this.obtainConnectionFactory())) {
            super.setSessionTransacted(true);
        }
        if (this.transactionDefinition.getName() == null && (beanName = this.getBeanName()) != null) {
            this.transactionDefinition.setName(beanName);
        }
        super.initialize();
    }

    protected MessageConsumer createListenerConsumer(Session session) throws JMSException {
        Destination destination = this.getDestination();
        if (destination == null) {
            String destinationName = this.getDestinationName();
            Assert.state((destinationName != null ? 1 : 0) != 0, (String)"No destination set");
            destination = this.resolveDestinationName(session, destinationName);
        }
        return this.createConsumer(session, destination);
    }

    protected boolean receiveAndExecute(Object invoker, @Nullable Session session, @Nullable MessageConsumer consumer) throws JMSException {
        if (this.transactionManager != null) {
            boolean messageReceived;
            TransactionStatus status = this.transactionManager.getTransaction((TransactionDefinition)this.transactionDefinition);
            try {
                messageReceived = this.doReceiveAndExecute(invoker, session, consumer, status);
            }
            catch (Error | RuntimeException | JMSException ex) {
                this.rollbackOnException(this.transactionManager, status, ex);
                throw ex;
            }
            try {
                this.transactionManager.commit(status);
            }
            catch (TransactionException ex) {
                throw ex;
            }
            catch (RuntimeException ex) {
                this.handleListenerException(ex);
            }
            return messageReceived;
        }
        return this.doReceiveAndExecute(invoker, session, consumer, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected boolean doReceiveAndExecute(Object invoker, @Nullable Session session, @Nullable MessageConsumer consumer, @Nullable TransactionStatus status) throws JMSException {
        MessageConsumer consumerToUse;
        boolean transactional;
        Session sessionToUse;
        MessageConsumer consumerToClose;
        Session sessionToClose;
        Connection conToClose;
        block21: {
            boolean bl;
            conToClose = null;
            sessionToClose = null;
            consumerToClose = null;
            try {
                boolean exposeResource;
                Message message;
                sessionToUse = session;
                transactional = false;
                if (sessionToUse == null) {
                    sessionToUse = ConnectionFactoryUtils.doGetTransactionalSession(this.obtainConnectionFactory(), this.transactionalResourceFactory, true);
                    boolean bl2 = transactional = sessionToUse != null;
                }
                if (sessionToUse == null) {
                    Connection conToUse;
                    if (this.sharedConnectionEnabled()) {
                        conToUse = this.getSharedConnection();
                    } else {
                        conToClose = conToUse = this.createConnection();
                        conToUse.start();
                    }
                    sessionToClose = sessionToUse = this.createSession(conToUse);
                }
                if ((consumerToUse = consumer) == null) {
                    consumerToClose = consumerToUse = this.createListenerConsumer(sessionToUse);
                }
                if ((message = this.receiveMessage(consumerToUse)) == null) break block21;
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug((Object)("Received message of type [" + message.getClass() + "] from consumer [" + consumerToUse + "] of " + (transactional ? "transactional " : "") + "session [" + sessionToUse + "]"));
                }
                this.messageReceived(invoker, sessionToUse);
                boolean bl3 = exposeResource = !transactional && this.isExposeListenerSession() && !TransactionSynchronizationManager.hasResource((Object)this.obtainConnectionFactory());
                if (exposeResource) {
                    TransactionSynchronizationManager.bindResource((Object)this.obtainConnectionFactory(), (Object)((Object)new LocallyExposedJmsResourceHolder(sessionToUse)));
                }
                try {
                    this.doExecuteListener(sessionToUse, message);
                }
                catch (Throwable ex) {
                    if (status != null) {
                        if (this.logger.isDebugEnabled()) {
                            this.logger.debug((Object)("Rolling back transaction because of listener exception thrown: " + ex));
                        }
                        status.setRollbackOnly();
                    }
                    this.handleListenerException(ex);
                    if (ex instanceof JMSException) {
                        throw (JMSException)ex;
                    }
                }
                finally {
                    if (exposeResource) {
                        TransactionSynchronizationManager.unbindResource((Object)this.obtainConnectionFactory());
                    }
                }
                bl = true;
            }
            catch (Throwable throwable) {
                JmsUtils.closeMessageConsumer(consumerToClose);
                JmsUtils.closeSession(sessionToClose);
                ConnectionFactoryUtils.releaseConnection(conToClose, this.getConnectionFactory(), true);
                throw throwable;
            }
            JmsUtils.closeMessageConsumer(consumerToClose);
            JmsUtils.closeSession(sessionToClose);
            ConnectionFactoryUtils.releaseConnection(conToClose, this.getConnectionFactory(), true);
            return bl;
        }
        if (this.logger.isTraceEnabled()) {
            this.logger.trace((Object)("Consumer [" + consumerToUse + "] of " + (transactional ? "transactional " : "") + "session [" + sessionToUse + "] did not receive a message"));
        }
        this.noMessageReceived(invoker, sessionToUse);
        if (this.shouldCommitAfterNoMessageReceived(sessionToUse)) {
            this.commitIfNecessary(sessionToUse, null);
        }
        boolean bl = false;
        JmsUtils.closeMessageConsumer(consumerToClose);
        JmsUtils.closeSession(sessionToClose);
        ConnectionFactoryUtils.releaseConnection(conToClose, this.getConnectionFactory(), true);
        return bl;
    }

    @Override
    protected boolean isSessionLocallyTransacted(Session session) {
        if (!super.isSessionLocallyTransacted(session)) {
            return false;
        }
        JmsResourceHolder resourceHolder = (JmsResourceHolder)((Object)TransactionSynchronizationManager.getResource((Object)this.obtainConnectionFactory()));
        return resourceHolder == null || resourceHolder instanceof LocallyExposedJmsResourceHolder || !resourceHolder.containsSession(session);
    }

    protected boolean shouldCommitAfterNoMessageReceived(Session session) {
        return true;
    }

    private void rollbackOnException(PlatformTransactionManager manager, TransactionStatus status, Throwable ex) {
        this.logger.debug((Object)"Initiating transaction rollback on listener exception", ex);
        try {
            manager.rollback(status);
        }
        catch (RuntimeException ex2) {
            this.logger.error((Object)"Listener exception overridden by rollback exception", ex);
            throw ex2;
        }
        catch (Error err) {
            this.logger.error((Object)"Listener exception overridden by rollback error", ex);
            throw err;
        }
    }

    @Nullable
    protected Message receiveMessage(MessageConsumer consumer) throws JMSException {
        return this.receiveFromConsumer(consumer, this.getReceiveTimeout());
    }

    protected void messageReceived(Object invoker, Session session) {
    }

    protected void noMessageReceived(Object invoker, Session session) {
    }

    @Nullable
    protected Connection getConnection(JmsResourceHolder holder) {
        return holder.getConnection();
    }

    @Nullable
    protected Session getSession(JmsResourceHolder holder) {
        return holder.getSession();
    }

    private class MessageListenerContainerResourceFactory
    implements ConnectionFactoryUtils.ResourceFactory {
        private MessageListenerContainerResourceFactory() {
        }

        @Override
        @Nullable
        public Connection getConnection(JmsResourceHolder holder) {
            return AbstractPollingMessageListenerContainer.this.getConnection(holder);
        }

        @Override
        @Nullable
        public Session getSession(JmsResourceHolder holder) {
            return AbstractPollingMessageListenerContainer.this.getSession(holder);
        }

        @Override
        public Connection createConnection() throws JMSException {
            if (AbstractPollingMessageListenerContainer.this.sharedConnectionEnabled()) {
                Connection sharedCon = AbstractPollingMessageListenerContainer.this.getSharedConnection();
                return new SingleConnectionFactory(sharedCon).createConnection();
            }
            return AbstractPollingMessageListenerContainer.this.createConnection();
        }

        @Override
        public Session createSession(Connection con) throws JMSException {
            return AbstractPollingMessageListenerContainer.this.createSession(con);
        }

        @Override
        public boolean isSynchedLocalTransactionAllowed() {
            return AbstractPollingMessageListenerContainer.this.isSessionTransacted();
        }
    }
}

