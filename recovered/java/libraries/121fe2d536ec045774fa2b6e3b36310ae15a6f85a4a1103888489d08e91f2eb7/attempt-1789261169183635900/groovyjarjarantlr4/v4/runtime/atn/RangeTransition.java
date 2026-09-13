/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.Transition;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public final class RangeTransition
extends Transition {
    public final int from;
    public final int to;

    public RangeTransition(@NotNull ATNState target, int from, int to) {
        super(target);
        this.from = from;
        this.to = to;
    }

    @Override
    public int getSerializationType() {
        return 2;
    }

    @Override
    @NotNull
    public IntervalSet label() {
        return IntervalSet.of(this.from, this.to);
    }

    @Override
    public boolean matches(int symbol, int minVocabSymbol, int maxVocabSymbol) {
        return symbol >= this.from && symbol <= this.to;
    }

    @NotNull
    public String toString() {
        return new StringBuilder("'").appendCodePoint(this.from).append("'..'").appendCodePoint(this.to).append("'").toString();
    }
}

