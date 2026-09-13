/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.integration.annotation.GatewayHeader;

@Target(value={ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public @interface Gateway {
    public String requestChannel() default "";

    public String replyChannel() default "";

    public long requestTimeout() default -9223372036854775808L;

    public String requestTimeoutExpression() default "";

    public long replyTimeout() default -9223372036854775808L;

    public String replyTimeoutExpression() default "";

    public String payloadExpression() default "";

    public GatewayHeader[] headers() default {};
}

