/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.annotation.AliasFor
 */
package org.springframework.boot.actuate.endpoint.jmx.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.FilteredEndpoint;
import org.springframework.boot.actuate.endpoint.jmx.annotation.JmxEndpointFilter;
import org.springframework.core.annotation.AliasFor;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Endpoint
@FilteredEndpoint(value=JmxEndpointFilter.class)
public @interface JmxEndpoint {
    @AliasFor(annotation=Endpoint.class)
    public String id() default "";

    @AliasFor(annotation=Endpoint.class)
    public boolean enableByDefault() default true;
}

