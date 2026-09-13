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
import org.springframework.integration.config.xml.AbstractConsumerEndpointParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public abstract class AbstractOutboundGatewayParser
extends AbstractConsumerEndpointParser {
    protected abstract String getGatewayClassName(Element var1);

    @Override
    protected String getInputChannelAttributeName() {
        return "request-channel";
    }

    @Override
    protected BeanDefinitionBuilder parseHandler(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition((String)this.getGatewayClassName(element));
        String url = this.parseUrl(element, parserContext);
        builder.addConstructorArgValue((Object)url);
        String replyChannel = element.getAttribute("reply-channel");
        if (StringUtils.hasText((String)replyChannel)) {
            builder.addPropertyReference("replyChannel", replyChannel);
        }
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "requires-reply");
        this.postProcessGateway(builder, element, parserContext);
        return builder;
    }

    protected String parseUrl(Element element, ParserContext parserContext) {
        String url = element.getAttribute("url");
        if (!StringUtils.hasText((String)url)) {
            parserContext.getReaderContext().error("The 'url' attribute is required.", (Object)element);
        }
        return url;
    }

    protected void postProcessGateway(BeanDefinitionBuilder builder, Element element, ParserContext parserContext) {
    }
}

