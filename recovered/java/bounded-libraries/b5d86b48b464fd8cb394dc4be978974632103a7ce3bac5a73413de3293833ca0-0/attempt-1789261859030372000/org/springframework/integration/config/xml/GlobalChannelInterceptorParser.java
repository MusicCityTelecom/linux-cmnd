/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.RuntimeBeanReference
 *  org.springframework.beans.factory.parsing.BeanComponentDefinition
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionReaderUtils
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.xml.AbstractBeanDefinitionParser
 *  org.springframework.beans.factory.xml.BeanDefinitionParserDelegate
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import java.util.List;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.channel.interceptor.GlobalChannelInterceptorWrapper;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.config.xml.WireTapParser;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class GlobalChannelInterceptorParser
extends AbstractBeanDefinitionParser {
    private static final String CHANNEL_NAME_PATTERN_ATTRIBUTE = "pattern";
    private static final String REF_ATTRIBUTE = "ref";

    protected boolean shouldGenerateIdAsFallback() {
        return true;
    }

    protected boolean shouldFireEvents() {
        return false;
    }

    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder globalChannelInterceptorBuilder = BeanDefinitionBuilder.genericBeanDefinition(GlobalChannelInterceptorWrapper.class);
        Object childBeanDefinition = this.getBeanDefinitionBuilderConstructorValue(element, parserContext);
        globalChannelInterceptorBuilder.addConstructorArgValue(childBeanDefinition);
        IntegrationNamespaceUtils.setValueIfAttributeDefined(globalChannelInterceptorBuilder, element, "order");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(globalChannelInterceptorBuilder, element, CHANNEL_NAME_PATTERN_ATTRIBUTE, "patterns");
        return globalChannelInterceptorBuilder.getBeanDefinition();
    }

    protected Object getBeanDefinitionBuilderConstructorValue(Element element, ParserContext parserContext) {
        BeanComponentDefinition interceptorBeanDefinition = IntegrationNamespaceUtils.parseInnerHandlerDefinition(element, parserContext);
        if (interceptorBeanDefinition != null) {
            return interceptorBeanDefinition;
        }
        BeanDefinitionParserDelegate delegate = parserContext.getDelegate();
        String beanName = null;
        if (element.hasAttribute(REF_ATTRIBUTE)) {
            beanName = element.getAttribute(REF_ATTRIBUTE);
        } else {
            List els = DomUtils.getChildElements((Element)element);
            if (els.isEmpty()) {
                parserContext.getReaderContext().error("child BeanDefinition must not be null", (Object)element);
            } else {
                Element child = (Element)els.get(0);
                if ("wire-tap".equals(child.getLocalName())) {
                    beanName = new WireTapParser().parse(child, parserContext);
                } else {
                    if (delegate.nodeNameEquals((Node)child, REF_ATTRIBUTE)) {
                        return delegate.parsePropertySubElement(child, null);
                    }
                    BeanDefinition beanDef = delegate.parseCustomElement(child);
                    beanName = BeanDefinitionReaderUtils.generateBeanName((BeanDefinition)beanDef, (BeanDefinitionRegistry)parserContext.getRegistry());
                }
            }
        }
        return new RuntimeBeanReference(beanName);
    }
}

