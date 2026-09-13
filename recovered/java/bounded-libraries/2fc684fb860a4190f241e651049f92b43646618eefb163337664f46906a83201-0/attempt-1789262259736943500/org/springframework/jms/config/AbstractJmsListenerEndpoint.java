/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.MessageListener
 *  org.springframework.lang.Nullable
 *  org.springframework.util.StringUtils
 */
package org.springframework.jms.config;

import javax.jms.MessageListener;
import org.springframework.jms.config.JmsListenerEndpoint;
import org.springframework.jms.listener.AbstractMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.listener.endpoint.JmsActivationSpecConfig;
import org.springframework.jms.listener.endpoint.JmsMessageEndpointManager;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

public abstract class AbstractJmsListenerEndpoint
implements JmsListenerEndpoint {
    private String id = "";
    @Nullable
    private String destination;
    @Nullable
    private String subscription;
    @Nullable
    private String selector;
    @Nullable
    private String concurrency;

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setDestination(@Nullable String destination) {
        this.destination = destination;
    }

    @Nullable
    public String getDestination() {
        return this.destination;
    }

    public void setSubscription(@Nullable String subscription) {
        this.subscription = subscription;
    }

    @Nullable
    public String getSubscription() {
        return this.subscription;
    }

    public void setSelector(@Nullable String selector) {
        this.selector = selector;
    }

    @Nullable
    public String getSelector() {
        return this.selector;
    }

    public void setConcurrency(@Nullable String concurrency) {
        this.concurrency = concurrency;
    }

    @Nullable
    public String getConcurrency() {
        return this.concurrency;
    }

    @Override
    public void setupListenerContainer(MessageListenerContainer listenerContainer) {
        if (listenerContainer instanceof AbstractMessageListenerContainer) {
            this.setupJmsListenerContainer((AbstractMessageListenerContainer)listenerContainer);
        } else {
            new JcaEndpointConfigurer().configureEndpoint(listenerContainer);
        }
    }

    private void setupJmsListenerContainer(AbstractMessageListenerContainer listenerContainer) {
        if (StringUtils.hasText((String)this.getId())) {
            listenerContainer.setBeanName(this.getId());
        }
        if (this.getDestination() != null) {
            listenerContainer.setDestinationName(this.getDestination());
        }
        if (this.getSubscription() != null) {
            listenerContainer.setSubscriptionName(this.getSubscription());
        }
        if (this.getSelector() != null) {
            listenerContainer.setMessageSelector(this.getSelector());
        }
        if (this.getConcurrency() != null) {
            listenerContainer.setConcurrency(this.getConcurrency());
        }
        this.setupMessageListener(listenerContainer);
    }

    protected abstract MessageListener createMessageListener(MessageListenerContainer var1);

    private void setupMessageListener(MessageListenerContainer container) {
        container.setupMessageListener(this.createMessageListener(container));
    }

    protected StringBuilder getEndpointDescription() {
        StringBuilder result = new StringBuilder();
        return result.append(this.getClass().getSimpleName()).append('[').append(this.id).append("] destination=").append(this.destination).append("' | subscription='").append(this.subscription).append(" | selector='").append(this.selector).append('\'');
    }

    public String toString() {
        return this.getEndpointDescription().toString();
    }

    private class JcaEndpointConfigurer {
        private JcaEndpointConfigurer() {
        }

        public void configureEndpoint(Object listenerContainer) {
            if (!(listenerContainer instanceof JmsMessageEndpointManager)) {
                throw new IllegalArgumentException("Could not configure endpoint with the specified container '" + listenerContainer + "' Only JMS (" + AbstractMessageListenerContainer.class.getName() + " subclass) or JCA (" + JmsMessageEndpointManager.class.getName() + ") are supported.");
            }
            this.setupJcaMessageContainer((JmsMessageEndpointManager)listenerContainer);
        }

        private void setupJcaMessageContainer(JmsMessageEndpointManager container) {
            JmsActivationSpecConfig activationSpecConfig = container.getActivationSpecConfig();
            if (activationSpecConfig == null) {
                activationSpecConfig = new JmsActivationSpecConfig();
                container.setActivationSpecConfig(activationSpecConfig);
            }
            if (AbstractJmsListenerEndpoint.this.getDestination() != null) {
                activationSpecConfig.setDestinationName(AbstractJmsListenerEndpoint.this.getDestination());
            }
            if (AbstractJmsListenerEndpoint.this.getSubscription() != null) {
                activationSpecConfig.setSubscriptionName(AbstractJmsListenerEndpoint.this.getSubscription());
            }
            if (AbstractJmsListenerEndpoint.this.getSelector() != null) {
                activationSpecConfig.setMessageSelector(AbstractJmsListenerEndpoint.this.getSelector());
            }
            if (AbstractJmsListenerEndpoint.this.getConcurrency() != null) {
                activationSpecConfig.setConcurrency(AbstractJmsListenerEndpoint.this.getConcurrency());
            }
            AbstractJmsListenerEndpoint.this.setupMessageListener(container);
        }
    }
}

