/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Connection
 *  javax.jms.ConnectionFactory
 *  javax.jms.IllegalStateException
 *  javax.jms.JMSContext
 *  javax.jms.JMSException
 *  javax.jms.QueueConnection
 *  javax.jms.QueueConnectionFactory
 *  javax.jms.QueueSession
 *  javax.jms.Session
 *  javax.jms.TopicConnection
 *  javax.jms.TopicConnectionFactory
 *  javax.jms.TopicSession
 *  javax.jms.TransactionInProgressException
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 */
package org.springframework.jms.connection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.IllegalStateException;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TransactionInProgressException;
import org.springframework.jms.connection.ConnectionFactoryUtils;
import org.springframework.jms.connection.SessionProxy;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public class TransactionAwareConnectionFactoryProxy
implements ConnectionFactory,
QueueConnectionFactory,
TopicConnectionFactory {
    @Nullable
    private ConnectionFactory targetConnectionFactory;
    private boolean synchedLocalTransactionAllowed = false;

    public TransactionAwareConnectionFactoryProxy() {
    }

    public TransactionAwareConnectionFactoryProxy(ConnectionFactory targetConnectionFactory) {
        this.setTargetConnectionFactory(targetConnectionFactory);
    }

    public final void setTargetConnectionFactory(ConnectionFactory targetConnectionFactory) {
        Assert.notNull((Object)targetConnectionFactory, (String)"'targetConnectionFactory' must not be null");
        this.targetConnectionFactory = targetConnectionFactory;
    }

    protected ConnectionFactory getTargetConnectionFactory() {
        ConnectionFactory target = this.targetConnectionFactory;
        Assert.state((target != null ? 1 : 0) != 0, (String)"'targetConnectionFactory' is required");
        return target;
    }

    public void setSynchedLocalTransactionAllowed(boolean synchedLocalTransactionAllowed) {
        this.synchedLocalTransactionAllowed = synchedLocalTransactionAllowed;
    }

    protected boolean isSynchedLocalTransactionAllowed() {
        return this.synchedLocalTransactionAllowed;
    }

    public Connection createConnection() throws JMSException {
        Connection targetConnection = this.getTargetConnectionFactory().createConnection();
        return this.getTransactionAwareConnectionProxy(targetConnection);
    }

    public Connection createConnection(String username, String password) throws JMSException {
        Connection targetConnection = this.getTargetConnectionFactory().createConnection(username, password);
        return this.getTransactionAwareConnectionProxy(targetConnection);
    }

    public QueueConnection createQueueConnection() throws JMSException {
        ConnectionFactory target = this.getTargetConnectionFactory();
        if (!(target instanceof QueueConnectionFactory)) {
            throw new IllegalStateException("'targetConnectionFactory' is no QueueConnectionFactory");
        }
        QueueConnection targetConnection = ((QueueConnectionFactory)target).createQueueConnection();
        return (QueueConnection)this.getTransactionAwareConnectionProxy((Connection)targetConnection);
    }

    public QueueConnection createQueueConnection(String username, String password) throws JMSException {
        ConnectionFactory target = this.getTargetConnectionFactory();
        if (!(target instanceof QueueConnectionFactory)) {
            throw new IllegalStateException("'targetConnectionFactory' is no QueueConnectionFactory");
        }
        QueueConnection targetConnection = ((QueueConnectionFactory)target).createQueueConnection(username, password);
        return (QueueConnection)this.getTransactionAwareConnectionProxy((Connection)targetConnection);
    }

    public TopicConnection createTopicConnection() throws JMSException {
        ConnectionFactory target = this.getTargetConnectionFactory();
        if (!(target instanceof TopicConnectionFactory)) {
            throw new IllegalStateException("'targetConnectionFactory' is no TopicConnectionFactory");
        }
        TopicConnection targetConnection = ((TopicConnectionFactory)target).createTopicConnection();
        return (TopicConnection)this.getTransactionAwareConnectionProxy((Connection)targetConnection);
    }

    public TopicConnection createTopicConnection(String username, String password) throws JMSException {
        ConnectionFactory target = this.getTargetConnectionFactory();
        if (!(target instanceof TopicConnectionFactory)) {
            throw new IllegalStateException("'targetConnectionFactory' is no TopicConnectionFactory");
        }
        TopicConnection targetConnection = ((TopicConnectionFactory)target).createTopicConnection(username, password);
        return (TopicConnection)this.getTransactionAwareConnectionProxy((Connection)targetConnection);
    }

    public JMSContext createContext() {
        return this.getTargetConnectionFactory().createContext();
    }

    public JMSContext createContext(String userName, String password) {
        return this.getTargetConnectionFactory().createContext(userName, password);
    }

    public JMSContext createContext(String userName, String password, int sessionMode) {
        return this.getTargetConnectionFactory().createContext(userName, password, sessionMode);
    }

    public JMSContext createContext(int sessionMode) {
        return this.getTargetConnectionFactory().createContext(sessionMode);
    }

    protected Connection getTransactionAwareConnectionProxy(Connection target) {
        ArrayList<Class<TopicConnection>> classes = new ArrayList<Class<TopicConnection>>(3);
        classes.add(Connection.class);
        if (target instanceof QueueConnection) {
            classes.add(QueueConnection.class);
        }
        if (target instanceof TopicConnection) {
            classes.add(TopicConnection.class);
        }
        return (Connection)Proxy.newProxyInstance(Connection.class.getClassLoader(), ClassUtils.toClassArray(classes), (InvocationHandler)new TransactionAwareConnectionInvocationHandler(target));
    }

    private static class CloseSuppressingSessionInvocationHandler
    implements InvocationHandler {
        private final Session target;

        public CloseSuppressingSessionInvocationHandler(Session target) {
            this.target = target;
        }

        @Override
        @Nullable
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            switch (method.getName()) {
                case "equals": {
                    return proxy == args[0];
                }
                case "hashCode": {
                    return System.identityHashCode(proxy);
                }
                case "commit": {
                    throw new TransactionInProgressException("Commit call not allowed within a managed transaction");
                }
                case "rollback": {
                    throw new TransactionInProgressException("Rollback call not allowed within a managed transaction");
                }
                case "close": {
                    return null;
                }
                case "getTargetSession": {
                    return this.target;
                }
            }
            try {
                return method.invoke(this.target, args);
            }
            catch (InvocationTargetException ex) {
                throw ex.getTargetException();
            }
        }
    }

    private class TransactionAwareConnectionInvocationHandler
    implements InvocationHandler {
        private final Connection target;

        public TransactionAwareConnectionInvocationHandler(Connection target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Session session;
            switch (method.getName()) {
                case "equals": {
                    return proxy == args[0];
                }
                case "hashCode": {
                    return System.identityHashCode(proxy);
                }
            }
            if (Session.class == method.getReturnType() ? (session = ConnectionFactoryUtils.getTransactionalSession(TransactionAwareConnectionFactoryProxy.this.getTargetConnectionFactory(), this.target, TransactionAwareConnectionFactoryProxy.this.isSynchedLocalTransactionAllowed())) != null : (QueueSession.class == method.getReturnType() ? (session = ConnectionFactoryUtils.getTransactionalQueueSession((QueueConnectionFactory)TransactionAwareConnectionFactoryProxy.this.getTargetConnectionFactory(), (QueueConnection)this.target, TransactionAwareConnectionFactoryProxy.this.isSynchedLocalTransactionAllowed())) != null : TopicSession.class == method.getReturnType() && (session = ConnectionFactoryUtils.getTransactionalTopicSession((TopicConnectionFactory)TransactionAwareConnectionFactoryProxy.this.getTargetConnectionFactory(), (TopicConnection)this.target, TransactionAwareConnectionFactoryProxy.this.isSynchedLocalTransactionAllowed())) != null)) {
                return this.getCloseSuppressingSessionProxy(session);
            }
            try {
                return method.invoke(this.target, args);
            }
            catch (InvocationTargetException ex) {
                throw ex.getTargetException();
            }
        }

        private Session getCloseSuppressingSessionProxy(Session target) {
            ArrayList<Class<TopicSession>> classes = new ArrayList<Class<TopicSession>>(3);
            classes.add(SessionProxy.class);
            if (target instanceof QueueSession) {
                classes.add(QueueSession.class);
            }
            if (target instanceof TopicSession) {
                classes.add(TopicSession.class);
            }
            return (Session)Proxy.newProxyInstance(SessionProxy.class.getClassLoader(), ClassUtils.toClassArray(classes), (InvocationHandler)new CloseSuppressingSessionInvocationHandler(target));
        }
    }
}

