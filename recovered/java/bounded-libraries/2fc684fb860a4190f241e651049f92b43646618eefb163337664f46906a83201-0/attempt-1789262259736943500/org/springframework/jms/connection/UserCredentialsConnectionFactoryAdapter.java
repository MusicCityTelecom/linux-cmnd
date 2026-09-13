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
 *  org.springframework.core.NamedThreadLocal
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
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
import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class UserCredentialsConnectionFactoryAdapter
implements ConnectionFactory,
QueueConnectionFactory,
TopicConnectionFactory,
InitializingBean {
    @Nullable
    private ConnectionFactory targetConnectionFactory;
    @Nullable
    private String username;
    @Nullable
    private String password;
    private final ThreadLocal<JmsUserCredentials> threadBoundCredentials = new NamedThreadLocal("Current JMS user credentials");

    public void setTargetConnectionFactory(ConnectionFactory targetConnectionFactory) {
        Assert.notNull((Object)targetConnectionFactory, (String)"'targetConnectionFactory' must not be null");
        this.targetConnectionFactory = targetConnectionFactory;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void afterPropertiesSet() {
        if (this.targetConnectionFactory == null) {
            throw new IllegalArgumentException("Property 'targetConnectionFactory' is required");
        }
    }

    public void setCredentialsForCurrentThread(String username, String password) {
        this.threadBoundCredentials.set(new JmsUserCredentials(username, password));
    }

    public void removeCredentialsFromCurrentThread() {
        this.threadBoundCredentials.remove();
    }

    public final Connection createConnection() throws JMSException {
        JmsUserCredentials threadCredentials = this.threadBoundCredentials.get();
        if (threadCredentials != null) {
            return this.doCreateConnection(threadCredentials.username, threadCredentials.password);
        }
        return this.doCreateConnection(this.username, this.password);
    }

    public Connection createConnection(String username, String password) throws JMSException {
        return this.doCreateConnection(username, password);
    }

    protected Connection doCreateConnection(@Nullable String username, @Nullable String password) throws JMSException {
        ConnectionFactory target = this.obtainTargetConnectionFactory();
        if (StringUtils.hasLength((String)username)) {
            return target.createConnection(username, password);
        }
        return target.createConnection();
    }

    public final QueueConnection createQueueConnection() throws JMSException {
        JmsUserCredentials threadCredentials = this.threadBoundCredentials.get();
        if (threadCredentials != null) {
            return this.doCreateQueueConnection(threadCredentials.username, threadCredentials.password);
        }
        return this.doCreateQueueConnection(this.username, this.password);
    }

    public QueueConnection createQueueConnection(String username, String password) throws JMSException {
        return this.doCreateQueueConnection(username, password);
    }

    protected QueueConnection doCreateQueueConnection(@Nullable String username, @Nullable String password) throws JMSException {
        ConnectionFactory target = this.obtainTargetConnectionFactory();
        if (!(target instanceof QueueConnectionFactory)) {
            throw new IllegalStateException("'targetConnectionFactory' is not a QueueConnectionFactory");
        }
        QueueConnectionFactory queueFactory = (QueueConnectionFactory)target;
        if (StringUtils.hasLength((String)username)) {
            return queueFactory.createQueueConnection(username, password);
        }
        return queueFactory.createQueueConnection();
    }

    public final TopicConnection createTopicConnection() throws JMSException {
        JmsUserCredentials threadCredentials = this.threadBoundCredentials.get();
        if (threadCredentials != null) {
            return this.doCreateTopicConnection(threadCredentials.username, threadCredentials.password);
        }
        return this.doCreateTopicConnection(this.username, this.password);
    }

    public TopicConnection createTopicConnection(String username, String password) throws JMSException {
        return this.doCreateTopicConnection(username, password);
    }

    protected TopicConnection doCreateTopicConnection(@Nullable String username, @Nullable String password) throws JMSException {
        ConnectionFactory target = this.obtainTargetConnectionFactory();
        if (!(target instanceof TopicConnectionFactory)) {
            throw new IllegalStateException("'targetConnectionFactory' is not a TopicConnectionFactory");
        }
        TopicConnectionFactory queueFactory = (TopicConnectionFactory)target;
        if (StringUtils.hasLength((String)username)) {
            return queueFactory.createTopicConnection(username, password);
        }
        return queueFactory.createTopicConnection();
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

    private ConnectionFactory obtainTargetConnectionFactory() {
        Assert.state((this.targetConnectionFactory != null ? 1 : 0) != 0, (String)"'targetConnectionFactory' is required");
        return this.targetConnectionFactory;
    }

    private static final class JmsUserCredentials {
        public final String username;
        public final String password;

        public JmsUserCredentials(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String toString() {
            return "JmsUserCredentials[username='" + this.username + "',password='" + this.password + "']";
        }
    }
}

