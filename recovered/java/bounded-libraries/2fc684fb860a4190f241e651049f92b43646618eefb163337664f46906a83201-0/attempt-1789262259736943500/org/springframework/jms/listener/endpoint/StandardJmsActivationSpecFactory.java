/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.JMSException
 *  javax.jms.Queue
 *  javax.jms.Topic
 *  javax.resource.spi.ActivationSpec
 *  javax.resource.spi.ResourceAdapter
 *  org.springframework.beans.BeanUtils
 *  org.springframework.beans.BeanWrapper
 *  org.springframework.beans.PropertyAccessorFactory
 *  org.springframework.lang.Nullable
 */
package org.springframework.jms.listener.endpoint;

import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.ResourceAdapter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.jms.listener.endpoint.JmsActivationSpecConfig;
import org.springframework.jms.listener.endpoint.JmsActivationSpecFactory;
import org.springframework.jms.support.destination.DestinationResolutionException;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.lang.Nullable;

public class StandardJmsActivationSpecFactory
implements JmsActivationSpecFactory {
    @Nullable
    private Class<?> activationSpecClass;
    @Nullable
    private Map<String, String> defaultProperties;
    @Nullable
    private DestinationResolver destinationResolver;

    public void setActivationSpecClass(Class<?> activationSpecClass) {
        this.activationSpecClass = activationSpecClass;
    }

    public void setDefaultProperties(Map<String, String> defaultProperties) {
        this.defaultProperties = defaultProperties;
    }

    public void setDestinationResolver(@Nullable DestinationResolver destinationResolver) {
        this.destinationResolver = destinationResolver;
    }

    @Nullable
    public DestinationResolver getDestinationResolver() {
        return this.destinationResolver;
    }

    @Override
    public ActivationSpec createActivationSpec(ResourceAdapter adapter, JmsActivationSpecConfig config) {
        Class<?> activationSpecClassToUse = this.activationSpecClass;
        if (activationSpecClassToUse == null && (activationSpecClassToUse = this.determineActivationSpecClass(adapter)) == null) {
            throw new IllegalStateException("Property 'activationSpecClass' is required");
        }
        ActivationSpec spec = (ActivationSpec)BeanUtils.instantiateClass(activationSpecClassToUse);
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess((Object)spec);
        if (this.defaultProperties != null) {
            bw.setPropertyValues(this.defaultProperties);
        }
        this.populateActivationSpecProperties(bw, config);
        return spec;
    }

    @Nullable
    protected Class<?> determineActivationSpecClass(ResourceAdapter adapter) {
        return null;
    }

    protected void populateActivationSpecProperties(BeanWrapper bw, JmsActivationSpecConfig config) {
        String destinationName = config.getDestinationName();
        if (destinationName != null) {
            boolean pubSubDomain = config.isPubSubDomain();
            String destination = destinationName;
            if (this.destinationResolver != null) {
                try {
                    destination = this.destinationResolver.resolveDestinationName(null, destinationName, pubSubDomain);
                }
                catch (JMSException ex) {
                    throw new DestinationResolutionException("Cannot resolve destination name [" + destinationName + "]", ex);
                }
            }
            bw.setPropertyValue("destination", (Object)destination);
            bw.setPropertyValue("destinationType", (Object)(pubSubDomain ? Topic.class.getName() : Queue.class.getName()));
        }
        if (bw.isWritableProperty("subscriptionDurability")) {
            bw.setPropertyValue("subscriptionDurability", (Object)(config.isSubscriptionDurable() ? "Durable" : "NonDurable"));
        } else if (config.isSubscriptionDurable()) {
            throw new IllegalArgumentException("Durable subscriptions not supported by underlying provider");
        }
        if (config.isSubscriptionShared()) {
            throw new IllegalArgumentException("Shared subscriptions not supported for JCA-driven endpoints");
        }
        if (config.getSubscriptionName() != null) {
            bw.setPropertyValue("subscriptionName", (Object)config.getSubscriptionName());
        }
        if (config.getClientId() != null) {
            bw.setPropertyValue("clientId", (Object)config.getClientId());
        }
        if (config.getMessageSelector() != null) {
            bw.setPropertyValue("messageSelector", (Object)config.getMessageSelector());
        }
        this.applyAcknowledgeMode(bw, config.getAcknowledgeMode());
    }

    protected void applyAcknowledgeMode(BeanWrapper bw, int ackMode) {
        if (ackMode == 0) {
            throw new IllegalArgumentException("No support for SESSION_TRANSACTED: Only \"Auto-acknowledge\" and \"Dups-ok-acknowledge\" supported in standard JCA 1.5");
        }
        if (ackMode == 2) {
            throw new IllegalArgumentException("No support for CLIENT_ACKNOWLEDGE: Only \"Auto-acknowledge\" and \"Dups-ok-acknowledge\" supported in standard JCA 1.5");
        }
        if (bw.isWritableProperty("acknowledgeMode")) {
            bw.setPropertyValue("acknowledgeMode", (Object)(ackMode == 3 ? "Dups-ok-acknowledge" : "Auto-acknowledge"));
        } else if (ackMode == 3) {
            throw new IllegalArgumentException("Dups-ok-acknowledge not supported by underlying provider");
        }
    }
}

