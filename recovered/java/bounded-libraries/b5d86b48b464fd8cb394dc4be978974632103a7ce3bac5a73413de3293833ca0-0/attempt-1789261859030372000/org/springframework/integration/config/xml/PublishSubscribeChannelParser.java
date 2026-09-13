/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.xml.AbstractChannelParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class PublishSubscribeChannelParser
extends AbstractChannelParser {
    @Override
    protected BeanDefinitionBuilder buildBeanDefinition(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(PublishSubscribeChannel.class);
        String taskExecutorRef = element.getAttribute("task-executor");
        if (StringUtils.hasText((String)taskExecutorRef)) {
            builder.addConstructorArgReference(taskExecutorRef);
        }
        builder.addConstructorArgValue((Object)element.getAttribute("require-subscribers"));
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, "error-handler");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "ignore-failures");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "apply-sequence");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "max-subscribers");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "min-subscribers");
        return builder;
    }
}

