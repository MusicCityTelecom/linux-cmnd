/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.integration.config.xml.AbstractConsumerEndpointParser
 *  org.springframework.integration.config.xml.IntegrationNamespaceUtils
 */
package org.springframework.integration.jmx.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractConsumerEndpointParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.jmx.OperationInvokingMessageHandler;
import org.w3c.dom.Element;

public class OperationInvokingOutboundGatewayParser
extends AbstractConsumerEndpointParser {
    protected String getInputChannelAttributeName() {
        return "request-channel";
    }

    protected BeanDefinitionBuilder parseHandler(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(OperationInvokingMessageHandler.class);
        builder.addConstructorArgReference(element.getAttribute("server"));
        IntegrationNamespaceUtils.setValueIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"object-name");
        IntegrationNamespaceUtils.setValueIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"operation-name");
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"reply-channel", (String)"outputChannel");
        IntegrationNamespaceUtils.setValueIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"requires-reply");
        return builder;
    }
}

