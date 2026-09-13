/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanMetadataElement
 *  org.springframework.beans.factory.config.RuntimeBeanReference
 *  org.springframework.beans.factory.parsing.BeanComponentDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.aggregator.DefaultAggregatingMessageGroupProcessor;
import org.springframework.integration.aggregator.ExpressionEvaluatingMessageGroupProcessor;
import org.springframework.integration.config.AggregatorFactoryBean;
import org.springframework.integration.config.xml.AbstractCorrelatingMessageHandlerParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class AggregatorParser
extends AbstractCorrelatingMessageHandlerParser {
    private static final String EXPIRE_GROUPS_UPON_COMPLETION = "expire-groups-upon-completion";

    @Override
    protected BeanDefinitionBuilder parseHandler(Element element, ParserContext parserContext) {
        BeanComponentDefinition innerHandlerDefinition = IntegrationNamespaceUtils.parseInnerHandlerDefinition(element, parserContext);
        String ref = element.getAttribute("ref");
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(AggregatorFactoryBean.class);
        String headersFunction = element.getAttribute("headers-function");
        Object processor = null;
        if (innerHandlerDefinition != null || StringUtils.hasText((String)ref)) {
            processor = innerHandlerDefinition != null ? innerHandlerDefinition : new RuntimeBeanReference(ref);
            builder.addPropertyValue("processorBean", processor);
            if (StringUtils.hasText((String)headersFunction)) {
                builder.addPropertyReference("headersFunction", headersFunction);
            }
        } else {
            BeanDefinitionBuilder groupProcessorBuilder;
            if (StringUtils.hasText((String)element.getAttribute("expression"))) {
                String expression = element.getAttribute("expression");
                groupProcessorBuilder = BeanDefinitionBuilder.genericBeanDefinition(ExpressionEvaluatingMessageGroupProcessor.class);
                groupProcessorBuilder.addConstructorArgValue((Object)expression);
            } else {
                groupProcessorBuilder = BeanDefinitionBuilder.genericBeanDefinition(DefaultAggregatingMessageGroupProcessor.class);
            }
            builder.addPropertyValue("processorBean", (Object)groupProcessorBuilder.getBeanDefinition());
            if (StringUtils.hasText((String)headersFunction)) {
                groupProcessorBuilder.addPropertyReference("headersFunction", headersFunction);
            }
        }
        if (StringUtils.hasText((String)element.getAttribute("method"))) {
            String method = element.getAttribute("method");
            builder.addPropertyValue("methodName", (Object)method);
        }
        this.doParse(builder, element, (BeanMetadataElement)processor, parserContext);
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, EXPIRE_GROUPS_UPON_COMPLETION);
        return builder;
    }
}

