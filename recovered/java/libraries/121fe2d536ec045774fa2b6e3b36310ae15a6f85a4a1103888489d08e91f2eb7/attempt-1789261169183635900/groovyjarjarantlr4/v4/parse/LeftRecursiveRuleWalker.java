/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.parse;

import groovyjarjarantlr4.runtime.BaseRecognizer;
import groovyjarjarantlr4.runtime.BitSet;
import groovyjarjarantlr4.runtime.DFA;
import groovyjarjarantlr4.runtime.EarlyExitException;
import groovyjarjarantlr4.runtime.FailedPredicateException;
import groovyjarjarantlr4.runtime.MismatchedSetException;
import groovyjarjarantlr4.runtime.NoViableAltException;
import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.RecognizerSharedState;
import groovyjarjarantlr4.runtime.tree.CommonTree;
import groovyjarjarantlr4.runtime.tree.TreeNodeStream;
import groovyjarjarantlr4.runtime.tree.TreeParser;
import groovyjarjarantlr4.runtime.tree.TreeRuleReturnScope;
import groovyjarjarantlr4.v4.tool.ast.AltAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;

public class LeftRecursiveRuleWalker
extends TreeParser {
    public static final String[] tokenNames;
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
    private String ruleName;
    private int currentOuterAltNumber;
    public int numAlts;
    protected DFA11 dfa11 = new DFA11(this);
    protected DFA14 dfa14 = new DFA14(this);
    static final String DFA11_eotS = "X\uffff";
    static final String DFA11_eofS = "X\uffff";
    static final String DFA11_minS = "\u0001\u0004\u0003\u0002\u0001\uffff\u0002\u001c\u0002\u0002\u0001\u0003\u0001\uffff\u0002\u0004\u0002R\u0004\u0002\u0004\u0003\u0002\u0002\u0002\u0003\u0001\u0002\u0002\u0003\u0001\u0002\u0001\u0003\u0002R\u0001\u001c\u0001\u0003\u0001\u001c\u0001\u0003\u0002\u0002\u0002\u0004\u000b\u0003\u0001\u0002\u0002\u0003\u0001\u0002\t\u0003\u0001\u001c\u0001\u0003\u0001\u001c\u0001\u0003\u0002\u0004\u0010\u0003";
    static final String DFA11_maxS = "\u0001d\u0002\u0002\u0001d\u0001\uffff\u0002\u001c\u0003d\u0001\uffff\u0002d\u0002R\u0002\u0003\u0002\u0002\u0002d\u0002\u001c\u0003d\u0001\u001c\u0001\u0002\u0001\u0003\u0001\u001c\u0001\u0002\u0001\u0003\u0002R\u0001\u001c\u0001d\u0001\u001c\u0001d\u0002\u0002\u0002>\u0002\u001c\b\u0003\u0001\u001c\u0001\u0002\u0001\u0003\u0001\u001c\u0001\u0002\u0001\u0003\t\u001c\u0001d\u0001\u001c\u0001d\u0002>\b\u0003\b\u001c";
    static final String DFA11_acceptS = "\u0004\uffff\u0001\u0001\u0005\uffff\u0001\u0002M\uffff";
    static final String DFA11_specialS = "X\uffff}>";
    static final String[] DFA11_transitionS;
    static final short[] DFA11_eot;
    static final short[] DFA11_eof;
    static final char[] DFA11_min;
    static final char[] DFA11_max;
    static final short[] DFA11_accept;
    static final short[] DFA11_special;
    static final short[][] DFA11_transition;
    static final String DFA14_eotS = "X\uffff";
    static final String DFA14_eofS = "X\uffff";
    static final String DFA14_minS = "\u0001\u0004\u0003\u0002\u0001\uffff\u0002\u001c\u0002\u0002\u0001\u0003\u0001\uffff\u0002\u0004\u0002R\u0004\u0002\u0004\u0003\u0002\u0002\u0002\u0003\u0001\u0002\u0002\u0003\u0001\u0002\u0001\u0003\u0002R\u0001\u001c\u0001\u0003\u0001\u001c\u0001\u0003\u0002\u0002\u0002\u0004\u000b\u0003\u0001\u0002\u0002\u0003\u0001\u0002\t\u0003\u0001\u001c\u0001\u0003\u0001\u001c\u0001\u0003\u0002\u0004\u0010\u0003";
    static final String DFA14_maxS = "\u0001d\u0002\u0002\u0001d\u0001\uffff\u0002\u001c\u0003d\u0001\uffff\u0002d\u0002R\u0002\u0003\u0002\u0002\u0002d\u0002\u001c\u0003d\u0001\u001c\u0001\u0002\u0001\u0003\u0001\u001c\u0001\u0002\u0001\u0003\u0002R\u0001\u001c\u0001d\u0001\u001c\u0001d\u0002\u0002\u0002>\u0002\u001c\b\u0003\u0001\u001c\u0001\u0002\u0001\u0003\u0001\u001c\u0001\u0002\u0001\u0003\t\u001c\u0001d\u0001\u001c\u0001d\u0002>\b\u0003\b\u001c";
    static final String DFA14_acceptS = "\u0004\uffff\u0001\u0001\u0005\uffff\u0001\u0002M\uffff";
    static final String DFA14_specialS = "X\uffff}>";
    static final String[] DFA14_transitionS;
    static final short[] DFA14_eot;
    static final short[] DFA14_eof;
    static final char[] DFA14_min;
    static final char[] DFA14_max;
    static final short[] DFA14_accept;
    static final short[] DFA14_special;
    static final short[][] DFA14_transition;
    public static final BitSet FOLLOW_RULE_in_rec_rule72;
    public static final BitSet FOLLOW_RULE_REF_in_rec_rule76;
    public static final BitSet FOLLOW_ruleModifier_in_rec_rule83;
    public static final BitSet FOLLOW_RETURNS_in_rec_rule92;
    public static final BitSet FOLLOW_ARG_ACTION_in_rec_rule96;
    public static final BitSet FOLLOW_LOCALS_in_rec_rule115;
    public static final BitSet FOLLOW_ARG_ACTION_in_rec_rule117;
    public static final BitSet FOLLOW_OPTIONS_in_rec_rule135;
    public static final BitSet FOLLOW_AT_in_rec_rule152;
    public static final BitSet FOLLOW_ID_in_rec_rule154;
    public static final BitSet FOLLOW_ACTION_in_rec_rule156;
    public static final BitSet FOLLOW_ruleBlock_in_rec_rule172;
    public static final BitSet FOLLOW_exceptionGroup_in_rec_rule179;
    public static final BitSet FOLLOW_exceptionHandler_in_exceptionGroup197;
    public static final BitSet FOLLOW_finallyClause_in_exceptionGroup200;
    public static final BitSet FOLLOW_CATCH_in_exceptionHandler216;
    public static final BitSet FOLLOW_ARG_ACTION_in_exceptionHandler218;
    public static final BitSet FOLLOW_ACTION_in_exceptionHandler220;
    public static final BitSet FOLLOW_FINALLY_in_finallyClause233;
    public static final BitSet FOLLOW_ACTION_in_finallyClause235;
    public static final BitSet FOLLOW_BLOCK_in_ruleBlock290;
    public static final BitSet FOLLOW_outerAlternative_in_ruleBlock303;
    public static final BitSet FOLLOW_binary_in_outerAlternative362;
    public static final BitSet FOLLOW_prefix_in_outerAlternative418;
    public static final BitSet FOLLOW_suffix_in_outerAlternative474;
    public static final BitSet FOLLOW_nonLeftRecur_in_outerAlternative515;
    public static final BitSet FOLLOW_ALT_in_binary541;
    public static final BitSet FOLLOW_elementOptions_in_binary543;
    public static final BitSet FOLLOW_recurse_in_binary546;
    public static final BitSet FOLLOW_element_in_binary548;
    public static final BitSet FOLLOW_recurse_in_binary551;
    public static final BitSet FOLLOW_epsilonElement_in_binary553;
    public static final BitSet FOLLOW_ALT_in_prefix579;
    public static final BitSet FOLLOW_elementOptions_in_prefix581;
    public static final BitSet FOLLOW_element_in_prefix587;
    public static final BitSet FOLLOW_recurse_in_prefix593;
    public static final BitSet FOLLOW_epsilonElement_in_prefix595;
    public static final BitSet FOLLOW_ALT_in_suffix630;
    public static final BitSet FOLLOW_elementOptions_in_suffix632;
    public static final BitSet FOLLOW_recurse_in_suffix635;
    public static final BitSet FOLLOW_element_in_suffix637;
    public static final BitSet FOLLOW_ALT_in_nonLeftRecur671;
    public static final BitSet FOLLOW_elementOptions_in_nonLeftRecur673;
    public static final BitSet FOLLOW_element_in_nonLeftRecur676;
    public static final BitSet FOLLOW_ASSIGN_in_recurse693;
    public static final BitSet FOLLOW_ID_in_recurse695;
    public static final BitSet FOLLOW_recurseNoLabel_in_recurse697;
    public static final BitSet FOLLOW_PLUS_ASSIGN_in_recurse704;
    public static final BitSet FOLLOW_ID_in_recurse706;
    public static final BitSet FOLLOW_recurseNoLabel_in_recurse708;
    public static final BitSet FOLLOW_recurseNoLabel_in_recurse714;
    public static final BitSet FOLLOW_RULE_REF_in_recurseNoLabel726;
    public static final BitSet FOLLOW_ASSIGN_in_token740;
    public static final BitSet FOLLOW_ID_in_token742;
    public static final BitSet FOLLOW_token_in_token746;
    public static final BitSet FOLLOW_PLUS_ASSIGN_in_token755;
    public static final BitSet FOLLOW_ID_in_token757;
    public static final BitSet FOLLOW_token_in_token761;
    public static final BitSet FOLLOW_STRING_LITERAL_in_token771;
    public static final BitSet FOLLOW_STRING_LITERAL_in_token792;
    public static final BitSet FOLLOW_elementOptions_in_token794;
    public static final BitSet FOLLOW_TOKEN_REF_in_token809;
    public static final BitSet FOLLOW_elementOptions_in_token811;
    public static final BitSet FOLLOW_TOKEN_REF_in_token823;
    public static final BitSet FOLLOW_ELEMENT_OPTIONS_in_elementOptions853;
    public static final BitSet FOLLOW_elementOption_in_elementOptions855;
    public static final BitSet FOLLOW_ID_in_elementOption874;
    public static final BitSet FOLLOW_ASSIGN_in_elementOption885;
    public static final BitSet FOLLOW_ID_in_elementOption887;
    public static final BitSet FOLLOW_ID_in_elementOption889;
    public static final BitSet FOLLOW_ASSIGN_in_elementOption901;
    public static final BitSet FOLLOW_ID_in_elementOption903;
    public static final BitSet FOLLOW_STRING_LITERAL_in_elementOption905;
    public static final BitSet FOLLOW_ASSIGN_in_elementOption917;
    public static final BitSet FOLLOW_ID_in_elementOption919;
    public static final BitSet FOLLOW_ACTION_in_elementOption921;
    public static final BitSet FOLLOW_ASSIGN_in_elementOption933;
    public static final BitSet FOLLOW_ID_in_elementOption935;
    public static final BitSet FOLLOW_INT_in_elementOption937;
    public static final BitSet FOLLOW_atom_in_element952;
    public static final BitSet FOLLOW_NOT_in_element958;
    public static final BitSet FOLLOW_element_in_element960;
    public static final BitSet FOLLOW_RANGE_in_element967;
    public static final BitSet FOLLOW_atom_in_element969;
    public static final BitSet FOLLOW_atom_in_element971;
    public static final BitSet FOLLOW_ASSIGN_in_element978;
    public static final BitSet FOLLOW_ID_in_element980;
    public static final BitSet FOLLOW_element_in_element982;
    public static final BitSet FOLLOW_PLUS_ASSIGN_in_element989;
    public static final BitSet FOLLOW_ID_in_element991;
    public static final BitSet FOLLOW_element_in_element993;
    public static final BitSet FOLLOW_SET_in_element1003;
    public static final BitSet FOLLOW_setElement_in_element1005;
    public static final BitSet FOLLOW_RULE_REF_in_element1017;
    public static final BitSet FOLLOW_ebnf_in_element1022;
    public static final BitSet FOLLOW_epsilonElement_in_element1027;
    public static final BitSet FOLLOW_ACTION_in_epsilonElement1038;
    public static final BitSet FOLLOW_SEMPRED_in_epsilonElement1043;
    public static final BitSet FOLLOW_EPSILON_in_epsilonElement1048;
    public static final BitSet FOLLOW_ACTION_in_epsilonElement1054;
    public static final BitSet FOLLOW_elementOptions_in_epsilonElement1056;
    public static final BitSet FOLLOW_SEMPRED_in_epsilonElement1063;
    public static final BitSet FOLLOW_elementOptions_in_epsilonElement1065;
    public static final BitSet FOLLOW_STRING_LITERAL_in_setElement1078;
    public static final BitSet FOLLOW_elementOptions_in_setElement1080;
    public static final BitSet FOLLOW_TOKEN_REF_in_setElement1087;
    public static final BitSet FOLLOW_elementOptions_in_setElement1089;
    public static final BitSet FOLLOW_STRING_LITERAL_in_setElement1095;
    public static final BitSet FOLLOW_TOKEN_REF_in_setElement1100;
    public static final BitSet FOLLOW_block_in_ebnf1111;
    public static final BitSet FOLLOW_OPTIONAL_in_ebnf1123;
    public static final BitSet FOLLOW_block_in_ebnf1125;
    public static final BitSet FOLLOW_CLOSURE_in_ebnf1139;
    public static final BitSet FOLLOW_block_in_ebnf1141;
    public static final BitSet FOLLOW_POSITIVE_CLOSURE_in_ebnf1155;
    public static final BitSet FOLLOW_block_in_ebnf1157;
    public static final BitSet FOLLOW_BLOCK_in_block1177;
    public static final BitSet FOLLOW_ACTION_in_block1179;
    public static final BitSet FOLLOW_alternative_in_block1182;
    public static final BitSet FOLLOW_ALT_in_alternative1199;
    public static final BitSet FOLLOW_elementOptions_in_alternative1201;
    public static final BitSet FOLLOW_element_in_alternative1204;
    public static final BitSet FOLLOW_RULE_REF_in_atom1221;
    public static final BitSet FOLLOW_ARG_ACTION_in_atom1223;
    public static final BitSet FOLLOW_elementOptions_in_atom1226;
    public static final BitSet FOLLOW_STRING_LITERAL_in_atom1238;
    public static final BitSet FOLLOW_elementOptions_in_atom1240;
    public static final BitSet FOLLOW_STRING_LITERAL_in_atom1246;
    public static final BitSet FOLLOW_TOKEN_REF_in_atom1255;
    public static final BitSet FOLLOW_elementOptions_in_atom1257;
    public static final BitSet FOLLOW_TOKEN_REF_in_atom1263;
    public static final BitSet FOLLOW_WILDCARD_in_atom1272;
    public static final BitSet FOLLOW_elementOptions_in_atom1274;
    public static final BitSet FOLLOW_WILDCARD_in_atom1280;
    public static final BitSet FOLLOW_DOT_in_atom1286;
    public static final BitSet FOLLOW_ID_in_atom1288;
    public static final BitSet FOLLOW_element_in_atom1290;
    public static final BitSet FOLLOW_binary_in_synpred1_LeftRecursiveRuleWalker348;
    public static final BitSet FOLLOW_prefix_in_synpred2_LeftRecursiveRuleWalker404;
    public static final BitSet FOLLOW_suffix_in_synpred3_LeftRecursiveRuleWalker460;

    public TreeParser[] getDelegates() {
        return new TreeParser[0];
    }

    public LeftRecursiveRuleWalker(TreeNodeStream input) {
        this(input, new RecognizerSharedState());
    }

    public LeftRecursiveRuleWalker(TreeNodeStream input, RecognizerSharedState state) {
        super(input, state);
    }

    @Override
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override
    public String getGrammarFileName() {
        return "org\\antlr\\v4\\parse\\LeftRecursiveRuleWalker.g";
    }

    public void setAltAssoc(AltAST altTree, int alt) {
    }

    public void binaryAlt(AltAST altTree, int alt) {
    }

    public void prefixAlt(AltAST altTree, int alt) {
    }

    public void suffixAlt(AltAST altTree, int alt) {
    }

    public void otherAlt(AltAST altTree, int alt) {
    }

    public void setReturnValues(GrammarAST t) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean rec_rule() throws RecognitionException {
        isLeftRec = false;
        r = null;
        id = null;
        a = null;
        ruleBlock1 = null;
        this.currentOuterAltNumber = 1;
        r = (GrammarAST)this.match(this.input, 94, LeftRecursiveRuleWalker.FOLLOW_RULE_in_rec_rule72);
        if (this.state.failed) {
            return isLeftRec;
        }
        this.match(this.input, 2, null);
        if (this.state.failed) {
            return isLeftRec;
        }
        id = (GrammarAST)this.match(this.input, 57, LeftRecursiveRuleWalker.FOLLOW_RULE_REF_in_rec_rule76);
        if (this.state.failed) {
            return isLeftRec;
        }
        if (this.state.backtracking == 0) {
            this.ruleName = id.getText();
        }
        alt1 = 2;
        LA1_0 = this.input.LA(1);
        if (LA1_0 >= 48 && LA1_0 <= 50) {
            alt1 = 1;
        }
        switch (alt1) {
            case 1: {
                this.pushFollow(LeftRecursiveRuleWalker.FOLLOW_ruleModifier_in_rec_rule83);
                this.ruleModifier();
                --this.state._fsp;
                if (!this.state.failed) break;
                return isLeftRec;
            }
        }
        alt2 = 2;
        LA2_0 = this.input.LA(1);
        if (LA2_0 == 55) {
            alt2 = 1;
        }
        switch (alt2) {
            case 1: {
                this.match(this.input, 55, LeftRecursiveRuleWalker.FOLLOW_RETURNS_in_rec_rule92);
                if (this.state.failed) {
                    return isLeftRec;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return isLeftRec;
                }
                a = (GrammarAST)this.match(this.input, 8, LeftRecursiveRuleWalker.FOLLOW_ARG_ACTION_in_rec_rule96);
                if (this.state.failed) {
                    return isLeftRec;
                }
                if (this.state.backtracking == 0) {
                    this.setReturnValues(a);
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) break;
                return isLeftRec;
            }
        }
        alt3 = 2;
        LA3_0 = this.input.LA(1);
        if (LA3_0 == 33) {
            alt3 = 1;
        }
        switch (alt3) {
            case 1: {
                this.match(this.input, 33, LeftRecursiveRuleWalker.FOLLOW_LOCALS_in_rec_rule115);
                if (this.state.failed) {
                    return isLeftRec;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return isLeftRec;
                }
                this.match(this.input, 8, LeftRecursiveRuleWalker.FOLLOW_ARG_ACTION_in_rec_rule117);
                if (this.state.failed) {
                    return isLeftRec;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) ** break;
                return isLeftRec;
            }
        }
        block18: while (true) {
            alt5 = 3;
            LA5_0 = this.input.LA(1);
            if (LA5_0 == 42) {
                alt5 = 1;
            } else if (LA5_0 == 11) {
                alt5 = 2;
            }
            switch (alt5) {
                case 1: {
                    this.match(this.input, 42, LeftRecursiveRuleWalker.FOLLOW_OPTIONS_in_rec_rule135);
                    if (this.state.failed) {
                        return isLeftRec;
                    }
                    if (this.input.LA(1) != 2) continue block18;
                    this.match(this.input, 2, null);
                    if (this.state.failed) {
                        return isLeftRec;
                    }
                    block19: while (true) {
                        alt4 = 2;
                        LA4_0 = this.input.LA(1);
                        if (LA4_0 >= 4 && LA4_0 <= 100) {
                            alt4 = 1;
                        } else if (LA4_0 == 3) {
                            alt4 = 2;
                        }
                        switch (alt4) {
                            case 1: {
                                this.matchAny(this.input);
                                if (!this.state.failed) continue block19;
                                return isLeftRec;
                            }
                        }
                        break;
                    }
                    this.match(this.input, 3, null);
                    if (!this.state.failed) continue block18;
                    return isLeftRec;
                }
                case 2: {
                    this.match(this.input, 11, LeftRecursiveRuleWalker.FOLLOW_AT_in_rec_rule152);
                    if (this.state.failed) {
                        return isLeftRec;
                    }
                    this.match(this.input, 2, null);
                    if (this.state.failed) {
                        return isLeftRec;
                    }
                    this.match(this.input, 28, LeftRecursiveRuleWalker.FOLLOW_ID_in_rec_rule154);
                    if (this.state.failed) {
                        return isLeftRec;
                    }
                    this.match(this.input, 4, LeftRecursiveRuleWalker.FOLLOW_ACTION_in_rec_rule156);
                    if (this.state.failed) {
                        return isLeftRec;
                    }
                    this.match(this.input, 3, null);
                    if (this.state.failed) ** break;
                    continue block18;
                    return isLeftRec;
                }
            }
            break;
        }
        this.pushFollow(LeftRecursiveRuleWalker.FOLLOW_ruleBlock_in_rec_rule172);
        ruleBlock1 = this.ruleBlock();
        --this.state._fsp;
        if (this.state.failed) {
            return isLeftRec;
        }
        if (this.state.backtracking == 0) {
            isLeftRec = ruleBlock1 != null ? ruleBlock1.isLeftRec : false;
        }
        this.pushFollow(LeftRecursiveRuleWalker.FOLLOW_exceptionGroup_in_rec_rule179);
        this.exceptionGroup();
        --this.state._fsp;
        if (this.state.failed) {
            return isLeftRec;
        }
        this.match(this.input, 3, null);
        if (this.state.failed == false) return isLeftRec;
        return isLeftRec;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void exceptionGroup() throws RecognitionException {
        block8: while (true) {
            int alt6 = 2;
            int LA6_0 = this.input.LA(1);
            if (LA6_0 == 12) {
                alt6 = 1;
            }
            switch (alt6) {
                case 1: {
                    this.pushFollow(FOLLOW_exceptionHandler_in_exceptionGroup197);
                    this.exceptionHandler();
                    --this.state._fsp;
                    if (!this.state.failed) continue block8;
                    return;
                }
            }
            break;
        }
        int alt7 = 2;
        int LA7_0 = this.input.LA(1);
        if (LA7_0 == 23) {
            alt7 = 1;
        }
        switch (alt7) {
            case 1: {
                this.pushFollow(FOLLOW_finallyClause_in_exceptionGroup200);
                this.finallyClause();
                --this.state._fsp;
                if (!this.state.failed) break;
                return;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void exceptionHandler() throws RecognitionException {
        this.match(this.input, 12, FOLLOW_CATCH_in_exceptionHandler216);
        if (this.state.failed) {
            return;
        }
        this.match(this.input, 2, null);
        if (this.state.failed) {
            return;
        }
        this.match(this.input, 8, FOLLOW_ARG_ACTION_in_exceptionHandler218);
        if (this.state.failed) {
            return;
        }
        this.match(this.input, 4, FOLLOW_ACTION_in_exceptionHandler220);
        if (this.state.failed) {
            return;
        }
        this.match(this.input, 3, null);
        if (this.state.failed) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void finallyClause() throws RecognitionException {
        this.match(this.input, 23, FOLLOW_FINALLY_in_finallyClause233);
        if (this.state.failed) {
            return;
        }
        this.match(this.input, 2, null);
        if (this.state.failed) {
            return;
        }
        this.match(this.input, 4, FOLLOW_ACTION_in_finallyClause235);
        if (this.state.failed) {
            return;
        }
        this.match(this.input, 3, null);
        if (this.state.failed) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void ruleModifier() throws RecognitionException {
        if (this.input.LA(1) < 48 || this.input.LA(1) > 50) {
            if (this.state.backtracking > 0) {
                this.state.failed = true;
                return;
            }
            MismatchedSetException mse = new MismatchedSetException(null, this.input);
            throw mse;
        }
        this.input.consume();
        this.state.errorRecovery = false;
        this.state.failed = false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final ruleBlock_return ruleBlock() throws RecognitionException {
        ruleBlock_return retval = new ruleBlock_return();
        retval.start = this.input.LT(1);
        outerAlternative_return o = null;
        boolean lr = false;
        this.numAlts = ((GrammarAST)retval.start).getChildCount();
        this.match(this.input, 78, FOLLOW_BLOCK_in_ruleBlock290);
        if (this.state.failed) {
            return retval;
        }
        this.match(this.input, 2, null);
        if (this.state.failed) {
            return retval;
        }
        int cnt8 = 0;
        block5: while (true) {
            int alt8 = 2;
            int LA8_0 = this.input.LA(1);
            if (LA8_0 == 74) {
                alt8 = 1;
            }
            switch (alt8) {
                case 1: {
                    this.pushFollow(FOLLOW_outerAlternative_in_ruleBlock303);
                    o = this.outerAlternative();
                    --this.state._fsp;
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 0 && o != null && o.isLeftRec) {
                        retval.isLeftRec = true;
                    }
                    if (this.state.backtracking != 0) break;
                    ++this.currentOuterAltNumber;
                    break;
                }
                default: {
                    if (cnt8 >= 1) break block5;
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                    }
                    EarlyExitException eee = new EarlyExitException(8, this.input);
                    throw eee;
                }
            }
            ++cnt8;
        }
        this.match(this.input, 3, null);
        if (!this.state.failed) return retval;
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final outerAlternative_return outerAlternative() throws RecognitionException {
        outerAlternative_return retval = new outerAlternative_return();
        retval.start = this.input.LT(1);
        int alt9 = 4;
        int LA9_0 = this.input.LA(1);
        if (LA9_0 == 74) {
            int LA9_1 = this.input.LA(2);
            alt9 = this.synpred1_LeftRecursiveRuleWalker() ? 1 : (this.synpred2_LeftRecursiveRuleWalker() ? 2 : (this.synpred3_LeftRecursiveRuleWalker() ? 3 : 4));
        } else {
            if (this.state.backtracking > 0) {
                this.state.failed = true;
                return retval;
            }
            NoViableAltException nvae = new NoViableAltException("", 9, 0, this.input);
            throw nvae;
        }
        switch (alt9) {
            case 1: {
                this.pushFollow(FOLLOW_binary_in_outerAlternative362);
                this.binary();
                --this.state._fsp;
                if (this.state.failed) {
                    return retval;
                }
                if (this.state.backtracking != 0) return retval;
                this.binaryAlt((AltAST)((GrammarAST)retval.start), this.currentOuterAltNumber);
                retval.isLeftRec = true;
                return retval;
            }
            case 2: {
                this.pushFollow(FOLLOW_prefix_in_outerAlternative418);
                this.prefix();
                --this.state._fsp;
                if (this.state.failed) {
                    return retval;
                }
                if (this.state.backtracking != 0) return retval;
                this.prefixAlt((AltAST)((GrammarAST)retval.start), this.currentOuterAltNumber);
                return retval;
            }
            case 3: {
                this.pushFollow(FOLLOW_suffix_in_outerAlternative474);
                this.suffix();
                --this.state._fsp;
                if (this.state.failed) {
                    return retval;
                }
                if (this.state.backtracking != 0) return retval;
                this.suffixAlt((AltAST)((GrammarAST)retval.start), this.currentOuterAltNumber);
                retval.isLeftRec = true;
                return retval;
            }
            case 4: {
                this.pushFollow(FOLLOW_nonLeftRecur_in_outerAlternative515);
                this.nonLeftRecur();
                --this.state._fsp;
                if (this.state.failed) {
                    return retval;
                }
                if (this.state.backtracking != 0) return retval;
                this.otherAlt((AltAST)((GrammarAST)retval.start), this.currentOuterAltNumber);
                return retval;
            }
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void binary() throws RecognitionException {
        GrammarAST ALT2 = null;
        ALT2 = (GrammarAST)this.match(this.input, 74, FOLLOW_ALT_in_binary541);
        if (this.state.failed) {
            return;
        }
        this.match(this.input, 2, null);
        if (this.state.failed) {
            return;
        }
        int alt10 = 2;
        int LA10_0 = this.input.LA(1);
        if (LA10_0 == 82) {
            alt10 = 1;
        }
        switch (alt10) {
            case 1: {
                this.pushFollow(FOLLOW_elementOptions_in_binary543);
                this.elementOptions();
                --this.state._fsp;
                if (!this.state.failed) break;
                return;
            }
        }
        this.pushFollow(FOLLOW_recurse_in_binary546);
        this.recurse();
        --this.state._fsp;
        if (this.state.failed) {
            return;
        }
        block11: while (true) {
            int alt11 = 2;
            alt11 = this.dfa11.predict(this.input);
            switch (alt11) {
                case 1: {
                    this.pushFollow(FOLLOW_element_in_binary548);
                    this.element();
                    --this.state._fsp;
                    if (!this.state.failed) continue block11;
                    return;
                }
            }
            break;
        }
        this.pushFollow(FOLLOW_recurse_in_binary551);
        this.recurse();
        --this.state._fsp;
        if (this.state.failed) {
            return;
        }
        block12: while (true) {
            int alt12 = 2;
            int LA12_0 = this.input.LA(1);
            if (LA12_0 == 4 || LA12_0 == 59 || LA12_0 == 83) {
                alt12 = 1;
            }
            switch (alt12) {
                case 1: {
                    this.pushFollow(FOLLOW_epsilonElement_in_binary553);
                    this.epsilonElement();
                    --this.state._fsp;
                    if (!this.state.failed) continue block12;
                    return;
                }
            }
            break;
        }
        this.match(this.input, 3, null);
        if (this.state.failed) {
            return;
        }
        if (this.state.backtracking == 0) {
            this.setAltAssoc((AltAST)ALT2, this.currentOuterAltNumber);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void prefix() throws RecognitionException {
        GrammarAST ALT3 = null;
        ALT3 = (GrammarAST)this.match(this.input, 74, FOLLOW_ALT_in_prefix579);
        if (this.state.failed) {
            return;
        }
        this.match(this.input, 2, null);
        if (this.state.failed) {
            return;
        }
        int alt13 = 2;
        int LA13_0 = this.input.LA(1);
        if (LA13_0 == 82) {
            alt13 = 1;
        }
        switch (alt13) {
            case 1: {
                this.pushFollow(FOLLOW_elementOptions_in_prefix581);
                this.elementOptions();
                --this.state._fsp;
                if (!this.state.failed) break;
                return;
            }
        }
        int cnt14 = 0;
        block11: while (true) {
            int alt14 = 2;
            alt14 = this.dfa14.predict(this.input);
            switch (alt14) {
                case 1: {
                    this.pushFollow(FOLLOW_element_in_prefix587);
                    this.element();
                    --this.state._fsp;
                    if (!this.state.failed) break;
                    return;
                }
                default: {
                    if (cnt14 >= 1) break block11;
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    EarlyExitException eee = new EarlyExitException(14, this.input);
                    throw eee;
                }
            }
            ++cnt14;
        }
        this.pushFollow(FOLLOW_recurse_in_prefix593);
        this.recurse();
        --this.state._fsp;
        if (this.state.failed) {
            return;
        }
        block12: while (true) {
            int alt15 = 2;
            int LA15_0 = this.input.LA(1);
            if (LA15_0 == 4 || LA15_0 == 59 || LA15_0 == 83) {
                alt15 = 1;
            }
            switch (alt15) {
                case 1: {
                    this.pushFollow(FOLLOW_epsilonElement_in_prefix595);
                    this.epsilonElement();
                    --this.state._fsp;
                    if (!this.state.failed) continue block12;
                    return;
                }
            }
            break;
        }
        this.match(this.input, 3, null);
        if (this.state.failed) {
            return;
        }
        if (this.state.backtracking == 0) {
            this.setAltAssoc((AltAST)ALT3, this.currentOuterAltNumber);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void suffix() throws RecognitionException {
        GrammarAST ALT4 = null;
        ALT4 = (GrammarAST)this.match(this.input, 74, FOLLOW_ALT_in_suffix630);
        if (this.state.failed) {
            return;
        }
        this.match(this.input, 2, null);
        if (this.state.failed) {
            return;
        }
        int alt16 = 2;
        int LA16_0 = this.input.LA(1);
        if (LA16_0 == 82) {
            alt16 = 1;
        }
        switch (alt16) {
            case 1: {
                this.pushFollow(FOLLOW_elementOptions_in_suffix632);
                this.elementOptions();
                --this.state._fsp;
                if (!this.state.failed) break;
                return;
            }
        }
        this.pushFollow(FOLLOW_recurse_in_suffix635);
        this.recurse();
        --this.state._fsp;
        if (this.state.failed) {
            return;
        }
        int cnt17 = 0;
        block8: while (true) {
            int alt17 = 2;
            int LA17_0 = this.input.LA(1);
            if (LA17_0 == 4 || LA17_0 == 10 || LA17_0 == 20 || LA17_0 == 39 || LA17_0 == 46 || LA17_0 == 52 || LA17_0 == 57 || LA17_0 == 59 || LA17_0 == 62 || LA17_0 == 66 || LA17_0 == 78 || LA17_0 == 80 || LA17_0 == 83 || LA17_0 >= 89 && LA17_0 <= 90 || LA17_0 == 98 || LA17_0 == 100) {
                alt17 = 1;
            }
            switch (alt17) {
                case 1: {
                    this.pushFollow(FOLLOW_element_in_suffix637);
                    this.element();
                    --this.state._fsp;
                    if (!this.state.failed) break;
                    return;
                }
                default: {
                    if (cnt17 >= 1) break block8;
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    EarlyExitException eee = new EarlyExitException(17, this.input);
                    throw eee;
                }
            }
            ++cnt17;
        }
        this.match(this.input, 3, null);
        if (this.state.failed) {
            return;
        }
        if (this.state.backtracking == 0) {
            this.setAltAssoc((AltAST)ALT4, this.currentOuterAltNumber);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void nonLeftRecur() throws RecognitionException {
        this.match(this.input, 74, FOLLOW_ALT_in_nonLeftRecur671);
        if (this.state.failed) {
            return;
        }
        this.match(this.input, 2, null);
        if (this.state.failed) {
            return;
        }
        int alt18 = 2;
        int LA18_0 = this.input.LA(1);
        if (LA18_0 == 82) {
            alt18 = 1;
        }
        switch (alt18) {
            case 1: {
                this.pushFollow(FOLLOW_elementOptions_in_nonLeftRecur673);
                this.elementOptions();
                --this.state._fsp;
                if (!this.state.failed) break;
                return;
            }
        }
        int cnt19 = 0;
        block8: while (true) {
            int alt19 = 2;
            int LA19_0 = this.input.LA(1);
            if (LA19_0 == 4 || LA19_0 == 10 || LA19_0 == 20 || LA19_0 == 39 || LA19_0 == 46 || LA19_0 == 52 || LA19_0 == 57 || LA19_0 == 59 || LA19_0 == 62 || LA19_0 == 66 || LA19_0 == 78 || LA19_0 == 80 || LA19_0 == 83 || LA19_0 >= 89 && LA19_0 <= 90 || LA19_0 == 98 || LA19_0 == 100) {
                alt19 = 1;
            }
            switch (alt19) {
                case 1: {
                    this.pushFollow(FOLLOW_element_in_nonLeftRecur676);
                    this.element();
                    --this.state._fsp;
                    if (!this.state.failed) break;
                    return;
                }
                default: {
                    if (cnt19 >= 1) break block8;
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    EarlyExitException eee = new EarlyExitException(19, this.input);
                    throw eee;
                }
            }
            ++cnt19;
        }
        this.match(this.input, 3, null);
        if (this.state.failed) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void recurse() throws RecognitionException {
        int alt20 = 3;
        switch (this.input.LA(1)) {
            case 10: {
                alt20 = 1;
                break;
            }
            case 46: {
                alt20 = 2;
                break;
            }
            case 57: {
                alt20 = 3;
                break;
            }
            default: {
                if (this.state.backtracking > 0) {
                    this.state.failed = true;
                    return;
                }
                NoViableAltException nvae = new NoViableAltException("", 20, 0, this.input);
                throw nvae;
            }
        }
        switch (alt20) {
            case 1: {
                this.match(this.input, 10, FOLLOW_ASSIGN_in_recurse693);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 28, FOLLOW_ID_in_recurse695);
                if (this.state.failed) {
                    return;
                }
                this.pushFollow(FOLLOW_recurseNoLabel_in_recurse697);
                this.recurseNoLabel();
                --this.state._fsp;
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) break;
                return;
            }
            case 2: {
                this.match(this.input, 46, FOLLOW_PLUS_ASSIGN_in_recurse704);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 28, FOLLOW_ID_in_recurse706);
                if (this.state.failed) {
                    return;
                }
                this.pushFollow(FOLLOW_recurseNoLabel_in_recurse708);
                this.recurseNoLabel();
                --this.state._fsp;
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) break;
                return;
            }
            case 3: {
                this.pushFollow(FOLLOW_recurseNoLabel_in_recurse714);
                this.recurseNoLabel();
                --this.state._fsp;
                if (!this.state.failed) break;
                return;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void recurseNoLabel() throws RecognitionException {
        if (!((CommonTree)this.input.LT(1)).getText().equals(this.ruleName)) {
            if (this.state.backtracking > 0) {
                this.state.failed = true;
                return;
            }
            throw new FailedPredicateException(this.input, "recurseNoLabel", "((CommonTree)input.LT(1)).getText().equals(ruleName)");
        }
        this.match(this.input, 57, FOLLOW_RULE_REF_in_recurseNoLabel726);
        if (this.state.failed) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final GrammarAST token() throws RecognitionException {
        GrammarAST t = null;
        GrammarAST b = null;
        GrammarAST c = null;
        GrammarAST s = null;
        int alt21 = 6;
        switch (this.input.LA(1)) {
            case 10: {
                alt21 = 1;
                break;
            }
            case 46: {
                alt21 = 2;
                break;
            }
            case 62: {
                int LA21_3 = this.input.LA(2);
                if (LA21_3 == 2) {
                    alt21 = 4;
                    break;
                }
                if (LA21_3 == 3) {
                    alt21 = 3;
                    break;
                }
                if (this.state.backtracking > 0) {
                    this.state.failed = true;
                    return t;
                }
                int nvaeMark = this.input.mark();
                try {
                    this.input.consume();
                    NoViableAltException nvae = new NoViableAltException("", 21, 3, this.input);
                    throw nvae;
                }
                catch (Throwable throwable) {
                    this.input.rewind(nvaeMark);
                    throw throwable;
                }
            }
            case 66: {
                int LA21_4 = this.input.LA(2);
                if (LA21_4 == 2) {
                    alt21 = 5;
                    break;
                }
                if (LA21_4 == 3) {
                    alt21 = 6;
                    break;
                }
                if (this.state.backtracking > 0) {
                    this.state.failed = true;
                    return t;
                }
                int nvaeMark = this.input.mark();
                try {
                    this.input.consume();
                    NoViableAltException nvae = new NoViableAltException("", 21, 4, this.input);
                    throw nvae;
                }
                catch (Throwable throwable) {
                    this.input.rewind(nvaeMark);
                    throw throwable;
                }
            }
            default: {
                if (this.state.backtracking > 0) {
                    this.state.failed = true;
                    return t;
                }
                NoViableAltException nvae = new NoViableAltException("", 21, 0, this.input);
                throw nvae;
            }
        }
        switch (alt21) {
            case 1: {
                this.match(this.input, 10, FOLLOW_ASSIGN_in_token740);
                if (this.state.failed) {
                    return t;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return t;
                }
                this.match(this.input, 28, FOLLOW_ID_in_token742);
                if (this.state.failed) {
                    return t;
                }
                this.pushFollow(FOLLOW_token_in_token746);
                s = this.token();
                --this.state._fsp;
                if (this.state.failed) {
                    return t;
                }
                if (this.state.backtracking == 0) {
                    t = s;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) return t;
                return t;
            }
            case 2: {
                this.match(this.input, 46, FOLLOW_PLUS_ASSIGN_in_token755);
                if (this.state.failed) {
                    return t;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return t;
                }
                this.match(this.input, 28, FOLLOW_ID_in_token757);
                if (this.state.failed) {
                    return t;
                }
                this.pushFollow(FOLLOW_token_in_token761);
                s = this.token();
                --this.state._fsp;
                if (this.state.failed) {
                    return t;
                }
                if (this.state.backtracking == 0) {
                    t = s;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) return t;
                return t;
            }
            case 3: {
                b = (GrammarAST)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_token771);
                if (this.state.failed) {
                    return t;
                }
                if (this.state.backtracking != 0) return t;
                return b;
            }
            case 4: {
                b = (GrammarAST)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_token792);
                if (this.state.failed) {
                    return t;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return t;
                }
                this.pushFollow(FOLLOW_elementOptions_in_token794);
                this.elementOptions();
                --this.state._fsp;
                if (this.state.failed) {
                    return t;
                }
                this.match(this.input, 3, null);
                if (this.state.failed) {
                    return t;
                }
                if (this.state.backtracking != 0) return t;
                return b;
            }
            case 5: {
                c = (GrammarAST)this.match(this.input, 66, FOLLOW_TOKEN_REF_in_token809);
                if (this.state.failed) {
                    return t;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return t;
                }
                this.pushFollow(FOLLOW_elementOptions_in_token811);
                this.elementOptions();
                --this.state._fsp;
                if (this.state.failed) {
                    return t;
                }
                this.match(this.input, 3, null);
                if (this.state.failed) {
                    return t;
                }
                if (this.state.backtracking != 0) return t;
                return c;
            }
            case 6: {
                c = (GrammarAST)this.match(this.input, 66, FOLLOW_TOKEN_REF_in_token823);
                if (this.state.failed) {
                    return t;
                }
                if (this.state.backtracking != 0) return t;
                return c;
            }
        }
        return t;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void elementOptions() throws RecognitionException {
        this.match(this.input, 82, FOLLOW_ELEMENT_OPTIONS_in_elementOptions853);
        if (this.state.failed) {
            return;
        }
        if (this.input.LA(1) == 2) {
            this.match(this.input, 2, null);
            if (this.state.failed) {
                return;
            }
            block5: while (true) {
                int alt22 = 2;
                int LA22_0 = this.input.LA(1);
                if (LA22_0 == 10 || LA22_0 == 28) {
                    alt22 = 1;
                }
                switch (alt22) {
                    case 1: {
                        this.pushFollow(FOLLOW_elementOption_in_elementOptions855);
                        this.elementOption();
                        --this.state._fsp;
                        if (!this.state.failed) continue block5;
                        return;
                    }
                }
                break;
            }
            this.match(this.input, 3, null);
            if (this.state.failed) {
                return;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void elementOption() throws RecognitionException {
        int alt23;
        block51: {
            alt23 = 5;
            int LA23_0 = this.input.LA(1);
            if (LA23_0 == 28) {
                alt23 = 1;
            } else {
                if (LA23_0 == 10) {
                    int LA23_2 = this.input.LA(2);
                    if (LA23_2 == 2) {
                        int LA23_3 = this.input.LA(3);
                        if (LA23_3 == 28) {
                            switch (this.input.LA(4)) {
                                case 28: {
                                    alt23 = 2;
                                    break;
                                }
                                case 62: {
                                    alt23 = 3;
                                    break;
                                }
                                case 4: {
                                    alt23 = 4;
                                    break;
                                }
                                case 30: {
                                    alt23 = 5;
                                    break;
                                }
                                default: {
                                    if (this.state.backtracking > 0) {
                                        this.state.failed = true;
                                        return;
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
                            }
                            break block51;
                        } else {
                            if (this.state.backtracking > 0) {
                                this.state.failed = true;
                                return;
                            }
                            int nvaeMark = this.input.mark();
                            try {
                                int nvaeConsume = 0;
                                while (true) {
                                    if (nvaeConsume >= 2) {
                                        NoViableAltException nvae = new NoViableAltException("", 23, 3, this.input);
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
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 23, 2, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                if (this.state.backtracking > 0) {
                    this.state.failed = true;
                    return;
                }
                NoViableAltException nvae = new NoViableAltException("", 23, 0, this.input);
                throw nvae;
            }
        }
        switch (alt23) {
            case 1: {
                this.match(this.input, 28, FOLLOW_ID_in_elementOption874);
                if (!this.state.failed) return;
                return;
            }
            case 2: {
                this.match(this.input, 10, FOLLOW_ASSIGN_in_elementOption885);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 28, FOLLOW_ID_in_elementOption887);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 28, FOLLOW_ID_in_elementOption889);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) return;
                return;
            }
            case 3: {
                this.match(this.input, 10, FOLLOW_ASSIGN_in_elementOption901);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 28, FOLLOW_ID_in_elementOption903);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_elementOption905);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) return;
                return;
            }
            case 4: {
                this.match(this.input, 10, FOLLOW_ASSIGN_in_elementOption917);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 28, FOLLOW_ID_in_elementOption919);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 4, FOLLOW_ACTION_in_elementOption921);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) return;
                return;
            }
            case 5: {
                this.match(this.input, 10, FOLLOW_ASSIGN_in_elementOption933);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 28, FOLLOW_ID_in_elementOption935);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 30, FOLLOW_INT_in_elementOption937);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) return;
                return;
            }
        }
        return;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void element() throws RecognitionException {
        int alt25 = 9;
        switch (this.input.LA(1)) {
            case 57: {
                int LA25_1 = this.input.LA(2);
                if (LA25_1 == 2) {
                    alt25 = 1;
                    break;
                }
                if (LA25_1 >= 3 && LA25_1 <= 4 || LA25_1 == 10 || LA25_1 == 20 || LA25_1 == 39 || LA25_1 == 46 || LA25_1 == 52 || LA25_1 == 57 || LA25_1 == 59 || LA25_1 == 62 || LA25_1 == 66 || LA25_1 == 78 || LA25_1 == 80 || LA25_1 == 83 || LA25_1 >= 89 && LA25_1 <= 90 || LA25_1 == 98 || LA25_1 == 100) {
                    alt25 = 7;
                    break;
                }
                if (this.state.backtracking > 0) {
                    this.state.failed = true;
                    return;
                }
                int nvaeMark = this.input.mark();
                try {
                    this.input.consume();
                    NoViableAltException nvae = new NoViableAltException("", 25, 1, this.input);
                    throw nvae;
                }
                catch (Throwable throwable) {
                    this.input.rewind(nvaeMark);
                    throw throwable;
                }
            }
            case 20: 
            case 62: 
            case 66: 
            case 100: {
                alt25 = 1;
                break;
            }
            case 39: {
                alt25 = 2;
                break;
            }
            case 52: {
                alt25 = 3;
                break;
            }
            case 10: {
                alt25 = 4;
                break;
            }
            case 46: {
                alt25 = 5;
                break;
            }
            case 98: {
                alt25 = 6;
                break;
            }
            case 78: 
            case 80: 
            case 89: 
            case 90: {
                alt25 = 8;
                break;
            }
            case 4: 
            case 59: 
            case 83: {
                alt25 = 9;
                break;
            }
            default: {
                if (this.state.backtracking > 0) {
                    this.state.failed = true;
                    return;
                }
                NoViableAltException nvae = new NoViableAltException("", 25, 0, this.input);
                throw nvae;
            }
        }
        switch (alt25) {
            case 1: {
                this.pushFollow(FOLLOW_atom_in_element952);
                this.atom();
                --this.state._fsp;
                if (!this.state.failed) break;
                return;
            }
            case 2: {
                this.match(this.input, 39, FOLLOW_NOT_in_element958);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return;
                }
                this.pushFollow(FOLLOW_element_in_element960);
                this.element();
                --this.state._fsp;
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) break;
                return;
            }
            case 3: {
                this.match(this.input, 52, FOLLOW_RANGE_in_element967);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return;
                }
                this.pushFollow(FOLLOW_atom_in_element969);
                this.atom();
                --this.state._fsp;
                if (this.state.failed) {
                    return;
                }
                this.pushFollow(FOLLOW_atom_in_element971);
                this.atom();
                --this.state._fsp;
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) break;
                return;
            }
            case 4: {
                this.match(this.input, 10, FOLLOW_ASSIGN_in_element978);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 28, FOLLOW_ID_in_element980);
                if (this.state.failed) {
                    return;
                }
                this.pushFollow(FOLLOW_element_in_element982);
                this.element();
                --this.state._fsp;
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) break;
                return;
            }
            case 5: {
                this.match(this.input, 46, FOLLOW_PLUS_ASSIGN_in_element989);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 28, FOLLOW_ID_in_element991);
                if (this.state.failed) {
                    return;
                }
                this.pushFollow(FOLLOW_element_in_element993);
                this.element();
                --this.state._fsp;
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) break;
                return;
            }
            case 6: {
                this.match(this.input, 98, FOLLOW_SET_in_element1003);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return;
                }
                int cnt24 = 0;
                block29: while (true) {
                    int alt24 = 2;
                    int LA24_0 = this.input.LA(1);
                    if (LA24_0 == 62 || LA24_0 == 66) {
                        alt24 = 1;
                    }
                    switch (alt24) {
                        case 1: {
                            this.pushFollow(FOLLOW_setElement_in_element1005);
                            this.setElement();
                            --this.state._fsp;
                            if (!this.state.failed) break;
                            return;
                        }
                        default: {
                            if (cnt24 >= 1) break block29;
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
                this.match(this.input, 3, null);
                if (this.state.failed) {
                    return;
                }
                break;
            }
            case 7: {
                this.match(this.input, 57, FOLLOW_RULE_REF_in_element1017);
                if (!this.state.failed) break;
                return;
            }
            case 8: {
                this.pushFollow(FOLLOW_ebnf_in_element1022);
                this.ebnf();
                --this.state._fsp;
                if (!this.state.failed) break;
                return;
            }
            case 9: {
                this.pushFollow(FOLLOW_epsilonElement_in_element1027);
                this.epsilonElement();
                --this.state._fsp;
                if (!this.state.failed) break;
                return;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void epsilonElement() throws RecognitionException {
        int alt26 = 5;
        switch (this.input.LA(1)) {
            case 4: {
                int LA26_1 = this.input.LA(2);
                if (LA26_1 == 2) {
                    alt26 = 4;
                    break;
                }
                if (LA26_1 >= 3 && LA26_1 <= 4 || LA26_1 == 10 || LA26_1 == 20 || LA26_1 == 39 || LA26_1 == 46 || LA26_1 == 52 || LA26_1 == 57 || LA26_1 == 59 || LA26_1 == 62 || LA26_1 == 66 || LA26_1 == 78 || LA26_1 == 80 || LA26_1 == 83 || LA26_1 >= 89 && LA26_1 <= 90 || LA26_1 == 98 || LA26_1 == 100) {
                    alt26 = 1;
                    break;
                }
                if (this.state.backtracking > 0) {
                    this.state.failed = true;
                    return;
                }
                int nvaeMark = this.input.mark();
                try {
                    this.input.consume();
                    NoViableAltException nvae = new NoViableAltException("", 26, 1, this.input);
                    throw nvae;
                }
                catch (Throwable throwable) {
                    this.input.rewind(nvaeMark);
                    throw throwable;
                }
            }
            case 59: {
                int LA26_2 = this.input.LA(2);
                if (LA26_2 == 2) {
                    alt26 = 5;
                    break;
                }
                if (LA26_2 >= 3 && LA26_2 <= 4 || LA26_2 == 10 || LA26_2 == 20 || LA26_2 == 39 || LA26_2 == 46 || LA26_2 == 52 || LA26_2 == 57 || LA26_2 == 59 || LA26_2 == 62 || LA26_2 == 66 || LA26_2 == 78 || LA26_2 == 80 || LA26_2 == 83 || LA26_2 >= 89 && LA26_2 <= 90 || LA26_2 == 98 || LA26_2 == 100) {
                    alt26 = 2;
                    break;
                }
                if (this.state.backtracking > 0) {
                    this.state.failed = true;
                    return;
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
            case 83: {
                alt26 = 3;
                break;
            }
            default: {
                if (this.state.backtracking > 0) {
                    this.state.failed = true;
                    return;
                }
                NoViableAltException nvae = new NoViableAltException("", 26, 0, this.input);
                throw nvae;
            }
        }
        switch (alt26) {
            case 1: {
                this.match(this.input, 4, FOLLOW_ACTION_in_epsilonElement1038);
                if (!this.state.failed) break;
                return;
            }
            case 2: {
                this.match(this.input, 59, FOLLOW_SEMPRED_in_epsilonElement1043);
                if (!this.state.failed) break;
                return;
            }
            case 3: {
                this.match(this.input, 83, FOLLOW_EPSILON_in_epsilonElement1048);
                if (!this.state.failed) break;
                return;
            }
            case 4: {
                this.match(this.input, 4, FOLLOW_ACTION_in_epsilonElement1054);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return;
                }
                this.pushFollow(FOLLOW_elementOptions_in_epsilonElement1056);
                this.elementOptions();
                --this.state._fsp;
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) break;
                return;
            }
            case 5: {
                this.match(this.input, 59, FOLLOW_SEMPRED_in_epsilonElement1063);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return;
                }
                this.pushFollow(FOLLOW_elementOptions_in_epsilonElement1065);
                this.elementOptions();
                --this.state._fsp;
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) break;
                return;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void setElement() throws RecognitionException {
        int alt27;
        block29: {
            block31: {
                int LA27_0;
                block30: {
                    alt27 = 4;
                    LA27_0 = this.input.LA(1);
                    if (LA27_0 != 62) break block30;
                    int LA27_1 = this.input.LA(2);
                    if (LA27_1 == 2) {
                        alt27 = 1;
                        break block29;
                    } else if (LA27_1 == 3 || LA27_1 == 62 || LA27_1 == 66) {
                        alt27 = 3;
                        break block29;
                    } else {
                        if (this.state.backtracking > 0) {
                            this.state.failed = true;
                            return;
                        }
                        int nvaeMark = this.input.mark();
                        try {
                            this.input.consume();
                            NoViableAltException nvae = new NoViableAltException("", 27, 1, this.input);
                            throw nvae;
                        }
                        catch (Throwable throwable) {
                            this.input.rewind(nvaeMark);
                            throw throwable;
                        }
                    }
                }
                if (LA27_0 != 66) break block31;
                int LA27_2 = this.input.LA(2);
                if (LA27_2 == 2) {
                    alt27 = 2;
                    break block29;
                } else if (LA27_2 == 3 || LA27_2 == 62 || LA27_2 == 66) {
                    alt27 = 4;
                    break block29;
                } else {
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 27, 2, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
            }
            if (this.state.backtracking > 0) {
                this.state.failed = true;
                return;
            }
            NoViableAltException nvae = new NoViableAltException("", 27, 0, this.input);
            throw nvae;
        }
        switch (alt27) {
            case 1: {
                this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_setElement1078);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return;
                }
                this.pushFollow(FOLLOW_elementOptions_in_setElement1080);
                this.elementOptions();
                --this.state._fsp;
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) return;
                return;
            }
            case 2: {
                this.match(this.input, 66, FOLLOW_TOKEN_REF_in_setElement1087);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return;
                }
                this.pushFollow(FOLLOW_elementOptions_in_setElement1089);
                this.elementOptions();
                --this.state._fsp;
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) return;
                return;
            }
            case 3: {
                this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_setElement1095);
                if (!this.state.failed) return;
                return;
            }
            case 4: {
                this.match(this.input, 66, FOLLOW_TOKEN_REF_in_setElement1100);
                if (!this.state.failed) return;
                return;
            }
        }
        return;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void ebnf() throws RecognitionException {
        int alt28 = 4;
        switch (this.input.LA(1)) {
            case 78: {
                alt28 = 1;
                break;
            }
            case 89: {
                alt28 = 2;
                break;
            }
            case 80: {
                alt28 = 3;
                break;
            }
            case 90: {
                alt28 = 4;
                break;
            }
            default: {
                if (this.state.backtracking > 0) {
                    this.state.failed = true;
                    return;
                }
                NoViableAltException nvae = new NoViableAltException("", 28, 0, this.input);
                throw nvae;
            }
        }
        switch (alt28) {
            case 1: {
                this.pushFollow(FOLLOW_block_in_ebnf1111);
                this.block();
                --this.state._fsp;
                if (!this.state.failed) break;
                return;
            }
            case 2: {
                this.match(this.input, 89, FOLLOW_OPTIONAL_in_ebnf1123);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return;
                }
                this.pushFollow(FOLLOW_block_in_ebnf1125);
                this.block();
                --this.state._fsp;
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) break;
                return;
            }
            case 3: {
                this.match(this.input, 80, FOLLOW_CLOSURE_in_ebnf1139);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return;
                }
                this.pushFollow(FOLLOW_block_in_ebnf1141);
                this.block();
                --this.state._fsp;
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) break;
                return;
            }
            case 4: {
                this.match(this.input, 90, FOLLOW_POSITIVE_CLOSURE_in_ebnf1155);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return;
                }
                this.pushFollow(FOLLOW_block_in_ebnf1157);
                this.block();
                --this.state._fsp;
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) break;
                return;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void block() throws RecognitionException {
        this.match(this.input, 78, FOLLOW_BLOCK_in_block1177);
        if (this.state.failed) {
            return;
        }
        this.match(this.input, 2, null);
        if (this.state.failed) {
            return;
        }
        int alt29 = 2;
        int LA29_0 = this.input.LA(1);
        if (LA29_0 == 4) {
            alt29 = 1;
        }
        switch (alt29) {
            case 1: {
                this.match(this.input, 4, FOLLOW_ACTION_in_block1179);
                if (!this.state.failed) break;
                return;
            }
        }
        int cnt30 = 0;
        block8: while (true) {
            int alt30 = 2;
            int LA30_0 = this.input.LA(1);
            if (LA30_0 == 74) {
                alt30 = 1;
            }
            switch (alt30) {
                case 1: {
                    this.pushFollow(FOLLOW_alternative_in_block1182);
                    this.alternative();
                    --this.state._fsp;
                    if (!this.state.failed) break;
                    return;
                }
                default: {
                    if (cnt30 >= 1) break block8;
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    EarlyExitException eee = new EarlyExitException(30, this.input);
                    throw eee;
                }
            }
            ++cnt30;
        }
        this.match(this.input, 3, null);
        if (this.state.failed) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void alternative() throws RecognitionException {
        this.match(this.input, 74, FOLLOW_ALT_in_alternative1199);
        if (this.state.failed) {
            return;
        }
        this.match(this.input, 2, null);
        if (this.state.failed) {
            return;
        }
        int alt31 = 2;
        int LA31_0 = this.input.LA(1);
        if (LA31_0 == 82) {
            alt31 = 1;
        }
        switch (alt31) {
            case 1: {
                this.pushFollow(FOLLOW_elementOptions_in_alternative1201);
                this.elementOptions();
                --this.state._fsp;
                if (!this.state.failed) break;
                return;
            }
        }
        int cnt32 = 0;
        block8: while (true) {
            int alt32 = 2;
            int LA32_0 = this.input.LA(1);
            if (LA32_0 == 4 || LA32_0 == 10 || LA32_0 == 20 || LA32_0 == 39 || LA32_0 == 46 || LA32_0 == 52 || LA32_0 == 57 || LA32_0 == 59 || LA32_0 == 62 || LA32_0 == 66 || LA32_0 == 78 || LA32_0 == 80 || LA32_0 == 83 || LA32_0 >= 89 && LA32_0 <= 90 || LA32_0 == 98 || LA32_0 == 100) {
                alt32 = 1;
            }
            switch (alt32) {
                case 1: {
                    this.pushFollow(FOLLOW_element_in_alternative1204);
                    this.element();
                    --this.state._fsp;
                    if (!this.state.failed) break;
                    return;
                }
                default: {
                    if (cnt32 >= 1) break block8;
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                    }
                    EarlyExitException eee = new EarlyExitException(32, this.input);
                    throw eee;
                }
            }
            ++cnt32;
        }
        this.match(this.input, 3, null);
        if (this.state.failed) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void atom() throws RecognitionException {
        int alt35 = 8;
        switch (this.input.LA(1)) {
            case 57: {
                alt35 = 1;
                break;
            }
            case 62: {
                int LA35_2 = this.input.LA(2);
                if (LA35_2 == 2) {
                    alt35 = 2;
                    break;
                }
                if (LA35_2 >= 3 && LA35_2 <= 4 || LA35_2 == 10 || LA35_2 == 20 || LA35_2 == 39 || LA35_2 == 46 || LA35_2 == 52 || LA35_2 == 57 || LA35_2 == 59 || LA35_2 == 62 || LA35_2 == 66 || LA35_2 == 78 || LA35_2 == 80 || LA35_2 == 83 || LA35_2 >= 89 && LA35_2 <= 90 || LA35_2 == 98 || LA35_2 == 100) {
                    alt35 = 3;
                    break;
                }
                if (this.state.backtracking > 0) {
                    this.state.failed = true;
                    return;
                }
                int nvaeMark = this.input.mark();
                try {
                    this.input.consume();
                    NoViableAltException nvae = new NoViableAltException("", 35, 2, this.input);
                    throw nvae;
                }
                catch (Throwable throwable) {
                    this.input.rewind(nvaeMark);
                    throw throwable;
                }
            }
            case 66: {
                int LA35_3 = this.input.LA(2);
                if (LA35_3 == 2) {
                    alt35 = 4;
                    break;
                }
                if (LA35_3 >= 3 && LA35_3 <= 4 || LA35_3 == 10 || LA35_3 == 20 || LA35_3 == 39 || LA35_3 == 46 || LA35_3 == 52 || LA35_3 == 57 || LA35_3 == 59 || LA35_3 == 62 || LA35_3 == 66 || LA35_3 == 78 || LA35_3 == 80 || LA35_3 == 83 || LA35_3 >= 89 && LA35_3 <= 90 || LA35_3 == 98 || LA35_3 == 100) {
                    alt35 = 5;
                    break;
                }
                if (this.state.backtracking > 0) {
                    this.state.failed = true;
                    return;
                }
                int nvaeMark = this.input.mark();
                try {
                    this.input.consume();
                    NoViableAltException nvae = new NoViableAltException("", 35, 3, this.input);
                    throw nvae;
                }
                catch (Throwable throwable) {
                    this.input.rewind(nvaeMark);
                    throw throwable;
                }
            }
            case 100: {
                int LA35_4 = this.input.LA(2);
                if (LA35_4 == 2) {
                    alt35 = 6;
                    break;
                }
                if (LA35_4 >= 3 && LA35_4 <= 4 || LA35_4 == 10 || LA35_4 == 20 || LA35_4 == 39 || LA35_4 == 46 || LA35_4 == 52 || LA35_4 == 57 || LA35_4 == 59 || LA35_4 == 62 || LA35_4 == 66 || LA35_4 == 78 || LA35_4 == 80 || LA35_4 == 83 || LA35_4 >= 89 && LA35_4 <= 90 || LA35_4 == 98 || LA35_4 == 100) {
                    alt35 = 7;
                    break;
                }
                if (this.state.backtracking > 0) {
                    this.state.failed = true;
                    return;
                }
                int nvaeMark = this.input.mark();
                try {
                    this.input.consume();
                    NoViableAltException nvae = new NoViableAltException("", 35, 4, this.input);
                    throw nvae;
                }
                catch (Throwable throwable) {
                    this.input.rewind(nvaeMark);
                    throw throwable;
                }
            }
            case 20: {
                alt35 = 8;
                break;
            }
            default: {
                if (this.state.backtracking > 0) {
                    this.state.failed = true;
                    return;
                }
                NoViableAltException nvae = new NoViableAltException("", 35, 0, this.input);
                throw nvae;
            }
        }
        switch (alt35) {
            case 1: {
                this.match(this.input, 57, FOLLOW_RULE_REF_in_atom1221);
                if (this.state.failed) {
                    return;
                }
                if (this.input.LA(1) != 2) break;
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return;
                }
                int alt33 = 2;
                int LA33_0 = this.input.LA(1);
                if (LA33_0 == 8) {
                    alt33 = 1;
                }
                switch (alt33) {
                    case 1: {
                        this.match(this.input, 8, FOLLOW_ARG_ACTION_in_atom1223);
                        if (!this.state.failed) break;
                        return;
                    }
                }
                int alt34 = 2;
                int LA34_0 = this.input.LA(1);
                if (LA34_0 == 82) {
                    alt34 = 1;
                }
                switch (alt34) {
                    case 1: {
                        this.pushFollow(FOLLOW_elementOptions_in_atom1226);
                        this.elementOptions();
                        --this.state._fsp;
                        if (!this.state.failed) break;
                        return;
                    }
                }
                this.match(this.input, 3, null);
                if (this.state.failed) {
                    return;
                }
                break;
            }
            case 2: {
                this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_atom1238);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return;
                }
                this.pushFollow(FOLLOW_elementOptions_in_atom1240);
                this.elementOptions();
                --this.state._fsp;
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) break;
                return;
            }
            case 3: {
                this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_atom1246);
                if (!this.state.failed) break;
                return;
            }
            case 4: {
                this.match(this.input, 66, FOLLOW_TOKEN_REF_in_atom1255);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return;
                }
                this.pushFollow(FOLLOW_elementOptions_in_atom1257);
                this.elementOptions();
                --this.state._fsp;
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) break;
                return;
            }
            case 5: {
                this.match(this.input, 66, FOLLOW_TOKEN_REF_in_atom1263);
                if (!this.state.failed) break;
                return;
            }
            case 6: {
                this.match(this.input, 100, FOLLOW_WILDCARD_in_atom1272);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return;
                }
                this.pushFollow(FOLLOW_elementOptions_in_atom1274);
                this.elementOptions();
                --this.state._fsp;
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) break;
                return;
            }
            case 7: {
                this.match(this.input, 100, FOLLOW_WILDCARD_in_atom1280);
                if (!this.state.failed) break;
                return;
            }
            case 8: {
                this.match(this.input, 20, FOLLOW_DOT_in_atom1286);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 28, FOLLOW_ID_in_atom1288);
                if (this.state.failed) {
                    return;
                }
                this.pushFollow(FOLLOW_element_in_atom1290);
                this.element();
                --this.state._fsp;
                if (this.state.failed) {
                    return;
                }
                this.match(this.input, 3, null);
                if (!this.state.failed) break;
                return;
            }
        }
    }

    public final void synpred1_LeftRecursiveRuleWalker_fragment() throws RecognitionException {
        this.pushFollow(FOLLOW_binary_in_synpred1_LeftRecursiveRuleWalker348);
        this.binary();
        --this.state._fsp;
        if (this.state.failed) {
            return;
        }
    }

    public final void synpred2_LeftRecursiveRuleWalker_fragment() throws RecognitionException {
        this.pushFollow(FOLLOW_prefix_in_synpred2_LeftRecursiveRuleWalker404);
        this.prefix();
        --this.state._fsp;
        if (this.state.failed) {
            return;
        }
    }

    public final void synpred3_LeftRecursiveRuleWalker_fragment() throws RecognitionException {
        this.pushFollow(FOLLOW_suffix_in_synpred3_LeftRecursiveRuleWalker460);
        this.suffix();
        --this.state._fsp;
        if (this.state.failed) {
            return;
        }
    }

    public final boolean synpred2_LeftRecursiveRuleWalker() {
        ++this.state.backtracking;
        int start = this.input.mark();
        try {
            this.synpred2_LeftRecursiveRuleWalker_fragment();
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

    public final boolean synpred1_LeftRecursiveRuleWalker() {
        ++this.state.backtracking;
        int start = this.input.mark();
        try {
            this.synpred1_LeftRecursiveRuleWalker_fragment();
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

    public final boolean synpred3_LeftRecursiveRuleWalker() {
        ++this.state.backtracking;
        int start = this.input.mark();
        try {
            this.synpred3_LeftRecursiveRuleWalker_fragment();
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
        tokenNames = new String[]{"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ACTION", "ACTION_CHAR_LITERAL", "ACTION_ESC", "ACTION_STRING_LITERAL", "ARG_ACTION", "ARG_OR_CHARSET", "ASSIGN", "AT", "CATCH", "CHANNELS", "COLON", "COLONCOLON", "COMMA", "COMMENT", "DOC_COMMENT", "DOLLAR", "DOT", "ERRCHAR", "ESC_SEQ", "FINALLY", "FRAGMENT", "GRAMMAR", "GT", "HEX_DIGIT", "ID", "IMPORT", "INT", "LEXER", "LEXER_CHAR_SET", "LOCALS", "LPAREN", "LT", "MODE", "NESTED_ACTION", "NLCHARS", "NOT", "NameChar", "NameStartChar", "OPTIONS", "OR", "PARSER", "PLUS", "PLUS_ASSIGN", "POUND", "PRIVATE", "PROTECTED", "PUBLIC", "QUESTION", "RANGE", "RARROW", "RBRACE", "RETURNS", "RPAREN", "RULE_REF", "SEMI", "SEMPRED", "SRC", "STAR", "STRING_LITERAL", "SYNPRED", "THROWS", "TOKENS_SPEC", "TOKEN_REF", "TREE_GRAMMAR", "UNICODE_ESC", "UNICODE_EXTENDED_ESC", "UnicodeBOM", "WS", "WSCHARS", "WSNLCHARS", "ALT", "ALTLIST", "ARG", "ARGLIST", "BLOCK", "CHAR_RANGE", "CLOSURE", "COMBINED", "ELEMENT_OPTIONS", "EPSILON", "INITACTION", "LABEL", "LEXER_ACTION_CALL", "LEXER_ALT_ACTION", "LIST", "OPTIONAL", "POSITIVE_CLOSURE", "PREC_RULE", "RESULT", "RET", "RULE", "RULEACTIONS", "RULEMODIFIERS", "RULES", "SET", "TEMPLATE", "WILDCARD"};
        DFA11_transitionS = new String[]{"\u0001\u0004\u0005\uffff\u0001\u0001\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0002\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0003\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0004\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001\u0005", "\u0001\u0006", "\u0001\u0004\u0001\n\u0001\u0007\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\b\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\t\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "", "\u0001\u000b", "\u0001\f", "\u0001\r\u0001\n\u0001\u0007\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\b\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\t\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001\u000e\u0001\n\u0001\u0007\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\b\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\t\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001\n\u0001\u0007\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\b\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\t\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "", "\u0001\u0004\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u000f\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0004\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001\u0004\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0010\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0004\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001\u0011", "\u0001\u0012", "\u0001\u0004\u0001\u0013", "\u0001\u0004\u0001\u0014", "\u0001\u0015", "\u0001\u0016", "\u0001\n\u0001\u0017\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\u0018\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0019\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001\n\u0001\u0017\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\u0018\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0019\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001\u001c\u0006\uffff\u0001\u001b\u0011\uffff\u0001\u001a", "\u0001\u001f\u0006\uffff\u0001\u001e\u0011\uffff\u0001\u001d", "\u0001 \u0001\n\u0001\u0017\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\u0018\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0019\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001!\u0001\n\u0001\u0017\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\u0018\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0019\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001\n\u0001\u0017\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\u0018\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0019\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001\u001c\u0006\uffff\u0001\u001b\u0011\uffff\u0001\u001a", "\u0001\"", "\u0001#", "\u0001\u001f\u0006\uffff\u0001\u001e\u0011\uffff\u0001\u001d", "\u0001$", "\u0001%", "\u0001&", "\u0001'", "\u0001(", "\u0001\n\u0001\u0007\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\b\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\t\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001)", "\u0001\n\u0001\u0007\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\b\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\t\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001*", "\u0001+", "\u0001.\u0017\uffff\u0001,\u0001\uffff\u0001/\u001f\uffff\u0001-", "\u00012\u0017\uffff\u00010\u0001\uffff\u00013\u001f\uffff\u00011", "\u00016\u0006\uffff\u00015\u0011\uffff\u00014", "\u00019\u0006\uffff\u00018\u0011\uffff\u00017", "\u0001:", "\u0001;", "\u0001<", "\u0001=", "\u0001>", "\u0001?", "\u0001@", "\u0001A", "\u00016\u0006\uffff\u00015\u0011\uffff\u00014", "\u0001B", "\u0001C", "\u00019\u0006\uffff\u00018\u0011\uffff\u00017", "\u0001D", "\u0001E", "\u0001\u001c\u0006\uffff\u0001\u001b\u0011\uffff\u0001\u001a", "\u0001\u001c\u0006\uffff\u0001\u001b\u0011\uffff\u0001\u001a", "\u0001\u001c\u0006\uffff\u0001\u001b\u0011\uffff\u0001\u001a", "\u0001\u001c\u0006\uffff\u0001\u001b\u0011\uffff\u0001\u001a", "\u0001\u001f\u0006\uffff\u0001\u001e\u0011\uffff\u0001\u001d", "\u0001\u001f\u0006\uffff\u0001\u001e\u0011\uffff\u0001\u001d", "\u0001\u001f\u0006\uffff\u0001\u001e\u0011\uffff\u0001\u001d", "\u0001\u001f\u0006\uffff\u0001\u001e\u0011\uffff\u0001\u001d", "\u0001F", "\u0001\n\u0001\u0017\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\u0018\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0019\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001G", "\u0001\n\u0001\u0017\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\u0018\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0019\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001J\u0017\uffff\u0001H\u0001\uffff\u0001K\u001f\uffff\u0001I", "\u0001N\u0017\uffff\u0001L\u0001\uffff\u0001O\u001f\uffff\u0001M", "\u0001P", "\u0001Q", "\u0001R", "\u0001S", "\u0001T", "\u0001U", "\u0001V", "\u0001W", "\u00016\u0006\uffff\u00015\u0011\uffff\u00014", "\u00016\u0006\uffff\u00015\u0011\uffff\u00014", "\u00016\u0006\uffff\u00015\u0011\uffff\u00014", "\u00016\u0006\uffff\u00015\u0011\uffff\u00014", "\u00019\u0006\uffff\u00018\u0011\uffff\u00017", "\u00019\u0006\uffff\u00018\u0011\uffff\u00017", "\u00019\u0006\uffff\u00018\u0011\uffff\u00017", "\u00019\u0006\uffff\u00018\u0011\uffff\u00017"};
        DFA11_eot = DFA.unpackEncodedString("X\uffff");
        DFA11_eof = DFA.unpackEncodedString("X\uffff");
        DFA11_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u0004\u0003\u0002\u0001\uffff\u0002\u001c\u0002\u0002\u0001\u0003\u0001\uffff\u0002\u0004\u0002R\u0004\u0002\u0004\u0003\u0002\u0002\u0002\u0003\u0001\u0002\u0002\u0003\u0001\u0002\u0001\u0003\u0002R\u0001\u001c\u0001\u0003\u0001\u001c\u0001\u0003\u0002\u0002\u0002\u0004\u000b\u0003\u0001\u0002\u0002\u0003\u0001\u0002\t\u0003\u0001\u001c\u0001\u0003\u0001\u001c\u0001\u0003\u0002\u0004\u0010\u0003");
        DFA11_max = DFA.unpackEncodedStringToUnsignedChars("\u0001d\u0002\u0002\u0001d\u0001\uffff\u0002\u001c\u0003d\u0001\uffff\u0002d\u0002R\u0002\u0003\u0002\u0002\u0002d\u0002\u001c\u0003d\u0001\u001c\u0001\u0002\u0001\u0003\u0001\u001c\u0001\u0002\u0001\u0003\u0002R\u0001\u001c\u0001d\u0001\u001c\u0001d\u0002\u0002\u0002>\u0002\u001c\b\u0003\u0001\u001c\u0001\u0002\u0001\u0003\u0001\u001c\u0001\u0002\u0001\u0003\t\u001c\u0001d\u0001\u001c\u0001d\u0002>\b\u0003\b\u001c");
        DFA11_accept = DFA.unpackEncodedString("\u0004\uffff\u0001\u0001\u0005\uffff\u0001\u0002M\uffff");
        DFA11_special = DFA.unpackEncodedString("X\uffff}>");
        int numStates = DFA11_transitionS.length;
        DFA11_transition = new short[numStates][];
        for (i = 0; i < numStates; ++i) {
            LeftRecursiveRuleWalker.DFA11_transition[i] = DFA.unpackEncodedString(DFA11_transitionS[i]);
        }
        DFA14_transitionS = new String[]{"\u0001\u0004\u0005\uffff\u0001\u0001\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0002\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0003\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0004\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001\u0005", "\u0001\u0006", "\u0001\u0004\u0001\n\u0001\u0007\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\b\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\t\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "", "\u0001\u000b", "\u0001\f", "\u0001\r\u0001\n\u0001\u0007\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\b\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\t\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001\u000e\u0001\n\u0001\u0007\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\b\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\t\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001\n\u0001\u0007\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\b\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\t\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "", "\u0001\u0004\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u000f\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0004\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001\u0004\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0010\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0004\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001\u0011", "\u0001\u0012", "\u0001\u0004\u0001\u0013", "\u0001\u0004\u0001\u0014", "\u0001\u0015", "\u0001\u0016", "\u0001\n\u0001\u0017\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\u0018\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0019\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001\n\u0001\u0017\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\u0018\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0019\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001\u001c\u0006\uffff\u0001\u001b\u0011\uffff\u0001\u001a", "\u0001\u001f\u0006\uffff\u0001\u001e\u0011\uffff\u0001\u001d", "\u0001 \u0001\n\u0001\u0017\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\u0018\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0019\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001!\u0001\n\u0001\u0017\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\u0018\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0019\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001\n\u0001\u0017\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\u0018\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0019\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001\u001c\u0006\uffff\u0001\u001b\u0011\uffff\u0001\u001a", "\u0001\"", "\u0001#", "\u0001\u001f\u0006\uffff\u0001\u001e\u0011\uffff\u0001\u001d", "\u0001$", "\u0001%", "\u0001&", "\u0001'", "\u0001(", "\u0001\n\u0001\u0007\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\b\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\t\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001)", "\u0001\n\u0001\u0007\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\b\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\t\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001*", "\u0001+", "\u0001.\u0017\uffff\u0001,\u0001\uffff\u0001/\u001f\uffff\u0001-", "\u00012\u0017\uffff\u00010\u0001\uffff\u00013\u001f\uffff\u00011", "\u00016\u0006\uffff\u00015\u0011\uffff\u00014", "\u00019\u0006\uffff\u00018\u0011\uffff\u00017", "\u0001:", "\u0001;", "\u0001<", "\u0001=", "\u0001>", "\u0001?", "\u0001@", "\u0001A", "\u00016\u0006\uffff\u00015\u0011\uffff\u00014", "\u0001B", "\u0001C", "\u00019\u0006\uffff\u00018\u0011\uffff\u00017", "\u0001D", "\u0001E", "\u0001\u001c\u0006\uffff\u0001\u001b\u0011\uffff\u0001\u001a", "\u0001\u001c\u0006\uffff\u0001\u001b\u0011\uffff\u0001\u001a", "\u0001\u001c\u0006\uffff\u0001\u001b\u0011\uffff\u0001\u001a", "\u0001\u001c\u0006\uffff\u0001\u001b\u0011\uffff\u0001\u001a", "\u0001\u001f\u0006\uffff\u0001\u001e\u0011\uffff\u0001\u001d", "\u0001\u001f\u0006\uffff\u0001\u001e\u0011\uffff\u0001\u001d", "\u0001\u001f\u0006\uffff\u0001\u001e\u0011\uffff\u0001\u001d", "\u0001\u001f\u0006\uffff\u0001\u001e\u0011\uffff\u0001\u001d", "\u0001F", "\u0001\n\u0001\u0017\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\u0018\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0019\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001G", "\u0001\n\u0001\u0017\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\u0018\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0019\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001J\u0017\uffff\u0001H\u0001\uffff\u0001K\u001f\uffff\u0001I", "\u0001N\u0017\uffff\u0001L\u0001\uffff\u0001O\u001f\uffff\u0001M", "\u0001P", "\u0001Q", "\u0001R", "\u0001S", "\u0001T", "\u0001U", "\u0001V", "\u0001W", "\u00016\u0006\uffff\u00015\u0011\uffff\u00014", "\u00016\u0006\uffff\u00015\u0011\uffff\u00014", "\u00016\u0006\uffff\u00015\u0011\uffff\u00014", "\u00016\u0006\uffff\u00015\u0011\uffff\u00014", "\u00019\u0006\uffff\u00018\u0011\uffff\u00017", "\u00019\u0006\uffff\u00018\u0011\uffff\u00017", "\u00019\u0006\uffff\u00018\u0011\uffff\u00017", "\u00019\u0006\uffff\u00018\u0011\uffff\u00017"};
        DFA14_eot = DFA.unpackEncodedString("X\uffff");
        DFA14_eof = DFA.unpackEncodedString("X\uffff");
        DFA14_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u0004\u0003\u0002\u0001\uffff\u0002\u001c\u0002\u0002\u0001\u0003\u0001\uffff\u0002\u0004\u0002R\u0004\u0002\u0004\u0003\u0002\u0002\u0002\u0003\u0001\u0002\u0002\u0003\u0001\u0002\u0001\u0003\u0002R\u0001\u001c\u0001\u0003\u0001\u001c\u0001\u0003\u0002\u0002\u0002\u0004\u000b\u0003\u0001\u0002\u0002\u0003\u0001\u0002\t\u0003\u0001\u001c\u0001\u0003\u0001\u001c\u0001\u0003\u0002\u0004\u0010\u0003");
        DFA14_max = DFA.unpackEncodedStringToUnsignedChars("\u0001d\u0002\u0002\u0001d\u0001\uffff\u0002\u001c\u0003d\u0001\uffff\u0002d\u0002R\u0002\u0003\u0002\u0002\u0002d\u0002\u001c\u0003d\u0001\u001c\u0001\u0002\u0001\u0003\u0001\u001c\u0001\u0002\u0001\u0003\u0002R\u0001\u001c\u0001d\u0001\u001c\u0001d\u0002\u0002\u0002>\u0002\u001c\b\u0003\u0001\u001c\u0001\u0002\u0001\u0003\u0001\u001c\u0001\u0002\u0001\u0003\t\u001c\u0001d\u0001\u001c\u0001d\u0002>\b\u0003\b\u001c");
        DFA14_accept = DFA.unpackEncodedString("\u0004\uffff\u0001\u0001\u0005\uffff\u0001\u0002M\uffff");
        DFA14_special = DFA.unpackEncodedString("X\uffff}>");
        numStates = DFA14_transitionS.length;
        DFA14_transition = new short[numStates][];
        for (i = 0; i < numStates; ++i) {
            LeftRecursiveRuleWalker.DFA14_transition[i] = DFA.unpackEncodedString(DFA14_transitionS[i]);
        }
        FOLLOW_RULE_in_rec_rule72 = new BitSet(new long[]{4L});
        FOLLOW_RULE_REF_in_rec_rule76 = new BitSet(new long[]{38003528492386304L, 16384L});
        FOLLOW_ruleModifier_in_rec_rule83 = new BitSet(new long[]{36033203655411712L, 16384L});
        FOLLOW_RETURNS_in_rec_rule92 = new BitSet(new long[]{4L});
        FOLLOW_ARG_ACTION_in_rec_rule96 = new BitSet(new long[]{8L});
        FOLLOW_LOCALS_in_rec_rule115 = new BitSet(new long[]{4L});
        FOLLOW_ARG_ACTION_in_rec_rule117 = new BitSet(new long[]{8L});
        FOLLOW_OPTIONS_in_rec_rule135 = new BitSet(new long[]{4L});
        FOLLOW_AT_in_rec_rule152 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_rec_rule154 = new BitSet(new long[]{16L});
        FOLLOW_ACTION_in_rec_rule156 = new BitSet(new long[]{8L});
        FOLLOW_ruleBlock_in_rec_rule172 = new BitSet(new long[]{0x801008L});
        FOLLOW_exceptionGroup_in_rec_rule179 = new BitSet(new long[]{8L});
        FOLLOW_exceptionHandler_in_exceptionGroup197 = new BitSet(new long[]{8392706L});
        FOLLOW_finallyClause_in_exceptionGroup200 = new BitSet(new long[]{2L});
        FOLLOW_CATCH_in_exceptionHandler216 = new BitSet(new long[]{4L});
        FOLLOW_ARG_ACTION_in_exceptionHandler218 = new BitSet(new long[]{16L});
        FOLLOW_ACTION_in_exceptionHandler220 = new BitSet(new long[]{8L});
        FOLLOW_FINALLY_in_finallyClause233 = new BitSet(new long[]{4L});
        FOLLOW_ACTION_in_finallyClause235 = new BitSet(new long[]{8L});
        FOLLOW_BLOCK_in_ruleBlock290 = new BitSet(new long[]{4L});
        FOLLOW_outerAlternative_in_ruleBlock303 = new BitSet(new long[]{8L, 1024L});
        FOLLOW_binary_in_outerAlternative362 = new BitSet(new long[]{2L});
        FOLLOW_prefix_in_outerAlternative418 = new BitSet(new long[]{2L});
        FOLLOW_suffix_in_outerAlternative474 = new BitSet(new long[]{2L});
        FOLLOW_nonLeftRecur_in_outerAlternative515 = new BitSet(new long[]{2L});
        FOLLOW_ALT_in_binary541 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_binary543 = new BitSet(new long[]{0x200400000000400L});
        FOLLOW_recurse_in_binary546 = new BitSet(new long[]{5336836476935078928L, 86000615428L});
        FOLLOW_element_in_binary548 = new BitSet(new long[]{5336836476935078928L, 86000615428L});
        FOLLOW_recurse_in_binary551 = new BitSet(new long[]{0x800000000000018L, 524288L});
        FOLLOW_epsilonElement_in_binary553 = new BitSet(new long[]{0x800000000000018L, 524288L});
        FOLLOW_ALT_in_prefix579 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_prefix581 = new BitSet(new long[]{5336836476935078928L, 86000615428L});
        FOLLOW_element_in_prefix587 = new BitSet(new long[]{5336836476935078928L, 86000615428L});
        FOLLOW_recurse_in_prefix593 = new BitSet(new long[]{0x800000000000018L, 524288L});
        FOLLOW_epsilonElement_in_prefix595 = new BitSet(new long[]{0x800000000000018L, 524288L});
        FOLLOW_ALT_in_suffix630 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_suffix632 = new BitSet(new long[]{0x200400000000400L});
        FOLLOW_recurse_in_suffix635 = new BitSet(new long[]{5336836476935078928L, 86000615428L});
        FOLLOW_element_in_suffix637 = new BitSet(new long[]{5336836476935078936L, 86000615428L});
        FOLLOW_ALT_in_nonLeftRecur671 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_nonLeftRecur673 = new BitSet(new long[]{5336836476935078928L, 86000615428L});
        FOLLOW_element_in_nonLeftRecur676 = new BitSet(new long[]{5336836476935078936L, 86000615428L});
        FOLLOW_ASSIGN_in_recurse693 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_recurse695 = new BitSet(new long[]{0x200000000000000L});
        FOLLOW_recurseNoLabel_in_recurse697 = new BitSet(new long[]{8L});
        FOLLOW_PLUS_ASSIGN_in_recurse704 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_recurse706 = new BitSet(new long[]{0x200000000000000L});
        FOLLOW_recurseNoLabel_in_recurse708 = new BitSet(new long[]{8L});
        FOLLOW_recurseNoLabel_in_recurse714 = new BitSet(new long[]{2L});
        FOLLOW_RULE_REF_in_recurseNoLabel726 = new BitSet(new long[]{2L});
        FOLLOW_ASSIGN_in_token740 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_token742 = new BitSet(new long[]{0x4000400000000400L, 4L});
        FOLLOW_token_in_token746 = new BitSet(new long[]{8L});
        FOLLOW_PLUS_ASSIGN_in_token755 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_token757 = new BitSet(new long[]{0x4000400000000400L, 4L});
        FOLLOW_token_in_token761 = new BitSet(new long[]{8L});
        FOLLOW_STRING_LITERAL_in_token771 = new BitSet(new long[]{2L});
        FOLLOW_STRING_LITERAL_in_token792 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_token794 = new BitSet(new long[]{8L});
        FOLLOW_TOKEN_REF_in_token809 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_token811 = new BitSet(new long[]{8L});
        FOLLOW_TOKEN_REF_in_token823 = new BitSet(new long[]{2L});
        FOLLOW_ELEMENT_OPTIONS_in_elementOptions853 = new BitSet(new long[]{4L});
        FOLLOW_elementOption_in_elementOptions855 = new BitSet(new long[]{268436488L});
        FOLLOW_ID_in_elementOption874 = new BitSet(new long[]{2L});
        FOLLOW_ASSIGN_in_elementOption885 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_elementOption887 = new BitSet(new long[]{0x10000000L});
        FOLLOW_ID_in_elementOption889 = new BitSet(new long[]{8L});
        FOLLOW_ASSIGN_in_elementOption901 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_elementOption903 = new BitSet(new long[]{0x4000000000000000L});
        FOLLOW_STRING_LITERAL_in_elementOption905 = new BitSet(new long[]{8L});
        FOLLOW_ASSIGN_in_elementOption917 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_elementOption919 = new BitSet(new long[]{16L});
        FOLLOW_ACTION_in_elementOption921 = new BitSet(new long[]{8L});
        FOLLOW_ASSIGN_in_elementOption933 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_elementOption935 = new BitSet(new long[]{0x40000000L});
        FOLLOW_INT_in_elementOption937 = new BitSet(new long[]{8L});
        FOLLOW_atom_in_element952 = new BitSet(new long[]{2L});
        FOLLOW_NOT_in_element958 = new BitSet(new long[]{4L});
        FOLLOW_element_in_element960 = new BitSet(new long[]{8L});
        FOLLOW_RANGE_in_element967 = new BitSet(new long[]{4L});
        FOLLOW_atom_in_element969 = new BitSet(new long[]{4755801206504292352L, 0x1000000004L});
        FOLLOW_atom_in_element971 = new BitSet(new long[]{8L});
        FOLLOW_ASSIGN_in_element978 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_element980 = new BitSet(new long[]{5336836476935078928L, 86000615428L});
        FOLLOW_element_in_element982 = new BitSet(new long[]{8L});
        FOLLOW_PLUS_ASSIGN_in_element989 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_element991 = new BitSet(new long[]{5336836476935078928L, 86000615428L});
        FOLLOW_element_in_element993 = new BitSet(new long[]{8L});
        FOLLOW_SET_in_element1003 = new BitSet(new long[]{4L});
        FOLLOW_setElement_in_element1005 = new BitSet(new long[]{0x4000000000000008L, 4L});
        FOLLOW_RULE_REF_in_element1017 = new BitSet(new long[]{2L});
        FOLLOW_ebnf_in_element1022 = new BitSet(new long[]{2L});
        FOLLOW_epsilonElement_in_element1027 = new BitSet(new long[]{2L});
        FOLLOW_ACTION_in_epsilonElement1038 = new BitSet(new long[]{2L});
        FOLLOW_SEMPRED_in_epsilonElement1043 = new BitSet(new long[]{2L});
        FOLLOW_EPSILON_in_epsilonElement1048 = new BitSet(new long[]{2L});
        FOLLOW_ACTION_in_epsilonElement1054 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_epsilonElement1056 = new BitSet(new long[]{8L});
        FOLLOW_SEMPRED_in_epsilonElement1063 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_epsilonElement1065 = new BitSet(new long[]{8L});
        FOLLOW_STRING_LITERAL_in_setElement1078 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_setElement1080 = new BitSet(new long[]{8L});
        FOLLOW_TOKEN_REF_in_setElement1087 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_setElement1089 = new BitSet(new long[]{8L});
        FOLLOW_STRING_LITERAL_in_setElement1095 = new BitSet(new long[]{2L});
        FOLLOW_TOKEN_REF_in_setElement1100 = new BitSet(new long[]{2L});
        FOLLOW_block_in_ebnf1111 = new BitSet(new long[]{2L});
        FOLLOW_OPTIONAL_in_ebnf1123 = new BitSet(new long[]{4L});
        FOLLOW_block_in_ebnf1125 = new BitSet(new long[]{8L});
        FOLLOW_CLOSURE_in_ebnf1139 = new BitSet(new long[]{4L});
        FOLLOW_block_in_ebnf1141 = new BitSet(new long[]{8L});
        FOLLOW_POSITIVE_CLOSURE_in_ebnf1155 = new BitSet(new long[]{4L});
        FOLLOW_block_in_ebnf1157 = new BitSet(new long[]{8L});
        FOLLOW_BLOCK_in_block1177 = new BitSet(new long[]{4L});
        FOLLOW_ACTION_in_block1179 = new BitSet(new long[]{0L, 1024L});
        FOLLOW_alternative_in_block1182 = new BitSet(new long[]{8L, 1024L});
        FOLLOW_ALT_in_alternative1199 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_alternative1201 = new BitSet(new long[]{5336836476935078928L, 86000615428L});
        FOLLOW_element_in_alternative1204 = new BitSet(new long[]{5336836476935078936L, 86000615428L});
        FOLLOW_RULE_REF_in_atom1221 = new BitSet(new long[]{4L});
        FOLLOW_ARG_ACTION_in_atom1223 = new BitSet(new long[]{8L, 262144L});
        FOLLOW_elementOptions_in_atom1226 = new BitSet(new long[]{8L});
        FOLLOW_STRING_LITERAL_in_atom1238 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_atom1240 = new BitSet(new long[]{8L});
        FOLLOW_STRING_LITERAL_in_atom1246 = new BitSet(new long[]{2L});
        FOLLOW_TOKEN_REF_in_atom1255 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_atom1257 = new BitSet(new long[]{8L});
        FOLLOW_TOKEN_REF_in_atom1263 = new BitSet(new long[]{2L});
        FOLLOW_WILDCARD_in_atom1272 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_atom1274 = new BitSet(new long[]{8L});
        FOLLOW_WILDCARD_in_atom1280 = new BitSet(new long[]{2L});
        FOLLOW_DOT_in_atom1286 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_atom1288 = new BitSet(new long[]{5336836476935078928L, 86000615428L});
        FOLLOW_element_in_atom1290 = new BitSet(new long[]{8L});
        FOLLOW_binary_in_synpred1_LeftRecursiveRuleWalker348 = new BitSet(new long[]{2L});
        FOLLOW_prefix_in_synpred2_LeftRecursiveRuleWalker404 = new BitSet(new long[]{2L});
        FOLLOW_suffix_in_synpred3_LeftRecursiveRuleWalker460 = new BitSet(new long[]{2L});
    }

    protected class DFA14
    extends DFA {
        public DFA14(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 14;
            this.eot = DFA14_eot;
            this.eof = DFA14_eof;
            this.min = DFA14_min;
            this.max = DFA14_max;
            this.accept = DFA14_accept;
            this.special = DFA14_special;
            this.transition = DFA14_transition;
        }

        @Override
        public String getDescription() {
            return "()+ loopback of 106:4: ( element )+";
        }
    }

    protected class DFA11
    extends DFA {
        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = DFA11_eot;
            this.eof = DFA11_eof;
            this.min = DFA11_min;
            this.max = DFA11_max;
            this.accept = DFA11_accept;
            this.special = DFA11_special;
            this.transition = DFA11_transition;
        }

        @Override
        public String getDescription() {
            return "()* loopback of 100:35: ( element )*";
        }
    }

    public static class outerAlternative_return
    extends TreeRuleReturnScope {
        public boolean isLeftRec;
    }

    public static class ruleBlock_return
    extends TreeRuleReturnScope {
        public boolean isLeftRec;
    }
}

