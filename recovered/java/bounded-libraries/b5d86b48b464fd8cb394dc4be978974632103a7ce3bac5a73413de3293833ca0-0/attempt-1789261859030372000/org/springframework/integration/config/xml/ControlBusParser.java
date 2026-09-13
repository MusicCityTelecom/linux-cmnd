/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractConsumerEndpointParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.w3c.dom.Element;

public class ControlBusParser
extends AbstractConsumerEndpointParser {
    @Override
    protected BeanDefinitionBuilder parseHandler(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition((String)"org.springframework.integration.config.ExpressionControlBusFactoryBean");
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, "bean-resolver");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "send-timeout");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "order");
        return builder;
    }
}

