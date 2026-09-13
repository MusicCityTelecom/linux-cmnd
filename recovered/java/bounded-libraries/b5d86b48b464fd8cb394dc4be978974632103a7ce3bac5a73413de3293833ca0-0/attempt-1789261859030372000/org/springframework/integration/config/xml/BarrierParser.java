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
import org.springframework.integration.aggregator.BarrierMessageHandler;
import org.springframework.integration.config.xml.AbstractConsumerEndpointParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class BarrierParser
extends AbstractConsumerEndpointParser {
    @Override
    protected BeanDefinitionBuilder parseHandler(Element element, ParserContext parserContext) {
        String processor;
        BeanDefinitionBuilder handlerBuilder = BeanDefinitionBuilder.genericBeanDefinition(BarrierMessageHandler.class);
        handlerBuilder.addConstructorArgValue((Object)element.getAttribute("timeout"));
        String triggerTimeout = element.getAttribute("trigger-timeout");
        if (StringUtils.hasText((String)triggerTimeout)) {
            handlerBuilder.addConstructorArgValue((Object)triggerTimeout);
        }
        if (StringUtils.hasText((String)(processor = element.getAttribute("output-processor")))) {
            handlerBuilder.addConstructorArgReference(processor);
        }
        IntegrationNamespaceUtils.injectConstructorWithAdapter("correlation-strategy", "correlation-strategy-method", "correlation-strategy-expression", "CorrelationStrategy", element, handlerBuilder, null, parserContext);
        IntegrationNamespaceUtils.setValueIfAttributeDefined(handlerBuilder, element, "requires-reply");
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(handlerBuilder, element, "discard-channel");
        return handlerBuilder;
    }
}

