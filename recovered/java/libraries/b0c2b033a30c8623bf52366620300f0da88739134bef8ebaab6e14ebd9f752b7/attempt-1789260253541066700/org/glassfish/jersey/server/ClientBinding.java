/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.ws.rs.core.Configuration;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.ANNOTATION_TYPE})
@Documented
public @interface ClientBinding {
    public Class<? extends Configuration> configClass() default Configuration.class;

    public boolean inheritServerProviders() default true;

    public String baseUri() default "";
}

