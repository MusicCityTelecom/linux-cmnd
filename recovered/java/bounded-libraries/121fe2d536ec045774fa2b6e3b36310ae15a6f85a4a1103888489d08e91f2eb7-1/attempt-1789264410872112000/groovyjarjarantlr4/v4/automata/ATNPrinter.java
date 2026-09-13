/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.automata;

import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.ActionTransition;
import groovyjarjarantlr4.v4.runtime.atn.AtomTransition;
import groovyjarjarantlr4.v4.runtime.atn.BlockEndState;
import groovyjarjarantlr4.v4.runtime.atn.BlockStartState;
import groovyjarjarantlr4.v4.runtime.atn.EpsilonTransition;
import groovyjarjarantlr4.v4.runtime.atn.NotSetTransition;
import groovyjarjarantlr4.v4.runtime.atn.PlusBlockStartState;
import groovyjarjarantlr4.v4.runtime.atn.PlusLoopbackState;
import groovyjarjarantlr4.v4.runtime.atn.RuleStartState;
import groovyjarjarantlr4.v4.runtime.atn.RuleStopState;
import groovyjarjarantlr4.v4.runtime.atn.RuleTransition;
import groovyjarjarantlr4.v4.runtime.atn.SetTransition;
import groovyjarjarantlr4.v4.runtime.atn.StarBlockStartState;
import groovyjarjarantlr4.v4.runtime.atn.StarLoopEntryState;
import groovyjarjarantlr4.v4.runtime.atn.StarLoopbackState;
import groovyjarjarantlr4.v4.runtime.atn.Transition;
import groovyjarjarantlr4.v4.tool.Grammar;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ATNPrinter {
    List<ATNState> work;
    Set<ATNState> marked;
    Grammar g;
    ATNState start;

    public ATNPrinter(Grammar g, ATNState start) {
        this.g = g;
        this.start = start;
    }

    public String asString() {
        if (this.start == null) {
            return null;
        }
        this.marked = new HashSet<ATNState>();
        this.work = new ArrayList<ATNState>();
        this.work.add(this.start);
        StringBuilder buf = new StringBuilder();
        while (!this.work.isEmpty()) {
            ATNState s = this.work.remove(0);
            if (this.marked.contains(s)) continue;
            int n = s.getNumberOfTransitions();
            this.marked.add(s);
            for (int i = 0; i < n; ++i) {
                Transition a;
                Transition t = s.transition(i);
                if (!(s instanceof RuleStopState)) {
                    if (t instanceof RuleTransition) {
                        this.work.add(((RuleTransition)t).followState);
                    } else {
                        this.work.add(t.target);
                    }
                }
                buf.append(this.getStateString(s));
                if (t instanceof EpsilonTransition) {
                    buf.append("->").append(this.getStateString(t.target)).append('\n');
                    continue;
                }
                if (t instanceof RuleTransition) {
                    buf.append("-").append(this.g.getRule((int)((RuleTransition)t).ruleIndex).name).append("->").append(this.getStateString(t.target)).append('\n');
                    continue;
                }
                if (t instanceof ActionTransition) {
                    a = (ActionTransition)t;
                    buf.append("-").append(a.toString()).append("->").append(this.getStateString(t.target)).append('\n');
                    continue;
                }
                if (t instanceof SetTransition) {
                    SetTransition st = (SetTransition)t;
                    boolean not = st instanceof NotSetTransition;
                    if (this.g.isLexer()) {
                        buf.append("-").append(not ? "~" : "").append(st.toString()).append("->").append(this.getStateString(t.target)).append('\n');
                        continue;
                    }
                    buf.append("-").append(not ? "~" : "").append(st.label().toString(this.g.getVocabulary())).append("->").append(this.getStateString(t.target)).append('\n');
                    continue;
                }
                if (t instanceof AtomTransition) {
                    a = (AtomTransition)t;
                    String label = this.g.getTokenDisplayName(((AtomTransition)a).label);
                    buf.append("-").append(label).append("->").append(this.getStateString(t.target)).append('\n');
                    continue;
                }
                buf.append("-").append(t.toString()).append("->").append(this.getStateString(t.target)).append('\n');
            }
        }
        return buf.toString();
    }

    String getStateString(ATNState s) {
        int n = s.stateNumber;
        String stateStr = "s" + n;
        if (s instanceof StarBlockStartState) {
            stateStr = "StarBlockStart_" + n;
        } else if (s instanceof PlusBlockStartState) {
            stateStr = "PlusBlockStart_" + n;
        } else if (s instanceof BlockStartState) {
            stateStr = "BlockStart_" + n;
        } else if (s instanceof BlockEndState) {
            stateStr = "BlockEnd_" + n;
        } else if (s instanceof RuleStartState) {
            stateStr = "RuleStart_" + this.g.getRule((int)s.ruleIndex).name + "_" + n;
        } else if (s instanceof RuleStopState) {
            stateStr = "RuleStop_" + this.g.getRule((int)s.ruleIndex).name + "_" + n;
        } else if (s instanceof PlusLoopbackState) {
            stateStr = "PlusLoopBack_" + n;
        } else if (s instanceof StarLoopbackState) {
            stateStr = "StarLoopBack_" + n;
        } else if (s instanceof StarLoopEntryState) {
            stateStr = "StarLoopEntry_" + n;
        }
        return stateStr;
    }
}

