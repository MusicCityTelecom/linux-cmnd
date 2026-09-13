/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.parsing.BeanComponentDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.StringUtils
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractConsumerEndpointParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.expression.DynamicExpression;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

abstract class AbstractDelegatingConsumerEndpointParser
extends AbstractConsumerEndpointParser {
    AbstractDelegatingConsumerEndpointParser() {
    }

    @Override
    protected final BeanDefinitionBuilder parseHandler(Element element, ParserContext parserContext) {
        Object source = parserContext.extractSource((Object)element);
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition((String)this.getFactoryBeanClassName());
        BeanComponentDefinition innerDefinition = IntegrationNamespaceUtils.parseInnerHandlerDefinition(element, parserContext);
        String ref = element.getAttribute("ref");
        String expression = element.getAttribute("expression");
        boolean hasRef = StringUtils.hasText((String)ref);
        boolean hasExpression = StringUtils.hasText((String)expression);
        Element scriptElement = DomUtils.getChildElementByTagName((Element)element, (String)"script");
        Element expressionElement = DomUtils.getChildElementByTagName((Element)element, (String)"expression");
        if (innerDefinition != null) {
            this.innerDefinition(element, parserContext, source, builder, innerDefinition, hasRef, hasExpression, expressionElement);
        } else if (scriptElement != null) {
            this.scriptElement(element, parserContext, source, builder, hasRef, hasExpression, scriptElement, expressionElement);
        } else if (expressionElement != null) {
            this.expressionElement(element, parserContext, source, builder, hasRef, hasExpression, expressionElement);
        } else {
            if (hasRef && hasExpression) {
                parserContext.getReaderContext().error("Only one of 'ref' or 'expression' is permitted, not both, on element " + IntegrationNamespaceUtils.createElementDescription(element) + ".", source);
                return null;
            }
            if (hasRef) {
                builder.addPropertyReference("targetObject", ref);
            } else if (hasExpression) {
                builder.addPropertyValue("expressionString", (Object)expression);
            } else if (!this.hasDefaultOption()) {
                parserContext.getReaderContext().error("Exactly one of the 'ref' attribute, 'expression' attribute, or inner bean (<bean/>) definition is required for element " + IntegrationNamespaceUtils.createElementDescription(element) + ".", source);
                return null;
            }
        }
        this.methodAttribute(element, parserContext, source, builder, innerDefinition, hasRef, hasExpression, expressionElement);
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "requires-reply");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "send-timeout");
        this.postProcess(builder, element, parserContext);
        return builder;
    }

    private void innerDefinition(Element element, ParserContext parserContext, Object source, BeanDefinitionBuilder builder, BeanComponentDefinition innerDefinition, boolean hasRef, boolean hasExpression, Element expressionElement) {
        if (hasRef || hasExpression || expressionElement != null) {
            parserContext.getReaderContext().error("Neither 'ref' nor 'expression' are permitted when an inner bean (<bean/>) is configured on element " + IntegrationNamespaceUtils.createElementDescription(element) + ".", source);
        }
        builder.addPropertyValue("targetObject", (Object)innerDefinition);
    }

    private void scriptElement(Element element, ParserContext parserContext, Object source, BeanDefinitionBuilder builder, boolean hasRef, boolean hasExpression, Element scriptElement, Element expressionElement) {
        if (hasRef || hasExpression || expressionElement != null) {
            parserContext.getReaderContext().error("Neither 'ref' nor 'expression' are permitted when an inner script element is configured on element " + IntegrationNamespaceUtils.createElementDescription(element) + ".", source);
        }
        BeanDefinition scriptBeanDefinition = parserContext.getDelegate().parseCustomElement(scriptElement, (BeanDefinition)builder.getBeanDefinition());
        builder.addPropertyValue("targetObject", (Object)scriptBeanDefinition);
    }

    private void expressionElement(Element element, ParserContext parserContext, Object source, BeanDefinitionBuilder builder, boolean hasRef, boolean hasExpression, Element expressionElement) {
        if (hasRef || hasExpression) {
            parserContext.getReaderContext().error("Neither 'ref' nor 'expression' are permitted when an inner 'expression' element is configured on element " + IntegrationNamespaceUtils.createElementDescription(element) + ".", source);
        }
        BeanDefinitionBuilder dynamicExpressionBuilder = BeanDefinitionBuilder.genericBeanDefinition(DynamicExpression.class);
        String key = expressionElement.getAttribute("key");
        String expressionSourceReference = expressionElement.getAttribute("source");
        dynamicExpressionBuilder.addConstructorArgValue((Object)key);
        dynamicExpressionBuilder.addConstructorArgReference(expressionSourceReference);
        builder.addPropertyValue("expression", (Object)dynamicExpressionBuilder.getBeanDefinition());
    }

    private void methodAttribute(Element element, ParserContext parserContext, Object source, BeanDefinitionBuilder builder, BeanComponentDefinition innerDefinition, boolean hasRef, boolean hasExpression, Element expressionElement) {
        String method = element.getAttribute("method");
        if (StringUtils.hasText((String)method)) {
            if (hasExpression || expressionElement != null) {
                parserContext.getReaderContext().error("A 'method' attribute is not permitted when configuring an 'expression' on element " + IntegrationNamespaceUtils.createElementDescription(element) + ".", source);
            }
            if (hasRef || innerDefinition != null) {
                builder.addPropertyValue("targetMethodName", (Object)method);
            } else {
                parserContext.getReaderContext().error("A 'method' attribute is only permitted when either a 'ref' or inner-bean definition is provided on element " + IntegrationNamespaceUtils.createElementDescription(element) + ".", source);
            }
        }
    }

    void postProcess(BeanDefinitionBuilder builder, Element element, ParserContext parserContext) {
    }

    abstract boolean hasDefaultOption();

    abstract String getFactoryBeanClassName();
}

