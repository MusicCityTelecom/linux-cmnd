/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.function;

import org.jooq.lambda.function.Function0;
import org.jooq.lambda.function.Function1;
import org.jooq.lambda.function.Function2;
import org.jooq.lambda.tuple.Tuple1;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;

@FunctionalInterface
public interface Function3<T1, T2, T3, R> {
    default public R apply(Tuple3<? extends T1, ? extends T2, ? extends T3> args) {
        return this.apply(args.v1, args.v2, args.v3);
    }

    public R apply(T1 var1, T2 var2, T3 var3);

    default public Function2<T2, T3, R> applyPartially(T1 v1) {
        return (v2, v3) -> this.apply(v1, v2, v3);
    }

    default public Function1<T3, R> applyPartially(T1 v1, T2 v2) {
        return v3 -> this.apply(v1, v2, v3);
    }

    default public Function0<R> applyPartially(T1 v1, T2 v2, T3 v3) {
        return () -> this.apply(v1, v2, v3);
    }

    default public Function2<T2, T3, R> applyPartially(Tuple1<? extends T1> args) {
        return (v2, v3) -> this.apply(args.v1, v2, v3);
    }

    default public Function1<T3, R> applyPartially(Tuple2<? extends T1, ? extends T2> args) {
        return v3 -> this.apply(args.v1, args.v2, v3);
    }

    default public Function0<R> applyPartially(Tuple3<? extends T1, ? extends T2, ? extends T3> args) {
        return () -> this.apply(args.v1, args.v2, args.v3);
    }

    @Deprecated
    default public Function2<T2, T3, R> curry(T1 v1) {
        return (v2, v3) -> this.apply(v1, v2, v3);
    }

    @Deprecated
    default public Function1<T3, R> curry(T1 v1, T2 v2) {
        return v3 -> this.apply(v1, v2, v3);
    }

    @Deprecated
    default public Function0<R> curry(T1 v1, T2 v2, T3 v3) {
        return () -> this.apply(v1, v2, v3);
    }

    @Deprecated
    default public Function2<T2, T3, R> curry(Tuple1<? extends T1> args) {
        return (v2, v3) -> this.apply(args.v1, v2, v3);
    }

    @Deprecated
    default public Function1<T3, R> curry(Tuple2<? extends T1, ? extends T2> args) {
        return v3 -> this.apply(args.v1, args.v2, v3);
    }

    @Deprecated
    default public Function0<R> curry(Tuple3<? extends T1, ? extends T2, ? extends T3> args) {
        return () -> this.apply(args.v1, args.v2, args.v3);
    }
}

