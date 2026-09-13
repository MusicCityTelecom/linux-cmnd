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
import groovyjarjarantlr4.runtime.tree.CommonTreeNodeStream;
import groovyjarjarantlr4.runtime.tree.TreeNodeStream;
import groovyjarjarantlr4.runtime.tree.TreeParser;
import groovyjarjarantlr4.runtime.tree.TreeRuleReturnScope;
import groovyjarjarantlr4.v4.parse.GrammarASTAdaptor;
import groovyjarjarantlr4.v4.tool.ErrorManager;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.AltAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTWithOptions;
import groovyjarjarantlr4.v4.tool.ast.GrammarRootAST;
import groovyjarjarantlr4.v4.tool.ast.PredAST;
import groovyjarjarantlr4.v4.tool.ast.RuleAST;
import groovyjarjarantlr4.v4.tool.ast.TerminalAST;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class GrammarTreeVisitor
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
    public String grammarName;
    public GrammarAST currentRuleAST;
    public String currentModeName = "DEFAULT_MODE";
    public String currentRuleName;
    public GrammarAST currentOuterAltRoot;
    public int currentOuterAltNumber = 1;
    public int rewriteEBNFLevel = 0;
    protected DFA38 dfa38 = new DFA38(this);
    static final String DFA38_eotS = "\u0014\uffff";
    static final String DFA38_eofS = "\u0014\uffff";
    static final String DFA38_minS = "\u0001J\u0001\u0002\u0001\u0004\u0001\u0002\u0002\uffff\u0002\u0003\u0001\u0002\u0001\u0004\u0001\u001c\u0001\u0004\b\u0003";
    static final String DFA38_maxS = "\u0001J\u0001\u0002\u0001d\u0001\u0002\u0002\uffff\u0002\u001c\u0001\u0002\u0001d\u0001\u001c\u0001>\u0004\u0003\u0004\u001c";
    static final String DFA38_acceptS = "\u0004\uffff\u0001\u0001\u0001\u0002\u000e\uffff";
    static final String DFA38_specialS = "\u0014\uffff}>";
    static final String[] DFA38_transitionS = new String[]{"\u0001\u0001", "\u0001\u0002", "\u0001\u0004\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0001\uffff\u0001\u0003\u0001\u0005\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001\u0006", "", "", "\u0001\t\u0006\uffff\u0001\b\u0011\uffff\u0001\u0007", "\u0001\t\u0006\uffff\u0001\b\u0011\uffff\u0001\u0007", "\u0001\n", "\u0001\u0004\u0005\uffff\u0001\u0004\t\uffff\u0001\u0004\u0012\uffff\u0001\u0004\u0006\uffff\u0001\u0004\u0005\uffff\u0001\u0004\u0004\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0004\u0003\uffff\u0001\u0004\u000b\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0002\uffff\u0001\u0005\u0005\uffff\u0002\u0004\u0007\uffff\u0001\u0004\u0001\uffff\u0001\u0004", "\u0001\u000b", "\u0001\u000e\u0017\uffff\u0001\f\u0001\uffff\u0001\u000f\u001f\uffff\u0001\r", "\u0001\u0010", "\u0001\u0011", "\u0001\u0012", "\u0001\u0013", "\u0001\t\u0006\uffff\u0001\b\u0011\uffff\u0001\u0007", "\u0001\t\u0006\uffff\u0001\b\u0011\uffff\u0001\u0007", "\u0001\t\u0006\uffff\u0001\b\u0011\uffff\u0001\u0007", "\u0001\t\u0006\uffff\u0001\b\u0011\uffff\u0001\u0007"};
    static final short[] DFA38_eot = DFA.unpackEncodedString("\u0014\uffff");
    static final short[] DFA38_eof = DFA.unpackEncodedString("\u0014\uffff");
    static final char[] DFA38_min = DFA.unpackEncodedStringToUnsignedChars("\u0001J\u0001\u0002\u0001\u0004\u0001\u0002\u0002\uffff\u0002\u0003\u0001\u0002\u0001\u0004\u0001\u001c\u0001\u0004\b\u0003");
    static final char[] DFA38_max = DFA.unpackEncodedStringToUnsignedChars("\u0001J\u0001\u0002\u0001d\u0001\u0002\u0002\uffff\u0002\u001c\u0001\u0002\u0001d\u0001\u001c\u0001>\u0004\u0003\u0004\u001c");
    static final short[] DFA38_accept = DFA.unpackEncodedString("\u0004\uffff\u0001\u0001\u0001\u0002\u000e\uffff");
    static final short[] DFA38_special = DFA.unpackEncodedString("\u0014\uffff}>");
    static final short[][] DFA38_transition;
    public static final BitSet FOLLOW_GRAMMAR_in_grammarSpec85;
    public static final BitSet FOLLOW_ID_in_grammarSpec87;
    public static final BitSet FOLLOW_prequelConstructs_in_grammarSpec106;
    public static final BitSet FOLLOW_rules_in_grammarSpec123;
    public static final BitSet FOLLOW_mode_in_grammarSpec125;
    public static final BitSet FOLLOW_prequelConstruct_in_prequelConstructs167;
    public static final BitSet FOLLOW_optionsSpec_in_prequelConstruct194;
    public static final BitSet FOLLOW_delegateGrammars_in_prequelConstruct204;
    public static final BitSet FOLLOW_tokensSpec_in_prequelConstruct214;
    public static final BitSet FOLLOW_channelsSpec_in_prequelConstruct224;
    public static final BitSet FOLLOW_action_in_prequelConstruct234;
    public static final BitSet FOLLOW_OPTIONS_in_optionsSpec259;
    public static final BitSet FOLLOW_option_in_optionsSpec261;
    public static final BitSet FOLLOW_ASSIGN_in_option295;
    public static final BitSet FOLLOW_ID_in_option297;
    public static final BitSet FOLLOW_optionValue_in_option301;
    public static final BitSet FOLLOW_IMPORT_in_delegateGrammars389;
    public static final BitSet FOLLOW_delegateGrammar_in_delegateGrammars391;
    public static final BitSet FOLLOW_ASSIGN_in_delegateGrammar420;
    public static final BitSet FOLLOW_ID_in_delegateGrammar424;
    public static final BitSet FOLLOW_ID_in_delegateGrammar428;
    public static final BitSet FOLLOW_ID_in_delegateGrammar443;
    public static final BitSet FOLLOW_TOKENS_SPEC_in_tokensSpec477;
    public static final BitSet FOLLOW_tokenSpec_in_tokensSpec479;
    public static final BitSet FOLLOW_ID_in_tokenSpec502;
    public static final BitSet FOLLOW_CHANNELS_in_channelsSpec532;
    public static final BitSet FOLLOW_channelSpec_in_channelsSpec534;
    public static final BitSet FOLLOW_ID_in_channelSpec557;
    public static final BitSet FOLLOW_AT_in_action585;
    public static final BitSet FOLLOW_ID_in_action589;
    public static final BitSet FOLLOW_ID_in_action594;
    public static final BitSet FOLLOW_ACTION_in_action596;
    public static final BitSet FOLLOW_RULES_in_rules624;
    public static final BitSet FOLLOW_rule_in_rules629;
    public static final BitSet FOLLOW_lexerRule_in_rules631;
    public static final BitSet FOLLOW_MODE_in_mode662;
    public static final BitSet FOLLOW_ID_in_mode664;
    public static final BitSet FOLLOW_lexerRule_in_mode668;
    public static final BitSet FOLLOW_RULE_in_lexerRule694;
    public static final BitSet FOLLOW_TOKEN_REF_in_lexerRule696;
    public static final BitSet FOLLOW_RULEMODIFIERS_in_lexerRule708;
    public static final BitSet FOLLOW_FRAGMENT_in_lexerRule712;
    public static final BitSet FOLLOW_lexerRuleBlock_in_lexerRule737;
    public static final BitSet FOLLOW_RULE_in_rule782;
    public static final BitSet FOLLOW_RULE_REF_in_rule784;
    public static final BitSet FOLLOW_RULEMODIFIERS_in_rule793;
    public static final BitSet FOLLOW_ruleModifier_in_rule798;
    public static final BitSet FOLLOW_ARG_ACTION_in_rule809;
    public static final BitSet FOLLOW_ruleReturns_in_rule822;
    public static final BitSet FOLLOW_throwsSpec_in_rule835;
    public static final BitSet FOLLOW_locals_in_rule848;
    public static final BitSet FOLLOW_optionsSpec_in_rule863;
    public static final BitSet FOLLOW_ruleAction_in_rule877;
    public static final BitSet FOLLOW_ruleBlock_in_rule908;
    public static final BitSet FOLLOW_exceptionGroup_in_rule910;
    public static final BitSet FOLLOW_exceptionHandler_in_exceptionGroup957;
    public static final BitSet FOLLOW_finallyClause_in_exceptionGroup960;
    public static final BitSet FOLLOW_CATCH_in_exceptionHandler986;
    public static final BitSet FOLLOW_ARG_ACTION_in_exceptionHandler988;
    public static final BitSet FOLLOW_ACTION_in_exceptionHandler990;
    public static final BitSet FOLLOW_FINALLY_in_finallyClause1015;
    public static final BitSet FOLLOW_ACTION_in_finallyClause1017;
    public static final BitSet FOLLOW_LOCALS_in_locals1045;
    public static final BitSet FOLLOW_ARG_ACTION_in_locals1047;
    public static final BitSet FOLLOW_RETURNS_in_ruleReturns1070;
    public static final BitSet FOLLOW_ARG_ACTION_in_ruleReturns1072;
    public static final BitSet FOLLOW_THROWS_in_throwsSpec1098;
    public static final BitSet FOLLOW_ID_in_throwsSpec1100;
    public static final BitSet FOLLOW_AT_in_ruleAction1127;
    public static final BitSet FOLLOW_ID_in_ruleAction1129;
    public static final BitSet FOLLOW_ACTION_in_ruleAction1131;
    public static final BitSet FOLLOW_BLOCK_in_lexerRuleBlock1209;
    public static final BitSet FOLLOW_lexerOuterAlternative_in_lexerRuleBlock1228;
    public static final BitSet FOLLOW_BLOCK_in_ruleBlock1273;
    public static final BitSet FOLLOW_outerAlternative_in_ruleBlock1292;
    public static final BitSet FOLLOW_lexerAlternative_in_lexerOuterAlternative1332;
    public static final BitSet FOLLOW_alternative_in_outerAlternative1354;
    public static final BitSet FOLLOW_LEXER_ALT_ACTION_in_lexerAlternative1376;
    public static final BitSet FOLLOW_lexerElements_in_lexerAlternative1378;
    public static final BitSet FOLLOW_lexerCommand_in_lexerAlternative1380;
    public static final BitSet FOLLOW_lexerElements_in_lexerAlternative1392;
    public static final BitSet FOLLOW_ALT_in_lexerElements1420;
    public static final BitSet FOLLOW_lexerElement_in_lexerElements1422;
    public static final BitSet FOLLOW_labeledLexerElement_in_lexerElement1448;
    public static final BitSet FOLLOW_lexerAtom_in_lexerElement1453;
    public static final BitSet FOLLOW_lexerSubrule_in_lexerElement1458;
    public static final BitSet FOLLOW_ACTION_in_lexerElement1465;
    public static final BitSet FOLLOW_SEMPRED_in_lexerElement1479;
    public static final BitSet FOLLOW_ACTION_in_lexerElement1494;
    public static final BitSet FOLLOW_elementOptions_in_lexerElement1496;
    public static final BitSet FOLLOW_SEMPRED_in_lexerElement1507;
    public static final BitSet FOLLOW_elementOptions_in_lexerElement1509;
    public static final BitSet FOLLOW_EPSILON_in_lexerElement1517;
    public static final BitSet FOLLOW_set_in_labeledLexerElement1544;
    public static final BitSet FOLLOW_ID_in_labeledLexerElement1550;
    public static final BitSet FOLLOW_lexerAtom_in_labeledLexerElement1553;
    public static final BitSet FOLLOW_block_in_labeledLexerElement1555;
    public static final BitSet FOLLOW_BLOCK_in_lexerBlock1580;
    public static final BitSet FOLLOW_optionsSpec_in_lexerBlock1582;
    public static final BitSet FOLLOW_lexerAlternative_in_lexerBlock1585;
    public static final BitSet FOLLOW_terminal_in_lexerAtom1616;
    public static final BitSet FOLLOW_NOT_in_lexerAtom1627;
    public static final BitSet FOLLOW_blockSet_in_lexerAtom1629;
    public static final BitSet FOLLOW_blockSet_in_lexerAtom1640;
    public static final BitSet FOLLOW_WILDCARD_in_lexerAtom1651;
    public static final BitSet FOLLOW_elementOptions_in_lexerAtom1653;
    public static final BitSet FOLLOW_WILDCARD_in_lexerAtom1664;
    public static final BitSet FOLLOW_LEXER_CHAR_SET_in_lexerAtom1672;
    public static final BitSet FOLLOW_range_in_lexerAtom1682;
    public static final BitSet FOLLOW_ruleref_in_lexerAtom1692;
    public static final BitSet FOLLOW_ACTION_in_actionElement1716;
    public static final BitSet FOLLOW_ACTION_in_actionElement1724;
    public static final BitSet FOLLOW_elementOptions_in_actionElement1726;
    public static final BitSet FOLLOW_SEMPRED_in_actionElement1734;
    public static final BitSet FOLLOW_SEMPRED_in_actionElement1742;
    public static final BitSet FOLLOW_elementOptions_in_actionElement1744;
    public static final BitSet FOLLOW_ALT_in_alternative1767;
    public static final BitSet FOLLOW_elementOptions_in_alternative1769;
    public static final BitSet FOLLOW_element_in_alternative1772;
    public static final BitSet FOLLOW_ALT_in_alternative1780;
    public static final BitSet FOLLOW_elementOptions_in_alternative1782;
    public static final BitSet FOLLOW_EPSILON_in_alternative1785;
    public static final BitSet FOLLOW_LEXER_ACTION_CALL_in_lexerCommand1811;
    public static final BitSet FOLLOW_ID_in_lexerCommand1813;
    public static final BitSet FOLLOW_lexerCommandExpr_in_lexerCommand1815;
    public static final BitSet FOLLOW_ID_in_lexerCommand1831;
    public static final BitSet FOLLOW_labeledElement_in_element1888;
    public static final BitSet FOLLOW_atom_in_element1893;
    public static final BitSet FOLLOW_subrule_in_element1898;
    public static final BitSet FOLLOW_ACTION_in_element1905;
    public static final BitSet FOLLOW_SEMPRED_in_element1919;
    public static final BitSet FOLLOW_ACTION_in_element1934;
    public static final BitSet FOLLOW_elementOptions_in_element1936;
    public static final BitSet FOLLOW_SEMPRED_in_element1947;
    public static final BitSet FOLLOW_elementOptions_in_element1949;
    public static final BitSet FOLLOW_range_in_element1957;
    public static final BitSet FOLLOW_NOT_in_element1963;
    public static final BitSet FOLLOW_blockSet_in_element1965;
    public static final BitSet FOLLOW_NOT_in_element1972;
    public static final BitSet FOLLOW_block_in_element1974;
    public static final BitSet FOLLOW_atom_in_astOperand1996;
    public static final BitSet FOLLOW_NOT_in_astOperand2002;
    public static final BitSet FOLLOW_blockSet_in_astOperand2004;
    public static final BitSet FOLLOW_NOT_in_astOperand2011;
    public static final BitSet FOLLOW_block_in_astOperand2013;
    public static final BitSet FOLLOW_set_in_labeledElement2036;
    public static final BitSet FOLLOW_ID_in_labeledElement2042;
    public static final BitSet FOLLOW_element_in_labeledElement2044;
    public static final BitSet FOLLOW_blockSuffix_in_subrule2069;
    public static final BitSet FOLLOW_block_in_subrule2071;
    public static final BitSet FOLLOW_block_in_subrule2078;
    public static final BitSet FOLLOW_blockSuffix_in_lexerSubrule2103;
    public static final BitSet FOLLOW_lexerBlock_in_lexerSubrule2105;
    public static final BitSet FOLLOW_lexerBlock_in_lexerSubrule2112;
    public static final BitSet FOLLOW_ebnfSuffix_in_blockSuffix2139;
    public static final BitSet FOLLOW_DOT_in_atom2200;
    public static final BitSet FOLLOW_ID_in_atom2202;
    public static final BitSet FOLLOW_terminal_in_atom2204;
    public static final BitSet FOLLOW_DOT_in_atom2211;
    public static final BitSet FOLLOW_ID_in_atom2213;
    public static final BitSet FOLLOW_ruleref_in_atom2215;
    public static final BitSet FOLLOW_WILDCARD_in_atom2225;
    public static final BitSet FOLLOW_elementOptions_in_atom2227;
    public static final BitSet FOLLOW_WILDCARD_in_atom2238;
    public static final BitSet FOLLOW_terminal_in_atom2254;
    public static final BitSet FOLLOW_blockSet_in_atom2262;
    public static final BitSet FOLLOW_ruleref_in_atom2272;
    public static final BitSet FOLLOW_SET_in_blockSet2297;
    public static final BitSet FOLLOW_setElement_in_blockSet2299;
    public static final BitSet FOLLOW_STRING_LITERAL_in_setElement2323;
    public static final BitSet FOLLOW_elementOptions_in_setElement2325;
    public static final BitSet FOLLOW_TOKEN_REF_in_setElement2337;
    public static final BitSet FOLLOW_elementOptions_in_setElement2339;
    public static final BitSet FOLLOW_STRING_LITERAL_in_setElement2349;
    public static final BitSet FOLLOW_TOKEN_REF_in_setElement2374;
    public static final BitSet FOLLOW_RANGE_in_setElement2403;
    public static final BitSet FOLLOW_STRING_LITERAL_in_setElement2407;
    public static final BitSet FOLLOW_STRING_LITERAL_in_setElement2411;
    public static final BitSet FOLLOW_LEXER_CHAR_SET_in_setElement2434;
    public static final BitSet FOLLOW_BLOCK_in_block2459;
    public static final BitSet FOLLOW_optionsSpec_in_block2461;
    public static final BitSet FOLLOW_ruleAction_in_block2464;
    public static final BitSet FOLLOW_ACTION_in_block2467;
    public static final BitSet FOLLOW_alternative_in_block2470;
    public static final BitSet FOLLOW_RULE_REF_in_ruleref2500;
    public static final BitSet FOLLOW_ARG_ACTION_in_ruleref2504;
    public static final BitSet FOLLOW_elementOptions_in_ruleref2507;
    public static final BitSet FOLLOW_RANGE_in_range2544;
    public static final BitSet FOLLOW_STRING_LITERAL_in_range2546;
    public static final BitSet FOLLOW_STRING_LITERAL_in_range2548;
    public static final BitSet FOLLOW_STRING_LITERAL_in_terminal2578;
    public static final BitSet FOLLOW_elementOptions_in_terminal2580;
    public static final BitSet FOLLOW_STRING_LITERAL_in_terminal2603;
    public static final BitSet FOLLOW_TOKEN_REF_in_terminal2617;
    public static final BitSet FOLLOW_elementOptions_in_terminal2619;
    public static final BitSet FOLLOW_TOKEN_REF_in_terminal2630;
    public static final BitSet FOLLOW_ELEMENT_OPTIONS_in_elementOptions2667;
    public static final BitSet FOLLOW_elementOption_in_elementOptions2669;
    public static final BitSet FOLLOW_ID_in_elementOption2700;
    public static final BitSet FOLLOW_ASSIGN_in_elementOption2720;
    public static final BitSet FOLLOW_ID_in_elementOption2724;
    public static final BitSet FOLLOW_ID_in_elementOption2728;
    public static final BitSet FOLLOW_ASSIGN_in_elementOption2744;
    public static final BitSet FOLLOW_ID_in_elementOption2746;
    public static final BitSet FOLLOW_STRING_LITERAL_in_elementOption2750;
    public static final BitSet FOLLOW_ASSIGN_in_elementOption2764;
    public static final BitSet FOLLOW_ID_in_elementOption2766;
    public static final BitSet FOLLOW_ACTION_in_elementOption2770;
    public static final BitSet FOLLOW_ASSIGN_in_elementOption2786;
    public static final BitSet FOLLOW_ID_in_elementOption2788;
    public static final BitSet FOLLOW_INT_in_elementOption2792;

    public TreeParser[] getDelegates() {
        return new TreeParser[0];
    }

    public GrammarTreeVisitor(TreeNodeStream input) {
        this(input, new RecognizerSharedState());
    }

    public GrammarTreeVisitor(TreeNodeStream input, RecognizerSharedState state) {
        super(input, state);
    }

    @Override
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override
    public String getGrammarFileName() {
        return "org\\antlr\\v4\\parse\\GrammarTreeVisitor.g";
    }

    public GrammarTreeVisitor() {
        this((TreeNodeStream)null);
    }

    public ErrorManager getErrorManager() {
        return null;
    }

    public void visitGrammar(GrammarAST t) {
        this.visit(t, "grammarSpec");
    }

    public void visit(GrammarAST t, String ruleName) {
        CommonTreeNodeStream nodes = new CommonTreeNodeStream(new GrammarASTAdaptor(), t);
        this.setTreeNodeStream(nodes);
        try {
            Method m = this.getClass().getMethod(ruleName, new Class[0]);
            m.invoke(this, new Object[0]);
        }
        catch (Throwable e) {
            ErrorManager errMgr = this.getErrorManager();
            if (e instanceof InvocationTargetException) {
                e = e.getCause();
            }
            if (errMgr == null) {
                System.err.println("can't find rule " + ruleName + " or tree structure error: " + t.toStringTree());
                e.printStackTrace(System.err);
            }
            errMgr.toolError(ErrorType.INTERNAL_ERROR, e, new Object[0]);
        }
    }

    public void discoverGrammar(GrammarRootAST root, GrammarAST ID) {
    }

    public void finishPrequels(GrammarAST firstPrequel) {
    }

    public void finishGrammar(GrammarRootAST root, GrammarAST ID) {
    }

    public void grammarOption(GrammarAST ID, GrammarAST valueAST) {
    }

    public void ruleOption(GrammarAST ID, GrammarAST valueAST) {
    }

    public void blockOption(GrammarAST ID, GrammarAST valueAST) {
    }

    public void defineToken(GrammarAST ID) {
    }

    public void defineChannel(GrammarAST ID) {
    }

    public void globalNamedAction(GrammarAST scope, GrammarAST ID, ActionAST action) {
    }

    public void importGrammar(GrammarAST label, GrammarAST ID) {
    }

    public void modeDef(GrammarAST m, GrammarAST ID) {
    }

    public void discoverRules(GrammarAST rules) {
    }

    public void finishRules(GrammarAST rule) {
    }

    public void discoverRule(RuleAST rule, GrammarAST ID, List<GrammarAST> modifiers, ActionAST arg, ActionAST returns, GrammarAST thrws, GrammarAST options, ActionAST locals, List<GrammarAST> actions, GrammarAST block) {
    }

    public void finishRule(RuleAST rule, GrammarAST ID, GrammarAST block) {
    }

    public void discoverLexerRule(RuleAST rule, GrammarAST ID, List<GrammarAST> modifiers, GrammarAST block) {
    }

    public void finishLexerRule(RuleAST rule, GrammarAST ID, GrammarAST block) {
    }

    public void ruleCatch(GrammarAST arg, ActionAST action) {
    }

    public void finallyAction(ActionAST action) {
    }

    public void discoverOuterAlt(AltAST alt) {
    }

    public void finishOuterAlt(AltAST alt) {
    }

    public void discoverAlt(AltAST alt) {
    }

    public void finishAlt(AltAST alt) {
    }

    public void ruleRef(GrammarAST ref, ActionAST arg) {
    }

    public void tokenRef(TerminalAST ref) {
    }

    public void elementOption(GrammarASTWithOptions t, GrammarAST ID, GrammarAST valueAST) {
    }

    public void stringRef(TerminalAST ref) {
    }

    public void wildcardRef(GrammarAST ref) {
    }

    public void actionInAlt(ActionAST action) {
    }

    public void sempredInAlt(PredAST pred) {
    }

    public void label(GrammarAST op, GrammarAST ID, GrammarAST element) {
    }

    public void lexerCallCommand(int outerAltNumber, GrammarAST ID, GrammarAST arg) {
    }

    public void lexerCommand(int outerAltNumber, GrammarAST ID) {
    }

    protected void enterGrammarSpec(GrammarAST tree) {
    }

    protected void exitGrammarSpec(GrammarAST tree) {
    }

    protected void enterPrequelConstructs(GrammarAST tree) {
    }

    protected void exitPrequelConstructs(GrammarAST tree) {
    }

    protected void enterPrequelConstruct(GrammarAST tree) {
    }

    protected void exitPrequelConstruct(GrammarAST tree) {
    }

    protected void enterOptionsSpec(GrammarAST tree) {
    }

    protected void exitOptionsSpec(GrammarAST tree) {
    }

    protected void enterOption(GrammarAST tree) {
    }

    protected void exitOption(GrammarAST tree) {
    }

    protected void enterOptionValue(GrammarAST tree) {
    }

    protected void exitOptionValue(GrammarAST tree) {
    }

    protected void enterDelegateGrammars(GrammarAST tree) {
    }

    protected void exitDelegateGrammars(GrammarAST tree) {
    }

    protected void enterDelegateGrammar(GrammarAST tree) {
    }

    protected void exitDelegateGrammar(GrammarAST tree) {
    }

    protected void enterTokensSpec(GrammarAST tree) {
    }

    protected void exitTokensSpec(GrammarAST tree) {
    }

    protected void enterTokenSpec(GrammarAST tree) {
    }

    protected void exitTokenSpec(GrammarAST tree) {
    }

    protected void enterChannelsSpec(GrammarAST tree) {
    }

    protected void exitChannelsSpec(GrammarAST tree) {
    }

    protected void enterChannelSpec(GrammarAST tree) {
    }

    protected void exitChannelSpec(GrammarAST tree) {
    }

    protected void enterAction(GrammarAST tree) {
    }

    protected void exitAction(GrammarAST tree) {
    }

    protected void enterRules(GrammarAST tree) {
    }

    protected void exitRules(GrammarAST tree) {
    }

    protected void enterMode(GrammarAST tree) {
    }

    protected void exitMode(GrammarAST tree) {
    }

    protected void enterLexerRule(GrammarAST tree) {
    }

    protected void exitLexerRule(GrammarAST tree) {
    }

    protected void enterRule(GrammarAST tree) {
    }

    protected void exitRule(GrammarAST tree) {
    }

    protected void enterExceptionGroup(GrammarAST tree) {
    }

    protected void exitExceptionGroup(GrammarAST tree) {
    }

    protected void enterExceptionHandler(GrammarAST tree) {
    }

    protected void exitExceptionHandler(GrammarAST tree) {
    }

    protected void enterFinallyClause(GrammarAST tree) {
    }

    protected void exitFinallyClause(GrammarAST tree) {
    }

    protected void enterLocals(GrammarAST tree) {
    }

    protected void exitLocals(GrammarAST tree) {
    }

    protected void enterRuleReturns(GrammarAST tree) {
    }

    protected void exitRuleReturns(GrammarAST tree) {
    }

    protected void enterThrowsSpec(GrammarAST tree) {
    }

    protected void exitThrowsSpec(GrammarAST tree) {
    }

    protected void enterRuleAction(GrammarAST tree) {
    }

    protected void exitRuleAction(GrammarAST tree) {
    }

    protected void enterRuleModifier(GrammarAST tree) {
    }

    protected void exitRuleModifier(GrammarAST tree) {
    }

    protected void enterLexerRuleBlock(GrammarAST tree) {
    }

    protected void exitLexerRuleBlock(GrammarAST tree) {
    }

    protected void enterRuleBlock(GrammarAST tree) {
    }

    protected void exitRuleBlock(GrammarAST tree) {
    }

    protected void enterLexerOuterAlternative(AltAST tree) {
    }

    protected void exitLexerOuterAlternative(AltAST tree) {
    }

    protected void enterOuterAlternative(AltAST tree) {
    }

    protected void exitOuterAlternative(AltAST tree) {
    }

    protected void enterLexerAlternative(GrammarAST tree) {
    }

    protected void exitLexerAlternative(GrammarAST tree) {
    }

    protected void enterLexerElements(GrammarAST tree) {
    }

    protected void exitLexerElements(GrammarAST tree) {
    }

    protected void enterLexerElement(GrammarAST tree) {
    }

    protected void exitLexerElement(GrammarAST tree) {
    }

    protected void enterLabeledLexerElement(GrammarAST tree) {
    }

    protected void exitLabeledLexerElement(GrammarAST tree) {
    }

    protected void enterLexerBlock(GrammarAST tree) {
    }

    protected void exitLexerBlock(GrammarAST tree) {
    }

    protected void enterLexerAtom(GrammarAST tree) {
    }

    protected void exitLexerAtom(GrammarAST tree) {
    }

    protected void enterActionElement(GrammarAST tree) {
    }

    protected void exitActionElement(GrammarAST tree) {
    }

    protected void enterAlternative(AltAST tree) {
    }

    protected void exitAlternative(AltAST tree) {
    }

    protected void enterLexerCommand(GrammarAST tree) {
    }

    protected void exitLexerCommand(GrammarAST tree) {
    }

    protected void enterLexerCommandExpr(GrammarAST tree) {
    }

    protected void exitLexerCommandExpr(GrammarAST tree) {
    }

    protected void enterElement(GrammarAST tree) {
    }

    protected void exitElement(GrammarAST tree) {
    }

    protected void enterAstOperand(GrammarAST tree) {
    }

    protected void exitAstOperand(GrammarAST tree) {
    }

    protected void enterLabeledElement(GrammarAST tree) {
    }

    protected void exitLabeledElement(GrammarAST tree) {
    }

    protected void enterSubrule(GrammarAST tree) {
    }

    protected void exitSubrule(GrammarAST tree) {
    }

    protected void enterLexerSubrule(GrammarAST tree) {
    }

    protected void exitLexerSubrule(GrammarAST tree) {
    }

    protected void enterBlockSuffix(GrammarAST tree) {
    }

    protected void exitBlockSuffix(GrammarAST tree) {
    }

    protected void enterEbnfSuffix(GrammarAST tree) {
    }

    protected void exitEbnfSuffix(GrammarAST tree) {
    }

    protected void enterAtom(GrammarAST tree) {
    }

    protected void exitAtom(GrammarAST tree) {
    }

    protected void enterBlockSet(GrammarAST tree) {
    }

    protected void exitBlockSet(GrammarAST tree) {
    }

    protected void enterSetElement(GrammarAST tree) {
    }

    protected void exitSetElement(GrammarAST tree) {
    }

    protected void enterBlock(GrammarAST tree) {
    }

    protected void exitBlock(GrammarAST tree) {
    }

    protected void enterRuleref(GrammarAST tree) {
    }

    protected void exitRuleref(GrammarAST tree) {
    }

    protected void enterRange(GrammarAST tree) {
    }

    protected void exitRange(GrammarAST tree) {
    }

    protected void enterTerminal(GrammarAST tree) {
    }

    protected void exitTerminal(GrammarAST tree) {
    }

    protected void enterElementOptions(GrammarAST tree) {
    }

    protected void exitElementOptions(GrammarAST tree) {
    }

    protected void enterElementOption(GrammarAST tree) {
    }

    protected void exitElementOption(GrammarAST tree) {
    }

    @Override
    public void traceIn(String ruleName, int ruleIndex) {
        System.err.println("enter " + ruleName + ": " + this.input.LT(1));
    }

    @Override
    public void traceOut(String ruleName, int ruleIndex) {
        System.err.println("exit " + ruleName + ": " + this.input.LT(1));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final grammarSpec_return grammarSpec() throws RecognitionException {
        grammarSpec_return retval = new grammarSpec_return();
        retval.start = this.input.LT(1);
        GrammarAST ID1 = null;
        GrammarAST GRAMMAR2 = null;
        prequelConstructs_return prequelConstructs3 = null;
        this.enterGrammarSpec((GrammarAST)retval.start);
        try {
            GRAMMAR2 = (GrammarAST)this.match(this.input, 25, FOLLOW_GRAMMAR_in_grammarSpec85);
            this.match(this.input, 2, null);
            ID1 = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_grammarSpec87);
            this.grammarName = ID1 != null ? ID1.getText() : null;
            this.discoverGrammar((GrammarRootAST)GRAMMAR2, ID1);
            this.pushFollow(FOLLOW_prequelConstructs_in_grammarSpec106);
            prequelConstructs3 = this.prequelConstructs();
            --this.state._fsp;
            this.finishPrequels(prequelConstructs3 != null ? prequelConstructs3.firstOne : null);
            this.pushFollow(FOLLOW_rules_in_grammarSpec123);
            this.rules();
            --this.state._fsp;
            block7: while (true) {
                int alt1 = 2;
                int LA1_0 = this.input.LA(1);
                if (LA1_0 == 36) {
                    alt1 = 1;
                }
                switch (alt1) {
                    case 1: {
                        this.pushFollow(FOLLOW_mode_in_grammarSpec125);
                        this.mode();
                        --this.state._fsp;
                        continue block7;
                    }
                }
                break;
            }
            this.finishGrammar((GrammarRootAST)GRAMMAR2, ID1);
            this.match(this.input, 3, null);
            this.exitGrammarSpec((GrammarAST)retval.start);
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
    public final prequelConstructs_return prequelConstructs() throws RecognitionException {
        prequelConstructs_return retval = new prequelConstructs_return();
        retval.start = this.input.LT(1);
        this.enterPrequelConstructs((GrammarAST)retval.start);
        try {
            int alt3 = 2;
            int LA3_0 = this.input.LA(1);
            if (LA3_0 == 11 || LA3_0 == 13 || LA3_0 == 29 || LA3_0 == 42 || LA3_0 == 65) {
                alt3 = 1;
            } else if (LA3_0 == 97) {
                alt3 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 3, 0, this.input);
                throw nvae;
            }
            block2 : switch (alt3) {
                case 1: {
                    retval.firstOne = (GrammarAST)retval.start;
                    int cnt2 = 0;
                    while (true) {
                        int alt2 = 2;
                        int LA2_0 = this.input.LA(1);
                        if (LA2_0 == 11 || LA2_0 == 13 || LA2_0 == 29 || LA2_0 == 42 || LA2_0 == 65) {
                            alt2 = 1;
                        }
                        switch (alt2) {
                            case 1: {
                                this.pushFollow(FOLLOW_prequelConstruct_in_prequelConstructs167);
                                this.prequelConstruct();
                                --this.state._fsp;
                                break;
                            }
                            default: {
                                if (cnt2 >= 1) break block2;
                                EarlyExitException eee = new EarlyExitException(2, this.input);
                                throw eee;
                            }
                        }
                        ++cnt2;
                    }
                }
            }
            this.exitPrequelConstructs((GrammarAST)retval.start);
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
    public final prequelConstruct_return prequelConstruct() throws RecognitionException {
        prequelConstruct_return retval = new prequelConstruct_return();
        retval.start = this.input.LT(1);
        this.enterPrequelConstructs((GrammarAST)retval.start);
        try {
            int alt4 = 5;
            switch (this.input.LA(1)) {
                case 42: {
                    alt4 = 1;
                    break;
                }
                case 29: {
                    alt4 = 2;
                    break;
                }
                case 65: {
                    alt4 = 3;
                    break;
                }
                case 13: {
                    alt4 = 4;
                    break;
                }
                case 11: {
                    alt4 = 5;
                    break;
                }
                default: {
                    NoViableAltException nvae = new NoViableAltException("", 4, 0, this.input);
                    throw nvae;
                }
            }
            switch (alt4) {
                case 1: {
                    this.pushFollow(FOLLOW_optionsSpec_in_prequelConstruct194);
                    this.optionsSpec();
                    --this.state._fsp;
                    break;
                }
                case 2: {
                    this.pushFollow(FOLLOW_delegateGrammars_in_prequelConstruct204);
                    this.delegateGrammars();
                    --this.state._fsp;
                    break;
                }
                case 3: {
                    this.pushFollow(FOLLOW_tokensSpec_in_prequelConstruct214);
                    this.tokensSpec();
                    --this.state._fsp;
                    break;
                }
                case 4: {
                    this.pushFollow(FOLLOW_channelsSpec_in_prequelConstruct224);
                    this.channelsSpec();
                    --this.state._fsp;
                    break;
                }
                case 5: {
                    this.pushFollow(FOLLOW_action_in_prequelConstruct234);
                    this.action();
                    --this.state._fsp;
                }
            }
            this.exitPrequelConstructs((GrammarAST)retval.start);
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
    public final optionsSpec_return optionsSpec() throws RecognitionException {
        optionsSpec_return retval = new optionsSpec_return();
        retval.start = this.input.LT(1);
        this.enterOptionsSpec((GrammarAST)retval.start);
        try {
            this.match(this.input, 42, FOLLOW_OPTIONS_in_optionsSpec259);
            if (this.input.LA(1) == 2) {
                this.match(this.input, 2, null);
                block7: while (true) {
                    int alt5 = 2;
                    int LA5_0 = this.input.LA(1);
                    if (LA5_0 == 10) {
                        alt5 = 1;
                    }
                    switch (alt5) {
                        case 1: {
                            this.pushFollow(FOLLOW_option_in_optionsSpec261);
                            this.option();
                            --this.state._fsp;
                            continue block7;
                        }
                    }
                    break;
                }
                this.match(this.input, 3, null);
            }
            this.exitOptionsSpec((GrammarAST)retval.start);
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
    public final option_return option() throws RecognitionException {
        option_return retval = new option_return();
        retval.start = this.input.LT(1);
        GrammarAST a = null;
        GrammarAST ID4 = null;
        optionValue_return v = null;
        this.enterOption((GrammarAST)retval.start);
        boolean rule = this.inContext("RULE ...");
        boolean block = this.inContext("BLOCK ...");
        try {
            a = (GrammarAST)this.match(this.input, 10, FOLLOW_ASSIGN_in_option295);
            this.match(this.input, 2, null);
            ID4 = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_option297);
            this.pushFollow(FOLLOW_optionValue_in_option301);
            v = this.optionValue();
            --this.state._fsp;
            this.match(this.input, 3, null);
            if (block) {
                this.blockOption(ID4, v != null ? (GrammarAST)v.start : null);
            } else if (rule) {
                this.ruleOption(ID4, v != null ? (GrammarAST)v.start : null);
            } else {
                this.grammarOption(ID4, v != null ? (GrammarAST)v.start : null);
            }
            this.exitOption((GrammarAST)retval.start);
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
    public final optionValue_return optionValue() throws RecognitionException {
        optionValue_return retval = new optionValue_return();
        retval.start = this.input.LT(1);
        this.enterOptionValue((GrammarAST)retval.start);
        retval.v = ((GrammarAST)retval.start).token.getText();
        try {
            if (this.input.LA(1) != 28 && this.input.LA(1) != 30 && this.input.LA(1) != 62) {
                MismatchedSetException mse = new MismatchedSetException(null, this.input);
                throw mse;
            }
            this.input.consume();
            this.state.errorRecovery = false;
            this.exitOptionValue((GrammarAST)retval.start);
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
    public final delegateGrammars_return delegateGrammars() throws RecognitionException {
        delegateGrammars_return retval = new delegateGrammars_return();
        retval.start = this.input.LT(1);
        this.enterDelegateGrammars((GrammarAST)retval.start);
        try {
            this.match(this.input, 29, FOLLOW_IMPORT_in_delegateGrammars389);
            this.match(this.input, 2, null);
            int cnt6 = 0;
            block7: while (true) {
                int alt6 = 2;
                int LA6_0 = this.input.LA(1);
                if (LA6_0 == 10 || LA6_0 == 28) {
                    alt6 = 1;
                }
                switch (alt6) {
                    case 1: {
                        this.pushFollow(FOLLOW_delegateGrammar_in_delegateGrammars391);
                        this.delegateGrammar();
                        --this.state._fsp;
                        break;
                    }
                    default: {
                        if (cnt6 >= 1) break block7;
                        EarlyExitException eee = new EarlyExitException(6, this.input);
                        throw eee;
                    }
                }
                ++cnt6;
            }
            this.match(this.input, 3, null);
            this.exitDelegateGrammars((GrammarAST)retval.start);
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
    public final delegateGrammar_return delegateGrammar() throws RecognitionException {
        delegateGrammar_return retval = new delegateGrammar_return();
        retval.start = this.input.LT(1);
        GrammarAST label = null;
        GrammarAST id = null;
        this.enterDelegateGrammar((GrammarAST)retval.start);
        try {
            int alt7 = 2;
            int LA7_0 = this.input.LA(1);
            if (LA7_0 == 10) {
                alt7 = 1;
            } else if (LA7_0 == 28) {
                alt7 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 7, 0, this.input);
                throw nvae;
            }
            switch (alt7) {
                case 1: {
                    this.match(this.input, 10, FOLLOW_ASSIGN_in_delegateGrammar420);
                    this.match(this.input, 2, null);
                    label = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_delegateGrammar424);
                    id = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_delegateGrammar428);
                    this.match(this.input, 3, null);
                    this.importGrammar(label, id);
                    break;
                }
                case 2: {
                    id = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_delegateGrammar443);
                    this.importGrammar(null, id);
                }
            }
            this.exitDelegateGrammar((GrammarAST)retval.start);
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
    public final tokensSpec_return tokensSpec() throws RecognitionException {
        tokensSpec_return retval = new tokensSpec_return();
        retval.start = this.input.LT(1);
        this.enterTokensSpec((GrammarAST)retval.start);
        try {
            this.match(this.input, 65, FOLLOW_TOKENS_SPEC_in_tokensSpec477);
            this.match(this.input, 2, null);
            int cnt8 = 0;
            block7: while (true) {
                int alt8 = 2;
                int LA8_0 = this.input.LA(1);
                if (LA8_0 == 28) {
                    alt8 = 1;
                }
                switch (alt8) {
                    case 1: {
                        this.pushFollow(FOLLOW_tokenSpec_in_tokensSpec479);
                        this.tokenSpec();
                        --this.state._fsp;
                        break;
                    }
                    default: {
                        if (cnt8 >= 1) break block7;
                        EarlyExitException eee = new EarlyExitException(8, this.input);
                        throw eee;
                    }
                }
                ++cnt8;
            }
            this.match(this.input, 3, null);
            this.exitTokensSpec((GrammarAST)retval.start);
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
    public final tokenSpec_return tokenSpec() throws RecognitionException {
        tokenSpec_return retval = new tokenSpec_return();
        retval.start = this.input.LT(1);
        GrammarAST ID5 = null;
        this.enterTokenSpec((GrammarAST)retval.start);
        try {
            ID5 = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_tokenSpec502);
            this.defineToken(ID5);
            this.exitTokenSpec((GrammarAST)retval.start);
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
    public final channelsSpec_return channelsSpec() throws RecognitionException {
        channelsSpec_return retval = new channelsSpec_return();
        retval.start = this.input.LT(1);
        this.enterChannelsSpec((GrammarAST)retval.start);
        try {
            this.match(this.input, 13, FOLLOW_CHANNELS_in_channelsSpec532);
            if (this.input.LA(1) == 2) {
                this.match(this.input, 2, null);
                block7: while (true) {
                    int alt9 = 2;
                    int LA9_0 = this.input.LA(1);
                    if (LA9_0 == 28) {
                        alt9 = 1;
                    }
                    switch (alt9) {
                        case 1: {
                            this.pushFollow(FOLLOW_channelSpec_in_channelsSpec534);
                            this.channelSpec();
                            --this.state._fsp;
                            continue block7;
                        }
                    }
                    break;
                }
                this.match(this.input, 3, null);
            }
            this.exitChannelsSpec((GrammarAST)retval.start);
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
    public final channelSpec_return channelSpec() throws RecognitionException {
        channelSpec_return retval = new channelSpec_return();
        retval.start = this.input.LT(1);
        GrammarAST ID6 = null;
        this.enterChannelSpec((GrammarAST)retval.start);
        try {
            ID6 = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_channelSpec557);
            this.defineChannel(ID6);
            this.exitChannelSpec((GrammarAST)retval.start);
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
    public final action_return action() throws RecognitionException {
        action_return retval = new action_return();
        retval.start = this.input.LT(1);
        GrammarAST sc = null;
        GrammarAST name = null;
        GrammarAST ACTION7 = null;
        this.enterAction((GrammarAST)retval.start);
        try {
            int LA10_1;
            this.match(this.input, 11, FOLLOW_AT_in_action585);
            this.match(this.input, 2, null);
            int alt10 = 2;
            int LA10_0 = this.input.LA(1);
            if (LA10_0 == 28 && (LA10_1 = this.input.LA(2)) == 28) {
                alt10 = 1;
            }
            switch (alt10) {
                case 1: {
                    sc = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_action589);
                }
            }
            name = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_action594);
            ACTION7 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_action596);
            this.match(this.input, 3, null);
            this.globalNamedAction(sc, name, (ActionAST)ACTION7);
            this.exitAction((GrammarAST)retval.start);
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
    public final rules_return rules() throws RecognitionException {
        rules_return retval = new rules_return();
        retval.start = this.input.LT(1);
        GrammarAST RULES8 = null;
        this.enterRules((GrammarAST)retval.start);
        try {
            RULES8 = (GrammarAST)this.match(this.input, 97, FOLLOW_RULES_in_rules624);
            this.discoverRules(RULES8);
            if (this.input.LA(1) == 2) {
                this.match(this.input, 2, null);
                block8: while (true) {
                    int LA11_2;
                    int alt11 = 3;
                    int LA11_0 = this.input.LA(1);
                    if (LA11_0 == 94 && (LA11_2 = this.input.LA(2)) == 2) {
                        int LA11_3 = this.input.LA(3);
                        if (LA11_3 == 57) {
                            alt11 = 1;
                        } else if (LA11_3 == 66) {
                            alt11 = 2;
                        }
                    }
                    switch (alt11) {
                        case 1: {
                            this.pushFollow(FOLLOW_rule_in_rules629);
                            this.rule();
                            --this.state._fsp;
                            continue block8;
                        }
                        case 2: {
                            this.pushFollow(FOLLOW_lexerRule_in_rules631);
                            this.lexerRule();
                            --this.state._fsp;
                            continue block8;
                        }
                    }
                    break;
                }
                this.finishRules(RULES8);
                this.match(this.input, 3, null);
            }
            this.exitRules((GrammarAST)retval.start);
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
    public final mode_return mode() throws RecognitionException {
        mode_return retval = new mode_return();
        retval.start = this.input.LT(1);
        GrammarAST ID9 = null;
        GrammarAST MODE10 = null;
        this.enterMode((GrammarAST)retval.start);
        try {
            MODE10 = (GrammarAST)this.match(this.input, 36, FOLLOW_MODE_in_mode662);
            this.match(this.input, 2, null);
            ID9 = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_mode664);
            this.currentModeName = ID9 != null ? ID9.getText() : null;
            this.modeDef(MODE10, ID9);
            block7: while (true) {
                int alt12 = 2;
                int LA12_0 = this.input.LA(1);
                if (LA12_0 == 94) {
                    alt12 = 1;
                }
                switch (alt12) {
                    case 1: {
                        this.pushFollow(FOLLOW_lexerRule_in_mode668);
                        this.lexerRule();
                        --this.state._fsp;
                        continue block7;
                    }
                }
                break;
            }
            this.match(this.input, 3, null);
            this.exitMode((GrammarAST)retval.start);
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
    public final lexerRule_return lexerRule() throws RecognitionException {
        lexerRule_return retval = new lexerRule_return();
        retval.start = this.input.LT(1);
        GrammarAST m = null;
        GrammarAST TOKEN_REF11 = null;
        GrammarAST RULE12 = null;
        lexerRuleBlock_return lexerRuleBlock13 = null;
        this.enterLexerRule((GrammarAST)retval.start);
        ArrayList<GrammarAST> mods = new ArrayList<GrammarAST>();
        this.currentOuterAltNumber = 0;
        try {
            RULE12 = (GrammarAST)this.match(this.input, 94, FOLLOW_RULE_in_lexerRule694);
            this.match(this.input, 2, null);
            TOKEN_REF11 = (GrammarAST)this.match(this.input, 66, FOLLOW_TOKEN_REF_in_lexerRule696);
            this.currentRuleName = TOKEN_REF11 != null ? TOKEN_REF11.getText() : null;
            this.currentRuleAST = RULE12;
            int alt13 = 2;
            int LA13_0 = this.input.LA(1);
            if (LA13_0 == 96) {
                alt13 = 1;
            }
            switch (alt13) {
                case 1: {
                    this.match(this.input, 96, FOLLOW_RULEMODIFIERS_in_lexerRule708);
                    this.match(this.input, 2, null);
                    m = (GrammarAST)this.match(this.input, 24, FOLLOW_FRAGMENT_in_lexerRule712);
                    mods.add(m);
                    this.match(this.input, 3, null);
                }
            }
            this.discoverLexerRule((RuleAST)RULE12, TOKEN_REF11, mods, (GrammarAST)this.input.LT(1));
            this.pushFollow(FOLLOW_lexerRuleBlock_in_lexerRule737);
            lexerRuleBlock13 = this.lexerRuleBlock();
            --this.state._fsp;
            this.finishLexerRule((RuleAST)RULE12, TOKEN_REF11, lexerRuleBlock13 != null ? (GrammarAST)lexerRuleBlock13.start : null);
            this.currentRuleName = null;
            this.currentRuleAST = null;
            this.match(this.input, 3, null);
            this.exitLexerRule((GrammarAST)retval.start);
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
    public final rule_return rule() throws RecognitionException {
        rule_return retval = new rule_return();
        retval.start = this.input.LT(1);
        GrammarAST RULE_REF14 = null;
        GrammarAST RULE15 = null;
        GrammarAST ARG_ACTION16 = null;
        ruleModifier_return m = null;
        ruleReturns_return ret = null;
        throwsSpec_return thr = null;
        locals_return loc = null;
        optionsSpec_return opts = null;
        ruleAction_return a = null;
        ruleBlock_return ruleBlock17 = null;
        this.enterRule((GrammarAST)retval.start);
        ArrayList<GrammarAST> mods = new ArrayList<GrammarAST>();
        ArrayList<GrammarAST> actions = new ArrayList<GrammarAST>();
        this.currentOuterAltNumber = 0;
        try {
            RULE15 = (GrammarAST)this.match(this.input, 94, FOLLOW_RULE_in_rule782);
            this.match(this.input, 2, null);
            RULE_REF14 = (GrammarAST)this.match(this.input, 57, FOLLOW_RULE_REF_in_rule784);
            this.currentRuleName = RULE_REF14 != null ? RULE_REF14.getText() : null;
            this.currentRuleAST = RULE15;
            int alt15 = 2;
            int LA15_0 = this.input.LA(1);
            if (LA15_0 == 96) {
                alt15 = 1;
            }
            switch (alt15) {
                case 1: {
                    this.match(this.input, 96, FOLLOW_RULEMODIFIERS_in_rule793);
                    this.match(this.input, 2, null);
                    int cnt14 = 0;
                    block26: while (true) {
                        int alt14 = 2;
                        int LA14_0 = this.input.LA(1);
                        if (LA14_0 == 24 || LA14_0 >= 48 && LA14_0 <= 50) {
                            alt14 = 1;
                        }
                        switch (alt14) {
                            case 1: {
                                this.pushFollow(FOLLOW_ruleModifier_in_rule798);
                                m = this.ruleModifier();
                                --this.state._fsp;
                                mods.add(m != null ? (GrammarAST)m.start : null);
                                break;
                            }
                            default: {
                                if (cnt14 >= 1) break block26;
                                EarlyExitException eee = new EarlyExitException(14, this.input);
                                throw eee;
                            }
                        }
                        ++cnt14;
                    }
                    this.match(this.input, 3, null);
                }
            }
            int alt16 = 2;
            int LA16_0 = this.input.LA(1);
            if (LA16_0 == 8) {
                alt16 = 1;
            }
            switch (alt16) {
                case 1: {
                    ARG_ACTION16 = (GrammarAST)this.match(this.input, 8, FOLLOW_ARG_ACTION_in_rule809);
                }
            }
            int alt17 = 2;
            int LA17_0 = this.input.LA(1);
            if (LA17_0 == 55) {
                alt17 = 1;
            }
            switch (alt17) {
                case 1: {
                    this.pushFollow(FOLLOW_ruleReturns_in_rule822);
                    ret = this.ruleReturns();
                    --this.state._fsp;
                }
            }
            int alt18 = 2;
            int LA18_0 = this.input.LA(1);
            if (LA18_0 == 64) {
                alt18 = 1;
            }
            switch (alt18) {
                case 1: {
                    this.pushFollow(FOLLOW_throwsSpec_in_rule835);
                    thr = this.throwsSpec();
                    --this.state._fsp;
                }
            }
            int alt19 = 2;
            int LA19_0 = this.input.LA(1);
            if (LA19_0 == 33) {
                alt19 = 1;
            }
            switch (alt19) {
                case 1: {
                    this.pushFollow(FOLLOW_locals_in_rule848);
                    loc = this.locals();
                    --this.state._fsp;
                }
            }
            block27: while (true) {
                int alt20 = 3;
                int LA20_0 = this.input.LA(1);
                if (LA20_0 == 42) {
                    alt20 = 1;
                } else if (LA20_0 == 11) {
                    alt20 = 2;
                }
                switch (alt20) {
                    case 1: {
                        this.pushFollow(FOLLOW_optionsSpec_in_rule863);
                        opts = this.optionsSpec();
                        --this.state._fsp;
                        continue block27;
                    }
                    case 2: {
                        this.pushFollow(FOLLOW_ruleAction_in_rule877);
                        a = this.ruleAction();
                        --this.state._fsp;
                        actions.add(a != null ? (GrammarAST)a.start : null);
                        continue block27;
                    }
                }
                break;
            }
            this.discoverRule((RuleAST)RULE15, RULE_REF14, mods, (ActionAST)ARG_ACTION16, (ret != null ? (GrammarAST)ret.start : null) != null ? (ActionAST)(ret != null ? (GrammarAST)ret.start : null).getChild(0) : null, thr != null ? (GrammarAST)thr.start : null, opts != null ? (GrammarAST)opts.start : null, (loc != null ? (GrammarAST)loc.start : null) != null ? (ActionAST)(loc != null ? (GrammarAST)loc.start : null).getChild(0) : null, actions, (GrammarAST)this.input.LT(1));
            this.pushFollow(FOLLOW_ruleBlock_in_rule908);
            ruleBlock17 = this.ruleBlock();
            --this.state._fsp;
            this.pushFollow(FOLLOW_exceptionGroup_in_rule910);
            this.exceptionGroup();
            --this.state._fsp;
            this.finishRule((RuleAST)RULE15, RULE_REF14, ruleBlock17 != null ? (GrammarAST)ruleBlock17.start : null);
            this.currentRuleName = null;
            this.currentRuleAST = null;
            this.match(this.input, 3, null);
            this.exitRule((GrammarAST)retval.start);
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
    public final exceptionGroup_return exceptionGroup() throws RecognitionException {
        exceptionGroup_return retval = new exceptionGroup_return();
        retval.start = this.input.LT(1);
        this.enterExceptionGroup((GrammarAST)retval.start);
        try {
            block10: while (true) {
                int alt21 = 2;
                int LA21_0 = this.input.LA(1);
                if (LA21_0 == 12) {
                    alt21 = 1;
                }
                switch (alt21) {
                    case 1: {
                        this.pushFollow(FOLLOW_exceptionHandler_in_exceptionGroup957);
                        this.exceptionHandler();
                        --this.state._fsp;
                        continue block10;
                    }
                }
                break;
            }
            int alt22 = 2;
            int LA22_0 = this.input.LA(1);
            if (LA22_0 == 23) {
                alt22 = 1;
            }
            switch (alt22) {
                case 1: {
                    this.pushFollow(FOLLOW_finallyClause_in_exceptionGroup960);
                    this.finallyClause();
                    --this.state._fsp;
                }
            }
            this.exitExceptionGroup((GrammarAST)retval.start);
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
    public final exceptionHandler_return exceptionHandler() throws RecognitionException {
        exceptionHandler_return retval = new exceptionHandler_return();
        retval.start = this.input.LT(1);
        GrammarAST ARG_ACTION18 = null;
        GrammarAST ACTION19 = null;
        this.enterExceptionHandler((GrammarAST)retval.start);
        try {
            this.match(this.input, 12, FOLLOW_CATCH_in_exceptionHandler986);
            this.match(this.input, 2, null);
            ARG_ACTION18 = (GrammarAST)this.match(this.input, 8, FOLLOW_ARG_ACTION_in_exceptionHandler988);
            ACTION19 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_exceptionHandler990);
            this.match(this.input, 3, null);
            this.ruleCatch(ARG_ACTION18, (ActionAST)ACTION19);
            this.exitExceptionHandler((GrammarAST)retval.start);
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
    public final finallyClause_return finallyClause() throws RecognitionException {
        finallyClause_return retval = new finallyClause_return();
        retval.start = this.input.LT(1);
        GrammarAST ACTION20 = null;
        this.enterFinallyClause((GrammarAST)retval.start);
        try {
            this.match(this.input, 23, FOLLOW_FINALLY_in_finallyClause1015);
            this.match(this.input, 2, null);
            ACTION20 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_finallyClause1017);
            this.match(this.input, 3, null);
            this.finallyAction((ActionAST)ACTION20);
            this.exitFinallyClause((GrammarAST)retval.start);
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
    public final locals_return locals() throws RecognitionException {
        locals_return retval = new locals_return();
        retval.start = this.input.LT(1);
        this.enterLocals((GrammarAST)retval.start);
        try {
            this.match(this.input, 33, FOLLOW_LOCALS_in_locals1045);
            this.match(this.input, 2, null);
            this.match(this.input, 8, FOLLOW_ARG_ACTION_in_locals1047);
            this.match(this.input, 3, null);
            this.exitLocals((GrammarAST)retval.start);
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
    public final ruleReturns_return ruleReturns() throws RecognitionException {
        ruleReturns_return retval = new ruleReturns_return();
        retval.start = this.input.LT(1);
        this.enterRuleReturns((GrammarAST)retval.start);
        try {
            this.match(this.input, 55, FOLLOW_RETURNS_in_ruleReturns1070);
            this.match(this.input, 2, null);
            this.match(this.input, 8, FOLLOW_ARG_ACTION_in_ruleReturns1072);
            this.match(this.input, 3, null);
            this.exitRuleReturns((GrammarAST)retval.start);
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
    public final throwsSpec_return throwsSpec() throws RecognitionException {
        throwsSpec_return retval = new throwsSpec_return();
        retval.start = this.input.LT(1);
        this.enterThrowsSpec((GrammarAST)retval.start);
        try {
            this.match(this.input, 64, FOLLOW_THROWS_in_throwsSpec1098);
            this.match(this.input, 2, null);
            int cnt23 = 0;
            block7: while (true) {
                int alt23 = 2;
                int LA23_0 = this.input.LA(1);
                if (LA23_0 == 28) {
                    alt23 = 1;
                }
                switch (alt23) {
                    case 1: {
                        this.match(this.input, 28, FOLLOW_ID_in_throwsSpec1100);
                        break;
                    }
                    default: {
                        if (cnt23 >= 1) break block7;
                        EarlyExitException eee = new EarlyExitException(23, this.input);
                        throw eee;
                    }
                }
                ++cnt23;
            }
            this.match(this.input, 3, null);
            this.exitThrowsSpec((GrammarAST)retval.start);
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
    public final ruleAction_return ruleAction() throws RecognitionException {
        ruleAction_return retval = new ruleAction_return();
        retval.start = this.input.LT(1);
        this.enterRuleAction((GrammarAST)retval.start);
        try {
            this.match(this.input, 11, FOLLOW_AT_in_ruleAction1127);
            this.match(this.input, 2, null);
            this.match(this.input, 28, FOLLOW_ID_in_ruleAction1129);
            this.match(this.input, 4, FOLLOW_ACTION_in_ruleAction1131);
            this.match(this.input, 3, null);
            this.exitRuleAction((GrammarAST)retval.start);
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
    public final ruleModifier_return ruleModifier() throws RecognitionException {
        ruleModifier_return retval = new ruleModifier_return();
        retval.start = this.input.LT(1);
        this.enterRuleModifier((GrammarAST)retval.start);
        try {
            if (this.input.LA(1) != 24 && (this.input.LA(1) < 48 || this.input.LA(1) > 50)) {
                MismatchedSetException mse = new MismatchedSetException(null, this.input);
                throw mse;
            }
            this.input.consume();
            this.state.errorRecovery = false;
            this.exitRuleModifier((GrammarAST)retval.start);
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
    public final lexerRuleBlock_return lexerRuleBlock() throws RecognitionException {
        lexerRuleBlock_return retval = new lexerRuleBlock_return();
        retval.start = this.input.LT(1);
        this.enterLexerRuleBlock((GrammarAST)retval.start);
        try {
            this.match(this.input, 78, FOLLOW_BLOCK_in_lexerRuleBlock1209);
            this.match(this.input, 2, null);
            int cnt24 = 0;
            block7: while (true) {
                int alt24 = 2;
                int LA24_0 = this.input.LA(1);
                if (LA24_0 == 74 || LA24_0 == 87) {
                    alt24 = 1;
                }
                switch (alt24) {
                    case 1: {
                        this.currentOuterAltRoot = (GrammarAST)this.input.LT(1);
                        ++this.currentOuterAltNumber;
                        this.pushFollow(FOLLOW_lexerOuterAlternative_in_lexerRuleBlock1228);
                        this.lexerOuterAlternative();
                        --this.state._fsp;
                        break;
                    }
                    default: {
                        if (cnt24 >= 1) break block7;
                        EarlyExitException eee = new EarlyExitException(24, this.input);
                        throw eee;
                    }
                }
                ++cnt24;
            }
            this.match(this.input, 3, null);
            this.exitLexerRuleBlock((GrammarAST)retval.start);
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
    public final ruleBlock_return ruleBlock() throws RecognitionException {
        ruleBlock_return retval = new ruleBlock_return();
        retval.start = this.input.LT(1);
        this.enterRuleBlock((GrammarAST)retval.start);
        try {
            this.match(this.input, 78, FOLLOW_BLOCK_in_ruleBlock1273);
            this.match(this.input, 2, null);
            int cnt25 = 0;
            block7: while (true) {
                int alt25 = 2;
                int LA25_0 = this.input.LA(1);
                if (LA25_0 == 74) {
                    alt25 = 1;
                }
                switch (alt25) {
                    case 1: {
                        this.currentOuterAltRoot = (GrammarAST)this.input.LT(1);
                        ++this.currentOuterAltNumber;
                        this.pushFollow(FOLLOW_outerAlternative_in_ruleBlock1292);
                        this.outerAlternative();
                        --this.state._fsp;
                        break;
                    }
                    default: {
                        if (cnt25 >= 1) break block7;
                        EarlyExitException eee = new EarlyExitException(25, this.input);
                        throw eee;
                    }
                }
                ++cnt25;
            }
            this.match(this.input, 3, null);
            this.exitRuleBlock((GrammarAST)retval.start);
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
    public final lexerOuterAlternative_return lexerOuterAlternative() throws RecognitionException {
        lexerOuterAlternative_return retval = new lexerOuterAlternative_return();
        retval.start = this.input.LT(1);
        this.enterLexerOuterAlternative((AltAST)((GrammarAST)retval.start));
        this.discoverOuterAlt((AltAST)((GrammarAST)retval.start));
        try {
            this.pushFollow(FOLLOW_lexerAlternative_in_lexerOuterAlternative1332);
            this.lexerAlternative();
            --this.state._fsp;
            this.finishOuterAlt((AltAST)((GrammarAST)retval.start));
            this.exitLexerOuterAlternative((AltAST)((GrammarAST)retval.start));
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
    public final outerAlternative_return outerAlternative() throws RecognitionException {
        outerAlternative_return retval = new outerAlternative_return();
        retval.start = this.input.LT(1);
        this.enterOuterAlternative((AltAST)((GrammarAST)retval.start));
        this.discoverOuterAlt((AltAST)((GrammarAST)retval.start));
        try {
            this.pushFollow(FOLLOW_alternative_in_outerAlternative1354);
            this.alternative();
            --this.state._fsp;
            this.finishOuterAlt((AltAST)((GrammarAST)retval.start));
            this.exitOuterAlternative((AltAST)((GrammarAST)retval.start));
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
    public final lexerAlternative_return lexerAlternative() throws RecognitionException {
        lexerAlternative_return retval = new lexerAlternative_return();
        retval.start = this.input.LT(1);
        this.enterLexerAlternative((GrammarAST)retval.start);
        try {
            int alt27 = 2;
            int LA27_0 = this.input.LA(1);
            if (LA27_0 == 87) {
                alt27 = 1;
            } else if (LA27_0 == 74) {
                alt27 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 27, 0, this.input);
                throw nvae;
            }
            switch (alt27) {
                case 1: {
                    this.match(this.input, 87, FOLLOW_LEXER_ALT_ACTION_in_lexerAlternative1376);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_lexerElements_in_lexerAlternative1378);
                    this.lexerElements();
                    --this.state._fsp;
                    int cnt26 = 0;
                    block11: while (true) {
                        int alt26 = 2;
                        int LA26_0 = this.input.LA(1);
                        if (LA26_0 == 28 || LA26_0 == 86) {
                            alt26 = 1;
                        }
                        switch (alt26) {
                            case 1: {
                                this.pushFollow(FOLLOW_lexerCommand_in_lexerAlternative1380);
                                this.lexerCommand();
                                --this.state._fsp;
                                break;
                            }
                            default: {
                                if (cnt26 >= 1) break block11;
                                EarlyExitException eee = new EarlyExitException(26, this.input);
                                throw eee;
                            }
                        }
                        ++cnt26;
                    }
                    this.match(this.input, 3, null);
                    break;
                }
                case 2: {
                    this.pushFollow(FOLLOW_lexerElements_in_lexerAlternative1392);
                    this.lexerElements();
                    --this.state._fsp;
                }
            }
            this.exitLexerAlternative((GrammarAST)retval.start);
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
    public final lexerElements_return lexerElements() throws RecognitionException {
        lexerElements_return retval = new lexerElements_return();
        retval.start = this.input.LT(1);
        this.enterLexerElements((GrammarAST)retval.start);
        try {
            this.match(this.input, 74, FOLLOW_ALT_in_lexerElements1420);
            this.match(this.input, 2, null);
            int cnt28 = 0;
            block7: while (true) {
                int alt28 = 2;
                int LA28_0 = this.input.LA(1);
                if (LA28_0 == 4 || LA28_0 == 10 || LA28_0 == 32 || LA28_0 == 39 || LA28_0 == 46 || LA28_0 == 52 || LA28_0 == 57 || LA28_0 == 59 || LA28_0 == 62 || LA28_0 == 66 || LA28_0 == 78 || LA28_0 == 80 || LA28_0 == 83 || LA28_0 >= 89 && LA28_0 <= 90 || LA28_0 == 98 || LA28_0 == 100) {
                    alt28 = 1;
                }
                switch (alt28) {
                    case 1: {
                        this.pushFollow(FOLLOW_lexerElement_in_lexerElements1422);
                        this.lexerElement();
                        --this.state._fsp;
                        break;
                    }
                    default: {
                        if (cnt28 >= 1) break block7;
                        EarlyExitException eee = new EarlyExitException(28, this.input);
                        throw eee;
                    }
                }
                ++cnt28;
            }
            this.match(this.input, 3, null);
            this.exitLexerElements((GrammarAST)retval.start);
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
    public final lexerElement_return lexerElement() throws RecognitionException {
        lexerElement_return retval = new lexerElement_return();
        retval.start = this.input.LT(1);
        GrammarAST ACTION21 = null;
        GrammarAST SEMPRED22 = null;
        GrammarAST ACTION23 = null;
        GrammarAST SEMPRED24 = null;
        this.enterLexerElement((GrammarAST)retval.start);
        try {
            int alt29 = 8;
            switch (this.input.LA(1)) {
                case 10: 
                case 46: {
                    alt29 = 1;
                    break;
                }
                case 32: 
                case 39: 
                case 52: 
                case 57: 
                case 62: 
                case 66: 
                case 98: 
                case 100: {
                    alt29 = 2;
                    break;
                }
                case 78: 
                case 80: 
                case 89: 
                case 90: {
                    alt29 = 3;
                    break;
                }
                case 4: {
                    int LA29_4 = this.input.LA(2);
                    if (LA29_4 == 2) {
                        alt29 = 6;
                        break;
                    }
                    if (LA29_4 >= 3 && LA29_4 <= 4 || LA29_4 == 10 || LA29_4 == 32 || LA29_4 == 39 || LA29_4 == 46 || LA29_4 == 52 || LA29_4 == 57 || LA29_4 == 59 || LA29_4 == 62 || LA29_4 == 66 || LA29_4 == 78 || LA29_4 == 80 || LA29_4 == 83 || LA29_4 >= 89 && LA29_4 <= 90 || LA29_4 == 98 || LA29_4 == 100) {
                        alt29 = 4;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 29, 4, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 59: {
                    int LA29_5 = this.input.LA(2);
                    if (LA29_5 == 2) {
                        alt29 = 7;
                        break;
                    }
                    if (LA29_5 >= 3 && LA29_5 <= 4 || LA29_5 == 10 || LA29_5 == 32 || LA29_5 == 39 || LA29_5 == 46 || LA29_5 == 52 || LA29_5 == 57 || LA29_5 == 59 || LA29_5 == 62 || LA29_5 == 66 || LA29_5 == 78 || LA29_5 == 80 || LA29_5 == 83 || LA29_5 >= 89 && LA29_5 <= 90 || LA29_5 == 98 || LA29_5 == 100) {
                        alt29 = 5;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 29, 5, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 83: {
                    alt29 = 8;
                    break;
                }
                default: {
                    NoViableAltException nvae = new NoViableAltException("", 29, 0, this.input);
                    throw nvae;
                }
            }
            switch (alt29) {
                case 1: {
                    this.pushFollow(FOLLOW_labeledLexerElement_in_lexerElement1448);
                    this.labeledLexerElement();
                    --this.state._fsp;
                    break;
                }
                case 2: {
                    this.pushFollow(FOLLOW_lexerAtom_in_lexerElement1453);
                    this.lexerAtom();
                    --this.state._fsp;
                    break;
                }
                case 3: {
                    this.pushFollow(FOLLOW_lexerSubrule_in_lexerElement1458);
                    this.lexerSubrule();
                    --this.state._fsp;
                    break;
                }
                case 4: {
                    ACTION21 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_lexerElement1465);
                    this.actionInAlt((ActionAST)ACTION21);
                    break;
                }
                case 5: {
                    SEMPRED22 = (GrammarAST)this.match(this.input, 59, FOLLOW_SEMPRED_in_lexerElement1479);
                    this.sempredInAlt((PredAST)SEMPRED22);
                    break;
                }
                case 6: {
                    ACTION23 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_lexerElement1494);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_elementOptions_in_lexerElement1496);
                    this.elementOptions();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    this.actionInAlt((ActionAST)ACTION23);
                    break;
                }
                case 7: {
                    SEMPRED24 = (GrammarAST)this.match(this.input, 59, FOLLOW_SEMPRED_in_lexerElement1507);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_elementOptions_in_lexerElement1509);
                    this.elementOptions();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    this.sempredInAlt((PredAST)SEMPRED24);
                    break;
                }
                case 8: {
                    this.match(this.input, 83, FOLLOW_EPSILON_in_lexerElement1517);
                }
            }
            this.exitLexerElement((GrammarAST)retval.start);
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
    public final labeledLexerElement_return labeledLexerElement() throws RecognitionException {
        labeledLexerElement_return retval = new labeledLexerElement_return();
        retval.start = this.input.LT(1);
        this.enterLabeledLexerElement((GrammarAST)retval.start);
        try {
            if (this.input.LA(1) != 10 && this.input.LA(1) != 46) {
                MismatchedSetException mse = new MismatchedSetException(null, this.input);
                throw mse;
            }
            this.input.consume();
            this.state.errorRecovery = false;
            this.match(this.input, 2, null);
            this.match(this.input, 28, FOLLOW_ID_in_labeledLexerElement1550);
            int alt30 = 2;
            int LA30_0 = this.input.LA(1);
            if (LA30_0 == 32 || LA30_0 == 39 || LA30_0 == 52 || LA30_0 == 57 || LA30_0 == 62 || LA30_0 == 66 || LA30_0 == 98 || LA30_0 == 100) {
                alt30 = 1;
            } else if (LA30_0 == 78) {
                alt30 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 30, 0, this.input);
                throw nvae;
            }
            switch (alt30) {
                case 1: {
                    this.pushFollow(FOLLOW_lexerAtom_in_labeledLexerElement1553);
                    this.lexerAtom();
                    --this.state._fsp;
                    break;
                }
                case 2: {
                    this.pushFollow(FOLLOW_block_in_labeledLexerElement1555);
                    this.block();
                    --this.state._fsp;
                }
            }
            this.match(this.input, 3, null);
            this.exitLabeledLexerElement((GrammarAST)retval.start);
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
    public final lexerBlock_return lexerBlock() throws RecognitionException {
        lexerBlock_return retval = new lexerBlock_return();
        retval.start = this.input.LT(1);
        this.enterLexerBlock((GrammarAST)retval.start);
        try {
            this.match(this.input, 78, FOLLOW_BLOCK_in_lexerBlock1580);
            this.match(this.input, 2, null);
            int alt31 = 2;
            int LA31_0 = this.input.LA(1);
            if (LA31_0 == 42) {
                alt31 = 1;
            }
            switch (alt31) {
                case 1: {
                    this.pushFollow(FOLLOW_optionsSpec_in_lexerBlock1582);
                    this.optionsSpec();
                    --this.state._fsp;
                }
            }
            int cnt32 = 0;
            block10: while (true) {
                int alt32 = 2;
                int LA32_0 = this.input.LA(1);
                if (LA32_0 == 74 || LA32_0 == 87) {
                    alt32 = 1;
                }
                switch (alt32) {
                    case 1: {
                        this.pushFollow(FOLLOW_lexerAlternative_in_lexerBlock1585);
                        this.lexerAlternative();
                        --this.state._fsp;
                        break;
                    }
                    default: {
                        if (cnt32 >= 1) break block10;
                        EarlyExitException eee = new EarlyExitException(32, this.input);
                        throw eee;
                    }
                }
                ++cnt32;
            }
            this.match(this.input, 3, null);
            this.exitLexerBlock((GrammarAST)retval.start);
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
    public final lexerAtom_return lexerAtom() throws RecognitionException {
        lexerAtom_return retval = new lexerAtom_return();
        retval.start = this.input.LT(1);
        this.enterLexerAtom((GrammarAST)retval.start);
        try {
            int alt33 = 8;
            switch (this.input.LA(1)) {
                case 62: 
                case 66: {
                    alt33 = 1;
                    break;
                }
                case 39: {
                    alt33 = 2;
                    break;
                }
                case 98: {
                    alt33 = 3;
                    break;
                }
                case 100: {
                    int LA33_4 = this.input.LA(2);
                    if (LA33_4 == 2) {
                        alt33 = 4;
                        break;
                    }
                    if (LA33_4 >= 3 && LA33_4 <= 4 || LA33_4 == 10 || LA33_4 == 32 || LA33_4 == 39 || LA33_4 == 46 || LA33_4 == 52 || LA33_4 == 57 || LA33_4 == 59 || LA33_4 == 62 || LA33_4 == 66 || LA33_4 == 78 || LA33_4 == 80 || LA33_4 == 83 || LA33_4 >= 89 && LA33_4 <= 90 || LA33_4 == 98 || LA33_4 == 100) {
                        alt33 = 5;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 33, 4, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 32: {
                    alt33 = 6;
                    break;
                }
                case 52: {
                    alt33 = 7;
                    break;
                }
                case 57: {
                    alt33 = 8;
                    break;
                }
                default: {
                    NoViableAltException nvae = new NoViableAltException("", 33, 0, this.input);
                    throw nvae;
                }
            }
            switch (alt33) {
                case 1: {
                    this.pushFollow(FOLLOW_terminal_in_lexerAtom1616);
                    this.terminal();
                    --this.state._fsp;
                    break;
                }
                case 2: {
                    this.match(this.input, 39, FOLLOW_NOT_in_lexerAtom1627);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_blockSet_in_lexerAtom1629);
                    this.blockSet();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    break;
                }
                case 3: {
                    this.pushFollow(FOLLOW_blockSet_in_lexerAtom1640);
                    this.blockSet();
                    --this.state._fsp;
                    break;
                }
                case 4: {
                    this.match(this.input, 100, FOLLOW_WILDCARD_in_lexerAtom1651);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_elementOptions_in_lexerAtom1653);
                    this.elementOptions();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    break;
                }
                case 5: {
                    this.match(this.input, 100, FOLLOW_WILDCARD_in_lexerAtom1664);
                    break;
                }
                case 6: {
                    this.match(this.input, 32, FOLLOW_LEXER_CHAR_SET_in_lexerAtom1672);
                    break;
                }
                case 7: {
                    this.pushFollow(FOLLOW_range_in_lexerAtom1682);
                    this.range();
                    --this.state._fsp;
                    break;
                }
                case 8: {
                    this.pushFollow(FOLLOW_ruleref_in_lexerAtom1692);
                    this.ruleref();
                    --this.state._fsp;
                }
            }
            this.exitLexerAtom((GrammarAST)retval.start);
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
    public final actionElement_return actionElement() throws RecognitionException {
        actionElement_return retval = new actionElement_return();
        retval.start = this.input.LT(1);
        this.enterActionElement((GrammarAST)retval.start);
        try {
            int alt34;
            block23: {
                int LA34_0;
                block24: {
                    alt34 = 4;
                    LA34_0 = this.input.LA(1);
                    if (LA34_0 != 4) break block24;
                    int LA34_1 = this.input.LA(2);
                    if (LA34_1 == 2) {
                        alt34 = 2;
                        break block23;
                    } else if (LA34_1 == -1) {
                        alt34 = 1;
                        break block23;
                    } else {
                        int nvaeMark = this.input.mark();
                        try {
                            this.input.consume();
                            NoViableAltException nvae = new NoViableAltException("", 34, 1, this.input);
                            throw nvae;
                        }
                        catch (Throwable throwable) {
                            this.input.rewind(nvaeMark);
                            throw throwable;
                        }
                    }
                }
                if (LA34_0 != 59) {
                    NoViableAltException nvae = new NoViableAltException("", 34, 0, this.input);
                    throw nvae;
                }
                int LA34_2 = this.input.LA(2);
                if (LA34_2 == 2) {
                    alt34 = 4;
                } else if (LA34_2 == -1) {
                    alt34 = 3;
                } else {
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 34, 2, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
            }
            switch (alt34) {
                case 1: {
                    this.match(this.input, 4, FOLLOW_ACTION_in_actionElement1716);
                    break;
                }
                case 2: {
                    this.match(this.input, 4, FOLLOW_ACTION_in_actionElement1724);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_elementOptions_in_actionElement1726);
                    this.elementOptions();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    break;
                }
                case 3: {
                    this.match(this.input, 59, FOLLOW_SEMPRED_in_actionElement1734);
                    break;
                }
                case 4: {
                    this.match(this.input, 59, FOLLOW_SEMPRED_in_actionElement1742);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_elementOptions_in_actionElement1744);
                    this.elementOptions();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    break;
                }
            }
            this.exitActionElement((GrammarAST)retval.start);
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
    public final alternative_return alternative() throws RecognitionException {
        alternative_return retval = new alternative_return();
        retval.start = this.input.LT(1);
        this.enterAlternative((AltAST)((GrammarAST)retval.start));
        this.discoverAlt((AltAST)((GrammarAST)retval.start));
        try {
            int alt38 = 2;
            alt38 = this.dfa38.predict(this.input);
            switch (alt38) {
                case 1: {
                    this.match(this.input, 74, FOLLOW_ALT_in_alternative1767);
                    this.match(this.input, 2, null);
                    int alt35 = 2;
                    int LA35_0 = this.input.LA(1);
                    if (LA35_0 == 82) {
                        alt35 = 1;
                    }
                    switch (alt35) {
                        case 1: {
                            this.pushFollow(FOLLOW_elementOptions_in_alternative1769);
                            this.elementOptions();
                            --this.state._fsp;
                        }
                    }
                    int cnt36 = 0;
                    block17: while (true) {
                        int alt36 = 2;
                        int LA36_0 = this.input.LA(1);
                        if (LA36_0 == 4 || LA36_0 == 10 || LA36_0 == 20 || LA36_0 == 39 || LA36_0 == 46 || LA36_0 == 52 || LA36_0 == 57 || LA36_0 == 59 || LA36_0 == 62 || LA36_0 == 66 || LA36_0 == 78 || LA36_0 == 80 || LA36_0 >= 89 && LA36_0 <= 90 || LA36_0 == 98 || LA36_0 == 100) {
                            alt36 = 1;
                        }
                        switch (alt36) {
                            case 1: {
                                this.pushFollow(FOLLOW_element_in_alternative1772);
                                this.element();
                                --this.state._fsp;
                                break;
                            }
                            default: {
                                if (cnt36 >= 1) break block17;
                                EarlyExitException eee = new EarlyExitException(36, this.input);
                                throw eee;
                            }
                        }
                        ++cnt36;
                    }
                    this.match(this.input, 3, null);
                    break;
                }
                case 2: {
                    this.match(this.input, 74, FOLLOW_ALT_in_alternative1780);
                    this.match(this.input, 2, null);
                    int alt37 = 2;
                    int LA37_0 = this.input.LA(1);
                    if (LA37_0 == 82) {
                        alt37 = 1;
                    }
                    switch (alt37) {
                        case 1: {
                            this.pushFollow(FOLLOW_elementOptions_in_alternative1782);
                            this.elementOptions();
                            --this.state._fsp;
                        }
                    }
                    this.match(this.input, 83, FOLLOW_EPSILON_in_alternative1785);
                    this.match(this.input, 3, null);
                }
            }
            this.finishAlt((AltAST)((GrammarAST)retval.start));
            this.exitAlternative((AltAST)((GrammarAST)retval.start));
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
    public final lexerCommand_return lexerCommand() throws RecognitionException {
        lexerCommand_return retval = new lexerCommand_return();
        retval.start = this.input.LT(1);
        GrammarAST ID25 = null;
        GrammarAST ID27 = null;
        lexerCommandExpr_return lexerCommandExpr26 = null;
        this.enterLexerCommand((GrammarAST)retval.start);
        try {
            int alt39 = 2;
            int LA39_0 = this.input.LA(1);
            if (LA39_0 == 86) {
                alt39 = 1;
            } else if (LA39_0 == 28) {
                alt39 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 39, 0, this.input);
                throw nvae;
            }
            switch (alt39) {
                case 1: {
                    this.match(this.input, 86, FOLLOW_LEXER_ACTION_CALL_in_lexerCommand1811);
                    this.match(this.input, 2, null);
                    ID25 = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_lexerCommand1813);
                    this.pushFollow(FOLLOW_lexerCommandExpr_in_lexerCommand1815);
                    lexerCommandExpr26 = this.lexerCommandExpr();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    this.lexerCallCommand(this.currentOuterAltNumber, ID25, lexerCommandExpr26 != null ? (GrammarAST)lexerCommandExpr26.start : null);
                    break;
                }
                case 2: {
                    ID27 = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_lexerCommand1831);
                    this.lexerCommand(this.currentOuterAltNumber, ID27);
                }
            }
            this.exitLexerCommand((GrammarAST)retval.start);
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
    public final lexerCommandExpr_return lexerCommandExpr() throws RecognitionException {
        lexerCommandExpr_return retval = new lexerCommandExpr_return();
        retval.start = this.input.LT(1);
        this.enterLexerCommandExpr((GrammarAST)retval.start);
        try {
            if (this.input.LA(1) != 28 && this.input.LA(1) != 30) {
                MismatchedSetException mse = new MismatchedSetException(null, this.input);
                throw mse;
            }
            this.input.consume();
            this.state.errorRecovery = false;
            this.exitLexerCommandExpr((GrammarAST)retval.start);
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
        GrammarAST ACTION28 = null;
        GrammarAST SEMPRED29 = null;
        GrammarAST ACTION30 = null;
        GrammarAST SEMPRED31 = null;
        this.enterElement((GrammarAST)retval.start);
        try {
            int alt40 = 10;
            switch (this.input.LA(1)) {
                case 10: 
                case 46: {
                    alt40 = 1;
                    break;
                }
                case 20: 
                case 57: 
                case 62: 
                case 66: 
                case 98: 
                case 100: {
                    alt40 = 2;
                    break;
                }
                case 78: 
                case 80: 
                case 89: 
                case 90: {
                    alt40 = 3;
                    break;
                }
                case 4: {
                    int LA40_4 = this.input.LA(2);
                    if (LA40_4 == 2) {
                        alt40 = 6;
                        break;
                    }
                    if (LA40_4 >= 3 && LA40_4 <= 4 || LA40_4 == 10 || LA40_4 == 20 || LA40_4 == 39 || LA40_4 == 46 || LA40_4 == 52 || LA40_4 == 57 || LA40_4 == 59 || LA40_4 == 62 || LA40_4 == 66 || LA40_4 == 78 || LA40_4 == 80 || LA40_4 >= 89 && LA40_4 <= 90 || LA40_4 == 98 || LA40_4 == 100) {
                        alt40 = 4;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 40, 4, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 59: {
                    int LA40_5 = this.input.LA(2);
                    if (LA40_5 == 2) {
                        alt40 = 7;
                        break;
                    }
                    if (LA40_5 >= 3 && LA40_5 <= 4 || LA40_5 == 10 || LA40_5 == 20 || LA40_5 == 39 || LA40_5 == 46 || LA40_5 == 52 || LA40_5 == 57 || LA40_5 == 59 || LA40_5 == 62 || LA40_5 == 66 || LA40_5 == 78 || LA40_5 == 80 || LA40_5 >= 89 && LA40_5 <= 90 || LA40_5 == 98 || LA40_5 == 100) {
                        alt40 = 5;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 40, 5, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 52: {
                    alt40 = 8;
                    break;
                }
                case 39: {
                    int LA40_7 = this.input.LA(2);
                    if (LA40_7 == 2) {
                        int LA40_12 = this.input.LA(3);
                        if (LA40_12 == 98) {
                            alt40 = 9;
                            break;
                        }
                        if (LA40_12 == 78) {
                            alt40 = 10;
                            break;
                        }
                        int nvaeMark = this.input.mark();
                        try {
                            for (int nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                                this.input.consume();
                            }
                            NoViableAltException nvae = new NoViableAltException("", 40, 12, this.input);
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
                        NoViableAltException nvae = new NoViableAltException("", 40, 7, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                default: {
                    NoViableAltException nvae = new NoViableAltException("", 40, 0, this.input);
                    throw nvae;
                }
            }
            switch (alt40) {
                case 1: {
                    this.pushFollow(FOLLOW_labeledElement_in_element1888);
                    this.labeledElement();
                    --this.state._fsp;
                    break;
                }
                case 2: {
                    this.pushFollow(FOLLOW_atom_in_element1893);
                    this.atom();
                    --this.state._fsp;
                    break;
                }
                case 3: {
                    this.pushFollow(FOLLOW_subrule_in_element1898);
                    this.subrule();
                    --this.state._fsp;
                    break;
                }
                case 4: {
                    ACTION28 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_element1905);
                    this.actionInAlt((ActionAST)ACTION28);
                    break;
                }
                case 5: {
                    SEMPRED29 = (GrammarAST)this.match(this.input, 59, FOLLOW_SEMPRED_in_element1919);
                    this.sempredInAlt((PredAST)SEMPRED29);
                    break;
                }
                case 6: {
                    ACTION30 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_element1934);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_elementOptions_in_element1936);
                    this.elementOptions();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    this.actionInAlt((ActionAST)ACTION30);
                    break;
                }
                case 7: {
                    SEMPRED31 = (GrammarAST)this.match(this.input, 59, FOLLOW_SEMPRED_in_element1947);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_elementOptions_in_element1949);
                    this.elementOptions();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    this.sempredInAlt((PredAST)SEMPRED31);
                    break;
                }
                case 8: {
                    this.pushFollow(FOLLOW_range_in_element1957);
                    this.range();
                    --this.state._fsp;
                    break;
                }
                case 9: {
                    this.match(this.input, 39, FOLLOW_NOT_in_element1963);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_blockSet_in_element1965);
                    this.blockSet();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    break;
                }
                case 10: {
                    this.match(this.input, 39, FOLLOW_NOT_in_element1972);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_block_in_element1974);
                    this.block();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                }
            }
            this.exitElement((GrammarAST)retval.start);
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
    public final astOperand_return astOperand() throws RecognitionException {
        astOperand_return retval = new astOperand_return();
        retval.start = this.input.LT(1);
        this.enterAstOperand((GrammarAST)retval.start);
        try {
            int alt41;
            block20: {
                block22: {
                    int LA41_0;
                    block21: {
                        alt41 = 3;
                        LA41_0 = this.input.LA(1);
                        if (LA41_0 != 20 && LA41_0 != 57 && LA41_0 != 62 && LA41_0 != 66 && LA41_0 != 98 && LA41_0 != 100) break block21;
                        alt41 = 1;
                        break block20;
                    }
                    if (LA41_0 != 39) {
                        NoViableAltException nvae = new NoViableAltException("", 41, 0, this.input);
                        throw nvae;
                    }
                    int LA41_2 = this.input.LA(2);
                    if (LA41_2 != 2) break block22;
                    int LA41_3 = this.input.LA(3);
                    if (LA41_3 == 98) {
                        alt41 = 2;
                        break block20;
                    } else if (LA41_3 == 78) {
                        alt41 = 3;
                        break block20;
                    } else {
                        int nvaeMark = this.input.mark();
                        try {
                            int nvaeConsume = 0;
                            while (true) {
                                if (nvaeConsume >= 2) {
                                    NoViableAltException nvae = new NoViableAltException("", 41, 3, this.input);
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
                    NoViableAltException nvae = new NoViableAltException("", 41, 2, this.input);
                    throw nvae;
                }
                catch (Throwable throwable) {
                    this.input.rewind(nvaeMark);
                    throw throwable;
                }
            }
            switch (alt41) {
                case 1: {
                    this.pushFollow(FOLLOW_atom_in_astOperand1996);
                    this.atom();
                    --this.state._fsp;
                    break;
                }
                case 2: {
                    this.match(this.input, 39, FOLLOW_NOT_in_astOperand2002);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_blockSet_in_astOperand2004);
                    this.blockSet();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    break;
                }
                case 3: {
                    this.match(this.input, 39, FOLLOW_NOT_in_astOperand2011);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_block_in_astOperand2013);
                    this.block();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    break;
                }
            }
            this.exitAstOperand((GrammarAST)retval.start);
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
    public final labeledElement_return labeledElement() throws RecognitionException {
        labeledElement_return retval = new labeledElement_return();
        retval.start = this.input.LT(1);
        GrammarAST ID32 = null;
        element_return element33 = null;
        this.enterLabeledElement((GrammarAST)retval.start);
        try {
            if (this.input.LA(1) != 10 && this.input.LA(1) != 46) {
                MismatchedSetException mse = new MismatchedSetException(null, this.input);
                throw mse;
            }
            this.input.consume();
            this.state.errorRecovery = false;
            this.match(this.input, 2, null);
            ID32 = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_labeledElement2042);
            this.pushFollow(FOLLOW_element_in_labeledElement2044);
            element33 = this.element();
            --this.state._fsp;
            this.match(this.input, 3, null);
            this.label((GrammarAST)retval.start, ID32, element33 != null ? (GrammarAST)element33.start : null);
            this.exitLabeledElement((GrammarAST)retval.start);
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
    public final subrule_return subrule() throws RecognitionException {
        subrule_return retval = new subrule_return();
        retval.start = this.input.LT(1);
        this.enterSubrule((GrammarAST)retval.start);
        try {
            int alt42 = 2;
            int LA42_0 = this.input.LA(1);
            if (LA42_0 == 80 || LA42_0 >= 89 && LA42_0 <= 90) {
                alt42 = 1;
            } else if (LA42_0 == 78) {
                alt42 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 42, 0, this.input);
                throw nvae;
            }
            switch (alt42) {
                case 1: {
                    this.pushFollow(FOLLOW_blockSuffix_in_subrule2069);
                    this.blockSuffix();
                    --this.state._fsp;
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_block_in_subrule2071);
                    this.block();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    break;
                }
                case 2: {
                    this.pushFollow(FOLLOW_block_in_subrule2078);
                    this.block();
                    --this.state._fsp;
                }
            }
            this.exitSubrule((GrammarAST)retval.start);
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
    public final lexerSubrule_return lexerSubrule() throws RecognitionException {
        lexerSubrule_return retval = new lexerSubrule_return();
        retval.start = this.input.LT(1);
        this.enterLexerSubrule((GrammarAST)retval.start);
        try {
            int alt43 = 2;
            int LA43_0 = this.input.LA(1);
            if (LA43_0 == 80 || LA43_0 >= 89 && LA43_0 <= 90) {
                alt43 = 1;
            } else if (LA43_0 == 78) {
                alt43 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 43, 0, this.input);
                throw nvae;
            }
            switch (alt43) {
                case 1: {
                    this.pushFollow(FOLLOW_blockSuffix_in_lexerSubrule2103);
                    this.blockSuffix();
                    --this.state._fsp;
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_lexerBlock_in_lexerSubrule2105);
                    this.lexerBlock();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    break;
                }
                case 2: {
                    this.pushFollow(FOLLOW_lexerBlock_in_lexerSubrule2112);
                    this.lexerBlock();
                    --this.state._fsp;
                }
            }
            this.exitLexerSubrule((GrammarAST)retval.start);
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
    public final blockSuffix_return blockSuffix() throws RecognitionException {
        blockSuffix_return retval = new blockSuffix_return();
        retval.start = this.input.LT(1);
        this.enterBlockSuffix((GrammarAST)retval.start);
        try {
            this.pushFollow(FOLLOW_ebnfSuffix_in_blockSuffix2139);
            this.ebnfSuffix();
            --this.state._fsp;
            this.exitBlockSuffix((GrammarAST)retval.start);
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
    public final ebnfSuffix_return ebnfSuffix() throws RecognitionException {
        ebnfSuffix_return retval = new ebnfSuffix_return();
        retval.start = this.input.LT(1);
        this.enterEbnfSuffix((GrammarAST)retval.start);
        try {
            if (this.input.LA(1) != 80 && (this.input.LA(1) < 89 || this.input.LA(1) > 90)) {
                MismatchedSetException mse = new MismatchedSetException(null, this.input);
                throw mse;
            }
            this.input.consume();
            this.state.errorRecovery = false;
            this.exitEbnfSuffix((GrammarAST)retval.start);
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
        GrammarAST WILDCARD34 = null;
        GrammarAST WILDCARD35 = null;
        this.enterAtom((GrammarAST)retval.start);
        try {
            int alt44 = 7;
            switch (this.input.LA(1)) {
                case 20: {
                    int LA44_1 = this.input.LA(2);
                    if (LA44_1 == 2) {
                        int LA44_6 = this.input.LA(3);
                        if (LA44_6 == 28) {
                            int LA44_9 = this.input.LA(4);
                            if (LA44_9 == 62 || LA44_9 == 66) {
                                alt44 = 1;
                                break;
                            }
                            if (LA44_9 == 57) {
                                alt44 = 2;
                                break;
                            }
                            int nvaeMark = this.input.mark();
                            try {
                                for (int nvaeConsume = 0; nvaeConsume < 3; ++nvaeConsume) {
                                    this.input.consume();
                                }
                                NoViableAltException nvae = new NoViableAltException("", 44, 9, this.input);
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
                            NoViableAltException nvae = new NoViableAltException("", 44, 6, this.input);
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
                        NoViableAltException nvae = new NoViableAltException("", 44, 1, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 100: {
                    int LA44_2 = this.input.LA(2);
                    if (LA44_2 == 2) {
                        alt44 = 3;
                        break;
                    }
                    if (LA44_2 == -1 || LA44_2 >= 3 && LA44_2 <= 4 || LA44_2 == 10 || LA44_2 == 20 || LA44_2 == 39 || LA44_2 == 46 || LA44_2 == 52 || LA44_2 == 57 || LA44_2 == 59 || LA44_2 == 62 || LA44_2 == 66 || LA44_2 == 78 || LA44_2 == 80 || LA44_2 >= 89 && LA44_2 <= 90 || LA44_2 == 98 || LA44_2 == 100) {
                        alt44 = 4;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 44, 2, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 62: 
                case 66: {
                    alt44 = 5;
                    break;
                }
                case 98: {
                    alt44 = 6;
                    break;
                }
                case 57: {
                    alt44 = 7;
                    break;
                }
                default: {
                    NoViableAltException nvae = new NoViableAltException("", 44, 0, this.input);
                    throw nvae;
                }
            }
            switch (alt44) {
                case 1: {
                    this.match(this.input, 20, FOLLOW_DOT_in_atom2200);
                    this.match(this.input, 2, null);
                    this.match(this.input, 28, FOLLOW_ID_in_atom2202);
                    this.pushFollow(FOLLOW_terminal_in_atom2204);
                    this.terminal();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    break;
                }
                case 2: {
                    this.match(this.input, 20, FOLLOW_DOT_in_atom2211);
                    this.match(this.input, 2, null);
                    this.match(this.input, 28, FOLLOW_ID_in_atom2213);
                    this.pushFollow(FOLLOW_ruleref_in_atom2215);
                    this.ruleref();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    break;
                }
                case 3: {
                    WILDCARD34 = (GrammarAST)this.match(this.input, 100, FOLLOW_WILDCARD_in_atom2225);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_elementOptions_in_atom2227);
                    this.elementOptions();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    this.wildcardRef(WILDCARD34);
                    break;
                }
                case 4: {
                    WILDCARD35 = (GrammarAST)this.match(this.input, 100, FOLLOW_WILDCARD_in_atom2238);
                    this.wildcardRef(WILDCARD35);
                    break;
                }
                case 5: {
                    this.pushFollow(FOLLOW_terminal_in_atom2254);
                    this.terminal();
                    --this.state._fsp;
                    break;
                }
                case 6: {
                    this.pushFollow(FOLLOW_blockSet_in_atom2262);
                    this.blockSet();
                    --this.state._fsp;
                    break;
                }
                case 7: {
                    this.pushFollow(FOLLOW_ruleref_in_atom2272);
                    this.ruleref();
                    --this.state._fsp;
                }
            }
            this.exitAtom((GrammarAST)retval.start);
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
    public final blockSet_return blockSet() throws RecognitionException {
        blockSet_return retval = new blockSet_return();
        retval.start = this.input.LT(1);
        this.enterBlockSet((GrammarAST)retval.start);
        try {
            this.match(this.input, 98, FOLLOW_SET_in_blockSet2297);
            this.match(this.input, 2, null);
            int cnt45 = 0;
            block7: while (true) {
                int alt45 = 2;
                int LA45_0 = this.input.LA(1);
                if (LA45_0 == 32 || LA45_0 == 52 || LA45_0 == 62 || LA45_0 == 66) {
                    alt45 = 1;
                }
                switch (alt45) {
                    case 1: {
                        this.pushFollow(FOLLOW_setElement_in_blockSet2299);
                        this.setElement();
                        --this.state._fsp;
                        break;
                    }
                    default: {
                        if (cnt45 >= 1) break block7;
                        EarlyExitException eee = new EarlyExitException(45, this.input);
                        throw eee;
                    }
                }
                ++cnt45;
            }
            this.match(this.input, 3, null);
            this.exitBlockSet((GrammarAST)retval.start);
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
        GrammarAST STRING_LITERAL36 = null;
        GrammarAST TOKEN_REF37 = null;
        GrammarAST STRING_LITERAL38 = null;
        GrammarAST TOKEN_REF39 = null;
        this.enterSetElement((GrammarAST)retval.start);
        try {
            int alt46 = 6;
            switch (this.input.LA(1)) {
                case 62: {
                    int LA46_1 = this.input.LA(2);
                    if (LA46_1 == 2) {
                        alt46 = 1;
                        break;
                    }
                    if (LA46_1 == 3 || LA46_1 == 32 || LA46_1 == 52 || LA46_1 == 62 || LA46_1 == 66) {
                        alt46 = 3;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 46, 1, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 66: {
                    int LA46_2 = this.input.LA(2);
                    if (LA46_2 == 2) {
                        alt46 = 2;
                        break;
                    }
                    if (LA46_2 == 3 || LA46_2 == 32 || LA46_2 == 52 || LA46_2 == 62 || LA46_2 == 66) {
                        alt46 = 4;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 46, 2, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 52: {
                    alt46 = 5;
                    break;
                }
                case 32: {
                    alt46 = 6;
                    break;
                }
                default: {
                    NoViableAltException nvae = new NoViableAltException("", 46, 0, this.input);
                    throw nvae;
                }
            }
            switch (alt46) {
                case 1: {
                    STRING_LITERAL36 = (GrammarAST)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_setElement2323);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_elementOptions_in_setElement2325);
                    this.elementOptions();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    this.stringRef((TerminalAST)STRING_LITERAL36);
                    break;
                }
                case 2: {
                    TOKEN_REF37 = (GrammarAST)this.match(this.input, 66, FOLLOW_TOKEN_REF_in_setElement2337);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_elementOptions_in_setElement2339);
                    this.elementOptions();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    this.tokenRef((TerminalAST)TOKEN_REF37);
                    break;
                }
                case 3: {
                    STRING_LITERAL38 = (GrammarAST)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_setElement2349);
                    this.stringRef((TerminalAST)STRING_LITERAL38);
                    break;
                }
                case 4: {
                    TOKEN_REF39 = (GrammarAST)this.match(this.input, 66, FOLLOW_TOKEN_REF_in_setElement2374);
                    this.tokenRef((TerminalAST)TOKEN_REF39);
                    break;
                }
                case 5: {
                    this.match(this.input, 52, FOLLOW_RANGE_in_setElement2403);
                    this.match(this.input, 2, null);
                    a = (GrammarAST)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_setElement2407);
                    b = (GrammarAST)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_setElement2411);
                    this.match(this.input, 3, null);
                    this.stringRef((TerminalAST)a);
                    this.stringRef((TerminalAST)b);
                    break;
                }
                case 6: {
                    this.match(this.input, 32, FOLLOW_LEXER_CHAR_SET_in_setElement2434);
                }
            }
            this.exitSetElement((GrammarAST)retval.start);
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
    public final block_return block() throws RecognitionException {
        block_return retval = new block_return();
        retval.start = this.input.LT(1);
        this.enterBlock((GrammarAST)retval.start);
        try {
            this.match(this.input, 78, FOLLOW_BLOCK_in_block2459);
            this.match(this.input, 2, null);
            int alt47 = 2;
            int LA47_0 = this.input.LA(1);
            if (LA47_0 == 42) {
                alt47 = 1;
            }
            switch (alt47) {
                case 1: {
                    this.pushFollow(FOLLOW_optionsSpec_in_block2461);
                    this.optionsSpec();
                    --this.state._fsp;
                }
            }
            block16: while (true) {
                int alt48 = 2;
                int LA48_0 = this.input.LA(1);
                if (LA48_0 == 11) {
                    alt48 = 1;
                }
                switch (alt48) {
                    case 1: {
                        this.pushFollow(FOLLOW_ruleAction_in_block2464);
                        this.ruleAction();
                        --this.state._fsp;
                        continue block16;
                    }
                }
                break;
            }
            int alt49 = 2;
            int LA49_0 = this.input.LA(1);
            if (LA49_0 == 4) {
                alt49 = 1;
            }
            switch (alt49) {
                case 1: {
                    this.match(this.input, 4, FOLLOW_ACTION_in_block2467);
                }
            }
            int cnt50 = 0;
            block17: while (true) {
                int alt50 = 2;
                int LA50_0 = this.input.LA(1);
                if (LA50_0 == 74) {
                    alt50 = 1;
                }
                switch (alt50) {
                    case 1: {
                        this.pushFollow(FOLLOW_alternative_in_block2470);
                        this.alternative();
                        --this.state._fsp;
                        break;
                    }
                    default: {
                        if (cnt50 >= 1) break block17;
                        EarlyExitException eee = new EarlyExitException(50, this.input);
                        throw eee;
                    }
                }
                ++cnt50;
            }
            this.match(this.input, 3, null);
            this.exitBlock((GrammarAST)retval.start);
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
    public final ruleref_return ruleref() throws RecognitionException {
        ruleref_return retval = new ruleref_return();
        retval.start = this.input.LT(1);
        GrammarAST arg = null;
        GrammarAST RULE_REF40 = null;
        this.enterRuleref((GrammarAST)retval.start);
        try {
            RULE_REF40 = (GrammarAST)this.match(this.input, 57, FOLLOW_RULE_REF_in_ruleref2500);
            if (this.input.LA(1) == 2) {
                this.match(this.input, 2, null);
                int alt51 = 2;
                int LA51_0 = this.input.LA(1);
                if (LA51_0 == 8) {
                    alt51 = 1;
                }
                switch (alt51) {
                    case 1: {
                        arg = (GrammarAST)this.match(this.input, 8, FOLLOW_ARG_ACTION_in_ruleref2504);
                    }
                }
                int alt52 = 2;
                int LA52_0 = this.input.LA(1);
                if (LA52_0 == 82) {
                    alt52 = 1;
                }
                switch (alt52) {
                    case 1: {
                        this.pushFollow(FOLLOW_elementOptions_in_ruleref2507);
                        this.elementOptions();
                        --this.state._fsp;
                    }
                }
                this.match(this.input, 3, null);
            }
            this.ruleRef(RULE_REF40, (ActionAST)arg);
            if (arg != null) {
                this.actionInAlt((ActionAST)arg);
            }
            this.exitRuleref((GrammarAST)retval.start);
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
    public final range_return range() throws RecognitionException {
        range_return retval = new range_return();
        retval.start = this.input.LT(1);
        this.enterRange((GrammarAST)retval.start);
        try {
            this.match(this.input, 52, FOLLOW_RANGE_in_range2544);
            this.match(this.input, 2, null);
            this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_range2546);
            this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_range2548);
            this.match(this.input, 3, null);
            this.exitRange((GrammarAST)retval.start);
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
    public final terminal_return terminal() throws RecognitionException {
        terminal_return retval = new terminal_return();
        retval.start = this.input.LT(1);
        GrammarAST STRING_LITERAL41 = null;
        GrammarAST STRING_LITERAL42 = null;
        GrammarAST TOKEN_REF43 = null;
        GrammarAST TOKEN_REF44 = null;
        this.enterTerminal((GrammarAST)retval.start);
        try {
            int alt53;
            block23: {
                int LA53_0;
                block24: {
                    alt53 = 4;
                    LA53_0 = this.input.LA(1);
                    if (LA53_0 != 62) break block24;
                    int LA53_1 = this.input.LA(2);
                    if (LA53_1 == 2) {
                        alt53 = 1;
                        break block23;
                    } else if (LA53_1 == -1 || LA53_1 >= 3 && LA53_1 <= 4 || LA53_1 == 10 || LA53_1 == 20 || LA53_1 == 32 || LA53_1 == 39 || LA53_1 == 46 || LA53_1 == 52 || LA53_1 == 57 || LA53_1 == 59 || LA53_1 == 62 || LA53_1 == 66 || LA53_1 == 78 || LA53_1 == 80 || LA53_1 == 83 || LA53_1 >= 89 && LA53_1 <= 90 || LA53_1 == 98 || LA53_1 == 100) {
                        alt53 = 2;
                        break block23;
                    } else {
                        int nvaeMark = this.input.mark();
                        try {
                            this.input.consume();
                            NoViableAltException nvae = new NoViableAltException("", 53, 1, this.input);
                            throw nvae;
                        }
                        catch (Throwable throwable) {
                            this.input.rewind(nvaeMark);
                            throw throwable;
                        }
                    }
                }
                if (LA53_0 != 66) {
                    NoViableAltException nvae = new NoViableAltException("", 53, 0, this.input);
                    throw nvae;
                }
                int LA53_2 = this.input.LA(2);
                if (LA53_2 == 2) {
                    alt53 = 3;
                } else if (LA53_2 == -1 || LA53_2 >= 3 && LA53_2 <= 4 || LA53_2 == 10 || LA53_2 == 20 || LA53_2 == 32 || LA53_2 == 39 || LA53_2 == 46 || LA53_2 == 52 || LA53_2 == 57 || LA53_2 == 59 || LA53_2 == 62 || LA53_2 == 66 || LA53_2 == 78 || LA53_2 == 80 || LA53_2 == 83 || LA53_2 >= 89 && LA53_2 <= 90 || LA53_2 == 98 || LA53_2 == 100) {
                    alt53 = 4;
                } else {
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 53, 2, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
            }
            switch (alt53) {
                case 1: {
                    STRING_LITERAL41 = (GrammarAST)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_terminal2578);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_elementOptions_in_terminal2580);
                    this.elementOptions();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    this.stringRef((TerminalAST)STRING_LITERAL41);
                    break;
                }
                case 2: {
                    STRING_LITERAL42 = (GrammarAST)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_terminal2603);
                    this.stringRef((TerminalAST)STRING_LITERAL42);
                    break;
                }
                case 3: {
                    TOKEN_REF43 = (GrammarAST)this.match(this.input, 66, FOLLOW_TOKEN_REF_in_terminal2617);
                    this.match(this.input, 2, null);
                    this.pushFollow(FOLLOW_elementOptions_in_terminal2619);
                    this.elementOptions();
                    --this.state._fsp;
                    this.match(this.input, 3, null);
                    this.tokenRef((TerminalAST)TOKEN_REF43);
                    break;
                }
                case 4: {
                    TOKEN_REF44 = (GrammarAST)this.match(this.input, 66, FOLLOW_TOKEN_REF_in_terminal2630);
                    this.tokenRef((TerminalAST)TOKEN_REF44);
                    break;
                }
            }
            this.exitTerminal((GrammarAST)retval.start);
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
    public final elementOptions_return elementOptions() throws RecognitionException {
        elementOptions_return retval = new elementOptions_return();
        retval.start = this.input.LT(1);
        this.enterElementOptions((GrammarAST)retval.start);
        try {
            this.match(this.input, 82, FOLLOW_ELEMENT_OPTIONS_in_elementOptions2667);
            if (this.input.LA(1) == 2) {
                this.match(this.input, 2, null);
                block7: while (true) {
                    int alt54 = 2;
                    int LA54_0 = this.input.LA(1);
                    if (LA54_0 == 10 || LA54_0 == 28) {
                        alt54 = 1;
                    }
                    switch (alt54) {
                        case 1: {
                            this.pushFollow(FOLLOW_elementOption_in_elementOptions2669);
                            this.elementOption((GrammarASTWithOptions)((GrammarAST)retval.start).getParent());
                            --this.state._fsp;
                            continue block7;
                        }
                    }
                    break;
                }
                this.match(this.input, 3, null);
            }
            this.exitElementOptions((GrammarAST)retval.start);
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
    public final elementOption_return elementOption(GrammarASTWithOptions t) throws RecognitionException {
        elementOption_return retval = new elementOption_return();
        retval.start = this.input.LT(1);
        GrammarAST id = null;
        GrammarAST v = null;
        GrammarAST ID45 = null;
        GrammarAST ID46 = null;
        GrammarAST ID47 = null;
        GrammarAST ID48 = null;
        this.enterElementOption((GrammarAST)retval.start);
        try {
            int alt55;
            block33: {
                alt55 = 5;
                int LA55_0 = this.input.LA(1);
                if (LA55_0 == 28) {
                    alt55 = 1;
                } else {
                    if (LA55_0 != 10) {
                        NoViableAltException nvae = new NoViableAltException("", 55, 0, this.input);
                        throw nvae;
                    }
                    int LA55_2 = this.input.LA(2);
                    if (LA55_2 == 2) {
                        int LA55_3 = this.input.LA(3);
                        if (LA55_3 == 28) {
                            switch (this.input.LA(4)) {
                                case 28: {
                                    alt55 = 2;
                                    break;
                                }
                                case 62: {
                                    alt55 = 3;
                                    break;
                                }
                                case 4: {
                                    alt55 = 4;
                                    break;
                                }
                                case 30: {
                                    alt55 = 5;
                                    break;
                                }
                                default: {
                                    int nvaeMark = this.input.mark();
                                    try {
                                        int nvaeConsume = 0;
                                        while (true) {
                                            if (nvaeConsume >= 3) {
                                                NoViableAltException nvae = new NoViableAltException("", 55, 4, this.input);
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
                                        NoViableAltException nvae = new NoViableAltException("", 55, 3, this.input);
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
                        NoViableAltException nvae = new NoViableAltException("", 55, 2, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
            }
            switch (alt55) {
                case 1: {
                    ID45 = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_elementOption2700);
                    this.elementOption(t, ID45, null);
                    break;
                }
                case 2: {
                    this.match(this.input, 10, FOLLOW_ASSIGN_in_elementOption2720);
                    this.match(this.input, 2, null);
                    id = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_elementOption2724);
                    v = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_elementOption2728);
                    this.match(this.input, 3, null);
                    this.elementOption(t, id, v);
                    break;
                }
                case 3: {
                    this.match(this.input, 10, FOLLOW_ASSIGN_in_elementOption2744);
                    this.match(this.input, 2, null);
                    ID46 = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_elementOption2746);
                    v = (GrammarAST)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_elementOption2750);
                    this.match(this.input, 3, null);
                    this.elementOption(t, ID46, v);
                    break;
                }
                case 4: {
                    this.match(this.input, 10, FOLLOW_ASSIGN_in_elementOption2764);
                    this.match(this.input, 2, null);
                    ID47 = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_elementOption2766);
                    v = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_elementOption2770);
                    this.match(this.input, 3, null);
                    this.elementOption(t, ID47, v);
                    break;
                }
                case 5: {
                    this.match(this.input, 10, FOLLOW_ASSIGN_in_elementOption2786);
                    this.match(this.input, 2, null);
                    ID48 = (GrammarAST)this.match(this.input, 28, FOLLOW_ID_in_elementOption2788);
                    v = (GrammarAST)this.match(this.input, 30, FOLLOW_INT_in_elementOption2792);
                    this.match(this.input, 3, null);
                    this.elementOption(t, ID48, v);
                    break;
                }
            }
            this.exitElementOption((GrammarAST)retval.start);
            return retval;
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            return retval;
        }
    }

    static {
        int numStates = DFA38_transitionS.length;
        DFA38_transition = new short[numStates][];
        for (int i = 0; i < numStates; ++i) {
            GrammarTreeVisitor.DFA38_transition[i] = DFA.unpackEncodedString(DFA38_transitionS[i]);
        }
        FOLLOW_GRAMMAR_in_grammarSpec85 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_grammarSpec87 = new BitSet(new long[]{4398583392256L, 0x200000002L});
        FOLLOW_prequelConstructs_in_grammarSpec106 = new BitSet(new long[]{0L, 0x200000000L});
        FOLLOW_rules_in_grammarSpec123 = new BitSet(new long[]{0x1000000008L});
        FOLLOW_mode_in_grammarSpec125 = new BitSet(new long[]{0x1000000008L});
        FOLLOW_prequelConstruct_in_prequelConstructs167 = new BitSet(new long[]{4398583392258L, 2L});
        FOLLOW_optionsSpec_in_prequelConstruct194 = new BitSet(new long[]{2L});
        FOLLOW_delegateGrammars_in_prequelConstruct204 = new BitSet(new long[]{2L});
        FOLLOW_tokensSpec_in_prequelConstruct214 = new BitSet(new long[]{2L});
        FOLLOW_channelsSpec_in_prequelConstruct224 = new BitSet(new long[]{2L});
        FOLLOW_action_in_prequelConstruct234 = new BitSet(new long[]{2L});
        FOLLOW_OPTIONS_in_optionsSpec259 = new BitSet(new long[]{4L});
        FOLLOW_option_in_optionsSpec261 = new BitSet(new long[]{1032L});
        FOLLOW_ASSIGN_in_option295 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_option297 = new BitSet(new long[]{0x4000000050000000L});
        FOLLOW_optionValue_in_option301 = new BitSet(new long[]{8L});
        FOLLOW_IMPORT_in_delegateGrammars389 = new BitSet(new long[]{4L});
        FOLLOW_delegateGrammar_in_delegateGrammars391 = new BitSet(new long[]{268436488L});
        FOLLOW_ASSIGN_in_delegateGrammar420 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_delegateGrammar424 = new BitSet(new long[]{0x10000000L});
        FOLLOW_ID_in_delegateGrammar428 = new BitSet(new long[]{8L});
        FOLLOW_ID_in_delegateGrammar443 = new BitSet(new long[]{2L});
        FOLLOW_TOKENS_SPEC_in_tokensSpec477 = new BitSet(new long[]{4L});
        FOLLOW_tokenSpec_in_tokensSpec479 = new BitSet(new long[]{0x10000008L});
        FOLLOW_ID_in_tokenSpec502 = new BitSet(new long[]{2L});
        FOLLOW_CHANNELS_in_channelsSpec532 = new BitSet(new long[]{4L});
        FOLLOW_channelSpec_in_channelsSpec534 = new BitSet(new long[]{0x10000008L});
        FOLLOW_ID_in_channelSpec557 = new BitSet(new long[]{2L});
        FOLLOW_AT_in_action585 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_action589 = new BitSet(new long[]{0x10000000L});
        FOLLOW_ID_in_action594 = new BitSet(new long[]{16L});
        FOLLOW_ACTION_in_action596 = new BitSet(new long[]{8L});
        FOLLOW_RULES_in_rules624 = new BitSet(new long[]{4L});
        FOLLOW_rule_in_rules629 = new BitSet(new long[]{8L, 0x40000000L});
        FOLLOW_lexerRule_in_rules631 = new BitSet(new long[]{8L, 0x40000000L});
        FOLLOW_MODE_in_mode662 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_mode664 = new BitSet(new long[]{8L, 0x40000000L});
        FOLLOW_lexerRule_in_mode668 = new BitSet(new long[]{8L, 0x40000000L});
        FOLLOW_RULE_in_lexerRule694 = new BitSet(new long[]{4L});
        FOLLOW_TOKEN_REF_in_lexerRule696 = new BitSet(new long[]{0L, 0x100004000L});
        FOLLOW_RULEMODIFIERS_in_lexerRule708 = new BitSet(new long[]{4L});
        FOLLOW_FRAGMENT_in_lexerRule712 = new BitSet(new long[]{8L});
        FOLLOW_lexerRuleBlock_in_lexerRule737 = new BitSet(new long[]{8L});
        FOLLOW_RULE_in_rule782 = new BitSet(new long[]{4L});
        FOLLOW_RULE_REF_in_rule784 = new BitSet(new long[]{36033203655411968L, 0x100004001L});
        FOLLOW_RULEMODIFIERS_in_rule793 = new BitSet(new long[]{4L});
        FOLLOW_ruleModifier_in_rule798 = new BitSet(new long[]{1970324853751816L});
        FOLLOW_ARG_ACTION_in_rule809 = new BitSet(new long[]{36033203655411712L, 16385L});
        FOLLOW_ruleReturns_in_rule822 = new BitSet(new long[]{4406636447744L, 16385L});
        FOLLOW_throwsSpec_in_rule835 = new BitSet(new long[]{4406636447744L, 16384L});
        FOLLOW_locals_in_rule848 = new BitSet(new long[]{0x40000000800L, 16384L});
        FOLLOW_optionsSpec_in_rule863 = new BitSet(new long[]{0x40000000800L, 16384L});
        FOLLOW_ruleAction_in_rule877 = new BitSet(new long[]{0x40000000800L, 16384L});
        FOLLOW_ruleBlock_in_rule908 = new BitSet(new long[]{0x801008L});
        FOLLOW_exceptionGroup_in_rule910 = new BitSet(new long[]{8L});
        FOLLOW_exceptionHandler_in_exceptionGroup957 = new BitSet(new long[]{8392706L});
        FOLLOW_finallyClause_in_exceptionGroup960 = new BitSet(new long[]{2L});
        FOLLOW_CATCH_in_exceptionHandler986 = new BitSet(new long[]{4L});
        FOLLOW_ARG_ACTION_in_exceptionHandler988 = new BitSet(new long[]{16L});
        FOLLOW_ACTION_in_exceptionHandler990 = new BitSet(new long[]{8L});
        FOLLOW_FINALLY_in_finallyClause1015 = new BitSet(new long[]{4L});
        FOLLOW_ACTION_in_finallyClause1017 = new BitSet(new long[]{8L});
        FOLLOW_LOCALS_in_locals1045 = new BitSet(new long[]{4L});
        FOLLOW_ARG_ACTION_in_locals1047 = new BitSet(new long[]{8L});
        FOLLOW_RETURNS_in_ruleReturns1070 = new BitSet(new long[]{4L});
        FOLLOW_ARG_ACTION_in_ruleReturns1072 = new BitSet(new long[]{8L});
        FOLLOW_THROWS_in_throwsSpec1098 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_throwsSpec1100 = new BitSet(new long[]{0x10000008L});
        FOLLOW_AT_in_ruleAction1127 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_ruleAction1129 = new BitSet(new long[]{16L});
        FOLLOW_ACTION_in_ruleAction1131 = new BitSet(new long[]{8L});
        FOLLOW_BLOCK_in_lexerRuleBlock1209 = new BitSet(new long[]{4L});
        FOLLOW_lexerOuterAlternative_in_lexerRuleBlock1228 = new BitSet(new long[]{8L, 0x800400L});
        FOLLOW_BLOCK_in_ruleBlock1273 = new BitSet(new long[]{4L});
        FOLLOW_outerAlternative_in_ruleBlock1292 = new BitSet(new long[]{8L, 1024L});
        FOLLOW_lexerAlternative_in_lexerOuterAlternative1332 = new BitSet(new long[]{2L});
        FOLLOW_alternative_in_outerAlternative1354 = new BitSet(new long[]{2L});
        FOLLOW_LEXER_ALT_ACTION_in_lexerAlternative1376 = new BitSet(new long[]{4L});
        FOLLOW_lexerElements_in_lexerAlternative1378 = new BitSet(new long[]{0x10000000L, 0x400000L});
        FOLLOW_lexerCommand_in_lexerAlternative1380 = new BitSet(new long[]{0x10000008L, 0x400000L});
        FOLLOW_lexerElements_in_lexerAlternative1392 = new BitSet(new long[]{2L});
        FOLLOW_ALT_in_lexerElements1420 = new BitSet(new long[]{4L});
        FOLLOW_lexerElement_in_lexerElements1422 = new BitSet(new long[]{5336836481228997656L, 86000615428L});
        FOLLOW_labeledLexerElement_in_lexerElement1448 = new BitSet(new long[]{2L});
        FOLLOW_lexerAtom_in_lexerElement1453 = new BitSet(new long[]{2L});
        FOLLOW_lexerSubrule_in_lexerElement1458 = new BitSet(new long[]{2L});
        FOLLOW_ACTION_in_lexerElement1465 = new BitSet(new long[]{2L});
        FOLLOW_SEMPRED_in_lexerElement1479 = new BitSet(new long[]{2L});
        FOLLOW_ACTION_in_lexerElement1494 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_lexerElement1496 = new BitSet(new long[]{8L});
        FOLLOW_SEMPRED_in_lexerElement1507 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_lexerElement1509 = new BitSet(new long[]{8L});
        FOLLOW_EPSILON_in_lexerElement1517 = new BitSet(new long[]{2L});
        FOLLOW_set_in_labeledLexerElement1544 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_labeledLexerElement1550 = new BitSet(new long[]{4760305360181395456L, 0x1400004004L});
        FOLLOW_lexerAtom_in_labeledLexerElement1553 = new BitSet(new long[]{8L});
        FOLLOW_block_in_labeledLexerElement1555 = new BitSet(new long[]{8L});
        FOLLOW_BLOCK_in_lexerBlock1580 = new BitSet(new long[]{4L});
        FOLLOW_optionsSpec_in_lexerBlock1582 = new BitSet(new long[]{0L, 0x800400L});
        FOLLOW_lexerAlternative_in_lexerBlock1585 = new BitSet(new long[]{8L, 0x800400L});
        FOLLOW_terminal_in_lexerAtom1616 = new BitSet(new long[]{2L});
        FOLLOW_NOT_in_lexerAtom1627 = new BitSet(new long[]{4L});
        FOLLOW_blockSet_in_lexerAtom1629 = new BitSet(new long[]{8L});
        FOLLOW_blockSet_in_lexerAtom1640 = new BitSet(new long[]{2L});
        FOLLOW_WILDCARD_in_lexerAtom1651 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_lexerAtom1653 = new BitSet(new long[]{8L});
        FOLLOW_WILDCARD_in_lexerAtom1664 = new BitSet(new long[]{2L});
        FOLLOW_LEXER_CHAR_SET_in_lexerAtom1672 = new BitSet(new long[]{2L});
        FOLLOW_range_in_lexerAtom1682 = new BitSet(new long[]{2L});
        FOLLOW_ruleref_in_lexerAtom1692 = new BitSet(new long[]{2L});
        FOLLOW_ACTION_in_actionElement1716 = new BitSet(new long[]{2L});
        FOLLOW_ACTION_in_actionElement1724 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_actionElement1726 = new BitSet(new long[]{8L});
        FOLLOW_SEMPRED_in_actionElement1734 = new BitSet(new long[]{2L});
        FOLLOW_SEMPRED_in_actionElement1742 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_actionElement1744 = new BitSet(new long[]{8L});
        FOLLOW_ALT_in_alternative1767 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_alternative1769 = new BitSet(new long[]{5336836476935078928L, 86000091140L});
        FOLLOW_element_in_alternative1772 = new BitSet(new long[]{5336836476935078936L, 86000091140L});
        FOLLOW_ALT_in_alternative1780 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_alternative1782 = new BitSet(new long[]{0L, 524288L});
        FOLLOW_EPSILON_in_alternative1785 = new BitSet(new long[]{8L});
        FOLLOW_LEXER_ACTION_CALL_in_lexerCommand1811 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_lexerCommand1813 = new BitSet(new long[]{0x50000000L});
        FOLLOW_lexerCommandExpr_in_lexerCommand1815 = new BitSet(new long[]{8L});
        FOLLOW_ID_in_lexerCommand1831 = new BitSet(new long[]{2L});
        FOLLOW_labeledElement_in_element1888 = new BitSet(new long[]{2L});
        FOLLOW_atom_in_element1893 = new BitSet(new long[]{2L});
        FOLLOW_subrule_in_element1898 = new BitSet(new long[]{2L});
        FOLLOW_ACTION_in_element1905 = new BitSet(new long[]{2L});
        FOLLOW_SEMPRED_in_element1919 = new BitSet(new long[]{2L});
        FOLLOW_ACTION_in_element1934 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_element1936 = new BitSet(new long[]{8L});
        FOLLOW_SEMPRED_in_element1947 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_element1949 = new BitSet(new long[]{8L});
        FOLLOW_range_in_element1957 = new BitSet(new long[]{2L});
        FOLLOW_NOT_in_element1963 = new BitSet(new long[]{4L});
        FOLLOW_blockSet_in_element1965 = new BitSet(new long[]{8L});
        FOLLOW_NOT_in_element1972 = new BitSet(new long[]{4L});
        FOLLOW_block_in_element1974 = new BitSet(new long[]{8L});
        FOLLOW_atom_in_astOperand1996 = new BitSet(new long[]{2L});
        FOLLOW_NOT_in_astOperand2002 = new BitSet(new long[]{4L});
        FOLLOW_blockSet_in_astOperand2004 = new BitSet(new long[]{8L});
        FOLLOW_NOT_in_astOperand2011 = new BitSet(new long[]{4L});
        FOLLOW_block_in_astOperand2013 = new BitSet(new long[]{8L});
        FOLLOW_set_in_labeledElement2036 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_labeledElement2042 = new BitSet(new long[]{5336836476935078928L, 86000091140L});
        FOLLOW_element_in_labeledElement2044 = new BitSet(new long[]{8L});
        FOLLOW_blockSuffix_in_subrule2069 = new BitSet(new long[]{4L});
        FOLLOW_block_in_subrule2071 = new BitSet(new long[]{8L});
        FOLLOW_block_in_subrule2078 = new BitSet(new long[]{2L});
        FOLLOW_blockSuffix_in_lexerSubrule2103 = new BitSet(new long[]{4L});
        FOLLOW_lexerBlock_in_lexerSubrule2105 = new BitSet(new long[]{8L});
        FOLLOW_lexerBlock_in_lexerSubrule2112 = new BitSet(new long[]{2L});
        FOLLOW_ebnfSuffix_in_blockSuffix2139 = new BitSet(new long[]{2L});
        FOLLOW_DOT_in_atom2200 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_atom2202 = new BitSet(new long[]{0x4000000000000000L, 4L});
        FOLLOW_terminal_in_atom2204 = new BitSet(new long[]{8L});
        FOLLOW_DOT_in_atom2211 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_atom2213 = new BitSet(new long[]{0x200000000000000L});
        FOLLOW_ruleref_in_atom2215 = new BitSet(new long[]{8L});
        FOLLOW_WILDCARD_in_atom2225 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_atom2227 = new BitSet(new long[]{8L});
        FOLLOW_WILDCARD_in_atom2238 = new BitSet(new long[]{2L});
        FOLLOW_terminal_in_atom2254 = new BitSet(new long[]{2L});
        FOLLOW_blockSet_in_atom2262 = new BitSet(new long[]{2L});
        FOLLOW_ruleref_in_atom2272 = new BitSet(new long[]{2L});
        FOLLOW_SET_in_blockSet2297 = new BitSet(new long[]{4L});
        FOLLOW_setElement_in_blockSet2299 = new BitSet(new long[]{4616189622349725704L, 4L});
        FOLLOW_STRING_LITERAL_in_setElement2323 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_setElement2325 = new BitSet(new long[]{8L});
        FOLLOW_TOKEN_REF_in_setElement2337 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_setElement2339 = new BitSet(new long[]{8L});
        FOLLOW_STRING_LITERAL_in_setElement2349 = new BitSet(new long[]{2L});
        FOLLOW_TOKEN_REF_in_setElement2374 = new BitSet(new long[]{2L});
        FOLLOW_RANGE_in_setElement2403 = new BitSet(new long[]{4L});
        FOLLOW_STRING_LITERAL_in_setElement2407 = new BitSet(new long[]{0x4000000000000000L});
        FOLLOW_STRING_LITERAL_in_setElement2411 = new BitSet(new long[]{8L});
        FOLLOW_LEXER_CHAR_SET_in_setElement2434 = new BitSet(new long[]{2L});
        FOLLOW_BLOCK_in_block2459 = new BitSet(new long[]{4L});
        FOLLOW_optionsSpec_in_block2461 = new BitSet(new long[]{2064L, 1024L});
        FOLLOW_ruleAction_in_block2464 = new BitSet(new long[]{2064L, 1024L});
        FOLLOW_ACTION_in_block2467 = new BitSet(new long[]{0L, 1024L});
        FOLLOW_alternative_in_block2470 = new BitSet(new long[]{8L, 1024L});
        FOLLOW_RULE_REF_in_ruleref2500 = new BitSet(new long[]{4L});
        FOLLOW_ARG_ACTION_in_ruleref2504 = new BitSet(new long[]{8L, 262144L});
        FOLLOW_elementOptions_in_ruleref2507 = new BitSet(new long[]{8L});
        FOLLOW_RANGE_in_range2544 = new BitSet(new long[]{4L});
        FOLLOW_STRING_LITERAL_in_range2546 = new BitSet(new long[]{0x4000000000000000L});
        FOLLOW_STRING_LITERAL_in_range2548 = new BitSet(new long[]{8L});
        FOLLOW_STRING_LITERAL_in_terminal2578 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_terminal2580 = new BitSet(new long[]{8L});
        FOLLOW_STRING_LITERAL_in_terminal2603 = new BitSet(new long[]{2L});
        FOLLOW_TOKEN_REF_in_terminal2617 = new BitSet(new long[]{4L});
        FOLLOW_elementOptions_in_terminal2619 = new BitSet(new long[]{8L});
        FOLLOW_TOKEN_REF_in_terminal2630 = new BitSet(new long[]{2L});
        FOLLOW_ELEMENT_OPTIONS_in_elementOptions2667 = new BitSet(new long[]{4L});
        FOLLOW_elementOption_in_elementOptions2669 = new BitSet(new long[]{268436488L});
        FOLLOW_ID_in_elementOption2700 = new BitSet(new long[]{2L});
        FOLLOW_ASSIGN_in_elementOption2720 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_elementOption2724 = new BitSet(new long[]{0x10000000L});
        FOLLOW_ID_in_elementOption2728 = new BitSet(new long[]{8L});
        FOLLOW_ASSIGN_in_elementOption2744 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_elementOption2746 = new BitSet(new long[]{0x4000000000000000L});
        FOLLOW_STRING_LITERAL_in_elementOption2750 = new BitSet(new long[]{8L});
        FOLLOW_ASSIGN_in_elementOption2764 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_elementOption2766 = new BitSet(new long[]{16L});
        FOLLOW_ACTION_in_elementOption2770 = new BitSet(new long[]{8L});
        FOLLOW_ASSIGN_in_elementOption2786 = new BitSet(new long[]{4L});
        FOLLOW_ID_in_elementOption2788 = new BitSet(new long[]{0x40000000L});
        FOLLOW_INT_in_elementOption2792 = new BitSet(new long[]{8L});
    }

    protected class DFA38
    extends DFA {
        public DFA38(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 38;
            this.eot = DFA38_eot;
            this.eof = DFA38_eof;
            this.min = DFA38_min;
            this.max = DFA38_max;
            this.accept = DFA38_accept;
            this.special = DFA38_special;
            this.transition = DFA38_transition;
        }

        @Override
        public String getDescription() {
            return "749:1: alternative : ( ^( ALT ( elementOptions )? ( element )+ ) | ^( ALT ( elementOptions )? EPSILON ) );";
        }
    }

    public static class elementOption_return
    extends TreeRuleReturnScope {
    }

    public static class elementOptions_return
    extends TreeRuleReturnScope {
    }

    public static class terminal_return
    extends TreeRuleReturnScope {
    }

    public static class range_return
    extends TreeRuleReturnScope {
    }

    public static class ruleref_return
    extends TreeRuleReturnScope {
    }

    public static class block_return
    extends TreeRuleReturnScope {
    }

    public static class setElement_return
    extends TreeRuleReturnScope {
    }

    public static class blockSet_return
    extends TreeRuleReturnScope {
    }

    public static class atom_return
    extends TreeRuleReturnScope {
    }

    public static class ebnfSuffix_return
    extends TreeRuleReturnScope {
    }

    public static class blockSuffix_return
    extends TreeRuleReturnScope {
    }

    public static class lexerSubrule_return
    extends TreeRuleReturnScope {
    }

    public static class subrule_return
    extends TreeRuleReturnScope {
    }

    public static class labeledElement_return
    extends TreeRuleReturnScope {
    }

    public static class astOperand_return
    extends TreeRuleReturnScope {
    }

    public static class element_return
    extends TreeRuleReturnScope {
    }

    public static class lexerCommandExpr_return
    extends TreeRuleReturnScope {
    }

    public static class lexerCommand_return
    extends TreeRuleReturnScope {
    }

    public static class alternative_return
    extends TreeRuleReturnScope {
    }

    public static class actionElement_return
    extends TreeRuleReturnScope {
    }

    public static class lexerAtom_return
    extends TreeRuleReturnScope {
    }

    public static class lexerBlock_return
    extends TreeRuleReturnScope {
    }

    public static class labeledLexerElement_return
    extends TreeRuleReturnScope {
    }

    public static class lexerElement_return
    extends TreeRuleReturnScope {
    }

    public static class lexerElements_return
    extends TreeRuleReturnScope {
    }

    public static class lexerAlternative_return
    extends TreeRuleReturnScope {
    }

    public static class outerAlternative_return
    extends TreeRuleReturnScope {
    }

    public static class lexerOuterAlternative_return
    extends TreeRuleReturnScope {
    }

    public static class ruleBlock_return
    extends TreeRuleReturnScope {
    }

    public static class lexerRuleBlock_return
    extends TreeRuleReturnScope {
    }

    public static class ruleModifier_return
    extends TreeRuleReturnScope {
    }

    public static class ruleAction_return
    extends TreeRuleReturnScope {
    }

    public static class throwsSpec_return
    extends TreeRuleReturnScope {
    }

    public static class ruleReturns_return
    extends TreeRuleReturnScope {
    }

    public static class locals_return
    extends TreeRuleReturnScope {
    }

    public static class finallyClause_return
    extends TreeRuleReturnScope {
    }

    public static class exceptionHandler_return
    extends TreeRuleReturnScope {
    }

    public static class exceptionGroup_return
    extends TreeRuleReturnScope {
    }

    public static class rule_return
    extends TreeRuleReturnScope {
    }

    public static class lexerRule_return
    extends TreeRuleReturnScope {
    }

    public static class mode_return
    extends TreeRuleReturnScope {
    }

    public static class rules_return
    extends TreeRuleReturnScope {
    }

    public static class action_return
    extends TreeRuleReturnScope {
    }

    public static class channelSpec_return
    extends TreeRuleReturnScope {
    }

    public static class channelsSpec_return
    extends TreeRuleReturnScope {
    }

    public static class tokenSpec_return
    extends TreeRuleReturnScope {
    }

    public static class tokensSpec_return
    extends TreeRuleReturnScope {
    }

    public static class delegateGrammar_return
    extends TreeRuleReturnScope {
    }

    public static class delegateGrammars_return
    extends TreeRuleReturnScope {
    }

    public static class optionValue_return
    extends TreeRuleReturnScope {
        public String v;
    }

    public static class option_return
    extends TreeRuleReturnScope {
    }

    public static class optionsSpec_return
    extends TreeRuleReturnScope {
    }

    public static class prequelConstruct_return
    extends TreeRuleReturnScope {
    }

    public static class prequelConstructs_return
    extends TreeRuleReturnScope {
        public GrammarAST firstOne = null;
    }

    public static class grammarSpec_return
    extends TreeRuleReturnScope {
    }
}

