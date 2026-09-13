/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.IntStream;
import groovyjarjarantlr4.v4.runtime.RuleContext;
import groovyjarjarantlr4.v4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.TokenSource;
import groovyjarjarantlr4.v4.runtime.misc.Interval;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public interface TokenStream
extends IntStream {
    @NotNull
    public Token LT(int var1);

    @NotNull
    public Token get(int var1);

    @NotNull
    public TokenSource getTokenSource();

    @NotNull
    public String getText(@NotNull Interval var1);

    @NotNull
    public String getText();

    @NotNull
    public String getText(@NotNull RuleContext var1);

    @NotNull
    public String getText(Object var1, Object var2);
}

