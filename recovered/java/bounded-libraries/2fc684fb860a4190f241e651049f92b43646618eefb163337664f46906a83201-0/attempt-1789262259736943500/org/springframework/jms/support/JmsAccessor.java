/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Connection
 *  javax.jms.ConnectionFactory
 *  javax.jms.JMSException
 *  javax.jms.Session
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.core.Constants
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.jms.support;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Constants;
import org.springframework.jms.JmsException;
import org.springframework.jms.support.JmsUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public abstract class JmsAccessor
implements InitializingBean {
    private static final Constants sessionConstants = new Constants(Session.class);
    protected final Log logger = LogFactory.getLog(this.getClass());
    @Nullable
    private ConnectionFactory connectionFactory;
    private boolean sessionTransacted = false;
    private int sessionAcknowledgeMode = 1;

    public void setConnectionFactory(@Nullable ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
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

    public void setSessionTransacted(boolean sessionTransacted) {
        this.sessionTransacted = sessionTransacted;
    }

    public boolean isSessionTransacted() {
        return this.sessionTransacted;
    }

    public void setSessionAcknowledgeModeName(String constantName) {
        this.setSessionAcknowledgeMode(sessionConstants.asNumber(constantName).intValue());
    }

    public void setSessionAcknowledgeMode(int sessionAcknowledgeMode) {
        this.sessionAcknowledgeMode = sessionAcknowledgeMode;
    }

    public int getSessionAcknowledgeMode() {
        return this.sessionAcknowledgeMode;
    }

    public void afterPropertiesSet() {
        if (this.getConnectionFactory() == null) {
            throw new IllegalArgumentException("Property 'connectionFactory' is required");
        }
    }

    protected JmsException convertJmsAccessException(JMSException ex) {
        return JmsUtils.convertJmsAccessException(ex);
    }

    protected Connection createConnection() throws JMSException {
        return this.obtainConnectionFactory().createConnection();
    }

    protected Session createSession(Connection con) throws JMSException {
        return con.createSession(this.isSessionTransacted(), this.getSessionAcknowledgeMode());
    }

    protected boolean isClientAcknowledge(Session session) throws JMSException {
        return session.getAcknowledgeMode() == 2;
    }
}

