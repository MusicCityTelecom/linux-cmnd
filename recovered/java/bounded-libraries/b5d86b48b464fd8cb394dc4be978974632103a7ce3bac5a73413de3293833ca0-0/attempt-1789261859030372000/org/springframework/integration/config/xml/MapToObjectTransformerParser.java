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
import org.springframework.integration.transformer.MapToObjectTransformer;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class MapToObjectTransformerParser
extends AbstractTransformerParser {
    @Override
    protected String getTransformerClassName() {
        return MapToObjectTransformer.class.getName();
    }

    @Override
    protected void parseTransformer(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        String ref = element.getAttribute("ref");
        String type = element.getAttribute("type");
        if (StringUtils.hasText((String)ref) && StringUtils.hasText((String)type)) {
            parserContext.getReaderContext().error("'type' and 'ref' attributes are mutually-exclusive, but both have valid values; type: " + type + "; ref: " + ref, (Object)IntegrationNamespaceUtils.createElementDescription(element));
        }
        if (StringUtils.hasText((String)ref)) {
            builder.getBeanDefinition().getConstructorArgumentValues().addGenericArgumentValue((Object)ref, "java.lang.String");
        } else if (StringUtils.hasText((String)type)) {
            builder.getBeanDefinition().getConstructorArgumentValues().addGenericArgumentValue((Object)type, "java.lang.Class");
        }
    }
}

