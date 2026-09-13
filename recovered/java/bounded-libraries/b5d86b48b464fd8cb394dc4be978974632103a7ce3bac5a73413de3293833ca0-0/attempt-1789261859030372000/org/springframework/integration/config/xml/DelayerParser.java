/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.StringUtils
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.ExpressionFactoryBean;
import org.springframework.integration.config.xml.AbstractConsumerEndpointParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.expression.DynamicExpression;
import org.springframework.integration.handler.DelayHandler;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class DelayerParser
extends AbstractConsumerEndpointParser {
    @Override
    protected BeanDefinitionBuilder parseHandler(Element element, ParserContext parserContext) {
        boolean hasExpressionElement;
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DelayHandler.class);
        String id = element.getAttribute("id");
        if (!StringUtils.hasText((String)id)) {
            parserContext.getReaderContext().error("The 'id' attribute is required.", (Object)element);
        }
        String defaultDelay = element.getAttribute("default-delay");
        String expression = element.getAttribute("expression");
        Element expressionElement = DomUtils.getChildElementByTagName((Element)element, (String)"expression");
        boolean hasDefaultDelay = StringUtils.hasText((String)defaultDelay);
        boolean hasExpression = StringUtils.hasText((String)expression);
        boolean bl = hasExpressionElement = expressionElement != null;
        if (!(hasDefaultDelay | hasExpression | hasExpressionElement)) {
            parserContext.getReaderContext().error("The 'default-delay' or 'expression' attributes, or 'expression' sub-element should be provided.", (Object)element);
        }
        if (hasExpression & hasExpressionElement) {
            parserContext.getReaderContext().error("'expression' attribute and 'expression' sub-element are mutually exclusive.", (Object)element);
        }
        builder.addConstructorArgValue((Object)(id + ".messageGroupId"));
        String scheduler = element.getAttribute("scheduler");
        if (StringUtils.hasText((String)scheduler)) {
            builder.addConstructorArgReference(scheduler);
        }
        if (hasDefaultDelay) {
            builder.addPropertyValue("defaultDelay", (Object)defaultDelay);
        }
        BeanDefinitionBuilder expressionBuilder = null;
        if (hasExpression) {
            expressionBuilder = BeanDefinitionBuilder.genericBeanDefinition(ExpressionFactoryBean.class);
            expressionBuilder.addConstructorArgValue((Object)expression);
        } else if (expressionElement != null) {
            expressionBuilder = BeanDefinitionBuilder.genericBeanDefinition(DynamicExpression.class);
            String key = expressionElement.getAttribute("key");
            String expressionSourceReference = expressionElement.getAttribute("source");
            expressionBuilder.addConstructorArgValue((Object)key);
            expressionBuilder.addConstructorArgReference(expressionSourceReference);
        }
        if (expressionBuilder != null) {
            builder.addPropertyValue("delayExpression", (Object)expressionBuilder.getBeanDefinition());
        }
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, "message-store");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "send-timeout");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "ignore-expression-failures");
        Element txElement = DomUtils.getChildElementByTagName((Element)element, (String)"transactional");
        Element adviceChainElement = DomUtils.getChildElementByTagName((Element)element, (String)"advice-chain");
        IntegrationNamespaceUtils.configureAndSetAdviceChainIfPresent(adviceChainElement, txElement, (BeanDefinition)builder.getRawBeanDefinition(), parserContext, "delayedAdviceChain");
        if (txElement != null) {
            element.removeChild(txElement);
        }
        return builder;
    }
}

