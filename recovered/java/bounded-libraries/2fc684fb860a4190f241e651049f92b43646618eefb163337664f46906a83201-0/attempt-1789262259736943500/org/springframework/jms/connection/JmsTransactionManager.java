/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Connection
 *  javax.jms.ConnectionFactory
 *  javax.jms.JMSException
 *  javax.jms.Session
 *  javax.jms.TransactionRolledBackException
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.lang.Nullable
 *  org.springframework.transaction.CannotCreateTransactionException
 *  org.springframework.transaction.InvalidIsolationLevelException
 *  org.springframework.transaction.TransactionDefinition
 *  org.springframework.transaction.TransactionSystemException
 *  org.springframework.transaction.UnexpectedRollbackException
 *  org.springframework.transaction.support.AbstractPlatformTransactionManager
 *  org.springframework.transaction.support.DefaultTransactionStatus
 *  org.springframework.transaction.support.ResourceTransactionManager
 *  org.springframework.transaction.support.SmartTransactionObject
 *  org.springframework.transaction.support.TransactionSynchronizationManager
 *  org.springframework.util.Assert
 */
package org.springframework.jms.connection;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TransactionRolledBackException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.connection.JmsResourceHolder;
import org.springframework.jms.connection.TransactionAwareConnectionFactoryProxy;
import org.springframework.lang.Nullable;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.InvalidIsolationLevelException;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.ResourceTransactionManager;
import org.springframework.transaction.support.SmartTransactionObject;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

public class JmsTransactionManager
extends AbstractPlatformTransactionManager
implements ResourceTransactionManager,
InitializingBean {
    @Nullable
    private ConnectionFactory connectionFactory;
    private boolean lazyResourceRetrieval = false;

    public JmsTransactionManager() {
        this.setTransactionSynchronization(2);
    }

    public JmsTransactionManager(ConnectionFactory connectionFactory) {
        this();
        this.setConnectionFactory(connectionFactory);
        this.afterPropertiesSet();
    }

    public void setConnectionFactory(@Nullable ConnectionFactory cf) {
        this.connectionFactory = cf instanceof TransactionAwareConnectionFactoryProxy ? ((TransactionAwareConnectionFactoryProxy)cf).getTargetConnectionFactory() : cf;
    }

    @Nullable
    public ConnectionFactory getConnectionFactory() {
        return this.connectionFactory;
    }

    protected final ConnectionFactory obtainConnectionFactory() {
        ConnectionFactory connectionFactory = this.getConnectionFactory();
        Assert.state((connectionFactory != null ? 1 : 0) != 0, (String)"No ConnectionFactory set");
        return connectionFactory;
    }

    public void setLazyResourceRetrieval(boolean lazyResourceRetrieval) {
        this.lazyResourceRetrieval = lazyResourceRetrieval;
    }

    public void afterPropertiesSet() {
        if (this.getConnectionFactory() == null) {
            throw new IllegalArgumentException("Property 'connectionFactory' is required");
        }
    }

    public Object getResourceFactory() {
        return this.obtainConnectionFactory();
    }

    protected Object doGetTransaction() {
        JmsTransactionObject txObject = new JmsTransactionObject();
        txObject.setResourceHolder((JmsResourceHolder)((Object)TransactionSynchronizationManager.getResource((Object)this.obtainConnectionFactory())));
        return txObject;
    }

    protected boolean isExistingTransaction(Object transaction) {
        JmsTransactionObject txObject = (JmsTransactionObject)transaction;
        return txObject.hasResourceHolder();
    }

    protected void doBegin(Object transaction, TransactionDefinition definition) {
        if (definition.getIsolationLevel() != -1) {
            throw new InvalidIsolationLevelException("JMS does not support an isolation level concept");
        }
        ConnectionFactory connectionFactory = this.obtainConnectionFactory();
        JmsTransactionObject txObject = (JmsTransactionObject)transaction;
        Connection con = null;
        Session session = null;
        try {
            JmsResourceHolder resourceHolder;
            if (this.lazyResourceRetrieval) {
                resourceHolder = new LazyJmsResourceHolder(connectionFactory);
            } else {
                con = this.createConnection();
                session = this.createSession(con);
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug((Object)("Created JMS transaction on Session [" + session + "] from Connection [" + con + "]"));
                }
                resourceHolder = new JmsResourceHolder(connectionFactory, con, session);
            }
            resourceHolder.setSynchronizedWithTransaction(true);
            int timeout = this.determineTimeout(definition);
            if (timeout != -1) {
                resourceHolder.setTimeoutInSeconds(timeout);
            }
            txObject.setResourceHolder(resourceHolder);
            TransactionSynchronizationManager.bindResource((Object)connectionFactory, (Object)((Object)resourceHolder));
        }
        catch (Throwable ex) {
            if (session != null) {
                try {
                    session.close();
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            if (con != null) {
                try {
                    con.close();
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            throw new CannotCreateTransactionException("Could not create JMS transaction", ex);
        }
    }

    protected Object doSuspend(Object transaction) {
        JmsTransactionObject txObject = (JmsTransactionObject)transaction;
        txObject.setResourceHolder(null);
        return TransactionSynchronizationManager.unbindResource((Object)this.obtainConnectionFactory());
    }

    protected void doResume(@Nullable Object transaction, Object suspendedResources) {
        TransactionSynchronizationManager.bindResource((Object)this.obtainConnectionFactory(), (Object)suspendedResources);
    }

    protected void doCommit(DefaultTransactionStatus status) {
        JmsTransactionObject txObject = (JmsTransactionObject)status.getTransaction();
        Session session = txObject.getResourceHolder().getOriginalSession();
        if (session != null) {
            try {
                if (status.isDebug()) {
                    this.logger.debug((Object)("Committing JMS transaction on Session [" + session + "]"));
                }
                session.commit();
            }
            catch (TransactionRolledBackException ex) {
                throw new UnexpectedRollbackException("JMS transaction rolled back", (Throwable)ex);
            }
            catch (JMSException ex) {
                throw new TransactionSystemException("Could not commit JMS transaction", (Throwable)ex);
            }
        }
    }

    protected void doRollback(DefaultTransactionStatus status) {
        JmsTransactionObject txObject = (JmsTransactionObject)status.getTransaction();
        Session session = txObject.getResourceHolder().getOriginalSession();
        if (session != null) {
            try {
                if (status.isDebug()) {
                    this.logger.debug((Object)("Rolling back JMS transaction on Session [" + session + "]"));
                }
                session.rollback();
            }
            catch (JMSException ex) {
                throw new TransactionSystemException("Could not roll back JMS transaction", (Throwable)ex);
            }
        }
    }

    protected void doSetRollbackOnly(DefaultTransactionStatus status) {
        JmsTransactionObject txObject = (JmsTransactionObject)status.getTransaction();
        txObject.getResourceHolder().setRollbackOnly();
    }

    protected void doCleanupAfterCompletion(Object transaction) {
        JmsTransactionObject txObject = (JmsTransactionObject)transaction;
        TransactionSynchronizationManager.unbindResource((Object)this.obtainConnectionFactory());
        txObject.getResourceHolder().closeAll();
        txObject.getResourceHolder().clear();
    }

    protected Connection createConnection() throws JMSException {
        return this.obtainConnectionFactory().createConnection();
    }

    protected Session createSession(Connection con) throws JMSException {
        return con.createSession(true, 1);
    }

    private static class JmsTransactionObject
    implements SmartTransactionObject {
        @Nullable
        private JmsResourceHolder resourceHolder;

        private JmsTransactionObject() {
        }

        public void setResourceHolder(@Nullable JmsResourceHolder resourceHolder) {
            this.resourceHolder = resourceHolder;
        }

        public JmsResourceHolder getResourceHolder() {
            Assert.state((this.resourceHolder != null ? 1 : 0) != 0, (String)"No JmsResourceHolder available");
            return this.resourceHolder;
        }

        public boolean hasResourceHolder() {
            return this.resourceHolder != null;
        }

        public boolean isRollbackOnly() {
            return this.resourceHolder != null && this.resourceHolder.isRollbackOnly();
        }

        public void flush() {
        }
    }

    private class LazyJmsResourceHolder
    extends JmsResourceHolder {
        private boolean connectionInitialized;
        private boolean sessionInitialized;

        public LazyJmsResourceHolder(ConnectionFactory connectionFactory) {
            super(connectionFactory);
            this.connectionInitialized = false;
            this.sessionInitialized = false;
        }

        @Override
        @Nullable
        public Connection getConnection() {
            this.initializeConnection();
            return super.getConnection();
        }

        @Override
        @Nullable
        public <C extends Connection> C getConnection(Class<C> connectionType) {
            this.initializeConnection();
            return super.getConnection(connectionType);
        }

        @Override
        @Nullable
        public Session getSession() {
            this.initializeSession();
            return super.getSession();
        }

        @Override
        @Nullable
        public <S extends Session> S getSession(Class<S> sessionType) {
            this.initializeSession();
            return super.getSession(sessionType);
        }

        @Override
        @Nullable
        public <S extends Session> S getSession(Class<S> sessionType, @Nullable Connection connection) {
            this.initializeSession();
            return super.getSession(sessionType, connection);
        }

        private void initializeConnection() {
            if (!this.connectionInitialized) {
                try {
                    this.addConnection(JmsTransactionManager.this.createConnection());
                }
                catch (JMSException ex) {
                    throw new CannotCreateTransactionException("Failed to lazily initialize JMS Connection for transaction", (Throwable)ex);
                }
                this.connectionInitialized = true;
            }
        }

        private void initializeSession() {
            if (!this.sessionInitialized) {
                Connection con = this.getConnection();
                Assert.state((con != null ? 1 : 0) != 0, (String)"No transactional JMS Connection");
                try {
                    this.addSession(JmsTransactionManager.this.createSession(con), con);
                }
                catch (JMSException ex) {
                    throw new CannotCreateTransactionException("Failed to lazily initialize JMS Session for transaction", (Throwable)ex);
                }
                this.sessionInitialized = true;
            }
        }
    }
}

