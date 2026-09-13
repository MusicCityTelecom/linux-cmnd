/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.ComponentScan$Filter
 *  org.springframework.context.annotation.Import
 *  org.springframework.core.annotation.AliasFor
 */
package org.springframework.integration.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.integration.config.IntegrationComponentScanRegistrar;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
@Documented
@Import(value={IntegrationComponentScanRegistrar.class})
public @interface IntegrationComponentScan {
    @AliasFor(value="basePackages")
    public String[] value() default {};

    @AliasFor(value="value")
    public String[] basePackages() default {};

    public Class<?>[] basePackageClasses() default {};

    public boolean useDefaultFilters() default true;

    public ComponentScan.Filter[] includeFilters() default {};

    public ComponentScan.Filter[] excludeFilters() default {};
}

