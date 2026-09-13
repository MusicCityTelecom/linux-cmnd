/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanMetadataElement
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.ConstructorArgumentValues$ValueHolder
 *  org.springframework.beans.factory.config.RuntimeBeanReference
 *  org.springframework.beans.factory.parsing.BeanComponentDefinition
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.ManagedSet
 *  org.springframework.beans.factory.xml.AbstractBeanDefinitionParser
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config.xml;

import java.util.Set;
import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedSet;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class ConverterParser
extends AbstractBeanDefinitionParser {
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        BeanDefinitionRegistry registry = parserContext.getRegistry();
        BeanComponentDefinition converterDefinition = IntegrationNamespaceUtils.parseInnerHandlerDefinition(element, parserContext);
        if (converterDefinition != null) {
            ConverterParser.registerConverter(registry, (BeanMetadataElement)converterDefinition);
        } else {
            String beanName = element.getAttribute("ref");
            Assert.isTrue((boolean)StringUtils.hasText((String)beanName), (String)"Either a 'ref' attribute pointing to a Converter or a <bean> sub-element defining a Converter is required.");
            ConverterParser.registerConverter(registry, (BeanMetadataElement)new RuntimeBeanReference(beanName));
        }
        return null;
    }

    private static void registerConverter(BeanDefinitionRegistry registry, BeanMetadataElement converterBeanDefinition) {
        Object converters = new ManagedSet();
        if (!registry.containsBeanDefinition("converterRegistrar")) {
            BeanDefinitionBuilder converterRegistrarBuilder = BeanDefinitionBuilder.genericBeanDefinition((String)"org.springframework.integration.config.ConverterRegistrar").addConstructorArgValue(converters);
            registry.registerBeanDefinition("converterRegistrar", (BeanDefinition)converterRegistrarBuilder.getBeanDefinition());
        } else {
            BeanDefinition converterRegistrarBeanDefinition = registry.getBeanDefinition("converterRegistrar");
            converters = (Set)((ConstructorArgumentValues.ValueHolder)converterRegistrarBeanDefinition.getConstructorArgumentValues().getIndexedArgumentValues().values().iterator().next()).getValue();
        }
        converters.add(converterBeanDefinition);
    }
}

