/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractTransformerParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.transformer.PayloadDeserializingTransformer;
import org.w3c.dom.Element;

public class PayloadDeserializingTransformerParser
extends AbstractTransformerParser {
    @Override
    protected String getTransformerClassName() {
        return PayloadDeserializingTransformer.class.getName();
    }

    @Override
    protected void parseTransformer(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, "deserializer");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "allow-list", "allowedPatterns");
    }
}

