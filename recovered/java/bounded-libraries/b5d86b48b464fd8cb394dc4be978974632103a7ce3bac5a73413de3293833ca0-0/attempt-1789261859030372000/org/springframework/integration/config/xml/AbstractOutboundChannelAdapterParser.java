/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.parsing.BeanComponentDefinition
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionReaderUtils
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.ManagedList
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.StringUtils
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import java.util.Collection;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.ConsumerEndpointFactoryBean;
import org.springframework.integration.config.xml.AbstractChannelAdapterParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public abstract class AbstractOutboundChannelAdapterParser
extends AbstractChannelAdapterParser {
    @Override
    protected AbstractBeanDefinition doParse(Element element, ParserContext parserContext, String channelName) {
        if (parserContext.isNested()) {
            if (channelName != null) {
                String elementDescription = IntegrationNamespaceUtils.createElementDescription(element);
                parserContext.getReaderContext().error("The 'channel' attribute isn't allowed for " + elementDescription + " when it is used as a nested element, e.g. inside a <chain/>", (Object)element);
            }
            AbstractBeanDefinition consumerBeanDefinition = this.parseConsumer(element, parserContext);
            this.configureRequestHandlerAdviceChain(element, parserContext, (BeanDefinition)consumerBeanDefinition, null);
            return consumerBeanDefinition;
        }
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ConsumerEndpointFactoryBean.class);
        Element pollerElement = DomUtils.getChildElementByTagName((Element)element, (String)"poller");
        BeanComponentDefinition handlerBeanComponentDefinition = this.doParseAndRegisterConsumer(element, parserContext);
        builder.addPropertyReference("handler", handlerBeanComponentDefinition.getBeanName());
        IntegrationNamespaceUtils.checkAndConfigureFixedSubscriberChannel(element, parserContext, channelName, handlerBeanComponentDefinition.getBeanName());
        if (pollerElement != null) {
            if (!StringUtils.hasText((String)channelName)) {
                parserContext.getReaderContext().error("outbound channel adapter with a 'poller' requires a 'channel' to poll", (Object)element);
            }
            IntegrationNamespaceUtils.configurePollerMetadata(pollerElement, builder, parserContext);
        }
        builder.addPropertyValue("inputChannelName", (Object)channelName);
        this.configureRequestHandlerAdviceChain(element, parserContext, handlerBeanComponentDefinition.getBeanDefinition(), builder);
        return builder.getBeanDefinition();
    }

    private void configureRequestHandlerAdviceChain(Element element, ParserContext parserContext, BeanDefinition handlerBeanDefinition, BeanDefinitionBuilder consumerBuilder) {
        Element txElement = DomUtils.getChildElementByTagName((Element)element, (String)"transactional");
        Element adviceChainElement = DomUtils.getChildElementByTagName((Element)element, (String)"request-handler-advice-chain");
        ManagedList adviceChain = IntegrationNamespaceUtils.configureAdviceChain(adviceChainElement, txElement, handlerBeanDefinition, parserContext);
        if (!CollectionUtils.isEmpty((Collection)adviceChain)) {
            boolean isReplyProducer = this.isUsingReplyProducer();
            if (!isReplyProducer) {
                AbstractBeanDefinition abstractBeanDefinition;
                Class beanClass = null;
                if (handlerBeanDefinition instanceof AbstractBeanDefinition && (abstractBeanDefinition = (AbstractBeanDefinition)handlerBeanDefinition).hasBeanClass()) {
                    beanClass = abstractBeanDefinition.getBeanClass();
                }
                boolean bl = isReplyProducer = beanClass != null && AbstractReplyProducingMessageHandler.class.isAssignableFrom(beanClass);
            }
            if (isReplyProducer) {
                handlerBeanDefinition.getPropertyValues().add("adviceChain", (Object)adviceChain);
            } else if (consumerBuilder != null) {
                consumerBuilder.addPropertyValue("adviceChain", (Object)adviceChain);
            } else {
                String elementDescription = IntegrationNamespaceUtils.createElementDescription(element);
                parserContext.getReaderContext().error("'request-handler-advice-chain' isn't allowed for " + elementDescription + " within a <chain/>, because its Handler isn't an AbstractReplyProducingMessageHandler", (Object)element);
            }
        }
    }

    protected BeanComponentDefinition doParseAndRegisterConsumer(Element element, ParserContext parserContext) {
        String order;
        AbstractBeanDefinition definition = this.parseConsumer(element, parserContext);
        if (definition == null) {
            parserContext.getReaderContext().error("Consumer parsing must return an AbstractBeanDefinition.", (Object)element);
        }
        if (StringUtils.hasText((String)(order = element.getAttribute("order")))) {
            definition.getPropertyValues().addPropertyValue("order", (Object)order);
        }
        String beanName = BeanDefinitionReaderUtils.generateBeanName((BeanDefinition)definition, (BeanDefinitionRegistry)parserContext.getRegistry());
        String[] handlerAlias = IntegrationNamespaceUtils.generateAlias(element);
        BeanComponentDefinition beanComponentDefinition = new BeanComponentDefinition((BeanDefinition)definition, beanName, handlerAlias);
        parserContext.registerBeanComponent(beanComponentDefinition);
        return beanComponentDefinition;
    }

    protected abstract AbstractBeanDefinition parseConsumer(Element var1, ParserContext var2);

    protected boolean isUsingReplyProducer() {
        return false;
    }
}

