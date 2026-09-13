/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.Parser;
import groovyjarjarantlr4.v4.runtime.ParserRuleContext;
import groovyjarjarantlr4.v4.runtime.RecognitionException;
import groovyjarjarantlr4.v4.runtime.Recognizer;
import groovyjarjarantlr4.v4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.TokenStream;
import groovyjarjarantlr4.v4.runtime.atn.ATNConfigSet;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;

public class NoViableAltException
extends RecognitionException {
    private static final long serialVersionUID = 5096000008992867052L;
    @Nullable
    private final ATNConfigSet deadEndConfigs;
    @NotNull
    private final Token startToken;

    public NoViableAltException(@NotNull Parser recognizer) {
        this(recognizer, recognizer.getInputStream(), recognizer.getCurrentToken(), recognizer.getCurrentToken(), null, recognizer._ctx);
    }

    public NoViableAltException(@NotNull Recognizer<Token, ?> recognizer, @NotNull TokenStream input, @NotNull Token startToken, @NotNull Token offendingToken, @Nullable ATNConfigSet deadEndConfigs, @NotNull ParserRuleContext ctx) {
        super(recognizer, input, ctx);
        this.deadEndConfigs = deadEndConfigs;
        this.startToken = startToken;
        this.setOffendingToken(recognizer, offendingToken);
    }

    public Token getStartToken() {
        return this.startToken;
    }

    @Nullable
    public ATNConfigSet getDeadEndConfigs() {
        return this.deadEndConfigs;
    }
}

