/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanMetadataElement
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.RuntimeBeanReference
 *  org.springframework.beans.factory.parsing.BeanComponentDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.ManagedMap
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.StringUtils
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import java.util.Collection;
import java.util.List;
import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.ExpressionFactoryBean;
import org.springframework.integration.config.xml.AbstractPollingInboundChannelAdapterParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.endpoint.ExpressionEvaluatingMessageSource;
import org.springframework.integration.endpoint.MethodInvokingMessageSource;
import org.springframework.integration.expression.DynamicExpression;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class DefaultInboundChannelAdapterParser
extends AbstractPollingInboundChannelAdapterParser {
    @Override
    protected BeanMetadataElement parseSource(Element element, ParserContext parserContext) {
        Object source = parserContext.extractSource((Object)element);
        Object result = null;
        BeanComponentDefinition innerBeanDef = IntegrationNamespaceUtils.parseInnerHandlerDefinition(element, parserContext);
        String sourceRef = element.getAttribute("ref");
        String methodName = element.getAttribute("method");
        String expressionString = element.getAttribute("expression");
        Element scriptElement = DomUtils.getChildElementByTagName((Element)element, (String)"script");
        Element expressionElement = DomUtils.getChildElementByTagName((Element)element, (String)"expression");
        boolean hasInnerDef = innerBeanDef != null;
        boolean hasRef = StringUtils.hasText((String)sourceRef);
        boolean hasExpression = StringUtils.hasText((String)expressionString);
        boolean hasScriptElement = scriptElement != null;
        boolean hasExpressionElement = expressionElement != null;
        boolean hasMethod = StringUtils.hasText((String)methodName);
        if (!(hasInnerDef || hasRef || hasExpression || hasScriptElement || hasExpressionElement)) {
            parserContext.getReaderContext().error("Exactly one of the 'ref', 'expression', inner bean, <script> or <expression> is required.", (Object)element);
        }
        if (hasInnerDef) {
            if (hasRef || hasExpression) {
                parserContext.getReaderContext().error("Neither 'ref' nor 'expression' are permitted when an inner bean (<bean/>) is configured on element " + IntegrationNamespaceUtils.createElementDescription(element) + ".", source);
                return null;
            }
            result = hasMethod ? this.parseMethodInvokingSource((BeanMetadataElement)innerBeanDef, methodName, element, parserContext) : innerBeanDef.getBeanDefinition();
        } else if (hasScriptElement) {
            if (hasRef || hasMethod || hasExpression) {
                parserContext.getReaderContext().error("Neither 'ref' and 'method' nor 'expression' are permitted when an inner script element is configured on element " + IntegrationNamespaceUtils.createElementDescription(element) + ".", source);
                return null;
            }
            BeanDefinition scriptBeanDefinition = parserContext.getDelegate().parseCustomElement(scriptElement);
            BeanDefinitionBuilder sourceBuilder = BeanDefinitionBuilder.genericBeanDefinition((String)"org.springframework.integration.scripting.ScriptExecutingMessageSource");
            sourceBuilder.addConstructorArgValue((Object)scriptBeanDefinition);
            this.parseHeaderExpressions(sourceBuilder, element, parserContext);
            result = sourceBuilder.getBeanDefinition();
        } else if (hasExpression || hasExpressionElement) {
            if (hasRef || hasMethod) {
                parserContext.getReaderContext().error("The 'ref' and 'method' attributes can't be used with 'expression' attribute or inner <expression>.", (Object)element);
                return null;
            }
            if (hasExpression & hasExpressionElement) {
                parserContext.getReaderContext().error("Exactly one of the 'expression' attribute or inner <expression> is required.", (Object)element);
                return null;
            }
            result = this.parseExpression(expressionString, expressionElement, element, parserContext);
        } else if (hasRef) {
            RuntimeBeanReference sourceValue = new RuntimeBeanReference(sourceRef);
            result = hasMethod ? this.parseMethodInvokingSource((BeanMetadataElement)sourceValue, methodName, element, parserContext) : sourceValue;
        }
        return result;
    }

    private BeanMetadataElement parseMethodInvokingSource(BeanMetadataElement targetObject, String methodName, Element element, ParserContext parserContext) {
        BeanDefinitionBuilder sourceBuilder = BeanDefinitionBuilder.genericBeanDefinition(MethodInvokingMessageSource.class);
        sourceBuilder.addPropertyValue("object", (Object)targetObject);
        sourceBuilder.addPropertyValue("methodName", (Object)methodName);
        this.parseHeaderExpressions(sourceBuilder, element, parserContext);
        return sourceBuilder.getBeanDefinition();
    }

    private BeanMetadataElement parseExpression(String expressionString, Element expressionElement, Element element, ParserContext parserContext) {
        RootBeanDefinition expressionDef;
        BeanDefinitionBuilder sourceBuilder = BeanDefinitionBuilder.genericBeanDefinition(ExpressionEvaluatingMessageSource.class);
        if (StringUtils.hasText((String)expressionString)) {
            expressionDef = new RootBeanDefinition(ExpressionFactoryBean.class);
            expressionDef.getConstructorArgumentValues().addGenericArgumentValue((Object)expressionString);
        } else {
            BeanDefinitionBuilder dynamicExpressionBuilder = BeanDefinitionBuilder.genericBeanDefinition(DynamicExpression.class);
            String key = expressionElement.getAttribute("key");
            String expressionSourceReference = expressionElement.getAttribute("source");
            dynamicExpressionBuilder.addConstructorArgValue((Object)key);
            dynamicExpressionBuilder.addConstructorArgReference(expressionSourceReference);
            expressionDef = dynamicExpressionBuilder.getBeanDefinition();
        }
        sourceBuilder.addConstructorArgValue((Object)expressionDef);
        sourceBuilder.addConstructorArgValue(null);
        this.parseHeaderExpressions(sourceBuilder, element, parserContext);
        return sourceBuilder.getBeanDefinition();
    }

    private void parseHeaderExpressions(BeanDefinitionBuilder builder, Element element, ParserContext parserContext) {
        List headerElements = DomUtils.getChildElementsByTagName((Element)element, (String)"header");
        if (!CollectionUtils.isEmpty((Collection)headerElements)) {
            ManagedMap headerExpressions = new ManagedMap();
            for (Element headerElement : headerElements) {
                String headerName = headerElement.getAttribute("name");
                BeanDefinition expressionDef = IntegrationNamespaceUtils.createExpressionDefinitionFromValueOrExpression("value", "expression", parserContext, headerElement, true);
                headerExpressions.put((Object)headerName, (Object)expressionDef);
            }
            builder.addPropertyValue("headerExpressions", (Object)headerExpressions);
        }
    }
}

