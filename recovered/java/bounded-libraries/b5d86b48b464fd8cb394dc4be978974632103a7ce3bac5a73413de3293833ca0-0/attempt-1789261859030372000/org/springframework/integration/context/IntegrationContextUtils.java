/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.jetbrains.annotations.Nullable
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanNotOfRequiredTypeException
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.expression.spel.support.SimpleEvaluationContext
 *  org.springframework.expression.spel.support.StandardEvaluationContext
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.scheduling.TaskScheduler
 *  org.springframework.util.Assert
 */
package org.springframework.integration.context;

import java.util.Map;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.integration.context.IntegrationProperties;
import org.springframework.integration.metadata.MetadataStore;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.Assert;

public abstract class IntegrationContextUtils {
    public static final String BASE_PACKAGE = "org.springframework.integration";
    public static final String TASK_SCHEDULER_BEAN_NAME = "taskScheduler";
    public static final String ERROR_CHANNEL_BEAN_NAME = "errorChannel";
    public static final String NULL_CHANNEL_BEAN_NAME = "nullChannel";
    public static final String ERROR_LOGGER_BEAN_NAME = "_org.springframework.integration.errorLogger";
    public static final String METADATA_STORE_BEAN_NAME = "metadataStore";
    public static final String CONVERTER_REGISTRAR_BEAN_NAME = "converterRegistrar";
    public static final String INTEGRATION_EVALUATION_CONTEXT_BEAN_NAME = "integrationEvaluationContext";
    public static final String INTEGRATION_SIMPLE_EVALUATION_CONTEXT_BEAN_NAME = "integrationSimpleEvaluationContext";
    public static final String INTEGRATION_HEADER_CHANNEL_REGISTRY_BEAN_NAME = "integrationHeaderChannelRegistry";
    public static final String INTEGRATION_GLOBAL_PROPERTIES_BEAN_NAME = "integrationGlobalProperties";
    public static final String MERGED_INTEGRATION_GLOBAL_PROPERTIES_BEAN_NAME = "mergedIntegrationGlobalProperties";
    public static final String CHANNEL_INITIALIZER_BEAN_NAME = "channelInitializer";
    public static final String AUTO_CREATE_CHANNEL_CANDIDATES_BEAN_NAME = "$autoCreateChannelCandidates";
    public static final String DEFAULT_CONFIGURING_POSTPROCESSOR_BEAN_NAME = "DefaultConfiguringBeanFactoryPostProcessor";
    public static final String MESSAGING_ANNOTATION_POSTPROCESSOR_NAME = "org.springframework.integration.internalMessagingAnnotationPostProcessor";
    public static final String PUBLISHER_ANNOTATION_POSTPROCESSOR_NAME = "org.springframework.integration.internalPublisherAnnotationBeanPostProcessor";
    public static final String INTEGRATION_CONFIGURATION_POST_PROCESSOR_BEAN_NAME = "integrationConfigurationBeanFactoryPostProcessor";
    public static final String INTEGRATION_MESSAGE_HISTORY_CONFIGURER_BEAN_NAME = "messageHistoryConfigurer";
    public static final String INTEGRATION_DATATYPE_CHANNEL_MESSAGE_CONVERTER_BEAN_NAME = "datatypeChannelMessageConverter";
    public static final String INTEGRATION_FIXED_SUBSCRIBER_CHANNEL_BPP_BEAN_NAME = "fixedSubscriberChannelBeanFactoryPostProcessor";
    public static final String GLOBAL_CHANNEL_INTERCEPTOR_PROCESSOR_BEAN_NAME = "globalChannelInterceptorProcessor";
    public static final String JSON_NODE_WRAPPER_TO_JSON_NODE_CONVERTER = "jsonNodeWrapperToJsonNodeConverter";
    public static final String INTEGRATION_LIFECYCLE_ROLE_CONTROLLER = "integrationLifecycleRoleController";
    public static final String INTEGRATION_GRAPH_SERVER_BEAN_NAME = "integrationGraphServer";
    public static final String SPEL_PROPERTY_ACCESSOR_REGISTRAR_BEAN_NAME = "spelPropertyAccessorRegistrar";
    public static final String ARGUMENT_RESOLVER_MESSAGE_CONVERTER_BEAN_NAME = "integrationArgumentResolverMessageConverter";
    @Deprecated
    public static final String DISPOSABLES_BEAN_NAME = "integrationDisposableAutoCreatedBeans";
    public static final String MESSAGE_HANDLER_FACTORY_BEAN_NAME = "integrationMessageHandlerMethodFactory";
    public static final String LIST_MESSAGE_HANDLER_FACTORY_BEAN_NAME = "integrationListMessageHandlerMethodFactory";
    private static final Log LOGGER = LogFactory.getLog(IntegrationContextUtils.class);

    public static MetadataStore getMetadataStore(BeanFactory beanFactory) {
        return IntegrationContextUtils.getBeanOfType(beanFactory, METADATA_STORE_BEAN_NAME, MetadataStore.class);
    }

    public static MessageChannel getErrorChannel(BeanFactory beanFactory) {
        return IntegrationContextUtils.getBeanOfType(beanFactory, ERROR_CHANNEL_BEAN_NAME, MessageChannel.class);
    }

    public static TaskScheduler getTaskScheduler(BeanFactory beanFactory) {
        return IntegrationContextUtils.getBeanOfType(beanFactory, TASK_SCHEDULER_BEAN_NAME, TaskScheduler.class);
    }

    public static TaskScheduler getRequiredTaskScheduler(BeanFactory beanFactory) {
        TaskScheduler taskScheduler = IntegrationContextUtils.getTaskScheduler(beanFactory);
        Assert.state((taskScheduler != null ? 1 : 0) != 0, (String)"No such bean 'taskScheduler'");
        return taskScheduler;
    }

    public static StandardEvaluationContext getEvaluationContext(BeanFactory beanFactory) {
        return IntegrationContextUtils.getBeanOfType(beanFactory, INTEGRATION_EVALUATION_CONTEXT_BEAN_NAME, StandardEvaluationContext.class);
    }

    public static SimpleEvaluationContext getSimpleEvaluationContext(BeanFactory beanFactory) {
        return IntegrationContextUtils.getBeanOfType(beanFactory, INTEGRATION_SIMPLE_EVALUATION_CONTEXT_BEAN_NAME, SimpleEvaluationContext.class);
    }

    private static <T> T getBeanOfType(BeanFactory beanFactory, String beanName, Class<T> type) {
        Assert.notNull((Object)beanFactory, (String)"BeanFactory must not be null");
        if (!beanFactory.containsBean(beanName)) {
            return null;
        }
        return (T)beanFactory.getBean(beanName, type);
    }

    public static Properties getIntegrationProperties(BeanFactory beanFactory) {
        Properties properties;
        if (beanFactory != null) {
            properties = IntegrationContextUtils.getBeanOfType(beanFactory, MERGED_INTEGRATION_GLOBAL_PROPERTIES_BEAN_NAME, Properties.class);
            if (properties == null) {
                Properties propertiesToRegister = new Properties();
                propertiesToRegister.putAll((Map<?, ?>)IntegrationProperties.defaults());
                Properties userProperties = IntegrationContextUtils.obtainUserProperties(beanFactory);
                if (userProperties != null) {
                    propertiesToRegister.putAll((Map<?, ?>)userProperties);
                }
                if (beanFactory instanceof BeanDefinitionRegistry) {
                    BeanDefinitionRegistry registry = (BeanDefinitionRegistry)beanFactory;
                    RootBeanDefinition beanDefinition = new RootBeanDefinition(Properties.class);
                    beanDefinition.setInstanceSupplier(() -> propertiesToRegister);
                    registry.registerBeanDefinition(MERGED_INTEGRATION_GLOBAL_PROPERTIES_BEAN_NAME, (BeanDefinition)beanDefinition);
                }
                properties = propertiesToRegister;
            }
        } else {
            properties = new Properties();
            properties.putAll((Map<?, ?>)IntegrationProperties.defaults());
        }
        return properties;
    }

    @Nullable
    private static Properties obtainUserProperties(BeanFactory beanFactory) {
        Object userProperties = IntegrationContextUtils.getBeanOfType(beanFactory, INTEGRATION_GLOBAL_PROPERTIES_BEAN_NAME, Object.class);
        if (userProperties instanceof Properties) {
            if (2 != ((BeanDefinitionRegistry)beanFactory).getBeanDefinition(INTEGRATION_GLOBAL_PROPERTIES_BEAN_NAME).getRole()) {
                LOGGER.warn((Object)"The 'integrationGlobalProperties' bean must be declared as an instance of 'org.springframework.integration.context.IntegrationProperties'. A 'java.util.Properties' support as a bean is deprecated for 'integrationGlobalProperties' since version 5.5.");
            }
            return (Properties)userProperties;
        }
        if (userProperties instanceof IntegrationProperties) {
            return ((IntegrationProperties)userProperties).toProperties();
        }
        if (userProperties != null) {
            throw new BeanNotOfRequiredTypeException(INTEGRATION_GLOBAL_PROPERTIES_BEAN_NAME, IntegrationProperties.class, userProperties.getClass());
        }
        return null;
    }

    public static BeanDefinition getBeanDefinition(String name, ConfigurableListableBeanFactory beanFactory) {
        try {
            return beanFactory.getBeanDefinition(name);
        }
        catch (NoSuchBeanDefinitionException ex) {
            BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();
            if (parentBeanFactory instanceof ConfigurableListableBeanFactory) {
                return IntegrationContextUtils.getBeanDefinition(name, (ConfigurableListableBeanFactory)parentBeanFactory);
            }
            throw ex;
        }
    }
}

