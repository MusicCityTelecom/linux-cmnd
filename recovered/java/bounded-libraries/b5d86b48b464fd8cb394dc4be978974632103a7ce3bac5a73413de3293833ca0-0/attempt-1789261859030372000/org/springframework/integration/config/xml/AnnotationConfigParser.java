/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.xml.BeanDefinitionParser
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.EnablePublisher;
import org.springframework.integration.config.IntegrationRegistrar;
import org.springframework.integration.config.PublisherRegistrar;
import org.springframework.integration.config.annotation.AnnotationMetadataAdapter;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class AnnotationConfigParser
implements BeanDefinitionParser {
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        ExtendedAnnotationMetadata importingClassMetadata = new ExtendedAnnotationMetadata(element);
        BeanDefinitionRegistry registry = parserContext.getRegistry();
        new IntegrationRegistrar().registerBeanDefinitions(importingClassMetadata, registry);
        if (DomUtils.getChildElementByTagName((Element)element, (String)"enable-publisher") != null) {
            new PublisherRegistrar().registerBeanDefinitions(importingClassMetadata, registry);
        }
        return null;
    }

    private static final class ExtendedAnnotationMetadata
    extends AnnotationMetadataAdapter {
        private final Element element;

        ExtendedAnnotationMetadata(Element element) {
            this.element = element;
        }

        public Map<String, Object> getAnnotationAttributes(String annotationType) {
            if (EnablePublisher.class.getName().equals(annotationType)) {
                Element enablePublisherElement = DomUtils.getChildElementByTagName((Element)this.element, (String)"enable-publisher");
                if (enablePublisherElement != null) {
                    HashMap<String, Object> attributes = new HashMap<String, Object>();
                    attributes.put("defaultChannel", enablePublisherElement.getAttribute("default-publisher-channel"));
                    attributes.put("proxyTargetClass", enablePublisherElement.getAttribute("proxy-target-class"));
                    attributes.put("order", enablePublisherElement.getAttribute("order"));
                    return attributes;
                }
                return null;
            }
            return null;
        }
    }
}

