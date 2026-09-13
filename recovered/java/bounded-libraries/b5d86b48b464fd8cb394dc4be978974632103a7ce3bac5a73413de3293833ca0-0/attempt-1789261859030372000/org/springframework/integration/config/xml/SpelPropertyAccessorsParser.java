/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.BeanDefinitionHolder
 *  org.springframework.beans.factory.config.BeanReference
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.ManagedMap
 *  org.springframework.beans.factory.xml.BeanDefinitionParser
 *  org.springframework.beans.factory.xml.BeanDefinitionParserDelegate
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config.xml;

import java.util.Map;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.expression.SpelPropertyAccessorRegistrar;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SpelPropertyAccessorsParser
implements BeanDefinitionParser {
    private final Map<String, Object> propertyAccessors = new ManagedMap();

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        this.initializeSpelPropertyAccessorRegistrarIfNecessary(parserContext);
        BeanDefinitionParserDelegate delegate = parserContext.getDelegate();
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); ++i) {
            BeanDefinitionHolder propertyAccessor;
            String propertyAccessorName;
            Node node = children.item(i);
            if (!(node instanceof Element) || delegate.nodeNameEquals(node, "description")) continue;
            Element ele = (Element)node;
            if (delegate.nodeNameEquals((Node)ele, "bean")) {
                propertyAccessorName = ele.getAttribute("id");
                if (!StringUtils.hasText((String)propertyAccessorName)) {
                    parserContext.getReaderContext().error("The '<bean>' 'id' attribute is required within 'spel-property-accessors'.", (Object)ele);
                    return null;
                }
                propertyAccessor = delegate.parseBeanDefinitionElement(ele);
            } else if (delegate.nodeNameEquals((Node)ele, "ref")) {
                BeanReference propertyAccessorRef = (BeanReference)delegate.parsePropertySubElement(ele, null);
                propertyAccessorName = propertyAccessorRef.getBeanName();
                propertyAccessor = propertyAccessorRef;
            } else {
                parserContext.getReaderContext().error("Only '<bean>' and '<ref>' elements are allowed.", (Object)element);
                return null;
            }
            this.propertyAccessors.put(propertyAccessorName, propertyAccessor);
        }
        return null;
    }

    private synchronized void initializeSpelPropertyAccessorRegistrarIfNecessary(ParserContext parserContext) {
        if (!parserContext.getRegistry().containsBeanDefinition("spelPropertyAccessorRegistrar")) {
            BeanDefinitionBuilder registrarBuilder = BeanDefinitionBuilder.genericBeanDefinition(SpelPropertyAccessorRegistrar.class).setRole(2).addConstructorArgValue(this.propertyAccessors);
            parserContext.getRegistry().registerBeanDefinition("spelPropertyAccessorRegistrar", (BeanDefinition)registrarBuilder.getBeanDefinition());
        }
    }
}

