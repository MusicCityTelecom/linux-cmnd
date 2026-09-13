/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.process.internal;

import java.util.function.Function;
import org.glassfish.jersey.process.internal.ChainableStage;

public interface Stage<DATA> {
    public Continuation<DATA> apply(DATA var1);

    public static interface Builder<DATA> {
        public Builder<DATA> to(Function<DATA, DATA> var1);

        public Builder<DATA> to(ChainableStage<DATA> var1);

        public Stage<DATA> build();

        public Stage<DATA> build(Stage<DATA> var1);
    }

    public static final class Continuation<DATA> {
        private final DATA result;
        private final Stage<DATA> next;

        Continuation(DATA result, Stage<DATA> next) {
            this.result = result;
            this.next = next;
        }

        public static <DATA> Continuation<DATA> of(DATA result, Stage<DATA> next) {
            return new Continuation<DATA>(result, next);
        }

        public static <DATA> Continuation<DATA> of(DATA result) {
            return new Continuation<DATA>(result, null);
        }

        public DATA result() {
            return this.result;
        }

        public Stage<DATA> next() {
            return this.next;
        }

        public boolean hasNext() {
            return this.next != null;
        }
    }
}

