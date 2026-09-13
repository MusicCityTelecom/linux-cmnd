/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.atn.ArrayPredictionContext;
import groovyjarjarantlr4.v4.runtime.atn.PredictionContext;
import groovyjarjarantlr4.v4.runtime.atn.PredictionContextCache;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public class SingletonPredictionContext
extends PredictionContext {
    @NotNull
    public final PredictionContext parent;
    public final int returnState;

    SingletonPredictionContext(@NotNull PredictionContext parent, int returnState) {
        super(SingletonPredictionContext.calculateHashCode(parent, returnState));
        assert (returnState != Integer.MAX_VALUE && returnState != Integer.MIN_VALUE);
        this.parent = parent;
        this.returnState = returnState;
    }

    @Override
    public PredictionContext getParent(int index) {
        assert (index == 0);
        return this.parent;
    }

    @Override
    public int getReturnState(int index) {
        assert (index == 0);
        return this.returnState;
    }

    @Override
    public int findReturnState(int returnState) {
        return this.returnState == returnState ? 0 : -1;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean hasEmpty() {
        return false;
    }

    @Override
    public PredictionContext appendContext(PredictionContext suffix, PredictionContextCache contextCache) {
        return contextCache.getChild(this.parent.appendContext(suffix, contextCache), this.returnState);
    }

    @Override
    protected PredictionContext addEmptyContext() {
        PredictionContext[] parents = new PredictionContext[]{this.parent, EMPTY_FULL};
        int[] returnStates = new int[]{this.returnState, Integer.MAX_VALUE};
        return new ArrayPredictionContext(parents, returnStates);
    }

    @Override
    protected PredictionContext removeEmptyContext() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SingletonPredictionContext)) {
            return false;
        }
        SingletonPredictionContext other = (SingletonPredictionContext)o;
        if (this.hashCode() != other.hashCode()) {
            return false;
        }
        return this.returnState == other.returnState && this.parent.equals(other.parent);
    }
}

