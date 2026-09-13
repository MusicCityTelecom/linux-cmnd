/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.parse;

import groovyjarjarantlr4.runtime.BaseRecognizer;
import groovyjarjarantlr4.runtime.CharStream;
import groovyjarjarantlr4.runtime.CommonToken;
import groovyjarjarantlr4.runtime.CommonTokenStream;
import groovyjarjarantlr4.runtime.DFA;
import groovyjarjarantlr4.runtime.EarlyExitException;
import groovyjarjarantlr4.runtime.FailedPredicateException;
import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.Lexer;
import groovyjarjarantlr4.runtime.MismatchedSetException;
import groovyjarjarantlr4.runtime.NoViableAltException;
import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.RecognizerSharedState;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.misc.Interval;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.Grammar;

public class ANTLRLexer
extends Lexer {
    public static final int EOF = -1;
    public static final int ACTION = 4;
    public static final int ACTION_CHAR_LITERAL = 5;
    public static final int ACTION_ESC = 6;
    public static final int ACTION_STRING_LITERAL = 7;
    public static final int ARG_ACTION = 8;
    public static final int ARG_OR_CHARSET = 9;
    public static final int ASSIGN = 10;
    public static final int AT = 11;
    public static final int CATCH = 12;
    public static final int CHANNELS = 13;
    public static final int COLON = 14;
    public static final int COLONCOLON = 15;
    public static final int COMMA = 16;
    public static final int COMMENT = 17;
    public static final int DOC_COMMENT = 18;
    public static final int DOLLAR = 19;
    public static final int DOT = 20;
    public static final int ERRCHAR = 21;
    public static final int ESC_SEQ = 22;
    public static final int FINALLY = 23;
    public static final int FRAGMENT = 24;
    public static final int GRAMMAR = 25;
    public static final int GT = 26;
    public static final int HEX_DIGIT = 27;
    public static final int ID = 28;
    public static final int IMPORT = 29;
    public static final int INT = 30;
    public static final int LEXER = 31;
    public static final int LEXER_CHAR_SET = 32;
    public static final int LOCALS = 33;
    public static final int LPAREN = 34;
    public static final int LT = 35;
    public static final int MODE = 36;
    public static final int NESTED_ACTION = 37;
    public static final int NLCHARS = 38;
    public static final int NOT = 39;
    public static final int NameChar = 40;
    public static final int NameStartChar = 41;
    public static final int OPTIONS = 42;
    public static final int OR = 43;
    public static final int PARSER = 44;
    public static final int PLUS = 45;
    public static final int PLUS_ASSIGN = 46;
    public static final int POUND = 47;
    public static final int PRIVATE = 48;
    public static final int PROTECTED = 49;
    public static final int PUBLIC = 50;
    public static final int QUESTION = 51;
    public static final int RANGE = 52;
    public static final int RARROW = 53;
    public static final int RBRACE = 54;
    public static final int RETURNS = 55;
    public static final int RPAREN = 56;
    public static final int RULE_REF = 57;
    public static final int SEMI = 58;
    public static final int SEMPRED = 59;
    public static final int SRC = 60;
    public static final int STAR = 61;
    public static final int STRING_LITERAL = 62;
    public static final int SYNPRED = 63;
    public static final int THROWS = 64;
    public static final int TOKENS_SPEC = 65;
    public static final int TOKEN_REF = 66;
    public static final int TREE_GRAMMAR = 67;
    public static final int UNICODE_ESC = 68;
    public static final int UNICODE_EXTENDED_ESC = 69;
    public static final int UnicodeBOM = 70;
    public static final int WS = 71;
    public static final int WSCHARS = 72;
    public static final int WSNLCHARS = 73;
    public static final int COMMENTS_CHANNEL = 2;
    public CommonTokenStream tokens;
    public boolean isLexerRule = false;
    protected DFA2 dfa2 = new DFA2(this);
    protected DFA35 dfa35 = new DFA35(this);
    static final String DFA2_eotS = "\u0002\u0002\u0001\uffff\u000e\u0002\u0001\uffff\u0003\u0002\u0001\uffff\u0002\u0002\u0002\uffff";
    static final String DFA2_eofS = "\u001a\uffff";
    static final String DFA2_minS = "\u0001 \u0001$\u0001\uffff\u0001A\u0001N\u0001T\u0001L\u0001R\u0001s\u0001r\u0001c\u0002\t\u0003\u0000\u0001\t\u0001\uffff\u0003\u0000\u0001\uffff\u0001\t\u0003\u0000";
    static final String DFA2_maxS = "\u0001 \u0001$\u0001\uffff\u0001A\u0001N\u0001T\u0001L\u0001R\u0001s\u0001r\u0001c\u0001 \u0001\"\u0003\uffff\u0001 \u0001\uffff\u0003\uffff\u0001\uffff\u00019\u0001\uffff\u0002\u0000";
    static final String DFA2_acceptS = "\u0002\uffff\u0001\u0002\u000e\uffff\u0001\u0001\u0003\uffff\u0001\u0001\u0004\uffff";
    static final String DFA2_specialS = "\r\uffff\u0001\b\u0001\u0003\u0001\u0005\u0002\uffff\u0001\u0002\u0001\u0001\u0001\u0004\u0002\uffff\u0001\u0006\u0001\u0000\u0001\u0007}>";
    static final String[] DFA2_transitionS;
    static final short[] DFA2_eot;
    static final short[] DFA2_eof;
    static final char[] DFA2_min;
    static final char[] DFA2_max;
    static final short[] DFA2_accept;
    static final short[] DFA2_special;
    static final short[][] DFA2_transition;
    static final String DFA35_eotS = "\u0002\uffff\u0001&\u0001\uffff\n,\u0001>\u0004\uffff\u0001&\u0002\uffff\u0001G\u0002\uffff\u0001K\u0002\uffff\u0001O\u000e\uffff\u0001,\u0001\uffff\u0010,\u001b\uffff#,\u0001\u008e\u0002,\u0001\uffff\u0003,\u0001\u0094\u0003,\u0001\u0098\u0007,\u0001\uffff\u0003,\u0001\u00a3\u0001,\u0001\uffff\u0001\u00a5\u0002,\u0001\uffff\u0001\u00a8\u0001\u00a9\u0002,\u0001\u00ac\u0003,\u0001\uffff\u0001,\u0001\uffff\u0001,\u0001\uffff\u0001,\u0001\u00b3\u0002\uffff\u0001,\u0001\u00b5\u0001\uffff\u0001\u00b6\u0001\u00b7\u0001\uffff\u0002,\u0001\u00ba\u0001\uffff\u0001,\u0003\uffff\u0001,\u0002\uffff\u0001\u00bd\u0001,\u0001\uffff\u0001~";
    static final String DFA35_eofS = "\u00bf\uffff";
    static final String DFA35_minS = "\u0001\u0000\u0001\uffff\u0001\u0000\u0001\uffff\u0001p\u0001h\u0001a\u0001m\u0001i\u0001e\u0001a\u0001r\u0001e\u0001o\u0001:\u0004\uffff\u0001>\u0002\uffff\u0001>\u0002\uffff\u0001=\u0002\uffff\u0001.\u000e\uffff\u0001t\u0001\uffff\u0001k\u0001e\u0001r\u0001a\u0001t\u0001p\u0001a\u0001n\u0001x\u0001c\u0001r\u0001i\u0001b\u0001a\u0001t\u0001d\u001b\uffff\u0001i\u0002e\u0001o\u0001n\u0001c\u0001o\u0001g\u0001a\u0001e\u0001a\u0001s\u0001t\u0001v\u0001l\u0001m\u0001u\u0001e\u0001o\u0001n\u0001\t\u0001w\u0001n\u0001h\u0001r\u0001m\u0001l\u0001r\u0001l\u0002e\u0001a\u0001i\u0001m\u0001r\u00010\u0001n\u0001s\u0001\uffff\u0001r\u0001s\u0001e\u00010\u0001t\u0001e\u0001l\u00010\u0001s\u0001r\u0001c\u0001t\u0001c\u0001a\u0001n\u0001\uffff\u0001s\u0001\t\u0001a\u00010\u0001l\u0001\uffff\u00010\u0001n\u0001y\u0001\uffff\u00020\u0001t\u0001e\u00010\u0001r\u0001s\u0001\t\u0001\uffff\u0001m\u0001\uffff\u0001s\u0001\uffff\u0001t\u00010\u0002\uffff\u0001e\u00010\u0001\uffff\u00020\u0001\uffff\u0001m\u0001\t\u00010\u0001\uffff\u0001d\u0003\uffff\u0001a\u0002\uffff\u00010\u0001r\u0001\uffff\u00010";
    static final String DFA35_maxS = "\u0001\uffff\u0001\uffff\u0001\uffff\u0001\uffff\u0001p\u0001r\u0001h\u0001m\u0001r\u0001o\u0001u\u0001r\u0001e\u0001o\u0001:\u0004\uffff\u0001>\u0002\uffff\u0001>\u0002\uffff\u0001=\u0002\uffff\u0001.\u000e\uffff\u0001t\u0001\uffff\u0001k\u0001e\u0001r\u0001a\u0001t\u0001p\u0001a\u0001n\u0001x\u0001c\u0001r\u0001o\u0001b\u0001a\u0001t\u0001d\u001b\uffff\u0001i\u0002e\u0001o\u0001n\u0001c\u0001o\u0001g\u0001a\u0001e\u0001a\u0001s\u0001t\u0001v\u0001l\u0001m\u0001u\u0001e\u0001o\u0001n\u0001g\u0001w\u0001n\u0001h\u0001r\u0001m\u0001l\u0001r\u0001l\u0002e\u0001a\u0001i\u0001m\u0001r\u0001\ufffd\u0001n\u0001s\u0001\uffff\u0001r\u0001s\u0001e\u0001\ufffd\u0001t\u0001e\u0001l\u0001\ufffd\u0001s\u0001r\u0001c\u0001t\u0001c\u0001a\u0001n\u0001\uffff\u0001s\u0001{\u0001a\u0001\ufffd\u0001l\u0001\uffff\u0001\ufffd\u0001n\u0001y\u0001\uffff\u0002\ufffd\u0001t\u0001e\u0001\ufffd\u0001r\u0001s\u0001{\u0001\uffff\u0001m\u0001\uffff\u0001s\u0001\uffff\u0001t\u0001\ufffd\u0002\uffff\u0001e\u0001\ufffd\u0001\uffff\u0002\ufffd\u0001\uffff\u0001m\u0001{\u0001\ufffd\u0001\uffff\u0001d\u0003\uffff\u0001a\u0002\uffff\u0001\ufffd\u0001r\u0001\uffff\u0001\ufffd";
    static final String DFA35_acceptS = "\u0001\uffff\u0001\u0001\u0001\uffff\u0001\u0003\u000b\uffff\u0001\u0018\u0001\u0019\u0001\u001a\u0001\u001b\u0001\uffff\u0001\u001d\u0001\u001e\u0001\uffff\u0001 \u0001\"\u0001\uffff\u0001%\u0001&\u0001\uffff\u0001)\u0001*\u0001+\u0001,\u0001-\u0001.\u0001/\u00010\u00011\u00012\u0001\u0001\u0002\u0002\u0001\u0003\u0001\uffff\u0001-\u0010\uffff\u0001\u0017\u0001\u0016\u0001\u0018\u0001\u0019\u0001\u001a\u0001\u001b\u0001\u001c\u0001\u001d\u0001\u001e\u0001!\u0001\u001f\u0001 \u0001\"\u0001$\u0001#\u0001%\u0001&\u0001(\u0001'\u0001)\u0001*\u0001+\u0001,\u0001.\u0001/\u00010\u00011&\uffff\u0001\f\u000f\uffff\u0001\u0015\u0005\uffff\u0001\u0013\u0003\uffff\u0001\t\b\uffff\u0001\u0005\u0001\uffff\u0001\u0012\u0001\uffff\u0001\u0007\u0002\uffff\u0001\u0011\u0001\n\u0002\uffff\u0001\u000e\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0014\u0001\uffff\u0001\u000f\u0001\u000b\u0001\u0010\u0001\uffff\u0001\u0006\u0001\b\u0002\uffff\u0001\r\u0001\uffff";
    static final String DFA35_specialS = "\u0001\u0001\u0001\uffff\u0001\u0000\u00bc\uffff}>";
    static final String[] DFA35_transitionS;
    static final short[] DFA35_eot;
    static final short[] DFA35_eof;
    static final char[] DFA35_min;
    static final char[] DFA35_max;
    static final short[] DFA35_accept;
    static final short[] DFA35_special;
    static final short[][] DFA35_transition;

    public void grammarError(ErrorType etype, Token token, Object ... args) {
    }

    public Token getRuleOrSubruleStartToken() {
        int n;
        if (this.tokens == null) {
            return null;
        }
        int i = this.tokens.index();
        if (i >= (n = this.tokens.size())) {
            i = n - 1;
        }
        while (i >= 0 && i < n) {
            int ttype = this.tokens.get(i).getType();
            if (ttype == 34 || ttype == 66 || ttype == 57) {
                return this.tokens.get(i);
            }
            --i;
        }
        return null;
    }

    public Lexer[] getDelegates() {
        return new Lexer[0];
    }

    public ANTLRLexer() {
    }

    public ANTLRLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }

    public ANTLRLexer(CharStream input, RecognizerSharedState state) {
        super(input, state);
    }

    @Override
    public String getGrammarFileName() {
        return "org\\antlr\\v4\\parse\\ANTLRLexer.g";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mDOC_COMMENT() throws RecognitionException {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mCOMMENT() throws RecognitionException {
        int _type = 17;
        int _channel = 0;
        int startLine = this.state.tokenStartLine;
        int offset = this.getCharPositionInLine();
        this.match(47);
        if (this.state.failed) {
            return;
        }
        int alt6 = 3;
        switch (this.input.LA(1)) {
            case 47: {
                alt6 = 1;
                break;
            }
            case 42: {
                alt6 = 2;
                break;
            }
            default: {
                alt6 = 3;
            }
        }
        switch (alt6) {
            case 1: {
                this.match(47);
                if (this.state.failed) {
                    return;
                }
                int alt2 = 2;
                alt2 = this.dfa2.predict(this.input);
                switch (alt2) {
                    case 1: {
                        this.match(" $ANTLR");
                        if (this.state.failed) {
                            return;
                        }
                        this.mSRC();
                        if (!this.state.failed) break;
                        return;
                    }
                    case 2: {
                        block28: while (true) {
                            int alt1 = 2;
                            int LA1_0 = this.input.LA(1);
                            if (LA1_0 >= 0 && LA1_0 <= 9 || LA1_0 >= 11 && LA1_0 <= 12 || LA1_0 >= 14 && LA1_0 <= 65535) {
                                alt1 = 1;
                            }
                            switch (alt1) {
                                case 1: {
                                    if (this.input.LA(1) >= 0 && this.input.LA(1) <= 9 || this.input.LA(1) >= 11 && this.input.LA(1) <= 12 || this.input.LA(1) >= 14 && this.input.LA(1) <= 65535) {
                                        this.input.consume();
                                        this.state.failed = false;
                                        continue block28;
                                    }
                                    if (this.state.backtracking > 0) {
                                        this.state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, this.input);
                                    this.recover(mse);
                                    throw mse;
                                }
                            }
                            break;
                        }
                        break;
                    }
                }
                break;
            }
            case 2: {
                this.match(42);
                if (this.state.failed) {
                    return;
                }
                int alt3 = 2;
                int LA3_0 = this.input.LA(1);
                if (LA3_0 == 42) {
                    if (this.input.LA(2) == 47) {
                        // empty if block
                    }
                    int LA3_1 = this.input.LA(2);
                    alt3 = this.input.LA(2) != 47 ? 1 : 2;
                }
                switch (alt3) {
                    case 1: {
                        if (this.input.LA(2) == 47) {
                            if (this.state.backtracking > 0) {
                                this.state.failed = true;
                                return;
                            }
                            throw new FailedPredicateException(this.input, "COMMENT", " input.LA(2) != '/'");
                        }
                        this.match(42);
                        if (this.state.failed) {
                            return;
                        }
                        if (this.state.backtracking != 0) break;
                        _type = 18;
                        break;
                    }
                }
                block29: while (true) {
                    int alt4 = 2;
                    int LA4_0 = this.input.LA(1);
                    if (LA4_0 == 42) {
                        int LA4_1 = this.input.LA(2);
                        if (LA4_1 == 47) {
                            int LA4_4 = this.input.LA(3);
                            if (this.input.LA(1) != 42 || this.input.LA(2) != 47) {
                                alt4 = 1;
                            }
                        } else {
                            alt4 = 1;
                        }
                    } else if (LA4_0 >= 0 && LA4_0 <= 41 || LA4_0 >= 43 && LA4_0 <= 65535) {
                        alt4 = 1;
                    }
                    switch (alt4) {
                        case 1: {
                            if (this.input.LA(1) == 42 && this.input.LA(2) == 47) {
                                if (this.state.backtracking > 0) {
                                    this.state.failed = true;
                                    return;
                                }
                                throw new FailedPredicateException(this.input, "COMMENT", "    !(input.LA(1) == '*' && input.LA(2) == '/') ");
                            }
                            this.matchAny();
                            if (!this.state.failed) continue block29;
                            return;
                        }
                    }
                    break;
                }
                int alt5 = 2;
                int LA5_0 = this.input.LA(1);
                alt5 = LA5_0 == 42 ? 1 : 2;
                switch (alt5) {
                    case 1: {
                        this.match("*/");
                        if (!this.state.failed) break;
                        return;
                    }
                    case 2: {
                        if (this.state.backtracking != 0) break;
                    }
                }
                break;
            }
            case 3: {
                if (this.state.backtracking != 0) break;
            }
        }
        if (this.state.backtracking == 0) {
            _channel = 2;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mARG_OR_CHARSET() throws RecognitionException {
        int _type = 9;
        int _channel = 0;
        int alt7 = 2;
        int LA7_0 = this.input.LA(1);
        if (LA7_0 == 91 && (!this.isLexerRule || this.isLexerRule)) {
            int LA7_1 = this.input.LA(2);
            if (this.isLexerRule) {
                alt7 = 1;
            } else if (!this.isLexerRule) {
                alt7 = 2;
            } else {
                if (this.state.backtracking > 0) {
                    this.state.failed = true;
                    return;
                }
                int nvaeMark = this.input.mark();
                try {
                    this.input.consume();
                    NoViableAltException nvae = new NoViableAltException("", 7, 1, this.input);
                    throw nvae;
                }
                catch (Throwable throwable) {
                    this.input.rewind(nvaeMark);
                    throw throwable;
                }
            }
        }
        switch (alt7) {
            case 1: {
                if (!this.isLexerRule) {
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    throw new FailedPredicateException(this.input, "ARG_OR_CHARSET", "isLexerRule");
                }
                this.mLEXER_CHAR_SET();
                if (this.state.failed) {
                    return;
                }
                if (this.state.backtracking != 0) break;
                _type = 32;
                break;
            }
            case 2: {
                if (this.isLexerRule) {
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    throw new FailedPredicateException(this.input, "ARG_OR_CHARSET", "!isLexerRule");
                }
                this.mARG_ACTION();
                if (this.state.failed) {
                    return;
                }
                if (this.state.backtracking != 0) break;
                _type = 8;
                String t = this.getText();
                t = t.substring(1, t.length() - 1);
                this.setText(t);
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mLEXER_CHAR_SET() throws RecognitionException {
        this.match(91);
        if (this.state.failed) {
            return;
        }
        block6: while (true) {
            int alt8 = 3;
            int LA8_0 = this.input.LA(1);
            if (LA8_0 == 92) {
                alt8 = 1;
            } else if (LA8_0 >= 0 && LA8_0 <= 9 || LA8_0 >= 11 && LA8_0 <= 12 || LA8_0 >= 14 && LA8_0 <= 91 || LA8_0 >= 94 && LA8_0 <= 65535) {
                alt8 = 2;
            }
            switch (alt8) {
                case 1: {
                    this.match(92);
                    if (this.state.failed) {
                        return;
                    }
                    if (this.input.LA(1) >= 0 && this.input.LA(1) <= 9 || this.input.LA(1) >= 11 && this.input.LA(1) <= 12 || this.input.LA(1) >= 14 && this.input.LA(1) <= 65535) {
                        this.input.consume();
                        this.state.failed = false;
                        continue block6;
                    }
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    this.recover(mse);
                    throw mse;
                }
                case 2: {
                    if (this.input.LA(1) >= 0 && this.input.LA(1) <= 9 || this.input.LA(1) >= 11 && this.input.LA(1) <= 12 || this.input.LA(1) >= 14 && this.input.LA(1) <= 91 || this.input.LA(1) >= 94 && this.input.LA(1) <= 65535) {
                        this.input.consume();
                        this.state.failed = false;
                        continue block6;
                    }
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    this.recover(mse);
                    throw mse;
                }
            }
            break;
        }
        this.match(93);
        if (this.state.failed) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mARG_ACTION() throws RecognitionException {
        this.match(91);
        if (this.state.failed) {
            return;
        }
        block8: while (true) {
            int alt9 = 5;
            int LA9_0 = this.input.LA(1);
            if (LA9_0 == 91) {
                alt9 = 1;
            } else if (LA9_0 == 34) {
                int LA9_3 = this.input.LA(2);
                alt9 = this.synpred2_ANTLRLexer() ? 2 : 4;
            } else if (LA9_0 == 39) {
                int LA9_4 = this.input.LA(2);
                alt9 = this.synpred3_ANTLRLexer() ? 3 : 4;
            } else if (LA9_0 >= 0 && LA9_0 <= 33 || LA9_0 >= 35 && LA9_0 <= 38 || LA9_0 >= 40 && LA9_0 <= 90 || LA9_0 == 92 || LA9_0 >= 94 && LA9_0 <= 65535) {
                alt9 = 4;
            }
            switch (alt9) {
                case 1: {
                    this.mARG_ACTION();
                    if (!this.state.failed) continue block8;
                    return;
                }
                case 2: {
                    this.mACTION_STRING_LITERAL();
                    if (!this.state.failed) continue block8;
                    return;
                }
                case 3: {
                    this.mACTION_CHAR_LITERAL();
                    if (!this.state.failed) continue block8;
                    return;
                }
                case 4: {
                    if (this.input.LA(1) >= 0 && this.input.LA(1) <= 90 || this.input.LA(1) == 92 || this.input.LA(1) >= 94 && this.input.LA(1) <= 65535) {
                        this.input.consume();
                        this.state.failed = false;
                        continue block8;
                    }
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    this.recover(mse);
                    throw mse;
                }
            }
            break;
        }
        this.match(93);
        if (this.state.failed) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    public final void mACTION() throws RecognitionException {
        _type = 4;
        _channel = 0;
        this.mNESTED_ACTION();
        if (this.state.failed) {
            return;
        }
        alt12 = 2;
        LA12_0 = this.input.LA(1);
        if (LA12_0 == 63) {
            alt12 = 1;
        }
        switch (alt12) {
            case 1: {
                this.match(63);
                if (this.state.failed) {
                    return;
                }
                if (this.state.backtracking == 0) {
                    _type = 59;
                }
                alt11 = 2;
                LA11_0 = this.input.LA(1);
                if ((LA11_0 >= 9 && LA11_0 <= 10 || LA11_0 >= 12 && LA11_0 <= 13 || LA11_0 == 32) && this.synpred4_ANTLRLexer()) {
                    alt11 = 1;
                } else if (LA11_0 == 61 && this.synpred4_ANTLRLexer()) {
                    alt11 = 1;
                }
                switch (alt11) {
                    case 1: {
                        block11: while (true) {
                            alt10 = 2;
                            LA10_0 = this.input.LA(1);
                            if (LA10_0 >= 9 && LA10_0 <= 10 || LA10_0 >= 12 && LA10_0 <= 13 || LA10_0 == 32) {
                                alt10 = 1;
                            }
                            switch (alt10) {
                                case 1: {
                                    if (this.input.LA(1) >= 9 && this.input.LA(1) <= 10 || this.input.LA(1) >= 12 && this.input.LA(1) <= 13 || this.input.LA(1) == 32) {
                                        this.input.consume();
                                        this.state.failed = false;
                                        continue block11;
                                    }
                                    if (this.state.backtracking > 0) {
                                        this.state.failed = true;
                                        return;
                                    }
                                    mse = new MismatchedSetException(null, this.input);
                                    this.recover(mse);
                                    throw mse;
                                }
                            }
                            break;
                        }
                        this.match("=>");
                        if (this.state.failed) {
                            return;
                        }
                        if (this.state.backtracking == 0) {
                            t = new CommonToken(this.input, this.state.type, this.state.channel, this.state.tokenStartCharIndex, this.getCharIndex() - 1);
                            t.setLine(this.state.tokenStartLine);
                            t.setText(this.state.text);
                            t.setCharPositionInLine(this.state.tokenStartCharPositionInLine);
                            this.grammarError(ErrorType.V3_GATED_SEMPRED, t, new Object[0]);
                        } else {
                            ** GOTO lbl53
                        }
                    }
                }
            }
        }
lbl53:
        // 5 sources

        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mNESTED_ACTION() throws RecognitionException {
        int startLine = this.getLine();
        int offset = this.getCharPositionInLine();
        this.match(123);
        if (this.state.failed) {
            return;
        }
        block14: while (true) {
            int alt13 = 7;
            int LA13_0 = this.input.LA(1);
            if (LA13_0 == 123) {
                alt13 = 1;
            } else if (LA13_0 == 39) {
                alt13 = 2;
            } else if (LA13_0 == 47) {
                alt13 = 3;
            } else if (LA13_0 == 34) {
                alt13 = 4;
            } else if (LA13_0 == 92) {
                alt13 = 5;
            } else if (LA13_0 >= 0 && LA13_0 <= 33 || LA13_0 >= 35 && LA13_0 <= 38 || LA13_0 >= 40 && LA13_0 <= 46 || LA13_0 >= 48 && LA13_0 <= 91 || LA13_0 >= 93 && LA13_0 <= 122 || LA13_0 == 124 || LA13_0 >= 126 && LA13_0 <= 65535) {
                alt13 = 6;
            }
            switch (alt13) {
                case 1: {
                    this.mNESTED_ACTION();
                    if (!this.state.failed) continue block14;
                    return;
                }
                case 2: {
                    this.mACTION_CHAR_LITERAL();
                    if (!this.state.failed) continue block14;
                    return;
                }
                case 3: {
                    this.mCOMMENT();
                    if (!this.state.failed) continue block14;
                    return;
                }
                case 4: {
                    this.mACTION_STRING_LITERAL();
                    if (!this.state.failed) continue block14;
                    return;
                }
                case 5: {
                    this.mACTION_ESC();
                    if (!this.state.failed) continue block14;
                    return;
                }
                case 6: {
                    if (this.input.LA(1) >= 0 && this.input.LA(1) <= 33 || this.input.LA(1) >= 35 && this.input.LA(1) <= 38 || this.input.LA(1) >= 40 && this.input.LA(1) <= 46 || this.input.LA(1) >= 48 && this.input.LA(1) <= 91 || this.input.LA(1) >= 93 && this.input.LA(1) <= 122 || this.input.LA(1) == 124 || this.input.LA(1) >= 126 && this.input.LA(1) <= 65535) {
                        this.input.consume();
                        this.state.failed = false;
                        continue block14;
                    }
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    this.recover(mse);
                    throw mse;
                }
            }
            break;
        }
        int alt14 = 2;
        int LA14_0 = this.input.LA(1);
        alt14 = LA14_0 == 125 ? 1 : 2;
        switch (alt14) {
            case 1: {
                this.match(125);
                if (!this.state.failed) break;
                return;
            }
            case 2: {
                if (this.state.backtracking != 0) break;
                System.out.println("Block starting  at line " + startLine + " offset " + (offset + 1) + " contains imbalanced {} or is missing a }");
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mOPTIONS() throws RecognitionException {
        int _type = 42;
        int _channel = 0;
        this.match("options");
        if (this.state.failed) {
            return;
        }
        block5: while (true) {
            int alt15 = 2;
            int LA15_0 = this.input.LA(1);
            if (LA15_0 >= 9 && LA15_0 <= 10 || LA15_0 >= 12 && LA15_0 <= 13 || LA15_0 == 32) {
                alt15 = 1;
            }
            switch (alt15) {
                case 1: {
                    if (this.input.LA(1) >= 9 && this.input.LA(1) <= 10 || this.input.LA(1) >= 12 && this.input.LA(1) <= 13 || this.input.LA(1) == 32) {
                        this.input.consume();
                        this.state.failed = false;
                        continue block5;
                    }
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    this.recover(mse);
                    throw mse;
                }
            }
            break;
        }
        this.match(123);
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mTOKENS_SPEC() throws RecognitionException {
        int _type = 65;
        int _channel = 0;
        this.match("tokens");
        if (this.state.failed) {
            return;
        }
        block5: while (true) {
            int alt16 = 2;
            int LA16_0 = this.input.LA(1);
            if (LA16_0 >= 9 && LA16_0 <= 10 || LA16_0 >= 12 && LA16_0 <= 13 || LA16_0 == 32) {
                alt16 = 1;
            }
            switch (alt16) {
                case 1: {
                    if (this.input.LA(1) >= 9 && this.input.LA(1) <= 10 || this.input.LA(1) >= 12 && this.input.LA(1) <= 13 || this.input.LA(1) == 32) {
                        this.input.consume();
                        this.state.failed = false;
                        continue block5;
                    }
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    this.recover(mse);
                    throw mse;
                }
            }
            break;
        }
        this.match(123);
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mCHANNELS() throws RecognitionException {
        int _type = 13;
        int _channel = 0;
        this.match("channels");
        if (this.state.failed) {
            return;
        }
        block5: while (true) {
            int alt17 = 2;
            int LA17_0 = this.input.LA(1);
            if (LA17_0 >= 9 && LA17_0 <= 10 || LA17_0 >= 12 && LA17_0 <= 13 || LA17_0 == 32) {
                alt17 = 1;
            }
            switch (alt17) {
                case 1: {
                    if (this.input.LA(1) >= 9 && this.input.LA(1) <= 10 || this.input.LA(1) >= 12 && this.input.LA(1) <= 13 || this.input.LA(1) == 32) {
                        this.input.consume();
                        this.state.failed = false;
                        continue block5;
                    }
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    this.recover(mse);
                    throw mse;
                }
            }
            break;
        }
        this.match(123);
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mIMPORT() throws RecognitionException {
        int _type = 29;
        int _channel = 0;
        this.match("import");
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mFRAGMENT() throws RecognitionException {
        int _type = 24;
        int _channel = 0;
        this.match("fragment");
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mLEXER() throws RecognitionException {
        int _type = 31;
        int _channel = 0;
        this.match("lexer");
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mPARSER() throws RecognitionException {
        int _type = 44;
        int _channel = 0;
        this.match("parser");
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mGRAMMAR() throws RecognitionException {
        int _type = 25;
        int _channel = 0;
        this.match("grammar");
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mTREE_GRAMMAR() throws RecognitionException {
        int _type = 67;
        int _channel = 0;
        this.match("tree");
        if (this.state.failed) {
            return;
        }
        block5: while (true) {
            int alt18 = 2;
            int LA18_0 = this.input.LA(1);
            if (LA18_0 >= 9 && LA18_0 <= 10 || LA18_0 >= 12 && LA18_0 <= 13 || LA18_0 == 32) {
                alt18 = 1;
            }
            switch (alt18) {
                case 1: {
                    if (this.input.LA(1) >= 9 && this.input.LA(1) <= 10 || this.input.LA(1) >= 12 && this.input.LA(1) <= 13 || this.input.LA(1) == 32) {
                        this.input.consume();
                        this.state.failed = false;
                        continue block5;
                    }
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    this.recover(mse);
                    throw mse;
                }
            }
            break;
        }
        this.match("grammar");
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mPROTECTED() throws RecognitionException {
        int _type = 49;
        int _channel = 0;
        this.match("protected");
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mPUBLIC() throws RecognitionException {
        int _type = 50;
        int _channel = 0;
        this.match("public");
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mPRIVATE() throws RecognitionException {
        int _type = 48;
        int _channel = 0;
        this.match("private");
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mRETURNS() throws RecognitionException {
        int _type = 55;
        int _channel = 0;
        this.match("returns");
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mLOCALS() throws RecognitionException {
        int _type = 33;
        int _channel = 0;
        this.match("locals");
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mTHROWS() throws RecognitionException {
        int _type = 64;
        int _channel = 0;
        this.match("throws");
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mCATCH() throws RecognitionException {
        int _type = 12;
        int _channel = 0;
        this.match("catch");
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mFINALLY() throws RecognitionException {
        int _type = 23;
        int _channel = 0;
        this.match("finally");
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mMODE() throws RecognitionException {
        int _type = 36;
        int _channel = 0;
        this.match("mode");
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mCOLON() throws RecognitionException {
        Token t;
        int _type = 14;
        int _channel = 0;
        this.match(58);
        if (this.state.failed) {
            return;
        }
        if (this.state.backtracking == 0 && (t = this.getRuleOrSubruleStartToken()) != null) {
            if (t.getType() == 57) {
                this.isLexerRule = false;
            } else if (t.getType() == 66) {
                this.isLexerRule = true;
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mCOLONCOLON() throws RecognitionException {
        int _type = 15;
        int _channel = 0;
        this.match("::");
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mCOMMA() throws RecognitionException {
        int _type = 16;
        int _channel = 0;
        this.match(44);
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mSEMI() throws RecognitionException {
        int _type = 58;
        int _channel = 0;
        this.match(59);
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mLPAREN() throws RecognitionException {
        int _type = 34;
        int _channel = 0;
        this.match(40);
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mRPAREN() throws RecognitionException {
        int _type = 56;
        int _channel = 0;
        this.match(41);
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mRARROW() throws RecognitionException {
        int _type = 53;
        int _channel = 0;
        this.match("->");
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mLT() throws RecognitionException {
        int _type = 35;
        int _channel = 0;
        this.match(60);
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mGT() throws RecognitionException {
        int _type = 26;
        int _channel = 0;
        this.match(62);
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mASSIGN() throws RecognitionException {
        int _type = 10;
        int _channel = 0;
        this.match(61);
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mQUESTION() throws RecognitionException {
        int _type = 51;
        int _channel = 0;
        this.match(63);
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mSYNPRED() throws RecognitionException {
        int _type = 63;
        int _channel = 0;
        this.match("=>");
        if (this.state.failed) {
            return;
        }
        if (this.state.backtracking == 0) {
            CommonToken t = new CommonToken(this.input, this.state.type, this.state.channel, this.state.tokenStartCharIndex, this.getCharIndex() - 1);
            t.setLine(this.state.tokenStartLine);
            t.setText(this.state.text);
            t.setCharPositionInLine(this.state.tokenStartCharPositionInLine);
            this.grammarError(ErrorType.V3_SYNPRED, t, new Object[0]);
            _channel = 99;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mSTAR() throws RecognitionException {
        int _type = 61;
        int _channel = 0;
        this.match(42);
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mPLUS() throws RecognitionException {
        int _type = 45;
        int _channel = 0;
        this.match(43);
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mPLUS_ASSIGN() throws RecognitionException {
        int _type = 46;
        int _channel = 0;
        this.match("+=");
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mOR() throws RecognitionException {
        int _type = 43;
        int _channel = 0;
        this.match(124);
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mDOLLAR() throws RecognitionException {
        int _type = 19;
        int _channel = 0;
        this.match(36);
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mDOT() throws RecognitionException {
        int _type = 20;
        int _channel = 0;
        this.match(46);
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mRANGE() throws RecognitionException {
        int _type = 52;
        int _channel = 0;
        this.match("..");
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mAT() throws RecognitionException {
        int _type = 11;
        int _channel = 0;
        this.match(64);
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mPOUND() throws RecognitionException {
        int _type = 47;
        int _channel = 0;
        this.match(35);
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mNOT() throws RecognitionException {
        int _type = 39;
        int _channel = 0;
        this.match(126);
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mRBRACE() throws RecognitionException {
        int _type = 54;
        int _channel = 0;
        this.match(125);
        if (this.state.failed) {
            return;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mID() throws RecognitionException {
        int _type = 28;
        int _channel = 0;
        CommonToken a = null;
        int aStart2935 = this.getCharIndex();
        int aStartLine2935 = this.getLine();
        int aStartCharPos2935 = this.getCharPositionInLine();
        this.mNameStartChar();
        if (this.state.failed) {
            return;
        }
        a = new CommonToken(this.input, 0, 0, aStart2935, this.getCharIndex() - 1);
        a.setLine(aStartLine2935);
        a.setCharPositionInLine(aStartCharPos2935);
        block5: while (true) {
            int alt19 = 2;
            int LA19_0 = this.input.LA(1);
            if (LA19_0 >= 48 && LA19_0 <= 57 || LA19_0 >= 65 && LA19_0 <= 90 || LA19_0 == 95 || LA19_0 >= 97 && LA19_0 <= 122 || LA19_0 == 183 || LA19_0 >= 192 && LA19_0 <= 214 || LA19_0 >= 216 && LA19_0 <= 246 || LA19_0 >= 248 && LA19_0 <= 893 || LA19_0 >= 895 && LA19_0 <= 8191 || LA19_0 >= 8204 && LA19_0 <= 8205 || LA19_0 >= 8255 && LA19_0 <= 8256 || LA19_0 >= 8304 && LA19_0 <= 8591 || LA19_0 >= 11264 && LA19_0 <= 12271 || LA19_0 >= 12289 && LA19_0 <= 55295 || LA19_0 >= 63744 && LA19_0 <= 64975 || LA19_0 >= 65008 && LA19_0 <= 65278 || LA19_0 >= 65280 && LA19_0 <= 65533) {
                alt19 = 1;
            }
            switch (alt19) {
                case 1: {
                    if (this.input.LA(1) >= 48 && this.input.LA(1) <= 57 || this.input.LA(1) >= 65 && this.input.LA(1) <= 90 || this.input.LA(1) == 95 || this.input.LA(1) >= 97 && this.input.LA(1) <= 122 || this.input.LA(1) == 183 || this.input.LA(1) >= 192 && this.input.LA(1) <= 214 || this.input.LA(1) >= 216 && this.input.LA(1) <= 246 || this.input.LA(1) >= 248 && this.input.LA(1) <= 893 || this.input.LA(1) >= 895 && this.input.LA(1) <= 8191 || this.input.LA(1) >= 8204 && this.input.LA(1) <= 8205 || this.input.LA(1) >= 8255 && this.input.LA(1) <= 8256 || this.input.LA(1) >= 8304 && this.input.LA(1) <= 8591 || this.input.LA(1) >= 11264 && this.input.LA(1) <= 12271 || this.input.LA(1) >= 12289 && this.input.LA(1) <= 55295 || this.input.LA(1) >= 63744 && this.input.LA(1) <= 64975 || this.input.LA(1) >= 65008 && this.input.LA(1) <= 65278 || this.input.LA(1) >= 65280 && this.input.LA(1) <= 65533) {
                        this.input.consume();
                        this.state.failed = false;
                        continue block5;
                    }
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    this.recover(mse);
                    throw mse;
                }
            }
            break;
        }
        if (this.state.backtracking == 0) {
            _type = Grammar.isTokenName(a != null ? a.getText() : null) ? 66 : 57;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mNameChar() throws RecognitionException {
        if (!(this.input.LA(1) >= 48 && this.input.LA(1) <= 57 || this.input.LA(1) >= 65 && this.input.LA(1) <= 90 || this.input.LA(1) == 95 || this.input.LA(1) >= 97 && this.input.LA(1) <= 122 || this.input.LA(1) == 183 || this.input.LA(1) >= 192 && this.input.LA(1) <= 214 || this.input.LA(1) >= 216 && this.input.LA(1) <= 246 || this.input.LA(1) >= 248 && this.input.LA(1) <= 893 || this.input.LA(1) >= 895 && this.input.LA(1) <= 8191 || this.input.LA(1) >= 8204 && this.input.LA(1) <= 8205 || this.input.LA(1) >= 8255 && this.input.LA(1) <= 8256 || this.input.LA(1) >= 8304 && this.input.LA(1) <= 8591 || this.input.LA(1) >= 11264 && this.input.LA(1) <= 12271 || this.input.LA(1) >= 12289 && this.input.LA(1) <= 55295 || this.input.LA(1) >= 63744 && this.input.LA(1) <= 64975 || this.input.LA(1) >= 65008 && this.input.LA(1) <= 65278 || this.input.LA(1) >= 65280 && this.input.LA(1) <= 65533)) {
            if (this.state.backtracking > 0) {
                this.state.failed = true;
                return;
            }
            MismatchedSetException mse = new MismatchedSetException(null, this.input);
            this.recover(mse);
            throw mse;
        }
        this.input.consume();
        this.state.failed = false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mNameStartChar() throws RecognitionException {
        if (!(this.input.LA(1) >= 65 && this.input.LA(1) <= 90 || this.input.LA(1) >= 97 && this.input.LA(1) <= 122 || this.input.LA(1) >= 192 && this.input.LA(1) <= 214 || this.input.LA(1) >= 216 && this.input.LA(1) <= 246 || this.input.LA(1) >= 248 && this.input.LA(1) <= 767 || this.input.LA(1) >= 880 && this.input.LA(1) <= 893 || this.input.LA(1) >= 895 && this.input.LA(1) <= 8191 || this.input.LA(1) >= 8204 && this.input.LA(1) <= 8205 || this.input.LA(1) >= 8304 && this.input.LA(1) <= 8591 || this.input.LA(1) >= 11264 && this.input.LA(1) <= 12271 || this.input.LA(1) >= 12289 && this.input.LA(1) <= 55295 || this.input.LA(1) >= 63744 && this.input.LA(1) <= 64975 || this.input.LA(1) >= 65008 && this.input.LA(1) <= 65278 || this.input.LA(1) >= 65280 && this.input.LA(1) <= 65533)) {
            if (this.state.backtracking > 0) {
                this.state.failed = true;
                return;
            }
            MismatchedSetException mse = new MismatchedSetException(null, this.input);
            this.recover(mse);
            throw mse;
        }
        this.input.consume();
        this.state.failed = false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mACTION_CHAR_LITERAL() throws RecognitionException {
        this.match(39);
        if (this.state.failed) {
            return;
        }
        block6: while (true) {
            int alt20 = 3;
            int LA20_0 = this.input.LA(1);
            if (LA20_0 == 92) {
                int LA20_2 = this.input.LA(2);
                if (LA20_2 == 39) {
                    int LA20_4 = this.input.LA(3);
                    alt20 = LA20_4 == 39 && this.synpred5_ANTLRLexer() ? 1 : (LA20_4 == 92 && this.synpred5_ANTLRLexer() ? 1 : ((LA20_4 >= 0 && LA20_4 <= 38 || LA20_4 >= 40 && LA20_4 <= 91 || LA20_4 >= 93 && LA20_4 <= 65535) && this.synpred5_ANTLRLexer() ? 1 : 2));
                } else if (LA20_2 == 92) {
                    int LA20_5 = this.input.LA(3);
                    alt20 = this.synpred5_ANTLRLexer() ? 1 : 2;
                } else if (LA20_2 >= 0 && LA20_2 <= 38 || LA20_2 >= 40 && LA20_2 <= 91 || LA20_2 >= 93 && LA20_2 <= 65535) {
                    int LA20_6 = this.input.LA(3);
                    alt20 = this.synpred5_ANTLRLexer() ? 1 : 2;
                }
            } else if (LA20_0 >= 0 && LA20_0 <= 38 || LA20_0 >= 40 && LA20_0 <= 91 || LA20_0 >= 93 && LA20_0 <= 65535) {
                alt20 = 2;
            }
            switch (alt20) {
                case 1: {
                    this.mACTION_ESC();
                    if (!this.state.failed) continue block6;
                    return;
                }
                case 2: {
                    if (this.input.LA(1) >= 0 && this.input.LA(1) <= 38 || this.input.LA(1) >= 40 && this.input.LA(1) <= 65535) {
                        this.input.consume();
                        this.state.failed = false;
                        continue block6;
                    }
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    this.recover(mse);
                    throw mse;
                }
            }
            break;
        }
        this.match(39);
        if (this.state.failed) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mACTION_STRING_LITERAL() throws RecognitionException {
        this.match(34);
        if (this.state.failed) {
            return;
        }
        block6: while (true) {
            int alt21 = 3;
            int LA21_0 = this.input.LA(1);
            if (LA21_0 == 92) {
                int LA21_2 = this.input.LA(2);
                if (LA21_2 == 34) {
                    int LA21_4 = this.input.LA(3);
                    alt21 = LA21_4 == 34 && this.synpred6_ANTLRLexer() ? 1 : (LA21_4 == 92 && this.synpred6_ANTLRLexer() ? 1 : ((LA21_4 >= 0 && LA21_4 <= 33 || LA21_4 >= 35 && LA21_4 <= 91 || LA21_4 >= 93 && LA21_4 <= 65535) && this.synpred6_ANTLRLexer() ? 1 : 2));
                } else if (LA21_2 == 92) {
                    int LA21_5 = this.input.LA(3);
                    alt21 = this.synpred6_ANTLRLexer() ? 1 : 2;
                } else if (LA21_2 >= 0 && LA21_2 <= 33 || LA21_2 >= 35 && LA21_2 <= 91 || LA21_2 >= 93 && LA21_2 <= 65535) {
                    int LA21_6 = this.input.LA(3);
                    alt21 = this.synpred6_ANTLRLexer() ? 1 : 2;
                }
            } else if (LA21_0 >= 0 && LA21_0 <= 33 || LA21_0 >= 35 && LA21_0 <= 91 || LA21_0 >= 93 && LA21_0 <= 65535) {
                alt21 = 2;
            }
            switch (alt21) {
                case 1: {
                    this.mACTION_ESC();
                    if (!this.state.failed) continue block6;
                    return;
                }
                case 2: {
                    if (this.input.LA(1) >= 0 && this.input.LA(1) <= 33 || this.input.LA(1) >= 35 && this.input.LA(1) <= 65535) {
                        this.input.consume();
                        this.state.failed = false;
                        continue block6;
                    }
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    this.recover(mse);
                    throw mse;
                }
            }
            break;
        }
        this.match(34);
        if (this.state.failed) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mACTION_ESC() throws RecognitionException {
        this.match(92);
        if (this.state.failed) {
            return;
        }
        this.matchAny();
        if (this.state.failed) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mINT() throws RecognitionException {
        int _type = 30;
        int _channel = 0;
        int cnt22 = 0;
        block5: while (true) {
            int alt22 = 2;
            int LA22_0 = this.input.LA(1);
            if (LA22_0 >= 48 && LA22_0 <= 57) {
                alt22 = 1;
            }
            switch (alt22) {
                case 1: {
                    if (this.input.LA(1) >= 48 && this.input.LA(1) <= 57) {
                        this.input.consume();
                        this.state.failed = false;
                        break;
                    }
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    this.recover(mse);
                    throw mse;
                }
                default: {
                    if (cnt22 >= 1) break block5;
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    EarlyExitException eee = new EarlyExitException(22, this.input);
                    throw eee;
                }
            }
            ++cnt22;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void mSRC() throws RecognitionException {
        CommonToken file = null;
        CommonToken line = null;
        this.match("src");
        if (this.state.failed) {
            return;
        }
        int cnt23 = 0;
        block8: while (true) {
            int alt23 = 2;
            int LA23_0 = this.input.LA(1);
            if (LA23_0 == 9 || LA23_0 == 12 || LA23_0 == 32) {
                alt23 = 1;
            }
            switch (alt23) {
                case 1: {
                    if (this.input.LA(1) == 9 || this.input.LA(1) == 12 || this.input.LA(1) == 32) {
                        this.input.consume();
                        this.state.failed = false;
                        break;
                    }
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    this.recover(mse);
                    throw mse;
                }
                default: {
                    if (cnt23 >= 1) break block8;
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    EarlyExitException eee = new EarlyExitException(23, this.input);
                    throw eee;
                }
            }
            ++cnt23;
        }
        int fileStart3507 = this.getCharIndex();
        int fileStartLine3507 = this.getLine();
        int fileStartCharPos3507 = this.getCharPositionInLine();
        this.mACTION_STRING_LITERAL();
        if (this.state.failed) {
            return;
        }
        file = new CommonToken(this.input, 0, 0, fileStart3507, this.getCharIndex() - 1);
        file.setLine(fileStartLine3507);
        file.setCharPositionInLine(fileStartCharPos3507);
        int cnt24 = 0;
        block9: while (true) {
            int alt24 = 2;
            int LA24_0 = this.input.LA(1);
            if (LA24_0 == 9 || LA24_0 == 12 || LA24_0 == 32) {
                alt24 = 1;
            }
            switch (alt24) {
                case 1: {
                    if (this.input.LA(1) == 9 || this.input.LA(1) == 12 || this.input.LA(1) == 32) {
                        this.input.consume();
                        this.state.failed = false;
                        break;
                    }
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    this.recover(mse);
                    throw mse;
                }
                default: {
                    if (cnt24 >= 1) break block9;
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    EarlyExitException eee = new EarlyExitException(24, this.input);
                    throw eee;
                }
            }
            ++cnt24;
        }
        int lineStart3514 = this.getCharIndex();
        int lineStartLine3514 = this.getLine();
        int lineStartCharPos3514 = this.getCharPositionInLine();
        this.mINT();
        if (this.state.failed) {
            return;
        }
        line = new CommonToken(this.input, 0, 0, lineStart3514, this.getCharIndex() - 1);
        line.setLine(lineStartLine3514);
        line.setCharPositionInLine(lineStartCharPos3514);
        if (this.state.backtracking != 0) return;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mSTRING_LITERAL() throws RecognitionException {
        int _type = 62;
        int _channel = 0;
        this.match(39);
        if (this.state.failed) {
            return;
        }
        block13: while (true) {
            int alt26 = 2;
            int LA26_0 = this.input.LA(1);
            if (LA26_0 >= 0 && LA26_0 <= 9 || LA26_0 >= 11 && LA26_0 <= 12 || LA26_0 >= 14 && LA26_0 <= 38 || LA26_0 >= 40 && LA26_0 <= 65535) {
                alt26 = 1;
            }
            switch (alt26) {
                case 1: {
                    int alt25 = 2;
                    int LA25_0 = this.input.LA(1);
                    if (LA25_0 == 92) {
                        alt25 = 1;
                    } else if (LA25_0 >= 0 && LA25_0 <= 9 || LA25_0 >= 11 && LA25_0 <= 12 || LA25_0 >= 14 && LA25_0 <= 38 || LA25_0 >= 40 && LA25_0 <= 91 || LA25_0 >= 93 && LA25_0 <= 65535) {
                        alt25 = 2;
                    } else {
                        if (this.state.backtracking > 0) {
                            this.state.failed = true;
                            return;
                        }
                        NoViableAltException nvae = new NoViableAltException("", 25, 0, this.input);
                        throw nvae;
                    }
                    switch (alt25) {
                        case 1: {
                            this.mESC_SEQ();
                            if (!this.state.failed) break;
                            return;
                        }
                        case 2: {
                            if (this.input.LA(1) >= 0 && this.input.LA(1) <= 9 || this.input.LA(1) >= 11 && this.input.LA(1) <= 12 || this.input.LA(1) >= 14 && this.input.LA(1) <= 38 || this.input.LA(1) >= 40 && this.input.LA(1) <= 91 || this.input.LA(1) >= 93 && this.input.LA(1) <= 65535) {
                                this.input.consume();
                                this.state.failed = false;
                                break;
                            }
                            if (this.state.backtracking > 0) {
                                this.state.failed = true;
                                return;
                            }
                            MismatchedSetException mse = new MismatchedSetException(null, this.input);
                            this.recover(mse);
                            throw mse;
                        }
                    }
                    continue block13;
                }
            }
            break;
        }
        int alt27 = 2;
        int LA27_0 = this.input.LA(1);
        alt27 = LA27_0 == 39 ? 1 : 2;
        switch (alt27) {
            case 1: {
                this.match(39);
                if (!this.state.failed) break;
                return;
            }
            case 2: {
                if (this.state.backtracking != 0) break;
                CommonToken t = new CommonToken(this.input, this.state.type, this.state.channel, this.state.tokenStartCharIndex, this.getCharIndex() - 1);
                t.setLine(this.state.tokenStartLine);
                t.setText(this.state.text);
                t.setCharPositionInLine(this.state.tokenStartCharPositionInLine);
                this.grammarError(ErrorType.UNTERMINATED_STRING_LITERAL, t, new Object[0]);
            }
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mHEX_DIGIT() throws RecognitionException {
        if (!(this.input.LA(1) >= 48 && this.input.LA(1) <= 57 || this.input.LA(1) >= 65 && this.input.LA(1) <= 70 || this.input.LA(1) >= 97 && this.input.LA(1) <= 102)) {
            if (this.state.backtracking > 0) {
                this.state.failed = true;
                return;
            }
            MismatchedSetException mse = new MismatchedSetException(null, this.input);
            this.recover(mse);
            throw mse;
        }
        this.input.consume();
        this.state.failed = false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mESC_SEQ() throws RecognitionException {
        this.match(92);
        if (this.state.failed) {
            return;
        }
        int alt28 = 10;
        int LA28_0 = this.input.LA(1);
        if (LA28_0 == 98) {
            alt28 = 1;
        } else if (LA28_0 == 116) {
            alt28 = 2;
        } else if (LA28_0 == 110) {
            alt28 = 3;
        } else if (LA28_0 == 102) {
            alt28 = 4;
        } else if (LA28_0 == 114) {
            alt28 = 5;
        } else if (LA28_0 == 39) {
            alt28 = 6;
        } else if (LA28_0 == 92) {
            alt28 = 7;
        } else if (LA28_0 == 117) {
            int LA28_8 = this.input.LA(2);
            alt28 = LA28_8 == 123 ? 9 : 8;
        } else if (LA28_0 >= 0 && LA28_0 <= 38 || LA28_0 >= 40 && LA28_0 <= 91 || LA28_0 >= 93 && LA28_0 <= 97 || LA28_0 >= 99 && LA28_0 <= 101 || LA28_0 >= 103 && LA28_0 <= 109 || LA28_0 >= 111 && LA28_0 <= 113 || LA28_0 == 115 || LA28_0 >= 118 && LA28_0 <= 65535) {
            alt28 = 10;
        } else {
            if (this.state.backtracking > 0) {
                this.state.failed = true;
                return;
            }
            NoViableAltException nvae = new NoViableAltException("", 28, 0, this.input);
            throw nvae;
        }
        switch (alt28) {
            case 1: {
                this.match(98);
                if (!this.state.failed) break;
                return;
            }
            case 2: {
                this.match(116);
                if (!this.state.failed) break;
                return;
            }
            case 3: {
                this.match(110);
                if (!this.state.failed) break;
                return;
            }
            case 4: {
                this.match(102);
                if (!this.state.failed) break;
                return;
            }
            case 5: {
                this.match(114);
                if (!this.state.failed) break;
                return;
            }
            case 6: {
                this.match(39);
                if (!this.state.failed) break;
                return;
            }
            case 7: {
                this.match(92);
                if (!this.state.failed) break;
                return;
            }
            case 8: {
                this.mUNICODE_ESC();
                if (!this.state.failed) break;
                return;
            }
            case 9: {
                this.mUNICODE_EXTENDED_ESC();
                if (!this.state.failed) break;
                return;
            }
            case 10: {
                if (!(this.input.LA(1) >= 0 && this.input.LA(1) <= 38 || this.input.LA(1) >= 40 && this.input.LA(1) <= 91 || this.input.LA(1) >= 93 && this.input.LA(1) <= 97 || this.input.LA(1) >= 99 && this.input.LA(1) <= 101 || this.input.LA(1) >= 103 && this.input.LA(1) <= 109 || this.input.LA(1) >= 111 && this.input.LA(1) <= 113 || this.input.LA(1) == 115 || this.input.LA(1) >= 118 && this.input.LA(1) <= 65535)) {
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    this.recover(mse);
                    throw mse;
                }
                this.input.consume();
                this.state.failed = false;
                if (this.state.backtracking != 0) break;
                CommonToken t = new CommonToken(this.input, this.state.type, this.state.channel, this.getCharIndex() - 2, this.getCharIndex() - 1);
                t.setText(t.getText());
                t.setLine(this.input.getLine());
                t.setCharPositionInLine(this.input.getCharPositionInLine() - 2);
                this.grammarError(ErrorType.INVALID_ESCAPE_SEQUENCE, t, this.input.substring(this.getCharIndex() - 2, this.getCharIndex() - 1));
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mUNICODE_ESC() throws RecognitionException {
        int hCount = 0;
        this.match(117);
        if (this.state.failed) {
            return;
        }
        int alt32 = 2;
        int LA32_0 = this.input.LA(1);
        alt32 = LA32_0 >= 48 && LA32_0 <= 57 || LA32_0 >= 65 && LA32_0 <= 70 || LA32_0 >= 97 && LA32_0 <= 102 ? 1 : 2;
        block1 : switch (alt32) {
            case 1: {
                this.mHEX_DIGIT();
                if (this.state.failed) {
                    return;
                }
                if (this.state.backtracking == 0) {
                    ++hCount;
                }
                int alt31 = 2;
                int LA31_0 = this.input.LA(1);
                alt31 = LA31_0 >= 48 && LA31_0 <= 57 || LA31_0 >= 65 && LA31_0 <= 70 || LA31_0 >= 97 && LA31_0 <= 102 ? 1 : 2;
                switch (alt31) {
                    case 1: {
                        this.mHEX_DIGIT();
                        if (this.state.failed) {
                            return;
                        }
                        if (this.state.backtracking == 0) {
                            ++hCount;
                        }
                        int alt30 = 2;
                        int LA30_0 = this.input.LA(1);
                        alt30 = LA30_0 >= 48 && LA30_0 <= 57 || LA30_0 >= 65 && LA30_0 <= 70 || LA30_0 >= 97 && LA30_0 <= 102 ? 1 : 2;
                        switch (alt30) {
                            case 1: {
                                this.mHEX_DIGIT();
                                if (this.state.failed) {
                                    return;
                                }
                                if (this.state.backtracking == 0) {
                                    ++hCount;
                                }
                                int alt29 = 2;
                                int LA29_0 = this.input.LA(1);
                                alt29 = LA29_0 >= 48 && LA29_0 <= 57 || LA29_0 >= 65 && LA29_0 <= 70 || LA29_0 >= 97 && LA29_0 <= 102 ? 1 : 2;
                                switch (alt29) {
                                    case 1: {
                                        this.mHEX_DIGIT();
                                        if (this.state.failed) {
                                            return;
                                        }
                                        if (this.state.backtracking != 0) break;
                                        ++hCount;
                                        break;
                                    }
                                }
                                break block1;
                            }
                        }
                        break block1;
                    }
                }
                break;
            }
        }
        if (this.state.backtracking == 0 && hCount < 4) {
            Interval badRange = Interval.of(this.getCharIndex() - 2 - hCount, this.getCharIndex());
            String lastChar = this.input.substring(badRange.b, badRange.b);
            if (lastChar.codePointAt(0) == 39) {
                badRange = new Interval(badRange.a, badRange.b - 1);
            }
            String bad = this.input.substring(badRange.a, badRange.b);
            CommonToken t = new CommonToken(this.input, this.state.type, this.state.channel, badRange.a, badRange.b);
            t.setLine(this.input.getLine());
            t.setCharPositionInLine(this.input.getCharPositionInLine() - hCount - 2);
            this.grammarError(ErrorType.INVALID_ESCAPE_SEQUENCE, t, bad);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mUNICODE_EXTENDED_ESC() throws RecognitionException {
        int numDigits;
        this.match("u{");
        if (this.state.failed) {
            return;
        }
        int cnt33 = 0;
        block5: while (true) {
            int alt33 = 2;
            int LA33_0 = this.input.LA(1);
            if (LA33_0 >= 48 && LA33_0 <= 57 || LA33_0 >= 65 && LA33_0 <= 70 || LA33_0 >= 97 && LA33_0 <= 102) {
                alt33 = 1;
            }
            switch (alt33) {
                case 1: {
                    if (this.input.LA(1) >= 48 && this.input.LA(1) <= 57 || this.input.LA(1) >= 65 && this.input.LA(1) <= 70 || this.input.LA(1) >= 97 && this.input.LA(1) <= 102) {
                        this.input.consume();
                        this.state.failed = false;
                        break;
                    }
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    this.recover(mse);
                    throw mse;
                }
                default: {
                    if (cnt33 >= 1) break block5;
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    EarlyExitException eee = new EarlyExitException(33, this.input);
                    throw eee;
                }
            }
            ++cnt33;
        }
        this.match(125);
        if (this.state.failed) {
            return;
        }
        if (this.state.backtracking == 0 && (numDigits = this.getCharIndex() - this.state.tokenStartCharIndex - 6) > 6) {
            CommonToken t = new CommonToken(this.input, this.state.type, this.state.channel, this.state.tokenStartCharIndex, this.getCharIndex() - 1);
            t.setText(t.getText());
            t.setLine(this.input.getLine());
            t.setCharPositionInLine(this.input.getCharPositionInLine() - numDigits);
            this.grammarError(ErrorType.INVALID_ESCAPE_SEQUENCE, t, this.input.substring(this.state.tokenStartCharIndex, this.getCharIndex() - 1));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mWS() throws RecognitionException {
        int _type = 71;
        int _channel = 0;
        int cnt34 = 0;
        block5: while (true) {
            int alt34 = 2;
            int LA34_0 = this.input.LA(1);
            if (LA34_0 >= 9 && LA34_0 <= 10 || LA34_0 >= 12 && LA34_0 <= 13 || LA34_0 == 32) {
                alt34 = 1;
            }
            switch (alt34) {
                case 1: {
                    if (this.input.LA(1) >= 9 && this.input.LA(1) <= 10 || this.input.LA(1) >= 12 && this.input.LA(1) <= 13 || this.input.LA(1) == 32) {
                        this.input.consume();
                        this.state.failed = false;
                        break;
                    }
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    this.recover(mse);
                    throw mse;
                }
                default: {
                    if (cnt34 >= 1) break block5;
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    EarlyExitException eee = new EarlyExitException(34, this.input);
                    throw eee;
                }
            }
            ++cnt34;
        }
        if (this.state.backtracking == 0) {
            _channel = 99;
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mNLCHARS() throws RecognitionException {
        if (this.input.LA(1) != 10 && this.input.LA(1) != 13) {
            if (this.state.backtracking > 0) {
                this.state.failed = true;
                return;
            }
            MismatchedSetException mse = new MismatchedSetException(null, this.input);
            this.recover(mse);
            throw mse;
        }
        this.input.consume();
        this.state.failed = false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mWSCHARS() throws RecognitionException {
        if (this.input.LA(1) != 9 && this.input.LA(1) != 12 && this.input.LA(1) != 32) {
            if (this.state.backtracking > 0) {
                this.state.failed = true;
                return;
            }
            MismatchedSetException mse = new MismatchedSetException(null, this.input);
            this.recover(mse);
            throw mse;
        }
        this.input.consume();
        this.state.failed = false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mWSNLCHARS() throws RecognitionException {
        if (!(this.input.LA(1) >= 9 && this.input.LA(1) <= 10 || this.input.LA(1) >= 12 && this.input.LA(1) <= 13 || this.input.LA(1) == 32)) {
            if (this.state.backtracking > 0) {
                this.state.failed = true;
                return;
            }
            MismatchedSetException mse = new MismatchedSetException(null, this.input);
            this.recover(mse);
            throw mse;
        }
        this.input.consume();
        this.state.failed = false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mUnicodeBOM() throws RecognitionException {
        int _type = 70;
        int _channel = 0;
        this.match(65279);
        if (this.state.failed) {
            return;
        }
        if (this.state.backtracking == 0) {
            this.skip();
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mERRCHAR() throws RecognitionException {
        int _type = 21;
        int _channel = 0;
        this.matchAny();
        if (this.state.failed) {
            return;
        }
        if (this.state.backtracking == 0) {
            CommonToken t = new CommonToken(this.input, this.state.type, this.state.channel, this.state.tokenStartCharIndex, this.getCharIndex() - 1);
            t.setLine(this.state.tokenStartLine);
            t.setText(this.state.text);
            t.setCharPositionInLine(this.state.tokenStartCharPositionInLine);
            String msg = this.getTokenErrorDisplay(t) + " came as a complete surprise to me";
            this.grammarError(ErrorType.SYNTAX_ERROR, t, msg);
            ++this.state.syntaxErrors;
            this.skip();
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    @Override
    public void mTokens() throws RecognitionException {
        int alt35 = 50;
        alt35 = this.dfa35.predict(this.input);
        switch (alt35) {
            case 1: {
                this.mCOMMENT();
                if (!this.state.failed) break;
                return;
            }
            case 2: {
                this.mARG_OR_CHARSET();
                if (!this.state.failed) break;
                return;
            }
            case 3: {
                this.mACTION();
                if (!this.state.failed) break;
                return;
            }
            case 4: {
                this.mOPTIONS();
                if (!this.state.failed) break;
                return;
            }
            case 5: {
                this.mTOKENS_SPEC();
                if (!this.state.failed) break;
                return;
            }
            case 6: {
                this.mCHANNELS();
                if (!this.state.failed) break;
                return;
            }
            case 7: {
                this.mIMPORT();
                if (!this.state.failed) break;
                return;
            }
            case 8: {
                this.mFRAGMENT();
                if (!this.state.failed) break;
                return;
            }
            case 9: {
                this.mLEXER();
                if (!this.state.failed) break;
                return;
            }
            case 10: {
                this.mPARSER();
                if (!this.state.failed) break;
                return;
            }
            case 11: {
                this.mGRAMMAR();
                if (!this.state.failed) break;
                return;
            }
            case 12: {
                this.mTREE_GRAMMAR();
                if (!this.state.failed) break;
                return;
            }
            case 13: {
                this.mPROTECTED();
                if (!this.state.failed) break;
                return;
            }
            case 14: {
                this.mPUBLIC();
                if (!this.state.failed) break;
                return;
            }
            case 15: {
                this.mPRIVATE();
                if (!this.state.failed) break;
                return;
            }
            case 16: {
                this.mRETURNS();
                if (!this.state.failed) break;
                return;
            }
            case 17: {
                this.mLOCALS();
                if (!this.state.failed) break;
                return;
            }
            case 18: {
                this.mTHROWS();
                if (!this.state.failed) break;
                return;
            }
            case 19: {
                this.mCATCH();
                if (!this.state.failed) break;
                return;
            }
            case 20: {
                this.mFINALLY();
                if (!this.state.failed) break;
                return;
            }
            case 21: {
                this.mMODE();
                if (!this.state.failed) break;
                return;
            }
            case 22: {
                this.mCOLON();
                if (!this.state.failed) break;
                return;
            }
            case 23: {
                this.mCOLONCOLON();
                if (!this.state.failed) break;
                return;
            }
            case 24: {
                this.mCOMMA();
                if (!this.state.failed) break;
                return;
            }
            case 25: {
                this.mSEMI();
                if (!this.state.failed) break;
                return;
            }
            case 26: {
                this.mLPAREN();
                if (!this.state.failed) break;
                return;
            }
            case 27: {
                this.mRPAREN();
                if (!this.state.failed) break;
                return;
            }
            case 28: {
                this.mRARROW();
                if (!this.state.failed) break;
                return;
            }
            case 29: {
                this.mLT();
                if (!this.state.failed) break;
                return;
            }
            case 30: {
                this.mGT();
                if (!this.state.failed) break;
                return;
            }
            case 31: {
                this.mASSIGN();
                if (!this.state.failed) break;
                return;
            }
            case 32: {
                this.mQUESTION();
                if (!this.state.failed) break;
                return;
            }
            case 33: {
                this.mSYNPRED();
                if (!this.state.failed) break;
                return;
            }
            case 34: {
                this.mSTAR();
                if (!this.state.failed) break;
                return;
            }
            case 35: {
                this.mPLUS();
                if (!this.state.failed) break;
                return;
            }
            case 36: {
                this.mPLUS_ASSIGN();
                if (!this.state.failed) break;
                return;
            }
            case 37: {
                this.mOR();
                if (!this.state.failed) break;
                return;
            }
            case 38: {
                this.mDOLLAR();
                if (!this.state.failed) break;
                return;
            }
            case 39: {
                this.mDOT();
                if (!this.state.failed) break;
                return;
            }
            case 40: {
                this.mRANGE();
                if (!this.state.failed) break;
                return;
            }
            case 41: {
                this.mAT();
                if (!this.state.failed) break;
                return;
            }
            case 42: {
                this.mPOUND();
                if (!this.state.failed) break;
                return;
            }
            case 43: {
                this.mNOT();
                if (!this.state.failed) break;
                return;
            }
            case 44: {
                this.mRBRACE();
                if (!this.state.failed) break;
                return;
            }
            case 45: {
                this.mID();
                if (!this.state.failed) break;
                return;
            }
            case 46: {
                this.mINT();
                if (!this.state.failed) break;
                return;
            }
            case 47: {
                this.mSTRING_LITERAL();
                if (!this.state.failed) break;
                return;
            }
            case 48: {
                this.mWS();
                if (!this.state.failed) break;
                return;
            }
            case 49: {
                this.mUnicodeBOM();
                if (!this.state.failed) break;
                return;
            }
            case 50: {
                this.mERRCHAR();
                if (!this.state.failed) break;
                return;
            }
        }
    }

    public final void synpred1_ANTLRLexer_fragment() throws RecognitionException {
        this.match(" $ANTLR");
        if (this.state.failed) {
            return;
        }
    }

    public final void synpred2_ANTLRLexer_fragment() throws RecognitionException {
        this.match(34);
        if (this.state.failed) {
            return;
        }
    }

    public final void synpred3_ANTLRLexer_fragment() throws RecognitionException {
        this.match(39);
        if (this.state.failed) {
            return;
        }
    }

    public final void synpred4_ANTLRLexer_fragment() throws RecognitionException {
        block3: while (true) {
            int alt36 = 2;
            int LA36_0 = this.input.LA(1);
            if (LA36_0 >= 9 && LA36_0 <= 10 || LA36_0 >= 12 && LA36_0 <= 13 || LA36_0 == 32) {
                alt36 = 1;
            }
            switch (alt36) {
                case 1: {
                    if (this.input.LA(1) >= 9 && this.input.LA(1) <= 10 || this.input.LA(1) >= 12 && this.input.LA(1) <= 13 || this.input.LA(1) == 32) {
                        this.input.consume();
                        this.state.failed = false;
                        continue block3;
                    }
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    this.recover(mse);
                    throw mse;
                }
            }
            break;
        }
        this.match("=>");
        if (this.state.failed) {
            return;
        }
    }

    public final void synpred5_ANTLRLexer_fragment() throws RecognitionException {
        this.match(92);
        if (this.state.failed) {
            return;
        }
    }

    public final void synpred6_ANTLRLexer_fragment() throws RecognitionException {
        this.match(92);
        if (this.state.failed) {
            return;
        }
    }

    public final boolean synpred5_ANTLRLexer() {
        ++this.state.backtracking;
        int start = this.input.mark();
        try {
            this.synpred5_ANTLRLexer_fragment();
        }
        catch (RecognitionException re) {
            System.err.println("impossible: " + re);
        }
        boolean success = !this.state.failed;
        this.input.rewind(start);
        --this.state.backtracking;
        this.state.failed = false;
        return success;
    }

    public final boolean synpred4_ANTLRLexer() {
        ++this.state.backtracking;
        int start = this.input.mark();
        try {
            this.synpred4_ANTLRLexer_fragment();
        }
        catch (RecognitionException re) {
            System.err.println("impossible: " + re);
        }
        boolean success = !this.state.failed;
        this.input.rewind(start);
        --this.state.backtracking;
        this.state.failed = false;
        return success;
    }

    public final boolean synpred3_ANTLRLexer() {
        ++this.state.backtracking;
        int start = this.input.mark();
        try {
            this.synpred3_ANTLRLexer_fragment();
        }
        catch (RecognitionException re) {
            System.err.println("impossible: " + re);
        }
        boolean success = !this.state.failed;
        this.input.rewind(start);
        --this.state.backtracking;
        this.state.failed = false;
        return success;
    }

    public final boolean synpred1_ANTLRLexer() {
        ++this.state.backtracking;
        int start = this.input.mark();
        try {
            this.synpred1_ANTLRLexer_fragment();
        }
        catch (RecognitionException re) {
            System.err.println("impossible: " + re);
        }
        boolean success = !this.state.failed;
        this.input.rewind(start);
        --this.state.backtracking;
        this.state.failed = false;
        return success;
    }

    public final boolean synpred2_ANTLRLexer() {
        ++this.state.backtracking;
        int start = this.input.mark();
        try {
            this.synpred2_ANTLRLexer_fragment();
        }
        catch (RecognitionException re) {
            System.err.println("impossible: " + re);
        }
        boolean success = !this.state.failed;
        this.input.rewind(start);
        --this.state.backtracking;
        this.state.failed = false;
        return success;
    }

    public final boolean synpred6_ANTLRLexer() {
        ++this.state.backtracking;
        int start = this.input.mark();
        try {
            this.synpred6_ANTLRLexer_fragment();
        }
        catch (RecognitionException re) {
            System.err.println("impossible: " + re);
        }
        boolean success = !this.state.failed;
        this.input.rewind(start);
        --this.state.backtracking;
        this.state.failed = false;
        return success;
    }

    static {
        int i;
        DFA2_transitionS = new String[]{"\u0001\u0001", "\u0001\u0003", "", "\u0001\u0004", "\u0001\u0005", "\u0001\u0006", "\u0001\u0007", "\u0001\b", "\u0001\t", "\u0001\n", "\u0001\u000b", "\u0001\f\u0002\uffff\u0001\f\u0013\uffff\u0001\f", "\u0001\f\u0002\uffff\u0001\f\u0013\uffff\u0001\f\u0001\uffff\u0001\r", "\n\u000f\u0001\u0011\u0002\u000f\u0001\u0011\u0014\u000f\u0001\u00109\u000f\u0001\u000e\uffa3\u000f", "\n\u0014\u0001\u0015\u0002\u0014\u0001\u0015\u0014\u0014\u0001\u00129\u0014\u0001\u0013\uffa3\u0014", "\n\u000f\u0001\u0011\u0002\u000f\u0001\u0011\u0014\u000f\u0001\u00109\u000f\u0001\u000e\uffa3\u000f", "\u0001\u0016\u0002\uffff\u0001\u0016\u0013\uffff\u0001\u0016", "", "\t\u000f\u0001\u0017\u0001\u0011\u0001\u000f\u0001\u0017\u0001\u0011\u0012\u000f\u0001\u0017\u0001\u000f\u0001\u00109\u000f\u0001\u000e\uffa3\u000f", "\n\u0014\u0001\u0015\u0002\u0014\u0001\u0015\u0014\u0014\u0001\u00129\u0014\u0001\u0013\uffa3\u0014", "\n\u000f\u0001\u0011\u0002\u000f\u0001\u0011\u0014\u000f\u0001\u00109\u000f\u0001\u000e\uffa3\u000f", "", "\u0001\u0016\u0002\uffff\u0001\u0016\u0013\uffff\u0001\u0016\u000f\uffff\n\u0018", "\t\u000f\u0001\u0017\u0001\u0011\u0001\u000f\u0001\u0017\u0001\u0011\u0012\u000f\u0001\u0017\u0001\u000f\u0001\u0010\r\u000f\n\u0019\"\u000f\u0001\u000e\uffa3\u000f", "\u0001\uffff", "\u0001\uffff"};
        DFA2_eot = DFA.unpackEncodedString(DFA2_eotS);
        DFA2_eof = DFA.unpackEncodedString(DFA2_eofS);
        DFA2_min = DFA.unpackEncodedStringToUnsignedChars(DFA2_minS);
        DFA2_max = DFA.unpackEncodedStringToUnsignedChars(DFA2_maxS);
        DFA2_accept = DFA.unpackEncodedString(DFA2_acceptS);
        DFA2_special = DFA.unpackEncodedString(DFA2_specialS);
        int numStates = DFA2_transitionS.length;
        DFA2_transition = new short[numStates][];
        for (i = 0; i < numStates; ++i) {
            ANTLRLexer.DFA2_transition[i] = DFA.unpackEncodedString(DFA2_transitionS[i]);
        }
        DFA35_transitionS = new String[]{"\t&\u0002$\u0001&\u0002$\u0012&\u0001$\u0002&\u0001\u001e\u0001\u001b\u0002&\u0001#\u0001\u0011\u0001\u0012\u0001\u0018\u0001\u0019\u0001\u000f\u0001\u0013\u0001\u001c\u0001\u0001\n\"\u0001\u000e\u0001\u0010\u0001\u0014\u0001\u0016\u0001\u0015\u0001\u0017\u0001\u001d\u001a!\u0001\u0002\u0005&\u0002!\u0001\u0006\u0002!\u0001\b\u0001\u000b\u0001!\u0001\u0007\u0002!\u0001\t\u0001\r\u0001!\u0001\u0004\u0001\n\u0001!\u0001\f\u0001!\u0001\u0005\u0006!\u0001\u0003\u0001\u001a\u0001 \u0001\u001fA&\u0017!\u0001&\u001f!\u0001&\u0208!p&\u000e!\u0001&\u1c81!\f&\u0002!b&\u0120!\u0a70&\u03f0!\u0011&\ua7ff!\u2100&\u04d0! &\u010f!\u0001%\u00fe!\u0002&", "", "\n(\u0001)\u0002(\u0001)\ufff2(", "", "\u0001+", "\u0001/\u0006\uffff\u0001-\u0002\uffff\u0001.", "\u00011\u0006\uffff\u00010", "\u00012", "\u00014\b\uffff\u00013", "\u00015\t\uffff\u00016", "\u00017\u0010\uffff\u00018\u0002\uffff\u00019", "\u0001:", "\u0001;", "\u0001<", "\u0001=", "", "", "", "", "\u0001C", "", "", "\u0001F", "", "", "\u0001J", "", "", "\u0001N", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "\u0001X", "", "\u0001Y", "\u0001Z", "\u0001[", "\u0001\\", "\u0001]", "\u0001^", "\u0001_", "\u0001`", "\u0001a", "\u0001b", "\u0001c", "\u0001e\u0005\uffff\u0001d", "\u0001f", "\u0001g", "\u0001h", "\u0001i", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "\u0001j", "\u0001k", "\u0001l", "\u0001m", "\u0001n", "\u0001o", "\u0001p", "\u0001q", "\u0001r", "\u0001s", "\u0001t", "\u0001u", "\u0001v", "\u0001w", "\u0001x", "\u0001y", "\u0001z", "\u0001{", "\u0001|", "\u0001}", "\u0002~\u0001\uffff\u0002~\u0012\uffff\u0001~F\uffff\u0001\u007f", "\u0001\u0080", "\u0001\u0081", "\u0001\u0082", "\u0001\u0083", "\u0001\u0084", "\u0001\u0085", "\u0001\u0086", "\u0001\u0087", "\u0001\u0088", "\u0001\u0089", "\u0001\u008a", "\u0001\u008b", "\u0001\u008c", "\u0001\u008d", "\n,\u0007\uffff\u001a,\u0004\uffff\u0001,\u0001\uffff\u001a,<\uffff\u0001,\b\uffff\u0017,\u0001\uffff\u001f,\u0001\uffff\u0286,\u0001\uffff\u1c81,\f\uffff\u0002,1\uffff\u0002,/\uffff\u0120,\u0a70\uffff\u03f0,\u0011\uffff\ua7ff,\u2100\uffff\u04d0, \uffff\u010f,\u0001\uffff\u00fe,", "\u0001\u008f", "\u0001\u0090", "", "\u0001\u0091", "\u0001\u0092", "\u0001\u0093", "\n,\u0007\uffff\u001a,\u0004\uffff\u0001,\u0001\uffff\u001a,<\uffff\u0001,\b\uffff\u0017,\u0001\uffff\u001f,\u0001\uffff\u0286,\u0001\uffff\u1c81,\f\uffff\u0002,1\uffff\u0002,/\uffff\u0120,\u0a70\uffff\u03f0,\u0011\uffff\ua7ff,\u2100\uffff\u04d0, \uffff\u010f,\u0001\uffff\u00fe,", "\u0001\u0095", "\u0001\u0096", "\u0001\u0097", "\n,\u0007\uffff\u001a,\u0004\uffff\u0001,\u0001\uffff\u001a,<\uffff\u0001,\b\uffff\u0017,\u0001\uffff\u001f,\u0001\uffff\u0286,\u0001\uffff\u1c81,\f\uffff\u0002,1\uffff\u0002,/\uffff\u0120,\u0a70\uffff\u03f0,\u0011\uffff\ua7ff,\u2100\uffff\u04d0, \uffff\u010f,\u0001\uffff\u00fe,", "\u0001\u0099", "\u0001\u009a", "\u0001\u009b", "\u0001\u009c", "\u0001\u009d", "\u0001\u009e", "\u0001\u009f", "", "\u0001\u00a0", "\u0002\u00a1\u0001\uffff\u0002\u00a1\u0012\uffff\u0001\u00a1Z\uffff\u0001\u00a1", "\u0001\u00a2", "\n,\u0007\uffff\u001a,\u0004\uffff\u0001,\u0001\uffff\u001a,<\uffff\u0001,\b\uffff\u0017,\u0001\uffff\u001f,\u0001\uffff\u0286,\u0001\uffff\u1c81,\f\uffff\u0002,1\uffff\u0002,/\uffff\u0120,\u0a70\uffff\u03f0,\u0011\uffff\ua7ff,\u2100\uffff\u04d0, \uffff\u010f,\u0001\uffff\u00fe,", "\u0001\u00a4", "", "\n,\u0007\uffff\u001a,\u0004\uffff\u0001,\u0001\uffff\u001a,<\uffff\u0001,\b\uffff\u0017,\u0001\uffff\u001f,\u0001\uffff\u0286,\u0001\uffff\u1c81,\f\uffff\u0002,1\uffff\u0002,/\uffff\u0120,\u0a70\uffff\u03f0,\u0011\uffff\ua7ff,\u2100\uffff\u04d0, \uffff\u010f,\u0001\uffff\u00fe,", "\u0001\u00a6", "\u0001\u00a7", "", "\n,\u0007\uffff\u001a,\u0004\uffff\u0001,\u0001\uffff\u001a,<\uffff\u0001,\b\uffff\u0017,\u0001\uffff\u001f,\u0001\uffff\u0286,\u0001\uffff\u1c81,\f\uffff\u0002,1\uffff\u0002,/\uffff\u0120,\u0a70\uffff\u03f0,\u0011\uffff\ua7ff,\u2100\uffff\u04d0, \uffff\u010f,\u0001\uffff\u00fe,", "\n,\u0007\uffff\u001a,\u0004\uffff\u0001,\u0001\uffff\u001a,<\uffff\u0001,\b\uffff\u0017,\u0001\uffff\u001f,\u0001\uffff\u0286,\u0001\uffff\u1c81,\f\uffff\u0002,1\uffff\u0002,/\uffff\u0120,\u0a70\uffff\u03f0,\u0011\uffff\ua7ff,\u2100\uffff\u04d0, \uffff\u010f,\u0001\uffff\u00fe,", "\u0001\u00aa", "\u0001\u00ab", "\n,\u0007\uffff\u001a,\u0004\uffff\u0001,\u0001\uffff\u001a,<\uffff\u0001,\b\uffff\u0017,\u0001\uffff\u001f,\u0001\uffff\u0286,\u0001\uffff\u1c81,\f\uffff\u0002,1\uffff\u0002,/\uffff\u0120,\u0a70\uffff\u03f0,\u0011\uffff\ua7ff,\u2100\uffff\u04d0, \uffff\u010f,\u0001\uffff\u00fe,", "\u0001\u00ad", "\u0001\u00ae", "\u0002\u00af\u0001\uffff\u0002\u00af\u0012\uffff\u0001\u00afZ\uffff\u0001\u00af", "", "\u0001\u00b0", "", "\u0001\u00b1", "", "\u0001\u00b2", "\n,\u0007\uffff\u001a,\u0004\uffff\u0001,\u0001\uffff\u001a,<\uffff\u0001,\b\uffff\u0017,\u0001\uffff\u001f,\u0001\uffff\u0286,\u0001\uffff\u1c81,\f\uffff\u0002,1\uffff\u0002,/\uffff\u0120,\u0a70\uffff\u03f0,\u0011\uffff\ua7ff,\u2100\uffff\u04d0, \uffff\u010f,\u0001\uffff\u00fe,", "", "", "\u0001\u00b4", "\n,\u0007\uffff\u001a,\u0004\uffff\u0001,\u0001\uffff\u001a,<\uffff\u0001,\b\uffff\u0017,\u0001\uffff\u001f,\u0001\uffff\u0286,\u0001\uffff\u1c81,\f\uffff\u0002,1\uffff\u0002,/\uffff\u0120,\u0a70\uffff\u03f0,\u0011\uffff\ua7ff,\u2100\uffff\u04d0, \uffff\u010f,\u0001\uffff\u00fe,", "", "\n,\u0007\uffff\u001a,\u0004\uffff\u0001,\u0001\uffff\u001a,<\uffff\u0001,\b\uffff\u0017,\u0001\uffff\u001f,\u0001\uffff\u0286,\u0001\uffff\u1c81,\f\uffff\u0002,1\uffff\u0002,/\uffff\u0120,\u0a70\uffff\u03f0,\u0011\uffff\ua7ff,\u2100\uffff\u04d0, \uffff\u010f,\u0001\uffff\u00fe,", "\n,\u0007\uffff\u001a,\u0004\uffff\u0001,\u0001\uffff\u001a,<\uffff\u0001,\b\uffff\u0017,\u0001\uffff\u001f,\u0001\uffff\u0286,\u0001\uffff\u1c81,\f\uffff\u0002,1\uffff\u0002,/\uffff\u0120,\u0a70\uffff\u03f0,\u0011\uffff\ua7ff,\u2100\uffff\u04d0, \uffff\u010f,\u0001\uffff\u00fe,", "", "\u0001\u00b8", "\u0002\u00b9\u0001\uffff\u0002\u00b9\u0012\uffff\u0001\u00b9Z\uffff\u0001\u00b9", "\n,\u0007\uffff\u001a,\u0004\uffff\u0001,\u0001\uffff\u001a,<\uffff\u0001,\b\uffff\u0017,\u0001\uffff\u001f,\u0001\uffff\u0286,\u0001\uffff\u1c81,\f\uffff\u0002,1\uffff\u0002,/\uffff\u0120,\u0a70\uffff\u03f0,\u0011\uffff\ua7ff,\u2100\uffff\u04d0, \uffff\u010f,\u0001\uffff\u00fe,", "", "\u0001\u00bb", "", "", "", "\u0001\u00bc", "", "", "\n,\u0007\uffff\u001a,\u0004\uffff\u0001,\u0001\uffff\u001a,<\uffff\u0001,\b\uffff\u0017,\u0001\uffff\u001f,\u0001\uffff\u0286,\u0001\uffff\u1c81,\f\uffff\u0002,1\uffff\u0002,/\uffff\u0120,\u0a70\uffff\u03f0,\u0011\uffff\ua7ff,\u2100\uffff\u04d0, \uffff\u010f,\u0001\uffff\u00fe,", "\u0001\u00be", "", "\n,\u0007\uffff\u001a,\u0004\uffff\u0001,\u0001\uffff\u001a,<\uffff\u0001,\b\uffff\u0017,\u0001\uffff\u001f,\u0001\uffff\u0286,\u0001\uffff\u1c81,\f\uffff\u0002,1\uffff\u0002,/\uffff\u0120,\u0a70\uffff\u03f0,\u0011\uffff\ua7ff,\u2100\uffff\u04d0, \uffff\u010f,\u0001\uffff\u00fe,"};
        DFA35_eot = DFA.unpackEncodedString(DFA35_eotS);
        DFA35_eof = DFA.unpackEncodedString(DFA35_eofS);
        DFA35_min = DFA.unpackEncodedStringToUnsignedChars(DFA35_minS);
        DFA35_max = DFA.unpackEncodedStringToUnsignedChars(DFA35_maxS);
        DFA35_accept = DFA.unpackEncodedString(DFA35_acceptS);
        DFA35_special = DFA.unpackEncodedString(DFA35_specialS);
        numStates = DFA35_transitionS.length;
        DFA35_transition = new short[numStates][];
        for (i = 0; i < numStates; ++i) {
            ANTLRLexer.DFA35_transition[i] = DFA.unpackEncodedString(DFA35_transitionS[i]);
        }
    }

    protected class DFA35
    extends DFA {
        public DFA35(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 35;
            this.eot = DFA35_eot;
            this.eof = DFA35_eof;
            this.min = DFA35_min;
            this.max = DFA35_max;
            this.accept = DFA35_accept;
            this.special = DFA35_special;
            this.transition = DFA35_transition;
        }

        @Override
        public String getDescription() {
            return "1:1: Tokens : ( COMMENT | ARG_OR_CHARSET | ACTION | OPTIONS | TOKENS_SPEC | CHANNELS | IMPORT | FRAGMENT | LEXER | PARSER | GRAMMAR | TREE_GRAMMAR | PROTECTED | PUBLIC | PRIVATE | RETURNS | LOCALS | THROWS | CATCH | FINALLY | MODE | COLON | COLONCOLON | COMMA | SEMI | LPAREN | RPAREN | RARROW | LT | GT | ASSIGN | QUESTION | SYNPRED | STAR | PLUS | PLUS_ASSIGN | OR | DOLLAR | DOT | RANGE | AT | POUND | NOT | RBRACE | ID | INT | STRING_LITERAL | WS | UnicodeBOM | ERRCHAR );";
        }

        @Override
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
            int _s = s;
            switch (s) {
                case 0: {
                    int LA35_2 = input.LA(1);
                    int index35_2 = input.index();
                    input.rewind();
                    s = -1;
                    s = (LA35_2 >= 0 && LA35_2 <= 9 || LA35_2 >= 11 && LA35_2 <= 12 || LA35_2 >= 14 && LA35_2 <= 65535) && (!ANTLRLexer.this.isLexerRule || ANTLRLexer.this.isLexerRule) ? 40 : ((LA35_2 == 10 || LA35_2 == 13) && !ANTLRLexer.this.isLexerRule ? 41 : 38);
                    input.seek(index35_2);
                    if (s < 0) break;
                    return s;
                }
                case 1: {
                    int LA35_0 = input.LA(1);
                    s = -1;
                    if (LA35_0 == 47) {
                        s = 1;
                    } else if (LA35_0 == 91) {
                        s = 2;
                    } else if (LA35_0 == 123) {
                        s = 3;
                    } else if (LA35_0 == 111) {
                        s = 4;
                    } else if (LA35_0 == 116) {
                        s = 5;
                    } else if (LA35_0 == 99) {
                        s = 6;
                    } else if (LA35_0 == 105) {
                        s = 7;
                    } else if (LA35_0 == 102) {
                        s = 8;
                    } else if (LA35_0 == 108) {
                        s = 9;
                    } else if (LA35_0 == 112) {
                        s = 10;
                    } else if (LA35_0 == 103) {
                        s = 11;
                    } else if (LA35_0 == 114) {
                        s = 12;
                    } else if (LA35_0 == 109) {
                        s = 13;
                    } else if (LA35_0 == 58) {
                        s = 14;
                    } else if (LA35_0 == 44) {
                        s = 15;
                    } else if (LA35_0 == 59) {
                        s = 16;
                    } else if (LA35_0 == 40) {
                        s = 17;
                    } else if (LA35_0 == 41) {
                        s = 18;
                    } else if (LA35_0 == 45) {
                        s = 19;
                    } else if (LA35_0 == 60) {
                        s = 20;
                    } else if (LA35_0 == 62) {
                        s = 21;
                    } else if (LA35_0 == 61) {
                        s = 22;
                    } else if (LA35_0 == 63) {
                        s = 23;
                    } else if (LA35_0 == 42) {
                        s = 24;
                    } else if (LA35_0 == 43) {
                        s = 25;
                    } else if (LA35_0 == 124) {
                        s = 26;
                    } else if (LA35_0 == 36) {
                        s = 27;
                    } else if (LA35_0 == 46) {
                        s = 28;
                    } else if (LA35_0 == 64) {
                        s = 29;
                    } else if (LA35_0 == 35) {
                        s = 30;
                    } else if (LA35_0 == 126) {
                        s = 31;
                    } else if (LA35_0 == 125) {
                        s = 32;
                    } else if (LA35_0 >= 65 && LA35_0 <= 90 || LA35_0 >= 97 && LA35_0 <= 98 || LA35_0 >= 100 && LA35_0 <= 101 || LA35_0 == 104 || LA35_0 >= 106 && LA35_0 <= 107 || LA35_0 == 110 || LA35_0 == 113 || LA35_0 == 115 || LA35_0 >= 117 && LA35_0 <= 122 || LA35_0 >= 192 && LA35_0 <= 214 || LA35_0 >= 216 && LA35_0 <= 246 || LA35_0 >= 248 && LA35_0 <= 767 || LA35_0 >= 880 && LA35_0 <= 893 || LA35_0 >= 895 && LA35_0 <= 8191 || LA35_0 >= 8204 && LA35_0 <= 8205 || LA35_0 >= 8304 && LA35_0 <= 8591 || LA35_0 >= 11264 && LA35_0 <= 12271 || LA35_0 >= 12289 && LA35_0 <= 55295 || LA35_0 >= 63744 && LA35_0 <= 64975 || LA35_0 >= 65008 && LA35_0 <= 65278 || LA35_0 >= 65280 && LA35_0 <= 65533) {
                        s = 33;
                    } else if (LA35_0 >= 48 && LA35_0 <= 57) {
                        s = 34;
                    } else if (LA35_0 == 39) {
                        s = 35;
                    } else if (LA35_0 >= 9 && LA35_0 <= 10 || LA35_0 >= 12 && LA35_0 <= 13 || LA35_0 == 32) {
                        s = 36;
                    } else if (LA35_0 == 65279) {
                        s = 37;
                    } else if (LA35_0 >= 0 && LA35_0 <= 8 || LA35_0 == 11 || LA35_0 >= 14 && LA35_0 <= 31 || LA35_0 >= 33 && LA35_0 <= 34 || LA35_0 >= 37 && LA35_0 <= 38 || LA35_0 >= 92 && LA35_0 <= 96 || LA35_0 >= 127 && LA35_0 <= 191 || LA35_0 == 215 || LA35_0 == 247 || LA35_0 >= 768 && LA35_0 <= 879 || LA35_0 == 894 || LA35_0 >= 8192 && LA35_0 <= 8203 || LA35_0 >= 8206 && LA35_0 <= 8303 || LA35_0 >= 8592 && LA35_0 <= 11263 || LA35_0 >= 12272 && LA35_0 <= 12288 || LA35_0 >= 55296 && LA35_0 <= 63743 || LA35_0 >= 64976 && LA35_0 <= 65007 || LA35_0 >= 65534 && LA35_0 <= 65535) {
                        s = 38;
                    }
                    if (s < 0) break;
                    return s;
                }
            }
            if (((ANTLRLexer)ANTLRLexer.this).state.backtracking > 0) {
                ((ANTLRLexer)ANTLRLexer.this).state.failed = true;
                return -1;
            }
            NoViableAltException nvae = new NoViableAltException(this.getDescription(), 35, _s, input);
            this.error(nvae);
            throw nvae;
        }
    }

    protected class DFA2
    extends DFA {
        public DFA2(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 2;
            this.eot = DFA2_eot;
            this.eof = DFA2_eof;
            this.min = DFA2_min;
            this.max = DFA2_max;
            this.accept = DFA2_accept;
            this.special = DFA2_special;
            this.transition = DFA2_transition;
        }

        @Override
        public String getDescription() {
            return "170:13: ( ( ' $ANTLR' )=> ' $ANTLR' SRC | (~ ( NLCHARS ) )* )";
        }

        @Override
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
            int _s = s;
            switch (s) {
                case 0: {
                    int LA2_24 = input.LA(1);
                    int index2_24 = input.index();
                    input.rewind();
                    s = -1;
                    s = ANTLRLexer.this.synpred1_ANTLRLexer() ? 21 : 2;
                    input.seek(index2_24);
                    if (s < 0) break;
                    return s;
                }
                case 1: {
                    int LA2_19 = input.LA(1);
                    int index2_19 = input.index();
                    input.rewind();
                    s = -1;
                    s = LA2_19 == 34 ? 18 : (LA2_19 == 92 ? 19 : (LA2_19 >= 0 && LA2_19 <= 9 || LA2_19 >= 11 && LA2_19 <= 12 || LA2_19 >= 14 && LA2_19 <= 33 || LA2_19 >= 35 && LA2_19 <= 91 || LA2_19 >= 93 && LA2_19 <= 65535 ? 20 : ((LA2_19 == 10 || LA2_19 == 13) && ANTLRLexer.this.synpred1_ANTLRLexer() ? 21 : 2)));
                    input.seek(index2_19);
                    if (s < 0) break;
                    return s;
                }
                case 2: {
                    int LA2_18 = input.LA(1);
                    int index2_18 = input.index();
                    input.rewind();
                    s = -1;
                    s = LA2_18 == 34 ? 16 : (LA2_18 == 92 ? 14 : (LA2_18 == 9 || LA2_18 == 12 || LA2_18 == 32 ? 23 : (LA2_18 >= 0 && LA2_18 <= 8 || LA2_18 == 11 || LA2_18 >= 14 && LA2_18 <= 31 || LA2_18 == 33 || LA2_18 >= 35 && LA2_18 <= 91 || LA2_18 >= 93 && LA2_18 <= 65535 ? 15 : ((LA2_18 == 10 || LA2_18 == 13) && ANTLRLexer.this.synpred1_ANTLRLexer() ? 17 : 2))));
                    input.seek(index2_18);
                    if (s < 0) break;
                    return s;
                }
                case 3: {
                    int LA2_14 = input.LA(1);
                    int index2_14 = input.index();
                    input.rewind();
                    s = -1;
                    s = LA2_14 == 34 ? 18 : (LA2_14 == 92 ? 19 : (LA2_14 >= 0 && LA2_14 <= 9 || LA2_14 >= 11 && LA2_14 <= 12 || LA2_14 >= 14 && LA2_14 <= 33 || LA2_14 >= 35 && LA2_14 <= 91 || LA2_14 >= 93 && LA2_14 <= 65535 ? 20 : ((LA2_14 == 10 || LA2_14 == 13) && ANTLRLexer.this.synpred1_ANTLRLexer() ? 21 : 2)));
                    input.seek(index2_14);
                    if (s < 0) break;
                    return s;
                }
                case 4: {
                    int LA2_20 = input.LA(1);
                    int index2_20 = input.index();
                    input.rewind();
                    s = -1;
                    s = LA2_20 == 34 ? 16 : (LA2_20 == 92 ? 14 : (LA2_20 >= 0 && LA2_20 <= 9 || LA2_20 >= 11 && LA2_20 <= 12 || LA2_20 >= 14 && LA2_20 <= 33 || LA2_20 >= 35 && LA2_20 <= 91 || LA2_20 >= 93 && LA2_20 <= 65535 ? 15 : ((LA2_20 == 10 || LA2_20 == 13) && ANTLRLexer.this.synpred1_ANTLRLexer() ? 17 : 2)));
                    input.seek(index2_20);
                    if (s < 0) break;
                    return s;
                }
                case 5: {
                    int LA2_15 = input.LA(1);
                    int index2_15 = input.index();
                    input.rewind();
                    s = -1;
                    s = LA2_15 == 34 ? 16 : (LA2_15 == 92 ? 14 : (LA2_15 >= 0 && LA2_15 <= 9 || LA2_15 >= 11 && LA2_15 <= 12 || LA2_15 >= 14 && LA2_15 <= 33 || LA2_15 >= 35 && LA2_15 <= 91 || LA2_15 >= 93 && LA2_15 <= 65535 ? 15 : ((LA2_15 == 10 || LA2_15 == 13) && ANTLRLexer.this.synpred1_ANTLRLexer() ? 17 : 2)));
                    input.seek(index2_15);
                    if (s < 0) break;
                    return s;
                }
                case 6: {
                    int LA2_23 = input.LA(1);
                    int index2_23 = input.index();
                    input.rewind();
                    s = -1;
                    s = LA2_23 == 34 ? 16 : (LA2_23 == 92 ? 14 : (LA2_23 >= 48 && LA2_23 <= 57 ? 25 : (LA2_23 == 9 || LA2_23 == 12 || LA2_23 == 32 ? 23 : (LA2_23 >= 0 && LA2_23 <= 8 || LA2_23 == 11 || LA2_23 >= 14 && LA2_23 <= 31 || LA2_23 == 33 || LA2_23 >= 35 && LA2_23 <= 47 || LA2_23 >= 58 && LA2_23 <= 91 || LA2_23 >= 93 && LA2_23 <= 65535 ? 15 : ((LA2_23 == 10 || LA2_23 == 13) && ANTLRLexer.this.synpred1_ANTLRLexer() ? 17 : 2)))));
                    input.seek(index2_23);
                    if (s < 0) break;
                    return s;
                }
                case 7: {
                    int LA2_25 = input.LA(1);
                    int index2_25 = input.index();
                    input.rewind();
                    s = -1;
                    s = ANTLRLexer.this.synpred1_ANTLRLexer() ? 21 : 2;
                    input.seek(index2_25);
                    if (s < 0) break;
                    return s;
                }
                case 8: {
                    int LA2_13 = input.LA(1);
                    int index2_13 = input.index();
                    input.rewind();
                    s = -1;
                    s = LA2_13 == 92 ? 14 : (LA2_13 >= 0 && LA2_13 <= 9 || LA2_13 >= 11 && LA2_13 <= 12 || LA2_13 >= 14 && LA2_13 <= 33 || LA2_13 >= 35 && LA2_13 <= 91 || LA2_13 >= 93 && LA2_13 <= 65535 ? 15 : (LA2_13 == 34 ? 16 : ((LA2_13 == 10 || LA2_13 == 13) && ANTLRLexer.this.synpred1_ANTLRLexer() ? 17 : 2)));
                    input.seek(index2_13);
                    if (s < 0) break;
                    return s;
                }
            }
            if (((ANTLRLexer)ANTLRLexer.this).state.backtracking > 0) {
                ((ANTLRLexer)ANTLRLexer.this).state.failed = true;
                return -1;
            }
            NoViableAltException nvae = new NoViableAltException(this.getDescription(), 2, _s, input);
            this.error(nvae);
            throw nvae;
        }
    }
}

