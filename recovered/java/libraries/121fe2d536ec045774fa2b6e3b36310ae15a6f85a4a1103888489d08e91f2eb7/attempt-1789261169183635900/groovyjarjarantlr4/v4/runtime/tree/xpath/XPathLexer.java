/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.tree.xpath;

import groovyjarjarantlr4.v4.runtime.CharStream;
import groovyjarjarantlr4.v4.runtime.Lexer;
import groovyjarjarantlr4.v4.runtime.RuleContext;
import groovyjarjarantlr4.v4.runtime.Vocabulary;
import groovyjarjarantlr4.v4.runtime.VocabularyImpl;
import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNDeserializer;
import groovyjarjarantlr4.v4.runtime.atn.LexerATNSimulator;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public class XPathLexer
extends Lexer {
    public static final int TOKEN_REF = 1;
    public static final int RULE_REF = 2;
    public static final int ANYWHERE = 3;
    public static final int ROOT = 4;
    public static final int WILDCARD = 5;
    public static final int BANG = 6;
    public static final int ID = 7;
    public static final int STRING = 8;
    public static String[] channelNames = new String[]{"DEFAULT_TOKEN_CHANNEL", "HIDDEN"};
    public static String[] modeNames = new String[]{"DEFAULT_MODE"};
    public static final String[] ruleNames = XPathLexer.makeRuleNames();
    private static final String[] _LITERAL_NAMES = XPathLexer.makeLiteralNames();
    private static final String[] _SYMBOLIC_NAMES = XPathLexer.makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);
    @Deprecated
    public static final String[] tokenNames = new String[_SYMBOLIC_NAMES.length];
    public static final String _serializedATN = "\u0003\uc91d\ucaba\u058d\uafba\u4f53\u0607\uea8b\uc241\u0002\n8\b\u0001\u0004\u0002\t\u0002\u0004\u0003\t\u0003\u0004\u0004\t\u0004\u0004\u0005\t\u0005\u0004\u0006\t\u0006\u0004\u0007\t\u0007\u0004\b\t\b\u0004\t\t\t\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0003\u0003\u0003\u0003\u0004\u0003\u0004\u0003\u0005\u0003\u0005\u0003\u0006\u0003\u0006\u0007\u0006\u001f\n\u0006\f\u0006\u000e\u0006\"\u000b\u0006\u0003\u0006\u0003\u0006\u0003\u0007\u0003\u0007\u0003\u0007\u0005\u0007)\n\u0007\u0003\b\u0003\b\u0003\b\u0005\b.\n\b\u0003\t\u0003\t\u0007\t2\n\t\f\t\u000e\t5\u000b\t\u0003\t\u0003\t\u00033\u0002\u0002\n\u0003\u0002\u0005\u0005\u0002\u0006\u0007\u0002\u0007\t\u0002\b\u000b\u0002\t\r\u0002\u0002\u000f\u0002\u0002\u0011\u0002\n\u0003\u0002\u0005\u0006\u00022;C\\aac|\u0003\u0002\u0002\u0081\u0004\u0002C\\c|\u00029\u0002\u0003\u0003\u0002\u0002\u0002\u0002\u0005\u0003\u0002\u0002\u0002\u0002\u0007\u0003\u0002\u0002\u0002\u0002\t\u0003\u0002\u0002\u0002\u0002\u000b\u0003\u0002\u0002\u0002\u0002\u0011\u0003\u0002\u0002\u0002\u0003\u0013\u0003\u0002\u0002\u0002\u0005\u0016\u0003\u0002\u0002\u0002\u0007\u0018\u0003\u0002\u0002\u0002\t\u001a\u0003\u0002\u0002\u0002\u000b\u001c\u0003\u0002\u0002\u0002\r(\u0003\u0002\u0002\u0002\u000f-\u0003\u0002\u0002\u0002\u0011/\u0003\u0002\u0002\u0002\u0013\u0014\u00071\u0002\u0002\u0014\u0015\u00071\u0002\u0002\u0015\u0004\u0003\u0002\u0002\u0002\u0016\u0017\u00071\u0002\u0002\u0017\u0006\u0003\u0002\u0002\u0002\u0018\u0019\u0007,\u0002\u0002\u0019\b\u0003\u0002\u0002\u0002\u001a\u001b\u0007#\u0002\u0002\u001b\n\u0003\u0002\u0002\u0002\u001c \u0005\u000f\b\u0002\u001d\u001f\u0005\r\u0007\u0002\u001e\u001d\u0003\u0002\u0002\u0002\u001f\"\u0003\u0002\u0002\u0002 \u001e\u0003\u0002\u0002\u0002 !\u0003\u0002\u0002\u0002!#\u0003\u0002\u0002\u0002\" \u0003\u0002\u0002\u0002#$\b\u0006\u0002\u0002$\f\u0003\u0002\u0002\u0002%)\t\u0002\u0002\u0002&'\n\u0003\u0002\u0002')\u0006\u0007\u0002\u0002(%\u0003\u0002\u0002\u0002(&\u0003\u0002\u0002\u0002)\u000e\u0003\u0002\u0002\u0002*.\t\u0004\u0002\u0002+,\n\u0003\u0002\u0002,.\u0006\b\u0003\u0002-*\u0003\u0002\u0002\u0002-+\u0003\u0002\u0002\u0002.\u0010\u0003\u0002\u0002\u0002/3\u0007)\u0002\u000202\u000b\u0002\u0002\u000210\u0003\u0002\u0002\u000225\u0003\u0002\u0002\u000234\u0003\u0002\u0002\u000231\u0003\u0002\u0002\u000246\u0003\u0002\u0002\u000253\u0003\u0002\u0002\u000267\u0007)\u0002\u00027\u0012\u0003\u0002\u0002\u0002\u0007\u0002 (-3\u0003\u0003\u0006\u0002";
    public static final ATN _ATN;

    private static String[] makeRuleNames() {
        return new String[]{"ANYWHERE", "ROOT", "WILDCARD", "BANG", "ID", "NameChar", "NameStartChar", "STRING"};
    }

    private static String[] makeLiteralNames() {
        return new String[]{null, null, null, "'//'", "'/'", "'*'", "'!'"};
    }

    private static String[] makeSymbolicNames() {
        return new String[]{null, "TOKEN_REF", "RULE_REF", "ANYWHERE", "ROOT", "WILDCARD", "BANG", "ID", "STRING"};
    }

    @Override
    @Deprecated
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override
    @NotNull
    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }

    public XPathLexer(CharStream input) {
        super(input);
        this._interp = new LexerATNSimulator(this, _ATN);
        this.validateInputStream(_ATN, input);
    }

    @Override
    public String getGrammarFileName() {
        return "XPathLexer.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    @NotNull
    public String[] getChannelNames() {
        return channelNames;
    }

    @Override
    @NotNull
    public String[] getModeNames() {
        return modeNames;
    }

    @Override
    public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
        switch (ruleIndex) {
            case 4: {
                this.ID_action(_localctx, actionIndex);
            }
        }
    }

    private void ID_action(RuleContext _localctx, int actionIndex) {
        switch (actionIndex) {
            case 0: {
                String text = this.getText();
                if (Character.isUpperCase(text.charAt(0))) {
                    this.setType(1);
                    break;
                }
                this.setType(2);
            }
        }
    }

    @Override
    public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
        switch (ruleIndex) {
            case 5: {
                return this.NameChar_sempred(_localctx, predIndex);
            }
            case 6: {
                return this.NameStartChar_sempred(_localctx, predIndex);
            }
        }
        return true;
    }

    private boolean NameChar_sempred(RuleContext _localctx, int predIndex) {
        switch (predIndex) {
            case 0: {
                return Character.isUnicodeIdentifierPart(this._input.LA(-1));
            }
        }
        return true;
    }

    private boolean NameStartChar_sempred(RuleContext _localctx, int predIndex) {
        switch (predIndex) {
            case 1: {
                return Character.isUnicodeIdentifierStart(this._input.LA(-1));
            }
        }
        return true;
    }

    static {
        for (int i = 0; i < tokenNames.length; ++i) {
            XPathLexer.tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                XPathLexer.tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }
            if (tokenNames[i] != null) continue;
            XPathLexer.tokenNames[i] = "<INVALID>";
        }
        _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    }
}

