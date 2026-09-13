/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanMetadataElement
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractPollingInboundChannelAdapterParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.resource.ResourceRetrievingMessageSource;
import org.springframework.integration.util.AcceptOnceCollectionFilter;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class ResourceInboundChannelAdapterParser
extends AbstractPollingInboundChannelAdapterParser {
    private static final String FILTER = "filter";

    @Override
    protected BeanMetadataElement parseSource(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder sourceBuilder = BeanDefinitionBuilder.genericBeanDefinition(ResourceRetrievingMessageSource.class);
        sourceBuilder.addConstructorArgValue((Object)element.getAttribute("pattern"));
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(sourceBuilder, element, "pattern-resolver");
        boolean hasFilter = element.hasAttribute(FILTER);
        if (hasFilter) {
            String filterValue = element.getAttribute(FILTER);
            if (StringUtils.hasText((String)filterValue)) {
                sourceBuilder.addPropertyReference(FILTER, filterValue);
            }
        } else {
            BeanDefinitionBuilder filterBuilder = BeanDefinitionBuilder.genericBeanDefinition(AcceptOnceCollectionFilter.class);
            sourceBuilder.addPropertyValue(FILTER, (Object)filterBuilder.getBeanDefinition());
        }
        return sourceBuilder.getBeanDefinition();
    }
}

