/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.annotation.AliasFor
 */
package org.springframework.boot.autoconfigure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AliasFor;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Configuration(proxyBeanMethods=false)
@AutoConfigureBefore
@AutoConfigureAfter
public @interface AutoConfiguration {
    @AliasFor(annotation=Configuration.class)
    public String value() default "";

    @AliasFor(annotation=AutoConfigureBefore.class, attribute="value")
    public Class<?>[] before() default {};

    @AliasFor(annotation=AutoConfigureBefore.class, attribute="name")
    public String[] beforeName() default {};

    @AliasFor(annotation=AutoConfigureAfter.class, attribute="value")
    public Class<?>[] after() default {};

    @AliasFor(annotation=AutoConfigureAfter.class, attribute="name")
    public String[] afterName() default {};
}

