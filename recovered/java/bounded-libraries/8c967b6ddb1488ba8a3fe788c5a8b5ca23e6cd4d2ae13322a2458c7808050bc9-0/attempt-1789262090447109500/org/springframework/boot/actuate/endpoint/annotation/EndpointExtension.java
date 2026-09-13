/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.actuate.endpoint.EndpointFilter;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public @interface EndpointExtension {
    public Class<? extends EndpointFilter<?>> filter();

    public Class<?> endpoint() default Void.class;
}

