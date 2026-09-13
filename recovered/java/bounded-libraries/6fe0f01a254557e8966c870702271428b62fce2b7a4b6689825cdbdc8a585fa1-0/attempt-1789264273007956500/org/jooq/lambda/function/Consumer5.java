/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.function;

import org.jooq.lambda.function.Consumer0;
import org.jooq.lambda.function.Consumer1;
import org.jooq.lambda.function.Consumer2;
import org.jooq.lambda.function.Consumer3;
import org.jooq.lambda.function.Consumer4;
import org.jooq.lambda.tuple.Tuple1;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;
import org.jooq.lambda.tuple.Tuple4;
import org.jooq.lambda.tuple.Tuple5;

@FunctionalInterface
public interface Consumer5<T1, T2, T3, T4, T5> {
    default public void accept(Tuple5<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5> args) {
        this.accept(args.v1, args.v2, args.v3, args.v4, args.v5);
    }

    public void accept(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

    default public Consumer4<T2, T3, T4, T5> acceptPartially(T1 v1) {
        return (v2, v3, v4, v5) -> this.accept(v1, v2, v3, v4, v5);
    }

    default public Consumer3<T3, T4, T5> acceptPartially(T1 v1, T2 v2) {
        return (v3, v4, v5) -> this.accept(v1, v2, v3, v4, v5);
    }

    default public Consumer2<T4, T5> acceptPartially(T1 v1, T2 v2, T3 v3) {
        return (v4, v5) -> this.accept(v1, v2, v3, v4, v5);
    }

    default public Consumer1<T5> acceptPartially(T1 v1, T2 v2, T3 v3, T4 v4) {
        return v5 -> this.accept(v1, v2, v3, v4, v5);
    }

    default public Consumer0 acceptPartially(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5) {
        return () -> this.accept(v1, v2, v3, v4, v5);
    }

    default public Consumer4<T2, T3, T4, T5> acceptPartially(Tuple1<? extends T1> args) {
        return (v2, v3, v4, v5) -> this.accept(args.v1, v2, v3, v4, v5);
    }

    default public Consumer3<T3, T4, T5> acceptPartially(Tuple2<? extends T1, ? extends T2> args) {
        return (v3, v4, v5) -> this.accept(args.v1, args.v2, v3, v4, v5);
    }

    default public Consumer2<T4, T5> acceptPartially(Tuple3<? extends T1, ? extends T2, ? extends T3> args) {
        return (v4, v5) -> this.accept(args.v1, args.v2, args.v3, v4, v5);
    }

    default public Consumer1<T5> acceptPartially(Tuple4<? extends T1, ? extends T2, ? extends T3, ? extends T4> args) {
        return v5 -> this.accept(args.v1, args.v2, args.v3, args.v4, v5);
    }

    default public Consumer0 acceptPartially(Tuple5<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5> args) {
        return () -> this.accept(args.v1, args.v2, args.v3, args.v4, args.v5);
    }
}

