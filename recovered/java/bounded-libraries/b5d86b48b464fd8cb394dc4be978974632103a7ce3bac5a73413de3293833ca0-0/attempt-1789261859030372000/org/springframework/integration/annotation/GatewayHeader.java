/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface GatewayHeader {
    public String name();

    public String value() default "";

    public String expression() default "";
}

