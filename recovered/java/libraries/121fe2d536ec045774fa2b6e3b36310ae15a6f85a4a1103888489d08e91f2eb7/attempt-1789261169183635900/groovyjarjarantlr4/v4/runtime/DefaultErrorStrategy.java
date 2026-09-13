/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.ANTLRErrorStrategy;
import groovyjarjarantlr4.v4.runtime.FailedPredicateException;
import groovyjarjarantlr4.v4.runtime.InputMismatchException;
import groovyjarjarantlr4.v4.runtime.NoViableAltException;
import groovyjarjarantlr4.v4.runtime.Parser;
import groovyjarjarantlr4.v4.runtime.ParserRuleContext;
import groovyjarjarantlr4.v4.runtime.RecognitionException;
import groovyjarjarantlr4.v4.runtime.RuleContext;
import groovyjarjarantlr4.v4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.TokenFactory;
import groovyjarjarantlr4.v4.runtime.TokenSource;
import groovyjarjarantlr4.v4.runtime.TokenStream;
import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.ParserATNSimulator;
import groovyjarjarantlr4.v4.runtime.atn.PredictionContext;
import groovyjarjarantlr4.v4.runtime.atn.RuleTransition;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.runtime.misc.Tuple;

public class DefaultErrorStrategy
implements ANTLRErrorStrategy {
    protected boolean errorRecoveryMode = false;
    protected int lastErrorIndex = -1;
    protected IntervalSet lastErrorStates;
    protected ParserRuleContext nextTokensContext;
    protected int nextTokensState;

    @Override
    public void reset(Parser recognizer) {
        this.endErrorCondition(recognizer);
    }

    protected void beginErrorCondition(@NotNull Parser recognizer) {
        this.errorRecoveryMode = true;
    }

    @Override
    public boolean inErrorRecoveryMode(Parser recognizer) {
        return this.errorRecoveryMode;
    }

    protected void endErrorCondition(@NotNull Parser recognizer) {
        this.errorRecoveryMode = false;
        this.lastErrorStates = null;
        this.lastErrorIndex = -1;
    }

    @Override
    public void reportMatch(Parser recognizer) {
        this.endErrorCondition(recognizer);
    }

    @Override
    public void reportError(Parser recognizer, RecognitionException e) {
        if (this.inErrorRecoveryMode(recognizer)) {
            return;
        }
        this.beginErrorCondition(recognizer);
        if (e instanceof NoViableAltException) {
            this.reportNoViableAlternative(recognizer, (NoViableAltException)e);
        } else if (e instanceof InputMismatchException) {
            this.reportInputMismatch(recognizer, (InputMismatchException)e);
        } else if (e instanceof FailedPredicateException) {
            this.reportFailedPredicate(recognizer, (FailedPredicateException)e);
        } else {
            System.err.println("unknown recognition error type: " + e.getClass().getName());
            this.notifyErrorListeners(recognizer, e.getMessage(), e);
        }
    }

    protected void notifyErrorListeners(@NotNull Parser recognizer, String message, RecognitionException e) {
        recognizer.notifyErrorListeners(e.getOffendingToken(recognizer), message, e);
    }

    @Override
    public void recover(Parser recognizer, RecognitionException e) {
        if (this.lastErrorIndex == recognizer.getInputStream().index() && this.lastErrorStates != null && this.lastErrorStates.contains(recognizer.getState())) {
            recognizer.consume();
        }
        this.lastErrorIndex = recognizer.getInputStream().index();
        if (this.lastErrorStates == null) {
            this.lastErrorStates = new IntervalSet(new int[0]);
        }
        this.lastErrorStates.add(recognizer.getState());
        IntervalSet followSet = this.getErrorRecoverySet(recognizer);
        this.consumeUntil(recognizer, followSet);
    }

    @Override
    public void sync(Parser recognizer) throws RecognitionException {
        ATNState s = ((ParserATNSimulator)recognizer.getInterpreter()).atn.states.get(recognizer.getState());
        if (this.inErrorRecoveryMode(recognizer)) {
            return;
        }
        TokenStream tokens = recognizer.getInputStream();
        int la = tokens.LA(1);
        IntervalSet nextTokens = recognizer.getATN().nextTokens(s);
        if (nextTokens.contains(la)) {
            this.nextTokensContext = null;
            this.nextTokensState = -1;
            return;
        }
        if (nextTokens.contains(-2)) {
            if (this.nextTokensContext == null) {
                this.nextTokensContext = recognizer.getContext();
                this.nextTokensState = recognizer.getState();
            }
            return;
        }
        switch (s.getStateType()) {
            case 3: 
            case 4: 
            case 5: 
            case 10: {
                if (this.singleTokenDeletion(recognizer) != null) {
                    return;
                }
                throw new InputMismatchException(recognizer);
            }
            case 9: 
            case 11: {
                this.reportUnwantedToken(recognizer);
                IntervalSet expecting = recognizer.getExpectedTokens();
                IntervalSet whatFollowsLoopIterationOrRule = expecting.or(this.getErrorRecoverySet(recognizer));
                this.consumeUntil(recognizer, whatFollowsLoopIterationOrRule);
                break;
            }
        }
    }

    protected void reportNoViableAlternative(@NotNull Parser recognizer, @NotNull NoViableAltException e) {
        TokenStream tokens = recognizer.getInputStream();
        String input = tokens != null ? (e.getStartToken().getType() == -1 ? "<EOF>" : tokens.getText(e.getStartToken(), e.getOffendingToken())) : "<unknown input>";
        String msg = "no viable alternative at input " + this.escapeWSAndQuote(input);
        this.notifyErrorListeners(recognizer, msg, e);
    }

    protected void reportInputMismatch(@NotNull Parser recognizer, @NotNull InputMismatchException e) {
        String msg = "mismatched input " + this.getTokenErrorDisplay(e.getOffendingToken(recognizer)) + " expecting " + e.getExpectedTokens().toString(recognizer.getVocabulary());
        this.notifyErrorListeners(recognizer, msg, e);
    }

    protected void reportFailedPredicate(@NotNull Parser recognizer, @NotNull FailedPredicateException e) {
        String ruleName = recognizer.getRuleNames()[recognizer._ctx.getRuleIndex()];
        String msg = "rule " + ruleName + " " + e.getMessage();
        this.notifyErrorListeners(recognizer, msg, e);
    }

    protected void reportUnwantedToken(@NotNull Parser recognizer) {
        if (this.inErrorRecoveryMode(recognizer)) {
            return;
        }
        this.beginErrorCondition(recognizer);
        Token t = recognizer.getCurrentToken();
        String tokenName = this.getTokenErrorDisplay(t);
        IntervalSet expecting = this.getExpectedTokens(recognizer);
        String msg = "extraneous input " + tokenName + " expecting " + expecting.toString(recognizer.getVocabulary());
        recognizer.notifyErrorListeners(t, msg, null);
    }

    protected void reportMissingToken(@NotNull Parser recognizer) {
        if (this.inErrorRecoveryMode(recognizer)) {
            return;
        }
        this.beginErrorCondition(recognizer);
        Token t = recognizer.getCurrentToken();
        IntervalSet expecting = this.getExpectedTokens(recognizer);
        String msg = "missing " + expecting.toString(recognizer.getVocabulary()) + " at " + this.getTokenErrorDisplay(t);
        recognizer.notifyErrorListeners(t, msg, null);
    }

    @Override
    public Token recoverInline(Parser recognizer) throws RecognitionException {
        Token matchedSymbol = this.singleTokenDeletion(recognizer);
        if (matchedSymbol != null) {
            recognizer.consume();
            return matchedSymbol;
        }
        if (this.singleTokenInsertion(recognizer)) {
            return this.getMissingSymbol(recognizer);
        }
        InputMismatchException e = this.nextTokensContext == null ? new InputMismatchException(recognizer) : new InputMismatchException(recognizer, this.nextTokensState, this.nextTokensContext);
        throw e;
    }

    protected boolean singleTokenInsertion(@NotNull Parser recognizer) {
        int currentSymbolType = recognizer.getInputStream().LA(1);
        ATNState currentState = ((ParserATNSimulator)recognizer.getInterpreter()).atn.states.get(recognizer.getState());
        ATN atn = ((ParserATNSimulator)recognizer.getInterpreter()).atn;
        ATNState next = currentState.transition((int)0).target;
        IntervalSet expectingAtLL2 = atn.nextTokens(next, PredictionContext.fromRuleContext(atn, recognizer._ctx));
        if (expectingAtLL2.contains(currentSymbolType)) {
            this.reportMissingToken(recognizer);
            return true;
        }
        return false;
    }

    @Nullable
    protected Token singleTokenDeletion(@NotNull Parser recognizer) {
        int nextTokenType = recognizer.getInputStream().LA(2);
        IntervalSet expecting = this.getExpectedTokens(recognizer);
        if (expecting.contains(nextTokenType)) {
            this.reportUnwantedToken(recognizer);
            recognizer.consume();
            Token matchedSymbol = recognizer.getCurrentToken();
            this.reportMatch(recognizer);
            return matchedSymbol;
        }
        return null;
    }

    @NotNull
    protected Token getMissingSymbol(@NotNull Parser recognizer) {
        Token currentSymbol = recognizer.getCurrentToken();
        IntervalSet expecting = this.getExpectedTokens(recognizer);
        int expectedTokenType = 0;
        if (!expecting.isNil()) {
            expectedTokenType = expecting.getMinElement();
        }
        String tokenText = expectedTokenType == -1 ? "<missing EOF>" : "<missing " + recognizer.getVocabulary().getDisplayName(expectedTokenType) + ">";
        Token current = currentSymbol;
        Token lookback = recognizer.getInputStream().LT(-1);
        if (current.getType() == -1 && lookback != null) {
            current = lookback;
        }
        return this.constructToken(recognizer.getInputStream().getTokenSource(), expectedTokenType, tokenText, current);
    }

    protected Token constructToken(TokenSource tokenSource, int expectedTokenType, String tokenText, Token current) {
        TokenFactory factory = tokenSource.getTokenFactory();
        return factory.create(Tuple.create(tokenSource, current.getTokenSource().getInputStream()), expectedTokenType, tokenText, 0, -1, -1, current.getLine(), current.getCharPositionInLine());
    }

    @NotNull
    protected IntervalSet getExpectedTokens(@NotNull Parser recognizer) {
        return recognizer.getExpectedTokens();
    }

    protected String getTokenErrorDisplay(Token t) {
        if (t == null) {
            return "<no token>";
        }
        String s = this.getSymbolText(t);
        if (s == null) {
            s = this.getSymbolType(t) == -1 ? "<EOF>" : "<" + this.getSymbolType(t) + ">";
        }
        return this.escapeWSAndQuote(s);
    }

    protected String getSymbolText(@NotNull Token symbol) {
        return symbol.getText();
    }

    protected int getSymbolType(@NotNull Token symbol) {
        return symbol.getType();
    }

    @NotNull
    protected String escapeWSAndQuote(@NotNull String s) {
        s = s.replace("\n", "\\n");
        s = s.replace("\r", "\\r");
        s = s.replace("\t", "\\t");
        return "'" + s + "'";
    }

    @NotNull
    protected IntervalSet getErrorRecoverySet(@NotNull Parser recognizer) {
        ATN atn = ((ParserATNSimulator)recognizer.getInterpreter()).atn;
        RuleContext ctx = recognizer._ctx;
        IntervalSet recoverSet = new IntervalSet(new int[0]);
        while (ctx != null && ctx.invokingState >= 0) {
            ATNState invokingState = atn.states.get(ctx.invokingState);
            RuleTransition rt = (RuleTransition)invokingState.transition(0);
            IntervalSet follow = atn.nextTokens(rt.followState);
            recoverSet.addAll(follow);
            ctx = ctx.parent;
        }
        recoverSet.remove(-2);
        return recoverSet;
    }

    protected void consumeUntil(@NotNull Parser recognizer, @NotNull IntervalSet set) {
        int ttype = recognizer.getInputStream().LA(1);
        while (ttype != -1 && !set.contains(ttype)) {
            recognizer.consume();
            ttype = recognizer.getInputStream().LA(1);
        }
    }
}

