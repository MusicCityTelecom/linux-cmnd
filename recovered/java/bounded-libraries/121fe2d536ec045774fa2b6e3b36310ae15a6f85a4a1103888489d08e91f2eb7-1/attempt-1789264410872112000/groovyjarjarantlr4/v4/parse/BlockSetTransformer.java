/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.parse;

import groovyjarjarantlr4.runtime.BaseRecognizer;
import groovyjarjarantlr4.runtime.BitSet;
import groovyjarjarantlr4.runtime.DFA;
import groovyjarjarantlr4.runtime.EarlyExitException;
import groovyjarjarantlr4.runtime.FailedPredicateException;
import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.MismatchedSetException;
import groovyjarjarantlr4.runtime.NoViableAltException;
import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.RecognizerSharedState;
import groovyjarjarantlr4.runtime.RuleReturnScope;
import groovyjarjarantlr4.runtime.tree.CommonTree;
import groovyjarjarantlr4.runtime.tree.CommonTreeAdaptor;
import groovyjarjarantlr4.runtime.tree.RewriteEarlyExitException;
import groovyjarjarantlr4.runtime.tree.RewriteRuleNodeStream;
import groovyjarjarantlr4.runtime.tree.RewriteRuleSubtreeStream;
import groovyjarjarantlr4.runtime.tree.TreeAdaptor;
import groovyjarjarantlr4.runtime.tree.TreeNodeStream;
import groovyjarjarantlr4.runtime.tree.TreeRewriter;
import groovyjarjarantlr4.runtime.tree.TreeRuleReturnScope;
import groovyjarjarantlr4.v4.misc.CharSupport;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.GrammarTransformPipeline;
import groovyjarjarantlr4.v4.tool.ast.AltAST;
import groovyjarjarantlr4.v4.tool.ast.BlockAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;

public class BlockSetTransformer
extends TreeRewriter {
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
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();
    public String currentRuleName;
    public GrammarAST currentAlt;
    public Grammar g;
    protected DFA10 dfa10 = new DFA10(this);
    static final String DFA10_eotS = "|\uffff";
    static final String DFA10_eofS = "|\uffff";
    static final String DFA10_minS = "\u0001N\u0001\u0002\u0001J\u0001\u0002\u00014\u0004\u0002\u0001\u0003\u0001R\u0001J\u0001R\u0001>\u0001\u0003\u0001\u0002\u00014\u0003\u0002\u0001>\u0001\u001c\u0001\u0003\u00014\u0002\u0003\u0001\u0004\u0001\u0003\u0001\u0002\u0001\u0003\u0004\u0002\u0001\u0003\u0001\u0002\u0006\u0003\u0001\u001c\u0002\u0003\u0001R\u0001\u0003\u0001R\u0001>\u0001\u001c\u0005\u0003\u0001\u0004\u0001\u0003\u0001\u0002\u00014\u0001\u0002\u0001\u0000\u0001\u0002\u0001>\u0001\u0004\u0004\u0003\u0001\u001c\u0001\u0003\u0002\uffff\n\u0003\u0001\u0004\u0001\u0003\u0001\u0002\u0002\u0003\u0001\u0002\n\u0003\u0001\u001c\u0001\u0003\u0001\u001c\u0005\u0003\u0002\u0004\u0010\u0003";
    static final String DFA10_maxS = "\u0001N\u0001\u0002\u0001J\u0001\u0002\u0001R\u0001\u0002\u0002\u0003\u0001\u0002\u0001\u001c\u0001R\u0001J\u0001R\u0001>\u0001\u001c\u0001\u0002\u0001B\u0003\u0002\u0001>\u0002\u001c\u0001R\u0001\u001c\u0001\u0003\u0001>\u0001\u001c\u0001\u0002\u0001\u0003\u0001\u0002\u0002\u0003\u0001\u0002\u0001\u001c\u0001\u0002\u0006\u0003\u0001\u001c\u0001\u0003\u0001\u001c\u0001R\u0001J\u0001R\u0001>\u0001\u001c\u0001\u0003\u0004\u001c\u0001>\u0001\u001c\u0001\u0002\u0001B\u0001\u0002\u0001\u0000\u0001\u0002\u0002>\u0004\u0003\u0002\u001c\u0002\uffff\u0001\u001c\u0005\u0003\u0004\u001c\u0001>\u0001\u001c\u0001\u0002\u0001\u0003\u0001\u001c\u0001\u0002\u0002\u0003\u0004\u001c\u0004\u0003\u0001\u001c\u0001\u0003\u0001\u001c\u0001\u0003\u0004\u001c\u0002>\b\u0003\b\u001c";
    static final String DFA10_acceptS = "F\uffff\u0001\u0001\u0001\u00024\uffff";
    static final String DFA10_specialS = "<\uffff\u0001\u0000?\uffff}>";
    static final String[] DFA10_transitionS = new String[]{"\u0001\u0001", "\u0001\u0002", "\u0001\u0003", "\u0001\u0004", "\u0001\b\t\uffff\u0001\u0006\u0003\uffff\u0001\u0007\u000f\uffff\u0001\u0005", "\u0001\t", "\u0001\n\u0001\u000b", "\u0001\f\u0001\u000b", "\u0001\r", "\u0001\u0010\u0006\uffff\u0001\u000f\u0011\uffff\u0001\u000e", "\u0001\u0011", "\u0001\u0012", "\u0001\u0013", "\u0001\u0014", "\u0001\u0010\u0006\uffff\u0001\u000f\u0011\uffff\u0001\u000e", "\u0001\u0015", "\u0001\b\t\uffff\u0001\u0006\u0003\uffff\u0001\u0007", "\u0001\u0016", "\u0001\u0017", "\u0001\u0018", "\u0001\u0019", "\u0001\u001a", "\u0001\u001d\u0006\uffff\u0001\u001c\u0011\uffff\u0001\u001b", "\u0001!\t\uffff\u0001\u001f\u0003\uffff\u0001 \u000f\uffff\u0001\u001e", "\u0001$\u0006\uffff\u0001#\u0011\uffff\u0001\"", "\u0001%", "\u0001(\u0017\uffff\u0001&\u0001\uffff\u0001)\u001f\uffff\u0001'", "\u0001\u001d\u0006\uffff\u0001\u001c\u0011\uffff\u0001\u001b", "\u0001*", "\u0001+", "\u0001,", "\u0001-\u0001.", "\u0001/\u0001.", "\u00010", "\u0001$\u0006\uffff\u0001#\u0011\uffff\u0001\"", "\u00011", "\u00012", "\u0001\u000b", "\u00013", "\u00014", "\u00015", "\u00016", "\u00017", "\u0001\u000b", "\u0001:\u0006\uffff\u00019\u0011\uffff\u00018", "\u0001;", "\u0001<F\uffff\u0001\u0012", "\u0001=", "\u0001>", "\u0001?", "\u0001\u000b", "\u0001\u0010\u0006\uffff\u0001\u000f\u0011\uffff\u0001\u000e", "\u0001\u0010\u0006\uffff\u0001\u000f\u0011\uffff\u0001\u000e", "\u0001\u0010\u0006\uffff\u0001\u000f\u0011\uffff\u0001\u000e", "\u0001\u0010\u0006\uffff\u0001\u000f\u0011\uffff\u0001\u000e", "\u0001B\u0017\uffff\u0001@\u0001\uffff\u0001C\u001f\uffff\u0001A", "\u0001:\u0006\uffff\u00019\u0011\uffff\u00018", "\u0001D", "\u0001!\t\uffff\u0001\u001f\u0003\uffff\u0001 ", "\u0001E", "\u0001\uffff", "\u0001H", "\u0001I", "\u0001L\u0017\uffff\u0001J\u0001\uffff\u0001M\u001f\uffff\u0001K", "\u0001N", "\u0001O", "\u0001P", "\u0001Q", "\u0001R", "\u0001U\u0006\uffff\u0001T\u0011\uffff\u0001S", "", "", "\u0001X\u0006\uffff\u0001W\u0011\uffff\u0001V", "\u0001Y", "\u0001Z", "\u0001[", "\u0001\\", "\u0001]", "\u0001\u001d\u0006\uffff\u0001\u001c\u0011\uffff\u0001\u001b", "\u0001\u001d\u0006\uffff\u0001\u001c\u0011\uffff\u0001\u001b", "\u0001\u001d\u0006\uffff\u0001\u001c\u0011\uffff\u0001\u001b", "\u0001\u001d\u0006\uffff\u0001\u001c\u0011\uffff\u0001\u001b", "\u0001`\u0017\uffff\u0001^\u0001\uffff\u0001a\u001f\uffff\u0001_", "\u0001U\u0006\uffff\u0001T\u0011\uffff\u0001S", "\u0001b", "\u0001c", "\u0001X\u0006\uffff\u0001W\u0011\uffff\u0001V", "\u0001d", "\u0001e", "\u0001.", "\u0001$\u0006\uffff\u0001#\u0011\uffff\u0001\"", "\u0001$\u0006\uffff\u0001#\u0011\uffff\u0001\"", "\u0001$\u0006\uffff\u0001#\u0011\uffff\u0001\"", "\u0001$\u0006\uffff\u0001#\u0011\uffff\u0001\"", "\u0001f", "\u0001g", "\u0001h", "\u0001i", "\u0001j", "\u0001.", "\u0001k", "\u0001.", "\u0001:\u0006\uffff\u00019\u0011\uffff\u00018", "\u0001:\u0006\uffff\u00019\u0011\uffff\u00018", "\u0001:\u0006\uffff\u00019\u0011\uffff\u00018", "\u0001:\u0006\uffff\u00019\u0011\uffff\u00018", "\u0001n\u0017\uffff\u0001l\u0001\uffff\u0001o\u001f\uffff\u0001m", "\u0001r\u0017\uffff\u0001p\u0001\uffff\u0001s\u001f\uffff\u0001q", "\u0001t", "\u0001u", "\u0001v", "\u0001w", "\u0001x", "\u0001y", "\u0001z", "\u0001{", "\u0001U\u0006\uffff\u0001T\u0011\uffff\u0001S", "\u0001U\u0006\uffff\u0001T\u0011\uffff\u0001S", "\u0001U\u0006\uffff\u0001T\u0011\uffff\u0001S", "\u0001U\u0006\uffff\u0001T\u0011\uffff\u0001S", "\u0001X\u0006\uffff\u0001W\u0011\uffff\u0001V", "\u0001X\u0006\uffff\u0001W\u0011\uffff\u0001V", "\u0001X\u0006\uffff\u0001W\u0011\uffff\u0001V", "\u0001X\u0006\uffff\u0001W\u0011\uffff\u0001V"};
    static final short[] DFA10_eot = DFA.unpackEncodedString("|\uffff");
    static final short[] DFA10_eof = DFA.unpackEncodedString("|\uffff");
    static final char[] DFA10_min = DFA.unpackEncodedStringToUnsignedChars("\u0001N\u0001\u0002\u0001J\u0001\u0002\u00014\u0004\u0002\u0001\u0003\u0001R\u0001J\u0001R\u0001>\u0001\u0003\u0001\u0002\u00014\u0003\u0002\u0001>\u0001\u001c\u0001\u0003\u00014\u0002\u0003\u0001\u0004\u0001\u0003\u0001\u0002\u0001\u0003\u0004\u0002\u0001\u0003\u0001\u0002\u0006\u0003\u0001\u001c\u0002\u0003\u0001R\u0001\u0003\u0001R\u0001>\u0001\u001c\u0005\u0003\u0001\u0004\u0001\u0003\u0001\u0002\u00014\u0001\u0002\u0001\u0000\u0001\u0002\u0001>\u0001\u0004\u0004\u0003\u0001\u001c\u0001\u0003\u0002\uffff\n\u0003\u0001\u0004\u0001\u0003\u0001\u0002\u0002\u0003\u0001\u0002\n\u0003\u0001\u001c\u0001\u0003\u0001\u001c\u0005\u0003\u0002\u0004\u0010\u0003");
    static final char[] DFA10_max = DFA.unpackEncodedStringToUnsignedChars("\u0001N\u0001\u0002\u0001J\u0001\u0002\u0001R\u0001\u0002\u0002\u0003\u0001\u0002\u0001\u001c\u0001R\u0001J\u0001R\u0001>\u0001\u001c\u0001\u0002\u0001B\u0003\u0002\u0001>\u0002\u001c\u0001R\u0001\u001c\u0001\u0003\u0001>\u0001\u001c\u0001\u0002\u0001\u0003\u0001\u0002\u0002\u0003\u0001\u0002\u0001\u001c\u0001\u0002\u0006\u0003\u0001\u001c\u0001\u0003\u0001\u001c\u0001R\u0001J\u0001R\u0001>\u0001\u001c\u0001\u0003\u0004\u001c\u0001>\u0001\u001c\u0001\u0002\u0001B\u0001\u0002\u0001\u0000\u0001\u0002\u0002>\u0004\u0003\u0002\u001c\u0002\uffff\u0001\u001c\u0005\u0003\u0004\u001c\u0001>\u0001\u001c\u0001\u0002\u0001\u0003\u0001\u001c\u0001\u0002\u0002\u0003\u0004\u001c\u0004\u0003\u0001\u001c\u0001\u0003\u0001\u001c\u0001\u0003\u0004\u001c\u0002>\b\u0003\b\u001c");
    static final short[] DFA10_accept = DFA.unpackEncodedString("F\uffff\u0001\u0001\u0001\u00024\uffff");
    static final short[] DFA10_special = DFA.unpackEncodedString("<\uffff\u0001\u0000?\uffff}>");
    static final short[][] DFA10_transition;
    public static final BitSet FOLLOW_RULE_in_topdown86;
    public static final BitSet FOLLOW_TOKEN_REF_in_topdown91;
    public static final BitSet FOLLOW_RULE_REF_in_topdown95;
    public static final BitSet FOLLOW_setAlt_in_topdown110;
    public static final BitSet FOLLOW_ebnfBlockSet_in_topdown118;
    public static final BitSet FOLLOW_blockSet_in_topdown126;
    public static final BitSet FOLLOW_ALT_in_setAlt141;
    public static final BitSet FOLLOW_ebnfSuffix_in_ebnfBlockSet161;
    public static final BitSet FOLLOW_blockSet_in_ebnfBlockSet163;
    public static final BitSet FOLLOW_BLOCK_in_blockSet244;
    public static final BitSet FOLLOW_ALT_in_blockSet249;
    public static final BitSet FOLLOW_elementOptions_in_blockSet251;
    public static final BitSet FOLLOW_setElement_in_blockSet256;
    public static final BitSet FOLLOW_ALT_in_blockSet263;
    public static final BitSet FOLLOW_elementOptions_in_blockSet265;
    public static final BitSet FOLLOW_setElement_in_blockSet268;
    public static final BitSet FOLLOW_BLOCK_in_blockSet313;
    public static final BitSet FOLLOW_ALT_in_blockSet316;
    public static final BitSet FOLLOW_elementOptions_in_blockSet318;
    public static final BitSet FOLLOW_setElement_in_blockSet321;
    public static final BitSet FOLLOW_ALT_in_blockSet328;
    public static final BitSet FOLLOW_elementOptions_in_blockSet330;
    public static final BitSet FOLLOW_setElement_in_blockSet333;
    public static final BitSet FOLLOW_STRING_LITERAL_in_setElement373;
    public static final BitSet FOLLOW_elementOptions_in_setElement375;
    public static final BitSet FOLLOW_STRING_LITERAL_in_setElement388;
    public static final BitSet FOLLOW_TOKEN_REF_in_setElement400;
    public static final BitSet FOLLOW_elementOptions_in_setElement402;
    public static final BitSet FOLLOW_TOKEN_REF_in_setElement414;
    public static final BitSet FOLLOW_RANGE_in_setElement425;
    public static final BitSet FOLLOW_STRING_LITERAL_in_setElement429;
    public static final BitSet FOLLOW_STRING_LITERAL_in_setElement433;
    public static final BitSet FOLLOW_ELEMENT_OPTIONS_in_elementOptions455;
    public static final BitSet FOLLOW_elementOption_in_elementOptions457;
    public static final BitSet FOLLOW_ID_in_elementOption470;
    public static final BitSet FOLLOW_ASSIGN_in_elementOption476;
    public static final BitSet FOLLOW_ID_in_elementOption480;
    public static final BitSet FOLLOW_ID_in_elementOption484;
    public static final BitSet FOLLOW_ASSIGN_in_elementOption491;
    public static final BitSet FOLLOW_ID_in_elementOption493;
    public static final BitSet FOLLOW_STRING_LITERAL_in_elementOption497;
    public static final BitSet FOLLOW_ASSIGN_in_elementOption504;
    public static final BitSet FOLLOW_ID_in_elementOption506;
    public static final BitSet FOLLOW_ACTION_in_elementOption510;
    public static final BitSet FOLLOW_ASSIGN_in_elementOption517;
    public static final BitSet FOLLOW_ID_in_elementOption519;
    public static final BitSet FOLLOW_INT_in_elementOption523;

    public TreeRewriter[] getDelegates() {
        return new TreeRewriter[0];
    }

    public BlockSetTransformer(TreeNodeStream input) {
        this(input, new RecognizerSharedState());
    }

    public BlockSetTransformer(TreeNodeStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }

    public TreeAdaptor getTreeAdaptor() {
        return this.adaptor;
    }

    @Override
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override
    public String getGrammarFileName() {
        return "org\\antlr\\v4\\parse\\BlockSetTransformer.g";
    }

    public BlockSetTransformer(TreeNodeStream input, Grammar g) {
        this(input, new RecognizerSharedState());
        this.g = g;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final topdown_return topdown() throws RecognitionException {
        topdown_return retval = new topdown_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        GrammarAST _first_0 = null;
        GrammarAST _last = null;
        CommonTree id = null;
        GrammarAST RULE1 = null;
        GrammarAST wildcard2 = null;
        setAlt_return setAlt3 = null;
        ebnfBlockSet_return ebnfBlockSet4 = null;
        blockSet_return blockSet5 = null;
        Object id_tree = null;
        Object RULE1_tree = null;
        Object wildcard2_tree = null;
        try {
            int alt3 = 4;
            switch (this.input.LA(1)) {
                case 94: {
                    alt3 = 1;
                    break;
                }
                case 74: {
                    alt3 = 2;
                    break;
                }
                case 80: 
                case 89: 
                case 90: {
                    alt3 = 3;
                    break;
                }
                case 78: {
                    alt3 = 4;
                    break;
                }
                default: {
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                    }
                    NoViableAltException nvae = new NoViableAltException("", 3, 0, this.input);
                    throw nvae;
                }
            }
            switch (alt3) {
                case 1: {
                    GrammarAST _save_last_1 = _last = (GrammarAST)this.input.LT(1);
                    CommonTree _first_1 = null;
                    _last = (GrammarAST)this.input.LT(1);
                    RULE1 = (GrammarAST)this.match(this.input, 94, FOLLOW_RULE_in_topdown86);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_0 == null) {
                        _first_0 = RULE1;
                    }
                    this.match(this.input, 2, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    int alt1 = 2;
                    int LA1_0 = this.input.LA(1);
                    if (LA1_0 == 66) {
                        alt1 = 1;
                    } else if (LA1_0 == 57) {
                        alt1 = 2;
                    } else {
                        if (this.state.backtracking > 0) {
                            this.state.failed = true;
                            return retval;
                        }
                        NoViableAltException nvae = new NoViableAltException("", 1, 0, this.input);
                        throw nvae;
                    }
                    switch (alt1) {
                        case 1: {
                            _last = (GrammarAST)this.input.LT(1);
                            id = (GrammarAST)this.match(this.input, 66, FOLLOW_TOKEN_REF_in_topdown91);
                            if (this.state.failed) {
                                return retval;
                            }
                            if (this.state.backtracking == 1 && _first_1 == null) {
                                _first_1 = id;
                            }
                            if (this.state.backtracking != 1) break;
                            retval.tree = _first_0;
                            if (this.adaptor.getParent(retval.tree) == null || !this.adaptor.isNil(this.adaptor.getParent(retval.tree))) break;
                            retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                            break;
                        }
                        case 2: {
                            _last = (GrammarAST)this.input.LT(1);
                            id = (GrammarAST)this.match(this.input, 57, FOLLOW_RULE_REF_in_topdown95);
                            if (this.state.failed) {
                                return retval;
                            }
                            if (this.state.backtracking == 1 && _first_1 == null) {
                                _first_1 = id;
                            }
                            if (this.state.backtracking != 1) break;
                            retval.tree = _first_0;
                            if (this.adaptor.getParent(retval.tree) == null || !this.adaptor.isNil(this.adaptor.getParent(retval.tree))) break;
                            retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                            break;
                        }
                    }
                    if (this.state.backtracking == 1) {
                        this.currentRuleName = id != null ? id.getText() : null;
                    }
                    int cnt2 = 0;
                    block23: while (true) {
                        int alt2 = 2;
                        int LA2_0 = this.input.LA(1);
                        if (LA2_0 >= 4 && LA2_0 <= 100) {
                            alt2 = 1;
                        } else if (LA2_0 == 3) {
                            alt2 = 2;
                        }
                        switch (alt2) {
                            case 1: {
                                _last = (GrammarAST)this.input.LT(1);
                                wildcard2 = (GrammarAST)this.input.LT(1);
                                this.matchAny(this.input);
                                if (this.state.failed) {
                                    return retval;
                                }
                                if (this.state.backtracking == 1 && _first_1 == null) {
                                    _first_1 = wildcard2;
                                }
                                if (this.state.backtracking != 1) break;
                                retval.tree = _first_0;
                                if (this.adaptor.getParent(retval.tree) == null || !this.adaptor.isNil(this.adaptor.getParent(retval.tree))) break;
                                retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                                break;
                            }
                            default: {
                                if (cnt2 >= 1) break block23;
                                if (this.state.backtracking > 0) {
                                    this.state.failed = true;
                                    return retval;
                                }
                                EarlyExitException eee = new EarlyExitException(2, this.input);
                                throw eee;
                            }
                        }
                        ++cnt2;
                    }
                    this.match(this.input, 3, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    _last = _save_last_1;
                    if (this.state.backtracking != 1) return retval;
                    retval.tree = _first_0;
                    if (this.adaptor.getParent(retval.tree) == null) return retval;
                    if (!this.adaptor.isNil(this.adaptor.getParent(retval.tree))) return retval;
                    retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                    return retval;
                }
                case 2: {
                    _last = (GrammarAST)this.input.LT(1);
                    this.pushFollow(FOLLOW_setAlt_in_topdown110);
                    setAlt3 = this.setAlt();
                    --this.state._fsp;
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_0 == null) {
                        _first_0 = (GrammarAST)((RuleReturnScope)setAlt3).getTree();
                    }
                    if (this.state.backtracking != 1) return retval;
                    retval.tree = _first_0;
                    if (this.adaptor.getParent(retval.tree) == null) return retval;
                    if (!this.adaptor.isNil(this.adaptor.getParent(retval.tree))) return retval;
                    retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                    return retval;
                }
                case 3: {
                    _last = (GrammarAST)this.input.LT(1);
                    this.pushFollow(FOLLOW_ebnfBlockSet_in_topdown118);
                    ebnfBlockSet4 = this.ebnfBlockSet();
                    --this.state._fsp;
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_0 == null) {
                        _first_0 = (GrammarAST)((RuleReturnScope)ebnfBlockSet4).getTree();
                    }
                    if (this.state.backtracking != 1) return retval;
                    retval.tree = _first_0;
                    if (this.adaptor.getParent(retval.tree) == null) return retval;
                    if (!this.adaptor.isNil(this.adaptor.getParent(retval.tree))) return retval;
                    retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                    return retval;
                }
                case 4: {
                    _last = (GrammarAST)this.input.LT(1);
                    this.pushFollow(FOLLOW_blockSet_in_topdown126);
                    blockSet5 = this.blockSet();
                    --this.state._fsp;
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_0 == null) {
                        _first_0 = (GrammarAST)((RuleReturnScope)blockSet5).getTree();
                    }
                    if (this.state.backtracking != 1) return retval;
                    retval.tree = _first_0;
                    if (this.adaptor.getParent(retval.tree) == null) return retval;
                    if (!this.adaptor.isNil(this.adaptor.getParent(retval.tree))) return retval;
                    retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
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
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final setAlt_return setAlt() throws RecognitionException {
        setAlt_return retval = new setAlt_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        GrammarAST _first_0 = null;
        GrammarAST _last = null;
        GrammarAST ALT6 = null;
        Object ALT6_tree = null;
        try {
            if (!this.inContext("RULE BLOCK")) {
                if (this.state.backtracking <= 0) throw new FailedPredicateException(this.input, "setAlt", "inContext(\"RULE BLOCK\")");
                this.state.failed = true;
                return retval;
            }
            _last = (GrammarAST)this.input.LT(1);
            ALT6 = (GrammarAST)this.match(this.input, 74, FOLLOW_ALT_in_setAlt141);
            if (this.state.failed) {
                return retval;
            }
            if (this.state.backtracking == 1 && _first_0 == null) {
                _first_0 = ALT6;
            }
            if (this.state.backtracking == 1) {
                this.currentAlt = (GrammarAST)retval.start;
            }
            if (this.state.backtracking != 1) return retval;
            retval.tree = _first_0;
            if (this.adaptor.getParent(retval.tree) == null) return retval;
            if (!this.adaptor.isNil(this.adaptor.getParent(retval.tree))) return retval;
            retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
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
    public final ebnfBlockSet_return ebnfBlockSet() throws RecognitionException {
        GrammarAST _save_last_1;
        RewriteRuleSubtreeStream stream_blockSet;
        RewriteRuleSubtreeStream stream_ebnfSuffix;
        GrammarAST _last;
        GrammarAST root_0;
        ebnfBlockSet_return retval;
        block16: {
            blockSet_return blockSet8;
            block15: {
                block14: {
                    ebnfSuffix_return ebnfSuffix7;
                    GrammarAST _first_0;
                    block13: {
                        retval = new ebnfBlockSet_return();
                        retval.start = this.input.LT(1);
                        root_0 = null;
                        _first_0 = null;
                        _last = null;
                        ebnfSuffix7 = null;
                        blockSet8 = null;
                        stream_ebnfSuffix = new RewriteRuleSubtreeStream(this.adaptor, "rule ebnfSuffix");
                        stream_blockSet = new RewriteRuleSubtreeStream(this.adaptor, "rule blockSet");
                        _save_last_1 = _last = (GrammarAST)this.input.LT(1);
                        Object _first_1 = null;
                        _last = (GrammarAST)this.input.LT(1);
                        this.pushFollow(FOLLOW_ebnfSuffix_in_ebnfBlockSet161);
                        ebnfSuffix7 = this.ebnfSuffix();
                        --this.state._fsp;
                        if (!this.state.failed) break block13;
                        ebnfBlockSet_return ebnfBlockSet_return2 = retval;
                        return ebnfBlockSet_return2;
                    }
                    if (this.state.backtracking == 1) {
                        stream_ebnfSuffix.add(((RuleReturnScope)ebnfSuffix7).getTree());
                    }
                    if (this.state.backtracking == 1 && _first_0 == null) {
                        _first_0 = (GrammarAST)((RuleReturnScope)ebnfSuffix7).getTree();
                    }
                    this.match(this.input, 2, null);
                    if (!this.state.failed) break block14;
                    ebnfBlockSet_return ebnfBlockSet_return3 = retval;
                    return ebnfBlockSet_return3;
                }
                _last = (GrammarAST)this.input.LT(1);
                this.pushFollow(FOLLOW_blockSet_in_ebnfBlockSet163);
                blockSet8 = this.blockSet();
                --this.state._fsp;
                if (!this.state.failed) break block15;
                ebnfBlockSet_return ebnfBlockSet_return4 = retval;
                return ebnfBlockSet_return4;
            }
            if (this.state.backtracking == 1) {
                stream_blockSet.add(((RuleReturnScope)blockSet8).getTree());
            }
            this.match(this.input, 3, null);
            if (!this.state.failed) break block16;
            ebnfBlockSet_return ebnfBlockSet_return5 = retval;
            return ebnfBlockSet_return5;
        }
        try {
            _last = _save_last_1;
            if (this.state.backtracking == 1) {
                retval.tree = root_0;
                RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                root_0 = (GrammarAST)this.adaptor.nil();
                GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                root_1 = (GrammarAST)this.adaptor.becomeRoot(stream_ebnfSuffix.nextNode(), (Object)root_1);
                GrammarAST root_2 = (GrammarAST)this.adaptor.nil();
                root_2 = (GrammarAST)this.adaptor.becomeRoot(new BlockAST(78), (Object)root_2);
                GrammarAST root_3 = (GrammarAST)this.adaptor.nil();
                root_3 = (GrammarAST)this.adaptor.becomeRoot(new AltAST(74), (Object)root_3);
                this.adaptor.addChild(root_3, stream_blockSet.nextTree());
                this.adaptor.addChild(root_2, root_3);
                this.adaptor.addChild(root_1, root_2);
                this.adaptor.addChild(root_0, root_1);
                retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                this.input.replaceChildren(this.adaptor.getParent(retval.start), this.adaptor.getChildIndex(retval.start), this.adaptor.getChildIndex(_last), retval.tree);
            }
            if (this.state.backtracking == 1) {
                GrammarTransformPipeline.setGrammarPtr(this.g, retval.tree);
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
    public final ebnfSuffix_return ebnfSuffix() throws RecognitionException {
        ebnfSuffix_return retval = new ebnfSuffix_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        Object _first_0 = null;
        GrammarAST _last = null;
        GrammarAST set9 = null;
        Object set9_tree = null;
        try {
            block7: {
                block8: {
                    block6: {
                        _last = (GrammarAST)this.input.LT(1);
                        set9 = (GrammarAST)this.input.LT(1);
                        if (this.input.LA(1) != 80 && (this.input.LA(1) < 89 || this.input.LA(1) > 90)) break block6;
                        this.input.consume();
                        this.state.errorRecovery = false;
                        this.state.failed = false;
                        if (this.state.backtracking != 1) break block7;
                        break block8;
                    }
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, this.input);
                    throw mse;
                }
                retval.tree = _first_0;
                if (this.adaptor.getParent(retval.tree) != null && this.adaptor.isNil(this.adaptor.getParent(retval.tree))) {
                    retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                }
            }
            if (this.state.backtracking != 1) return retval;
            retval.tree = (GrammarAST)this.adaptor.dupNode((GrammarAST)retval.start);
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
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final blockSet_return blockSet() throws RecognitionException {
        blockSet_return retval = new blockSet_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        GrammarAST _first_0 = null;
        GrammarAST _last = null;
        GrammarAST alt = null;
        GrammarAST BLOCK10 = null;
        GrammarAST ALT13 = null;
        GrammarAST BLOCK16 = null;
        GrammarAST ALT17 = null;
        GrammarAST ALT20 = null;
        elementOptions_return elementOptions11 = null;
        setElement_return setElement12 = null;
        elementOptions_return elementOptions14 = null;
        setElement_return setElement15 = null;
        elementOptions_return elementOptions18 = null;
        setElement_return setElement19 = null;
        elementOptions_return elementOptions21 = null;
        setElement_return setElement22 = null;
        Object alt_tree = null;
        Object BLOCK10_tree = null;
        Object ALT13_tree = null;
        Object BLOCK16_tree = null;
        Object ALT17_tree = null;
        Object ALT20_tree = null;
        RewriteRuleNodeStream stream_ALT = new RewriteRuleNodeStream(this.adaptor, "token ALT");
        RewriteRuleNodeStream stream_BLOCK = new RewriteRuleNodeStream(this.adaptor, "token BLOCK");
        RewriteRuleSubtreeStream stream_setElement = new RewriteRuleSubtreeStream(this.adaptor, "rule setElement");
        RewriteRuleSubtreeStream stream_elementOptions = new RewriteRuleSubtreeStream(this.adaptor, "rule elementOptions");
        boolean inLexer = Grammar.isTokenName(this.currentRuleName);
        try {
            int alt10 = 2;
            alt10 = this.dfa10.predict(this.input);
            switch (alt10) {
                case 1: {
                    if (!this.inContext("RULE")) {
                        if (this.state.backtracking <= 0) throw new FailedPredicateException(this.input, "blockSet", "inContext(\"RULE\")");
                        this.state.failed = true;
                        return retval;
                    }
                    GrammarAST _save_last_1 = _last = (GrammarAST)this.input.LT(1);
                    GrammarAST _first_1 = null;
                    _last = (GrammarAST)this.input.LT(1);
                    BLOCK10 = (GrammarAST)this.match(this.input, 78, FOLLOW_BLOCK_in_blockSet244);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1) {
                        stream_BLOCK.add(BLOCK10);
                    }
                    if (this.state.backtracking == 1 && _first_0 == null) {
                        _first_0 = BLOCK10;
                    }
                    this.match(this.input, 2, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    GrammarAST _save_last_2 = _last = (GrammarAST)this.input.LT(1);
                    Object _first_2 = null;
                    _last = (GrammarAST)this.input.LT(1);
                    alt = (GrammarAST)this.match(this.input, 74, FOLLOW_ALT_in_blockSet249);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1) {
                        stream_ALT.add(alt);
                    }
                    if (this.state.backtracking == 1 && _first_1 == null) {
                        _first_1 = alt;
                    }
                    this.match(this.input, 2, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    int alt4 = 2;
                    int LA4_0 = this.input.LA(1);
                    if (LA4_0 == 82) {
                        alt4 = 1;
                    }
                    switch (alt4) {
                        case 1: {
                            _last = (GrammarAST)this.input.LT(1);
                            this.pushFollow(FOLLOW_elementOptions_in_blockSet251);
                            elementOptions11 = this.elementOptions();
                            --this.state._fsp;
                            if (this.state.failed) {
                                return retval;
                            }
                            if (this.state.backtracking == 1) {
                                stream_elementOptions.add(((RuleReturnScope)elementOptions11).getTree());
                            }
                            if (this.state.backtracking != 1) break;
                            retval.tree = _first_0;
                            if (this.adaptor.getParent(retval.tree) == null || !this.adaptor.isNil(this.adaptor.getParent(retval.tree))) break;
                            retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                            break;
                        }
                    }
                    if (((AltAST)alt).altLabel != null) {
                        if (this.state.backtracking <= 0) throw new FailedPredicateException(this.input, "blockSet", "((AltAST)$alt).altLabel==null");
                        this.state.failed = true;
                        return retval;
                    }
                    _last = (GrammarAST)this.input.LT(1);
                    this.pushFollow(FOLLOW_setElement_in_blockSet256);
                    setElement12 = this.setElement(inLexer);
                    --this.state._fsp;
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1) {
                        stream_setElement.add(((RuleReturnScope)setElement12).getTree());
                    }
                    this.match(this.input, 3, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    _last = _save_last_2;
                    int cnt6 = 0;
                    block26: while (true) {
                        int alt6 = 2;
                        int LA6_0 = this.input.LA(1);
                        if (LA6_0 == 74) {
                            alt6 = 1;
                        }
                        switch (alt6) {
                            case 1: {
                                GrammarAST _save_last_22 = _last = (GrammarAST)this.input.LT(1);
                                Object _first_22 = null;
                                _last = (GrammarAST)this.input.LT(1);
                                ALT13 = (GrammarAST)this.match(this.input, 74, FOLLOW_ALT_in_blockSet263);
                                if (this.state.failed) {
                                    return retval;
                                }
                                if (this.state.backtracking == 1) {
                                    stream_ALT.add(ALT13);
                                }
                                if (this.state.backtracking == 1 && _first_1 == null) {
                                    _first_1 = ALT13;
                                }
                                this.match(this.input, 2, null);
                                if (this.state.failed) {
                                    return retval;
                                }
                                int alt5 = 2;
                                int LA5_0 = this.input.LA(1);
                                if (LA5_0 == 82) {
                                    alt5 = 1;
                                }
                                switch (alt5) {
                                    case 1: {
                                        _last = (GrammarAST)this.input.LT(1);
                                        this.pushFollow(FOLLOW_elementOptions_in_blockSet265);
                                        elementOptions14 = this.elementOptions();
                                        --this.state._fsp;
                                        if (this.state.failed) {
                                            return retval;
                                        }
                                        if (this.state.backtracking == 1) {
                                            stream_elementOptions.add(((RuleReturnScope)elementOptions14).getTree());
                                        }
                                        if (this.state.backtracking != 1) break;
                                        retval.tree = _first_0;
                                        if (this.adaptor.getParent(retval.tree) == null || !this.adaptor.isNil(this.adaptor.getParent(retval.tree))) break;
                                        retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                                        break;
                                    }
                                }
                                _last = (GrammarAST)this.input.LT(1);
                                this.pushFollow(FOLLOW_setElement_in_blockSet268);
                                setElement15 = this.setElement(inLexer);
                                --this.state._fsp;
                                if (this.state.failed) {
                                    return retval;
                                }
                                if (this.state.backtracking == 1) {
                                    stream_setElement.add(((RuleReturnScope)setElement15).getTree());
                                }
                                this.match(this.input, 3, null);
                                if (this.state.failed) {
                                    return retval;
                                }
                                _last = _save_last_22;
                                if (this.state.backtracking != 1) break;
                                retval.tree = _first_0;
                                if (this.adaptor.getParent(retval.tree) == null || !this.adaptor.isNil(this.adaptor.getParent(retval.tree))) break;
                                retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                                break;
                            }
                            default: {
                                if (cnt6 >= 1) break block26;
                                if (this.state.backtracking > 0) {
                                    this.state.failed = true;
                                    return retval;
                                }
                                EarlyExitException eee = new EarlyExitException(6, this.input);
                                throw eee;
                            }
                        }
                        ++cnt6;
                    }
                    this.match(this.input, 3, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    _last = _save_last_1;
                    if (this.state.backtracking != 1) break;
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                    root_1 = (GrammarAST)this.adaptor.becomeRoot(new BlockAST(78, BLOCK10.token), (Object)root_1);
                    GrammarAST root_2 = (GrammarAST)this.adaptor.nil();
                    root_2 = (GrammarAST)this.adaptor.becomeRoot(new AltAST(74, BLOCK10.token, "ALT"), (Object)root_2);
                    GrammarAST root_3 = (GrammarAST)this.adaptor.nil();
                    root_3 = (GrammarAST)this.adaptor.becomeRoot((GrammarAST)this.adaptor.create(98, BLOCK10.token, "SET"), (Object)root_3);
                    if (!stream_setElement.hasNext()) {
                        throw new RewriteEarlyExitException();
                    }
                    while (stream_setElement.hasNext()) {
                        this.adaptor.addChild(root_3, stream_setElement.nextTree());
                    }
                    stream_setElement.reset();
                    this.adaptor.addChild(root_2, root_3);
                    this.adaptor.addChild(root_1, root_2);
                    this.adaptor.addChild(root_0, root_1);
                    retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                    this.input.replaceChildren(this.adaptor.getParent(retval.start), this.adaptor.getChildIndex(retval.start), this.adaptor.getChildIndex(_last), retval.tree);
                    break;
                }
                case 2: {
                    if (this.inContext("RULE")) {
                        if (this.state.backtracking <= 0) throw new FailedPredicateException(this.input, "blockSet", "!inContext(\"RULE\")");
                        this.state.failed = true;
                        return retval;
                    }
                    GrammarAST _save_last_1 = _last = (GrammarAST)this.input.LT(1);
                    GrammarAST _first_1 = null;
                    _last = (GrammarAST)this.input.LT(1);
                    BLOCK16 = (GrammarAST)this.match(this.input, 78, FOLLOW_BLOCK_in_blockSet313);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1) {
                        stream_BLOCK.add(BLOCK16);
                    }
                    if (this.state.backtracking == 1 && _first_0 == null) {
                        _first_0 = BLOCK16;
                    }
                    this.match(this.input, 2, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    GrammarAST _save_last_2 = _last = (GrammarAST)this.input.LT(1);
                    Object _first_2 = null;
                    _last = (GrammarAST)this.input.LT(1);
                    ALT17 = (GrammarAST)this.match(this.input, 74, FOLLOW_ALT_in_blockSet316);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1) {
                        stream_ALT.add(ALT17);
                    }
                    if (this.state.backtracking == 1 && _first_1 == null) {
                        _first_1 = ALT17;
                    }
                    this.match(this.input, 2, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    int alt7 = 2;
                    int LA7_0 = this.input.LA(1);
                    if (LA7_0 == 82) {
                        alt7 = 1;
                    }
                    switch (alt7) {
                        case 1: {
                            _last = (GrammarAST)this.input.LT(1);
                            this.pushFollow(FOLLOW_elementOptions_in_blockSet318);
                            elementOptions18 = this.elementOptions();
                            --this.state._fsp;
                            if (this.state.failed) {
                                return retval;
                            }
                            if (this.state.backtracking == 1) {
                                stream_elementOptions.add(((RuleReturnScope)elementOptions18).getTree());
                            }
                            if (this.state.backtracking != 1) break;
                            retval.tree = _first_0;
                            if (this.adaptor.getParent(retval.tree) == null || !this.adaptor.isNil(this.adaptor.getParent(retval.tree))) break;
                            retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                            break;
                        }
                    }
                    _last = (GrammarAST)this.input.LT(1);
                    this.pushFollow(FOLLOW_setElement_in_blockSet321);
                    setElement19 = this.setElement(inLexer);
                    --this.state._fsp;
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1) {
                        stream_setElement.add(((RuleReturnScope)setElement19).getTree());
                    }
                    this.match(this.input, 3, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    _last = _save_last_2;
                    int cnt9 = 0;
                    block28: while (true) {
                        int alt9 = 2;
                        int LA9_0 = this.input.LA(1);
                        if (LA9_0 == 74) {
                            alt9 = 1;
                        }
                        switch (alt9) {
                            case 1: {
                                GrammarAST _save_last_23 = _last = (GrammarAST)this.input.LT(1);
                                Object _first_23 = null;
                                _last = (GrammarAST)this.input.LT(1);
                                ALT20 = (GrammarAST)this.match(this.input, 74, FOLLOW_ALT_in_blockSet328);
                                if (this.state.failed) {
                                    return retval;
                                }
                                if (this.state.backtracking == 1) {
                                    stream_ALT.add(ALT20);
                                }
                                if (this.state.backtracking == 1 && _first_1 == null) {
                                    _first_1 = ALT20;
                                }
                                this.match(this.input, 2, null);
                                if (this.state.failed) {
                                    return retval;
                                }
                                int alt8 = 2;
                                int LA8_0 = this.input.LA(1);
                                if (LA8_0 == 82) {
                                    alt8 = 1;
                                }
                                switch (alt8) {
                                    case 1: {
                                        _last = (GrammarAST)this.input.LT(1);
                                        this.pushFollow(FOLLOW_elementOptions_in_blockSet330);
                                        elementOptions21 = this.elementOptions();
                                        --this.state._fsp;
                                        if (this.state.failed) {
                                            return retval;
                                        }
                                        if (this.state.backtracking == 1) {
                                            stream_elementOptions.add(((RuleReturnScope)elementOptions21).getTree());
                                        }
                                        if (this.state.backtracking != 1) break;
                                        retval.tree = _first_0;
                                        if (this.adaptor.getParent(retval.tree) == null || !this.adaptor.isNil(this.adaptor.getParent(retval.tree))) break;
                                        retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                                        break;
                                    }
                                }
                                _last = (GrammarAST)this.input.LT(1);
                                this.pushFollow(FOLLOW_setElement_in_blockSet333);
                                setElement22 = this.setElement(inLexer);
                                --this.state._fsp;
                                if (this.state.failed) {
                                    return retval;
                                }
                                if (this.state.backtracking == 1) {
                                    stream_setElement.add(((RuleReturnScope)setElement22).getTree());
                                }
                                this.match(this.input, 3, null);
                                if (this.state.failed) {
                                    return retval;
                                }
                                _last = _save_last_23;
                                if (this.state.backtracking != 1) break;
                                retval.tree = _first_0;
                                if (this.adaptor.getParent(retval.tree) == null || !this.adaptor.isNil(this.adaptor.getParent(retval.tree))) break;
                                retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                                break;
                            }
                            default: {
                                if (cnt9 >= 1) break block28;
                                if (this.state.backtracking > 0) {
                                    this.state.failed = true;
                                    return retval;
                                }
                                EarlyExitException eee = new EarlyExitException(9, this.input);
                                throw eee;
                            }
                        }
                        ++cnt9;
                    }
                    this.match(this.input, 3, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    _last = _save_last_1;
                    if (this.state.backtracking != 1) break;
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                    root_1 = (GrammarAST)this.adaptor.becomeRoot((GrammarAST)this.adaptor.create(98, BLOCK16.token, "SET"), (Object)root_1);
                    if (!stream_setElement.hasNext()) {
                        throw new RewriteEarlyExitException();
                    }
                    while (stream_setElement.hasNext()) {
                        this.adaptor.addChild(root_1, stream_setElement.nextTree());
                    }
                    stream_setElement.reset();
                    this.adaptor.addChild(root_0, root_1);
                    retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                    this.input.replaceChildren(this.adaptor.getParent(retval.start), this.adaptor.getChildIndex(retval.start), this.adaptor.getChildIndex(_last), retval.tree);
                    break;
                }
            }
            if (this.state.backtracking != 1) return retval;
            GrammarTransformPipeline.setGrammarPtr(this.g, retval.tree);
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
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final setElement_return setElement(boolean inLexer) throws RecognitionException {
        setElement_return retval = new setElement_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        GrammarAST _first_0 = null;
        GrammarAST _last = null;
        GrammarAST a = null;
        GrammarAST b = null;
        GrammarAST TOKEN_REF24 = null;
        GrammarAST TOKEN_REF26 = null;
        GrammarAST RANGE27 = null;
        elementOptions_return elementOptions23 = null;
        elementOptions_return elementOptions25 = null;
        Object a_tree = null;
        Object b_tree = null;
        Object TOKEN_REF24_tree = null;
        Object TOKEN_REF26_tree = null;
        Object RANGE27_tree = null;
        try {
            int alt11;
            block54: {
                int LA11_0;
                block56: {
                    block55: {
                        alt11 = 5;
                        LA11_0 = this.input.LA(1);
                        if (LA11_0 != 62) break block55;
                        int LA11_1 = this.input.LA(2);
                        if (LA11_1 == 2) {
                            alt11 = 1;
                            break block54;
                        } else if (LA11_1 == 3) {
                            alt11 = 2;
                            break block54;
                        } else {
                            if (this.state.backtracking > 0) {
                                this.state.failed = true;
                                return retval;
                            }
                            int nvaeMark = this.input.mark();
                            try {
                                this.input.consume();
                                NoViableAltException nvae = new NoViableAltException("", 11, 1, this.input);
                                throw nvae;
                            }
                            catch (Throwable throwable) {
                                this.input.rewind(nvaeMark);
                                throw throwable;
                            }
                        }
                    }
                    if (LA11_0 != 66 || inLexer) break block56;
                    int LA11_2 = this.input.LA(2);
                    if (LA11_2 == 2 && !inLexer) {
                        alt11 = 3;
                        break block54;
                    } else if (LA11_2 == 3 && !inLexer) {
                        alt11 = 4;
                    }
                    break block54;
                }
                if (LA11_0 == 52 && inLexer) {
                    alt11 = 5;
                }
            }
            switch (alt11) {
                case 1: {
                    _last = (GrammarAST)this.input.LT(1);
                    GrammarAST _save_last_1 = _last;
                    GrammarAST _first_1 = null;
                    _last = (GrammarAST)this.input.LT(1);
                    a = (GrammarAST)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_setElement373);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_0 == null) {
                        _first_0 = a;
                    }
                    this.match(this.input, 2, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    _last = (GrammarAST)this.input.LT(1);
                    this.pushFollow(FOLLOW_elementOptions_in_setElement375);
                    elementOptions23 = this.elementOptions();
                    --this.state._fsp;
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_1 == null) {
                        _first_1 = (GrammarAST)((RuleReturnScope)elementOptions23).getTree();
                    }
                    this.match(this.input, 3, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    _last = _save_last_1;
                    if (inLexer && CharSupport.getCharValueFromGrammarCharLiteral(a.getText()) == -1) {
                        if (this.state.backtracking <= 0) throw new FailedPredicateException(this.input, "setElement", "!inLexer || CharSupport.getCharValueFromGrammarCharLiteral($a.getText())!=-1");
                        this.state.failed = true;
                        return retval;
                    }
                    if (this.state.backtracking != 1) break;
                    retval.tree = _first_0;
                    if (this.adaptor.getParent(retval.tree) == null || !this.adaptor.isNil(this.adaptor.getParent(retval.tree))) break;
                    retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                    break;
                }
                case 2: {
                    _last = (GrammarAST)this.input.LT(1);
                    a = (GrammarAST)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_setElement388);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_0 == null) {
                        _first_0 = a;
                    }
                    if (inLexer && CharSupport.getCharValueFromGrammarCharLiteral(a.getText()) == -1) {
                        if (this.state.backtracking <= 0) throw new FailedPredicateException(this.input, "setElement", "!inLexer || CharSupport.getCharValueFromGrammarCharLiteral($a.getText())!=-1");
                        this.state.failed = true;
                        return retval;
                    }
                    if (this.state.backtracking != 1) break;
                    retval.tree = _first_0;
                    if (this.adaptor.getParent(retval.tree) == null || !this.adaptor.isNil(this.adaptor.getParent(retval.tree))) break;
                    retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                    break;
                }
                case 3: {
                    if (inLexer) {
                        if (this.state.backtracking <= 0) throw new FailedPredicateException(this.input, "setElement", "!inLexer");
                        this.state.failed = true;
                        return retval;
                    }
                    GrammarAST _save_last_1 = _last = (GrammarAST)this.input.LT(1);
                    GrammarAST _first_1 = null;
                    _last = (GrammarAST)this.input.LT(1);
                    TOKEN_REF24 = (GrammarAST)this.match(this.input, 66, FOLLOW_TOKEN_REF_in_setElement400);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_0 == null) {
                        _first_0 = TOKEN_REF24;
                    }
                    this.match(this.input, 2, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    _last = (GrammarAST)this.input.LT(1);
                    this.pushFollow(FOLLOW_elementOptions_in_setElement402);
                    elementOptions25 = this.elementOptions();
                    --this.state._fsp;
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_1 == null) {
                        _first_1 = (GrammarAST)((RuleReturnScope)elementOptions25).getTree();
                    }
                    this.match(this.input, 3, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    _last = _save_last_1;
                    if (this.state.backtracking != 1) break;
                    retval.tree = _first_0;
                    if (this.adaptor.getParent(retval.tree) == null || !this.adaptor.isNil(this.adaptor.getParent(retval.tree))) break;
                    retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                    break;
                }
                case 4: {
                    if (inLexer) {
                        if (this.state.backtracking <= 0) throw new FailedPredicateException(this.input, "setElement", "!inLexer");
                        this.state.failed = true;
                        return retval;
                    }
                    _last = (GrammarAST)this.input.LT(1);
                    TOKEN_REF26 = (GrammarAST)this.match(this.input, 66, FOLLOW_TOKEN_REF_in_setElement414);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_0 == null) {
                        _first_0 = TOKEN_REF26;
                    }
                    if (this.state.backtracking != 1) break;
                    retval.tree = _first_0;
                    if (this.adaptor.getParent(retval.tree) == null || !this.adaptor.isNil(this.adaptor.getParent(retval.tree))) break;
                    retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                    break;
                }
                case 5: {
                    if (!inLexer) {
                        if (this.state.backtracking <= 0) throw new FailedPredicateException(this.input, "setElement", "inLexer");
                        this.state.failed = true;
                        return retval;
                    }
                    GrammarAST _save_last_1 = _last = (GrammarAST)this.input.LT(1);
                    GrammarAST _first_1 = null;
                    _last = (GrammarAST)this.input.LT(1);
                    RANGE27 = (GrammarAST)this.match(this.input, 52, FOLLOW_RANGE_in_setElement425);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_0 == null) {
                        _first_0 = RANGE27;
                    }
                    this.match(this.input, 2, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    _last = (GrammarAST)this.input.LT(1);
                    a = (GrammarAST)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_setElement429);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_1 == null) {
                        _first_1 = a;
                    }
                    _last = (GrammarAST)this.input.LT(1);
                    b = (GrammarAST)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_setElement433);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_1 == null) {
                        _first_1 = b;
                    }
                    this.match(this.input, 3, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    _last = _save_last_1;
                    if (CharSupport.getCharValueFromGrammarCharLiteral(a.getText()) == -1 || CharSupport.getCharValueFromGrammarCharLiteral(b.getText()) == -1) {
                        if (this.state.backtracking <= 0) throw new FailedPredicateException(this.input, "setElement", "CharSupport.getCharValueFromGrammarCharLiteral($a.getText())!=-1 &&\r\n\t\t\t CharSupport.getCharValueFromGrammarCharLiteral($b.getText())!=-1");
                        this.state.failed = true;
                        return retval;
                    }
                    if (this.state.backtracking != 1) break;
                    retval.tree = _first_0;
                    if (this.adaptor.getParent(retval.tree) == null || !this.adaptor.isNil(this.adaptor.getParent(retval.tree))) break;
                    retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                    break;
                }
            }
            if (this.state.backtracking == 1) {
                retval.tree = _first_0;
                if (this.adaptor.getParent(retval.tree) != null && this.adaptor.isNil(this.adaptor.getParent(retval.tree))) {
                    retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                }
            }
            if (this.state.backtracking != 1) return retval;
            GrammarTransformPipeline.setGrammarPtr(this.g, retval.tree);
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
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final elementOptions_return elementOptions() throws RecognitionException {
        elementOptions_return retval = new elementOptions_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        GrammarAST _first_0 = null;
        GrammarAST _last = null;
        GrammarAST ELEMENT_OPTIONS28 = null;
        elementOption_return elementOption29 = null;
        Object ELEMENT_OPTIONS28_tree = null;
        try {
            GrammarAST _save_last_1 = _last = (GrammarAST)this.input.LT(1);
            GrammarAST _first_1 = null;
            _last = (GrammarAST)this.input.LT(1);
            ELEMENT_OPTIONS28 = (GrammarAST)this.match(this.input, 82, FOLLOW_ELEMENT_OPTIONS_in_elementOptions455);
            if (this.state.failed) {
                return retval;
            }
            if (this.state.backtracking == 1 && _first_0 == null) {
                _first_0 = ELEMENT_OPTIONS28;
            }
            if (this.input.LA(1) == 2) {
                this.match(this.input, 2, null);
                if (this.state.failed) {
                    return retval;
                }
                block7: while (true) {
                    int alt12 = 2;
                    int LA12_0 = this.input.LA(1);
                    if (LA12_0 == 10 || LA12_0 == 28) {
                        alt12 = 1;
                    }
                    switch (alt12) {
                        case 1: {
                            _last = (GrammarAST)this.input.LT(1);
                            this.pushFollow(FOLLOW_elementOption_in_elementOptions457);
                            elementOption29 = this.elementOption();
                            --this.state._fsp;
                            if (this.state.failed) {
                                return retval;
                            }
                            if (this.state.backtracking == 1 && _first_1 == null) {
                                _first_1 = (GrammarAST)((RuleReturnScope)elementOption29).getTree();
                            }
                            if (this.state.backtracking != 1) continue block7;
                            retval.tree = _first_0;
                            if (this.adaptor.getParent(retval.tree) == null || !this.adaptor.isNil(this.adaptor.getParent(retval.tree))) continue block7;
                            retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                            continue block7;
                        }
                    }
                    break;
                }
                this.match(this.input, 3, null);
                if (this.state.failed) {
                    return retval;
                }
            }
            _last = _save_last_1;
            if (this.state.backtracking != 1) return retval;
            retval.tree = _first_0;
            if (this.adaptor.getParent(retval.tree) == null) return retval;
            if (!this.adaptor.isNil(this.adaptor.getParent(retval.tree))) return retval;
            retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
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
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final elementOption_return elementOption() throws RecognitionException {
        elementOption_return retval = new elementOption_return();
        retval.start = this.input.LT(1);
        Object root_0 = null;
        GrammarAST _first_0 = null;
        GrammarAST _last = null;
        GrammarAST id = null;
        GrammarAST v = null;
        GrammarAST ID30 = null;
        GrammarAST ASSIGN31 = null;
        GrammarAST ASSIGN32 = null;
        GrammarAST ID33 = null;
        GrammarAST ASSIGN34 = null;
        GrammarAST ID35 = null;
        GrammarAST ASSIGN36 = null;
        GrammarAST ID37 = null;
        Object id_tree = null;
        Object v_tree = null;
        Object ID30_tree = null;
        Object ASSIGN31_tree = null;
        Object ASSIGN32_tree = null;
        Object ID33_tree = null;
        Object ASSIGN34_tree = null;
        Object ID35_tree = null;
        Object ASSIGN36_tree = null;
        Object ID37_tree = null;
        try {
            int alt13;
            block71: {
                alt13 = 5;
                int LA13_0 = this.input.LA(1);
                if (LA13_0 == 28) {
                    alt13 = 1;
                } else {
                    if (LA13_0 == 10) {
                        int LA13_2 = this.input.LA(2);
                        if (LA13_2 == 2) {
                            int LA13_3 = this.input.LA(3);
                            if (LA13_3 == 28) {
                                switch (this.input.LA(4)) {
                                    case 28: {
                                        alt13 = 2;
                                        break;
                                    }
                                    case 62: {
                                        alt13 = 3;
                                        break;
                                    }
                                    case 4: {
                                        alt13 = 4;
                                        break;
                                    }
                                    case 30: {
                                        alt13 = 5;
                                        break;
                                    }
                                    default: {
                                        if (this.state.backtracking > 0) {
                                            this.state.failed = true;
                                            return retval;
                                        }
                                        int nvaeMark = this.input.mark();
                                        try {
                                            int nvaeConsume = 0;
                                            while (true) {
                                                if (nvaeConsume >= 3) {
                                                    NoViableAltException nvae = new NoViableAltException("", 13, 4, this.input);
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
                                break block71;
                            } else {
                                if (this.state.backtracking > 0) {
                                    this.state.failed = true;
                                    return retval;
                                }
                                int nvaeMark = this.input.mark();
                                try {
                                    int nvaeConsume = 0;
                                    while (true) {
                                        if (nvaeConsume >= 2) {
                                            NoViableAltException nvae = new NoViableAltException("", 13, 3, this.input);
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
                            return retval;
                        }
                        int nvaeMark = this.input.mark();
                        try {
                            this.input.consume();
                            NoViableAltException nvae = new NoViableAltException("", 13, 2, this.input);
                            throw nvae;
                        }
                        catch (Throwable throwable) {
                            this.input.rewind(nvaeMark);
                            throw throwable;
                        }
                    }
                    if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                    }
                    NoViableAltException nvae = new NoViableAltException("", 13, 0, this.input);
                    throw nvae;
                }
            }
            switch (alt13) {
                case 1: {
                    _last = (GrammarAST)this.input.LT(1);
                    ID30 = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_elementOption470);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_0 == null) {
                        _first_0 = ID30;
                    }
                    if (this.state.backtracking != 1) return retval;
                    retval.tree = _first_0;
                    if (this.adaptor.getParent(retval.tree) == null) return retval;
                    if (!this.adaptor.isNil(this.adaptor.getParent(retval.tree))) return retval;
                    retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                    return retval;
                }
                case 2: {
                    GrammarAST _save_last_1 = _last = (GrammarAST)this.input.LT(1);
                    GrammarAST _first_1 = null;
                    _last = (GrammarAST)this.input.LT(1);
                    ASSIGN31 = (GrammarAST)this.match(this.input, 10, FOLLOW_ASSIGN_in_elementOption476);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_0 == null) {
                        _first_0 = ASSIGN31;
                    }
                    this.match(this.input, 2, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    _last = (GrammarAST)this.input.LT(1);
                    id = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_elementOption480);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_1 == null) {
                        _first_1 = id;
                    }
                    _last = (GrammarAST)this.input.LT(1);
                    v = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_elementOption484);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_1 == null) {
                        _first_1 = v;
                    }
                    this.match(this.input, 3, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    _last = _save_last_1;
                    if (this.state.backtracking != 1) return retval;
                    retval.tree = _first_0;
                    if (this.adaptor.getParent(retval.tree) == null) return retval;
                    if (!this.adaptor.isNil(this.adaptor.getParent(retval.tree))) return retval;
                    retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                    return retval;
                }
                case 3: {
                    GrammarAST _save_last_1 = _last = (GrammarAST)this.input.LT(1);
                    GrammarAST _first_1 = null;
                    _last = (GrammarAST)this.input.LT(1);
                    ASSIGN32 = (GrammarAST)this.match(this.input, 10, FOLLOW_ASSIGN_in_elementOption491);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_0 == null) {
                        _first_0 = ASSIGN32;
                    }
                    this.match(this.input, 2, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    _last = (GrammarAST)this.input.LT(1);
                    ID33 = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_elementOption493);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_1 == null) {
                        _first_1 = ID33;
                    }
                    _last = (GrammarAST)this.input.LT(1);
                    v = (GrammarAST)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_elementOption497);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_1 == null) {
                        _first_1 = v;
                    }
                    this.match(this.input, 3, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    _last = _save_last_1;
                    if (this.state.backtracking != 1) return retval;
                    retval.tree = _first_0;
                    if (this.adaptor.getParent(retval.tree) == null) return retval;
                    if (!this.adaptor.isNil(this.adaptor.getParent(retval.tree))) return retval;
                    retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                    return retval;
                }
                case 4: {
                    GrammarAST _save_last_1 = _last = (GrammarAST)this.input.LT(1);
                    GrammarAST _first_1 = null;
                    _last = (GrammarAST)this.input.LT(1);
                    ASSIGN34 = (GrammarAST)this.match(this.input, 10, FOLLOW_ASSIGN_in_elementOption504);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_0 == null) {
                        _first_0 = ASSIGN34;
                    }
                    this.match(this.input, 2, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    _last = (GrammarAST)this.input.LT(1);
                    ID35 = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_elementOption506);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_1 == null) {
                        _first_1 = ID35;
                    }
                    _last = (GrammarAST)this.input.LT(1);
                    v = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_elementOption510);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_1 == null) {
                        _first_1 = v;
                    }
                    this.match(this.input, 3, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    _last = _save_last_1;
                    if (this.state.backtracking != 1) return retval;
                    retval.tree = _first_0;
                    if (this.adaptor.getParent(retval.tree) == null) return retval;
                    if (!this.adaptor.isNil(this.adaptor.getParent(retval.tree))) return retval;
                    retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
                    return retval;
                }
                case 5: {
                    GrammarAST _save_last_1 = _last = (GrammarAST)this.input.LT(1);
                    GrammarAST _first_1 = null;
                    _last = (GrammarAST)this.input.LT(1);
                    ASSIGN36 = (GrammarAST)this.match(this.input, 10, FOLLOW_ASSIGN_in_elementOption517);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_0 == null) {
                        _first_0 = ASSIGN36;
                    }
                    this.match(this.input, 2, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    _last = (GrammarAST)this.input.LT(1);
                    ID37 = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_elementOption519);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_1 == null) {
                        _first_1 = ID37;
                    }
                    _last = (GrammarAST)this.input.LT(1);
                    v = (GrammarAST)this.match(this.input, 30, FOLLOW_INT_in_elementOption523);
                    if (this.state.failed) {
                        return retval;
                    }
                    if (this.state.backtracking == 1 && _first_1 == null) {
                        _first_1 = v;
                    }
                    this.match(this.input, 3, null);
                    if (this.state.failed) {
                        return retval;
                    }
                    _last = _save_last_1;
                    if (this.state.backtracking != 1) return retval;
                    retval.tree = _first_0;
                    if (this.adaptor.getParent(retval.tree) == null) return retval;
                    if (!this.adaptor.isNil(this.adaptor.getParent(retval.tree))) return retval;
                    retval.tree = (GrammarAST)this.adaptor.getParent(retval.tree);
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

    static {
        int numStates = DFA10_transitionS.length;
        DFA10_transition = new short[numStates][];
        for (int i = 0; i < numStates; ++i) {
            BlockSetTransformer.DFA10_transition[i] = DFA.unpackEncodedString(DFA10_transitionS[i]);
        }
        FOLLOW_RULE_in_topdown86 = new BitSet(new long[]{4L});
        FOLLOW_TOKEN_REF_in_topdown91 = new BitSet(new long[]{-16L, 0x1FFFFFFFFFL});
        FOLLOW_RULE_REF_in_topdown95 = new BitSet(new long[]{-16L, 0x1FFFFFFFFFL});
        FOLLOW_setAlt_in_topdown110 = new BitSet(new long[]{2L});
        FOLLOW_ebnfBlockSet_in_topdown118 = new BitSet(new long[]{2L});
        FOLLOW_blockSet_in_topdown126 = new BitSet(new long[]{2L});
        FOLLOW_ALT_in_setAlt141 = new BitSet(new long[]{2L});
        FOLLOW_ebnfSuffix_in_ebnfBlockSet161 = new BitSet(new long[]{4L});
        FOLLOW_blockSet_in_ebnfBlockSet163 = new BitSet(new long[]{8L});
        FOLLOW_BLOCK_in_blockSet244 = new BitSet(new long[]{4L});
        FOLLOW_ALT_in_blockSet249 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_blockSet251 = new BitSet(new long[]{0x4010000000000000L, 4L});
        FOLLOW_setElement_in_blockSet256 = new BitSet(new long[]{8L});
        FOLLOW_ALT_in_blockSet263 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_blockSet265 = new BitSet(new long[]{0x4010000000000000L, 4L});
        FOLLOW_setElement_in_blockSet268 = new BitSet(new long[]{8L});
        FOLLOW_BLOCK_in_blockSet313 = new BitSet(new long[]{4L});
        FOLLOW_ALT_in_blockSet316 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_blockSet318 = new BitSet(new long[]{0x4010000000000000L, 4L});
        FOLLOW_setElement_in_blockSet321 = new BitSet(new long[]{8L});
        FOLLOW_ALT_in_blockSet328 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_blockSet330 = new BitSet(new long[]{0x4010000000000000L, 4L});
        FOLLOW_setElement_in_blockSet333 = new BitSet(new long[]{8L});
        FOLLOW_STRING_LITERAL_in_setElement373 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_setElement375 = new BitSet(new long[]{8L});
        FOLLOW_STRING_LITERAL_in_setElement388 = new BitSet(new long[]{2L});
        FOLLOW_TOKEN_REF_in_setElement400 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_setElement402 = new BitSet(new long[]{8L});
        FOLLOW_TOKEN_REF_in_setElement414 = new BitSet(new long[]{2L});
        FOLLOW_RANGE_in_setElement425 = new BitSet(new long[]{4L});
        FOLLOW_STRING_LITERAL_in_setElement429 = new BitSet(new long[]{0x4000000000000000L});
        FOLLOW_STRING_LITERAL_in_setElement433 = new BitSet(new long[]{8L});
        FOLLOW_ELEMENT_OPTIONS_in_elementOptions455 = new BitSet(new long[]{4L});
        FOLLOW_elementOption_in_elementOptions457 = new BitSet(new long[]{268436488L});
        FOLLOW_ID_in_elementOption470 = new BitSet(new long[]{2L});
        FOLLOW_ASSIGN_in_elementOption476 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_elementOption480 = new BitSet(new long[]{0x10000000L});
        FOLLOW_ID_in_elementOption484 = new BitSet(new long[]{8L});
        FOLLOW_ASSIGN_in_elementOption491 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_elementOption493 = new BitSet(new long[]{0x4000000000000000L});
        FOLLOW_STRING_LITERAL_in_elementOption497 = new BitSet(new long[]{8L});
        FOLLOW_ASSIGN_in_elementOption504 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_elementOption506 = new BitSet(new long[]{16L});
        FOLLOW_ACTION_in_elementOption510 = new BitSet(new long[]{8L});
        FOLLOW_ASSIGN_in_elementOption517 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_elementOption519 = new BitSet(new long[]{0x40000000L});
        FOLLOW_INT_in_elementOption523 = new BitSet(new long[]{8L});
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
            return "66:1: blockSet : ({...}? ^( BLOCK ^(alt= ALT ( elementOptions )? {...}? setElement[inLexer] ) ( ^( ALT ( elementOptions )? setElement[inLexer] ) )+ ) -> ^( BLOCK[$BLOCK.token] ^( ALT[$BLOCK.token,\"ALT\"] ^( SET[$BLOCK.token, \"SET\"] ( setElement )+ ) ) ) |{...}? ^( BLOCK ^( ALT ( elementOptions )? setElement[inLexer] ) ( ^( ALT ( elementOptions )? setElement[inLexer] ) )+ ) -> ^( SET[$BLOCK.token, \"SET\"] ( setElement )+ ) );";
        }

        @Override
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TreeNodeStream input = (TreeNodeStream)_input;
            int _s = s;
            switch (s) {
                case 0: {
                    int LA10_60 = input.LA(1);
                    int index10_60 = input.index();
                    input.rewind();
                    s = -1;
                    if (BlockSetTransformer.this.inContext("RULE")) {
                        s = 70;
                    } else if (!BlockSetTransformer.this.inContext("RULE")) {
                        s = 71;
                    }
                    input.seek(index10_60);
                    if (s < 0) break;
                    return s;
                }
            }
            if (((BlockSetTransformer)BlockSetTransformer.this).state.backtracking > 0) {
                ((BlockSetTransformer)BlockSetTransformer.this).state.failed = true;
                return -1;
            }
            NoViableAltException nvae = new NoViableAltException(this.getDescription(), 10, _s, input);
            this.error(nvae);
            throw nvae;
        }
    }

    public static class elementOption_return
    extends TreeRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class elementOptions_return
    extends TreeRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class setElement_return
    extends TreeRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class blockSet_return
    extends TreeRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class ebnfSuffix_return
    extends TreeRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class ebnfBlockSet_return
    extends TreeRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class setAlt_return
    extends TreeRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class topdown_return
    extends TreeRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }
}

