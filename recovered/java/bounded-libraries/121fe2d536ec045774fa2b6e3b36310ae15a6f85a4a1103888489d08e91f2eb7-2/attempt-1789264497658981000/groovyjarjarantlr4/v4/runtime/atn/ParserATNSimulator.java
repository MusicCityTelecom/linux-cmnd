/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.NoViableAltException;
import groovyjarjarantlr4.v4.runtime.Parser;
import groovyjarjarantlr4.v4.runtime.ParserRuleContext;
import groovyjarjarantlr4.v4.runtime.RuleContext;
import groovyjarjarantlr4.v4.runtime.TokenStream;
import groovyjarjarantlr4.v4.runtime.Vocabulary;
import groovyjarjarantlr4.v4.runtime.VocabularyImpl;
import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNConfig;
import groovyjarjarantlr4.v4.runtime.atn.ATNConfigSet;
import groovyjarjarantlr4.v4.runtime.atn.ATNSimulator;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.ActionTransition;
import groovyjarjarantlr4.v4.runtime.atn.AtomTransition;
import groovyjarjarantlr4.v4.runtime.atn.ConflictInfo;
import groovyjarjarantlr4.v4.runtime.atn.DecisionState;
import groovyjarjarantlr4.v4.runtime.atn.EpsilonTransition;
import groovyjarjarantlr4.v4.runtime.atn.NotSetTransition;
import groovyjarjarantlr4.v4.runtime.atn.PrecedencePredicateTransition;
import groovyjarjarantlr4.v4.runtime.atn.PredicateTransition;
import groovyjarjarantlr4.v4.runtime.atn.PredictionContext;
import groovyjarjarantlr4.v4.runtime.atn.PredictionContextCache;
import groovyjarjarantlr4.v4.runtime.atn.PredictionMode;
import groovyjarjarantlr4.v4.runtime.atn.RuleStopState;
import groovyjarjarantlr4.v4.runtime.atn.RuleTransition;
import groovyjarjarantlr4.v4.runtime.atn.SemanticContext;
import groovyjarjarantlr4.v4.runtime.atn.SetTransition;
import groovyjarjarantlr4.v4.runtime.atn.SimulatorState;
import groovyjarjarantlr4.v4.runtime.atn.StarLoopEntryState;
import groovyjarjarantlr4.v4.runtime.atn.Transition;
import groovyjarjarantlr4.v4.runtime.dfa.AcceptStateInfo;
import groovyjarjarantlr4.v4.runtime.dfa.DFA;
import groovyjarjarantlr4.v4.runtime.dfa.DFAState;
import groovyjarjarantlr4.v4.runtime.misc.IntegerList;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.runtime.misc.Tuple;
import groovyjarjarantlr4.v4.runtime.misc.Tuple2;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class ParserATNSimulator
extends ATNSimulator {
    public static final boolean debug = false;
    public static final boolean dfa_debug = false;
    public static final boolean retry_debug = false;
    @NotNull
    private PredictionMode predictionMode = PredictionMode.LL;
    public boolean force_global_context = false;
    public boolean always_try_local_context = true;
    public boolean enable_global_context_dfa = false;
    public boolean optimize_unique_closure = true;
    public boolean optimize_ll1 = true;
    @Deprecated
    public boolean optimize_hidden_conflicted_configs = false;
    public boolean optimize_tail_calls = true;
    public boolean tail_call_preserves_sll = true;
    public boolean treat_sllk1_conflict_as_ambiguity = false;
    @Nullable
    protected final Parser parser;
    public boolean reportAmbiguities = false;
    protected boolean userWantsCtxSensitive = true;
    private DFA dfa;
    private static final Comparator<ATNConfig> STATE_ALT_SORT_COMPARATOR = new Comparator<ATNConfig>(){

        @Override
        public int compare(ATNConfig o1, ATNConfig o2) {
            int diff = o1.getState().getNonStopStateNumber() - o2.getState().getNonStopStateNumber();
            if (diff != 0) {
                return diff;
            }
            diff = o1.getAlt() - o2.getAlt();
            if (diff != 0) {
                return diff;
            }
            return 0;
        }
    };

    public ParserATNSimulator(@NotNull ATN atn) {
        this(null, atn);
    }

    public ParserATNSimulator(@Nullable Parser parser, @NotNull ATN atn) {
        super(atn);
        this.parser = parser;
    }

    @NotNull
    public final PredictionMode getPredictionMode() {
        return this.predictionMode;
    }

    public final void setPredictionMode(@NotNull PredictionMode predictionMode) {
        this.predictionMode = predictionMode;
    }

    @Override
    public void reset() {
    }

    public int adaptivePredict(@NotNull TokenStream input, int decision, @Nullable ParserRuleContext outerContext) {
        return this.adaptivePredict(input, decision, outerContext, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int adaptivePredict(@NotNull TokenStream input, int decision, @Nullable ParserRuleContext outerContext, boolean useContext) {
        int key;
        Integer alt;
        int ll_1;
        DFA dfa = this.atn.decisionToDFA[decision];
        assert (dfa != null);
        if (this.optimize_ll1 && !dfa.isPrecedenceDfa() && !dfa.isEmpty() && (ll_1 = input.LA(1)) >= 0 && ll_1 <= Short.MAX_VALUE && (alt = (Integer)this.atn.LL1Table.get(key = (decision << 16) + ll_1)) != null) {
            return alt;
        }
        this.dfa = dfa;
        if (this.force_global_context) {
            useContext = true;
        } else if (!this.always_try_local_context) {
            useContext |= dfa.isContextSensitive();
        }
        boolean bl = this.userWantsCtxSensitive = useContext || this.predictionMode != PredictionMode.SLL && outerContext != null && !this.atn.decisionToState.get((int)decision).sll;
        if (outerContext == null) {
            outerContext = ParserRuleContext.emptyContext();
        }
        SimulatorState state = null;
        if (!dfa.isEmpty()) {
            state = this.getStartState(dfa, input, outerContext, useContext);
        }
        if (state == null) {
            if (outerContext == null) {
                outerContext = ParserRuleContext.emptyContext();
            }
            state = this.computeStartState(dfa, outerContext, useContext);
        }
        int m = input.mark();
        int index = input.index();
        try {
            int alt2;
            int n = alt2 = this.execDFA(dfa, input, index, state);
            return n;
        }
        finally {
            this.dfa = null;
            input.seek(index);
            input.release(m);
        }
    }

    protected SimulatorState getStartState(@NotNull DFA dfa, @NotNull TokenStream input, @NotNull ParserRuleContext outerContext, boolean useContext) {
        DFAState s0;
        if (!useContext) {
            if (dfa.isPrecedenceDfa()) {
                DFAState state = dfa.getPrecedenceStartState(this.parser.getPrecedence(), false);
                if (state == null) {
                    return null;
                }
                return new SimulatorState(outerContext, state, false, outerContext);
            }
            if (dfa.s0.get() == null) {
                return null;
            }
            return new SimulatorState(outerContext, dfa.s0.get(), false, outerContext);
        }
        if (!this.enable_global_context_dfa) {
            return null;
        }
        ParserRuleContext remainingContext = outerContext;
        assert (outerContext != null);
        for (s0 = dfa.isPrecedenceDfa() ? dfa.getPrecedenceStartState(this.parser.getPrecedence(), true) : dfa.s0full.get(); remainingContext != null && s0 != null && s0.isContextSensitive(); s0 = s0.getContextTarget(this.getReturnState(remainingContext))) {
            remainingContext = this.skipTailCalls(remainingContext);
            if (remainingContext.isEmpty()) {
                assert (s0 == null || !s0.isContextSensitive());
                continue;
            }
            remainingContext = remainingContext.getParent();
        }
        if (s0 == null) {
            return null;
        }
        return new SimulatorState(outerContext, s0, useContext, remainingContext);
    }

    protected int execDFA(@NotNull DFA dfa, @NotNull TokenStream input, int startIndex, @NotNull SimulatorState state) {
        ParserRuleContext outerContext = state.outerContext;
        DFAState s = state.s0;
        int t = input.LA(1);
        ParserRuleContext remainingOuterContext = state.remainingOuterContext;
        while (true) {
            if (state.useContext) {
                while (s.isContextSymbol(t)) {
                    DFAState next = null;
                    if (remainingOuterContext != null) {
                        remainingOuterContext = this.skipTailCalls(remainingOuterContext);
                        next = s.getContextTarget(this.getReturnState(remainingOuterContext));
                    }
                    if (next == null) {
                        SimulatorState initialState = new SimulatorState(state.outerContext, s, state.useContext, remainingOuterContext);
                        return this.execATN(dfa, input, startIndex, initialState);
                    }
                    assert (remainingOuterContext != null);
                    remainingOuterContext = remainingOuterContext.getParent();
                    s = next;
                }
            }
            if (this.isAcceptState(s, state.useContext)) {
                if (s.predicates == null) break;
                break;
            }
            assert (!this.isAcceptState(s, state.useContext));
            DFAState target = this.getExistingTargetState(s, t);
            if (target == null) {
                SimulatorState initialState = new SimulatorState(outerContext, s, state.useContext, remainingOuterContext);
                int alt = this.execATN(dfa, input, startIndex, initialState);
                return alt;
            }
            if (target == ERROR) {
                SimulatorState errorState = new SimulatorState(outerContext, s, state.useContext, remainingOuterContext);
                return this.handleNoViableAlt(input, startIndex, errorState);
            }
            s = target;
            if (this.isAcceptState(s, state.useContext) || t == -1) continue;
            input.consume();
            t = input.LA(1);
        }
        if (!(state.useContext || s.configs.getConflictInfo() == null || !(dfa.atnStartState instanceof DecisionState) || !this.userWantsCtxSensitive || !s.configs.getDipsIntoOuterContext() && s.configs.isExactConflict() || this.treat_sllk1_conflict_as_ambiguity && input.index() == startIndex)) {
            assert (!state.useContext);
            BitSet conflictingAlts = null;
            DFAState.PredPrediction[] predicates = s.predicates;
            if (predicates != null) {
                int conflictIndex = input.index();
                if (conflictIndex != startIndex) {
                    input.seek(startIndex);
                }
                if ((conflictingAlts = this.evalSemanticContext(predicates, outerContext, true)).cardinality() == 1) {
                    return conflictingAlts.nextSetBit(0);
                }
                if (conflictIndex != startIndex) {
                    input.seek(conflictIndex);
                }
            }
            if (this.reportAmbiguities) {
                SimulatorState conflictState = new SimulatorState(outerContext, s, state.useContext, remainingOuterContext);
                this.reportAttemptingFullContext(dfa, conflictingAlts, conflictState, startIndex, input.index());
            }
            input.seek(startIndex);
            return this.adaptivePredict(input, dfa.decision, outerContext, true);
        }
        DFAState.PredPrediction[] predicates = s.predicates;
        if (predicates != null) {
            int stopIndex = input.index();
            if (startIndex != stopIndex) {
                input.seek(startIndex);
            }
            BitSet alts = this.evalSemanticContext(predicates, outerContext, this.reportAmbiguities && this.predictionMode == PredictionMode.LL_EXACT_AMBIG_DETECTION);
            switch (alts.cardinality()) {
                case 0: {
                    throw this.noViableAlt(input, outerContext, s.configs, startIndex);
                }
                case 1: {
                    return alts.nextSetBit(0);
                }
            }
            if (startIndex != stopIndex) {
                input.seek(stopIndex);
            }
            this.reportAmbiguity(dfa, s, startIndex, stopIndex, s.configs.isExactConflict(), alts, s.configs);
            return alts.nextSetBit(0);
        }
        return s.getPrediction();
    }

    protected boolean isAcceptState(DFAState state, boolean useContext) {
        if (!state.isAcceptState()) {
            return false;
        }
        if (state.configs.getConflictingAlts() == null) {
            return true;
        }
        if (useContext && this.predictionMode == PredictionMode.LL_EXACT_AMBIG_DETECTION) {
            return state.configs.isExactConflict();
        }
        return true;
    }

    protected int execATN(@NotNull DFA dfa, @NotNull TokenStream input, int startIndex, @NotNull SimulatorState initialState) {
        ParserRuleContext outerContext = initialState.outerContext;
        boolean useContext = initialState.useContext;
        int t = input.LA(1);
        SimulatorState previous = initialState;
        PredictionContextCache contextCache = new PredictionContextCache();
        while (true) {
            SimulatorState nextState;
            if ((nextState = this.computeReachSet(dfa, previous, t, contextCache)) == null) {
                this.addDFAEdge(previous.s0, input.LA(1), ERROR);
                return this.handleNoViableAlt(input, startIndex, previous);
            }
            DFAState D = nextState.s0;
            assert (D.isAcceptState() || D.getPrediction() == 0);
            assert (D.isAcceptState() || D.configs.getConflictInfo() == null);
            if (this.isAcceptState(D, useContext)) {
                DFAState.PredPrediction[] predPredictions;
                boolean attemptFullContext;
                int predictedAlt;
                BitSet conflictingAlts = D.configs.getConflictingAlts();
                int n = predictedAlt = conflictingAlts == null ? D.getPrediction() : 0;
                if (predictedAlt != 0) {
                    if (this.optimize_ll1 && input.index() == startIndex && !dfa.isPrecedenceDfa() && nextState.outerContext == nextState.remainingOuterContext && dfa.decision >= 0 && !D.configs.hasSemanticContext() && t >= 0 && t <= Short.MAX_VALUE) {
                        int key = (dfa.decision << 16) + t;
                        this.atn.LL1Table.put(key, predictedAlt);
                    }
                    if (useContext && this.always_try_local_context) {
                        this.reportContextSensitivity(dfa, predictedAlt, nextState, startIndex, input.index());
                    }
                }
                predictedAlt = D.getPrediction();
                boolean bl = attemptFullContext = conflictingAlts != null && this.userWantsCtxSensitive;
                if (attemptFullContext) {
                    boolean bl2 = attemptFullContext = !(useContext || !D.configs.getDipsIntoOuterContext() && D.configs.isExactConflict() || this.treat_sllk1_conflict_as_ambiguity && input.index() == startIndex);
                }
                if (D.configs.hasSemanticContext() && (predPredictions = D.predicates) != null) {
                    int conflictIndex = input.index();
                    if (conflictIndex != startIndex) {
                        input.seek(startIndex);
                    }
                    conflictingAlts = this.evalSemanticContext(predPredictions, outerContext, attemptFullContext || this.reportAmbiguities);
                    switch (conflictingAlts.cardinality()) {
                        case 0: {
                            throw this.noViableAlt(input, outerContext, D.configs, startIndex);
                        }
                        case 1: {
                            return conflictingAlts.nextSetBit(0);
                        }
                    }
                    if (conflictIndex != startIndex) {
                        input.seek(conflictIndex);
                    }
                }
                if (!attemptFullContext) {
                    if (conflictingAlts != null) {
                        if (this.reportAmbiguities && conflictingAlts.cardinality() > 1) {
                            this.reportAmbiguity(dfa, D, startIndex, input.index(), D.configs.isExactConflict(), conflictingAlts, D.configs);
                        }
                        predictedAlt = conflictingAlts.nextSetBit(0);
                    }
                    return predictedAlt;
                }
                assert (!useContext);
                assert (this.isAcceptState(D, false));
                SimulatorState fullContextState = this.computeStartState(dfa, outerContext, true);
                if (this.reportAmbiguities) {
                    this.reportAttemptingFullContext(dfa, conflictingAlts, nextState, startIndex, input.index());
                }
                input.seek(startIndex);
                return this.execATN(dfa, input, startIndex, fullContextState);
            }
            previous = nextState;
            if (t == -1) continue;
            input.consume();
            t = input.LA(1);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected int handleNoViableAlt(@NotNull TokenStream input, int startIndex, @NotNull SimulatorState previous) {
        block12: {
            if (previous.s0 == null) break block12;
            BitSet alts = new BitSet();
            int maxAlt = 0;
            for (ATNConfig config : previous.s0.configs) {
                if (!config.getReachesIntoOuterContext() && !(config.getState() instanceof RuleStopState)) continue;
                alts.set(config.getAlt());
                maxAlt = Math.max(maxAlt, config.getAlt());
            }
            switch (alts.cardinality()) {
                case 0: {
                    break;
                }
                case 1: {
                    return alts.nextSetBit(0);
                }
                default: {
                    DFAState.PredPrediction[] predicates;
                    if (!previous.s0.configs.hasSemanticContext()) {
                        return alts.nextSetBit(0);
                    }
                    ATNConfigSet filteredConfigs = new ATNConfigSet();
                    for (ATNConfig config : previous.s0.configs) {
                        if (!config.getReachesIntoOuterContext() && !(config.getState() instanceof RuleStopState)) continue;
                        filteredConfigs.add(config);
                    }
                    SemanticContext[] altToPred = this.getPredsForAmbigAlts(alts, filteredConfigs, maxAlt);
                    if (altToPred != null && (predicates = this.getPredicatePredictions(alts, altToPred)) != null) {
                        int stopIndex = input.index();
                        try {
                            input.seek(startIndex);
                            BitSet filteredAlts = this.evalSemanticContext(predicates, previous.outerContext, false);
                            if (!filteredAlts.isEmpty()) {
                                int n = filteredAlts.nextSetBit(0);
                                return n;
                            }
                        }
                        finally {
                            input.seek(stopIndex);
                        }
                    }
                    return alts.nextSetBit(0);
                }
            }
        }
        throw this.noViableAlt(input, previous.outerContext, previous.s0.configs, startIndex);
    }

    protected SimulatorState computeReachSet(DFA dfa, SimulatorState previous, int t, PredictionContextCache contextCache) {
        boolean useContext = previous.useContext;
        ParserRuleContext remainingGlobalContext = previous.remainingOuterContext;
        DFAState s = previous.s0;
        if (useContext) {
            while (s.isContextSymbol(t)) {
                DFAState next = null;
                if (remainingGlobalContext != null) {
                    remainingGlobalContext = this.skipTailCalls(remainingGlobalContext);
                    next = s.getContextTarget(this.getReturnState(remainingGlobalContext));
                }
                if (next == null) break;
                assert (remainingGlobalContext != null);
                remainingGlobalContext = remainingGlobalContext.getParent();
                s = next;
            }
        }
        assert (!this.isAcceptState(s, useContext));
        if (this.isAcceptState(s, useContext)) {
            return new SimulatorState(previous.outerContext, s, useContext, remainingGlobalContext);
        }
        DFAState s0 = s;
        DFAState target = this.getExistingTargetState(s0, t);
        if (target == null) {
            Tuple2<DFAState, ParserRuleContext> result = this.computeTargetState(dfa, s0, remainingGlobalContext, t, useContext, contextCache);
            target = result.getItem1();
            remainingGlobalContext = result.getItem2();
        }
        if (target == ERROR) {
            return null;
        }
        assert (!useContext || !target.configs.getDipsIntoOuterContext());
        return new SimulatorState(previous.outerContext, target, useContext, remainingGlobalContext);
    }

    @Nullable
    protected DFAState getExistingTargetState(@NotNull DFAState s, int t) {
        return s.getTarget(t);
    }

    @NotNull
    protected Tuple2<DFAState, ParserRuleContext> computeTargetState(@NotNull DFA dfa, @NotNull DFAState s, ParserRuleContext remainingGlobalContext, int t, boolean useContext, PredictionContextCache contextCache) {
        boolean stepIntoGlobal;
        ArrayList<ATNConfig> closureConfigs = new ArrayList<ATNConfig>(s.configs);
        IntegerList contextElements = null;
        ATNConfigSet reach = new ATNConfigSet();
        do {
            boolean hasMoreContext;
            boolean bl = hasMoreContext = !useContext || remainingGlobalContext != null;
            if (!hasMoreContext) {
                reach.setOutermostConfigSet(true);
            }
            ATNConfigSet reachIntermediate = new ATNConfigSet();
            ArrayList<ATNConfig> skippedStopStates = null;
            for (ATNConfig c : closureConfigs) {
                if (c.getState() instanceof RuleStopState) {
                    assert (c.getContext().isEmpty());
                    if ((!useContext || c.getReachesIntoOuterContext()) && t != -1) continue;
                    if (skippedStopStates == null) {
                        skippedStopStates = new ArrayList<ATNConfig>();
                    }
                    skippedStopStates.add(c);
                    continue;
                }
                int n = c.getState().getNumberOfOptimizedTransitions();
                for (int ti = 0; ti < n; ++ti) {
                    Transition trans = c.getState().getOptimizedTransition(ti);
                    ATNState target = this.getReachableTarget(c, trans, t);
                    if (target == null) continue;
                    reachIntermediate.add(c.transform(target, false), contextCache);
                }
            }
            if (this.optimize_unique_closure && skippedStopStates == null && t != -1 && reachIntermediate.getUniqueAlt() != 0) {
                reachIntermediate.setOutermostConfigSet(reach.isOutermostConfigSet());
                reach = reachIntermediate;
                break;
            }
            boolean collectPredicates = false;
            boolean treatEofAsEpsilon = t == -1;
            this.closure(reachIntermediate, reach, false, hasMoreContext, contextCache, treatEofAsEpsilon);
            stepIntoGlobal = reach.getDipsIntoOuterContext();
            if (t == -1) {
                reach = this.removeAllConfigsNotInRuleStopState(reach, contextCache);
            }
            if (!(skippedStopStates == null || useContext && PredictionMode.hasConfigInRuleStopState(reach))) {
                assert (!skippedStopStates.isEmpty());
                for (ATNConfig c : skippedStopStates) {
                    reach.add(c, contextCache);
                }
            }
            if (!useContext || !stepIntoGlobal) continue;
            reach.clear();
            remainingGlobalContext = this.skipTailCalls(remainingGlobalContext);
            int nextContextElement = this.getReturnState(remainingGlobalContext);
            if (contextElements == null) {
                contextElements = new IntegerList();
            }
            remainingGlobalContext = remainingGlobalContext.isEmpty() ? null : remainingGlobalContext.getParent();
            contextElements.add(nextContextElement);
            if (nextContextElement == Integer.MAX_VALUE) continue;
            for (int i = 0; i < closureConfigs.size(); ++i) {
                closureConfigs.set(i, ((ATNConfig)closureConfigs.get(i)).appendContext(nextContextElement, contextCache));
            }
        } while (useContext && stepIntoGlobal);
        if (reach.isEmpty()) {
            this.addDFAEdge(s, t, ERROR);
            return Tuple.create(ERROR, remainingGlobalContext);
        }
        DFAState result = this.addDFAEdge(dfa, s, t, contextElements, reach, contextCache);
        return Tuple.create(result, remainingGlobalContext);
    }

    @NotNull
    protected ATNConfigSet removeAllConfigsNotInRuleStopState(@NotNull ATNConfigSet configs, PredictionContextCache contextCache) {
        if (PredictionMode.allConfigsInRuleStopStates(configs)) {
            return configs;
        }
        ATNConfigSet result = new ATNConfigSet();
        for (ATNConfig config : configs) {
            if (!(config.getState() instanceof RuleStopState)) continue;
            result.add(config, contextCache);
        }
        return result;
    }

    @NotNull
    protected SimulatorState computeStartState(DFA dfa, ParserRuleContext globalContext, boolean useContext) {
        DFAState s0;
        DFAState dFAState = dfa.isPrecedenceDfa() ? dfa.getPrecedenceStartState(this.parser.getPrecedence(), useContext) : (s0 = useContext ? dfa.s0full.get() : dfa.s0.get());
        if (s0 != null) {
            if (!useContext) {
                return new SimulatorState(globalContext, s0, useContext, globalContext);
            }
            s0.setContextSensitive(this.atn);
        }
        int decision = dfa.decision;
        ATNState p = dfa.atnStartState;
        int previousContext = 0;
        ParserRuleContext remainingGlobalContext = globalContext;
        PredictionContext initialContext = useContext ? PredictionContext.EMPTY_FULL : PredictionContext.EMPTY_LOCAL;
        PredictionContextCache contextCache = new PredictionContextCache();
        if (useContext) {
            if (!this.enable_global_context_dfa) {
                while (remainingGlobalContext != null) {
                    if (remainingGlobalContext.isEmpty()) {
                        previousContext = Integer.MAX_VALUE;
                        remainingGlobalContext = null;
                        continue;
                    }
                    previousContext = this.getReturnState(remainingGlobalContext);
                    initialContext = initialContext.appendContext(previousContext, contextCache);
                    remainingGlobalContext = remainingGlobalContext.getParent();
                }
            }
            while (s0 != null && s0.isContextSensitive() && remainingGlobalContext != null) {
                DFAState next;
                if ((remainingGlobalContext = this.skipTailCalls(remainingGlobalContext)).isEmpty()) {
                    next = s0.getContextTarget(Integer.MAX_VALUE);
                    previousContext = Integer.MAX_VALUE;
                    remainingGlobalContext = null;
                } else {
                    previousContext = this.getReturnState(remainingGlobalContext);
                    next = s0.getContextTarget(previousContext);
                    initialContext = initialContext.appendContext(previousContext, contextCache);
                    remainingGlobalContext = remainingGlobalContext.getParent();
                }
                if (next == null) break;
                s0 = next;
            }
        }
        if (s0 != null && !s0.isContextSensitive()) {
            return new SimulatorState(globalContext, s0, useContext, remainingGlobalContext);
        }
        ATNConfigSet configs = new ATNConfigSet();
        while (true) {
            DFAState next;
            boolean hasMoreContext;
            ATNConfigSet reachIntermediate = new ATNConfigSet();
            int n = p.getNumberOfTransitions();
            for (int ti = 0; ti < n; ++ti) {
                ATNState target = p.transition((int)ti).target;
                reachIntermediate.add(ATNConfig.create(target, ti + 1, initialContext));
            }
            boolean bl = hasMoreContext = remainingGlobalContext != null;
            if (!hasMoreContext) {
                configs.setOutermostConfigSet(true);
            }
            boolean collectPredicates = true;
            this.closure(reachIntermediate, configs, true, hasMoreContext, contextCache, false);
            boolean stepIntoGlobal = configs.getDipsIntoOuterContext();
            if (useContext && !this.enable_global_context_dfa) {
                s0 = this.addDFAState(dfa, configs, contextCache);
                break;
            }
            if (s0 == null) {
                if (!dfa.isPrecedenceDfa()) {
                    AtomicReference<DFAState> reference = useContext ? dfa.s0full : dfa.s0;
                    if (!reference.compareAndSet(null, next = this.addDFAState(dfa, configs, contextCache))) {
                        next = reference.get();
                    }
                } else {
                    configs = this.applyPrecedenceFilter(configs, globalContext, contextCache);
                    next = this.addDFAState(dfa, configs, contextCache);
                    dfa.setPrecedenceStartState(this.parser.getPrecedence(), useContext, next);
                }
            } else {
                if (dfa.isPrecedenceDfa()) {
                    configs = this.applyPrecedenceFilter(configs, globalContext, contextCache);
                }
                next = this.addDFAState(dfa, configs, contextCache);
                s0.setContextTarget(previousContext, next);
            }
            s0 = next;
            if (!useContext || !stepIntoGlobal) break;
            next.setContextSensitive(this.atn);
            configs.clear();
            remainingGlobalContext = this.skipTailCalls(remainingGlobalContext);
            int nextContextElement = this.getReturnState(remainingGlobalContext);
            remainingGlobalContext = remainingGlobalContext.isEmpty() ? null : remainingGlobalContext.getParent();
            if (nextContextElement != Integer.MAX_VALUE) {
                initialContext = initialContext.appendContext(nextContextElement, contextCache);
            }
            previousContext = nextContextElement;
        }
        return new SimulatorState(globalContext, s0, useContext, remainingGlobalContext);
    }

    @NotNull
    protected ATNConfigSet applyPrecedenceFilter(@NotNull ATNConfigSet configs, ParserRuleContext globalContext, PredictionContextCache contextCache) {
        HashMap<Integer, PredictionContext> statesFromAlt1 = new HashMap<Integer, PredictionContext>();
        ATNConfigSet configSet = new ATNConfigSet();
        for (ATNConfig config : configs) {
            SemanticContext updatedContext;
            if (config.getAlt() != 1 || (updatedContext = config.getSemanticContext().evalPrecedence(this.parser, globalContext)) == null) continue;
            statesFromAlt1.put(config.getState().stateNumber, config.getContext());
            if (updatedContext != config.getSemanticContext()) {
                configSet.add(config.transform(config.getState(), updatedContext, false), contextCache);
                continue;
            }
            configSet.add(config, contextCache);
        }
        for (ATNConfig config : configs) {
            PredictionContext context;
            if (config.getAlt() == 1 || !config.isPrecedenceFilterSuppressed() && (context = (PredictionContext)statesFromAlt1.get(config.getState().stateNumber)) != null && context.equals(config.getContext())) continue;
            configSet.add(config, contextCache);
        }
        return configSet;
    }

    @Nullable
    protected ATNState getReachableTarget(@NotNull ATNConfig source, @NotNull Transition trans, int ttype) {
        if (trans.matches(ttype, 0, this.atn.maxTokenType)) {
            return trans.target;
        }
        return null;
    }

    protected DFAState.PredPrediction[] predicateDFAState(DFAState D, ATNConfigSet configs, int nalts) {
        BitSet conflictingAlts = this.getConflictingAltsFromConfigSet(configs);
        SemanticContext[] altToPred = this.getPredsForAmbigAlts(conflictingAlts, configs, nalts);
        DFAState.PredPrediction[] predPredictions = null;
        if (altToPred != null) {
            predPredictions = this.getPredicatePredictions(conflictingAlts, altToPred);
            D.predicates = predPredictions;
        }
        return predPredictions;
    }

    protected SemanticContext[] getPredsForAmbigAlts(@NotNull BitSet ambigAlts, @NotNull ATNConfigSet configs, int nalts) {
        SemanticContext[] altToPred = new SemanticContext[nalts + 1];
        int n = altToPred.length;
        for (ATNConfig c : configs) {
            if (!ambigAlts.get(c.getAlt())) continue;
            altToPred[c.getAlt()] = SemanticContext.or(altToPred[c.getAlt()], c.getSemanticContext());
        }
        int nPredAlts = 0;
        for (int i = 0; i < n; ++i) {
            if (altToPred[i] == null) {
                altToPred[i] = SemanticContext.NONE;
                continue;
            }
            if (altToPred[i] == SemanticContext.NONE) continue;
            ++nPredAlts;
        }
        if (nPredAlts == 0) {
            altToPred = null;
        }
        return altToPred;
    }

    protected DFAState.PredPrediction[] getPredicatePredictions(BitSet ambigAlts, SemanticContext[] altToPred) {
        ArrayList<DFAState.PredPrediction> pairs = new ArrayList<DFAState.PredPrediction>();
        boolean containsPredicate = false;
        for (int i = 1; i < altToPred.length; ++i) {
            SemanticContext pred = altToPred[i];
            assert (pred != null);
            if (ambigAlts != null && ambigAlts.get(i) && pred == SemanticContext.NONE) {
                pairs.add(new DFAState.PredPrediction(pred, i));
                continue;
            }
            if (pred == SemanticContext.NONE) continue;
            containsPredicate = true;
            pairs.add(new DFAState.PredPrediction(pred, i));
        }
        if (!containsPredicate) {
            return null;
        }
        return pairs.toArray(new DFAState.PredPrediction[pairs.size()]);
    }

    protected BitSet evalSemanticContext(@NotNull DFAState.PredPrediction[] predPredictions, ParserRuleContext outerContext, boolean complete) {
        BitSet predictions = new BitSet();
        for (DFAState.PredPrediction pair : predPredictions) {
            if (pair.pred == SemanticContext.NONE) {
                predictions.set(pair.alt);
                if (complete) continue;
                break;
            }
            boolean evaluatedResult = this.evalSemanticContext(pair.pred, outerContext, pair.alt);
            if (!evaluatedResult) continue;
            predictions.set(pair.alt);
            if (!complete) break;
        }
        return predictions;
    }

    protected boolean evalSemanticContext(@NotNull SemanticContext pred, ParserRuleContext parserCallStack, int alt) {
        return pred.eval(this.parser, parserCallStack);
    }

    protected void closure(ATNConfigSet sourceConfigs, @NotNull ATNConfigSet configs, boolean collectPredicates, boolean hasMoreContext, @Nullable PredictionContextCache contextCache, boolean treatEofAsEpsilon) {
        if (contextCache == null) {
            contextCache = PredictionContextCache.UNCACHED;
        }
        ATNConfigSet currentConfigs = sourceConfigs;
        HashSet<ATNConfig> closureBusy = new HashSet<ATNConfig>();
        while (currentConfigs.size() > 0) {
            ATNConfigSet intermediate = new ATNConfigSet();
            for (ATNConfig config : currentConfigs) {
                this.closure(config, configs, intermediate, closureBusy, collectPredicates, hasMoreContext, contextCache, 0, treatEofAsEpsilon);
            }
            currentConfigs = intermediate;
        }
    }

    protected void closure(@NotNull ATNConfig config, @NotNull ATNConfigSet configs, @Nullable ATNConfigSet intermediate, @NotNull Set<ATNConfig> closureBusy, boolean collectPredicates, boolean hasMoreContexts, @NotNull PredictionContextCache contextCache, int depth, boolean treatEofAsEpsilon) {
        ATNState p;
        if (config.getState() instanceof RuleStopState) {
            if (!config.getContext().isEmpty()) {
                boolean hasEmpty = config.getContext().hasEmpty();
                int nonEmptySize = config.getContext().size() - (hasEmpty ? 1 : 0);
                for (int i = 0; i < nonEmptySize; ++i) {
                    PredictionContext newContext = config.getContext().getParent(i);
                    ATNState returnState = this.atn.states.get(config.getContext().getReturnState(i));
                    ATNConfig c = ATNConfig.create(returnState, config.getAlt(), newContext, config.getSemanticContext());
                    c.setOuterContextDepth(config.getOuterContextDepth());
                    c.setPrecedenceFilterSuppressed(config.isPrecedenceFilterSuppressed());
                    assert (depth > Integer.MIN_VALUE);
                    this.closure(c, configs, intermediate, closureBusy, collectPredicates, hasMoreContexts, contextCache, depth - 1, treatEofAsEpsilon);
                }
                if (!hasEmpty || !hasMoreContexts) {
                    return;
                }
                config = config.transform(config.getState(), PredictionContext.EMPTY_LOCAL, false);
            } else {
                if (!hasMoreContexts) {
                    configs.add(config, contextCache);
                    return;
                }
                if (config.getContext() == PredictionContext.EMPTY_FULL) {
                    config = config.transform(config.getState(), PredictionContext.EMPTY_LOCAL, false);
                } else if (!config.getReachesIntoOuterContext() && PredictionContext.isEmptyLocal(config.getContext())) {
                    configs.add(config, contextCache);
                }
            }
        }
        if (!(p = config.getState()).onlyHasEpsilonTransitions()) {
            configs.add(config, contextCache);
        }
        for (int i = 0; i < p.getNumberOfOptimizedTransitions(); ++i) {
            boolean continueCollecting;
            Transition t;
            ATNConfig c;
            if (i == 0 && p.getStateType() == 10 && ((StarLoopEntryState)p).precedenceRuleDecision && !config.getContext().hasEmpty()) {
                StarLoopEntryState precedenceDecision = (StarLoopEntryState)p;
                boolean suppress = true;
                for (int j = 0; j < config.getContext().size(); ++j) {
                    if (precedenceDecision.precedenceLoopbackStates.get(config.getContext().getReturnState(j))) continue;
                    suppress = false;
                    break;
                }
                if (suppress) continue;
            }
            if ((c = this.getEpsilonTarget(config, t, continueCollecting = !((t = p.getOptimizedTransition(i)) instanceof ActionTransition) && collectPredicates, depth == 0, contextCache, treatEofAsEpsilon)) == null) continue;
            if (t instanceof RuleTransition && intermediate != null && !collectPredicates) {
                intermediate.add(c, contextCache);
                continue;
            }
            int newDepth = depth;
            if (config.getState() instanceof RuleStopState) {
                int outermostPrecedenceReturn;
                if (this.dfa != null && this.dfa.isPrecedenceDfa() && (outermostPrecedenceReturn = ((EpsilonTransition)t).outermostPrecedenceReturn()) == this.dfa.atnStartState.ruleIndex) {
                    c.setPrecedenceFilterSuppressed(true);
                }
                c.setOuterContextDepth(c.getOuterContextDepth() + 1);
                if (!closureBusy.add(c)) continue;
                assert (newDepth > Integer.MIN_VALUE);
                --newDepth;
            } else if (t instanceof RuleTransition) {
                if (this.optimize_tail_calls && ((RuleTransition)t).optimizedTailCall && (!this.tail_call_preserves_sll || !PredictionContext.isEmptyLocal(config.getContext()))) {
                    assert (c.getContext() == config.getContext());
                    if (newDepth == 0) {
                        --newDepth;
                        if (!this.tail_call_preserves_sll && PredictionContext.isEmptyLocal(config.getContext())) {
                            c.setOuterContextDepth(c.getOuterContextDepth() + 1);
                        }
                    }
                } else if (newDepth >= 0) {
                    ++newDepth;
                }
            } else if (!t.isEpsilon() && !closureBusy.add(c)) continue;
            this.closure(c, configs, intermediate, closureBusy, continueCollecting, hasMoreContexts, contextCache, newDepth, treatEofAsEpsilon);
        }
    }

    @NotNull
    public String getRuleName(int index) {
        if (this.parser != null && index >= 0) {
            return this.parser.getRuleNames()[index];
        }
        return "<rule " + index + ">";
    }

    @Nullable
    protected ATNConfig getEpsilonTarget(@NotNull ATNConfig config, @NotNull Transition t, boolean collectPredicates, boolean inContext, PredictionContextCache contextCache, boolean treatEofAsEpsilon) {
        switch (t.getSerializationType()) {
            case 3: {
                return this.ruleTransition(config, (RuleTransition)t, contextCache);
            }
            case 10: {
                return this.precedenceTransition(config, (PrecedencePredicateTransition)t, collectPredicates, inContext);
            }
            case 4: {
                return this.predTransition(config, (PredicateTransition)t, collectPredicates, inContext);
            }
            case 6: {
                return this.actionTransition(config, (ActionTransition)t);
            }
            case 1: {
                return config.transform(t.target, false);
            }
            case 2: 
            case 5: 
            case 7: {
                if (treatEofAsEpsilon && t.matches(-1, 0, 1)) {
                    return config.transform(t.target, false);
                }
                return null;
            }
        }
        return null;
    }

    @NotNull
    protected ATNConfig actionTransition(@NotNull ATNConfig config, @NotNull ActionTransition t) {
        return config.transform(t.target, false);
    }

    @Nullable
    protected ATNConfig precedenceTransition(@NotNull ATNConfig config, @NotNull PrecedencePredicateTransition pt, boolean collectPredicates, boolean inContext) {
        ATNConfig c;
        if (collectPredicates && inContext) {
            SemanticContext newSemCtx = SemanticContext.and(config.getSemanticContext(), pt.getPredicate());
            c = config.transform(pt.target, newSemCtx, false);
        } else {
            c = config.transform(pt.target, false);
        }
        return c;
    }

    @Nullable
    protected ATNConfig predTransition(@NotNull ATNConfig config, @NotNull PredicateTransition pt, boolean collectPredicates, boolean inContext) {
        ATNConfig c;
        if (collectPredicates && (!pt.isCtxDependent || pt.isCtxDependent && inContext)) {
            SemanticContext newSemCtx = SemanticContext.and(config.getSemanticContext(), pt.getPredicate());
            c = config.transform(pt.target, newSemCtx, false);
        } else {
            c = config.transform(pt.target, false);
        }
        return c;
    }

    @NotNull
    protected ATNConfig ruleTransition(@NotNull ATNConfig config, @NotNull RuleTransition t, @Nullable PredictionContextCache contextCache) {
        ATNState returnState = t.followState;
        PredictionContext newContext = this.optimize_tail_calls && t.optimizedTailCall && (!this.tail_call_preserves_sll || !PredictionContext.isEmptyLocal(config.getContext())) ? config.getContext() : (contextCache != null ? contextCache.getChild(config.getContext(), returnState.stateNumber) : config.getContext().getChild(returnState.stateNumber));
        return config.transform(t.target, newContext, false);
    }

    private ConflictInfo isConflicted(@NotNull ATNConfigSet configset, PredictionContextCache contextCache) {
        ATNConfig config;
        int i;
        if (configset.getUniqueAlt() != 0 || configset.size() <= 1) {
            return null;
        }
        ArrayList<ATNConfig> configs = new ArrayList<ATNConfig>(configset);
        Collections.sort(configs, STATE_ALT_SORT_COMPARATOR);
        boolean exact = !configset.getDipsIntoOuterContext();
        BitSet alts = new BitSet();
        int minAlt = ((ATNConfig)configs.get(0)).getAlt();
        alts.set(minAlt);
        int currentState = ((ATNConfig)configs.get(0)).getState().getNonStopStateNumber();
        for (ATNConfig config2 : configs) {
            int stateNumber = config2.getState().getNonStopStateNumber();
            if (stateNumber == currentState) continue;
            if (config2.getAlt() != minAlt) {
                return null;
            }
            currentState = stateNumber;
        }
        if (exact) {
            ATNConfig config3;
            currentState = ((ATNConfig)configs.get(0)).getState().getNonStopStateNumber();
            BitSet representedAlts = new BitSet();
            int maxAlt = minAlt;
            Iterator i$ = configs.iterator();
            while (i$.hasNext() && (config3 = (ATNConfig)i$.next()).getState().getNonStopStateNumber() == currentState) {
                int alt = config3.getAlt();
                representedAlts.set(alt);
                maxAlt = alt;
            }
            currentState = ((ATNConfig)configs.get(0)).getState().getNonStopStateNumber();
            int currentAlt = minAlt;
            for (ATNConfig config4 : configs) {
                int stateNumber = config4.getState().getNonStopStateNumber();
                int alt = config4.getAlt();
                if (stateNumber != currentState) {
                    if (currentAlt != maxAlt) {
                        exact = false;
                        break;
                    }
                    currentState = stateNumber;
                    currentAlt = minAlt;
                    continue;
                }
                if (alt == currentAlt) continue;
                if (alt != representedAlts.nextSetBit(currentAlt + 1)) {
                    exact = false;
                    break;
                }
                currentAlt = alt;
            }
        }
        currentState = ((ATNConfig)configs.get(0)).getState().getNonStopStateNumber();
        int firstIndexCurrentState = 0;
        int lastIndexCurrentStateMinAlt = 0;
        PredictionContext joinedCheckContext = ((ATNConfig)configs.get(0)).getContext();
        for (i = 1; i < configs.size() && (config = (ATNConfig)configs.get(i)).getAlt() == minAlt && config.getState().getNonStopStateNumber() == currentState; ++i) {
            lastIndexCurrentStateMinAlt = i;
            joinedCheckContext = contextCache.join(joinedCheckContext, ((ATNConfig)configs.get(i)).getContext());
        }
        for (i = lastIndexCurrentStateMinAlt + 1; i < configs.size(); ++i) {
            ATNConfig config2;
            ATNConfig config5 = (ATNConfig)configs.get(i);
            ATNState state = config5.getState();
            alts.set(config5.getAlt());
            if (state.getNonStopStateNumber() != currentState) {
                ATNConfig config22;
                currentState = state.getNonStopStateNumber();
                firstIndexCurrentState = i;
                lastIndexCurrentStateMinAlt = i;
                joinedCheckContext = config5.getContext();
                int j = firstIndexCurrentState + 1;
                while (j < configs.size() && (config22 = (ATNConfig)configs.get(j)).getAlt() == minAlt && config22.getState().getNonStopStateNumber() == currentState) {
                    lastIndexCurrentStateMinAlt = j++;
                    joinedCheckContext = contextCache.join(joinedCheckContext, config22.getContext());
                }
                i = lastIndexCurrentStateMinAlt;
                continue;
            }
            PredictionContext joinedCheckContext2 = config5.getContext();
            int currentAlt = config5.getAlt();
            int lastIndexCurrentStateCurrentAlt = i;
            int j = lastIndexCurrentStateCurrentAlt + 1;
            while (j < configs.size() && (config2 = (ATNConfig)configs.get(j)).getAlt() == currentAlt && config2.getState().getNonStopStateNumber() == currentState) {
                lastIndexCurrentStateCurrentAlt = j++;
                joinedCheckContext2 = contextCache.join(joinedCheckContext2, config2.getContext());
            }
            i = lastIndexCurrentStateCurrentAlt;
            PredictionContext check = contextCache.join(joinedCheckContext, joinedCheckContext2);
            if (!joinedCheckContext.equals(check)) {
                return null;
            }
            exact = exact && joinedCheckContext.equals(joinedCheckContext2);
        }
        return new ConflictInfo(alts, exact);
    }

    protected BitSet getConflictingAltsFromConfigSet(ATNConfigSet configs) {
        BitSet conflictingAlts = configs.getConflictingAlts();
        if (conflictingAlts == null && configs.getUniqueAlt() != 0) {
            conflictingAlts = new BitSet();
            conflictingAlts.set(configs.getUniqueAlt());
        }
        return conflictingAlts;
    }

    @NotNull
    public String getTokenName(int t) {
        if (t == -1) {
            return "EOF";
        }
        Vocabulary vocabulary = this.parser != null ? this.parser.getVocabulary() : VocabularyImpl.EMPTY_VOCABULARY;
        String displayName = vocabulary.getDisplayName(t);
        if (displayName.equals(Integer.toString(t))) {
            return displayName;
        }
        return displayName + "<" + t + ">";
    }

    public String getLookaheadName(TokenStream input) {
        return this.getTokenName(input.LA(1));
    }

    public void dumpDeadEndConfigs(@NotNull NoViableAltException nvae) {
        System.err.println("dead end configs: ");
        for (ATNConfig c : nvae.getDeadEndConfigs()) {
            String trans = "no edges";
            if (c.getState().getNumberOfOptimizedTransitions() > 0) {
                Transition t = c.getState().getOptimizedTransition(0);
                if (t instanceof AtomTransition) {
                    AtomTransition at = (AtomTransition)t;
                    trans = "Atom " + this.getTokenName(at.label);
                } else if (t instanceof SetTransition) {
                    SetTransition st = (SetTransition)t;
                    boolean not = st instanceof NotSetTransition;
                    trans = (not ? "~" : "") + "Set " + st.set.toString();
                }
            }
            System.err.println(c.toString(this.parser, true) + ":" + trans);
        }
    }

    @NotNull
    protected NoViableAltException noViableAlt(@NotNull TokenStream input, @NotNull ParserRuleContext outerContext, @NotNull ATNConfigSet configs, int startIndex) {
        return new NoViableAltException(this.parser, input, input.get(startIndex), input.LT(1), configs, outerContext);
    }

    protected int getUniqueAlt(@NotNull Collection<ATNConfig> configs) {
        int alt = 0;
        for (ATNConfig c : configs) {
            if (alt == 0) {
                alt = c.getAlt();
                continue;
            }
            if (c.getAlt() == alt) continue;
            return 0;
        }
        return alt;
    }

    protected boolean configWithAltAtStopState(@NotNull Collection<ATNConfig> configs, int alt) {
        for (ATNConfig c : configs) {
            if (c.getAlt() != alt || !(c.getState() instanceof RuleStopState)) continue;
            return true;
        }
        return false;
    }

    @NotNull
    protected DFAState addDFAEdge(@NotNull DFA dfa, @NotNull DFAState fromState, int t, IntegerList contextTransitions, @NotNull ATNConfigSet toConfigs, PredictionContextCache contextCache) {
        assert (contextTransitions == null || contextTransitions.isEmpty() || dfa.isContextSensitive());
        DFAState from = fromState;
        DFAState to = this.addDFAState(dfa, toConfigs, contextCache);
        if (contextTransitions != null) {
            for (int context : contextTransitions.toArray()) {
                if (context == Integer.MAX_VALUE && from.configs.isOutermostConfigSet()) continue;
                from.setContextSensitive(this.atn);
                from.setContextSymbol(t);
                DFAState next = from.getContextTarget(context);
                if (next != null) {
                    from = next;
                    continue;
                }
                next = this.addDFAContextState(dfa, from.configs, context, contextCache);
                assert (context != Integer.MAX_VALUE || next.configs.isOutermostConfigSet());
                from.setContextTarget(context, next);
                from = next;
            }
        }
        this.addDFAEdge(from, t, to);
        return to;
    }

    protected void addDFAEdge(@Nullable DFAState p, int t, @Nullable DFAState q) {
        if (p != null) {
            p.setTarget(t, q);
        }
    }

    @NotNull
    protected DFAState addDFAContextState(@NotNull DFA dfa, @NotNull ATNConfigSet configs, int returnContext, PredictionContextCache contextCache) {
        if (returnContext != Integer.MAX_VALUE) {
            ATNConfigSet contextConfigs = new ATNConfigSet();
            for (ATNConfig config : configs) {
                contextConfigs.add(config.appendContext(returnContext, contextCache));
            }
            return this.addDFAState(dfa, contextConfigs, contextCache);
        }
        assert (!configs.isOutermostConfigSet()) : "Shouldn't be adding a duplicate edge.";
        configs = configs.clone(true);
        configs.setOutermostConfigSet(true);
        return this.addDFAState(dfa, configs, contextCache);
    }

    @NotNull
    protected DFAState addDFAState(@NotNull DFA dfa, @NotNull ATNConfigSet configs, PredictionContextCache contextCache) {
        boolean enableDfa;
        boolean bl = enableDfa = this.enable_global_context_dfa || !configs.isOutermostConfigSet();
        if (enableDfa) {
            DFAState proposed;
            DFAState existing;
            if (!configs.isReadOnly()) {
                configs.optimizeConfigs(this);
            }
            if ((existing = (DFAState)dfa.states.get(proposed = this.createDFAState(dfa, configs))) != null) {
                return existing;
            }
        }
        if (!configs.isReadOnly() && configs.getConflictInfo() == null) {
            configs.setConflictInfo(this.isConflicted(configs, contextCache));
        }
        DFAState newState = this.createDFAState(dfa, configs.clone(true));
        DecisionState decisionState = this.atn.getDecisionState(dfa.decision);
        int predictedAlt = this.getUniqueAlt(configs);
        if (predictedAlt != 0) {
            newState.setAcceptState(new AcceptStateInfo(predictedAlt));
        } else if (configs.getConflictingAlts() != null) {
            newState.setAcceptState(new AcceptStateInfo(newState.configs.getConflictingAlts().nextSetBit(0)));
        }
        if (newState.isAcceptState() && configs.hasSemanticContext()) {
            this.predicateDFAState(newState, configs, decisionState.getNumberOfTransitions());
        }
        if (!enableDfa) {
            return newState;
        }
        DFAState added = dfa.addState(newState);
        return added;
    }

    @NotNull
    protected DFAState createDFAState(@NotNull DFA dfa, @NotNull ATNConfigSet configs) {
        return new DFAState(dfa, configs);
    }

    protected void reportAttemptingFullContext(@NotNull DFA dfa, @Nullable BitSet conflictingAlts, @NotNull SimulatorState conflictState, int startIndex, int stopIndex) {
        if (this.parser != null) {
            this.parser.getErrorListenerDispatch().reportAttemptingFullContext(this.parser, dfa, startIndex, stopIndex, conflictingAlts, conflictState);
        }
    }

    protected void reportContextSensitivity(@NotNull DFA dfa, int prediction, @NotNull SimulatorState acceptState, int startIndex, int stopIndex) {
        if (this.parser != null) {
            this.parser.getErrorListenerDispatch().reportContextSensitivity(this.parser, dfa, startIndex, stopIndex, prediction, acceptState);
        }
    }

    protected void reportAmbiguity(@NotNull DFA dfa, DFAState D, int startIndex, int stopIndex, boolean exact, @NotNull BitSet ambigAlts, @NotNull ATNConfigSet configs) {
        if (this.parser != null) {
            this.parser.getErrorListenerDispatch().reportAmbiguity(this.parser, dfa, startIndex, stopIndex, exact, ambigAlts, configs);
        }
    }

    protected final int getReturnState(RuleContext context) {
        if (context.isEmpty()) {
            return Integer.MAX_VALUE;
        }
        ATNState state = this.atn.states.get(context.invokingState);
        RuleTransition transition = (RuleTransition)state.transition(0);
        return transition.followState.stateNumber;
    }

    protected final ParserRuleContext skipTailCalls(ParserRuleContext context) {
        if (!this.optimize_tail_calls) {
            return context;
        }
        while (!context.isEmpty()) {
            ATNState state = this.atn.states.get(context.invokingState);
            assert (state.getNumberOfTransitions() == 1 && state.transition(0).getSerializationType() == 3);
            RuleTransition transition = (RuleTransition)state.transition(0);
            if (!transition.tailCall) break;
            context = context.getParent();
        }
        return context;
    }

    public Parser getParser() {
        return this.parser;
    }
}

