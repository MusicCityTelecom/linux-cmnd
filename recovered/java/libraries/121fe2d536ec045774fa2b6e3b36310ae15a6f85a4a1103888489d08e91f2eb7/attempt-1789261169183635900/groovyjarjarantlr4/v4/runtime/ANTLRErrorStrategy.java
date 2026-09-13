/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.Parser;
import groovyjarjarantlr4.v4.runtime.RecognitionException;
import groovyjarjarantlr4.v4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public interface ANTLRErrorStrategy {
    public void reset(@NotNull Parser var1);

    @NotNull
    public Token recoverInline(@NotNull Parser var1) throws RecognitionException;

    public void recover(@NotNull Parser var1, @NotNull RecognitionException var2) throws RecognitionException;

    public void sync(@NotNull Parser var1) throws RecognitionException;

    public boolean inErrorRecoveryMode(@NotNull Parser var1);

    public void reportMatch(@NotNull Parser var1);

    public void reportError(@NotNull Parser var1, @NotNull RecognitionException var2);
}

