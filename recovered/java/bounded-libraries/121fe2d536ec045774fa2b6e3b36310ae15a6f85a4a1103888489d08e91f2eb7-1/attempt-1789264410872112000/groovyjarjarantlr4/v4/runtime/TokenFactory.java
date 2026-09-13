/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.CharStream;
import groovyjarjarantlr4.v4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.TokenSource;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Tuple2;

public interface TokenFactory {
    @NotNull
    public Token create(@NotNull Tuple2<? extends TokenSource, CharStream> var1, int var2, String var3, int var4, int var5, int var6, int var7, int var8);

    @NotNull
    public Token create(int var1, String var2);
}

