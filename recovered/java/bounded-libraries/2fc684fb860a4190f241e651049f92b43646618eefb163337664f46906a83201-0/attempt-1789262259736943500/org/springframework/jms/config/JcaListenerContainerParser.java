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

class JcaListenerContainerParser
extends AbstractListenerContainerParser {
    private static final String RESOURCE_ADAPTER_ATTRIBUTE = "resource-adapter";
    private static final String ACTIVATION_SPEC_FACTORY_ATTRIBUTE = "activation-spec-factory";

    JcaListenerContainerParser() {
    }

    @Override
    protected RootBeanDefinition createContainerFactory(String factoryId, Element containerEle, ParserContext parserContext, PropertyValues commonContainerProperties, PropertyValues specificContainerProperties) {
        RootBeanDefinition factoryDef = new RootBeanDefinition();
        factoryDef.setBeanClassName("org.springframework.jms.config.DefaultJcaListenerContainerFactory");
        factoryDef.getPropertyValues().addPropertyValues(commonContainerProperties);
        factoryDef.getPropertyValues().addPropertyValues(specificContainerProperties);
        return factoryDef;
    }

    @Override
    protected RootBeanDefinition createContainer(Element containerEle, Element listenerEle, ParserContext parserContext, PropertyValues commonContainerProperties, PropertyValues specificContainerProperties) {
        RootBeanDefinition containerDef = new RootBeanDefinition();
        containerDef.setSource(parserContext.extractSource((Object)containerEle));
        containerDef.setBeanClassName("org.springframework.jms.listener.endpoint.JmsMessageEndpointManager");
        containerDef.getPropertyValues().addPropertyValues(specificContainerProperties);
        RootBeanDefinition configDef = new RootBeanDefinition();
        configDef.setSource(parserContext.extractSource((Object)containerEle));
        configDef.setBeanClassName("org.springframework.jms.listener.endpoint.JmsActivationSpecConfig");
        configDef.getPropertyValues().addPropertyValues(commonContainerProperties);
        this.parseListenerConfiguration(listenerEle, parserContext, configDef.getPropertyValues());
        containerDef.getPropertyValues().add("activationSpecConfig", (Object)configDef);
        return containerDef;
    }

    @Override
    protected MutablePropertyValues parseCommonContainerProperties(Element containerEle, ParserContext parserContext) {
        String prefetch;
        String concurrency;
        MutablePropertyValues properties = super.parseCommonContainerProperties(containerEle, parserContext);
        Integer acknowledgeMode = this.parseAcknowledgeMode(containerEle, parserContext);
        if (acknowledgeMode != null) {
            properties.add("acknowledgeMode", (Object)acknowledgeMode);
        }
        if (StringUtils.hasText((String)(concurrency = containerEle.getAttribute("concurrency")))) {
            properties.add("concurrency", (Object)concurrency);
        }
        if (StringUtils.hasText((String)(prefetch = containerEle.getAttribute("prefetch")))) {
            properties.add("prefetchSize", (Object)Integer.valueOf(prefetch));
        }
        return properties;
    }

    @Override
    protected MutablePropertyValues parseSpecificContainerProperties(Element containerEle, ParserContext parserContext) {
        String phase;
        String transactionManagerBeanName;
        MutablePropertyValues properties = new MutablePropertyValues();
        if (containerEle.hasAttribute(RESOURCE_ADAPTER_ATTRIBUTE)) {
            String resourceAdapterBeanName = containerEle.getAttribute(RESOURCE_ADAPTER_ATTRIBUTE);
            if (!StringUtils.hasText((String)resourceAdapterBeanName)) {
                parserContext.getReaderContext().error("Listener container 'resource-adapter' attribute contains empty value.", (Object)containerEle);
            } else {
                properties.add("resourceAdapter", (Object)new RuntimeBeanReference(resourceAdapterBeanName));
            }
        }
        String activationSpecFactoryBeanName = containerEle.getAttribute(ACTIVATION_SPEC_FACTORY_ATTRIBUTE);
        String destinationResolverBeanName = containerEle.getAttribute("destination-resolver");
        if (StringUtils.hasText((String)activationSpecFactoryBeanName)) {
            if (StringUtils.hasText((String)destinationResolverBeanName)) {
                parserContext.getReaderContext().error("Specify either 'activation-spec-factory' or 'destination-resolver', not both. If you define a dedicated JmsActivationSpecFactory bean, specify the custom DestinationResolver there (if possible).", (Object)containerEle);
            }
            properties.add("activationSpecFactory", (Object)new RuntimeBeanReference(activationSpecFactoryBeanName));
        }
        if (StringUtils.hasText((String)destinationResolverBeanName)) {
            properties.add("destinationResolver", (Object)new RuntimeBeanReference(destinationResolverBeanName));
        }
        if (StringUtils.hasText((String)(transactionManagerBeanName = containerEle.getAttribute("transaction-manager")))) {
            properties.add("transactionManager", (Object)new RuntimeBeanReference(transactionManagerBeanName));
        }
        if (StringUtils.hasText((String)(phase = containerEle.getAttribute("phase")))) {
            properties.add("phase", (Object)phase);
        }
        return properties;
    }
}

