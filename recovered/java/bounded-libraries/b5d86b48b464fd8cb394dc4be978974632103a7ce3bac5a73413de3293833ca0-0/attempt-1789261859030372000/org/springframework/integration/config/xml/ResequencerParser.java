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
import org.springframework.integration.aggregator.ResequencingMessageGroupProcessor;
import org.springframework.integration.aggregator.ResequencingMessageHandler;
import org.springframework.integration.config.xml.AbstractCorrelatingMessageHandlerParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.w3c.dom.Element;

public class ResequencerParser
extends AbstractCorrelatingMessageHandlerParser {
    private static final String RELEASE_PARTIAL_SEQUENCES_ATTRIBUTE = "release-partial-sequences";

    @Override
    protected BeanDefinitionBuilder parseHandler(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ResequencingMessageHandler.class);
        BeanDefinitionBuilder processorBuilder = BeanDefinitionBuilder.genericBeanDefinition(ResequencingMessageGroupProcessor.class);
        builder.addConstructorArgValue((Object)processorBuilder.getBeanDefinition());
        this.doParse(builder, element, null, parserContext);
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, RELEASE_PARTIAL_SEQUENCES_ATTRIBUTE);
        return builder;
    }
}

