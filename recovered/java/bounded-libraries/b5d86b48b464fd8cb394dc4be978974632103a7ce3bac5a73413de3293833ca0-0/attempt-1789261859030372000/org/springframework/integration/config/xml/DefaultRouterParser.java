/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.ManagedMap
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.RouterFactoryBean;
import org.springframework.integration.config.xml.AbstractDelegatingConsumerEndpointParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class DefaultRouterParser
extends AbstractDelegatingConsumerEndpointParser {
    @Override
    String getFactoryBeanClassName() {
        return RouterFactoryBean.class.getName();
    }

    @Override
    boolean hasDefaultOption() {
        return false;
    }

    @Override
    protected void postProcess(BeanDefinitionBuilder builder, Element element, ParserContext parserContext) {
        List mappingElements = DomUtils.getChildElementsByTagName((Element)element, (String)"mapping");
        if (!CollectionUtils.isEmpty((Collection)mappingElements)) {
            ManagedMap channelMappings = new ManagedMap();
            for (Element mappingElement : mappingElements) {
                channelMappings.put((Object)mappingElement.getAttribute("value"), (Object)mappingElement.getAttribute("channel"));
            }
            builder.addPropertyValue("channelMappings", (Object)channelMappings);
        }
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, "default-output-channel");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "timeout");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "resolution-required");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "apply-sequence");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "ignore-send-failures");
    }
}

