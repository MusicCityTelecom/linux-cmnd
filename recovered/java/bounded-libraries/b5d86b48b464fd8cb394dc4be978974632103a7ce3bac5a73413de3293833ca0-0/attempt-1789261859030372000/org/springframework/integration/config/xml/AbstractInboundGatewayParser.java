/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanDefinitionStoreException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public abstract class AbstractInboundGatewayParser
extends AbstractSimpleBeanDefinitionParser {
    protected String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext) throws BeanDefinitionStoreException {
        String id = super.resolveId(element, definition, parserContext);
        if (!StringUtils.hasText((String)id)) {
            id = element.getAttribute("name");
        }
        if (!StringUtils.hasText((String)id)) {
            id = parserContext.getReaderContext().generateBeanName((BeanDefinition)definition);
        }
        return id;
    }

    protected boolean isEligibleAttribute(String attributeName) {
        return !attributeName.equals("name") && !attributeName.equals("request-channel") && !attributeName.equals("error-channel") && !attributeName.equals("reply-channel") && super.isEligibleAttribute(attributeName);
    }

    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        super.doParse(element, parserContext, builder);
        AbstractBeanDefinition beanDefinition = builder.getRawBeanDefinition();
        beanDefinition.setResource(parserContext.getReaderContext().getResource());
        beanDefinition.setSource((Object)IntegrationNamespaceUtils.createElementDescription(element));
    }

    protected final void postProcess(BeanDefinitionBuilder builder, Element element) {
        String errorChannel;
        String requestChannelRef = element.getAttribute("request-channel");
        Assert.hasText((String)requestChannelRef, (String)"a 'request-channel' reference is required");
        builder.addPropertyValue("requestChannelName", (Object)requestChannelRef);
        String replyChannel = element.getAttribute("reply-channel");
        if (StringUtils.hasText((String)replyChannel)) {
            builder.addPropertyValue("replyChannelName", (Object)replyChannel);
        }
        if (StringUtils.hasText((String)(errorChannel = element.getAttribute("error-channel")))) {
            builder.addPropertyValue("errorChannelName", (Object)errorChannel);
        }
        this.doPostProcess(builder, element);
    }

    protected void doPostProcess(BeanDefinitionBuilder builder, Element element) {
    }
}

