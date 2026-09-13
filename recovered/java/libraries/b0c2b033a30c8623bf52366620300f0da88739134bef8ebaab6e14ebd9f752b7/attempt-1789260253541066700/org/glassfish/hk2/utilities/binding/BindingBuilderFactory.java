/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.binding;

import java.lang.annotation.Annotation;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.HK2Loader;
import org.glassfish.hk2.utilities.binding.AbstractBindingBuilder;
import org.glassfish.hk2.utilities.binding.BindingBuilder;
import org.glassfish.hk2.utilities.binding.ScopedBindingBuilder;
import org.glassfish.hk2.utilities.binding.ServiceBindingBuilder;

public class BindingBuilderFactory {
    public static void addBinding(BindingBuilder<?> builder, DynamicConfiguration configuration) {
        if (!(builder instanceof AbstractBindingBuilder)) {
            throw new IllegalArgumentException("Unknown binding builder type: " + builder.getClass().getName());
        }
        ((AbstractBindingBuilder)builder).complete(configuration, null);
    }

    public static void addBinding(BindingBuilder<?> builder, DynamicConfiguration configuration, HK2Loader defaultLoader) {
        if (!(builder instanceof AbstractBindingBuilder)) {
            throw new IllegalArgumentException("Unknown binding builder type: " + builder.getClass().getName());
        }
        ((AbstractBindingBuilder)builder).complete(configuration, defaultLoader);
    }

    public static <T> ServiceBindingBuilder<T> newFactoryBinder(Class<? extends Factory<T>> factoryType, Class<? extends Annotation> factoryScope) {
        return AbstractBindingBuilder.createFactoryBinder(factoryType, factoryScope);
    }

    public static <T> ServiceBindingBuilder<T> newFactoryBinder(Class<? extends Factory<T>> factoryType) {
        return AbstractBindingBuilder.createFactoryBinder(factoryType, null);
    }

    public static <T> ServiceBindingBuilder<T> newFactoryBinder(Factory<T> factory) {
        return AbstractBindingBuilder.createFactoryBinder(factory);
    }

    public static <T> ServiceBindingBuilder<T> newBinder(Class<T> serviceType) {
        return AbstractBindingBuilder.create(serviceType, false);
    }

    public static <T> ScopedBindingBuilder<T> newBinder(T service) {
        return AbstractBindingBuilder.create(service);
    }
}

