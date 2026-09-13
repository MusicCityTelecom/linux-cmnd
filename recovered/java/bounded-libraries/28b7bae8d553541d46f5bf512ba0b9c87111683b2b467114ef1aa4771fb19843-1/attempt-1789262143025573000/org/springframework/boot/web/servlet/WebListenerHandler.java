/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.annotation.WebListener
 *  org.springframework.beans.factory.annotation.AnnotatedBeanDefinition
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 */
package org.springframework.boot.web.servlet;

import java.util.Map;
import javax.servlet.annotation.WebListener;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.web.servlet.ServletComponentHandler;
import org.springframework.boot.web.servlet.WebListenerRegistrar;
import org.springframework.boot.web.servlet.WebListenerRegistry;

class WebListenerHandler
extends ServletComponentHandler {
    WebListenerHandler() {
        super(WebListener.class);
    }

    @Override
    protected void doHandle(Map<String, Object> attributes, AnnotatedBeanDefinition beanDefinition, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(ServletComponentWebListenerRegistrar.class);
        builder.addConstructorArgValue((Object)beanDefinition.getBeanClassName());
        registry.registerBeanDefinition(beanDefinition.getBeanClassName() + "Registrar", (BeanDefinition)builder.getBeanDefinition());
    }

    static class ServletComponentWebListenerRegistrar
    implements WebListenerRegistrar {
        private final String listenerClassName;

        ServletComponentWebListenerRegistrar(String listenerClassName) {
            this.listenerClassName = listenerClassName;
        }

        @Override
        public void register(WebListenerRegistry registry) {
            registry.addWebListeners(this.listenerClassName);
        }
    }
}

