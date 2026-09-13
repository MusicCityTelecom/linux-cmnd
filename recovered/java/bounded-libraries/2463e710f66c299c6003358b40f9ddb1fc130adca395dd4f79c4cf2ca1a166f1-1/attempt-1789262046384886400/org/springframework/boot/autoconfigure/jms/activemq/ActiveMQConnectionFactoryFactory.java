/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.activemq.ActiveMQConnectionFactory
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.jms.activemq;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQConnectionFactoryCustomizer;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

class ActiveMQConnectionFactoryFactory {
    private static final String DEFAULT_EMBEDDED_BROKER_URL = "vm://localhost?broker.persistent=false";
    private static final String DEFAULT_NETWORK_BROKER_URL = "tcp://localhost:61616";
    private final ActiveMQProperties properties;
    private final List<ActiveMQConnectionFactoryCustomizer> factoryCustomizers;

    ActiveMQConnectionFactoryFactory(ActiveMQProperties properties, List<ActiveMQConnectionFactoryCustomizer> factoryCustomizers) {
        Assert.notNull((Object)properties, (String)"Properties must not be null");
        this.properties = properties;
        this.factoryCustomizers = factoryCustomizers != null ? factoryCustomizers : Collections.emptyList();
    }

    <T extends ActiveMQConnectionFactory> T createConnectionFactory(Class<T> factoryClass) {
        try {
            return this.doCreateConnectionFactory(factoryClass);
        }
        catch (Exception ex) {
            throw new IllegalStateException("Unable to create ActiveMQConnectionFactory", ex);
        }
    }

    private <T extends ActiveMQConnectionFactory> T doCreateConnectionFactory(Class<T> factoryClass) throws Exception {
        ActiveMQProperties.Packages packages;
        T factory = this.createConnectionFactoryInstance(factoryClass);
        if (this.properties.getCloseTimeout() != null) {
            factory.setCloseTimeout((int)this.properties.getCloseTimeout().toMillis());
        }
        factory.setNonBlockingRedelivery(this.properties.isNonBlockingRedelivery());
        if (this.properties.getSendTimeout() != null) {
            factory.setSendTimeout((int)this.properties.getSendTimeout().toMillis());
        }
        if ((packages = this.properties.getPackages()).getTrustAll() != null) {
            factory.setTrustAllPackages(packages.getTrustAll().booleanValue());
        }
        if (!packages.getTrusted().isEmpty()) {
            factory.setTrustedPackages(packages.getTrusted());
        }
        this.customize((ActiveMQConnectionFactory)factory);
        return factory;
    }

    private <T extends ActiveMQConnectionFactory> T createConnectionFactoryInstance(Class<T> factoryClass) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String brokerUrl = this.determineBrokerUrl();
        String user = this.properties.getUser();
        String password = this.properties.getPassword();
        if (StringUtils.hasLength((String)user) && StringUtils.hasLength((String)password)) {
            return (T)((ActiveMQConnectionFactory)factoryClass.getConstructor(String.class, String.class, String.class).newInstance(user, password, brokerUrl));
        }
        return (T)((ActiveMQConnectionFactory)factoryClass.getConstructor(String.class).newInstance(brokerUrl));
    }

    private void customize(ActiveMQConnectionFactory connectionFactory) {
        for (ActiveMQConnectionFactoryCustomizer factoryCustomizer : this.factoryCustomizers) {
            factoryCustomizer.customize(connectionFactory);
        }
    }

    String determineBrokerUrl() {
        if (this.properties.getBrokerUrl() != null) {
            return this.properties.getBrokerUrl();
        }
        if (this.properties.isInMemory()) {
            return DEFAULT_EMBEDDED_BROKER_URL;
        }
        return DEFAULT_NETWORK_BROKER_URL;
    }
}

