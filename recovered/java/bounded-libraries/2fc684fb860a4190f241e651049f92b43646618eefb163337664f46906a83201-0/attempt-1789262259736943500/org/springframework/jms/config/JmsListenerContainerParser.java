/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.MutablePropertyValues
 *  org.springframework.beans.PropertyValues
 *  org.springframework.beans.factory.config.RuntimeBeanReference
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.StringUtils
 */
package org.springframework.jms.config;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.jms.config.AbstractListenerContainerParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

class JmsListenerContainerParser
extends AbstractListenerContainerParser {
    private static final String CONTAINER_TYPE_ATTRIBUTE = "container-type";
    private static final String CONTAINER_CLASS_ATTRIBUTE = "container-class";
    private static final String CONNECTION_FACTORY_ATTRIBUTE = "connection-factory";
    private static final String TASK_EXECUTOR_ATTRIBUTE = "task-executor";
    private static final String ERROR_HANDLER_ATTRIBUTE = "error-handler";
    private static final String CACHE_ATTRIBUTE = "cache";
    private static final String RECEIVE_TIMEOUT_ATTRIBUTE = "receive-timeout";
    private static final String RECOVERY_INTERVAL_ATTRIBUTE = "recovery-interval";
    private static final String BACK_OFF_ATTRIBUTE = "back-off";

    JmsListenerContainerParser() {
    }

    @Override
    protected RootBeanDefinition createContainerFactory(String factoryId, Element containerEle, ParserContext parserContext, PropertyValues commonContainerProperties, PropertyValues specificContainerProperties) {
        RootBeanDefinition factoryDef = new RootBeanDefinition();
        String containerType = containerEle.getAttribute(CONTAINER_TYPE_ATTRIBUTE);
        String containerClass = containerEle.getAttribute(CONTAINER_CLASS_ATTRIBUTE);
        if (StringUtils.hasLength((String)containerClass)) {
            return null;
        }
        if (!StringUtils.hasLength((String)containerType) || containerType.startsWith("default")) {
            factoryDef.setBeanClassName("org.springframework.jms.config.DefaultJmsListenerContainerFactory");
        } else if (containerType.startsWith("simple")) {
            factoryDef.setBeanClassName("org.springframework.jms.config.SimpleJmsListenerContainerFactory");
        }
        factoryDef.getPropertyValues().addPropertyValues(commonContainerProperties);
        factoryDef.getPropertyValues().addPropertyValues(specificContainerProperties);
        return factoryDef;
    }

    @Override
    protected RootBeanDefinition createContainer(Element containerEle, Element listenerEle, ParserContext parserContext, PropertyValues commonContainerProperties, PropertyValues specificContainerProperties) {
        RootBeanDefinition containerDef = new RootBeanDefinition();
        containerDef.setSource(parserContext.extractSource((Object)containerEle));
        containerDef.getPropertyValues().addPropertyValues(commonContainerProperties);
        containerDef.getPropertyValues().addPropertyValues(specificContainerProperties);
        String containerType = containerEle.getAttribute(CONTAINER_TYPE_ATTRIBUTE);
        String containerClass = containerEle.getAttribute(CONTAINER_CLASS_ATTRIBUTE);
        if (StringUtils.hasLength((String)containerClass)) {
            containerDef.setBeanClassName(containerClass);
        } else if (!StringUtils.hasLength((String)containerType) || containerType.startsWith("default")) {
            containerDef.setBeanClassName("org.springframework.jms.listener.DefaultMessageListenerContainer");
        } else if (containerType.startsWith("simple")) {
            containerDef.setBeanClassName("org.springframework.jms.listener.SimpleMessageListenerContainer");
        } else {
            parserContext.getReaderContext().error("Invalid 'container-type' attribute: only \"default\" and \"simple\" supported.", (Object)containerEle);
        }
        this.parseListenerConfiguration(listenerEle, parserContext, containerDef.getPropertyValues());
        return containerDef;
    }

    @Override
    protected MutablePropertyValues parseSpecificContainerProperties(Element containerEle, ParserContext parserContext) {
        String backOffBeanName;
        String receiveTimeout;
        String phase;
        String prefetch;
        String concurrency;
        String transactionManagerBeanName;
        Integer acknowledgeMode;
        String cache;
        String destinationResolverBeanName;
        String errorHandlerBeanName;
        String taskExecutorBeanName;
        MutablePropertyValues properties = new MutablePropertyValues();
        boolean isSimpleContainer = containerEle.getAttribute(CONTAINER_TYPE_ATTRIBUTE).startsWith("simple");
        String connectionFactoryBeanName = "connectionFactory";
        if (containerEle.hasAttribute(CONNECTION_FACTORY_ATTRIBUTE) && !StringUtils.hasText((String)(connectionFactoryBeanName = containerEle.getAttribute(CONNECTION_FACTORY_ATTRIBUTE)))) {
            parserContext.getReaderContext().error("Listener container 'connection-factory' attribute contains empty value.", (Object)containerEle);
        }
        if (StringUtils.hasText((String)connectionFactoryBeanName)) {
            properties.add("connectionFactory", (Object)new RuntimeBeanReference(connectionFactoryBeanName));
        }
        if (StringUtils.hasText((String)(taskExecutorBeanName = containerEle.getAttribute(TASK_EXECUTOR_ATTRIBUTE)))) {
            properties.add("taskExecutor", (Object)new RuntimeBeanReference(taskExecutorBeanName));
        }
        if (StringUtils.hasText((String)(errorHandlerBeanName = containerEle.getAttribute(ERROR_HANDLER_ATTRIBUTE)))) {
            properties.add("errorHandler", (Object)new RuntimeBeanReference(errorHandlerBeanName));
        }
        if (StringUtils.hasText((String)(destinationResolverBeanName = containerEle.getAttribute("destination-resolver")))) {
            properties.add("destinationResolver", (Object)new RuntimeBeanReference(destinationResolverBeanName));
        }
        if (StringUtils.hasText((String)(cache = containerEle.getAttribute(CACHE_ATTRIBUTE)))) {
            if (isSimpleContainer) {
                if (!"auto".equals(cache) && !"consumer".equals(cache)) {
                    parserContext.getReaderContext().warning("'cache' attribute not actively supported for listener container of type \"simple\". Effective runtime behavior will be equivalent to \"consumer\" / \"auto\".", (Object)containerEle);
                }
            } else {
                properties.add("cacheLevelName", (Object)("CACHE_" + cache.toUpperCase()));
            }
        }
        if ((acknowledgeMode = this.parseAcknowledgeMode(containerEle, parserContext)) != null) {
            if (acknowledgeMode == 0) {
                properties.add("sessionTransacted", (Object)Boolean.TRUE);
            } else {
                properties.add("sessionAcknowledgeMode", (Object)acknowledgeMode);
            }
        }
        if (StringUtils.hasText((String)(transactionManagerBeanName = containerEle.getAttribute("transaction-manager")))) {
            if (isSimpleContainer) {
                parserContext.getReaderContext().error("'transaction-manager' attribute not supported for listener container of type \"simple\".", (Object)containerEle);
            } else {
                properties.add("transactionManager", (Object)new RuntimeBeanReference(transactionManagerBeanName));
            }
        }
        if (StringUtils.hasText((String)(concurrency = containerEle.getAttribute("concurrency")))) {
            properties.add("concurrency", (Object)concurrency);
        }
        if (StringUtils.hasText((String)(prefetch = containerEle.getAttribute("prefetch"))) && !isSimpleContainer) {
            properties.add("maxMessagesPerTask", (Object)prefetch);
        }
        if (StringUtils.hasText((String)(phase = containerEle.getAttribute("phase")))) {
            properties.add("phase", (Object)phase);
        }
        if (StringUtils.hasText((String)(receiveTimeout = containerEle.getAttribute(RECEIVE_TIMEOUT_ATTRIBUTE))) && !isSimpleContainer) {
            properties.add("receiveTimeout", (Object)receiveTimeout);
        }
        if (StringUtils.hasText((String)(backOffBeanName = containerEle.getAttribute(BACK_OFF_ATTRIBUTE)))) {
            if (!isSimpleContainer) {
                properties.add("backOff", (Object)new RuntimeBeanReference(backOffBeanName));
            }
        } else {
            String recoveryInterval = containerEle.getAttribute(RECOVERY_INTERVAL_ATTRIBUTE);
            if (StringUtils.hasText((String)recoveryInterval) && !isSimpleContainer) {
                properties.add("recoveryInterval", (Object)recoveryInterval);
            }
        }
        return properties;
    }
}

