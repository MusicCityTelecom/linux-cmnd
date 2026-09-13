/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.ManagedMap
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.StringUtils
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.RouterFactoryBean;
import org.springframework.integration.config.xml.AbstractConsumerEndpointParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public abstract class AbstractRouterParser
extends AbstractConsumerEndpointParser {
    @Override
    protected final BeanDefinitionBuilder parseHandler(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(RouterFactoryBean.class);
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, "default-output-channel");
        if (StringUtils.hasText((String)element.getAttribute("timeout")) && StringUtils.hasText((String)element.getAttribute("send-timeout"))) {
            parserContext.getReaderContext().error("Only one of 'timeout' and 'send-timeout' is allowed", (Object)element);
        }
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "timeout", "sendTimeout");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "send-timeout");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "resolution-required");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "apply-sequence");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "ignore-send-failures");
        BeanDefinition targetRouterBeanDefinition = this.parseRouter(element, parserContext);
        builder.addPropertyValue("targetObject", (Object)targetRouterBeanDefinition);
        return builder;
    }

    protected final BeanDefinition parseRouter(Element element, ParserContext parserContext) {
        List mappingElements;
        BeanDefinition beanDefinition = this.doParseRouter(element, parserContext);
        if (beanDefinition != null && !CollectionUtils.isEmpty((Collection)(mappingElements = DomUtils.getChildElementsByTagName((Element)element, (String)"mapping")))) {
            ManagedMap channelMappings = new ManagedMap();
            for (Element mappingElement : mappingElements) {
                String key = mappingElement.getAttribute(this.getMappingKeyAttributeName());
                channelMappings.put((Object)key, (Object)mappingElement.getAttribute("channel"));
            }
            beanDefinition.getPropertyValues().add("channelMappings", (Object)channelMappings);
        }
        return beanDefinition;
    }

    protected String getMappingKeyAttributeName() {
        return "value";
    }

    protected abstract BeanDefinition doParseRouter(Element var1, ParserContext var2);
}

