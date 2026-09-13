/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.annotation.AnnotatedBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.core.annotation.AnnotationAttributes
 *  org.springframework.core.type.filter.AnnotationTypeFilter
 *  org.springframework.core.type.filter.TypeFilter
 *  org.springframework.util.Assert
 */
package org.springframework.boot.web.servlet;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.Assert;

abstract class ServletComponentHandler {
    private final Class<? extends Annotation> annotationType;
    private final TypeFilter typeFilter;

    protected ServletComponentHandler(Class<? extends Annotation> annotationType) {
        this.typeFilter = new AnnotationTypeFilter(annotationType);
        this.annotationType = annotationType;
    }

    TypeFilter getTypeFilter() {
        return this.typeFilter;
    }

    protected String[] extractUrlPatterns(Map<String, Object> attributes) {
        String[] value = (String[])attributes.get("value");
        String[] urlPatterns = (String[])attributes.get("urlPatterns");
        if (urlPatterns.length > 0) {
            Assert.state((value.length == 0 ? 1 : 0) != 0, (String)"The urlPatterns and value attributes are mutually exclusive.");
            return urlPatterns;
        }
        return value;
    }

    protected final Map<String, String> extractInitParameters(Map<String, Object> attributes) {
        HashMap<String, String> initParameters = new HashMap<String, String>();
        for (AnnotationAttributes initParam : (AnnotationAttributes[])attributes.get("initParams")) {
            String name = (String)initParam.get((Object)"name");
            String value = (String)initParam.get((Object)"value");
            initParameters.put(name, value);
        }
        return initParameters;
    }

    void handle(AnnotatedBeanDefinition beanDefinition, BeanDefinitionRegistry registry) {
        Map attributes = beanDefinition.getMetadata().getAnnotationAttributes(this.annotationType.getName());
        if (attributes != null) {
            this.doHandle(attributes, beanDefinition, registry);
        }
    }

    protected abstract void doHandle(Map<String, Object> var1, AnnotatedBeanDefinition var2, BeanDefinitionRegistry var3);
}

