/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.dfa;

import groovyjarjarantlr4.v4.runtime.Recognizer;
import groovyjarjarantlr4.v4.runtime.Vocabulary;
import groovyjarjarantlr4.v4.runtime.VocabularyImpl;
import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNConfig;
import groovyjarjarantlr4.v4.runtime.atn.ATNSimulator;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.dfa.DFA;
import groovyjarjarantlr4.v4.runtime.dfa.DFAState;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class DFASerializer {
    @NotNull
    private final DFA dfa;
    @NotNull
    private final Vocabulary vocabulary;
    @Nullable
    final String[] ruleNames;
    @Nullable
    final ATN atn;

    @Deprecated
    public DFASerializer(@NotNull DFA dfa, @Nullable String[] tokenNames) {
        this(dfa, VocabularyImpl.fromTokenNames(tokenNames), null, null);
    }

    public DFASerializer(@NotNull DFA dfa, @NotNull Vocabulary vocabulary) {
        this(dfa, vocabulary, null, null);
    }

    public DFASerializer(@NotNull DFA dfa, @Nullable Recognizer<?, ?> parser) {
        this(dfa, parser != null ? parser.getVocabulary() : VocabularyImpl.EMPTY_VOCABULARY, parser != null ? parser.getRuleNames() : null, parser != null ? parser.getATN() : null);
    }

    @Deprecated
    public DFASerializer(@NotNull DFA dfa, @Nullable String[] tokenNames, @Nullable String[] ruleNames, @Nullable ATN atn) {
        this(dfa, VocabularyImpl.fromTokenNames(tokenNames), ruleNames, atn);
    }

    public DFASerializer(@NotNull DFA dfa, @NotNull Vocabulary vocabulary, @Nullable String[] ruleNames, @Nullable ATN atn) {
        this.dfa = dfa;
        this.vocabulary = vocabulary;
        this.ruleNames = ruleNames;
        this.atn = atn;
    }

    public String toString() {
        String output;
        if (this.dfa.s0.get() == null) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        if (this.dfa.states != null) {
            ArrayList states = new ArrayList(this.dfa.states.values());
            Collections.sort(states, new Comparator<DFAState>(){

                @Override
                public int compare(DFAState o1, DFAState o2) {
                    return o1.stateNumber - o2.stateNumber;
                }
            });
            for (DFAState s : states) {
                Map<Integer, DFAState> edges = s.getEdgeMap();
                Map<Integer, DFAState> contextEdges = s.getContextEdgeMap();
                for (Map.Entry<Integer, DFAState> entry : edges.entrySet()) {
                    DFAState t;
                    if ((entry.getValue() == null || entry.getValue() == ATNSimulator.ERROR) && !s.isContextSymbol(entry.getKey())) continue;
                    boolean contextSymbol = false;
                    buf.append(this.getStateString(s)).append("-").append(this.getEdgeLabel(entry.getKey())).append("->");
                    if (s.isContextSymbol(entry.getKey())) {
                        buf.append("!");
                        contextSymbol = true;
                    }
                    if ((t = entry.getValue()) != null && t.stateNumber != Integer.MAX_VALUE) {
                        buf.append(this.getStateString(t)).append('\n');
                        continue;
                    }
                    if (!contextSymbol) continue;
                    buf.append("ctx\n");
                }
                if (!s.isContextSensitive()) continue;
                for (Map.Entry<Integer, DFAState> entry : contextEdges.entrySet()) {
                    buf.append(this.getStateString(s)).append("-").append(this.getContextLabel(entry.getKey())).append("->").append(this.getStateString(entry.getValue())).append("\n");
                }
            }
        }
        if ((output = buf.toString()).length() == 0) {
            return null;
        }
        return output;
    }

    protected String getContextLabel(int i) {
        if (i == Integer.MAX_VALUE) {
            return "ctx:EMPTY_FULL";
        }
        if (i == Integer.MIN_VALUE) {
            return "ctx:EMPTY_LOCAL";
        }
        if (this.atn != null && i > 0 && i <= this.atn.states.size()) {
            ATNState state = this.atn.states.get(i);
            int ruleIndex = state.ruleIndex;
            if (this.ruleNames != null && ruleIndex >= 0 && ruleIndex < this.ruleNames.length) {
                return "ctx:" + String.valueOf(i) + "(" + this.ruleNames[ruleIndex] + ")";
            }
        }
        return "ctx:" + String.valueOf(i);
    }

    protected String getEdgeLabel(int i) {
        return this.vocabulary.getDisplayName(i);
    }

    String getStateString(DFAState s) {
        if (s == ATNSimulator.ERROR) {
            return "ERROR";
        }
        int n = s.stateNumber;
        String stateStr = "s" + n;
        if (s.isAcceptState()) {
            stateStr = s.predicates != null ? ":s" + n + "=>" + Arrays.toString(s.predicates) : ":s" + n + "=>" + s.getPrediction();
        }
        if (s.isContextSensitive()) {
            stateStr = stateStr + "*";
            for (ATNConfig config : s.configs) {
                if (!config.getReachesIntoOuterContext()) continue;
                stateStr = stateStr + "*";
                break;
            }
        }
        return stateStr;
    }
}

