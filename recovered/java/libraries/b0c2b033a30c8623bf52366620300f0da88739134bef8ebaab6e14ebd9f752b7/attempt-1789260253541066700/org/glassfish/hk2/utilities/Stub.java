/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(value=RetentionPolicy.SOURCE)
@Target(value={ElementType.TYPE})
public @interface Stub {
    public Type value() default Type.VALUES;

    public static enum Type {
        VALUES,
        EXCEPTIONS;

    }
}

