/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.xml.BeanDefinitionParser
 *  org.springframework.beans.factory.xml.ParserContext
 */
package org.springframework.integration.config.xml;

import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.MessageHistoryRegistrar;
import org.springframework.integration.config.annotation.AnnotationMetadataAdapter;
import org.w3c.dom.Element;

public class MessageHistoryParser
implements BeanDefinitionParser {
    private final MessageHistoryRegistrar messageHistoryRegistrar = new MessageHistoryRegistrar();

    public BeanDefinition parse(final Element element, ParserContext parserContext) {
        this.messageHistoryRegistrar.registerBeanDefinitions(new AnnotationMetadataAdapter(){

            public Map<String, Object> getAnnotationAttributes(String annotationType) {
                return Collections.singletonMap("value", element.getAttribute("tracked-components"));
            }
        }, parserContext.getRegistry());
        return null;
    }
}

