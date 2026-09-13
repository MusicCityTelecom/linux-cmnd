/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.function;

import org.jooq.lambda.function.Function0;
import org.jooq.lambda.function.Function1;
import org.jooq.lambda.function.Function2;
import org.jooq.lambda.function.Function3;
import org.jooq.lambda.tuple.Tuple1;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;
import org.jooq.lambda.tuple.Tuple4;

@FunctionalInterface
public interface Function4<T1, T2, T3, T4, R> {
    default public R apply(Tuple4<? extends T1, ? extends T2, ? extends T3, ? extends T4> args) {
        return this.apply(args.v1, args.v2, args.v3, args.v4);
    }

    public R apply(T1 var1, T2 var2, T3 var3, T4 var4);

    default public Function3<T2, T3, T4, R> applyPartially(T1 v1) {
        return (v2, v3, v4) -> this.apply(v1, v2, v3, v4);
    }

    default public Function2<T3, T4, R> applyPartially(T1 v1, T2 v2) {
        return (v3, v4) -> this.apply(v1, v2, v3, v4);
    }

    default public Function1<T4, R> applyPartially(T1 v1, T2 v2, T3 v3) {
        return v4 -> this.apply(v1, v2, v3, v4);
    }

    default public Function0<R> applyPartially(T1 v1, T2 v2, T3 v3, T4 v4) {
        return () -> this.apply(v1, v2, v3, v4);
    }

    default public Function3<T2, T3, T4, R> applyPartially(Tuple1<? extends T1> args) {
        return (v2, v3, v4) -> this.apply(args.v1, v2, v3, v4);
    }

    default public Function2<T3, T4, R> applyPartially(Tuple2<? extends T1, ? extends T2> args) {
        return (v3, v4) -> this.apply(args.v1, args.v2, v3, v4);
    }

    default public Function1<T4, R> applyPartially(Tuple3<? extends T1, ? extends T2, ? extends T3> args) {
        return v4 -> this.apply(args.v1, args.v2, args.v3, v4);
    }

    default public Function0<R> applyPartially(Tuple4<? extends T1, ? extends T2, ? extends T3, ? extends T4> args) {
        return () -> this.apply(args.v1, args.v2, args.v3, args.v4);
    }

    @Deprecated
    default public Function3<T2, T3, T4, R> curry(T1 v1) {
        return (v2, v3, v4) -> this.apply(v1, v2, v3, v4);
    }

    @Deprecated
    default public Function2<T3, T4, R> curry(T1 v1, T2 v2) {
        return (v3, v4) -> this.apply(v1, v2, v3, v4);
    }

    @Deprecated
    default public Function1<T4, R> curry(T1 v1, T2 v2, T3 v3) {
        return v4 -> this.apply(v1, v2, v3, v4);
    }

    @Deprecated
    default public Function0<R> curry(T1 v1, T2 v2, T3 v3, T4 v4) {
        return () -> this.apply(v1, v2, v3, v4);
    }

    @Deprecated
    default public Function3<T2, T3, T4, R> curry(Tuple1<? extends T1> args) {
        return (v2, v3, v4) -> this.apply(args.v1, v2, v3, v4);
    }

    @Deprecated
    default public Function2<T3, T4, R> curry(Tuple2<? extends T1, ? extends T2> args) {
        return (v3, v4) -> this.apply(args.v1, args.v2, v3, v4);
    }

    @Deprecated
    default public Function1<T4, R> curry(Tuple3<? extends T1, ? extends T2, ? extends T3> args) {
        return v4 -> this.apply(args.v1, args.v2, args.v3, v4);
    }

    @Deprecated
    default public Function0<R> curry(Tuple4<? extends T1, ? extends T2, ? extends T3, ? extends T4> args) {
        return () -> this.apply(args.v1, args.v2, args.v3, args.v4);
    }
}

