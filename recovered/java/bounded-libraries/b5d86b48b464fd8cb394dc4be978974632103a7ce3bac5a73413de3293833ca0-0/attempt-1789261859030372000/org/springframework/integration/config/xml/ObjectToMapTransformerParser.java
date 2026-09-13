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
import org.springframework.integration.transformer.ObjectToMapTransformer;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class ObjectToMapTransformerParser
extends AbstractTransformerParser {
    @Override
    protected String getTransformerClassName() {
        return ObjectToMapTransformer.class.getName();
    }

    @Override
    protected void parseTransformer(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        String objectMapper = element.getAttribute("object-mapper");
        if (StringUtils.hasText((String)objectMapper)) {
            builder.addConstructorArgReference(objectMapper);
        }
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "flatten", "shouldFlattenKeys");
    }
}

