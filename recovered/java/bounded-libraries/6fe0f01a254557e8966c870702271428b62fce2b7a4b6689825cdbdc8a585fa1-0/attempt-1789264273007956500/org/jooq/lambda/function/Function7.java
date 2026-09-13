/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.function;

import org.jooq.lambda.function.Function0;
import org.jooq.lambda.function.Function1;
import org.jooq.lambda.function.Function2;
import org.jooq.lambda.function.Function3;
import org.jooq.lambda.function.Function4;
import org.jooq.lambda.function.Function5;
import org.jooq.lambda.function.Function6;
import org.jooq.lambda.tuple.Tuple1;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;
import org.jooq.lambda.tuple.Tuple4;
import org.jooq.lambda.tuple.Tuple5;
import org.jooq.lambda.tuple.Tuple6;
import org.jooq.lambda.tuple.Tuple7;

@FunctionalInterface
public interface Function7<T1, T2, T3, T4, T5, T6, T7, R> {
    default public R apply(Tuple7<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5, ? extends T6, ? extends T7> args) {
        return this.apply(args.v1, args.v2, args.v3, args.v4, args.v5, args.v6, args.v7);
    }

    public R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7);

    default public Function6<T2, T3, T4, T5, T6, T7, R> applyPartially(T1 v1) {
        return (v2, v3, v4, v5, v6, v7) -> this.apply(v1, v2, v3, v4, v5, v6, v7);
    }

    default public Function5<T3, T4, T5, T6, T7, R> applyPartially(T1 v1, T2 v2) {
        return (v3, v4, v5, v6, v7) -> this.apply(v1, v2, v3, v4, v5, v6, v7);
    }

    default public Function4<T4, T5, T6, T7, R> applyPartially(T1 v1, T2 v2, T3 v3) {
        return (v4, v5, v6, v7) -> this.apply(v1, v2, v3, v4, v5, v6, v7);
    }

    default public Function3<T5, T6, T7, R> applyPartially(T1 v1, T2 v2, T3 v3, T4 v4) {
        return (v5, v6, v7) -> this.apply(v1, v2, v3, v4, v5, v6, v7);
    }

    default public Function2<T6, T7, R> applyPartially(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5) {
        return (v6, v7) -> this.apply(v1, v2, v3, v4, v5, v6, v7);
    }

    default public Function1<T7, R> applyPartially(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5, T6 v6) {
        return v7 -> this.apply(v1, v2, v3, v4, v5, v6, v7);
    }

    default public Function0<R> applyPartially(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5, T6 v6, T7 v7) {
        return () -> this.apply(v1, v2, v3, v4, v5, v6, v7);
    }

    default public Function6<T2, T3, T4, T5, T6, T7, R> applyPartially(Tuple1<? extends T1> args) {
        return (v2, v3, v4, v5, v6, v7) -> this.apply(args.v1, v2, v3, v4, v5, v6, v7);
    }

    default public Function5<T3, T4, T5, T6, T7, R> applyPartially(Tuple2<? extends T1, ? extends T2> args) {
        return (v3, v4, v5, v6, v7) -> this.apply(args.v1, args.v2, v3, v4, v5, v6, v7);
    }

    default public Function4<T4, T5, T6, T7, R> applyPartially(Tuple3<? extends T1, ? extends T2, ? extends T3> args) {
        return (v4, v5, v6, v7) -> this.apply(args.v1, args.v2, args.v3, v4, v5, v6, v7);
    }

    default public Function3<T5, T6, T7, R> applyPartially(Tuple4<? extends T1, ? extends T2, ? extends T3, ? extends T4> args) {
        return (v5, v6, v7) -> this.apply(args.v1, args.v2, args.v3, args.v4, v5, v6, v7);
    }

    default public Function2<T6, T7, R> applyPartially(Tuple5<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5> args) {
        return (v6, v7) -> this.apply(args.v1, args.v2, args.v3, args.v4, args.v5, v6, v7);
    }

    default public Function1<T7, R> applyPartially(Tuple6<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5, ? extends T6> args) {
        return v7 -> this.apply(args.v1, args.v2, args.v3, args.v4, args.v5, args.v6, v7);
    }

    default public Function0<R> applyPartially(Tuple7<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5, ? extends T6, ? extends T7> args) {
        return () -> this.apply(args.v1, args.v2, args.v3, args.v4, args.v5, args.v6, args.v7);
    }

    @Deprecated
    default public Function6<T2, T3, T4, T5, T6, T7, R> curry(T1 v1) {
        return (v2, v3, v4, v5, v6, v7) -> this.apply(v1, v2, v3, v4, v5, v6, v7);
    }

    @Deprecated
    default public Function5<T3, T4, T5, T6, T7, R> curry(T1 v1, T2 v2) {
        return (v3, v4, v5, v6, v7) -> this.apply(v1, v2, v3, v4, v5, v6, v7);
    }

    @Deprecated
    default public Function4<T4, T5, T6, T7, R> curry(T1 v1, T2 v2, T3 v3) {
        return (v4, v5, v6, v7) -> this.apply(v1, v2, v3, v4, v5, v6, v7);
    }

    @Deprecated
    default public Function3<T5, T6, T7, R> curry(T1 v1, T2 v2, T3 v3, T4 v4) {
        return (v5, v6, v7) -> this.apply(v1, v2, v3, v4, v5, v6, v7);
    }

    @Deprecated
    default public Function2<T6, T7, R> curry(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5) {
        return (v6, v7) -> this.apply(v1, v2, v3, v4, v5, v6, v7);
    }

    @Deprecated
    default public Function1<T7, R> curry(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5, T6 v6) {
        return v7 -> this.apply(v1, v2, v3, v4, v5, v6, v7);
    }

    @Deprecated
    default public Function0<R> curry(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5, T6 v6, T7 v7) {
        return () -> this.apply(v1, v2, v3, v4, v5, v6, v7);
    }

    @Deprecated
    default public Function6<T2, T3, T4, T5, T6, T7, R> curry(Tuple1<? extends T1> args) {
        return (v2, v3, v4, v5, v6, v7) -> this.apply(args.v1, v2, v3, v4, v5, v6, v7);
    }

    @Deprecated
    default public Function5<T3, T4, T5, T6, T7, R> curry(Tuple2<? extends T1, ? extends T2> args) {
        return (v3, v4, v5, v6, v7) -> this.apply(args.v1, args.v2, v3, v4, v5, v6, v7);
    }

    @Deprecated
    default public Function4<T4, T5, T6, T7, R> curry(Tuple3<? extends T1, ? extends T2, ? extends T3> args) {
        return (v4, v5, v6, v7) -> this.apply(args.v1, args.v2, args.v3, v4, v5, v6, v7);
    }

    @Deprecated
    default public Function3<T5, T6, T7, R> curry(Tuple4<? extends T1, ? extends T2, ? extends T3, ? extends T4> args) {
        return (v5, v6, v7) -> this.apply(args.v1, args.v2, args.v3, args.v4, v5, v6, v7);
    }

    @Deprecated
    default public Function2<T6, T7, R> curry(Tuple5<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5> args) {
        return (v6, v7) -> this.apply(args.v1, args.v2, args.v3, args.v4, args.v5, v6, v7);
    }

    @Deprecated
    default public Function1<T7, R> curry(Tuple6<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5, ? extends T6> args) {
        return v7 -> this.apply(args.v1, args.v2, args.v3, args.v4, args.v5, args.v6, v7);
    }

    @Deprecated
    default public Function0<R> curry(Tuple7<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5, ? extends T6, ? extends T7> args) {
        return () -> this.apply(args.v1, args.v2, args.v3, args.v4, args.v5, args.v6, args.v7);
    }
}

