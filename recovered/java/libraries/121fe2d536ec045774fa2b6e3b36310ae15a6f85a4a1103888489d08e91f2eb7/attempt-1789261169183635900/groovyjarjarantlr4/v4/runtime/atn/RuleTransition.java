/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.RuleStartState;
import groovyjarjarantlr4.v4.runtime.atn.Transition;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public final class RuleTransition
extends Transition {
    public final int ruleIndex;
    public final int precedence;
    @NotNull
    public ATNState followState;
    public boolean tailCall;
    public boolean optimizedTailCall;

    @Deprecated
    public RuleTransition(@NotNull RuleStartState ruleStart, int ruleIndex, @NotNull ATNState followState) {
        this(ruleStart, ruleIndex, 0, followState);
    }

    public RuleTransition(@NotNull RuleStartState ruleStart, int ruleIndex, int precedence, @NotNull ATNState followState) {
        super(ruleStart);
        this.ruleIndex = ruleIndex;
        this.precedence = precedence;
        this.followState = followState;
    }

    @Override
    public int getSerializationType() {
        return 3;
    }

    @Override
    public boolean isEpsilon() {
        return true;
    }

    @Override
    public boolean matches(int symbol, int minVocabSymbol, int maxVocabSymbol) {
        return false;
    }
}

