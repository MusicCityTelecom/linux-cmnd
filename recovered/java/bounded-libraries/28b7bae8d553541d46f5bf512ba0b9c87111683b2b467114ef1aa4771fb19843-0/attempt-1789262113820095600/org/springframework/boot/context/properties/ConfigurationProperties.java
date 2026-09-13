/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.annotation.AliasFor
 *  org.springframework.stereotype.Indexed
 */
package org.springframework.boot.context.properties;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

@Target(value={ElementType.TYPE, ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface ConfigurationProperties {
    @AliasFor(value="prefix")
    public String value() default "";

    @AliasFor(value="value")
    public String prefix() default "";

    public boolean ignoreInvalidFields() default false;

    public boolean ignoreUnknownFields() default true;
}

