/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanMetadataElement
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.RuntimeBeanReference
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.SourcePollingChannelAdapterFactoryBean;
import org.springframework.integration.config.xml.AbstractChannelAdapterParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public abstract class AbstractPollingInboundChannelAdapterParser
extends AbstractChannelAdapterParser {
    @Override
    protected AbstractBeanDefinition doParse(Element element, ParserContext parserContext, String channelName) {
        BeanMetadataElement source = this.parseSource(element, parserContext);
        if (source == null) {
            parserContext.getReaderContext().error("failed to parse source", (Object)element);
        }
        BeanDefinitionBuilder adapterBuilder = BeanDefinitionBuilder.genericBeanDefinition(SourcePollingChannelAdapterFactoryBean.class);
        String sourceBeanName = null;
        if (source instanceof BeanDefinition) {
            String channelAdapterId = this.resolveId(element, adapterBuilder.getRawBeanDefinition(), parserContext);
            sourceBeanName = channelAdapterId + ".source";
            parserContext.getRegistry().registerBeanDefinition(sourceBeanName, (BeanDefinition)source);
        } else if (source instanceof RuntimeBeanReference) {
            sourceBeanName = ((RuntimeBeanReference)source).getBeanName();
        } else {
            parserContext.getReaderContext().error("Wrong 'source' type: must be 'BeanDefinition' or 'RuntimeBeanReference'", (Object)source);
        }
        adapterBuilder.addPropertyReference("source", sourceBeanName);
        adapterBuilder.addPropertyReference("outputChannel", channelName);
        IntegrationNamespaceUtils.setValueIfAttributeDefined(adapterBuilder, element, "send-timeout");
        Element pollerElement = DomUtils.getChildElementByTagName((Element)element, (String)"poller");
        if (pollerElement != null) {
            IntegrationNamespaceUtils.configurePollerMetadata(pollerElement, adapterBuilder, parserContext);
        }
        return adapterBuilder.getBeanDefinition();
    }

    protected abstract BeanMetadataElement parseSource(Element var1, ParserContext var2);
}

