/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.FilterFactoryBean;
import org.springframework.integration.config.xml.AbstractDelegatingConsumerEndpointParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class FilterParser
extends AbstractDelegatingConsumerEndpointParser {
    @Override
    String getFactoryBeanClassName() {
        return FilterFactoryBean.class.getName();
    }

    @Override
    boolean hasDefaultOption() {
        return false;
    }

    @Override
    void postProcess(BeanDefinitionBuilder builder, Element element, ParserContext parserContext) {
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, "discard-channel");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "throw-exception-on-rejection");
        Element adviceChainElement = DomUtils.getChildElementByTagName((Element)element, (String)"request-handler-advice-chain");
        if (adviceChainElement != null) {
            IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, adviceChainElement, "discard-within-advice");
        }
    }
}

