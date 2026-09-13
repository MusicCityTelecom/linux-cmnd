/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.RuntimeBeanReference
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.ManagedMap
 *  org.springframework.beans.factory.xml.AbstractBeanDefinitionParser
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.StringUtils
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.aop.MessagePublishingInterceptor;
import org.springframework.integration.aop.MethodNameMappingPublisherMetadataSource;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class PublishingInterceptorParser
extends AbstractBeanDefinitionParser {
    private static final String PAYLOAD = "payload";

    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder rootBuilder = BeanDefinitionBuilder.genericBeanDefinition(MessagePublishingInterceptor.class);
        BeanDefinitionBuilder spelSourceBuilder = BeanDefinitionBuilder.genericBeanDefinition(MethodNameMappingPublisherMetadataSource.class);
        Map<String, Map<?, ?>> mappings = this.getMappings(element, element.getAttribute("default-channel"), parserContext);
        spelSourceBuilder.addConstructorArgValue(mappings.get(PAYLOAD));
        if (mappings.get("headers") != null) {
            spelSourceBuilder.addPropertyValue("headerExpressionMap", mappings.get("headers"));
        }
        if (mappings.get("channels") != null) {
            spelSourceBuilder.addPropertyValue("channelMap", mappings.get("channels"));
        }
        String defaultChannel = StringUtils.hasText((String)element.getAttribute("default-channel")) ? element.getAttribute("default-channel") : "nullChannel";
        rootBuilder.addConstructorArgValue((Object)spelSourceBuilder.getBeanDefinition());
        rootBuilder.addPropertyValue("defaultChannelName", (Object)defaultChannel);
        return rootBuilder.getBeanDefinition();
    }

    private Map<String, Map<?, ?>> getMappings(Element element, String defaultChannel, ParserContext parserContext) {
        List mappings = DomUtils.getChildElementsByTagName((Element)element, (String)"method");
        HashMap interceptorMappings = new HashMap();
        HashMap<String, String> payloadExpressionMap = new HashMap<String, String>();
        HashMap<String, Map<String, String>> headersExpressionMap = new HashMap<String, Map<String, String>>();
        HashMap<String, String> channelMap = new HashMap<String, String>();
        ManagedMap resolvableChannelMap = new ManagedMap();
        if (!CollectionUtils.isEmpty((Collection)mappings)) {
            for (Element mapping : mappings) {
                String tmpChannel;
                String methodPattern = StringUtils.hasText((String)mapping.getAttribute("pattern")) ? mapping.getAttribute("pattern") : "*";
                String payloadExpression = StringUtils.hasText((String)mapping.getAttribute(PAYLOAD)) ? mapping.getAttribute(PAYLOAD) : "#return";
                payloadExpressionMap.put(methodPattern, payloadExpression);
                Map<String, String> headerExpressions = this.headerExpressions(parserContext, mapping);
                if (headerExpressions.size() > 0) {
                    headersExpressionMap.put(methodPattern, headerExpressions);
                }
                String channel = StringUtils.hasText((String)(tmpChannel = mapping.getAttribute("channel"))) ? tmpChannel : defaultChannel;
                channelMap.put(methodPattern, channel);
                resolvableChannelMap.put((Object)channel, (Object)new RuntimeBeanReference(channel));
            }
        }
        if (payloadExpressionMap.size() == 0) {
            payloadExpressionMap.put("*", "#return");
        }
        interceptorMappings.put(PAYLOAD, payloadExpressionMap);
        if (headersExpressionMap.size() > 0) {
            interceptorMappings.put("headers", headersExpressionMap);
        }
        if (channelMap.size() > 0) {
            interceptorMappings.put("channels", channelMap);
            interceptorMappings.put("resolvableChannels", (Map<?, ?>)resolvableChannelMap);
        }
        return interceptorMappings;
    }

    private Map<String, String> headerExpressions(ParserContext parserContext, Element mapping) {
        List headerElements = DomUtils.getChildElementsByTagName((Element)mapping, (String)"header");
        HashMap<String, String> headerExpressions = new HashMap<String, String>();
        for (Element headerElement : headerElements) {
            boolean hasExpression;
            String name = headerElement.getAttribute("name");
            if (!StringUtils.hasText((String)name)) {
                parserContext.getReaderContext().error("the 'name' attribute is required on the <header> element", parserContext.extractSource((Object)headerElement));
                continue;
            }
            String value = headerElement.getAttribute("value");
            String expression = headerElement.getAttribute("expression");
            boolean hasValue = StringUtils.hasText((String)value);
            if (hasValue == (hasExpression = StringUtils.hasText((String)expression))) {
                parserContext.getReaderContext().error("exactly one of 'value' or 'expression' is required on the <header> element", parserContext.extractSource((Object)headerElement));
                continue;
            }
            if (hasValue) {
                expression = "'" + value + "'";
            }
            headerExpressions.put(name, expression);
        }
        return headerExpressions;
    }
}

