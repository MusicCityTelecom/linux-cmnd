/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Import
 *  org.springframework.core.annotation.AliasFor
 */
package org.springframework.boot.autoconfigure.domain;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.domain.EntityScanPackages;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Import(value={EntityScanPackages.Registrar.class})
public @interface EntityScan {
    @AliasFor(value="basePackages")
    public String[] value() default {};

    @AliasFor(value="value")
    public String[] basePackages() default {};

    public Class<?>[] basePackageClasses() default {};
}

