/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.PARAMETER})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public @interface Selector {
    public Match match() default Match.SINGLE;

    public static enum Match {
        SINGLE,
        ALL_REMAINING;

    }
}

