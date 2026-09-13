/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.stereotype.Indexed
 */
package org.springframework.integration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.stereotype.Indexed;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Indexed
public @interface MessagingGateway {
    public String name() default "";

    public String defaultRequestChannel() default "";

    public String defaultReplyChannel() default "";

    public String errorChannel() default "";

    public String defaultRequestTimeout() default "-9223372036854775808";

    public String defaultReplyTimeout() default "-9223372036854775808";

    public String asyncExecutor() default "";

    public String defaultPayloadExpression() default "";

    public GatewayHeader[] defaultHeaders() default {};

    public String mapper() default "";

    public boolean proxyDefaultMethods() default false;
}

