/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.integration.config.xml.AbstractChannelAdapterParser
 *  org.springframework.integration.config.xml.IntegrationNamespaceUtils
 */
package org.springframework.integration.jmx.config;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractChannelAdapterParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.jmx.NotificationListeningMessageProducer;
import org.w3c.dom.Element;

public class NotificationListeningChannelAdapterParser
extends AbstractChannelAdapterParser {
    protected boolean shouldGenerateIdAsFallback() {
        return true;
    }

    protected AbstractBeanDefinition doParse(Element element, ParserContext parserContext, String channelName) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(NotificationListeningMessageProducer.class);
        builder.addPropertyReference("outputChannel", channelName);
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"server");
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"notification-filter", (String)"filter");
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"handback");
        IntegrationNamespaceUtils.setValueIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"send-timeout");
        IntegrationNamespaceUtils.setValueIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"object-name");
        return builder.getBeanDefinition();
    }
}

