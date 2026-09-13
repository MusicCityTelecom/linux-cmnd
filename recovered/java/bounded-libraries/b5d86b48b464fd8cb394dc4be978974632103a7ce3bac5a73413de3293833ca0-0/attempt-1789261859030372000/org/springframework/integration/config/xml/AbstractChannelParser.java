/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.aop.scope.ScopedProxyUtils
 *  org.springframework.beans.factory.config.BeanDefinitionHolder
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.ManagedList
 *  org.springframework.beans.factory.xml.AbstractBeanDefinitionParser
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.StringUtils
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.channel.FixedSubscriberChannel;
import org.springframework.integration.config.xml.ChannelInterceptorParser;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public abstract class AbstractChannelParser
extends AbstractBeanDefinitionParser {
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = this.buildBeanDefinition(element, parserContext);
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        Element interceptorsElement = DomUtils.getChildElementByTagName((Element)element, (String)"interceptors");
        String datatypeAttr = element.getAttribute("datatype");
        String messageConverter = element.getAttribute("message-converter");
        if (!FixedSubscriberChannel.class.getName().equals(builder.getBeanDefinition().getBeanClassName())) {
            ManagedList interceptors = null;
            if (interceptorsElement != null) {
                ChannelInterceptorParser interceptorParser = new ChannelInterceptorParser();
                interceptors = interceptorParser.parseInterceptors(interceptorsElement, parserContext);
            }
            if (interceptors == null) {
                interceptors = new ManagedList();
            }
            if (StringUtils.hasText((String)datatypeAttr)) {
                builder.addPropertyValue("datatypes", (Object)datatypeAttr);
            }
            if (StringUtils.hasText((String)messageConverter)) {
                builder.addPropertyReference("messageConverter", messageConverter);
            }
            builder.addPropertyValue("interceptors", (Object)interceptors);
            String scopeAttr = element.getAttribute("scope");
            if (StringUtils.hasText((String)scopeAttr)) {
                builder.setScope(scopeAttr);
            }
        } else {
            if (interceptorsElement != null) {
                parserContext.getReaderContext().error("Cannot have interceptors when 'fixed-subscriber=\"true\"'", (Object)element);
            }
            if (StringUtils.hasText((String)datatypeAttr)) {
                parserContext.getReaderContext().error("Cannot have 'datatype' when 'fixed-subscriber=\"true\"'", (Object)element);
            }
            if (StringUtils.hasText((String)messageConverter)) {
                parserContext.getReaderContext().error("Cannot have 'message-converter' when 'fixed-subscriber=\"true\"'", (Object)element);
            }
        }
        beanDefinition.setSource(parserContext.extractSource((Object)element));
        return beanDefinition;
    }

    protected void registerBeanDefinition(BeanDefinitionHolder definition, BeanDefinitionRegistry registry) {
        String scope = definition.getBeanDefinition().getScope();
        if (!("".equals(scope) || "singleton".equals(scope) || "prototype".equals(scope))) {
            super.registerBeanDefinition(ScopedProxyUtils.createScopedProxy((BeanDefinitionHolder)definition, (BeanDefinitionRegistry)registry, (boolean)false), registry);
        } else {
            super.registerBeanDefinition(definition, registry);
        }
    }

    protected abstract BeanDefinitionBuilder buildBeanDefinition(Element var1, ParserContext var2);
}

