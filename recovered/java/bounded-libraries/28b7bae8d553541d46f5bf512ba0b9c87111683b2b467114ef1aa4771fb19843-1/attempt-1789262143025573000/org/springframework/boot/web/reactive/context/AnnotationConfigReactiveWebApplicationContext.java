/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.DefaultListableBeanFactory
 *  org.springframework.context.annotation.AnnotationConfigApplicationContext
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.io.Resource
 */
package org.springframework.boot.web.reactive.context;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.web.reactive.context.ConfigurableReactiveWebApplicationContext;
import org.springframework.boot.web.reactive.context.FilteredReactiveWebContextResource;
import org.springframework.boot.web.reactive.context.StandardReactiveWebEnvironment;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;

public class AnnotationConfigReactiveWebApplicationContext
extends AnnotationConfigApplicationContext
implements ConfigurableReactiveWebApplicationContext {
    public AnnotationConfigReactiveWebApplicationContext() {
    }

    public AnnotationConfigReactiveWebApplicationContext(DefaultListableBeanFactory beanFactory) {
        super(beanFactory);
    }

    public AnnotationConfigReactiveWebApplicationContext(Class<?> ... annotatedClasses) {
        super((Class[])annotatedClasses);
    }

    public AnnotationConfigReactiveWebApplicationContext(String ... basePackages) {
        super(basePackages);
    }

    protected ConfigurableEnvironment createEnvironment() {
        return new StandardReactiveWebEnvironment();
    }

    protected Resource getResourceByPath(String path) {
        return new FilteredReactiveWebContextResource(path);
    }
}

