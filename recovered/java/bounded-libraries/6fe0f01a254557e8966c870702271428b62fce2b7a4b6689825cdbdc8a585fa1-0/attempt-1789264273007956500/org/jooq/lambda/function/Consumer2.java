/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.function;

import java.util.function.BiConsumer;
import org.jooq.lambda.function.Consumer0;
import org.jooq.lambda.function.Consumer1;
import org.jooq.lambda.tuple.Tuple1;
import org.jooq.lambda.tuple.Tuple2;

@FunctionalInterface
public interface Consumer2<T1, T2>
extends BiConsumer<T1, T2> {
    default public void accept(Tuple2<? extends T1, ? extends T2> args) {
        this.accept((T1)args.v1, (T2)args.v2);
    }

    @Override
    public void accept(T1 var1, T2 var2);

    default public BiConsumer<T1, T2> toBiConsumer() {
        return this::accept;
    }

    public static <T1, T2> Consumer2<T1, T2> from(BiConsumer<? super T1, ? super T2> consumer) {
        return consumer::accept;
    }

    default public Consumer1<T2> acceptPartially(T1 v1) {
        return v2 -> this.accept(v1, (T2)v2);
    }

    default public Consumer0 acceptPartially(T1 v1, T2 v2) {
        return () -> this.accept(v1, v2);
    }

    default public Consumer1<T2> acceptPartially(Tuple1<? extends T1> args) {
        return v2 -> this.accept((T1)args.v1, (T2)v2);
    }

    default public Consumer0 acceptPartially(Tuple2<? extends T1, ? extends T2> args) {
        return () -> this.accept((T1)args.v1, (T2)args.v2);
    }
}

