/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.retry.annotation.Retryable;

@Target(value={ElementType.METHOD, ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Retryable(stateful=true)
public @interface CircuitBreaker {
    public Class<? extends Throwable>[] value() default {};

    public Class<? extends Throwable>[] include() default {};

    public Class<? extends Throwable>[] exclude() default {};

    public int maxAttempts() default 3;

    public String maxAttemptsExpression() default "";

    public String label() default "";

    public long resetTimeout() default 20000L;

    public String resetTimeoutExpression() default "";

    public long openTimeout() default 5000L;

    public String openTimeoutExpression() default "";

    public String exceptionExpression() default "";
}

