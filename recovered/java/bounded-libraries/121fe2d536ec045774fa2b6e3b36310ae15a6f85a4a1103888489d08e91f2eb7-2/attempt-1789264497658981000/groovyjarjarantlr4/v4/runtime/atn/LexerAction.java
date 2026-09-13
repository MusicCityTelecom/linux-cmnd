/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.Lexer;
import groovyjarjarantlr4.v4.runtime.atn.LexerActionType;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public interface LexerAction {
    @NotNull
    public LexerActionType getActionType();

    public boolean isPositionDependent();

    public void execute(@NotNull Lexer var1);
}

