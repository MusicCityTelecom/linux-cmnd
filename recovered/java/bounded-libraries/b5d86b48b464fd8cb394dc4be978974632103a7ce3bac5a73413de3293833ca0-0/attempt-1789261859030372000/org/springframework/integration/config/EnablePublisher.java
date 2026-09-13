/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Import
 *  org.springframework.core.annotation.AliasFor
 */
package org.springframework.integration.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.integration.config.PublisherRegistrar;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Import(value={PublisherRegistrar.class})
public @interface EnablePublisher {
    @AliasFor(value="defaultChannel")
    public String value() default "";

    @AliasFor(value="value")
    public String defaultChannel() default "";

    public boolean proxyTargetClass() default false;

    public int order() default 0x7FFFFFFF;
}

