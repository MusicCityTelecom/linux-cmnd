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
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class ObjectToJsonTransformerParser
extends AbstractTransformerParser {
    @Override
    protected String getTransformerClassName() {
        return ObjectToJsonTransformer.class.getName();
    }

    @Override
    protected void parseTransformer(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        String resultType;
        String objectMapper = element.getAttribute("object-mapper");
        if (StringUtils.hasText((String)objectMapper)) {
            builder.addConstructorArgReference(objectMapper);
        }
        if (StringUtils.hasText((String)(resultType = element.getAttribute("result-type")))) {
            builder.addConstructorArgValue((Object)resultType);
        }
        if (element.hasAttribute("content-type")) {
            builder.addPropertyValue("contentType", (Object)element.getAttribute("content-type"));
        }
    }
}

