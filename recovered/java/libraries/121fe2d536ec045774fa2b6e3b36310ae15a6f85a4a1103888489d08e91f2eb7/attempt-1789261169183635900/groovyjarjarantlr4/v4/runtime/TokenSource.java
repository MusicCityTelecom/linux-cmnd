/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.CharStream;
import groovyjarjarantlr4.v4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.TokenFactory;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;

public interface TokenSource {
    @NotNull
    public Token nextToken();

    public int getLine();

    public int getCharPositionInLine();

    @Nullable
    public CharStream getInputStream();

    @NotNull
    public String getSourceName();

    public void setTokenFactory(@NotNull TokenFactory var1);

    @NotNull
    public TokenFactory getTokenFactory();
}

