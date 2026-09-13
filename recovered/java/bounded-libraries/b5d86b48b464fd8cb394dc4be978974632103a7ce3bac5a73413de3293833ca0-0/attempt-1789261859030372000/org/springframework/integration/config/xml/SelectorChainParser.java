/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.RuntimeBeanReference
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionReaderUtils
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.ManagedList
 *  org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.filter.MethodInvokingSelector;
import org.springframework.integration.selector.MessageSelectorChain;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SelectorChainParser
extends AbstractSingleBeanDefinitionParser {
    protected Class<?> getBeanClass(Element element) {
        return MessageSelectorChain.class;
    }

    public void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        if (!StringUtils.hasText((String)element.getAttribute("id"))) {
            parserContext.getReaderContext().error("id is required", (Object)element);
        }
        this.parseSelectorChain(builder, element, parserContext);
    }

    private void parseSelectorChain(BeanDefinitionBuilder builder, Element element, ParserContext parserContext) {
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "voting-strategy");
        ManagedList selectors = new ManagedList();
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); ++i) {
            Node child = childNodes.item(i);
            if (child.getNodeType() != 1) continue;
            String nodeName = child.getLocalName();
            if ("selector".equals(nodeName)) {
                String ref = ((Element)child).getAttribute("ref");
                String method = ((Element)child).getAttribute("method");
                if (!StringUtils.hasText((String)method)) {
                    selectors.add((Object)new RuntimeBeanReference(ref));
                    continue;
                }
                selectors.add((Object)this.buildMethodInvokingSelector(parserContext, ref, method));
                continue;
            }
            if (!"selector-chain".equals(nodeName)) continue;
            selectors.add((Object)this.buildSelectorChain(parserContext, child));
        }
        builder.addPropertyValue("selectors", (Object)selectors);
    }

    private RuntimeBeanReference buildSelectorChain(ParserContext parserContext, Node child) {
        BeanDefinitionBuilder nestedBuilder = BeanDefinitionBuilder.genericBeanDefinition(MessageSelectorChain.class);
        this.parseSelectorChain(nestedBuilder, (Element)child, parserContext);
        String nestedBeanName = BeanDefinitionReaderUtils.registerWithGeneratedName((AbstractBeanDefinition)nestedBuilder.getBeanDefinition(), (BeanDefinitionRegistry)parserContext.getRegistry());
        return new RuntimeBeanReference(nestedBeanName);
    }

    private RuntimeBeanReference buildMethodInvokingSelector(ParserContext parserContext, String ref, String method) {
        BeanDefinitionBuilder methodInvokingSelectorBuilder = BeanDefinitionBuilder.genericBeanDefinition(MethodInvokingSelector.class);
        methodInvokingSelectorBuilder.addConstructorArgValue((Object)new RuntimeBeanReference(ref));
        methodInvokingSelectorBuilder.addConstructorArgValue((Object)method);
        return new RuntimeBeanReference(BeanDefinitionReaderUtils.registerWithGeneratedName((AbstractBeanDefinition)methodInvokingSelectorBuilder.getBeanDefinition(), (BeanDefinitionRegistry)parserContext.getRegistry()));
    }
}

