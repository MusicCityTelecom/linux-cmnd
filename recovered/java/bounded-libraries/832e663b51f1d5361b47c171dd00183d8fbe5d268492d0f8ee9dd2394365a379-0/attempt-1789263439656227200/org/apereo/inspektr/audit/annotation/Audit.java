/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.inspektr.audit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface Audit {
    public String applicationCode() default "";

    public String action();

    public String resourceResolverName();

    public String actionResolverName();

    public String principalResolverName() default "";
}

