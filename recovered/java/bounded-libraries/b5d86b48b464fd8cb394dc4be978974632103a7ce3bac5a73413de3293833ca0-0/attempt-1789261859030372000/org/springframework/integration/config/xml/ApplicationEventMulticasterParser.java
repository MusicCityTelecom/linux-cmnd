/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanDefinitionStoreException
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.context.event.SimpleApplicationEventMulticaster
 *  org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class ApplicationEventMulticasterParser
extends AbstractSingleBeanDefinitionParser {
    protected Class<?> getBeanClass(Element element) {
        return SimpleApplicationEventMulticaster.class;
    }

    protected String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext) throws BeanDefinitionStoreException {
        return "applicationEventMulticaster";
    }

    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        String taskExecutorRef = element.getAttribute("task-executor");
        if (StringUtils.hasText((String)taskExecutorRef)) {
            builder.addPropertyReference("taskExecutor", taskExecutorRef);
        } else {
            BeanDefinitionBuilder executorBuilder = BeanDefinitionBuilder.genericBeanDefinition(ThreadPoolTaskExecutor.class);
            executorBuilder.addPropertyValue("corePoolSize", (Object)1);
            executorBuilder.addPropertyValue("maxPoolSize", (Object)10);
            executorBuilder.addPropertyValue("queueCapacity", (Object)0);
            executorBuilder.addPropertyValue("threadNamePrefix", (Object)"event-multicaster-");
            builder.addPropertyValue("taskExecutor", (Object)executorBuilder.getBeanDefinition());
        }
    }
}

