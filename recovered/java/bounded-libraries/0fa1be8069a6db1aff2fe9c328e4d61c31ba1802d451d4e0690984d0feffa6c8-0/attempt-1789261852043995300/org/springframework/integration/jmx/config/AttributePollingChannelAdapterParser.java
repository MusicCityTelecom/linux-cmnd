/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanMetadataElement
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.integration.config.xml.AbstractPollingInboundChannelAdapterParser
 *  org.springframework.integration.config.xml.IntegrationNamespaceUtils
 */
package org.springframework.integration.jmx.config;

import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractPollingInboundChannelAdapterParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.w3c.dom.Element;

public class AttributePollingChannelAdapterParser
extends AbstractPollingInboundChannelAdapterParser {
    protected boolean shouldGenerateIdAsFallback() {
        return true;
    }

    protected BeanMetadataElement parseSource(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition((String)"org.springframework.integration.jmx.AttributePollingMessageSource");
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"server", (String)"server");
        IntegrationNamespaceUtils.setValueIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"object-name");
        IntegrationNamespaceUtils.setValueIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"attribute-name");
        return builder.getBeanDefinition();
    }
}

