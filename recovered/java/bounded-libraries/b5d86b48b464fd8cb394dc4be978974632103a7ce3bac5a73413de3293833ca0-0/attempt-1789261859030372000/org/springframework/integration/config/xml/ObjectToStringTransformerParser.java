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
import org.springframework.integration.transformer.ObjectToStringTransformer;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class ObjectToStringTransformerParser
extends AbstractTransformerParser {
    @Override
    protected String getTransformerClassName() {
        return ObjectToStringTransformer.class.getName();
    }

    @Override
    protected void parseTransformer(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        String charset = element.getAttribute("charset");
        if (StringUtils.hasText((String)charset)) {
            builder.addConstructorArgValue((Object)charset);
        }
    }
}

