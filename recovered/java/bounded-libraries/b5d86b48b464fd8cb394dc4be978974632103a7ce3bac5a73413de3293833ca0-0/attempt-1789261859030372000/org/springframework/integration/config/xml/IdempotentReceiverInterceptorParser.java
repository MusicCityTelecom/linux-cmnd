/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.RuntimeBeanReference
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.beans.factory.xml.AbstractBeanDefinitionParser
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.ExpressionFactoryBean;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.handler.ExpressionEvaluatingMessageProcessor;
import org.springframework.integration.handler.advice.IdempotentReceiverInterceptor;
import org.springframework.integration.metadata.SimpleMetadataStore;
import org.springframework.integration.selector.MetadataStoreSelector;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class IdempotentReceiverInterceptorParser
extends AbstractBeanDefinitionParser {
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        RuntimeBeanReference selectorBeanDefinition;
        Object source = parserContext.extractSource((Object)element);
        String selector = element.getAttribute("selector");
        boolean hasSelector = StringUtils.hasText((String)selector);
        String store = element.getAttribute("metadata-store");
        boolean hasStore = StringUtils.hasText((String)store);
        String keyStrategy = element.getAttribute("key-strategy");
        boolean hasKeyStrategy = StringUtils.hasText((String)keyStrategy);
        String keyExpression = element.getAttribute("key-expression");
        boolean hasKeyExpression = StringUtils.hasText((String)keyExpression);
        String valueStrategy = element.getAttribute("value-strategy");
        boolean hasValueStrategy = StringUtils.hasText((String)valueStrategy);
        String valueExpression = element.getAttribute("value-expression");
        boolean hasValueExpression = StringUtils.hasText((String)valueExpression);
        String compareValues = element.getAttribute("compare-values");
        boolean hasCompareValues = StringUtils.hasText((String)compareValues);
        String endpoints = element.getAttribute("endpoint");
        if (!(hasSelector || hasKeyStrategy || hasKeyExpression)) {
            parserContext.getReaderContext().error("One of the 'selector', 'key-strategy' or 'key-expression' attributes must be provided", source);
        }
        if (hasSelector && (hasStore || hasKeyStrategy || hasKeyExpression || hasValueStrategy || hasValueExpression || hasCompareValues)) {
            parserContext.getReaderContext().error("The 'selector' attribute is mutually exclusive with 'metadata-store', 'key-strategy', 'key-expression', 'value-strategy', 'value-expression', and 'compare-values'", source);
        }
        if (hasKeyStrategy && hasKeyExpression) {
            parserContext.getReaderContext().error("The 'key-strategy' and 'key-expression' attributes are mutually exclusive", source);
        }
        if (hasValueStrategy && hasValueExpression) {
            parserContext.getReaderContext().error("The 'value-strategy' and 'value-expression' attributes are mutually exclusive", source);
        }
        if (!StringUtils.hasText((String)endpoints)) {
            parserContext.getReaderContext().error("The 'endpoint' attribute is required", source);
        }
        if (hasSelector) {
            selectorBeanDefinition = new RuntimeBeanReference(selector);
        } else {
            BeanDefinitionBuilder selectorBuilder = BeanDefinitionBuilder.genericBeanDefinition(MetadataStoreSelector.class);
            Object keyStrategyBeanDefinition = hasKeyStrategy ? new RuntimeBeanReference(keyStrategy) : BeanDefinitionBuilder.genericBeanDefinition(ExpressionEvaluatingMessageProcessor.class).addConstructorArgValue((Object)BeanDefinitionBuilder.genericBeanDefinition(ExpressionFactoryBean.class).addConstructorArgValue((Object)keyExpression).getBeanDefinition()).getBeanDefinition();
            selectorBuilder.addConstructorArgValue(keyStrategyBeanDefinition);
            RuntimeBeanReference valueStrategyBeanDefinition = null;
            if (hasValueStrategy) {
                valueStrategyBeanDefinition = new RuntimeBeanReference(valueStrategy);
            } else if (hasValueExpression) {
                valueStrategyBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(ExpressionEvaluatingMessageProcessor.class).addConstructorArgValue((Object)BeanDefinitionBuilder.genericBeanDefinition(ExpressionFactoryBean.class).addConstructorArgValue((Object)valueExpression).getBeanDefinition()).getBeanDefinition();
            }
            selectorBuilder.addConstructorArgValue((Object)valueStrategyBeanDefinition);
            if (hasStore) {
                selectorBuilder.addConstructorArgReference(store);
            } else {
                selectorBuilder.addConstructorArgValue((Object)new RootBeanDefinition(SimpleMetadataStore.class));
            }
            IntegrationNamespaceUtils.setReferenceIfAttributeDefined(selectorBuilder, element, "compare-values");
            selectorBeanDefinition = selectorBuilder.getBeanDefinition();
        }
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(IdempotentReceiverInterceptor.class).addConstructorArgValue((Object)selectorBeanDefinition);
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "throw-exception-on-rejection");
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, "discard-channel");
        AbstractBeanDefinition interceptorBeanDefinition = builder.getBeanDefinition();
        interceptorBeanDefinition.setAttribute("IDEMPOTENT_ENDPOINTS_MAPPING", (Object)endpoints);
        return interceptorBeanDefinition;
    }

    protected boolean shouldGenerateIdAsFallback() {
        return true;
    }

    protected boolean shouldFireEvents() {
        return false;
    }
}

