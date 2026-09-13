/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.annotation.AliasFor
 *  org.springframework.web.bind.annotation.ResponseBody
 */
package org.springframework.boot.actuate.endpoint.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.FilteredEndpoint;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointFilter;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.ResponseBody;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Endpoint
@FilteredEndpoint(value=ControllerEndpointFilter.class)
@ResponseBody
public @interface RestControllerEndpoint {
    @AliasFor(annotation=Endpoint.class)
    public String id();

    @AliasFor(annotation=Endpoint.class)
    public boolean enableByDefault() default true;
}

