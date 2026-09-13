/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.beans.factory.annotation.AnnotatedBeanDefinition
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
 *  org.springframework.core.annotation.MergedAnnotation
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.core.annotation.MergedAnnotations$SearchStrategy
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.type.filter.AnnotationTypeFilter
 *  org.springframework.core.type.filter.TypeFilter
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import java.util.Collection;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.jackson.JsonMixin;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class JsonMixinModule
extends SimpleModule
implements InitializingBean {
    private final ApplicationContext context;
    private final Collection<String> basePackages;

    public JsonMixinModule(ApplicationContext context, Collection<String> basePackages) {
        Assert.notNull((Object)context, (String)"Context must not be null");
        this.context = context;
        this.basePackages = basePackages;
    }

    public void afterPropertiesSet() throws Exception {
        if (ObjectUtils.isEmpty(this.basePackages)) {
            return;
        }
        JsonMixinComponentScanner scanner = new JsonMixinComponentScanner();
        scanner.setEnvironment(this.context.getEnvironment());
        scanner.setResourceLoader((ResourceLoader)this.context);
        for (String basePackage : this.basePackages) {
            if (!StringUtils.hasText((String)basePackage)) continue;
            for (BeanDefinition candidate : scanner.findCandidateComponents(basePackage)) {
                this.addJsonMixin(ClassUtils.forName((String)candidate.getBeanClassName(), (ClassLoader)this.context.getClassLoader()));
            }
        }
    }

    private void addJsonMixin(Class<?> mixinClass) {
        MergedAnnotation annotation = MergedAnnotations.from(mixinClass, (MergedAnnotations.SearchStrategy)MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(JsonMixin.class);
        for (Class targetType : annotation.getClassArray("type")) {
            this.setMixInAnnotation(targetType, mixinClass);
        }
    }

    static class JsonMixinComponentScanner
    extends ClassPathScanningCandidateComponentProvider {
        JsonMixinComponentScanner() {
            this.addIncludeFilter((TypeFilter)new AnnotationTypeFilter(JsonMixin.class));
        }

        protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
            return true;
        }
    }
}

