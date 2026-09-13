/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config.xml;

import java.util.Map;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.HeaderEnricherParserSupport;
import org.springframework.integration.handler.MethodInvokingMessageProcessor;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class StandardHeaderEnricherParser
extends HeaderEnricherParserSupport {
    public StandardHeaderEnricherParser() {
        this.addElementToHeaderMapping("reply-channel", "replyChannel");
        this.addElementToHeaderMapping("error-channel", "errorChannel");
        this.addElementToHeaderMapping("correlation-id", "correlationId");
        this.addElementToHeaderMapping("expiration-date", "expirationDate", Long.class.getName());
        this.addElementToHeaderMapping("priority", "priority", Integer.class.getName());
        this.addElementToHeaderMapping("routing-slip", "routingSlip", Map.class.getName());
    }

    @Override
    protected void postProcessHeaderEnricher(BeanDefinitionBuilder builder, Element element, ParserContext parserContext) {
        String ref = element.getAttribute("ref");
        String method = element.getAttribute("method");
        if (StringUtils.hasText((String)ref) || StringUtils.hasText((String)method)) {
            if (!StringUtils.hasText((String)ref) || !StringUtils.hasText((String)method)) {
                parserContext.getReaderContext().error("If either 'ref' or 'method' is provided, then they are both required.", parserContext.extractSource((Object)element));
                return;
            }
            BeanDefinitionBuilder processorBuilder = BeanDefinitionBuilder.genericBeanDefinition(MethodInvokingMessageProcessor.class);
            processorBuilder.addConstructorArgReference(ref);
            processorBuilder.addConstructorArgValue((Object)method);
            builder.addPropertyValue("messageProcessor", (Object)processorBuilder.getBeanDefinition());
        }
    }
}

