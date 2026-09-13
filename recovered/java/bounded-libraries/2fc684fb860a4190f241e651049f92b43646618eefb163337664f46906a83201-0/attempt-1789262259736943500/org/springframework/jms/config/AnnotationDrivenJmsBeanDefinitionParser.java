/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.BeanDefinitionHolder
 *  org.springframework.beans.factory.parsing.BeanComponentDefinition
 *  org.springframework.beans.factory.parsing.ComponentDefinition
 *  org.springframework.beans.factory.parsing.CompositeComponentDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.xml.BeanDefinitionParser
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.lang.Nullable
 *  org.springframework.util.StringUtils
 */
package org.springframework.jms.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.parsing.ComponentDefinition;
import org.springframework.beans.factory.parsing.CompositeComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

class AnnotationDrivenJmsBeanDefinitionParser
implements BeanDefinitionParser {
    AnnotationDrivenJmsBeanDefinitionParser() {
    }

    @Nullable
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        Object source = parserContext.extractSource((Object)element);
        CompositeComponentDefinition compDefinition = new CompositeComponentDefinition(element.getTagName(), source);
        parserContext.pushContainingComponent(compDefinition);
        BeanDefinitionRegistry registry = parserContext.getRegistry();
        if (registry.containsBeanDefinition("org.springframework.jms.config.internalJmsListenerAnnotationProcessor")) {
            parserContext.getReaderContext().error("Only one JmsListenerAnnotationBeanPostProcessor may exist within the context.", source);
        } else {
            String handlerMethodFactory;
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition((String)"org.springframework.jms.annotation.JmsListenerAnnotationBeanPostProcessor");
            builder.getRawBeanDefinition().setSource(source);
            String endpointRegistry = element.getAttribute("registry");
            if (StringUtils.hasText((String)endpointRegistry)) {
                builder.addPropertyReference("endpointRegistry", endpointRegistry);
            } else {
                AnnotationDrivenJmsBeanDefinitionParser.registerDefaultEndpointRegistry(source, parserContext);
            }
            String containerFactory = element.getAttribute("container-factory");
            if (StringUtils.hasText((String)containerFactory)) {
                builder.addPropertyValue("containerFactoryBeanName", (Object)containerFactory);
            }
            if (StringUtils.hasText((String)(handlerMethodFactory = element.getAttribute("handler-method-factory")))) {
                builder.addPropertyReference("messageHandlerMethodFactory", handlerMethodFactory);
            }
            AnnotationDrivenJmsBeanDefinitionParser.registerInfrastructureBean(parserContext, builder, "org.springframework.jms.config.internalJmsListenerAnnotationProcessor");
        }
        parserContext.popAndRegisterContainingComponent();
        return null;
    }

    private static void registerDefaultEndpointRegistry(@Nullable Object source, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition((String)"org.springframework.jms.config.JmsListenerEndpointRegistry");
        builder.getRawBeanDefinition().setSource(source);
        AnnotationDrivenJmsBeanDefinitionParser.registerInfrastructureBean(parserContext, builder, "org.springframework.jms.config.internalJmsListenerEndpointRegistry");
    }

    private static void registerInfrastructureBean(ParserContext parserContext, BeanDefinitionBuilder builder, String beanName) {
        builder.setRole(2);
        parserContext.getRegistry().registerBeanDefinition(beanName, (BeanDefinition)builder.getBeanDefinition());
        BeanDefinitionHolder holder = new BeanDefinitionHolder((BeanDefinition)builder.getBeanDefinition(), beanName);
        parserContext.registerComponent((ComponentDefinition)new BeanComponentDefinition(holder));
    }
}

