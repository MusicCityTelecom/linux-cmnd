/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.function;

import java.util.function.Consumer;
import org.jooq.lambda.function.Consumer0;
import org.jooq.lambda.tuple.Tuple1;

@FunctionalInterface
public interface Consumer1<T1>
extends Consumer<T1> {
    @Override
    default public void accept(Tuple1<? extends T1> args) {
        this.accept((T1)args.v1);
    }

    @Override
    public void accept(T1 var1);

    default public Consumer<T1> toConsumer() {
        return this::accept;
    }

    public static <T1> Consumer1<T1> from(Consumer<? super T1> consumer) {
        return consumer::accept;
    }

    default public Consumer0 acceptPartially(T1 v1) {
        return () -> this.accept(v1);
    }

    default public Consumer0 acceptPartially(Tuple1<? extends T1> args) {
        return () -> this.accept((T1)args.v1);
    }
}

