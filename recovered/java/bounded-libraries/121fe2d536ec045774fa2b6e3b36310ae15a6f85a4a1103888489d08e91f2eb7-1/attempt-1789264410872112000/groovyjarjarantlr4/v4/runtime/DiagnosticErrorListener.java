/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.BaseErrorListener;
import groovyjarjarantlr4.v4.runtime.Parser;
import groovyjarjarantlr4.v4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.atn.ATNConfig;
import groovyjarjarantlr4.v4.runtime.atn.ATNConfigSet;
import groovyjarjarantlr4.v4.runtime.atn.SimulatorState;
import groovyjarjarantlr4.v4.runtime.dfa.DFA;
import groovyjarjarantlr4.v4.runtime.misc.Interval;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import java.util.BitSet;

public class DiagnosticErrorListener
extends BaseErrorListener {
    protected final boolean exactOnly;

    public DiagnosticErrorListener() {
        this(true);
    }

    public DiagnosticErrorListener(boolean exactOnly) {
        this.exactOnly = exactOnly;
    }

    @Override
    public void reportAmbiguity(@NotNull Parser recognizer, @NotNull DFA dfa, int startIndex, int stopIndex, boolean exact, @Nullable BitSet ambigAlts, @NotNull ATNConfigSet configs) {
        if (this.exactOnly && !exact) {
            return;
        }
        String format = "reportAmbiguity d=%s: ambigAlts=%s, input='%s'";
        String decision = this.getDecisionDescription(recognizer, dfa);
        BitSet conflictingAlts = this.getConflictingAlts(ambigAlts, configs);
        String text = recognizer.getInputStream().getText(Interval.of(startIndex, stopIndex));
        String message = String.format(format, decision, conflictingAlts, text);
        recognizer.notifyErrorListeners(message);
    }

    @Override
    public void reportAttemptingFullContext(@NotNull Parser recognizer, @NotNull DFA dfa, int startIndex, int stopIndex, @Nullable BitSet conflictingAlts, @NotNull SimulatorState conflictState) {
        String format = "reportAttemptingFullContext d=%s, input='%s'";
        String decision = this.getDecisionDescription(recognizer, dfa);
        String text = recognizer.getInputStream().getText(Interval.of(startIndex, stopIndex));
        String message = String.format(format, decision, text);
        recognizer.notifyErrorListeners(message);
    }

    @Override
    public void reportContextSensitivity(@NotNull Parser recognizer, @NotNull DFA dfa, int startIndex, int stopIndex, int prediction, @NotNull SimulatorState acceptState) {
        String format = "reportContextSensitivity d=%s, input='%s'";
        String decision = this.getDecisionDescription(recognizer, dfa);
        String text = recognizer.getInputStream().getText(Interval.of(startIndex, stopIndex));
        String message = String.format(format, decision, text);
        recognizer.notifyErrorListeners(message);
    }

    protected <T extends Token> String getDecisionDescription(@NotNull Parser recognizer, @NotNull DFA dfa) {
        int decision = dfa.decision;
        int ruleIndex = dfa.atnStartState.ruleIndex;
        String[] ruleNames = recognizer.getRuleNames();
        if (ruleIndex < 0 || ruleIndex >= ruleNames.length) {
            return String.valueOf(decision);
        }
        String ruleName = ruleNames[ruleIndex];
        if (ruleName == null || ruleName.isEmpty()) {
            return String.valueOf(decision);
        }
        return String.format("%d (%s)", decision, ruleName);
    }

    @NotNull
    protected BitSet getConflictingAlts(@Nullable BitSet reportedAlts, @NotNull ATNConfigSet configs) {
        if (reportedAlts != null) {
            return reportedAlts;
        }
        BitSet result = new BitSet();
        for (ATNConfig config : configs) {
            result.set(config.getAlt());
        }
        return result;
    }
}

