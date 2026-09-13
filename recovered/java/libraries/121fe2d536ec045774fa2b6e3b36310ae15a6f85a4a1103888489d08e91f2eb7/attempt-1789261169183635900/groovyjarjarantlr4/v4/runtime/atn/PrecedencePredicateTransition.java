/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.AbstractPredicateTransition;
import groovyjarjarantlr4.v4.runtime.atn.SemanticContext;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public final class PrecedencePredicateTransition
extends AbstractPredicateTransition {
    public final int precedence;

    public PrecedencePredicateTransition(@NotNull ATNState target, int precedence) {
        super(target);
        this.precedence = precedence;
    }

    @Override
    public int getSerializationType() {
        return 10;
    }

    @Override
    public boolean isEpsilon() {
        return true;
    }

    @Override
    public boolean matches(int symbol, int minVocabSymbol, int maxVocabSymbol) {
        return false;
    }

    public SemanticContext.PrecedencePredicate getPredicate() {
        return new SemanticContext.PrecedencePredicate(this.precedence);
    }

    public String toString() {
        return this.precedence + " >= _p";
    }
}

