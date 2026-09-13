/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.TokenSource;

public interface TokenStream
extends IntStream {
    public Token LT(int var1);

    public int range();

    public Token get(int var1);

    public TokenSource getTokenSource();

    public String toString(int var1, int var2);

    public String toString(Token var1, Token var2);
}

