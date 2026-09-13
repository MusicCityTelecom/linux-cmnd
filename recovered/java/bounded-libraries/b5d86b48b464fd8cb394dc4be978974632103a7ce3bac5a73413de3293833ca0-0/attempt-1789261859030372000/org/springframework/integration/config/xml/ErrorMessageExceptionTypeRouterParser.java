/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.beans.factory.xml.ParserContext
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractRouterParser;
import org.springframework.integration.router.ErrorMessageExceptionTypeRouter;
import org.w3c.dom.Element;

public class ErrorMessageExceptionTypeRouterParser
extends AbstractRouterParser {
    @Override
    protected String getMappingKeyAttributeName() {
        return "exception-type";
    }

    @Override
    protected BeanDefinition doParseRouter(Element element, ParserContext parserContext) {
        return new RootBeanDefinition(ErrorMessageExceptionTypeRouter.class);
    }
}

