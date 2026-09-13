/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.Parser;
import groovyjarjarantlr4.v4.runtime.ParserRuleContext;
import groovyjarjarantlr4.v4.runtime.TokenStream;
import groovyjarjarantlr4.v4.runtime.atn.ATNConfigSet;
import groovyjarjarantlr4.v4.runtime.atn.AmbiguityInfo;
import groovyjarjarantlr4.v4.runtime.atn.ContextSensitivityInfo;
import groovyjarjarantlr4.v4.runtime.atn.DecisionInfo;
import groovyjarjarantlr4.v4.runtime.atn.ErrorInfo;
import groovyjarjarantlr4.v4.runtime.atn.LookaheadEventInfo;
import groovyjarjarantlr4.v4.runtime.atn.ParserATNSimulator;
import groovyjarjarantlr4.v4.runtime.atn.PredicateEvalInfo;
import groovyjarjarantlr4.v4.runtime.atn.PredictionContextCache;
import groovyjarjarantlr4.v4.runtime.atn.SemanticContext;
import groovyjarjarantlr4.v4.runtime.atn.SimulatorState;
import groovyjarjarantlr4.v4.runtime.dfa.DFA;
import groovyjarjarantlr4.v4.runtime.dfa.DFAState;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Tuple2;
import java.util.BitSet;

public class ProfilingATNSimulator
extends ParserATNSimulator {
    protected final DecisionInfo[] decisions;
    protected int numDecisions;
    protected TokenStream _input;
    protected int _startIndex;
    protected int _sllStopIndex;
    protected int _llStopIndex;
    protected int currentDecision;
    protected SimulatorState currentState;
    protected int conflictingAltResolvedBySLL;

    public ProfilingATNSimulator(Parser parser) {
        super(parser, ((ParserATNSimulator)parser.getInterpreter()).atn);
        this.optimize_ll1 = false;
        this.reportAmbiguities = true;
        this.numDecisions = this.atn.decisionToState.size();
        this.decisions = new DecisionInfo[this.numDecisions];
        for (int i = 0; i < this.numDecisions; ++i) {
            this.decisions[i] = new DecisionInfo(i);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int adaptivePredict(TokenStream input, int decision, ParserRuleContext outerContext) {
        try {
            this._input = input;
            this._startIndex = input.index();
            this._sllStopIndex = this._startIndex - 1;
            this._llStopIndex = -1;
            this.currentDecision = decision;
            this.currentState = null;
            this.conflictingAltResolvedBySLL = 0;
            long start = System.nanoTime();
            int alt = super.adaptivePredict(input, decision, outerContext);
            long stop = System.nanoTime();
            this.decisions[decision].timeInPrediction += stop - start;
            ++this.decisions[decision].invocations;
            int SLL_k = this._sllStopIndex - this._startIndex + 1;
            this.decisions[decision].SLL_TotalLook += (long)SLL_k;
            long l = this.decisions[decision].SLL_MinLook = this.decisions[decision].SLL_MinLook == 0L ? (long)SLL_k : Math.min(this.decisions[decision].SLL_MinLook, (long)SLL_k);
            if ((long)SLL_k > this.decisions[decision].SLL_MaxLook) {
                this.decisions[decision].SLL_MaxLook = SLL_k;
                this.decisions[decision].SLL_MaxLookEvent = new LookaheadEventInfo(decision, null, alt, input, this._startIndex, this._sllStopIndex, false);
            }
            if (this._llStopIndex >= 0) {
                int LL_k = this._llStopIndex - this._startIndex + 1;
                this.decisions[decision].LL_TotalLook += (long)LL_k;
                long l2 = this.decisions[decision].LL_MinLook = this.decisions[decision].LL_MinLook == 0L ? (long)LL_k : Math.min(this.decisions[decision].LL_MinLook, (long)LL_k);
                if ((long)LL_k > this.decisions[decision].LL_MaxLook) {
                    this.decisions[decision].LL_MaxLook = LL_k;
                    this.decisions[decision].LL_MaxLookEvent = new LookaheadEventInfo(decision, null, alt, input, this._startIndex, this._llStopIndex, true);
                }
            }
            int n = alt;
            return n;
        }
        finally {
            this._input = null;
            this.currentDecision = -1;
        }
    }

    @Override
    protected SimulatorState getStartState(DFA dfa, TokenStream input, ParserRuleContext outerContext, boolean useContext) {
        SimulatorState state;
        this.currentState = state = super.getStartState(dfa, input, outerContext, useContext);
        return state;
    }

    @Override
    protected SimulatorState computeStartState(DFA dfa, ParserRuleContext globalContext, boolean useContext) {
        SimulatorState state;
        this.currentState = state = super.computeStartState(dfa, globalContext, useContext);
        return state;
    }

    @Override
    protected SimulatorState computeReachSet(DFA dfa, SimulatorState previous, int t, PredictionContextCache contextCache) {
        SimulatorState reachState = super.computeReachSet(dfa, previous, t, contextCache);
        if (reachState == null) {
            this.decisions[this.currentDecision].errors.add(new ErrorInfo(this.currentDecision, previous, this._input, this._startIndex, this._input.index()));
        }
        this.currentState = reachState;
        return reachState;
    }

    @Override
    protected DFAState getExistingTargetState(DFAState previousD, int t) {
        if (this.currentState.useContext) {
            this._llStopIndex = this._input.index();
        } else {
            this._sllStopIndex = this._input.index();
        }
        DFAState existingTargetState = super.getExistingTargetState(previousD, t);
        if (existingTargetState != null) {
            this.currentState = new SimulatorState(this.currentState.outerContext, existingTargetState, this.currentState.useContext, this.currentState.remainingOuterContext);
            if (this.currentState.useContext) {
                ++this.decisions[this.currentDecision].LL_DFATransitions;
            } else {
                ++this.decisions[this.currentDecision].SLL_DFATransitions;
            }
            if (existingTargetState == ERROR) {
                SimulatorState state = new SimulatorState(this.currentState.outerContext, previousD, this.currentState.useContext, this.currentState.remainingOuterContext);
                this.decisions[this.currentDecision].errors.add(new ErrorInfo(this.currentDecision, state, this._input, this._startIndex, this._input.index()));
            }
        }
        return existingTargetState;
    }

    @Override
    protected Tuple2<DFAState, ParserRuleContext> computeTargetState(DFA dfa, DFAState s, ParserRuleContext remainingGlobalContext, int t, boolean useContext, PredictionContextCache contextCache) {
        Tuple2<DFAState, ParserRuleContext> targetState = super.computeTargetState(dfa, s, remainingGlobalContext, t, useContext, contextCache);
        if (useContext) {
            ++this.decisions[this.currentDecision].LL_ATNTransitions;
        } else {
            ++this.decisions[this.currentDecision].SLL_ATNTransitions;
        }
        return targetState;
    }

    @Override
    protected boolean evalSemanticContext(SemanticContext pred, ParserRuleContext parserCallStack, int alt) {
        boolean result = super.evalSemanticContext(pred, parserCallStack, alt);
        if (!(pred instanceof SemanticContext.PrecedencePredicate)) {
            boolean fullContext = this._llStopIndex >= 0;
            int stopIndex = fullContext ? this._llStopIndex : this._sllStopIndex;
            this.decisions[this.currentDecision].predicateEvals.add(new PredicateEvalInfo(this.currentState, this.currentDecision, this._input, this._startIndex, stopIndex, pred, result, alt));
        }
        return result;
    }

    @Override
    protected void reportContextSensitivity(DFA dfa, int prediction, SimulatorState acceptState, int startIndex, int stopIndex) {
        if (prediction != this.conflictingAltResolvedBySLL) {
            this.decisions[this.currentDecision].contextSensitivities.add(new ContextSensitivityInfo(this.currentDecision, acceptState, this._input, startIndex, stopIndex));
        }
        super.reportContextSensitivity(dfa, prediction, acceptState, startIndex, stopIndex);
    }

    @Override
    protected void reportAttemptingFullContext(DFA dfa, BitSet conflictingAlts, SimulatorState conflictState, int startIndex, int stopIndex) {
        this.conflictingAltResolvedBySLL = conflictingAlts != null ? conflictingAlts.nextSetBit(0) : conflictState.s0.configs.getRepresentedAlternatives().nextSetBit(0);
        ++this.decisions[this.currentDecision].LL_Fallback;
        super.reportAttemptingFullContext(dfa, conflictingAlts, conflictState, startIndex, stopIndex);
    }

    @Override
    protected void reportAmbiguity(@NotNull DFA dfa, DFAState D, int startIndex, int stopIndex, boolean exact, @NotNull BitSet ambigAlts, @NotNull ATNConfigSet configs) {
        int prediction = ambigAlts != null ? ambigAlts.nextSetBit(0) : configs.getRepresentedAlternatives().nextSetBit(0);
        if (this.conflictingAltResolvedBySLL != 0 && prediction != this.conflictingAltResolvedBySLL) {
            this.decisions[this.currentDecision].contextSensitivities.add(new ContextSensitivityInfo(this.currentDecision, this.currentState, this._input, startIndex, stopIndex));
        }
        this.decisions[this.currentDecision].ambiguities.add(new AmbiguityInfo(this.currentDecision, this.currentState, ambigAlts, this._input, startIndex, stopIndex));
        super.reportAmbiguity(dfa, D, startIndex, stopIndex, exact, ambigAlts, configs);
    }

    public DecisionInfo[] getDecisionInfo() {
        return this.decisions;
    }

    public SimulatorState getCurrentState() {
        return this.currentState;
    }
}

