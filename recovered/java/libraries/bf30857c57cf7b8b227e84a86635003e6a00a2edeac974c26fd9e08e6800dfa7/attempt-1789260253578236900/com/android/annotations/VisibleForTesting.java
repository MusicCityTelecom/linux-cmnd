/*
 * Decompiled with CFR 0.152.
 */
package com.android.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.SOURCE)
public @interface VisibleForTesting {
    public Visibility visibility() default Visibility.PRIVATE;

    public static enum Visibility {
        PROTECTED,
        PACKAGE,
        PRIVATE;

    }
}

