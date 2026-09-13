/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.parser.antlr4.internal;

import groovyjarjarantlr4.v4.runtime.BailErrorStrategy;
import groovyjarjarantlr4.v4.runtime.CharStream;
import groovyjarjarantlr4.v4.runtime.FailedPredicateException;
import groovyjarjarantlr4.v4.runtime.InputMismatchException;
import groovyjarjarantlr4.v4.runtime.NoViableAltException;
import groovyjarjarantlr4.v4.runtime.Parser;
import groovyjarjarantlr4.v4.runtime.ParserRuleContext;
import groovyjarjarantlr4.v4.runtime.RecognitionException;
import groovyjarjarantlr4.v4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.TokenStream;
import groovyjarjarantlr4.v4.runtime.atn.ParserATNSimulator;
import groovyjarjarantlr4.v4.runtime.atn.PredictionMode;
import groovyjarjarantlr4.v4.runtime.misc.Interval;
import groovyjarjarantlr4.v4.runtime.misc.ParseCancellationException;

public class DescriptiveErrorStrategy
extends BailErrorStrategy {
    private CharStream charStream;

    public DescriptiveErrorStrategy(CharStream charStream) {
        this.charStream = charStream;
    }

    @Override
    public void recover(Parser recognizer, RecognitionException e) {
        for (ParserRuleContext context = recognizer.getContext(); context != null; context = context.getParent()) {
            context.exception = e;
        }
        if (PredictionMode.LL.equals((Object)((ParserATNSimulator)recognizer.getInterpreter()).getPredictionMode())) {
            if (e instanceof NoViableAltException) {
                this.reportNoViableAlternative(recognizer, (NoViableAltException)e);
            } else if (e instanceof InputMismatchException) {
                this.reportInputMismatch(recognizer, (InputMismatchException)e);
            } else if (e instanceof FailedPredicateException) {
                this.reportFailedPredicate(recognizer, (FailedPredicateException)e);
            }
        }
        throw new ParseCancellationException(e);
    }

    @Override
    public Token recoverInline(Parser recognizer) throws RecognitionException {
        this.recover(recognizer, new InputMismatchException(recognizer));
        return null;
    }

    protected String createNoViableAlternativeErrorMessage(Parser recognizer, NoViableAltException e) {
        TokenStream tokens = recognizer.getInputStream();
        String input = tokens != null ? (e.getStartToken().getType() == -1 ? "<EOF>" : this.charStream.getText(Interval.of(e.getStartToken().getStartIndex(), e.getOffendingToken().getStopIndex()))) : "<unknown input>";
        return "Unexpected input: " + this.escapeWSAndQuote(input);
    }

    @Override
    protected void reportNoViableAlternative(Parser recognizer, NoViableAltException e) {
        this.notifyErrorListeners(recognizer, this.createNoViableAlternativeErrorMessage(recognizer, e), e);
    }

    protected String createInputMismatchErrorMessage(Parser recognizer, InputMismatchException e) {
        return "Unexpected input: " + this.getTokenErrorDisplay(e.getOffendingToken(recognizer));
    }

    @Override
    protected void reportInputMismatch(Parser recognizer, InputMismatchException e) {
        this.notifyErrorListeners(recognizer, this.createInputMismatchErrorMessage(recognizer, e), e);
    }

    protected String createFailedPredicateErrorMessage(Parser recognizer, FailedPredicateException e) {
        return e.getMessage();
    }

    @Override
    protected void reportFailedPredicate(Parser recognizer, FailedPredicateException e) {
        this.notifyErrorListeners(recognizer, this.createFailedPredicateErrorMessage(recognizer, e), e);
    }
}

