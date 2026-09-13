/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.beans.factory.config.ConfigurableBeanFactory
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory
 *  org.springframework.util.Assert
 */
package org.springframework.jms.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpoint;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.lang.Nullable;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import org.springframework.util.Assert;

public class JmsListenerEndpointRegistrar
implements BeanFactoryAware,
InitializingBean {
    @Nullable
    private JmsListenerEndpointRegistry endpointRegistry;
    @Nullable
    private MessageHandlerMethodFactory messageHandlerMethodFactory;
    @Nullable
    private JmsListenerContainerFactory<?> containerFactory;
    @Nullable
    private String containerFactoryBeanName;
    @Nullable
    private BeanFactory beanFactory;
    private final List<JmsListenerEndpointDescriptor> endpointDescriptors;
    private boolean startImmediately;
    private Object mutex;

    public JmsListenerEndpointRegistrar() {
        this.mutex = this.endpointDescriptors = new ArrayList<JmsListenerEndpointDescriptor>();
    }

    public void setEndpointRegistry(@Nullable JmsListenerEndpointRegistry endpointRegistry) {
        this.endpointRegistry = endpointRegistry;
    }

    @Nullable
    public JmsListenerEndpointRegistry getEndpointRegistry() {
        return this.endpointRegistry;
    }

    public void setMessageHandlerMethodFactory(@Nullable MessageHandlerMethodFactory messageHandlerMethodFactory) {
        this.messageHandlerMethodFactory = messageHandlerMethodFactory;
    }

    @Nullable
    public MessageHandlerMethodFactory getMessageHandlerMethodFactory() {
        return this.messageHandlerMethodFactory;
    }

    public void setContainerFactory(JmsListenerContainerFactory<?> containerFactory) {
        this.containerFactory = containerFactory;
    }

    public void setContainerFactoryBeanName(String containerFactoryBeanName) {
        this.containerFactoryBeanName = containerFactoryBeanName;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        if (beanFactory instanceof ConfigurableBeanFactory) {
            this.mutex = ((ConfigurableBeanFactory)beanFactory).getSingletonMutex();
        }
    }

    public void afterPropertiesSet() {
        this.registerAllEndpoints();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void registerAllEndpoints() {
        Assert.state((this.endpointRegistry != null ? 1 : 0) != 0, (String)"No JmsListenerEndpointRegistry set");
        Object object = this.mutex;
        synchronized (object) {
            for (JmsListenerEndpointDescriptor descriptor : this.endpointDescriptors) {
                this.endpointRegistry.registerListenerContainer(descriptor.endpoint, this.resolveContainerFactory(descriptor));
            }
            this.startImmediately = true;
        }
    }

    private JmsListenerContainerFactory<?> resolveContainerFactory(JmsListenerEndpointDescriptor descriptor) {
        if (descriptor.containerFactory != null) {
            return descriptor.containerFactory;
        }
        if (this.containerFactory != null) {
            return this.containerFactory;
        }
        if (this.containerFactoryBeanName != null) {
            Assert.state((this.beanFactory != null ? 1 : 0) != 0, (String)"BeanFactory must be set to obtain container factory by bean name");
            this.containerFactory = (JmsListenerContainerFactory)this.beanFactory.getBean(this.containerFactoryBeanName, JmsListenerContainerFactory.class);
            return this.containerFactory;
        }
        throw new IllegalStateException("Could not resolve the " + JmsListenerContainerFactory.class.getSimpleName() + " to use for [" + descriptor.endpoint + "] no factory was given and no default is set.");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void registerEndpoint(JmsListenerEndpoint endpoint, @Nullable JmsListenerContainerFactory<?> factory) {
        Assert.notNull((Object)endpoint, (String)"Endpoint must not be null");
        Assert.hasText((String)endpoint.getId(), (String)"Endpoint id must be set");
        JmsListenerEndpointDescriptor descriptor = new JmsListenerEndpointDescriptor(endpoint, factory);
        Object object = this.mutex;
        synchronized (object) {
            if (this.startImmediately) {
                Assert.state((this.endpointRegistry != null ? 1 : 0) != 0, (String)"No JmsListenerEndpointRegistry set");
                this.endpointRegistry.registerListenerContainer(descriptor.endpoint, this.resolveContainerFactory(descriptor), true);
            } else {
                this.endpointDescriptors.add(descriptor);
            }
        }
    }

    public void registerEndpoint(JmsListenerEndpoint endpoint) {
        this.registerEndpoint(endpoint, null);
    }

    private static class JmsListenerEndpointDescriptor {
        public final JmsListenerEndpoint endpoint;
        @Nullable
        public final JmsListenerContainerFactory<?> containerFactory;

        public JmsListenerEndpointDescriptor(JmsListenerEndpoint endpoint, @Nullable JmsListenerContainerFactory<?> containerFactory) {
            this.endpoint = endpoint;
            this.containerFactory = containerFactory;
        }
    }
}

