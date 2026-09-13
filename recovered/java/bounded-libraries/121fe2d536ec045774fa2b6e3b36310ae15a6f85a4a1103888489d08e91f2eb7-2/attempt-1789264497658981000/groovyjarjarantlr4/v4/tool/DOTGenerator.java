/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.stringtemplate.v4.ST
 *  org.stringtemplate.v4.STGroup
 *  org.stringtemplate.v4.STGroupFile
 */
package groovyjarjarantlr4.v4.tool;

import groovyjarjarantlr4.v4.misc.Utils;
import groovyjarjarantlr4.v4.runtime.atn.ATNConfig;
import groovyjarjarantlr4.v4.runtime.atn.ATNConfigSet;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.AbstractPredicateTransition;
import groovyjarjarantlr4.v4.runtime.atn.ActionTransition;
import groovyjarjarantlr4.v4.runtime.atn.AtomTransition;
import groovyjarjarantlr4.v4.runtime.atn.BlockEndState;
import groovyjarjarantlr4.v4.runtime.atn.BlockStartState;
import groovyjarjarantlr4.v4.runtime.atn.DecisionState;
import groovyjarjarantlr4.v4.runtime.atn.NotSetTransition;
import groovyjarjarantlr4.v4.runtime.atn.PlusBlockStartState;
import groovyjarjarantlr4.v4.runtime.atn.PlusLoopbackState;
import groovyjarjarantlr4.v4.runtime.atn.RangeTransition;
import groovyjarjarantlr4.v4.runtime.atn.RuleStartState;
import groovyjarjarantlr4.v4.runtime.atn.RuleStopState;
import groovyjarjarantlr4.v4.runtime.atn.RuleTransition;
import groovyjarjarantlr4.v4.runtime.atn.SetTransition;
import groovyjarjarantlr4.v4.runtime.atn.StarBlockStartState;
import groovyjarjarantlr4.v4.runtime.atn.StarLoopEntryState;
import groovyjarjarantlr4.v4.runtime.atn.StarLoopbackState;
import groovyjarjarantlr4.v4.runtime.atn.Transition;
import groovyjarjarantlr4.v4.runtime.dfa.DFA;
import groovyjarjarantlr4.v4.runtime.dfa.DFAState;
import groovyjarjarantlr4.v4.tool.Grammar;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class DOTGenerator {
    public static final boolean STRIP_NONREDUCED_STATES = false;
    protected String arrowhead = "normal";
    protected String rankdir = "LR";
    public static STGroup stlib = new STGroupFile("groovyjarjarantlr4/v4/tool/templates/dot/graphs.stg");
    protected Grammar grammar;

    public DOTGenerator(Grammar grammar) {
        this.grammar = grammar;
    }

    public String getDOT(DFA dfa, boolean isLexer) {
        ST st;
        if (dfa.s0.get() == null) {
            return null;
        }
        ST dot = stlib.getInstanceOf("dfa");
        dot.add("name", (Object)("DFA" + dfa.decision));
        dot.add("startState", (Object)dfa.s0.get().stateNumber);
        dot.add("rankdir", (Object)this.rankdir);
        for (DFAState d : dfa.states.keySet()) {
            if (!d.isAcceptState()) continue;
            st = stlib.getInstanceOf("stopstate");
            st.add("name", (Object)("s" + d.stateNumber));
            st.add("label", (Object)this.getStateLabel(d));
            dot.add("states", (Object)st);
        }
        for (DFAState d : dfa.states.keySet()) {
            if (d.isAcceptState() || d.stateNumber == Integer.MAX_VALUE) continue;
            st = stlib.getInstanceOf("state");
            st.add("name", (Object)("s" + d.stateNumber));
            st.add("label", (Object)this.getStateLabel(d));
            dot.add("states", (Object)st);
        }
        for (DFAState d : dfa.states.keySet()) {
            Map<Integer, DFAState> edges = d.getEdgeMap();
            for (Map.Entry<Integer, DFAState> entry : edges.entrySet()) {
                DFAState target = entry.getValue();
                if (target == null || target.stateNumber == Integer.MAX_VALUE) continue;
                int ttype = entry.getKey();
                String label = String.valueOf(ttype);
                if (isLexer) {
                    label = "'" + this.getEdgeLabel(new StringBuilder().appendCodePoint(entry.getKey()).toString()) + "'";
                } else if (this.grammar != null) {
                    label = this.grammar.getTokenDisplayName(ttype);
                }
                ST st2 = stlib.getInstanceOf("edge");
                st2.add("label", (Object)label);
                st2.add("src", (Object)("s" + d.stateNumber));
                st2.add("target", (Object)("s" + target.stateNumber));
                st2.add("arrowhead", (Object)this.arrowhead);
                dot.add("edges", (Object)st2);
            }
        }
        String output = dot.render();
        return Utils.sortLinesInString(output);
    }

    protected String getStateLabel(DFAState s) {
        if (s == null) {
            return "null";
        }
        StringBuilder buf = new StringBuilder(250);
        buf.append('s');
        buf.append(s.stateNumber);
        if (s.isAcceptState()) {
            buf.append("=>").append(s.getPrediction());
        }
        if (this.grammar != null) {
            BitSet alts = s.configs.getRepresentedAlternatives();
            buf.append("\\n");
            ATNConfigSet configurations = s.configs;
            int alt = alts.nextSetBit(0);
            while (alt >= 0) {
                if (alt > alts.nextSetBit(0)) {
                    buf.append("\\n");
                }
                buf.append("alt");
                buf.append(alt);
                buf.append(':');
                ArrayList<ATNConfig> configsInAlt = new ArrayList<ATNConfig>();
                for (ATNConfig c : configurations) {
                    if (c.getAlt() != alt) continue;
                    configsInAlt.add(c);
                }
                int n = 0;
                for (int cIndex = 0; cIndex < configsInAlt.size(); ++cIndex) {
                    ATNConfig c = (ATNConfig)configsInAlt.get(cIndex);
                    ++n;
                    buf.append(c.toString(null, false));
                    if (cIndex + 1 < configsInAlt.size()) {
                        buf.append(", ");
                    }
                    if (n % 5 != 0 || configsInAlt.size() - cIndex <= 3) continue;
                    buf.append("\\n");
                }
                alt = alts.nextSetBit(alt + 1);
            }
        }
        String stateLabel = buf.toString();
        return stateLabel;
    }

    public String getDOT(ATNState startState) {
        return this.getDOT(startState, false);
    }

    public String getDOT(ATNState startState, boolean isLexer) {
        Set ruleNames = this.grammar.rules.keySet();
        String[] names = new String[ruleNames.size() + 1];
        int i = 0;
        for (String s : ruleNames) {
            names[i++] = s;
        }
        return this.getDOT(startState, names, isLexer);
    }

    public String getDOT(ATNState startState, String[] ruleNames, boolean isLexer) {
        if (startState == null) {
            return null;
        }
        HashSet<ATNState> markedStates = new HashSet<ATNState>();
        ST dot = stlib.getInstanceOf("atn");
        dot.add("startState", (Object)startState.stateNumber);
        dot.add("rankdir", (Object)this.rankdir);
        LinkedList<ATNState> work = new LinkedList<ATNState>();
        work.add(startState);
        while (!work.isEmpty()) {
            ATNState s = (ATNState)work.get(0);
            if (markedStates.contains(s)) {
                work.remove(0);
                continue;
            }
            markedStates.add(s);
            if (s instanceof RuleStopState) continue;
            for (int i = 0; i < s.getNumberOfTransitions(); ++i) {
                String label;
                ST edgeST;
                Transition edge = s.transition(i);
                if (edge instanceof RuleTransition) {
                    RuleTransition rr = (RuleTransition)edge;
                    edgeST = stlib.getInstanceOf("edge");
                    label = "<" + ruleNames[rr.ruleIndex];
                    if (((RuleStartState)rr.target).isPrecedenceRule) {
                        label = label + "[" + rr.precedence + "]";
                    }
                    label = label + ">";
                    edgeST.add("label", (Object)label);
                    edgeST.add("src", (Object)("s" + s.stateNumber));
                    edgeST.add("target", (Object)("s" + rr.followState.stateNumber));
                    edgeST.add("arrowhead", (Object)this.arrowhead);
                    dot.add("edges", (Object)edgeST);
                    work.add(rr.followState);
                    continue;
                }
                if (edge instanceof ActionTransition) {
                    edgeST = stlib.getInstanceOf("action-edge");
                    edgeST.add("label", (Object)this.getEdgeLabel(edge.toString()));
                } else if (edge instanceof AbstractPredicateTransition) {
                    edgeST = stlib.getInstanceOf("edge");
                    edgeST.add("label", (Object)this.getEdgeLabel(edge.toString()));
                } else if (edge.isEpsilon()) {
                    edgeST = stlib.getInstanceOf("epsilon-edge");
                    edgeST.add("label", (Object)this.getEdgeLabel(edge.toString()));
                    boolean loopback = false;
                    if (edge.target instanceof PlusBlockStartState) {
                        loopback = s.equals(((PlusBlockStartState)edge.target).loopBackState);
                    } else if (edge.target instanceof StarLoopEntryState) {
                        loopback = s.equals(((StarLoopEntryState)edge.target).loopBackState);
                    }
                    edgeST.add("loopback", (Object)loopback);
                } else if (edge instanceof AtomTransition) {
                    edgeST = stlib.getInstanceOf("edge");
                    AtomTransition atom = (AtomTransition)edge;
                    label = String.valueOf(atom.label);
                    if (isLexer) {
                        label = "'" + this.getEdgeLabel(new StringBuilder().appendCodePoint(atom.label).toString()) + "'";
                    } else if (this.grammar != null) {
                        label = this.grammar.getTokenDisplayName(atom.label);
                    }
                    edgeST.add("label", (Object)this.getEdgeLabel(label));
                } else if (edge instanceof SetTransition) {
                    edgeST = stlib.getInstanceOf("edge");
                    SetTransition set = (SetTransition)edge;
                    label = set.label().toString();
                    if (isLexer) {
                        label = set.label().toString(true);
                    } else if (this.grammar != null) {
                        label = set.label().toString(this.grammar.getVocabulary());
                    }
                    if (edge instanceof NotSetTransition) {
                        label = "~" + label;
                    }
                    edgeST.add("label", (Object)this.getEdgeLabel(label));
                } else if (edge instanceof RangeTransition) {
                    edgeST = stlib.getInstanceOf("edge");
                    RangeTransition range = (RangeTransition)edge;
                    label = range.label().toString();
                    if (isLexer) {
                        label = range.toString();
                    } else if (this.grammar != null) {
                        label = range.label().toString(this.grammar.getVocabulary());
                    }
                    edgeST.add("label", (Object)this.getEdgeLabel(label));
                } else {
                    edgeST = stlib.getInstanceOf("edge");
                    edgeST.add("label", (Object)this.getEdgeLabel(edge.toString()));
                }
                edgeST.add("src", (Object)("s" + s.stateNumber));
                edgeST.add("target", (Object)("s" + edge.target.stateNumber));
                edgeST.add("arrowhead", (Object)this.arrowhead);
                if (s.getNumberOfTransitions() > 1) {
                    edgeST.add("transitionIndex", (Object)i);
                } else {
                    edgeST.add("transitionIndex", (Object)false);
                }
                dot.add("edges", (Object)edgeST);
                work.add(edge.target);
            }
        }
        for (ATNState s : markedStates) {
            if (!(s instanceof RuleStopState)) continue;
            ST st = stlib.getInstanceOf("stopstate");
            st.add("name", (Object)("s" + s.stateNumber));
            st.add("label", (Object)this.getStateLabel(s));
            dot.add("states", (Object)st);
        }
        for (ATNState s : markedStates) {
            if (s instanceof RuleStopState) continue;
            ST st = stlib.getInstanceOf("state");
            st.add("name", (Object)("s" + s.stateNumber));
            st.add("label", (Object)this.getStateLabel(s));
            st.add("transitions", (Object)s.getTransitions());
            dot.add("states", (Object)st);
        }
        return dot.render();
    }

    protected String getEdgeLabel(String label) {
        label = label.replace("\\", "\\\\");
        label = label.replace("\"", "\\\"");
        label = label.replace("\n", "\\\\n");
        label = label.replace("\r", "");
        return label;
    }

    protected String getStateLabel(ATNState s) {
        if (s == null) {
            return "null";
        }
        String stateLabel = "";
        if (s instanceof BlockStartState) {
            stateLabel = stateLabel + "&rarr;\\n";
        } else if (s instanceof BlockEndState) {
            stateLabel = stateLabel + "&larr;\\n";
        }
        stateLabel = stateLabel + String.valueOf(s.stateNumber);
        if (s instanceof PlusBlockStartState || s instanceof PlusLoopbackState) {
            stateLabel = stateLabel + "+";
        } else if (s instanceof StarBlockStartState || s instanceof StarLoopEntryState || s instanceof StarLoopbackState) {
            stateLabel = stateLabel + "*";
        }
        if (s instanceof DecisionState && ((DecisionState)s).decision >= 0) {
            stateLabel = stateLabel + "\\nd=" + ((DecisionState)s).decision;
        }
        return stateLabel;
    }
}

