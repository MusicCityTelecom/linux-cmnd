/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen;

import groovyjarjarantlr4.runtime.BaseRecognizer;
import groovyjarjarantlr4.runtime.BitSet;
import groovyjarjarantlr4.runtime.DFA;
import groovyjarjarantlr4.runtime.EarlyExitException;
import groovyjarjarantlr4.runtime.NoViableAltException;
import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.RecognizerSharedState;
import groovyjarjarantlr4.runtime.tree.TreeNodeStream;
import groovyjarjarantlr4.runtime.tree.TreeParser;
import groovyjarjarantlr4.runtime.tree.TreeRuleReturnScope;
import groovyjarjarantlr4.v4.codegen.DefaultOutputModelFactory;
import groovyjarjarantlr4.v4.codegen.OutputModelController;
import groovyjarjarantlr4.v4.codegen.model.Choice;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForAlt;
import groovyjarjarantlr4.v4.codegen.model.PlusBlock;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import groovyjarjarantlr4.v4.codegen.model.StarBlock;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.AltAST;
import groovyjarjarantlr4.v4.tool.ast.BlockAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.ArrayList;
import java.util.List;

public class SourceGenTriggers
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
    public OutputModelController controller;
    public boolean hasLookaheadBlock;
    protected DFA7 dfa7 = new DFA7(this);
    static final String DFA7_eotS = "\u0014\uffff";
    static final String DFA7_eofS = "\u0014\uffff";
    static final String DFA7_minS = "\u0001J\u0001\u0002\u0001\u0004\u0001\u0002\u0002\uffff\u0001\n\u0001\u0003\u0001\u0002\u0001\u0004\u0001\u001c\u0001\u0004\b\u0003";
    static final String DFA7_maxS = "\u0001J\u0001\u0002\u0001d\u0001\u0002\u0002\uffff\u0002\u001c\u0001\u0002\u0001d\u0001\u001c\u0001>\u0004\u0003\u0004\u001c";
    static final String DFA7_acceptS = "\u0004\uffff\u0001\u0001\u0001\u0002\u000e\uffff";
    static final String DFA7_specialS = "\u0014\uffff}>";
    static final String[] DFA7_transitionS = new String[]{"\u0001\u0001", "\u0001\u0002", "\u0001\u0004\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0001\uffff\u0001\u0003\u0001\u0005\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001\u0006", "", "", "\u0001\b\u0011\uffff\u0001\u0007", "\u0001\t\u0006\uffff\u0001\b\u0011\uffff\u0001\u0007", "\u0001\n", "\u0001\u0004\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0005\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001\u000b", "\u0001\u000e\u0017\uffff\u0001\f\u0001\uffff\u0001\u000f\u001f\uffff\u0001\r", "\u0001\u0010", "\u0001\u0011", "\u0001\u0012", "\u0001\u0013", "\u0001\t\u0006\uffff\u0001\b\u0011\uffff\u0001\u0007", "\u0001\t\u0006\uffff\u0001\b\u0011\uffff\u0001\u0007", "\u0001\t\u0006\uffff\u0001\b\u0011\uffff\u0001\u0007", "\u0001\t\u0006\uffff\u0001\b\u0011\uffff\u0001\u0007"};
    static final short[] DFA7_eot = DFA.unpackEncodedString("\u0014\uffff");
    static final short[] DFA7_eof = DFA.unpackEncodedString("\u0014\uffff");
    static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars("\u0001J\u0001\u0002\u0001\u0004\u0001\u0002\u0002\uffff\u0001\n\u0001\u0003\u0001\u0002\u0001\u0004\u0001\u001c\u0001\u0004\b\u0003");
    static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars("\u0001J\u0001\u0002\u0001d\u0001\u0002\u0002\uffff\u0002\u001c\u0001\u0002\u0001d\u0001\u001c\u0001>\u0004\u0003\u0004\u001c");
    static final short[] DFA7_accept = DFA.unpackEncodedString("\u0004\uffff\u0001\u0001\u0001\u0002\u000e\uffff");
    static final short[] DFA7_special = DFA.unpackEncodedString("\u0014\uffff}>");
    static final short[][] DFA7_transition;
    public static final BitSet FOLLOW_block_in_dummy61;
    public static final BitSet FOLLOW_BLOCK_in_block84;
    public static final BitSet FOLLOW_OPTIONS_in_block88;
    public static final BitSet FOLLOW_alternative_in_block109;
    public static final BitSet FOLLOW_alt_in_alternative161;
    public static final BitSet FOLLOW_ALT_in_alt191;
    public static final BitSet FOLLOW_elementOptions_in_alt193;
    public static final BitSet FOLLOW_element_in_alt198;
    public static final BitSet FOLLOW_ALT_in_alt212;
    public static final BitSet FOLLOW_elementOptions_in_alt214;
    public static final BitSet FOLLOW_EPSILON_in_alt217;
    public static final BitSet FOLLOW_labeledElement_in_element246;
    public static final BitSet FOLLOW_atom_in_element257;
    public static final BitSet FOLLOW_subrule_in_element267;
    public static final BitSet FOLLOW_ACTION_in_element282;
    public static final BitSet FOLLOW_SEMPRED_in_element297;
    public static final BitSet FOLLOW_ACTION_in_element311;
    public static final BitSet FOLLOW_elementOptions_in_element313;
    public static final BitSet FOLLOW_SEMPRED_in_element325;
    public static final BitSet FOLLOW_elementOptions_in_element327;
    public static final BitSet FOLLOW_ASSIGN_in_labeledElement347;
    public static final BitSet FOLLOW_ID_in_labeledElement349;
    public static final BitSet FOLLOW_atom_in_labeledElement351;
    public static final BitSet FOLLOW_PLUS_ASSIGN_in_labeledElement364;
    public static final BitSet FOLLOW_ID_in_labeledElement366;
    public static final BitSet FOLLOW_atom_in_labeledElement368;
    public static final BitSet FOLLOW_ASSIGN_in_labeledElement379;
    public static final BitSet FOLLOW_ID_in_labeledElement381;
    public static final BitSet FOLLOW_block_in_labeledElement383;
    public static final BitSet FOLLOW_PLUS_ASSIGN_in_labeledElement396;
    public static final BitSet FOLLOW_ID_in_labeledElement398;
    public static final BitSet FOLLOW_block_in_labeledElement400;
    public static final BitSet FOLLOW_OPTIONAL_in_subrule421;
    public static final BitSet FOLLOW_block_in_subrule425;
    public static final BitSet FOLLOW_CLOSURE_in_subrule441;
    public static final BitSet FOLLOW_block_in_subrule445;
    public static final BitSet FOLLOW_POSITIVE_CLOSURE_in_subrule456;
    public static final BitSet FOLLOW_block_in_subrule460;
    public static final BitSet FOLLOW_block_in_subrule476;
    public static final BitSet FOLLOW_SET_in_blockSet506;
    public static final BitSet FOLLOW_atom_in_blockSet508;
    public static final BitSet FOLLOW_NOT_in_atom538;
    public static final BitSet FOLLOW_atom_in_atom542;
    public static final BitSet FOLLOW_range_in_atom552;
    public static final BitSet FOLLOW_DOT_in_atom567;
    public static final BitSet FOLLOW_ID_in_atom569;
    public static final BitSet FOLLOW_terminal_in_atom571;
    public static final BitSet FOLLOW_DOT_in_atom579;
    public static final BitSet FOLLOW_ID_in_atom581;
    public static final BitSet FOLLOW_ruleref_in_atom583;
    public static final BitSet FOLLOW_WILDCARD_in_atom594;
    public static final BitSet FOLLOW_WILDCARD_in_atom613;
    public static final BitSet FOLLOW_terminal_in_atom632;
    public static final BitSet FOLLOW_ruleref_in_atom649;
    public static final BitSet FOLLOW_blockSet_in_atom661;
    public static final BitSet FOLLOW_RULE_REF_in_ruleref685;
    public static final BitSet FOLLOW_ARG_ACTION_in_ruleref687;
    public static final BitSet FOLLOW_elementOptions_in_ruleref690;
    public static final BitSet FOLLOW_RANGE_in_range718;
    public static final BitSet FOLLOW_STRING_LITERAL_in_range722;
    public static final BitSet FOLLOW_STRING_LITERAL_in_range726;
    public static final BitSet FOLLOW_STRING_LITERAL_in_terminal751;
    public static final BitSet FOLLOW_STRING_LITERAL_in_terminal766;
    public static final BitSet FOLLOW_TOKEN_REF_in_terminal780;
    public static final BitSet FOLLOW_ARG_ACTION_in_terminal782;
    public static final BitSet FOLLOW_TOKEN_REF_in_terminal796;
    public static final BitSet FOLLOW_TOKEN_REF_in_terminal812;
    public static final BitSet FOLLOW_ELEMENT_OPTIONS_in_elementOptions836;
    public static final BitSet FOLLOW_elementOption_in_elementOptions838;
    public static final BitSet FOLLOW_ID_in_elementOption857;
    public static final BitSet FOLLOW_ASSIGN_in_elementOption868;
    public static final BitSet FOLLOW_ID_in_elementOption870;
    public static final BitSet FOLLOW_ID_in_elementOption872;
    public static final BitSet FOLLOW_ASSIGN_in_elementOption884;
    public static final BitSet FOLLOW_ID_in_elementOption886;
    public static final BitSet FOLLOW_STRING_LITERAL_in_elementOption888;
    public static final BitSet FOLLOW_ASSIGN_in_elementOption900;
    public static final BitSet FOLLOW_ID_in_elementOption902;
    public static final BitSet FOLLOW_ACTION_in_elementOption904;
    public static final BitSet FOLLOW_ASSIGN_in_elementOption916;
    public static final BitSet FOLLOW_ID_in_elementOption918;
    public static final BitSet FOLLOW_INT_in_elementOption920;

    public TreeParser[] getDelegates() {
        return new TreeParser[0];
    }

    public SourceGenTriggers(TreeNodeStream input) {
        this(input, new RecognizerSharedState());
    }

    public SourceGenTriggers(TreeNodeStream input, RecognizerSharedState state) {
        super(input, state);
    }

    @Override
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override
    public String getGrammarFileName() {
        return "org\\antlr\\v4\\codegen\\SourceGenTriggers.g";
    }

    public SourceGenTriggers(TreeNodeStream input, OutputModelController controller) {
        this(input);
        this.controller = controller;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void dummy() throws RecognitionException {
        try {
            this.pushFollow(FOLLOW_block_in_dummy61);
            this.block(null, null);
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
    public final List<? extends SrcOp> block(GrammarAST label, GrammarAST ebnfRoot) throws RecognitionException {
        List<SrcOp> omos;
        block23: {
            ArrayList<CodeBlockForAlt> alts;
            GrammarAST blk;
            block22: {
                omos = null;
                blk = null;
                alternative_return alternative1 = null;
                blk = (GrammarAST)this.match(this.input, 78, FOLLOW_BLOCK_in_block84);
                this.match(this.input, 2, null);
                int alt2 = 2;
                int LA2_0 = this.input.LA(1);
                if (LA2_0 == 42) {
                    alt2 = 1;
                }
                switch (alt2) {
                    case 1: {
                        this.match(this.input, 42, FOLLOW_OPTIONS_in_block88);
                        this.match(this.input, 2, null);
                        int cnt1 = 0;
                        block14: while (true) {
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
                                    break;
                                }
                                default: {
                                    if (cnt1 >= 1) break block14;
                                    EarlyExitException eee = new EarlyExitException(1, this.input);
                                    throw eee;
                                }
                            }
                            ++cnt1;
                        }
                        this.match(this.input, 3, null);
                    }
                }
                alts = new ArrayList<CodeBlockForAlt>();
                int cnt3 = 0;
                block15: while (true) {
                    int alt3 = 2;
                    int LA3_0 = this.input.LA(1);
                    if (LA3_0 == 74) {
                        alt3 = 1;
                    }
                    switch (alt3) {
                        case 1: {
                            this.pushFollow(FOLLOW_alternative_in_block109);
                            alternative1 = this.alternative();
                            --this.state._fsp;
                            alts.add(alternative1 != null ? alternative1.altCodeBlock : null);
                            break;
                        }
                        default: {
                            if (cnt3 >= 1) break block15;
                            EarlyExitException eee = new EarlyExitException(3, this.input);
                            throw eee;
                        }
                    }
                    ++cnt3;
                }
                this.match(this.input, 3, null);
                if (alts.size() != 1 || ebnfRoot != null) break block22;
                ArrayList<CodeBlockForAlt> alt3 = alts;
                return alt3;
            }
            try {
                if (ebnfRoot == null) {
                    omos = DefaultOutputModelFactory.list(this.controller.getChoiceBlock((BlockAST)blk, alts, label));
                    break block23;
                }
                Choice choice = this.controller.getEBNFBlock(ebnfRoot, alts);
                this.hasLookaheadBlock |= choice instanceof PlusBlock || choice instanceof StarBlock;
                omos = DefaultOutputModelFactory.list(choice);
            }
            catch (RecognitionException re) {
                this.reportError(re);
                this.recover(this.input, re);
            }
        }
        return omos;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final alternative_return alternative() throws RecognitionException {
        alternative_return retval = new alternative_return();
        retval.start = this.input.LT(1);
        alt_return a = null;
        boolean outerMost = this.inContext("RULE BLOCK");
        try {
            this.pushFollow(FOLLOW_alt_in_alternative161);
            a = this.alt(outerMost);
            --this.state._fsp;
            retval.altCodeBlock = a != null ? a.altCodeBlock : null;
            retval.ops = a != null ? a.ops : null;
            this.controller.finishAlternative(retval.altCodeBlock, retval.ops, outerMost);
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
    public final alt_return alt(boolean outerMost) throws RecognitionException {
        alt_return retval = new alt_return();
        retval.start = this.input.LT(1);
        List<? extends SrcOp> element2 = null;
        AltAST altAST = (AltAST)retval.start;
        if (outerMost) {
            this.controller.setCurrentOuterMostAlt(altAST.alt);
        }
        try {
            int alt7 = 2;
            alt7 = this.dfa7.predict(this.input);
            switch (alt7) {
                case 1: {
                    ArrayList<SrcOp> elems = new ArrayList<SrcOp>();
                    retval.altCodeBlock = this.controller.alternative(this.controller.getCurrentOuterMostAlt(), outerMost);
                    retval.ops = elems;
                    retval.altCodeBlock.ops = retval.ops;
                    this.controller.setCurrentBlock(retval.altCodeBlock);
                    this.match(this.input, 74, FOLLOW_ALT_in_alt191);
                    this.match(this.input, 2, null);
                    int alt4 = 2;
                    int LA4_0 = this.input.LA(1);
                    if (LA4_0 == 82) {
                        alt4 = 1;
                    }
                    switch (alt4) {
                        case 1: {
                            this.pushFollow(FOLLOW_elementOptions_in_alt193);
                            this.elementOptions();
                            --this.state._fsp;
                        }
                    }
                    int cnt5 = 0;
                    block17: while (true) {
                        int alt5 = 2;
                        int LA5_0 = this.input.LA(1);
                        if (LA5_0 == 4 || LA5_0 == 10 || LA5_0 == 20 || LA5_0 == 39 || LA5_0 == 46 || LA5_0 == 52 || LA5_0 == 57 || LA5_0 == 59 || LA5_0 == 62 || LA5_0 == 66 || LA5_0 == 78 || LA5_0 == 80 || LA5_0 >= 89 && LA5_0 <= 90 || LA5_0 == 98 || LA5_0 == 100) {
                            alt5 = 1;
                        }
                        switch (alt5) {
                            case 1: {
                                this.pushFollow(FOLLOW_element_in_alt198);
                                element2 = this.element();
                                --this.state._fsp;
                                if (element2 == null) break;
                                elems.addAll(element2);
                                break;
                            }
                            default: {
                                if (cnt5 >= 1) break block17;
                                EarlyExitException eee = new EarlyExitException(5, this.input);
                                throw eee;
                            }
                        }
                        ++cnt5;
                    }
                    this.match(this.input, 3, null);
                    break;
                }
                case 2: {
                    this.match(this.input, 74, FOLLOW_ALT_in_alt212);
                    this.match(this.input, 2, null);
                    int alt6 = 2;
                    int LA6_0 = this.input.LA(1);
                    if (LA6_0 == 82) {
                        alt6 = 1;
                    }
                    switch (alt6) {
                        case 1: {
                            this.pushFollow(FOLLOW_elementOptions_in_alt214);
                            this.elementOptions();
                            --this.state._fsp;
                        }
                    }
                    this.match(this.input, 83, FOLLOW_EPSILON_in_alt217);
                    this.match(this.input, 3, null);
                    retval.altCodeBlock = this.controller.epsilon(this.controller.getCurrentOuterMostAlt(), outerMost);
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
    public final List<? extends SrcOp> element() throws RecognitionException {
        List<SrcOp> omos = null;
        GrammarAST ACTION6 = null;
        GrammarAST SEMPRED7 = null;
        GrammarAST ACTION8 = null;
        GrammarAST SEMPRED9 = null;
        List<? extends SrcOp> labeledElement3 = null;
        List<SrcOp> atom4 = null;
        List<? extends SrcOp> subrule5 = null;
        try {
            int alt8 = 7;
            switch (this.input.LA(1)) {
                case 10: 
                case 46: {
                    alt8 = 1;
                    break;
                }
                case 20: 
                case 39: 
                case 52: 
                case 57: 
                case 62: 
                case 66: 
                case 98: 
                case 100: {
                    alt8 = 2;
                    break;
                }
                case 78: 
                case 80: 
                case 89: 
                case 90: {
                    alt8 = 3;
                    break;
                }
                case 4: {
                    int LA8_4 = this.input.LA(2);
                    if (LA8_4 == 2) {
                        alt8 = 6;
                        break;
                    }
                    if (LA8_4 >= 3 && LA8_4 <= 4 || LA8_4 == 10 || LA8_4 == 20 || LA8_4 == 39 || LA8_4 == 46 || LA8_4 == 52 || LA8_4 == 57 || LA8_4 == 59 || LA8_4 == 62 || LA8_4 == 66 || LA8_4 == 78 || LA8_4 == 80 || LA8_4 >= 89 && LA8_4 <= 90 || LA8_4 == 98 || LA8_4 == 100) {
                        alt8 = 4;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 8, 4, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 59: {
                    int LA8_5 = this.input.LA(2);
                    if (LA8_5 == 2) {
                        alt8 = 7;
                        break;
                    }
                    if (LA8_5 >= 3 && LA8_5 <= 4 || LA8_5 == 10 || LA8_5 == 20 || LA8_5 == 39 || LA8_5 == 46 || LA8_5 == 52 || LA8_5 == 57 || LA8_5 == 59 || LA8_5 == 62 || LA8_5 == 66 || LA8_5 == 78 || LA8_5 == 80 || LA8_5 >= 89 && LA8_5 <= 90 || LA8_5 == 98 || LA8_5 == 100) {
                        alt8 = 5;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 8, 5, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                default: {
                    NoViableAltException nvae = new NoViableAltException("", 8, 0, this.input);
                    throw nvae;
                }
            }
            switch (alt8) {
                case 1: {
                    this.pushFollow(FOLLOW_labeledElement_in_element246);
                    labeledElement3 = this.labeledElement();
                    --this.state._fsp;
                    omos = labeledElement3;
                    break;
                }
                case 2: {
                    this.pushFollow(FOLLOW_atom_in_element257);
                    atom4 = this.atom(null, false);
                    --this.state._fsp;
                    omos = atom4;
                    break;
                }
                case 3: {
                    this.pushFollow(FOLLOW_subrule_in_element267);
                    subrule5 = this.subrule();
                    --this.state._fsp;
                    omos = subrule5;
                    break;
                }
                case 4: {
                    ACTION6 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_element282);
                    omos = this.controller.action((ActionAST)ACTION6);
                    break;
                }
                case 5: {
                    SEMPRED7 = (GrammarAST)this.match(this.input, 59, FOLLOW_SEMPRED_in_element297);
                    omos = this.controller.sempred((ActionAST)SEMPRED7);
                    break;
                }
                case 6: {
                    ACTION8 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_element311);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_elementOptions_in_element313);
                    this.elementOptions();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    omos = this.controller.action((ActionAST)ACTION8);
                    break;
                }
                case 7: {
                    SEMPRED9 = (GrammarAST)this.match(this.input, 59, FOLLOW_SEMPRED_in_element325);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_elementOptions_in_element327);
                    this.elementOptions();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    omos = this.controller.sempred((ActionAST)SEMPRED9);
                }
            }
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
        }
        return omos;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final List<? extends SrcOp> labeledElement() throws RecognitionException {
        List<SrcOp> omos = null;
        GrammarAST ID10 = null;
        GrammarAST ID12 = null;
        GrammarAST ID14 = null;
        GrammarAST ID16 = null;
        List<SrcOp> atom11 = null;
        List<SrcOp> atom13 = null;
        List<? extends SrcOp> block15 = null;
        List<? extends SrcOp> block17 = null;
        try {
            int alt9;
            block39: {
                block43: {
                    block44: {
                        int LA9_0;
                        block40: {
                            block41: {
                                block42: {
                                    alt9 = 4;
                                    LA9_0 = this.input.LA(1);
                                    if (LA9_0 != 10) break block40;
                                    int LA9_1 = this.input.LA(2);
                                    if (LA9_1 != 2) break block41;
                                    int LA9_3 = this.input.LA(3);
                                    if (LA9_3 != 28) break block42;
                                    int LA9_5 = this.input.LA(4);
                                    if (LA9_5 == 20 || LA9_5 == 39 || LA9_5 == 52 || LA9_5 == 57 || LA9_5 == 62 || LA9_5 == 66 || LA9_5 == 98 || LA9_5 == 100) {
                                        alt9 = 1;
                                        break block39;
                                    } else if (LA9_5 == 78) {
                                        alt9 = 3;
                                        break block39;
                                    } else {
                                        int nvaeMark = this.input.mark();
                                        try {
                                            int nvaeConsume = 0;
                                            while (true) {
                                                if (nvaeConsume >= 3) {
                                                    NoViableAltException nvae = new NoViableAltException("", 9, 5, this.input);
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
                                    int nvaeConsume = 0;
                                    while (true) {
                                        if (nvaeConsume >= 2) {
                                            NoViableAltException nvae = new NoViableAltException("", 9, 3, this.input);
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
                            int nvaeMark = this.input.mark();
                            try {
                                this.input.consume();
                                NoViableAltException nvae = new NoViableAltException("", 9, 1, this.input);
                                throw nvae;
                            }
                            catch (Throwable throwable) {
                                this.input.rewind(nvaeMark);
                                throw throwable;
                            }
                        }
                        if (LA9_0 != 46) {
                            NoViableAltException nvae = new NoViableAltException("", 9, 0, this.input);
                            throw nvae;
                        }
                        int LA9_2 = this.input.LA(2);
                        if (LA9_2 != 2) break block43;
                        int LA9_4 = this.input.LA(3);
                        if (LA9_4 != 28) break block44;
                        int LA9_6 = this.input.LA(4);
                        if (LA9_6 == 20 || LA9_6 == 39 || LA9_6 == 52 || LA9_6 == 57 || LA9_6 == 62 || LA9_6 == 66 || LA9_6 == 98 || LA9_6 == 100) {
                            alt9 = 2;
                            break block39;
                        } else if (LA9_6 == 78) {
                            alt9 = 4;
                            break block39;
                        } else {
                            int nvaeMark = this.input.mark();
                            try {
                                int nvaeConsume = 0;
                                while (true) {
                                    if (nvaeConsume >= 3) {
                                        NoViableAltException nvae = new NoViableAltException("", 9, 6, this.input);
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
                        int nvaeConsume = 0;
                        while (true) {
                            if (nvaeConsume >= 2) {
                                NoViableAltException nvae = new NoViableAltException("", 9, 4, this.input);
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
                int nvaeMark = this.input.mark();
                try {
                    this.input.consume();
                    NoViableAltException nvae = new NoViableAltException("", 9, 2, this.input);
                    throw nvae;
                }
                catch (Throwable throwable) {
                    this.input.rewind(nvaeMark);
                    throw throwable;
                }
            }
            switch (alt9) {
                case 1: {
                    this.match(this.input, 10, FOLLOW_ASSIGN_in_labeledElement347);
                    this.match(this.input, 2, null);
                    ID10 = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_labeledElement349);
                    this.pushFollow(FOLLOW_atom_in_labeledElement351);
                    atom11 = this.atom(ID10, false);
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    return atom11;
                }
                case 2: {
                    this.match(this.input, 46, FOLLOW_PLUS_ASSIGN_in_labeledElement364);
                    this.match(this.input, 2, null);
                    ID12 = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_labeledElement366);
                    this.pushFollow(FOLLOW_atom_in_labeledElement368);
                    atom13 = this.atom(ID12, false);
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    return atom13;
                }
                case 3: {
                    this.match(this.input, 10, FOLLOW_ASSIGN_in_labeledElement379);
                    this.match(this.input, 2, null);
                    ID14 = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_labeledElement381);
                    this.pushFollow(FOLLOW_block_in_labeledElement383);
                    block15 = this.block(ID14, null);
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    return block15;
                }
                case 4: {
                    this.match(this.input, 46, FOLLOW_PLUS_ASSIGN_in_labeledElement396);
                    this.match(this.input, 2, null);
                    ID16 = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_labeledElement398);
                    this.pushFollow(FOLLOW_block_in_labeledElement400);
                    block17 = this.block(ID16, null);
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    return block17;
                }
            }
            return omos;
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            return omos;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final List<? extends SrcOp> subrule() throws RecognitionException {
        List<? extends SrcOp> omos = null;
        GrammarAST op = null;
        GrammarAST OPTIONAL18 = null;
        List<? extends SrcOp> b = null;
        List<? extends SrcOp> block19 = null;
        try {
            int alt11 = 3;
            switch (this.input.LA(1)) {
                case 89: {
                    alt11 = 1;
                    break;
                }
                case 80: 
                case 90: {
                    alt11 = 2;
                    break;
                }
                case 78: {
                    alt11 = 3;
                    break;
                }
                default: {
                    NoViableAltException nvae = new NoViableAltException("", 11, 0, this.input);
                    throw nvae;
                }
            }
            switch (alt11) {
                case 1: {
                    OPTIONAL18 = (GrammarAST)this.match(this.input, 89, FOLLOW_OPTIONAL_in_subrule421);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_block_in_subrule425);
                    b = this.block(null, OPTIONAL18);
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    omos = b;
                    break;
                }
                case 2: {
                    int alt10 = 2;
                    int LA10_0 = this.input.LA(1);
                    if (LA10_0 == 80) {
                        alt10 = 1;
                    } else if (LA10_0 == 90) {
                        alt10 = 2;
                    } else {
                        NoViableAltException nvae = new NoViableAltException("", 10, 0, this.input);
                        throw nvae;
                    }
                    switch (alt10) {
                        case 1: {
                            op = (GrammarAST)this.match(this.input, 80, FOLLOW_CLOSURE_in_subrule441);
                            this.match(this.input, 2, null);
                            this.pushFollow(FOLLOW_block_in_subrule445);
                            b = this.block(null, null);
                            --this.state._fsp;
                            this.match(this.input, 3, null);
                            break;
                        }
                        case 2: {
                            op = (GrammarAST)this.match(this.input, 90, FOLLOW_POSITIVE_CLOSURE_in_subrule456);
                            this.match(this.input, 2, null);
                            this.pushFollow(FOLLOW_block_in_subrule460);
                            b = this.block(null, null);
                            --this.state._fsp;
                            this.match(this.input, 3, null);
                        }
                    }
                    ArrayList<CodeBlockForAlt> alts = new ArrayList<CodeBlockForAlt>();
                    SrcOp blk = (SrcOp)b.get(0);
                    CodeBlockForAlt alt = new CodeBlockForAlt(this.controller.delegate);
                    alt.addOp(blk);
                    alts.add(alt);
                    Choice loop = this.controller.getEBNFBlock(op, alts);
                    this.hasLookaheadBlock |= loop instanceof PlusBlock || loop instanceof StarBlock;
                    omos = DefaultOutputModelFactory.list(loop);
                    break;
                }
                case 3: {
                    this.pushFollow(FOLLOW_block_in_subrule476);
                    block19 = this.block(null, null);
                    --this.state._fsp;
                    omos = block19;
                }
            }
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
        }
        return omos;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final List<SrcOp> blockSet(GrammarAST label, boolean invert) throws RecognitionException {
        List<SrcOp> omos = null;
        GrammarAST SET20 = null;
        try {
            SET20 = (GrammarAST)this.match(this.input, 98, FOLLOW_SET_in_blockSet506);
            this.match(this.input, 2, null);
            int cnt12 = 0;
            block7: while (true) {
                int alt12 = 2;
                int LA12_0 = this.input.LA(1);
                if (LA12_0 == 20 || LA12_0 == 39 || LA12_0 == 52 || LA12_0 == 57 || LA12_0 == 62 || LA12_0 == 66 || LA12_0 == 98 || LA12_0 == 100) {
                    alt12 = 1;
                }
                switch (alt12) {
                    case 1: {
                        this.pushFollow(FOLLOW_atom_in_blockSet508);
                        this.atom(label, invert);
                        --this.state._fsp;
                        break;
                    }
                    default: {
                        if (cnt12 >= 1) break block7;
                        EarlyExitException eee = new EarlyExitException(12, this.input);
                        throw eee;
                    }
                }
                ++cnt12;
            }
            this.match(this.input, 3, null);
            omos = this.controller.set(SET20, label, invert);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
        }
        return omos;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final List<SrcOp> atom(GrammarAST label, boolean invert) throws RecognitionException {
        List<SrcOp> omos = null;
        GrammarAST WILDCARD22 = null;
        GrammarAST WILDCARD23 = null;
        List<SrcOp> a = null;
        List<SrcOp> range21 = null;
        List<SrcOp> terminal24 = null;
        List<SrcOp> ruleref25 = null;
        List<SrcOp> blockSet26 = null;
        try {
            int alt13 = 9;
            switch (this.input.LA(1)) {
                case 39: {
                    alt13 = 1;
                    break;
                }
                case 52: {
                    alt13 = 2;
                    break;
                }
                case 20: {
                    int LA13_3 = this.input.LA(2);
                    if (LA13_3 == 2) {
                        int LA13_8 = this.input.LA(3);
                        if (LA13_8 == 28) {
                            int LA13_11 = this.input.LA(4);
                            if (LA13_11 == 62 || LA13_11 == 66) {
                                alt13 = 3;
                                break;
                            }
                            if (LA13_11 == 57) {
                                alt13 = 4;
                                break;
                            }
                            int nvaeMark = this.input.mark();
                            try {
                                for (int nvaeConsume = 0; nvaeConsume < 3; ++nvaeConsume) {
                                    this.input.consume();
                                }
                                NoViableAltException nvae = new NoViableAltException("", 13, 11, this.input);
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
                            NoViableAltException nvae = new NoViableAltException("", 13, 8, this.input);
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
                        NoViableAltException nvae = new NoViableAltException("", 13, 3, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 100: {
                    int LA13_4 = this.input.LA(2);
                    if (LA13_4 == 2) {
                        alt13 = 5;
                        break;
                    }
                    if (LA13_4 >= 3 && LA13_4 <= 4 || LA13_4 == 10 || LA13_4 == 20 || LA13_4 == 39 || LA13_4 == 46 || LA13_4 == 52 || LA13_4 == 57 || LA13_4 == 59 || LA13_4 == 62 || LA13_4 == 66 || LA13_4 == 78 || LA13_4 == 80 || LA13_4 >= 89 && LA13_4 <= 90 || LA13_4 == 98 || LA13_4 == 100) {
                        alt13 = 6;
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
                case 62: 
                case 66: {
                    alt13 = 7;
                    break;
                }
                case 57: {
                    alt13 = 8;
                    break;
                }
                case 98: {
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
                    this.match(this.input, 39, FOLLOW_NOT_in_atom538);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_atom_in_atom542);
                    a = this.atom(label, true);
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    omos = a;
                    break;
                }
                case 2: {
                    this.pushFollow(FOLLOW_range_in_atom552);
                    range21 = this.range(label);
                    --this.state._fsp;
                    omos = range21;
                    break;
                }
                case 3: {
                    this.match(this.input, 20, FOLLOW_DOT_in_atom567);
                    this.match(this.input, 2, null);
                    this.match(this.input, 28, FOLLOW_ID_in_atom569);
                    this.pushFollow(FOLLOW_terminal_in_atom571);
                    this.terminal(label);
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    break;
                }
                case 4: {
                    this.match(this.input, 20, FOLLOW_DOT_in_atom579);
                    this.match(this.input, 2, null);
                    this.match(this.input, 28, FOLLOW_ID_in_atom581);
                    this.pushFollow(FOLLOW_ruleref_in_atom583);
                    this.ruleref(label);
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    break;
                }
                case 5: {
                    WILDCARD22 = (GrammarAST)this.match(this.input, 100, FOLLOW_WILDCARD_in_atom594);
                    this.match(this.input, 2, null);
                    this.matchAny(this.input);
                    this.match(this.input, 3, null);
                    omos = this.controller.wildcard(WILDCARD22, label);
                    break;
                }
                case 6: {
                    WILDCARD23 = (GrammarAST)this.match(this.input, 100, FOLLOW_WILDCARD_in_atom613);
                    omos = this.controller.wildcard(WILDCARD23, label);
                    break;
                }
                case 7: {
                    this.pushFollow(FOLLOW_terminal_in_atom632);
                    terminal24 = this.terminal(label);
                    --this.state._fsp;
                    omos = terminal24;
                    break;
                }
                case 8: {
                    this.pushFollow(FOLLOW_ruleref_in_atom649);
                    ruleref25 = this.ruleref(label);
                    --this.state._fsp;
                    omos = ruleref25;
                    break;
                }
                case 9: {
                    this.pushFollow(FOLLOW_blockSet_in_atom661);
                    blockSet26 = this.blockSet(label, invert);
                    --this.state._fsp;
                    omos = blockSet26;
                }
            }
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
        }
        return omos;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final List<SrcOp> ruleref(GrammarAST label) throws RecognitionException {
        List<SrcOp> omos = null;
        GrammarAST RULE_REF27 = null;
        GrammarAST ARG_ACTION28 = null;
        try {
            RULE_REF27 = (GrammarAST)this.match(this.input, 57, FOLLOW_RULE_REF_in_ruleref685);
            if (this.input.LA(1) == 2) {
                this.match(this.input, 2, null);
                int alt14 = 2;
                int LA14_0 = this.input.LA(1);
                if (LA14_0 == 8) {
                    alt14 = 1;
                }
                switch (alt14) {
                    case 1: {
                        ARG_ACTION28 = (GrammarAST)this.match(this.input, 8, FOLLOW_ARG_ACTION_in_ruleref687);
                    }
                }
                int alt15 = 2;
                int LA15_0 = this.input.LA(1);
                if (LA15_0 == 82) {
                    alt15 = 1;
                }
                switch (alt15) {
                    case 1: {
                        this.pushFollow(FOLLOW_elementOptions_in_ruleref690);
                        this.elementOptions();
                        --this.state._fsp;
                    }
                }
                this.match(this.input, 3, null);
            }
            omos = this.controller.ruleRef(RULE_REF27, label, ARG_ACTION28);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
        }
        return omos;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final List<SrcOp> range(GrammarAST label) throws RecognitionException {
        List<SrcOp> omos = null;
        GrammarAST a = null;
        GrammarAST b = null;
        try {
            this.match(this.input, 52, FOLLOW_RANGE_in_range718);
            this.match(this.input, 2, null);
            a = (GrammarAST)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_range722);
            b = (GrammarAST)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_range726);
            this.match(this.input, 3, null);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
        }
        return omos;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final List<SrcOp> terminal(GrammarAST label) throws RecognitionException {
        List<SrcOp> omos = null;
        GrammarAST STRING_LITERAL29 = null;
        GrammarAST STRING_LITERAL30 = null;
        GrammarAST TOKEN_REF31 = null;
        GrammarAST ARG_ACTION32 = null;
        GrammarAST TOKEN_REF33 = null;
        GrammarAST TOKEN_REF34 = null;
        try {
            int alt16;
            block36: {
                int LA16_2;
                block38: {
                    int LA16_5;
                    block39: {
                        int LA16_0;
                        block37: {
                            alt16 = 5;
                            LA16_0 = this.input.LA(1);
                            if (LA16_0 != 62) break block37;
                            int LA16_1 = this.input.LA(2);
                            if (LA16_1 == 2) {
                                alt16 = 1;
                                break block36;
                            } else if (LA16_1 >= 3 && LA16_1 <= 4 || LA16_1 == 10 || LA16_1 == 20 || LA16_1 == 39 || LA16_1 == 46 || LA16_1 == 52 || LA16_1 == 57 || LA16_1 == 59 || LA16_1 == 62 || LA16_1 == 66 || LA16_1 == 78 || LA16_1 == 80 || LA16_1 >= 89 && LA16_1 <= 90 || LA16_1 == 98 || LA16_1 == 100) {
                                alt16 = 2;
                                break block36;
                            } else {
                                int nvaeMark = this.input.mark();
                                try {
                                    this.input.consume();
                                    NoViableAltException nvae = new NoViableAltException("", 16, 1, this.input);
                                    throw nvae;
                                }
                                catch (Throwable throwable) {
                                    this.input.rewind(nvaeMark);
                                    throw throwable;
                                }
                            }
                        }
                        if (LA16_0 != 66) {
                            NoViableAltException nvae = new NoViableAltException("", 16, 0, this.input);
                            throw nvae;
                        }
                        LA16_2 = this.input.LA(2);
                        if (LA16_2 != 2) break block38;
                        LA16_5 = this.input.LA(3);
                        if (LA16_5 != 8) break block39;
                        int LA16_7 = this.input.LA(4);
                        if (LA16_7 >= 4 && LA16_7 <= 100) {
                            alt16 = 3;
                            break block36;
                        } else if (LA16_7 >= 2 && LA16_7 <= 3) {
                            alt16 = 4;
                            break block36;
                        } else {
                            int nvaeMark = this.input.mark();
                            try {
                                int nvaeConsume = 0;
                                while (true) {
                                    if (nvaeConsume >= 3) {
                                        NoViableAltException nvae = new NoViableAltException("", 16, 7, this.input);
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
                    if (LA16_5 >= 4 && LA16_5 <= 7 || LA16_5 >= 9 && LA16_5 <= 100) {
                        alt16 = 4;
                        break block36;
                    } else {
                        int nvaeMark = this.input.mark();
                        try {
                            int nvaeConsume = 0;
                            while (true) {
                                if (nvaeConsume >= 2) {
                                    NoViableAltException nvae = new NoViableAltException("", 16, 5, this.input);
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
                if (LA16_2 >= 3 && LA16_2 <= 4 || LA16_2 == 10 || LA16_2 == 20 || LA16_2 == 39 || LA16_2 == 46 || LA16_2 == 52 || LA16_2 == 57 || LA16_2 == 59 || LA16_2 == 62 || LA16_2 == 66 || LA16_2 == 78 || LA16_2 == 80 || LA16_2 >= 89 && LA16_2 <= 90 || LA16_2 == 98 || LA16_2 == 100) {
                    alt16 = 5;
                } else {
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 16, 2, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
            }
            switch (alt16) {
                case 1: {
                    STRING_LITERAL29 = (GrammarAST)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_terminal751);
                    this.match(this.input, 2, null);
                    this.matchAny(this.input);
                    this.match(this.input, 3, null);
                    return this.controller.stringRef(STRING_LITERAL29, label);
                }
                case 2: {
                    STRING_LITERAL30 = (GrammarAST)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_terminal766);
                    return this.controller.stringRef(STRING_LITERAL30, label);
                }
                case 3: {
                    TOKEN_REF31 = (GrammarAST)this.match(this.input, 66, FOLLOW_TOKEN_REF_in_terminal780);
                    this.match(this.input, 2, null);
                    ARG_ACTION32 = (GrammarAST)this.match(this.input, 8, FOLLOW_ARG_ACTION_in_terminal782);
                    this.matchAny(this.input);
                    this.match(this.input, 3, null);
                    return this.controller.tokenRef(TOKEN_REF31, label, ARG_ACTION32);
                }
                case 4: {
                    TOKEN_REF33 = (GrammarAST)this.match(this.input, 66, FOLLOW_TOKEN_REF_in_terminal796);
                    this.match(this.input, 2, null);
                    this.matchAny(this.input);
                    this.match(this.input, 3, null);
                    return this.controller.tokenRef(TOKEN_REF33, label, null);
                }
                case 5: {
                    TOKEN_REF34 = (GrammarAST)this.match(this.input, 66, FOLLOW_TOKEN_REF_in_terminal812);
                    return this.controller.tokenRef(TOKEN_REF34, label, null);
                }
            }
            return omos;
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            return omos;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void elementOptions() throws RecognitionException {
        try {
            this.match(this.input, 82, FOLLOW_ELEMENT_OPTIONS_in_elementOptions836);
            this.match(this.input, 2, null);
            int cnt17 = 0;
            block7: while (true) {
                int alt17 = 2;
                int LA17_0 = this.input.LA(1);
                if (LA17_0 == 10 || LA17_0 == 28) {
                    alt17 = 1;
                }
                switch (alt17) {
                    case 1: {
                        this.pushFollow(FOLLOW_elementOption_in_elementOptions838);
                        this.elementOption();
                        --this.state._fsp;
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
            int alt18;
            block33: {
                alt18 = 5;
                int LA18_0 = this.input.LA(1);
                if (LA18_0 == 28) {
                    alt18 = 1;
                } else {
                    if (LA18_0 != 10) {
                        NoViableAltException nvae = new NoViableAltException("", 18, 0, this.input);
                        throw nvae;
                    }
                    int LA18_2 = this.input.LA(2);
                    if (LA18_2 == 2) {
                        int LA18_3 = this.input.LA(3);
                        if (LA18_3 == 28) {
                            switch (this.input.LA(4)) {
                                case 28: {
                                    alt18 = 2;
                                    break;
                                }
                                case 62: {
                                    alt18 = 3;
                                    break;
                                }
                                case 4: {
                                    alt18 = 4;
                                    break;
                                }
                                case 30: {
                                    alt18 = 5;
                                    break;
                                }
                                default: {
                                    int nvaeMark = this.input.mark();
                                    try {
                                        int nvaeConsume = 0;
                                        while (true) {
                                            if (nvaeConsume >= 3) {
                                                NoViableAltException nvae = new NoViableAltException("", 18, 4, this.input);
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
                                        NoViableAltException nvae = new NoViableAltException("", 18, 3, this.input);
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
                        NoViableAltException nvae = new NoViableAltException("", 18, 2, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
            }
            switch (alt18) {
                case 1: {
                    this.match(this.input, 28, FOLLOW_ID_in_elementOption857);
                    return;
                }
                case 2: {
                    this.match(this.input, 10, FOLLOW_ASSIGN_in_elementOption868);
                    this.match(this.input, 2, null);
                    this.match(this.input, 28, FOLLOW_ID_in_elementOption870);
                    this.match(this.input, 28, FOLLOW_ID_in_elementOption872);
                    this.match(this.input, 3, null);
                    return;
                }
                case 3: {
                    this.match(this.input, 10, FOLLOW_ASSIGN_in_elementOption884);
                    this.match(this.input, 2, null);
                    this.match(this.input, 28, FOLLOW_ID_in_elementOption886);
                    this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_elementOption888);
                    this.match(this.input, 3, null);
                    return;
                }
                case 4: {
                    this.match(this.input, 10, FOLLOW_ASSIGN_in_elementOption900);
                    this.match(this.input, 2, null);
                    this.match(this.input, 28, FOLLOW_ID_in_elementOption902);
                    this.match(this.input, 4, FOLLOW_ACTION_in_elementOption904);
                    this.match(this.input, 3, null);
                    return;
                }
                case 5: {
                    this.match(this.input, 10, FOLLOW_ASSIGN_in_elementOption916);
                    this.match(this.input, 2, null);
                    this.match(this.input, 28, FOLLOW_ID_in_elementOption918);
                    this.match(this.input, 30, FOLLOW_INT_in_elementOption920);
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
        int numStates = DFA7_transitionS.length;
        DFA7_transition = new short[numStates][];
        for (int i = 0; i < numStates; ++i) {
            SourceGenTriggers.DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
        }
        FOLLOW_block_in_dummy61 = new BitSet(new long[]{2L});
        FOLLOW_BLOCK_in_block84 = new BitSet(new long[]{4L});
        FOLLOW_OPTIONS_in_block88 = new BitSet(new long[]{4L});
        FOLLOW_alternative_in_block109 = new BitSet(new long[]{8L, 1024L});
        FOLLOW_alt_in_alternative161 = new BitSet(new long[]{2L});
        FOLLOW_ALT_in_alt191 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_alt193 = new BitSet(new long[]{5336836476935078928L, 86000091140L});
        FOLLOW_element_in_alt198 = new BitSet(new long[]{5336836476935078936L, 86000091140L});
        FOLLOW_ALT_in_alt212 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_alt214 = new BitSet(new long[]{0L, 524288L});
        FOLLOW_EPSILON_in_alt217 = new BitSet(new long[]{8L});
        FOLLOW_labeledElement_in_element246 = new BitSet(new long[]{2L});
        FOLLOW_atom_in_element257 = new BitSet(new long[]{2L});
        FOLLOW_subrule_in_element267 = new BitSet(new long[]{2L});
        FOLLOW_ACTION_in_element282 = new BitSet(new long[]{2L});
        FOLLOW_SEMPRED_in_element297 = new BitSet(new long[]{2L});
        FOLLOW_ACTION_in_element311 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_element313 = new BitSet(new long[]{8L});
        FOLLOW_SEMPRED_in_element325 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_element327 = new BitSet(new long[]{8L});
        FOLLOW_ASSIGN_in_labeledElement347 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_labeledElement349 = new BitSet(new long[]{4760305355887476736L, 0x1400000004L});
        FOLLOW_atom_in_labeledElement351 = new BitSet(new long[]{8L});
        FOLLOW_PLUS_ASSIGN_in_labeledElement364 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_labeledElement366 = new BitSet(new long[]{4760305355887476736L, 0x1400000004L});
        FOLLOW_atom_in_labeledElement368 = new BitSet(new long[]{8L});
        FOLLOW_ASSIGN_in_labeledElement379 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_labeledElement381 = new BitSet(new long[]{0L, 16384L});
        FOLLOW_block_in_labeledElement383 = new BitSet(new long[]{8L});
        FOLLOW_PLUS_ASSIGN_in_labeledElement396 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_labeledElement398 = new BitSet(new long[]{0L, 16384L});
        FOLLOW_block_in_labeledElement400 = new BitSet(new long[]{8L});
        FOLLOW_OPTIONAL_in_subrule421 = new BitSet(new long[]{4L});
        FOLLOW_block_in_subrule425 = new BitSet(new long[]{8L});
        FOLLOW_CLOSURE_in_subrule441 = new BitSet(new long[]{4L});
        FOLLOW_block_in_subrule445 = new BitSet(new long[]{8L});
        FOLLOW_POSITIVE_CLOSURE_in_subrule456 = new BitSet(new long[]{4L});
        FOLLOW_block_in_subrule460 = new BitSet(new long[]{8L});
        FOLLOW_block_in_subrule476 = new BitSet(new long[]{2L});
        FOLLOW_SET_in_blockSet506 = new BitSet(new long[]{4L});
        FOLLOW_atom_in_blockSet508 = new BitSet(new long[]{4760305355887476744L, 0x1400000004L});
        FOLLOW_NOT_in_atom538 = new BitSet(new long[]{4L});
        FOLLOW_atom_in_atom542 = new BitSet(new long[]{8L});
        FOLLOW_range_in_atom552 = new BitSet(new long[]{2L});
        FOLLOW_DOT_in_atom567 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_atom569 = new BitSet(new long[]{0x4000000000000000L, 4L});
        FOLLOW_terminal_in_atom571 = new BitSet(new long[]{8L});
        FOLLOW_DOT_in_atom579 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_atom581 = new BitSet(new long[]{0x200000000000000L});
        FOLLOW_ruleref_in_atom583 = new BitSet(new long[]{8L});
        FOLLOW_WILDCARD_in_atom594 = new BitSet(new long[]{4L});
        FOLLOW_WILDCARD_in_atom613 = new BitSet(new long[]{2L});
        FOLLOW_terminal_in_atom632 = new BitSet(new long[]{2L});
        FOLLOW_ruleref_in_atom649 = new BitSet(new long[]{2L});
        FOLLOW_blockSet_in_atom661 = new BitSet(new long[]{2L});
        FOLLOW_RULE_REF_in_ruleref685 = new BitSet(new long[]{4L});
        FOLLOW_ARG_ACTION_in_ruleref687 = new BitSet(new long[]{8L, 262144L});
        FOLLOW_elementOptions_in_ruleref690 = new BitSet(new long[]{8L});
        FOLLOW_RANGE_in_range718 = new BitSet(new long[]{4L});
        FOLLOW_STRING_LITERAL_in_range722 = new BitSet(new long[]{0x4000000000000000L});
        FOLLOW_STRING_LITERAL_in_range726 = new BitSet(new long[]{8L});
        FOLLOW_STRING_LITERAL_in_terminal751 = new BitSet(new long[]{4L});
        FOLLOW_STRING_LITERAL_in_terminal766 = new BitSet(new long[]{2L});
        FOLLOW_TOKEN_REF_in_terminal780 = new BitSet(new long[]{4L});
        FOLLOW_ARG_ACTION_in_terminal782 = new BitSet(new long[]{-16L, 0x1FFFFFFFFFL});
        FOLLOW_TOKEN_REF_in_terminal796 = new BitSet(new long[]{4L});
        FOLLOW_TOKEN_REF_in_terminal812 = new BitSet(new long[]{2L});
        FOLLOW_ELEMENT_OPTIONS_in_elementOptions836 = new BitSet(new long[]{4L});
        FOLLOW_elementOption_in_elementOptions838 = new BitSet(new long[]{268436488L});
        FOLLOW_ID_in_elementOption857 = new BitSet(new long[]{2L});
        FOLLOW_ASSIGN_in_elementOption868 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_elementOption870 = new BitSet(new long[]{0x10000000L});
        FOLLOW_ID_in_elementOption872 = new BitSet(new long[]{8L});
        FOLLOW_ASSIGN_in_elementOption884 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_elementOption886 = new BitSet(new long[]{0x4000000000000000L});
        FOLLOW_STRING_LITERAL_in_elementOption888 = new BitSet(new long[]{8L});
        FOLLOW_ASSIGN_in_elementOption900 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_elementOption902 = new BitSet(new long[]{16L});
        FOLLOW_ACTION_in_elementOption904 = new BitSet(new long[]{8L});
        FOLLOW_ASSIGN_in_elementOption916 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_elementOption918 = new BitSet(new long[]{0x40000000L});
        FOLLOW_INT_in_elementOption920 = new BitSet(new long[]{8L});
    }

    protected class DFA7
    extends DFA {
        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = DFA7_eot;
            this.eof = DFA7_eof;
            this.min = DFA7_min;
            this.max = DFA7_max;
            this.accept = DFA7_accept;
            this.special = DFA7_special;
            this.transition = DFA7_transition;
        }

        @Override
        public String getDescription() {
            return "65:1: alt[boolean outerMost] returns [CodeBlockForAlt altCodeBlock, List<SrcOp> ops] : ( ^( ALT ( elementOptions )? ( element )+ ) | ^( ALT ( elementOptions )? EPSILON ) );";
        }
    }

    public static class alt_return
    extends TreeRuleReturnScope {
        public CodeBlockForAlt altCodeBlock;
        public List<SrcOp> ops;
    }

    public static class alternative_return
    extends TreeRuleReturnScope {
        public CodeBlockForAlt altCodeBlock;
        public List<SrcOp> ops;
    }
}

