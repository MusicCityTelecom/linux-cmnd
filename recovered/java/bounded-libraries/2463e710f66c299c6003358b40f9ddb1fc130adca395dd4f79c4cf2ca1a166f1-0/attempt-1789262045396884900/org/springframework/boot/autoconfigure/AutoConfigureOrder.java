/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.autoconfigure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Documented
public @interface AutoConfigureOrder {
    public static final int DEFAULT_ORDER = 0;

    public int value() default 0;
}

