/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.automata;

import groovyjarjarantlr4.v4.misc.CharSupport;
import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.AtomTransition;
import groovyjarjarantlr4.v4.runtime.atn.BlockEndState;
import groovyjarjarantlr4.v4.runtime.atn.CodePointTransitions;
import groovyjarjarantlr4.v4.runtime.atn.DecisionState;
import groovyjarjarantlr4.v4.runtime.atn.EpsilonTransition;
import groovyjarjarantlr4.v4.runtime.atn.NotSetTransition;
import groovyjarjarantlr4.v4.runtime.atn.RangeTransition;
import groovyjarjarantlr4.v4.runtime.atn.SetTransition;
import groovyjarjarantlr4.v4.runtime.atn.Transition;
import groovyjarjarantlr4.v4.runtime.misc.Interval;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.Rule;
import java.util.List;

public class ATNOptimizer {
    public static void optimize(@NotNull Grammar g, @NotNull ATN atn) {
        ATNOptimizer.optimizeSets(g, atn);
        ATNOptimizer.optimizeStates(atn);
    }

    private static void optimizeSets(Grammar g, ATN atn) {
        if (g.isParser()) {
            return;
        }
        int removedStates = 0;
        List<DecisionState> decisions = atn.decisionToState;
        for (DecisionState decision : decisions) {
            int i;
            if (decision.ruleIndex >= 0) {
                Rule rule = g.getRule(decision.ruleIndex);
                if (Character.isLowerCase(rule.name.charAt(0))) continue;
            }
            IntervalSet setTransitions = new IntervalSet(new int[0]);
            for (i = 0; i < decision.getNumberOfTransitions(); ++i) {
                Transition epsTransition = decision.transition(i);
                if (!(epsTransition instanceof EpsilonTransition) || epsTransition.target.getNumberOfTransitions() != 1) continue;
                Transition transition = epsTransition.target.transition(0);
                if (!(transition.target instanceof BlockEndState) || transition instanceof NotSetTransition || !(transition instanceof AtomTransition) && !(transition instanceof RangeTransition) && !(transition instanceof SetTransition)) continue;
                setTransitions.add(i);
            }
            for (i = setTransitions.getIntervals().size() - 1; i >= 0; --i) {
                Transition newTransition;
                Interval interval = setTransitions.getIntervals().get(i);
                if (interval.length() <= 1) continue;
                ATNState blockEndState = decision.transition((int)interval.a).target.transition((int)0).target;
                IntervalSet matchSet = new IntervalSet(new int[0]);
                for (int j = interval.a; j <= interval.b; ++j) {
                    Transition matchTransition = decision.transition((int)j).target.transition(0);
                    if (matchTransition instanceof NotSetTransition) {
                        throw new UnsupportedOperationException("Not yet implemented.");
                    }
                    IntervalSet set = matchTransition.label();
                    List<Interval> intervals = set.getIntervals();
                    int n = intervals.size();
                    block4: for (int k = 0; k < n; ++k) {
                        Interval setInterval = intervals.get(k);
                        int a = setInterval.a;
                        int b = setInterval.b;
                        if (a == -1 || b == -1) continue;
                        for (int v = a; v <= b; ++v) {
                            if (!matchSet.contains(v)) continue;
                            g.tool.errMgr.grammarError(ErrorType.CHARACTERS_COLLISION_IN_SET, g.fileName, null, CharSupport.getANTLRCharLiteralForChar(v), CharSupport.getIntervalSetEscapedString(matchSet));
                            continue block4;
                        }
                    }
                    matchSet.addAll(set);
                }
                if (matchSet.getIntervals().size() == 1) {
                    if (matchSet.size() == 1) {
                        newTransition = CodePointTransitions.createWithCodePoint(blockEndState, matchSet.getMinElement());
                    } else {
                        Interval matchInterval = matchSet.getIntervals().get(0);
                        newTransition = CodePointTransitions.createWithCodePointRange(blockEndState, matchInterval.a, matchInterval.b);
                    }
                } else {
                    newTransition = new SetTransition(blockEndState, matchSet);
                }
                decision.transition((int)interval.a).target.setTransition(0, newTransition);
                for (int j = interval.a + 1; j <= interval.b; ++j) {
                    Transition removed = decision.removeTransition(interval.a + 1);
                    atn.removeState(removed.target);
                    ++removedStates;
                }
            }
        }
    }

    private static void optimizeStates(ATN atn) {
        List<ATNState> states = atn.states;
        int current = 0;
        for (int i = 0; i < states.size(); ++i) {
            ATNState state = states.get(i);
            if (state == null) continue;
            if (i != current) {
                state.stateNumber = current;
                states.set(current, state);
                states.set(i, null);
            }
            ++current;
        }
        states.subList(current, states.size()).clear();
    }

    private ATNOptimizer() {
    }
}

