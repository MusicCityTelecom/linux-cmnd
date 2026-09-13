/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.ConnectionFactory
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.BeanInitializationException
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.lang.Nullable
 */
package org.springframework.jms.core.support;

import javax.jms.ConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.lang.Nullable;

public abstract class JmsGatewaySupport
implements InitializingBean {
    protected final Log logger = LogFactory.getLog(this.getClass());
    @Nullable
    private JmsTemplate jmsTemplate;

    public final void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.jmsTemplate = this.createJmsTemplate(connectionFactory);
    }

    protected JmsTemplate createJmsTemplate(ConnectionFactory connectionFactory) {
        return new JmsTemplate(connectionFactory);
    }

    @Nullable
    public final ConnectionFactory getConnectionFactory() {
        return this.jmsTemplate != null ? this.jmsTemplate.getConnectionFactory() : null;
    }

    public final void setJmsTemplate(@Nullable JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Nullable
    public final JmsTemplate getJmsTemplate() {
        return this.jmsTemplate;
    }

    public final void afterPropertiesSet() throws IllegalArgumentException, BeanInitializationException {
        if (this.jmsTemplate == null) {
            throw new IllegalArgumentException("'connectionFactory' or 'jmsTemplate' is required");
        }
        try {
            this.initGateway();
        }
        catch (Exception ex) {
            throw new BeanInitializationException("Initialization of JMS gateway failed: " + ex.getMessage(), (Throwable)ex);
        }
    }

    protected void initGateway() throws Exception {
    }
}

