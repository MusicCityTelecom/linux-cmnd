/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.CharStream;
import groovyjarjarantlr4.v4.runtime.Lexer;
import groovyjarjarantlr4.v4.runtime.LexerNoViableAltException;
import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNConfig;
import groovyjarjarantlr4.v4.runtime.atn.ATNConfigSet;
import groovyjarjarantlr4.v4.runtime.atn.ATNSimulator;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.ActionTransition;
import groovyjarjarantlr4.v4.runtime.atn.LexerActionExecutor;
import groovyjarjarantlr4.v4.runtime.atn.OrderedATNConfigSet;
import groovyjarjarantlr4.v4.runtime.atn.PredicateTransition;
import groovyjarjarantlr4.v4.runtime.atn.PredictionContext;
import groovyjarjarantlr4.v4.runtime.atn.RuleStopState;
import groovyjarjarantlr4.v4.runtime.atn.RuleTransition;
import groovyjarjarantlr4.v4.runtime.atn.Transition;
import groovyjarjarantlr4.v4.runtime.dfa.AcceptStateInfo;
import groovyjarjarantlr4.v4.runtime.dfa.DFA;
import groovyjarjarantlr4.v4.runtime.dfa.DFAState;
import groovyjarjarantlr4.v4.runtime.misc.Interval;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;

public class LexerATNSimulator
extends ATNSimulator {
    public static final boolean debug = false;
    public static final boolean dfa_debug = false;
    public static final int MIN_DFA_EDGE = 0;
    public static final int MAX_DFA_EDGE = 0x10FFFF;
    public boolean optimize_tail_calls = true;
    @Nullable
    protected final Lexer recog;
    protected int startIndex = -1;
    protected int line = 1;
    protected int charPositionInLine = 0;
    protected int mode = 0;
    @NotNull
    protected final SimState prevAccept = new SimState();
    @Deprecated
    public static int match_calls = 0;

    public LexerATNSimulator(@NotNull ATN atn) {
        this(null, atn);
    }

    public LexerATNSimulator(@Nullable Lexer recog, @NotNull ATN atn) {
        super(atn);
        this.recog = recog;
    }

    public void copyState(@NotNull LexerATNSimulator simulator) {
        this.charPositionInLine = simulator.charPositionInLine;
        this.line = simulator.line;
        this.mode = simulator.mode;
        this.startIndex = simulator.startIndex;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int match(@NotNull CharStream input, int mode) {
        this.mode = mode;
        int mark = input.mark();
        try {
            this.startIndex = input.index();
            this.prevAccept.reset();
            DFAState s0 = this.atn.modeToDFA[mode].s0.get();
            if (s0 == null) {
                int n = this.matchATN(input);
                return n;
            }
            int n = this.execATN(input, s0);
            return n;
        }
        finally {
            input.release(mark);
        }
    }

    @Override
    public void reset() {
        this.prevAccept.reset();
        this.startIndex = -1;
        this.line = 1;
        this.charPositionInLine = 0;
        this.mode = 0;
    }

    protected int matchATN(@NotNull CharStream input) {
        ATNState startState = this.atn.modeToStartState.get(this.mode);
        int old_mode = this.mode;
        ATNConfigSet s0_closure = this.computeStartState(input, startState);
        boolean suppressEdge = s0_closure.hasSemanticContext();
        if (suppressEdge) {
            s0_closure.clearExplicitSemanticContext();
        }
        DFAState next = this.addDFAState(s0_closure);
        if (!suppressEdge && !this.atn.modeToDFA[this.mode].s0.compareAndSet(null, next)) {
            next = this.atn.modeToDFA[this.mode].s0.get();
        }
        int predict = this.execATN(input, next);
        return predict;
    }

    protected int execATN(@NotNull CharStream input, @NotNull DFAState ds0) {
        if (ds0.isAcceptState()) {
            this.captureSimState(this.prevAccept, input, ds0);
        }
        int t = input.LA(1);
        DFAState s = ds0;
        while (true) {
            DFAState target;
            if ((target = this.getExistingTargetState(s, t)) == null) {
                target = this.computeTargetState(input, s, t);
            }
            if (target == ERROR) break;
            if (t != -1) {
                this.consume(input);
            }
            if (target.isAcceptState()) {
                this.captureSimState(this.prevAccept, input, target);
                if (t == -1) break;
            }
            t = input.LA(1);
            s = target;
        }
        return this.failOrAccept(this.prevAccept, input, s.configs, t);
    }

    @Nullable
    protected DFAState getExistingTargetState(@NotNull DFAState s, int t) {
        DFAState target = s.getTarget(t);
        return target;
    }

    @NotNull
    protected DFAState computeTargetState(@NotNull CharStream input, @NotNull DFAState s, int t) {
        OrderedATNConfigSet reach = new OrderedATNConfigSet();
        this.getReachableConfigSet(input, s.configs, reach, t);
        if (reach.isEmpty()) {
            if (!reach.hasSemanticContext()) {
                this.addDFAEdge(s, t, ERROR);
            }
            return ERROR;
        }
        return this.addDFAEdge(s, t, reach);
    }

    protected int failOrAccept(SimState prevAccept, CharStream input, ATNConfigSet reach, int t) {
        if (prevAccept.dfaState != null) {
            LexerActionExecutor lexerActionExecutor = prevAccept.dfaState.getLexerActionExecutor();
            this.accept(input, lexerActionExecutor, this.startIndex, prevAccept.index, prevAccept.line, prevAccept.charPos);
            return prevAccept.dfaState.getPrediction();
        }
        if (t == -1 && input.index() == this.startIndex) {
            return -1;
        }
        throw new LexerNoViableAltException(this.recog, input, this.startIndex, reach);
    }

    protected void getReachableConfigSet(@NotNull CharStream input, @NotNull ATNConfigSet closure, @NotNull ATNConfigSet reach, int t) {
        int skipAlt = 0;
        block0: for (ATNConfig c : closure) {
            boolean currentAltReachedAcceptState;
            boolean bl = currentAltReachedAcceptState = c.getAlt() == skipAlt;
            if (currentAltReachedAcceptState && c.hasPassedThroughNonGreedyDecision()) continue;
            int n = c.getState().getNumberOfOptimizedTransitions();
            for (int ti = 0; ti < n; ++ti) {
                boolean treatEofAsEpsilon;
                Transition trans = c.getState().getOptimizedTransition(ti);
                ATNState target = this.getReachableTarget(trans, t);
                if (target == null) continue;
                LexerActionExecutor lexerActionExecutor = c.getLexerActionExecutor();
                if (lexerActionExecutor != null) {
                    lexerActionExecutor = lexerActionExecutor.fixOffsetBeforeMatch(input.index() - this.startIndex);
                }
                boolean bl2 = treatEofAsEpsilon = t == -1;
                if (!this.closure(input, c.transform(target, lexerActionExecutor, true), reach, currentAltReachedAcceptState, true, treatEofAsEpsilon)) continue;
                skipAlt = c.getAlt();
                continue block0;
            }
        }
    }

    protected void accept(@NotNull CharStream input, LexerActionExecutor lexerActionExecutor, int startIndex, int index, int line, int charPos) {
        input.seek(index);
        this.line = line;
        this.charPositionInLine = charPos;
        if (lexerActionExecutor != null && this.recog != null) {
            lexerActionExecutor.execute(this.recog, input, startIndex);
        }
    }

    @Nullable
    protected ATNState getReachableTarget(Transition trans, int t) {
        if (trans.matches(t, 0, 0x10FFFF)) {
            return trans.target;
        }
        return null;
    }

    @NotNull
    protected ATNConfigSet computeStartState(@NotNull CharStream input, @NotNull ATNState p) {
        PredictionContext initialContext = PredictionContext.EMPTY_FULL;
        OrderedATNConfigSet configs = new OrderedATNConfigSet();
        for (int i = 0; i < p.getNumberOfTransitions(); ++i) {
            ATNState target = p.transition((int)i).target;
            ATNConfig c = ATNConfig.create(target, i + 1, initialContext);
            this.closure(input, c, configs, false, false, false);
        }
        return configs;
    }

    protected boolean closure(@NotNull CharStream input, @NotNull ATNConfig config, @NotNull ATNConfigSet configs, boolean currentAltReachedAcceptState, boolean speculative, boolean treatEofAsEpsilon) {
        if (config.getState() instanceof RuleStopState) {
            PredictionContext context = config.getContext();
            if (context.isEmpty()) {
                configs.add(config);
                return true;
            }
            if (context.hasEmpty()) {
                configs.add(config.transform(config.getState(), PredictionContext.EMPTY_FULL, true));
                currentAltReachedAcceptState = true;
            }
            for (int i = 0; i < context.size(); ++i) {
                int returnStateNumber = context.getReturnState(i);
                if (returnStateNumber == Integer.MAX_VALUE) continue;
                PredictionContext newContext = context.getParent(i);
                ATNState returnState = this.atn.states.get(returnStateNumber);
                ATNConfig c = config.transform(returnState, newContext, false);
                currentAltReachedAcceptState = this.closure(input, c, configs, currentAltReachedAcceptState, speculative, treatEofAsEpsilon);
            }
            return currentAltReachedAcceptState;
        }
        if (!(config.getState().onlyHasEpsilonTransitions() || currentAltReachedAcceptState && config.hasPassedThroughNonGreedyDecision())) {
            configs.add(config);
        }
        ATNState p = config.getState();
        for (int i = 0; i < p.getNumberOfOptimizedTransitions(); ++i) {
            Transition t = p.getOptimizedTransition(i);
            ATNConfig c = this.getEpsilonTarget(input, config, t, configs, speculative, treatEofAsEpsilon);
            if (c == null) continue;
            currentAltReachedAcceptState = this.closure(input, c, configs, currentAltReachedAcceptState, speculative, treatEofAsEpsilon);
        }
        return currentAltReachedAcceptState;
    }

    @Nullable
    protected ATNConfig getEpsilonTarget(@NotNull CharStream input, @NotNull ATNConfig config, @NotNull Transition t, @NotNull ATNConfigSet configs, boolean speculative, boolean treatEofAsEpsilon) {
        ATNConfig c;
        switch (t.getSerializationType()) {
            case 3: {
                RuleTransition ruleTransition = (RuleTransition)t;
                if (this.optimize_tail_calls && ruleTransition.optimizedTailCall && !config.getContext().hasEmpty()) {
                    c = config.transform(t.target, true);
                    break;
                }
                PredictionContext newContext = config.getContext().getChild(ruleTransition.followState.stateNumber);
                c = config.transform(t.target, newContext, true);
                break;
            }
            case 10: {
                throw new UnsupportedOperationException("Precedence predicates are not supported in lexers.");
            }
            case 4: {
                PredicateTransition pt = (PredicateTransition)t;
                configs.markExplicitSemanticContext();
                if (this.evaluatePredicate(input, pt.ruleIndex, pt.predIndex, speculative)) {
                    c = config.transform(t.target, true);
                    break;
                }
                c = null;
                break;
            }
            case 6: {
                if (config.getContext().hasEmpty()) {
                    LexerActionExecutor lexerActionExecutor = LexerActionExecutor.append(config.getLexerActionExecutor(), this.atn.lexerActions[((ActionTransition)t).actionIndex]);
                    c = config.transform(t.target, lexerActionExecutor, true);
                    break;
                }
                c = config.transform(t.target, true);
                break;
            }
            case 1: {
                c = config.transform(t.target, true);
                break;
            }
            case 2: 
            case 5: 
            case 7: {
                if (treatEofAsEpsilon && t.matches(-1, 0, 0x10FFFF)) {
                    c = config.transform(t.target, false);
                    break;
                }
                c = null;
                break;
            }
            default: {
                c = null;
            }
        }
        return c;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected boolean evaluatePredicate(@NotNull CharStream input, int ruleIndex, int predIndex, boolean speculative) {
        if (this.recog == null) {
            return true;
        }
        if (!speculative) {
            return this.recog.sempred(null, ruleIndex, predIndex);
        }
        int savedCharPositionInLine = this.charPositionInLine;
        int savedLine = this.line;
        int index = input.index();
        int marker = input.mark();
        try {
            this.consume(input);
            boolean bl = this.recog.sempred(null, ruleIndex, predIndex);
            return bl;
        }
        finally {
            this.charPositionInLine = savedCharPositionInLine;
            this.line = savedLine;
            input.seek(index);
            input.release(marker);
        }
    }

    protected void captureSimState(@NotNull SimState settings, @NotNull CharStream input, @NotNull DFAState dfaState) {
        settings.index = input.index();
        settings.line = this.line;
        settings.charPos = this.charPositionInLine;
        settings.dfaState = dfaState;
    }

    @NotNull
    protected DFAState addDFAEdge(@NotNull DFAState from, int t, @NotNull ATNConfigSet q) {
        boolean suppressEdge = q.hasSemanticContext();
        if (suppressEdge) {
            q.clearExplicitSemanticContext();
        }
        DFAState to = this.addDFAState(q);
        if (suppressEdge) {
            return to;
        }
        this.addDFAEdge(from, t, to);
        return to;
    }

    protected void addDFAEdge(@NotNull DFAState p, int t, @NotNull DFAState q) {
        if (p != null) {
            p.setTarget(t, q);
        }
    }

    @NotNull
    protected DFAState addDFAState(@NotNull ATNConfigSet configs) {
        assert (!configs.hasSemanticContext());
        DFAState proposed = new DFAState(this.atn.modeToDFA[this.mode], configs);
        DFAState existing = (DFAState)this.atn.modeToDFA[this.mode].states.get(proposed);
        if (existing != null) {
            return existing;
        }
        configs.optimizeConfigs(this);
        DFAState newState = new DFAState(this.atn.modeToDFA[this.mode], configs.clone(true));
        ATNConfig firstConfigWithRuleStopState = null;
        for (ATNConfig c : configs) {
            if (!(c.getState() instanceof RuleStopState)) continue;
            firstConfigWithRuleStopState = c;
            break;
        }
        if (firstConfigWithRuleStopState != null) {
            int prediction = this.atn.ruleToTokenType[firstConfigWithRuleStopState.getState().ruleIndex];
            LexerActionExecutor lexerActionExecutor = firstConfigWithRuleStopState.getLexerActionExecutor();
            newState.setAcceptState(new AcceptStateInfo(prediction, lexerActionExecutor));
        }
        return this.atn.modeToDFA[this.mode].addState(newState);
    }

    @NotNull
    public final DFA getDFA(int mode) {
        return this.atn.modeToDFA[mode];
    }

    @NotNull
    public String getText(@NotNull CharStream input) {
        return input.getText(Interval.of(this.startIndex, input.index() - 1));
    }

    public int getLine() {
        return this.line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getCharPositionInLine() {
        return this.charPositionInLine;
    }

    public void setCharPositionInLine(int charPositionInLine) {
        this.charPositionInLine = charPositionInLine;
    }

    public void consume(@NotNull CharStream input) {
        int curChar = input.LA(1);
        if (curChar == 10) {
            ++this.line;
            this.charPositionInLine = 0;
        } else {
            ++this.charPositionInLine;
        }
        input.consume();
    }

    @NotNull
    public String getTokenName(int t) {
        if (t == -1) {
            return "EOF";
        }
        return "'" + (char)t + "'";
    }

    protected static class SimState {
        protected int index = -1;
        protected int line = 0;
        protected int charPos = -1;
        protected DFAState dfaState;

        protected SimState() {
        }

        protected void reset() {
            this.index = -1;
            this.line = 0;
            this.charPos = -1;
            this.dfaState = null;
        }
    }
}

