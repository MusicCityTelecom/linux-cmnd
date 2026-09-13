/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.parse;

import groovyjarjarantlr4.runtime.BaseRecognizer;
import groovyjarjarantlr4.runtime.BitSet;
import groovyjarjarantlr4.runtime.DFA;
import groovyjarjarantlr4.runtime.EarlyExitException;
import groovyjarjarantlr4.runtime.MismatchedSetException;
import groovyjarjarantlr4.runtime.NoViableAltException;
import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.RecognizerSharedState;
import groovyjarjarantlr4.runtime.tree.TreeNodeStream;
import groovyjarjarantlr4.runtime.tree.TreeParser;
import groovyjarjarantlr4.runtime.tree.TreeRuleReturnScope;
import groovyjarjarantlr4.v4.automata.ATNFactory;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.BlockAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.PredAST;
import groovyjarjarantlr4.v4.tool.ast.TerminalAST;
import java.util.ArrayList;

public class ATNBuilder
extends TreeParser {
    public static final String[] tokenNames = new String[]{"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ACTION", "ACTION_CHAR_LITERAL", "ACTION_ESC", "ACTION_STRING_LITERAL", "ARG_ACTION", "ARG_OR_CHARSET", "ASSIGN", "AT", "CATCH", "CHANNELS", "COLON", "COLONCOLON", "COMMA", "COMMENT", "DOC_COMMENT", "DOLLAR", "DOT", "ERRCHAR", "ESC_SEQ", "FINALLY", "FRAGMENT", "GRAMMAR", "GT", "HEX_DIGIT", "ID", "IMPORT", "INT", "LEXER", "LEXER_CHAR_SET", "LOCALS", "LPAREN", "LT", "MODE", "NESTED_ACTION", "NLCHARS", "NOT", "NameChar", "NameStartChar", "OPTIONS", "OR", "PARSER", "PLUS", "PLUS_ASSIGN", "POUND", "PRIVATE", "PROTECTED", "PUBLIC", "QUESTION", "RANGE", "RARROW", "RBRACE", "RETURNS", "RPAREN", "RULE_REF", "SEMI", "SEMPRED", "SRC", "STAR", "STRING_LITERAL", "SYNPRED", "THROWS", "TOKENS_SPEC", "TOKEN_REF", "TREE_GRAMMAR", "UNICODE_ESC", "UNICODE_EXTENDED_ESC", "UnicodeBOM", "WS", "WSCHARS", "WSNLCHARS", "ALT", "ALTLIST", "ARG", "ARGLIST", "BLOCK", "CHAR_RANGE", "CLOSURE", "COMBINED", "ELEMENT_OPTIONS", "EPSILON", "INITACTION", "LABEL", "LEXER_ACTION_CALL", "LEXER_ALT_ACTION", "LIST", "OPTIONAL", "POSITIVE_CLOSURE", "PREC_RULE", "RESULT", "RET", "RULE", "RULEACTIONS", "RULEMODIFIERS", "RULES", "SET", "TEMPLATE", "WILDCARD"};
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
    public static final int ALT = 74;
    public static final int ALTLIST = 75;
    public static final int ARG = 76;
    public static final int ARGLIST = 77;
    public static final int BLOCK = 78;
    public static final int CHAR_RANGE = 79;
    public static final int CLOSURE = 80;
    public static final int COMBINED = 81;
    public static final int ELEMENT_OPTIONS = 82;
    public static final int EPSILON = 83;
    public static final int INITACTION = 84;
    public static final int LABEL = 85;
    public static final int LEXER_ACTION_CALL = 86;
    public static final int LEXER_ALT_ACTION = 87;
    public static final int LIST = 88;
    public static final int OPTIONAL = 89;
    public static final int POSITIVE_CLOSURE = 90;
    public static final int PREC_RULE = 91;
    public static final int RESULT = 92;
    public static final int RET = 93;
    public static final int RULE = 94;
    public static final int RULEACTIONS = 95;
    public static final int RULEMODIFIERS = 96;
    public static final int RULES = 97;
    public static final int SET = 98;
    public static final int TEMPLATE = 99;
    public static final int WILDCARD = 100;
    ATNFactory factory;
    protected DFA10 dfa10 = new DFA10(this);
    static final String DFA10_eotS = "\u0015\uffff";
    static final String DFA10_eofS = "\u0015\uffff";
    static final String DFA10_minS = "\u0001J\u0001\uffff\u0001\u0002\u0001\u0004\u0001\u0002\u0002\uffff\u0002\u0003\u0001\u0002\u0001\u0004\u0001\u001c\u0001\u0004\b\u0003";
    static final String DFA10_maxS = "\u0001W\u0001\uffff\u0001\u0002\u0001d\u0001\u0002\u0002\uffff\u0002\u001c\u0001\u0002\u0001d\u0001\u001c\u0001>\u0004\u0003\u0004\u001c";
    static final String DFA10_acceptS = "\u0001\uffff\u0001\u0001\u0003\uffff\u0001\u0002\u0001\u0003\u000e\uffff";
    static final String DFA10_specialS = "\u0015\uffff}>";
    static final String[] DFA10_transitionS = new String[]{"\u0001\u0002\f\uffff\u0001\u0001", "", "\u0001\u0003", "\u0001\u0006\u0005\uffff\u0001\u0006\t\uffff\u0001\u0006\u000b\uffff\u0001\u0006\u0006\uffff\u0001\u0006\u0006\uffff\u0001\u0006\u0005\uffff\u0001\u0006\u0004\uffff\u0001\u0006\u0001\uffff\u0001\u0006\u0002\uffff\u0001\u0006\u0003\uffff\u0001\u0006\u000b\uffff\u0001\u0006\u0001\uffff\u0001\u0006\u0001\uffff\u0001\u0004\u0001\u0005\u0005\uffff\u0002\u0006\u0007\uffff\u0001\u0006\u0001\uffff\u0001\u0006", "\u0001\u0007", "", "", "\u0001\n\u0006\uffff\u0001\t\u0011\uffff\u0001\b", "\u0001\n\u0006\uffff\u0001\t\u0011\uffff\u0001\b", "\u0001\u000b", "\u0001\u0006\u0005\uffff\u0001\u0006\t\uffff\u0001\u0006\u000b\uffff\u0001\u0006\u0006\uffff\u0001\u0006\u0006\uffff\u0001\u0006\u0005\uffff\u0001\u0006\u0004\uffff\u0001\u0006\u0001\uffff\u0001\u0006\u0002\uffff\u0001\u0006\u0003\uffff\u0001\u0006\u000b\uffff\u0001\u0006\u0001\uffff\u0001\u0006\u0002\uffff\u0001\u0005\u0005\uffff\u0002\u0006\u0007\uffff\u0001\u0006\u0001\uffff\u0001\u0006", "\u0001\f", "\u0001\u000f\u0017\uffff\u0001\r\u0001\uffff\u0001\u0010\u001f\uffff\u0001\u000e", "\u0001\u0011", "\u0001\u0012", "\u0001\u0013", "\u0001\u0014", "\u0001\n\u0006\uffff\u0001\t\u0011\uffff\u0001\b", "\u0001\n\u0006\uffff\u0001\t\u0011\uffff\u0001\b", "\u0001\n\u0006\uffff\u0001\t\u0011\uffff\u0001\b", "\u0001\n\u0006\uffff\u0001\t\u0011\uffff\u0001\b"};
    static final short[] DFA10_eot = DFA.unpackEncodedString("\u0015\uffff");
    static final short[] DFA10_eof = DFA.unpackEncodedString("\u0015\uffff");
    static final char[] DFA10_min = DFA.unpackEncodedStringToUnsignedChars("\u0001J\u0001\uffff\u0001\u0002\u0001\u0004\u0001\u0002\u0002\uffff\u0002\u0003\u0001\u0002\u0001\u0004\u0001\u001c\u0001\u0004\b\u0003");
    static final char[] DFA10_max = DFA.unpackEncodedStringToUnsignedChars("\u0001W\u0001\uffff\u0001\u0002\u0001d\u0001\u0002\u0002\uffff\u0002\u001c\u0001\u0002\u0001d\u0001\u001c\u0001>\u0004\u0003\u0004\u001c");
    static final short[] DFA10_accept = DFA.unpackEncodedString("\u0001\uffff\u0001\u0001\u0003\uffff\u0001\u0002\u0001\u0003\u000e\uffff");
    static final short[] DFA10_special = DFA.unpackEncodedString("\u0015\uffff}>");
    static final short[][] DFA10_transition;
    public static final BitSet FOLLOW_block_in_dummy63;
    public static final BitSet FOLLOW_BLOCK_in_ruleBlock89;
    public static final BitSet FOLLOW_OPTIONS_in_ruleBlock105;
    public static final BitSet FOLLOW_alternative_in_ruleBlock131;
    public static final BitSet FOLLOW_BLOCK_in_block209;
    public static final BitSet FOLLOW_OPTIONS_in_block213;
    public static final BitSet FOLLOW_alternative_in_block224;
    public static final BitSet FOLLOW_LEXER_ALT_ACTION_in_alternative263;
    public static final BitSet FOLLOW_alternative_in_alternative267;
    public static final BitSet FOLLOW_lexerCommands_in_alternative269;
    public static final BitSet FOLLOW_ALT_in_alternative289;
    public static final BitSet FOLLOW_elementOptions_in_alternative291;
    public static final BitSet FOLLOW_EPSILON_in_alternative294;
    public static final BitSet FOLLOW_ALT_in_alternative314;
    public static final BitSet FOLLOW_elementOptions_in_alternative316;
    public static final BitSet FOLLOW_element_in_alternative322;
    public static final BitSet FOLLOW_lexerCommand_in_lexerCommands360;
    public static final BitSet FOLLOW_LEXER_ACTION_CALL_in_lexerCommand393;
    public static final BitSet FOLLOW_ID_in_lexerCommand395;
    public static final BitSet FOLLOW_lexerCommandExpr_in_lexerCommand397;
    public static final BitSet FOLLOW_ID_in_lexerCommand413;
    public static final BitSet FOLLOW_labeledElement_in_element454;
    public static final BitSet FOLLOW_atom_in_element464;
    public static final BitSet FOLLOW_subrule_in_element476;
    public static final BitSet FOLLOW_ACTION_in_element490;
    public static final BitSet FOLLOW_SEMPRED_in_element504;
    public static final BitSet FOLLOW_ACTION_in_element519;
    public static final BitSet FOLLOW_SEMPRED_in_element536;
    public static final BitSet FOLLOW_NOT_in_element553;
    public static final BitSet FOLLOW_blockSet_in_element557;
    public static final BitSet FOLLOW_LEXER_CHAR_SET_in_element570;
    public static final BitSet FOLLOW_atom_in_astOperand590;
    public static final BitSet FOLLOW_NOT_in_astOperand603;
    public static final BitSet FOLLOW_blockSet_in_astOperand605;
    public static final BitSet FOLLOW_ASSIGN_in_labeledElement626;
    public static final BitSet FOLLOW_ID_in_labeledElement628;
    public static final BitSet FOLLOW_element_in_labeledElement630;
    public static final BitSet FOLLOW_PLUS_ASSIGN_in_labeledElement643;
    public static final BitSet FOLLOW_ID_in_labeledElement645;
    public static final BitSet FOLLOW_element_in_labeledElement647;
    public static final BitSet FOLLOW_OPTIONAL_in_subrule668;
    public static final BitSet FOLLOW_block_in_subrule670;
    public static final BitSet FOLLOW_CLOSURE_in_subrule682;
    public static final BitSet FOLLOW_block_in_subrule684;
    public static final BitSet FOLLOW_POSITIVE_CLOSURE_in_subrule696;
    public static final BitSet FOLLOW_block_in_subrule698;
    public static final BitSet FOLLOW_block_in_subrule708;
    public static final BitSet FOLLOW_SET_in_blockSet742;
    public static final BitSet FOLLOW_setElement_in_blockSet745;
    public static final BitSet FOLLOW_STRING_LITERAL_in_setElement766;
    public static final BitSet FOLLOW_TOKEN_REF_in_setElement775;
    public static final BitSet FOLLOW_STRING_LITERAL_in_setElement783;
    public static final BitSet FOLLOW_TOKEN_REF_in_setElement788;
    public static final BitSet FOLLOW_RANGE_in_setElement794;
    public static final BitSet FOLLOW_STRING_LITERAL_in_setElement798;
    public static final BitSet FOLLOW_STRING_LITERAL_in_setElement802;
    public static final BitSet FOLLOW_LEXER_CHAR_SET_in_setElement813;
    public static final BitSet FOLLOW_range_in_atom828;
    public static final BitSet FOLLOW_DOT_in_atom840;
    public static final BitSet FOLLOW_ID_in_atom842;
    public static final BitSet FOLLOW_terminal_in_atom844;
    public static final BitSet FOLLOW_DOT_in_atom854;
    public static final BitSet FOLLOW_ID_in_atom856;
    public static final BitSet FOLLOW_ruleref_in_atom858;
    public static final BitSet FOLLOW_WILDCARD_in_atom871;
    public static final BitSet FOLLOW_WILDCARD_in_atom886;
    public static final BitSet FOLLOW_blockSet_in_atom899;
    public static final BitSet FOLLOW_terminal_in_atom914;
    public static final BitSet FOLLOW_ruleref_in_atom929;
    public static final BitSet FOLLOW_RULE_REF_in_ruleref957;
    public static final BitSet FOLLOW_ARG_ACTION_in_ruleref959;
    public static final BitSet FOLLOW_ELEMENT_OPTIONS_in_ruleref963;
    public static final BitSet FOLLOW_RULE_REF_in_ruleref980;
    public static final BitSet FOLLOW_ARG_ACTION_in_ruleref982;
    public static final BitSet FOLLOW_RULE_REF_in_ruleref1001;
    public static final BitSet FOLLOW_RANGE_in_range1035;
    public static final BitSet FOLLOW_STRING_LITERAL_in_range1039;
    public static final BitSet FOLLOW_STRING_LITERAL_in_range1043;
    public static final BitSet FOLLOW_STRING_LITERAL_in_terminal1069;
    public static final BitSet FOLLOW_STRING_LITERAL_in_terminal1084;
    public static final BitSet FOLLOW_TOKEN_REF_in_terminal1098;
    public static final BitSet FOLLOW_ARG_ACTION_in_terminal1100;
    public static final BitSet FOLLOW_TOKEN_REF_in_terminal1114;
    public static final BitSet FOLLOW_TOKEN_REF_in_terminal1130;
    public static final BitSet FOLLOW_ELEMENT_OPTIONS_in_elementOptions1151;
    public static final BitSet FOLLOW_elementOption_in_elementOptions1153;
    public static final BitSet FOLLOW_ID_in_elementOption1166;
    public static final BitSet FOLLOW_ASSIGN_in_elementOption1172;
    public static final BitSet FOLLOW_ID_in_elementOption1174;
    public static final BitSet FOLLOW_ID_in_elementOption1176;
    public static final BitSet FOLLOW_ASSIGN_in_elementOption1183;
    public static final BitSet FOLLOW_ID_in_elementOption1185;
    public static final BitSet FOLLOW_STRING_LITERAL_in_elementOption1187;
    public static final BitSet FOLLOW_ASSIGN_in_elementOption1194;
    public static final BitSet FOLLOW_ID_in_elementOption1196;
    public static final BitSet FOLLOW_ACTION_in_elementOption1198;
    public static final BitSet FOLLOW_ASSIGN_in_elementOption1205;
    public static final BitSet FOLLOW_ID_in_elementOption1207;
    public static final BitSet FOLLOW_INT_in_elementOption1209;

    public TreeParser[] getDelegates() {
        return new TreeParser[0];
    }

    public ATNBuilder(TreeNodeStream input) {
        this(input, new RecognizerSharedState());
    }

    public ATNBuilder(TreeNodeStream input, RecognizerSharedState state) {
        super(input, state);
    }

    @Override
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override
    public String getGrammarFileName() {
        return "org\\antlr\\v4\\parse\\ATNBuilder.g";
    }

    public ATNBuilder(TreeNodeStream input, ATNFactory factory) {
        this(input);
        this.factory = factory;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void dummy() throws RecognitionException {
        try {
            this.pushFollow(FOLLOW_block_in_dummy63);
            this.block(null);
            --this.state._fsp;
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final ATNFactory.Handle ruleBlock(GrammarAST ebnfRoot) throws RecognitionException {
        ATNFactory.Handle p = null;
        GrammarAST BLOCK1 = null;
        ATNFactory.Handle a = null;
        ArrayList<ATNFactory.Handle> alts = new ArrayList<ATNFactory.Handle>();
        int alt = 1;
        this.factory.setCurrentOuterAlt(alt);
        try {
            BLOCK1 = (GrammarAST)this.match(this.input, 78, FOLLOW_BLOCK_in_ruleBlock89);
            this.match(this.input, 2, null);
            int alt2 = 2;
            int LA2_0 = this.input.LA(1);
            if (LA2_0 == 42) {
                alt2 = 1;
            }
            switch (alt2) {
                case 1: {
                    this.match(this.input, 42, FOLLOW_OPTIONS_in_ruleBlock105);
                    if (this.input.LA(1) != 2) break;
                    this.match(this.input, 2, null);
                    block13: while (true) {
                        int alt1 = 2;
                        int LA1_0 = this.input.LA(1);
                        if (LA1_0 >= 4 && LA1_0 <= 100) {
                            alt1 = 1;
                        } else if (LA1_0 == 3) {
                            alt1 = 2;
                        }
                        switch (alt1) {
                            case 1: {
                                this.matchAny(this.input);
                                continue block13;
                            }
                        }
                        break;
                    }
                    this.match(this.input, 3, null);
                }
            }
            int cnt3 = 0;
            block14: while (true) {
                int alt3 = 2;
                int LA3_0 = this.input.LA(1);
                if (LA3_0 == 74 || LA3_0 == 87) {
                    alt3 = 1;
                }
                switch (alt3) {
                    case 1: {
                        this.pushFollow(FOLLOW_alternative_in_ruleBlock131);
                        a = this.alternative();
                        --this.state._fsp;
                        alts.add(a);
                        this.factory.setCurrentOuterAlt(++alt);
                        break;
                    }
                    default: {
                        if (cnt3 >= 1) break block14;
                        EarlyExitException eee = new EarlyExitException(3, this.input);
                        throw eee;
                    }
                }
                ++cnt3;
            }
            this.match(this.input, 3, null);
            p = this.factory.block((BlockAST)BLOCK1, ebnfRoot, alts);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
        }
        return p;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final ATNFactory.Handle block(GrammarAST ebnfRoot) throws RecognitionException {
        ATNFactory.Handle p = null;
        GrammarAST BLOCK2 = null;
        ATNFactory.Handle a = null;
        ArrayList<ATNFactory.Handle> alts = new ArrayList<ATNFactory.Handle>();
        try {
            BLOCK2 = (GrammarAST)this.match(this.input, 78, FOLLOW_BLOCK_in_block209);
            this.match(this.input, 2, null);
            int alt5 = 2;
            int LA5_0 = this.input.LA(1);
            if (LA5_0 == 42) {
                alt5 = 1;
            }
            switch (alt5) {
                case 1: {
                    this.match(this.input, 42, FOLLOW_OPTIONS_in_block213);
                    if (this.input.LA(1) != 2) break;
                    this.match(this.input, 2, null);
                    block13: while (true) {
                        int alt4 = 2;
                        int LA4_0 = this.input.LA(1);
                        if (LA4_0 >= 4 && LA4_0 <= 100) {
                            alt4 = 1;
                        } else if (LA4_0 == 3) {
                            alt4 = 2;
                        }
                        switch (alt4) {
                            case 1: {
                                this.matchAny(this.input);
                                continue block13;
                            }
                        }
                        break;
                    }
                    this.match(this.input, 3, null);
                }
            }
            int cnt6 = 0;
            block14: while (true) {
                int alt6 = 2;
                int LA6_0 = this.input.LA(1);
                if (LA6_0 == 74 || LA6_0 == 87) {
                    alt6 = 1;
                }
                switch (alt6) {
                    case 1: {
                        this.pushFollow(FOLLOW_alternative_in_block224);
                        a = this.alternative();
                        --this.state._fsp;
                        alts.add(a);
                        break;
                    }
                    default: {
                        if (cnt6 >= 1) break block14;
                        EarlyExitException eee = new EarlyExitException(6, this.input);
                        throw eee;
                    }
                }
                ++cnt6;
            }
            this.match(this.input, 3, null);
            p = this.factory.block((BlockAST)BLOCK2, ebnfRoot, alts);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
        }
        return p;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final ATNFactory.Handle alternative() throws RecognitionException {
        ATNFactory.Handle p = null;
        GrammarAST EPSILON4 = null;
        ATNFactory.Handle a = null;
        element_return e = null;
        ATNFactory.Handle lexerCommands3 = null;
        ArrayList<ATNFactory.Handle> els = new ArrayList<ATNFactory.Handle>();
        try {
            int alt10 = 3;
            alt10 = this.dfa10.predict(this.input);
            switch (alt10) {
                case 1: {
                    this.match(this.input, 87, FOLLOW_LEXER_ALT_ACTION_in_alternative263);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_alternative_in_alternative267);
                    a = this.alternative();
                    --this.state._fsp;
                    this.pushFollow(FOLLOW_lexerCommands_in_alternative269);
                    lexerCommands3 = this.lexerCommands();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    p = this.factory.lexerAltCommands(a, lexerCommands3);
                    break;
                }
                case 2: {
                    this.match(this.input, 74, FOLLOW_ALT_in_alternative289);
                    this.match(this.input, 2, null);
                    int alt7 = 2;
                    int LA7_0 = this.input.LA(1);
                    if (LA7_0 == 82) {
                        alt7 = 1;
                    }
                    switch (alt7) {
                        case 1: {
                            this.pushFollow(FOLLOW_elementOptions_in_alternative291);
                            this.elementOptions();
                            --this.state._fsp;
                        }
                    }
                    EPSILON4 = (GrammarAST)this.match(this.input, 83, FOLLOW_EPSILON_in_alternative294);
                    this.match(this.input, 3, null);
                    p = this.factory.epsilon(EPSILON4);
                    break;
                }
                case 3: {
                    this.match(this.input, 74, FOLLOW_ALT_in_alternative314);
                    this.match(this.input, 2, null);
                    int alt8 = 2;
                    int LA8_0 = this.input.LA(1);
                    if (LA8_0 == 82) {
                        alt8 = 1;
                    }
                    switch (alt8) {
                        case 1: {
                            this.pushFollow(FOLLOW_elementOptions_in_alternative316);
                            this.elementOptions();
                            --this.state._fsp;
                        }
                    }
                    int cnt9 = 0;
                    block18: while (true) {
                        int alt9 = 2;
                        int LA9_0 = this.input.LA(1);
                        if (LA9_0 == 4 || LA9_0 == 10 || LA9_0 == 20 || LA9_0 == 32 || LA9_0 == 39 || LA9_0 == 46 || LA9_0 == 52 || LA9_0 == 57 || LA9_0 == 59 || LA9_0 == 62 || LA9_0 == 66 || LA9_0 == 78 || LA9_0 == 80 || LA9_0 >= 89 && LA9_0 <= 90 || LA9_0 == 98 || LA9_0 == 100) {
                            alt9 = 1;
                        }
                        switch (alt9) {
                            case 1: {
                                this.pushFollow(FOLLOW_element_in_alternative322);
                                e = this.element();
                                --this.state._fsp;
                                els.add(e != null ? e.p : null);
                                break;
                            }
                            default: {
                                if (cnt9 >= 1) break block18;
                                EarlyExitException eee = new EarlyExitException(9, this.input);
                                throw eee;
                            }
                        }
                        ++cnt9;
                    }
                    this.match(this.input, 3, null);
                    p = this.factory.alt(els);
                }
            }
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
        }
        return p;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final ATNFactory.Handle lexerCommands() throws RecognitionException {
        ATNFactory.Handle p = null;
        ATNFactory.Handle c = null;
        ArrayList<ATNFactory.Handle> cmds = new ArrayList<ATNFactory.Handle>();
        try {
            int cnt11 = 0;
            block7: while (true) {
                int alt11 = 2;
                int LA11_0 = this.input.LA(1);
                if (LA11_0 == 28 || LA11_0 == 86) {
                    alt11 = 1;
                }
                switch (alt11) {
                    case 1: {
                        this.pushFollow(FOLLOW_lexerCommand_in_lexerCommands360);
                        c = this.lexerCommand();
                        --this.state._fsp;
                        if (c == null) break;
                        cmds.add(c);
                        break;
                    }
                    default: {
                        if (cnt11 >= 1) break block7;
                        EarlyExitException eee = new EarlyExitException(11, this.input);
                        throw eee;
                    }
                }
                ++cnt11;
            }
            p = this.factory.alt(cmds);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
        }
        return p;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final ATNFactory.Handle lexerCommand() throws RecognitionException {
        ATNFactory.Handle cmd = null;
        GrammarAST ID5 = null;
        GrammarAST ID7 = null;
        lexerCommandExpr_return lexerCommandExpr6 = null;
        try {
            int alt12 = 2;
            int LA12_0 = this.input.LA(1);
            if (LA12_0 == 86) {
                alt12 = 1;
            } else if (LA12_0 == 28) {
                alt12 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 12, 0, this.input);
                throw nvae;
            }
            switch (alt12) {
                case 1: {
                    this.match(this.input, 86, FOLLOW_LEXER_ACTION_CALL_in_lexerCommand393);
                    this.match(this.input, 2, null);
                    ID5 = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_lexerCommand395);
                    this.pushFollow(FOLLOW_lexerCommandExpr_in_lexerCommand397);
                    lexerCommandExpr6 = this.lexerCommandExpr();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    cmd = this.factory.lexerCallCommand(ID5, lexerCommandExpr6 != null ? (GrammarAST)lexerCommandExpr6.start : null);
                    break;
                }
                case 2: {
                    ID7 = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_lexerCommand413);
                    cmd = this.factory.lexerCommand(ID7);
                }
            }
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
        }
        return cmd;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final lexerCommandExpr_return lexerCommandExpr() throws RecognitionException {
        lexerCommandExpr_return retval = new lexerCommandExpr_return();
        retval.start = this.input.LT(1);
        try {
            if (this.input.LA(1) != 28 && this.input.LA(1) != 30) {
                MismatchedSetException mse = new MismatchedSetException(null, this.input);
                throw mse;
            }
            this.input.consume();
            this.state.errorRecovery = false;
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final element_return element() throws RecognitionException {
        element_return retval = new element_return();
        retval.start = this.input.LT(1);
        GrammarAST ACTION11 = null;
        GrammarAST SEMPRED12 = null;
        GrammarAST ACTION13 = null;
        GrammarAST SEMPRED14 = null;
        blockSet_return b = null;
        ATNFactory.Handle labeledElement8 = null;
        atom_return atom9 = null;
        subrule_return subrule10 = null;
        try {
            int alt13 = 9;
            switch (this.input.LA(1)) {
                case 10: 
                case 46: {
                    alt13 = 1;
                    break;
                }
                case 20: 
                case 52: 
                case 57: 
                case 62: 
                case 66: 
                case 98: 
                case 100: {
                    alt13 = 2;
                    break;
                }
                case 78: 
                case 80: 
                case 89: 
                case 90: {
                    alt13 = 3;
                    break;
                }
                case 4: {
                    int LA13_4 = this.input.LA(2);
                    if (LA13_4 == 2) {
                        alt13 = 6;
                        break;
                    }
                    if (LA13_4 >= 3 && LA13_4 <= 4 || LA13_4 == 10 || LA13_4 == 20 || LA13_4 == 32 || LA13_4 == 39 || LA13_4 == 46 || LA13_4 == 52 || LA13_4 == 57 || LA13_4 == 59 || LA13_4 == 62 || LA13_4 == 66 || LA13_4 == 78 || LA13_4 == 80 || LA13_4 >= 89 && LA13_4 <= 90 || LA13_4 == 98 || LA13_4 == 100) {
                        alt13 = 4;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 13, 4, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 59: {
                    int LA13_5 = this.input.LA(2);
                    if (LA13_5 == 2) {
                        alt13 = 7;
                        break;
                    }
                    if (LA13_5 >= 3 && LA13_5 <= 4 || LA13_5 == 10 || LA13_5 == 20 || LA13_5 == 32 || LA13_5 == 39 || LA13_5 == 46 || LA13_5 == 52 || LA13_5 == 57 || LA13_5 == 59 || LA13_5 == 62 || LA13_5 == 66 || LA13_5 == 78 || LA13_5 == 80 || LA13_5 >= 89 && LA13_5 <= 90 || LA13_5 == 98 || LA13_5 == 100) {
                        alt13 = 5;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 13, 5, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 39: {
                    alt13 = 8;
                    break;
                }
                case 32: {
                    alt13 = 9;
                    break;
                }
                default: {
                    NoViableAltException nvae = new NoViableAltException("", 13, 0, this.input);
                    throw nvae;
                }
            }
            switch (alt13) {
                case 1: {
                    this.pushFollow(FOLLOW_labeledElement_in_element454);
                    labeledElement8 = this.labeledElement();
                    --this.state._fsp;
                    retval.p = labeledElement8;
                    break;
                }
                case 2: {
                    this.pushFollow(FOLLOW_atom_in_element464);
                    atom9 = this.atom();
                    --this.state._fsp;
                    retval.p = atom9 != null ? atom9.p : null;
                    break;
                }
                case 3: {
                    this.pushFollow(FOLLOW_subrule_in_element476);
                    subrule10 = this.subrule();
                    --this.state._fsp;
                    retval.p = subrule10 != null ? subrule10.p : null;
                    break;
                }
                case 4: {
                    ACTION11 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_element490);
                    retval.p = this.factory.action((ActionAST)ACTION11);
                    break;
                }
                case 5: {
                    SEMPRED12 = (GrammarAST)this.match(this.input, 59, FOLLOW_SEMPRED_in_element504);
                    retval.p = this.factory.sempred((PredAST)SEMPRED12);
                    break;
                }
                case 6: {
                    ACTION13 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_element519);
                    this.match(this.input, 2, null);
                    this.matchAny(this.input);
                    this.match(this.input, 3, null);
                    retval.p = this.factory.action((ActionAST)ACTION13);
                    break;
                }
                case 7: {
                    SEMPRED14 = (GrammarAST)this.match(this.input, 59, FOLLOW_SEMPRED_in_element536);
                    this.match(this.input, 2, null);
                    this.matchAny(this.input);
                    this.match(this.input, 3, null);
                    retval.p = this.factory.sempred((PredAST)SEMPRED14);
                    break;
                }
                case 8: {
                    this.match(this.input, 39, FOLLOW_NOT_in_element553);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_blockSet_in_element557);
                    b = this.blockSet(true);
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    retval.p = b != null ? b.p : null;
                    break;
                }
                case 9: {
                    this.match(this.input, 32, FOLLOW_LEXER_CHAR_SET_in_element570);
                    retval.p = this.factory.charSetLiteral((GrammarAST)retval.start);
                }
            }
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final ATNFactory.Handle astOperand() throws RecognitionException {
        ATNFactory.Handle p = null;
        atom_return atom15 = null;
        blockSet_return blockSet16 = null;
        try {
            int alt14 = 2;
            int LA14_0 = this.input.LA(1);
            if (LA14_0 == 20 || LA14_0 == 52 || LA14_0 == 57 || LA14_0 == 62 || LA14_0 == 66 || LA14_0 == 98 || LA14_0 == 100) {
                alt14 = 1;
            } else if (LA14_0 == 39) {
                alt14 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 14, 0, this.input);
                throw nvae;
            }
            switch (alt14) {
                case 1: {
                    this.pushFollow(FOLLOW_atom_in_astOperand590);
                    atom15 = this.atom();
                    --this.state._fsp;
                    p = atom15 != null ? atom15.p : null;
                    break;
                }
                case 2: {
                    this.match(this.input, 39, FOLLOW_NOT_in_astOperand603);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_blockSet_in_astOperand605);
                    blockSet16 = this.blockSet(true);
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    p = blockSet16 != null ? blockSet16.p : null;
                }
            }
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
        }
        return p;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final ATNFactory.Handle labeledElement() throws RecognitionException {
        ATNFactory.Handle p = null;
        element_return element17 = null;
        element_return element18 = null;
        try {
            int alt15 = 2;
            int LA15_0 = this.input.LA(1);
            if (LA15_0 == 10) {
                alt15 = 1;
            } else if (LA15_0 == 46) {
                alt15 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 15, 0, this.input);
                throw nvae;
            }
            switch (alt15) {
                case 1: {
                    this.match(this.input, 10, FOLLOW_ASSIGN_in_labeledElement626);
                    this.match(this.input, 2, null);
                    this.match(this.input, 28, FOLLOW_ID_in_labeledElement628);
                    this.pushFollow(FOLLOW_element_in_labeledElement630);
                    element17 = this.element();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    p = this.factory.label(element17 != null ? element17.p : null);
                    break;
                }
                case 2: {
                    this.match(this.input, 46, FOLLOW_PLUS_ASSIGN_in_labeledElement643);
                    this.match(this.input, 2, null);
                    this.match(this.input, 28, FOLLOW_ID_in_labeledElement645);
                    this.pushFollow(FOLLOW_element_in_labeledElement647);
                    element18 = this.element();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    p = this.factory.listLabel(element18 != null ? element18.p : null);
                }
            }
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
        }
        return p;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final subrule_return subrule() throws RecognitionException {
        subrule_return retval = new subrule_return();
        retval.start = this.input.LT(1);
        ATNFactory.Handle block19 = null;
        ATNFactory.Handle block20 = null;
        ATNFactory.Handle block21 = null;
        ATNFactory.Handle block22 = null;
        try {
            int alt16 = 4;
            switch (this.input.LA(1)) {
                case 89: {
                    alt16 = 1;
                    break;
                }
                case 80: {
                    alt16 = 2;
                    break;
                }
                case 90: {
                    alt16 = 3;
                    break;
                }
                case 78: {
                    alt16 = 4;
                    break;
                }
                default: {
                    NoViableAltException nvae = new NoViableAltException("", 16, 0, this.input);
                    throw nvae;
                }
            }
            switch (alt16) {
                case 1: {
                    this.match(this.input, 89, FOLLOW_OPTIONAL_in_subrule668);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_block_in_subrule670);
                    block19 = this.block((GrammarAST)retval.start);
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    retval.p = block19;
                    break;
                }
                case 2: {
                    this.match(this.input, 80, FOLLOW_CLOSURE_in_subrule682);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_block_in_subrule684);
                    block20 = this.block((GrammarAST)retval.start);
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    retval.p = block20;
                    break;
                }
                case 3: {
                    this.match(this.input, 90, FOLLOW_POSITIVE_CLOSURE_in_subrule696);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_block_in_subrule698);
                    block21 = this.block((GrammarAST)retval.start);
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    retval.p = block21;
                    break;
                }
                case 4: {
                    this.pushFollow(FOLLOW_block_in_subrule708);
                    block22 = this.block(null);
                    --this.state._fsp;
                    retval.p = block22;
                }
            }
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final blockSet_return blockSet(boolean invert) throws RecognitionException {
        blockSet_return retval = new blockSet_return();
        retval.start = this.input.LT(1);
        setElement_return setElement23 = null;
        ArrayList<GrammarAST> alts = new ArrayList<GrammarAST>();
        try {
            this.match(this.input, 98, FOLLOW_SET_in_blockSet742);
            this.match(this.input, 2, null);
            int cnt17 = 0;
            block7: while (true) {
                int alt17 = 2;
                int LA17_0 = this.input.LA(1);
                if (LA17_0 == 32 || LA17_0 == 52 || LA17_0 == 62 || LA17_0 == 66) {
                    alt17 = 1;
                }
                switch (alt17) {
                    case 1: {
                        this.pushFollow(FOLLOW_setElement_in_blockSet745);
                        setElement23 = this.setElement();
                        --this.state._fsp;
                        alts.add(setElement23 != null ? (GrammarAST)setElement23.start : null);
                        break;
                    }
                    default: {
                        if (cnt17 >= 1) break block7;
                        EarlyExitException eee = new EarlyExitException(17, this.input);
                        throw eee;
                    }
                }
                ++cnt17;
            }
            this.match(this.input, 3, null);
            retval.p = this.factory.set((GrammarAST)retval.start, alts, invert);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final setElement_return setElement() throws RecognitionException {
        setElement_return retval = new setElement_return();
        retval.start = this.input.LT(1);
        GrammarAST a = null;
        GrammarAST b = null;
        try {
            int alt18 = 6;
            switch (this.input.LA(1)) {
                case 62: {
                    int LA18_1 = this.input.LA(2);
                    if (LA18_1 == 2) {
                        alt18 = 1;
                        break;
                    }
                    if (LA18_1 == 3 || LA18_1 == 32 || LA18_1 == 52 || LA18_1 == 62 || LA18_1 == 66) {
                        alt18 = 3;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 18, 1, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 66: {
                    int LA18_2 = this.input.LA(2);
                    if (LA18_2 == 2) {
                        alt18 = 2;
                        break;
                    }
                    if (LA18_2 == 3 || LA18_2 == 32 || LA18_2 == 52 || LA18_2 == 62 || LA18_2 == 66) {
                        alt18 = 4;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 18, 2, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 52: {
                    alt18 = 5;
                    break;
                }
                case 32: {
                    alt18 = 6;
                    break;
                }
                default: {
                    NoViableAltException nvae = new NoViableAltException("", 18, 0, this.input);
                    throw nvae;
                }
            }
            switch (alt18) {
                case 1: {
                    this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_setElement766);
                    this.match(this.input, 2, null);
                    this.matchAny(this.input);
                    this.match(this.input, 3, null);
                    break;
                }
                case 2: {
                    this.match(this.input, 66, FOLLOW_TOKEN_REF_in_setElement775);
                    this.match(this.input, 2, null);
                    this.matchAny(this.input);
                    this.match(this.input, 3, null);
                    break;
                }
                case 3: {
                    this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_setElement783);
                    break;
                }
                case 4: {
                    this.match(this.input, 66, FOLLOW_TOKEN_REF_in_setElement788);
                    break;
                }
                case 5: {
                    this.match(this.input, 52, FOLLOW_RANGE_in_setElement794);
                    this.match(this.input, 2, null);
                    a = (GrammarAST)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_setElement798);
                    b = (GrammarAST)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_setElement802);
                    this.match(this.input, 3, null);
                    break;
                }
                case 6: {
                    this.match(this.input, 32, FOLLOW_LEXER_CHAR_SET_in_setElement813);
                }
            }
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final atom_return atom() throws RecognitionException {
        atom_return retval = new atom_return();
        retval.start = this.input.LT(1);
        ATNFactory.Handle range24 = null;
        terminal_return terminal25 = null;
        ATNFactory.Handle ruleref26 = null;
        blockSet_return blockSet27 = null;
        terminal_return terminal28 = null;
        ATNFactory.Handle ruleref29 = null;
        try {
            int alt19 = 8;
            switch (this.input.LA(1)) {
                case 52: {
                    alt19 = 1;
                    break;
                }
                case 20: {
                    int LA19_2 = this.input.LA(2);
                    if (LA19_2 == 2) {
                        int LA19_7 = this.input.LA(3);
                        if (LA19_7 == 28) {
                            int LA19_10 = this.input.LA(4);
                            if (LA19_10 == 62 || LA19_10 == 66) {
                                alt19 = 2;
                                break;
                            }
                            if (LA19_10 == 57) {
                                alt19 = 3;
                                break;
                            }
                            int nvaeMark = this.input.mark();
                            try {
                                for (int nvaeConsume = 0; nvaeConsume < 3; ++nvaeConsume) {
                                    this.input.consume();
                                }
                                NoViableAltException nvae = new NoViableAltException("", 19, 10, this.input);
                                throw nvae;
                            }
                            catch (Throwable throwable) {
                                this.input.rewind(nvaeMark);
                                throw throwable;
                            }
                        }
                        int nvaeMark = this.input.mark();
                        try {
                            for (int nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                                this.input.consume();
                            }
                            NoViableAltException nvae = new NoViableAltException("", 19, 7, this.input);
                            throw nvae;
                        }
                        catch (Throwable throwable) {
                            this.input.rewind(nvaeMark);
                            throw throwable;
                        }
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 19, 2, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 100: {
                    int LA19_3 = this.input.LA(2);
                    if (LA19_3 == 2) {
                        alt19 = 4;
                        break;
                    }
                    if (LA19_3 == -1 || LA19_3 >= 3 && LA19_3 <= 4 || LA19_3 == 10 || LA19_3 == 20 || LA19_3 == 32 || LA19_3 == 39 || LA19_3 == 46 || LA19_3 == 52 || LA19_3 == 57 || LA19_3 == 59 || LA19_3 == 62 || LA19_3 == 66 || LA19_3 == 78 || LA19_3 == 80 || LA19_3 >= 89 && LA19_3 <= 90 || LA19_3 == 98 || LA19_3 == 100) {
                        alt19 = 5;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 19, 3, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 98: {
                    alt19 = 6;
                    break;
                }
                case 62: 
                case 66: {
                    alt19 = 7;
                    break;
                }
                case 57: {
                    alt19 = 8;
                    break;
                }
                default: {
                    NoViableAltException nvae = new NoViableAltException("", 19, 0, this.input);
                    throw nvae;
                }
            }
            switch (alt19) {
                case 1: {
                    this.pushFollow(FOLLOW_range_in_atom828);
                    range24 = this.range();
                    --this.state._fsp;
                    retval.p = range24;
                    break;
                }
                case 2: {
                    this.match(this.input, 20, FOLLOW_DOT_in_atom840);
                    this.match(this.input, 2, null);
                    this.match(this.input, 28, FOLLOW_ID_in_atom842);
                    this.pushFollow(FOLLOW_terminal_in_atom844);
                    terminal25 = this.terminal();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    retval.p = terminal25 != null ? terminal25.p : null;
                    break;
                }
                case 3: {
                    this.match(this.input, 20, FOLLOW_DOT_in_atom854);
                    this.match(this.input, 2, null);
                    this.match(this.input, 28, FOLLOW_ID_in_atom856);
                    this.pushFollow(FOLLOW_ruleref_in_atom858);
                    ruleref26 = this.ruleref();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    retval.p = ruleref26;
                    break;
                }
                case 4: {
                    this.match(this.input, 100, FOLLOW_WILDCARD_in_atom871);
                    this.match(this.input, 2, null);
                    this.matchAny(this.input);
                    this.match(this.input, 3, null);
                    retval.p = this.factory.wildcard((GrammarAST)retval.start);
                    break;
                }
                case 5: {
                    this.match(this.input, 100, FOLLOW_WILDCARD_in_atom886);
                    retval.p = this.factory.wildcard((GrammarAST)retval.start);
                    break;
                }
                case 6: {
                    this.pushFollow(FOLLOW_blockSet_in_atom899);
                    blockSet27 = this.blockSet(false);
                    --this.state._fsp;
                    retval.p = blockSet27 != null ? blockSet27.p : null;
                    break;
                }
                case 7: {
                    this.pushFollow(FOLLOW_terminal_in_atom914);
                    terminal28 = this.terminal();
                    --this.state._fsp;
                    retval.p = terminal28 != null ? terminal28.p : null;
                    break;
                }
                case 8: {
                    this.pushFollow(FOLLOW_ruleref_in_atom929);
                    ruleref29 = this.ruleref();
                    --this.state._fsp;
                    retval.p = ruleref29;
                }
            }
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final ATNFactory.Handle ruleref() throws RecognitionException {
        ATNFactory.Handle p = null;
        GrammarAST RULE_REF30 = null;
        GrammarAST RULE_REF31 = null;
        GrammarAST RULE_REF32 = null;
        try {
            int alt23 = 3;
            int LA23_0 = this.input.LA(1);
            if (LA23_0 != 57) {
                NoViableAltException nvae = new NoViableAltException("", 23, 0, this.input);
                throw nvae;
            }
            int LA23_1 = this.input.LA(2);
            if (LA23_1 == 2) {
                switch (this.input.LA(3)) {
                    case 8: {
                        int LA23_4 = this.input.LA(4);
                        if (LA23_4 == 82) {
                            alt23 = 1;
                            break;
                        }
                        if (LA23_4 == 3) {
                            alt23 = 2;
                            break;
                        }
                        int nvaeMark = this.input.mark();
                        try {
                            int nvaeConsume = 0;
                            while (true) {
                                if (nvaeConsume >= 3) {
                                    NoViableAltException nvae = new NoViableAltException("", 23, 4, this.input);
                                    throw nvae;
                                }
                                this.input.consume();
                                ++nvaeConsume;
                            }
                        }
                        catch (Throwable throwable) {
                            this.input.rewind(nvaeMark);
                            throw throwable;
                        }
                    }
                    case 82: {
                        alt23 = 1;
                        break;
                    }
                    case 3: {
                        alt23 = 2;
                        break;
                    }
                    default: {
                        int nvaeMark = this.input.mark();
                        try {
                            int nvaeConsume = 0;
                            while (true) {
                                if (nvaeConsume >= 2) {
                                    NoViableAltException nvae = new NoViableAltException("", 23, 2, this.input);
                                    throw nvae;
                                }
                                this.input.consume();
                                ++nvaeConsume;
                            }
                        }
                        catch (Throwable throwable) {
                            this.input.rewind(nvaeMark);
                            throw throwable;
                        }
                    }
                }
            } else if (LA23_1 == -1 || LA23_1 >= 3 && LA23_1 <= 4 || LA23_1 == 10 || LA23_1 == 20 || LA23_1 == 32 || LA23_1 == 39 || LA23_1 == 46 || LA23_1 == 52 || LA23_1 == 57 || LA23_1 == 59 || LA23_1 == 62 || LA23_1 == 66 || LA23_1 == 78 || LA23_1 == 80 || LA23_1 >= 89 && LA23_1 <= 90 || LA23_1 == 98 || LA23_1 == 100) {
                alt23 = 3;
            } else {
                int nvaeMark = this.input.mark();
                try {
                    this.input.consume();
                    NoViableAltException nvae = new NoViableAltException("", 23, 1, this.input);
                    throw nvae;
                }
                catch (Throwable throwable) {
                    this.input.rewind(nvaeMark);
                    throw throwable;
                }
            }
            switch (alt23) {
                case 1: {
                    RULE_REF30 = (GrammarAST)this.match(this.input, 57, FOLLOW_RULE_REF_in_ruleref957);
                    this.match(this.input, 2, null);
                    int alt20 = 2;
                    int LA20_0 = this.input.LA(1);
                    if (LA20_0 == 8) {
                        alt20 = 1;
                    }
                    switch (alt20) {
                        case 1: {
                            this.match(this.input, 8, FOLLOW_ARG_ACTION_in_ruleref959);
                            break;
                        }
                    }
                    this.match(this.input, 82, FOLLOW_ELEMENT_OPTIONS_in_ruleref963);
                    if (this.input.LA(1) == 2) {
                        this.match(this.input, 2, null);
                        block31: while (true) {
                            int alt21 = 2;
                            int LA21_0 = this.input.LA(1);
                            if (LA21_0 >= 4 && LA21_0 <= 100) {
                                alt21 = 1;
                            } else if (LA21_0 == 3) {
                                alt21 = 2;
                            }
                            switch (alt21) {
                                case 1: {
                                    this.matchAny(this.input);
                                    continue block31;
                                }
                            }
                            break;
                        }
                        this.match(this.input, 3, null);
                    }
                    this.match(this.input, 3, null);
                    return this.factory.ruleRef(RULE_REF30);
                }
                case 2: {
                    RULE_REF31 = (GrammarAST)this.match(this.input, 57, FOLLOW_RULE_REF_in_ruleref980);
                    if (this.input.LA(1) != 2) return this.factory.ruleRef(RULE_REF31);
                    this.match(this.input, 2, null);
                    int alt22 = 2;
                    int LA22_0 = this.input.LA(1);
                    if (LA22_0 == 8) {
                        alt22 = 1;
                    }
                    switch (alt22) {
                        case 1: {
                            this.match(this.input, 8, FOLLOW_ARG_ACTION_in_ruleref982);
                            break;
                        }
                    }
                    this.match(this.input, 3, null);
                    return this.factory.ruleRef(RULE_REF31);
                }
                case 3: {
                    RULE_REF32 = (GrammarAST)this.match(this.input, 57, FOLLOW_RULE_REF_in_ruleref1001);
                    return this.factory.ruleRef(RULE_REF32);
                }
            }
            return p;
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            return p;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final ATNFactory.Handle range() throws RecognitionException {
        ATNFactory.Handle p = null;
        GrammarAST a = null;
        GrammarAST b = null;
        try {
            this.match(this.input, 52, FOLLOW_RANGE_in_range1035);
            this.match(this.input, 2, null);
            a = (GrammarAST)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_range1039);
            b = (GrammarAST)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_range1043);
            this.match(this.input, 3, null);
            p = this.factory.range(a, b);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
        }
        return p;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final terminal_return terminal() throws RecognitionException {
        terminal_return retval = new terminal_return();
        retval.start = this.input.LT(1);
        try {
            int alt24;
            block36: {
                int LA24_2;
                block38: {
                    int LA24_5;
                    block39: {
                        int LA24_0;
                        block37: {
                            alt24 = 5;
                            LA24_0 = this.input.LA(1);
                            if (LA24_0 != 62) break block37;
                            int LA24_1 = this.input.LA(2);
                            if (LA24_1 == 2) {
                                alt24 = 1;
                                break block36;
                            } else if (LA24_1 == -1 || LA24_1 >= 3 && LA24_1 <= 4 || LA24_1 == 10 || LA24_1 == 20 || LA24_1 == 32 || LA24_1 == 39 || LA24_1 == 46 || LA24_1 == 52 || LA24_1 == 57 || LA24_1 == 59 || LA24_1 == 62 || LA24_1 == 66 || LA24_1 == 78 || LA24_1 == 80 || LA24_1 >= 89 && LA24_1 <= 90 || LA24_1 == 98 || LA24_1 == 100) {
                                alt24 = 2;
                                break block36;
                            } else {
                                int nvaeMark = this.input.mark();
                                try {
                                    this.input.consume();
                                    NoViableAltException nvae = new NoViableAltException("", 24, 1, this.input);
                                    throw nvae;
                                }
                                catch (Throwable throwable) {
                                    this.input.rewind(nvaeMark);
                                    throw throwable;
                                }
                            }
                        }
                        if (LA24_0 != 66) {
                            NoViableAltException nvae = new NoViableAltException("", 24, 0, this.input);
                            throw nvae;
                        }
                        LA24_2 = this.input.LA(2);
                        if (LA24_2 != 2) break block38;
                        LA24_5 = this.input.LA(3);
                        if (LA24_5 != 8) break block39;
                        int LA24_7 = this.input.LA(4);
                        if (LA24_7 >= 4 && LA24_7 <= 100) {
                            alt24 = 3;
                            break block36;
                        } else if (LA24_7 >= 2 && LA24_7 <= 3) {
                            alt24 = 4;
                            break block36;
                        } else {
                            int nvaeMark = this.input.mark();
                            try {
                                int nvaeConsume = 0;
                                while (true) {
                                    if (nvaeConsume >= 3) {
                                        NoViableAltException nvae = new NoViableAltException("", 24, 7, this.input);
                                        throw nvae;
                                    }
                                    this.input.consume();
                                    ++nvaeConsume;
                                }
                            }
                            catch (Throwable throwable) {
                                this.input.rewind(nvaeMark);
                                throw throwable;
                            }
                        }
                    }
                    if (LA24_5 >= 4 && LA24_5 <= 7 || LA24_5 >= 9 && LA24_5 <= 100) {
                        alt24 = 4;
                        break block36;
                    } else {
                        int nvaeMark = this.input.mark();
                        try {
                            int nvaeConsume = 0;
                            while (true) {
                                if (nvaeConsume >= 2) {
                                    NoViableAltException nvae = new NoViableAltException("", 24, 5, this.input);
                                    throw nvae;
                                }
                                this.input.consume();
                                ++nvaeConsume;
                            }
                        }
                        catch (Throwable throwable) {
                            this.input.rewind(nvaeMark);
                            throw throwable;
                        }
                    }
                }
                if (LA24_2 == -1 || LA24_2 >= 3 && LA24_2 <= 4 || LA24_2 == 10 || LA24_2 == 20 || LA24_2 == 32 || LA24_2 == 39 || LA24_2 == 46 || LA24_2 == 52 || LA24_2 == 57 || LA24_2 == 59 || LA24_2 == 62 || LA24_2 == 66 || LA24_2 == 78 || LA24_2 == 80 || LA24_2 >= 89 && LA24_2 <= 90 || LA24_2 == 98 || LA24_2 == 100) {
                    alt24 = 5;
                } else {
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 24, 2, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
            }
            switch (alt24) {
                case 1: {
                    this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_terminal1069);
                    this.match(this.input, 2, null);
                    this.matchAny(this.input);
                    this.match(this.input, 3, null);
                    retval.p = this.factory.stringLiteral((TerminalAST)((GrammarAST)retval.start));
                    return retval;
                }
                case 2: {
                    this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_terminal1084);
                    retval.p = this.factory.stringLiteral((TerminalAST)((GrammarAST)retval.start));
                    return retval;
                }
                case 3: {
                    this.match(this.input, 66, FOLLOW_TOKEN_REF_in_terminal1098);
                    this.match(this.input, 2, null);
                    this.match(this.input, 8, FOLLOW_ARG_ACTION_in_terminal1100);
                    this.matchAny(this.input);
                    this.match(this.input, 3, null);
                    retval.p = this.factory.tokenRef((TerminalAST)((GrammarAST)retval.start));
                    return retval;
                }
                case 4: {
                    this.match(this.input, 66, FOLLOW_TOKEN_REF_in_terminal1114);
                    this.match(this.input, 2, null);
                    this.matchAny(this.input);
                    this.match(this.input, 3, null);
                    retval.p = this.factory.tokenRef((TerminalAST)((GrammarAST)retval.start));
                    return retval;
                }
                case 5: {
                    this.match(this.input, 66, FOLLOW_TOKEN_REF_in_terminal1130);
                    retval.p = this.factory.tokenRef((TerminalAST)((GrammarAST)retval.start));
                    return retval;
                }
            }
            return retval;
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            return retval;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void elementOptions() throws RecognitionException {
        try {
            this.match(this.input, 82, FOLLOW_ELEMENT_OPTIONS_in_elementOptions1151);
            if (this.input.LA(1) == 2) {
                this.match(this.input, 2, null);
                block7: while (true) {
                    int alt25 = 2;
                    int LA25_0 = this.input.LA(1);
                    if (LA25_0 == 10 || LA25_0 == 28) {
                        alt25 = 1;
                    }
                    switch (alt25) {
                        case 1: {
                            this.pushFollow(FOLLOW_elementOption_in_elementOptions1153);
                            this.elementOption();
                            --this.state._fsp;
                            continue block7;
                        }
                    }
                    break;
                }
                this.match(this.input, 3, null);
            }
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void elementOption() throws RecognitionException {
        try {
            int alt26;
            block33: {
                alt26 = 5;
                int LA26_0 = this.input.LA(1);
                if (LA26_0 == 28) {
                    alt26 = 1;
                } else {
                    if (LA26_0 != 10) {
                        NoViableAltException nvae = new NoViableAltException("", 26, 0, this.input);
                        throw nvae;
                    }
                    int LA26_2 = this.input.LA(2);
                    if (LA26_2 == 2) {
                        int LA26_3 = this.input.LA(3);
                        if (LA26_3 == 28) {
                            switch (this.input.LA(4)) {
                                case 28: {
                                    alt26 = 2;
                                    break;
                                }
                                case 62: {
                                    alt26 = 3;
                                    break;
                                }
                                case 4: {
                                    alt26 = 4;
                                    break;
                                }
                                case 30: {
                                    alt26 = 5;
                                    break;
                                }
                                default: {
                                    int nvaeMark = this.input.mark();
                                    try {
                                        int nvaeConsume = 0;
                                        while (true) {
                                            if (nvaeConsume >= 3) {
                                                NoViableAltException nvae = new NoViableAltException("", 26, 4, this.input);
                                                throw nvae;
                                            }
                                            this.input.consume();
                                            ++nvaeConsume;
                                        }
                                    }
                                    catch (Throwable throwable) {
                                        this.input.rewind(nvaeMark);
                                        throw throwable;
                                    }
                                }
                            }
                            break block33;
                        } else {
                            int nvaeMark = this.input.mark();
                            try {
                                int nvaeConsume = 0;
                                while (true) {
                                    if (nvaeConsume >= 2) {
                                        NoViableAltException nvae = new NoViableAltException("", 26, 3, this.input);
                                        throw nvae;
                                    }
                                    this.input.consume();
                                    ++nvaeConsume;
                                }
                            }
                            catch (Throwable throwable) {
                                this.input.rewind(nvaeMark);
                                throw throwable;
                            }
                        }
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 26, 2, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
            }
            switch (alt26) {
                case 1: {
                    this.match(this.input, 28, FOLLOW_ID_in_elementOption1166);
                    return;
                }
                case 2: {
                    this.match(this.input, 10, FOLLOW_ASSIGN_in_elementOption1172);
                    this.match(this.input, 2, null);
                    this.match(this.input, 28, FOLLOW_ID_in_elementOption1174);
                    this.match(this.input, 28, FOLLOW_ID_in_elementOption1176);
                    this.match(this.input, 3, null);
                    return;
                }
                case 3: {
                    this.match(this.input, 10, FOLLOW_ASSIGN_in_elementOption1183);
                    this.match(this.input, 2, null);
                    this.match(this.input, 28, FOLLOW_ID_in_elementOption1185);
                    this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_elementOption1187);
                    this.match(this.input, 3, null);
                    return;
                }
                case 4: {
                    this.match(this.input, 10, FOLLOW_ASSIGN_in_elementOption1194);
                    this.match(this.input, 2, null);
                    this.match(this.input, 28, FOLLOW_ID_in_elementOption1196);
                    this.match(this.input, 4, FOLLOW_ACTION_in_elementOption1198);
                    this.match(this.input, 3, null);
                    return;
                }
                case 5: {
                    this.match(this.input, 10, FOLLOW_ASSIGN_in_elementOption1205);
                    this.match(this.input, 2, null);
                    this.match(this.input, 28, FOLLOW_ID_in_elementOption1207);
                    this.match(this.input, 30, FOLLOW_INT_in_elementOption1209);
                    this.match(this.input, 3, null);
                    return;
                }
            }
            return;
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            return;
        }
    }

    static {
        int numStates = DFA10_transitionS.length;
        DFA10_transition = new short[numStates][];
        for (int i = 0; i < numStates; ++i) {
            ATNBuilder.DFA10_transition[i] = DFA.unpackEncodedString(DFA10_transitionS[i]);
        }
        FOLLOW_block_in_dummy63 = new BitSet(new long[]{2L});
        FOLLOW_BLOCK_in_ruleBlock89 = new BitSet(new long[]{4L});
        FOLLOW_OPTIONS_in_ruleBlock105 = new BitSet(new long[]{4L});
        FOLLOW_alternative_in_ruleBlock131 = new BitSet(new long[]{8L, 0x800400L});
        FOLLOW_BLOCK_in_block209 = new BitSet(new long[]{4L});
        FOLLOW_OPTIONS_in_block213 = new BitSet(new long[]{4L});
        FOLLOW_alternative_in_block224 = new BitSet(new long[]{8L, 0x800400L});
        FOLLOW_LEXER_ALT_ACTION_in_alternative263 = new BitSet(new long[]{4L});
        FOLLOW_alternative_in_alternative267 = new BitSet(new long[]{0x10000000L, 0x400000L});
        FOLLOW_lexerCommands_in_alternative269 = new BitSet(new long[]{8L});
        FOLLOW_ALT_in_alternative289 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_alternative291 = new BitSet(new long[]{0L, 524288L});
        FOLLOW_EPSILON_in_alternative294 = new BitSet(new long[]{8L});
        FOLLOW_ALT_in_alternative314 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_alternative316 = new BitSet(new long[]{5336836481230046224L, 86000091140L});
        FOLLOW_element_in_alternative322 = new BitSet(new long[]{5336836481230046232L, 86000091140L});
        FOLLOW_lexerCommand_in_lexerCommands360 = new BitSet(new long[]{0x10000002L, 0x400000L});
        FOLLOW_LEXER_ACTION_CALL_in_lexerCommand393 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_lexerCommand395 = new BitSet(new long[]{0x50000000L});
        FOLLOW_lexerCommandExpr_in_lexerCommand397 = new BitSet(new long[]{8L});
        FOLLOW_ID_in_lexerCommand413 = new BitSet(new long[]{2L});
        FOLLOW_labeledElement_in_element454 = new BitSet(new long[]{2L});
        FOLLOW_atom_in_element464 = new BitSet(new long[]{2L});
        FOLLOW_subrule_in_element476 = new BitSet(new long[]{2L});
        FOLLOW_ACTION_in_element490 = new BitSet(new long[]{2L});
        FOLLOW_SEMPRED_in_element504 = new BitSet(new long[]{2L});
        FOLLOW_ACTION_in_element519 = new BitSet(new long[]{4L});
        FOLLOW_SEMPRED_in_element536 = new BitSet(new long[]{4L});
        FOLLOW_NOT_in_element553 = new BitSet(new long[]{4L});
        FOLLOW_blockSet_in_element557 = new BitSet(new long[]{8L});
        FOLLOW_LEXER_CHAR_SET_in_element570 = new BitSet(new long[]{2L});
        FOLLOW_atom_in_astOperand590 = new BitSet(new long[]{2L});
        FOLLOW_NOT_in_astOperand603 = new BitSet(new long[]{4L});
        FOLLOW_blockSet_in_astOperand605 = new BitSet(new long[]{8L});
        FOLLOW_ASSIGN_in_labeledElement626 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_labeledElement628 = new BitSet(new long[]{5336836481230046224L, 86000091140L});
        FOLLOW_element_in_labeledElement630 = new BitSet(new long[]{8L});
        FOLLOW_PLUS_ASSIGN_in_labeledElement643 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_labeledElement645 = new BitSet(new long[]{5336836481230046224L, 86000091140L});
        FOLLOW_element_in_labeledElement647 = new BitSet(new long[]{8L});
        FOLLOW_OPTIONAL_in_subrule668 = new BitSet(new long[]{4L});
        FOLLOW_block_in_subrule670 = new BitSet(new long[]{8L});
        FOLLOW_CLOSURE_in_subrule682 = new BitSet(new long[]{4L});
        FOLLOW_block_in_subrule684 = new BitSet(new long[]{8L});
        FOLLOW_POSITIVE_CLOSURE_in_subrule696 = new BitSet(new long[]{4L});
        FOLLOW_block_in_subrule698 = new BitSet(new long[]{8L});
        FOLLOW_block_in_subrule708 = new BitSet(new long[]{2L});
        FOLLOW_SET_in_blockSet742 = new BitSet(new long[]{4L});
        FOLLOW_setElement_in_blockSet745 = new BitSet(new long[]{4616189622349725704L, 4L});
        FOLLOW_STRING_LITERAL_in_setElement766 = new BitSet(new long[]{4L});
        FOLLOW_TOKEN_REF_in_setElement775 = new BitSet(new long[]{4L});
        FOLLOW_STRING_LITERAL_in_setElement783 = new BitSet(new long[]{2L});
        FOLLOW_TOKEN_REF_in_setElement788 = new BitSet(new long[]{2L});
        FOLLOW_RANGE_in_setElement794 = new BitSet(new long[]{4L});
        FOLLOW_STRING_LITERAL_in_setElement798 = new BitSet(new long[]{0x4000000000000000L});
        FOLLOW_STRING_LITERAL_in_setElement802 = new BitSet(new long[]{8L});
        FOLLOW_LEXER_CHAR_SET_in_setElement813 = new BitSet(new long[]{2L});
        FOLLOW_range_in_atom828 = new BitSet(new long[]{2L});
        FOLLOW_DOT_in_atom840 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_atom842 = new BitSet(new long[]{0x4000000000000000L, 4L});
        FOLLOW_terminal_in_atom844 = new BitSet(new long[]{8L});
        FOLLOW_DOT_in_atom854 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_atom856 = new BitSet(new long[]{0x200000000000000L});
        FOLLOW_ruleref_in_atom858 = new BitSet(new long[]{8L});
        FOLLOW_WILDCARD_in_atom871 = new BitSet(new long[]{4L});
        FOLLOW_WILDCARD_in_atom886 = new BitSet(new long[]{2L});
        FOLLOW_blockSet_in_atom899 = new BitSet(new long[]{2L});
        FOLLOW_terminal_in_atom914 = new BitSet(new long[]{2L});
        FOLLOW_ruleref_in_atom929 = new BitSet(new long[]{2L});
        FOLLOW_RULE_REF_in_ruleref957 = new BitSet(new long[]{4L});
        FOLLOW_ARG_ACTION_in_ruleref959 = new BitSet(new long[]{0L, 262144L});
        FOLLOW_ELEMENT_OPTIONS_in_ruleref963 = new BitSet(new long[]{4L});
        FOLLOW_RULE_REF_in_ruleref980 = new BitSet(new long[]{4L});
        FOLLOW_ARG_ACTION_in_ruleref982 = new BitSet(new long[]{8L});
        FOLLOW_RULE_REF_in_ruleref1001 = new BitSet(new long[]{2L});
        FOLLOW_RANGE_in_range1035 = new BitSet(new long[]{4L});
        FOLLOW_STRING_LITERAL_in_range1039 = new BitSet(new long[]{0x4000000000000000L});
        FOLLOW_STRING_LITERAL_in_range1043 = new BitSet(new long[]{8L});
        FOLLOW_STRING_LITERAL_in_terminal1069 = new BitSet(new long[]{4L});
        FOLLOW_STRING_LITERAL_in_terminal1084 = new BitSet(new long[]{2L});
        FOLLOW_TOKEN_REF_in_terminal1098 = new BitSet(new long[]{4L});
        FOLLOW_ARG_ACTION_in_terminal1100 = new BitSet(new long[]{-16L, 0x1FFFFFFFFFL});
        FOLLOW_TOKEN_REF_in_terminal1114 = new BitSet(new long[]{4L});
        FOLLOW_TOKEN_REF_in_terminal1130 = new BitSet(new long[]{2L});
        FOLLOW_ELEMENT_OPTIONS_in_elementOptions1151 = new BitSet(new long[]{4L});
        FOLLOW_elementOption_in_elementOptions1153 = new BitSet(new long[]{268436488L});
        FOLLOW_ID_in_elementOption1166 = new BitSet(new long[]{2L});
        FOLLOW_ASSIGN_in_elementOption1172 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_elementOption1174 = new BitSet(new long[]{0x10000000L});
        FOLLOW_ID_in_elementOption1176 = new BitSet(new long[]{8L});
        FOLLOW_ASSIGN_in_elementOption1183 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_elementOption1185 = new BitSet(new long[]{0x4000000000000000L});
        FOLLOW_STRING_LITERAL_in_elementOption1187 = new BitSet(new long[]{8L});
        FOLLOW_ASSIGN_in_elementOption1194 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_elementOption1196 = new BitSet(new long[]{16L});
        FOLLOW_ACTION_in_elementOption1198 = new BitSet(new long[]{8L});
        FOLLOW_ASSIGN_in_elementOption1205 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_elementOption1207 = new BitSet(new long[]{0x40000000L});
        FOLLOW_INT_in_elementOption1209 = new BitSet(new long[]{8L});
    }

    protected class DFA10
    extends DFA {
        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = DFA10_eot;
            this.eof = DFA10_eof;
            this.min = DFA10_min;
            this.max = DFA10_max;
            this.accept = DFA10_accept;
            this.special = DFA10_special;
            this.transition = DFA10_transition;
        }

        @Override
        public String getDescription() {
            return "59:1: alternative returns [ATNFactory.Handle p] : ( ^( LEXER_ALT_ACTION a= alternative lexerCommands ) | ^( ALT ( elementOptions )? EPSILON ) | ^( ALT ( elementOptions )? (e= element )+ ) );";
        }
    }

    public static class terminal_return
    extends TreeRuleReturnScope {
        public ATNFactory.Handle p;
    }

    public static class atom_return
    extends TreeRuleReturnScope {
        public ATNFactory.Handle p;
    }

    public static class setElement_return
    extends TreeRuleReturnScope {
    }

    public static class blockSet_return
    extends TreeRuleReturnScope {
        public ATNFactory.Handle p;
    }

    public static class subrule_return
    extends TreeRuleReturnScope {
        public ATNFactory.Handle p;
    }

    public static class element_return
    extends TreeRuleReturnScope {
        public ATNFactory.Handle p;
    }

    public static class lexerCommandExpr_return
    extends TreeRuleReturnScope {
    }
}

