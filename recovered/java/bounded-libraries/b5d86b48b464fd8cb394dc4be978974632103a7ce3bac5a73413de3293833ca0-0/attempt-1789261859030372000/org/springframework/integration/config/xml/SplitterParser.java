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
import org.springframework.integration.config.SplitterFactoryBean;
import org.springframework.integration.config.xml.AbstractDelegatingConsumerEndpointParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.w3c.dom.Element;

public class SplitterParser
extends AbstractDelegatingConsumerEndpointParser {
    @Override
    String getFactoryBeanClassName() {
        return SplitterFactoryBean.class.getName();
    }

    @Override
    boolean hasDefaultOption() {
        return true;
    }

    @Override
    void postProcess(BeanDefinitionBuilder builder, Element element, ParserContext parserContext) {
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "apply-sequence");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "delimiters");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "discard-channel", "discardChannelName");
    }
}

