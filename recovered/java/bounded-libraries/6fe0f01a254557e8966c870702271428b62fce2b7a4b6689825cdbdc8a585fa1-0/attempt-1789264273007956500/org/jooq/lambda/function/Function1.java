/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.function;

import java.util.function.Function;
import org.jooq.lambda.function.Function0;
import org.jooq.lambda.tuple.Tuple1;

@FunctionalInterface
public interface Function1<T1, R>
extends Function<T1, R> {
    @Override
    default public R apply(Tuple1<? extends T1> args) {
        return this.apply((T1)args.v1);
    }

    @Override
    public R apply(T1 var1);

    default public Function<T1, R> toFunction() {
        return this::apply;
    }

    public static <T1, R> Function1<T1, R> from(Function<? super T1, ? extends R> function) {
        return function::apply;
    }

    default public Function0<R> applyPartially(T1 v1) {
        return () -> this.apply(v1);
    }

    default public Function0<R> applyPartially(Tuple1<? extends T1> args) {
        return () -> this.apply((T1)args.v1);
    }

    @Deprecated
    default public Function0<R> curry(T1 v1) {
        return () -> this.apply(v1);
    }

    @Deprecated
    default public Function0<R> curry(Tuple1<? extends T1> args) {
        return () -> this.apply((T1)args.v1);
    }
}

