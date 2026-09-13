/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.CharStream;
import groovyjarjarantlr4.v4.runtime.Lexer;
import groovyjarjarantlr4.v4.runtime.RecognitionException;
import groovyjarjarantlr4.v4.runtime.atn.ATNConfigSet;
import groovyjarjarantlr4.v4.runtime.misc.Interval;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.runtime.misc.Utils;
import java.util.Locale;

public class LexerNoViableAltException
extends RecognitionException {
    private static final long serialVersionUID = -730999203913001726L;
    private final int startIndex;
    @Nullable
    private final ATNConfigSet deadEndConfigs;

    public LexerNoViableAltException(@Nullable Lexer lexer, @NotNull CharStream input, int startIndex, @Nullable ATNConfigSet deadEndConfigs) {
        super(lexer, input);
        this.startIndex = startIndex;
        this.deadEndConfigs = deadEndConfigs;
    }

    public int getStartIndex() {
        return this.startIndex;
    }

    @Nullable
    public ATNConfigSet getDeadEndConfigs() {
        return this.deadEndConfigs;
    }

    @Override
    public CharStream getInputStream() {
        return (CharStream)super.getInputStream();
    }

    @Override
    public String toString() {
        String symbol = "";
        if (this.startIndex >= 0 && this.startIndex < this.getInputStream().size()) {
            symbol = this.getInputStream().getText(Interval.of(this.startIndex, this.startIndex));
            symbol = Utils.escapeWhitespace(symbol, false);
        }
        return String.format(Locale.getDefault(), "%s('%s')", LexerNoViableAltException.class.getSimpleName(), symbol);
    }
}

