/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractOutboundChannelAdapterParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class LoggingChannelAdapterParser
extends AbstractOutboundChannelAdapterParser {
    @Override
    protected AbstractBeanDefinition parseConsumer(Element element, ParserContext parserContext) {
        Object source = parserContext.extractSource((Object)element);
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(LoggingHandler.class);
        builder.addConstructorArgValue((Object)element.getAttribute("level"));
        String expression = element.getAttribute("expression");
        String logFullMessage = element.getAttribute("log-full-message");
        if (StringUtils.hasText((String)logFullMessage)) {
            if (StringUtils.hasText((String)expression)) {
                parserContext.getReaderContext().error("The 'expression' and 'log-full-message' attributes are mutually exclusive.", source);
            }
            builder.addPropertyValue("shouldLogFullMessage", (Object)logFullMessage);
        }
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "logger-name");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "expression", "logExpressionString");
        return builder.getBeanDefinition();
    }
}

