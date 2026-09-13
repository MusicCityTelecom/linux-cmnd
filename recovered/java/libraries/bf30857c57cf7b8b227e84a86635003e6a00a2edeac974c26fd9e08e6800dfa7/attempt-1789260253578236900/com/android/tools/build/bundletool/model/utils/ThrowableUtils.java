/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils;

import com.google.common.base.Throwables;
import java.util.Arrays;
import java.util.function.Predicate;

public final class ThrowableUtils {
    public static boolean anyInCausalChainOrSuppressedMatches(Throwable baseThrowable, Predicate<Throwable> predicate) {
        for (Throwable throwable : Throwables.getCausalChain(baseThrowable)) {
            if (!predicate.test(throwable) && !Arrays.stream(throwable.getSuppressed()).anyMatch(predicate)) continue;
            return true;
        }
        return false;
    }

    private ThrowableUtils() {
    }
}

