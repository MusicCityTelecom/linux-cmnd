/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 */
package com.google.common.base;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

final class Java8Usage {
    @CanIgnoreReturnValue
    static @SomeTypeAnnotation String performCheck() {
        Runnable r = () -> {};
        r.run();
        return "";
    }

    private Java8Usage() {
    }

    @Target(value={ElementType.TYPE_USE})
    @Retention(value=RetentionPolicy.RUNTIME)
    private static @interface SomeTypeAnnotation {
    }
}

