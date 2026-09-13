/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.lang;

import java.util.function.Consumer;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedRunnable {
    public void run() throws Throwable;

    public static Runnable sneaky(CheckedRunnable runnable) {
        return Sneaky.runnable(runnable);
    }

    public static Runnable unchecked(CheckedRunnable runnable) {
        return Unchecked.runnable(runnable);
    }

    public static Runnable unchecked(CheckedRunnable runnable, Consumer<Throwable> handler) {
        return Unchecked.runnable(runnable, handler);
    }
}

