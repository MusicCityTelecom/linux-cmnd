/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.Transition;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;

public class SetTransition
extends Transition {
    @NotNull
    public final IntervalSet set;

    public SetTransition(@NotNull ATNState target, @Nullable IntervalSet set) {
        super(target);
        if (set == null) {
            set = IntervalSet.of(0);
        }
        this.set = set;
    }

    @Override
    public int getSerializationType() {
        return 7;
    }

    @Override
    @NotNull
    public IntervalSet label() {
        return this.set;
    }

    @Override
    public boolean matches(int symbol, int minVocabSymbol, int maxVocabSymbol) {
        return this.set.contains(symbol);
    }

    @NotNull
    public String toString() {
        return this.set.toString();
    }
}

