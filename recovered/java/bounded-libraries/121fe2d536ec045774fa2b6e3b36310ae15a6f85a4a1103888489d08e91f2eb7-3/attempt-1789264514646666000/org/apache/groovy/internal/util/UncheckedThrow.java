/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.internal.util;

import org.apache.groovy.lang.annotation.Incubating;

@Incubating
public class UncheckedThrow {
    public static void rethrow(Throwable checkedException) {
        UncheckedThrow.thrownInsteadOf(checkedException);
    }

    private static <T extends Throwable> void thrownInsteadOf(Throwable t) throws T {
        throw t;
    }
}

