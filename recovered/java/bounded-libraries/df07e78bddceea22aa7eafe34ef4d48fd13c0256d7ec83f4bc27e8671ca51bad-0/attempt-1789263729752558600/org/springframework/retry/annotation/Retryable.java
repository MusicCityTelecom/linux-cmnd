/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.retry.annotation.Backoff;

@Target(value={ElementType.METHOD, ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public @interface Retryable {
    public String recover() default "";

    public String interceptor() default "";

    public Class<? extends Throwable>[] value() default {};

    public Class<? extends Throwable>[] include() default {};

    public Class<? extends Throwable>[] exclude() default {};

    public String label() default "";

    public boolean stateful() default false;

    public int maxAttempts() default 3;

    public String maxAttemptsExpression() default "";

    public Backoff backoff() default @Backoff;

    public String exceptionExpression() default "";

    public String[] listeners() default {};
}

