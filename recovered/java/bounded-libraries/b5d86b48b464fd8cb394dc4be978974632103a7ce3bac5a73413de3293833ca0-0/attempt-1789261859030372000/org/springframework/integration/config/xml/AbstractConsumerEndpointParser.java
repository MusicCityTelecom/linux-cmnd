/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanDefinitionStoreException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.ConstructorArgumentValues
 *  org.springframework.beans.factory.config.ConstructorArgumentValues$ValueHolder
 *  org.springframework.beans.factory.parsing.BeanComponentDefinition
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionReaderUtils
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.ManagedList
 *  org.springframework.beans.factory.support.ManagedSet
 *  org.springframework.beans.factory.xml.AbstractBeanDefinitionParser
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.StringUtils
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.ManagedSet;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.ConsumerEndpointFactoryBean;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public abstract class AbstractConsumerEndpointParser
extends AbstractBeanDefinitionParser {
    protected static final String REF_ATTRIBUTE = "ref";
    protected static final String METHOD_ATTRIBUTE = "method";
    protected static final String EXPRESSION_ATTRIBUTE = "expression";

    protected String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext) throws BeanDefinitionStoreException {
        String id = element.getAttribute("id");
        if (!StringUtils.hasText((String)id)) {
            id = element.getAttribute("name");
        }
        if (!StringUtils.hasText((String)id)) {
            id = BeanDefinitionReaderUtils.generateBeanName((BeanDefinition)definition, (BeanDefinitionRegistry)parserContext.getRegistry(), (boolean)parserContext.isNested());
        }
        return id;
    }

    protected abstract BeanDefinitionBuilder parseHandler(Element var1, ParserContext var2);

    protected String getInputChannelAttributeName() {
        return "input-channel";
    }

    protected final AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        boolean hasAdviceChain;
        BeanDefinitionBuilder handlerBuilder = this.parseHandler(element, parserContext);
        IntegrationNamespaceUtils.setValueIfAttributeDefined(handlerBuilder, element, "output-channel", "outputChannelName");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(handlerBuilder, element, "order");
        Element txElement = DomUtils.getChildElementByTagName((Element)element, (String)"transactional");
        Element adviceChainElement = DomUtils.getChildElementByTagName((Element)element, (String)"request-handler-advice-chain");
        ManagedList adviceChain = IntegrationNamespaceUtils.configureAdviceChain(adviceChainElement, txElement, true, (BeanDefinition)handlerBuilder.getRawBeanDefinition(), parserContext);
        boolean bl = hasAdviceChain = !CollectionUtils.isEmpty((Collection)adviceChain);
        if (hasAdviceChain) {
            handlerBuilder.addPropertyValue("adviceChain", (Object)adviceChain);
        }
        AbstractBeanDefinition handlerBeanDefinition = handlerBuilder.getBeanDefinition();
        handlerBeanDefinition.setResource(parserContext.getReaderContext().getResource());
        String elementDescription = IntegrationNamespaceUtils.createElementDescription(element);
        handlerBeanDefinition.setSource((Object)elementDescription);
        String inputChannelAttributeName = this.getInputChannelAttributeName();
        boolean hasInputChannelAttribute = element.hasAttribute(inputChannelAttributeName);
        if (parserContext.isNested()) {
            if (hasInputChannelAttribute) {
                parserContext.getReaderContext().error("The '" + inputChannelAttributeName + "' attribute isn't allowed for a nested (e.g. inside a <chain/>) endpoint element: " + elementDescription + ".", (Object)element);
            }
            if (!this.replyChannelInChainAllowed(element) && StringUtils.hasText((String)element.getAttribute("reply-channel"))) {
                parserContext.getReaderContext().error("The 'reply-channel' attribute isn't allowed for a nested (e.g. inside a <chain/>) outbound gateway element: " + elementDescription + ".", (Object)element);
            }
            return handlerBeanDefinition;
        }
        if (!hasInputChannelAttribute) {
            parserContext.getReaderContext().error("The '" + inputChannelAttributeName + "' attribute is required for the top-level endpoint element: " + elementDescription + ".", (Object)element);
        }
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ConsumerEndpointFactoryBean.class);
        if (hasAdviceChain) {
            builder.addPropertyValue("adviceChain", (Object)adviceChain);
        }
        String handlerBeanName = BeanDefinitionReaderUtils.generateBeanName((BeanDefinition)handlerBeanDefinition, (BeanDefinitionRegistry)parserContext.getRegistry());
        String[] handlerAlias = IntegrationNamespaceUtils.generateAlias(element);
        parserContext.registerBeanComponent(new BeanComponentDefinition((BeanDefinition)handlerBeanDefinition, handlerBeanName, handlerAlias));
        builder.addPropertyReference("handler", handlerBeanName);
        String inputChannelName = element.getAttribute(inputChannelAttributeName);
        if (!parserContext.getRegistry().containsBeanDefinition(inputChannelName)) {
            this.registerChannelForCreation(parserContext, inputChannelName);
        }
        IntegrationNamespaceUtils.checkAndConfigureFixedSubscriberChannel(element, parserContext, inputChannelName, handlerBeanName);
        builder.addPropertyValue("inputChannelName", (Object)inputChannelName);
        List pollerElementList = DomUtils.getChildElementsByTagName((Element)element, (String)"poller");
        this.poller(element, parserContext, builder, pollerElementList);
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "auto-startup");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "phase");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "role");
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        String beanName = this.resolveId(element, beanDefinition, parserContext);
        parserContext.registerBeanComponent(new BeanComponentDefinition((BeanDefinition)beanDefinition, beanName));
        return null;
    }

    private void poller(Element element, ParserContext parserContext, BeanDefinitionBuilder builder, List<Element> pollerElementList) {
        if (!CollectionUtils.isEmpty(pollerElementList)) {
            if (pollerElementList.size() != 1) {
                parserContext.getReaderContext().error("at most one poller element may be configured for an endpoint", (Object)element);
            }
            IntegrationNamespaceUtils.configurePollerMetadata(pollerElementList.get(0), builder, parserContext);
        }
    }

    private void registerChannelForCreation(ParserContext parserContext, String inputChannelName) {
        if (parserContext.getRegistry().containsBeanDefinition("$autoCreateChannelCandidates")) {
            BeanDefinition channelRegistry = parserContext.getRegistry().getBeanDefinition("$autoCreateChannelCandidates");
            ConstructorArgumentValues caValues = channelRegistry.getConstructorArgumentValues();
            ConstructorArgumentValues.ValueHolder vh = caValues.getArgumentValue(0, Collection.class);
            if (vh == null) {
                caValues.addIndexedArgumentValue(0, (Object)new ManagedSet());
            }
            Collection channelCandidateNames = (Collection)caValues.getArgumentValue(0, Collection.class).getValue();
            channelCandidateNames.add(inputChannelName);
        } else {
            parserContext.getReaderContext().error("Failed to locate '$autoCreateChannelCandidates'", (Object)parserContext.getRegistry());
        }
    }

    protected boolean replyChannelInChainAllowed(Element element) {
        String localName = element.getLocalName();
        return localName == null || !localName.contains("outbound-gateway");
    }
}

