/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.RecognitionException;
import groovyjarjarantlr4.v4.runtime.Recognizer;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;

public interface ANTLRErrorListener<Symbol> {
    public <T extends Symbol> void syntaxError(@NotNull Recognizer<T, ?> var1, @Nullable T var2, int var3, int var4, @NotNull String var5, @Nullable RecognitionException var6);
}

