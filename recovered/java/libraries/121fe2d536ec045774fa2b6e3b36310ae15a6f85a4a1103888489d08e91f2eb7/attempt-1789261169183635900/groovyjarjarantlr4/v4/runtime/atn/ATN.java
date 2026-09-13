/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.RuleContext;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.ATNType;
import groovyjarjarantlr4.v4.runtime.atn.DecisionState;
import groovyjarjarantlr4.v4.runtime.atn.LL1Analyzer;
import groovyjarjarantlr4.v4.runtime.atn.LexerAction;
import groovyjarjarantlr4.v4.runtime.atn.PredictionContext;
import groovyjarjarantlr4.v4.runtime.atn.RuleStartState;
import groovyjarjarantlr4.v4.runtime.atn.RuleStopState;
import groovyjarjarantlr4.v4.runtime.atn.RuleTransition;
import groovyjarjarantlr4.v4.runtime.atn.TokensStartState;
import groovyjarjarantlr4.v4.runtime.dfa.DFA;
import groovyjarjarantlr4.v4.runtime.misc.Args;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ATN {
    public static final int INVALID_ALT_NUMBER = 0;
    @NotNull
    public final List<ATNState> states = new ArrayList<ATNState>();
    @NotNull
    public final List<DecisionState> decisionToState = new ArrayList<DecisionState>();
    public RuleStartState[] ruleToStartState;
    public RuleStopState[] ruleToStopState;
    @NotNull
    public final Map<String, TokensStartState> modeNameToStartState = new LinkedHashMap<String, TokensStartState>();
    public final ATNType grammarType;
    public final int maxTokenType;
    private boolean hasUnicodeSMPTransitions;
    public int[] ruleToTokenType;
    public LexerAction[] lexerActions;
    @NotNull
    public final List<TokensStartState> modeToStartState = new ArrayList<TokensStartState>();
    private final ConcurrentMap<PredictionContext, PredictionContext> contextCache = new ConcurrentHashMap<PredictionContext, PredictionContext>();
    @NotNull
    public DFA[] decisionToDFA = new DFA[0];
    @NotNull
    public DFA[] modeToDFA = new DFA[0];
    protected final ConcurrentMap<Integer, Integer> LL1Table = new ConcurrentHashMap<Integer, Integer>();

    public ATN(@NotNull ATNType grammarType, int maxTokenType) {
        this.grammarType = grammarType;
        this.maxTokenType = maxTokenType;
    }

    public final void clearDFA() {
        int i;
        this.decisionToDFA = new DFA[this.decisionToState.size()];
        for (i = 0; i < this.decisionToDFA.length; ++i) {
            this.decisionToDFA[i] = new DFA(this.decisionToState.get(i), i);
        }
        this.modeToDFA = new DFA[this.modeToStartState.size()];
        for (i = 0; i < this.modeToDFA.length; ++i) {
            this.modeToDFA[i] = new DFA(this.modeToStartState.get(i));
        }
        this.contextCache.clear();
        this.LL1Table.clear();
    }

    public int getContextCacheSize() {
        return this.contextCache.size();
    }

    public PredictionContext getCachedContext(PredictionContext context) {
        return PredictionContext.getCachedContext(context, this.contextCache, new PredictionContext.IdentityHashMap());
    }

    public final DFA[] getDecisionToDFA() {
        assert (this.decisionToDFA != null && this.decisionToDFA.length == this.decisionToState.size());
        return this.decisionToDFA;
    }

    @NotNull
    public IntervalSet nextTokens(ATNState s, @NotNull PredictionContext ctx) {
        Args.notNull("ctx", ctx);
        LL1Analyzer anal = new LL1Analyzer(this);
        IntervalSet next = anal.LOOK(s, ctx);
        return next;
    }

    @NotNull
    public IntervalSet nextTokens(@NotNull ATNState s) {
        if (s.nextTokenWithinRule != null) {
            return s.nextTokenWithinRule;
        }
        s.nextTokenWithinRule = this.nextTokens(s, PredictionContext.EMPTY_LOCAL);
        s.nextTokenWithinRule.setReadonly(true);
        return s.nextTokenWithinRule;
    }

    public void addState(@Nullable ATNState state) {
        if (state != null) {
            state.atn = this;
            state.stateNumber = this.states.size();
        }
        this.states.add(state);
    }

    public void removeState(@NotNull ATNState state) {
        this.states.set(state.stateNumber, null);
    }

    public void defineMode(@NotNull String name, @NotNull TokensStartState s) {
        this.modeNameToStartState.put(name, s);
        this.modeToStartState.add(s);
        this.modeToDFA = Arrays.copyOf(this.modeToDFA, this.modeToStartState.size());
        this.modeToDFA[this.modeToDFA.length - 1] = new DFA(s);
        this.defineDecisionState(s);
    }

    public int defineDecisionState(@NotNull DecisionState s) {
        this.decisionToState.add(s);
        s.decision = this.decisionToState.size() - 1;
        this.decisionToDFA = Arrays.copyOf(this.decisionToDFA, this.decisionToState.size());
        this.decisionToDFA[this.decisionToDFA.length - 1] = new DFA(s, s.decision);
        return s.decision;
    }

    public DecisionState getDecisionState(int decision) {
        if (!this.decisionToState.isEmpty()) {
            return this.decisionToState.get(decision);
        }
        return null;
    }

    public int getNumberOfDecisions() {
        return this.decisionToState.size();
    }

    @NotNull
    public IntervalSet getExpectedTokens(int stateNumber, @Nullable RuleContext context) {
        if (stateNumber < 0 || stateNumber >= this.states.size()) {
            throw new IllegalArgumentException("Invalid state number.");
        }
        RuleContext ctx = context;
        ATNState s = this.states.get(stateNumber);
        IntervalSet following = this.nextTokens(s);
        if (!following.contains(-2)) {
            return following;
        }
        IntervalSet expected = new IntervalSet(new int[0]);
        expected.addAll(following);
        expected.remove(-2);
        while (ctx != null && ctx.invokingState >= 0 && following.contains(-2)) {
            ATNState invokingState = this.states.get(ctx.invokingState);
            RuleTransition rt = (RuleTransition)invokingState.transition(0);
            following = this.nextTokens(rt.followState);
            expected.addAll(following);
            expected.remove(-2);
            ctx = ctx.parent;
        }
        if (following.contains(-2)) {
            expected.add(-1);
        }
        return expected;
    }

    public boolean hasUnicodeSMPTransitions() {
        return this.hasUnicodeSMPTransitions;
    }

    public void setHasUnicodeSMPTransitions(boolean value) {
        this.hasUnicodeSMPTransitions = value;
    }
}

