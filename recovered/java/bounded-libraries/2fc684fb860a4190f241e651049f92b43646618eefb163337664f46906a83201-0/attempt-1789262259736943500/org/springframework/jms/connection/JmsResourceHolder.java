/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Connection
 *  javax.jms.ConnectionFactory
 *  javax.jms.IllegalStateException
 *  javax.jms.JMSException
 *  javax.jms.Session
 *  javax.jms.TransactionInProgressException
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.lang.Nullable
 *  org.springframework.transaction.support.ResourceHolderSupport
 *  org.springframework.transaction.support.TransactionSynchronizationManager
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.jms.connection;

import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.IllegalStateException;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TransactionInProgressException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.connection.ConnectionFactoryUtils;
import org.springframework.lang.Nullable;
import org.springframework.transaction.support.ResourceHolderSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

public class JmsResourceHolder
extends ResourceHolderSupport {
    private static final Log logger = LogFactory.getLog(JmsResourceHolder.class);
    @Nullable
    private ConnectionFactory connectionFactory;
    private boolean frozen = false;
    private final Deque<Connection> connections = new ArrayDeque<Connection>();
    private final Deque<Session> sessions = new ArrayDeque<Session>();
    private final Map<Connection, Deque<Session>> sessionsPerConnection = new HashMap<Connection, Deque<Session>>();

    public JmsResourceHolder() {
    }

    public JmsResourceHolder(@Nullable ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public JmsResourceHolder(Session session) {
        this.addSession(session);
        this.frozen = true;
    }

    public JmsResourceHolder(Connection connection, Session session) {
        this.addConnection(connection);
        this.addSession(session, connection);
        this.frozen = true;
    }

    public JmsResourceHolder(@Nullable ConnectionFactory connectionFactory, Connection connection, Session session) {
        this.connectionFactory = connectionFactory;
        this.addConnection(connection);
        this.addSession(session, connection);
        this.frozen = true;
    }

    public final boolean isFrozen() {
        return this.frozen;
    }

    public final void addConnection(Connection connection) {
        Assert.isTrue((!this.frozen ? 1 : 0) != 0, (String)"Cannot add Connection because JmsResourceHolder is frozen");
        Assert.notNull((Object)connection, (String)"Connection must not be null");
        if (!this.connections.contains(connection)) {
            this.connections.add(connection);
        }
    }

    public final void addSession(Session session) {
        this.addSession(session, null);
    }

    public final void addSession(Session session, @Nullable Connection connection) {
        Assert.isTrue((!this.frozen ? 1 : 0) != 0, (String)"Cannot add Session because JmsResourceHolder is frozen");
        Assert.notNull((Object)session, (String)"Session must not be null");
        if (!this.sessions.contains(session)) {
            this.sessions.add(session);
            if (connection != null) {
                Deque sessions = this.sessionsPerConnection.computeIfAbsent(connection, k -> new ArrayDeque());
                sessions.add(session);
            }
        }
    }

    public boolean containsSession(Session session) {
        return this.sessions.contains(session);
    }

    @Nullable
    public Connection getConnection() {
        return this.connections.peek();
    }

    @Nullable
    public <C extends Connection> C getConnection(Class<C> connectionType) {
        return (C)((Connection)CollectionUtils.findValueOfType(this.connections, connectionType));
    }

    @Nullable
    Session getOriginalSession() {
        return this.sessions.peek();
    }

    @Nullable
    public Session getSession() {
        return this.sessions.peek();
    }

    @Nullable
    public <S extends Session> S getSession(Class<S> sessionType) {
        return this.getSession(sessionType, null);
    }

    @Nullable
    public <S extends Session> S getSession(Class<S> sessionType, @Nullable Connection connection) {
        Deque<Session> sessions = connection != null ? this.sessionsPerConnection.get(connection) : this.sessions;
        return (S)((Session)CollectionUtils.findValueOfType(sessions, sessionType));
    }

    public void commitAll() throws JMSException {
        for (Session session : this.sessions) {
            try {
                session.commit();
            }
            catch (TransactionInProgressException transactionInProgressException) {
            }
            catch (IllegalStateException ex) {
                block11: {
                    if (this.connectionFactory != null) {
                        try {
                            Method getDataSourceMethod = this.connectionFactory.getClass().getMethod("getDataSource", new Class[0]);
                            Object ds = ReflectionUtils.invokeMethod((Method)getDataSourceMethod, (Object)this.connectionFactory);
                            while (ds != null) {
                                if (TransactionSynchronizationManager.hasResource((Object)ds)) {
                                    return;
                                }
                                try {
                                    Method getTargetDataSourceMethod = ds.getClass().getMethod("getTargetDataSource", new Class[0]);
                                    ds = ReflectionUtils.invokeMethod((Method)getTargetDataSourceMethod, (Object)ds);
                                }
                                catch (NoSuchMethodException nsme) {
                                    ds = null;
                                }
                            }
                        }
                        catch (Throwable ex2) {
                            if (!logger.isDebugEnabled()) break block11;
                            logger.debug((Object)("No working getDataSource method found on ConnectionFactory: " + ex2));
                        }
                    }
                }
                throw ex;
            }
        }
    }

    public void closeAll() {
        for (Session session : this.sessions) {
            try {
                session.close();
            }
            catch (Throwable ex) {
                logger.debug((Object)"Could not close synchronized JMS Session after transaction", ex);
            }
        }
        for (Connection con : this.connections) {
            ConnectionFactoryUtils.releaseConnection(con, this.connectionFactory, true);
        }
        this.connections.clear();
        this.sessions.clear();
        this.sessionsPerConnection.clear();
    }
}

