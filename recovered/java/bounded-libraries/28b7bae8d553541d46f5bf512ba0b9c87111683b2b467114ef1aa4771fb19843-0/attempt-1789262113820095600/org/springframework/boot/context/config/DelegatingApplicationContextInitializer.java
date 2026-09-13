/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanUtils
 *  org.springframework.context.ApplicationContextException
 *  org.springframework.context.ApplicationContextInitializer
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.core.GenericTypeResolver
 *  org.springframework.core.Ordered
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.context.config;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

public class DelegatingApplicationContextInitializer
implements ApplicationContextInitializer<ConfigurableApplicationContext>,
Ordered {
    private static final String PROPERTY_NAME = "context.initializer.classes";
    private int order = 0;

    public void initialize(ConfigurableApplicationContext context) {
        ConfigurableEnvironment environment = context.getEnvironment();
        List<Class<?>> initializerClasses = this.getInitializerClasses(environment);
        if (!initializerClasses.isEmpty()) {
            this.applyInitializerClasses(context, initializerClasses);
        }
    }

    private List<Class<?>> getInitializerClasses(ConfigurableEnvironment env) {
        String classNames = env.getProperty(PROPERTY_NAME);
        ArrayList classes = new ArrayList();
        if (StringUtils.hasLength((String)classNames)) {
            for (String className : StringUtils.tokenizeToStringArray((String)classNames, (String)",")) {
                classes.add(this.getInitializerClass(className));
            }
        }
        return classes;
    }

    private Class<?> getInitializerClass(String className) throws LinkageError {
        try {
            Class initializerClass = ClassUtils.forName((String)className, (ClassLoader)ClassUtils.getDefaultClassLoader());
            Assert.isAssignable(ApplicationContextInitializer.class, (Class)initializerClass);
            return initializerClass;
        }
        catch (ClassNotFoundException ex) {
            throw new ApplicationContextException("Failed to load context initializer class [" + className + "]", (Throwable)ex);
        }
    }

    private void applyInitializerClasses(ConfigurableApplicationContext context, List<Class<?>> initializerClasses) {
        Class<?> contextClass = context.getClass();
        ArrayList initializers = new ArrayList();
        for (Class<?> initializerClass : initializerClasses) {
            initializers.add(this.instantiateInitializer(contextClass, initializerClass));
        }
        this.applyInitializers(context, initializers);
    }

    private ApplicationContextInitializer<?> instantiateInitializer(Class<?> contextClass, Class<?> initializerClass) {
        Class requireContextClass = GenericTypeResolver.resolveTypeArgument(initializerClass, ApplicationContextInitializer.class);
        Assert.isAssignable((Class)requireContextClass, contextClass, () -> String.format("Could not add context initializer [%s] as its generic parameter [%s] is not assignable from the type of application context used by this context loader [%s]: ", initializerClass.getName(), requireContextClass.getName(), contextClass.getName()));
        return (ApplicationContextInitializer)BeanUtils.instantiateClass(initializerClass);
    }

    private void applyInitializers(ConfigurableApplicationContext context, List<ApplicationContextInitializer<?>> initializers) {
        initializers.sort((Comparator<ApplicationContextInitializer<?>>)new AnnotationAwareOrderComparator());
        for (ApplicationContextInitializer<?> initializer : initializers) {
            initializer.initialize(context);
        }
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return this.order;
    }
}

