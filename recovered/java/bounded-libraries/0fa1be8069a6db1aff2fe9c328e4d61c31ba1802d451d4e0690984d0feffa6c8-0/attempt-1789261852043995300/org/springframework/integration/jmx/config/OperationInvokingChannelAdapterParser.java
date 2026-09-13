/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.integration.config.xml.AbstractOutboundChannelAdapterParser
 *  org.springframework.integration.config.xml.IntegrationNamespaceUtils
 */
package org.springframework.integration.jmx.config;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractOutboundChannelAdapterParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.jmx.OperationInvokingMessageHandler;
import org.w3c.dom.Element;

public class OperationInvokingChannelAdapterParser
extends AbstractOutboundChannelAdapterParser {
    protected boolean shouldGenerateIdAsFallback() {
        return true;
    }

    protected AbstractBeanDefinition parseConsumer(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(OperationInvokingMessageHandler.class);
        builder.addConstructorArgReference(element.getAttribute("server"));
        builder.addPropertyValue("expectReply", (Object)false);
        IntegrationNamespaceUtils.setValueIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"object-name");
        IntegrationNamespaceUtils.setValueIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"operation-name");
        return builder.getBeanDefinition();
    }
}

