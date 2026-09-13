/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.TypedStringValue
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.ManagedMap
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.expression.common.LiteralExpression
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.StringUtils
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.config.ExpressionFactoryBean;
import org.springframework.integration.config.xml.AbstractConsumerEndpointParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.expression.ValueExpression;
import org.springframework.integration.transformer.ContentEnricher;
import org.springframework.integration.transformer.support.ExpressionEvaluatingHeaderValueMessageProcessor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class EnricherParser
extends AbstractConsumerEndpointParser {
    private static final String TYPE_ATTRIBUTE = "type";

    @Override
    protected BeanDefinitionBuilder parseHandler(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ContentEnricher.class);
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, "request-channel");
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, "reply-channel");
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, "error-channel");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "request-timeout");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "reply-timeout");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "requires-reply");
        this.propertySubElements(element, parserContext, builder);
        this.headerSubElements(element, parserContext, builder);
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "should-clone-payload");
        String requestPayloadExpression = element.getAttribute("request-payload-expression");
        if (StringUtils.hasText((String)requestPayloadExpression)) {
            BeanDefinitionBuilder expressionBuilder = BeanDefinitionBuilder.genericBeanDefinition(ExpressionFactoryBean.class).addConstructorArgValue((Object)requestPayloadExpression);
            builder.addPropertyValue("requestPayloadExpression", (Object)expressionBuilder.getBeanDefinition());
        }
        return builder;
    }

    private void propertySubElements(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        List subElements = DomUtils.getChildElementsByTagName((Element)element, (String)"property");
        if (!CollectionUtils.isEmpty((Collection)subElements)) {
            ManagedMap expressions = new ManagedMap();
            ManagedMap nullResultExpressions = new ManagedMap();
            for (Element subElement : subElements) {
                String name = subElement.getAttribute("name");
                String value = subElement.getAttribute("value");
                String type = subElement.getAttribute(TYPE_ATTRIBUTE);
                String expression = subElement.getAttribute("expression");
                String nullResultExpression = subElement.getAttribute("null-result-expression");
                boolean hasAttributeValue = StringUtils.hasText((String)value);
                boolean hasAttributeExpression = StringUtils.hasText((String)expression);
                boolean hasAttributeNullResultExpression = StringUtils.hasText((String)nullResultExpression);
                if (hasAttributeValue && hasAttributeExpression) {
                    parserContext.getReaderContext().error("Only one of 'value' or 'expression' is allowed", (Object)element);
                }
                if (!(hasAttributeValue || hasAttributeExpression || hasAttributeNullResultExpression)) {
                    parserContext.getReaderContext().error("One of 'value' or 'expression' or 'null-result-expression' is required", (Object)element);
                }
                this.expression(element, parserContext, (ManagedMap<String, Object>)expressions, (ManagedMap<String, Object>)nullResultExpressions, name, value, type, expression, nullResultExpression, hasAttributeValue, hasAttributeExpression, hasAttributeNullResultExpression);
            }
            if (expressions.size() > 0) {
                builder.addPropertyValue("propertyExpressions", (Object)expressions);
            }
            if (nullResultExpressions.size() > 0) {
                builder.addPropertyValue("nullResultPropertyExpressions", (Object)nullResultExpressions);
            }
        }
    }

    private void expression(Element element, ParserContext parserContext, ManagedMap<String, Object> expressions, ManagedMap<String, Object> nullResultExpressions, String name, String value, String type, String expression, String nullResultExpression, boolean hasAttributeValue, boolean hasAttributeExpression, boolean hasAttributeNullResultExpression) {
        AbstractBeanDefinition expressionDef = null;
        if (hasAttributeValue) {
            BeanDefinitionBuilder expressionBuilder = BeanDefinitionBuilder.genericBeanDefinition(ValueExpression.class);
            if (StringUtils.hasText((String)type)) {
                expressionBuilder.addConstructorArgValue((Object)new TypedStringValue(value, type));
            } else {
                expressionBuilder.addConstructorArgValue((Object)value);
            }
            expressionDef = expressionBuilder.getBeanDefinition();
        } else if (hasAttributeExpression) {
            if (StringUtils.hasText((String)type)) {
                parserContext.getReaderContext().error("The 'type' attribute for '<property>' of '<enricher>' is not allowed with an 'expression' attribute.", (Object)element);
            }
            expressionDef = BeanDefinitionBuilder.genericBeanDefinition(ExpressionFactoryBean.class).addConstructorArgValue((Object)expression).getBeanDefinition();
        }
        if (expressionDef != null) {
            expressions.put((Object)name, expressionDef);
        }
        if (hasAttributeNullResultExpression) {
            AbstractBeanDefinition nullResultExpressionExpressionDef = BeanDefinitionBuilder.genericBeanDefinition(ExpressionFactoryBean.class).addConstructorArgValue((Object)nullResultExpression).getBeanDefinition();
            nullResultExpressions.put((Object)name, (Object)nullResultExpressionExpressionDef);
        }
    }

    private void headerSubElements(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        List subElements = DomUtils.getChildElementsByTagName((Element)element, (String)"header");
        if (!CollectionUtils.isEmpty((Collection)subElements)) {
            ManagedMap expressions = new ManagedMap();
            ManagedMap nullResultHeaderExpressions = new ManagedMap();
            for (Element subElement : subElements) {
                String name = subElement.getAttribute("name");
                String nullResultHeaderExpression = subElement.getAttribute("null-result-expression");
                String valueElementValue = subElement.getAttribute("value");
                String expressionElementValue = subElement.getAttribute("expression");
                boolean hasAttributeValue = StringUtils.hasText((String)valueElementValue);
                boolean hasAttributeExpression = StringUtils.hasText((String)expressionElementValue);
                boolean hasAttributeNullResultExpression = StringUtils.hasText((String)nullResultHeaderExpression);
                if (hasAttributeValue && hasAttributeExpression) {
                    parserContext.getReaderContext().error("Only one of 'value' or 'expression' is allowed", (Object)subElement);
                }
                if (!(hasAttributeValue || hasAttributeExpression || hasAttributeNullResultExpression)) {
                    parserContext.getReaderContext().error("One of 'value' or 'expression' or 'null-result-expression' is required", (Object)subElement);
                }
                this.headerExpression(parserContext, (ManagedMap<String, Object>)expressions, (ManagedMap<String, Object>)nullResultHeaderExpressions, subElement, name, valueElementValue, hasAttributeValue, hasAttributeExpression, hasAttributeNullResultExpression);
            }
            if (expressions.size() > 0) {
                builder.addPropertyValue("headerExpressions", (Object)expressions);
            }
            if (nullResultHeaderExpressions.size() > 0) {
                builder.addPropertyValue("nullResultHeaderExpressions", (Object)nullResultHeaderExpressions);
            }
        }
    }

    private void headerExpression(ParserContext parserContext, ManagedMap<String, Object> expressions, ManagedMap<String, Object> nullResultHeaderExpressions, Element subElement, String name, String valueElementValue, boolean hasAttributeValue, boolean hasAttributeExpression, boolean hasAttributeNullResultExpression) {
        RootBeanDefinition expressionDef = null;
        if (hasAttributeValue) {
            expressionDef = new RootBeanDefinition(LiteralExpression.class);
            expressionDef.getConstructorArgumentValues().addGenericArgumentValue((Object)valueElementValue);
        } else if (hasAttributeExpression) {
            expressionDef = IntegrationNamespaceUtils.createExpressionDefIfAttributeDefined("expression", subElement);
        }
        if (StringUtils.hasText((String)subElement.getAttribute("expression")) && StringUtils.hasText((String)subElement.getAttribute(TYPE_ATTRIBUTE))) {
            parserContext.getReaderContext().warning("The use of a 'type' attribute is deprecated since 4.0 when using 'expression'", (Object)subElement);
        }
        if (expressionDef != null) {
            BeanDefinitionBuilder valueProcessorBuilder = BeanDefinitionBuilder.genericBeanDefinition(ExpressionEvaluatingHeaderValueMessageProcessor.class).addConstructorArgValue((Object)expressionDef).addConstructorArgValue((Object)subElement.getAttribute(TYPE_ATTRIBUTE));
            IntegrationNamespaceUtils.setValueIfAttributeDefined(valueProcessorBuilder, subElement, "overwrite");
            expressions.put((Object)name, (Object)valueProcessorBuilder.getBeanDefinition());
        }
        if (hasAttributeNullResultExpression) {
            BeanDefinition nullResultExpressionDefinition = IntegrationNamespaceUtils.createExpressionDefIfAttributeDefined("null-result-expression", subElement);
            BeanDefinitionBuilder nullResultValueProcessorBuilder = BeanDefinitionBuilder.genericBeanDefinition(ExpressionEvaluatingHeaderValueMessageProcessor.class).addConstructorArgValue((Object)nullResultExpressionDefinition).addConstructorArgValue((Object)subElement.getAttribute(TYPE_ATTRIBUTE));
            IntegrationNamespaceUtils.setValueIfAttributeDefined(nullResultValueProcessorBuilder, subElement, "overwrite");
            nullResultHeaderExpressions.put((Object)name, (Object)nullResultValueProcessorBuilder.getBeanDefinition());
        }
    }

    @Override
    protected boolean replyChannelInChainAllowed(Element element) {
        return true;
    }
}

