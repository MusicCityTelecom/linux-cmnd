/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.Transition;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public final class EpsilonTransition
extends Transition {
    private final int outermostPrecedenceReturn;

    public EpsilonTransition(@NotNull ATNState target) {
        this(target, -1);
    }

    public EpsilonTransition(@NotNull ATNState target, int outermostPrecedenceReturn) {
        super(target);
        this.outermostPrecedenceReturn = outermostPrecedenceReturn;
    }

    public int outermostPrecedenceReturn() {
        return this.outermostPrecedenceReturn;
    }

    @Override
    public int getSerializationType() {
        return 1;
    }

    @Override
    public boolean isEpsilon() {
        return true;
    }

    @Override
    public boolean matches(int symbol, int minVocabSymbol, int maxVocabSymbol) {
        return false;
    }

    @NotNull
    public String toString() {
        return "epsilon";
    }
}

