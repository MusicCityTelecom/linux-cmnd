/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.parse;

import groovyjarjarantlr4.runtime.CharStream;
import groovyjarjarantlr4.runtime.CommonToken;
import groovyjarjarantlr4.runtime.EarlyExitException;
import groovyjarjarantlr4.runtime.FailedPredicateException;
import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.Lexer;
import groovyjarjarantlr4.runtime.MismatchedSetException;
import groovyjarjarantlr4.runtime.NoViableAltException;
import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.RecognizerSharedState;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.parse.ActionSplitterListener;
import java.util.ArrayList;
import java.util.List;

public class ActionSplitter
extends Lexer {
    public static final int EOF = -1;
    public static final int ATTR = 4;
    public static final int ATTR_VALUE_EXPR = 5;
    public static final int COMMENT = 6;
    public static final int ID = 7;
    public static final int LINE_COMMENT = 8;
    public static final int NONLOCAL_ATTR = 9;
    public static final int QUALIFIED_ATTR = 10;
    public static final int SET_ATTR = 11;
    public static final int SET_NONLOCAL_ATTR = 12;
    public static final int TEXT = 13;
    public static final int WS = 14;
    ActionSplitterListener delegate;

    public ActionSplitter(CharStream input, ActionSplitterListener delegate) {
        this(input, new RecognizerSharedState());
        this.delegate = delegate;
    }

    public List<Token> getActionTokens() {
        ArrayList<Token> chunks = new ArrayList<Token>();
        Token t = this.nextToken();
        while (t.getType() != -1) {
            chunks.add(t);
            t = this.nextToken();
        }
        return chunks;
    }

    private boolean isIDStartChar(int c) {
        return c == 95 || Character.isLetter(c);
    }

    public Lexer[] getDelegates() {
        return new Lexer[0];
    }

    public ActionSplitter() {
    }

    public ActionSplitter(CharStream input) {
        this(input, new RecognizerSharedState());
    }

    public ActionSplitter(CharStream input, RecognizerSharedState state) {
        super(input, state);
    }

    @Override
    public String getGrammarFileName() {
        return "org\\antlr\\v4\\parse\\ActionSplitter.g";
    }

    @Override
    public Token nextToken() {
        while (true) {
            if (this.input.LA(1) == -1) {
                CommonToken eof = new CommonToken(this.input, -1, 0, this.input.index(), this.input.index());
                eof.setLine(this.getLine());
                eof.setCharPositionInLine(this.getCharPositionInLine());
                return eof;
            }
            this.state.token = null;
            this.state.channel = 0;
            this.state.tokenStartCharIndex = this.input.index();
            this.state.tokenStartCharPositionInLine = this.input.getCharPositionInLine();
            this.state.tokenStartLine = this.input.getLine();
            this.state.text = null;
            try {
                int m = this.input.mark();
                this.state.backtracking = 1;
                this.state.failed = false;
                this.mTokens();
                this.state.backtracking = 0;
                if (this.state.failed) {
                    this.input.rewind(m);
                    this.input.consume();
                    continue;
                }
                this.emit();
                return this.state.token;
            }
            catch (RecognitionException re) {
                this.reportError(re);
                this.recover(re);
                continue;
            }
            break;
        }
    }

    @Override
    public void memoize(IntStream input, int ruleIndex, int ruleStartIndex) {
        if (this.state.backtracking > 1) {
            super.memoize(input, ruleIndex, ruleStartIndex);
        }
    }

    @Override
    public boolean alreadyParsedRule(IntStream input, int ruleIndex) {
        if (this.state.backtracking > 1) {
            return super.alreadyParsedRule(input, ruleIndex);
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mCOMMENT() throws RecognitionException {
        int _type = 6;
        int _channel = 0;
        this.match("/*");
        if (this.state.failed) {
            return;
        }
        block5: while (true) {
            int alt1 = 2;
            int LA1_0 = this.input.LA(1);
            if (LA1_0 == 42) {
                int LA1_1 = this.input.LA(2);
                if (LA1_1 == 47) {
                    alt1 = 2;
                } else if (LA1_1 >= 0 && LA1_1 <= 46 || LA1_1 >= 48 && LA1_1 <= 65535) {
                    alt1 = 1;
                }
            } else if (LA1_0 >= 0 && LA1_0 <= 41 || LA1_0 >= 43 && LA1_0 <= 65535) {
                alt1 = 1;
            }
            switch (alt1) {
                case 1: {
                    this.matchAny();
                    if (!this.state.failed) continue block5;
                    return;
                }
            }
            break;
        }
        this.match("*/");
        if (this.state.failed) {
            return;
        }
        if (this.state.backtracking == 1) {
            this.delegate.text(this.getText());
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mLINE_COMMENT() throws RecognitionException {
        int _type = 8;
        int _channel = 0;
        this.match("//");
        if (this.state.failed) {
            return;
        }
        block8: while (true) {
            int alt2 = 2;
            int LA2_0 = this.input.LA(1);
            if (LA2_0 >= 0 && LA2_0 <= 9 || LA2_0 >= 11 && LA2_0 <= 12 || LA2_0 >= 14 && LA2_0 <= 65535) {
                alt2 = 1;
            }
            switch (alt2) {
                case 1: {
                    if (this.input.LA(1) >= 0 && this.input.LA(1) <= 9 || this.input.LA(1) >= 11 && this.input.LA(1) <= 12 || this.input.LA(1) >= 14 && this.input.LA(1) <= 65535) {
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
        int alt3 = 2;
        int LA3_0 = this.input.LA(1);
        if (LA3_0 == 13) {
            alt3 = 1;
        }
        switch (alt3) {
            case 1: {
                this.match(13);
                if (!this.state.failed) break;
                return;
            }
        }
        this.match(10);
        if (this.state.failed) {
            return;
        }
        if (this.state.backtracking == 1) {
            this.delegate.text(this.getText());
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mSET_NONLOCAL_ATTR() throws RecognitionException {
        int _type = 12;
        int _channel = 0;
        CommonToken x = null;
        CommonToken y = null;
        CommonToken expr = null;
        this.match(36);
        if (this.state.failed) {
            return;
        }
        int xStart115 = this.getCharIndex();
        int xStartLine115 = this.getLine();
        int xStartCharPos115 = this.getCharPositionInLine();
        this.mID();
        if (this.state.failed) {
            return;
        }
        x = new CommonToken(this.input, 0, 0, xStart115, this.getCharIndex() - 1);
        x.setLine(xStartLine115);
        x.setCharPositionInLine(xStartCharPos115);
        this.match("::");
        if (this.state.failed) {
            return;
        }
        int yStart121 = this.getCharIndex();
        int yStartLine121 = this.getLine();
        int yStartCharPos121 = this.getCharPositionInLine();
        this.mID();
        if (this.state.failed) {
            return;
        }
        y = new CommonToken(this.input, 0, 0, yStart121, this.getCharIndex() - 1);
        y.setLine(yStartLine121);
        y.setCharPositionInLine(yStartCharPos121);
        int alt4 = 2;
        int LA4_0 = this.input.LA(1);
        if (LA4_0 >= 9 && LA4_0 <= 10 || LA4_0 == 13 || LA4_0 == 32) {
            alt4 = 1;
        }
        switch (alt4) {
            case 1: {
                this.mWS();
                if (!this.state.failed) break;
                return;
            }
        }
        this.match(61);
        if (this.state.failed) {
            return;
        }
        int exprStart130 = this.getCharIndex();
        int exprStartLine130 = this.getLine();
        int exprStartCharPos130 = this.getCharPositionInLine();
        this.mATTR_VALUE_EXPR();
        if (this.state.failed) {
            return;
        }
        expr = new CommonToken(this.input, 0, 0, exprStart130, this.getCharIndex() - 1);
        expr.setLine(exprStartLine130);
        expr.setCharPositionInLine(exprStartCharPos130);
        this.match(59);
        if (this.state.failed) {
            return;
        }
        if (this.state.backtracking == 1) {
            this.delegate.setNonLocalAttr(this.getText(), x, y, expr);
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mNONLOCAL_ATTR() throws RecognitionException {
        int _type = 9;
        int _channel = 0;
        CommonToken x = null;
        CommonToken y = null;
        this.match(36);
        if (this.state.failed) {
            return;
        }
        int xStart151 = this.getCharIndex();
        int xStartLine151 = this.getLine();
        int xStartCharPos151 = this.getCharPositionInLine();
        this.mID();
        if (this.state.failed) {
            return;
        }
        x = new CommonToken(this.input, 0, 0, xStart151, this.getCharIndex() - 1);
        x.setLine(xStartLine151);
        x.setCharPositionInLine(xStartCharPos151);
        this.match("::");
        if (this.state.failed) {
            return;
        }
        int yStart157 = this.getCharIndex();
        int yStartLine157 = this.getLine();
        int yStartCharPos157 = this.getCharPositionInLine();
        this.mID();
        if (this.state.failed) {
            return;
        }
        y = new CommonToken(this.input, 0, 0, yStart157, this.getCharIndex() - 1);
        y.setLine(yStartLine157);
        y.setCharPositionInLine(yStartCharPos157);
        if (this.state.backtracking == 1) {
            this.delegate.nonLocalAttr(this.getText(), x, y);
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mQUALIFIED_ATTR() throws RecognitionException {
        int _type = 10;
        int _channel = 0;
        CommonToken x = null;
        CommonToken y = null;
        this.match(36);
        if (this.state.failed) {
            return;
        }
        int xStart174 = this.getCharIndex();
        int xStartLine174 = this.getLine();
        int xStartCharPos174 = this.getCharPositionInLine();
        this.mID();
        if (this.state.failed) {
            return;
        }
        x = new CommonToken(this.input, 0, 0, xStart174, this.getCharIndex() - 1);
        x.setLine(xStartLine174);
        x.setCharPositionInLine(xStartCharPos174);
        this.match(46);
        if (this.state.failed) {
            return;
        }
        int yStart180 = this.getCharIndex();
        int yStartLine180 = this.getLine();
        int yStartCharPos180 = this.getCharPositionInLine();
        this.mID();
        if (this.state.failed) {
            return;
        }
        y = new CommonToken(this.input, 0, 0, yStart180, this.getCharIndex() - 1);
        y.setLine(yStartLine180);
        y.setCharPositionInLine(yStartCharPos180);
        if (this.input.LA(1) == 40) {
            if (this.state.backtracking > 0) {
                this.state.failed = true;
                return;
            }
            throw new FailedPredicateException(this.input, "QUALIFIED_ATTR", "input.LA(1)!='('");
        }
        if (this.state.backtracking == 1) {
            this.delegate.qualifiedAttr(this.getText(), x, y);
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mSET_ATTR() throws RecognitionException {
        int _type = 11;
        int _channel = 0;
        CommonToken x = null;
        CommonToken expr = null;
        this.match(36);
        if (this.state.failed) {
            return;
        }
        int xStart199 = this.getCharIndex();
        int xStartLine199 = this.getLine();
        int xStartCharPos199 = this.getCharPositionInLine();
        this.mID();
        if (this.state.failed) {
            return;
        }
        x = new CommonToken(this.input, 0, 0, xStart199, this.getCharIndex() - 1);
        x.setLine(xStartLine199);
        x.setCharPositionInLine(xStartCharPos199);
        int alt5 = 2;
        int LA5_0 = this.input.LA(1);
        if (LA5_0 >= 9 && LA5_0 <= 10 || LA5_0 == 13 || LA5_0 == 32) {
            alt5 = 1;
        }
        switch (alt5) {
            case 1: {
                this.mWS();
                if (!this.state.failed) break;
                return;
            }
        }
        this.match(61);
        if (this.state.failed) {
            return;
        }
        int exprStart208 = this.getCharIndex();
        int exprStartLine208 = this.getLine();
        int exprStartCharPos208 = this.getCharPositionInLine();
        this.mATTR_VALUE_EXPR();
        if (this.state.failed) {
            return;
        }
        expr = new CommonToken(this.input, 0, 0, exprStart208, this.getCharIndex() - 1);
        expr.setLine(exprStartLine208);
        expr.setCharPositionInLine(exprStartCharPos208);
        this.match(59);
        if (this.state.failed) {
            return;
        }
        if (this.state.backtracking == 1) {
            this.delegate.setAttr(this.getText(), x, expr);
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mATTR() throws RecognitionException {
        int _type = 4;
        int _channel = 0;
        CommonToken x = null;
        this.match(36);
        if (this.state.failed) {
            return;
        }
        int xStart229 = this.getCharIndex();
        int xStartLine229 = this.getLine();
        int xStartCharPos229 = this.getCharPositionInLine();
        this.mID();
        if (this.state.failed) {
            return;
        }
        x = new CommonToken(this.input, 0, 0, xStart229, this.getCharIndex() - 1);
        x.setLine(xStartLine229);
        x.setCharPositionInLine(xStartCharPos229);
        if (this.state.backtracking == 1) {
            this.delegate.attr(this.getText(), x);
        }
        this.state.type = _type;
        this.state.channel = _channel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mTEXT() throws RecognitionException {
        int _type = 13;
        int _channel = 0;
        StringBuilder buf = new StringBuilder();
        int cnt6 = 0;
        block8: while (true) {
            int alt6 = 5;
            int LA6_0 = this.input.LA(1);
            if (LA6_0 >= 0 && LA6_0 <= 35 || LA6_0 >= 37 && LA6_0 <= 91 || LA6_0 >= 93 && LA6_0 <= 65535) {
                alt6 = 1;
            } else if (LA6_0 == 92) {
                int LA6_3 = this.input.LA(2);
                if (LA6_3 == 36) {
                    alt6 = 2;
                } else if (LA6_3 >= 0 && LA6_3 <= 35 || LA6_3 >= 37 && LA6_3 <= 65535) {
                    alt6 = 3;
                }
            } else if (LA6_0 == 36 && !this.isIDStartChar(this.input.LA(2))) {
                alt6 = 4;
            }
            switch (alt6) {
                case 1: {
                    int c = this.input.LA(1);
                    if (!(this.input.LA(1) >= 0 && this.input.LA(1) <= 35 || this.input.LA(1) >= 37 && this.input.LA(1) <= 91 || this.input.LA(1) >= 93 && this.input.LA(1) <= 65535)) {
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
                    if (this.state.backtracking != 1) break;
                    buf.append((char)c);
                    break;
                }
                case 2: {
                    this.match("\\$");
                    if (this.state.failed) {
                        return;
                    }
                    if (this.state.backtracking != 1) break;
                    buf.append('$');
                    break;
                }
                case 3: {
                    this.match(92);
                    if (this.state.failed) {
                        return;
                    }
                    int c = this.input.LA(1);
                    if (!(this.input.LA(1) >= 0 && this.input.LA(1) <= 35 || this.input.LA(1) >= 37 && this.input.LA(1) <= 65535)) {
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
                    if (this.state.backtracking != 1) break;
                    buf.append('\\').append((char)c);
                    break;
                }
                case 4: {
                    if (this.isIDStartChar(this.input.LA(2))) {
                        if (this.state.backtracking > 0) {
                            this.state.failed = true;
                            return;
                        }
                        throw new FailedPredicateException(this.input, "TEXT", "!isIDStartChar(input.LA(2))");
                    }
                    this.match(36);
                    if (this.state.failed) {
                        return;
                    }
                    if (this.state.backtracking != 1) break;
                    buf.append('$');
                    break;
                }
                default: {
                    if (cnt6 >= 1) break block8;
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    EarlyExitException eee = new EarlyExitException(6, this.input);
                    throw eee;
                }
            }
            ++cnt6;
        }
        this.state.type = _type;
        this.state.channel = _channel;
        if (this.state.backtracking == 1) {
            this.delegate.text(buf.toString());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mID() throws RecognitionException {
        if (!(this.input.LA(1) >= 65 && this.input.LA(1) <= 90 || this.input.LA(1) == 95 || this.input.LA(1) >= 97 && this.input.LA(1) <= 122)) {
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
        block5: while (true) {
            int alt7 = 2;
            int LA7_0 = this.input.LA(1);
            if (LA7_0 >= 48 && LA7_0 <= 57 || LA7_0 >= 65 && LA7_0 <= 90 || LA7_0 == 95 || LA7_0 >= 97 && LA7_0 <= 122) {
                alt7 = 1;
            }
            switch (alt7) {
                case 1: {
                    if (this.input.LA(1) >= 48 && this.input.LA(1) <= 57 || this.input.LA(1) >= 65 && this.input.LA(1) <= 90 || this.input.LA(1) == 95 || this.input.LA(1) >= 97 && this.input.LA(1) <= 122) {
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
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mATTR_VALUE_EXPR() throws RecognitionException {
        if (!(this.input.LA(1) >= 0 && this.input.LA(1) <= 60 || this.input.LA(1) >= 62 && this.input.LA(1) <= 65535)) {
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
        block5: while (true) {
            int alt8 = 2;
            int LA8_0 = this.input.LA(1);
            if (LA8_0 >= 0 && LA8_0 <= 58 || LA8_0 >= 60 && LA8_0 <= 65535) {
                alt8 = 1;
            }
            switch (alt8) {
                case 1: {
                    if (this.input.LA(1) >= 0 && this.input.LA(1) <= 58 || this.input.LA(1) >= 60 && this.input.LA(1) <= 65535) {
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
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void mWS() throws RecognitionException {
        block11: {
            int cnt9 = 0;
            while (true) {
                int alt9 = 2;
                int LA9_0 = this.input.LA(1);
                if (LA9_0 >= 9 && LA9_0 <= 10 || LA9_0 == 13 || LA9_0 == 32) {
                    alt9 = 1;
                }
                switch (alt9) {
                    case 1: {
                        if (this.input.LA(1) >= 9 && this.input.LA(1) <= 10 || this.input.LA(1) == 13 || this.input.LA(1) == 32) {
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
                        if (cnt9 < 1) {
                            if (this.state.backtracking > 0) {
                                this.state.failed = true;
                                return;
                            }
                            EarlyExitException eee = new EarlyExitException(9, this.input);
                            throw eee;
                        }
                        break block11;
                    }
                }
                ++cnt9;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void mTokens() throws RecognitionException {
        int alt10;
        block20: {
            int LA10_0;
            block22: {
                block26: {
                    block25: {
                        block24: {
                            block23: {
                                block21: {
                                    alt10 = 8;
                                    LA10_0 = this.input.LA(1);
                                    if (LA10_0 != 47) break block21;
                                    int LA10_1 = this.input.LA(2);
                                    alt10 = this.synpred1_ActionSplitter() ? 1 : (this.synpred2_ActionSplitter() ? 2 : 8);
                                    break block20;
                                }
                                if (LA10_0 != 36) break block22;
                                int LA10_2 = this.input.LA(2);
                                if (!this.synpred3_ActionSplitter()) break block23;
                                alt10 = 3;
                                break block20;
                            }
                            if (!this.synpred4_ActionSplitter()) break block24;
                            alt10 = 4;
                            break block20;
                        }
                        if (!this.synpred5_ActionSplitter()) break block25;
                        alt10 = 5;
                        break block20;
                    }
                    if (!this.synpred6_ActionSplitter()) break block26;
                    alt10 = 6;
                    break block20;
                }
                if (this.synpred7_ActionSplitter()) {
                    alt10 = 7;
                    break block20;
                } else if (!this.isIDStartChar(this.input.LA(2))) {
                    alt10 = 8;
                    break block20;
                } else {
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 10, 2, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
            }
            if (LA10_0 >= 0 && LA10_0 <= 35 || LA10_0 >= 37 && LA10_0 <= 46 || LA10_0 >= 48 && LA10_0 <= 65535) {
                alt10 = 8;
            } else {
                if (this.state.backtracking > 0) {
                    this.state.failed = true;
                    return;
                }
                NoViableAltException nvae = new NoViableAltException("", 10, 0, this.input);
                throw nvae;
            }
        }
        switch (alt10) {
            case 1: {
                this.mCOMMENT();
                if (!this.state.failed) break;
                return;
            }
            case 2: {
                this.mLINE_COMMENT();
                if (!this.state.failed) break;
                return;
            }
            case 3: {
                this.mSET_NONLOCAL_ATTR();
                if (!this.state.failed) break;
                return;
            }
            case 4: {
                this.mNONLOCAL_ATTR();
                if (!this.state.failed) break;
                return;
            }
            case 5: {
                this.mQUALIFIED_ATTR();
                if (!this.state.failed) break;
                return;
            }
            case 6: {
                this.mSET_ATTR();
                if (!this.state.failed) break;
                return;
            }
            case 7: {
                this.mATTR();
                if (!this.state.failed) break;
                return;
            }
            case 8: {
                this.mTEXT();
                if (!this.state.failed) break;
                return;
            }
        }
    }

    public final void synpred1_ActionSplitter_fragment() throws RecognitionException {
        this.mCOMMENT();
        if (this.state.failed) {
            return;
        }
    }

    public final void synpred2_ActionSplitter_fragment() throws RecognitionException {
        this.mLINE_COMMENT();
        if (this.state.failed) {
            return;
        }
    }

    public final void synpred3_ActionSplitter_fragment() throws RecognitionException {
        this.mSET_NONLOCAL_ATTR();
        if (this.state.failed) {
            return;
        }
    }

    public final void synpred4_ActionSplitter_fragment() throws RecognitionException {
        this.mNONLOCAL_ATTR();
        if (this.state.failed) {
            return;
        }
    }

    public final void synpred5_ActionSplitter_fragment() throws RecognitionException {
        this.mQUALIFIED_ATTR();
        if (this.state.failed) {
            return;
        }
    }

    public final void synpred6_ActionSplitter_fragment() throws RecognitionException {
        this.mSET_ATTR();
        if (this.state.failed) {
            return;
        }
    }

    public final void synpred7_ActionSplitter_fragment() throws RecognitionException {
        this.mATTR();
        if (this.state.failed) {
            return;
        }
    }

    public final boolean synpred4_ActionSplitter() {
        ++this.state.backtracking;
        int start = this.input.mark();
        try {
            this.synpred4_ActionSplitter_fragment();
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

    public final boolean synpred1_ActionSplitter() {
        ++this.state.backtracking;
        int start = this.input.mark();
        try {
            this.synpred1_ActionSplitter_fragment();
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

    public final boolean synpred2_ActionSplitter() {
        ++this.state.backtracking;
        int start = this.input.mark();
        try {
            this.synpred2_ActionSplitter_fragment();
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

    public final boolean synpred7_ActionSplitter() {
        ++this.state.backtracking;
        int start = this.input.mark();
        try {
            this.synpred7_ActionSplitter_fragment();
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

    public final boolean synpred6_ActionSplitter() {
        ++this.state.backtracking;
        int start = this.input.mark();
        try {
            this.synpred6_ActionSplitter_fragment();
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

    public final boolean synpred5_ActionSplitter() {
        ++this.state.backtracking;
        int start = this.input.mark();
        try {
            this.synpred5_ActionSplitter_fragment();
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

    public final boolean synpred3_ActionSplitter() {
        ++this.state.backtracking;
        int start = this.input.mark();
        try {
            this.synpred3_ActionSplitter_fragment();
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
}

