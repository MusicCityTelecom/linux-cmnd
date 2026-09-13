/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.io.support.SpringFactoriesLoader
 */
package org.springframework.boot.env;

import java.util.List;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.ReflectionEnvironmentPostProcessorsFactory;
import org.springframework.boot.logging.DeferredLogFactory;
import org.springframework.core.io.support.SpringFactoriesLoader;

@FunctionalInterface
public interface EnvironmentPostProcessorsFactory {
    public List<EnvironmentPostProcessor> getEnvironmentPostProcessors(DeferredLogFactory var1, ConfigurableBootstrapContext var2);

    public static EnvironmentPostProcessorsFactory fromSpringFactories(ClassLoader classLoader) {
        return new ReflectionEnvironmentPostProcessorsFactory(classLoader, SpringFactoriesLoader.loadFactoryNames(EnvironmentPostProcessor.class, (ClassLoader)classLoader));
    }

    public static EnvironmentPostProcessorsFactory of(Class<?> ... classes) {
        return new ReflectionEnvironmentPostProcessorsFactory(classes);
    }

    public static EnvironmentPostProcessorsFactory of(String ... classNames) {
        return EnvironmentPostProcessorsFactory.of(null, classNames);
    }

    public static EnvironmentPostProcessorsFactory of(ClassLoader classLoader, String ... classNames) {
        return new ReflectionEnvironmentPostProcessorsFactory(classLoader, classNames);
    }
}

