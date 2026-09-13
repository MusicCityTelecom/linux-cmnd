/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.MessageListener
 *  javax.resource.ResourceException
 *  javax.resource.spi.endpoint.MessageEndpointFactory
 *  org.springframework.beans.factory.BeanNameAware
 *  org.springframework.jca.endpoint.GenericMessageEndpointManager
 *  org.springframework.lang.Nullable
 */
package org.springframework.jms.listener.endpoint;

import javax.jms.MessageListener;
import javax.resource.ResourceException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.jca.endpoint.GenericMessageEndpointManager;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.listener.endpoint.DefaultJmsActivationSpecFactory;
import org.springframework.jms.listener.endpoint.JmsActivationSpecConfig;
import org.springframework.jms.listener.endpoint.JmsActivationSpecFactory;
import org.springframework.jms.listener.endpoint.JmsMessageEndpointFactory;
import org.springframework.jms.listener.endpoint.StandardJmsActivationSpecFactory;
import org.springframework.jms.support.QosSettings;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.lang.Nullable;

public class JmsMessageEndpointManager
extends GenericMessageEndpointManager
implements BeanNameAware,
MessageListenerContainer {
    private final JmsMessageEndpointFactory endpointFactory = new JmsMessageEndpointFactory();
    private boolean messageListenerSet = false;
    private JmsActivationSpecFactory activationSpecFactory = new DefaultJmsActivationSpecFactory();
    @Nullable
    private JmsActivationSpecConfig activationSpecConfig;

    public void setMessageListener(MessageListener messageListener) {
        this.endpointFactory.setMessageListener(messageListener);
        this.messageListenerSet = true;
    }

    public MessageListener getMessageListener() {
        return this.endpointFactory.getMessageListener();
    }

    public void setTransactionManager(Object transactionManager) {
        this.endpointFactory.setTransactionManager(transactionManager);
    }

    public void setActivationSpecFactory(@Nullable JmsActivationSpecFactory activationSpecFactory) {
        this.activationSpecFactory = activationSpecFactory != null ? activationSpecFactory : new DefaultJmsActivationSpecFactory();
    }

    public void setDestinationResolver(DestinationResolver destinationResolver) {
        DefaultJmsActivationSpecFactory factory = new DefaultJmsActivationSpecFactory();
        factory.setDestinationResolver(destinationResolver);
        this.activationSpecFactory = factory;
    }

    public void setActivationSpecConfig(@Nullable JmsActivationSpecConfig activationSpecConfig) {
        this.activationSpecConfig = activationSpecConfig;
    }

    @Nullable
    public JmsActivationSpecConfig getActivationSpecConfig() {
        return this.activationSpecConfig;
    }

    public void setBeanName(String beanName) {
        this.endpointFactory.setBeanName(beanName);
    }

    public void afterPropertiesSet() throws ResourceException {
        if (this.getResourceAdapter() == null) {
            throw new IllegalArgumentException("Property 'resourceAdapter' is required");
        }
        if (this.messageListenerSet) {
            this.setMessageEndpointFactory((MessageEndpointFactory)this.endpointFactory);
        }
        if (this.activationSpecConfig != null) {
            this.setActivationSpec(this.activationSpecFactory.createActivationSpec(this.getResourceAdapter(), this.activationSpecConfig));
        }
        super.afterPropertiesSet();
    }

    @Override
    public void setupMessageListener(Object messageListener) {
        if (!(messageListener instanceof MessageListener)) {
            throw new IllegalArgumentException("Unsupported message listener '" + messageListener.getClass().getName() + "': only '" + MessageListener.class.getName() + "' type is supported");
        }
        this.setMessageListener((MessageListener)messageListener);
    }

    @Override
    @Nullable
    public MessageConverter getMessageConverter() {
        JmsActivationSpecConfig config = this.getActivationSpecConfig();
        if (config != null) {
            return config.getMessageConverter();
        }
        return null;
    }

    @Override
    @Nullable
    public DestinationResolver getDestinationResolver() {
        if (this.activationSpecFactory instanceof StandardJmsActivationSpecFactory) {
            return ((StandardJmsActivationSpecFactory)this.activationSpecFactory).getDestinationResolver();
        }
        return null;
    }

    @Override
    public boolean isPubSubDomain() {
        JmsActivationSpecConfig config = this.getActivationSpecConfig();
        if (config != null) {
            return config.isPubSubDomain();
        }
        throw new IllegalStateException("Could not determine pubSubDomain - no activation spec config is set");
    }

    @Override
    public boolean isReplyPubSubDomain() {
        JmsActivationSpecConfig config = this.getActivationSpecConfig();
        if (config != null) {
            return config.isReplyPubSubDomain();
        }
        throw new IllegalStateException("Could not determine reply pubSubDomain - no activation spec config is set");
    }

    @Override
    @Nullable
    public QosSettings getReplyQosSettings() {
        JmsActivationSpecConfig config = this.getActivationSpecConfig();
        if (config != null) {
            return config.getReplyQosSettings();
        }
        throw new IllegalStateException("Could not determine reply qosSettings - no activation spec config is set");
    }
}

