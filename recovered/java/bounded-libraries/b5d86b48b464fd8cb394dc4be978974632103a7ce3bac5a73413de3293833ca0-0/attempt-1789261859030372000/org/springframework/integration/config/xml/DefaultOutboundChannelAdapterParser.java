/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.parsing.BeanComponentDefinition
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.StringUtils
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.ExpressionFactoryBean;
import org.springframework.integration.config.xml.AbstractOutboundChannelAdapterParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.handler.ExpressionEvaluatingMessageHandler;
import org.springframework.integration.handler.MethodInvokingMessageHandler;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class DefaultOutboundChannelAdapterParser
extends AbstractOutboundChannelAdapterParser {
    @Override
    protected AbstractBeanDefinition parseConsumer(Element element, ParserContext parserContext) {
        boolean hasScript;
        Object source = parserContext.extractSource((Object)element);
        BeanComponentDefinition innerConsumerDefinition = IntegrationNamespaceUtils.parseInnerHandlerDefinition(element, parserContext);
        String consumerRef = element.getAttribute("ref");
        String methodName = element.getAttribute("method");
        String consumerExpressionString = element.getAttribute("expression");
        Element scriptElement = DomUtils.getChildElementByTagName((Element)element, (String)"script");
        boolean isInnerConsumer = innerConsumerDefinition != null;
        boolean isRef = StringUtils.hasText((String)consumerRef);
        boolean isExpression = StringUtils.hasText((String)consumerExpressionString);
        boolean hasMethod = StringUtils.hasText((String)methodName);
        if (!isInnerConsumer & !isRef & !isExpression & !(hasScript = scriptElement != null)) {
            parserContext.getReaderContext().error("Exactly one of the 'ref', 'expression', <script> or inner bean is required.", source);
        }
        if (hasScript && isRef | isExpression) {
            parserContext.getReaderContext().error("Neither 'ref' nor 'expression' are permitted when an inner script element is configured.", source);
        }
        if (hasMethod & isExpression) {
            parserContext.getReaderContext().error("The 'method' attribute cannot be used with the 'expression' attribute.", (Object)element);
        }
        BeanDefinitionBuilder consumerBuilder = null;
        if (isExpression) {
            consumerBuilder = BeanDefinitionBuilder.genericBeanDefinition(ExpressionEvaluatingMessageHandler.class);
            RootBeanDefinition expressionDef = new RootBeanDefinition(ExpressionFactoryBean.class);
            expressionDef.getConstructorArgumentValues().addGenericArgumentValue((Object)consumerExpressionString);
            consumerBuilder.addConstructorArgValue((Object)expressionDef);
        } else if (hasScript) {
            consumerBuilder = BeanDefinitionBuilder.genericBeanDefinition(MethodInvokingMessageHandler.class);
            BeanDefinition scriptBeanDefinition = parserContext.getDelegate().parseCustomElement(scriptElement, (BeanDefinition)consumerBuilder.getBeanDefinition());
            consumerBuilder.addConstructorArgValue((Object)scriptBeanDefinition);
            consumerBuilder.addConstructorArgValue((Object)"processMessage");
        } else {
            consumerBuilder = BeanDefinitionBuilder.genericBeanDefinition(MethodInvokingMessageHandler.class);
            if (isRef) {
                consumerBuilder.addConstructorArgReference(consumerRef);
            } else {
                consumerBuilder.addConstructorArgValue((Object)innerConsumerDefinition);
            }
            consumerBuilder.addConstructorArgValue((Object)(hasMethod ? methodName : "handleMessage"));
        }
        consumerBuilder.addPropertyValue("componentType", (Object)"outbound-channel-adapter");
        return consumerBuilder.getBeanDefinition();
    }
}

