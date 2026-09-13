/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.function;

import java.util.function.BiFunction;
import org.jooq.lambda.function.Function0;
import org.jooq.lambda.function.Function1;
import org.jooq.lambda.tuple.Tuple1;
import org.jooq.lambda.tuple.Tuple2;

@FunctionalInterface
public interface Function2<T1, T2, R>
extends BiFunction<T1, T2, R> {
    default public R apply(Tuple2<? extends T1, ? extends T2> args) {
        return this.apply((T1)args.v1, (T2)args.v2);
    }

    @Override
    public R apply(T1 var1, T2 var2);

    default public BiFunction<T1, T2, R> toBiFunction() {
        return this::apply;
    }

    public static <T1, T2, R> Function2<T1, T2, R> from(BiFunction<? super T1, ? super T2, ? extends R> function) {
        return function::apply;
    }

    default public Function1<T2, R> applyPartially(T1 v1) {
        return v2 -> this.apply(v1, (T2)v2);
    }

    default public Function0<R> applyPartially(T1 v1, T2 v2) {
        return () -> this.apply(v1, v2);
    }

    default public Function1<T2, R> applyPartially(Tuple1<? extends T1> args) {
        return v2 -> this.apply((T1)args.v1, (T2)v2);
    }

    default public Function0<R> applyPartially(Tuple2<? extends T1, ? extends T2> args) {
        return () -> this.apply((T1)args.v1, (T2)args.v2);
    }

    @Deprecated
    default public Function1<T2, R> curry(T1 v1) {
        return v2 -> this.apply(v1, (T2)v2);
    }

    @Deprecated
    default public Function0<R> curry(T1 v1, T2 v2) {
        return () -> this.apply(v1, v2);
    }

    @Deprecated
    default public Function1<T2, R> curry(Tuple1<? extends T1> args) {
        return v2 -> this.apply((T1)args.v1, (T2)v2);
    }

    @Deprecated
    default public Function0<R> curry(Tuple2<? extends T1, ? extends T2> args) {
        return () -> this.apply((T1)args.v1, (T2)args.v2);
    }
}

