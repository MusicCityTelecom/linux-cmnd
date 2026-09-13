/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 *  org.checkerframework.checker.nullness.qual.Nullable
 */
package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.checkerframework.checker.nullness.qual.Nullable;

@FunctionalInterface
@GwtCompatible
public interface Predicate<T>
extends java.util.function.Predicate<T> {
    @CanIgnoreReturnValue
    public boolean apply(@Nullable T var1);

    public boolean equals(@Nullable Object var1);

    @Override
    default public boolean test(@Nullable T input) {
        return this.apply(input);
    }
}

