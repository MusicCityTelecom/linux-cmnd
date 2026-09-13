/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Connection
 *  javax.jms.ConnectionFactory
 *  javax.jms.JMSException
 *  javax.jms.QueueConnection
 *  javax.jms.QueueConnectionFactory
 *  javax.jms.QueueSession
 *  javax.jms.Session
 *  javax.jms.TopicConnection
 *  javax.jms.TopicConnectionFactory
 *  javax.jms.TopicSession
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.lang.Nullable
 *  org.springframework.transaction.support.ResourceHolder
 *  org.springframework.transaction.support.ResourceHolderSynchronization
 *  org.springframework.transaction.support.TransactionSynchronization
 *  org.springframework.transaction.support.TransactionSynchronizationManager
 *  org.springframework.util.Assert
 */
package org.springframework.jms.connection;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.connection.JmsResourceHolder;
import org.springframework.jms.connection.SessionProxy;
import org.springframework.jms.connection.SmartConnectionFactory;
import org.springframework.jms.connection.SynchedLocalTransactionFailedException;
import org.springframework.lang.Nullable;
import org.springframework.transaction.support.ResourceHolder;
import org.springframework.transaction.support.ResourceHolderSynchronization;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

public abstract class ConnectionFactoryUtils {
    private static final Log logger = LogFactory.getLog(ConnectionFactoryUtils.class);

    public static void releaseConnection(@Nullable Connection con, @Nullable ConnectionFactory cf, boolean started) {
        if (con == null) {
            return;
        }
        if (started && cf instanceof SmartConnectionFactory && ((SmartConnectionFactory)cf).shouldStop(con)) {
            try {
                con.stop();
            }
            catch (Throwable ex) {
                logger.debug((Object)"Could not stop JMS Connection before closing it", ex);
            }
        }
        try {
            con.close();
        }
        catch (Throwable ex) {
            logger.debug((Object)"Could not close JMS Connection", ex);
        }
    }

    public static Session getTargetSession(Session session) {
        Session sessionToUse = session;
        while (sessionToUse instanceof SessionProxy) {
            sessionToUse = ((SessionProxy)sessionToUse).getTargetSession();
        }
        return sessionToUse;
    }

    public static boolean isSessionTransactional(@Nullable Session session, @Nullable ConnectionFactory cf) {
        if (session == null || cf == null) {
            return false;
        }
        JmsResourceHolder resourceHolder = (JmsResourceHolder)((Object)TransactionSynchronizationManager.getResource((Object)cf));
        return resourceHolder != null && resourceHolder.containsSession(session);
    }

    @Nullable
    public static Session getTransactionalSession(final ConnectionFactory cf, final @Nullable Connection existingCon, final boolean synchedLocalTransactionAllowed) throws JMSException {
        return ConnectionFactoryUtils.doGetTransactionalSession(cf, new ResourceFactory(){

            @Override
            @Nullable
            public Session getSession(JmsResourceHolder holder) {
                return holder.getSession(Session.class, existingCon);
            }

            @Override
            @Nullable
            public Connection getConnection(JmsResourceHolder holder) {
                return existingCon != null ? existingCon : holder.getConnection();
            }

            @Override
            public Connection createConnection() throws JMSException {
                return cf.createConnection();
            }

            @Override
            public Session createSession(Connection con) throws JMSException {
                return con.createSession(synchedLocalTransactionAllowed, 1);
            }

            @Override
            public boolean isSynchedLocalTransactionAllowed() {
                return synchedLocalTransactionAllowed;
            }
        }, true);
    }

    @Nullable
    public static QueueSession getTransactionalQueueSession(final QueueConnectionFactory cf, final @Nullable QueueConnection existingCon, final boolean synchedLocalTransactionAllowed) throws JMSException {
        return (QueueSession)ConnectionFactoryUtils.doGetTransactionalSession((ConnectionFactory)cf, new ResourceFactory(){

            @Override
            @Nullable
            public Session getSession(JmsResourceHolder holder) {
                return holder.getSession(QueueSession.class, (Connection)existingCon);
            }

            @Override
            @Nullable
            public Connection getConnection(JmsResourceHolder holder) {
                return existingCon != null ? existingCon : holder.getConnection(QueueConnection.class);
            }

            @Override
            public Connection createConnection() throws JMSException {
                return cf.createQueueConnection();
            }

            @Override
            public Session createSession(Connection con) throws JMSException {
                return ((QueueConnection)con).createQueueSession(synchedLocalTransactionAllowed, 1);
            }

            @Override
            public boolean isSynchedLocalTransactionAllowed() {
                return synchedLocalTransactionAllowed;
            }
        }, true);
    }

    @Nullable
    public static TopicSession getTransactionalTopicSession(final TopicConnectionFactory cf, final @Nullable TopicConnection existingCon, final boolean synchedLocalTransactionAllowed) throws JMSException {
        return (TopicSession)ConnectionFactoryUtils.doGetTransactionalSession((ConnectionFactory)cf, new ResourceFactory(){

            @Override
            @Nullable
            public Session getSession(JmsResourceHolder holder) {
                return holder.getSession(TopicSession.class, (Connection)existingCon);
            }

            @Override
            @Nullable
            public Connection getConnection(JmsResourceHolder holder) {
                return existingCon != null ? existingCon : holder.getConnection(TopicConnection.class);
            }

            @Override
            public Connection createConnection() throws JMSException {
                return cf.createTopicConnection();
            }

            @Override
            public Session createSession(Connection con) throws JMSException {
                return ((TopicConnection)con).createTopicSession(synchedLocalTransactionAllowed, 1);
            }

            @Override
            public boolean isSynchedLocalTransactionAllowed() {
                return synchedLocalTransactionAllowed;
            }
        }, true);
    }

    @Nullable
    public static Session doGetTransactionalSession(ConnectionFactory connectionFactory, ResourceFactory resourceFactory) throws JMSException {
        return ConnectionFactoryUtils.doGetTransactionalSession(connectionFactory, resourceFactory, true);
    }

    @Nullable
    public static Session doGetTransactionalSession(ConnectionFactory connectionFactory, ResourceFactory resourceFactory, boolean startConnection) throws JMSException {
        Assert.notNull((Object)connectionFactory, (String)"ConnectionFactory must not be null");
        Assert.notNull((Object)resourceFactory, (String)"ResourceFactory must not be null");
        JmsResourceHolder resourceHolder = (JmsResourceHolder)((Object)TransactionSynchronizationManager.getResource((Object)connectionFactory));
        if (resourceHolder != null) {
            Session session = resourceFactory.getSession(resourceHolder);
            if (session != null) {
                Connection con;
                if (startConnection && (con = resourceFactory.getConnection(resourceHolder)) != null) {
                    con.start();
                }
                return session;
            }
            if (resourceHolder.isFrozen()) {
                return null;
            }
        }
        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            return null;
        }
        JmsResourceHolder resourceHolderToUse = resourceHolder;
        if (resourceHolderToUse == null) {
            resourceHolderToUse = new JmsResourceHolder(connectionFactory);
        }
        Connection con = resourceFactory.getConnection(resourceHolderToUse);
        Session session = null;
        try {
            boolean isExistingCon;
            boolean bl = isExistingCon = con != null;
            if (!isExistingCon) {
                con = resourceFactory.createConnection();
                resourceHolderToUse.addConnection(con);
            }
            session = resourceFactory.createSession(con);
            resourceHolderToUse.addSession(session, con);
            if (startConnection) {
                con.start();
            }
        }
        catch (JMSException ex) {
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
            throw ex;
        }
        if (resourceHolderToUse != resourceHolder) {
            TransactionSynchronizationManager.registerSynchronization((TransactionSynchronization)new JmsResourceSynchronization(resourceHolderToUse, connectionFactory, resourceFactory.isSynchedLocalTransactionAllowed()));
            resourceHolderToUse.setSynchronizedWithTransaction(true);
            TransactionSynchronizationManager.bindResource((Object)connectionFactory, (Object)((Object)resourceHolderToUse));
        }
        return session;
    }

    private static class JmsResourceSynchronization
    extends ResourceHolderSynchronization<JmsResourceHolder, Object> {
        private final boolean transacted;

        public JmsResourceSynchronization(JmsResourceHolder resourceHolder, Object resourceKey, boolean transacted) {
            super((ResourceHolder)resourceHolder, resourceKey);
            this.transacted = transacted;
        }

        protected boolean shouldReleaseBeforeCompletion() {
            return !this.transacted;
        }

        protected void processResourceAfterCommit(JmsResourceHolder resourceHolder) {
            try {
                resourceHolder.commitAll();
            }
            catch (JMSException ex) {
                throw new SynchedLocalTransactionFailedException("Local JMS transaction failed to commit", ex);
            }
        }

        protected void releaseResource(JmsResourceHolder resourceHolder, Object resourceKey) {
            resourceHolder.closeAll();
        }
    }

    public static interface ResourceFactory {
        @Nullable
        public Session getSession(JmsResourceHolder var1);

        @Nullable
        public Connection getConnection(JmsResourceHolder var1);

        public Connection createConnection() throws JMSException;

        public Session createSession(Connection var1) throws JMSException;

        public boolean isSynchedLocalTransactionAllowed();
    }
}

