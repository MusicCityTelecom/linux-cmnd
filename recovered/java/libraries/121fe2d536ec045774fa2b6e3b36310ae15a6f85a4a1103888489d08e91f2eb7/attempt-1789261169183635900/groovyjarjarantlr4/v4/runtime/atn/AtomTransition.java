/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.Transition;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public final class AtomTransition
extends Transition {
    public final int label;

    public AtomTransition(@NotNull ATNState target, int label) {
        super(target);
        this.label = label;
    }

    @Override
    public int getSerializationType() {
        return 5;
    }

    @Override
    @NotNull
    public IntervalSet label() {
        return IntervalSet.of(this.label);
    }

    @Override
    public boolean matches(int symbol, int minVocabSymbol, int maxVocabSymbol) {
        return this.label == symbol;
    }

    @NotNull
    public String toString() {
        return String.valueOf(this.label);
    }
}

