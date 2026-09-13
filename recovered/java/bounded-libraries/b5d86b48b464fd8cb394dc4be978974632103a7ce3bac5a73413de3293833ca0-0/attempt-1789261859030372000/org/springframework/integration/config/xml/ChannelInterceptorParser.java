/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinitionHolder
 *  org.springframework.beans.factory.config.RuntimeBeanReference
 *  org.springframework.beans.factory.parsing.BeanComponentDefinition
 *  org.springframework.beans.factory.support.ManagedList
 *  org.springframework.beans.factory.xml.BeanDefinitionParserDelegate
 *  org.springframework.beans.factory.xml.ParserContext
 */
package org.springframework.integration.config.xml;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.BeanDefinitionRegisteringParser;
import org.springframework.integration.config.xml.WireTapParser;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ChannelInterceptorParser {
    private final Map<String, BeanDefinitionRegisteringParser> parsers = new HashMap<String, BeanDefinitionRegisteringParser>();

    public ChannelInterceptorParser() {
        this.parsers.put("wire-tap", new WireTapParser());
    }

    public ManagedList parseInterceptors(Element element, ParserContext parserContext) {
        ManagedList interceptors = new ManagedList();
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); ++i) {
            Node child = childNodes.item(i);
            if (child.getNodeType() != 1) continue;
            Element childElement = (Element)child;
            String localName = child.getLocalName();
            if ("bean".equals(localName)) {
                BeanDefinitionParserDelegate delegate = parserContext.getDelegate();
                BeanDefinitionHolder holder = delegate.parseBeanDefinitionElement(childElement);
                holder = delegate.decorateBeanDefinitionIfRequired(childElement, holder);
                parserContext.registerBeanComponent(new BeanComponentDefinition(holder));
                interceptors.add((Object)new RuntimeBeanReference(holder.getBeanName()));
                continue;
            }
            if ("ref".equals(localName)) {
                String ref = childElement.getAttribute("bean");
                interceptors.add((Object)new RuntimeBeanReference(ref));
                continue;
            }
            BeanDefinitionRegisteringParser parser = this.parsers.get(localName);
            if (parser == null) {
                parserContext.getReaderContext().error("unsupported interceptor element '" + localName + "'", (Object)childElement);
            }
            String interceptorBeanName = parser.parse(childElement, parserContext);
            interceptors.add((Object)new RuntimeBeanReference(interceptorBeanName));
        }
        return interceptors;
    }
}

