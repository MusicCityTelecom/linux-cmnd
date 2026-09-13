/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractRouterParser;
import org.springframework.integration.router.HeaderValueRouter;
import org.w3c.dom.Element;

public class HeaderValueRouterParser
extends AbstractRouterParser {
    @Override
    protected BeanDefinition doParseRouter(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder headerValueRouterBuilder = BeanDefinitionBuilder.genericBeanDefinition(HeaderValueRouter.class);
        headerValueRouterBuilder.addConstructorArgValue((Object)element.getAttribute("header-name"));
        return headerValueRouterBuilder.getBeanDefinition();
    }
}

