/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNConfig;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.AbstractPredicateTransition;
import groovyjarjarantlr4.v4.runtime.atn.NotSetTransition;
import groovyjarjarantlr4.v4.runtime.atn.PredictionContext;
import groovyjarjarantlr4.v4.runtime.atn.RuleStopState;
import groovyjarjarantlr4.v4.runtime.atn.RuleTransition;
import groovyjarjarantlr4.v4.runtime.atn.Transition;
import groovyjarjarantlr4.v4.runtime.atn.WildcardTransition;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

public class LL1Analyzer {
    public static final int HIT_PRED = 0;
    @NotNull
    public final ATN atn;

    public LL1Analyzer(@NotNull ATN atn) {
        this.atn = atn;
    }

    @Nullable
    public IntervalSet[] getDecisionLookahead(@Nullable ATNState s) {
        if (s == null) {
            return null;
        }
        IntervalSet[] look = new IntervalSet[s.getNumberOfTransitions()];
        for (int alt = 0; alt < s.getNumberOfTransitions(); ++alt) {
            look[alt] = new IntervalSet(new int[0]);
            HashSet<ATNConfig> lookBusy = new HashSet<ATNConfig>();
            boolean seeThruPreds = false;
            this._LOOK(s.transition((int)alt).target, null, PredictionContext.EMPTY_LOCAL, look[alt], lookBusy, new BitSet(), seeThruPreds, false);
            if (look[alt].size() != 0 && !look[alt].contains(0)) continue;
            look[alt] = null;
        }
        return look;
    }

    @NotNull
    public IntervalSet LOOK(@NotNull ATNState s, @NotNull PredictionContext ctx) {
        return this.LOOK(s, s.atn.ruleToStopState[s.ruleIndex], ctx);
    }

    @NotNull
    public IntervalSet LOOK(@NotNull ATNState s, @Nullable ATNState stopState, @NotNull PredictionContext ctx) {
        IntervalSet r = new IntervalSet(new int[0]);
        boolean seeThruPreds = true;
        boolean addEOF = true;
        this._LOOK(s, stopState, ctx, r, new HashSet<ATNConfig>(), new BitSet(), true, true);
        return r;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void _LOOK(@NotNull ATNState s, @Nullable ATNState stopState, @NotNull PredictionContext ctx, @NotNull IntervalSet look, @NotNull Set<ATNConfig> lookBusy, @NotNull BitSet calledRuleStack, boolean seeThruPreds, boolean addEOF) {
        int i;
        ATNConfig c = ATNConfig.create(s, 0, ctx);
        if (!lookBusy.add(c)) {
            return;
        }
        if (s == stopState) {
            if (PredictionContext.isEmptyLocal(ctx)) {
                look.add(-2);
                return;
            }
            if (ctx.isEmpty()) {
                if (addEOF) {
                    look.add(-1);
                }
                return;
            }
        }
        if (s instanceof RuleStopState) {
            if (ctx.isEmpty() && !PredictionContext.isEmptyLocal(ctx)) {
                if (addEOF) {
                    look.add(-1);
                }
                return;
            }
            boolean removed = calledRuleStack.get(s.ruleIndex);
            try {
                calledRuleStack.clear(s.ruleIndex);
                for (i = 0; i < ctx.size(); ++i) {
                    if (ctx.getReturnState(i) == Integer.MAX_VALUE) continue;
                    ATNState returnState = this.atn.states.get(ctx.getReturnState(i));
                    this._LOOK(returnState, stopState, ctx.getParent(i), look, lookBusy, calledRuleStack, seeThruPreds, addEOF);
                }
            }
            finally {
                if (removed) {
                    calledRuleStack.set(s.ruleIndex);
                }
            }
        }
        int n = s.getNumberOfTransitions();
        for (i = 0; i < n; ++i) {
            Transition t = s.transition(i);
            if (t instanceof RuleTransition) {
                RuleTransition ruleTransition = (RuleTransition)t;
                if (calledRuleStack.get(ruleTransition.ruleIndex)) continue;
                PredictionContext newContext = ctx.getChild(ruleTransition.followState.stateNumber);
                try {
                    calledRuleStack.set(ruleTransition.ruleIndex);
                    this._LOOK(t.target, stopState, newContext, look, lookBusy, calledRuleStack, seeThruPreds, addEOF);
                    continue;
                }
                finally {
                    calledRuleStack.clear(ruleTransition.ruleIndex);
                }
            }
            if (t instanceof AbstractPredicateTransition) {
                if (seeThruPreds) {
                    this._LOOK(t.target, stopState, ctx, look, lookBusy, calledRuleStack, seeThruPreds, addEOF);
                    continue;
                }
                look.add(0);
                continue;
            }
            if (t.isEpsilon()) {
                this._LOOK(t.target, stopState, ctx, look, lookBusy, calledRuleStack, seeThruPreds, addEOF);
                continue;
            }
            if (t.getClass() == WildcardTransition.class) {
                look.addAll(IntervalSet.of(1, this.atn.maxTokenType));
                continue;
            }
            IntervalSet set = t.label();
            if (set == null) continue;
            if (t instanceof NotSetTransition) {
                set = set.complement(IntervalSet.of(1, this.atn.maxTokenType));
            }
            look.addAll(set);
        }
    }
}

