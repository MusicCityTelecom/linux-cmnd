/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractTransformerParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.transformer.HeaderFilter;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class HeaderFilterParser
extends AbstractTransformerParser {
    @Override
    protected final String getTransformerClassName() {
        return HeaderFilter.class.getName();
    }

    @Override
    protected void parseTransformer(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        String headerNames = element.getAttribute("header-names");
        if (!StringUtils.hasText((String)headerNames)) {
            parserContext.getReaderContext().error("The 'header-names' attribute must not be empty.", parserContext.extractSource((Object)element));
        }
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "pattern-match");
        builder.addConstructorArgValue((Object)headerNames);
    }
}

