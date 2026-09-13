/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.integration.config.SpelFunctionFactoryBean;
import org.w3c.dom.Element;

public class SpelFunctionParser
extends AbstractSingleBeanDefinitionParser {
    protected Class<?> getBeanClass(Element element) {
        return SpelFunctionFactoryBean.class;
    }

    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        builder.addConstructorArgValue((Object)element.getAttribute("class")).addConstructorArgValue((Object)element.getAttribute("method"));
    }
}

