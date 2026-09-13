/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.parse;

import groovyjarjarantlr4.runtime.BitSet;
import groovyjarjarantlr4.runtime.EarlyExitException;
import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.NoViableAltException;
import groovyjarjarantlr4.runtime.Parser;
import groovyjarjarantlr4.runtime.ParserRuleReturnScope;
import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.RecognizerSharedState;
import groovyjarjarantlr4.runtime.RuleReturnScope;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.TokenStream;
import groovyjarjarantlr4.runtime.tree.CommonTreeAdaptor;
import groovyjarjarantlr4.runtime.tree.RewriteEarlyExitException;
import groovyjarjarantlr4.runtime.tree.RewriteRuleSubtreeStream;
import groovyjarjarantlr4.runtime.tree.RewriteRuleTokenStream;
import groovyjarjarantlr4.runtime.tree.TreeAdaptor;
import groovyjarjarantlr4.v4.parse.ResyncToEndOfRuleBlock;
import groovyjarjarantlr4.v4.parse.v3TreeGrammarException;
import groovyjarjarantlr4.v4.parse.v4ParserException;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.AltAST;
import groovyjarjarantlr4.v4.tool.ast.BlockAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarRootAST;
import groovyjarjarantlr4.v4.tool.ast.NotAST;
import groovyjarjarantlr4.v4.tool.ast.OptionalBlockAST;
import groovyjarjarantlr4.v4.tool.ast.PlusBlockAST;
import groovyjarjarantlr4.v4.tool.ast.PredAST;
import groovyjarjarantlr4.v4.tool.ast.RangeAST;
import groovyjarjarantlr4.v4.tool.ast.RuleAST;
import groovyjarjarantlr4.v4.tool.ast.RuleRefAST;
import groovyjarjarantlr4.v4.tool.ast.SetAST;
import groovyjarjarantlr4.v4.tool.ast.StarBlockAST;
import groovyjarjarantlr4.v4.tool.ast.TerminalAST;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class ANTLRParser
extends Parser {
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
    Deque<String> paraphrases = new ArrayDeque<String>();
    public static final BitSet FOLLOW_grammarType_in_grammarSpec396 = new BitSet(new long[]{0x200000000000000L, 4L});
    public static final BitSet FOLLOW_id_in_grammarSpec398 = new BitSet(new long[]{0x400000000000000L});
    public static final BitSet FOLLOW_SEMI_in_grammarSpec400 = new BitSet(new long[]{144119586676025344L, 6L});
    public static final BitSet FOLLOW_sync_in_grammarSpec438 = new BitSet(new long[]{144119586676025344L, 6L});
    public static final BitSet FOLLOW_prequelConstruct_in_grammarSpec442 = new BitSet(new long[]{144119586676025344L, 6L});
    public static final BitSet FOLLOW_sync_in_grammarSpec444 = new BitSet(new long[]{144119586676025344L, 6L});
    public static final BitSet FOLLOW_rules_in_grammarSpec469 = new BitSet(new long[]{0x1000000000L});
    public static final BitSet FOLLOW_modeSpec_in_grammarSpec475 = new BitSet(new long[]{0x1000000000L});
    public static final BitSet FOLLOW_EOF_in_grammarSpec513 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_LEXER_in_grammarType683 = new BitSet(new long[]{0x2000000L});
    public static final BitSet FOLLOW_GRAMMAR_in_grammarType687 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_PARSER_in_grammarType710 = new BitSet(new long[]{0x2000000L});
    public static final BitSet FOLLOW_GRAMMAR_in_grammarType714 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_GRAMMAR_in_grammarType735 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_TREE_GRAMMAR_in_grammarType762 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_optionsSpec_in_prequelConstruct788 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_delegateGrammars_in_prequelConstruct811 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_tokensSpec_in_prequelConstruct855 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_channelsSpec_in_prequelConstruct865 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_action_in_prequelConstruct902 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_OPTIONS_in_optionsSpec917 = new BitSet(new long[]{0x240000000000000L, 4L});
    public static final BitSet FOLLOW_option_in_optionsSpec920 = new BitSet(new long[]{0x400000000000000L});
    public static final BitSet FOLLOW_SEMI_in_optionsSpec922 = new BitSet(new long[]{0x240000000000000L, 4L});
    public static final BitSet FOLLOW_RBRACE_in_optionsSpec926 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_id_in_option955 = new BitSet(new long[]{1024L});
    public static final BitSet FOLLOW_ASSIGN_in_option957 = new BitSet(new long[]{4755801207576985616L, 4L});
    public static final BitSet FOLLOW_optionValue_in_option960 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_qid_in_optionValue1003 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_optionValue1011 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_ACTION_in_optionValue1016 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_INT_in_optionValue1027 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_IMPORT_in_delegateGrammars1043 = new BitSet(new long[]{0x200000000000000L, 4L});
    public static final BitSet FOLLOW_delegateGrammar_in_delegateGrammars1045 = new BitSet(new long[]{0x400000000010000L});
    public static final BitSet FOLLOW_COMMA_in_delegateGrammars1048 = new BitSet(new long[]{0x200000000000000L, 4L});
    public static final BitSet FOLLOW_delegateGrammar_in_delegateGrammars1050 = new BitSet(new long[]{0x400000000010000L});
    public static final BitSet FOLLOW_SEMI_in_delegateGrammars1054 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_id_in_delegateGrammar1081 = new BitSet(new long[]{1024L});
    public static final BitSet FOLLOW_ASSIGN_in_delegateGrammar1083 = new BitSet(new long[]{0x200000000000000L, 4L});
    public static final BitSet FOLLOW_id_in_delegateGrammar1086 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_id_in_delegateGrammar1096 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_TOKENS_SPEC_in_tokensSpec1110 = new BitSet(new long[]{0x200000000000000L, 4L});
    public static final BitSet FOLLOW_id_in_tokensSpec1112 = new BitSet(new long[]{0x40000000010000L});
    public static final BitSet FOLLOW_COMMA_in_tokensSpec1115 = new BitSet(new long[]{0x200000000000000L, 4L});
    public static final BitSet FOLLOW_id_in_tokensSpec1117 = new BitSet(new long[]{0x40000000010000L});
    public static final BitSet FOLLOW_COMMA_in_tokensSpec1121 = new BitSet(new long[]{0x40000000000000L});
    public static final BitSet FOLLOW_RBRACE_in_tokensSpec1124 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_TOKENS_SPEC_in_tokensSpec1141 = new BitSet(new long[]{0x40000000000000L});
    public static final BitSet FOLLOW_RBRACE_in_tokensSpec1143 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_TOKENS_SPEC_in_tokensSpec1153 = new BitSet(new long[]{0x200000000000000L, 4L});
    public static final BitSet FOLLOW_v3tokenSpec_in_tokensSpec1156 = new BitSet(new long[]{0x240000000000000L, 4L});
    public static final BitSet FOLLOW_RBRACE_in_tokensSpec1159 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_id_in_v3tokenSpec1179 = new BitSet(new long[]{0x400000000000400L});
    public static final BitSet FOLLOW_ASSIGN_in_v3tokenSpec1185 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_v3tokenSpec1189 = new BitSet(new long[]{0x400000000000000L});
    public static final BitSet FOLLOW_SEMI_in_v3tokenSpec1250 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_CHANNELS_in_channelsSpec1261 = new BitSet(new long[]{0x240000000000000L, 4L});
    public static final BitSet FOLLOW_id_in_channelsSpec1265 = new BitSet(new long[]{0x40000000010000L});
    public static final BitSet FOLLOW_COMMA_in_channelsSpec1268 = new BitSet(new long[]{0x200000000000000L, 4L});
    public static final BitSet FOLLOW_id_in_channelsSpec1271 = new BitSet(new long[]{0x40000000010000L});
    public static final BitSet FOLLOW_COMMA_in_channelsSpec1275 = new BitSet(new long[]{0x40000000000000L});
    public static final BitSet FOLLOW_RBRACE_in_channelsSpec1280 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_AT_in_action1297 = new BitSet(new long[]{144132782409383936L, 4L});
    public static final BitSet FOLLOW_actionScopeName_in_action1300 = new BitSet(new long[]{32768L});
    public static final BitSet FOLLOW_COLONCOLON_in_action1302 = new BitSet(new long[]{0x200000000000000L, 4L});
    public static final BitSet FOLLOW_id_in_action1306 = new BitSet(new long[]{16L});
    public static final BitSet FOLLOW_ACTION_in_action1308 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_id_in_actionScopeName1337 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_LEXER_in_actionScopeName1342 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_PARSER_in_actionScopeName1357 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_MODE_in_modeSpec1376 = new BitSet(new long[]{0x200000000000000L, 4L});
    public static final BitSet FOLLOW_id_in_modeSpec1378 = new BitSet(new long[]{0x400000000000000L});
    public static final BitSet FOLLOW_SEMI_in_modeSpec1380 = new BitSet(new long[]{0x1000000L, 4L});
    public static final BitSet FOLLOW_sync_in_modeSpec1382 = new BitSet(new long[]{0x1000002L, 4L});
    public static final BitSet FOLLOW_lexerRule_in_modeSpec1385 = new BitSet(new long[]{0x1000000L, 4L});
    public static final BitSet FOLLOW_sync_in_modeSpec1387 = new BitSet(new long[]{0x1000002L, 4L});
    public static final BitSet FOLLOW_sync_in_rules1418 = new BitSet(new long[]{0x200000001000002L, 4L});
    public static final BitSet FOLLOW_rule_in_rules1421 = new BitSet(new long[]{0x200000001000000L, 4L});
    public static final BitSet FOLLOW_sync_in_rules1423 = new BitSet(new long[]{0x200000001000002L, 4L});
    public static final BitSet FOLLOW_parserRule_in_rule1485 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_lexerRule_in_rule1490 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_RULE_REF_in_parserRule1539 = new BitSet(new long[]{36033203655411968L, 1L});
    public static final BitSet FOLLOW_ARG_ACTION_in_parserRule1569 = new BitSet(new long[]{36033203655411712L, 1L});
    public static final BitSet FOLLOW_ruleReturns_in_parserRule1576 = new BitSet(new long[]{4406636447744L, 1L});
    public static final BitSet FOLLOW_throwsSpec_in_parserRule1583 = new BitSet(new long[]{4406636447744L});
    public static final BitSet FOLLOW_localsSpec_in_parserRule1590 = new BitSet(new long[]{0x40000000800L});
    public static final BitSet FOLLOW_rulePrequels_in_parserRule1628 = new BitSet(new long[]{16384L});
    public static final BitSet FOLLOW_COLON_in_parserRule1637 = new BitSet(new long[]{5332403297591492624L, 4L});
    public static final BitSet FOLLOW_ruleBlock_in_parserRule1660 = new BitSet(new long[]{0x400000000000000L});
    public static final BitSet FOLLOW_SEMI_in_parserRule1669 = new BitSet(new long[]{0x801000L});
    public static final BitSet FOLLOW_exceptionGroup_in_parserRule1678 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_exceptionHandler_in_exceptionGroup1761 = new BitSet(new long[]{8392706L});
    public static final BitSet FOLLOW_finallyClause_in_exceptionGroup1764 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_CATCH_in_exceptionHandler1781 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_ARG_ACTION_in_exceptionHandler1783 = new BitSet(new long[]{16L});
    public static final BitSet FOLLOW_ACTION_in_exceptionHandler1785 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_FINALLY_in_finallyClause1812 = new BitSet(new long[]{16L});
    public static final BitSet FOLLOW_ACTION_in_finallyClause1814 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_sync_in_rulePrequels1846 = new BitSet(new long[]{4398046513154L});
    public static final BitSet FOLLOW_rulePrequel_in_rulePrequels1849 = new BitSet(new long[]{0x40000000800L});
    public static final BitSet FOLLOW_sync_in_rulePrequels1851 = new BitSet(new long[]{4398046513154L});
    public static final BitSet FOLLOW_optionsSpec_in_rulePrequel1875 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_ruleAction_in_rulePrequel1883 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_RETURNS_in_ruleReturns1903 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_ARG_ACTION_in_ruleReturns1906 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_THROWS_in_throwsSpec1934 = new BitSet(new long[]{0x200000000000000L, 4L});
    public static final BitSet FOLLOW_qid_in_throwsSpec1936 = new BitSet(new long[]{65538L});
    public static final BitSet FOLLOW_COMMA_in_throwsSpec1939 = new BitSet(new long[]{0x200000000000000L, 4L});
    public static final BitSet FOLLOW_qid_in_throwsSpec1941 = new BitSet(new long[]{65538L});
    public static final BitSet FOLLOW_LOCALS_in_localsSpec1966 = new BitSet(new long[]{256L});
    public static final BitSet FOLLOW_ARG_ACTION_in_localsSpec1969 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_AT_in_ruleAction1992 = new BitSet(new long[]{0x200000000000000L, 4L});
    public static final BitSet FOLLOW_id_in_ruleAction1994 = new BitSet(new long[]{16L});
    public static final BitSet FOLLOW_ACTION_in_ruleAction1996 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_ruleAltList_in_ruleBlock2034 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_labeledAlt_in_ruleAltList2070 = new BitSet(new long[]{0x80000000002L});
    public static final BitSet FOLLOW_OR_in_ruleAltList2073 = new BitSet(new long[]{5332403297591492624L, 4L});
    public static final BitSet FOLLOW_labeledAlt_in_ruleAltList2075 = new BitSet(new long[]{0x80000000002L});
    public static final BitSet FOLLOW_alternative_in_labeledAlt2093 = new BitSet(new long[]{0x800000000002L});
    public static final BitSet FOLLOW_POUND_in_labeledAlt2099 = new BitSet(new long[]{0x200000000000000L, 4L});
    public static final BitSet FOLLOW_id_in_labeledAlt2102 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_FRAGMENT_in_lexerRule2134 = new BitSet(new long[]{0L, 4L});
    public static final BitSet FOLLOW_TOKEN_REF_in_lexerRule2140 = new BitSet(new long[]{16384L});
    public static final BitSet FOLLOW_COLON_in_lexerRule2142 = new BitSet(new long[]{5341269729293107216L, 4L});
    public static final BitSet FOLLOW_lexerRuleBlock_in_lexerRule2144 = new BitSet(new long[]{0x400000000000000L});
    public static final BitSet FOLLOW_SEMI_in_lexerRule2146 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_lexerAltList_in_lexerRuleBlock2210 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_lexerAlt_in_lexerAltList2246 = new BitSet(new long[]{0x80000000002L});
    public static final BitSet FOLLOW_OR_in_lexerAltList2249 = new BitSet(new long[]{5341269729293107216L, 4L});
    public static final BitSet FOLLOW_lexerAlt_in_lexerAltList2251 = new BitSet(new long[]{0x80000000002L});
    public static final BitSet FOLLOW_lexerElements_in_lexerAlt2269 = new BitSet(new long[]{0x20000000000002L});
    public static final BitSet FOLLOW_lexerCommands_in_lexerAlt2275 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_lexerElement_in_lexerElements2318 = new BitSet(new long[]{5332262530038366226L, 4L});
    public static final BitSet FOLLOW_labeledLexerElement_in_lexerElement2374 = new BitSet(new long[]{0x2008200000000002L});
    public static final BitSet FOLLOW_ebnfSuffix_in_lexerElement2380 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_lexerAtom_in_lexerElement2426 = new BitSet(new long[]{0x2008200000000002L});
    public static final BitSet FOLLOW_ebnfSuffix_in_lexerElement2432 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_lexerBlock_in_lexerElement2478 = new BitSet(new long[]{0x2008200000000002L});
    public static final BitSet FOLLOW_ebnfSuffix_in_lexerElement2484 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_actionElement_in_lexerElement2512 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_id_in_labeledLexerElement2542 = new BitSet(new long[]{0x400000000400L});
    public static final BitSet FOLLOW_ASSIGN_in_labeledLexerElement2547 = new BitSet(new long[]{4755801777734942720L, 4L});
    public static final BitSet FOLLOW_PLUS_ASSIGN_in_labeledLexerElement2551 = new BitSet(new long[]{4755801777734942720L, 4L});
    public static final BitSet FOLLOW_lexerAtom_in_labeledLexerElement2558 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_lexerBlock_in_labeledLexerElement2575 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_LPAREN_in_lexerBlock2608 = new BitSet(new long[]{5341274127339618320L, 4L});
    public static final BitSet FOLLOW_optionsSpec_in_lexerBlock2620 = new BitSet(new long[]{16384L});
    public static final BitSet FOLLOW_COLON_in_lexerBlock2622 = new BitSet(new long[]{5341269729293107216L, 4L});
    public static final BitSet FOLLOW_lexerAltList_in_lexerBlock2635 = new BitSet(new long[]{0x100000000000000L});
    public static final BitSet FOLLOW_RPAREN_in_lexerBlock2645 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_RARROW_in_lexerCommands2682 = new BitSet(new long[]{0x200001000000000L, 4L});
    public static final BitSet FOLLOW_lexerCommand_in_lexerCommands2684 = new BitSet(new long[]{65538L});
    public static final BitSet FOLLOW_COMMA_in_lexerCommands2687 = new BitSet(new long[]{0x200001000000000L, 4L});
    public static final BitSet FOLLOW_lexerCommand_in_lexerCommands2689 = new BitSet(new long[]{65538L});
    public static final BitSet FOLLOW_lexerCommandName_in_lexerCommand2707 = new BitSet(new long[]{0x400000000L});
    public static final BitSet FOLLOW_LPAREN_in_lexerCommand2709 = new BitSet(new long[]{0x200000040000000L, 4L});
    public static final BitSet FOLLOW_lexerCommandExpr_in_lexerCommand2711 = new BitSet(new long[]{0x100000000000000L});
    public static final BitSet FOLLOW_RPAREN_in_lexerCommand2713 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_lexerCommandName_in_lexerCommand2728 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_id_in_lexerCommandExpr2739 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_INT_in_lexerCommandExpr2744 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_id_in_lexerCommandName2768 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_MODE_in_lexerCommandName2786 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_alternative_in_altList2814 = new BitSet(new long[]{0x80000000002L});
    public static final BitSet FOLLOW_OR_in_altList2817 = new BitSet(new long[]{5332271356196159504L, 4L});
    public static final BitSet FOLLOW_alternative_in_altList2819 = new BitSet(new long[]{0x80000000002L});
    public static final BitSet FOLLOW_elementOptions_in_alternative2853 = new BitSet(new long[]{5332262525743398930L, 4L});
    public static final BitSet FOLLOW_element_in_alternative2862 = new BitSet(new long[]{5332262525743398930L, 4L});
    public static final BitSet FOLLOW_labeledElement_in_element2977 = new BitSet(new long[]{0x2008200000000002L});
    public static final BitSet FOLLOW_ebnfSuffix_in_element2983 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_atom_in_element3029 = new BitSet(new long[]{0x2008200000000002L});
    public static final BitSet FOLLOW_ebnfSuffix_in_element3035 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_ebnf_in_element3081 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_actionElement_in_element3086 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_ACTION_in_actionElement3112 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_ACTION_in_actionElement3122 = new BitSet(new long[]{0x800000000L});
    public static final BitSet FOLLOW_elementOptions_in_actionElement3124 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_SEMPRED_in_actionElement3142 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_SEMPRED_in_actionElement3152 = new BitSet(new long[]{0x800000000L});
    public static final BitSet FOLLOW_elementOptions_in_actionElement3154 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_id_in_labeledElement3176 = new BitSet(new long[]{0x400000000400L});
    public static final BitSet FOLLOW_ASSIGN_in_labeledElement3181 = new BitSet(new long[]{4755801773439975424L, 4L});
    public static final BitSet FOLLOW_PLUS_ASSIGN_in_labeledElement3185 = new BitSet(new long[]{4755801773439975424L, 4L});
    public static final BitSet FOLLOW_atom_in_labeledElement3192 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_block_in_labeledElement3214 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_block_in_ebnf3250 = new BitSet(new long[]{0x2008200000000002L});
    public static final BitSet FOLLOW_blockSuffix_in_ebnf3274 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_ebnfSuffix_in_blockSuffix3324 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_QUESTION_in_ebnfSuffix3339 = new BitSet(new long[]{0x8000000000002L});
    public static final BitSet FOLLOW_QUESTION_in_ebnfSuffix3343 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_STAR_in_ebnfSuffix3359 = new BitSet(new long[]{0x8000000000002L});
    public static final BitSet FOLLOW_QUESTION_in_ebnfSuffix3363 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_PLUS_in_ebnfSuffix3381 = new BitSet(new long[]{0x8000000000002L});
    public static final BitSet FOLLOW_QUESTION_in_ebnfSuffix3385 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_range_in_lexerAtom3406 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_terminal_in_lexerAtom3411 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_RULE_REF_in_lexerAtom3421 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_notSet_in_lexerAtom3432 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_wildcard_in_lexerAtom3440 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_LEXER_CHAR_SET_in_lexerAtom3448 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_range_in_atom3493 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_terminal_in_atom3500 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_ruleref_in_atom3510 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_notSet_in_atom3518 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_wildcard_in_atom3526 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_DOT_in_wildcard3574 = new BitSet(new long[]{0x800000002L});
    public static final BitSet FOLLOW_elementOptions_in_wildcard3576 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_NOT_in_notSet3614 = new BitSet(new long[]{0x4000000100000000L, 4L});
    public static final BitSet FOLLOW_setElement_in_notSet3616 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_NOT_in_notSet3644 = new BitSet(new long[]{0x400000000L});
    public static final BitSet FOLLOW_blockSet_in_notSet3646 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_LPAREN_in_blockSet3681 = new BitSet(new long[]{0x4000000100000000L, 4L});
    public static final BitSet FOLLOW_setElement_in_blockSet3683 = new BitSet(new long[]{0x100080000000000L});
    public static final BitSet FOLLOW_OR_in_blockSet3686 = new BitSet(new long[]{0x4000000100000000L, 4L});
    public static final BitSet FOLLOW_setElement_in_blockSet3688 = new BitSet(new long[]{0x100080000000000L});
    public static final BitSet FOLLOW_RPAREN_in_blockSet3692 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_TOKEN_REF_in_setElement3722 = new BitSet(new long[]{0x800000002L});
    public static final BitSet FOLLOW_elementOptions_in_setElement3728 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_setElement3734 = new BitSet(new long[]{0x800000002L});
    public static final BitSet FOLLOW_elementOptions_in_setElement3740 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_range_in_setElement3746 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_LEXER_CHAR_SET_in_setElement3756 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_LPAREN_in_block3780 = new BitSet(new long[]{5332275754242689040L, 4L});
    public static final BitSet FOLLOW_optionsSpec_in_block3792 = new BitSet(new long[]{18432L});
    public static final BitSet FOLLOW_ruleAction_in_block3797 = new BitSet(new long[]{18432L});
    public static final BitSet FOLLOW_COLON_in_block3800 = new BitSet(new long[]{5332271356196159504L, 4L});
    public static final BitSet FOLLOW_altList_in_block3813 = new BitSet(new long[]{0x100000000000000L});
    public static final BitSet FOLLOW_RPAREN_in_block3817 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_RULE_REF_in_ruleref3871 = new BitSet(new long[]{34359738626L});
    public static final BitSet FOLLOW_ARG_ACTION_in_ruleref3873 = new BitSet(new long[]{0x800000002L});
    public static final BitSet FOLLOW_elementOptions_in_ruleref3876 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_range3932 = new BitSet(new long[]{0x10000000000000L});
    public static final BitSet FOLLOW_RANGE_in_range3937 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_range3943 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_TOKEN_REF_in_terminal3967 = new BitSet(new long[]{0x800000002L});
    public static final BitSet FOLLOW_elementOptions_in_terminal3969 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_terminal3990 = new BitSet(new long[]{0x800000002L});
    public static final BitSet FOLLOW_elementOptions_in_terminal3992 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_LT_in_elementOptions4023 = new BitSet(new long[]{0x200000004000000L, 4L});
    public static final BitSet FOLLOW_elementOption_in_elementOptions4026 = new BitSet(new long[]{0x4010000L});
    public static final BitSet FOLLOW_COMMA_in_elementOptions4029 = new BitSet(new long[]{0x200000000000000L, 4L});
    public static final BitSet FOLLOW_elementOption_in_elementOptions4031 = new BitSet(new long[]{0x4010000L});
    public static final BitSet FOLLOW_GT_in_elementOptions4037 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_qid_in_elementOption4085 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_id_in_elementOption4093 = new BitSet(new long[]{1024L});
    public static final BitSet FOLLOW_ASSIGN_in_elementOption4095 = new BitSet(new long[]{4755801207576985616L, 4L});
    public static final BitSet FOLLOW_optionValue_in_elementOption4098 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_RULE_REF_in_id4129 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_TOKEN_REF_in_id4142 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_id_in_qid4170 = new BitSet(new long[]{0x100002L});
    public static final BitSet FOLLOW_DOT_in_qid4173 = new BitSet(new long[]{0x200000000000000L, 4L});
    public static final BitSet FOLLOW_id_in_qid4175 = new BitSet(new long[]{0x100002L});
    public static final BitSet FOLLOW_alternative_in_alternativeEntry4192 = new BitSet(new long[]{0L});
    public static final BitSet FOLLOW_EOF_in_alternativeEntry4194 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_element_in_elementEntry4203 = new BitSet(new long[]{0L});
    public static final BitSet FOLLOW_EOF_in_elementEntry4205 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_rule_in_ruleEntry4213 = new BitSet(new long[]{0L});
    public static final BitSet FOLLOW_EOF_in_ruleEntry4215 = new BitSet(new long[]{2L});
    public static final BitSet FOLLOW_block_in_blockEntry4223 = new BitSet(new long[]{0L});
    public static final BitSet FOLLOW_EOF_in_blockEntry4225 = new BitSet(new long[]{2L});

    public Parser[] getDelegates() {
        return new Parser[0];
    }

    public ANTLRParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }

    public ANTLRParser(TokenStream input, RecognizerSharedState state) {
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
        return "org\\antlr\\v4\\parse\\ANTLRParser.g";
    }

    public void grammarError(ErrorType etype, Token token, Object ... args) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final grammarSpec_return grammarSpec() throws RecognitionException {
        grammarSpec_return retval = new grammarSpec_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token SEMI3 = null;
        Token EOF9 = null;
        grammarType_return grammarType1 = null;
        id_return id2 = null;
        sync_return sync4 = null;
        prequelConstruct_return prequelConstruct5 = null;
        sync_return sync6 = null;
        rules_return rules7 = null;
        modeSpec_return modeSpec8 = null;
        Object SEMI3_tree = null;
        Object EOF9_tree = null;
        RewriteRuleTokenStream stream_EOF = new RewriteRuleTokenStream(this.adaptor, "token EOF");
        RewriteRuleTokenStream stream_SEMI = new RewriteRuleTokenStream(this.adaptor, "token SEMI");
        RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
        RewriteRuleSubtreeStream stream_sync = new RewriteRuleSubtreeStream(this.adaptor, "rule sync");
        RewriteRuleSubtreeStream stream_modeSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule modeSpec");
        RewriteRuleSubtreeStream stream_prequelConstruct = new RewriteRuleSubtreeStream(this.adaptor, "rule prequelConstruct");
        RewriteRuleSubtreeStream stream_grammarType = new RewriteRuleSubtreeStream(this.adaptor, "rule grammarType");
        RewriteRuleSubtreeStream stream_rules = new RewriteRuleSubtreeStream(this.adaptor, "rule rules");
        try {
            this.pushFollow(FOLLOW_grammarType_in_grammarSpec396);
            grammarType1 = this.grammarType();
            --this.state._fsp;
            stream_grammarType.add(((ParserRuleReturnScope)grammarType1).getTree());
            this.pushFollow(FOLLOW_id_in_grammarSpec398);
            id2 = this.id();
            --this.state._fsp;
            stream_id.add(((ParserRuleReturnScope)id2).getTree());
            SEMI3 = (Token)this.match(this.input, 58, FOLLOW_SEMI_in_grammarSpec400);
            stream_SEMI.add(SEMI3);
            this.pushFollow(FOLLOW_sync_in_grammarSpec438);
            sync4 = this.sync();
            --this.state._fsp;
            stream_sync.add(((ParserRuleReturnScope)sync4).getTree());
            block10: while (true) {
                int alt1 = 2;
                int LA1_0 = this.input.LA(1);
                if (LA1_0 == 11 || LA1_0 == 13 || LA1_0 == 29 || LA1_0 == 42 || LA1_0 == 65) {
                    alt1 = 1;
                }
                switch (alt1) {
                    case 1: {
                        this.pushFollow(FOLLOW_prequelConstruct_in_grammarSpec442);
                        prequelConstruct5 = this.prequelConstruct();
                        --this.state._fsp;
                        stream_prequelConstruct.add(((ParserRuleReturnScope)prequelConstruct5).getTree());
                        this.pushFollow(FOLLOW_sync_in_grammarSpec444);
                        sync6 = this.sync();
                        --this.state._fsp;
                        stream_sync.add(((ParserRuleReturnScope)sync6).getTree());
                        continue block10;
                    }
                }
                break;
            }
            this.pushFollow(FOLLOW_rules_in_grammarSpec469);
            rules7 = this.rules();
            --this.state._fsp;
            stream_rules.add(((ParserRuleReturnScope)rules7).getTree());
            block11: while (true) {
                int alt2 = 2;
                int LA2_0 = this.input.LA(1);
                if (LA2_0 == 36) {
                    alt2 = 1;
                }
                switch (alt2) {
                    case 1: {
                        this.pushFollow(FOLLOW_modeSpec_in_grammarSpec475);
                        modeSpec8 = this.modeSpec();
                        --this.state._fsp;
                        stream_modeSpec.add(((ParserRuleReturnScope)modeSpec8).getTree());
                        continue block11;
                    }
                }
                break;
            }
            EOF9 = (Token)this.match(this.input, -1, FOLLOW_EOF_in_grammarSpec513);
            stream_EOF.add(EOF9);
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
            root_1 = (GrammarAST)this.adaptor.becomeRoot(stream_grammarType.nextNode(), (Object)root_1);
            this.adaptor.addChild(root_1, stream_id.nextTree());
            while (stream_prequelConstruct.hasNext()) {
                this.adaptor.addChild(root_1, stream_prequelConstruct.nextTree());
            }
            stream_prequelConstruct.reset();
            this.adaptor.addChild(root_1, stream_rules.nextTree());
            while (stream_modeSpec.hasNext()) {
                this.adaptor.addChild(root_1, stream_modeSpec.nextTree());
            }
            stream_modeSpec.reset();
            this.adaptor.addChild(root_0, root_1);
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            GrammarAST options = (GrammarAST)retval.tree.getFirstChildWithType(42);
            if (options != null) {
                Grammar.setNodeOptions(retval.tree, options);
            }
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final grammarType_return grammarType() throws RecognitionException {
        grammarType_return retval;
        block18: {
            retval = new grammarType_return();
            retval.start = this.input.LT(1);
            GrammarAST root_0 = null;
            Token t = null;
            Token g = null;
            Token tg = null;
            Object t_tree = null;
            Object g_tree = null;
            Object tg_tree = null;
            RewriteRuleTokenStream stream_TREE_GRAMMAR = new RewriteRuleTokenStream(this.adaptor, "token TREE_GRAMMAR");
            RewriteRuleTokenStream stream_PARSER = new RewriteRuleTokenStream(this.adaptor, "token PARSER");
            RewriteRuleTokenStream stream_LEXER = new RewriteRuleTokenStream(this.adaptor, "token LEXER");
            RewriteRuleTokenStream stream_GRAMMAR = new RewriteRuleTokenStream(this.adaptor, "token GRAMMAR");
            try {
                int alt3 = 4;
                switch (this.input.LA(1)) {
                    case 31: {
                        alt3 = 1;
                        break;
                    }
                    case 44: {
                        alt3 = 2;
                        break;
                    }
                    case 25: {
                        alt3 = 3;
                        break;
                    }
                    case 67: {
                        alt3 = 4;
                        break;
                    }
                    default: {
                        NoViableAltException nvae = new NoViableAltException("", 3, 0, this.input);
                        throw nvae;
                    }
                }
                switch (alt3) {
                    case 1: {
                        t = (Token)this.match(this.input, 31, FOLLOW_LEXER_in_grammarType683);
                        stream_LEXER.add(t);
                        g = (Token)this.match(this.input, 25, FOLLOW_GRAMMAR_in_grammarType687);
                        stream_GRAMMAR.add(g);
                        retval.tree = root_0;
                        RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                        root_0 = (GrammarAST)this.adaptor.nil();
                        this.adaptor.addChild(root_0, new GrammarRootAST(25, g, "LEXER_GRAMMAR", this.getTokenStream()));
                        retval.tree = root_0;
                        break;
                    }
                    case 2: {
                        t = (Token)this.match(this.input, 44, FOLLOW_PARSER_in_grammarType710);
                        stream_PARSER.add(t);
                        g = (Token)this.match(this.input, 25, FOLLOW_GRAMMAR_in_grammarType714);
                        stream_GRAMMAR.add(g);
                        retval.tree = root_0;
                        RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                        root_0 = (GrammarAST)this.adaptor.nil();
                        this.adaptor.addChild(root_0, new GrammarRootAST(25, g, "PARSER_GRAMMAR", this.getTokenStream()));
                        retval.tree = root_0;
                        break;
                    }
                    case 3: {
                        g = (Token)this.match(this.input, 25, FOLLOW_GRAMMAR_in_grammarType735);
                        stream_GRAMMAR.add(g);
                        retval.tree = root_0;
                        RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                        root_0 = (GrammarAST)this.adaptor.nil();
                        this.adaptor.addChild(root_0, new GrammarRootAST(25, g, "COMBINED_GRAMMAR", this.getTokenStream()));
                        retval.tree = root_0;
                        break;
                    }
                    case 4: {
                        tg = (Token)this.match(this.input, 67, FOLLOW_TREE_GRAMMAR_in_grammarType762);
                        stream_TREE_GRAMMAR.add(tg);
                    }
                }
                retval.stop = this.input.LT(-1);
                retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                if (tg != null) {
                    throw new v3TreeGrammarException(tg);
                }
                if (t != null) {
                    ((GrammarRootAST)retval.tree).grammarType = t != null ? t.getType() : 0;
                    break block18;
                }
                ((GrammarRootAST)retval.tree).grammarType = 81;
            }
            catch (RecognitionException re) {
                this.reportError(re);
                this.recover(this.input, re);
                retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
            }
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final prequelConstruct_return prequelConstruct() throws RecognitionException {
        prequelConstruct_return retval = new prequelConstruct_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        optionsSpec_return optionsSpec10 = null;
        delegateGrammars_return delegateGrammars11 = null;
        tokensSpec_return tokensSpec12 = null;
        channelsSpec_return channelsSpec13 = null;
        action_return action14 = null;
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
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_optionsSpec_in_prequelConstruct788);
                    optionsSpec10 = this.optionsSpec();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)optionsSpec10).getTree());
                    break;
                }
                case 2: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_delegateGrammars_in_prequelConstruct811);
                    delegateGrammars11 = this.delegateGrammars();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)delegateGrammars11).getTree());
                    break;
                }
                case 3: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_tokensSpec_in_prequelConstruct855);
                    tokensSpec12 = this.tokensSpec();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)tokensSpec12).getTree());
                    break;
                }
                case 4: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_channelsSpec_in_prequelConstruct865);
                    channelsSpec13 = this.channelsSpec();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)channelsSpec13).getTree());
                    break;
                }
                case 5: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_action_in_prequelConstruct902);
                    action14 = this.action();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)action14).getTree());
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final optionsSpec_return optionsSpec() throws RecognitionException {
        optionsSpec_return retval = new optionsSpec_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token OPTIONS15 = null;
        Token SEMI17 = null;
        Token RBRACE18 = null;
        option_return option16 = null;
        Object OPTIONS15_tree = null;
        Object SEMI17_tree = null;
        Object RBRACE18_tree = null;
        RewriteRuleTokenStream stream_RBRACE = new RewriteRuleTokenStream(this.adaptor, "token RBRACE");
        RewriteRuleTokenStream stream_SEMI = new RewriteRuleTokenStream(this.adaptor, "token SEMI");
        RewriteRuleTokenStream stream_OPTIONS = new RewriteRuleTokenStream(this.adaptor, "token OPTIONS");
        RewriteRuleSubtreeStream stream_option = new RewriteRuleSubtreeStream(this.adaptor, "rule option");
        try {
            OPTIONS15 = (Token)this.match(this.input, 42, FOLLOW_OPTIONS_in_optionsSpec917);
            stream_OPTIONS.add(OPTIONS15);
            block7: while (true) {
                int alt5 = 2;
                int LA5_0 = this.input.LA(1);
                if (LA5_0 == 57 || LA5_0 == 66) {
                    alt5 = 1;
                }
                switch (alt5) {
                    case 1: {
                        this.pushFollow(FOLLOW_option_in_optionsSpec920);
                        option16 = this.option();
                        --this.state._fsp;
                        stream_option.add(((ParserRuleReturnScope)option16).getTree());
                        SEMI17 = (Token)this.match(this.input, 58, FOLLOW_SEMI_in_optionsSpec922);
                        stream_SEMI.add(SEMI17);
                        continue block7;
                    }
                }
                break;
            }
            RBRACE18 = (Token)this.match(this.input, 54, FOLLOW_RBRACE_in_optionsSpec926);
            stream_RBRACE.add(RBRACE18);
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
            root_1 = (GrammarAST)this.adaptor.becomeRoot((GrammarAST)this.adaptor.create(42, OPTIONS15, "OPTIONS"), (Object)root_1);
            while (stream_option.hasNext()) {
                this.adaptor.addChild(root_1, stream_option.nextTree());
            }
            stream_option.reset();
            this.adaptor.addChild(root_0, root_1);
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final option_return option() throws RecognitionException {
        option_return retval = new option_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token ASSIGN20 = null;
        id_return id19 = null;
        optionValue_return optionValue21 = null;
        GrammarAST ASSIGN20_tree = null;
        try {
            root_0 = (GrammarAST)this.adaptor.nil();
            this.pushFollow(FOLLOW_id_in_option955);
            id19 = this.id();
            --this.state._fsp;
            this.adaptor.addChild(root_0, ((ParserRuleReturnScope)id19).getTree());
            ASSIGN20 = (Token)this.match(this.input, 10, FOLLOW_ASSIGN_in_option957);
            ASSIGN20_tree = (GrammarAST)this.adaptor.create(ASSIGN20);
            root_0 = (GrammarAST)this.adaptor.becomeRoot(ASSIGN20_tree, (Object)root_0);
            this.pushFollow(FOLLOW_optionValue_in_option960);
            optionValue21 = this.optionValue();
            --this.state._fsp;
            this.adaptor.addChild(root_0, ((ParserRuleReturnScope)optionValue21).getTree());
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final optionValue_return optionValue() throws RecognitionException {
        optionValue_return retval = new optionValue_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token STRING_LITERAL23 = null;
        Token ACTION24 = null;
        Token INT25 = null;
        qid_return qid22 = null;
        GrammarAST STRING_LITERAL23_tree = null;
        ActionAST ACTION24_tree = null;
        GrammarAST INT25_tree = null;
        try {
            int alt6 = 4;
            switch (this.input.LA(1)) {
                case 57: 
                case 66: {
                    alt6 = 1;
                    break;
                }
                case 62: {
                    alt6 = 2;
                    break;
                }
                case 4: {
                    alt6 = 3;
                    break;
                }
                case 30: {
                    alt6 = 4;
                    break;
                }
                default: {
                    NoViableAltException nvae = new NoViableAltException("", 6, 0, this.input);
                    throw nvae;
                }
            }
            switch (alt6) {
                case 1: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_qid_in_optionValue1003);
                    qid22 = this.qid();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)qid22).getTree());
                    break;
                }
                case 2: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    STRING_LITERAL23 = (Token)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_optionValue1011);
                    STRING_LITERAL23_tree = (GrammarAST)this.adaptor.create(STRING_LITERAL23);
                    this.adaptor.addChild(root_0, STRING_LITERAL23_tree);
                    break;
                }
                case 3: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    ACTION24 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_optionValue1016);
                    ACTION24_tree = new ActionAST(ACTION24);
                    this.adaptor.addChild(root_0, ACTION24_tree);
                    break;
                }
                case 4: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    INT25 = (Token)this.match(this.input, 30, FOLLOW_INT_in_optionValue1027);
                    INT25_tree = (GrammarAST)this.adaptor.create(INT25);
                    this.adaptor.addChild(root_0, INT25_tree);
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final delegateGrammars_return delegateGrammars() throws RecognitionException {
        delegateGrammars_return retval = new delegateGrammars_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token IMPORT26 = null;
        Token COMMA28 = null;
        Token SEMI30 = null;
        delegateGrammar_return delegateGrammar27 = null;
        delegateGrammar_return delegateGrammar29 = null;
        Object IMPORT26_tree = null;
        Object COMMA28_tree = null;
        Object SEMI30_tree = null;
        RewriteRuleTokenStream stream_IMPORT = new RewriteRuleTokenStream(this.adaptor, "token IMPORT");
        RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream stream_SEMI = new RewriteRuleTokenStream(this.adaptor, "token SEMI");
        RewriteRuleSubtreeStream stream_delegateGrammar = new RewriteRuleSubtreeStream(this.adaptor, "rule delegateGrammar");
        try {
            IMPORT26 = (Token)this.match(this.input, 29, FOLLOW_IMPORT_in_delegateGrammars1043);
            stream_IMPORT.add(IMPORT26);
            this.pushFollow(FOLLOW_delegateGrammar_in_delegateGrammars1045);
            delegateGrammar27 = this.delegateGrammar();
            --this.state._fsp;
            stream_delegateGrammar.add(((ParserRuleReturnScope)delegateGrammar27).getTree());
            block7: while (true) {
                int alt7 = 2;
                int LA7_0 = this.input.LA(1);
                if (LA7_0 == 16) {
                    alt7 = 1;
                }
                switch (alt7) {
                    case 1: {
                        COMMA28 = (Token)this.match(this.input, 16, FOLLOW_COMMA_in_delegateGrammars1048);
                        stream_COMMA.add(COMMA28);
                        this.pushFollow(FOLLOW_delegateGrammar_in_delegateGrammars1050);
                        delegateGrammar29 = this.delegateGrammar();
                        --this.state._fsp;
                        stream_delegateGrammar.add(((ParserRuleReturnScope)delegateGrammar29).getTree());
                        continue block7;
                    }
                }
                break;
            }
            SEMI30 = (Token)this.match(this.input, 58, FOLLOW_SEMI_in_delegateGrammars1054);
            stream_SEMI.add(SEMI30);
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
            root_1 = (GrammarAST)this.adaptor.becomeRoot(stream_IMPORT.nextNode(), (Object)root_1);
            if (!stream_delegateGrammar.hasNext()) {
                throw new RewriteEarlyExitException();
            }
            while (stream_delegateGrammar.hasNext()) {
                this.adaptor.addChild(root_1, stream_delegateGrammar.nextTree());
            }
            stream_delegateGrammar.reset();
            this.adaptor.addChild(root_0, root_1);
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final delegateGrammar_return delegateGrammar() throws RecognitionException {
        delegateGrammar_return retval = new delegateGrammar_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token ASSIGN32 = null;
        id_return id31 = null;
        id_return id33 = null;
        id_return id34 = null;
        GrammarAST ASSIGN32_tree = null;
        try {
            int alt8;
            block21: {
                int LA8_0;
                block22: {
                    alt8 = 2;
                    LA8_0 = this.input.LA(1);
                    if (LA8_0 != 57) break block22;
                    int LA8_1 = this.input.LA(2);
                    if (LA8_1 == 10) {
                        alt8 = 1;
                        break block21;
                    } else if (LA8_1 == 16 || LA8_1 == 58) {
                        alt8 = 2;
                        break block21;
                    } else {
                        int nvaeMark = this.input.mark();
                        try {
                            this.input.consume();
                            NoViableAltException nvae = new NoViableAltException("", 8, 1, this.input);
                            throw nvae;
                        }
                        catch (Throwable throwable) {
                            this.input.rewind(nvaeMark);
                            throw throwable;
                        }
                    }
                }
                if (LA8_0 != 66) {
                    NoViableAltException nvae = new NoViableAltException("", 8, 0, this.input);
                    throw nvae;
                }
                int LA8_2 = this.input.LA(2);
                if (LA8_2 == 10) {
                    alt8 = 1;
                } else if (LA8_2 == 16 || LA8_2 == 58) {
                    alt8 = 2;
                } else {
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 8, 2, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
            }
            switch (alt8) {
                case 1: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_id_in_delegateGrammar1081);
                    id31 = this.id();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)id31).getTree());
                    ASSIGN32 = (Token)this.match(this.input, 10, FOLLOW_ASSIGN_in_delegateGrammar1083);
                    ASSIGN32_tree = (GrammarAST)this.adaptor.create(ASSIGN32);
                    root_0 = (GrammarAST)this.adaptor.becomeRoot(ASSIGN32_tree, (Object)root_0);
                    this.pushFollow(FOLLOW_id_in_delegateGrammar1086);
                    id33 = this.id();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)id33).getTree());
                    break;
                }
                case 2: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_id_in_delegateGrammar1096);
                    id34 = this.id();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)id34).getTree());
                    break;
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            return retval;
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
            return retval;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final tokensSpec_return tokensSpec() throws RecognitionException {
        tokensSpec_return retval = new tokensSpec_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token TOKENS_SPEC35 = null;
        Token COMMA37 = null;
        Token COMMA39 = null;
        Token RBRACE40 = null;
        Token TOKENS_SPEC41 = null;
        Token RBRACE42 = null;
        Token TOKENS_SPEC43 = null;
        Token RBRACE45 = null;
        id_return id36 = null;
        id_return id38 = null;
        v3tokenSpec_return v3tokenSpec44 = null;
        Object TOKENS_SPEC35_tree = null;
        Object COMMA37_tree = null;
        Object COMMA39_tree = null;
        Object RBRACE40_tree = null;
        Object TOKENS_SPEC41_tree = null;
        Object RBRACE42_tree = null;
        GrammarAST TOKENS_SPEC43_tree = null;
        Object RBRACE45_tree = null;
        RewriteRuleTokenStream stream_RBRACE = new RewriteRuleTokenStream(this.adaptor, "token RBRACE");
        RewriteRuleTokenStream stream_TOKENS_SPEC = new RewriteRuleTokenStream(this.adaptor, "token TOKENS_SPEC");
        RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
        try {
            int alt12 = 3;
            int LA12_0 = this.input.LA(1);
            if (LA12_0 == 65) {
                switch (this.input.LA(2)) {
                    case 54: {
                        alt12 = 2;
                        break;
                    }
                    case 57: {
                        int LA12_3 = this.input.LA(3);
                        if (LA12_3 == 16 || LA12_3 == 54) {
                            alt12 = 1;
                            break;
                        }
                        if (LA12_3 == 10 || LA12_3 == 58) {
                            alt12 = 3;
                            break;
                        }
                        int nvaeMark = this.input.mark();
                        try {
                            for (int nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                                this.input.consume();
                            }
                            NoViableAltException nvae = new NoViableAltException("", 12, 3, this.input);
                            throw nvae;
                        }
                        catch (Throwable throwable) {
                            this.input.rewind(nvaeMark);
                            throw throwable;
                        }
                    }
                    case 66: {
                        int LA12_4 = this.input.LA(3);
                        if (LA12_4 == 16 || LA12_4 == 54) {
                            alt12 = 1;
                            break;
                        }
                        if (LA12_4 == 10 || LA12_4 == 58) {
                            alt12 = 3;
                            break;
                        }
                        int nvaeMark = this.input.mark();
                        try {
                            for (int nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                                this.input.consume();
                            }
                            NoViableAltException nvae = new NoViableAltException("", 12, 4, this.input);
                            throw nvae;
                        }
                        catch (Throwable throwable) {
                            this.input.rewind(nvaeMark);
                            throw throwable;
                        }
                    }
                    default: {
                        int nvaeMark = this.input.mark();
                        try {
                            this.input.consume();
                            NoViableAltException nvae = new NoViableAltException("", 12, 1, this.input);
                            throw nvae;
                        }
                        catch (Throwable throwable) {
                            this.input.rewind(nvaeMark);
                            throw throwable;
                        }
                    }
                }
            } else {
                NoViableAltException nvae = new NoViableAltException("", 12, 0, this.input);
                throw nvae;
            }
            switch (alt12) {
                case 1: {
                    TOKENS_SPEC35 = (Token)this.match(this.input, 65, FOLLOW_TOKENS_SPEC_in_tokensSpec1110);
                    stream_TOKENS_SPEC.add(TOKENS_SPEC35);
                    this.pushFollow(FOLLOW_id_in_tokensSpec1112);
                    id36 = this.id();
                    --this.state._fsp;
                    stream_id.add(((ParserRuleReturnScope)id36).getTree());
                    block31: while (true) {
                        int LA9_1;
                        int alt9 = 2;
                        int LA9_0 = this.input.LA(1);
                        if (LA9_0 == 16 && ((LA9_1 = this.input.LA(2)) == 57 || LA9_1 == 66)) {
                            alt9 = 1;
                        }
                        switch (alt9) {
                            case 1: {
                                COMMA37 = (Token)this.match(this.input, 16, FOLLOW_COMMA_in_tokensSpec1115);
                                stream_COMMA.add(COMMA37);
                                this.pushFollow(FOLLOW_id_in_tokensSpec1117);
                                id38 = this.id();
                                --this.state._fsp;
                                stream_id.add(((ParserRuleReturnScope)id38).getTree());
                                continue block31;
                            }
                        }
                        break;
                    }
                    int alt10 = 2;
                    int LA10_0 = this.input.LA(1);
                    if (LA10_0 == 16) {
                        alt10 = 1;
                    }
                    switch (alt10) {
                        case 1: {
                            COMMA39 = (Token)this.match(this.input, 16, FOLLOW_COMMA_in_tokensSpec1121);
                            stream_COMMA.add(COMMA39);
                        }
                    }
                    RBRACE40 = (Token)this.match(this.input, 54, FOLLOW_RBRACE_in_tokensSpec1124);
                    stream_RBRACE.add(RBRACE40);
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                    root_1 = (GrammarAST)this.adaptor.becomeRoot(stream_TOKENS_SPEC.nextNode(), (Object)root_1);
                    if (!stream_id.hasNext()) {
                        throw new RewriteEarlyExitException();
                    }
                    while (stream_id.hasNext()) {
                        this.adaptor.addChild(root_1, stream_id.nextTree());
                    }
                    stream_id.reset();
                    this.adaptor.addChild(root_0, root_1);
                    retval.tree = root_0;
                    break;
                }
                case 2: {
                    TOKENS_SPEC41 = (Token)this.match(this.input, 65, FOLLOW_TOKENS_SPEC_in_tokensSpec1141);
                    stream_TOKENS_SPEC.add(TOKENS_SPEC41);
                    RBRACE42 = (Token)this.match(this.input, 54, FOLLOW_RBRACE_in_tokensSpec1143);
                    stream_RBRACE.add(RBRACE42);
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    retval.tree = root_0 = null;
                    break;
                }
                case 3: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    TOKENS_SPEC43 = (Token)this.match(this.input, 65, FOLLOW_TOKENS_SPEC_in_tokensSpec1153);
                    TOKENS_SPEC43_tree = (GrammarAST)this.adaptor.create(TOKENS_SPEC43);
                    root_0 = (GrammarAST)this.adaptor.becomeRoot(TOKENS_SPEC43_tree, (Object)root_0);
                    int cnt11 = 0;
                    block33: while (true) {
                        int alt11 = 2;
                        int LA11_0 = this.input.LA(1);
                        if (LA11_0 == 57 || LA11_0 == 66) {
                            alt11 = 1;
                        }
                        switch (alt11) {
                            case 1: {
                                this.pushFollow(FOLLOW_v3tokenSpec_in_tokensSpec1156);
                                v3tokenSpec44 = this.v3tokenSpec();
                                --this.state._fsp;
                                this.adaptor.addChild(root_0, ((ParserRuleReturnScope)v3tokenSpec44).getTree());
                                break;
                            }
                            default: {
                                if (cnt11 >= 1) break block33;
                                EarlyExitException eee = new EarlyExitException(11, this.input);
                                throw eee;
                            }
                        }
                        ++cnt11;
                    }
                    RBRACE45 = (Token)this.match(this.input, 54, FOLLOW_RBRACE_in_tokensSpec1159);
                    this.grammarError(ErrorType.V3_TOKENS_SYNTAX, TOKENS_SPEC43, new Object[0]);
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final v3tokenSpec_return v3tokenSpec() throws RecognitionException {
        v3tokenSpec_return retval = new v3tokenSpec_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token lit = null;
        Token ASSIGN47 = null;
        Token SEMI48 = null;
        id_return id46 = null;
        Object lit_tree = null;
        Object ASSIGN47_tree = null;
        Object SEMI48_tree = null;
        RewriteRuleTokenStream stream_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL");
        RewriteRuleTokenStream stream_SEMI = new RewriteRuleTokenStream(this.adaptor, "token SEMI");
        RewriteRuleTokenStream stream_ASSIGN = new RewriteRuleTokenStream(this.adaptor, "token ASSIGN");
        RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
        try {
            this.pushFollow(FOLLOW_id_in_v3tokenSpec1179);
            id46 = this.id();
            --this.state._fsp;
            stream_id.add(((ParserRuleReturnScope)id46).getTree());
            int alt13 = 2;
            int LA13_0 = this.input.LA(1);
            if (LA13_0 == 10) {
                alt13 = 1;
            } else if (LA13_0 == 58) {
                alt13 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 13, 0, this.input);
                throw nvae;
            }
            switch (alt13) {
                case 1: {
                    ASSIGN47 = (Token)this.match(this.input, 10, FOLLOW_ASSIGN_in_v3tokenSpec1185);
                    stream_ASSIGN.add(ASSIGN47);
                    lit = (Token)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_v3tokenSpec1189);
                    stream_STRING_LITERAL.add(lit);
                    this.grammarError(ErrorType.V3_ASSIGN_IN_TOKENS, id46 != null ? id46.start : null, id46 != null ? this.input.toString(id46.start, id46.stop) : null, lit.getText());
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.adaptor.addChild(root_0, stream_id.nextTree());
                    retval.tree = root_0;
                    break;
                }
                case 2: {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.adaptor.addChild(root_0, stream_id.nextTree());
                    retval.tree = root_0;
                }
            }
            SEMI48 = (Token)this.match(this.input, 58, FOLLOW_SEMI_in_v3tokenSpec1250);
            stream_SEMI.add(SEMI48);
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final channelsSpec_return channelsSpec() throws RecognitionException {
        channelsSpec_return retval = new channelsSpec_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token CHANNELS49 = null;
        Token COMMA51 = null;
        Token COMMA53 = null;
        Token RBRACE54 = null;
        id_return id50 = null;
        id_return id52 = null;
        GrammarAST CHANNELS49_tree = null;
        Object COMMA51_tree = null;
        GrammarAST COMMA53_tree = null;
        Object RBRACE54_tree = null;
        try {
            root_0 = (GrammarAST)this.adaptor.nil();
            CHANNELS49 = (Token)this.match(this.input, 13, FOLLOW_CHANNELS_in_channelsSpec1261);
            CHANNELS49_tree = (GrammarAST)this.adaptor.create(CHANNELS49);
            root_0 = (GrammarAST)this.adaptor.becomeRoot(CHANNELS49_tree, (Object)root_0);
            int alt16 = 2;
            int LA16_0 = this.input.LA(1);
            if (LA16_0 == 57 || LA16_0 == 66) {
                alt16 = 1;
            }
            switch (alt16) {
                case 1: {
                    this.pushFollow(FOLLOW_id_in_channelsSpec1265);
                    id50 = this.id();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)id50).getTree());
                    block13: while (true) {
                        int LA14_1;
                        int alt14 = 2;
                        int LA14_0 = this.input.LA(1);
                        if (LA14_0 == 16 && ((LA14_1 = this.input.LA(2)) == 57 || LA14_1 == 66)) {
                            alt14 = 1;
                        }
                        switch (alt14) {
                            case 1: {
                                COMMA51 = (Token)this.match(this.input, 16, FOLLOW_COMMA_in_channelsSpec1268);
                                this.pushFollow(FOLLOW_id_in_channelsSpec1271);
                                id52 = this.id();
                                --this.state._fsp;
                                this.adaptor.addChild(root_0, ((ParserRuleReturnScope)id52).getTree());
                                continue block13;
                            }
                        }
                        break;
                    }
                    int alt15 = 2;
                    int LA15_0 = this.input.LA(1);
                    if (LA15_0 == 16) {
                        alt15 = 1;
                    }
                    switch (alt15) {
                        case 1: {
                            COMMA53 = (Token)this.match(this.input, 16, FOLLOW_COMMA_in_channelsSpec1275);
                            COMMA53_tree = (GrammarAST)this.adaptor.create(COMMA53);
                            this.adaptor.addChild(root_0, COMMA53_tree);
                        }
                    }
                }
            }
            RBRACE54 = (Token)this.match(this.input, 54, FOLLOW_RBRACE_in_channelsSpec1280);
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final action_return action() throws RecognitionException {
        action_return retval = new action_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token AT55 = null;
        Token COLONCOLON57 = null;
        Token ACTION59 = null;
        actionScopeName_return actionScopeName56 = null;
        id_return id58 = null;
        Object AT55_tree = null;
        Object COLONCOLON57_tree = null;
        Object ACTION59_tree = null;
        RewriteRuleTokenStream stream_AT = new RewriteRuleTokenStream(this.adaptor, "token AT");
        RewriteRuleTokenStream stream_COLONCOLON = new RewriteRuleTokenStream(this.adaptor, "token COLONCOLON");
        RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
        RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
        RewriteRuleSubtreeStream stream_actionScopeName = new RewriteRuleSubtreeStream(this.adaptor, "rule actionScopeName");
        try {
            AT55 = (Token)this.match(this.input, 11, FOLLOW_AT_in_action1297);
            stream_AT.add(AT55);
            int alt17 = 2;
            switch (this.input.LA(1)) {
                case 57: {
                    int LA17_1 = this.input.LA(2);
                    if (LA17_1 != 15) break;
                    alt17 = 1;
                    break;
                }
                case 66: {
                    int LA17_2 = this.input.LA(2);
                    if (LA17_2 != 15) break;
                    alt17 = 1;
                    break;
                }
                case 31: 
                case 44: {
                    alt17 = 1;
                }
            }
            switch (alt17) {
                case 1: {
                    this.pushFollow(FOLLOW_actionScopeName_in_action1300);
                    actionScopeName56 = this.actionScopeName();
                    --this.state._fsp;
                    stream_actionScopeName.add(((ParserRuleReturnScope)actionScopeName56).getTree());
                    COLONCOLON57 = (Token)this.match(this.input, 15, FOLLOW_COLONCOLON_in_action1302);
                    stream_COLONCOLON.add(COLONCOLON57);
                }
            }
            this.pushFollow(FOLLOW_id_in_action1306);
            id58 = this.id();
            --this.state._fsp;
            stream_id.add(((ParserRuleReturnScope)id58).getTree());
            ACTION59 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_action1308);
            stream_ACTION.add(ACTION59);
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
            root_1 = (GrammarAST)this.adaptor.becomeRoot(stream_AT.nextNode(), (Object)root_1);
            if (stream_actionScopeName.hasNext()) {
                this.adaptor.addChild(root_1, stream_actionScopeName.nextTree());
            }
            stream_actionScopeName.reset();
            this.adaptor.addChild(root_1, stream_id.nextTree());
            this.adaptor.addChild(root_1, new ActionAST(stream_ACTION.nextToken()));
            this.adaptor.addChild(root_0, root_1);
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final actionScopeName_return actionScopeName() throws RecognitionException {
        actionScopeName_return retval = new actionScopeName_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token LEXER61 = null;
        Token PARSER62 = null;
        id_return id60 = null;
        Object LEXER61_tree = null;
        Object PARSER62_tree = null;
        RewriteRuleTokenStream stream_PARSER = new RewriteRuleTokenStream(this.adaptor, "token PARSER");
        RewriteRuleTokenStream stream_LEXER = new RewriteRuleTokenStream(this.adaptor, "token LEXER");
        try {
            int alt18 = 3;
            switch (this.input.LA(1)) {
                case 57: 
                case 66: {
                    alt18 = 1;
                    break;
                }
                case 31: {
                    alt18 = 2;
                    break;
                }
                case 44: {
                    alt18 = 3;
                    break;
                }
                default: {
                    NoViableAltException nvae = new NoViableAltException("", 18, 0, this.input);
                    throw nvae;
                }
            }
            switch (alt18) {
                case 1: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_id_in_actionScopeName1337);
                    id60 = this.id();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)id60).getTree());
                    break;
                }
                case 2: {
                    LEXER61 = (Token)this.match(this.input, 31, FOLLOW_LEXER_in_actionScopeName1342);
                    stream_LEXER.add(LEXER61);
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(28, LEXER61));
                    retval.tree = root_0;
                    break;
                }
                case 3: {
                    PARSER62 = (Token)this.match(this.input, 44, FOLLOW_PARSER_in_actionScopeName1357);
                    stream_PARSER.add(PARSER62);
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(28, PARSER62));
                    retval.tree = root_0;
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final modeSpec_return modeSpec() throws RecognitionException {
        modeSpec_return retval = new modeSpec_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token MODE63 = null;
        Token SEMI65 = null;
        id_return id64 = null;
        sync_return sync66 = null;
        lexerRule_return lexerRule67 = null;
        sync_return sync68 = null;
        Object MODE63_tree = null;
        Object SEMI65_tree = null;
        RewriteRuleTokenStream stream_SEMI = new RewriteRuleTokenStream(this.adaptor, "token SEMI");
        RewriteRuleTokenStream stream_MODE = new RewriteRuleTokenStream(this.adaptor, "token MODE");
        RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
        RewriteRuleSubtreeStream stream_sync = new RewriteRuleSubtreeStream(this.adaptor, "rule sync");
        RewriteRuleSubtreeStream stream_lexerRule = new RewriteRuleSubtreeStream(this.adaptor, "rule lexerRule");
        try {
            MODE63 = (Token)this.match(this.input, 36, FOLLOW_MODE_in_modeSpec1376);
            stream_MODE.add(MODE63);
            this.pushFollow(FOLLOW_id_in_modeSpec1378);
            id64 = this.id();
            --this.state._fsp;
            stream_id.add(((ParserRuleReturnScope)id64).getTree());
            SEMI65 = (Token)this.match(this.input, 58, FOLLOW_SEMI_in_modeSpec1380);
            stream_SEMI.add(SEMI65);
            this.pushFollow(FOLLOW_sync_in_modeSpec1382);
            sync66 = this.sync();
            --this.state._fsp;
            stream_sync.add(((ParserRuleReturnScope)sync66).getTree());
            block7: while (true) {
                int alt19 = 2;
                int LA19_0 = this.input.LA(1);
                if (LA19_0 == 24 || LA19_0 == 66) {
                    alt19 = 1;
                }
                switch (alt19) {
                    case 1: {
                        this.pushFollow(FOLLOW_lexerRule_in_modeSpec1385);
                        lexerRule67 = this.lexerRule();
                        --this.state._fsp;
                        stream_lexerRule.add(((ParserRuleReturnScope)lexerRule67).getTree());
                        this.pushFollow(FOLLOW_sync_in_modeSpec1387);
                        sync68 = this.sync();
                        --this.state._fsp;
                        stream_sync.add(((ParserRuleReturnScope)sync68).getTree());
                        continue block7;
                    }
                }
                break;
            }
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
            root_1 = (GrammarAST)this.adaptor.becomeRoot(stream_MODE.nextNode(), (Object)root_1);
            this.adaptor.addChild(root_1, stream_id.nextTree());
            while (stream_lexerRule.hasNext()) {
                this.adaptor.addChild(root_1, stream_lexerRule.nextTree());
            }
            stream_lexerRule.reset();
            this.adaptor.addChild(root_0, root_1);
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final rules_return rules() throws RecognitionException {
        rules_return retval = new rules_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        sync_return sync69 = null;
        rule_return rule70 = null;
        sync_return sync71 = null;
        RewriteRuleSubtreeStream stream_sync = new RewriteRuleSubtreeStream(this.adaptor, "rule sync");
        RewriteRuleSubtreeStream stream_rule = new RewriteRuleSubtreeStream(this.adaptor, "rule rule");
        try {
            this.pushFollow(FOLLOW_sync_in_rules1418);
            sync69 = this.sync();
            --this.state._fsp;
            stream_sync.add(((ParserRuleReturnScope)sync69).getTree());
            block7: while (true) {
                int alt20 = 2;
                int LA20_0 = this.input.LA(1);
                if (LA20_0 == 24 || LA20_0 == 57 || LA20_0 == 66) {
                    alt20 = 1;
                }
                switch (alt20) {
                    case 1: {
                        this.pushFollow(FOLLOW_rule_in_rules1421);
                        rule70 = this.rule();
                        --this.state._fsp;
                        stream_rule.add(((ParserRuleReturnScope)rule70).getTree());
                        this.pushFollow(FOLLOW_sync_in_rules1423);
                        sync71 = this.sync();
                        --this.state._fsp;
                        stream_sync.add(((ParserRuleReturnScope)sync71).getTree());
                        continue block7;
                    }
                }
                break;
            }
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
            root_1 = (GrammarAST)this.adaptor.becomeRoot((GrammarAST)this.adaptor.create(97, "RULES"), (Object)root_1);
            while (stream_rule.hasNext()) {
                this.adaptor.addChild(root_1, stream_rule.nextTree());
            }
            stream_rule.reset();
            this.adaptor.addChild(root_0, root_1);
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final sync_return sync() throws RecognitionException {
        sync_return retval = new sync_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        BitSet followSet = this.computeErrorRecoverySet();
        if (this.input.LA(1) != -1 && !followSet.member(this.input.LA(1))) {
            this.reportError(new NoViableAltException("", 0, 0, this.input));
            this.beginResync();
            this.consumeUntil((IntStream)this.input, followSet);
            this.endResync();
        }
        root_0 = (GrammarAST)this.adaptor.nil();
        retval.stop = this.input.LT(-1);
        retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
        this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final rule_return rule() throws RecognitionException {
        rule_return retval = new rule_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        parserRule_return parserRule72 = null;
        lexerRule_return lexerRule73 = null;
        try {
            int alt21 = 2;
            int LA21_0 = this.input.LA(1);
            if (LA21_0 == 57) {
                alt21 = 1;
            } else if (LA21_0 == 24 || LA21_0 == 66) {
                alt21 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 21, 0, this.input);
                throw nvae;
            }
            switch (alt21) {
                case 1: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_parserRule_in_rule1485);
                    parserRule72 = this.parserRule();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)parserRule72).getTree());
                    break;
                }
                case 2: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_lexerRule_in_rule1490);
                    lexerRule73 = this.lexerRule();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)lexerRule73).getTree());
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final parserRule_return parserRule() throws RecognitionException {
        parserRule_return retval = new parserRule_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token RULE_REF74 = null;
        Token ARG_ACTION75 = null;
        Token COLON80 = null;
        Token SEMI82 = null;
        ruleReturns_return ruleReturns76 = null;
        throwsSpec_return throwsSpec77 = null;
        localsSpec_return localsSpec78 = null;
        rulePrequels_return rulePrequels79 = null;
        ruleBlock_return ruleBlock81 = null;
        exceptionGroup_return exceptionGroup83 = null;
        Object RULE_REF74_tree = null;
        Object ARG_ACTION75_tree = null;
        Object COLON80_tree = null;
        Object SEMI82_tree = null;
        RewriteRuleTokenStream stream_COLON = new RewriteRuleTokenStream(this.adaptor, "token COLON");
        RewriteRuleTokenStream stream_SEMI = new RewriteRuleTokenStream(this.adaptor, "token SEMI");
        RewriteRuleTokenStream stream_RULE_REF = new RewriteRuleTokenStream(this.adaptor, "token RULE_REF");
        RewriteRuleTokenStream stream_ARG_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ARG_ACTION");
        RewriteRuleSubtreeStream stream_rulePrequels = new RewriteRuleSubtreeStream(this.adaptor, "rule rulePrequels");
        RewriteRuleSubtreeStream stream_exceptionGroup = new RewriteRuleSubtreeStream(this.adaptor, "rule exceptionGroup");
        RewriteRuleSubtreeStream stream_ruleReturns = new RewriteRuleSubtreeStream(this.adaptor, "rule ruleReturns");
        RewriteRuleSubtreeStream stream_throwsSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule throwsSpec");
        RewriteRuleSubtreeStream stream_ruleBlock = new RewriteRuleSubtreeStream(this.adaptor, "rule ruleBlock");
        RewriteRuleSubtreeStream stream_localsSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule localsSpec");
        this.paraphrases.push("matching a rule");
        try {
            RULE_REF74 = (Token)this.match(this.input, 57, FOLLOW_RULE_REF_in_parserRule1539);
            stream_RULE_REF.add(RULE_REF74);
            int alt22 = 2;
            int LA22_0 = this.input.LA(1);
            if (LA22_0 == 8) {
                alt22 = 1;
            }
            switch (alt22) {
                case 1: {
                    ARG_ACTION75 = (Token)this.match(this.input, 8, FOLLOW_ARG_ACTION_in_parserRule1569);
                    stream_ARG_ACTION.add(ARG_ACTION75);
                }
            }
            int alt23 = 2;
            int LA23_0 = this.input.LA(1);
            if (LA23_0 == 55) {
                alt23 = 1;
            }
            switch (alt23) {
                case 1: {
                    this.pushFollow(FOLLOW_ruleReturns_in_parserRule1576);
                    ruleReturns76 = this.ruleReturns();
                    --this.state._fsp;
                    stream_ruleReturns.add(((ParserRuleReturnScope)ruleReturns76).getTree());
                }
            }
            int alt24 = 2;
            int LA24_0 = this.input.LA(1);
            if (LA24_0 == 64) {
                alt24 = 1;
            }
            switch (alt24) {
                case 1: {
                    this.pushFollow(FOLLOW_throwsSpec_in_parserRule1583);
                    throwsSpec77 = this.throwsSpec();
                    --this.state._fsp;
                    stream_throwsSpec.add(((ParserRuleReturnScope)throwsSpec77).getTree());
                }
            }
            int alt25 = 2;
            int LA25_0 = this.input.LA(1);
            if (LA25_0 == 33) {
                alt25 = 1;
            }
            switch (alt25) {
                case 1: {
                    this.pushFollow(FOLLOW_localsSpec_in_parserRule1590);
                    localsSpec78 = this.localsSpec();
                    --this.state._fsp;
                    stream_localsSpec.add(((ParserRuleReturnScope)localsSpec78).getTree());
                }
            }
            this.pushFollow(FOLLOW_rulePrequels_in_parserRule1628);
            rulePrequels79 = this.rulePrequels();
            --this.state._fsp;
            stream_rulePrequels.add(((ParserRuleReturnScope)rulePrequels79).getTree());
            COLON80 = (Token)this.match(this.input, 14, FOLLOW_COLON_in_parserRule1637);
            stream_COLON.add(COLON80);
            this.pushFollow(FOLLOW_ruleBlock_in_parserRule1660);
            ruleBlock81 = this.ruleBlock();
            --this.state._fsp;
            stream_ruleBlock.add(((ParserRuleReturnScope)ruleBlock81).getTree());
            SEMI82 = (Token)this.match(this.input, 58, FOLLOW_SEMI_in_parserRule1669);
            stream_SEMI.add(SEMI82);
            this.pushFollow(FOLLOW_exceptionGroup_in_parserRule1678);
            exceptionGroup83 = this.exceptionGroup();
            --this.state._fsp;
            stream_exceptionGroup.add(((ParserRuleReturnScope)exceptionGroup83).getTree());
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
            root_1 = (GrammarAST)this.adaptor.becomeRoot(new RuleAST(94), (Object)root_1);
            this.adaptor.addChild(root_1, stream_RULE_REF.nextNode());
            if (stream_ARG_ACTION.hasNext()) {
                this.adaptor.addChild(root_1, new ActionAST(stream_ARG_ACTION.nextToken()));
            }
            stream_ARG_ACTION.reset();
            if (stream_ruleReturns.hasNext()) {
                this.adaptor.addChild(root_1, stream_ruleReturns.nextTree());
            }
            stream_ruleReturns.reset();
            if (stream_throwsSpec.hasNext()) {
                this.adaptor.addChild(root_1, stream_throwsSpec.nextTree());
            }
            stream_throwsSpec.reset();
            if (stream_localsSpec.hasNext()) {
                this.adaptor.addChild(root_1, stream_localsSpec.nextTree());
            }
            stream_localsSpec.reset();
            if (stream_rulePrequels.hasNext()) {
                this.adaptor.addChild(root_1, stream_rulePrequels.nextTree());
            }
            stream_rulePrequels.reset();
            this.adaptor.addChild(root_1, stream_ruleBlock.nextTree());
            while (stream_exceptionGroup.hasNext()) {
                this.adaptor.addChild(root_1, stream_exceptionGroup.nextTree());
            }
            stream_exceptionGroup.reset();
            this.adaptor.addChild(root_0, root_1);
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            this.paraphrases.pop();
            GrammarAST options = (GrammarAST)retval.tree.getFirstChildWithType(42);
            if (options != null) {
                Grammar.setNodeOptions(retval.tree, options);
            }
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final exceptionGroup_return exceptionGroup() throws RecognitionException {
        exceptionGroup_return retval = new exceptionGroup_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        exceptionHandler_return exceptionHandler84 = null;
        finallyClause_return finallyClause85 = null;
        try {
            root_0 = (GrammarAST)this.adaptor.nil();
            block10: while (true) {
                int alt26 = 2;
                int LA26_0 = this.input.LA(1);
                if (LA26_0 == 12) {
                    alt26 = 1;
                }
                switch (alt26) {
                    case 1: {
                        this.pushFollow(FOLLOW_exceptionHandler_in_exceptionGroup1761);
                        exceptionHandler84 = this.exceptionHandler();
                        --this.state._fsp;
                        this.adaptor.addChild(root_0, ((ParserRuleReturnScope)exceptionHandler84).getTree());
                        continue block10;
                    }
                }
                break;
            }
            int alt27 = 2;
            int LA27_0 = this.input.LA(1);
            if (LA27_0 == 23) {
                alt27 = 1;
            }
            switch (alt27) {
                case 1: {
                    this.pushFollow(FOLLOW_finallyClause_in_exceptionGroup1764);
                    finallyClause85 = this.finallyClause();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)finallyClause85).getTree());
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final exceptionHandler_return exceptionHandler() throws RecognitionException {
        exceptionHandler_return retval = new exceptionHandler_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token CATCH86 = null;
        Token ARG_ACTION87 = null;
        Token ACTION88 = null;
        Object CATCH86_tree = null;
        Object ARG_ACTION87_tree = null;
        Object ACTION88_tree = null;
        RewriteRuleTokenStream stream_CATCH = new RewriteRuleTokenStream(this.adaptor, "token CATCH");
        RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
        RewriteRuleTokenStream stream_ARG_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ARG_ACTION");
        try {
            CATCH86 = (Token)this.match(this.input, 12, FOLLOW_CATCH_in_exceptionHandler1781);
            stream_CATCH.add(CATCH86);
            ARG_ACTION87 = (Token)this.match(this.input, 8, FOLLOW_ARG_ACTION_in_exceptionHandler1783);
            stream_ARG_ACTION.add(ARG_ACTION87);
            ACTION88 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_exceptionHandler1785);
            stream_ACTION.add(ACTION88);
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
            root_1 = (GrammarAST)this.adaptor.becomeRoot(stream_CATCH.nextNode(), (Object)root_1);
            this.adaptor.addChild(root_1, new ActionAST(stream_ARG_ACTION.nextToken()));
            this.adaptor.addChild(root_1, new ActionAST(stream_ACTION.nextToken()));
            this.adaptor.addChild(root_0, root_1);
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final finallyClause_return finallyClause() throws RecognitionException {
        finallyClause_return retval = new finallyClause_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token FINALLY89 = null;
        Token ACTION90 = null;
        Object FINALLY89_tree = null;
        Object ACTION90_tree = null;
        RewriteRuleTokenStream stream_FINALLY = new RewriteRuleTokenStream(this.adaptor, "token FINALLY");
        RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
        try {
            FINALLY89 = (Token)this.match(this.input, 23, FOLLOW_FINALLY_in_finallyClause1812);
            stream_FINALLY.add(FINALLY89);
            ACTION90 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_finallyClause1814);
            stream_ACTION.add(ACTION90);
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
            root_1 = (GrammarAST)this.adaptor.becomeRoot(stream_FINALLY.nextNode(), (Object)root_1);
            this.adaptor.addChild(root_1, new ActionAST(stream_ACTION.nextToken()));
            this.adaptor.addChild(root_0, root_1);
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final rulePrequels_return rulePrequels() throws RecognitionException {
        rulePrequels_return retval = new rulePrequels_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        sync_return sync91 = null;
        rulePrequel_return rulePrequel92 = null;
        sync_return sync93 = null;
        RewriteRuleSubtreeStream stream_rulePrequel = new RewriteRuleSubtreeStream(this.adaptor, "rule rulePrequel");
        RewriteRuleSubtreeStream stream_sync = new RewriteRuleSubtreeStream(this.adaptor, "rule sync");
        this.paraphrases.push("matching rule preamble");
        try {
            this.pushFollow(FOLLOW_sync_in_rulePrequels1846);
            sync91 = this.sync();
            --this.state._fsp;
            stream_sync.add(((ParserRuleReturnScope)sync91).getTree());
            block7: while (true) {
                int alt28 = 2;
                int LA28_0 = this.input.LA(1);
                if (LA28_0 == 11 || LA28_0 == 42) {
                    alt28 = 1;
                }
                switch (alt28) {
                    case 1: {
                        this.pushFollow(FOLLOW_rulePrequel_in_rulePrequels1849);
                        rulePrequel92 = this.rulePrequel();
                        --this.state._fsp;
                        stream_rulePrequel.add(((ParserRuleReturnScope)rulePrequel92).getTree());
                        this.pushFollow(FOLLOW_sync_in_rulePrequels1851);
                        sync93 = this.sync();
                        --this.state._fsp;
                        stream_sync.add(((ParserRuleReturnScope)sync93).getTree());
                        continue block7;
                    }
                }
                break;
            }
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            while (stream_rulePrequel.hasNext()) {
                this.adaptor.addChild(root_0, stream_rulePrequel.nextTree());
            }
            stream_rulePrequel.reset();
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            this.paraphrases.pop();
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final rulePrequel_return rulePrequel() throws RecognitionException {
        rulePrequel_return retval = new rulePrequel_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        optionsSpec_return optionsSpec94 = null;
        ruleAction_return ruleAction95 = null;
        try {
            int alt29 = 2;
            int LA29_0 = this.input.LA(1);
            if (LA29_0 == 42) {
                alt29 = 1;
            } else if (LA29_0 == 11) {
                alt29 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 29, 0, this.input);
                throw nvae;
            }
            switch (alt29) {
                case 1: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_optionsSpec_in_rulePrequel1875);
                    optionsSpec94 = this.optionsSpec();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)optionsSpec94).getTree());
                    break;
                }
                case 2: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_ruleAction_in_rulePrequel1883);
                    ruleAction95 = this.ruleAction();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)ruleAction95).getTree());
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final ruleReturns_return ruleReturns() throws RecognitionException {
        ruleReturns_return retval = new ruleReturns_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token RETURNS96 = null;
        Token ARG_ACTION97 = null;
        GrammarAST RETURNS96_tree = null;
        ActionAST ARG_ACTION97_tree = null;
        try {
            root_0 = (GrammarAST)this.adaptor.nil();
            RETURNS96 = (Token)this.match(this.input, 55, FOLLOW_RETURNS_in_ruleReturns1903);
            RETURNS96_tree = (GrammarAST)this.adaptor.create(RETURNS96);
            root_0 = (GrammarAST)this.adaptor.becomeRoot(RETURNS96_tree, (Object)root_0);
            ARG_ACTION97 = (Token)this.match(this.input, 8, FOLLOW_ARG_ACTION_in_ruleReturns1906);
            ARG_ACTION97_tree = new ActionAST(ARG_ACTION97);
            this.adaptor.addChild(root_0, ARG_ACTION97_tree);
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final throwsSpec_return throwsSpec() throws RecognitionException {
        throwsSpec_return retval = new throwsSpec_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token THROWS98 = null;
        Token COMMA100 = null;
        qid_return qid99 = null;
        qid_return qid101 = null;
        Object THROWS98_tree = null;
        Object COMMA100_tree = null;
        RewriteRuleTokenStream stream_THROWS = new RewriteRuleTokenStream(this.adaptor, "token THROWS");
        RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleSubtreeStream stream_qid = new RewriteRuleSubtreeStream(this.adaptor, "rule qid");
        try {
            THROWS98 = (Token)this.match(this.input, 64, FOLLOW_THROWS_in_throwsSpec1934);
            stream_THROWS.add(THROWS98);
            this.pushFollow(FOLLOW_qid_in_throwsSpec1936);
            qid99 = this.qid();
            --this.state._fsp;
            stream_qid.add(((ParserRuleReturnScope)qid99).getTree());
            block7: while (true) {
                int alt30 = 2;
                int LA30_0 = this.input.LA(1);
                if (LA30_0 == 16) {
                    alt30 = 1;
                }
                switch (alt30) {
                    case 1: {
                        COMMA100 = (Token)this.match(this.input, 16, FOLLOW_COMMA_in_throwsSpec1939);
                        stream_COMMA.add(COMMA100);
                        this.pushFollow(FOLLOW_qid_in_throwsSpec1941);
                        qid101 = this.qid();
                        --this.state._fsp;
                        stream_qid.add(((ParserRuleReturnScope)qid101).getTree());
                        continue block7;
                    }
                }
                break;
            }
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
            root_1 = (GrammarAST)this.adaptor.becomeRoot(stream_THROWS.nextNode(), (Object)root_1);
            if (!stream_qid.hasNext()) {
                throw new RewriteEarlyExitException();
            }
            while (stream_qid.hasNext()) {
                this.adaptor.addChild(root_1, stream_qid.nextTree());
            }
            stream_qid.reset();
            this.adaptor.addChild(root_0, root_1);
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final localsSpec_return localsSpec() throws RecognitionException {
        localsSpec_return retval = new localsSpec_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token LOCALS102 = null;
        Token ARG_ACTION103 = null;
        GrammarAST LOCALS102_tree = null;
        ActionAST ARG_ACTION103_tree = null;
        try {
            root_0 = (GrammarAST)this.adaptor.nil();
            LOCALS102 = (Token)this.match(this.input, 33, FOLLOW_LOCALS_in_localsSpec1966);
            LOCALS102_tree = (GrammarAST)this.adaptor.create(LOCALS102);
            root_0 = (GrammarAST)this.adaptor.becomeRoot(LOCALS102_tree, (Object)root_0);
            ARG_ACTION103 = (Token)this.match(this.input, 8, FOLLOW_ARG_ACTION_in_localsSpec1969);
            ARG_ACTION103_tree = new ActionAST(ARG_ACTION103);
            this.adaptor.addChild(root_0, ARG_ACTION103_tree);
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final ruleAction_return ruleAction() throws RecognitionException {
        ruleAction_return retval = new ruleAction_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token AT104 = null;
        Token ACTION106 = null;
        id_return id105 = null;
        Object AT104_tree = null;
        Object ACTION106_tree = null;
        RewriteRuleTokenStream stream_AT = new RewriteRuleTokenStream(this.adaptor, "token AT");
        RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
        RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
        try {
            AT104 = (Token)this.match(this.input, 11, FOLLOW_AT_in_ruleAction1992);
            stream_AT.add(AT104);
            this.pushFollow(FOLLOW_id_in_ruleAction1994);
            id105 = this.id();
            --this.state._fsp;
            stream_id.add(((ParserRuleReturnScope)id105).getTree());
            ACTION106 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_ruleAction1996);
            stream_ACTION.add(ACTION106);
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
            root_1 = (GrammarAST)this.adaptor.becomeRoot(stream_AT.nextNode(), (Object)root_1);
            this.adaptor.addChild(root_1, stream_id.nextTree());
            this.adaptor.addChild(root_1, new ActionAST(stream_ACTION.nextToken()));
            this.adaptor.addChild(root_0, root_1);
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final ruleBlock_return ruleBlock() throws RecognitionException {
        ruleBlock_return retval = new ruleBlock_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        ruleAltList_return ruleAltList107 = null;
        RewriteRuleSubtreeStream stream_ruleAltList = new RewriteRuleSubtreeStream(this.adaptor, "rule ruleAltList");
        Token colon = this.input.LT(-1);
        try {
            this.pushFollow(FOLLOW_ruleAltList_in_ruleBlock2034);
            ruleAltList107 = this.ruleAltList();
            --this.state._fsp;
            stream_ruleAltList.add(((ParserRuleReturnScope)ruleAltList107).getTree());
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
            root_1 = (GrammarAST)this.adaptor.becomeRoot(new BlockAST(78, colon, "BLOCK"), (Object)root_1);
            this.adaptor.addChild(root_1, stream_ruleAltList.nextTree());
            this.adaptor.addChild(root_0, root_1);
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (ResyncToEndOfRuleBlock e) {
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), null);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final ruleAltList_return ruleAltList() throws RecognitionException {
        ruleAltList_return retval = new ruleAltList_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token OR109 = null;
        labeledAlt_return labeledAlt108 = null;
        labeledAlt_return labeledAlt110 = null;
        Object OR109_tree = null;
        RewriteRuleTokenStream stream_OR = new RewriteRuleTokenStream(this.adaptor, "token OR");
        RewriteRuleSubtreeStream stream_labeledAlt = new RewriteRuleSubtreeStream(this.adaptor, "rule labeledAlt");
        try {
            this.pushFollow(FOLLOW_labeledAlt_in_ruleAltList2070);
            labeledAlt108 = this.labeledAlt();
            --this.state._fsp;
            stream_labeledAlt.add(((ParserRuleReturnScope)labeledAlt108).getTree());
            block7: while (true) {
                int alt31 = 2;
                int LA31_0 = this.input.LA(1);
                if (LA31_0 == 43) {
                    alt31 = 1;
                }
                switch (alt31) {
                    case 1: {
                        OR109 = (Token)this.match(this.input, 43, FOLLOW_OR_in_ruleAltList2073);
                        stream_OR.add(OR109);
                        this.pushFollow(FOLLOW_labeledAlt_in_ruleAltList2075);
                        labeledAlt110 = this.labeledAlt();
                        --this.state._fsp;
                        stream_labeledAlt.add(((ParserRuleReturnScope)labeledAlt110).getTree());
                        continue block7;
                    }
                }
                break;
            }
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            if (!stream_labeledAlt.hasNext()) {
                throw new RewriteEarlyExitException();
            }
            while (stream_labeledAlt.hasNext()) {
                this.adaptor.addChild(root_0, stream_labeledAlt.nextTree());
            }
            stream_labeledAlt.reset();
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final labeledAlt_return labeledAlt() throws RecognitionException {
        labeledAlt_return retval = new labeledAlt_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token POUND112 = null;
        alternative_return alternative111 = null;
        id_return id113 = null;
        Object POUND112_tree = null;
        try {
            root_0 = (GrammarAST)this.adaptor.nil();
            this.pushFollow(FOLLOW_alternative_in_labeledAlt2093);
            alternative111 = this.alternative();
            --this.state._fsp;
            this.adaptor.addChild(root_0, ((ParserRuleReturnScope)alternative111).getTree());
            int alt32 = 2;
            int LA32_0 = this.input.LA(1);
            if (LA32_0 == 47) {
                alt32 = 1;
            }
            switch (alt32) {
                case 1: {
                    POUND112 = (Token)this.match(this.input, 47, FOLLOW_POUND_in_labeledAlt2099);
                    this.pushFollow(FOLLOW_id_in_labeledAlt2102);
                    id113 = this.id();
                    --this.state._fsp;
                    ((AltAST)(alternative111 != null ? (GrammarAST)((ParserRuleReturnScope)alternative111).getTree() : null)).altLabel = id113 != null ? (GrammarAST)((ParserRuleReturnScope)id113).getTree() : null;
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final lexerRule_return lexerRule() throws RecognitionException {
        lexerRule_return retval = new lexerRule_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token FRAGMENT114 = null;
        Token TOKEN_REF115 = null;
        Token COLON116 = null;
        Token SEMI118 = null;
        lexerRuleBlock_return lexerRuleBlock117 = null;
        Object FRAGMENT114_tree = null;
        Object TOKEN_REF115_tree = null;
        Object COLON116_tree = null;
        Object SEMI118_tree = null;
        RewriteRuleTokenStream stream_COLON = new RewriteRuleTokenStream(this.adaptor, "token COLON");
        RewriteRuleTokenStream stream_SEMI = new RewriteRuleTokenStream(this.adaptor, "token SEMI");
        RewriteRuleTokenStream stream_FRAGMENT = new RewriteRuleTokenStream(this.adaptor, "token FRAGMENT");
        RewriteRuleTokenStream stream_TOKEN_REF = new RewriteRuleTokenStream(this.adaptor, "token TOKEN_REF");
        RewriteRuleSubtreeStream stream_lexerRuleBlock = new RewriteRuleSubtreeStream(this.adaptor, "rule lexerRuleBlock");
        this.paraphrases.push("matching a lexer rule");
        try {
            int alt33 = 2;
            int LA33_0 = this.input.LA(1);
            if (LA33_0 == 24) {
                alt33 = 1;
            }
            switch (alt33) {
                case 1: {
                    FRAGMENT114 = (Token)this.match(this.input, 24, FOLLOW_FRAGMENT_in_lexerRule2134);
                    stream_FRAGMENT.add(FRAGMENT114);
                }
            }
            TOKEN_REF115 = (Token)this.match(this.input, 66, FOLLOW_TOKEN_REF_in_lexerRule2140);
            stream_TOKEN_REF.add(TOKEN_REF115);
            COLON116 = (Token)this.match(this.input, 14, FOLLOW_COLON_in_lexerRule2142);
            stream_COLON.add(COLON116);
            this.pushFollow(FOLLOW_lexerRuleBlock_in_lexerRule2144);
            lexerRuleBlock117 = this.lexerRuleBlock();
            --this.state._fsp;
            stream_lexerRuleBlock.add(((ParserRuleReturnScope)lexerRuleBlock117).getTree());
            SEMI118 = (Token)this.match(this.input, 58, FOLLOW_SEMI_in_lexerRule2146);
            stream_SEMI.add(SEMI118);
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
            root_1 = (GrammarAST)this.adaptor.becomeRoot(new RuleAST(94), (Object)root_1);
            this.adaptor.addChild(root_1, stream_TOKEN_REF.nextNode());
            if (stream_FRAGMENT.hasNext()) {
                GrammarAST root_2 = (GrammarAST)this.adaptor.nil();
                root_2 = (GrammarAST)this.adaptor.becomeRoot((GrammarAST)this.adaptor.create(96, "RULEMODIFIERS"), (Object)root_2);
                this.adaptor.addChild(root_2, stream_FRAGMENT.nextNode());
                this.adaptor.addChild(root_1, root_2);
            }
            stream_FRAGMENT.reset();
            this.adaptor.addChild(root_1, stream_lexerRuleBlock.nextTree());
            this.adaptor.addChild(root_0, root_1);
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            this.paraphrases.pop();
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final lexerRuleBlock_return lexerRuleBlock() throws RecognitionException {
        lexerRuleBlock_return retval = new lexerRuleBlock_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        lexerAltList_return lexerAltList119 = null;
        RewriteRuleSubtreeStream stream_lexerAltList = new RewriteRuleSubtreeStream(this.adaptor, "rule lexerAltList");
        Token colon = this.input.LT(-1);
        try {
            this.pushFollow(FOLLOW_lexerAltList_in_lexerRuleBlock2210);
            lexerAltList119 = this.lexerAltList();
            --this.state._fsp;
            stream_lexerAltList.add(((ParserRuleReturnScope)lexerAltList119).getTree());
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
            root_1 = (GrammarAST)this.adaptor.becomeRoot(new BlockAST(78, colon, "BLOCK"), (Object)root_1);
            this.adaptor.addChild(root_1, stream_lexerAltList.nextTree());
            this.adaptor.addChild(root_0, root_1);
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (ResyncToEndOfRuleBlock e) {
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), null);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final lexerAltList_return lexerAltList() throws RecognitionException {
        lexerAltList_return retval = new lexerAltList_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token OR121 = null;
        lexerAlt_return lexerAlt120 = null;
        lexerAlt_return lexerAlt122 = null;
        Object OR121_tree = null;
        RewriteRuleTokenStream stream_OR = new RewriteRuleTokenStream(this.adaptor, "token OR");
        RewriteRuleSubtreeStream stream_lexerAlt = new RewriteRuleSubtreeStream(this.adaptor, "rule lexerAlt");
        try {
            this.pushFollow(FOLLOW_lexerAlt_in_lexerAltList2246);
            lexerAlt120 = this.lexerAlt();
            --this.state._fsp;
            stream_lexerAlt.add(((ParserRuleReturnScope)lexerAlt120).getTree());
            block7: while (true) {
                int alt34 = 2;
                int LA34_0 = this.input.LA(1);
                if (LA34_0 == 43) {
                    alt34 = 1;
                }
                switch (alt34) {
                    case 1: {
                        OR121 = (Token)this.match(this.input, 43, FOLLOW_OR_in_lexerAltList2249);
                        stream_OR.add(OR121);
                        this.pushFollow(FOLLOW_lexerAlt_in_lexerAltList2251);
                        lexerAlt122 = this.lexerAlt();
                        --this.state._fsp;
                        stream_lexerAlt.add(((ParserRuleReturnScope)lexerAlt122).getTree());
                        continue block7;
                    }
                }
                break;
            }
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            if (!stream_lexerAlt.hasNext()) {
                throw new RewriteEarlyExitException();
            }
            while (stream_lexerAlt.hasNext()) {
                this.adaptor.addChild(root_0, stream_lexerAlt.nextTree());
            }
            stream_lexerAlt.reset();
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final lexerAlt_return lexerAlt() throws RecognitionException {
        lexerAlt_return retval = new lexerAlt_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        lexerElements_return lexerElements123 = null;
        lexerCommands_return lexerCommands124 = null;
        RewriteRuleSubtreeStream stream_lexerElements = new RewriteRuleSubtreeStream(this.adaptor, "rule lexerElements");
        RewriteRuleSubtreeStream stream_lexerCommands = new RewriteRuleSubtreeStream(this.adaptor, "rule lexerCommands");
        try {
            this.pushFollow(FOLLOW_lexerElements_in_lexerAlt2269);
            lexerElements123 = this.lexerElements();
            --this.state._fsp;
            stream_lexerElements.add(((ParserRuleReturnScope)lexerElements123).getTree());
            int alt35 = 2;
            int LA35_0 = this.input.LA(1);
            if (LA35_0 == 53) {
                alt35 = 1;
            } else if (LA35_0 == 43 || LA35_0 == 56 || LA35_0 == 58) {
                alt35 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 35, 0, this.input);
                throw nvae;
            }
            switch (alt35) {
                case 1: {
                    this.pushFollow(FOLLOW_lexerCommands_in_lexerAlt2275);
                    lexerCommands124 = this.lexerCommands();
                    --this.state._fsp;
                    stream_lexerCommands.add(((ParserRuleReturnScope)lexerCommands124).getTree());
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                    root_1 = (GrammarAST)this.adaptor.becomeRoot(new AltAST(87), (Object)root_1);
                    this.adaptor.addChild(root_1, stream_lexerElements.nextTree());
                    this.adaptor.addChild(root_1, stream_lexerCommands.nextTree());
                    this.adaptor.addChild(root_0, root_1);
                    retval.tree = root_0;
                    break;
                }
                case 2: {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.adaptor.addChild(root_0, stream_lexerElements.nextTree());
                    retval.tree = root_0;
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final lexerElements_return lexerElements() throws RecognitionException {
        lexerElements_return retval = new lexerElements_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        lexerElement_return lexerElement125 = null;
        RewriteRuleSubtreeStream stream_lexerElement = new RewriteRuleSubtreeStream(this.adaptor, "rule lexerElement");
        try {
            int alt37 = 2;
            int LA37_0 = this.input.LA(1);
            if (LA37_0 == 4 || LA37_0 == 20 || LA37_0 == 32 || LA37_0 == 34 || LA37_0 == 39 || LA37_0 == 57 || LA37_0 == 59 || LA37_0 == 62 || LA37_0 == 66) {
                alt37 = 1;
            } else if (LA37_0 == 43 || LA37_0 == 53 || LA37_0 == 56 || LA37_0 == 58) {
                alt37 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 37, 0, this.input);
                throw nvae;
            }
            switch (alt37) {
                case 1: {
                    int cnt36 = 0;
                    block11: while (true) {
                        int alt36 = 2;
                        int LA36_0 = this.input.LA(1);
                        if (LA36_0 == 4 || LA36_0 == 20 || LA36_0 == 32 || LA36_0 == 34 || LA36_0 == 39 || LA36_0 == 57 || LA36_0 == 59 || LA36_0 == 62 || LA36_0 == 66) {
                            alt36 = 1;
                        }
                        switch (alt36) {
                            case 1: {
                                this.pushFollow(FOLLOW_lexerElement_in_lexerElements2318);
                                lexerElement125 = this.lexerElement();
                                --this.state._fsp;
                                stream_lexerElement.add(((ParserRuleReturnScope)lexerElement125).getTree());
                                break;
                            }
                            default: {
                                if (cnt36 >= 1) break block11;
                                EarlyExitException eee = new EarlyExitException(36, this.input);
                                throw eee;
                            }
                        }
                        ++cnt36;
                    }
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                    root_1 = (GrammarAST)this.adaptor.becomeRoot(new AltAST(74), (Object)root_1);
                    if (!stream_lexerElement.hasNext()) {
                        throw new RewriteEarlyExitException();
                    }
                    while (stream_lexerElement.hasNext()) {
                        this.adaptor.addChild(root_1, stream_lexerElement.nextTree());
                    }
                    stream_lexerElement.reset();
                    this.adaptor.addChild(root_0, root_1);
                    retval.tree = root_0;
                    break;
                }
                case 2: {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                    root_1 = (GrammarAST)this.adaptor.becomeRoot(new AltAST(74), (Object)root_1);
                    this.adaptor.addChild(root_1, (GrammarAST)this.adaptor.create(83, "EPSILON"));
                    this.adaptor.addChild(root_0, root_1);
                    retval.tree = root_0;
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final lexerElement_return lexerElement() throws RecognitionException {
        lexerElement_return retval = new lexerElement_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        labeledLexerElement_return labeledLexerElement126 = null;
        ebnfSuffix_return ebnfSuffix127 = null;
        lexerAtom_return lexerAtom128 = null;
        ebnfSuffix_return ebnfSuffix129 = null;
        lexerBlock_return lexerBlock130 = null;
        ebnfSuffix_return ebnfSuffix131 = null;
        actionElement_return actionElement132 = null;
        RewriteRuleSubtreeStream stream_ebnfSuffix = new RewriteRuleSubtreeStream(this.adaptor, "rule ebnfSuffix");
        RewriteRuleSubtreeStream stream_lexerBlock = new RewriteRuleSubtreeStream(this.adaptor, "rule lexerBlock");
        RewriteRuleSubtreeStream stream_labeledLexerElement = new RewriteRuleSubtreeStream(this.adaptor, "rule labeledLexerElement");
        RewriteRuleSubtreeStream stream_lexerAtom = new RewriteRuleSubtreeStream(this.adaptor, "rule lexerAtom");
        this.paraphrases.push("looking for lexer rule element");
        int m = this.input.mark();
        try {
            int alt41 = 4;
            switch (this.input.LA(1)) {
                case 57: {
                    int LA41_1 = this.input.LA(2);
                    if (LA41_1 == 10 || LA41_1 == 46) {
                        alt41 = 1;
                        break;
                    }
                    if (LA41_1 == 4 || LA41_1 == 20 || LA41_1 == 32 || LA41_1 == 34 || LA41_1 == 39 || LA41_1 == 43 || LA41_1 == 45 || LA41_1 == 51 || LA41_1 == 53 || LA41_1 >= 56 && LA41_1 <= 59 || LA41_1 >= 61 && LA41_1 <= 62 || LA41_1 == 66) {
                        alt41 = 2;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 41, 1, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 66: {
                    int LA41_2 = this.input.LA(2);
                    if (LA41_2 == 10 || LA41_2 == 46) {
                        alt41 = 1;
                        break;
                    }
                    if (LA41_2 == 4 || LA41_2 == 20 || LA41_2 == 32 || LA41_2 >= 34 && LA41_2 <= 35 || LA41_2 == 39 || LA41_2 == 43 || LA41_2 == 45 || LA41_2 == 51 || LA41_2 == 53 || LA41_2 >= 56 && LA41_2 <= 59 || LA41_2 >= 61 && LA41_2 <= 62 || LA41_2 == 66) {
                        alt41 = 2;
                        break;
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
                case 20: 
                case 32: 
                case 39: 
                case 62: {
                    alt41 = 2;
                    break;
                }
                case 34: {
                    alt41 = 3;
                    break;
                }
                case 4: 
                case 59: {
                    alt41 = 4;
                    break;
                }
                default: {
                    NoViableAltException nvae = new NoViableAltException("", 41, 0, this.input);
                    throw nvae;
                }
            }
            switch (alt41) {
                case 1: {
                    this.pushFollow(FOLLOW_labeledLexerElement_in_lexerElement2374);
                    labeledLexerElement126 = this.labeledLexerElement();
                    --this.state._fsp;
                    stream_labeledLexerElement.add(((ParserRuleReturnScope)labeledLexerElement126).getTree());
                    int alt38 = 2;
                    int LA38_0 = this.input.LA(1);
                    if (LA38_0 == 45 || LA38_0 == 51 || LA38_0 == 61) {
                        alt38 = 1;
                    } else if (LA38_0 == 4 || LA38_0 == 20 || LA38_0 == 32 || LA38_0 == 34 || LA38_0 == 39 || LA38_0 == 43 || LA38_0 == 53 || LA38_0 >= 56 && LA38_0 <= 59 || LA38_0 == 62 || LA38_0 == 66) {
                        alt38 = 2;
                    } else {
                        NoViableAltException nvae = new NoViableAltException("", 38, 0, this.input);
                        throw nvae;
                    }
                    switch (alt38) {
                        case 1: {
                            this.pushFollow(FOLLOW_ebnfSuffix_in_lexerElement2380);
                            ebnfSuffix127 = this.ebnfSuffix();
                            --this.state._fsp;
                            stream_ebnfSuffix.add(((ParserRuleReturnScope)ebnfSuffix127).getTree());
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                            root_0 = (GrammarAST)this.adaptor.nil();
                            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                            root_1 = (GrammarAST)this.adaptor.becomeRoot(stream_ebnfSuffix.nextNode(), (Object)root_1);
                            GrammarAST root_2 = (GrammarAST)this.adaptor.nil();
                            root_2 = (GrammarAST)this.adaptor.becomeRoot(new BlockAST(78, labeledLexerElement126 != null ? labeledLexerElement126.start : null, "BLOCK"), (Object)root_2);
                            GrammarAST root_3 = (GrammarAST)this.adaptor.nil();
                            root_3 = (GrammarAST)this.adaptor.becomeRoot(new AltAST(74), (Object)root_3);
                            this.adaptor.addChild(root_3, stream_labeledLexerElement.nextTree());
                            this.adaptor.addChild(root_2, root_3);
                            this.adaptor.addChild(root_1, root_2);
                            this.adaptor.addChild(root_0, root_1);
                            retval.tree = root_0;
                            break;
                        }
                        case 2: {
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                            root_0 = (GrammarAST)this.adaptor.nil();
                            this.adaptor.addChild(root_0, stream_labeledLexerElement.nextTree());
                            retval.tree = root_0;
                        }
                    }
                    break;
                }
                case 2: {
                    this.pushFollow(FOLLOW_lexerAtom_in_lexerElement2426);
                    lexerAtom128 = this.lexerAtom();
                    --this.state._fsp;
                    stream_lexerAtom.add(((ParserRuleReturnScope)lexerAtom128).getTree());
                    int alt39 = 2;
                    int LA39_0 = this.input.LA(1);
                    if (LA39_0 == 45 || LA39_0 == 51 || LA39_0 == 61) {
                        alt39 = 1;
                    } else if (LA39_0 == 4 || LA39_0 == 20 || LA39_0 == 32 || LA39_0 == 34 || LA39_0 == 39 || LA39_0 == 43 || LA39_0 == 53 || LA39_0 >= 56 && LA39_0 <= 59 || LA39_0 == 62 || LA39_0 == 66) {
                        alt39 = 2;
                    } else {
                        NoViableAltException nvae = new NoViableAltException("", 39, 0, this.input);
                        throw nvae;
                    }
                    switch (alt39) {
                        case 1: {
                            this.pushFollow(FOLLOW_ebnfSuffix_in_lexerElement2432);
                            ebnfSuffix129 = this.ebnfSuffix();
                            --this.state._fsp;
                            stream_ebnfSuffix.add(((ParserRuleReturnScope)ebnfSuffix129).getTree());
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                            root_0 = (GrammarAST)this.adaptor.nil();
                            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                            root_1 = (GrammarAST)this.adaptor.becomeRoot(stream_ebnfSuffix.nextNode(), (Object)root_1);
                            GrammarAST root_2 = (GrammarAST)this.adaptor.nil();
                            root_2 = (GrammarAST)this.adaptor.becomeRoot(new BlockAST(78, lexerAtom128 != null ? lexerAtom128.start : null, "BLOCK"), (Object)root_2);
                            GrammarAST root_3 = (GrammarAST)this.adaptor.nil();
                            root_3 = (GrammarAST)this.adaptor.becomeRoot(new AltAST(74), (Object)root_3);
                            this.adaptor.addChild(root_3, stream_lexerAtom.nextTree());
                            this.adaptor.addChild(root_2, root_3);
                            this.adaptor.addChild(root_1, root_2);
                            this.adaptor.addChild(root_0, root_1);
                            retval.tree = root_0;
                            break;
                        }
                        case 2: {
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                            root_0 = (GrammarAST)this.adaptor.nil();
                            this.adaptor.addChild(root_0, stream_lexerAtom.nextTree());
                            retval.tree = root_0;
                        }
                    }
                    break;
                }
                case 3: {
                    this.pushFollow(FOLLOW_lexerBlock_in_lexerElement2478);
                    lexerBlock130 = this.lexerBlock();
                    --this.state._fsp;
                    stream_lexerBlock.add(((ParserRuleReturnScope)lexerBlock130).getTree());
                    int alt40 = 2;
                    int LA40_0 = this.input.LA(1);
                    if (LA40_0 == 45 || LA40_0 == 51 || LA40_0 == 61) {
                        alt40 = 1;
                    } else if (LA40_0 == 4 || LA40_0 == 20 || LA40_0 == 32 || LA40_0 == 34 || LA40_0 == 39 || LA40_0 == 43 || LA40_0 == 53 || LA40_0 >= 56 && LA40_0 <= 59 || LA40_0 == 62 || LA40_0 == 66) {
                        alt40 = 2;
                    } else {
                        NoViableAltException nvae = new NoViableAltException("", 40, 0, this.input);
                        throw nvae;
                    }
                    switch (alt40) {
                        case 1: {
                            this.pushFollow(FOLLOW_ebnfSuffix_in_lexerElement2484);
                            ebnfSuffix131 = this.ebnfSuffix();
                            --this.state._fsp;
                            stream_ebnfSuffix.add(((ParserRuleReturnScope)ebnfSuffix131).getTree());
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                            root_0 = (GrammarAST)this.adaptor.nil();
                            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                            root_1 = (GrammarAST)this.adaptor.becomeRoot(stream_ebnfSuffix.nextNode(), (Object)root_1);
                            this.adaptor.addChild(root_1, stream_lexerBlock.nextTree());
                            this.adaptor.addChild(root_0, root_1);
                            retval.tree = root_0;
                            break;
                        }
                        case 2: {
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                            root_0 = (GrammarAST)this.adaptor.nil();
                            this.adaptor.addChild(root_0, stream_lexerBlock.nextTree());
                            retval.tree = root_0;
                        }
                    }
                    break;
                }
                case 4: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_actionElement_in_lexerElement2512);
                    actionElement132 = this.actionElement();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)actionElement132).getTree());
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            this.paraphrases.pop();
        }
        catch (RecognitionException re) {
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
            int ttype = this.input.get(this.input.range()).getType();
            if (ttype == 14 || ttype == 55 || ttype == 12 || ttype == 23 || ttype == 11 || ttype == -1) {
                v4ParserException missingSemi = new v4ParserException("unterminated rule (missing ';') detected at '" + this.input.LT(1).getText() + " " + this.input.LT(2).getText() + "'", this.input);
                this.reportError(missingSemi);
                if (ttype == -1) {
                    this.input.seek(this.input.index() + 1);
                } else if (ttype == 12 || ttype == 23) {
                    this.input.seek(this.input.range());
                } else if (ttype == 55 || ttype == 11) {
                    int p = this.input.index();
                    Token t = this.input.get(p);
                    while (t.getType() != 57 && t.getType() != 66) {
                        t = this.input.get(--p);
                    }
                    this.input.seek(p);
                }
                throw new ResyncToEndOfRuleBlock();
            }
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
        GrammarAST root_0 = null;
        Token ass = null;
        id_return id133 = null;
        lexerAtom_return lexerAtom134 = null;
        lexerBlock_return lexerBlock135 = null;
        Object ass_tree = null;
        RewriteRuleTokenStream stream_ASSIGN = new RewriteRuleTokenStream(this.adaptor, "token ASSIGN");
        RewriteRuleTokenStream stream_PLUS_ASSIGN = new RewriteRuleTokenStream(this.adaptor, "token PLUS_ASSIGN");
        RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
        RewriteRuleSubtreeStream stream_lexerBlock = new RewriteRuleSubtreeStream(this.adaptor, "rule lexerBlock");
        RewriteRuleSubtreeStream stream_lexerAtom = new RewriteRuleSubtreeStream(this.adaptor, "rule lexerAtom");
        try {
            this.pushFollow(FOLLOW_id_in_labeledLexerElement2542);
            id133 = this.id();
            --this.state._fsp;
            stream_id.add(((ParserRuleReturnScope)id133).getTree());
            int alt42 = 2;
            int LA42_0 = this.input.LA(1);
            if (LA42_0 == 10) {
                alt42 = 1;
            } else if (LA42_0 == 46) {
                alt42 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 42, 0, this.input);
                throw nvae;
            }
            switch (alt42) {
                case 1: {
                    ass = (Token)this.match(this.input, 10, FOLLOW_ASSIGN_in_labeledLexerElement2547);
                    stream_ASSIGN.add(ass);
                    break;
                }
                case 2: {
                    ass = (Token)this.match(this.input, 46, FOLLOW_PLUS_ASSIGN_in_labeledLexerElement2551);
                    stream_PLUS_ASSIGN.add(ass);
                }
            }
            int alt43 = 2;
            int LA43_0 = this.input.LA(1);
            if (LA43_0 == 20 || LA43_0 == 32 || LA43_0 == 39 || LA43_0 == 57 || LA43_0 == 62 || LA43_0 == 66) {
                alt43 = 1;
            } else if (LA43_0 == 34) {
                alt43 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 43, 0, this.input);
                throw nvae;
            }
            switch (alt43) {
                case 1: {
                    this.pushFollow(FOLLOW_lexerAtom_in_labeledLexerElement2558);
                    lexerAtom134 = this.lexerAtom();
                    --this.state._fsp;
                    stream_lexerAtom.add(((ParserRuleReturnScope)lexerAtom134).getTree());
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_ass = new RewriteRuleTokenStream(this.adaptor, "token ass", ass);
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                    root_1 = (GrammarAST)this.adaptor.becomeRoot(stream_ass.nextNode(), (Object)root_1);
                    this.adaptor.addChild(root_1, stream_id.nextTree());
                    this.adaptor.addChild(root_1, stream_lexerAtom.nextTree());
                    this.adaptor.addChild(root_0, root_1);
                    retval.tree = root_0;
                    break;
                }
                case 2: {
                    this.pushFollow(FOLLOW_lexerBlock_in_labeledLexerElement2575);
                    lexerBlock135 = this.lexerBlock();
                    --this.state._fsp;
                    stream_lexerBlock.add(((ParserRuleReturnScope)lexerBlock135).getTree());
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_ass = new RewriteRuleTokenStream(this.adaptor, "token ass", ass);
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                    root_1 = (GrammarAST)this.adaptor.becomeRoot(stream_ass.nextNode(), (Object)root_1);
                    this.adaptor.addChild(root_1, stream_id.nextTree());
                    this.adaptor.addChild(root_1, stream_lexerBlock.nextTree());
                    this.adaptor.addChild(root_0, root_1);
                    retval.tree = root_0;
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final lexerBlock_return lexerBlock() throws RecognitionException {
        lexerBlock_return retval = new lexerBlock_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token LPAREN136 = null;
        Token COLON138 = null;
        Token RPAREN140 = null;
        optionsSpec_return optionsSpec137 = null;
        lexerAltList_return lexerAltList139 = null;
        Object LPAREN136_tree = null;
        Object COLON138_tree = null;
        Object RPAREN140_tree = null;
        RewriteRuleTokenStream stream_COLON = new RewriteRuleTokenStream(this.adaptor, "token COLON");
        RewriteRuleTokenStream stream_RPAREN = new RewriteRuleTokenStream(this.adaptor, "token RPAREN");
        RewriteRuleTokenStream stream_LPAREN = new RewriteRuleTokenStream(this.adaptor, "token LPAREN");
        RewriteRuleSubtreeStream stream_optionsSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule optionsSpec");
        RewriteRuleSubtreeStream stream_lexerAltList = new RewriteRuleSubtreeStream(this.adaptor, "rule lexerAltList");
        try {
            LPAREN136 = (Token)this.match(this.input, 34, FOLLOW_LPAREN_in_lexerBlock2608);
            stream_LPAREN.add(LPAREN136);
            int alt44 = 2;
            int LA44_0 = this.input.LA(1);
            if (LA44_0 == 42) {
                alt44 = 1;
            }
            switch (alt44) {
                case 1: {
                    this.pushFollow(FOLLOW_optionsSpec_in_lexerBlock2620);
                    optionsSpec137 = this.optionsSpec();
                    --this.state._fsp;
                    stream_optionsSpec.add(((ParserRuleReturnScope)optionsSpec137).getTree());
                    COLON138 = (Token)this.match(this.input, 14, FOLLOW_COLON_in_lexerBlock2622);
                    stream_COLON.add(COLON138);
                }
            }
            this.pushFollow(FOLLOW_lexerAltList_in_lexerBlock2635);
            lexerAltList139 = this.lexerAltList();
            --this.state._fsp;
            stream_lexerAltList.add(((ParserRuleReturnScope)lexerAltList139).getTree());
            RPAREN140 = (Token)this.match(this.input, 56, FOLLOW_RPAREN_in_lexerBlock2645);
            stream_RPAREN.add(RPAREN140);
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
            root_1 = (GrammarAST)this.adaptor.becomeRoot(new BlockAST(78, LPAREN136, "BLOCK"), (Object)root_1);
            if (stream_optionsSpec.hasNext()) {
                this.adaptor.addChild(root_1, stream_optionsSpec.nextTree());
            }
            stream_optionsSpec.reset();
            this.adaptor.addChild(root_1, stream_lexerAltList.nextTree());
            this.adaptor.addChild(root_0, root_1);
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            GrammarAST options = (GrammarAST)retval.tree.getFirstChildWithType(42);
            if (options != null) {
                Grammar.setNodeOptions(retval.tree, options);
            }
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final lexerCommands_return lexerCommands() throws RecognitionException {
        lexerCommands_return retval = new lexerCommands_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token RARROW141 = null;
        Token COMMA143 = null;
        lexerCommand_return lexerCommand142 = null;
        lexerCommand_return lexerCommand144 = null;
        Object RARROW141_tree = null;
        Object COMMA143_tree = null;
        RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream stream_RARROW = new RewriteRuleTokenStream(this.adaptor, "token RARROW");
        RewriteRuleSubtreeStream stream_lexerCommand = new RewriteRuleSubtreeStream(this.adaptor, "rule lexerCommand");
        try {
            RARROW141 = (Token)this.match(this.input, 53, FOLLOW_RARROW_in_lexerCommands2682);
            stream_RARROW.add(RARROW141);
            this.pushFollow(FOLLOW_lexerCommand_in_lexerCommands2684);
            lexerCommand142 = this.lexerCommand();
            --this.state._fsp;
            stream_lexerCommand.add(((ParserRuleReturnScope)lexerCommand142).getTree());
            block7: while (true) {
                int alt45 = 2;
                int LA45_0 = this.input.LA(1);
                if (LA45_0 == 16) {
                    alt45 = 1;
                }
                switch (alt45) {
                    case 1: {
                        COMMA143 = (Token)this.match(this.input, 16, FOLLOW_COMMA_in_lexerCommands2687);
                        stream_COMMA.add(COMMA143);
                        this.pushFollow(FOLLOW_lexerCommand_in_lexerCommands2689);
                        lexerCommand144 = this.lexerCommand();
                        --this.state._fsp;
                        stream_lexerCommand.add(((ParserRuleReturnScope)lexerCommand144).getTree());
                        continue block7;
                    }
                }
                break;
            }
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            if (!stream_lexerCommand.hasNext()) {
                throw new RewriteEarlyExitException();
            }
            while (stream_lexerCommand.hasNext()) {
                this.adaptor.addChild(root_0, stream_lexerCommand.nextTree());
            }
            stream_lexerCommand.reset();
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final lexerCommand_return lexerCommand() throws RecognitionException {
        lexerCommand_return retval = new lexerCommand_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token LPAREN146 = null;
        Token RPAREN148 = null;
        lexerCommandName_return lexerCommandName145 = null;
        lexerCommandExpr_return lexerCommandExpr147 = null;
        lexerCommandName_return lexerCommandName149 = null;
        Object LPAREN146_tree = null;
        Object RPAREN148_tree = null;
        RewriteRuleTokenStream stream_RPAREN = new RewriteRuleTokenStream(this.adaptor, "token RPAREN");
        RewriteRuleTokenStream stream_LPAREN = new RewriteRuleTokenStream(this.adaptor, "token LPAREN");
        RewriteRuleSubtreeStream stream_lexerCommandName = new RewriteRuleSubtreeStream(this.adaptor, "rule lexerCommandName");
        RewriteRuleSubtreeStream stream_lexerCommandExpr = new RewriteRuleSubtreeStream(this.adaptor, "rule lexerCommandExpr");
        try {
            int alt46 = 2;
            switch (this.input.LA(1)) {
                case 57: {
                    int LA46_1 = this.input.LA(2);
                    if (LA46_1 == 34) {
                        alt46 = 1;
                        break;
                    }
                    if (LA46_1 == 16 || LA46_1 == 43 || LA46_1 == 56 || LA46_1 == 58) {
                        alt46 = 2;
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
                    if (LA46_2 == 34) {
                        alt46 = 1;
                        break;
                    }
                    if (LA46_2 == 16 || LA46_2 == 43 || LA46_2 == 56 || LA46_2 == 58) {
                        alt46 = 2;
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
                case 36: {
                    int LA46_3 = this.input.LA(2);
                    if (LA46_3 == 34) {
                        alt46 = 1;
                        break;
                    }
                    if (LA46_3 == 16 || LA46_3 == 43 || LA46_3 == 56 || LA46_3 == 58) {
                        alt46 = 2;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 46, 3, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                default: {
                    NoViableAltException nvae = new NoViableAltException("", 46, 0, this.input);
                    throw nvae;
                }
            }
            switch (alt46) {
                case 1: {
                    this.pushFollow(FOLLOW_lexerCommandName_in_lexerCommand2707);
                    lexerCommandName145 = this.lexerCommandName();
                    --this.state._fsp;
                    stream_lexerCommandName.add(((ParserRuleReturnScope)lexerCommandName145).getTree());
                    LPAREN146 = (Token)this.match(this.input, 34, FOLLOW_LPAREN_in_lexerCommand2709);
                    stream_LPAREN.add(LPAREN146);
                    this.pushFollow(FOLLOW_lexerCommandExpr_in_lexerCommand2711);
                    lexerCommandExpr147 = this.lexerCommandExpr();
                    --this.state._fsp;
                    stream_lexerCommandExpr.add(((ParserRuleReturnScope)lexerCommandExpr147).getTree());
                    RPAREN148 = (Token)this.match(this.input, 56, FOLLOW_RPAREN_in_lexerCommand2713);
                    stream_RPAREN.add(RPAREN148);
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                    root_1 = (GrammarAST)this.adaptor.becomeRoot((GrammarAST)this.adaptor.create(86, "LEXER_ACTION_CALL"), (Object)root_1);
                    this.adaptor.addChild(root_1, stream_lexerCommandName.nextTree());
                    this.adaptor.addChild(root_1, stream_lexerCommandExpr.nextTree());
                    this.adaptor.addChild(root_0, root_1);
                    retval.tree = root_0;
                    break;
                }
                case 2: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_lexerCommandName_in_lexerCommand2728);
                    lexerCommandName149 = this.lexerCommandName();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)lexerCommandName149).getTree());
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final lexerCommandExpr_return lexerCommandExpr() throws RecognitionException {
        lexerCommandExpr_return retval = new lexerCommandExpr_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token INT151 = null;
        id_return id150 = null;
        GrammarAST INT151_tree = null;
        try {
            int alt47 = 2;
            int LA47_0 = this.input.LA(1);
            if (LA47_0 == 57 || LA47_0 == 66) {
                alt47 = 1;
            } else if (LA47_0 == 30) {
                alt47 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 47, 0, this.input);
                throw nvae;
            }
            switch (alt47) {
                case 1: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_id_in_lexerCommandExpr2739);
                    id150 = this.id();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)id150).getTree());
                    break;
                }
                case 2: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    INT151 = (Token)this.match(this.input, 30, FOLLOW_INT_in_lexerCommandExpr2744);
                    INT151_tree = (GrammarAST)this.adaptor.create(INT151);
                    this.adaptor.addChild(root_0, INT151_tree);
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final lexerCommandName_return lexerCommandName() throws RecognitionException {
        lexerCommandName_return retval = new lexerCommandName_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token MODE153 = null;
        id_return id152 = null;
        Object MODE153_tree = null;
        RewriteRuleTokenStream stream_MODE = new RewriteRuleTokenStream(this.adaptor, "token MODE");
        try {
            int alt48 = 2;
            int LA48_0 = this.input.LA(1);
            if (LA48_0 == 57 || LA48_0 == 66) {
                alt48 = 1;
            } else if (LA48_0 == 36) {
                alt48 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 48, 0, this.input);
                throw nvae;
            }
            switch (alt48) {
                case 1: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_id_in_lexerCommandName2768);
                    id152 = this.id();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)id152).getTree());
                    break;
                }
                case 2: {
                    MODE153 = (Token)this.match(this.input, 36, FOLLOW_MODE_in_lexerCommandName2786);
                    stream_MODE.add(MODE153);
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(28, MODE153));
                    retval.tree = root_0;
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final altList_return altList() throws RecognitionException {
        altList_return retval = new altList_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token OR155 = null;
        alternative_return alternative154 = null;
        alternative_return alternative156 = null;
        Object OR155_tree = null;
        RewriteRuleTokenStream stream_OR = new RewriteRuleTokenStream(this.adaptor, "token OR");
        RewriteRuleSubtreeStream stream_alternative = new RewriteRuleSubtreeStream(this.adaptor, "rule alternative");
        try {
            this.pushFollow(FOLLOW_alternative_in_altList2814);
            alternative154 = this.alternative();
            --this.state._fsp;
            stream_alternative.add(((ParserRuleReturnScope)alternative154).getTree());
            block7: while (true) {
                int alt49 = 2;
                int LA49_0 = this.input.LA(1);
                if (LA49_0 == 43) {
                    alt49 = 1;
                }
                switch (alt49) {
                    case 1: {
                        OR155 = (Token)this.match(this.input, 43, FOLLOW_OR_in_altList2817);
                        stream_OR.add(OR155);
                        this.pushFollow(FOLLOW_alternative_in_altList2819);
                        alternative156 = this.alternative();
                        --this.state._fsp;
                        stream_alternative.add(((ParserRuleReturnScope)alternative156).getTree());
                        continue block7;
                    }
                }
                break;
            }
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            if (!stream_alternative.hasNext()) {
                throw new RewriteEarlyExitException();
            }
            while (stream_alternative.hasNext()) {
                this.adaptor.addChild(root_0, stream_alternative.nextTree());
            }
            stream_alternative.reset();
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final alternative_return alternative() throws RecognitionException {
        alternative_return retval = new alternative_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        ArrayList<Object> list_e = null;
        elementOptions_return o = null;
        element_return e = null;
        RewriteRuleSubtreeStream stream_element = new RewriteRuleSubtreeStream(this.adaptor, "rule element");
        RewriteRuleSubtreeStream stream_elementOptions = new RewriteRuleSubtreeStream(this.adaptor, "rule elementOptions");
        this.paraphrases.push("matching alternative");
        try {
            int alt50 = 2;
            int LA50_0 = this.input.LA(1);
            if (LA50_0 == 35) {
                alt50 = 1;
            }
            switch (alt50) {
                case 1: {
                    this.pushFollow(FOLLOW_elementOptions_in_alternative2853);
                    o = this.elementOptions();
                    --this.state._fsp;
                    stream_elementOptions.add(((ParserRuleReturnScope)o).getTree());
                }
            }
            int alt52 = 2;
            int LA52_0 = this.input.LA(1);
            if (LA52_0 == 4 || LA52_0 == 20 || LA52_0 == 34 || LA52_0 == 39 || LA52_0 == 57 || LA52_0 == 59 || LA52_0 == 62 || LA52_0 == 66) {
                alt52 = 1;
            } else if (LA52_0 == -1 || LA52_0 == 43 || LA52_0 == 47 || LA52_0 == 56 || LA52_0 == 58) {
                alt52 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 52, 0, this.input);
                throw nvae;
            }
            switch (alt52) {
                case 1: {
                    int cnt51 = 0;
                    block14: while (true) {
                        int alt51 = 2;
                        int LA51_0 = this.input.LA(1);
                        if (LA51_0 == 4 || LA51_0 == 20 || LA51_0 == 34 || LA51_0 == 39 || LA51_0 == 57 || LA51_0 == 59 || LA51_0 == 62 || LA51_0 == 66) {
                            alt51 = 1;
                        }
                        switch (alt51) {
                            case 1: {
                                this.pushFollow(FOLLOW_element_in_alternative2862);
                                e = this.element();
                                --this.state._fsp;
                                stream_element.add(((RuleReturnScope)e).getTree());
                                if (list_e == null) {
                                    list_e = new ArrayList<Object>();
                                }
                                list_e.add(((RuleReturnScope)e).getTree());
                                break;
                            }
                            default: {
                                if (cnt51 >= 1) break block14;
                                EarlyExitException eee = new EarlyExitException(51, this.input);
                                throw eee;
                            }
                        }
                        ++cnt51;
                    }
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    RewriteRuleSubtreeStream stream_e = new RewriteRuleSubtreeStream(this.adaptor, "token e", list_e);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                    root_1 = (GrammarAST)this.adaptor.becomeRoot(new AltAST(74), (Object)root_1);
                    if (stream_elementOptions.hasNext()) {
                        this.adaptor.addChild(root_1, stream_elementOptions.nextTree());
                    }
                    stream_elementOptions.reset();
                    if (!stream_e.hasNext()) {
                        throw new RewriteEarlyExitException();
                    }
                    while (stream_e.hasNext()) {
                        this.adaptor.addChild(root_1, stream_e.nextTree());
                    }
                    stream_e.reset();
                    this.adaptor.addChild(root_0, root_1);
                    retval.tree = root_0;
                    break;
                }
                case 2: {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                    root_1 = (GrammarAST)this.adaptor.becomeRoot(new AltAST(74), (Object)root_1);
                    if (stream_elementOptions.hasNext()) {
                        this.adaptor.addChild(root_1, stream_elementOptions.nextTree());
                    }
                    stream_elementOptions.reset();
                    this.adaptor.addChild(root_1, (GrammarAST)this.adaptor.create(83, "EPSILON"));
                    this.adaptor.addChild(root_0, root_1);
                    retval.tree = root_0;
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            this.paraphrases.pop();
            Grammar.setNodeOptions(retval.tree, o != null ? (GrammarAST)((ParserRuleReturnScope)o).getTree() : null);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final element_return element() throws RecognitionException {
        element_return retval = new element_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        labeledElement_return labeledElement157 = null;
        ebnfSuffix_return ebnfSuffix158 = null;
        atom_return atom159 = null;
        ebnfSuffix_return ebnfSuffix160 = null;
        ebnf_return ebnf161 = null;
        actionElement_return actionElement162 = null;
        RewriteRuleSubtreeStream stream_atom = new RewriteRuleSubtreeStream(this.adaptor, "rule atom");
        RewriteRuleSubtreeStream stream_ebnfSuffix = new RewriteRuleSubtreeStream(this.adaptor, "rule ebnfSuffix");
        RewriteRuleSubtreeStream stream_labeledElement = new RewriteRuleSubtreeStream(this.adaptor, "rule labeledElement");
        this.paraphrases.push("looking for rule element");
        int m = this.input.mark();
        try {
            int alt55 = 4;
            switch (this.input.LA(1)) {
                case 57: {
                    int LA55_1 = this.input.LA(2);
                    if (LA55_1 == 10 || LA55_1 == 46) {
                        alt55 = 1;
                        break;
                    }
                    if (LA55_1 == -1 || LA55_1 == 4 || LA55_1 == 8 || LA55_1 == 20 || LA55_1 >= 34 && LA55_1 <= 35 || LA55_1 == 39 || LA55_1 == 43 || LA55_1 == 45 || LA55_1 == 47 || LA55_1 == 51 || LA55_1 >= 56 && LA55_1 <= 59 || LA55_1 >= 61 && LA55_1 <= 62 || LA55_1 == 66) {
                        alt55 = 2;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 55, 1, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 66: {
                    int LA55_2 = this.input.LA(2);
                    if (LA55_2 == 10 || LA55_2 == 46) {
                        alt55 = 1;
                        break;
                    }
                    if (LA55_2 == -1 || LA55_2 == 4 || LA55_2 == 20 || LA55_2 >= 34 && LA55_2 <= 35 || LA55_2 == 39 || LA55_2 == 43 || LA55_2 == 45 || LA55_2 == 47 || LA55_2 == 51 || LA55_2 >= 56 && LA55_2 <= 59 || LA55_2 >= 61 && LA55_2 <= 62 || LA55_2 == 66) {
                        alt55 = 2;
                        break;
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
                case 20: 
                case 39: 
                case 62: {
                    alt55 = 2;
                    break;
                }
                case 34: {
                    alt55 = 3;
                    break;
                }
                case 4: 
                case 59: {
                    alt55 = 4;
                    break;
                }
                default: {
                    NoViableAltException nvae = new NoViableAltException("", 55, 0, this.input);
                    throw nvae;
                }
            }
            switch (alt55) {
                case 1: {
                    this.pushFollow(FOLLOW_labeledElement_in_element2977);
                    labeledElement157 = this.labeledElement();
                    --this.state._fsp;
                    stream_labeledElement.add(((ParserRuleReturnScope)labeledElement157).getTree());
                    int alt53 = 2;
                    int LA53_0 = this.input.LA(1);
                    if (LA53_0 == 45 || LA53_0 == 51 || LA53_0 == 61) {
                        alt53 = 1;
                    } else if (LA53_0 == -1 || LA53_0 == 4 || LA53_0 == 20 || LA53_0 == 34 || LA53_0 == 39 || LA53_0 == 43 || LA53_0 == 47 || LA53_0 >= 56 && LA53_0 <= 59 || LA53_0 == 62 || LA53_0 == 66) {
                        alt53 = 2;
                    } else {
                        NoViableAltException nvae = new NoViableAltException("", 53, 0, this.input);
                        throw nvae;
                    }
                    switch (alt53) {
                        case 1: {
                            this.pushFollow(FOLLOW_ebnfSuffix_in_element2983);
                            ebnfSuffix158 = this.ebnfSuffix();
                            --this.state._fsp;
                            stream_ebnfSuffix.add(((ParserRuleReturnScope)ebnfSuffix158).getTree());
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                            root_0 = (GrammarAST)this.adaptor.nil();
                            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                            root_1 = (GrammarAST)this.adaptor.becomeRoot(stream_ebnfSuffix.nextNode(), (Object)root_1);
                            GrammarAST root_2 = (GrammarAST)this.adaptor.nil();
                            root_2 = (GrammarAST)this.adaptor.becomeRoot(new BlockAST(78, labeledElement157 != null ? labeledElement157.start : null, "BLOCK"), (Object)root_2);
                            GrammarAST root_3 = (GrammarAST)this.adaptor.nil();
                            root_3 = (GrammarAST)this.adaptor.becomeRoot(new AltAST(74), (Object)root_3);
                            this.adaptor.addChild(root_3, stream_labeledElement.nextTree());
                            this.adaptor.addChild(root_2, root_3);
                            this.adaptor.addChild(root_1, root_2);
                            this.adaptor.addChild(root_0, root_1);
                            retval.tree = root_0;
                            break;
                        }
                        case 2: {
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                            root_0 = (GrammarAST)this.adaptor.nil();
                            this.adaptor.addChild(root_0, stream_labeledElement.nextTree());
                            retval.tree = root_0;
                        }
                    }
                    break;
                }
                case 2: {
                    this.pushFollow(FOLLOW_atom_in_element3029);
                    atom159 = this.atom();
                    --this.state._fsp;
                    stream_atom.add(((ParserRuleReturnScope)atom159).getTree());
                    int alt54 = 2;
                    int LA54_0 = this.input.LA(1);
                    if (LA54_0 == 45 || LA54_0 == 51 || LA54_0 == 61) {
                        alt54 = 1;
                    } else if (LA54_0 == -1 || LA54_0 == 4 || LA54_0 == 20 || LA54_0 == 34 || LA54_0 == 39 || LA54_0 == 43 || LA54_0 == 47 || LA54_0 >= 56 && LA54_0 <= 59 || LA54_0 == 62 || LA54_0 == 66) {
                        alt54 = 2;
                    } else {
                        NoViableAltException nvae = new NoViableAltException("", 54, 0, this.input);
                        throw nvae;
                    }
                    switch (alt54) {
                        case 1: {
                            this.pushFollow(FOLLOW_ebnfSuffix_in_element3035);
                            ebnfSuffix160 = this.ebnfSuffix();
                            --this.state._fsp;
                            stream_ebnfSuffix.add(((ParserRuleReturnScope)ebnfSuffix160).getTree());
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                            root_0 = (GrammarAST)this.adaptor.nil();
                            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                            root_1 = (GrammarAST)this.adaptor.becomeRoot(stream_ebnfSuffix.nextNode(), (Object)root_1);
                            GrammarAST root_2 = (GrammarAST)this.adaptor.nil();
                            root_2 = (GrammarAST)this.adaptor.becomeRoot(new BlockAST(78, atom159 != null ? atom159.start : null, "BLOCK"), (Object)root_2);
                            GrammarAST root_3 = (GrammarAST)this.adaptor.nil();
                            root_3 = (GrammarAST)this.adaptor.becomeRoot(new AltAST(74), (Object)root_3);
                            this.adaptor.addChild(root_3, stream_atom.nextTree());
                            this.adaptor.addChild(root_2, root_3);
                            this.adaptor.addChild(root_1, root_2);
                            this.adaptor.addChild(root_0, root_1);
                            retval.tree = root_0;
                            break;
                        }
                        case 2: {
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                            root_0 = (GrammarAST)this.adaptor.nil();
                            this.adaptor.addChild(root_0, stream_atom.nextTree());
                            retval.tree = root_0;
                        }
                    }
                    break;
                }
                case 3: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_ebnf_in_element3081);
                    ebnf161 = this.ebnf();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)ebnf161).getTree());
                    break;
                }
                case 4: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_actionElement_in_element3086);
                    actionElement162 = this.actionElement();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)actionElement162).getTree());
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            this.paraphrases.pop();
        }
        catch (RecognitionException re) {
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
            int ttype = this.input.get(this.input.range()).getType();
            if (ttype == 14 || ttype == 55 || ttype == 12 || ttype == 23 || ttype == 11) {
                v4ParserException missingSemi = new v4ParserException("unterminated rule (missing ';') detected at '" + this.input.LT(1).getText() + " " + this.input.LT(2).getText() + "'", this.input);
                this.reportError(missingSemi);
                if (ttype == 12 || ttype == 23) {
                    this.input.seek(this.input.range());
                }
                if (ttype == 55 || ttype == 11) {
                    int p = this.input.index();
                    Token t = this.input.get(p);
                    while (t.getType() != 57 && t.getType() != 66) {
                        t = this.input.get(--p);
                    }
                    this.input.seek(p);
                }
                throw new ResyncToEndOfRuleBlock();
            }
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
        GrammarAST root_0 = null;
        Token ACTION163 = null;
        Token ACTION164 = null;
        Token SEMPRED166 = null;
        Token SEMPRED167 = null;
        elementOptions_return elementOptions165 = null;
        elementOptions_return elementOptions168 = null;
        ActionAST ACTION163_tree = null;
        Object ACTION164_tree = null;
        PredAST SEMPRED166_tree = null;
        Object SEMPRED167_tree = null;
        RewriteRuleTokenStream stream_SEMPRED = new RewriteRuleTokenStream(this.adaptor, "token SEMPRED");
        RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
        RewriteRuleSubtreeStream stream_elementOptions = new RewriteRuleSubtreeStream(this.adaptor, "rule elementOptions");
        try {
            int alt56;
            block23: {
                int LA56_0;
                block24: {
                    alt56 = 4;
                    LA56_0 = this.input.LA(1);
                    if (LA56_0 != 4) break block24;
                    int LA56_1 = this.input.LA(2);
                    if (LA56_1 == -1 || LA56_1 == 4 || LA56_1 == 20 || LA56_1 == 32 || LA56_1 == 34 || LA56_1 == 39 || LA56_1 == 43 || LA56_1 == 47 || LA56_1 == 53 || LA56_1 >= 56 && LA56_1 <= 59 || LA56_1 == 62 || LA56_1 == 66) {
                        alt56 = 1;
                        break block23;
                    } else if (LA56_1 == 35) {
                        alt56 = 2;
                        break block23;
                    } else {
                        int nvaeMark = this.input.mark();
                        try {
                            this.input.consume();
                            NoViableAltException nvae = new NoViableAltException("", 56, 1, this.input);
                            throw nvae;
                        }
                        catch (Throwable throwable) {
                            this.input.rewind(nvaeMark);
                            throw throwable;
                        }
                    }
                }
                if (LA56_0 != 59) {
                    NoViableAltException nvae = new NoViableAltException("", 56, 0, this.input);
                    throw nvae;
                }
                int LA56_2 = this.input.LA(2);
                if (LA56_2 == -1 || LA56_2 == 4 || LA56_2 == 20 || LA56_2 == 32 || LA56_2 == 34 || LA56_2 == 39 || LA56_2 == 43 || LA56_2 == 47 || LA56_2 == 53 || LA56_2 >= 56 && LA56_2 <= 59 || LA56_2 == 62 || LA56_2 == 66) {
                    alt56 = 3;
                } else if (LA56_2 == 35) {
                    alt56 = 4;
                } else {
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 56, 2, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
            }
            switch (alt56) {
                case 1: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    ACTION163 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_actionElement3112);
                    ACTION163_tree = new ActionAST(ACTION163);
                    this.adaptor.addChild(root_0, ACTION163_tree);
                    break;
                }
                case 2: {
                    ACTION164 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_actionElement3122);
                    stream_ACTION.add(ACTION164);
                    this.pushFollow(FOLLOW_elementOptions_in_actionElement3124);
                    elementOptions165 = this.elementOptions();
                    --this.state._fsp;
                    stream_elementOptions.add(((ParserRuleReturnScope)elementOptions165).getTree());
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                    root_1 = (GrammarAST)this.adaptor.becomeRoot(new ActionAST(stream_ACTION.nextToken()), (Object)root_1);
                    this.adaptor.addChild(root_1, stream_elementOptions.nextTree());
                    this.adaptor.addChild(root_0, root_1);
                    retval.tree = root_0;
                    break;
                }
                case 3: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    SEMPRED166 = (Token)this.match(this.input, 59, FOLLOW_SEMPRED_in_actionElement3142);
                    SEMPRED166_tree = new PredAST(SEMPRED166);
                    this.adaptor.addChild(root_0, SEMPRED166_tree);
                    break;
                }
                case 4: {
                    SEMPRED167 = (Token)this.match(this.input, 59, FOLLOW_SEMPRED_in_actionElement3152);
                    stream_SEMPRED.add(SEMPRED167);
                    this.pushFollow(FOLLOW_elementOptions_in_actionElement3154);
                    elementOptions168 = this.elementOptions();
                    --this.state._fsp;
                    stream_elementOptions.add(((ParserRuleReturnScope)elementOptions168).getTree());
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                    root_1 = (GrammarAST)this.adaptor.becomeRoot(new PredAST(stream_SEMPRED.nextToken()), (Object)root_1);
                    this.adaptor.addChild(root_1, stream_elementOptions.nextTree());
                    this.adaptor.addChild(root_0, root_1);
                    retval.tree = root_0;
                    break;
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            GrammarAST options = (GrammarAST)retval.tree.getFirstChildWithType(82);
            if (options == null) return retval;
            Grammar.setNodeOptions(retval.tree, options);
            return retval;
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
            return retval;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final labeledElement_return labeledElement() throws RecognitionException {
        labeledElement_return retval = new labeledElement_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token ass = null;
        id_return id169 = null;
        atom_return atom170 = null;
        block_return block171 = null;
        Object ass_tree = null;
        RewriteRuleTokenStream stream_ASSIGN = new RewriteRuleTokenStream(this.adaptor, "token ASSIGN");
        RewriteRuleTokenStream stream_PLUS_ASSIGN = new RewriteRuleTokenStream(this.adaptor, "token PLUS_ASSIGN");
        RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
        RewriteRuleSubtreeStream stream_atom = new RewriteRuleSubtreeStream(this.adaptor, "rule atom");
        RewriteRuleSubtreeStream stream_block = new RewriteRuleSubtreeStream(this.adaptor, "rule block");
        try {
            this.pushFollow(FOLLOW_id_in_labeledElement3176);
            id169 = this.id();
            --this.state._fsp;
            stream_id.add(((ParserRuleReturnScope)id169).getTree());
            int alt57 = 2;
            int LA57_0 = this.input.LA(1);
            if (LA57_0 == 10) {
                alt57 = 1;
            } else if (LA57_0 == 46) {
                alt57 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 57, 0, this.input);
                throw nvae;
            }
            switch (alt57) {
                case 1: {
                    ass = (Token)this.match(this.input, 10, FOLLOW_ASSIGN_in_labeledElement3181);
                    stream_ASSIGN.add(ass);
                    break;
                }
                case 2: {
                    ass = (Token)this.match(this.input, 46, FOLLOW_PLUS_ASSIGN_in_labeledElement3185);
                    stream_PLUS_ASSIGN.add(ass);
                }
            }
            int alt58 = 2;
            int LA58_0 = this.input.LA(1);
            if (LA58_0 == 20 || LA58_0 == 39 || LA58_0 == 57 || LA58_0 == 62 || LA58_0 == 66) {
                alt58 = 1;
            } else if (LA58_0 == 34) {
                alt58 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 58, 0, this.input);
                throw nvae;
            }
            switch (alt58) {
                case 1: {
                    this.pushFollow(FOLLOW_atom_in_labeledElement3192);
                    atom170 = this.atom();
                    --this.state._fsp;
                    stream_atom.add(((ParserRuleReturnScope)atom170).getTree());
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_ass = new RewriteRuleTokenStream(this.adaptor, "token ass", ass);
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                    root_1 = (GrammarAST)this.adaptor.becomeRoot(stream_ass.nextNode(), (Object)root_1);
                    this.adaptor.addChild(root_1, stream_id.nextTree());
                    this.adaptor.addChild(root_1, stream_atom.nextTree());
                    this.adaptor.addChild(root_0, root_1);
                    retval.tree = root_0;
                    break;
                }
                case 2: {
                    this.pushFollow(FOLLOW_block_in_labeledElement3214);
                    block171 = this.block();
                    --this.state._fsp;
                    stream_block.add(((ParserRuleReturnScope)block171).getTree());
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_ass = new RewriteRuleTokenStream(this.adaptor, "token ass", ass);
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                    root_1 = (GrammarAST)this.adaptor.becomeRoot(stream_ass.nextNode(), (Object)root_1);
                    this.adaptor.addChild(root_1, stream_id.nextTree());
                    this.adaptor.addChild(root_1, stream_block.nextTree());
                    this.adaptor.addChild(root_0, root_1);
                    retval.tree = root_0;
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final ebnf_return ebnf() throws RecognitionException {
        ebnf_return retval = new ebnf_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        block_return block172 = null;
        blockSuffix_return blockSuffix173 = null;
        RewriteRuleSubtreeStream stream_block = new RewriteRuleSubtreeStream(this.adaptor, "rule block");
        RewriteRuleSubtreeStream stream_blockSuffix = new RewriteRuleSubtreeStream(this.adaptor, "rule blockSuffix");
        try {
            this.pushFollow(FOLLOW_block_in_ebnf3250);
            block172 = this.block();
            --this.state._fsp;
            stream_block.add(((ParserRuleReturnScope)block172).getTree());
            int alt59 = 2;
            int LA59_0 = this.input.LA(1);
            if (LA59_0 == 45 || LA59_0 == 51 || LA59_0 == 61) {
                alt59 = 1;
            } else if (LA59_0 == -1 || LA59_0 == 4 || LA59_0 == 20 || LA59_0 == 34 || LA59_0 == 39 || LA59_0 == 43 || LA59_0 == 47 || LA59_0 >= 56 && LA59_0 <= 59 || LA59_0 == 62 || LA59_0 == 66) {
                alt59 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 59, 0, this.input);
                throw nvae;
            }
            switch (alt59) {
                case 1: {
                    this.pushFollow(FOLLOW_blockSuffix_in_ebnf3274);
                    blockSuffix173 = this.blockSuffix();
                    --this.state._fsp;
                    stream_blockSuffix.add(((ParserRuleReturnScope)blockSuffix173).getTree());
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                    root_1 = (GrammarAST)this.adaptor.becomeRoot(stream_blockSuffix.nextNode(), (Object)root_1);
                    this.adaptor.addChild(root_1, stream_block.nextTree());
                    this.adaptor.addChild(root_0, root_1);
                    retval.tree = root_0;
                    break;
                }
                case 2: {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.adaptor.addChild(root_0, stream_block.nextTree());
                    retval.tree = root_0;
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final blockSuffix_return blockSuffix() throws RecognitionException {
        blockSuffix_return retval = new blockSuffix_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        ebnfSuffix_return ebnfSuffix174 = null;
        try {
            root_0 = (GrammarAST)this.adaptor.nil();
            this.pushFollow(FOLLOW_ebnfSuffix_in_blockSuffix3324);
            ebnfSuffix174 = this.ebnfSuffix();
            --this.state._fsp;
            this.adaptor.addChild(root_0, ((ParserRuleReturnScope)ebnfSuffix174).getTree());
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final ebnfSuffix_return ebnfSuffix() throws RecognitionException {
        ebnfSuffix_return retval = new ebnfSuffix_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token nongreedy = null;
        Token QUESTION175 = null;
        Token STAR176 = null;
        Token PLUS177 = null;
        Object nongreedy_tree = null;
        Object QUESTION175_tree = null;
        Object STAR176_tree = null;
        Object PLUS177_tree = null;
        RewriteRuleTokenStream stream_PLUS = new RewriteRuleTokenStream(this.adaptor, "token PLUS");
        RewriteRuleTokenStream stream_STAR = new RewriteRuleTokenStream(this.adaptor, "token STAR");
        RewriteRuleTokenStream stream_QUESTION = new RewriteRuleTokenStream(this.adaptor, "token QUESTION");
        try {
            int alt63 = 3;
            switch (this.input.LA(1)) {
                case 51: {
                    alt63 = 1;
                    break;
                }
                case 61: {
                    alt63 = 2;
                    break;
                }
                case 45: {
                    alt63 = 3;
                    break;
                }
                default: {
                    NoViableAltException nvae = new NoViableAltException("", 63, 0, this.input);
                    throw nvae;
                }
            }
            switch (alt63) {
                case 1: {
                    QUESTION175 = (Token)this.match(this.input, 51, FOLLOW_QUESTION_in_ebnfSuffix3339);
                    stream_QUESTION.add(QUESTION175);
                    int alt60 = 2;
                    int LA60_0 = this.input.LA(1);
                    if (LA60_0 == 51) {
                        alt60 = 1;
                    }
                    switch (alt60) {
                        case 1: {
                            nongreedy = (Token)this.match(this.input, 51, FOLLOW_QUESTION_in_ebnfSuffix3343);
                            stream_QUESTION.add(nongreedy);
                        }
                    }
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.adaptor.addChild(root_0, new OptionalBlockAST(89, retval.start, nongreedy));
                    retval.tree = root_0;
                    break;
                }
                case 2: {
                    STAR176 = (Token)this.match(this.input, 61, FOLLOW_STAR_in_ebnfSuffix3359);
                    stream_STAR.add(STAR176);
                    int alt61 = 2;
                    int LA61_0 = this.input.LA(1);
                    if (LA61_0 == 51) {
                        alt61 = 1;
                    }
                    switch (alt61) {
                        case 1: {
                            nongreedy = (Token)this.match(this.input, 51, FOLLOW_QUESTION_in_ebnfSuffix3363);
                            stream_QUESTION.add(nongreedy);
                        }
                    }
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.adaptor.addChild(root_0, new StarBlockAST(80, retval.start, nongreedy));
                    retval.tree = root_0;
                    break;
                }
                case 3: {
                    PLUS177 = (Token)this.match(this.input, 45, FOLLOW_PLUS_in_ebnfSuffix3381);
                    stream_PLUS.add(PLUS177);
                    int alt62 = 2;
                    int LA62_0 = this.input.LA(1);
                    if (LA62_0 == 51) {
                        alt62 = 1;
                    }
                    switch (alt62) {
                        case 1: {
                            nongreedy = (Token)this.match(this.input, 51, FOLLOW_QUESTION_in_ebnfSuffix3385);
                            stream_QUESTION.add(nongreedy);
                        }
                    }
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.adaptor.addChild(root_0, new PlusBlockAST(90, retval.start, nongreedy));
                    retval.tree = root_0;
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final lexerAtom_return lexerAtom() throws RecognitionException {
        lexerAtom_return retval = new lexerAtom_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token RULE_REF180 = null;
        Token LEXER_CHAR_SET183 = null;
        range_return range178 = null;
        terminal_return terminal179 = null;
        notSet_return notSet181 = null;
        wildcard_return wildcard182 = null;
        RuleRefAST RULE_REF180_tree = null;
        GrammarAST LEXER_CHAR_SET183_tree = null;
        try {
            int alt64 = 6;
            switch (this.input.LA(1)) {
                case 62: {
                    int LA64_1 = this.input.LA(2);
                    if (LA64_1 == 52) {
                        alt64 = 1;
                        break;
                    }
                    if (LA64_1 == 4 || LA64_1 == 20 || LA64_1 == 32 || LA64_1 >= 34 && LA64_1 <= 35 || LA64_1 == 39 || LA64_1 == 43 || LA64_1 == 45 || LA64_1 == 51 || LA64_1 == 53 || LA64_1 >= 56 && LA64_1 <= 59 || LA64_1 >= 61 && LA64_1 <= 62 || LA64_1 == 66) {
                        alt64 = 2;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 64, 1, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 66: {
                    alt64 = 2;
                    break;
                }
                case 57: {
                    alt64 = 3;
                    break;
                }
                case 39: {
                    alt64 = 4;
                    break;
                }
                case 20: {
                    alt64 = 5;
                    break;
                }
                case 32: {
                    alt64 = 6;
                    break;
                }
                default: {
                    NoViableAltException nvae = new NoViableAltException("", 64, 0, this.input);
                    throw nvae;
                }
            }
            switch (alt64) {
                case 1: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_range_in_lexerAtom3406);
                    range178 = this.range();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)range178).getTree());
                    break;
                }
                case 2: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_terminal_in_lexerAtom3411);
                    terminal179 = this.terminal();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)terminal179).getTree());
                    break;
                }
                case 3: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    RULE_REF180 = (Token)this.match(this.input, 57, FOLLOW_RULE_REF_in_lexerAtom3421);
                    RULE_REF180_tree = new RuleRefAST(RULE_REF180);
                    this.adaptor.addChild(root_0, RULE_REF180_tree);
                    break;
                }
                case 4: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_notSet_in_lexerAtom3432);
                    notSet181 = this.notSet();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)notSet181).getTree());
                    break;
                }
                case 5: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_wildcard_in_lexerAtom3440);
                    wildcard182 = this.wildcard();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)wildcard182).getTree());
                    break;
                }
                case 6: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    LEXER_CHAR_SET183 = (Token)this.match(this.input, 32, FOLLOW_LEXER_CHAR_SET_in_lexerAtom3448);
                    LEXER_CHAR_SET183_tree = (GrammarAST)this.adaptor.create(LEXER_CHAR_SET183);
                    this.adaptor.addChild(root_0, LEXER_CHAR_SET183_tree);
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    public final atom_return atom() throws RecognitionException {
        atom_return retval = new atom_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        range_return range184 = null;
        terminal_return terminal185 = null;
        ruleref_return ruleref186 = null;
        notSet_return notSet187 = null;
        wildcard_return wildcard188 = null;
        int alt65 = 5;
        switch (this.input.LA(1)) {
            case 62: {
                int LA65_1 = this.input.LA(2);
                if (LA65_1 == 52) {
                    alt65 = 1;
                    break;
                }
                if (LA65_1 == -1 || LA65_1 == 4 || LA65_1 == 20 || LA65_1 >= 34 && LA65_1 <= 35 || LA65_1 == 39 || LA65_1 == 43 || LA65_1 == 45 || LA65_1 == 47 || LA65_1 == 51 || LA65_1 >= 56 && LA65_1 <= 59 || LA65_1 >= 61 && LA65_1 <= 62 || LA65_1 == 66) {
                    alt65 = 2;
                    break;
                }
                int nvaeMark = this.input.mark();
                try {
                    this.input.consume();
                    NoViableAltException nvae = new NoViableAltException("", 65, 1, this.input);
                    throw nvae;
                }
                catch (Throwable throwable) {
                    this.input.rewind(nvaeMark);
                    throw throwable;
                }
            }
            case 66: {
                alt65 = 2;
                break;
            }
            case 57: {
                alt65 = 3;
                break;
            }
            case 39: {
                alt65 = 4;
                break;
            }
            case 20: {
                alt65 = 5;
                break;
            }
            default: {
                NoViableAltException nvae = new NoViableAltException("", 65, 0, this.input);
                throw nvae;
            }
        }
        switch (alt65) {
            case 1: {
                root_0 = (GrammarAST)this.adaptor.nil();
                this.pushFollow(FOLLOW_range_in_atom3493);
                range184 = this.range();
                --this.state._fsp;
                this.adaptor.addChild(root_0, ((ParserRuleReturnScope)range184).getTree());
                break;
            }
            case 2: {
                root_0 = (GrammarAST)this.adaptor.nil();
                this.pushFollow(FOLLOW_terminal_in_atom3500);
                terminal185 = this.terminal();
                --this.state._fsp;
                this.adaptor.addChild(root_0, ((ParserRuleReturnScope)terminal185).getTree());
                break;
            }
            case 3: {
                root_0 = (GrammarAST)this.adaptor.nil();
                this.pushFollow(FOLLOW_ruleref_in_atom3510);
                ruleref186 = this.ruleref();
                --this.state._fsp;
                this.adaptor.addChild(root_0, ((ParserRuleReturnScope)ruleref186).getTree());
                break;
            }
            case 4: {
                root_0 = (GrammarAST)this.adaptor.nil();
                this.pushFollow(FOLLOW_notSet_in_atom3518);
                notSet187 = this.notSet();
                --this.state._fsp;
                this.adaptor.addChild(root_0, ((ParserRuleReturnScope)notSet187).getTree());
                break;
            }
            case 5: {
                root_0 = (GrammarAST)this.adaptor.nil();
                this.pushFollow(FOLLOW_wildcard_in_atom3526);
                wildcard188 = this.wildcard();
                --this.state._fsp;
                this.adaptor.addChild(root_0, ((ParserRuleReturnScope)wildcard188).getTree());
            }
        }
        retval.stop = this.input.LT(-1);
        retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
        this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final wildcard_return wildcard() throws RecognitionException {
        wildcard_return retval = new wildcard_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token DOT189 = null;
        elementOptions_return elementOptions190 = null;
        Object DOT189_tree = null;
        RewriteRuleTokenStream stream_DOT = new RewriteRuleTokenStream(this.adaptor, "token DOT");
        RewriteRuleSubtreeStream stream_elementOptions = new RewriteRuleSubtreeStream(this.adaptor, "rule elementOptions");
        try {
            DOT189 = (Token)this.match(this.input, 20, FOLLOW_DOT_in_wildcard3574);
            stream_DOT.add(DOT189);
            int alt66 = 2;
            int LA66_0 = this.input.LA(1);
            if (LA66_0 == 35) {
                alt66 = 1;
            }
            switch (alt66) {
                case 1: {
                    this.pushFollow(FOLLOW_elementOptions_in_wildcard3576);
                    elementOptions190 = this.elementOptions();
                    --this.state._fsp;
                    stream_elementOptions.add(((ParserRuleReturnScope)elementOptions190).getTree());
                }
            }
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
            root_1 = (GrammarAST)this.adaptor.becomeRoot(new TerminalAST(100, DOT189), (Object)root_1);
            if (stream_elementOptions.hasNext()) {
                this.adaptor.addChild(root_1, stream_elementOptions.nextTree());
            }
            stream_elementOptions.reset();
            this.adaptor.addChild(root_0, root_1);
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            GrammarAST options = (GrammarAST)retval.tree.getFirstChildWithType(82);
            if (options != null) {
                Grammar.setNodeOptions(retval.tree, options);
            }
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final notSet_return notSet() throws RecognitionException {
        notSet_return retval = new notSet_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token NOT191 = null;
        Token NOT193 = null;
        setElement_return setElement192 = null;
        blockSet_return blockSet194 = null;
        Object NOT191_tree = null;
        Object NOT193_tree = null;
        RewriteRuleTokenStream stream_NOT = new RewriteRuleTokenStream(this.adaptor, "token NOT");
        RewriteRuleSubtreeStream stream_setElement = new RewriteRuleSubtreeStream(this.adaptor, "rule setElement");
        RewriteRuleSubtreeStream stream_blockSet = new RewriteRuleSubtreeStream(this.adaptor, "rule blockSet");
        try {
            int alt67 = 2;
            int LA67_0 = this.input.LA(1);
            if (LA67_0 != 39) {
                NoViableAltException nvae = new NoViableAltException("", 67, 0, this.input);
                throw nvae;
            }
            int LA67_1 = this.input.LA(2);
            if (LA67_1 == 32 || LA67_1 == 62 || LA67_1 == 66) {
                alt67 = 1;
            } else if (LA67_1 == 34) {
                alt67 = 2;
            } else {
                int nvaeMark = this.input.mark();
                try {
                    this.input.consume();
                    NoViableAltException nvae = new NoViableAltException("", 67, 1, this.input);
                    throw nvae;
                }
                catch (Throwable throwable) {
                    this.input.rewind(nvaeMark);
                    throw throwable;
                }
            }
            switch (alt67) {
                case 1: {
                    NOT191 = (Token)this.match(this.input, 39, FOLLOW_NOT_in_notSet3614);
                    stream_NOT.add(NOT191);
                    this.pushFollow(FOLLOW_setElement_in_notSet3616);
                    setElement192 = this.setElement();
                    --this.state._fsp;
                    stream_setElement.add(((ParserRuleReturnScope)setElement192).getTree());
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                    root_1 = (GrammarAST)this.adaptor.becomeRoot(new NotAST(39, NOT191), (Object)root_1);
                    GrammarAST root_2 = (GrammarAST)this.adaptor.nil();
                    root_2 = (GrammarAST)this.adaptor.becomeRoot(new SetAST(98, setElement192 != null ? setElement192.start : null, "SET"), (Object)root_2);
                    this.adaptor.addChild(root_2, stream_setElement.nextTree());
                    this.adaptor.addChild(root_1, root_2);
                    this.adaptor.addChild(root_0, root_1);
                    retval.tree = root_0;
                    break;
                }
                case 2: {
                    NOT193 = (Token)this.match(this.input, 39, FOLLOW_NOT_in_notSet3644);
                    stream_NOT.add(NOT193);
                    this.pushFollow(FOLLOW_blockSet_in_notSet3646);
                    blockSet194 = this.blockSet();
                    --this.state._fsp;
                    stream_blockSet.add(((ParserRuleReturnScope)blockSet194).getTree());
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                    root_1 = (GrammarAST)this.adaptor.becomeRoot(new NotAST(39, NOT193), (Object)root_1);
                    this.adaptor.addChild(root_1, stream_blockSet.nextTree());
                    this.adaptor.addChild(root_0, root_1);
                    retval.tree = root_0;
                    break;
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            return retval;
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
            return retval;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final blockSet_return blockSet() throws RecognitionException {
        blockSet_return retval = new blockSet_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token LPAREN195 = null;
        Token OR197 = null;
        Token RPAREN199 = null;
        setElement_return setElement196 = null;
        setElement_return setElement198 = null;
        Object LPAREN195_tree = null;
        Object OR197_tree = null;
        Object RPAREN199_tree = null;
        RewriteRuleTokenStream stream_RPAREN = new RewriteRuleTokenStream(this.adaptor, "token RPAREN");
        RewriteRuleTokenStream stream_LPAREN = new RewriteRuleTokenStream(this.adaptor, "token LPAREN");
        RewriteRuleTokenStream stream_OR = new RewriteRuleTokenStream(this.adaptor, "token OR");
        RewriteRuleSubtreeStream stream_setElement = new RewriteRuleSubtreeStream(this.adaptor, "rule setElement");
        boolean ebnf = false;
        try {
            LPAREN195 = (Token)this.match(this.input, 34, FOLLOW_LPAREN_in_blockSet3681);
            stream_LPAREN.add(LPAREN195);
            this.pushFollow(FOLLOW_setElement_in_blockSet3683);
            setElement196 = this.setElement();
            --this.state._fsp;
            stream_setElement.add(((ParserRuleReturnScope)setElement196).getTree());
            block7: while (true) {
                int alt68 = 2;
                int LA68_0 = this.input.LA(1);
                if (LA68_0 == 43) {
                    alt68 = 1;
                }
                switch (alt68) {
                    case 1: {
                        OR197 = (Token)this.match(this.input, 43, FOLLOW_OR_in_blockSet3686);
                        stream_OR.add(OR197);
                        this.pushFollow(FOLLOW_setElement_in_blockSet3688);
                        setElement198 = this.setElement();
                        --this.state._fsp;
                        stream_setElement.add(((ParserRuleReturnScope)setElement198).getTree());
                        continue block7;
                    }
                }
                break;
            }
            RPAREN199 = (Token)this.match(this.input, 56, FOLLOW_RPAREN_in_blockSet3692);
            stream_RPAREN.add(RPAREN199);
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
            root_1 = (GrammarAST)this.adaptor.becomeRoot(new SetAST(98, LPAREN195, "SET"), (Object)root_1);
            if (!stream_setElement.hasNext()) {
                throw new RewriteEarlyExitException();
            }
            while (stream_setElement.hasNext()) {
                this.adaptor.addChild(root_1, stream_setElement.nextTree());
            }
            stream_setElement.reset();
            this.adaptor.addChild(root_0, root_1);
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final setElement_return setElement() throws RecognitionException {
        setElement_return retval = new setElement_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token TOKEN_REF200 = null;
        Token STRING_LITERAL202 = null;
        Token LEXER_CHAR_SET205 = null;
        elementOptions_return elementOptions201 = null;
        elementOptions_return elementOptions203 = null;
        range_return range204 = null;
        TerminalAST TOKEN_REF200_tree = null;
        TerminalAST STRING_LITERAL202_tree = null;
        GrammarAST LEXER_CHAR_SET205_tree = null;
        try {
            int alt71 = 4;
            switch (this.input.LA(1)) {
                case 66: {
                    alt71 = 1;
                    break;
                }
                case 62: {
                    int LA71_2 = this.input.LA(2);
                    if (LA71_2 == 52) {
                        alt71 = 3;
                        break;
                    }
                    if (LA71_2 == -1 || LA71_2 == 4 || LA71_2 == 20 || LA71_2 == 32 || LA71_2 >= 34 && LA71_2 <= 35 || LA71_2 == 39 || LA71_2 == 43 || LA71_2 == 45 || LA71_2 == 47 || LA71_2 == 51 || LA71_2 == 53 || LA71_2 >= 56 && LA71_2 <= 59 || LA71_2 >= 61 && LA71_2 <= 62 || LA71_2 == 66) {
                        alt71 = 2;
                        break;
                    }
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 71, 2, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
                case 32: {
                    alt71 = 4;
                    break;
                }
                default: {
                    NoViableAltException nvae = new NoViableAltException("", 71, 0, this.input);
                    throw nvae;
                }
            }
            switch (alt71) {
                case 1: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    TOKEN_REF200 = (Token)this.match(this.input, 66, FOLLOW_TOKEN_REF_in_setElement3722);
                    TOKEN_REF200_tree = new TerminalAST(TOKEN_REF200);
                    root_0 = (GrammarAST)this.adaptor.becomeRoot(TOKEN_REF200_tree, (Object)root_0);
                    int alt69 = 2;
                    int LA69_0 = this.input.LA(1);
                    if (LA69_0 == 35) {
                        alt69 = 1;
                    }
                    switch (alt69) {
                        case 1: {
                            this.pushFollow(FOLLOW_elementOptions_in_setElement3728);
                            elementOptions201 = this.elementOptions();
                            --this.state._fsp;
                            this.adaptor.addChild(root_0, ((ParserRuleReturnScope)elementOptions201).getTree());
                        }
                    }
                    break;
                }
                case 2: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    STRING_LITERAL202 = (Token)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_setElement3734);
                    STRING_LITERAL202_tree = new TerminalAST(STRING_LITERAL202);
                    root_0 = (GrammarAST)this.adaptor.becomeRoot(STRING_LITERAL202_tree, (Object)root_0);
                    int alt70 = 2;
                    int LA70_0 = this.input.LA(1);
                    if (LA70_0 == 35) {
                        alt70 = 1;
                    }
                    switch (alt70) {
                        case 1: {
                            this.pushFollow(FOLLOW_elementOptions_in_setElement3740);
                            elementOptions203 = this.elementOptions();
                            --this.state._fsp;
                            this.adaptor.addChild(root_0, ((ParserRuleReturnScope)elementOptions203).getTree());
                        }
                    }
                    break;
                }
                case 3: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_range_in_setElement3746);
                    range204 = this.range();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)range204).getTree());
                    break;
                }
                case 4: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    LEXER_CHAR_SET205 = (Token)this.match(this.input, 32, FOLLOW_LEXER_CHAR_SET_in_setElement3756);
                    LEXER_CHAR_SET205_tree = (GrammarAST)this.adaptor.create(LEXER_CHAR_SET205);
                    this.adaptor.addChild(root_0, LEXER_CHAR_SET205_tree);
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final block_return block() throws RecognitionException {
        block_return retval = new block_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token LPAREN206 = null;
        Token COLON208 = null;
        Token RPAREN210 = null;
        ArrayList<Object> list_ra = null;
        optionsSpec_return optionsSpec207 = null;
        altList_return altList209 = null;
        ruleAction_return ra = null;
        Object LPAREN206_tree = null;
        Object COLON208_tree = null;
        Object RPAREN210_tree = null;
        RewriteRuleTokenStream stream_COLON = new RewriteRuleTokenStream(this.adaptor, "token COLON");
        RewriteRuleTokenStream stream_RPAREN = new RewriteRuleTokenStream(this.adaptor, "token RPAREN");
        RewriteRuleTokenStream stream_LPAREN = new RewriteRuleTokenStream(this.adaptor, "token LPAREN");
        RewriteRuleSubtreeStream stream_optionsSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule optionsSpec");
        RewriteRuleSubtreeStream stream_altList = new RewriteRuleSubtreeStream(this.adaptor, "rule altList");
        RewriteRuleSubtreeStream stream_ruleAction = new RewriteRuleSubtreeStream(this.adaptor, "rule ruleAction");
        try {
            LPAREN206 = (Token)this.match(this.input, 34, FOLLOW_LPAREN_in_block3780);
            stream_LPAREN.add(LPAREN206);
            int alt74 = 2;
            int LA74_0 = this.input.LA(1);
            if (LA74_0 == 11 || LA74_0 == 14 || LA74_0 == 42) {
                alt74 = 1;
            }
            switch (alt74) {
                case 1: {
                    int alt72 = 2;
                    int LA72_0 = this.input.LA(1);
                    if (LA72_0 == 42) {
                        alt72 = 1;
                    }
                    switch (alt72) {
                        case 1: {
                            this.pushFollow(FOLLOW_optionsSpec_in_block3792);
                            optionsSpec207 = this.optionsSpec();
                            --this.state._fsp;
                            stream_optionsSpec.add(((ParserRuleReturnScope)optionsSpec207).getTree());
                        }
                    }
                    block13: while (true) {
                        int alt73 = 2;
                        int LA73_0 = this.input.LA(1);
                        if (LA73_0 == 11) {
                            alt73 = 1;
                        }
                        switch (alt73) {
                            case 1: {
                                this.pushFollow(FOLLOW_ruleAction_in_block3797);
                                ra = this.ruleAction();
                                --this.state._fsp;
                                stream_ruleAction.add(((RuleReturnScope)ra).getTree());
                                if (list_ra == null) {
                                    list_ra = new ArrayList<Object>();
                                }
                                list_ra.add(((RuleReturnScope)ra).getTree());
                                continue block13;
                            }
                        }
                        break;
                    }
                    COLON208 = (Token)this.match(this.input, 14, FOLLOW_COLON_in_block3800);
                    stream_COLON.add(COLON208);
                }
            }
            this.pushFollow(FOLLOW_altList_in_block3813);
            altList209 = this.altList();
            --this.state._fsp;
            stream_altList.add(((ParserRuleReturnScope)altList209).getTree());
            RPAREN210 = (Token)this.match(this.input, 56, FOLLOW_RPAREN_in_block3817);
            stream_RPAREN.add(RPAREN210);
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            RewriteRuleSubtreeStream stream_ra = new RewriteRuleSubtreeStream(this.adaptor, "token ra", list_ra);
            root_0 = (GrammarAST)this.adaptor.nil();
            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
            root_1 = (GrammarAST)this.adaptor.becomeRoot(new BlockAST(78, LPAREN206, "BLOCK"), (Object)root_1);
            if (stream_optionsSpec.hasNext()) {
                this.adaptor.addChild(root_1, stream_optionsSpec.nextTree());
            }
            stream_optionsSpec.reset();
            while (stream_ra.hasNext()) {
                this.adaptor.addChild(root_1, stream_ra.nextTree());
            }
            stream_ra.reset();
            this.adaptor.addChild(root_1, stream_altList.nextTree());
            this.adaptor.addChild(root_0, root_1);
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            GrammarAST options = (GrammarAST)retval.tree.getFirstChildWithType(42);
            if (options != null) {
                Grammar.setNodeOptions(retval.tree, options);
            }
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    public final ruleref_return ruleref() throws RecognitionException {
        ruleref_return retval = new ruleref_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token RULE_REF211 = null;
        Token ARG_ACTION212 = null;
        elementOptions_return elementOptions213 = null;
        Object RULE_REF211_tree = null;
        Object ARG_ACTION212_tree = null;
        RewriteRuleTokenStream stream_RULE_REF = new RewriteRuleTokenStream(this.adaptor, "token RULE_REF");
        RewriteRuleTokenStream stream_ARG_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ARG_ACTION");
        RewriteRuleSubtreeStream stream_elementOptions = new RewriteRuleSubtreeStream(this.adaptor, "rule elementOptions");
        RULE_REF211 = (Token)this.match(this.input, 57, FOLLOW_RULE_REF_in_ruleref3871);
        stream_RULE_REF.add(RULE_REF211);
        int alt75 = 2;
        int LA75_0 = this.input.LA(1);
        if (LA75_0 == 8) {
            alt75 = 1;
        }
        switch (alt75) {
            case 1: {
                ARG_ACTION212 = (Token)this.match(this.input, 8, FOLLOW_ARG_ACTION_in_ruleref3873);
                stream_ARG_ACTION.add(ARG_ACTION212);
            }
        }
        int alt76 = 2;
        int LA76_0 = this.input.LA(1);
        if (LA76_0 == 35) {
            alt76 = 1;
        }
        switch (alt76) {
            case 1: {
                this.pushFollow(FOLLOW_elementOptions_in_ruleref3876);
                elementOptions213 = this.elementOptions();
                --this.state._fsp;
                stream_elementOptions.add(((ParserRuleReturnScope)elementOptions213).getTree());
            }
        }
        retval.tree = root_0;
        RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
        root_0 = (GrammarAST)this.adaptor.nil();
        GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
        root_1 = (GrammarAST)this.adaptor.becomeRoot(new RuleRefAST(stream_RULE_REF.nextToken()), (Object)root_1);
        if (stream_ARG_ACTION.hasNext()) {
            this.adaptor.addChild(root_1, new ActionAST(stream_ARG_ACTION.nextToken()));
        }
        stream_ARG_ACTION.reset();
        if (stream_elementOptions.hasNext()) {
            this.adaptor.addChild(root_1, stream_elementOptions.nextTree());
        }
        stream_elementOptions.reset();
        this.adaptor.addChild(root_0, root_1);
        retval.tree = root_0;
        retval.stop = this.input.LT(-1);
        retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
        this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        GrammarAST options = (GrammarAST)retval.tree.getFirstChildWithType(82);
        if (options != null) {
            Grammar.setNodeOptions(retval.tree, options);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final range_return range() throws RecognitionException {
        range_return retval = new range_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token STRING_LITERAL214 = null;
        Token RANGE215 = null;
        Token STRING_LITERAL216 = null;
        TerminalAST STRING_LITERAL214_tree = null;
        RangeAST RANGE215_tree = null;
        TerminalAST STRING_LITERAL216_tree = null;
        try {
            root_0 = (GrammarAST)this.adaptor.nil();
            STRING_LITERAL214 = (Token)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_range3932);
            STRING_LITERAL214_tree = new TerminalAST(STRING_LITERAL214);
            this.adaptor.addChild(root_0, STRING_LITERAL214_tree);
            RANGE215 = (Token)this.match(this.input, 52, FOLLOW_RANGE_in_range3937);
            RANGE215_tree = new RangeAST(RANGE215);
            root_0 = (GrammarAST)this.adaptor.becomeRoot(RANGE215_tree, (Object)root_0);
            STRING_LITERAL216 = (Token)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_range3943);
            STRING_LITERAL216_tree = new TerminalAST(STRING_LITERAL216);
            this.adaptor.addChild(root_0, STRING_LITERAL216_tree);
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final terminal_return terminal() throws RecognitionException {
        terminal_return retval = new terminal_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token TOKEN_REF217 = null;
        Token STRING_LITERAL219 = null;
        elementOptions_return elementOptions218 = null;
        elementOptions_return elementOptions220 = null;
        Object TOKEN_REF217_tree = null;
        Object STRING_LITERAL219_tree = null;
        RewriteRuleTokenStream stream_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL");
        RewriteRuleTokenStream stream_TOKEN_REF = new RewriteRuleTokenStream(this.adaptor, "token TOKEN_REF");
        RewriteRuleSubtreeStream stream_elementOptions = new RewriteRuleSubtreeStream(this.adaptor, "rule elementOptions");
        try {
            int alt79 = 2;
            int LA79_0 = this.input.LA(1);
            if (LA79_0 == 66) {
                alt79 = 1;
            } else if (LA79_0 == 62) {
                alt79 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 79, 0, this.input);
                throw nvae;
            }
            switch (alt79) {
                case 1: {
                    TOKEN_REF217 = (Token)this.match(this.input, 66, FOLLOW_TOKEN_REF_in_terminal3967);
                    stream_TOKEN_REF.add(TOKEN_REF217);
                    int alt77 = 2;
                    int LA77_0 = this.input.LA(1);
                    if (LA77_0 == 35) {
                        alt77 = 1;
                    }
                    switch (alt77) {
                        case 1: {
                            this.pushFollow(FOLLOW_elementOptions_in_terminal3969);
                            elementOptions218 = this.elementOptions();
                            --this.state._fsp;
                            stream_elementOptions.add(((ParserRuleReturnScope)elementOptions218).getTree());
                        }
                    }
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                    root_1 = (GrammarAST)this.adaptor.becomeRoot(new TerminalAST(stream_TOKEN_REF.nextToken()), (Object)root_1);
                    if (stream_elementOptions.hasNext()) {
                        this.adaptor.addChild(root_1, stream_elementOptions.nextTree());
                    }
                    stream_elementOptions.reset();
                    this.adaptor.addChild(root_0, root_1);
                    retval.tree = root_0;
                    break;
                }
                case 2: {
                    STRING_LITERAL219 = (Token)this.match(this.input, 62, FOLLOW_STRING_LITERAL_in_terminal3990);
                    stream_STRING_LITERAL.add(STRING_LITERAL219);
                    int alt78 = 2;
                    int LA78_0 = this.input.LA(1);
                    if (LA78_0 == 35) {
                        alt78 = 1;
                    }
                    switch (alt78) {
                        case 1: {
                            this.pushFollow(FOLLOW_elementOptions_in_terminal3992);
                            elementOptions220 = this.elementOptions();
                            --this.state._fsp;
                            stream_elementOptions.add(((ParserRuleReturnScope)elementOptions220).getTree());
                        }
                    }
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                    root_1 = (GrammarAST)this.adaptor.becomeRoot(new TerminalAST(stream_STRING_LITERAL.nextToken()), (Object)root_1);
                    if (stream_elementOptions.hasNext()) {
                        this.adaptor.addChild(root_1, stream_elementOptions.nextTree());
                    }
                    stream_elementOptions.reset();
                    this.adaptor.addChild(root_0, root_1);
                    retval.tree = root_0;
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            GrammarAST options = (GrammarAST)retval.tree.getFirstChildWithType(82);
            if (options != null) {
                Grammar.setNodeOptions(retval.tree, options);
            }
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final elementOptions_return elementOptions() throws RecognitionException {
        elementOptions_return retval = new elementOptions_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token LT221 = null;
        Token COMMA223 = null;
        Token GT225 = null;
        elementOption_return elementOption222 = null;
        elementOption_return elementOption224 = null;
        Object LT221_tree = null;
        Object COMMA223_tree = null;
        Object GT225_tree = null;
        RewriteRuleTokenStream stream_GT = new RewriteRuleTokenStream(this.adaptor, "token GT");
        RewriteRuleTokenStream stream_LT = new RewriteRuleTokenStream(this.adaptor, "token LT");
        RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleSubtreeStream stream_elementOption = new RewriteRuleSubtreeStream(this.adaptor, "rule elementOption");
        try {
            LT221 = (Token)this.match(this.input, 35, FOLLOW_LT_in_elementOptions4023);
            stream_LT.add(LT221);
            int alt81 = 2;
            int LA81_0 = this.input.LA(1);
            if (LA81_0 == 57 || LA81_0 == 66) {
                alt81 = 1;
            }
            switch (alt81) {
                case 1: {
                    this.pushFollow(FOLLOW_elementOption_in_elementOptions4026);
                    elementOption222 = this.elementOption();
                    --this.state._fsp;
                    stream_elementOption.add(((ParserRuleReturnScope)elementOption222).getTree());
                    block10: while (true) {
                        int alt80 = 2;
                        int LA80_0 = this.input.LA(1);
                        if (LA80_0 == 16) {
                            alt80 = 1;
                        }
                        switch (alt80) {
                            case 1: {
                                COMMA223 = (Token)this.match(this.input, 16, FOLLOW_COMMA_in_elementOptions4029);
                                stream_COMMA.add(COMMA223);
                                this.pushFollow(FOLLOW_elementOption_in_elementOptions4031);
                                elementOption224 = this.elementOption();
                                --this.state._fsp;
                                stream_elementOption.add(((ParserRuleReturnScope)elementOption224).getTree());
                                continue block10;
                            }
                        }
                        break;
                    }
                    break;
                }
            }
            GT225 = (Token)this.match(this.input, 26, FOLLOW_GT_in_elementOptions4037);
            stream_GT.add(GT225);
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
            root_1 = (GrammarAST)this.adaptor.becomeRoot((GrammarAST)this.adaptor.create(82, LT221, "ELEMENT_OPTIONS"), (Object)root_1);
            while (stream_elementOption.hasNext()) {
                this.adaptor.addChild(root_1, stream_elementOption.nextTree());
            }
            stream_elementOption.reset();
            this.adaptor.addChild(root_0, root_1);
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
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
        GrammarAST root_0 = null;
        Token ASSIGN228 = null;
        qid_return qid226 = null;
        id_return id227 = null;
        optionValue_return optionValue229 = null;
        GrammarAST ASSIGN228_tree = null;
        try {
            int alt82;
            block21: {
                int LA82_0;
                block22: {
                    alt82 = 2;
                    LA82_0 = this.input.LA(1);
                    if (LA82_0 != 57) break block22;
                    int LA82_1 = this.input.LA(2);
                    if (LA82_1 == 16 || LA82_1 == 20 || LA82_1 == 26) {
                        alt82 = 1;
                        break block21;
                    } else if (LA82_1 == 10) {
                        alt82 = 2;
                        break block21;
                    } else {
                        int nvaeMark = this.input.mark();
                        try {
                            this.input.consume();
                            NoViableAltException nvae = new NoViableAltException("", 82, 1, this.input);
                            throw nvae;
                        }
                        catch (Throwable throwable) {
                            this.input.rewind(nvaeMark);
                            throw throwable;
                        }
                    }
                }
                if (LA82_0 != 66) {
                    NoViableAltException nvae = new NoViableAltException("", 82, 0, this.input);
                    throw nvae;
                }
                int LA82_2 = this.input.LA(2);
                if (LA82_2 == 16 || LA82_2 == 20 || LA82_2 == 26) {
                    alt82 = 1;
                } else if (LA82_2 == 10) {
                    alt82 = 2;
                } else {
                    int nvaeMark = this.input.mark();
                    try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 82, 2, this.input);
                        throw nvae;
                    }
                    catch (Throwable throwable) {
                        this.input.rewind(nvaeMark);
                        throw throwable;
                    }
                }
            }
            switch (alt82) {
                case 1: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_qid_in_elementOption4085);
                    qid226 = this.qid();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)qid226).getTree());
                    break;
                }
                case 2: {
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.pushFollow(FOLLOW_id_in_elementOption4093);
                    id227 = this.id();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)id227).getTree());
                    ASSIGN228 = (Token)this.match(this.input, 10, FOLLOW_ASSIGN_in_elementOption4095);
                    ASSIGN228_tree = (GrammarAST)this.adaptor.create(ASSIGN228);
                    root_0 = (GrammarAST)this.adaptor.becomeRoot(ASSIGN228_tree, (Object)root_0);
                    this.pushFollow(FOLLOW_optionValue_in_elementOption4098);
                    optionValue229 = this.optionValue();
                    --this.state._fsp;
                    this.adaptor.addChild(root_0, ((ParserRuleReturnScope)optionValue229).getTree());
                    break;
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            return retval;
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
            return retval;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final id_return id() throws RecognitionException {
        id_return retval = new id_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token RULE_REF230 = null;
        Token TOKEN_REF231 = null;
        Object RULE_REF230_tree = null;
        Object TOKEN_REF231_tree = null;
        RewriteRuleTokenStream stream_RULE_REF = new RewriteRuleTokenStream(this.adaptor, "token RULE_REF");
        RewriteRuleTokenStream stream_TOKEN_REF = new RewriteRuleTokenStream(this.adaptor, "token TOKEN_REF");
        this.paraphrases.push("looking for an identifier");
        try {
            int alt83 = 2;
            int LA83_0 = this.input.LA(1);
            if (LA83_0 == 57) {
                alt83 = 1;
            } else if (LA83_0 == 66) {
                alt83 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 83, 0, this.input);
                throw nvae;
            }
            switch (alt83) {
                case 1: {
                    RULE_REF230 = (Token)this.match(this.input, 57, FOLLOW_RULE_REF_in_id4129);
                    stream_RULE_REF.add(RULE_REF230);
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(28, RULE_REF230));
                    retval.tree = root_0;
                    break;
                }
                case 2: {
                    TOKEN_REF231 = (Token)this.match(this.input, 66, FOLLOW_TOKEN_REF_in_id4142);
                    stream_TOKEN_REF.add(TOKEN_REF231);
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                    root_0 = (GrammarAST)this.adaptor.nil();
                    this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(28, TOKEN_REF231));
                    retval.tree = root_0;
                }
            }
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            this.paraphrases.pop();
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final qid_return qid() throws RecognitionException {
        qid_return retval = new qid_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token DOT233 = null;
        id_return id232 = null;
        id_return id234 = null;
        Object DOT233_tree = null;
        RewriteRuleTokenStream stream_DOT = new RewriteRuleTokenStream(this.adaptor, "token DOT");
        RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
        this.paraphrases.push("looking for a qualified identifier");
        try {
            this.pushFollow(FOLLOW_id_in_qid4170);
            id232 = this.id();
            --this.state._fsp;
            stream_id.add(((ParserRuleReturnScope)id232).getTree());
            block7: while (true) {
                int alt84 = 2;
                int LA84_0 = this.input.LA(1);
                if (LA84_0 == 20) {
                    alt84 = 1;
                }
                switch (alt84) {
                    case 1: {
                        DOT233 = (Token)this.match(this.input, 20, FOLLOW_DOT_in_qid4173);
                        stream_DOT.add(DOT233);
                        this.pushFollow(FOLLOW_id_in_qid4175);
                        id234 = this.id();
                        --this.state._fsp;
                        stream_id.add(((ParserRuleReturnScope)id234).getTree());
                        continue block7;
                    }
                }
                break;
            }
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(28, retval.start, this.input.toString(retval.start, this.input.LT(-1))));
            retval.tree = root_0;
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            this.paraphrases.pop();
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final alternativeEntry_return alternativeEntry() throws RecognitionException {
        alternativeEntry_return retval = new alternativeEntry_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token EOF236 = null;
        alternative_return alternative235 = null;
        GrammarAST EOF236_tree = null;
        try {
            root_0 = (GrammarAST)this.adaptor.nil();
            this.pushFollow(FOLLOW_alternative_in_alternativeEntry4192);
            alternative235 = this.alternative();
            --this.state._fsp;
            this.adaptor.addChild(root_0, ((ParserRuleReturnScope)alternative235).getTree());
            EOF236 = (Token)this.match(this.input, -1, FOLLOW_EOF_in_alternativeEntry4194);
            EOF236_tree = (GrammarAST)this.adaptor.create(EOF236);
            this.adaptor.addChild(root_0, EOF236_tree);
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final elementEntry_return elementEntry() throws RecognitionException {
        elementEntry_return retval = new elementEntry_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token EOF238 = null;
        element_return element237 = null;
        GrammarAST EOF238_tree = null;
        try {
            root_0 = (GrammarAST)this.adaptor.nil();
            this.pushFollow(FOLLOW_element_in_elementEntry4203);
            element237 = this.element();
            --this.state._fsp;
            this.adaptor.addChild(root_0, ((ParserRuleReturnScope)element237).getTree());
            EOF238 = (Token)this.match(this.input, -1, FOLLOW_EOF_in_elementEntry4205);
            EOF238_tree = (GrammarAST)this.adaptor.create(EOF238);
            this.adaptor.addChild(root_0, EOF238_tree);
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final ruleEntry_return ruleEntry() throws RecognitionException {
        ruleEntry_return retval = new ruleEntry_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token EOF240 = null;
        rule_return rule239 = null;
        GrammarAST EOF240_tree = null;
        try {
            root_0 = (GrammarAST)this.adaptor.nil();
            this.pushFollow(FOLLOW_rule_in_ruleEntry4213);
            rule239 = this.rule();
            --this.state._fsp;
            this.adaptor.addChild(root_0, ((ParserRuleReturnScope)rule239).getTree());
            EOF240 = (Token)this.match(this.input, -1, FOLLOW_EOF_in_ruleEntry4215);
            EOF240_tree = (GrammarAST)this.adaptor.create(EOF240);
            this.adaptor.addChild(root_0, EOF240_tree);
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final blockEntry_return blockEntry() throws RecognitionException {
        blockEntry_return retval = new blockEntry_return();
        retval.start = this.input.LT(1);
        GrammarAST root_0 = null;
        Token EOF242 = null;
        block_return block241 = null;
        GrammarAST EOF242_tree = null;
        try {
            root_0 = (GrammarAST)this.adaptor.nil();
            this.pushFollow(FOLLOW_block_in_blockEntry4223);
            block241 = this.block();
            --this.state._fsp;
            this.adaptor.addChild(root_0, ((ParserRuleReturnScope)block241).getTree());
            EOF242 = (Token)this.match(this.input, -1, FOLLOW_EOF_in_blockEntry4225);
            EOF242_tree = (GrammarAST)this.adaptor.create(EOF242);
            this.adaptor.addChild(root_0, EOF242_tree);
            retval.stop = this.input.LT(-1);
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
        }
        catch (RecognitionException re) {
            this.reportError(re);
            this.recover(this.input, re);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re);
        }
        return retval;
    }

    public static class blockEntry_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class ruleEntry_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class elementEntry_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class alternativeEntry_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class qid_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class id_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class elementOption_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class elementOptions_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class terminal_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class range_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class ruleref_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class block_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class setElement_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class blockSet_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class notSet_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class wildcard_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class atom_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class lexerAtom_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class ebnfSuffix_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class blockSuffix_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class ebnf_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class labeledElement_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class actionElement_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class element_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class alternative_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class altList_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class lexerCommandName_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class lexerCommandExpr_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class lexerCommand_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class lexerCommands_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class lexerBlock_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class labeledLexerElement_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class lexerElement_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class lexerElements_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class lexerAlt_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class lexerAltList_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class lexerRuleBlock_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class lexerRule_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class labeledAlt_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class ruleAltList_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class ruleBlock_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class ruleAction_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class localsSpec_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class throwsSpec_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class ruleReturns_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class rulePrequel_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class rulePrequels_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class finallyClause_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class exceptionHandler_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class exceptionGroup_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class parserRule_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class rule_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class sync_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class rules_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class modeSpec_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class actionScopeName_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class action_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class channelsSpec_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class v3tokenSpec_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class tokensSpec_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class delegateGrammar_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class delegateGrammars_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class optionValue_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class option_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class optionsSpec_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class prequelConstruct_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class grammarType_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }

    public static class grammarSpec_return
    extends ParserRuleReturnScope {
        GrammarAST tree;

        @Override
        public GrammarAST getTree() {
            return this.tree;
        }
    }
}

