/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.CharStream;
import groovyjarjarantlr4.v4.runtime.IntStream;
import groovyjarjarantlr4.v4.runtime.Lexer;
import groovyjarjarantlr4.v4.runtime.ParserRuleContext;
import groovyjarjarantlr4.v4.runtime.Recognizer;
import groovyjarjarantlr4.v4.runtime.RuleContext;
import groovyjarjarantlr4.v4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;

public class RecognitionException
extends RuntimeException {
    private static final long serialVersionUID = -3861826954750022374L;
    @Nullable
    private final Recognizer<?, ?> recognizer;
    @Nullable
    private final RuleContext ctx;
    @Nullable
    private final IntStream input;
    private Token offendingToken;
    private int offendingState = -1;

    public RecognitionException(@Nullable Lexer lexer, CharStream input) {
        this.recognizer = lexer;
        this.input = input;
        this.ctx = null;
    }

    public RecognitionException(@Nullable Recognizer<Token, ?> recognizer, @Nullable IntStream input, @Nullable ParserRuleContext ctx) {
        this.recognizer = recognizer;
        this.input = input;
        this.ctx = ctx;
        if (recognizer != null) {
            this.offendingState = recognizer.getState();
        }
    }

    public RecognitionException(String message, @Nullable Recognizer<Token, ?> recognizer, @Nullable IntStream input, @Nullable ParserRuleContext ctx) {
        super(message);
        this.recognizer = recognizer;
        this.input = input;
        this.ctx = ctx;
        if (recognizer != null) {
            this.offendingState = recognizer.getState();
        }
    }

    public int getOffendingState() {
        return this.offendingState;
    }

    protected final void setOffendingState(int offendingState) {
        this.offendingState = offendingState;
    }

    @Nullable
    public IntervalSet getExpectedTokens() {
        if (this.recognizer != null) {
            return this.recognizer.getATN().getExpectedTokens(this.offendingState, this.ctx);
        }
        return null;
    }

    @Nullable
    public RuleContext getContext() {
        return this.ctx;
    }

    @Nullable
    public IntStream getInputStream() {
        return this.input;
    }

    @Nullable
    public Token getOffendingToken() {
        return this.offendingToken;
    }

    protected final <Symbol extends Token> void setOffendingToken(Recognizer<Symbol, ?> recognizer, @Nullable Symbol offendingToken) {
        if (recognizer == this.recognizer) {
            this.offendingToken = offendingToken;
        }
    }

    @Nullable
    public Recognizer<?, ?> getRecognizer() {
        return this.recognizer;
    }

    public <T> T getOffendingToken(Recognizer<T, ?> recognizer) {
        return (T)(this.recognizer == recognizer ? this.offendingToken : null);
    }
}

