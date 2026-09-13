/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.function;

import org.jooq.lambda.tuple.Tuple0;

@FunctionalInterface
public interface Consumer0
extends Runnable {
    default public void accept(Tuple0 args) {
        this.accept();
    }

    public void accept();

    @Override
    default public void run() {
        this.accept();
    }

    default public Runnable toRunnable() {
        return this::accept;
    }

    public static Consumer0 from(Runnable runnable) {
        return runnable::run;
    }
}

