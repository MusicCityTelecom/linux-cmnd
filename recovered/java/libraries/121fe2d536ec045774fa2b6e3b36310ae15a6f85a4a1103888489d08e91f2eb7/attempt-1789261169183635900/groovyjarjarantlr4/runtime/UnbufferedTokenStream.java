/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.TokenSource;
import groovyjarjarantlr4.runtime.TokenStream;
import groovyjarjarantlr4.runtime.misc.LookaheadStream;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class UnbufferedTokenStream
extends LookaheadStream<Token>
implements TokenStream {
    protected TokenSource tokenSource;
    protected int tokenIndex = 0;
    protected int channel = 0;

    public UnbufferedTokenStream(TokenSource tokenSource) {
        this.tokenSource = tokenSource;
    }

    @Override
    public Token nextElement() {
        Token t = this.tokenSource.nextToken();
        t.setTokenIndex(this.tokenIndex++);
        return t;
    }

    @Override
    public boolean isEOF(Token o) {
        return o.getType() == -1;
    }

    @Override
    public TokenSource getTokenSource() {
        return this.tokenSource;
    }

    @Override
    public String toString(int start, int stop) {
        return "n/a";
    }

    @Override
    public String toString(Token start, Token stop) {
        return "n/a";
    }

    @Override
    public int LA(int i) {
        return ((Token)this.LT(i)).getType();
    }

    @Override
    public Token get(int i) {
        throw new UnsupportedOperationException("Absolute token indexes are meaningless in an unbuffered stream");
    }

    @Override
    public String getSourceName() {
        return this.tokenSource.getSourceName();
    }
}

