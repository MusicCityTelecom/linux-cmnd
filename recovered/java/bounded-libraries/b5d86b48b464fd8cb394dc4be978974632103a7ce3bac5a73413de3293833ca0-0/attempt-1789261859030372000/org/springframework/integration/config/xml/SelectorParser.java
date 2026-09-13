/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.RuntimeBeanReference
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.filter.MethodInvokingSelector;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class SelectorParser
extends AbstractSingleBeanDefinitionParser {
    protected String getBeanClassName(Element element) {
        return MethodInvokingSelector.class.getName();
    }

    public void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        String method;
        String ref;
        String id = element.getAttribute("id");
        if (!StringUtils.hasText((String)id)) {
            parserContext.getReaderContext().error("The 'id' attribute is required for a selector.", (Object)element);
        }
        if (!StringUtils.hasText((String)(ref = element.getAttribute("ref")))) {
            parserContext.getReaderContext().error("The 'ref' attribute is required for selector '" + id + "'.", (Object)element);
        }
        if (!StringUtils.hasText((String)(method = element.getAttribute("method")))) {
            parserContext.getReaderContext().error("The 'method' attribute is required for selector '" + id + "'.", (Object)element);
        }
        builder.addConstructorArgValue((Object)new RuntimeBeanReference(ref));
        builder.addConstructorArgValue((Object)method);
    }
}

