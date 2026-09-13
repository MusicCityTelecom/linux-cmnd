/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.MutablePropertySources
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.env.StandardEnvironment
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.util.ClassUtils;

final class EnvironmentConverter {
    private static final String CONFIGURABLE_WEB_ENVIRONMENT_CLASS = "org.springframework.web.context.ConfigurableWebEnvironment";
    private static final Set<String> SERVLET_ENVIRONMENT_SOURCE_NAMES;
    private final ClassLoader classLoader;

    EnvironmentConverter(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    StandardEnvironment convertEnvironmentIfNecessary(ConfigurableEnvironment environment, Class<? extends StandardEnvironment> type) {
        if (type.equals(environment.getClass())) {
            return (StandardEnvironment)environment;
        }
        return this.convertEnvironment(environment, type);
    }

    private StandardEnvironment convertEnvironment(ConfigurableEnvironment environment, Class<? extends StandardEnvironment> type) {
        StandardEnvironment result = this.createEnvironment(type);
        result.setActiveProfiles(environment.getActiveProfiles());
        result.setConversionService(environment.getConversionService());
        this.copyPropertySources(environment, result);
        return result;
    }

    private StandardEnvironment createEnvironment(Class<? extends StandardEnvironment> type) {
        try {
            return type.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception ex) {
            return new StandardEnvironment();
        }
    }

    private void copyPropertySources(ConfigurableEnvironment source, StandardEnvironment target) {
        this.removePropertySources(target.getPropertySources(), this.isServletEnvironment(target.getClass(), this.classLoader));
        for (PropertySource propertySource : source.getPropertySources()) {
            if (SERVLET_ENVIRONMENT_SOURCE_NAMES.contains(propertySource.getName())) continue;
            target.getPropertySources().addLast(propertySource);
        }
    }

    private boolean isServletEnvironment(Class<?> conversionType, ClassLoader classLoader) {
        try {
            Class webEnvironmentClass = ClassUtils.forName((String)CONFIGURABLE_WEB_ENVIRONMENT_CLASS, (ClassLoader)classLoader);
            return webEnvironmentClass.isAssignableFrom(conversionType);
        }
        catch (Throwable ex) {
            return false;
        }
    }

    private void removePropertySources(MutablePropertySources propertySources, boolean isServletEnvironment) {
        HashSet<String> names = new HashSet<String>();
        for (PropertySource propertySource : propertySources) {
            names.add(propertySource.getName());
        }
        for (String name : names) {
            if (isServletEnvironment && SERVLET_ENVIRONMENT_SOURCE_NAMES.contains(name)) continue;
            propertySources.remove(name);
        }
    }

    static {
        HashSet<String> names = new HashSet<String>();
        names.add("servletContextInitParams");
        names.add("servletConfigInitParams");
        names.add("jndiProperties");
        SERVLET_ENVIRONMENT_SOURCE_NAMES = Collections.unmodifiableSet(names);
    }
}

