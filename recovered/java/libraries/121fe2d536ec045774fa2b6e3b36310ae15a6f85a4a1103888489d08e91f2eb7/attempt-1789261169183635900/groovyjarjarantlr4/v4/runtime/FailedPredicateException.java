/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.Parser;
import groovyjarjarantlr4.v4.runtime.RecognitionException;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.AbstractPredicateTransition;
import groovyjarjarantlr4.v4.runtime.atn.ParserATNSimulator;
import groovyjarjarantlr4.v4.runtime.atn.PredicateTransition;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import java.util.Locale;

public class FailedPredicateException
extends RecognitionException {
    private static final long serialVersionUID = 5379330841495778709L;
    private final int ruleIndex;
    private final int predicateIndex;
    private final String predicate;

    public FailedPredicateException(@NotNull Parser recognizer) {
        this(recognizer, null);
    }

    public FailedPredicateException(@NotNull Parser recognizer, @Nullable String predicate) {
        this(recognizer, predicate, null);
    }

    public FailedPredicateException(@NotNull Parser recognizer, @Nullable String predicate, @Nullable String message) {
        super(FailedPredicateException.formatMessage(predicate, message), recognizer, recognizer.getInputStream(), recognizer._ctx);
        ATNState s = ((ParserATNSimulator)recognizer.getInterpreter()).atn.states.get(recognizer.getState());
        AbstractPredicateTransition trans = (AbstractPredicateTransition)s.transition(0);
        if (trans instanceof PredicateTransition) {
            this.ruleIndex = ((PredicateTransition)trans).ruleIndex;
            this.predicateIndex = ((PredicateTransition)trans).predIndex;
        } else {
            this.ruleIndex = 0;
            this.predicateIndex = 0;
        }
        this.predicate = predicate;
        this.setOffendingToken(recognizer, recognizer.getCurrentToken());
    }

    public int getRuleIndex() {
        return this.ruleIndex;
    }

    public int getPredIndex() {
        return this.predicateIndex;
    }

    @Nullable
    public String getPredicate() {
        return this.predicate;
    }

    @NotNull
    private static String formatMessage(@Nullable String predicate, @Nullable String message) {
        if (message != null) {
            return message;
        }
        return String.format(Locale.getDefault(), "failed predicate: {%s}?", predicate);
    }
}

