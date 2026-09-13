/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.Transition;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public final class WildcardTransition
extends Transition {
    public WildcardTransition(@NotNull ATNState target) {
        super(target);
    }

    @Override
    public int getSerializationType() {
        return 9;
    }

    @Override
    public boolean matches(int symbol, int minVocabSymbol, int maxVocabSymbol) {
        return symbol >= minVocabSymbol && symbol <= maxVocabSymbol;
    }

    @NotNull
    public String toString() {
        return ".";
    }
}

