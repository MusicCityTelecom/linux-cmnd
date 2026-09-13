/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.BeanNameGenerator
 *  org.springframework.boot.SpringBootConfiguration
 *  org.springframework.boot.context.TypeExcludeFilter
 *  org.springframework.context.annotation.ComponentScan
 *  org.springframework.context.annotation.ComponentScan$Filter
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.FilterType
 *  org.springframework.core.annotation.AliasFor
 */
package org.springframework.boot.autoconfigure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.annotation.AliasFor;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters={@ComponentScan.Filter(type=FilterType.CUSTOM, classes={TypeExcludeFilter.class}), @ComponentScan.Filter(type=FilterType.CUSTOM, classes={AutoConfigurationExcludeFilter.class})})
public @interface SpringBootApplication {
    @AliasFor(annotation=EnableAutoConfiguration.class)
    public Class<?>[] exclude() default {};

    @AliasFor(annotation=EnableAutoConfiguration.class)
    public String[] excludeName() default {};

    @AliasFor(annotation=ComponentScan.class, attribute="basePackages")
    public String[] scanBasePackages() default {};

    @AliasFor(annotation=ComponentScan.class, attribute="basePackageClasses")
    public Class<?>[] scanBasePackageClasses() default {};

    @AliasFor(annotation=ComponentScan.class, attribute="nameGenerator")
    public Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;

    @AliasFor(annotation=Configuration.class)
    public boolean proxyBeanMethods() default true;
}

