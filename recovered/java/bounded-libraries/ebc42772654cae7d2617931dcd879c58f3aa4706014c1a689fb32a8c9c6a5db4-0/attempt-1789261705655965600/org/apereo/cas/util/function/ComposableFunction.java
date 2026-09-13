/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.util.function;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

@FunctionalInterface
public interface ComposableFunction<T, R>
extends Function<T, R> {
    default public Consumer<T> andNext(Consumer<R> after) {
        Objects.requireNonNull(after);
        return t -> after.accept(this.apply(t));
    }
}

