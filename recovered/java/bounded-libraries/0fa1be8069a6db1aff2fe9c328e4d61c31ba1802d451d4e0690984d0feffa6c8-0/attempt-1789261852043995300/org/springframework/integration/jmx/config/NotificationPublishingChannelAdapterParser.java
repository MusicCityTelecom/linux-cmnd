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
import org.springframework.integration.jmx.NotificationPublishingMessageHandler;
import org.w3c.dom.Element;

public class NotificationPublishingChannelAdapterParser
extends AbstractOutboundChannelAdapterParser {
    protected boolean shouldGenerateIdAsFallback() {
        return true;
    }

    protected AbstractBeanDefinition parseConsumer(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(NotificationPublishingMessageHandler.class);
        builder.addConstructorArgValue((Object)element.getAttribute("object-name"));
        IntegrationNamespaceUtils.setValueIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"default-notification-type");
        return builder.getBeanDefinition();
    }
}

