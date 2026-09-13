/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.CharStream;
import groovyjarjarantlr4.v4.runtime.Lexer;
import groovyjarjarantlr4.v4.runtime.Vocabulary;
import groovyjarjarantlr4.v4.runtime.VocabularyImpl;
import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNType;
import groovyjarjarantlr4.v4.runtime.atn.LexerATNSimulator;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import java.util.Collection;

public class LexerInterpreter
extends Lexer {
    protected final String grammarFileName;
    protected final ATN atn;
    @Deprecated
    protected final String[] tokenNames;
    protected final String[] ruleNames;
    protected final String[] channelNames;
    protected final String[] modeNames;
    @NotNull
    private final Vocabulary vocabulary;

    @Deprecated
    public LexerInterpreter(String grammarFileName, Collection<String> tokenNames, Collection<String> ruleNames, Collection<String> modeNames, ATN atn, CharStream input) {
        this(grammarFileName, VocabularyImpl.fromTokenNames(tokenNames.toArray(new String[tokenNames.size()])), ruleNames, null, modeNames, atn, input);
    }

    @Deprecated
    public LexerInterpreter(String grammarFileName, @NotNull Vocabulary vocabulary, Collection<String> ruleNames, Collection<String> modeNames, ATN atn, CharStream input) {
        this(grammarFileName, vocabulary, ruleNames, null, modeNames, atn, input);
    }

    public LexerInterpreter(String grammarFileName, @NotNull Vocabulary vocabulary, Collection<String> ruleNames, @Nullable Collection<String> channelNames, Collection<String> modeNames, ATN atn, CharStream input) {
        super(input);
        if (atn.grammarType != ATNType.LEXER) {
            throw new IllegalArgumentException("The ATN must be a lexer ATN.");
        }
        this.grammarFileName = grammarFileName;
        this.atn = atn;
        this.tokenNames = new String[atn.maxTokenType];
        for (int i = 0; i < this.tokenNames.length; ++i) {
            this.tokenNames[i] = vocabulary.getDisplayName(i);
        }
        this.ruleNames = ruleNames.toArray(new String[ruleNames.size()]);
        this.channelNames = channelNames != null ? channelNames.toArray(new String[channelNames.size()]) : null;
        this.modeNames = modeNames.toArray(new String[modeNames.size()]);
        this.vocabulary = vocabulary;
        this._interp = new LexerATNSimulator(this, atn);
    }

    @Override
    public ATN getATN() {
        return this.atn;
    }

    @Override
    public String getGrammarFileName() {
        return this.grammarFileName;
    }

    @Override
    @Deprecated
    public String[] getTokenNames() {
        return this.tokenNames;
    }

    @Override
    public String[] getRuleNames() {
        return this.ruleNames;
    }

    @Override
    public String[] getChannelNames() {
        return this.channelNames;
    }

    @Override
    public String[] getModeNames() {
        return this.modeNames;
    }

    @Override
    public Vocabulary getVocabulary() {
        if (this.vocabulary != null) {
            return this.vocabulary;
        }
        return super.getVocabulary();
    }
}

