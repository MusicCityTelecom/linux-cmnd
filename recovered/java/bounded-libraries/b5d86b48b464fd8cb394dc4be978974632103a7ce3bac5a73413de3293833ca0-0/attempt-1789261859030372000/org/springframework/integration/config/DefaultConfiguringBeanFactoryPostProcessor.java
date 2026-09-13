/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.HierarchicalBeanFactory
 *  org.springframework.beans.factory.SmartInitializingSingleton
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.BeanDefinitionHolder
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.config.PropertiesFactoryBean
 *  org.springframework.beans.factory.parsing.BeanComponentDefinition
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionReaderUtils
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.core.log.LogAccessor
 *  org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
 *  org.springframework.util.ClassUtils
 */
package org.springframework.integration.config;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.log.LogAccessor;
import org.springframework.integration.channel.DefaultHeaderChannelRegistry;
import org.springframework.integration.channel.MessagePublishingErrorHandler;
import org.springframework.integration.channel.NullChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.ConsumerEndpointFactoryBean;
import org.springframework.integration.config.GlobalChannelInterceptorProcessor;
import org.springframework.integration.config.IdGeneratorConfigurer;
import org.springframework.integration.config.IntegrationConfigUtils;
import org.springframework.integration.config.IntegrationEvaluationContextFactoryBean;
import org.springframework.integration.config.IntegrationSimpleEvaluationContextFactoryBean;
import org.springframework.integration.config.MessageHandlerMethodFactoryCreatingFactoryBean;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.integration.context.IntegrationProperties;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.json.JsonNodeWrapperToJsonNodeConverter;
import org.springframework.integration.json.JsonPathUtils;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.support.SmartLifecycleRoleController;
import org.springframework.integration.support.channel.BeanFactoryChannelResolver;
import org.springframework.integration.support.converter.ConfigurableCompositeMessageConverter;
import org.springframework.integration.support.converter.DefaultDatatypeChannelMessageConverter;
import org.springframework.integration.support.json.JacksonPresent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.ClassUtils;

public class DefaultConfiguringBeanFactoryPostProcessor
implements BeanDefinitionRegistryPostProcessor,
SmartInitializingSingleton {
    private static final LogAccessor LOGGER = new LogAccessor(DefaultConfiguringBeanFactoryPostProcessor.class);
    private static final Set<Integer> REGISTRIES_PROCESSED = new HashSet<Integer>();
    private static final Class<?> XPATH_CLASS;
    private static final boolean JSON_PATH_PRESENT;
    private ConfigurableListableBeanFactory beanFactory;
    private BeanDefinitionRegistry registry;

    DefaultConfiguringBeanFactoryPostProcessor() {
    }

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        this.registry = registry;
        this.beanFactory = (ConfigurableListableBeanFactory)registry;
        this.registerBeanFactoryChannelResolver();
        this.registerMessagePublishingErrorHandler();
        this.registerNullChannel();
        this.registerErrorChannel();
        this.registerIntegrationEvaluationContext();
        this.registerTaskScheduler();
        this.registerIdGeneratorConfigurer();
        this.registerIntegrationProperties();
        this.registerBuiltInBeans();
        this.registerRoleController();
        this.registerMessageBuilderFactory();
        this.registerHeaderChannelRegistry();
        this.registerGlobalChannelInterceptorProcessor();
        this.registerDefaultDatatypeChannelMessageConverter();
        this.registerArgumentResolverMessageConverter();
        this.registerMessageHandlerMethodFactory();
        this.registerListMessageHandlerMethodFactory();
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    public void afterSingletonsInstantiated() {
        if (LOGGER.isDebugEnabled()) {
            Properties integrationProperties = IntegrationContextUtils.getIntegrationProperties((BeanFactory)this.beanFactory);
            StringWriter writer = new StringWriter();
            integrationProperties.list(new PrintWriter(writer));
            StringBuffer propertiesBuffer = writer.getBuffer().delete(0, "-- listing properties --".length());
            LOGGER.debug((CharSequence)("\nSpring Integration global properties:\n" + propertiesBuffer));
        }
    }

    private void registerBeanFactoryChannelResolver() {
        if (!this.beanFactory.containsBeanDefinition("integrationChannelResolver")) {
            this.registry.registerBeanDefinition("integrationChannelResolver", (BeanDefinition)new RootBeanDefinition(BeanFactoryChannelResolver.class, BeanFactoryChannelResolver::new));
        }
    }

    private void registerMessagePublishingErrorHandler() {
        if (!this.beanFactory.containsBeanDefinition("integrationMessagePublishingErrorHandler")) {
            this.registry.registerBeanDefinition("integrationMessagePublishingErrorHandler", (BeanDefinition)new RootBeanDefinition(MessagePublishingErrorHandler.class, MessagePublishingErrorHandler::new));
        }
    }

    private void registerNullChannel() {
        if (this.beanFactory.containsBean("nullChannel")) {
            BeanDefinition nullChannelDefinition = null;
            ConfigurableListableBeanFactory beanFactoryToUse = this.beanFactory;
            do {
                ConfigurableListableBeanFactory listable;
                if (beanFactoryToUse instanceof ConfigurableListableBeanFactory && (listable = beanFactoryToUse).containsBeanDefinition("nullChannel")) {
                    nullChannelDefinition = listable.getBeanDefinition("nullChannel");
                }
                if (!(beanFactoryToUse instanceof HierarchicalBeanFactory)) continue;
                beanFactoryToUse = ((HierarchicalBeanFactory)beanFactoryToUse).getParentBeanFactory();
            } while (nullChannelDefinition == null);
            if (!NullChannel.class.getName().equals(nullChannelDefinition.getBeanClassName())) {
                throw new IllegalStateException("The bean name 'nullChannel' is reserved.");
            }
        } else {
            this.registry.registerBeanDefinition("nullChannel", (BeanDefinition)new RootBeanDefinition(NullChannel.class, NullChannel::new));
        }
    }

    private void registerErrorChannel() {
        if (!this.beanFactory.containsBean("errorChannel")) {
            LOGGER.info(() -> "No bean named 'errorChannel' has been explicitly defined. Therefore, a default PublishSubscribeChannel will be created.");
            this.registry.registerBeanDefinition("errorChannel", (BeanDefinition)BeanDefinitionBuilder.rootBeanDefinition(PublishSubscribeChannel.class, this::createErrorChannel).addConstructorArgValue((Object)IntegrationProperties.getExpressionFor("spring.integration.channels.error.requireSubscribers")).addPropertyValue("ignoreFailures", (Object)IntegrationProperties.getExpressionFor("spring.integration.channels.error.ignoreFailures")).getBeanDefinition());
            String errorLoggerBeanName = "_org.springframework.integration.errorLogger.handler";
            this.registry.registerBeanDefinition(errorLoggerBeanName, (BeanDefinition)BeanDefinitionBuilder.genericBeanDefinition(LoggingHandler.class, () -> new LoggingHandler(LoggingHandler.Level.ERROR)).addConstructorArgValue((Object)LoggingHandler.Level.ERROR).getBeanDefinition());
            BeanDefinitionBuilder loggingEndpointBuilder = BeanDefinitionBuilder.genericBeanDefinition(ConsumerEndpointFactoryBean.class, ConsumerEndpointFactoryBean::new).addPropertyValue("inputChannelName", (Object)"errorChannel").addPropertyReference("handler", errorLoggerBeanName);
            BeanComponentDefinition componentDefinition = new BeanComponentDefinition((BeanDefinition)loggingEndpointBuilder.getBeanDefinition(), "_org.springframework.integration.errorLogger");
            BeanDefinitionReaderUtils.registerBeanDefinition((BeanDefinitionHolder)componentDefinition, (BeanDefinitionRegistry)this.registry);
        }
    }

    private PublishSubscribeChannel createErrorChannel() {
        Properties integrationProperties = IntegrationContextUtils.getIntegrationProperties((BeanFactory)this.beanFactory);
        String requireSubscribers = integrationProperties.getProperty("spring.integration.channels.error.requireSubscribers");
        return new PublishSubscribeChannel(Boolean.parseBoolean(requireSubscribers));
    }

    private void registerIntegrationEvaluationContext() {
        BeanDefinitionBuilder integrationEvaluationContextBuilder;
        if (!this.registry.containsBeanDefinition("integrationEvaluationContext")) {
            integrationEvaluationContextBuilder = BeanDefinitionBuilder.genericBeanDefinition(IntegrationEvaluationContextFactoryBean.class, IntegrationEvaluationContextFactoryBean::new).setRole(2);
            this.registry.registerBeanDefinition("integrationEvaluationContext", (BeanDefinition)integrationEvaluationContextBuilder.getBeanDefinition());
        }
        if (!this.registry.containsBeanDefinition("integrationSimpleEvaluationContext")) {
            integrationEvaluationContextBuilder = BeanDefinitionBuilder.genericBeanDefinition(IntegrationSimpleEvaluationContextFactoryBean.class, IntegrationSimpleEvaluationContextFactoryBean::new).setRole(2);
            this.registry.registerBeanDefinition("integrationSimpleEvaluationContext", (BeanDefinition)integrationEvaluationContextBuilder.getBeanDefinition());
        }
    }

    private void registerIdGeneratorConfigurer() {
        String[] definitionNames;
        Class<IdGeneratorConfigurer> clazz = IdGeneratorConfigurer.class;
        String className = clazz.getName();
        for (String definitionName : definitionNames = this.registry.getBeanDefinitionNames()) {
            BeanDefinition definition = this.registry.getBeanDefinition(definitionName);
            if (!className.equals(definition.getBeanClassName())) continue;
            LOGGER.info(() -> className + " is already registered and will be used");
            return;
        }
        RootBeanDefinition beanDefinition = new RootBeanDefinition(clazz, IdGeneratorConfigurer::new);
        beanDefinition.setRole(2);
        BeanDefinitionReaderUtils.registerWithGeneratedName((AbstractBeanDefinition)beanDefinition, (BeanDefinitionRegistry)this.registry);
    }

    private void registerTaskScheduler() {
        if (!this.beanFactory.containsBean("taskScheduler")) {
            LOGGER.info(() -> "No bean named 'taskScheduler' has been explicitly defined. Therefore, a default ThreadPoolTaskScheduler will be created.");
            AbstractBeanDefinition scheduler = BeanDefinitionBuilder.genericBeanDefinition(ThreadPoolTaskScheduler.class, ThreadPoolTaskScheduler::new).addPropertyValue("poolSize", (Object)IntegrationProperties.getExpressionFor("spring.integration.taskScheduler.poolSize")).addPropertyValue("threadNamePrefix", (Object)"task-scheduler-").addPropertyValue("rejectedExecutionHandler", (Object)new ThreadPoolExecutor.CallerRunsPolicy()).addPropertyReference("errorHandler", "integrationMessagePublishingErrorHandler").getBeanDefinition();
            this.registry.registerBeanDefinition("taskScheduler", (BeanDefinition)scheduler);
        }
    }

    private void registerIntegrationProperties() {
        if (!this.beanFactory.containsBean("integrationGlobalProperties")) {
            BeanDefinitionBuilder integrationPropertiesBuilder = BeanDefinitionBuilder.genericBeanDefinition(PropertiesFactoryBean.class, PropertiesFactoryBean::new).setRole(2).addPropertyValue("properties", (Object)IntegrationProperties.defaults()).addPropertyValue("locations", (Object)"classpath*:META-INF/spring.integration.properties");
            this.registry.registerBeanDefinition("integrationGlobalProperties", (BeanDefinition)integrationPropertiesBuilder.getBeanDefinition());
        }
    }

    private void registerBuiltInBeans() {
        int registryId = System.identityHashCode(this.registry);
        this.jsonPath(registryId);
        this.xpath(registryId);
        this.jsonNodeToString(registryId);
        REGISTRIES_PROCESSED.add(registryId);
    }

    private void jsonPath(int registryId) throws LinkageError {
        String jsonPathBeanName = "jsonPath";
        if (JSON_PATH_PRESENT) {
            if (!this.beanFactory.containsBean(jsonPathBeanName) && !REGISTRIES_PROCESSED.contains(registryId)) {
                IntegrationConfigUtils.registerSpelFunctionBean(this.registry, jsonPathBeanName, JsonPathUtils.class, "evaluate");
            }
        } else {
            LOGGER.debug((CharSequence)"The '#jsonPath' SpEL function cannot be registered: there is no jayway json-path.jar on the classpath.");
        }
    }

    private void xpath(int registryId) throws LinkageError {
        String xpathBeanName = "xpath";
        if (XPATH_CLASS != null && !this.beanFactory.containsBean(xpathBeanName) && !REGISTRIES_PROCESSED.contains(registryId)) {
            IntegrationConfigUtils.registerSpelFunctionBean(this.registry, xpathBeanName, XPATH_CLASS, "evaluate");
        }
    }

    private void jsonNodeToString(int registryId) {
        if (!this.beanFactory.containsBean("jsonNodeWrapperToJsonNodeConverter") && !REGISTRIES_PROCESSED.contains(registryId) && JacksonPresent.isJackson2Present()) {
            this.registry.registerBeanDefinition("jsonNodeWrapperToJsonNodeConverter", (BeanDefinition)new RootBeanDefinition(JsonNodeWrapperToJsonNodeConverter.class, JsonNodeWrapperToJsonNodeConverter::new));
        }
    }

    private void registerRoleController() {
        if (!this.beanFactory.containsBean("integrationLifecycleRoleController")) {
            this.registry.registerBeanDefinition("integrationLifecycleRoleController", (BeanDefinition)new RootBeanDefinition(SmartLifecycleRoleController.class, SmartLifecycleRoleController::new));
        }
    }

    private void registerMessageBuilderFactory() {
        if (!this.beanFactory.containsBean("messageBuilderFactory")) {
            BeanDefinitionBuilder mbfBuilder = BeanDefinitionBuilder.genericBeanDefinition(DefaultMessageBuilderFactory.class, DefaultMessageBuilderFactory::new).addPropertyValue("readOnlyHeaders", (Object)IntegrationProperties.getExpressionFor("spring.integration.readOnly.headers"));
            this.registry.registerBeanDefinition("messageBuilderFactory", (BeanDefinition)mbfBuilder.getBeanDefinition());
        }
    }

    private void registerHeaderChannelRegistry() {
        if (!this.beanFactory.containsBean("integrationHeaderChannelRegistry")) {
            LOGGER.info(() -> "No bean named 'integrationHeaderChannelRegistry' has been explicitly defined. Therefore, a default DefaultHeaderChannelRegistry will be created.");
            this.registry.registerBeanDefinition("integrationHeaderChannelRegistry", (BeanDefinition)new RootBeanDefinition(DefaultHeaderChannelRegistry.class, DefaultHeaderChannelRegistry::new));
        }
    }

    private void registerGlobalChannelInterceptorProcessor() {
        if (!this.registry.containsBeanDefinition("globalChannelInterceptorProcessor")) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(GlobalChannelInterceptorProcessor.class, GlobalChannelInterceptorProcessor::new).setRole(2);
            this.registry.registerBeanDefinition("globalChannelInterceptorProcessor", (BeanDefinition)builder.getBeanDefinition());
        }
    }

    private void registerDefaultDatatypeChannelMessageConverter() {
        if (!this.beanFactory.containsBean("datatypeChannelMessageConverter")) {
            this.registry.registerBeanDefinition("datatypeChannelMessageConverter", (BeanDefinition)new RootBeanDefinition(DefaultDatatypeChannelMessageConverter.class, DefaultDatatypeChannelMessageConverter::new));
        }
    }

    private void registerArgumentResolverMessageConverter() {
        if (!this.beanFactory.containsBean("integrationArgumentResolverMessageConverter")) {
            this.registry.registerBeanDefinition("integrationArgumentResolverMessageConverter", (BeanDefinition)new RootBeanDefinition(ConfigurableCompositeMessageConverter.class, ConfigurableCompositeMessageConverter::new));
        }
    }

    private void registerMessageHandlerMethodFactory() {
        if (!this.beanFactory.containsBean("integrationMessageHandlerMethodFactory")) {
            BeanDefinitionBuilder messageHandlerMethodFactoryBuilder = DefaultConfiguringBeanFactoryPostProcessor.createMessageHandlerMethodFactoryBeanDefinition(false);
            this.registry.registerBeanDefinition("integrationMessageHandlerMethodFactory", (BeanDefinition)messageHandlerMethodFactoryBuilder.getBeanDefinition());
        }
    }

    private void registerListMessageHandlerMethodFactory() {
        if (!this.beanFactory.containsBean("integrationListMessageHandlerMethodFactory")) {
            BeanDefinitionBuilder messageHandlerMethodFactoryBuilder = DefaultConfiguringBeanFactoryPostProcessor.createMessageHandlerMethodFactoryBeanDefinition(true);
            this.registry.registerBeanDefinition("integrationListMessageHandlerMethodFactory", (BeanDefinition)messageHandlerMethodFactoryBuilder.getBeanDefinition());
        }
    }

    private static BeanDefinitionBuilder createMessageHandlerMethodFactoryBeanDefinition(boolean listCapable) {
        return BeanDefinitionBuilder.genericBeanDefinition(MessageHandlerMethodFactoryCreatingFactoryBean.class, () -> new MessageHandlerMethodFactoryCreatingFactoryBean(listCapable)).addConstructorArgValue((Object)listCapable).addPropertyReference("argumentResolverMessageConverter", "integrationArgumentResolverMessageConverter");
    }

    static {
        JSON_PATH_PRESENT = ClassUtils.isPresent((String)"com.jayway.jsonpath.JsonPath", null);
        Class xpathClass = null;
        try {
            xpathClass = ClassUtils.forName((String)"org.springframework.integration.xml.xpath.XPathUtils", (ClassLoader)ClassUtils.getDefaultClassLoader());
        }
        catch (ClassNotFoundException e) {
            LOGGER.debug((CharSequence)"SpEL function '#xpath' isn't registered: there is no spring-integration-xml.jar on the classpath.");
        }
        finally {
            XPATH_CLASS = xpathClass;
        }
    }
}

