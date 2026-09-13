/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanUtils
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.annotation.AnnotationConfigApplicationContext
 *  org.springframework.core.io.support.SpringFactoriesLoader
 */
package org.springframework.boot;

import java.util.function.Supplier;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.support.SpringFactoriesLoader;

@FunctionalInterface
public interface ApplicationContextFactory {
    public static final ApplicationContextFactory DEFAULT = webApplicationType -> {
        try {
            for (ApplicationContextFactory candidate : SpringFactoriesLoader.loadFactories(ApplicationContextFactory.class, (ClassLoader)ApplicationContextFactory.class.getClassLoader())) {
                ConfigurableApplicationContext context = candidate.create(webApplicationType);
                if (context == null) continue;
                return context;
            }
            return new AnnotationConfigApplicationContext();
        }
        catch (Exception ex) {
            throw new IllegalStateException("Unable create a default ApplicationContext instance, you may need a custom ApplicationContextFactory", ex);
        }
    };

    public ConfigurableApplicationContext create(WebApplicationType var1);

    public static ApplicationContextFactory ofContextClass(Class<? extends ConfigurableApplicationContext> contextClass) {
        return ApplicationContextFactory.of(() -> (ConfigurableApplicationContext)BeanUtils.instantiateClass((Class)contextClass));
    }

    public static ApplicationContextFactory of(Supplier<ConfigurableApplicationContext> supplier) {
        return webApplicationType -> (ConfigurableApplicationContext)supplier.get();
    }
}

