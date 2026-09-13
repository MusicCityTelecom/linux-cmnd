/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import groovyjarjarantlr4.runtime.BaseRecognizer;
import groovyjarjarantlr4.runtime.BitSet;
import groovyjarjarantlr4.runtime.CommonToken;
import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.RecognizerSharedState;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.TokenStream;

public class Parser
extends BaseRecognizer {
    public TokenStream input;

    public Parser(TokenStream input) {
        this.setTokenStream(input);
    }

    public Parser(TokenStream input, RecognizerSharedState state) {
        super(state);
        this.input = input;
    }

    public void reset() {
        super.reset();
        if (this.input != null) {
            this.input.seek(0);
        }
    }

    protected Object getCurrentInputSymbol(IntStream input) {
        return ((TokenStream)input).LT(1);
    }

    protected Object getMissingSymbol(IntStream input, RecognitionException e, int expectedTokenType, BitSet follow) {
        String tokenText = expectedTokenType == -1 ? "<missing EOF>" : "<missing " + this.getTokenNames()[expectedTokenType] + ">";
        CommonToken t = new CommonToken(expectedTokenType, tokenText);
        Token current = ((TokenStream)input).LT(1);
        if (current.getType() == -1) {
            current = ((TokenStream)input).LT(-1);
        }
        t.line = current.getLine();
        t.charPositionInLine = current.getCharPositionInLine();
        t.channel = 0;
        t.input = current.getInputStream();
        return t;
    }

    public void setTokenStream(TokenStream input) {
        this.input = null;
        this.reset();
        this.input = input;
    }

    public TokenStream getTokenStream() {
        return this.input;
    }

    public String getSourceName() {
        return this.input.getSourceName();
    }

    public void traceIn(String ruleName, int ruleIndex) {
        super.traceIn(ruleName, ruleIndex, this.input.LT(1));
    }

    public void traceOut(String ruleName, int ruleIndex) {
        super.traceOut(ruleName, ruleIndex, this.input.LT(1));
    }
}

