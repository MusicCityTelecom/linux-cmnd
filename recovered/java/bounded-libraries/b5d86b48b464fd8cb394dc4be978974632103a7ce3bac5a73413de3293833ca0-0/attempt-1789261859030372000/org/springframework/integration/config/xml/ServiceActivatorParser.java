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
import org.springframework.integration.config.ServiceActivatorFactoryBean;
import org.springframework.integration.config.xml.AbstractDelegatingConsumerEndpointParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.w3c.dom.Element;

public class ServiceActivatorParser
extends AbstractDelegatingConsumerEndpointParser {
    @Override
    String getFactoryBeanClassName() {
        return ServiceActivatorFactoryBean.class.getName();
    }

    @Override
    boolean hasDefaultOption() {
        return false;
    }

    @Override
    void postProcess(BeanDefinitionBuilder builder, Element element, ParserContext parserContext) {
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "async");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "not-propagated-headers");
    }
}

