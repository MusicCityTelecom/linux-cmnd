/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.activemq.artemis.api.core.TransportConfiguration
 *  org.apache.activemq.artemis.api.core.client.ActiveMQClient
 *  org.apache.activemq.artemis.api.core.client.ServerLocator
 *  org.apache.activemq.artemis.core.remoting.impl.invm.InVMConnectorFactory
 *  org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.jms.artemis;

import java.lang.reflect.Constructor;
import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.api.core.client.ActiveMQClient;
import org.apache.activemq.artemis.api.core.client.ServerLocator;
import org.apache.activemq.artemis.core.remoting.impl.invm.InVMConnectorFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisMode;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisProperties;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

class ArtemisConnectionFactoryFactory {
    private static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";
    static final String[] EMBEDDED_JMS_CLASSES = new String[]{"org.apache.activemq.artemis.jms.server.embedded.EmbeddedJMS", "org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ"};
    private final ArtemisProperties properties;
    private final ListableBeanFactory beanFactory;

    ArtemisConnectionFactoryFactory(ListableBeanFactory beanFactory, ArtemisProperties properties) {
        Assert.notNull((Object)beanFactory, (String)"BeanFactory must not be null");
        Assert.notNull((Object)properties, (String)"Properties must not be null");
        this.beanFactory = beanFactory;
        this.properties = properties;
    }

    <T extends ActiveMQConnectionFactory> T createConnectionFactory(Class<T> factoryClass) {
        try {
            this.startEmbeddedJms();
            return this.doCreateConnectionFactory(factoryClass);
        }
        catch (Exception ex) {
            throw new IllegalStateException("Unable to create ActiveMQConnectionFactory", ex);
        }
    }

    private void startEmbeddedJms() {
        for (String embeddedJmsClass : EMBEDDED_JMS_CLASSES) {
            if (!ClassUtils.isPresent((String)embeddedJmsClass, null)) continue;
            try {
                this.beanFactory.getBeansOfType(Class.forName(embeddedJmsClass));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    private <T extends ActiveMQConnectionFactory> T doCreateConnectionFactory(Class<T> factoryClass) throws Exception {
        ArtemisMode mode = this.properties.getMode();
        if (mode == null) {
            mode = this.deduceMode();
        }
        if (mode == ArtemisMode.EMBEDDED) {
            return this.createEmbeddedConnectionFactory(factoryClass);
        }
        return this.createNativeConnectionFactory(factoryClass);
    }

    private ArtemisMode deduceMode() {
        if (this.properties.getEmbedded().isEnabled() && this.isEmbeddedJmsClassPresent()) {
            return ArtemisMode.EMBEDDED;
        }
        return ArtemisMode.NATIVE;
    }

    private boolean isEmbeddedJmsClassPresent() {
        for (String embeddedJmsClass : EMBEDDED_JMS_CLASSES) {
            if (!ClassUtils.isPresent((String)embeddedJmsClass, null)) continue;
            return true;
        }
        return false;
    }

    private <T extends ActiveMQConnectionFactory> T createEmbeddedConnectionFactory(Class<T> factoryClass) throws Exception {
        try {
            TransportConfiguration transportConfiguration = new TransportConfiguration(InVMConnectorFactory.class.getName(), this.properties.getEmbedded().generateTransportParameters());
            ServerLocator serviceLocator = ActiveMQClient.createServerLocatorWithoutHA((TransportConfiguration[])new TransportConfiguration[]{transportConfiguration});
            return (T)((ActiveMQConnectionFactory)factoryClass.getConstructor(ServerLocator.class).newInstance(serviceLocator));
        }
        catch (NoClassDefFoundError ex) {
            throw new IllegalStateException("Unable to create InVM Artemis connection, ensure that artemis-jms-server.jar is in the classpath", ex);
        }
    }

    private <T extends ActiveMQConnectionFactory> T createNativeConnectionFactory(Class<T> factoryClass) throws Exception {
        T connectionFactory = this.newNativeConnectionFactory(factoryClass);
        String user = this.properties.getUser();
        if (StringUtils.hasText((String)user)) {
            connectionFactory.setUser(user);
            connectionFactory.setPassword(this.properties.getPassword());
        }
        return connectionFactory;
    }

    private <T extends ActiveMQConnectionFactory> T newNativeConnectionFactory(Class<T> factoryClass) throws Exception {
        String brokerUrl = StringUtils.hasText((String)this.properties.getBrokerUrl()) ? this.properties.getBrokerUrl() : DEFAULT_BROKER_URL;
        Constructor<T> constructor = factoryClass.getConstructor(String.class);
        return (T)((ActiveMQConnectionFactory)constructor.newInstance(brokerUrl));
    }
}

