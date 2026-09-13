/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.model.internal;

import java.util.Map;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.InjectionManagerSupplier;

public class FeatureContextWrapper
implements FeatureContext,
InjectionManagerSupplier {
    private final FeatureContext context;
    private final InjectionManager injectionManager;

    public FeatureContextWrapper(FeatureContext context, InjectionManager injectionManager) {
        this.context = context;
        this.injectionManager = injectionManager;
    }

    @Override
    public Configuration getConfiguration() {
        return this.context.getConfiguration();
    }

    @Override
    public FeatureContext property(String name, Object value) {
        return (FeatureContext)this.context.property(name, value);
    }

    @Override
    public FeatureContext register(Class<?> componentClass) {
        return (FeatureContext)this.context.register(componentClass);
    }

    @Override
    public FeatureContext register(Class<?> componentClass, int priority) {
        return (FeatureContext)this.context.register(componentClass, priority);
    }

    @Override
    public FeatureContext register(Class<?> componentClass, Class<?> ... contracts) {
        return (FeatureContext)this.context.register(componentClass, contracts);
    }

    @Override
    public FeatureContext register(Class<?> componentClass, Map<Class<?>, Integer> contracts) {
        return (FeatureContext)this.context.register(componentClass, contracts);
    }

    @Override
    public FeatureContext register(Object component) {
        return (FeatureContext)this.context.register(component);
    }

    @Override
    public FeatureContext register(Object component, int priority) {
        return (FeatureContext)this.context.register(component, priority);
    }

    @Override
    public FeatureContext register(Object component, Class<?> ... contracts) {
        return (FeatureContext)this.context.register(component, contracts);
    }

    @Override
    public FeatureContext register(Object component, Map<Class<?>, Integer> contracts) {
        return (FeatureContext)this.context.register(component, contracts);
    }

    @Override
    public InjectionManager getInjectionManager() {
        return this.injectionManager;
    }
}

