/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanDefinitionStoreException
 *  org.springframework.beans.factory.config.ConstructorArgumentValues$ValueHolder
 *  org.springframework.beans.factory.config.RuntimeBeanReference
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.xml.ParserContext
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.GlobalChannelInterceptorParser;
import org.springframework.integration.config.xml.WireTapParser;
import org.w3c.dom.Element;

public class GlobalWireTapParser
extends GlobalChannelInterceptorParser {
    @Override
    protected Object getBeanDefinitionBuilderConstructorValue(Element element, ParserContext parserContext) {
        String wireTapBeanName = new WireTapParser().parse(element, parserContext);
        return new RuntimeBeanReference(wireTapBeanName);
    }

    protected String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext) throws BeanDefinitionStoreException {
        RuntimeBeanReference wireTapBean = (RuntimeBeanReference)((ConstructorArgumentValues.ValueHolder)definition.getConstructorArgumentValues().getIndexedArgumentValues().values().iterator().next()).getValue();
        return wireTapBean.getBeanName() + ".globalChannelInterceptor";
    }
}

