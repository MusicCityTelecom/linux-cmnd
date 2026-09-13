/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.process.internal;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Function;
import org.glassfish.jersey.internal.util.collection.Ref;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.process.internal.AbstractChainableStage;
import org.glassfish.jersey.process.internal.ChainableStage;
import org.glassfish.jersey.process.internal.Inflecting;
import org.glassfish.jersey.process.internal.Stage;

public final class Stages {
    private static final ChainableStage IDENTITY = new AbstractChainableStage(){

        @Override
        public Stage.Continuation apply(Object o) {
            return Stage.Continuation.of(o, this.getDefaultNext());
        }
    };

    private Stages() {
    }

    public static <DATA> ChainableStage<DATA> identity() {
        return IDENTITY;
    }

    public static <DATA, RESULT> Stage<DATA> asStage(Inflector<DATA, RESULT> inflector) {
        return new InflectingStage<DATA, RESULT>(inflector);
    }

    public static <DATA, RESULT, T extends Inflector<DATA, RESULT>> T extractInflector(Object stage) {
        if (stage instanceof Inflecting) {
            return (T)((Inflecting)stage).inflector();
        }
        return null;
    }

    public static <DATA> Stage.Builder<DATA> chain(Function<DATA, DATA> transformation) {
        return new StageChainBuilder(transformation);
    }

    public static <DATA> Stage.Builder<DATA> chain(ChainableStage<DATA> rootStage) {
        return new StageChainBuilder(rootStage);
    }

    public static <DATA> DATA process(DATA data, Stage<DATA> rootStage) {
        Stage<DATA> currentStage;
        Stage.Continuation<DATA> continuation = Stage.Continuation.of(data, rootStage);
        while ((currentStage = continuation.next()) != null) {
            continuation = currentStage.apply(continuation.result());
        }
        return continuation.result();
    }

    public static <DATA, RESULT, T extends Inflector<DATA, RESULT>> DATA process(DATA data, Stage<DATA> rootStage, Ref<T> inflectorRef) {
        Stage<DATA> lastStage = rootStage;
        Stage.Continuation<DATA> continuation = Stage.Continuation.of(data, lastStage);
        while (continuation.next() != null) {
            lastStage = continuation.next();
            continuation = lastStage.apply(continuation.result());
        }
        inflectorRef.set(Stages.extractInflector(lastStage));
        return continuation.result();
    }

    public static class LinkedStage<DATA>
    implements Stage<DATA> {
        private final Stage<DATA> nextStage;
        private final Function<DATA, DATA> transformation;

        public LinkedStage(Function<DATA, DATA> transformation, Stage<DATA> nextStage) {
            this.nextStage = nextStage;
            this.transformation = transformation;
        }

        public LinkedStage(Function<DATA, DATA> transformation) {
            this(transformation, null);
        }

        @Override
        public Stage.Continuation<DATA> apply(DATA data) {
            return Stage.Continuation.of(this.transformation.apply(data), this.nextStage);
        }
    }

    private static class StageChainBuilder<DATA>
    implements Stage.Builder<DATA> {
        private final Deque<Function<DATA, DATA>> transformations = new LinkedList<Function<DATA, DATA>>();
        private Stage<DATA> rootStage;
        private ChainableStage<DATA> lastStage;

        private StageChainBuilder(Function<DATA, DATA> transformation) {
            this.transformations.push(transformation);
        }

        private StageChainBuilder(ChainableStage<DATA> rootStage) {
            this.rootStage = rootStage;
            this.lastStage = rootStage;
        }

        @Override
        public Stage.Builder<DATA> to(Function<DATA, DATA> transformation) {
            this.transformations.push(transformation);
            return this;
        }

        @Override
        public Stage.Builder<DATA> to(ChainableStage<DATA> stage) {
            this.addTailStage(stage);
            this.lastStage = stage;
            return this;
        }

        private void addTailStage(Stage<DATA> lastStage) {
            Stage<DATA> tail = lastStage;
            if (!this.transformations.isEmpty()) {
                tail = this.convertTransformations(lastStage);
            }
            if (this.rootStage != null) {
                this.lastStage.setDefaultNext(tail);
            } else {
                this.rootStage = tail;
            }
        }

        @Override
        public Stage<DATA> build(Stage<DATA> stage) {
            this.addTailStage(stage);
            return this.rootStage;
        }

        @Override
        public Stage<DATA> build() {
            return this.build(null);
        }

        private Stage<DATA> convertTransformations(Stage<DATA> successor) {
            Function<DATA, DATA> t;
            LinkedStage<DATA> stage = successor == null ? new LinkedStage<DATA>(this.transformations.poll()) : new LinkedStage<DATA>(this.transformations.poll(), successor);
            while ((t = this.transformations.poll()) != null) {
                stage = new LinkedStage<DATA>(t, stage);
            }
            return stage;
        }
    }

    private static class InflectingStage<DATA, RESULT>
    implements Stage<DATA>,
    Inflecting<DATA, RESULT> {
        private final Inflector<DATA, RESULT> inflector;

        public InflectingStage(Inflector<DATA, RESULT> inflector) {
            this.inflector = inflector;
        }

        @Override
        public Inflector<DATA, RESULT> inflector() {
            return this.inflector;
        }

        @Override
        public Stage.Continuation<DATA> apply(DATA request) {
            return Stage.Continuation.of(request);
        }
    }
}

