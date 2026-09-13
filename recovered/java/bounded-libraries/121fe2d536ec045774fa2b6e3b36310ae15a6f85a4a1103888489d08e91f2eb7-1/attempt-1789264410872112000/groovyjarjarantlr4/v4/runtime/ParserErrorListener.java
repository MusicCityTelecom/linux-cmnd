/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.ANTLRErrorListener;
import groovyjarjarantlr4.v4.runtime.Parser;
import groovyjarjarantlr4.v4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.atn.ATNConfigSet;
import groovyjarjarantlr4.v4.runtime.atn.SimulatorState;
import groovyjarjarantlr4.v4.runtime.dfa.DFA;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import java.util.BitSet;

public interface ParserErrorListener
extends ANTLRErrorListener<Token> {
    public void reportAmbiguity(@NotNull Parser var1, @NotNull DFA var2, int var3, int var4, boolean var5, @Nullable BitSet var6, @NotNull ATNConfigSet var7);

    public void reportAttemptingFullContext(@NotNull Parser var1, @NotNull DFA var2, int var3, int var4, @Nullable BitSet var5, @NotNull SimulatorState var6);

    public void reportContextSensitivity(@NotNull Parser var1, @NotNull DFA var2, int var3, int var4, int var5, @NotNull SimulatorState var6);
}

