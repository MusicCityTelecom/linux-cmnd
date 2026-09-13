/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanMetadataElement
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.parsing.BeanComponentDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionReaderUtils
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.integration.config.xml.AbstractPollingInboundChannelAdapterParser
 *  org.springframework.integration.config.xml.IntegrationNamespaceUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.jmx.config;

import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractPollingInboundChannelAdapterParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.jmx.DefaultMBeanObjectConverter;
import org.springframework.integration.jmx.MBeanTreePollingMessageSource;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class MBeanTreePollingChannelAdapterParser
extends AbstractPollingInboundChannelAdapterParser {
    protected boolean shouldGenerateIdAsFallback() {
        return true;
    }

    protected BeanMetadataElement parseSource(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(MBeanTreePollingMessageSource.class);
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"server", (String)"server");
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"query-name-ref", (String)"queryNameReference");
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"query-expression-ref", (String)"queryExpressionReference");
        IntegrationNamespaceUtils.setValueIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"query-name", (String)"queryName");
        IntegrationNamespaceUtils.setValueIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"query-expression", (String)"queryExpression");
        if (StringUtils.hasText((String)element.getAttribute("query-name")) && StringUtils.hasText((String)element.getAttribute("query-name-ref"))) {
            parserContext.getReaderContext().error("Cannot have both `query-name' and 'query-name-ref'", (Object)element);
        }
        if (StringUtils.hasText((String)element.getAttribute("query-expression")) && StringUtils.hasText((String)element.getAttribute("query-expression-ref"))) {
            parserContext.getReaderContext().error("Cannot have both `query-expression' and 'query-expression-ref'", (Object)element);
        }
        BeanComponentDefinition innerBeanDef = IntegrationNamespaceUtils.parseInnerHandlerDefinition((Element)element, (ParserContext)parserContext);
        String beanName = element.getAttribute("converter");
        if (innerBeanDef != null) {
            if (StringUtils.hasText((String)beanName)) {
                parserContext.getReaderContext().error("Cannot have both a 'converter' and an inner bean", (Object)element);
            }
            beanName = BeanDefinitionReaderUtils.generateBeanName((BeanDefinition)innerBeanDef.getBeanDefinition(), (BeanDefinitionRegistry)parserContext.getRegistry(), (boolean)true);
            parserContext.getRegistry().registerBeanDefinition(beanName, innerBeanDef.getBeanDefinition());
        } else if (!StringUtils.hasText((String)beanName)) {
            BeanDefinitionBuilder childBuilder = BeanDefinitionBuilder.genericBeanDefinition(DefaultMBeanObjectConverter.class);
            beanName = BeanDefinitionReaderUtils.generateBeanName((BeanDefinition)childBuilder.getBeanDefinition(), (BeanDefinitionRegistry)parserContext.getRegistry(), (boolean)true);
            parserContext.getRegistry().registerBeanDefinition(beanName, (BeanDefinition)childBuilder.getBeanDefinition());
        }
        builder.addConstructorArgReference(beanName);
        return builder.getBeanDefinition();
    }
}

