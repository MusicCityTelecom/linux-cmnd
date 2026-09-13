/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.BeanDefinitionHolder
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionReaderUtils
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.ManagedMap
 *  org.springframework.beans.factory.xml.BeanDefinitionParser
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.StringUtils
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.ExpressionFactoryBean;
import org.springframework.integration.config.MessagingGatewayRegistrar;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.gateway.GatewayMethodMetadata;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class GatewayParser
implements BeanDefinitionParser {
    private final MessagingGatewayRegistrar registrar = new MessagingGatewayRegistrar();

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        boolean isNested = parserContext.isNested();
        HashMap<String, Object> gatewayAttributes = new HashMap<String, Object>();
        gatewayAttributes.put("name", element.getAttribute("id"));
        gatewayAttributes.put("defaultPayloadExpression", element.getAttribute("default-payload-expression"));
        gatewayAttributes.put("defaultRequestChannel", element.getAttribute(isNested ? "request-channel" : "default-request-channel"));
        gatewayAttributes.put("defaultReplyChannel", element.getAttribute(isNested ? "reply-channel" : "default-reply-channel"));
        gatewayAttributes.put("errorChannel", element.getAttribute("error-channel"));
        String asyncExecutor = element.getAttribute("async-executor");
        if (!element.hasAttribute("async-executor") || StringUtils.hasLength((String)asyncExecutor)) {
            gatewayAttributes.put("asyncExecutor", asyncExecutor);
        } else {
            gatewayAttributes.put("asyncExecutor", null);
        }
        gatewayAttributes.put("mapper", element.getAttribute("mapper"));
        gatewayAttributes.put("defaultReplyTimeout", element.getAttribute(isNested ? "reply-timeout" : "default-reply-timeout"));
        gatewayAttributes.put("defaultRequestTimeout", element.getAttribute(isNested ? "request-timeout" : "default-request-timeout"));
        this.headers(element, gatewayAttributes);
        this.methods(element, parserContext, gatewayAttributes);
        gatewayAttributes.put("serviceInterface", element.getAttribute("service-interface"));
        gatewayAttributes.put("proxyDefaultMethods", element.getAttribute("proxy-default-methods"));
        BeanDefinitionHolder gatewayHolder = this.registrar.parse(gatewayAttributes, parserContext.getRegistry());
        if (isNested) {
            return gatewayHolder.getBeanDefinition();
        }
        BeanDefinitionReaderUtils.registerBeanDefinition((BeanDefinitionHolder)gatewayHolder, (BeanDefinitionRegistry)parserContext.getRegistry());
        return null;
    }

    private void headers(Element element, Map<String, Object> gatewayAttributes) {
        List headerElements = DomUtils.getChildElementsByTagName((Element)element, (String)"default-header");
        if (!CollectionUtils.isEmpty((Collection)headerElements)) {
            ArrayList headers = new ArrayList(headerElements.size());
            for (Element e : headerElements) {
                HashMap<String, String> header = new HashMap<String, String>();
                header.put("name", e.getAttribute("name"));
                header.put("value", e.getAttribute("value"));
                header.put("expression", e.getAttribute("expression"));
                headers.add(header);
            }
            gatewayAttributes.put("defaultHeaders", headers.toArray(new Map[0]));
        }
    }

    private void methods(Element element, ParserContext parserContext, Map<String, Object> gatewayAttributes) {
        List methodElements = DomUtils.getChildElementsByTagName((Element)element, (String)"method");
        if (!CollectionUtils.isEmpty((Collection)methodElements)) {
            ManagedMap methodMetadataMap = new ManagedMap();
            for (Element methodElement : methodElements) {
                List invocationHeaders;
                String methodName = methodElement.getAttribute("name");
                BeanDefinitionBuilder methodMetadataBuilder = BeanDefinitionBuilder.genericBeanDefinition(GatewayMethodMetadata.class);
                methodMetadataBuilder.addPropertyValue("requestChannelName", (Object)methodElement.getAttribute("request-channel"));
                methodMetadataBuilder.addPropertyValue("replyChannelName", (Object)methodElement.getAttribute("reply-channel"));
                methodMetadataBuilder.addPropertyValue("requestTimeout", (Object)methodElement.getAttribute("request-timeout"));
                methodMetadataBuilder.addPropertyValue("replyTimeout", (Object)methodElement.getAttribute("reply-timeout"));
                boolean hasMapper = StringUtils.hasText((String)element.getAttribute("mapper"));
                String payloadExpression = methodElement.getAttribute("payload-expression");
                Assert.state((!hasMapper || !StringUtils.hasText((String)payloadExpression) ? 1 : 0) != 0, (String)"'payload-expression' is not allowed when a 'mapper' is provided");
                if (StringUtils.hasText((String)payloadExpression)) {
                    methodMetadataBuilder.addPropertyValue("payloadExpression", (Object)BeanDefinitionBuilder.genericBeanDefinition(ExpressionFactoryBean.class).addConstructorArgValue((Object)payloadExpression).getBeanDefinition());
                }
                if (!CollectionUtils.isEmpty((Collection)(invocationHeaders = DomUtils.getChildElementsByTagName((Element)methodElement, (String)"header")))) {
                    Assert.state((!hasMapper ? 1 : 0) != 0, (String)"header elements are not allowed when a 'mapper' is provided");
                    ManagedMap headerExpressions = new ManagedMap();
                    for (Element headerElement : invocationHeaders) {
                        BeanDefinition expressionDef = IntegrationNamespaceUtils.createExpressionDefinitionFromValueOrExpression("value", "expression", parserContext, headerElement, true);
                        headerExpressions.put(headerElement.getAttribute("name"), expressionDef);
                    }
                    methodMetadataBuilder.addPropertyValue("headerExpressions", (Object)headerExpressions);
                }
                methodMetadataMap.put(methodName, methodMetadataBuilder.getBeanDefinition());
            }
            gatewayAttributes.put("methods", methodMetadataMap);
        }
    }
}

