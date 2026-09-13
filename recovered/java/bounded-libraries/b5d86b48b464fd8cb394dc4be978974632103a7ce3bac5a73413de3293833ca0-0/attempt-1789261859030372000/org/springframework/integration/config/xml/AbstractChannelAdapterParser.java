/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.MutablePropertyValues
 *  org.springframework.beans.factory.BeanDefinitionStoreException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.TypedStringValue
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionReaderUtils
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.xml.AbstractBeanDefinitionParser
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public abstract class AbstractChannelAdapterParser
extends AbstractBeanDefinitionParser {
    protected final String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext) throws BeanDefinitionStoreException {
        String id = element.getAttribute("id");
        if (!element.hasAttribute("channel")) {
            id = id + ".adapter";
        } else if (!StringUtils.hasText((String)id)) {
            id = BeanDefinitionReaderUtils.generateBeanName((BeanDefinition)definition, (BeanDefinitionRegistry)parserContext.getRegistry(), (boolean)parserContext.isNested());
        }
        return id;
    }

    protected final AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        String channelName = element.getAttribute("channel");
        if (!StringUtils.hasText((String)channelName)) {
            channelName = this.createDirectChannel(element, parserContext);
        }
        AbstractBeanDefinition beanDefinition = this.doParse(element, parserContext, channelName);
        MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
        if (!parserContext.isNested()) {
            String role;
            String phase;
            String autoStartup = element.getAttribute("auto-startup");
            if (StringUtils.hasText((String)autoStartup)) {
                propertyValues.add("autoStartup", (Object)new TypedStringValue(autoStartup));
            }
            if (StringUtils.hasText((String)(phase = element.getAttribute("phase")))) {
                propertyValues.add("phase", (Object)new TypedStringValue(phase));
            }
            if (StringUtils.hasText((String)(role = element.getAttribute("role")))) {
                propertyValues.add("role", (Object)new TypedStringValue(role));
            }
        }
        beanDefinition.setResource(parserContext.getReaderContext().getResource());
        beanDefinition.setSource((Object)IntegrationNamespaceUtils.createElementDescription(element));
        return beanDefinition;
    }

    private String createDirectChannel(Element element, ParserContext parserContext) {
        if (parserContext.isNested()) {
            return null;
        }
        return IntegrationNamespaceUtils.createDirectChannel(element, parserContext);
    }

    protected abstract AbstractBeanDefinition doParse(Element var1, ParserContext var2, String var3);
}

