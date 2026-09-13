/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionReaderUtils
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.xml.ParserContext
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractConsumerEndpointParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.transformer.MessageTransformingHandler;
import org.w3c.dom.Element;

public abstract class AbstractTransformerParser
extends AbstractConsumerEndpointParser {
    @Override
    protected BeanDefinitionBuilder parseHandler(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MessageTransformingHandler.class);
        BeanDefinitionBuilder transformerBuilder = BeanDefinitionBuilder.genericBeanDefinition((String)this.getTransformerClassName());
        this.parseTransformer(element, parserContext, transformerBuilder);
        String transformerBeanName = BeanDefinitionReaderUtils.registerWithGeneratedName((AbstractBeanDefinition)transformerBuilder.getBeanDefinition(), (BeanDefinitionRegistry)parserContext.getRegistry());
        builder.addConstructorArgReference(transformerBeanName);
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "send-timeout");
        return builder;
    }

    protected abstract String getTransformerClassName();

    protected abstract void parseTransformer(Element var1, ParserContext var2, BeanDefinitionBuilder var3);
}

