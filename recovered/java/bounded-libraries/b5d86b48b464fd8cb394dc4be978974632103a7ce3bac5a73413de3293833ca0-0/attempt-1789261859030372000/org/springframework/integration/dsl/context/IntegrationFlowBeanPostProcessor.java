/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.aop.Advice
 *  org.springframework.aop.Advisor
 *  org.springframework.aop.framework.ProxyFactory
 *  org.springframework.aop.support.NameMatchMethodPointcutAdvisor
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.Aware
 *  org.springframework.beans.factory.BeanClassLoaderAware
 *  org.springframework.beans.factory.BeanCreationNotAllowedException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.BeanNameAware
 *  org.springframework.beans.factory.SmartInitializingSingleton
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.BeanDefinitionCustomizer
 *  org.springframework.beans.factory.config.BeanPostProcessor
 *  org.springframework.beans.factory.config.ConfigurableBeanFactory
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.config.EmbeddedValueResolver
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionOverrideException
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.context.ApplicationEventPublisher
 *  org.springframework.context.ApplicationEventPublisherAware
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.EmbeddedValueResolverAware
 *  org.springframework.context.EnvironmentAware
 *  org.springframework.context.MessageSource
 *  org.springframework.context.MessageSourceAware
 *  org.springframework.context.ResourceLoaderAware
 *  org.springframework.context.SmartLifecycle
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.DescriptiveResource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.type.MethodMetadata
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.StringUtils
 *  org.springframework.util.StringValueResolver
 *  reactor.util.function.Tuple2
 */
package org.springframework.integration.dsl.context;

import java.util.LinkedHashMap;
import java.util.Map;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.EmbeddedValueResolver;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionOverrideException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DescriptiveResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.MethodMetadata;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.FixedSubscriberChannel;
import org.springframework.integration.channel.NullChannel;
import org.springframework.integration.config.ConsumerEndpointFactoryBean;
import org.springframework.integration.config.SourcePollingChannelAdapterFactoryBean;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.ComponentsRegistration;
import org.springframework.integration.dsl.ConsumerEndpointSpec;
import org.springframework.integration.dsl.IntegrationComponentSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowAdapter;
import org.springframework.integration.dsl.IntegrationFlowBuilder;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.dsl.context.IntegrationFlowLifecycleAdvice;
import org.springframework.integration.dsl.support.MessageChannelReference;
import org.springframework.integration.gateway.AnnotationGatewayProxyFactoryBean;
import org.springframework.integration.support.context.NamedComponent;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.StringValueResolver;
import reactor.util.function.Tuple2;

public class IntegrationFlowBeanPostProcessor
implements BeanPostProcessor,
ApplicationContextAware,
SmartInitializingSingleton {
    private ConfigurableApplicationContext applicationContext;
    private StringValueResolver embeddedValueResolver;
    private ConfigurableListableBeanFactory beanFactory;
    private volatile IntegrationFlowContext flowContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Assert.isInstanceOf(ConfigurableApplicationContext.class, (Object)applicationContext, (String)"To use Spring Integration Java DSL the 'applicationContext' has to be an instance of 'ConfigurableApplicationContext'. Consider using 'GenericApplicationContext' implementation.");
        this.applicationContext = (ConfigurableApplicationContext)applicationContext;
        this.beanFactory = this.applicationContext.getBeanFactory();
        this.embeddedValueResolver = new EmbeddedValueResolver((ConfigurableBeanFactory)this.beanFactory);
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof StandardIntegrationFlow) {
            return this.processStandardIntegrationFlow((StandardIntegrationFlow)bean, beanName);
        }
        if (bean instanceof IntegrationFlow) {
            return this.processIntegrationFlowImpl((IntegrationFlow)bean, beanName);
        }
        if (bean instanceof IntegrationComponentSpec) {
            this.processIntegrationComponentSpec(beanName, (IntegrationComponentSpec)((Object)bean));
        }
        return bean;
    }

    public void afterSingletonsInstantiated() {
        for (String beanName : this.beanFactory.getBeanNamesForType(IntegrationFlow.class)) {
            String scope;
            if (!this.beanFactory.containsBeanDefinition(beanName) || !StringUtils.hasText((String)(scope = this.beanFactory.getBeanDefinition(beanName).getScope())) || "singleton".equals(scope)) continue;
            throw new BeanCreationNotAllowedException(beanName, "IntegrationFlows can not be scoped beans. Any dependant beans are registered as singletons, meanwhile IntegrationFlow is just a logical container for them. \nConsider using [IntegrationFlowContext] for manual registration of IntegrationFlows.");
        }
    }

    private Object processStandardIntegrationFlow(StandardIntegrationFlow flow, String flowBeanName) {
        String flowNamePrefix = flowBeanName + ".";
        if (this.flowContext == null) {
            this.flowContext = (IntegrationFlowContext)this.beanFactory.getBean(IntegrationFlowContext.class);
        }
        boolean useFlowIdAsPrefix = this.flowContext.isUseIdAsPrefix(flowBeanName);
        int subFlowNameIndex = 0;
        int channelNameIndex = 0;
        Map<Object, String> integrationComponents = flow.getIntegrationComponents();
        LinkedHashMap<Object, String> targetIntegrationComponents = new LinkedHashMap<Object, String>(integrationComponents.size());
        for (Map.Entry<Object, String> entry : integrationComponents.entrySet()) {
            String channelBeanName;
            String id;
            Object component = entry.getKey();
            if (component instanceof ConsumerEndpointSpec) {
                ConsumerEndpointSpec endpointSpec = (ConsumerEndpointSpec)component;
                MessageHandler messageHandler = (MessageHandler)((Tuple2)endpointSpec.get()).getT2();
                ConsumerEndpointFactoryBean endpoint = (ConsumerEndpointFactoryBean)((Tuple2)endpointSpec.get()).getT1();
                id = endpointSpec.getId();
                if (id == null) {
                    id = this.generateBeanName(endpoint, flowNamePrefix, entry.getValue(), useFlowIdAsPrefix);
                } else if (useFlowIdAsPrefix) {
                    id = flowNamePrefix + id;
                }
                if (this.noBeanPresentForComponent(messageHandler, flowBeanName)) {
                    String handlerBeanName = this.generateBeanName(messageHandler, flowNamePrefix);
                    this.registerComponent(messageHandler, handlerBeanName, flowBeanName, new BeanDefinitionCustomizer[0]);
                    this.beanFactory.registerAlias(handlerBeanName, id + ".handler");
                }
                this.registerComponent(endpoint, id, flowBeanName, new BeanDefinitionCustomizer[0]);
                targetIntegrationComponents.put(endpoint, id);
                continue;
            }
            if (component instanceof MessageChannelReference) {
                channelBeanName = ((MessageChannelReference)component).getName();
                if (this.beanFactory.containsBean(channelBeanName)) continue;
                DirectChannel directChannel = new DirectChannel();
                this.registerComponent(directChannel, channelBeanName, flowBeanName, new BeanDefinitionCustomizer[0]);
                targetIntegrationComponents.put(directChannel, channelBeanName);
                continue;
            }
            if (component instanceof SourcePollingChannelAdapterSpec) {
                SourcePollingChannelAdapterSpec spec = (SourcePollingChannelAdapterSpec)component;
                Map<Object, String> componentsToRegister = spec.getComponentsToRegister();
                if (!CollectionUtils.isEmpty(componentsToRegister)) {
                    componentsToRegister.entrySet().stream().filter(o -> this.noBeanPresentForComponent(o.getKey(), flowBeanName)).forEach(o -> this.registerComponent(o.getKey(), this.generateBeanName(o.getKey(), flowNamePrefix, (String)o.getValue(), useFlowIdAsPrefix)));
                }
                SourcePollingChannelAdapterFactoryBean pollingChannelAdapterFactoryBean = (SourcePollingChannelAdapterFactoryBean)((Tuple2)spec.get()).getT1();
                id = spec.getId();
                if (id == null) {
                    id = this.generateBeanName(pollingChannelAdapterFactoryBean, flowNamePrefix, entry.getValue(), useFlowIdAsPrefix);
                } else if (useFlowIdAsPrefix) {
                    id = flowNamePrefix + id;
                }
                this.registerComponent(pollingChannelAdapterFactoryBean, id, flowBeanName, new BeanDefinitionCustomizer[0]);
                targetIntegrationComponents.put(pollingChannelAdapterFactoryBean, id);
                MessageSource messageSource = (MessageSource)((Tuple2)spec.get()).getT2();
                if (!this.noBeanPresentForComponent(messageSource, flowBeanName)) continue;
                String messageSourceId = id + ".source";
                if (messageSource instanceof NamedComponent && ((NamedComponent)((Object)messageSource)).getComponentName() != null) {
                    messageSourceId = ((NamedComponent)((Object)messageSource)).getComponentName();
                }
                this.registerComponent(messageSource, messageSourceId, flowBeanName, new BeanDefinitionCustomizer[0]);
                continue;
            }
            if (this.noBeanPresentForComponent(component, flowBeanName)) {
                if (component instanceof AbstractMessageChannel || component instanceof NullChannel) {
                    channelBeanName = ((NamedComponent)component).getComponentName();
                    if (channelBeanName == null && (channelBeanName = entry.getValue()) == null) {
                        channelBeanName = flowNamePrefix + "channel" + "#" + channelNameIndex++;
                    }
                    this.registerComponent(component, channelBeanName, flowBeanName, new BeanDefinitionCustomizer[0]);
                    targetIntegrationComponents.put(component, channelBeanName);
                    continue;
                }
                if (component instanceof FixedSubscriberChannel) {
                    FixedSubscriberChannel fixedSubscriberChannel = (FixedSubscriberChannel)component;
                    String channelBeanName2 = fixedSubscriberChannel.getComponentName();
                    if ("Unnamed fixed subscriber channel".equals(channelBeanName2)) {
                        channelBeanName2 = flowNamePrefix + "channel" + "#" + channelNameIndex++;
                    }
                    this.registerComponent(component, channelBeanName2, flowBeanName, new BeanDefinitionCustomizer[0]);
                    targetIntegrationComponents.put(component, channelBeanName2);
                    continue;
                }
                if (component instanceof StandardIntegrationFlow) {
                    String subFlowBeanName = entry.getValue() != null ? entry.getValue() : flowNamePrefix + "subFlow" + "#" + subFlowNameIndex++;
                    this.registerComponent(component, subFlowBeanName, flowBeanName, new BeanDefinitionCustomizer[0]);
                    targetIntegrationComponents.put(component, subFlowBeanName);
                    continue;
                }
                if (component instanceof AnnotationGatewayProxyFactoryBean) {
                    AnnotationGatewayProxyFactoryBean gateway2 = (AnnotationGatewayProxyFactoryBean)component;
                    String gatewayId = entry.getValue();
                    if (gatewayId == null) {
                        gatewayId = gateway2.getComponentName();
                    }
                    if (gatewayId == null) {
                        gatewayId = flowNamePrefix + "gateway";
                    }
                    this.registerComponent(gateway2, gatewayId, flowBeanName, beanDefinition -> ((AbstractBeanDefinition)beanDefinition).setSource((Object)new DescriptiveResource("" + gateway2.getObjectType())));
                    targetIntegrationComponents.put(component, gatewayId);
                    continue;
                }
                String generatedBeanName = this.generateBeanName(component, flowNamePrefix, entry.getValue(), useFlowIdAsPrefix);
                this.registerComponent(component, generatedBeanName, flowBeanName, new BeanDefinitionCustomizer[0]);
                targetIntegrationComponents.put(component, generatedBeanName);
                continue;
            }
            Object componentToUse = entry.getKey();
            String beanNameToUse = entry.getValue();
            if (StringUtils.hasText((String)beanNameToUse) && "prototype".equals(IntegrationContextUtils.getBeanDefinition(beanNameToUse, this.beanFactory).getScope())) {
                this.beanFactory.initializeBean(componentToUse, beanNameToUse);
            }
            targetIntegrationComponents.put(component, beanNameToUse);
        }
        flow.setIntegrationComponents(targetIntegrationComponents);
        return flow;
    }

    private Object processIntegrationFlowImpl(IntegrationFlow flow, String beanName) {
        IntegrationFlowBuilder flowBuilder2 = IntegrationFlows.from(beanName + ".input");
        flow.configure(flowBuilder2);
        StandardIntegrationFlow target = flowBuilder2.get();
        this.processStandardIntegrationFlow(target, beanName);
        if (!(flow instanceof IntegrationFlowAdapter)) {
            NameMatchMethodPointcutAdvisor integrationFlowAdvice = new NameMatchMethodPointcutAdvisor((Advice)new IntegrationFlowLifecycleAdvice(target));
            integrationFlowAdvice.setMappedNames(new String[]{"getInputChannel", "getIntegrationComponents", "start", "stop", "isRunning", "isAutoStartup", "getPhase"});
            ProxyFactory proxyFactory = new ProxyFactory((Object)flow);
            proxyFactory.addAdvisor((Advisor)integrationFlowAdvice);
            if (!(flow instanceof SmartLifecycle)) {
                proxyFactory.addInterface(SmartLifecycle.class);
            }
            return proxyFactory.getProxy(this.beanFactory.getBeanClassLoader());
        }
        return flow;
    }

    private void processIntegrationComponentSpec(String beanName, IntegrationComponentSpec<?, ?> bean) {
        Map<Object, String> componentsToRegister;
        Object target = bean.get();
        this.invokeBeanInitializationHooks(beanName, target);
        if (bean instanceof ComponentsRegistration && !CollectionUtils.isEmpty(componentsToRegister = ((ComponentsRegistration)((Object)bean)).getComponentsToRegister())) {
            componentsToRegister.entrySet().stream().filter(component -> this.noBeanPresentForComponent(component.getKey(), beanName)).forEach(component -> this.registerComponent(component.getKey(), this.generateBeanName(component.getKey(), (String)component.getValue())));
        }
    }

    private void invokeBeanInitializationHooks(String beanName, Object bean) {
        if (bean instanceof Aware) {
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware)bean).setBeanName(beanName);
            }
            if (bean instanceof BeanClassLoaderAware && this.beanFactory.getBeanClassLoader() != null) {
                ((BeanClassLoaderAware)bean).setBeanClassLoader(this.beanFactory.getBeanClassLoader());
            }
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware)bean).setBeanFactory((BeanFactory)this.beanFactory);
            }
            if (bean instanceof EnvironmentAware) {
                ((EnvironmentAware)bean).setEnvironment((Environment)this.applicationContext.getEnvironment());
            }
            if (bean instanceof EmbeddedValueResolverAware) {
                ((EmbeddedValueResolverAware)bean).setEmbeddedValueResolver(this.embeddedValueResolver);
            }
            if (bean instanceof ResourceLoaderAware) {
                ((ResourceLoaderAware)bean).setResourceLoader((ResourceLoader)this.applicationContext);
            }
            if (bean instanceof ApplicationEventPublisherAware) {
                ((ApplicationEventPublisherAware)bean).setApplicationEventPublisher((ApplicationEventPublisher)this.applicationContext);
            }
            if (bean instanceof MessageSourceAware) {
                ((MessageSourceAware)bean).setMessageSource((org.springframework.context.MessageSource)this.applicationContext);
            }
            if (bean instanceof ApplicationContextAware) {
                ((ApplicationContextAware)bean).setApplicationContext((ApplicationContext)this.applicationContext);
            }
        }
    }

    private boolean noBeanPresentForComponent(Object instance, String parentBeanName) {
        if (instance instanceof NamedComponent) {
            String beanName = ((NamedComponent)instance).getBeanName();
            if (beanName == null || !this.beanFactory.containsBean(beanName)) {
                return true;
            }
            BeanDefinition existingBeanDefinition = IntegrationContextUtils.getBeanDefinition(beanName, this.beanFactory);
            if (!"prototype".equals(existingBeanDefinition.getScope()) && !instance.equals(this.beanFactory.getBean(beanName))) {
                AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(instance.getClass(), () -> instance).getBeanDefinition();
                beanDefinition.setResourceDescription("the '" + parentBeanName + "' bean definition");
                throw new BeanDefinitionOverrideException(beanName, (BeanDefinition)beanDefinition, existingBeanDefinition);
            }
            return false;
        }
        return !this.beanFactory.getBeansOfType(instance.getClass(), false, false).values().contains(instance);
    }

    private void registerComponent(Object component, String beanName) {
        this.registerComponent(component, beanName, null, new BeanDefinitionCustomizer[0]);
    }

    private void registerComponent(Object component, String beanName, String parentName, BeanDefinitionCustomizer ... customizers) {
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(component.getClass(), () -> component).applyCustomizers(customizers).getRawBeanDefinition();
        if (parentName != null && this.beanFactory.containsBeanDefinition(parentName)) {
            AbstractBeanDefinition parentBeanDefinition = (AbstractBeanDefinition)this.beanFactory.getBeanDefinition(parentName);
            beanDefinition.setResource(parentBeanDefinition.getResource());
            Object source = parentBeanDefinition.getSource();
            if (source instanceof MethodMetadata) {
                source = "bean method " + ((MethodMetadata)source).getMethodName();
            }
            beanDefinition.setSource(source);
            this.beanFactory.registerDependentBean(parentName, beanName);
        }
        ((BeanDefinitionRegistry)this.beanFactory).registerBeanDefinition(beanName, (BeanDefinition)beanDefinition);
        this.beanFactory.getBean(beanName);
    }

    private String generateBeanName(Object instance, String prefix) {
        return this.generateBeanName(instance, prefix, null, false);
    }

    private String generateBeanName(Object instance, String prefix, String fallbackId, boolean useFlowIdAsPrefix) {
        if (instance instanceof NamedComponent && ((NamedComponent)instance).getBeanName() != null) {
            String beanName = ((NamedComponent)instance).getBeanName();
            return useFlowIdAsPrefix ? prefix + beanName : beanName;
        }
        if (fallbackId != null) {
            return useFlowIdAsPrefix ? prefix + fallbackId : fallbackId;
        }
        String generatedBeanName = prefix;
        generatedBeanName = instance instanceof NamedComponent ? generatedBeanName + ((NamedComponent)instance).getComponentType() : generatedBeanName + instance.getClass().getName();
        String id = generatedBeanName;
        int counter = -1;
        while (counter == -1 || this.beanFactory.containsBean(id)) {
            id = generatedBeanName + "#" + ++counter;
        }
        return id;
    }
}

