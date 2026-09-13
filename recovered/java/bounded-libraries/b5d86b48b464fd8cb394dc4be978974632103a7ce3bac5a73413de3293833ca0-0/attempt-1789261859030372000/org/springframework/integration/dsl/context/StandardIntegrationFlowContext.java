/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.context.SmartLifecycle
 *  org.springframework.core.type.MethodMetadata
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.dsl.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.type.MethodMetadata;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.dsl.context.StandardIntegrationFlowRegistration;
import org.springframework.integration.support.context.NamedComponent;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public final class StandardIntegrationFlowContext
implements IntegrationFlowContext,
BeanFactoryAware {
    private final Map<String, IntegrationFlowContext.IntegrationFlowRegistration> registry = new ConcurrentHashMap<String, IntegrationFlowContext.IntegrationFlowRegistration>();
    private final Map<String, Boolean> useFlowIdAsPrefix = new ConcurrentHashMap<String, Boolean>();
    private final Lock registerFlowsLock = new ReentrantLock();
    private ConfigurableListableBeanFactory beanFactory;
    private BeanDefinitionRegistry beanDefinitionRegistry;

    StandardIntegrationFlowContext() {
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        Assert.isInstanceOf(ConfigurableListableBeanFactory.class, (Object)beanFactory, (String)"To use Spring Integration Java DSL the 'beanFactory' has to be an instance of 'ConfigurableListableBeanFactory'. Consider using 'GenericApplicationContext' implementation.");
        this.beanFactory = (ConfigurableListableBeanFactory)beanFactory;
        this.beanDefinitionRegistry = (BeanDefinitionRegistry)this.beanFactory;
    }

    @Override
    public StandardIntegrationFlowRegistrationBuilder registration(IntegrationFlow integrationFlow2) {
        return new StandardIntegrationFlowRegistrationBuilder(integrationFlow2);
    }

    @Override
    public boolean isUseIdAsPrefix(String flowId) {
        return Boolean.TRUE.equals(this.useFlowIdAsPrefix.get(flowId));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private IntegrationFlowContext.IntegrationFlowRegistration register(StandardIntegrationFlowRegistrationBuilder builder) {
        IntegrationFlow integrationFlow2 = builder.integrationFlow;
        String flowId = builder.id;
        Lock registerBeanLock = null;
        try {
            if (flowId == null) {
                registerBeanLock = this.registerFlowsLock;
                registerBeanLock.lock();
                flowId = this.generateBeanName(integrationFlow2, null);
                builder.id(flowId);
            } else if (this.registry.containsKey(flowId)) {
                throw new IllegalArgumentException("An IntegrationFlow '" + this.registry.get(flowId) + "' with flowId '" + flowId + "' is already registered.\nAn existing IntegrationFlowRegistration must be destroyed before overriding.");
            }
            integrationFlow2 = this.registerFlowBean(integrationFlow2, flowId, builder.source);
        }
        finally {
            if (registerBeanLock != null) {
                registerBeanLock.unlock();
            }
        }
        builder.integrationFlow = integrationFlow2;
        String theFlowId = flowId;
        builder.additionalBeans.forEach((key, value) -> this.registerBean(key, (String)value, theFlowId));
        StandardIntegrationFlowRegistration registration = new StandardIntegrationFlowRegistration(integrationFlow2, this, flowId);
        if (builder.autoStartup) {
            registration.start();
            builder.additionalBeans.keySet().stream().filter(SmartLifecycle.class::isInstance).filter(lifecycle -> ((SmartLifecycle)lifecycle).isAutoStartup()).forEach(lifecycle -> ((SmartLifecycle)lifecycle).start());
        }
        this.registry.put(flowId, registration);
        return registration;
    }

    private IntegrationFlow registerFlowBean(IntegrationFlow flow, String flowId, @Nullable Object source) {
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(IntegrationFlow.class, () -> flow).getRawBeanDefinition();
        beanDefinition.setSource(source);
        this.beanDefinitionRegistry.registerBeanDefinition(flowId, (BeanDefinition)beanDefinition);
        return (IntegrationFlow)this.beanFactory.getBean(flowId, IntegrationFlow.class);
    }

    private void registerBean(Object bean, @Nullable String beanNameArg, String parentName) {
        String beanName = beanNameArg;
        if (beanName == null) {
            beanName = this.generateBeanName(bean, parentName);
        }
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(bean.getClass(), () -> bean).getRawBeanDefinition();
        if (parentName != null) {
            AbstractBeanDefinition parentBeanDefinition = (AbstractBeanDefinition)this.beanFactory.getBeanDefinition(parentName);
            Object source = parentBeanDefinition.getSource();
            if (source instanceof MethodMetadata) {
                source = "bean method " + ((MethodMetadata)source).getMethodName();
            }
            beanDefinition.setSource(source);
            this.beanFactory.registerDependentBean(parentName, beanName);
        }
        this.beanDefinitionRegistry.registerBeanDefinition(beanName, (BeanDefinition)beanDefinition);
        this.beanFactory.getBean(beanName);
    }

    @Override
    public IntegrationFlowContext.IntegrationFlowRegistration getRegistrationById(String flowId) {
        return this.registry.get(flowId);
    }

    @Override
    public void remove(String flowId) {
        IntegrationFlowContext.IntegrationFlowRegistration flowRegistration = this.registry.remove(flowId);
        if (flowRegistration == null) {
            throw new IllegalStateException("An IntegrationFlow with the id [" + flowId + "] doesn't exist in the registry.");
        }
        flowRegistration.stop();
        this.removeDependantBeans(flowId);
        this.beanDefinitionRegistry.removeBeanDefinition(flowId);
    }

    private void removeDependantBeans(String parentName) {
        String[] dependentBeans;
        for (String beanName : dependentBeans = this.beanFactory.getDependentBeans(parentName)) {
            String[] aliases;
            this.removeDependantBeans(beanName);
            this.beanDefinitionRegistry.removeBeanDefinition(beanName);
            for (String alias : aliases = this.beanDefinitionRegistry.getAliases(beanName)) {
                this.beanDefinitionRegistry.removeAlias(alias);
            }
        }
    }

    @Override
    public MessagingTemplate messagingTemplateFor(String flowId) {
        return this.registry.get(flowId).getMessagingTemplate();
    }

    @Override
    public Map<String, IntegrationFlowContext.IntegrationFlowRegistration> getRegistry() {
        return Collections.unmodifiableMap(this.registry);
    }

    private String generateBeanName(Object instance, String parentName) {
        String generatedBeanName;
        String beanName;
        if (instance instanceof NamedComponent && (beanName = ((NamedComponent)instance).getBeanName()) != null) {
            return beanName;
        }
        String id = generatedBeanName = (parentName != null ? parentName : "") + instance.getClass().getName();
        int counter = -1;
        while (counter == -1 || this.beanFactory.containsBean(id)) {
            id = generatedBeanName + "#" + ++counter;
        }
        return id;
    }

    public final class StandardIntegrationFlowRegistrationBuilder
    implements IntegrationFlowContext.IntegrationFlowRegistrationBuilder {
        private final Map<Object, String> additionalBeans = new HashMap<Object, String>();
        private IntegrationFlow integrationFlow;
        private String id;
        private boolean autoStartup = true;
        private boolean idAsPrefix;
        @Nullable
        private Object source;

        StandardIntegrationFlowRegistrationBuilder(IntegrationFlow integrationFlow2) {
            this.integrationFlow = integrationFlow2;
        }

        @Override
        public StandardIntegrationFlowRegistrationBuilder id(String id) {
            this.id = id;
            return this;
        }

        @Override
        public StandardIntegrationFlowRegistrationBuilder autoStartup(boolean autoStartupToSet) {
            this.autoStartup = autoStartupToSet;
            return this;
        }

        @Override
        public StandardIntegrationFlowRegistrationBuilder addBean(Object bean) {
            return this.addBean(null, bean);
        }

        @Override
        public StandardIntegrationFlowRegistrationBuilder addBean(String name, Object bean) {
            this.additionalBeans.put(bean, name);
            return this;
        }

        @Override
        public IntegrationFlowContext.IntegrationFlowRegistrationBuilder setSource(Object source) {
            this.source = source;
            return this;
        }

        @Override
        public IntegrationFlowContext.IntegrationFlowRegistrationBuilder useFlowIdAsPrefix() {
            this.idAsPrefix = true;
            return this;
        }

        @Override
        public IntegrationFlowContext.IntegrationFlowRegistration register() {
            Assert.state((!this.idAsPrefix || StringUtils.hasText((String)this.id) ? 1 : 0) != 0, (String)"An 'id' must be present to use 'useFlowIdAsPrefix'");
            if (this.idAsPrefix) {
                StandardIntegrationFlowContext.this.useFlowIdAsPrefix.put(this.id, this.idAsPrefix);
            }
            IntegrationFlowContext.IntegrationFlowRegistration registration = StandardIntegrationFlowContext.this.register(this);
            registration.setBeanFactory((BeanFactory)StandardIntegrationFlowContext.this.beanFactory);
            return registration;
        }
    }
}

