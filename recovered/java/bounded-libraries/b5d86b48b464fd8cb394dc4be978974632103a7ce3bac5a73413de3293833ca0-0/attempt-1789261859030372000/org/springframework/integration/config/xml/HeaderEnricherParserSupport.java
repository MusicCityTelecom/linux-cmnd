/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.TypedStringValue
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.ManagedList
 *  org.springframework.beans.factory.support.ManagedMap
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.lang.Nullable
 *  org.springframework.util.StringUtils
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractTransformerParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.expression.DynamicExpression;
import org.springframework.integration.transformer.HeaderEnricher;
import org.springframework.integration.transformer.support.ExpressionEvaluatingHeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.MessageProcessingHeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.RoutingSlipHeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.StaticHeaderValueMessageProcessor;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class HeaderEnricherParserSupport
extends AbstractTransformerParser {
    private static final String TYPE_ATTRIBUTE = "type";
    private static final Map<String, String[][]> cannedHeaderElementExpressions = new HashMap<String, String[][]>();
    private final Map<String, String> elementToNameMap = new HashMap<String, String>();
    private final Map<String, String> elementToTypeMap = new HashMap<String, String>();

    @Override
    protected final String getTransformerClassName() {
        return HeaderEnricher.class.getName();
    }

    protected final void addElementToHeaderMapping(String elementName, String headerName) {
        this.addElementToHeaderMapping(elementName, headerName, null);
    }

    protected final void addElementToHeaderMapping(String elementName, String headerName, String headerType) {
        this.elementToNameMap.put(elementName, headerName);
        if (headerType != null) {
            this.elementToTypeMap.put(elementName, headerType);
        }
    }

    @Override
    protected void parseTransformer(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        ManagedMap headers = new ManagedMap();
        this.processHeaders(element, (ManagedMap<String, Object>)headers, parserContext);
        builder.addConstructorArgValue((Object)headers);
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "default-overwrite");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "should-skip-nulls");
        this.postProcessHeaderEnricher(builder, element, parserContext);
    }

    protected void processHeaders(Element element, ManagedMap<String, Object> headers, ParserContext parserContext) {
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); ++i) {
            Node node = childNodes.item(i);
            if (node.getNodeType() != 1) continue;
            this.elementNode(element, headers, parserContext, node);
        }
    }

    private void elementNode(Element element, ManagedMap<String, Object> headers, ParserContext parserContext, Node node) {
        String headerName = null;
        Element headerElement = (Element)node;
        String elementName = node.getLocalName();
        String headerType = null;
        String expression = null;
        String overwrite = headerElement.getAttribute("overwrite");
        if ("header".equals(elementName)) {
            headerName = headerElement.getAttribute("name");
        } else {
            headerName = this.elementToNameMap.get(elementName);
            headerType = this.elementToTypeMap.get(elementName);
            if (headerType != null && StringUtils.hasText((String)headerElement.getAttribute(TYPE_ATTRIBUTE))) {
                parserContext.getReaderContext().error("The " + elementName + " header does not accept a 'type' attribute. The required type is [" + headerType + "]", (Object)element);
            }
        }
        if (headerType == null) {
            headerType = headerElement.getAttribute(TYPE_ATTRIBUTE);
        }
        if (headerName == null) {
            String ttlExpression = headerElement.getAttribute("time-to-live-expression");
            if (cannedHeaderElementExpressions.containsKey(elementName)) {
                for (int j = 0; j < cannedHeaderElementExpressions.get(elementName).length; ++j) {
                    headerName = cannedHeaderElementExpressions.get(elementName)[j][0];
                    expression = cannedHeaderElementExpressions.get(elementName)[j][1];
                    expression = StringUtils.hasText((String)ttlExpression) ? expression.replace("####", ttlExpression) : expression.replace(", ####", "");
                    overwrite = "true";
                    this.addHeader(element, headers, parserContext, headerName, headerElement, headerType, expression, overwrite);
                }
            }
        } else {
            this.addHeader(element, headers, parserContext, headerName, headerElement, headerType, null, overwrite);
        }
    }

    private void addHeader(Element element, ManagedMap<String, Object> headers, ParserContext parserContext, String headerName, Element headerElement, String headerType, @Nullable String expressionArg, String overwrite) {
        String value = headerElement.getAttribute("value");
        String ref = headerElement.getAttribute("ref");
        String method = headerElement.getAttribute("method");
        String expression = expressionArg;
        if (expression == null) {
            expression = headerElement.getAttribute("expression");
        }
        Element beanElement = null;
        Element scriptElement = null;
        Element expressionElement = null;
        List subElements = DomUtils.getChildElements((Element)headerElement);
        if (!subElements.isEmpty()) {
            Element subElement = (Element)subElements.get(0);
            String subElementLocalName = subElement.getLocalName();
            if ("bean".equals(subElementLocalName)) {
                beanElement = subElement;
            } else if ("script".equals(subElementLocalName)) {
                scriptElement = subElement;
            } else if ("expression".equals(subElementLocalName)) {
                expressionElement = subElement;
            }
            if (beanElement == null && scriptElement == null && expressionElement == null) {
                parserContext.getReaderContext().error("Only 'bean', 'script' or 'expression' can be defined as a sub-element", (Object)element);
            }
        }
        if (StringUtils.hasText((String)expression) && expressionElement != null) {
            parserContext.getReaderContext().error("The 'expression' attribute and sub-element are mutually exclusive", (Object)element);
        }
        boolean isValue = StringUtils.hasText((String)value);
        boolean isRef = StringUtils.hasText((String)ref);
        boolean hasMethod = StringUtils.hasText((String)method);
        boolean isExpression = StringUtils.hasText((String)expression) || expressionElement != null;
        boolean isScript = scriptElement != null;
        BeanDefinition innerComponentDefinition = null;
        if (beanElement != null) {
            innerComponentDefinition = parserContext.getDelegate().parseBeanDefinitionElement(beanElement).getBeanDefinition();
        } else if (isScript) {
            innerComponentDefinition = parserContext.getDelegate().parseCustomElement(scriptElement);
        }
        BeanDefinitionBuilder valueProcessorBuilder = this.valueProcessor(element, parserContext, headerName, headerElement, headerType, overwrite, value, ref, method, expression, expressionElement, isValue, isRef, hasMethod, isExpression, isScript, innerComponentDefinition);
        headers.put((Object)headerName, (Object)valueProcessorBuilder.getBeanDefinition());
    }

    private BeanDefinitionBuilder valueProcessor(Element element, ParserContext parserContext, String headerName, Element headerElement, String headerType, String overwrite, String value, String ref, String method, String expression, Element expressionElement, boolean isValue, boolean isRef, boolean hasMethod, boolean isExpression, boolean isScript, BeanDefinition innerComponentDefinition) {
        boolean isCustomBean;
        boolean bl = isCustomBean = innerComponentDefinition != null;
        if (hasMethod && isScript) {
            parserContext.getReaderContext().error("The 'method' attribute cannot be used when a 'script' sub-element is defined", (Object)element);
        }
        if (isValue == (isRef ^ (isExpression ^ isCustomBean))) {
            parserContext.getReaderContext().error("Exactly one of the 'ref', 'value', 'expression' or inner bean is required.", (Object)element);
        }
        BeanDefinitionBuilder valueProcessorBuilder = null;
        valueProcessorBuilder = isValue ? this.value(element, parserContext, headerName, headerType, value, hasMethod) : (isExpression ? this.expression(element, parserContext, headerType, expression, expressionElement, hasMethod) : (isCustomBean ? this.innerComponentAndMethod(element, parserContext, headerElement, method, hasMethod, isScript, innerComponentDefinition) : this.refAndMethod(element, parserContext, headerElement, ref, method, hasMethod)));
        if (StringUtils.hasText((String)overwrite)) {
            valueProcessorBuilder.addPropertyValue("overwrite", (Object)overwrite);
        }
        return valueProcessorBuilder;
    }

    private BeanDefinitionBuilder value(Element element, ParserContext parserContext, String headerName, String headerType, String value, boolean hasMethod) {
        BeanDefinitionBuilder valueProcessorBuilder;
        if (hasMethod) {
            parserContext.getReaderContext().error("The 'method' attribute cannot be used with the 'value' attribute.", (Object)element);
        }
        if ("routingSlip".equals(headerName)) {
            ManagedList routingSlipPath = new ManagedList();
            routingSlipPath.addAll(Arrays.asList(StringUtils.tokenizeToStringArray((String)value, (String)";")));
            valueProcessorBuilder = BeanDefinitionBuilder.genericBeanDefinition(RoutingSlipHeaderValueMessageProcessor.class).addConstructorArgValue((Object)routingSlipPath);
        } else {
            String headerValue = value;
            if (StringUtils.hasText((String)headerType)) {
                TypedStringValue typedStringValue = new TypedStringValue(value);
                typedStringValue.setTargetTypeName(headerType);
                headerValue = typedStringValue;
            }
            valueProcessorBuilder = BeanDefinitionBuilder.genericBeanDefinition(StaticHeaderValueMessageProcessor.class).addConstructorArgValue((Object)headerValue);
        }
        return valueProcessorBuilder;
    }

    private BeanDefinitionBuilder expression(Element element, ParserContext parserContext, String headerType, String expression, Element expressionElement, boolean hasMethod) {
        if (hasMethod) {
            parserContext.getReaderContext().error("The 'method' attribute cannot be used with the 'expression' attribute.", (Object)element);
        }
        BeanDefinitionBuilder valueProcessorBuilder = BeanDefinitionBuilder.genericBeanDefinition(ExpressionEvaluatingHeaderValueMessageProcessor.class);
        if (expressionElement != null) {
            BeanDefinitionBuilder dynamicExpressionBuilder = BeanDefinitionBuilder.genericBeanDefinition(DynamicExpression.class);
            dynamicExpressionBuilder.addConstructorArgValue((Object)expressionElement.getAttribute("key"));
            dynamicExpressionBuilder.addConstructorArgReference(expressionElement.getAttribute("source"));
            valueProcessorBuilder.addConstructorArgValue((Object)dynamicExpressionBuilder.getBeanDefinition());
        } else {
            valueProcessorBuilder.addConstructorArgValue((Object)expression);
        }
        valueProcessorBuilder.addConstructorArgValue((Object)headerType);
        return valueProcessorBuilder;
    }

    private BeanDefinitionBuilder innerComponentAndMethod(Element element, ParserContext parserContext, Element headerElement, String method, boolean hasMethod, boolean isScript, BeanDefinition innerComponentDefinition) {
        BeanDefinitionBuilder valueProcessorBuilder;
        if (StringUtils.hasText((String)headerElement.getAttribute(TYPE_ATTRIBUTE))) {
            parserContext.getReaderContext().error("The 'type' attribute cannot be used with an inner bean.", (Object)element);
        }
        if (hasMethod || isScript) {
            valueProcessorBuilder = BeanDefinitionBuilder.genericBeanDefinition(MessageProcessingHeaderValueMessageProcessor.class).addConstructorArgValue((Object)innerComponentDefinition);
            if (hasMethod) {
                valueProcessorBuilder.addConstructorArgValue((Object)method);
            }
        } else {
            valueProcessorBuilder = BeanDefinitionBuilder.genericBeanDefinition(StaticHeaderValueMessageProcessor.class).addConstructorArgValue((Object)innerComponentDefinition);
        }
        return valueProcessorBuilder;
    }

    private BeanDefinitionBuilder refAndMethod(Element element, ParserContext parserContext, Element headerElement, String ref, String method, boolean hasMethod) {
        if (StringUtils.hasText((String)headerElement.getAttribute(TYPE_ATTRIBUTE))) {
            parserContext.getReaderContext().error("The 'type' attribute cannot be used with the 'ref' attribute.", (Object)element);
        }
        BeanDefinitionBuilder valueProcessorBuilder = hasMethod ? BeanDefinitionBuilder.genericBeanDefinition(MessageProcessingHeaderValueMessageProcessor.class).addConstructorArgReference(ref).addConstructorArgValue((Object)method) : BeanDefinitionBuilder.genericBeanDefinition(StaticHeaderValueMessageProcessor.class).addConstructorArgReference(ref);
        return valueProcessorBuilder;
    }

    protected void postProcessHeaderEnricher(BeanDefinitionBuilder builder, Element element, ParserContext parserContext) {
    }

    static {
        cannedHeaderElementExpressions.put("header-channels-to-string", new String[][]{{"replyChannel", "@integrationHeaderChannelRegistry.channelToChannelName(headers.replyChannel, ####)"}, {"errorChannel", "@integrationHeaderChannelRegistry.channelToChannelName(headers.errorChannel, ####)"}});
    }
}

