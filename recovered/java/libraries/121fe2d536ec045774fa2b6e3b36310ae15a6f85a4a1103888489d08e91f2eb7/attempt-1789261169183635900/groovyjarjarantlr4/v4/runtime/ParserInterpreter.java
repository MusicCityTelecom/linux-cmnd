/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.FailedPredicateException;
import groovyjarjarantlr4.v4.runtime.InputMismatchException;
import groovyjarjarantlr4.v4.runtime.InterpreterRuleContext;
import groovyjarjarantlr4.v4.runtime.Parser;
import groovyjarjarantlr4.v4.runtime.ParserRuleContext;
import groovyjarjarantlr4.v4.runtime.RecognitionException;
import groovyjarjarantlr4.v4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.TokenStream;
import groovyjarjarantlr4.v4.runtime.Vocabulary;
import groovyjarjarantlr4.v4.runtime.VocabularyImpl;
import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.ActionTransition;
import groovyjarjarantlr4.v4.runtime.atn.AtomTransition;
import groovyjarjarantlr4.v4.runtime.atn.DecisionState;
import groovyjarjarantlr4.v4.runtime.atn.LoopEndState;
import groovyjarjarantlr4.v4.runtime.atn.ParserATNSimulator;
import groovyjarjarantlr4.v4.runtime.atn.PrecedencePredicateTransition;
import groovyjarjarantlr4.v4.runtime.atn.PredicateTransition;
import groovyjarjarantlr4.v4.runtime.atn.RuleStartState;
import groovyjarjarantlr4.v4.runtime.atn.RuleTransition;
import groovyjarjarantlr4.v4.runtime.atn.StarLoopEntryState;
import groovyjarjarantlr4.v4.runtime.atn.Transition;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Tuple;
import groovyjarjarantlr4.v4.runtime.misc.Tuple2;
import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.Collection;
import java.util.Deque;

public class ParserInterpreter
extends Parser {
    protected final String grammarFileName;
    protected final ATN atn;
    protected final BitSet pushRecursionContextStates;
    @Deprecated
    protected final String[] tokenNames;
    protected final String[] ruleNames;
    @NotNull
    private final Vocabulary vocabulary;
    protected final Deque<Tuple2<ParserRuleContext, Integer>> _parentContextStack = new ArrayDeque<Tuple2<ParserRuleContext, Integer>>();
    protected int overrideDecision = -1;
    protected int overrideDecisionInputIndex = -1;
    protected int overrideDecisionAlt = -1;
    protected boolean overrideDecisionReached = false;
    protected InterpreterRuleContext overrideDecisionRoot = null;
    protected InterpreterRuleContext rootContext;

    public ParserInterpreter(@NotNull ParserInterpreter old) {
        super(old.getInputStream());
        this.grammarFileName = old.grammarFileName;
        this.atn = old.atn;
        this.pushRecursionContextStates = old.pushRecursionContextStates;
        this.tokenNames = old.tokenNames;
        this.ruleNames = old.ruleNames;
        this.vocabulary = old.vocabulary;
        this.setInterpreter(new ParserATNSimulator(this, this.atn));
    }

    @Deprecated
    public ParserInterpreter(String grammarFileName, Collection<String> tokenNames, Collection<String> ruleNames, ATN atn, TokenStream input) {
        this(grammarFileName, VocabularyImpl.fromTokenNames(tokenNames.toArray(new String[tokenNames.size()])), ruleNames, atn, input);
    }

    public ParserInterpreter(String grammarFileName, @NotNull Vocabulary vocabulary, Collection<String> ruleNames, ATN atn, TokenStream input) {
        super(input);
        this.grammarFileName = grammarFileName;
        this.atn = atn;
        this.tokenNames = new String[atn.maxTokenType];
        for (int i = 0; i < this.tokenNames.length; ++i) {
            this.tokenNames[i] = vocabulary.getDisplayName(i);
        }
        this.ruleNames = ruleNames.toArray(new String[ruleNames.size()]);
        this.vocabulary = vocabulary;
        this.pushRecursionContextStates = new BitSet(atn.states.size());
        for (ATNState state : atn.states) {
            if (!(state instanceof StarLoopEntryState) || !((StarLoopEntryState)state).precedenceRuleDecision) continue;
            this.pushRecursionContextStates.set(state.stateNumber);
        }
        this.setInterpreter(new ParserATNSimulator(this, atn));
    }

    @Override
    public void reset() {
        super.reset();
        this.overrideDecisionReached = false;
        this.overrideDecisionRoot = null;
    }

    @Override
    public ATN getATN() {
        return this.atn;
    }

    @Override
    @Deprecated
    public String[] getTokenNames() {
        return this.tokenNames;
    }

    @Override
    public Vocabulary getVocabulary() {
        return this.vocabulary;
    }

    @Override
    public String[] getRuleNames() {
        return this.ruleNames;
    }

    @Override
    public String getGrammarFileName() {
        return this.grammarFileName;
    }

    public ParserRuleContext parse(int startRuleIndex) {
        RuleStartState startRuleStartState = this.atn.ruleToStartState[startRuleIndex];
        this.rootContext = this.createInterpreterRuleContext(null, -1, startRuleIndex);
        if (startRuleStartState.isPrecedenceRule) {
            this.enterRecursionRule(this.rootContext, startRuleStartState.stateNumber, startRuleIndex, 0);
        } else {
            this.enterRule(this.rootContext, startRuleStartState.stateNumber, startRuleIndex);
        }
        block5: while (true) {
            ATNState p = this.getATNState();
            switch (p.getStateType()) {
                case 7: {
                    if (this._ctx.isEmpty()) {
                        if (startRuleStartState.isPrecedenceRule) {
                            ParserRuleContext result = this._ctx;
                            Tuple2<ParserRuleContext, Integer> parentContext = this._parentContextStack.pop();
                            this.unrollRecursionContexts(parentContext.getItem1());
                            return result;
                        }
                        this.exitRule();
                        return this.rootContext;
                    }
                    this.visitRuleStopState(p);
                    continue block5;
                }
            }
            try {
                this.visitState(p);
                continue;
            }
            catch (RecognitionException e) {
                this.setState(this.atn.ruleToStopState[p.ruleIndex].stateNumber);
                this.getContext().exception = e;
                this.getErrorHandler().reportError(this, e);
                this.recover(e);
                continue;
            }
            break;
        }
    }

    @Override
    public void enterRecursionRule(ParserRuleContext localctx, int state, int ruleIndex, int precedence) {
        this._parentContextStack.push(Tuple.create(this._ctx, localctx.invokingState));
        super.enterRecursionRule(localctx, state, ruleIndex, precedence);
    }

    protected ATNState getATNState() {
        return this.atn.states.get(this.getState());
    }

    protected void visitState(ATNState p) {
        int predictedAlt = 1;
        if (p.getNumberOfTransitions() > 1) {
            predictedAlt = this.visitDecisionState((DecisionState)p);
        }
        Transition transition = p.transition(predictedAlt - 1);
        switch (transition.getSerializationType()) {
            case 1: {
                if (!this.pushRecursionContextStates.get(p.stateNumber) || transition.target instanceof LoopEndState) break;
                InterpreterRuleContext localctx = this.createInterpreterRuleContext(this._parentContextStack.peek().getItem1(), this._parentContextStack.peek().getItem2(), this._ctx.getRuleIndex());
                this.pushNewRecursionContext(localctx, this.atn.ruleToStartState[p.ruleIndex].stateNumber, this._ctx.getRuleIndex());
                break;
            }
            case 5: {
                this.match(((AtomTransition)transition).label);
                break;
            }
            case 2: 
            case 7: 
            case 8: {
                if (!transition.matches(this._input.LA(1), 1, 65535)) {
                    this.recoverInline();
                }
                this.matchWildcard();
                break;
            }
            case 9: {
                this.matchWildcard();
                break;
            }
            case 3: {
                RuleStartState ruleStartState = (RuleStartState)transition.target;
                int ruleIndex = ruleStartState.ruleIndex;
                InterpreterRuleContext newctx = this.createInterpreterRuleContext(this._ctx, p.stateNumber, ruleIndex);
                if (ruleStartState.isPrecedenceRule) {
                    this.enterRecursionRule(newctx, ruleStartState.stateNumber, ruleIndex, ((RuleTransition)transition).precedence);
                    break;
                }
                this.enterRule(newctx, transition.target.stateNumber, ruleIndex);
                break;
            }
            case 4: {
                PredicateTransition predicateTransition = (PredicateTransition)transition;
                if (this.sempred(this._ctx, predicateTransition.ruleIndex, predicateTransition.predIndex)) break;
                throw new FailedPredicateException(this);
            }
            case 6: {
                ActionTransition actionTransition = (ActionTransition)transition;
                this.action(this._ctx, actionTransition.ruleIndex, actionTransition.actionIndex);
                break;
            }
            case 10: {
                if (this.precpred(this._ctx, ((PrecedencePredicateTransition)transition).precedence)) break;
                throw new FailedPredicateException(this, String.format("precpred(_ctx, %d)", ((PrecedencePredicateTransition)transition).precedence));
            }
            default: {
                throw new UnsupportedOperationException("Unrecognized ATN transition type.");
            }
        }
        this.setState(transition.target.stateNumber);
    }

    protected int visitDecisionState(DecisionState p) {
        int predictedAlt;
        this.getErrorHandler().sync(this);
        int decision = p.decision;
        if (decision == this.overrideDecision && this._input.index() == this.overrideDecisionInputIndex && !this.overrideDecisionReached) {
            predictedAlt = this.overrideDecisionAlt;
            this.overrideDecisionReached = true;
        } else {
            predictedAlt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, decision, this._ctx);
        }
        return predictedAlt;
    }

    protected InterpreterRuleContext createInterpreterRuleContext(ParserRuleContext parent, int invokingStateNumber, int ruleIndex) {
        return new InterpreterRuleContext(parent, invokingStateNumber, ruleIndex);
    }

    protected void visitRuleStopState(ATNState p) {
        RuleStartState ruleStartState = this.atn.ruleToStartState[p.ruleIndex];
        if (ruleStartState.isPrecedenceRule) {
            Tuple2<ParserRuleContext, Integer> parentContext = this._parentContextStack.pop();
            this.unrollRecursionContexts(parentContext.getItem1());
            this.setState(parentContext.getItem2());
        } else {
            this.exitRule();
        }
        RuleTransition ruleTransition = (RuleTransition)this.atn.states.get(this.getState()).transition(0);
        this.setState(ruleTransition.followState.stateNumber);
    }

    public void addDecisionOverride(int decision, int tokenIndex, int forcedAlt) {
        this.overrideDecision = decision;
        this.overrideDecisionInputIndex = tokenIndex;
        this.overrideDecisionAlt = forcedAlt;
    }

    public InterpreterRuleContext getOverrideDecisionRoot() {
        return this.overrideDecisionRoot;
    }

    protected void recover(RecognitionException e) {
        int i = this._input.index();
        this.getErrorHandler().recover(this, e);
        if (this._input.index() == i) {
            if (e instanceof InputMismatchException) {
                InputMismatchException ime = (InputMismatchException)e;
                Token tok = e.getOffendingToken();
                int expectedTokenType = 0;
                if (!ime.getExpectedTokens().isNil()) {
                    expectedTokenType = ime.getExpectedTokens().getMinElement();
                }
                Token errToken = this.getTokenFactory().create(Tuple.create(tok.getTokenSource(), tok.getTokenSource().getInputStream()), expectedTokenType, tok.getText(), 0, -1, -1, tok.getLine(), tok.getCharPositionInLine());
                this._ctx.addErrorNode(this.createErrorNode(this._ctx, errToken));
            } else {
                Token tok = e.getOffendingToken();
                Token errToken = this.getTokenFactory().create(Tuple.create(tok.getTokenSource(), tok.getTokenSource().getInputStream()), 0, tok.getText(), 0, -1, -1, tok.getLine(), tok.getCharPositionInLine());
                this._ctx.addErrorNode(this.createErrorNode(this._ctx, errToken));
            }
        }
    }

    protected Token recoverInline() {
        return this._errHandler.recoverInline(this);
    }

    public InterpreterRuleContext getRootContext() {
        return this.rootContext;
    }
}

