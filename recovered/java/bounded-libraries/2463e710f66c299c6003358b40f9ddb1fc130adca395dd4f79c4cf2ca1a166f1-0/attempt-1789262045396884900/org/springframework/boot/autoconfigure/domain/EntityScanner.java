/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.type.filter.AnnotationTypeFilter
 *  org.springframework.core.type.filter.TypeFilter
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.domain;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.domain.EntityScanPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

public class EntityScanner {
    private final ApplicationContext context;

    public EntityScanner(ApplicationContext context) {
        Assert.notNull((Object)context, (String)"Context must not be null");
        this.context = context;
    }

    @SafeVarargs
    public final Set<Class<?>> scan(Class<? extends Annotation> ... annotationTypes) throws ClassNotFoundException {
        List<String> packages = this.getPackages();
        if (packages.isEmpty()) {
            return Collections.emptySet();
        }
        ClassPathScanningCandidateComponentProvider scanner = this.createClassPathScanningCandidateComponentProvider(this.context);
        for (Class<? extends Annotation> annotationType : annotationTypes) {
            scanner.addIncludeFilter((TypeFilter)new AnnotationTypeFilter(annotationType));
        }
        HashSet entitySet = new HashSet();
        for (String basePackage : packages) {
            if (!StringUtils.hasText((String)basePackage)) continue;
            for (BeanDefinition candidate : scanner.findCandidateComponents(basePackage)) {
                entitySet.add(ClassUtils.forName((String)candidate.getBeanClassName(), (ClassLoader)this.context.getClassLoader()));
            }
        }
        return entitySet;
    }

    protected ClassPathScanningCandidateComponentProvider createClassPathScanningCandidateComponentProvider(ApplicationContext context) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.setEnvironment(context.getEnvironment());
        scanner.setResourceLoader((ResourceLoader)context);
        return scanner;
    }

    private List<String> getPackages() {
        List<String> packages = EntityScanPackages.get((BeanFactory)this.context).getPackageNames();
        if (packages.isEmpty() && AutoConfigurationPackages.has((BeanFactory)this.context)) {
            packages = AutoConfigurationPackages.get((BeanFactory)this.context);
        }
        return packages;
    }
}

