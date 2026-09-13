/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.function;

import org.jooq.lambda.function.Consumer0;
import org.jooq.lambda.function.Consumer1;
import org.jooq.lambda.function.Consumer2;
import org.jooq.lambda.function.Consumer3;
import org.jooq.lambda.function.Consumer4;
import org.jooq.lambda.function.Consumer5;
import org.jooq.lambda.function.Consumer6;
import org.jooq.lambda.function.Consumer7;
import org.jooq.lambda.function.Consumer8;
import org.jooq.lambda.tuple.Tuple1;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;
import org.jooq.lambda.tuple.Tuple4;
import org.jooq.lambda.tuple.Tuple5;
import org.jooq.lambda.tuple.Tuple6;
import org.jooq.lambda.tuple.Tuple7;
import org.jooq.lambda.tuple.Tuple8;
import org.jooq.lambda.tuple.Tuple9;

@FunctionalInterface
public interface Consumer9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
    default public void accept(Tuple9<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5, ? extends T6, ? extends T7, ? extends T8, ? extends T9> args) {
        this.accept(args.v1, args.v2, args.v3, args.v4, args.v5, args.v6, args.v7, args.v8, args.v9);
    }

    public void accept(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9);

    default public Consumer8<T2, T3, T4, T5, T6, T7, T8, T9> acceptPartially(T1 v1) {
        return (v2, v3, v4, v5, v6, v7, v8, v9) -> this.accept(v1, v2, v3, v4, v5, v6, v7, v8, v9);
    }

    default public Consumer7<T3, T4, T5, T6, T7, T8, T9> acceptPartially(T1 v1, T2 v2) {
        return (v3, v4, v5, v6, v7, v8, v9) -> this.accept(v1, v2, v3, v4, v5, v6, v7, v8, v9);
    }

    default public Consumer6<T4, T5, T6, T7, T8, T9> acceptPartially(T1 v1, T2 v2, T3 v3) {
        return (v4, v5, v6, v7, v8, v9) -> this.accept(v1, v2, v3, v4, v5, v6, v7, v8, v9);
    }

    default public Consumer5<T5, T6, T7, T8, T9> acceptPartially(T1 v1, T2 v2, T3 v3, T4 v4) {
        return (v5, v6, v7, v8, v9) -> this.accept(v1, v2, v3, v4, v5, v6, v7, v8, v9);
    }

    default public Consumer4<T6, T7, T8, T9> acceptPartially(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5) {
        return (v6, v7, v8, v9) -> this.accept(v1, v2, v3, v4, v5, v6, v7, v8, v9);
    }

    default public Consumer3<T7, T8, T9> acceptPartially(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5, T6 v6) {
        return (v7, v8, v9) -> this.accept(v1, v2, v3, v4, v5, v6, v7, v8, v9);
    }

    default public Consumer2<T8, T9> acceptPartially(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5, T6 v6, T7 v7) {
        return (v8, v9) -> this.accept(v1, v2, v3, v4, v5, v6, v7, v8, v9);
    }

    default public Consumer1<T9> acceptPartially(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5, T6 v6, T7 v7, T8 v8) {
        return v9 -> this.accept(v1, v2, v3, v4, v5, v6, v7, v8, v9);
    }

    default public Consumer0 acceptPartially(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5, T6 v6, T7 v7, T8 v8, T9 v9) {
        return () -> this.accept(v1, v2, v3, v4, v5, v6, v7, v8, v9);
    }

    default public Consumer8<T2, T3, T4, T5, T6, T7, T8, T9> acceptPartially(Tuple1<? extends T1> args) {
        return (v2, v3, v4, v5, v6, v7, v8, v9) -> this.accept(args.v1, v2, v3, v4, v5, v6, v7, v8, v9);
    }

    default public Consumer7<T3, T4, T5, T6, T7, T8, T9> acceptPartially(Tuple2<? extends T1, ? extends T2> args) {
        return (v3, v4, v5, v6, v7, v8, v9) -> this.accept(args.v1, args.v2, v3, v4, v5, v6, v7, v8, v9);
    }

    default public Consumer6<T4, T5, T6, T7, T8, T9> acceptPartially(Tuple3<? extends T1, ? extends T2, ? extends T3> args) {
        return (v4, v5, v6, v7, v8, v9) -> this.accept(args.v1, args.v2, args.v3, v4, v5, v6, v7, v8, v9);
    }

    default public Consumer5<T5, T6, T7, T8, T9> acceptPartially(Tuple4<? extends T1, ? extends T2, ? extends T3, ? extends T4> args) {
        return (v5, v6, v7, v8, v9) -> this.accept(args.v1, args.v2, args.v3, args.v4, v5, v6, v7, v8, v9);
    }

    default public Consumer4<T6, T7, T8, T9> acceptPartially(Tuple5<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5> args) {
        return (v6, v7, v8, v9) -> this.accept(args.v1, args.v2, args.v3, args.v4, args.v5, v6, v7, v8, v9);
    }

    default public Consumer3<T7, T8, T9> acceptPartially(Tuple6<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5, ? extends T6> args) {
        return (v7, v8, v9) -> this.accept(args.v1, args.v2, args.v3, args.v4, args.v5, args.v6, v7, v8, v9);
    }

    default public Consumer2<T8, T9> acceptPartially(Tuple7<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5, ? extends T6, ? extends T7> args) {
        return (v8, v9) -> this.accept(args.v1, args.v2, args.v3, args.v4, args.v5, args.v6, args.v7, v8, v9);
    }

    default public Consumer1<T9> acceptPartially(Tuple8<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5, ? extends T6, ? extends T7, ? extends T8> args) {
        return v9 -> this.accept(args.v1, args.v2, args.v3, args.v4, args.v5, args.v6, args.v7, args.v8, v9);
    }

    default public Consumer0 acceptPartially(Tuple9<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5, ? extends T6, ? extends T7, ? extends T8, ? extends T9> args) {
        return () -> this.accept(args.v1, args.v2, args.v3, args.v4, args.v5, args.v6, args.v7, args.v8, args.v9);
    }
}

