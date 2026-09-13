/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.DefaultListableBeanFactory
 *  org.springframework.context.support.GenericApplicationContext
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.io.Resource
 */
package org.springframework.boot.web.reactive.context;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.web.reactive.context.ConfigurableReactiveWebApplicationContext;
import org.springframework.boot.web.reactive.context.FilteredReactiveWebContextResource;
import org.springframework.boot.web.reactive.context.StandardReactiveWebEnvironment;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;

public class GenericReactiveWebApplicationContext
extends GenericApplicationContext
implements ConfigurableReactiveWebApplicationContext {
    public GenericReactiveWebApplicationContext() {
    }

    public GenericReactiveWebApplicationContext(DefaultListableBeanFactory beanFactory) {
        super(beanFactory);
    }

    protected ConfigurableEnvironment createEnvironment() {
        return new StandardReactiveWebEnvironment();
    }

    protected Resource getResourceByPath(String path) {
        return new FilteredReactiveWebContextResource(path);
    }
}

