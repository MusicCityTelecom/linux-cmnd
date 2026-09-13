/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.BeanMetadataElement
 *  org.springframework.beans.factory.BeanDefinitionStoreException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.BeanDefinitionHolder
 *  org.springframework.beans.factory.config.RuntimeBeanReference
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionReaderUtils
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.ManagedList
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.StringUtils
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import java.util.HashSet;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractConsumerEndpointParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.handler.MessageHandlerChain;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ChainParser
extends AbstractConsumerEndpointParser {
    private static final String SI_CHAIN_NESTED_ID_ATTRIBUTE = "SI.ChainParser.NestedId.Prefix";
    private final Log logger = LogFactory.getLog(((Object)((Object)this)).getClass());

    @Override
    protected BeanDefinitionBuilder parseHandler(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MessageHandlerChain.class);
        if (!StringUtils.hasText((String)element.getAttribute("id"))) {
            this.logger.info((Object)"It is useful to provide an explicit 'id' attribute on 'chain' elements to simplify the identification of child elements in logs etc.");
        }
        String chainHandlerId = this.resolveId(element, builder.getRawBeanDefinition(), parserContext);
        ManagedList handlerList = new ManagedList();
        HashSet<String> handlerBeanNameSet = new HashSet<String>();
        NodeList children = element.getChildNodes();
        int childOrder = 0;
        for (int i = 0; i < children.getLength(); ++i) {
            String handlerBeanName;
            BeanMetadataElement childBeanMetadata;
            Node child = children.item(i);
            if (child.getNodeType() != 1 || "poller".equals(child.getLocalName())) continue;
            if ((childBeanMetadata = this.parseChild(chainHandlerId, (Element)child, childOrder++, parserContext, (BeanDefinition)builder.getBeanDefinition())) instanceof RuntimeBeanReference && !handlerBeanNameSet.add(handlerBeanName = ((RuntimeBeanReference)childBeanMetadata).getBeanName())) {
                parserContext.getReaderContext().error("A bean definition is already registered for beanName: '" + handlerBeanName + "' within the current <chain>.", (Object)element);
                return null;
            }
            if ("gateway".equals(child.getLocalName())) {
                BeanDefinitionBuilder gwBuilder = BeanDefinitionBuilder.genericBeanDefinition((String)"org.springframework.integration.gateway.RequestReplyMessageHandlerAdapter");
                gwBuilder.addConstructorArgValue((Object)childBeanMetadata);
                handlerList.add(gwBuilder.getBeanDefinition());
                continue;
            }
            handlerList.add(childBeanMetadata);
        }
        builder.addPropertyValue("handlers", (Object)handlerList);
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "send-timeout");
        return builder;
    }

    @Override
    protected String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext) throws BeanDefinitionStoreException {
        String nestedChainIdPrefix;
        String id = super.resolveId(element, definition, parserContext);
        BeanDefinition containingBeanDefinition = parserContext.getContainingBeanDefinition();
        if (containingBeanDefinition != null && StringUtils.hasText((String)(nestedChainIdPrefix = (String)containingBeanDefinition.getAttribute(SI_CHAIN_NESTED_ID_ATTRIBUTE)))) {
            id = nestedChainIdPrefix + "$child." + id;
        }
        definition.setAttribute(SI_CHAIN_NESTED_ID_ATTRIBUTE, (Object)id);
        return id;
    }

    private BeanMetadataElement parseChild(String chainHandlerId, Element element, int order, ParserContext parserContext, BeanDefinition parentDefinition) {
        BeanDefinitionHolder holder = null;
        String id = element.getAttribute("id");
        boolean hasId = StringUtils.hasText((String)id);
        String handlerComponentName = chainHandlerId + "$child" + (hasId ? "." + id : "#" + order);
        if ("bean".equals(element.getLocalName())) {
            holder = parserContext.getDelegate().parseBeanDefinitionElement(element, parentDefinition);
        } else {
            this.validateChild(element, parserContext);
            BeanDefinition beanDefinition = parserContext.getDelegate().parseCustomElement(element, parentDefinition);
            if (beanDefinition == null) {
                parserContext.getReaderContext().error("child BeanDefinition must not be null", (Object)element);
                return null;
            }
            holder = new BeanDefinitionHolder(beanDefinition, handlerComponentName + ".handler");
        }
        holder.getBeanDefinition().getPropertyValues().add("componentName", (Object)handlerComponentName);
        if (hasId) {
            BeanDefinitionReaderUtils.registerBeanDefinition((BeanDefinitionHolder)holder, (BeanDefinitionRegistry)parserContext.getRegistry());
            return new RuntimeBeanReference(holder.getBeanName());
        }
        return holder;
    }

    private void validateChild(Element element, ParserContext parserContext) {
        List pollerChildElements;
        Object source = parserContext.extractSource((Object)element);
        String order = element.getAttribute("order");
        if (StringUtils.hasText((String)order)) {
            parserContext.getReaderContext().error(IntegrationNamespaceUtils.createElementDescription(element) + " must not define an 'order' attribute when used within a chain.", source);
        }
        if (!(pollerChildElements = DomUtils.getChildElementsByTagName((Element)element, (String)"poller")).isEmpty()) {
            parserContext.getReaderContext().error(IntegrationNamespaceUtils.createElementDescription(element) + " must not define a 'poller' sub-element when used within a chain.", source);
        }
    }
}

