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
 *  javax.jms.TopicConnection
 *  javax.jms.TopicConnectionFactory
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.jms.connection;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.IllegalStateException;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.connection.SmartConnectionFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class DelegatingConnectionFactory
implements SmartConnectionFactory,
QueueConnectionFactory,
TopicConnectionFactory,
InitializingBean {
    @Nullable
    private ConnectionFactory targetConnectionFactory;
    private boolean shouldStopConnections = false;

    public void setTargetConnectionFactory(@Nullable ConnectionFactory targetConnectionFactory) {
        this.targetConnectionFactory = targetConnectionFactory;
    }

    @Nullable
    public ConnectionFactory getTargetConnectionFactory() {
        return this.targetConnectionFactory;
    }

    private ConnectionFactory obtainTargetConnectionFactory() {
        ConnectionFactory target = this.getTargetConnectionFactory();
        Assert.state((target != null ? 1 : 0) != 0, (String)"No 'targetConnectionFactory' set");
        return target;
    }

    public void setShouldStopConnections(boolean shouldStopConnections) {
        this.shouldStopConnections = shouldStopConnections;
    }

    public void afterPropertiesSet() {
        if (this.getTargetConnectionFactory() == null) {
            throw new IllegalArgumentException("'targetConnectionFactory' is required");
        }
    }

    public Connection createConnection() throws JMSException {
        return this.obtainTargetConnectionFactory().createConnection();
    }

    public Connection createConnection(String username, String password) throws JMSException {
        return this.obtainTargetConnectionFactory().createConnection(username, password);
    }

    public QueueConnection createQueueConnection() throws JMSException {
        ConnectionFactory target = this.obtainTargetConnectionFactory();
        if (target instanceof QueueConnectionFactory) {
            return ((QueueConnectionFactory)target).createQueueConnection();
        }
        Connection con = target.createConnection();
        if (!(con instanceof QueueConnection)) {
            throw new IllegalStateException("'targetConnectionFactory' is not a QueueConnectionFactory");
        }
        return (QueueConnection)con;
    }

    public QueueConnection createQueueConnection(String username, String password) throws JMSException {
        ConnectionFactory target = this.obtainTargetConnectionFactory();
        if (target instanceof QueueConnectionFactory) {
            return ((QueueConnectionFactory)target).createQueueConnection(username, password);
        }
        Connection con = target.createConnection(username, password);
        if (!(con instanceof QueueConnection)) {
            throw new IllegalStateException("'targetConnectionFactory' is not a QueueConnectionFactory");
        }
        return (QueueConnection)con;
    }

    public TopicConnection createTopicConnection() throws JMSException {
        ConnectionFactory target = this.obtainTargetConnectionFactory();
        if (target instanceof TopicConnectionFactory) {
            return ((TopicConnectionFactory)target).createTopicConnection();
        }
        Connection con = target.createConnection();
        if (!(con instanceof TopicConnection)) {
            throw new IllegalStateException("'targetConnectionFactory' is not a TopicConnectionFactory");
        }
        return (TopicConnection)con;
    }

    public TopicConnection createTopicConnection(String username, String password) throws JMSException {
        ConnectionFactory target = this.obtainTargetConnectionFactory();
        if (target instanceof TopicConnectionFactory) {
            return ((TopicConnectionFactory)target).createTopicConnection(username, password);
        }
        Connection con = target.createConnection(username, password);
        if (!(con instanceof TopicConnection)) {
            throw new IllegalStateException("'targetConnectionFactory' is not a TopicConnectionFactory");
        }
        return (TopicConnection)con;
    }

    public JMSContext createContext() {
        return this.obtainTargetConnectionFactory().createContext();
    }

    public JMSContext createContext(String userName, String password) {
        return this.obtainTargetConnectionFactory().createContext(userName, password);
    }

    public JMSContext createContext(String userName, String password, int sessionMode) {
        return this.obtainTargetConnectionFactory().createContext(userName, password, sessionMode);
    }

    public JMSContext createContext(int sessionMode) {
        return this.obtainTargetConnectionFactory().createContext(sessionMode);
    }

    @Override
    public boolean shouldStop(Connection con) {
        return this.shouldStopConnections;
    }
}

