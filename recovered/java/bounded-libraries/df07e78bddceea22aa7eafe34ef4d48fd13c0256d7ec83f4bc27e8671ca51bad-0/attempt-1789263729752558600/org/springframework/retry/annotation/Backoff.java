/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public @interface Backoff {
    public long value() default 1000L;

    public long delay() default 0L;

    public long maxDelay() default 0L;

    public double multiplier() default 0.0;

    public String delayExpression() default "";

    public String maxDelayExpression() default "";

    public String multiplierExpression() default "";

    public boolean random() default false;

    public String randomExpression() default "";
}

