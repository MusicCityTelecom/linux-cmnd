/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.BeanDefinitionHolder
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionReaderUtils
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.channel.interceptor.WireTap;
import org.springframework.integration.config.ExpressionFactoryBean;
import org.springframework.integration.config.xml.BeanDefinitionRegisteringParser;
import org.springframework.integration.filter.ExpressionEvaluatingSelector;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class WireTapParser
implements BeanDefinitionRegisteringParser {
    @Override
    public String parse(Element element, ParserContext parserContext) {
        String id;
        Object source = parserContext.extractSource((Object)element);
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(WireTap.class);
        String targetRef = element.getAttribute("channel");
        if (!StringUtils.hasText((String)targetRef)) {
            parserContext.getReaderContext().error("The 'channel' attribute is required.", (Object)element);
        }
        builder.addConstructorArgReference(targetRef);
        String selectorRef = element.getAttribute("selector");
        String selectorExpression = element.getAttribute("selector-expression");
        if (StringUtils.hasText((String)selectorRef) && StringUtils.hasText((String)selectorExpression)) {
            parserContext.getReaderContext().error("Only one of 'selector' or 'selector-expression' is allowed", source);
        }
        if (StringUtils.hasText((String)selectorRef)) {
            builder.addConstructorArgReference(selectorRef);
        } else if (StringUtils.hasText((String)selectorExpression)) {
            BeanDefinitionBuilder expressionBuilder = BeanDefinitionBuilder.genericBeanDefinition(ExpressionFactoryBean.class);
            expressionBuilder.addConstructorArgValue((Object)selectorExpression);
            BeanDefinitionBuilder eemsBuilder = BeanDefinitionBuilder.genericBeanDefinition(ExpressionEvaluatingSelector.class);
            eemsBuilder.addConstructorArgValue((Object)expressionBuilder.getBeanDefinition());
            builder.addConstructorArgValue((Object)eemsBuilder.getBeanDefinition());
        }
        String timeout = element.getAttribute("timeout");
        if (StringUtils.hasText((String)timeout)) {
            builder.addPropertyValue("timeout", (Object)Long.parseLong(timeout));
        }
        if (StringUtils.hasText((String)(id = element.getAttribute("id")))) {
            BeanDefinitionReaderUtils.registerBeanDefinition((BeanDefinitionHolder)new BeanDefinitionHolder((BeanDefinition)builder.getBeanDefinition(), id), (BeanDefinitionRegistry)parserContext.getRegistry());
            return id;
        }
        return BeanDefinitionReaderUtils.registerWithGeneratedName((AbstractBeanDefinition)builder.getBeanDefinition(), (BeanDefinitionRegistry)parserContext.getRegistry());
    }
}

