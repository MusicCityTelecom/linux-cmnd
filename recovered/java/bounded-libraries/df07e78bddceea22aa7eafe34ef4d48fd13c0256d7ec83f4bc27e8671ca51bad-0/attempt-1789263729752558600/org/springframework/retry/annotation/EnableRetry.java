/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.EnableAspectJAutoProxy
 *  org.springframework.context.annotation.Import
 */
package org.springframework.retry.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.retry.annotation.RetryConfiguration;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@EnableAspectJAutoProxy(proxyTargetClass=false)
@Import(value={RetryConfiguration.class})
@Documented
public @interface EnableRetry {
    public boolean proxyTargetClass() default false;
}

