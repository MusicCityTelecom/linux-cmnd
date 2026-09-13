/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.function;

import org.jooq.lambda.function.Consumer0;
import org.jooq.lambda.function.Consumer1;
import org.jooq.lambda.function.Consumer2;
import org.jooq.lambda.tuple.Tuple1;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;

@FunctionalInterface
public interface Consumer3<T1, T2, T3> {
    default public void accept(Tuple3<? extends T1, ? extends T2, ? extends T3> args) {
        this.accept(args.v1, args.v2, args.v3);
    }

    public void accept(T1 var1, T2 var2, T3 var3);

    default public Consumer2<T2, T3> acceptPartially(T1 v1) {
        return (v2, v3) -> this.accept(v1, v2, v3);
    }

    default public Consumer1<T3> acceptPartially(T1 v1, T2 v2) {
        return v3 -> this.accept(v1, v2, v3);
    }

    default public Consumer0 acceptPartially(T1 v1, T2 v2, T3 v3) {
        return () -> this.accept(v1, v2, v3);
    }

    default public Consumer2<T2, T3> acceptPartially(Tuple1<? extends T1> args) {
        return (v2, v3) -> this.accept(args.v1, v2, v3);
    }

    default public Consumer1<T3> acceptPartially(Tuple2<? extends T1, ? extends T2> args) {
        return v3 -> this.accept(args.v1, args.v2, v3);
    }

    default public Consumer0 acceptPartially(Tuple3<? extends T1, ? extends T2, ? extends T3> args) {
        return () -> this.accept(args.v1, args.v2, args.v3);
    }
}

