/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.Recognizer;
import groovyjarjarantlr4.v4.runtime.atn.PredictionContext;
import groovyjarjarantlr4.v4.runtime.atn.PredictionContextCache;

public final class EmptyPredictionContext
extends PredictionContext {
    public static final EmptyPredictionContext LOCAL_CONTEXT = new EmptyPredictionContext(false);
    public static final EmptyPredictionContext FULL_CONTEXT = new EmptyPredictionContext(true);
    private final boolean fullContext;

    private EmptyPredictionContext(boolean fullContext) {
        super(EmptyPredictionContext.calculateEmptyHashCode());
        this.fullContext = fullContext;
    }

    public boolean isFullContext() {
        return this.fullContext;
    }

    @Override
    protected PredictionContext addEmptyContext() {
        return this;
    }

    @Override
    protected PredictionContext removeEmptyContext() {
        throw new UnsupportedOperationException("Cannot remove the empty context from itself.");
    }

    @Override
    public PredictionContext getParent(int index) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int getReturnState(int index) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int findReturnState(int returnState) {
        return -1;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public PredictionContext appendContext(int returnContext, PredictionContextCache contextCache) {
        return contextCache.getChild(this, returnContext);
    }

    @Override
    public PredictionContext appendContext(PredictionContext suffix, PredictionContextCache contextCache) {
        return suffix;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean hasEmpty() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public String[] toStrings(Recognizer<?, ?> recognizer, int currentState) {
        return new String[]{"[]"};
    }

    @Override
    public String[] toStrings(Recognizer<?, ?> recognizer, PredictionContext stop, int currentState) {
        return new String[]{"[]"};
    }
}

