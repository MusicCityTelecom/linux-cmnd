/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.Lexer;
import groovyjarjarantlr4.v4.runtime.atn.LexerAction;
import groovyjarjarantlr4.v4.runtime.atn.LexerActionType;
import groovyjarjarantlr4.v4.runtime.misc.MurmurHash;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public final class LexerPopModeAction
implements LexerAction {
    public static final LexerPopModeAction INSTANCE = new LexerPopModeAction();

    private LexerPopModeAction() {
    }

    @Override
    public LexerActionType getActionType() {
        return LexerActionType.POP_MODE;
    }

    @Override
    public boolean isPositionDependent() {
        return false;
    }

    @Override
    public void execute(@NotNull Lexer lexer) {
        lexer.popMode();
    }

    public int hashCode() {
        int hash = MurmurHash.initialize();
        hash = MurmurHash.update(hash, this.getActionType().ordinal());
        return MurmurHash.finish(hash, 1);
    }

    public boolean equals(Object obj) {
        return obj == this;
    }

    public String toString() {
        return "popMode";
    }
}

