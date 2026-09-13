/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.xml.XmlBeanDefinitionReader
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.ClassPathResource
 *  org.springframework.core.io.Resource
 */
package org.springframework.boot.web.servlet.context;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class XmlServletWebServerApplicationContext
extends ServletWebServerApplicationContext {
    private final XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader((BeanDefinitionRegistry)this);

    public XmlServletWebServerApplicationContext() {
        this.reader.setEnvironment((Environment)this.getEnvironment());
    }

    public XmlServletWebServerApplicationContext(Resource ... resources) {
        this.load(resources);
        this.refresh();
    }

    public XmlServletWebServerApplicationContext(String ... resourceLocations) {
        this.load(resourceLocations);
        this.refresh();
    }

    public XmlServletWebServerApplicationContext(Class<?> relativeClass, String ... resourceNames) {
        this.load(relativeClass, resourceNames);
        this.refresh();
    }

    public void setValidating(boolean validating) {
        this.reader.setValidating(validating);
    }

    public void setEnvironment(ConfigurableEnvironment environment) {
        super.setEnvironment(environment);
        this.reader.setEnvironment((Environment)this.getEnvironment());
    }

    public final void load(Resource ... resources) {
        this.reader.loadBeanDefinitions(resources);
    }

    public final void load(String ... resourceLocations) {
        this.reader.loadBeanDefinitions(resourceLocations);
    }

    public final void load(Class<?> relativeClass, String ... resourceNames) {
        Resource[] resources = new Resource[resourceNames.length];
        for (int i = 0; i < resourceNames.length; ++i) {
            resources[i] = new ClassPathResource(resourceNames[i], relativeClass);
        }
        this.reader.loadBeanDefinitions(resources);
    }
}

