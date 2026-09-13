/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Import
 *  org.springframework.core.annotation.AliasFor
 */
package org.springframework.boot.autoconfigure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.ImportAutoConfigurationImportSelector;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(value={ImportAutoConfigurationImportSelector.class})
public @interface ImportAutoConfiguration {
    @AliasFor(value="classes")
    public Class<?>[] value() default {};

    @AliasFor(value="value")
    public Class<?>[] classes() default {};

    public Class<?>[] exclude() default {};
}

