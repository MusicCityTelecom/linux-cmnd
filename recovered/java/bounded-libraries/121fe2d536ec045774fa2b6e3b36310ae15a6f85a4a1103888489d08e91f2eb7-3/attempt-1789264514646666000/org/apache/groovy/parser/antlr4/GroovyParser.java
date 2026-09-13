/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.parser.antlr4;

import groovyjarjarantlr4.v4.runtime.FailedPredicateException;
import groovyjarjarantlr4.v4.runtime.NoViableAltException;
import groovyjarjarantlr4.v4.runtime.ParserRuleContext;
import groovyjarjarantlr4.v4.runtime.RecognitionException;
import groovyjarjarantlr4.v4.runtime.RuleContext;
import groovyjarjarantlr4.v4.runtime.RuleVersion;
import groovyjarjarantlr4.v4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.TokenStream;
import groovyjarjarantlr4.v4.runtime.Vocabulary;
import groovyjarjarantlr4.v4.runtime.VocabularyImpl;
import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNDeserializer;
import groovyjarjarantlr4.v4.runtime.atn.ParserATNSimulator;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.runtime.tree.ParseTreeVisitor;
import groovyjarjarantlr4.v4.runtime.tree.TerminalNode;
import java.util.List;
import java.util.Map;
import org.apache.groovy.parser.antlr4.AbstractParser;
import org.apache.groovy.parser.antlr4.GroovyParserVisitor;
import org.apache.groovy.parser.antlr4.SemanticPredicates;
import org.codehaus.groovy.ast.NodeMetaDataHandler;

public class GroovyParser
extends AbstractParser {
    public static final int StringLiteral = 1;
    public static final int GStringBegin = 2;
    public static final int GStringEnd = 3;
    public static final int GStringPart = 4;
    public static final int GStringPathPart = 5;
    public static final int RollBackOne = 6;
    public static final int AS = 7;
    public static final int DEF = 8;
    public static final int IN = 9;
    public static final int TRAIT = 10;
    public static final int THREADSAFE = 11;
    public static final int VAR = 12;
    public static final int BuiltInPrimitiveType = 13;
    public static final int ABSTRACT = 14;
    public static final int ASSERT = 15;
    public static final int BREAK = 16;
    public static final int YIELD = 17;
    public static final int CASE = 18;
    public static final int CATCH = 19;
    public static final int CLASS = 20;
    public static final int CONST = 21;
    public static final int CONTINUE = 22;
    public static final int DEFAULT = 23;
    public static final int DO = 24;
    public static final int ELSE = 25;
    public static final int ENUM = 26;
    public static final int EXTENDS = 27;
    public static final int FINAL = 28;
    public static final int FINALLY = 29;
    public static final int FOR = 30;
    public static final int IF = 31;
    public static final int GOTO = 32;
    public static final int IMPLEMENTS = 33;
    public static final int IMPORT = 34;
    public static final int INSTANCEOF = 35;
    public static final int INTERFACE = 36;
    public static final int NATIVE = 37;
    public static final int NEW = 38;
    public static final int NON_SEALED = 39;
    public static final int PACKAGE = 40;
    public static final int PERMITS = 41;
    public static final int PRIVATE = 42;
    public static final int PROTECTED = 43;
    public static final int PUBLIC = 44;
    public static final int RECORD = 45;
    public static final int RETURN = 46;
    public static final int SEALED = 47;
    public static final int STATIC = 48;
    public static final int STRICTFP = 49;
    public static final int SUPER = 50;
    public static final int SWITCH = 51;
    public static final int SYNCHRONIZED = 52;
    public static final int THIS = 53;
    public static final int THROW = 54;
    public static final int THROWS = 55;
    public static final int TRANSIENT = 56;
    public static final int TRY = 57;
    public static final int VOID = 58;
    public static final int VOLATILE = 59;
    public static final int WHILE = 60;
    public static final int IntegerLiteral = 61;
    public static final int FloatingPointLiteral = 62;
    public static final int BooleanLiteral = 63;
    public static final int NullLiteral = 64;
    public static final int RANGE_INCLUSIVE = 65;
    public static final int RANGE_EXCLUSIVE_LEFT = 66;
    public static final int RANGE_EXCLUSIVE_RIGHT = 67;
    public static final int RANGE_EXCLUSIVE_FULL = 68;
    public static final int SPREAD_DOT = 69;
    public static final int SAFE_DOT = 70;
    public static final int SAFE_INDEX = 71;
    public static final int SAFE_CHAIN_DOT = 72;
    public static final int ELVIS = 73;
    public static final int METHOD_POINTER = 74;
    public static final int METHOD_REFERENCE = 75;
    public static final int REGEX_FIND = 76;
    public static final int REGEX_MATCH = 77;
    public static final int POWER = 78;
    public static final int POWER_ASSIGN = 79;
    public static final int SPACESHIP = 80;
    public static final int IDENTICAL = 81;
    public static final int NOT_IDENTICAL = 82;
    public static final int ARROW = 83;
    public static final int NOT_INSTANCEOF = 84;
    public static final int NOT_IN = 85;
    public static final int LPAREN = 86;
    public static final int RPAREN = 87;
    public static final int LBRACE = 88;
    public static final int RBRACE = 89;
    public static final int LBRACK = 90;
    public static final int RBRACK = 91;
    public static final int SEMI = 92;
    public static final int COMMA = 93;
    public static final int DOT = 94;
    public static final int ASSIGN = 95;
    public static final int GT = 96;
    public static final int LT = 97;
    public static final int NOT = 98;
    public static final int BITNOT = 99;
    public static final int QUESTION = 100;
    public static final int COLON = 101;
    public static final int EQUAL = 102;
    public static final int LE = 103;
    public static final int GE = 104;
    public static final int NOTEQUAL = 105;
    public static final int AND = 106;
    public static final int OR = 107;
    public static final int INC = 108;
    public static final int DEC = 109;
    public static final int ADD = 110;
    public static final int SUB = 111;
    public static final int MUL = 112;
    public static final int DIV = 113;
    public static final int BITAND = 114;
    public static final int BITOR = 115;
    public static final int XOR = 116;
    public static final int MOD = 117;
    public static final int ADD_ASSIGN = 118;
    public static final int SUB_ASSIGN = 119;
    public static final int MUL_ASSIGN = 120;
    public static final int DIV_ASSIGN = 121;
    public static final int AND_ASSIGN = 122;
    public static final int OR_ASSIGN = 123;
    public static final int XOR_ASSIGN = 124;
    public static final int MOD_ASSIGN = 125;
    public static final int LSHIFT_ASSIGN = 126;
    public static final int RSHIFT_ASSIGN = 127;
    public static final int URSHIFT_ASSIGN = 128;
    public static final int ELVIS_ASSIGN = 129;
    public static final int CapitalizedIdentifier = 130;
    public static final int Identifier = 131;
    public static final int AT = 132;
    public static final int ELLIPSIS = 133;
    public static final int WS = 134;
    public static final int NL = 135;
    public static final int SH_COMMENT = 136;
    public static final int UNEXPECTED_CHAR = 137;
    public static final int RULE_compilationUnit = 0;
    public static final int RULE_scriptStatements = 1;
    public static final int RULE_scriptStatement = 2;
    public static final int RULE_packageDeclaration = 3;
    public static final int RULE_importDeclaration = 4;
    public static final int RULE_typeDeclaration = 5;
    public static final int RULE_modifier = 6;
    public static final int RULE_modifiersOpt = 7;
    public static final int RULE_modifiers = 8;
    public static final int RULE_classOrInterfaceModifiersOpt = 9;
    public static final int RULE_classOrInterfaceModifiers = 10;
    public static final int RULE_classOrInterfaceModifier = 11;
    public static final int RULE_variableModifier = 12;
    public static final int RULE_variableModifiersOpt = 13;
    public static final int RULE_variableModifiers = 14;
    public static final int RULE_typeParameters = 15;
    public static final int RULE_typeParameter = 16;
    public static final int RULE_typeBound = 17;
    public static final int RULE_typeList = 18;
    public static final int RULE_classDeclaration = 19;
    public static final int RULE_classBody = 20;
    public static final int RULE_enumConstants = 21;
    public static final int RULE_enumConstant = 22;
    public static final int RULE_classBodyDeclaration = 23;
    public static final int RULE_memberDeclaration = 24;
    public static final int RULE_methodDeclaration = 25;
    public static final int RULE_compactConstructorDeclaration = 26;
    public static final int RULE_methodName = 27;
    public static final int RULE_returnType = 28;
    public static final int RULE_fieldDeclaration = 29;
    public static final int RULE_variableDeclarators = 30;
    public static final int RULE_variableDeclarator = 31;
    public static final int RULE_variableDeclaratorId = 32;
    public static final int RULE_variableInitializer = 33;
    public static final int RULE_variableInitializers = 34;
    public static final int RULE_emptyDims = 35;
    public static final int RULE_emptyDimsOpt = 36;
    public static final int RULE_standardType = 37;
    public static final int RULE_type = 38;
    public static final int RULE_classOrInterfaceType = 39;
    public static final int RULE_generalClassOrInterfaceType = 40;
    public static final int RULE_standardClassOrInterfaceType = 41;
    public static final int RULE_primitiveType = 42;
    public static final int RULE_typeArguments = 43;
    public static final int RULE_typeArgument = 44;
    public static final int RULE_annotatedQualifiedClassName = 45;
    public static final int RULE_qualifiedClassNameList = 46;
    public static final int RULE_formalParameters = 47;
    public static final int RULE_formalParameterList = 48;
    public static final int RULE_thisFormalParameter = 49;
    public static final int RULE_formalParameter = 50;
    public static final int RULE_methodBody = 51;
    public static final int RULE_qualifiedName = 52;
    public static final int RULE_qualifiedNameElement = 53;
    public static final int RULE_qualifiedNameElements = 54;
    public static final int RULE_qualifiedClassName = 55;
    public static final int RULE_qualifiedStandardClassName = 56;
    public static final int RULE_literal = 57;
    public static final int RULE_gstring = 58;
    public static final int RULE_gstringValue = 59;
    public static final int RULE_gstringPath = 60;
    public static final int RULE_lambdaExpression = 61;
    public static final int RULE_standardLambdaExpression = 62;
    public static final int RULE_lambdaParameters = 63;
    public static final int RULE_standardLambdaParameters = 64;
    public static final int RULE_lambdaBody = 65;
    public static final int RULE_closure = 66;
    public static final int RULE_closureOrLambdaExpression = 67;
    public static final int RULE_blockStatementsOpt = 68;
    public static final int RULE_blockStatements = 69;
    public static final int RULE_annotationsOpt = 70;
    public static final int RULE_annotation = 71;
    public static final int RULE_elementValues = 72;
    public static final int RULE_annotationName = 73;
    public static final int RULE_elementValuePairs = 74;
    public static final int RULE_elementValuePair = 75;
    public static final int RULE_elementValuePairName = 76;
    public static final int RULE_elementValue = 77;
    public static final int RULE_elementValueArrayInitializer = 78;
    public static final int RULE_block = 79;
    public static final int RULE_blockStatement = 80;
    public static final int RULE_localVariableDeclaration = 81;
    public static final int RULE_variableDeclaration = 82;
    public static final int RULE_typeNamePairs = 83;
    public static final int RULE_typeNamePair = 84;
    public static final int RULE_variableNames = 85;
    public static final int RULE_conditionalStatement = 86;
    public static final int RULE_ifElseStatement = 87;
    public static final int RULE_switchStatement = 88;
    public static final int RULE_loopStatement = 89;
    public static final int RULE_continueStatement = 90;
    public static final int RULE_breakStatement = 91;
    public static final int RULE_yieldStatement = 92;
    public static final int RULE_tryCatchStatement = 93;
    public static final int RULE_assertStatement = 94;
    public static final int RULE_statement = 95;
    public static final int RULE_catchClause = 96;
    public static final int RULE_catchType = 97;
    public static final int RULE_finallyBlock = 98;
    public static final int RULE_resources = 99;
    public static final int RULE_resourceList = 100;
    public static final int RULE_resource = 101;
    public static final int RULE_switchBlockStatementGroup = 102;
    public static final int RULE_switchLabel = 103;
    public static final int RULE_forControl = 104;
    public static final int RULE_enhancedForControl = 105;
    public static final int RULE_classicalForControl = 106;
    public static final int RULE_forInit = 107;
    public static final int RULE_forUpdate = 108;
    public static final int RULE_castParExpression = 109;
    public static final int RULE_parExpression = 110;
    public static final int RULE_expressionInPar = 111;
    public static final int RULE_expressionList = 112;
    public static final int RULE_expressionListElement = 113;
    public static final int RULE_enhancedStatementExpression = 114;
    public static final int RULE_statementExpression = 115;
    public static final int RULE_postfixExpression = 116;
    public static final int RULE_switchExpression = 117;
    public static final int RULE_switchBlockStatementExpressionGroup = 118;
    public static final int RULE_switchExpressionLabel = 119;
    public static final int RULE_expression = 120;
    public static final int RULE_castOperandExpression = 121;
    public static final int RULE_commandExpression = 122;
    public static final int RULE_commandArgument = 123;
    public static final int RULE_pathExpression = 124;
    public static final int RULE_pathElement = 125;
    public static final int RULE_namePart = 126;
    public static final int RULE_dynamicMemberName = 127;
    public static final int RULE_indexPropertyArgs = 128;
    public static final int RULE_namedPropertyArgs = 129;
    public static final int RULE_primary = 130;
    public static final int RULE_namedPropertyArgPrimary = 131;
    public static final int RULE_namedArgPrimary = 132;
    public static final int RULE_commandPrimary = 133;
    public static final int RULE_list = 134;
    public static final int RULE_map = 135;
    public static final int RULE_mapEntryList = 136;
    public static final int RULE_namedPropertyArgList = 137;
    public static final int RULE_mapEntry = 138;
    public static final int RULE_namedPropertyArg = 139;
    public static final int RULE_namedArg = 140;
    public static final int RULE_mapEntryLabel = 141;
    public static final int RULE_namedPropertyArgLabel = 142;
    public static final int RULE_namedArgLabel = 143;
    public static final int RULE_creator = 144;
    public static final int RULE_dim = 145;
    public static final int RULE_arrayInitializer = 146;
    public static final int RULE_anonymousInnerClassDeclaration = 147;
    public static final int RULE_createdName = 148;
    public static final int RULE_nonWildcardTypeArguments = 149;
    public static final int RULE_typeArgumentsOrDiamond = 150;
    public static final int RULE_arguments = 151;
    public static final int RULE_argumentList = 152;
    public static final int RULE_enhancedArgumentListInPar = 153;
    public static final int RULE_firstArgumentListElement = 154;
    public static final int RULE_argumentListElement = 155;
    public static final int RULE_enhancedArgumentListElement = 156;
    public static final int RULE_stringLiteral = 157;
    public static final int RULE_className = 158;
    public static final int RULE_identifier = 159;
    public static final int RULE_builtInType = 160;
    public static final int RULE_keywords = 161;
    public static final int RULE_rparen = 162;
    public static final int RULE_nls = 163;
    public static final int RULE_sep = 164;
    public static final String[] ruleNames = GroovyParser.makeRuleNames();
    private static final String[] _LITERAL_NAMES = GroovyParser.makeLiteralNames();
    private static final String[] _SYMBOLIC_NAMES = GroovyParser.makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);
    @Deprecated
    public static final String[] tokenNames = new String[_SYMBOLIC_NAMES.length];
    private int inSwitchExpressionLevel = 0;
    public static final String _serializedATN = "\u0003\uc91d\ucaba\u058d\uafba\u4f53\u0607\uea8b\uc241\u0003\u008b\u0718\u0004\u0002\t\u0002\u0004\u0003\t\u0003\u0004\u0004\t\u0004\u0004\u0005\t\u0005\u0004\u0006\t\u0006\u0004\u0007\t\u0007\u0004\b\t\b\u0004\t\t\t\u0004\n\t\n\u0004\u000b\t\u000b\u0004\f\t\f\u0004\r\t\r\u0004\u000e\t\u000e\u0004\u000f\t\u000f\u0004\u0010\t\u0010\u0004\u0011\t\u0011\u0004\u0012\t\u0012\u0004\u0013\t\u0013\u0004\u0014\t\u0014\u0004\u0015\t\u0015\u0004\u0016\t\u0016\u0004\u0017\t\u0017\u0004\u0018\t\u0018\u0004\u0019\t\u0019\u0004\u001a\t\u001a\u0004\u001b\t\u001b\u0004\u001c\t\u001c\u0004\u001d\t\u001d\u0004\u001e\t\u001e\u0004\u001f\t\u001f\u0004 \t \u0004!\t!\u0004\"\t\"\u0004#\t#\u0004$\t$\u0004%\t%\u0004&\t&\u0004'\t'\u0004(\t(\u0004)\t)\u0004*\t*\u0004+\t+\u0004,\t,\u0004-\t-\u0004.\t.\u0004/\t/\u00040\t0\u00041\t1\u00042\t2\u00043\t3\u00044\t4\u00045\t5\u00046\t6\u00047\t7\u00048\t8\u00049\t9\u0004:\t:\u0004;\t;\u0004<\t<\u0004=\t=\u0004>\t>\u0004?\t?\u0004@\t@\u0004A\tA\u0004B\tB\u0004C\tC\u0004D\tD\u0004E\tE\u0004F\tF\u0004G\tG\u0004H\tH\u0004I\tI\u0004J\tJ\u0004K\tK\u0004L\tL\u0004M\tM\u0004N\tN\u0004O\tO\u0004P\tP\u0004Q\tQ\u0004R\tR\u0004S\tS\u0004T\tT\u0004U\tU\u0004V\tV\u0004W\tW\u0004X\tX\u0004Y\tY\u0004Z\tZ\u0004[\t[\u0004\\\t\\\u0004]\t]\u0004^\t^\u0004_\t_\u0004`\t`\u0004a\ta\u0004b\tb\u0004c\tc\u0004d\td\u0004e\te\u0004f\tf\u0004g\tg\u0004h\th\u0004i\ti\u0004j\tj\u0004k\tk\u0004l\tl\u0004m\tm\u0004n\tn\u0004o\to\u0004p\tp\u0004q\tq\u0004r\tr\u0004s\ts\u0004t\tt\u0004u\tu\u0004v\tv\u0004w\tw\u0004x\tx\u0004y\ty\u0004z\tz\u0004{\t{\u0004|\t|\u0004}\t}\u0004~\t~\u0004\u007f\t\u007f\u0004\u0080\t\u0080\u0004\u0081\t\u0081\u0004\u0082\t\u0082\u0004\u0083\t\u0083\u0004\u0084\t\u0084\u0004\u0085\t\u0085\u0004\u0086\t\u0086\u0004\u0087\t\u0087\u0004\u0088\t\u0088\u0004\u0089\t\u0089\u0004\u008a\t\u008a\u0004\u008b\t\u008b\u0004\u008c\t\u008c\u0004\u008d\t\u008d\u0004\u008e\t\u008e\u0004\u008f\t\u008f\u0004\u0090\t\u0090\u0004\u0091\t\u0091\u0004\u0092\t\u0092\u0004\u0093\t\u0093\u0004\u0094\t\u0094\u0004\u0095\t\u0095\u0004\u0096\t\u0096\u0004\u0097\t\u0097\u0004\u0098\t\u0098\u0004\u0099\t\u0099\u0004\u009a\t\u009a\u0004\u009b\t\u009b\u0004\u009c\t\u009c\u0004\u009d\t\u009d\u0004\u009e\t\u009e\u0004\u009f\t\u009f\u0004\u00a0\t\u00a0\u0004\u00a1\t\u00a1\u0004\u00a2\t\u00a2\u0004\u00a3\t\u00a3\u0004\u00a4\t\u00a4\u0004\u00a5\t\u00a5\u0004\u00a6\t\u00a6\u0003\u0002\u0003\u0002\u0003\u0002\u0005\u0002\u0150\n\u0002\u0005\u0002\u0152\n\u0002\u0003\u0002\u0005\u0002\u0155\n\u0002\u0003\u0002\u0003\u0002\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0007\u0003\u015d\n\u0003\f\u0003\u000e\u0003\u0160\u000b\u0003\u0003\u0003\u0005\u0003\u0163\n\u0003\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0005\u0004\u016a\n\u0004\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0006\u0003\u0006\u0003\u0006\u0005\u0006\u0173\n\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0005\u0006\u017a\n\u0006\u0003\u0007\u0003\u0007\u0003\u0007\u0003\b\u0003\b\u0005\b\u0181\n\b\u0003\t\u0003\t\u0003\t\u0005\t\u0186\n\t\u0003\n\u0003\n\u0003\n\u0003\n\u0007\n\u018c\n\n\f\n\u000e\n\u018f\u000b\n\u0003\u000b\u0003\u000b\u0007\u000b\u0193\n\u000b\f\u000b\u000e\u000b\u0196\u000b\u000b\u0005\u000b\u0198\n\u000b\u0003\f\u0003\f\u0003\f\u0003\f\u0007\f\u019e\n\f\f\f\u000e\f\u01a1\u000b\f\u0003\r\u0003\r\u0005\r\u01a5\n\r\u0003\u000e\u0003\u000e\u0005\u000e\u01a9\n\u000e\u0003\u000f\u0003\u000f\u0003\u000f\u0005\u000f\u01ae\n\u000f\u0003\u0010\u0003\u0010\u0003\u0010\u0003\u0010\u0007\u0010\u01b4\n\u0010\f\u0010\u000e\u0010\u01b7\u000b\u0010\u0003\u0011\u0003\u0011\u0003\u0011\u0003\u0011\u0003\u0011\u0003\u0011\u0003\u0011\u0007\u0011\u01c0\n\u0011\f\u0011\u000e\u0011\u01c3\u000b\u0011\u0003\u0011\u0003\u0011\u0003\u0011\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0012\u0005\u0012\u01ce\n\u0012\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0007\u0013\u01d5\n\u0013\f\u0013\u000e\u0013\u01d8\u000b\u0013\u0003\u0014\u0003\u0014\u0003\u0014\u0003\u0014\u0003\u0014\u0007\u0014\u01df\n\u0014\f\u0014\u000e\u0014\u01e2\u000b\u0014\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0005\u0015\u01f1\n\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0005\u0015\u01f7\n\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0005\u0015\u01fc\n\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0005\u0015\u0203\n\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0005\u0015\u020a\n\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0005\u0015\u0211\n\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0005\u0016\u021d\n\u0016\u0003\u0016\u0005\u0016\u0220\n\u0016\u0003\u0016\u0005\u0016\u0223\n\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0007\u0016\u0229\n\u0016\f\u0016\u000e\u0016\u022c\u000b\u0016\u0005\u0016\u022e\n\u0016\u0003\u0016\u0005\u0016\u0231\n\u0016\u0003\u0016\u0003\u0016\u0003\u0017\u0003\u0017\u0003\u0017\u0003\u0017\u0003\u0017\u0003\u0017\u0007\u0017\u023b\n\u0017\f\u0017\u000e\u0017\u023e\u000b\u0017\u0003\u0018\u0003\u0018\u0003\u0018\u0005\u0018\u0243\n\u0018\u0003\u0018\u0005\u0018\u0246\n\u0018\u0003\u0019\u0003\u0019\u0005\u0019\u024a\n\u0019\u0003\u0019\u0003\u0019\u0005\u0019\u024e\n\u0019\u0003\u001a\u0003\u001a\u0003\u001a\u0003\u001a\u0003\u001a\u0005\u001a\u0255\n\u001a\u0005\u001a\u0257\n\u001a\u0003\u001b\u0003\u001b\u0005\u001b\u025b\n\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0005\u001b\u0260\n\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0005\u001b\u026d\n\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0005\u001b\u0272\n\u001b\u0005\u001b\u0274\n\u001b\u0003\u001c\u0003\u001c\u0003\u001c\u0003\u001c\u0003\u001d\u0003\u001d\u0005\u001d\u027c\n\u001d\u0003\u001e\u0003\u001e\u0005\u001e\u0280\n\u001e\u0003\u001f\u0003\u001f\u0003 \u0003 \u0003 \u0003 \u0003 \u0007 \u0289\n \f \u000e \u028c\u000b \u0003!\u0003!\u0003!\u0003!\u0003!\u0003!\u0005!\u0294\n!\u0003\"\u0003\"\u0003#\u0003#\u0003$\u0003$\u0003$\u0003$\u0003$\u0003$\u0007$\u02a0\n$\f$\u000e$\u02a3\u000b$\u0003$\u0003$\u0005$\u02a7\n$\u0003%\u0003%\u0003%\u0003%\u0006%\u02ad\n%\r%\u000e%\u02ae\u0003&\u0005&\u02b2\n&\u0003'\u0003'\u0003'\u0005'\u02b7\n'\u0003'\u0003'\u0003(\u0003(\u0003(\u0005(\u02be\n(\u0003(\u0005(\u02c1\n(\u0003(\u0003(\u0003)\u0003)\u0005)\u02c7\n)\u0003)\u0005)\u02ca\n)\u0003*\u0003*\u0005*\u02ce\n*\u0003+\u0003+\u0005+\u02d2\n+\u0003,\u0003,\u0003-\u0003-\u0003-\u0003-\u0003-\u0003-\u0003-\u0007-\u02dd\n-\f-\u000e-\u02e0\u000b-\u0003-\u0003-\u0003-\u0003.\u0003.\u0003.\u0003.\u0003.\u0003.\u0003.\u0005.\u02ec\n.\u0005.\u02ee\n.\u0003/\u0003/\u0003/\u00030\u00030\u00030\u00030\u00030\u00070\u02f8\n0\f0\u000e0\u02fb\u000b0\u00031\u00031\u00051\u02ff\n1\u00031\u00031\u00032\u00032\u00052\u0305\n2\u00032\u00032\u00032\u00032\u00072\u030b\n2\f2\u000e2\u030e\u000b2\u00033\u00033\u00033\u00034\u00034\u00054\u0315\n4\u00034\u00054\u0318\n4\u00034\u00034\u00034\u00034\u00034\u00034\u00054\u0320\n4\u00035\u00035\u00036\u00036\u00036\u00076\u0327\n6\f6\u000e6\u032a\u000b6\u00037\u00037\u00037\u00037\u00037\u00057\u0331\n7\u00038\u00038\u00038\u00078\u0336\n8\f8\u000e8\u0339\u000b8\u00039\u00039\u00039\u0003:\u0003:\u0003:\u0003:\u0007:\u0342\n:\f:\u000e:\u0345\u000b:\u0003;\u0003;\u0003;\u0003;\u0003;\u0005;\u034c\n;\u0003<\u0003<\u0003<\u0003<\u0007<\u0352\n<\f<\u000e<\u0355\u000b<\u0003<\u0003<\u0003=\u0003=\u0005=\u035b\n=\u0003>\u0003>\u0007>\u035f\n>\f>\u000e>\u0362\u000b>\u0003?\u0003?\u0003?\u0003?\u0003?\u0003?\u0003@\u0003@\u0003@\u0003@\u0003@\u0003@\u0003A\u0003A\u0003B\u0003B\u0005B\u0374\nB\u0003C\u0003C\u0005C\u0378\nC\u0003D\u0003D\u0003D\u0003D\u0003D\u0005D\u037f\nD\u0003D\u0003D\u0005D\u0383\nD\u0003D\u0005D\u0386\nD\u0003D\u0003D\u0003D\u0003E\u0003E\u0005E\u038d\nE\u0003F\u0005F\u0390\nF\u0003G\u0003G\u0003G\u0003G\u0007G\u0396\nG\fG\u000eG\u0399\u000bG\u0003G\u0005G\u039c\nG\u0003H\u0003H\u0003H\u0003H\u0007H\u03a2\nH\fH\u000eH\u03a5\u000bH\u0003H\u0003H\u0005H\u03a9\nH\u0003I\u0003I\u0003I\u0003I\u0003I\u0005I\u03b0\nI\u0003I\u0003I\u0005I\u03b4\nI\u0003J\u0003J\u0005J\u03b8\nJ\u0003K\u0003K\u0003L\u0003L\u0003L\u0007L\u03bf\nL\fL\u000eL\u03c2\u000bL\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003N\u0003N\u0005N\u03cc\nN\u0003O\u0003O\u0003O\u0005O\u03d1\nO\u0003P\u0003P\u0003P\u0003P\u0007P\u03d7\nP\fP\u000eP\u03da\u000bP\u0003P\u0005P\u03dd\nP\u0005P\u03df\nP\u0003P\u0003P\u0003Q\u0003Q\u0005Q\u03e5\nQ\u0003Q\u0003Q\u0003Q\u0003R\u0003R\u0005R\u03ec\nR\u0003S\u0003S\u0003S\u0003T\u0003T\u0003T\u0005T\u03f4\nT\u0003T\u0003T\u0003T\u0003T\u0003T\u0003T\u0003T\u0005T\u03fd\nT\u0003T\u0003T\u0003T\u0005T\u0402\nT\u0003U\u0003U\u0003U\u0003U\u0007U\u0408\nU\fU\u000eU\u040b\u000bU\u0003U\u0003U\u0003V\u0005V\u0410\nV\u0003V\u0003V\u0003W\u0003W\u0003W\u0003W\u0006W\u0418\nW\rW\u000eW\u0419\u0003W\u0003W\u0003X\u0003X\u0005X\u0420\nX\u0003Y\u0003Y\u0003Y\u0003Y\u0003Y\u0003Y\u0005Y\u0428\nY\u0003Y\u0003Y\u0003Y\u0003Y\u0005Y\u042e\nY\u0003Z\u0003Z\u0003Z\u0003Z\u0003Z\u0003Z\u0006Z\u0436\nZ\rZ\u000eZ\u0437\u0003Z\u0003Z\u0005Z\u043c\nZ\u0003Z\u0003Z\u0003[\u0003[\u0003[\u0003[\u0003[\u0003[\u0003[\u0003[\u0003[\u0003[\u0003[\u0003[\u0003[\u0003[\u0003[\u0003[\u0003[\u0003[\u0003[\u0005[\u0453\n[\u0003\\\u0003\\\u0005\\\u0457\n\\\u0003]\u0003]\u0005]\u045b\n]\u0003^\u0003^\u0003^\u0003_\u0003_\u0005_\u0462\n_\u0003_\u0003_\u0003_\u0003_\u0003_\u0007_\u0469\n_\f_\u000e_\u046c\u000b_\u0003_\u0003_\u0003_\u0005_\u0471\n_\u0003`\u0003`\u0003`\u0003`\u0003`\u0003`\u0003`\u0005`\u047a\n`\u0003a\u0003a\u0003a\u0003a\u0003a\u0003a\u0003a\u0003a\u0003a\u0003a\u0003a\u0005a\u0487\na\u0003a\u0003a\u0003a\u0003a\u0003a\u0003a\u0003a\u0003a\u0003a\u0003a\u0003a\u0003a\u0003a\u0003a\u0003a\u0005a\u0498\na\u0003b\u0003b\u0003b\u0003b\u0005b\u049e\nb\u0003b\u0003b\u0003b\u0003b\u0003b\u0003c\u0003c\u0003c\u0007c\u04a8\nc\fc\u000ec\u04ab\u000bc\u0003d\u0003d\u0003d\u0003d\u0003e\u0003e\u0003e\u0003e\u0005e\u04b5\ne\u0003e\u0003e\u0003f\u0003f\u0003f\u0003f\u0007f\u04bd\nf\ff\u000ef\u04c0\u000bf\u0003g\u0003g\u0005g\u04c4\ng\u0003h\u0003h\u0003h\u0003h\u0007h\u04ca\nh\fh\u000eh\u04cd\u000bh\u0003h\u0003h\u0003h\u0003i\u0003i\u0003i\u0003i\u0003i\u0003i\u0005i\u04d8\ni\u0003j\u0003j\u0005j\u04dc\nj\u0003k\u0003k\u0005k\u04e0\nk\u0003k\u0003k\u0003k\u0003k\u0003l\u0005l\u04e7\nl\u0003l\u0003l\u0005l\u04eb\nl\u0003l\u0003l\u0005l\u04ef\nl\u0003m\u0003m\u0005m\u04f3\nm\u0003n\u0003n\u0003o\u0003o\u0003o\u0003o\u0003p\u0003p\u0003q\u0003q\u0003q\u0003q\u0003r\u0003r\u0003r\u0003r\u0003r\u0007r\u0506\nr\fr\u000er\u0509\u000br\u0003s\u0005s\u050c\ns\u0003s\u0003s\u0003t\u0003t\u0005t\u0512\nt\u0003u\u0003u\u0003v\u0003v\u0005v\u0518\nv\u0003w\u0003w\u0003w\u0003w\u0003w\u0003w\u0007w\u0520\nw\fw\u000ew\u0523\u000bw\u0003w\u0003w\u0003w\u0003x\u0003x\u0003x\u0006x\u052b\nx\rx\u000ex\u052c\u0003x\u0003x\u0003y\u0003y\u0003y\u0005y\u0534\ny\u0003y\u0003y\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0005z\u054a\nz\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0005z\u0565\nz\u0003z\u0005z\u0568\nz\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0005z\u05a8\nz\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0003z\u0007z\u05b8\nz\fz\u000ez\u05bb\u000bz\u0003{\u0003{\u0003{\u0003{\u0003{\u0003{\u0003{\u0003{\u0003{\u0003{\u0005{\u05c7\n{\u0003|\u0003|\u0003|\u0003|\u0005|\u05cd\n|\u0003|\u0007|\u05d0\n|\f|\u000e|\u05d3\u000b|\u0003}\u0003}\u0006}\u05d7\n}\r}\u000e}\u05d8\u0003}\u0005}\u05dc\n}\u0003~\u0003~\u0003~\u0005~\u05e1\n~\u0003~\u0003~\u0003~\u0007~\u05e6\n~\f~\u000e~\u05e9\u000b~\u0003\u007f\u0003\u007f\u0003\u007f\u0003\u007f\u0003\u007f\u0003\u007f\u0003\u007f\u0003\u007f\u0003\u007f\u0003\u007f\u0003\u007f\u0005\u007f\u05f6\n\u007f\u0003\u007f\u0003\u007f\u0003\u007f\u0003\u007f\u0005\u007f\u05fc\n\u007f\u0003\u007f\u0003\u007f\u0003\u007f\u0003\u007f\u0003\u007f\u0003\u007f\u0005\u007f\u0604\n\u007f\u0003\u007f\u0003\u007f\u0003\u007f\u0003\u007f\u0003\u007f\u0003\u007f\u0003\u007f\u0003\u007f\u0003\u007f\u0005\u007f\u060f\n\u007f\u0003\u0080\u0003\u0080\u0003\u0080\u0003\u0080\u0005\u0080\u0615\n\u0080\u0003\u0081\u0003\u0081\u0005\u0081\u0619\n\u0081\u0003\u0082\u0003\u0082\u0005\u0082\u061d\n\u0082\u0003\u0082\u0003\u0082\u0003\u0083\u0003\u0083\u0003\u0083\u0005\u0083\u0624\n\u0083\u0003\u0083\u0003\u0083\u0003\u0084\u0003\u0084\u0005\u0084\u062a\n\u0084\u0003\u0084\u0003\u0084\u0003\u0084\u0003\u0084\u0003\u0084\u0003\u0084\u0003\u0084\u0003\u0084\u0003\u0084\u0003\u0084\u0003\u0084\u0003\u0084\u0003\u0084\u0005\u0084\u0639\n\u0084\u0003\u0085\u0003\u0085\u0003\u0085\u0003\u0085\u0003\u0085\u0003\u0085\u0005\u0085\u0641\n\u0085\u0003\u0086\u0003\u0086\u0003\u0086\u0005\u0086\u0646\n\u0086\u0003\u0087\u0003\u0087\u0003\u0087\u0005\u0087\u064b\n\u0087\u0003\u0088\u0003\u0088\u0005\u0088\u064f\n\u0088\u0003\u0088\u0005\u0088\u0652\n\u0088\u0003\u0088\u0003\u0088\u0003\u0089\u0003\u0089\u0003\u0089\u0005\u0089\u0659\n\u0089\u0003\u0089\u0005\u0089\u065c\n\u0089\u0003\u0089\u0003\u0089\u0003\u008a\u0003\u008a\u0003\u008a\u0007\u008a\u0663\n\u008a\f\u008a\u000e\u008a\u0666\u000b\u008a\u0003\u008b\u0003\u008b\u0003\u008b\u0007\u008b\u066b\n\u008b\f\u008b\u000e\u008b\u066e\u000b\u008b\u0003\u008c\u0003\u008c\u0003\u008c\u0003\u008c\u0003\u008c\u0003\u008c\u0003\u008c\u0003\u008c\u0003\u008c\u0003\u008c\u0005\u008c\u067a\n\u008c\u0003\u008d\u0003\u008d\u0003\u008d\u0003\u008d\u0003\u008d\u0003\u008d\u0003\u008d\u0003\u008d\u0003\u008d\u0003\u008d\u0005\u008d\u0686\n\u008d\u0003\u008e\u0003\u008e\u0003\u008e\u0003\u008e\u0003\u008e\u0003\u008e\u0003\u008e\u0003\u008e\u0003\u008e\u0003\u008e\u0005\u008e\u0692\n\u008e\u0003\u008f\u0003\u008f\u0005\u008f\u0696\n\u008f\u0003\u0090\u0003\u0090\u0005\u0090\u069a\n\u0090\u0003\u0091\u0003\u0091\u0005\u0091\u069e\n\u0091\u0003\u0092\u0003\u0092\u0003\u0092\u0003\u0092\u0005\u0092\u06a4\n\u0092\u0003\u0092\u0006\u0092\u06a7\n\u0092\r\u0092\u000e\u0092\u06a8\u0003\u0092\u0003\u0092\u0003\u0092\u0005\u0092\u06ae\n\u0092\u0005\u0092\u06b0\n\u0092\u0003\u0093\u0003\u0093\u0003\u0093\u0005\u0093\u06b5\n\u0093\u0003\u0093\u0003\u0093\u0003\u0094\u0003\u0094\u0003\u0094\u0003\u0094\u0003\u0094\u0005\u0094\u06be\n\u0094\u0003\u0094\u0003\u0094\u0003\u0095\u0003\u0095\u0003\u0096\u0003\u0096\u0003\u0096\u0003\u0096\u0005\u0096\u06c8\n\u0096\u0005\u0096\u06ca\n\u0096\u0003\u0097\u0003\u0097\u0003\u0097\u0003\u0097\u0003\u0097\u0003\u0097\u0003\u0098\u0003\u0098\u0003\u0098\u0005\u0098\u06d5\n\u0098\u0003\u0099\u0003\u0099\u0005\u0099\u06d9\n\u0099\u0003\u0099\u0005\u0099\u06dc\n\u0099\u0003\u0099\u0003\u0099\u0003\u009a\u0003\u009a\u0003\u009a\u0003\u009a\u0003\u009a\u0007\u009a\u06e5\n\u009a\f\u009a\u000e\u009a\u06e8\u000b\u009a\u0003\u009b\u0003\u009b\u0003\u009b\u0003\u009b\u0003\u009b\u0007\u009b\u06ef\n\u009b\f\u009b\u000e\u009b\u06f2\u000b\u009b\u0003\u009c\u0003\u009c\u0005\u009c\u06f6\n\u009c\u0003\u009d\u0003\u009d\u0005\u009d\u06fa\n\u009d\u0003\u009e\u0003\u009e\u0003\u009e\u0005\u009e\u06ff\n\u009e\u0003\u009f\u0003\u009f\u0003\u00a0\u0003\u00a0\u0003\u00a1\u0003\u00a1\u0003\u00a2\u0003\u00a2\u0003\u00a3\u0003\u00a3\u0003\u00a4\u0003\u00a4\u0003\u00a5\u0007\u00a5\u070e\n\u00a5\f\u00a5\u000e\u00a5\u0711\u000b\u00a5\u0003\u00a6\u0006\u00a6\u0714\n\u00a6\r\u00a6\u000e\u00a6\u0715\u0003\u00a6\u0002\u0002\u0003\u00f2\u00a7\u0002\u0002\u0004\u0002\u0006\u0002\b\u0002\n\u0002\f\u0002\u000e\u0002\u0010\u0002\u0012\u0002\u0014\u0002\u0016\u0002\u0018\u0002\u001a\u0002\u001c\u0002\u001e\u0002 \u0002\"\u0002$\u0002&\u0002(\u0002*\u0002,\u0002.\u00020\u00022\u00024\u00026\u00028\u0002:\u0002<\u0002>\u0002@\u0002B\u0002D\u0002F\u0002H\u0002J\u0002L\u0002N\u0002P\u0002R\u0002T\u0002V\u0002X\u0002Z\u0002\\\u0002^\u0002`\u0002b\u0002d\u0002f\u0002h\u0002j\u0002l\u0002n\u0002p\u0002r\u0002t\u0002v\u0002x\u0002z\u0002|\u0002~\u0002\u0080\u0002\u0082\u0002\u0084\u0002\u0086\u0002\u0088\u0002\u008a\u0002\u008c\u0002\u008e\u0002\u0090\u0002\u0092\u0002\u0094\u0002\u0096\u0002\u0098\u0002\u009a\u0002\u009c\u0002\u009e\u0002\u00a0\u0002\u00a2\u0002\u00a4\u0002\u00a6\u0002\u00a8\u0002\u00aa\u0002\u00ac\u0002\u00ae\u0002\u00b0\u0002\u00b2\u0002\u00b4\u0002\u00b6\u0002\u00b8\u0002\u00ba\u0002\u00bc\u0002\u00be\u0002\u00c0\u0002\u00c2\u0002\u00c4\u0002\u00c6\u0002\u00c8\u0002\u00ca\u0002\u00cc\u0002\u00ce\u0002\u00d0\u0002\u00d2\u0002\u00d4\u0002\u00d6\u0002\u00d8\u0002\u00da\u0002\u00dc\u0002\u00de\u0002\u00e0\u0002\u00e2\u0002\u00e4\u0002\u00e6\u0002\u00e8\u0002\u00ea\u0002\u00ec\u0002\u00ee\u0002\u00f0\u0002\u00f2\u0002\u00f4\u0002\u00f6\u0002\u00f8\u0002\u00fa\u0002\u00fc\u0002\u00fe\u0002\u0100\u0002\u0102\u0002\u0104\u0002\u0106\u0002\u0108\u0002\u010a\u0002\u010c\u0002\u010e\u0002\u0110\u0002\u0112\u0002\u0114\u0002\u0116\u0002\u0118\u0002\u011a\u0002\u011c\u0002\u011e\u0002\u0120\u0002\u0122\u0002\u0124\u0002\u0126\u0002\u0128\u0002\u012a\u0002\u012c\u0002\u012e\u0002\u0130\u0002\u0132\u0002\u0134\u0002\u0136\u0002\u0138\u0002\u013a\u0002\u013c\u0002\u013e\u0002\u0140\u0002\u0142\u0002\u0144\u0002\u0146\u0002\u0148\u0002\u014a\u0002\u0002\u001a\b\u0002\n\n\u000e\u000e''66::==\b\u0002\u0010\u0010\u0019\u0019\u001e\u001e)),.13\b\u0002\n\n\u000e\u000e\u0010\u0010\u001e\u001e,.23\u0004\u0002\u001d\u001d44\u0004\u0002__gg\u0004\u0002\u000b\u000bgg\u0003\u0002no\u0004\u0002UUgg\u0003\u0002de\u0003\u0002nq\u0004\u0002rsww\u0003\u0002pq\u0003\u0002CF\u0006\u0002\u000b\u000bWWbcij\u0005\u0002RThhkk\u0003\u0002NO\u0005\u0002\t\t%%VV\u0005\u0002QQaax\u0083\u0005\u0002GHJJ``\u0004\u0002II\\\\\n\u0002\t\t\u000b\f\u000e\u000e\u0013\u0013++//11\u0084\u0085\u0004\u0002\u000f\u000f<<\u0004\u0002\t>AB\u0004\u0002^^\u0089\u0089\u0002\u0781\u0002\u014c\u0003\u0002\u0002\u0002\u0004\u0158\u0003\u0002\u0002\u0002\u0006\u0169\u0003\u0002\u0002\u0002\b\u016b\u0003\u0002\u0002\u0002\n\u016f\u0003\u0002\u0002\u0002\f\u017b\u0003\u0002\u0002\u0002\u000e\u0180\u0003\u0002\u0002\u0002\u0010\u0185\u0003\u0002\u0002\u0002\u0012\u0187\u0003\u0002\u0002\u0002\u0014\u0197\u0003\u0002\u0002\u0002\u0016\u0199\u0003\u0002\u0002\u0002\u0018\u01a4\u0003\u0002\u0002\u0002\u001a\u01a8\u0003\u0002\u0002\u0002\u001c\u01ad\u0003\u0002\u0002\u0002\u001e\u01af\u0003\u0002\u0002\u0002 \u01b8\u0003\u0002\u0002\u0002\"\u01c7\u0003\u0002\u0002\u0002$\u01cf\u0003\u0002\u0002\u0002&\u01d9\u0003\u0002\u0002\u0002(\u01f0\u0003\u0002\u0002\u0002*\u0215\u0003\u0002\u0002\u0002,\u0234\u0003\u0002\u0002\u0002.\u023f\u0003\u0002\u0002\u00020\u024d\u0003\u0002\u0002\u00022\u0256\u0003\u0002\u0002\u00024\u0258\u0003\u0002\u0002\u00026\u0275\u0003\u0002\u0002\u00028\u027b\u0003\u0002\u0002\u0002:\u027f\u0003\u0002\u0002\u0002<\u0281\u0003\u0002\u0002\u0002>\u0283\u0003\u0002\u0002\u0002@\u028d\u0003\u0002\u0002\u0002B\u0295\u0003\u0002\u0002\u0002D\u0297\u0003\u0002\u0002\u0002F\u0299\u0003\u0002\u0002\u0002H\u02ac\u0003\u0002\u0002\u0002J\u02b1\u0003\u0002\u0002\u0002L\u02b3\u0003\u0002\u0002\u0002N\u02ba\u0003\u0002\u0002\u0002P\u02c6\u0003\u0002\u0002\u0002R\u02cb\u0003\u0002\u0002\u0002T\u02cf\u0003\u0002\u0002\u0002V\u02d3\u0003\u0002\u0002\u0002X\u02d5\u0003\u0002\u0002\u0002Z\u02ed\u0003\u0002\u0002\u0002\\\u02ef\u0003\u0002\u0002\u0002^\u02f2\u0003\u0002\u0002\u0002`\u02fc\u0003\u0002\u0002\u0002b\u0304\u0003\u0002\u0002\u0002d\u030f\u0003\u0002\u0002\u0002f\u0312\u0003\u0002\u0002\u0002h\u0321\u0003\u0002\u0002\u0002j\u0323\u0003\u0002\u0002\u0002l\u0330\u0003\u0002\u0002\u0002n\u0337\u0003\u0002\u0002\u0002p\u033a\u0003\u0002\u0002\u0002r\u033d\u0003\u0002\u0002\u0002t\u034b\u0003\u0002\u0002\u0002v\u034d\u0003\u0002\u0002\u0002x\u035a\u0003\u0002\u0002\u0002z\u035c\u0003\u0002\u0002\u0002|\u0363\u0003\u0002\u0002\u0002~\u0369\u0003\u0002\u0002\u0002\u0080\u036f\u0003\u0002\u0002\u0002\u0082\u0373\u0003\u0002\u0002\u0002\u0084\u0377\u0003\u0002\u0002\u0002\u0086\u0379\u0003\u0002\u0002\u0002\u0088\u038c\u0003\u0002\u0002\u0002\u008a\u038f\u0003\u0002\u0002\u0002\u008c\u0391\u0003\u0002\u0002\u0002\u008e\u03a8\u0003\u0002\u0002\u0002\u0090\u03aa\u0003\u0002\u0002\u0002\u0092\u03b7\u0003\u0002\u0002\u0002\u0094\u03b9\u0003\u0002\u0002\u0002\u0096\u03bb\u0003\u0002\u0002\u0002\u0098\u03c3\u0003\u0002\u0002\u0002\u009a\u03cb\u0003\u0002\u0002\u0002\u009c\u03d0\u0003\u0002\u0002\u0002\u009e\u03d2\u0003\u0002\u0002\u0002\u00a0\u03e2\u0003\u0002\u0002\u0002\u00a2\u03eb\u0003\u0002\u0002\u0002\u00a4\u03ed\u0003\u0002\u0002\u0002\u00a6\u0401\u0003\u0002\u0002\u0002\u00a8\u0403\u0003\u0002\u0002\u0002\u00aa\u040f\u0003\u0002\u0002\u0002\u00ac\u0413\u0003\u0002\u0002\u0002\u00ae\u041f\u0003\u0002\u0002\u0002\u00b0\u0421\u0003\u0002\u0002\u0002\u00b2\u042f\u0003\u0002\u0002\u0002\u00b4\u0452\u0003\u0002\u0002\u0002\u00b6\u0454\u0003\u0002\u0002\u0002\u00b8\u0458\u0003\u0002\u0002\u0002\u00ba\u045c\u0003\u0002\u0002\u0002\u00bc\u045f\u0003\u0002\u0002\u0002\u00be\u0472\u0003\u0002\u0002\u0002\u00c0\u0497\u0003\u0002\u0002\u0002\u00c2\u0499\u0003\u0002\u0002\u0002\u00c4\u04a4\u0003\u0002\u0002\u0002\u00c6\u04ac\u0003\u0002\u0002\u0002\u00c8\u04b0\u0003\u0002\u0002\u0002\u00ca\u04b8\u0003\u0002\u0002\u0002\u00cc\u04c3\u0003\u0002\u0002\u0002\u00ce\u04c5\u0003\u0002\u0002\u0002\u00d0\u04d7\u0003\u0002\u0002\u0002\u00d2\u04db\u0003\u0002\u0002\u0002\u00d4\u04dd\u0003\u0002\u0002\u0002\u00d6\u04e6\u0003\u0002\u0002\u0002\u00d8\u04f2\u0003\u0002\u0002\u0002\u00da\u04f4\u0003\u0002\u0002\u0002\u00dc\u04f6\u0003\u0002\u0002\u0002\u00de\u04fa\u0003\u0002\u0002\u0002\u00e0\u04fc\u0003\u0002\u0002\u0002\u00e2\u0500\u0003\u0002\u0002\u0002\u00e4\u050b\u0003\u0002\u0002\u0002\u00e6\u0511\u0003\u0002\u0002\u0002\u00e8\u0513\u0003\u0002\u0002\u0002\u00ea\u0515\u0003\u0002\u0002\u0002\u00ec\u0519\u0003\u0002\u0002\u0002\u00ee\u052a\u0003\u0002\u0002\u0002\u00f0\u0533\u0003\u0002\u0002\u0002\u00f2\u0549\u0003\u0002\u0002\u0002\u00f4\u05c6\u0003\u0002\u0002\u0002\u00f6\u05c8\u0003\u0002\u0002\u0002\u00f8\u05d4\u0003\u0002\u0002\u0002\u00fa\u05e0\u0003\u0002\u0002\u0002\u00fc\u060e\u0003\u0002\u0002\u0002\u00fe\u0614\u0003\u0002\u0002\u0002\u0100\u0618\u0003\u0002\u0002\u0002\u0102\u061a\u0003\u0002\u0002\u0002\u0104\u0620\u0003\u0002\u0002\u0002\u0106\u0638\u0003\u0002\u0002\u0002\u0108\u0640\u0003\u0002\u0002\u0002\u010a\u0645\u0003\u0002\u0002\u0002\u010c\u064a\u0003\u0002\u0002\u0002\u010e\u064c\u0003\u0002\u0002\u0002\u0110\u0655\u0003\u0002\u0002\u0002\u0112\u065f\u0003\u0002\u0002\u0002\u0114\u0667\u0003\u0002\u0002\u0002\u0116\u0679\u0003\u0002\u0002\u0002\u0118\u0685\u0003\u0002\u0002\u0002\u011a\u0691\u0003\u0002\u0002\u0002\u011c\u0695\u0003\u0002\u0002\u0002\u011e\u0699\u0003\u0002\u0002\u0002\u0120\u069d\u0003\u0002\u0002\u0002\u0122\u069f\u0003\u0002\u0002\u0002\u0124\u06b1\u0003\u0002\u0002\u0002\u0126\u06b8\u0003\u0002\u0002\u0002\u0128\u06c1\u0003\u0002\u0002\u0002\u012a\u06c3\u0003\u0002\u0002\u0002\u012c\u06cb\u0003\u0002\u0002\u0002\u012e\u06d4\u0003\u0002\u0002\u0002\u0130\u06d6\u0003\u0002\u0002\u0002\u0132\u06df\u0003\u0002\u0002\u0002\u0134\u06e9\u0003\u0002\u0002\u0002\u0136\u06f5\u0003\u0002\u0002\u0002\u0138\u06f9\u0003\u0002\u0002\u0002\u013a\u06fe\u0003\u0002\u0002\u0002\u013c\u0700\u0003\u0002\u0002\u0002\u013e\u0702\u0003\u0002\u0002\u0002\u0140\u0704\u0003\u0002\u0002\u0002\u0142\u0706\u0003\u0002\u0002\u0002\u0144\u0708\u0003\u0002\u0002\u0002\u0146\u070a\u0003\u0002\u0002\u0002\u0148\u070f\u0003\u0002\u0002\u0002\u014a\u0713\u0003\u0002\u0002\u0002\u014c\u0151\u0005\u0148\u00a5\u0002\u014d\u014f\u0005\b\u0005\u0002\u014e\u0150\u0005\u014a\u00a6\u0002\u014f\u014e\u0003\u0002\u0002\u0002\u014f\u0150\u0003\u0002\u0002\u0002\u0150\u0152\u0003\u0002\u0002\u0002\u0151\u014d\u0003\u0002\u0002\u0002\u0151\u0152\u0003\u0002\u0002\u0002\u0152\u0154\u0003\u0002\u0002\u0002\u0153\u0155\u0005\u0004\u0003\u0002\u0154\u0153\u0003\u0002\u0002\u0002\u0154\u0155\u0003\u0002\u0002\u0002\u0155\u0156\u0003\u0002\u0002\u0002\u0156\u0157\u0007\u0002\u0002\u0003\u0157\u0003\u0003\u0002\u0002\u0002\u0158\u015e\u0005\u0006\u0004\u0002\u0159\u015a\u0005\u014a\u00a6\u0002\u015a\u015b\u0005\u0006\u0004\u0002\u015b\u015d\u0003\u0002\u0002\u0002\u015c\u0159\u0003\u0002\u0002\u0002\u015d\u0160\u0003\u0002\u0002\u0002\u015e\u015c\u0003\u0002\u0002\u0002\u015e\u015f\u0003\u0002\u0002\u0002\u015f\u0162\u0003\u0002\u0002\u0002\u0160\u015e\u0003\u0002\u0002\u0002\u0161\u0163\u0005\u014a\u00a6\u0002\u0162\u0161\u0003\u0002\u0002\u0002\u0162\u0163\u0003\u0002\u0002\u0002\u0163\u0005\u0003\u0002\u0002\u0002\u0164\u016a\u0005\n\u0006\u0002\u0165\u016a\u0005\f\u0007\u0002\u0166\u0167\u0006\u0004\u0002\u0002\u0167\u016a\u00054\u001b\u0002\u0168\u016a\u0005\u00c0a\u0002\u0169\u0164\u0003\u0002\u0002\u0002\u0169\u0165\u0003\u0002\u0002\u0002\u0169\u0166\u0003\u0002\u0002\u0002\u0169\u0168\u0003\u0002\u0002\u0002\u016a\u0007\u0003\u0002\u0002\u0002\u016b\u016c\u0005\u008eH\u0002\u016c\u016d\u0007*\u0002\u0002\u016d\u016e\u0005j6\u0002\u016e\t\u0003\u0002\u0002\u0002\u016f\u0170\u0005\u008eH\u0002\u0170\u0172\u0007$\u0002\u0002\u0171\u0173\u00072\u0002\u0002\u0172\u0171\u0003\u0002\u0002\u0002\u0172\u0173\u0003\u0002\u0002\u0002\u0173\u0174\u0003\u0002\u0002\u0002\u0174\u0179\u0005j6\u0002\u0175\u0176\u0007`\u0002\u0002\u0176\u017a\u0007r\u0002\u0002\u0177\u0178\u0007\t\u0002\u0002\u0178\u017a\u0005\u0140\u00a1\u0002\u0179\u0175\u0003\u0002\u0002\u0002\u0179\u0177\u0003\u0002\u0002\u0002\u0179\u017a\u0003\u0002\u0002\u0002\u017a\u000b\u0003\u0002\u0002\u0002\u017b\u017c\u0005\u0014\u000b\u0002\u017c\u017d\u0005(\u0015\u0002\u017d\r\u0003\u0002\u0002\u0002\u017e\u0181\u0005\u0018\r\u0002\u017f\u0181\t\u0002\u0002\u0002\u0180\u017e\u0003\u0002\u0002\u0002\u0180\u017f\u0003\u0002\u0002\u0002\u0181\u000f\u0003\u0002\u0002\u0002\u0182\u0183\u0005\u0012\n\u0002\u0183\u0184\u0005\u0148\u00a5\u0002\u0184\u0186\u0003\u0002\u0002\u0002\u0185\u0182\u0003\u0002\u0002\u0002\u0185\u0186\u0003\u0002\u0002\u0002\u0186\u0011\u0003\u0002\u0002\u0002\u0187\u018d\u0005\u000e\b\u0002\u0188\u0189\u0005\u0148\u00a5\u0002\u0189\u018a\u0005\u000e\b\u0002\u018a\u018c\u0003\u0002\u0002\u0002\u018b\u0188\u0003\u0002\u0002\u0002\u018c\u018f\u0003\u0002\u0002\u0002\u018d\u018b\u0003\u0002\u0002\u0002\u018d\u018e\u0003\u0002\u0002\u0002\u018e\u0013\u0003\u0002\u0002\u0002\u018f\u018d\u0003\u0002\u0002\u0002\u0190\u0194\u0005\u0016\f\u0002\u0191\u0193\u0007\u0089\u0002\u0002\u0192\u0191\u0003\u0002\u0002\u0002\u0193\u0196\u0003\u0002\u0002\u0002\u0194\u0192\u0003\u0002\u0002\u0002\u0194\u0195\u0003\u0002\u0002\u0002\u0195\u0198\u0003\u0002\u0002\u0002\u0196\u0194\u0003\u0002\u0002\u0002\u0197\u0190\u0003\u0002\u0002\u0002\u0197\u0198\u0003\u0002\u0002\u0002\u0198\u0015\u0003\u0002\u0002\u0002\u0199\u019f\u0005\u0018\r\u0002\u019a\u019b\u0005\u0148\u00a5\u0002\u019b\u019c\u0005\u0018\r\u0002\u019c\u019e\u0003\u0002\u0002\u0002\u019d\u019a\u0003\u0002\u0002\u0002\u019e\u01a1\u0003\u0002\u0002\u0002\u019f\u019d\u0003\u0002\u0002\u0002\u019f\u01a0\u0003\u0002\u0002\u0002\u01a0\u0017\u0003\u0002\u0002\u0002\u01a1\u019f\u0003\u0002\u0002\u0002\u01a2\u01a5\u0005\u0090I\u0002\u01a3\u01a5\t\u0003\u0002\u0002\u01a4\u01a2\u0003\u0002\u0002\u0002\u01a4\u01a3\u0003\u0002\u0002\u0002\u01a5\u0019\u0003\u0002\u0002\u0002\u01a6\u01a9\u0005\u0090I\u0002\u01a7\u01a9\t\u0004\u0002\u0002\u01a8\u01a6\u0003\u0002\u0002\u0002\u01a8\u01a7\u0003\u0002\u0002\u0002\u01a9\u001b\u0003\u0002\u0002\u0002\u01aa\u01ab\u0005\u001e\u0010\u0002\u01ab\u01ac\u0005\u0148\u00a5\u0002\u01ac\u01ae\u0003\u0002\u0002\u0002\u01ad\u01aa\u0003\u0002\u0002\u0002\u01ad\u01ae\u0003\u0002\u0002\u0002\u01ae\u001d\u0003\u0002\u0002\u0002\u01af\u01b5\u0005\u001a\u000e\u0002\u01b0\u01b1\u0005\u0148\u00a5\u0002\u01b1\u01b2\u0005\u001a\u000e\u0002\u01b2\u01b4\u0003\u0002\u0002\u0002\u01b3\u01b0\u0003\u0002\u0002\u0002\u01b4\u01b7\u0003\u0002\u0002\u0002\u01b5\u01b3\u0003\u0002\u0002\u0002\u01b5\u01b6\u0003\u0002\u0002\u0002\u01b6\u001f\u0003\u0002\u0002\u0002\u01b7\u01b5\u0003\u0002\u0002\u0002\u01b8\u01b9\u0007c\u0002\u0002\u01b9\u01ba\u0005\u0148\u00a5\u0002\u01ba\u01c1\u0005\"\u0012\u0002\u01bb\u01bc\u0007_\u0002\u0002\u01bc\u01bd\u0005\u0148\u00a5\u0002\u01bd\u01be\u0005\"\u0012\u0002\u01be\u01c0\u0003\u0002\u0002\u0002\u01bf\u01bb\u0003\u0002\u0002\u0002\u01c0\u01c3\u0003\u0002\u0002\u0002\u01c1\u01bf\u0003\u0002\u0002\u0002\u01c1\u01c2\u0003\u0002\u0002\u0002\u01c2\u01c4\u0003\u0002\u0002\u0002\u01c3\u01c1\u0003\u0002\u0002\u0002\u01c4\u01c5\u0005\u0148\u00a5\u0002\u01c5\u01c6\u0007b\u0002\u0002\u01c6!\u0003\u0002\u0002\u0002\u01c7\u01c8\u0005\u008eH\u0002\u01c8\u01cd\u0005\u013e\u00a0\u0002\u01c9\u01ca\u0007\u001d\u0002\u0002\u01ca\u01cb\u0005\u0148\u00a5\u0002\u01cb\u01cc\u0005$\u0013\u0002\u01cc\u01ce\u0003\u0002\u0002\u0002\u01cd\u01c9\u0003\u0002\u0002\u0002\u01cd\u01ce\u0003\u0002\u0002\u0002\u01ce#\u0003\u0002\u0002\u0002\u01cf\u01d6\u0005N(\u0002\u01d0\u01d1\u0007t\u0002\u0002\u01d1\u01d2\u0005\u0148\u00a5\u0002\u01d2\u01d3\u0005N(\u0002\u01d3\u01d5\u0003\u0002\u0002\u0002\u01d4\u01d0\u0003\u0002\u0002\u0002\u01d5\u01d8\u0003\u0002\u0002\u0002\u01d6\u01d4\u0003\u0002\u0002\u0002\u01d6\u01d7\u0003\u0002\u0002\u0002\u01d7%\u0003\u0002\u0002\u0002\u01d8\u01d6\u0003\u0002\u0002\u0002\u01d9\u01e0\u0005N(\u0002\u01da\u01db\u0007_\u0002\u0002\u01db\u01dc\u0005\u0148\u00a5\u0002\u01dc\u01dd\u0005N(\u0002\u01dd\u01df\u0003\u0002\u0002\u0002\u01de\u01da\u0003\u0002\u0002\u0002\u01df\u01e2\u0003\u0002\u0002\u0002\u01e0\u01de\u0003\u0002\u0002\u0002\u01e0\u01e1\u0003\u0002\u0002\u0002\u01e1'\u0003\u0002\u0002\u0002\u01e2\u01e0\u0003\u0002\u0002\u0002\u01e3\u01e4\u0007\u0016\u0002\u0002\u01e4\u01f1\b\u0015\u0001\u0002\u01e5\u01e6\u0007&\u0002\u0002\u01e6\u01f1\b\u0015\u0001\u0002\u01e7\u01e8\u0007\u001c\u0002\u0002\u01e8\u01f1\b\u0015\u0001\u0002\u01e9\u01ea\u0007\u0086\u0002\u0002\u01ea\u01eb\u0007&\u0002\u0002\u01eb\u01f1\b\u0015\u0001\u0002\u01ec\u01ed\u0007\f\u0002\u0002\u01ed\u01f1\b\u0015\u0001\u0002\u01ee\u01ef\u0007/\u0002\u0002\u01ef\u01f1\b\u0015\u0001\u0002\u01f0\u01e3\u0003\u0002\u0002\u0002\u01f0\u01e5\u0003\u0002\u0002\u0002\u01f0\u01e7\u0003\u0002\u0002\u0002\u01f0\u01e9\u0003\u0002\u0002\u0002\u01f0\u01ec\u0003\u0002\u0002\u0002\u01f0\u01ee\u0003\u0002\u0002\u0002\u01f1\u01f2\u0003\u0002\u0002\u0002\u01f2\u01f6\u0005\u0140\u00a1\u0002\u01f3\u01f4\u0005\u0148\u00a5\u0002\u01f4\u01f5\u0005 \u0011\u0002\u01f5\u01f7\u0003\u0002\u0002\u0002\u01f6\u01f3\u0003\u0002\u0002\u0002\u01f6\u01f7\u0003\u0002\u0002\u0002\u01f7\u01fb\u0003\u0002\u0002\u0002\u01f8\u01f9\u0005\u0148\u00a5\u0002\u01f9\u01fa\u0005`1\u0002\u01fa\u01fc\u0003\u0002\u0002\u0002\u01fb\u01f8\u0003\u0002\u0002\u0002\u01fb\u01fc\u0003\u0002\u0002\u0002\u01fc\u0202\u0003\u0002\u0002\u0002\u01fd\u01fe\u0005\u0148\u00a5\u0002\u01fe\u01ff\u0007\u001d\u0002\u0002\u01ff\u0200\u0005\u0148\u00a5\u0002\u0200\u0201\u0005&\u0014\u0002\u0201\u0203\u0003\u0002\u0002\u0002\u0202\u01fd\u0003\u0002\u0002\u0002\u0202\u0203\u0003\u0002\u0002\u0002\u0203\u0209\u0003\u0002\u0002\u0002\u0204\u0205\u0005\u0148\u00a5\u0002\u0205\u0206\u0007#\u0002\u0002\u0206\u0207\u0005\u0148\u00a5\u0002\u0207\u0208\u0005&\u0014\u0002\u0208\u020a\u0003\u0002\u0002\u0002\u0209\u0204\u0003\u0002\u0002\u0002\u0209\u020a\u0003\u0002\u0002\u0002\u020a\u0210\u0003\u0002\u0002\u0002\u020b\u020c\u0005\u0148\u00a5\u0002\u020c\u020d\u0007+\u0002\u0002\u020d\u020e\u0005\u0148\u00a5\u0002\u020e\u020f\u0005&\u0014\u0002\u020f\u0211\u0003\u0002\u0002\u0002\u0210\u020b\u0003\u0002\u0002\u0002\u0210\u0211\u0003\u0002\u0002\u0002\u0211\u0212\u0003\u0002\u0002\u0002\u0212\u0213\u0005\u0148\u00a5\u0002\u0213\u0214\u0005*\u0016\u0002\u0214)\u0003\u0002\u0002\u0002\u0215\u0216\u0007Z\u0002\u0002\u0216\u0222\u0005\u0148\u00a5\u0002\u0217\u0218\u0006\u0016\u0003\u0003\u0218\u021c\u0005,\u0017\u0002\u0219\u021a\u0005\u0148\u00a5\u0002\u021a\u021b\u0007_\u0002\u0002\u021b\u021d\u0003\u0002\u0002\u0002\u021c\u0219\u0003\u0002\u0002\u0002\u021c\u021d\u0003\u0002\u0002\u0002\u021d\u021f\u0003\u0002\u0002\u0002\u021e\u0220\u0005\u014a\u00a6\u0002\u021f\u021e\u0003\u0002\u0002\u0002\u021f\u0220\u0003\u0002\u0002\u0002\u0220\u0223\u0003\u0002\u0002\u0002\u0221\u0223\u0003\u0002\u0002\u0002\u0222\u0217\u0003\u0002\u0002\u0002\u0222\u0221\u0003\u0002\u0002\u0002\u0223\u022d\u0003\u0002\u0002\u0002\u0224\u022a\u00050\u0019\u0002\u0225\u0226\u0005\u014a\u00a6\u0002\u0226\u0227\u00050\u0019\u0002\u0227\u0229\u0003\u0002\u0002\u0002\u0228\u0225\u0003\u0002\u0002\u0002\u0229\u022c\u0003\u0002\u0002\u0002\u022a\u0228\u0003\u0002\u0002\u0002\u022a\u022b\u0003\u0002\u0002\u0002\u022b\u022e\u0003\u0002\u0002\u0002\u022c\u022a\u0003\u0002\u0002\u0002\u022d\u0224\u0003\u0002\u0002\u0002\u022d\u022e\u0003\u0002\u0002\u0002\u022e\u0230\u0003\u0002\u0002\u0002\u022f\u0231\u0005\u014a\u00a6\u0002\u0230\u022f\u0003\u0002\u0002\u0002\u0230\u0231\u0003\u0002\u0002\u0002\u0231\u0232\u0003\u0002\u0002\u0002\u0232\u0233\u0007[\u0002\u0002\u0233+\u0003\u0002\u0002\u0002\u0234\u023c\u0005.\u0018\u0002\u0235\u0236\u0005\u0148\u00a5\u0002\u0236\u0237\u0007_\u0002\u0002\u0237\u0238\u0005\u0148\u00a5\u0002\u0238\u0239\u0005.\u0018\u0002\u0239\u023b\u0003\u0002\u0002\u0002\u023a\u0235\u0003\u0002\u0002\u0002\u023b\u023e\u0003\u0002\u0002\u0002\u023c\u023a\u0003\u0002\u0002\u0002\u023c\u023d\u0003\u0002\u0002\u0002\u023d-\u0003\u0002\u0002\u0002\u023e\u023c\u0003\u0002\u0002\u0002\u023f\u0240\u0005\u008eH\u0002\u0240\u0242\u0005\u0140\u00a1\u0002\u0241\u0243\u0005\u0130\u0099\u0002\u0242\u0241\u0003\u0002\u0002\u0002\u0242\u0243\u0003\u0002\u0002\u0002\u0243\u0245\u0003\u0002\u0002\u0002\u0244\u0246\u0005\u0128\u0095\u0002\u0245\u0244\u0003\u0002\u0002\u0002\u0245\u0246\u0003\u0002\u0002\u0002\u0246/\u0003\u0002\u0002\u0002\u0247\u0248\u00072\u0002\u0002\u0248\u024a\u0005\u0148\u00a5\u0002\u0249\u0247\u0003\u0002\u0002\u0002\u0249\u024a\u0003\u0002\u0002\u0002\u024a\u024b\u0003\u0002\u0002\u0002\u024b\u024e\u0005\u00a0Q\u0002\u024c\u024e\u00052\u001a\u0002\u024d\u0249\u0003\u0002\u0002\u0002\u024d\u024c\u0003\u0002\u0002\u0002\u024e1\u0003\u0002\u0002\u0002\u024f\u0257\u00054\u001b\u0002\u0250\u0257\u0005<\u001f\u0002\u0251\u0254\u0005\u0010\t\u0002\u0252\u0255\u0005(\u0015\u0002\u0253\u0255\u00056\u001c\u0002\u0254\u0252\u0003\u0002\u0002\u0002\u0254\u0253\u0003\u0002\u0002\u0002\u0255\u0257\u0003\u0002\u0002\u0002\u0256\u024f\u0003\u0002\u0002\u0002\u0256\u0250\u0003\u0002\u0002\u0002\u0256\u0251\u0003\u0002\u0002\u0002\u02573\u0003\u0002\u0002\u0002\u0258\u025a\u0005\u0010\t\u0002\u0259\u025b\u0005 \u0011\u0002\u025a\u0259\u0003\u0002\u0002\u0002\u025a\u025b\u0003\u0002\u0002\u0002\u025b\u025f\u0003\u0002\u0002\u0002\u025c\u025d\u0005:\u001e\u0002\u025d\u025e\u0005\u0148\u00a5\u0002\u025e\u0260\u0003\u0002\u0002\u0002\u025f\u025c\u0003\u0002\u0002\u0002\u025f\u0260\u0003\u0002\u0002\u0002\u0260\u0261\u0003\u0002\u0002\u0002\u0261\u0262\u00058\u001d\u0002\u0262\u0273\u0005`1\u0002\u0263\u0264\u0007\u0019\u0002\u0002\u0264\u0265\u0005\u0148\u00a5\u0002\u0265\u0266\u0005\u009cO\u0002\u0266\u0274\u0003\u0002\u0002\u0002\u0267\u0268\u0005\u0148\u00a5\u0002\u0268\u0269\u00079\u0002\u0002\u0269\u026a\u0005\u0148\u00a5\u0002\u026a\u026b\u0005^0\u0002\u026b\u026d\u0003\u0002\u0002\u0002\u026c\u0267\u0003\u0002\u0002\u0002\u026c\u026d\u0003\u0002\u0002\u0002\u026d\u0271\u0003\u0002\u0002\u0002\u026e\u026f\u0005\u0148\u00a5\u0002\u026f\u0270\u0005h5\u0002\u0270\u0272\u0003\u0002\u0002\u0002\u0271\u026e\u0003\u0002\u0002\u0002\u0271\u0272\u0003\u0002\u0002\u0002\u0272\u0274\u0003\u0002\u0002\u0002\u0273\u0263\u0003\u0002\u0002\u0002\u0273\u026c\u0003\u0002\u0002\u0002\u0273\u0274\u0003\u0002\u0002\u0002\u02745\u0003\u0002\u0002\u0002\u0275\u0276\u00058\u001d\u0002\u0276\u0277\u0005\u0148\u00a5\u0002\u0277\u0278\u0005h5\u0002\u02787\u0003\u0002\u0002\u0002\u0279\u027c\u0005\u0140\u00a1\u0002\u027a\u027c\u0005\u013c\u009f\u0002\u027b\u0279\u0003\u0002\u0002\u0002\u027b\u027a\u0003\u0002\u0002\u0002\u027c9\u0003\u0002\u0002\u0002\u027d\u0280\u0005L'\u0002\u027e\u0280\u0007<\u0002\u0002\u027f\u027d\u0003\u0002\u0002\u0002\u027f\u027e\u0003\u0002\u0002\u0002\u0280;\u0003\u0002\u0002\u0002\u0281\u0282\u0005\u00a6T\u0002\u0282=\u0003\u0002\u0002\u0002\u0283\u028a\u0005@!\u0002\u0284\u0285\u0007_\u0002\u0002\u0285\u0286\u0005\u0148\u00a5\u0002\u0286\u0287\u0005@!\u0002\u0287\u0289\u0003\u0002\u0002\u0002\u0288\u0284\u0003\u0002\u0002\u0002\u0289\u028c\u0003\u0002\u0002\u0002\u028a\u0288\u0003\u0002\u0002\u0002\u028a\u028b\u0003\u0002\u0002\u0002\u028b?\u0003\u0002\u0002\u0002\u028c\u028a\u0003\u0002\u0002\u0002\u028d\u0293\u0005B\"\u0002\u028e\u028f\u0005\u0148\u00a5\u0002\u028f\u0290\u0007a\u0002\u0002\u0290\u0291\u0005\u0148\u00a5\u0002\u0291\u0292\u0005D#\u0002\u0292\u0294\u0003\u0002\u0002\u0002\u0293\u028e\u0003\u0002\u0002\u0002\u0293\u0294\u0003\u0002\u0002\u0002\u0294A\u0003\u0002\u0002\u0002\u0295\u0296\u0005\u0140\u00a1\u0002\u0296C\u0003\u0002\u0002\u0002\u0297\u0298\u0005\u00e6t\u0002\u0298E\u0003\u0002\u0002\u0002\u0299\u02a1\u0005D#\u0002\u029a\u029b\u0005\u0148\u00a5\u0002\u029b\u029c\u0007_\u0002\u0002\u029c\u029d\u0005\u0148\u00a5\u0002\u029d\u029e\u0005D#\u0002\u029e\u02a0\u0003\u0002\u0002\u0002\u029f\u029a\u0003\u0002\u0002\u0002\u02a0\u02a3\u0003\u0002\u0002\u0002\u02a1\u029f\u0003\u0002\u0002\u0002\u02a1\u02a2\u0003\u0002\u0002\u0002\u02a2\u02a4\u0003\u0002\u0002\u0002\u02a3\u02a1\u0003\u0002\u0002\u0002\u02a4\u02a6\u0005\u0148\u00a5\u0002\u02a5\u02a7\u0007_\u0002\u0002\u02a6\u02a5\u0003\u0002\u0002\u0002\u02a6\u02a7\u0003\u0002\u0002\u0002\u02a7G\u0003\u0002\u0002\u0002\u02a8\u02a9\u0005\u008eH\u0002\u02a9\u02aa\u0007\\\u0002\u0002\u02aa\u02ab\u0007]\u0002\u0002\u02ab\u02ad\u0003\u0002\u0002\u0002\u02ac\u02a8\u0003\u0002\u0002\u0002\u02ad\u02ae\u0003\u0002\u0002\u0002\u02ae\u02ac\u0003\u0002\u0002\u0002\u02ae\u02af\u0003\u0002\u0002\u0002\u02afI\u0003\u0002\u0002\u0002\u02b0\u02b2\u0005H%\u0002\u02b1\u02b0\u0003\u0002\u0002\u0002\u02b1\u02b2\u0003\u0002\u0002\u0002\u02b2K\u0003\u0002\u0002\u0002\u02b3\u02b6\u0005\u008eH\u0002\u02b4\u02b7\u0005V,\u0002\u02b5\u02b7\u0005T+\u0002\u02b6\u02b4\u0003\u0002\u0002\u0002\u02b6\u02b5\u0003\u0002\u0002\u0002\u02b7\u02b8\u0003\u0002\u0002\u0002\u02b8\u02b9\u0005J&\u0002\u02b9M\u0003\u0002\u0002\u0002\u02ba\u02c0\u0005\u008eH\u0002\u02bb\u02be\u0005V,\u0002\u02bc\u02be\u0007<\u0002\u0002\u02bd\u02bb\u0003\u0002\u0002\u0002\u02bd\u02bc\u0003\u0002\u0002\u0002\u02be\u02c1\u0003\u0002\u0002\u0002\u02bf\u02c1\u0005R*\u0002\u02c0\u02bd\u0003\u0002\u0002\u0002\u02c0\u02bf\u0003\u0002\u0002\u0002\u02c1\u02c2\u0003\u0002\u0002\u0002\u02c2\u02c3\u0005J&\u0002\u02c3O\u0003\u0002\u0002\u0002\u02c4\u02c7\u0005p9\u0002\u02c5\u02c7\u0005r:\u0002\u02c6\u02c4\u0003\u0002\u0002\u0002\u02c6\u02c5\u0003\u0002\u0002\u0002\u02c7\u02c9\u0003\u0002\u0002\u0002\u02c8\u02ca\u0005X-\u0002\u02c9\u02c8\u0003\u0002\u0002\u0002\u02c9\u02ca\u0003\u0002\u0002\u0002\u02caQ\u0003\u0002\u0002\u0002\u02cb\u02cd\u0005p9\u0002\u02cc\u02ce\u0005X-\u0002\u02cd\u02cc\u0003\u0002\u0002\u0002\u02cd\u02ce\u0003\u0002\u0002\u0002\u02ceS\u0003\u0002\u0002\u0002\u02cf\u02d1\u0005r:\u0002\u02d0\u02d2\u0005X-\u0002\u02d1\u02d0\u0003\u0002\u0002\u0002\u02d1\u02d2\u0003\u0002\u0002\u0002\u02d2U\u0003\u0002\u0002\u0002\u02d3\u02d4\u0007\u000f\u0002\u0002\u02d4W\u0003\u0002\u0002\u0002\u02d5\u02d6\u0007c\u0002\u0002\u02d6\u02d7\u0005\u0148\u00a5\u0002\u02d7\u02de\u0005Z.\u0002\u02d8\u02d9\u0007_\u0002\u0002\u02d9\u02da\u0005\u0148\u00a5\u0002\u02da\u02db\u0005Z.\u0002\u02db\u02dd\u0003\u0002\u0002\u0002\u02dc\u02d8\u0003\u0002\u0002\u0002\u02dd\u02e0\u0003\u0002\u0002\u0002\u02de\u02dc\u0003\u0002\u0002\u0002\u02de\u02df\u0003\u0002\u0002\u0002\u02df\u02e1\u0003\u0002\u0002\u0002\u02e0\u02de\u0003\u0002\u0002\u0002\u02e1\u02e2\u0005\u0148\u00a5\u0002\u02e2\u02e3\u0007b\u0002\u0002\u02e3Y\u0003\u0002\u0002\u0002\u02e4\u02ee\u0005N(\u0002\u02e5\u02e6\u0005\u008eH\u0002\u02e6\u02eb\u0007f\u0002\u0002\u02e7\u02e8\t\u0005\u0002\u0002\u02e8\u02e9\u0005\u0148\u00a5\u0002\u02e9\u02ea\u0005N(\u0002\u02ea\u02ec\u0003\u0002\u0002\u0002\u02eb\u02e7\u0003\u0002\u0002\u0002\u02eb\u02ec\u0003\u0002\u0002\u0002\u02ec\u02ee\u0003\u0002\u0002\u0002\u02ed\u02e4\u0003\u0002\u0002\u0002\u02ed\u02e5\u0003\u0002\u0002\u0002\u02ee[\u0003\u0002\u0002\u0002\u02ef\u02f0\u0005\u008eH\u0002\u02f0\u02f1\u0005p9\u0002\u02f1]\u0003\u0002\u0002\u0002\u02f2\u02f9\u0005\\/\u0002\u02f3\u02f4\u0007_\u0002\u0002\u02f4\u02f5\u0005\u0148\u00a5\u0002\u02f5\u02f6\u0005\\/\u0002\u02f6\u02f8\u0003\u0002\u0002\u0002\u02f7\u02f3\u0003\u0002\u0002\u0002\u02f8\u02fb\u0003\u0002\u0002\u0002\u02f9\u02f7\u0003\u0002\u0002\u0002\u02f9\u02fa\u0003\u0002\u0002\u0002\u02fa_\u0003\u0002\u0002\u0002\u02fb\u02f9\u0003\u0002\u0002\u0002\u02fc\u02fe\u0007X\u0002\u0002\u02fd\u02ff\u0005b2\u0002\u02fe\u02fd\u0003\u0002\u0002\u0002\u02fe\u02ff\u0003\u0002\u0002\u0002\u02ff\u0300\u0003\u0002\u0002\u0002\u0300\u0301\u0005\u0146\u00a4\u0002\u0301a\u0003\u0002\u0002\u0002\u0302\u0305\u0005f4\u0002\u0303\u0305\u0005d3\u0002\u0304\u0302\u0003\u0002\u0002\u0002\u0304\u0303\u0003\u0002\u0002\u0002\u0305\u030c\u0003\u0002\u0002\u0002\u0306\u0307\u0007_\u0002\u0002\u0307\u0308\u0005\u0148\u00a5\u0002\u0308\u0309\u0005f4\u0002\u0309\u030b\u0003\u0002\u0002\u0002\u030a\u0306\u0003\u0002\u0002\u0002\u030b\u030e\u0003\u0002\u0002\u0002\u030c\u030a\u0003\u0002\u0002\u0002\u030c\u030d\u0003\u0002\u0002\u0002\u030dc\u0003\u0002\u0002\u0002\u030e\u030c\u0003\u0002\u0002\u0002\u030f\u0310\u0005N(\u0002\u0310\u0311\u00077\u0002\u0002\u0311e\u0003\u0002\u0002\u0002\u0312\u0314\u0005\u001c\u000f\u0002\u0313\u0315\u0005N(\u0002\u0314\u0313\u0003\u0002\u0002\u0002\u0314\u0315\u0003\u0002\u0002\u0002\u0315\u0317\u0003\u0002\u0002\u0002\u0316\u0318\u0007\u0087\u0002\u0002\u0317\u0316\u0003\u0002\u0002\u0002\u0317\u0318\u0003\u0002\u0002\u0002\u0318\u0319\u0003\u0002\u0002\u0002\u0319\u031f\u0005B\"\u0002\u031a\u031b\u0005\u0148\u00a5\u0002\u031b\u031c\u0007a\u0002\u0002\u031c\u031d\u0005\u0148\u00a5\u0002\u031d\u031e\u0005\u00f2z\u0002\u031e\u0320\u0003\u0002\u0002\u0002\u031f\u031a\u0003\u0002\u0002\u0002\u031f\u0320\u0003\u0002\u0002\u0002\u0320g\u0003\u0002\u0002\u0002\u0321\u0322\u0005\u00a0Q\u0002\u0322i\u0003\u0002\u0002\u0002\u0323\u0328\u0005l7\u0002\u0324\u0325\u0007`\u0002\u0002\u0325\u0327\u0005l7\u0002\u0326\u0324\u0003\u0002\u0002\u0002\u0327\u032a\u0003\u0002\u0002\u0002\u0328\u0326\u0003\u0002\u0002\u0002\u0328\u0329\u0003\u0002\u0002\u0002\u0329k\u0003\u0002\u0002\u0002\u032a\u0328\u0003\u0002\u0002\u0002\u032b\u0331\u0005\u0140\u00a1\u0002\u032c\u0331\u0007\n\u0002\u0002\u032d\u0331\u0007\u000b\u0002\u0002\u032e\u0331\u0007\t\u0002\u0002\u032f\u0331\u0007\f\u0002\u0002\u0330\u032b\u0003\u0002\u0002\u0002\u0330\u032c\u0003\u0002\u0002\u0002\u0330\u032d\u0003\u0002\u0002\u0002\u0330\u032e\u0003\u0002\u0002\u0002\u0330\u032f\u0003\u0002\u0002\u0002\u0331m\u0003\u0002\u0002\u0002\u0332\u0333\u0005l7\u0002\u0333\u0334\u0007`\u0002\u0002\u0334\u0336\u0003\u0002\u0002\u0002\u0335\u0332\u0003\u0002\u0002\u0002\u0336\u0339\u0003\u0002\u0002\u0002\u0337\u0335\u0003\u0002\u0002\u0002\u0337\u0338\u0003\u0002\u0002\u0002\u0338o\u0003\u0002\u0002\u0002\u0339\u0337\u0003\u0002\u0002\u0002\u033a\u033b\u0005n8\u0002\u033b\u033c\u0005\u0140\u00a1\u0002\u033cq\u0003\u0002\u0002\u0002\u033d\u033e\u0005n8\u0002\u033e\u0343\u0005\u013e\u00a0\u0002\u033f\u0340\u0007`\u0002\u0002\u0340\u0342\u0005\u013e\u00a0\u0002\u0341\u033f\u0003\u0002\u0002\u0002\u0342\u0345\u0003\u0002\u0002\u0002\u0343\u0341\u0003\u0002\u0002\u0002\u0343\u0344\u0003\u0002\u0002\u0002\u0344s\u0003\u0002\u0002\u0002\u0345\u0343\u0003\u0002\u0002\u0002\u0346\u034c\u0007?\u0002\u0002\u0347\u034c\u0007@\u0002\u0002\u0348\u034c\u0005\u013c\u009f\u0002\u0349\u034c\u0007A\u0002\u0002\u034a\u034c\u0007B\u0002\u0002\u034b\u0346\u0003\u0002\u0002\u0002\u034b\u0347\u0003\u0002\u0002\u0002\u034b\u0348\u0003\u0002\u0002\u0002\u034b\u0349\u0003\u0002\u0002\u0002\u034b\u034a\u0003\u0002\u0002\u0002\u034cu\u0003\u0002\u0002\u0002\u034d\u034e\u0007\u0004\u0002\u0002\u034e\u0353\u0005x=\u0002\u034f\u0350\u0007\u0006\u0002\u0002\u0350\u0352\u0005x=\u0002\u0351\u034f\u0003\u0002\u0002\u0002\u0352\u0355\u0003\u0002\u0002\u0002\u0353\u0351\u0003\u0002\u0002\u0002\u0353\u0354\u0003\u0002\u0002\u0002\u0354\u0356\u0003\u0002\u0002\u0002\u0355\u0353\u0003\u0002\u0002\u0002\u0356\u0357\u0007\u0005\u0002\u0002\u0357w\u0003\u0002\u0002\u0002\u0358\u035b\u0005z>\u0002\u0359\u035b\u0005\u0086D\u0002\u035a\u0358\u0003\u0002\u0002\u0002\u035a\u0359\u0003\u0002\u0002\u0002\u035by\u0003\u0002\u0002\u0002\u035c\u0360\u0005\u0140\u00a1\u0002\u035d\u035f\u0007\u0007\u0002\u0002\u035e\u035d\u0003\u0002\u0002\u0002\u035f\u0362\u0003\u0002\u0002\u0002\u0360\u035e\u0003\u0002\u0002\u0002\u0360\u0361\u0003\u0002\u0002\u0002\u0361{\u0003\u0002\u0002\u0002\u0362\u0360\u0003\u0002\u0002\u0002\u0363\u0364\u0005\u0080A\u0002\u0364\u0365\u0005\u0148\u00a5\u0002\u0365\u0366\u0007U\u0002\u0002\u0366\u0367\u0005\u0148\u00a5\u0002\u0367\u0368\u0005\u0084C\u0002\u0368}\u0003\u0002\u0002\u0002\u0369\u036a\u0005\u0082B\u0002\u036a\u036b\u0005\u0148\u00a5\u0002\u036b\u036c\u0007U\u0002\u0002\u036c\u036d\u0005\u0148\u00a5\u0002\u036d\u036e\u0005\u0084C\u0002\u036e\u007f\u0003\u0002\u0002\u0002\u036f\u0370\u0005`1\u0002\u0370\u0081\u0003\u0002\u0002\u0002\u0371\u0374\u0005`1\u0002\u0372\u0374\u0005B\"\u0002\u0373\u0371\u0003\u0002\u0002\u0002\u0373\u0372\u0003\u0002\u0002\u0002\u0374\u0083\u0003\u0002\u0002\u0002\u0375\u0378\u0005\u00a0Q\u0002\u0376\u0378\u0005\u00e8u\u0002\u0377\u0375\u0003\u0002\u0002\u0002\u0377\u0376\u0003\u0002\u0002\u0002\u0378\u0085\u0003\u0002\u0002\u0002\u0379\u0382\u0007Z\u0002\u0002\u037a\u037e\u0005\u0148\u00a5\u0002\u037b\u037c\u0005b2\u0002\u037c\u037d\u0005\u0148\u00a5\u0002\u037d\u037f\u0003\u0002\u0002\u0002\u037e\u037b\u0003\u0002\u0002\u0002\u037e\u037f\u0003\u0002\u0002\u0002\u037f\u0380\u0003\u0002\u0002\u0002\u0380\u0381\u0007U\u0002\u0002\u0381\u0383\u0003\u0002\u0002\u0002\u0382\u037a\u0003\u0002\u0002\u0002\u0382\u0383\u0003\u0002\u0002\u0002\u0383\u0385\u0003\u0002\u0002\u0002\u0384\u0386\u0005\u014a\u00a6\u0002\u0385\u0384\u0003\u0002\u0002\u0002\u0385\u0386\u0003\u0002\u0002\u0002\u0386\u0387\u0003\u0002\u0002\u0002\u0387\u0388\u0005\u008aF\u0002\u0388\u0389\u0007[\u0002\u0002\u0389\u0087\u0003\u0002\u0002\u0002\u038a\u038d\u0005\u0086D\u0002\u038b\u038d\u0005|?\u0002\u038c\u038a\u0003\u0002\u0002\u0002\u038c\u038b\u0003\u0002\u0002\u0002\u038d\u0089\u0003\u0002\u0002\u0002\u038e\u0390\u0005\u008cG\u0002\u038f\u038e\u0003\u0002\u0002\u0002\u038f\u0390\u0003\u0002\u0002\u0002\u0390\u008b\u0003\u0002\u0002\u0002\u0391\u0397\u0005\u00a2R\u0002\u0392\u0393\u0005\u014a\u00a6\u0002\u0393\u0394\u0005\u00a2R\u0002\u0394\u0396\u0003\u0002\u0002\u0002\u0395\u0392\u0003\u0002\u0002\u0002\u0396\u0399\u0003\u0002\u0002\u0002\u0397\u0395\u0003\u0002\u0002\u0002\u0397\u0398\u0003\u0002\u0002\u0002\u0398\u039b\u0003\u0002\u0002\u0002\u0399\u0397\u0003\u0002\u0002\u0002\u039a\u039c\u0005\u014a\u00a6\u0002\u039b\u039a\u0003\u0002\u0002\u0002\u039b\u039c\u0003\u0002\u0002\u0002\u039c\u008d\u0003\u0002\u0002\u0002\u039d\u03a3\u0005\u0090I\u0002\u039e\u039f\u0005\u0148\u00a5\u0002\u039f\u03a0\u0005\u0090I\u0002\u03a0\u03a2\u0003\u0002\u0002\u0002\u03a1\u039e\u0003\u0002\u0002\u0002\u03a2\u03a5\u0003\u0002\u0002\u0002\u03a3\u03a1\u0003\u0002\u0002\u0002\u03a3\u03a4\u0003\u0002\u0002\u0002\u03a4\u03a6\u0003\u0002\u0002\u0002\u03a5\u03a3\u0003\u0002\u0002\u0002\u03a6\u03a7\u0005\u0148\u00a5\u0002\u03a7\u03a9\u0003\u0002\u0002\u0002\u03a8\u039d\u0003\u0002\u0002\u0002\u03a8\u03a9\u0003\u0002\u0002\u0002\u03a9\u008f\u0003\u0002\u0002\u0002\u03aa\u03ab\u0007\u0086\u0002\u0002\u03ab\u03b3\u0005\u0094K\u0002\u03ac\u03ad\u0005\u0148\u00a5\u0002\u03ad\u03af\u0007X\u0002\u0002\u03ae\u03b0\u0005\u0092J\u0002\u03af\u03ae\u0003\u0002\u0002\u0002\u03af\u03b0\u0003\u0002\u0002\u0002\u03b0\u03b1\u0003\u0002\u0002\u0002\u03b1\u03b2\u0005\u0146\u00a4\u0002\u03b2\u03b4\u0003\u0002\u0002\u0002\u03b3\u03ac\u0003\u0002\u0002\u0002\u03b3\u03b4\u0003\u0002\u0002\u0002\u03b4\u0091\u0003\u0002\u0002\u0002\u03b5\u03b8\u0005\u0096L\u0002\u03b6\u03b8\u0005\u009cO\u0002\u03b7\u03b5\u0003\u0002\u0002\u0002\u03b7\u03b6\u0003\u0002\u0002\u0002\u03b8\u0093\u0003\u0002\u0002\u0002\u03b9\u03ba\u0005p9\u0002\u03ba\u0095\u0003\u0002\u0002\u0002\u03bb\u03c0\u0005\u0098M\u0002\u03bc\u03bd\u0007_\u0002\u0002\u03bd\u03bf\u0005\u0098M\u0002\u03be\u03bc\u0003\u0002\u0002\u0002\u03bf\u03c2\u0003\u0002\u0002\u0002\u03c0\u03be\u0003\u0002\u0002\u0002\u03c0\u03c1\u0003\u0002\u0002\u0002\u03c1\u0097\u0003\u0002\u0002\u0002\u03c2\u03c0\u0003\u0002\u0002\u0002\u03c3\u03c4\u0005\u009aN\u0002\u03c4\u03c5\u0005\u0148\u00a5\u0002\u03c5\u03c6\u0007a\u0002\u0002\u03c6\u03c7\u0005\u0148\u00a5\u0002\u03c7\u03c8\u0005\u009cO\u0002\u03c8\u0099\u0003\u0002\u0002\u0002\u03c9\u03cc\u0005\u0140\u00a1\u0002\u03ca\u03cc\u0005\u0144\u00a3\u0002\u03cb\u03c9\u0003\u0002\u0002\u0002\u03cb\u03ca\u0003\u0002\u0002\u0002\u03cc\u009b\u0003\u0002\u0002\u0002\u03cd\u03d1\u0005\u009eP\u0002\u03ce\u03d1\u0005\u0090I\u0002\u03cf\u03d1\u0005\u00f2z\u0002\u03d0\u03cd\u0003\u0002\u0002\u0002\u03d0\u03ce\u0003\u0002\u0002\u0002\u03d0\u03cf\u0003\u0002\u0002\u0002\u03d1\u009d\u0003\u0002\u0002\u0002\u03d2\u03de\u0007\\\u0002\u0002\u03d3\u03d8\u0005\u009cO\u0002\u03d4\u03d5\u0007_\u0002\u0002\u03d5\u03d7\u0005\u009cO\u0002\u03d6\u03d4\u0003\u0002\u0002\u0002\u03d7\u03da\u0003\u0002\u0002\u0002\u03d8\u03d6\u0003\u0002\u0002\u0002\u03d8\u03d9\u0003\u0002\u0002\u0002\u03d9\u03dc\u0003\u0002\u0002\u0002\u03da\u03d8\u0003\u0002\u0002\u0002\u03db\u03dd\u0007_\u0002\u0002\u03dc\u03db\u0003\u0002\u0002\u0002\u03dc\u03dd\u0003\u0002\u0002\u0002\u03dd\u03df\u0003\u0002\u0002\u0002\u03de\u03d3\u0003\u0002\u0002\u0002\u03de\u03df\u0003\u0002\u0002\u0002\u03df\u03e0\u0003\u0002\u0002\u0002\u03e0\u03e1\u0007]\u0002\u0002\u03e1\u009f\u0003\u0002\u0002\u0002\u03e2\u03e4\u0007Z\u0002\u0002\u03e3\u03e5\u0005\u014a\u00a6\u0002\u03e4\u03e3\u0003\u0002\u0002\u0002\u03e4\u03e5\u0003\u0002\u0002\u0002\u03e5\u03e6\u0003\u0002\u0002\u0002\u03e6\u03e7\u0005\u008aF\u0002\u03e7\u03e8\u0007[\u0002\u0002\u03e8\u00a1\u0003\u0002\u0002\u0002\u03e9\u03ec\u0005\u00a4S\u0002\u03ea\u03ec\u0005\u00c0a\u0002\u03eb\u03e9\u0003\u0002\u0002\u0002\u03eb\u03ea\u0003\u0002\u0002\u0002\u03ec\u00a3\u0003\u0002\u0002\u0002\u03ed\u03ee\u0006S\u0004\u0002\u03ee\u03ef\u0005\u00a6T\u0002\u03ef\u00a5\u0003\u0002\u0002\u0002\u03f0\u03f1\u0005\u0012\n\u0002\u03f1\u03fc\u0005\u0148\u00a5\u0002\u03f2\u03f4\u0005N(\u0002\u03f3\u03f2\u0003\u0002\u0002\u0002\u03f3\u03f4\u0003\u0002\u0002\u0002\u03f4\u03f5\u0003\u0002\u0002\u0002\u03f5\u03fd\u0005> \u0002\u03f6\u03f7\u0005\u00a8U\u0002\u03f7\u03f8\u0005\u0148\u00a5\u0002\u03f8\u03f9\u0007a\u0002\u0002\u03f9\u03fa\u0005\u0148\u00a5\u0002\u03fa\u03fb\u0005D#\u0002\u03fb\u03fd\u0003\u0002\u0002\u0002\u03fc\u03f3\u0003\u0002\u0002\u0002\u03fc\u03f6\u0003\u0002\u0002\u0002\u03fd\u0402\u0003\u0002\u0002\u0002\u03fe\u03ff\u0005N(\u0002\u03ff\u0400\u0005> \u0002\u0400\u0402\u0003\u0002\u0002\u0002\u0401\u03f0\u0003\u0002\u0002\u0002\u0401\u03fe\u0003\u0002\u0002\u0002\u0402\u00a7\u0003\u0002\u0002\u0002\u0403\u0404\u0007X\u0002\u0002\u0404\u0409\u0005\u00aaV\u0002\u0405\u0406\u0007_\u0002\u0002\u0406\u0408\u0005\u00aaV\u0002\u0407\u0405\u0003\u0002\u0002\u0002\u0408\u040b\u0003\u0002\u0002\u0002\u0409\u0407\u0003\u0002\u0002\u0002\u0409\u040a\u0003\u0002\u0002\u0002\u040a\u040c\u0003\u0002\u0002\u0002\u040b\u0409\u0003\u0002\u0002\u0002\u040c\u040d\u0005\u0146\u00a4\u0002\u040d\u00a9\u0003\u0002\u0002\u0002\u040e\u0410\u0005N(\u0002\u040f\u040e\u0003\u0002\u0002\u0002\u040f\u0410\u0003\u0002\u0002\u0002\u0410\u0411\u0003\u0002\u0002\u0002\u0411\u0412\u0005B\"\u0002\u0412\u00ab\u0003\u0002\u0002\u0002\u0413\u0414\u0007X\u0002\u0002\u0414\u0417\u0005B\"\u0002\u0415\u0416\u0007_\u0002\u0002\u0416\u0418\u0005B\"\u0002\u0417\u0415\u0003\u0002\u0002\u0002\u0418\u0419\u0003\u0002\u0002\u0002\u0419\u0417\u0003\u0002\u0002\u0002\u0419\u041a\u0003\u0002\u0002\u0002\u041a\u041b\u0003\u0002\u0002\u0002\u041b\u041c\u0005\u0146\u00a4\u0002\u041c\u00ad\u0003\u0002\u0002\u0002\u041d\u0420\u0005\u00b0Y\u0002\u041e\u0420\u0005\u00b2Z\u0002\u041f\u041d\u0003\u0002\u0002\u0002\u041f\u041e\u0003\u0002\u0002\u0002\u0420\u00af\u0003\u0002\u0002\u0002\u0421\u0422\u0007!\u0002\u0002\u0422\u0423\u0005\u00e0q\u0002\u0423\u0424\u0005\u0148\u00a5\u0002\u0424\u042d\u0005\u00c0a\u0002\u0425\u0428\u0005\u0148\u00a5\u0002\u0426\u0428\u0005\u014a\u00a6\u0002\u0427\u0425\u0003\u0002\u0002\u0002\u0427\u0426\u0003\u0002\u0002\u0002\u0428\u0429\u0003\u0002\u0002\u0002\u0429\u042a\u0007\u001b\u0002\u0002\u042a\u042b\u0005\u0148\u00a5\u0002\u042b\u042c\u0005\u00c0a\u0002\u042c\u042e\u0003\u0002\u0002\u0002\u042d\u0427\u0003\u0002\u0002\u0002\u042d\u042e\u0003\u0002\u0002\u0002\u042e\u00b1\u0003\u0002\u0002\u0002\u042f\u0430\u00075\u0002\u0002\u0430\u0431\u0005\u00e0q\u0002\u0431\u0432\u0005\u0148\u00a5\u0002\u0432\u0433\u0007Z\u0002\u0002\u0433\u043b\u0005\u0148\u00a5\u0002\u0434\u0436\u0005\u00ceh\u0002\u0435\u0434\u0003\u0002\u0002\u0002\u0436\u0437\u0003\u0002\u0002\u0002\u0437\u0435\u0003\u0002\u0002\u0002\u0437\u0438\u0003\u0002\u0002\u0002\u0438\u0439\u0003\u0002\u0002\u0002\u0439\u043a\u0005\u0148\u00a5\u0002\u043a\u043c\u0003\u0002\u0002\u0002\u043b\u0435\u0003\u0002\u0002\u0002\u043b\u043c\u0003\u0002\u0002\u0002\u043c\u043d\u0003\u0002\u0002\u0002\u043d\u043e\u0007[\u0002\u0002\u043e\u00b3\u0003\u0002\u0002\u0002\u043f\u0440\u0007 \u0002\u0002\u0440\u0441\u0007X\u0002\u0002\u0441\u0442\u0005\u00d2j\u0002\u0442\u0443\u0005\u0146\u00a4\u0002\u0443\u0444\u0005\u0148\u00a5\u0002\u0444\u0445\u0005\u00c0a\u0002\u0445\u0453\u0003\u0002\u0002\u0002\u0446\u0447\u0007>\u0002\u0002\u0447\u0448\u0005\u00e0q\u0002\u0448\u0449\u0005\u0148\u00a5\u0002\u0449\u044a\u0005\u00c0a\u0002\u044a\u0453\u0003\u0002\u0002\u0002\u044b\u044c\u0007\u001a\u0002\u0002\u044c\u044d\u0005\u0148\u00a5\u0002\u044d\u044e\u0005\u00c0a\u0002\u044e\u044f\u0005\u0148\u00a5\u0002\u044f\u0450\u0007>\u0002\u0002\u0450\u0451\u0005\u00e0q\u0002\u0451\u0453\u0003\u0002\u0002\u0002\u0452\u043f\u0003\u0002\u0002\u0002\u0452\u0446\u0003\u0002\u0002\u0002\u0452\u044b\u0003\u0002\u0002\u0002\u0453\u00b5\u0003\u0002\u0002\u0002\u0454\u0456\u0007\u0018\u0002\u0002\u0455\u0457\u0005\u0140\u00a1\u0002\u0456\u0455\u0003\u0002\u0002\u0002\u0456\u0457\u0003\u0002\u0002\u0002\u0457\u00b7\u0003\u0002\u0002\u0002\u0458\u045a\u0007\u0012\u0002\u0002\u0459\u045b\u0005\u0140\u00a1\u0002\u045a\u0459\u0003\u0002\u0002\u0002\u045a\u045b\u0003\u0002\u0002\u0002\u045b\u00b9\u0003\u0002\u0002\u0002\u045c\u045d\u0007\u0013\u0002\u0002\u045d\u045e\u0005\u00f2z\u0002\u045e\u00bb\u0003\u0002\u0002\u0002\u045f\u0461\u0007;\u0002\u0002\u0460\u0462\u0005\u00c8e\u0002\u0461\u0460\u0003\u0002\u0002\u0002\u0461\u0462\u0003\u0002\u0002\u0002\u0462\u0463\u0003\u0002\u0002\u0002\u0463\u0464\u0005\u0148\u00a5\u0002\u0464\u046a\u0005\u00a0Q\u0002\u0465\u0466\u0005\u0148\u00a5\u0002\u0466\u0467\u0005\u00c2b\u0002\u0467\u0469\u0003\u0002\u0002\u0002\u0468\u0465\u0003\u0002\u0002\u0002\u0469\u046c\u0003\u0002\u0002\u0002\u046a\u0468\u0003\u0002\u0002\u0002\u046a\u046b\u0003\u0002\u0002\u0002\u046b\u0470\u0003\u0002\u0002\u0002\u046c\u046a\u0003\u0002\u0002\u0002\u046d\u046e\u0005\u0148\u00a5\u0002\u046e\u046f\u0005\u00c6d\u0002\u046f\u0471\u0003\u0002\u0002\u0002\u0470\u046d\u0003\u0002\u0002\u0002\u0470\u0471\u0003\u0002\u0002\u0002\u0471\u00bd\u0003\u0002\u0002\u0002\u0472\u0473\u0007\u0011\u0002\u0002\u0473\u0479\u0005\u00f2z\u0002\u0474\u0475\u0005\u0148\u00a5\u0002\u0475\u0476\t\u0006\u0002\u0002\u0476\u0477\u0005\u0148\u00a5\u0002\u0477\u0478\u0005\u00f2z\u0002\u0478\u047a\u0003\u0002\u0002\u0002\u0479\u0474\u0003\u0002\u0002\u0002\u0479\u047a\u0003\u0002\u0002\u0002\u047a\u00bf\u0003\u0002\u0002\u0002\u047b\u0498\u0005\u00a0Q\u0002\u047c\u0498\u0005\u00aeX\u0002\u047d\u0498\u0005\u00b4[\u0002\u047e\u0498\u0005\u00bc_\u0002\u047f\u0480\u00076\u0002\u0002\u0480\u0481\u0005\u00e0q\u0002\u0481\u0482\u0005\u0148\u00a5\u0002\u0482\u0483\u0005\u00a0Q\u0002\u0483\u0498\u0003\u0002\u0002\u0002\u0484\u0486\u00070\u0002\u0002\u0485\u0487\u0005\u00f2z\u0002\u0486\u0485\u0003\u0002\u0002\u0002\u0486\u0487\u0003\u0002\u0002\u0002\u0487\u0498\u0003\u0002\u0002\u0002\u0488\u0489\u00078\u0002\u0002\u0489\u0498\u0005\u00f2z\u0002\u048a\u0498\u0005\u00b8]\u0002\u048b\u0498\u0005\u00b6\\\u0002\u048c\u048d\u0006a\u0005\u0002\u048d\u0498\u0005\u00ba^\u0002\u048e\u048f\u0005\u0140\u00a1\u0002\u048f\u0490\u0007g\u0002\u0002\u0490\u0491\u0005\u0148\u00a5\u0002\u0491\u0492\u0005\u00c0a\u0002\u0492\u0498\u0003\u0002\u0002\u0002\u0493\u0498\u0005\u00be`\u0002\u0494\u0498\u0005\u00a4S\u0002\u0495\u0498\u0005\u00e8u\u0002\u0496\u0498\u0007^\u0002\u0002\u0497\u047b\u0003\u0002\u0002\u0002\u0497\u047c\u0003\u0002\u0002\u0002\u0497\u047d\u0003\u0002\u0002\u0002\u0497\u047e\u0003\u0002\u0002\u0002\u0497\u047f\u0003\u0002\u0002\u0002\u0497\u0484\u0003\u0002\u0002\u0002\u0497\u0488\u0003\u0002\u0002\u0002\u0497\u048a\u0003\u0002\u0002\u0002\u0497\u048b\u0003\u0002\u0002\u0002\u0497\u048c\u0003\u0002\u0002\u0002\u0497\u048e\u0003\u0002\u0002\u0002\u0497\u0493\u0003\u0002\u0002\u0002\u0497\u0494\u0003\u0002\u0002\u0002\u0497\u0495\u0003\u0002\u0002\u0002\u0497\u0496\u0003\u0002\u0002\u0002\u0498\u00c1\u0003\u0002\u0002\u0002\u0499\u049a\u0007\u0015\u0002\u0002\u049a\u049b\u0007X\u0002\u0002\u049b\u049d\u0005\u001c\u000f\u0002\u049c\u049e\u0005\u00c4c\u0002\u049d\u049c\u0003\u0002\u0002\u0002\u049d\u049e\u0003\u0002\u0002\u0002\u049e\u049f\u0003\u0002\u0002\u0002\u049f\u04a0\u0005\u0140\u00a1\u0002\u04a0\u04a1\u0005\u0146\u00a4\u0002\u04a1\u04a2\u0005\u0148\u00a5\u0002\u04a2\u04a3\u0005\u00a0Q\u0002\u04a3\u00c3\u0003\u0002\u0002\u0002\u04a4\u04a9\u0005p9\u0002\u04a5\u04a6\u0007u\u0002\u0002\u04a6\u04a8\u0005p9\u0002\u04a7\u04a5\u0003\u0002\u0002\u0002\u04a8\u04ab\u0003\u0002\u0002\u0002\u04a9\u04a7\u0003\u0002\u0002\u0002\u04a9\u04aa\u0003\u0002\u0002\u0002\u04aa\u00c5\u0003\u0002\u0002\u0002\u04ab\u04a9\u0003\u0002\u0002\u0002\u04ac\u04ad\u0007\u001f\u0002\u0002\u04ad\u04ae\u0005\u0148\u00a5\u0002\u04ae\u04af\u0005\u00a0Q\u0002\u04af\u00c7\u0003\u0002\u0002\u0002\u04b0\u04b1\u0007X\u0002\u0002\u04b1\u04b2\u0005\u0148\u00a5\u0002\u04b2\u04b4\u0005\u00caf\u0002\u04b3\u04b5\u0005\u014a\u00a6\u0002\u04b4\u04b3\u0003\u0002\u0002\u0002\u04b4\u04b5\u0003\u0002\u0002\u0002\u04b5\u04b6\u0003\u0002\u0002\u0002\u04b6\u04b7\u0005\u0146\u00a4\u0002\u04b7\u00c9\u0003\u0002\u0002\u0002\u04b8\u04be\u0005\u00ccg\u0002\u04b9\u04ba\u0005\u014a\u00a6\u0002\u04ba\u04bb\u0005\u00ccg\u0002\u04bb\u04bd\u0003\u0002\u0002\u0002\u04bc\u04b9\u0003\u0002\u0002\u0002\u04bd\u04c0\u0003\u0002\u0002\u0002\u04be\u04bc\u0003\u0002\u0002\u0002\u04be\u04bf\u0003\u0002\u0002\u0002\u04bf\u00cb\u0003\u0002\u0002\u0002\u04c0\u04be\u0003\u0002\u0002\u0002\u04c1\u04c4\u0005\u00a4S\u0002\u04c2\u04c4\u0005\u00f2z\u0002\u04c3\u04c1\u0003\u0002\u0002\u0002\u04c3\u04c2\u0003\u0002\u0002\u0002\u04c4\u00cd\u0003\u0002\u0002\u0002\u04c5\u04cb\u0005\u00d0i\u0002\u04c6\u04c7\u0005\u0148\u00a5\u0002\u04c7\u04c8\u0005\u00d0i\u0002\u04c8\u04ca\u0003\u0002\u0002\u0002\u04c9\u04c6\u0003\u0002\u0002\u0002\u04ca\u04cd\u0003\u0002\u0002\u0002\u04cb\u04c9\u0003\u0002\u0002\u0002\u04cb\u04cc\u0003\u0002\u0002\u0002\u04cc\u04ce\u0003\u0002\u0002\u0002\u04cd\u04cb\u0003\u0002\u0002\u0002\u04ce\u04cf\u0005\u0148\u00a5\u0002\u04cf\u04d0\u0005\u008cG\u0002\u04d0\u00cf\u0003\u0002\u0002\u0002\u04d1\u04d2\u0007\u0014\u0002\u0002\u04d2\u04d3\u0005\u00f2z\u0002\u04d3\u04d4\u0007g\u0002\u0002\u04d4\u04d8\u0003\u0002\u0002\u0002\u04d5\u04d6\u0007\u0019\u0002\u0002\u04d6\u04d8\u0007g\u0002\u0002\u04d7\u04d1\u0003\u0002\u0002\u0002\u04d7\u04d5\u0003\u0002\u0002\u0002\u04d8\u00d1\u0003\u0002\u0002\u0002\u04d9\u04dc\u0005\u00d4k\u0002\u04da\u04dc\u0005\u00d6l\u0002\u04db\u04d9\u0003\u0002\u0002\u0002\u04db\u04da\u0003\u0002\u0002\u0002\u04dc\u00d3\u0003\u0002\u0002\u0002\u04dd\u04df\u0005\u001c\u000f\u0002\u04de\u04e0\u0005N(\u0002\u04df\u04de\u0003\u0002\u0002\u0002\u04df\u04e0\u0003\u0002\u0002\u0002\u04e0\u04e1\u0003\u0002\u0002\u0002\u04e1\u04e2\u0005B\"\u0002\u04e2\u04e3\t\u0007\u0002\u0002\u04e3\u04e4\u0005\u00f2z\u0002\u04e4\u00d5\u0003\u0002\u0002\u0002\u04e5\u04e7\u0005\u00d8m\u0002\u04e6\u04e5\u0003\u0002\u0002\u0002\u04e6\u04e7\u0003\u0002\u0002\u0002\u04e7\u04e8\u0003\u0002\u0002\u0002\u04e8\u04ea\u0007^\u0002\u0002\u04e9\u04eb\u0005\u00f2z\u0002\u04ea\u04e9\u0003\u0002\u0002\u0002\u04ea\u04eb\u0003\u0002\u0002\u0002\u04eb\u04ec\u0003\u0002\u0002\u0002\u04ec\u04ee\u0007^\u0002\u0002\u04ed\u04ef\u0005\u00dan\u0002\u04ee\u04ed\u0003\u0002\u0002\u0002\u04ee\u04ef\u0003\u0002\u0002\u0002\u04ef\u00d7\u0003\u0002\u0002\u0002\u04f0\u04f3\u0005\u00a4S\u0002\u04f1\u04f3\u0005\u00e2r\u0002\u04f2\u04f0\u0003\u0002\u0002\u0002\u04f2\u04f1\u0003\u0002\u0002\u0002\u04f3\u00d9\u0003\u0002\u0002\u0002\u04f4\u04f5\u0005\u00e2r\u0002\u04f5\u00db\u0003\u0002\u0002\u0002\u04f6\u04f7\u0007X\u0002\u0002\u04f7\u04f8\u0005N(\u0002\u04f8\u04f9\u0005\u0146\u00a4\u0002\u04f9\u00dd\u0003\u0002\u0002\u0002\u04fa\u04fb\u0005\u00e0q\u0002\u04fb\u00df\u0003\u0002\u0002\u0002\u04fc\u04fd\u0007X\u0002\u0002\u04fd\u04fe\u0005\u00e6t\u0002\u04fe\u04ff\u0005\u0146\u00a4\u0002\u04ff\u00e1\u0003\u0002\u0002\u0002\u0500\u0507\u0005\u00e4s\u0002\u0501\u0502\u0007_\u0002\u0002\u0502\u0503\u0005\u0148\u00a5\u0002\u0503\u0504\u0005\u00e4s\u0002\u0504\u0506\u0003\u0002\u0002\u0002\u0505\u0501\u0003\u0002\u0002\u0002\u0506\u0509\u0003\u0002\u0002\u0002\u0507\u0505\u0003\u0002\u0002\u0002\u0507\u0508\u0003\u0002\u0002\u0002\u0508\u00e3\u0003\u0002\u0002\u0002\u0509\u0507\u0003\u0002\u0002\u0002\u050a\u050c\u0007r\u0002\u0002\u050b\u050a\u0003\u0002\u0002\u0002\u050b\u050c\u0003\u0002\u0002\u0002\u050c\u050d\u0003\u0002\u0002\u0002\u050d\u050e\u0005\u00f2z\u0002\u050e\u00e5\u0003\u0002\u0002\u0002\u050f\u0512\u0005\u00e8u\u0002\u0510\u0512\u0005~@\u0002\u0511\u050f\u0003\u0002\u0002\u0002\u0511\u0510\u0003\u0002\u0002\u0002\u0512\u00e7\u0003\u0002\u0002\u0002\u0513\u0514\u0005\u00f6|\u0002\u0514\u00e9\u0003\u0002\u0002\u0002\u0515\u0517\u0005\u00fa~\u0002\u0516\u0518\t\b\u0002\u0002\u0517\u0516\u0003\u0002\u0002\u0002\u0517\u0518\u0003\u0002\u0002\u0002\u0518\u00eb\u0003\u0002\u0002\u0002\u0519\u051a\u00075\u0002\u0002\u051a\u051b\u0005\u00e0q\u0002\u051b\u051c\u0005\u0148\u00a5\u0002\u051c\u051d\u0007Z\u0002\u0002\u051d\u0521\u0005\u0148\u00a5\u0002\u051e\u0520\u0005\u00eex\u0002\u051f\u051e\u0003\u0002\u0002\u0002\u0520\u0523\u0003\u0002\u0002\u0002\u0521\u051f\u0003\u0002\u0002\u0002\u0521\u0522\u0003\u0002\u0002\u0002\u0522\u0524\u0003\u0002\u0002\u0002\u0523\u0521\u0003\u0002\u0002\u0002\u0524\u0525\u0005\u0148\u00a5\u0002\u0525\u0526\u0007[\u0002\u0002\u0526\u00ed\u0003\u0002\u0002\u0002\u0527\u0528\u0005\u00f0y\u0002\u0528\u0529\u0005\u0148\u00a5\u0002\u0529\u052b\u0003\u0002\u0002\u0002\u052a\u0527\u0003\u0002\u0002\u0002\u052b\u052c\u0003\u0002\u0002\u0002\u052c\u052a\u0003\u0002\u0002\u0002\u052c\u052d\u0003\u0002\u0002\u0002\u052d\u052e\u0003\u0002\u0002\u0002\u052e\u052f\u0005\u008cG\u0002\u052f\u00ef\u0003\u0002\u0002\u0002\u0530\u0531\u0007\u0014\u0002\u0002\u0531\u0534\u0005\u00e2r\u0002\u0532\u0534\u0007\u0019\u0002\u0002\u0533\u0530\u0003\u0002\u0002\u0002\u0533\u0532\u0003\u0002\u0002\u0002\u0534\u0535\u0003\u0002\u0002\u0002\u0535\u0536\t\t\u0002\u0002\u0536\u00f1\u0003\u0002\u0002\u0002\u0537\u0538\bz\u0001\u0002\u0538\u0539\u0005\u00dco\u0002\u0539\u053a\u0005\u00f4{\u0002\u053a\u054a\u0003\u0002\u0002\u0002\u053b\u054a\u0005\u00eav\u0002\u053c\u054a\u0005\u00ecw\u0002\u053d\u053e\t\n\u0002\u0002\u053e\u053f\u0005\u0148\u00a5\u0002\u053f\u0540\u0005\u00f2z\u0014\u0540\u054a\u0003\u0002\u0002\u0002\u0541\u0542\t\u000b\u0002\u0002\u0542\u054a\u0005\u00f2z\u0012\u0543\u0544\u0005\u00acW\u0002\u0544\u0545\u0005\u0148\u00a5\u0002\u0545\u0546\u0007a\u0002\u0002\u0546\u0547\u0005\u0148\u00a5\u0002\u0547\u0548\u0005\u00e8u\u0002\u0548\u054a\u0003\u0002\u0002\u0002\u0549\u0537\u0003\u0002\u0002\u0002\u0549\u053b\u0003\u0002\u0002\u0002\u0549\u053c\u0003\u0002\u0002\u0002\u0549\u053d\u0003\u0002\u0002\u0002\u0549\u0541\u0003\u0002\u0002\u0002\u0549\u0543\u0003\u0002\u0002\u0002\u054a\u05b9\u0003\u0002\u0002\u0002\u054b\u054c\f\u0013\u0002\u0002\u054c\u054d\u0007P\u0002\u0002\u054d\u054e\u0005\u0148\u00a5\u0002\u054e\u054f\u0005\u00f2z\u0014\u054f\u05b8\u0003\u0002\u0002\u0002\u0550\u0551\f\u0011\u0002\u0002\u0551\u0552\u0005\u0148\u00a5\u0002\u0552\u0553\t\f\u0002\u0002\u0553\u0554\u0005\u0148\u00a5\u0002\u0554\u0555\u0005\u00f2z\u0012\u0555\u05b8\u0003\u0002\u0002\u0002\u0556\u0557\f\u0010\u0002\u0002\u0557\u0558\t\r\u0002\u0002\u0558\u0559\u0005\u0148\u00a5\u0002\u0559\u055a\u0005\u00f2z\u0011\u055a\u05b8\u0003\u0002\u0002\u0002\u055b\u055c\f\u000f\u0002\u0002\u055c\u0567\u0005\u0148\u00a5\u0002\u055d\u055e\u0007c\u0002\u0002\u055e\u0565\u0007c\u0002\u0002\u055f\u0560\u0007b\u0002\u0002\u0560\u0561\u0007b\u0002\u0002\u0561\u0565\u0007b\u0002\u0002\u0562\u0563\u0007b\u0002\u0002\u0563\u0565\u0007b\u0002\u0002\u0564\u055d\u0003\u0002\u0002\u0002\u0564\u055f\u0003\u0002\u0002\u0002\u0564\u0562\u0003\u0002\u0002\u0002\u0565\u0568\u0003\u0002\u0002\u0002\u0566\u0568\t\u000e\u0002\u0002\u0567\u0564\u0003\u0002\u0002\u0002\u0567\u0566\u0003\u0002\u0002\u0002\u0568\u0569\u0003\u0002\u0002\u0002\u0569\u056a\u0005\u0148\u00a5\u0002\u056a\u056b\u0005\u00f2z\u0010\u056b\u05b8\u0003\u0002\u0002\u0002\u056c\u056d\f\r\u0002\u0002\u056d\u056e\u0005\u0148\u00a5\u0002\u056e\u056f\t\u000f\u0002\u0002\u056f\u0570\u0005\u0148\u00a5\u0002\u0570\u0571\u0005\u00f2z\u000e\u0571\u05b8\u0003\u0002\u0002\u0002\u0572\u0573\f\f\u0002\u0002\u0573\u0574\u0005\u0148\u00a5\u0002\u0574\u0575\t\u0010\u0002\u0002\u0575\u0576\u0005\u0148\u00a5\u0002\u0576\u0577\u0005\u00f2z\r\u0577\u05b8\u0003\u0002\u0002\u0002\u0578\u0579\f\u000b\u0002\u0002\u0579\u057a\u0005\u0148\u00a5\u0002\u057a\u057b\t\u0011\u0002\u0002\u057b\u057c\u0005\u0148\u00a5\u0002\u057c\u057d\u0005\u00f2z\f\u057d\u05b8\u0003\u0002\u0002\u0002\u057e\u057f\f\n\u0002\u0002\u057f\u0580\u0005\u0148\u00a5\u0002\u0580\u0581\u0007t\u0002\u0002\u0581\u0582\u0005\u0148\u00a5\u0002\u0582\u0583\u0005\u00f2z\u000b\u0583\u05b8\u0003\u0002\u0002\u0002\u0584\u0585\f\t\u0002\u0002\u0585\u0586\u0005\u0148\u00a5\u0002\u0586\u0587\u0007v\u0002\u0002\u0587\u0588\u0005\u0148\u00a5\u0002\u0588\u0589\u0005\u00f2z\n\u0589\u05b8\u0003\u0002\u0002\u0002\u058a\u058b\f\b\u0002\u0002\u058b\u058c\u0005\u0148\u00a5\u0002\u058c\u058d\u0007u\u0002\u0002\u058d\u058e\u0005\u0148\u00a5\u0002\u058e\u058f\u0005\u00f2z\t\u058f\u05b8\u0003\u0002\u0002\u0002\u0590\u0591\f\u0007\u0002\u0002\u0591\u0592\u0005\u0148\u00a5\u0002\u0592\u0593\u0007l\u0002\u0002\u0593\u0594\u0005\u0148\u00a5\u0002\u0594\u0595\u0005\u00f2z\b\u0595\u05b8\u0003\u0002\u0002\u0002\u0596\u0597\f\u0006\u0002\u0002\u0597\u0598\u0005\u0148\u00a5\u0002\u0598\u0599\u0007m\u0002\u0002\u0599\u059a\u0005\u0148\u00a5\u0002\u059a\u059b\u0005\u00f2z\u0007\u059b\u05b8\u0003\u0002\u0002\u0002\u059c\u059d\f\u0005\u0002\u0002\u059d\u05a7\u0005\u0148\u00a5\u0002\u059e\u059f\u0007f\u0002\u0002\u059f\u05a0\u0005\u0148\u00a5\u0002\u05a0\u05a1\u0005\u00f2z\u0002\u05a1\u05a2\u0005\u0148\u00a5\u0002\u05a2\u05a3\u0007g\u0002\u0002\u05a3\u05a4\u0005\u0148\u00a5\u0002\u05a4\u05a8\u0003\u0002\u0002\u0002\u05a5\u05a6\u0007K\u0002\u0002\u05a6\u05a8\u0005\u0148\u00a5\u0002\u05a7\u059e\u0003\u0002\u0002\u0002\u05a7\u05a5\u0003\u0002\u0002\u0002\u05a8\u05a9\u0003\u0002\u0002\u0002\u05a9\u05aa\u0005\u00f2z\u0005\u05aa\u05b8\u0003\u0002\u0002\u0002\u05ab\u05ac\f\u000e\u0002\u0002\u05ac\u05ad\u0005\u0148\u00a5\u0002\u05ad\u05ae\t\u0012\u0002\u0002\u05ae\u05af\u0005\u0148\u00a5\u0002\u05af\u05b0\u0005N(\u0002\u05b0\u05b8\u0003\u0002\u0002\u0002\u05b1\u05b2\f\u0003\u0002\u0002\u05b2\u05b3\u0005\u0148\u00a5\u0002\u05b3\u05b4\t\u0013\u0002\u0002\u05b4\u05b5\u0005\u0148\u00a5\u0002\u05b5\u05b6\u0005\u00e6t\u0002\u05b6\u05b8\u0003\u0002\u0002\u0002\u05b7\u054b\u0003\u0002\u0002\u0002\u05b7\u0550\u0003\u0002\u0002\u0002\u05b7\u0556\u0003\u0002\u0002\u0002\u05b7\u055b\u0003\u0002\u0002\u0002\u05b7\u056c\u0003\u0002\u0002\u0002\u05b7\u0572\u0003\u0002\u0002\u0002\u05b7\u0578\u0003\u0002\u0002\u0002\u05b7\u057e\u0003\u0002\u0002\u0002\u05b7\u0584\u0003\u0002\u0002\u0002\u05b7\u058a\u0003\u0002\u0002\u0002\u05b7\u0590\u0003\u0002\u0002\u0002\u05b7\u0596\u0003\u0002\u0002\u0002\u05b7\u059c\u0003\u0002\u0002\u0002\u05b7\u05ab\u0003\u0002\u0002\u0002\u05b7\u05b1\u0003\u0002\u0002\u0002\u05b8\u05bb\u0003\u0002\u0002\u0002\u05b9\u05b7\u0003\u0002\u0002\u0002\u05b9\u05ba\u0003\u0002\u0002\u0002\u05ba\u00f3\u0003\u0002\u0002\u0002\u05bb\u05b9\u0003\u0002\u0002\u0002\u05bc\u05bd\u0005\u00dco\u0002\u05bd\u05be\u0005\u00f4{\u0002\u05be\u05c7\u0003\u0002\u0002\u0002\u05bf\u05c7\u0005\u00eav\u0002\u05c0\u05c1\t\n\u0002\u0002\u05c1\u05c2\u0005\u0148\u00a5\u0002\u05c2\u05c3\u0005\u00f4{\u0002\u05c3\u05c7\u0003\u0002\u0002\u0002\u05c4\u05c5\t\u000b\u0002\u0002\u05c5\u05c7\u0005\u00f4{\u0002\u05c6\u05bc\u0003\u0002\u0002\u0002\u05c6\u05bf\u0003\u0002\u0002\u0002\u05c6\u05c0\u0003\u0002\u0002\u0002\u05c6\u05c4\u0003\u0002\u0002\u0002\u05c7\u00f5\u0003\u0002\u0002\u0002\u05c8\u05cc\u0005\u00f2z\u0002\u05c9\u05ca\u0006|\u0015\u0003\u05ca\u05cd\u0005\u0132\u009a\u0002\u05cb\u05cd\u0003\u0002\u0002\u0002\u05cc\u05c9\u0003\u0002\u0002\u0002\u05cc\u05cb\u0003\u0002\u0002\u0002\u05cd\u05d1\u0003\u0002\u0002\u0002\u05ce\u05d0\u0005\u00f8}\u0002\u05cf\u05ce\u0003\u0002\u0002\u0002\u05d0\u05d3\u0003\u0002\u0002\u0002\u05d1\u05cf\u0003\u0002\u0002\u0002\u05d1\u05d2\u0003\u0002\u0002\u0002\u05d2\u00f7\u0003\u0002\u0002\u0002\u05d3\u05d1\u0003\u0002\u0002\u0002\u05d4\u05db\u0005\u010c\u0087\u0002\u05d5\u05d7\u0005\u00fc\u007f\u0002\u05d6\u05d5\u0003\u0002\u0002\u0002\u05d7\u05d8\u0003\u0002\u0002\u0002\u05d8\u05d6\u0003\u0002\u0002\u0002\u05d8\u05d9\u0003\u0002\u0002\u0002\u05d9\u05dc\u0003\u0002\u0002\u0002\u05da\u05dc\u0005\u0132\u009a\u0002\u05db\u05d6\u0003\u0002\u0002\u0002\u05db\u05da\u0003\u0002\u0002\u0002\u05db\u05dc\u0003\u0002\u0002\u0002\u05dc\u00f9\u0003\u0002\u0002\u0002\u05dd\u05e1\u0005\u0106\u0084\u0002\u05de\u05df\u0006~\u0016\u0002\u05df\u05e1\u00072\u0002\u0002\u05e0\u05dd\u0003\u0002\u0002\u0002\u05e0\u05de\u0003\u0002\u0002\u0002\u05e1\u05e7\u0003\u0002\u0002\u0002\u05e2\u05e3\u0005\u00fc\u007f\u0002\u05e3\u05e4\b~\u0001\u0002\u05e4\u05e6\u0003\u0002\u0002\u0002\u05e5\u05e2\u0003\u0002\u0002\u0002\u05e6\u05e9\u0003\u0002\u0002\u0002\u05e7\u05e5\u0003\u0002\u0002\u0002\u05e7\u05e8\u0003\u0002\u0002\u0002\u05e8\u00fb\u0003\u0002\u0002\u0002\u05e9\u05e7\u0003\u0002\u0002\u0002\u05ea\u0603\u0005\u0148\u00a5\u0002\u05eb\u05ec\u0007`\u0002\u0002\u05ec\u05ed\u0005\u0148\u00a5\u0002\u05ed\u05ee\u0007(\u0002\u0002\u05ee\u05ef\u0005\u0122\u0092\u0002\u05ef\u05f0\b\u007f\u0001\u0002\u05f0\u0604\u0003\u0002\u0002\u0002\u05f1\u05f2\t\u0014\u0002\u0002\u05f2\u05f5\u0005\u0148\u00a5\u0002\u05f3\u05f6\u0007\u0086\u0002\u0002\u05f4\u05f6\u0005\u012c\u0097\u0002\u05f5\u05f3\u0003\u0002\u0002\u0002\u05f5\u05f4\u0003\u0002\u0002\u0002\u05f5\u05f6\u0003\u0002\u0002\u0002\u05f6\u05fc\u0003\u0002\u0002\u0002\u05f7\u05f8\u0007L\u0002\u0002\u05f8\u05fc\u0005\u0148\u00a5\u0002\u05f9\u05fa\u0007M\u0002\u0002\u05fa\u05fc\u0005\u0148\u00a5\u0002\u05fb\u05f1\u0003\u0002\u0002\u0002\u05fb\u05f7\u0003\u0002\u0002\u0002\u05fb\u05f9\u0003\u0002\u0002\u0002\u05fc\u05fd\u0003\u0002\u0002\u0002\u05fd\u05fe\u0005\u00fe\u0080\u0002\u05fe\u05ff\b\u007f\u0001\u0002\u05ff\u0604\u0003\u0002\u0002\u0002\u0600\u0601\u0005\u0088E\u0002\u0601\u0602\b\u007f\u0001\u0002\u0602\u0604\u0003\u0002\u0002\u0002\u0603\u05eb\u0003\u0002\u0002\u0002\u0603\u05fb\u0003\u0002\u0002\u0002\u0603\u0600\u0003\u0002\u0002\u0002\u0604\u060f\u0003\u0002\u0002\u0002\u0605\u0606\u0005\u0130\u0099\u0002\u0606\u0607\b\u007f\u0001\u0002\u0607\u060f\u0003\u0002\u0002\u0002\u0608\u0609\u0005\u0102\u0082\u0002\u0609\u060a\b\u007f\u0001\u0002\u060a\u060f\u0003\u0002\u0002\u0002\u060b\u060c\u0005\u0104\u0083\u0002\u060c\u060d\b\u007f\u0001\u0002\u060d\u060f\u0003\u0002\u0002\u0002\u060e\u05ea\u0003\u0002\u0002\u0002\u060e\u0605\u0003\u0002\u0002\u0002\u060e\u0608\u0003\u0002\u0002\u0002\u060e\u060b\u0003\u0002\u0002\u0002\u060f\u00fd\u0003\u0002\u0002\u0002\u0610\u0615\u0005\u0140\u00a1\u0002\u0611\u0615\u0005\u013c\u009f\u0002\u0612\u0615\u0005\u0100\u0081\u0002\u0613\u0615\u0005\u0144\u00a3\u0002\u0614\u0610\u0003\u0002\u0002\u0002\u0614\u0611\u0003\u0002\u0002\u0002\u0614\u0612\u0003\u0002\u0002\u0002\u0614\u0613\u0003\u0002\u0002\u0002\u0615\u00ff\u0003\u0002\u0002\u0002\u0616\u0619\u0005\u00dep\u0002\u0617\u0619\u0005v<\u0002\u0618\u0616\u0003\u0002\u0002\u0002\u0618\u0617\u0003\u0002\u0002\u0002\u0619\u0101\u0003\u0002\u0002\u0002\u061a\u061c\t\u0015\u0002\u0002\u061b\u061d\u0005\u00e2r\u0002\u061c\u061b\u0003\u0002\u0002\u0002\u061c\u061d\u0003\u0002\u0002\u0002\u061d\u061e\u0003\u0002\u0002\u0002\u061e\u061f\u0007]\u0002\u0002\u061f\u0103\u0003\u0002\u0002\u0002\u0620\u0623\t\u0015\u0002\u0002\u0621\u0624\u0005\u0114\u008b\u0002\u0622\u0624\u0007g\u0002\u0002\u0623\u0621\u0003\u0002\u0002\u0002\u0623\u0622\u0003\u0002\u0002\u0002\u0624\u0625\u0003\u0002\u0002\u0002\u0625\u0626\u0007]\u0002\u0002\u0626\u0105\u0003\u0002\u0002\u0002\u0627\u0629\u0005\u0140\u00a1\u0002\u0628\u062a\u0005X-\u0002\u0629\u0628\u0003\u0002\u0002\u0002\u0629\u062a\u0003\u0002\u0002\u0002\u062a\u0639\u0003\u0002\u0002\u0002\u062b\u0639\u0005t;\u0002\u062c\u0639\u0005v<\u0002\u062d\u062e\u0007(\u0002\u0002\u062e\u062f\u0005\u0148\u00a5\u0002\u062f\u0630\u0005\u0122\u0092\u0002\u0630\u0639\u0003\u0002\u0002\u0002\u0631\u0639\u00077\u0002\u0002\u0632\u0639\u00074\u0002\u0002\u0633\u0639\u0005\u00dep\u0002\u0634\u0639\u0005\u0088E\u0002\u0635\u0639\u0005\u010e\u0088\u0002\u0636\u0639\u0005\u0110\u0089\u0002\u0637\u0639\u0005\u0142\u00a2\u0002\u0638\u0627\u0003\u0002\u0002\u0002\u0638\u062b\u0003\u0002\u0002\u0002\u0638\u062c\u0003\u0002\u0002\u0002\u0638\u062d\u0003\u0002\u0002\u0002\u0638\u0631\u0003\u0002\u0002\u0002\u0638\u0632\u0003\u0002\u0002\u0002\u0638\u0633\u0003\u0002\u0002\u0002\u0638\u0634\u0003\u0002\u0002\u0002\u0638\u0635\u0003\u0002\u0002\u0002\u0638\u0636\u0003\u0002\u0002\u0002\u0638\u0637\u0003\u0002\u0002\u0002\u0639\u0107\u0003\u0002\u0002\u0002\u063a\u0641\u0005\u0140\u00a1\u0002\u063b\u0641\u0005t;\u0002\u063c\u0641\u0005v<\u0002\u063d\u0641\u0005\u00dep\u0002\u063e\u0641\u0005\u010e\u0088\u0002\u063f\u0641\u0005\u0110\u0089\u0002\u0640\u063a\u0003\u0002\u0002\u0002\u0640\u063b\u0003\u0002\u0002\u0002\u0640\u063c\u0003\u0002\u0002\u0002\u0640\u063d\u0003\u0002\u0002\u0002\u0640\u063e\u0003\u0002\u0002\u0002\u0640\u063f\u0003\u0002\u0002\u0002\u0641\u0109\u0003\u0002\u0002\u0002\u0642\u0646\u0005\u0140\u00a1\u0002\u0643\u0646\u0005t;\u0002\u0644\u0646\u0005v<\u0002\u0645\u0642\u0003\u0002\u0002\u0002\u0645\u0643\u0003\u0002\u0002\u0002\u0645\u0644\u0003\u0002\u0002\u0002\u0646\u010b\u0003\u0002\u0002\u0002\u0647\u064b\u0005\u0140\u00a1\u0002\u0648\u064b\u0005t;\u0002\u0649\u064b\u0005v<\u0002\u064a\u0647\u0003\u0002\u0002\u0002\u064a\u0648\u0003\u0002\u0002\u0002\u064a\u0649\u0003\u0002\u0002\u0002\u064b\u010d\u0003\u0002\u0002\u0002\u064c\u064e\u0007\\\u0002\u0002\u064d\u064f\u0005\u00e2r\u0002\u064e\u064d\u0003\u0002\u0002\u0002\u064e\u064f\u0003\u0002\u0002\u0002\u064f\u0651\u0003\u0002\u0002\u0002\u0650\u0652\u0007_\u0002\u0002\u0651\u0650\u0003\u0002\u0002\u0002\u0651\u0652\u0003\u0002\u0002\u0002\u0652\u0653\u0003\u0002\u0002\u0002\u0653\u0654\u0007]\u0002\u0002\u0654\u010f\u0003\u0002\u0002\u0002\u0655\u065b\u0007\\\u0002\u0002\u0656\u0658\u0005\u0112\u008a\u0002\u0657\u0659\u0007_\u0002\u0002\u0658\u0657\u0003\u0002\u0002\u0002\u0658\u0659\u0003\u0002\u0002\u0002\u0659\u065c\u0003\u0002\u0002\u0002\u065a\u065c\u0007g\u0002\u0002\u065b\u0656\u0003\u0002\u0002\u0002\u065b\u065a\u0003\u0002\u0002\u0002\u065c\u065d\u0003\u0002\u0002\u0002\u065d\u065e\u0007]\u0002\u0002\u065e\u0111\u0003\u0002\u0002\u0002\u065f\u0664\u0005\u0116\u008c\u0002\u0660\u0661\u0007_\u0002\u0002\u0661\u0663\u0005\u0116\u008c\u0002\u0662\u0660\u0003\u0002\u0002\u0002\u0663\u0666\u0003\u0002\u0002\u0002\u0664\u0662\u0003\u0002\u0002\u0002\u0664\u0665\u0003\u0002\u0002\u0002\u0665\u0113\u0003\u0002\u0002\u0002\u0666\u0664\u0003\u0002\u0002\u0002\u0667\u066c\u0005\u0118\u008d\u0002\u0668\u0669\u0007_\u0002\u0002\u0669\u066b\u0005\u0118\u008d\u0002\u066a\u0668\u0003\u0002\u0002\u0002\u066b\u066e\u0003\u0002\u0002\u0002\u066c\u066a\u0003\u0002\u0002\u0002\u066c\u066d\u0003\u0002\u0002\u0002\u066d\u0115\u0003\u0002\u0002\u0002\u066e\u066c\u0003\u0002\u0002\u0002\u066f\u0670\u0005\u011c\u008f\u0002\u0670\u0671\u0007g\u0002\u0002\u0671\u0672\u0005\u0148\u00a5\u0002\u0672\u0673\u0005\u00f2z\u0002\u0673\u067a\u0003\u0002\u0002\u0002\u0674\u0675\u0007r\u0002\u0002\u0675\u0676\u0007g\u0002\u0002\u0676\u0677\u0005\u0148\u00a5\u0002\u0677\u0678\u0005\u00f2z\u0002\u0678\u067a\u0003\u0002\u0002\u0002\u0679\u066f\u0003\u0002\u0002\u0002\u0679\u0674\u0003\u0002\u0002\u0002\u067a\u0117\u0003\u0002\u0002\u0002\u067b\u067c\u0005\u011e\u0090\u0002\u067c\u067d\u0007g\u0002\u0002\u067d\u067e\u0005\u0148\u00a5\u0002\u067e\u067f\u0005\u00f2z\u0002\u067f\u0686\u0003\u0002\u0002\u0002\u0680\u0681\u0007r\u0002\u0002\u0681\u0682\u0007g\u0002\u0002\u0682\u0683\u0005\u0148\u00a5\u0002\u0683\u0684\u0005\u00f2z\u0002\u0684\u0686\u0003\u0002\u0002\u0002\u0685\u067b\u0003\u0002\u0002\u0002\u0685\u0680\u0003\u0002\u0002\u0002\u0686\u0119\u0003\u0002\u0002\u0002\u0687\u0688\u0005\u0120\u0091\u0002\u0688\u0689\u0007g\u0002\u0002\u0689\u068a\u0005\u0148\u00a5\u0002\u068a\u068b\u0005\u00f2z\u0002\u068b\u0692\u0003\u0002\u0002\u0002\u068c\u068d\u0007r\u0002\u0002\u068d\u068e\u0007g\u0002\u0002\u068e\u068f\u0005\u0148\u00a5\u0002\u068f\u0690\u0005\u00f2z\u0002\u0690\u0692\u0003\u0002\u0002\u0002\u0691\u0687\u0003\u0002\u0002\u0002\u0691\u068c\u0003\u0002\u0002\u0002\u0692\u011b\u0003\u0002\u0002\u0002\u0693\u0696\u0005\u0144\u00a3\u0002\u0694\u0696\u0005\u0106\u0084\u0002\u0695\u0693\u0003\u0002\u0002\u0002\u0695\u0694\u0003\u0002\u0002\u0002\u0696\u011d\u0003\u0002\u0002\u0002\u0697\u069a\u0005\u0144\u00a3\u0002\u0698\u069a\u0005\u0108\u0085\u0002\u0699\u0697\u0003\u0002\u0002\u0002\u0699\u0698\u0003\u0002\u0002\u0002\u069a\u011f\u0003\u0002\u0002\u0002\u069b\u069e\u0005\u0144\u00a3\u0002\u069c\u069e\u0005\u010a\u0086\u0002\u069d\u069b\u0003\u0002\u0002\u0002\u069d\u069c\u0003\u0002\u0002\u0002\u069e\u0121\u0003\u0002\u0002\u0002\u069f\u06af\u0005\u012a\u0096\u0002\u06a0\u06a1\u0005\u0148\u00a5\u0002\u06a1\u06a3\u0005\u0130\u0099\u0002\u06a2\u06a4\u0005\u0128\u0095\u0002\u06a3\u06a2\u0003\u0002\u0002\u0002\u06a3\u06a4\u0003\u0002\u0002\u0002\u06a4\u06b0\u0003\u0002\u0002\u0002\u06a5\u06a7\u0005\u0124\u0093\u0002\u06a6\u06a5\u0003\u0002\u0002\u0002\u06a7\u06a8\u0003\u0002\u0002\u0002\u06a8\u06a6\u0003\u0002\u0002\u0002\u06a8\u06a9\u0003\u0002\u0002\u0002\u06a9\u06ad\u0003\u0002\u0002\u0002\u06aa\u06ab\u0005\u0148\u00a5\u0002\u06ab\u06ac\u0005\u0126\u0094\u0002\u06ac\u06ae\u0003\u0002\u0002\u0002\u06ad\u06aa\u0003\u0002\u0002\u0002\u06ad\u06ae\u0003\u0002\u0002\u0002\u06ae\u06b0\u0003\u0002\u0002\u0002\u06af\u06a0\u0003\u0002\u0002\u0002\u06af\u06a6\u0003\u0002\u0002\u0002\u06b0\u0123\u0003\u0002\u0002\u0002\u06b1\u06b2\u0005\u008eH\u0002\u06b2\u06b4\u0007\\\u0002\u0002\u06b3\u06b5\u0005\u00f2z\u0002\u06b4\u06b3\u0003\u0002\u0002\u0002\u06b4\u06b5\u0003\u0002\u0002\u0002\u06b5\u06b6\u0003\u0002\u0002\u0002\u06b6\u06b7\u0007]\u0002\u0002\u06b7\u0125\u0003\u0002\u0002\u0002\u06b8\u06b9\u0007Z\u0002\u0002\u06b9\u06bd\u0005\u0148\u00a5\u0002\u06ba\u06bb\u0005F$\u0002\u06bb\u06bc\u0005\u0148\u00a5\u0002\u06bc\u06be\u0003\u0002\u0002\u0002\u06bd\u06ba\u0003\u0002\u0002\u0002\u06bd\u06be\u0003\u0002\u0002\u0002\u06be\u06bf\u0003\u0002\u0002\u0002\u06bf\u06c0\u0007[\u0002\u0002\u06c0\u0127\u0003\u0002\u0002\u0002\u06c1\u06c2\u0005*\u0016\u0002\u06c2\u0129\u0003\u0002\u0002\u0002\u06c3\u06c9\u0005\u008eH\u0002\u06c4\u06ca\u0005V,\u0002\u06c5\u06c7\u0005p9\u0002\u06c6\u06c8\u0005\u012e\u0098\u0002\u06c7\u06c6\u0003\u0002\u0002\u0002\u06c7\u06c8\u0003\u0002\u0002\u0002\u06c8\u06ca\u0003\u0002\u0002\u0002\u06c9\u06c4\u0003\u0002\u0002\u0002\u06c9\u06c5\u0003\u0002\u0002\u0002\u06ca\u012b\u0003\u0002\u0002\u0002\u06cb\u06cc\u0007c\u0002\u0002\u06cc\u06cd\u0005\u0148\u00a5\u0002\u06cd\u06ce\u0005&\u0014\u0002\u06ce\u06cf\u0005\u0148\u00a5\u0002\u06cf\u06d0\u0007b\u0002\u0002\u06d0\u012d\u0003\u0002\u0002\u0002\u06d1\u06d2\u0007c\u0002\u0002\u06d2\u06d5\u0007b\u0002\u0002\u06d3\u06d5\u0005X-\u0002\u06d4\u06d1\u0003\u0002\u0002\u0002\u06d4\u06d3\u0003\u0002\u0002\u0002\u06d5\u012f\u0003\u0002\u0002\u0002\u06d6\u06d8\u0007X\u0002\u0002\u06d7\u06d9\u0005\u0134\u009b\u0002\u06d8\u06d7\u0003\u0002\u0002\u0002\u06d8\u06d9\u0003\u0002\u0002\u0002\u06d9\u06db\u0003\u0002\u0002\u0002\u06da\u06dc\u0007_\u0002\u0002\u06db\u06da\u0003\u0002\u0002\u0002\u06db\u06dc\u0003\u0002\u0002\u0002\u06dc\u06dd\u0003\u0002\u0002\u0002\u06dd\u06de\u0005\u0146\u00a4\u0002\u06de\u0131\u0003\u0002\u0002\u0002\u06df\u06e6\u0005\u0136\u009c\u0002\u06e0\u06e1\u0007_\u0002\u0002\u06e1\u06e2\u0005\u0148\u00a5\u0002\u06e2\u06e3\u0005\u0138\u009d\u0002\u06e3\u06e5\u0003\u0002\u0002\u0002\u06e4\u06e0\u0003\u0002\u0002\u0002\u06e5\u06e8\u0003\u0002\u0002\u0002\u06e6\u06e4\u0003\u0002\u0002\u0002\u06e6\u06e7\u0003\u0002\u0002\u0002\u06e7\u0133\u0003\u0002\u0002\u0002\u06e8\u06e6\u0003\u0002\u0002\u0002\u06e9\u06f0\u0005\u013a\u009e\u0002\u06ea\u06eb\u0007_\u0002\u0002\u06eb\u06ec\u0005\u0148\u00a5\u0002\u06ec\u06ed\u0005\u013a\u009e\u0002\u06ed\u06ef\u0003\u0002\u0002\u0002\u06ee\u06ea\u0003\u0002\u0002\u0002\u06ef\u06f2\u0003\u0002\u0002\u0002\u06f0\u06ee\u0003\u0002\u0002\u0002\u06f0\u06f1\u0003\u0002\u0002\u0002\u06f1\u0135\u0003\u0002\u0002\u0002\u06f2\u06f0\u0003\u0002\u0002\u0002\u06f3\u06f6\u0005\u00e4s\u0002\u06f4\u06f6\u0005\u011a\u008e\u0002\u06f5\u06f3\u0003\u0002\u0002\u0002\u06f5\u06f4\u0003\u0002\u0002\u0002\u06f6\u0137\u0003\u0002\u0002\u0002\u06f7\u06fa\u0005\u00e4s\u0002\u06f8\u06fa\u0005\u0118\u008d\u0002\u06f9\u06f7\u0003\u0002\u0002\u0002\u06f9\u06f8\u0003\u0002\u0002\u0002\u06fa\u0139\u0003\u0002\u0002\u0002\u06fb\u06ff\u0005\u00e4s\u0002\u06fc\u06ff\u0005~@\u0002\u06fd\u06ff\u0005\u0118\u008d\u0002\u06fe\u06fb\u0003\u0002\u0002\u0002\u06fe\u06fc\u0003\u0002\u0002\u0002\u06fe\u06fd\u0003\u0002\u0002\u0002\u06ff\u013b\u0003\u0002\u0002\u0002\u0700\u0701\u0007\u0003\u0002\u0002\u0701\u013d\u0003\u0002\u0002\u0002\u0702\u0703\u0007\u0084\u0002\u0002\u0703\u013f\u0003\u0002\u0002\u0002\u0704\u0705\t\u0016\u0002\u0002\u0705\u0141\u0003\u0002\u0002\u0002\u0706\u0707\t\u0017\u0002\u0002\u0707\u0143\u0003\u0002\u0002\u0002\u0708\u0709\t\u0018\u0002\u0002\u0709\u0145\u0003\u0002\u0002\u0002\u070a\u070b\u0007Y\u0002\u0002\u070b\u0147\u0003\u0002\u0002\u0002\u070c\u070e\u0007\u0089\u0002\u0002\u070d\u070c\u0003\u0002\u0002\u0002\u070e\u0711\u0003\u0002\u0002\u0002\u070f\u070d\u0003\u0002\u0002\u0002\u070f\u0710\u0003\u0002\u0002\u0002\u0710\u0149\u0003\u0002\u0002\u0002\u0711\u070f\u0003\u0002\u0002\u0002\u0712\u0714\t\u0019\u0002\u0002\u0713\u0712\u0003\u0002\u0002\u0002\u0714\u0715\u0003\u0002\u0002\u0002\u0715\u0713\u0003\u0002\u0002\u0002\u0715\u0716\u0003\u0002\u0002\u0002\u0716\u014b\u0003\u0002\u0002\u0002\u00c7\u014f\u0151\u0154\u015e\u0162\u0169\u0172\u0179\u0180\u0185\u018d\u0194\u0197\u019f\u01a4\u01a8\u01ad\u01b5\u01c1\u01cd\u01d6\u01e0\u01f0\u01f6\u01fb\u0202\u0209\u0210\u021c\u021f\u0222\u022a\u022d\u0230\u023c\u0242\u0245\u0249\u024d\u0254\u0256\u025a\u025f\u026c\u0271\u0273\u027b\u027f\u028a\u0293\u02a1\u02a6\u02ae\u02b1\u02b6\u02bd\u02c0\u02c6\u02c9\u02cd\u02d1\u02de\u02eb\u02ed\u02f9\u02fe\u0304\u030c\u0314\u0317\u031f\u0328\u0330\u0337\u0343\u034b\u0353\u035a\u0360\u0373\u0377\u037e\u0382\u0385\u038c\u038f\u0397\u039b\u03a3\u03a8\u03af\u03b3\u03b7\u03c0\u03cb\u03d0\u03d8\u03dc\u03de\u03e4\u03eb\u03f3\u03fc\u0401\u0409\u040f\u0419\u041f\u0427\u042d\u0437\u043b\u0452\u0456\u045a\u0461\u046a\u0470\u0479\u0486\u0497\u049d\u04a9\u04b4\u04be\u04c3\u04cb\u04d7\u04db\u04df\u04e6\u04ea\u04ee\u04f2\u0507\u050b\u0511\u0517\u0521\u052c\u0533\u0549\u0564\u0567\u05a7\u05b7\u05b9\u05c6\u05cc\u05d1\u05d8\u05db\u05e0\u05e7\u05f5\u05fb\u0603\u060e\u0614\u0618\u061c\u0623\u0629\u0638\u0640\u0645\u064a\u064e\u0651\u0658\u065b\u0664\u066c\u0679\u0685\u0691\u0695\u0699\u069d\u06a3\u06a8\u06ad\u06af\u06b4\u06bd\u06c7\u06c9\u06d4\u06d8\u06db\u06e6\u06f0\u06f5\u06f9\u06fe\u070f\u0715";
    public static final ATN _ATN;

    private static String[] makeRuleNames() {
        return new String[]{"compilationUnit", "scriptStatements", "scriptStatement", "packageDeclaration", "importDeclaration", "typeDeclaration", "modifier", "modifiersOpt", "modifiers", "classOrInterfaceModifiersOpt", "classOrInterfaceModifiers", "classOrInterfaceModifier", "variableModifier", "variableModifiersOpt", "variableModifiers", "typeParameters", "typeParameter", "typeBound", "typeList", "classDeclaration", "classBody", "enumConstants", "enumConstant", "classBodyDeclaration", "memberDeclaration", "methodDeclaration", "compactConstructorDeclaration", "methodName", "returnType", "fieldDeclaration", "variableDeclarators", "variableDeclarator", "variableDeclaratorId", "variableInitializer", "variableInitializers", "emptyDims", "emptyDimsOpt", "standardType", "type", "classOrInterfaceType", "generalClassOrInterfaceType", "standardClassOrInterfaceType", "primitiveType", "typeArguments", "typeArgument", "annotatedQualifiedClassName", "qualifiedClassNameList", "formalParameters", "formalParameterList", "thisFormalParameter", "formalParameter", "methodBody", "qualifiedName", "qualifiedNameElement", "qualifiedNameElements", "qualifiedClassName", "qualifiedStandardClassName", "literal", "gstring", "gstringValue", "gstringPath", "lambdaExpression", "standardLambdaExpression", "lambdaParameters", "standardLambdaParameters", "lambdaBody", "closure", "closureOrLambdaExpression", "blockStatementsOpt", "blockStatements", "annotationsOpt", "annotation", "elementValues", "annotationName", "elementValuePairs", "elementValuePair", "elementValuePairName", "elementValue", "elementValueArrayInitializer", "block", "blockStatement", "localVariableDeclaration", "variableDeclaration", "typeNamePairs", "typeNamePair", "variableNames", "conditionalStatement", "ifElseStatement", "switchStatement", "loopStatement", "continueStatement", "breakStatement", "yieldStatement", "tryCatchStatement", "assertStatement", "statement", "catchClause", "catchType", "finallyBlock", "resources", "resourceList", "resource", "switchBlockStatementGroup", "switchLabel", "forControl", "enhancedForControl", "classicalForControl", "forInit", "forUpdate", "castParExpression", "parExpression", "expressionInPar", "expressionList", "expressionListElement", "enhancedStatementExpression", "statementExpression", "postfixExpression", "switchExpression", "switchBlockStatementExpressionGroup", "switchExpressionLabel", "expression", "castOperandExpression", "commandExpression", "commandArgument", "pathExpression", "pathElement", "namePart", "dynamicMemberName", "indexPropertyArgs", "namedPropertyArgs", "primary", "namedPropertyArgPrimary", "namedArgPrimary", "commandPrimary", "list", "map", "mapEntryList", "namedPropertyArgList", "mapEntry", "namedPropertyArg", "namedArg", "mapEntryLabel", "namedPropertyArgLabel", "namedArgLabel", "creator", "dim", "arrayInitializer", "anonymousInnerClassDeclaration", "createdName", "nonWildcardTypeArguments", "typeArgumentsOrDiamond", "arguments", "argumentList", "enhancedArgumentListInPar", "firstArgumentListElement", "argumentListElement", "enhancedArgumentListElement", "stringLiteral", "className", "identifier", "builtInType", "keywords", "rparen", "nls", "sep"};
    }

    private static String[] makeLiteralNames() {
        return new String[]{null, null, null, null, null, null, null, "'as'", "'def'", "'in'", "'trait'", "'threadsafe'", "'var'", null, "'abstract'", "'assert'", "'break'", "'yield'", "'case'", "'catch'", "'class'", "'const'", "'continue'", "'default'", "'do'", "'else'", "'enum'", "'extends'", "'final'", "'finally'", "'for'", "'if'", "'goto'", "'implements'", "'import'", "'instanceof'", "'interface'", "'native'", "'new'", "'non-sealed'", "'package'", "'permits'", "'private'", "'protected'", "'public'", "'record'", "'return'", "'sealed'", "'static'", "'strictfp'", "'super'", "'switch'", "'synchronized'", "'this'", "'throw'", "'throws'", "'transient'", "'try'", "'void'", "'volatile'", "'while'", null, null, null, "'null'", "'..'", "'<..'", "'..<'", "'<..<'", "'*.'", "'?.'", null, "'??.'", "'?:'", "'.&'", "'::'", "'=~'", "'==~'", "'**'", "'**='", "'<=>'", "'==='", "'!=='", "'->'", "'!instanceof'", "'!in'", null, null, null, null, null, null, "';'", "','", null, "'='", "'>'", "'<'", "'!'", "'~'", "'?'", "':'", "'=='", "'<='", "'>='", "'!='", "'&&'", "'||'", "'++'", "'--'", "'+'", "'-'", "'*'", null, "'&'", "'|'", "'^'", "'%'", "'+='", "'-='", "'*='", "'/='", "'&='", "'|='", "'^='", "'%='", "'<<='", "'>>='", "'>>>='", "'?='", null, null, "'@'", "'...'"};
    }

    private static String[] makeSymbolicNames() {
        return new String[]{null, "StringLiteral", "GStringBegin", "GStringEnd", "GStringPart", "GStringPathPart", "RollBackOne", "AS", "DEF", "IN", "TRAIT", "THREADSAFE", "VAR", "BuiltInPrimitiveType", "ABSTRACT", "ASSERT", "BREAK", "YIELD", "CASE", "CATCH", "CLASS", "CONST", "CONTINUE", "DEFAULT", "DO", "ELSE", "ENUM", "EXTENDS", "FINAL", "FINALLY", "FOR", "IF", "GOTO", "IMPLEMENTS", "IMPORT", "INSTANCEOF", "INTERFACE", "NATIVE", "NEW", "NON_SEALED", "PACKAGE", "PERMITS", "PRIVATE", "PROTECTED", "PUBLIC", "RECORD", "RETURN", "SEALED", "STATIC", "STRICTFP", "SUPER", "SWITCH", "SYNCHRONIZED", "THIS", "THROW", "THROWS", "TRANSIENT", "TRY", "VOID", "VOLATILE", "WHILE", "IntegerLiteral", "FloatingPointLiteral", "BooleanLiteral", "NullLiteral", "RANGE_INCLUSIVE", "RANGE_EXCLUSIVE_LEFT", "RANGE_EXCLUSIVE_RIGHT", "RANGE_EXCLUSIVE_FULL", "SPREAD_DOT", "SAFE_DOT", "SAFE_INDEX", "SAFE_CHAIN_DOT", "ELVIS", "METHOD_POINTER", "METHOD_REFERENCE", "REGEX_FIND", "REGEX_MATCH", "POWER", "POWER_ASSIGN", "SPACESHIP", "IDENTICAL", "NOT_IDENTICAL", "ARROW", "NOT_INSTANCEOF", "NOT_IN", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", "SEMI", "COMMA", "DOT", "ASSIGN", "GT", "LT", "NOT", "BITNOT", "QUESTION", "COLON", "EQUAL", "LE", "GE", "NOTEQUAL", "AND", "OR", "INC", "DEC", "ADD", "SUB", "MUL", "DIV", "BITAND", "BITOR", "XOR", "MOD", "ADD_ASSIGN", "SUB_ASSIGN", "MUL_ASSIGN", "DIV_ASSIGN", "AND_ASSIGN", "OR_ASSIGN", "XOR_ASSIGN", "MOD_ASSIGN", "LSHIFT_ASSIGN", "RSHIFT_ASSIGN", "URSHIFT_ASSIGN", "ELVIS_ASSIGN", "CapitalizedIdentifier", "Identifier", "AT", "ELLIPSIS", "WS", "NL", "SH_COMMENT", "UNEXPECTED_CHAR"};
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

    @Override
    public String getGrammarFileName() {
        return "GroovyParser.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @NotNull
    private FailedPredicateException createFailedPredicateException() {
        return this.createFailedPredicateException(null);
    }

    @NotNull
    private FailedPredicateException createFailedPredicateException(@Nullable String predicate) {
        return this.createFailedPredicateException(predicate, null);
    }

    @NotNull
    protected FailedPredicateException createFailedPredicateException(@Nullable String predicate, @Nullable String message) {
        return new FailedPredicateException(this, predicate, message);
    }

    @Override
    public int getSyntaxErrorSource() {
        return 1;
    }

    @Override
    public int getErrorLine() {
        Token token = this._input.LT(-1);
        if (null == token) {
            return -1;
        }
        return token.getLine();
    }

    @Override
    public int getErrorColumn() {
        Token token = this._input.LT(-1);
        if (null == token) {
            return -1;
        }
        return token.getCharPositionInLine() + 1 + token.getText().length();
    }

    public GroovyParser(TokenStream input) {
        super(input);
        this._interp = new ParserATNSimulator(this, _ATN);
    }

    @RuleVersion(value=0)
    public final CompilationUnitContext compilationUnit() throws RecognitionException {
        CompilationUnitContext _localctx = new CompilationUnitContext(this._ctx, this.getState());
        this.enterRule(_localctx, 0, 0);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(330);
            this.nls();
            this.setState(335);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 1, this._ctx)) {
                case 1: {
                    this.setState(331);
                    this.packageDeclaration();
                    this.setState(333);
                    this._errHandler.sync(this);
                    switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 0, this._ctx)) {
                        case 1: {
                            this.setState(332);
                            this.sep();
                        }
                    }
                }
            }
            this.setState(338);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 2, this._ctx)) {
                case 1: {
                    this.setState(337);
                    this.scriptStatements();
                }
            }
            this.setState(340);
            this.match(-1);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final ScriptStatementsContext scriptStatements() throws RecognitionException {
        ScriptStatementsContext _localctx = new ScriptStatementsContext(this._ctx, this.getState());
        this.enterRule(_localctx, 2, 1);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(342);
            this.scriptStatement();
            this.setState(348);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 3, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(343);
                    this.sep();
                    this.setState(344);
                    this.scriptStatement();
                }
                this.setState(350);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 3, this._ctx);
            }
            this.setState(352);
            this._errHandler.sync(this);
            int _la = this._input.LA(1);
            if (_la == 92 || _la == 135) {
                this.setState(351);
                this.sep();
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final ScriptStatementContext scriptStatement() throws RecognitionException {
        ScriptStatementContext _localctx = new ScriptStatementContext(this._ctx, this.getState());
        this.enterRule(_localctx, 4, 2);
        try {
            this.setState(359);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 5, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(354);
                    this.importDeclaration();
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(355);
                    this.typeDeclaration();
                    return _localctx;
                }
                case 3: {
                    this.enterOuterAlt(_localctx, 3);
                    this.setState(356);
                    if (SemanticPredicates.isInvalidMethodDeclaration(this._input)) {
                        throw this.createFailedPredicateException(" !SemanticPredicates.isInvalidMethodDeclaration(_input) ");
                    }
                    this.setState(357);
                    this.methodDeclaration(3, 9);
                    return _localctx;
                }
                case 4: {
                    this.enterOuterAlt(_localctx, 4);
                    this.setState(358);
                    this.statement();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final PackageDeclarationContext packageDeclaration() throws RecognitionException {
        PackageDeclarationContext _localctx = new PackageDeclarationContext(this._ctx, this.getState());
        this.enterRule(_localctx, 6, 3);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(361);
            this.annotationsOpt();
            this.setState(362);
            this.match(40);
            this.setState(363);
            this.qualifiedName();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final ImportDeclarationContext importDeclaration() throws RecognitionException {
        ImportDeclarationContext _localctx = new ImportDeclarationContext(this._ctx, this.getState());
        this.enterRule(_localctx, 8, 4);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(365);
            this.annotationsOpt();
            this.setState(366);
            this.match(34);
            this.setState(368);
            this._errHandler.sync(this);
            int _la = this._input.LA(1);
            if (_la == 48) {
                this.setState(367);
                this.match(48);
            }
            this.setState(370);
            this.qualifiedName();
            this.setState(375);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 94: {
                    this.setState(371);
                    this.match(94);
                    this.setState(372);
                    this.match(112);
                    return _localctx;
                }
                case 7: {
                    this.setState(373);
                    this.match(7);
                    this.setState(374);
                    _localctx.alias = this.identifier();
                    return _localctx;
                }
                case -1: 
                case 92: 
                case 135: {
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final TypeDeclarationContext typeDeclaration() throws RecognitionException {
        TypeDeclarationContext _localctx = new TypeDeclarationContext(this._ctx, this.getState());
        this.enterRule(_localctx, 10, 5);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(377);
            this.classOrInterfaceModifiersOpt();
            this.setState(378);
            this.classDeclaration();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RuleVersion(value=0)
    public final ModifierContext modifier() throws RecognitionException {
        ModifierContext _localctx = new ModifierContext(this._ctx, this.getState());
        this.enterRule(_localctx, 12, 6);
        try {
            this.setState(382);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 14: 
                case 23: 
                case 28: 
                case 39: 
                case 42: 
                case 43: 
                case 44: 
                case 47: 
                case 48: 
                case 49: 
                case 132: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(380);
                    this.classOrInterfaceModifier();
                    return _localctx;
                }
                case 8: 
                case 12: 
                case 37: 
                case 52: 
                case 56: 
                case 59: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(381);
                    _localctx.m = this._input.LT(1);
                    int _la = this._input.LA(1);
                    if ((_la & 0xFFFFFFC0) != 0 || (1L << _la & 0x910002000001100L) == 0L) {
                        _localctx.m = this._errHandler.recoverInline(this);
                        return _localctx;
                    } else {
                        if (this._input.LA(1) == -1) {
                            this.matchedEOF = true;
                        }
                        this._errHandler.reportMatch(this);
                        this.consume();
                        return _localctx;
                    }
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final ModifiersOptContext modifiersOpt() throws RecognitionException {
        ModifiersOptContext _localctx = new ModifiersOptContext(this._ctx, this.getState());
        this.enterRule(_localctx, 14, 7);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(387);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 9, this._ctx)) {
                case 1: {
                    this.setState(384);
                    this.modifiers();
                    this.setState(385);
                    this.nls();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final ModifiersContext modifiers() throws RecognitionException {
        ModifiersContext _localctx = new ModifiersContext(this._ctx, this.getState());
        this.enterRule(_localctx, 16, 8);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(389);
            this.modifier();
            this.setState(395);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 10, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(390);
                    this.nls();
                    this.setState(391);
                    this.modifier();
                }
                this.setState(397);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 10, this._ctx);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final ClassOrInterfaceModifiersOptContext classOrInterfaceModifiersOpt() throws RecognitionException {
        ClassOrInterfaceModifiersOptContext _localctx = new ClassOrInterfaceModifiersOptContext(this._ctx, this.getState());
        this.enterRule(_localctx, 18, 9);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(405);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 12, this._ctx)) {
                case 1: {
                    this.setState(398);
                    this.classOrInterfaceModifiers();
                    this.setState(402);
                    this._errHandler.sync(this);
                    int _la = this._input.LA(1);
                    while (_la == 135) {
                        this.setState(399);
                        this.match(135);
                        this.setState(404);
                        this._errHandler.sync(this);
                        _la = this._input.LA(1);
                    }
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final ClassOrInterfaceModifiersContext classOrInterfaceModifiers() throws RecognitionException {
        ClassOrInterfaceModifiersContext _localctx = new ClassOrInterfaceModifiersContext(this._ctx, this.getState());
        this.enterRule(_localctx, 20, 10);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(407);
            this.classOrInterfaceModifier();
            this.setState(413);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 13, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(408);
                    this.nls();
                    this.setState(409);
                    this.classOrInterfaceModifier();
                }
                this.setState(415);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 13, this._ctx);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RuleVersion(value=0)
    public final ClassOrInterfaceModifierContext classOrInterfaceModifier() throws RecognitionException {
        ClassOrInterfaceModifierContext _localctx = new ClassOrInterfaceModifierContext(this._ctx, this.getState());
        this.enterRule(_localctx, 22, 11);
        try {
            this.setState(418);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 132: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(416);
                    this.annotation();
                    return _localctx;
                }
                case 14: 
                case 23: 
                case 28: 
                case 39: 
                case 42: 
                case 43: 
                case 44: 
                case 47: 
                case 48: 
                case 49: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(417);
                    _localctx.m = this._input.LT(1);
                    int _la = this._input.LA(1);
                    if ((_la & 0xFFFFFFC0) != 0 || (1L << _la & 0x39C8010804000L) == 0L) {
                        _localctx.m = this._errHandler.recoverInline(this);
                        return _localctx;
                    } else {
                        if (this._input.LA(1) == -1) {
                            this.matchedEOF = true;
                        }
                        this._errHandler.reportMatch(this);
                        this.consume();
                        return _localctx;
                    }
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RuleVersion(value=0)
    public final VariableModifierContext variableModifier() throws RecognitionException {
        VariableModifierContext _localctx = new VariableModifierContext(this._ctx, this.getState());
        this.enterRule(_localctx, 24, 12);
        try {
            this.setState(422);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 132: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(420);
                    this.annotation();
                    return _localctx;
                }
                case 8: 
                case 12: 
                case 14: 
                case 28: 
                case 42: 
                case 43: 
                case 44: 
                case 48: 
                case 49: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(421);
                    _localctx.m = this._input.LT(1);
                    int _la = this._input.LA(1);
                    if ((_la & 0xFFFFFFC0) != 0 || (1L << _la & 0x31C0010005100L) == 0L) {
                        _localctx.m = this._errHandler.recoverInline(this);
                        return _localctx;
                    } else {
                        if (this._input.LA(1) == -1) {
                            this.matchedEOF = true;
                        }
                        this._errHandler.reportMatch(this);
                        this.consume();
                        return _localctx;
                    }
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final VariableModifiersOptContext variableModifiersOpt() throws RecognitionException {
        VariableModifiersOptContext _localctx = new VariableModifiersOptContext(this._ctx, this.getState());
        this.enterRule(_localctx, 26, 13);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(427);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 16, this._ctx)) {
                case 1: {
                    this.setState(424);
                    this.variableModifiers();
                    this.setState(425);
                    this.nls();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final VariableModifiersContext variableModifiers() throws RecognitionException {
        VariableModifiersContext _localctx = new VariableModifiersContext(this._ctx, this.getState());
        this.enterRule(_localctx, 28, 14);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(429);
            this.variableModifier();
            this.setState(435);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 17, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(430);
                    this.nls();
                    this.setState(431);
                    this.variableModifier();
                }
                this.setState(437);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 17, this._ctx);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final TypeParametersContext typeParameters() throws RecognitionException {
        TypeParametersContext _localctx = new TypeParametersContext(this._ctx, this.getState());
        this.enterRule(_localctx, 30, 15);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(438);
            this.match(97);
            this.setState(439);
            this.nls();
            this.setState(440);
            this.typeParameter();
            this.setState(447);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 18, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(441);
                    this.match(93);
                    this.setState(442);
                    this.nls();
                    this.setState(443);
                    this.typeParameter();
                }
                this.setState(449);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 18, this._ctx);
            }
            this.setState(450);
            this.nls();
            this.setState(451);
            this.match(96);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final TypeParameterContext typeParameter() throws RecognitionException {
        TypeParameterContext _localctx = new TypeParameterContext(this._ctx, this.getState());
        this.enterRule(_localctx, 32, 16);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(453);
            this.annotationsOpt();
            this.setState(454);
            this.className();
            this.setState(459);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 19, this._ctx)) {
                case 1: {
                    this.setState(455);
                    this.match(27);
                    this.setState(456);
                    this.nls();
                    this.setState(457);
                    this.typeBound();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final TypeBoundContext typeBound() throws RecognitionException {
        TypeBoundContext _localctx = new TypeBoundContext(this._ctx, this.getState());
        this.enterRule(_localctx, 34, 17);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(461);
            this.type();
            this.setState(468);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 20, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(462);
                    this.match(114);
                    this.setState(463);
                    this.nls();
                    this.setState(464);
                    this.type();
                }
                this.setState(470);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 20, this._ctx);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final TypeListContext typeList() throws RecognitionException {
        TypeListContext _localctx = new TypeListContext(this._ctx, this.getState());
        this.enterRule(_localctx, 36, 18);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(471);
            this.type();
            this.setState(478);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 21, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(472);
                    this.match(93);
                    this.setState(473);
                    this.nls();
                    this.setState(474);
                    this.type();
                }
                this.setState(480);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 21, this._ctx);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final ClassDeclarationContext classDeclaration() throws RecognitionException {
        ClassDeclarationContext _localctx = new ClassDeclarationContext(this._ctx, this.getState());
        this.enterRule(_localctx, 38, 19);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(494);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 20: {
                    this.setState(481);
                    this.match(20);
                    _localctx.t = 0;
                    break;
                }
                case 36: {
                    this.setState(483);
                    this.match(36);
                    _localctx.t = 1;
                    break;
                }
                case 26: {
                    this.setState(485);
                    this.match(26);
                    _localctx.t = 2;
                    break;
                }
                case 132: {
                    this.setState(487);
                    this.match(132);
                    this.setState(488);
                    this.match(36);
                    _localctx.t = 3;
                    break;
                }
                case 10: {
                    this.setState(490);
                    this.match(10);
                    _localctx.t = 4;
                    break;
                }
                case 45: {
                    this.setState(492);
                    this.match(45);
                    _localctx.t = 5;
                    break;
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
            this.setState(496);
            this.identifier();
            this.setState(500);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 23, this._ctx)) {
                case 1: {
                    this.setState(497);
                    this.nls();
                    this.setState(498);
                    this.typeParameters();
                }
            }
            this.setState(505);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 24, this._ctx)) {
                case 1: {
                    this.setState(502);
                    this.nls();
                    this.setState(503);
                    this.formalParameters();
                }
            }
            this.setState(512);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 25, this._ctx)) {
                case 1: {
                    this.setState(507);
                    this.nls();
                    this.setState(508);
                    this.match(27);
                    this.setState(509);
                    this.nls();
                    this.setState(510);
                    _localctx.scs = this.typeList();
                }
            }
            this.setState(519);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 26, this._ctx)) {
                case 1: {
                    this.setState(514);
                    this.nls();
                    this.setState(515);
                    this.match(33);
                    this.setState(516);
                    this.nls();
                    this.setState(517);
                    _localctx.is = this.typeList();
                }
            }
            this.setState(526);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 27, this._ctx)) {
                case 1: {
                    this.setState(521);
                    this.nls();
                    this.setState(522);
                    this.match(41);
                    this.setState(523);
                    this.nls();
                    this.setState(524);
                    _localctx.ps = this.typeList();
                }
            }
            this.setState(528);
            this.nls();
            this.setState(529);
            this.classBody(_localctx.t);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final ClassBodyContext classBody(int t) throws RecognitionException {
        ClassBodyContext _localctx = new ClassBodyContext(this._ctx, this.getState(), t);
        this.enterRule(_localctx, 40, 20);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(531);
            this.match(88);
            this.setState(532);
            this.nls();
            this.setState(544);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 30, this._ctx)) {
                case 1: {
                    this.setState(533);
                    if (2 != _localctx.t) {
                        throw this.createFailedPredicateException(" 2 == $t ");
                    }
                    this.setState(534);
                    this.enumConstants();
                    this.setState(538);
                    this._errHandler.sync(this);
                    switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 28, this._ctx)) {
                        case 1: {
                            this.setState(535);
                            this.nls();
                            this.setState(536);
                            this.match(93);
                        }
                    }
                    this.setState(541);
                    this._errHandler.sync(this);
                    switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 29, this._ctx)) {
                        case 1: {
                            this.setState(540);
                            this.sep();
                        }
                    }
                    break;
                }
            }
            this.setState(555);
            this._errHandler.sync(this);
            int _la = this._input.LA(1);
            if ((_la & 0xFFFFFFC0) == 0 && (1L << _la & 0xD13BFB414927782L) != 0L || (_la - 88 & 0xFFFFFFC0) == 0 && (1L << _la - 88 & 0x1C0000001205L) != 0L) {
                this.setState(546);
                this.classBodyDeclaration(_localctx.t);
                this.setState(552);
                this._errHandler.sync(this);
                int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 31, this._ctx);
                while (_alt != 2 && _alt != 0) {
                    if (_alt == 1) {
                        this.setState(547);
                        this.sep();
                        this.setState(548);
                        this.classBodyDeclaration(_localctx.t);
                    }
                    this.setState(554);
                    this._errHandler.sync(this);
                    _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 31, this._ctx);
                }
            }
            this.setState(558);
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            if (_la == 92 || _la == 135) {
                this.setState(557);
                this.sep();
            }
            this.setState(560);
            this.match(89);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final EnumConstantsContext enumConstants() throws RecognitionException {
        EnumConstantsContext _localctx = new EnumConstantsContext(this._ctx, this.getState());
        this.enterRule(_localctx, 42, 21);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(562);
            this.enumConstant();
            this.setState(570);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 34, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(563);
                    this.nls();
                    this.setState(564);
                    this.match(93);
                    this.setState(565);
                    this.nls();
                    this.setState(566);
                    this.enumConstant();
                }
                this.setState(572);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 34, this._ctx);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final EnumConstantContext enumConstant() throws RecognitionException {
        EnumConstantContext _localctx = new EnumConstantContext(this._ctx, this.getState());
        this.enterRule(_localctx, 44, 22);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(573);
            this.annotationsOpt();
            this.setState(574);
            this.identifier();
            this.setState(576);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 35, this._ctx)) {
                case 1: {
                    this.setState(575);
                    this.arguments();
                    break;
                }
            }
            this.setState(579);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 36, this._ctx)) {
                case 1: {
                    this.setState(578);
                    this.anonymousInnerClassDeclaration(1);
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final ClassBodyDeclarationContext classBodyDeclaration(int t) throws RecognitionException {
        ClassBodyDeclarationContext _localctx = new ClassBodyDeclarationContext(this._ctx, this.getState(), t);
        this.enterRule(_localctx, 46, 23);
        try {
            this.setState(587);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 38, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(583);
                    this._errHandler.sync(this);
                    int _la = this._input.LA(1);
                    if (_la == 48) {
                        this.setState(581);
                        this.match(48);
                        this.setState(582);
                        this.nls();
                    }
                    this.setState(585);
                    this.block();
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(586);
                    this.memberDeclaration(_localctx.t);
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final MemberDeclarationContext memberDeclaration(int t) throws RecognitionException {
        MemberDeclarationContext _localctx = new MemberDeclarationContext(this._ctx, this.getState(), t);
        this.enterRule(_localctx, 48, 24);
        try {
            this.setState(596);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 40, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(589);
                    this.methodDeclaration(0, _localctx.t);
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(590);
                    this.fieldDeclaration();
                    return _localctx;
                }
                case 3: {
                    this.enterOuterAlt(_localctx, 3);
                    this.setState(591);
                    this.modifiersOpt();
                    this.setState(594);
                    this._errHandler.sync(this);
                    switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 39, this._ctx)) {
                        case 1: {
                            this.setState(592);
                            this.classDeclaration();
                            return _localctx;
                        }
                        case 2: {
                            this.setState(593);
                            this.compactConstructorDeclaration();
                        }
                    }
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final MethodDeclarationContext methodDeclaration(int t, int ct) throws RecognitionException {
        MethodDeclarationContext _localctx = new MethodDeclarationContext(this._ctx, this.getState(), t, ct);
        this.enterRule(_localctx, 50, 25);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(598);
            this.modifiersOpt();
            this.setState(600);
            this._errHandler.sync(this);
            int _la = this._input.LA(1);
            if (_la == 97) {
                this.setState(599);
                this.typeParameters();
            }
            this.setState(605);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 42, this._ctx)) {
                case 1: {
                    this.setState(602);
                    this.returnType(_localctx.ct);
                    this.setState(603);
                    this.nls();
                    break;
                }
            }
            this.setState(607);
            this.methodName();
            this.setState(608);
            this.formalParameters();
            this.setState(625);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 45, this._ctx)) {
                case 1: {
                    this.setState(609);
                    this.match(23);
                    this.setState(610);
                    this.nls();
                    this.setState(611);
                    this.elementValue();
                    return _localctx;
                }
                case 2: {
                    this.setState(618);
                    this._errHandler.sync(this);
                    switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 43, this._ctx)) {
                        case 1: {
                            this.setState(613);
                            this.nls();
                            this.setState(614);
                            this.match(55);
                            this.setState(615);
                            this.nls();
                            this.setState(616);
                            this.qualifiedClassNameList();
                            break;
                        }
                    }
                    this.setState(623);
                    this._errHandler.sync(this);
                    switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 44, this._ctx)) {
                        case 1: {
                            this.setState(620);
                            this.nls();
                            this.setState(621);
                            this.methodBody();
                        }
                    }
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final CompactConstructorDeclarationContext compactConstructorDeclaration() throws RecognitionException {
        CompactConstructorDeclarationContext _localctx = new CompactConstructorDeclarationContext(this._ctx, this.getState());
        this.enterRule(_localctx, 52, 26);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(627);
            this.methodName();
            this.setState(628);
            this.nls();
            this.setState(629);
            this.methodBody();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RuleVersion(value=0)
    public final MethodNameContext methodName() throws RecognitionException {
        MethodNameContext _localctx = new MethodNameContext(this._ctx, this.getState());
        this.enterRule(_localctx, 54, 27);
        try {
            this.setState(633);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 7: 
                case 9: 
                case 10: 
                case 12: 
                case 17: 
                case 41: 
                case 45: 
                case 47: 
                case 130: 
                case 131: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(631);
                    this.identifier();
                    return _localctx;
                }
                case 1: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(632);
                    this.stringLiteral();
                    return _localctx;
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final ReturnTypeContext returnType(int ct) throws RecognitionException {
        ReturnTypeContext _localctx = new ReturnTypeContext(this._ctx, this.getState(), ct);
        this.enterRule(_localctx, 56, 28);
        try {
            this.setState(637);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 47, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(635);
                    this.standardType();
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(636);
                    this.match(58);
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final FieldDeclarationContext fieldDeclaration() throws RecognitionException {
        FieldDeclarationContext _localctx = new FieldDeclarationContext(this._ctx, this.getState());
        this.enterRule(_localctx, 58, 29);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(639);
            this.variableDeclaration(1);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final VariableDeclaratorsContext variableDeclarators() throws RecognitionException {
        VariableDeclaratorsContext _localctx = new VariableDeclaratorsContext(this._ctx, this.getState());
        this.enterRule(_localctx, 60, 30);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(641);
            this.variableDeclarator();
            this.setState(648);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 48, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(642);
                    this.match(93);
                    this.setState(643);
                    this.nls();
                    this.setState(644);
                    this.variableDeclarator();
                }
                this.setState(650);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 48, this._ctx);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final VariableDeclaratorContext variableDeclarator() throws RecognitionException {
        VariableDeclaratorContext _localctx = new VariableDeclaratorContext(this._ctx, this.getState());
        this.enterRule(_localctx, 62, 31);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(651);
            this.variableDeclaratorId();
            this.setState(657);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 49, this._ctx)) {
                case 1: {
                    this.setState(652);
                    this.nls();
                    this.setState(653);
                    this.match(95);
                    this.setState(654);
                    this.nls();
                    this.setState(655);
                    this.variableInitializer();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final VariableDeclaratorIdContext variableDeclaratorId() throws RecognitionException {
        VariableDeclaratorIdContext _localctx = new VariableDeclaratorIdContext(this._ctx, this.getState());
        this.enterRule(_localctx, 64, 32);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(659);
            this.identifier();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final VariableInitializerContext variableInitializer() throws RecognitionException {
        VariableInitializerContext _localctx = new VariableInitializerContext(this._ctx, this.getState());
        this.enterRule(_localctx, 66, 33);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(661);
            this.enhancedStatementExpression();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final VariableInitializersContext variableInitializers() throws RecognitionException {
        VariableInitializersContext _localctx = new VariableInitializersContext(this._ctx, this.getState());
        this.enterRule(_localctx, 68, 34);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(663);
            this.variableInitializer();
            this.setState(671);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 50, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(664);
                    this.nls();
                    this.setState(665);
                    this.match(93);
                    this.setState(666);
                    this.nls();
                    this.setState(667);
                    this.variableInitializer();
                }
                this.setState(673);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 50, this._ctx);
            }
            this.setState(674);
            this.nls();
            this.setState(676);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 51, this._ctx)) {
                case 1: {
                    this.setState(675);
                    this.match(93);
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final EmptyDimsContext emptyDims() throws RecognitionException {
        EmptyDimsContext _localctx = new EmptyDimsContext(this._ctx, this.getState());
        this.enterRule(_localctx, 70, 35);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(682);
            this._errHandler.sync(this);
            int _alt = 1;
            do {
                switch (_alt) {
                    case 1: {
                        this.setState(678);
                        this.annotationsOpt();
                        this.setState(679);
                        this.match(90);
                        this.setState(680);
                        this.match(91);
                        break;
                    }
                    default: {
                        throw new NoViableAltException(this);
                    }
                }
                this.setState(684);
                this._errHandler.sync(this);
            } while ((_alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 52, this._ctx)) != 2 && _alt != 0);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final EmptyDimsOptContext emptyDimsOpt() throws RecognitionException {
        EmptyDimsOptContext _localctx = new EmptyDimsOptContext(this._ctx, this.getState());
        this.enterRule(_localctx, 72, 36);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(687);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 53, this._ctx)) {
                case 1: {
                    this.setState(686);
                    this.emptyDims();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final TypeContext standardType() throws RecognitionException {
        TypeContext _localctx = new TypeContext(this._ctx, this.getState());
        this.enterRule(_localctx, 74, 37);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(689);
            this.annotationsOpt();
            this.setState(692);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 13: {
                    this.setState(690);
                    this.primitiveType();
                    break;
                }
                case 7: 
                case 8: 
                case 9: 
                case 10: 
                case 12: 
                case 17: 
                case 41: 
                case 45: 
                case 47: 
                case 130: 
                case 131: {
                    this.setState(691);
                    this.standardClassOrInterfaceType();
                    break;
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
            this.setState(694);
            this.emptyDimsOpt();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final TypeContext type() throws RecognitionException {
        TypeContext _localctx = new TypeContext(this._ctx, this.getState());
        this.enterRule(_localctx, 76, 38);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(696);
            this.annotationsOpt();
            this.setState(702);
            this._errHandler.sync(this);
            block2 : switch (this._input.LA(1)) {
                case 13: 
                case 58: {
                    this.setState(699);
                    this._errHandler.sync(this);
                    switch (this._input.LA(1)) {
                        case 13: {
                            this.setState(697);
                            this.primitiveType();
                            break block2;
                        }
                        case 58: {
                            this.setState(698);
                            this.match(58);
                            break block2;
                        }
                    }
                    throw new NoViableAltException(this);
                }
                case 7: 
                case 8: 
                case 9: 
                case 10: 
                case 12: 
                case 17: 
                case 41: 
                case 45: 
                case 47: 
                case 130: 
                case 131: {
                    this.setState(701);
                    this.generalClassOrInterfaceType();
                    break;
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
            this.setState(704);
            this.emptyDimsOpt();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final ClassOrInterfaceTypeContext classOrInterfaceType() throws RecognitionException {
        ClassOrInterfaceTypeContext _localctx = new ClassOrInterfaceTypeContext(this._ctx, this.getState());
        this.enterRule(_localctx, 78, 39);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(708);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 57, this._ctx)) {
                case 1: {
                    this.setState(706);
                    this.qualifiedClassName();
                    break;
                }
                case 2: {
                    this.setState(707);
                    this.qualifiedStandardClassName();
                }
            }
            this.setState(711);
            this._errHandler.sync(this);
            int _la = this._input.LA(1);
            if (_la == 97) {
                this.setState(710);
                this.typeArguments();
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final ClassOrInterfaceTypeContext generalClassOrInterfaceType() throws RecognitionException {
        ClassOrInterfaceTypeContext _localctx = new ClassOrInterfaceTypeContext(this._ctx, this.getState());
        this.enterRule(_localctx, 80, 40);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(713);
            this.qualifiedClassName();
            this.setState(715);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 59, this._ctx)) {
                case 1: {
                    this.setState(714);
                    this.typeArguments();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final ClassOrInterfaceTypeContext standardClassOrInterfaceType() throws RecognitionException {
        ClassOrInterfaceTypeContext _localctx = new ClassOrInterfaceTypeContext(this._ctx, this.getState());
        this.enterRule(_localctx, 82, 41);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(717);
            this.qualifiedStandardClassName();
            this.setState(719);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 60, this._ctx)) {
                case 1: {
                    this.setState(718);
                    this.typeArguments();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final PrimitiveTypeContext primitiveType() throws RecognitionException {
        PrimitiveTypeContext _localctx = new PrimitiveTypeContext(this._ctx, this.getState());
        this.enterRule(_localctx, 84, 42);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(721);
            this.match(13);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final TypeArgumentsContext typeArguments() throws RecognitionException {
        TypeArgumentsContext _localctx = new TypeArgumentsContext(this._ctx, this.getState());
        this.enterRule(_localctx, 86, 43);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(723);
            this.match(97);
            this.setState(724);
            this.nls();
            this.setState(725);
            this.typeArgument();
            this.setState(732);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 61, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(726);
                    this.match(93);
                    this.setState(727);
                    this.nls();
                    this.setState(728);
                    this.typeArgument();
                }
                this.setState(734);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 61, this._ctx);
            }
            this.setState(735);
            this.nls();
            this.setState(736);
            this.match(96);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final TypeArgumentContext typeArgument() throws RecognitionException {
        TypeArgumentContext _localctx = new TypeArgumentContext(this._ctx, this.getState());
        this.enterRule(_localctx, 88, 44);
        try {
            this.setState(747);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 63, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(738);
                    this.type();
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(739);
                    this.annotationsOpt();
                    this.setState(740);
                    this.match(100);
                    this.setState(745);
                    this._errHandler.sync(this);
                    switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 62, this._ctx)) {
                        case 1: {
                            this.setState(741);
                            int _la = this._input.LA(1);
                            if (_la != 27 && _la != 50) {
                                this._errHandler.recoverInline(this);
                            } else {
                                if (this._input.LA(1) == -1) {
                                    this.matchedEOF = true;
                                }
                                this._errHandler.reportMatch(this);
                                this.consume();
                            }
                            this.setState(742);
                            this.nls();
                            this.setState(743);
                            this.type();
                        }
                    }
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final AnnotatedQualifiedClassNameContext annotatedQualifiedClassName() throws RecognitionException {
        AnnotatedQualifiedClassNameContext _localctx = new AnnotatedQualifiedClassNameContext(this._ctx, this.getState());
        this.enterRule(_localctx, 90, 45);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(749);
            this.annotationsOpt();
            this.setState(750);
            this.qualifiedClassName();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final QualifiedClassNameListContext qualifiedClassNameList() throws RecognitionException {
        QualifiedClassNameListContext _localctx = new QualifiedClassNameListContext(this._ctx, this.getState());
        this.enterRule(_localctx, 92, 46);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(752);
            this.annotatedQualifiedClassName();
            this.setState(759);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 64, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(753);
                    this.match(93);
                    this.setState(754);
                    this.nls();
                    this.setState(755);
                    this.annotatedQualifiedClassName();
                }
                this.setState(761);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 64, this._ctx);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final FormalParametersContext formalParameters() throws RecognitionException {
        FormalParametersContext _localctx = new FormalParametersContext(this._ctx, this.getState());
        this.enterRule(_localctx, 94, 47);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(762);
            this.match(86);
            this.setState(764);
            this._errHandler.sync(this);
            int _la = this._input.LA(1);
            if ((_la & 0xFFFFFFC0) == 0 && (1L << _la & 0x403BF0410027780L) != 0L || (_la - 90 & 0xFFFFFFC0) == 0 && (1L << _la - 90 & 0xF0000000401L) != 0L) {
                this.setState(763);
                this.formalParameterList();
            }
            this.setState(766);
            this.rparen();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final FormalParameterListContext formalParameterList() throws RecognitionException {
        FormalParameterListContext _localctx = new FormalParameterListContext(this._ctx, this.getState());
        this.enterRule(_localctx, 96, 48);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(770);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 66, this._ctx)) {
                case 1: {
                    this.setState(768);
                    this.formalParameter();
                    break;
                }
                case 2: {
                    this.setState(769);
                    this.thisFormalParameter();
                }
            }
            this.setState(778);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 67, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(772);
                    this.match(93);
                    this.setState(773);
                    this.nls();
                    this.setState(774);
                    this.formalParameter();
                }
                this.setState(780);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 67, this._ctx);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final ThisFormalParameterContext thisFormalParameter() throws RecognitionException {
        ThisFormalParameterContext _localctx = new ThisFormalParameterContext(this._ctx, this.getState());
        this.enterRule(_localctx, 98, 49);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(781);
            this.type();
            this.setState(782);
            this.match(53);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final FormalParameterContext formalParameter() throws RecognitionException {
        FormalParameterContext _localctx = new FormalParameterContext(this._ctx, this.getState());
        this.enterRule(_localctx, 100, 50);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(784);
            this.variableModifiersOpt();
            this.setState(786);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 68, this._ctx)) {
                case 1: {
                    this.setState(785);
                    this.type();
                    break;
                }
            }
            this.setState(789);
            this._errHandler.sync(this);
            int _la = this._input.LA(1);
            if (_la == 133) {
                this.setState(788);
                this.match(133);
            }
            this.setState(791);
            this.variableDeclaratorId();
            this.setState(797);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 70, this._ctx)) {
                case 1: {
                    this.setState(792);
                    this.nls();
                    this.setState(793);
                    this.match(95);
                    this.setState(794);
                    this.nls();
                    this.setState(795);
                    this.expression(0);
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final MethodBodyContext methodBody() throws RecognitionException {
        MethodBodyContext _localctx = new MethodBodyContext(this._ctx, this.getState());
        this.enterRule(_localctx, 102, 51);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(799);
            this.block();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final QualifiedNameContext qualifiedName() throws RecognitionException {
        QualifiedNameContext _localctx = new QualifiedNameContext(this._ctx, this.getState());
        this.enterRule(_localctx, 104, 52);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(801);
            this.qualifiedNameElement();
            this.setState(806);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 71, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(802);
                    this.match(94);
                    this.setState(803);
                    this.qualifiedNameElement();
                }
                this.setState(808);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 71, this._ctx);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final QualifiedNameElementContext qualifiedNameElement() throws RecognitionException {
        QualifiedNameElementContext _localctx = new QualifiedNameElementContext(this._ctx, this.getState());
        this.enterRule(_localctx, 106, 53);
        try {
            this.setState(814);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 72, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(809);
                    this.identifier();
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(810);
                    this.match(8);
                    return _localctx;
                }
                case 3: {
                    this.enterOuterAlt(_localctx, 3);
                    this.setState(811);
                    this.match(9);
                    return _localctx;
                }
                case 4: {
                    this.enterOuterAlt(_localctx, 4);
                    this.setState(812);
                    this.match(7);
                    return _localctx;
                }
                case 5: {
                    this.enterOuterAlt(_localctx, 5);
                    this.setState(813);
                    this.match(10);
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final QualifiedNameElementsContext qualifiedNameElements() throws RecognitionException {
        QualifiedNameElementsContext _localctx = new QualifiedNameElementsContext(this._ctx, this.getState());
        this.enterRule(_localctx, 108, 54);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(821);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 73, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(816);
                    this.qualifiedNameElement();
                    this.setState(817);
                    this.match(94);
                }
                this.setState(823);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 73, this._ctx);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final QualifiedClassNameContext qualifiedClassName() throws RecognitionException {
        QualifiedClassNameContext _localctx = new QualifiedClassNameContext(this._ctx, this.getState());
        this.enterRule(_localctx, 110, 55);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(824);
            this.qualifiedNameElements();
            this.setState(825);
            this.identifier();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final QualifiedStandardClassNameContext qualifiedStandardClassName() throws RecognitionException {
        QualifiedStandardClassNameContext _localctx = new QualifiedStandardClassNameContext(this._ctx, this.getState());
        this.enterRule(_localctx, 112, 56);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(827);
            this.qualifiedNameElements();
            this.setState(828);
            this.className();
            this.setState(833);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 74, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(829);
                    this.match(94);
                    this.setState(830);
                    this.className();
                }
                this.setState(835);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 74, this._ctx);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RuleVersion(value=0)
    public final LiteralContext literal() throws RecognitionException {
        LiteralContext _localctx = new LiteralContext(this._ctx, this.getState());
        this.enterRule(_localctx, 114, 57);
        try {
            this.setState(841);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 61: {
                    _localctx = new IntegerLiteralAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(836);
                    this.match(61);
                    return _localctx;
                }
                case 62: {
                    _localctx = new FloatingPointLiteralAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(837);
                    this.match(62);
                    return _localctx;
                }
                case 1: {
                    _localctx = new StringLiteralAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 3);
                    this.setState(838);
                    this.stringLiteral();
                    return _localctx;
                }
                case 63: {
                    _localctx = new BooleanLiteralAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 4);
                    this.setState(839);
                    this.match(63);
                    return _localctx;
                }
                case 64: {
                    _localctx = new NullLiteralAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 5);
                    this.setState(840);
                    this.match(64);
                    return _localctx;
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final GstringContext gstring() throws RecognitionException {
        GstringContext _localctx = new GstringContext(this._ctx, this.getState());
        this.enterRule(_localctx, 116, 58);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(843);
            this.match(2);
            this.setState(844);
            this.gstringValue();
            this.setState(849);
            this._errHandler.sync(this);
            int _la = this._input.LA(1);
            while (_la == 4) {
                this.setState(845);
                this.match(4);
                this.setState(846);
                this.gstringValue();
                this.setState(851);
                this._errHandler.sync(this);
                _la = this._input.LA(1);
            }
            this.setState(852);
            this.match(3);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RuleVersion(value=0)
    public final GstringValueContext gstringValue() throws RecognitionException {
        GstringValueContext _localctx = new GstringValueContext(this._ctx, this.getState());
        this.enterRule(_localctx, 118, 59);
        try {
            this.setState(856);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 7: 
                case 9: 
                case 10: 
                case 12: 
                case 17: 
                case 41: 
                case 45: 
                case 47: 
                case 130: 
                case 131: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(854);
                    this.gstringPath();
                    return _localctx;
                }
                case 88: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(855);
                    this.closure();
                    return _localctx;
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final GstringPathContext gstringPath() throws RecognitionException {
        GstringPathContext _localctx = new GstringPathContext(this._ctx, this.getState());
        this.enterRule(_localctx, 120, 60);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(858);
            this.identifier();
            this.setState(862);
            this._errHandler.sync(this);
            int _la = this._input.LA(1);
            while (_la == 5) {
                this.setState(859);
                this.match(5);
                this.setState(864);
                this._errHandler.sync(this);
                _la = this._input.LA(1);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final StandardLambdaExpressionContext lambdaExpression() throws RecognitionException {
        StandardLambdaExpressionContext _localctx = new StandardLambdaExpressionContext(this._ctx, this.getState());
        this.enterRule(_localctx, 122, 61);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(865);
            this.lambdaParameters();
            this.setState(866);
            this.nls();
            this.setState(867);
            this.match(83);
            this.setState(868);
            this.nls();
            this.setState(869);
            this.lambdaBody();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final StandardLambdaExpressionContext standardLambdaExpression() throws RecognitionException {
        StandardLambdaExpressionContext _localctx = new StandardLambdaExpressionContext(this._ctx, this.getState());
        this.enterRule(_localctx, 124, 62);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(871);
            this.standardLambdaParameters();
            this.setState(872);
            this.nls();
            this.setState(873);
            this.match(83);
            this.setState(874);
            this.nls();
            this.setState(875);
            this.lambdaBody();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final StandardLambdaParametersContext lambdaParameters() throws RecognitionException {
        StandardLambdaParametersContext _localctx = new StandardLambdaParametersContext(this._ctx, this.getState());
        this.enterRule(_localctx, 126, 63);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(877);
            this.formalParameters();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RuleVersion(value=0)
    public final StandardLambdaParametersContext standardLambdaParameters() throws RecognitionException {
        StandardLambdaParametersContext _localctx = new StandardLambdaParametersContext(this._ctx, this.getState());
        this.enterRule(_localctx, 128, 64);
        try {
            this.setState(881);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 86: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(879);
                    this.formalParameters();
                    return _localctx;
                }
                case 7: 
                case 9: 
                case 10: 
                case 12: 
                case 17: 
                case 41: 
                case 45: 
                case 47: 
                case 130: 
                case 131: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(880);
                    this.variableDeclaratorId();
                    return _localctx;
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final LambdaBodyContext lambdaBody() throws RecognitionException {
        LambdaBodyContext _localctx = new LambdaBodyContext(this._ctx, this.getState());
        this.enterRule(_localctx, 130, 65);
        try {
            this.setState(885);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 80, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(883);
                    this.block();
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(884);
                    this.statementExpression();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final ClosureContext closure() throws RecognitionException {
        ClosureContext _localctx = new ClosureContext(this._ctx, this.getState());
        this.enterRule(_localctx, 132, 66);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(887);
            this.match(88);
            this.setState(896);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 82, this._ctx)) {
                case 1: {
                    this.setState(888);
                    this.nls();
                    this.setState(892);
                    this._errHandler.sync(this);
                    int _la = this._input.LA(1);
                    if ((_la & 0xFFFFFFC0) == 0 && (1L << _la & 0x403BF0410027780L) != 0L || (_la - 90 & 0xFFFFFFC0) == 0 && (1L << _la - 90 & 0xF0000000401L) != 0L) {
                        this.setState(889);
                        this.formalParameterList();
                        this.setState(890);
                        this.nls();
                    }
                    this.setState(894);
                    this.match(83);
                }
            }
            this.setState(899);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 83, this._ctx)) {
                case 1: {
                    this.setState(898);
                    this.sep();
                }
            }
            this.setState(901);
            this.blockStatementsOpt();
            this.setState(902);
            this.match(89);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RuleVersion(value=0)
    public final ClosureOrLambdaExpressionContext closureOrLambdaExpression() throws RecognitionException {
        ClosureOrLambdaExpressionContext _localctx = new ClosureOrLambdaExpressionContext(this._ctx, this.getState());
        this.enterRule(_localctx, 134, 67);
        try {
            this.setState(906);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 88: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(904);
                    this.closure();
                    return _localctx;
                }
                case 86: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(905);
                    this.lambdaExpression();
                    return _localctx;
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final BlockStatementsOptContext blockStatementsOpt() throws RecognitionException {
        BlockStatementsOptContext _localctx = new BlockStatementsOptContext(this._ctx, this.getState());
        this.enterRule(_localctx, 136, 68);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(909);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 85, this._ctx)) {
                case 1: {
                    this.setState(908);
                    this.blockStatements();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final BlockStatementsContext blockStatements() throws RecognitionException {
        BlockStatementsContext _localctx = new BlockStatementsContext(this._ctx, this.getState());
        this.enterRule(_localctx, 138, 69);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(911);
            this.blockStatement();
            this.setState(917);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 86, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(912);
                    this.sep();
                    this.setState(913);
                    this.blockStatement();
                }
                this.setState(919);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 86, this._ctx);
            }
            this.setState(921);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 87, this._ctx)) {
                case 1: {
                    this.setState(920);
                    this.sep();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final AnnotationsOptContext annotationsOpt() throws RecognitionException {
        AnnotationsOptContext _localctx = new AnnotationsOptContext(this._ctx, this.getState());
        this.enterRule(_localctx, 140, 70);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(934);
            this._errHandler.sync(this);
            int _la = this._input.LA(1);
            if (_la == 132) {
                this.setState(923);
                this.annotation();
                this.setState(929);
                this._errHandler.sync(this);
                int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 88, this._ctx);
                while (_alt != 2 && _alt != 0) {
                    if (_alt == 1) {
                        this.setState(924);
                        this.nls();
                        this.setState(925);
                        this.annotation();
                    }
                    this.setState(931);
                    this._errHandler.sync(this);
                    _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 88, this._ctx);
                }
                this.setState(932);
                this.nls();
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final AnnotationContext annotation() throws RecognitionException {
        AnnotationContext _localctx = new AnnotationContext(this._ctx, this.getState());
        this.enterRule(_localctx, 142, 71);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(936);
            this.match(132);
            this.setState(937);
            this.annotationName();
            this.setState(945);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 91, this._ctx)) {
                case 1: {
                    this.setState(938);
                    this.nls();
                    this.setState(939);
                    this.match(86);
                    this.setState(941);
                    this._errHandler.sync(this);
                    switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 90, this._ctx)) {
                        case 1: {
                            this.setState(940);
                            this.elementValues();
                            break;
                        }
                    }
                    this.setState(943);
                    this.rparen();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final ElementValuesContext elementValues() throws RecognitionException {
        ElementValuesContext _localctx = new ElementValuesContext(this._ctx, this.getState());
        this.enterRule(_localctx, 144, 72);
        try {
            this.setState(949);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 92, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(947);
                    this.elementValuePairs();
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(948);
                    this.elementValue();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final AnnotationNameContext annotationName() throws RecognitionException {
        AnnotationNameContext _localctx = new AnnotationNameContext(this._ctx, this.getState());
        this.enterRule(_localctx, 146, 73);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(951);
            this.qualifiedClassName();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final ElementValuePairsContext elementValuePairs() throws RecognitionException {
        ElementValuePairsContext _localctx = new ElementValuePairsContext(this._ctx, this.getState());
        this.enterRule(_localctx, 148, 74);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(953);
            this.elementValuePair();
            this.setState(958);
            this._errHandler.sync(this);
            int _la = this._input.LA(1);
            while (_la == 93) {
                this.setState(954);
                this.match(93);
                this.setState(955);
                this.elementValuePair();
                this.setState(960);
                this._errHandler.sync(this);
                _la = this._input.LA(1);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final ElementValuePairContext elementValuePair() throws RecognitionException {
        ElementValuePairContext _localctx = new ElementValuePairContext(this._ctx, this.getState());
        this.enterRule(_localctx, 150, 75);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(961);
            this.elementValuePairName();
            this.setState(962);
            this.nls();
            this.setState(963);
            this.match(95);
            this.setState(964);
            this.nls();
            this.setState(965);
            this.elementValue();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final ElementValuePairNameContext elementValuePairName() throws RecognitionException {
        ElementValuePairNameContext _localctx = new ElementValuePairNameContext(this._ctx, this.getState());
        this.enterRule(_localctx, 152, 76);
        try {
            this.setState(969);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 94, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(967);
                    this.identifier();
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(968);
                    this.keywords();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final ElementValueContext elementValue() throws RecognitionException {
        ElementValueContext _localctx = new ElementValueContext(this._ctx, this.getState());
        this.enterRule(_localctx, 154, 77);
        try {
            this.setState(974);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 95, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(971);
                    this.elementValueArrayInitializer();
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(972);
                    this.annotation();
                    return _localctx;
                }
                case 3: {
                    this.enterOuterAlt(_localctx, 3);
                    this.setState(973);
                    this.expression(0);
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final ElementValueArrayInitializerContext elementValueArrayInitializer() throws RecognitionException {
        ElementValueArrayInitializerContext _localctx = new ElementValueArrayInitializerContext(this._ctx, this.getState());
        this.enterRule(_localctx, 156, 78);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(976);
            this.match(90);
            this.setState(988);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 98, this._ctx)) {
                case 1: {
                    this.setState(977);
                    this.elementValue();
                    this.setState(982);
                    this._errHandler.sync(this);
                    int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 96, this._ctx);
                    while (_alt != 2 && _alt != 0) {
                        if (_alt == 1) {
                            this.setState(978);
                            this.match(93);
                            this.setState(979);
                            this.elementValue();
                        }
                        this.setState(984);
                        this._errHandler.sync(this);
                        _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 96, this._ctx);
                    }
                    this.setState(986);
                    this._errHandler.sync(this);
                    int _la = this._input.LA(1);
                    if (_la != 93) break;
                    this.setState(985);
                    this.match(93);
                }
            }
            this.setState(990);
            this.match(91);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final BlockContext block() throws RecognitionException {
        BlockContext _localctx = new BlockContext(this._ctx, this.getState());
        this.enterRule(_localctx, 158, 79);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(992);
            this.match(88);
            this.setState(994);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 99, this._ctx)) {
                case 1: {
                    this.setState(993);
                    this.sep();
                }
            }
            this.setState(996);
            this.blockStatementsOpt();
            this.setState(997);
            this.match(89);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final BlockStatementContext blockStatement() throws RecognitionException {
        BlockStatementContext _localctx = new BlockStatementContext(this._ctx, this.getState());
        this.enterRule(_localctx, 160, 80);
        try {
            this.setState(1001);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 100, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(999);
                    this.localVariableDeclaration();
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1000);
                    this.statement();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final LocalVariableDeclarationContext localVariableDeclaration() throws RecognitionException {
        LocalVariableDeclarationContext _localctx = new LocalVariableDeclarationContext(this._ctx, this.getState());
        this.enterRule(_localctx, 162, 81);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1003);
            if (SemanticPredicates.isInvalidLocalVariableDeclaration(this._input)) {
                throw this.createFailedPredicateException(" !SemanticPredicates.isInvalidLocalVariableDeclaration(_input) ");
            }
            this.setState(1004);
            this.variableDeclaration(0);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final VariableDeclarationContext variableDeclaration(int t) throws RecognitionException {
        VariableDeclarationContext _localctx = new VariableDeclarationContext(this._ctx, this.getState(), t);
        this.enterRule(_localctx, 164, 82);
        try {
            this.setState(1023);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 103, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1006);
                    this.modifiers();
                    this.setState(1007);
                    this.nls();
                    this.setState(1018);
                    this._errHandler.sync(this);
                    switch (this._input.LA(1)) {
                        case 7: 
                        case 8: 
                        case 9: 
                        case 10: 
                        case 12: 
                        case 13: 
                        case 17: 
                        case 34: 
                        case 40: 
                        case 41: 
                        case 45: 
                        case 47: 
                        case 58: 
                        case 90: 
                        case 100: 
                        case 130: 
                        case 131: 
                        case 132: {
                            this.setState(1009);
                            this._errHandler.sync(this);
                            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 101, this._ctx)) {
                                case 1: {
                                    this.setState(1008);
                                    this.type();
                                    break;
                                }
                            }
                            this.setState(1011);
                            this.variableDeclarators();
                            return _localctx;
                        }
                        case 86: {
                            this.setState(1012);
                            this.typeNamePairs();
                            this.setState(1013);
                            this.nls();
                            this.setState(1014);
                            this.match(95);
                            this.setState(1015);
                            this.nls();
                            this.setState(1016);
                            this.variableInitializer();
                            return _localctx;
                        }
                    }
                    throw new NoViableAltException(this);
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1020);
                    this.type();
                    this.setState(1021);
                    this.variableDeclarators();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final TypeNamePairsContext typeNamePairs() throws RecognitionException {
        TypeNamePairsContext _localctx = new TypeNamePairsContext(this._ctx, this.getState());
        this.enterRule(_localctx, 166, 83);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1025);
            this.match(86);
            this.setState(1026);
            this.typeNamePair();
            this.setState(1031);
            this._errHandler.sync(this);
            int _la = this._input.LA(1);
            while (_la == 93) {
                this.setState(1027);
                this.match(93);
                this.setState(1028);
                this.typeNamePair();
                this.setState(1033);
                this._errHandler.sync(this);
                _la = this._input.LA(1);
            }
            this.setState(1034);
            this.rparen();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final TypeNamePairContext typeNamePair() throws RecognitionException {
        TypeNamePairContext _localctx = new TypeNamePairContext(this._ctx, this.getState());
        this.enterRule(_localctx, 168, 84);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1037);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 105, this._ctx)) {
                case 1: {
                    this.setState(1036);
                    this.type();
                }
            }
            this.setState(1039);
            this.variableDeclaratorId();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final VariableNamesContext variableNames() throws RecognitionException {
        VariableNamesContext _localctx = new VariableNamesContext(this._ctx, this.getState());
        this.enterRule(_localctx, 170, 85);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1041);
            this.match(86);
            this.setState(1042);
            this.variableDeclaratorId();
            this.setState(1045);
            this._errHandler.sync(this);
            int _la = this._input.LA(1);
            do {
                this.setState(1043);
                this.match(93);
                this.setState(1044);
                this.variableDeclaratorId();
                this.setState(1047);
                this._errHandler.sync(this);
            } while ((_la = this._input.LA(1)) == 93);
            this.setState(1049);
            this.rparen();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RuleVersion(value=0)
    public final ConditionalStatementContext conditionalStatement() throws RecognitionException {
        ConditionalStatementContext _localctx = new ConditionalStatementContext(this._ctx, this.getState());
        this.enterRule(_localctx, 172, 86);
        try {
            this.setState(1053);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 31: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1051);
                    this.ifElseStatement();
                    return _localctx;
                }
                case 51: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1052);
                    this.switchStatement();
                    return _localctx;
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final IfElseStatementContext ifElseStatement() throws RecognitionException {
        IfElseStatementContext _localctx = new IfElseStatementContext(this._ctx, this.getState());
        this.enterRule(_localctx, 174, 87);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1055);
            this.match(31);
            this.setState(1056);
            this.expressionInPar();
            this.setState(1057);
            this.nls();
            this.setState(1058);
            _localctx.tb = this.statement();
            this.setState(1067);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 109, this._ctx)) {
                case 1: {
                    this.setState(1061);
                    this._errHandler.sync(this);
                    switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 108, this._ctx)) {
                        case 1: {
                            this.setState(1059);
                            this.nls();
                            break;
                        }
                        case 2: {
                            this.setState(1060);
                            this.sep();
                            break;
                        }
                    }
                    this.setState(1063);
                    this.match(25);
                    this.setState(1064);
                    this.nls();
                    this.setState(1065);
                    _localctx.fb = this.statement();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final SwitchStatementContext switchStatement() throws RecognitionException {
        SwitchStatementContext _localctx = new SwitchStatementContext(this._ctx, this.getState());
        this.enterRule(_localctx, 176, 88);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1069);
            this.match(51);
            this.setState(1070);
            this.expressionInPar();
            this.setState(1071);
            this.nls();
            this.setState(1072);
            this.match(88);
            this.setState(1073);
            this.nls();
            this.setState(1081);
            this._errHandler.sync(this);
            int _la = this._input.LA(1);
            if (_la == 18 || _la == 23) {
                this.setState(1075);
                this._errHandler.sync(this);
                int _alt = 1;
                do {
                    switch (_alt) {
                        case 1: {
                            this.setState(1074);
                            this.switchBlockStatementGroup();
                            break;
                        }
                        default: {
                            throw new NoViableAltException(this);
                        }
                    }
                    this.setState(1077);
                    this._errHandler.sync(this);
                } while ((_alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 110, this._ctx)) != 2 && _alt != 0);
                this.setState(1079);
                this.nls();
            }
            this.setState(1083);
            this.match(89);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RuleVersion(value=0)
    public final LoopStatementContext loopStatement() throws RecognitionException {
        LoopStatementContext _localctx = new LoopStatementContext(this._ctx, this.getState());
        this.enterRule(_localctx, 178, 89);
        try {
            this.setState(1104);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 30: {
                    _localctx = new ForStmtAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1085);
                    this.match(30);
                    this.setState(1086);
                    this.match(86);
                    this.setState(1087);
                    this.forControl();
                    this.setState(1088);
                    this.rparen();
                    this.setState(1089);
                    this.nls();
                    this.setState(1090);
                    this.statement();
                    return _localctx;
                }
                case 60: {
                    _localctx = new WhileStmtAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1092);
                    this.match(60);
                    this.setState(1093);
                    this.expressionInPar();
                    this.setState(1094);
                    this.nls();
                    this.setState(1095);
                    this.statement();
                    return _localctx;
                }
                case 24: {
                    _localctx = new DoWhileStmtAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 3);
                    this.setState(1097);
                    this.match(24);
                    this.setState(1098);
                    this.nls();
                    this.setState(1099);
                    this.statement();
                    this.setState(1100);
                    this.nls();
                    this.setState(1101);
                    this.match(60);
                    this.setState(1102);
                    this.expressionInPar();
                    return _localctx;
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final ContinueStatementContext continueStatement() throws RecognitionException {
        ContinueStatementContext _localctx = new ContinueStatementContext(this._ctx, this.getState());
        this.enterRule(_localctx, 180, 90);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1106);
            this.match(22);
            this.setState(1108);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 113, this._ctx)) {
                case 1: {
                    this.setState(1107);
                    this.identifier();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final BreakStatementContext breakStatement() throws RecognitionException {
        BreakStatementContext _localctx = new BreakStatementContext(this._ctx, this.getState());
        this.enterRule(_localctx, 182, 91);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1110);
            this.match(16);
            this.setState(1112);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 114, this._ctx)) {
                case 1: {
                    this.setState(1111);
                    this.identifier();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final YieldStatementContext yieldStatement() throws RecognitionException {
        YieldStatementContext _localctx = new YieldStatementContext(this._ctx, this.getState());
        this.enterRule(_localctx, 184, 92);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1114);
            this.match(17);
            this.setState(1115);
            this.expression(0);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final TryCatchStatementContext tryCatchStatement() throws RecognitionException {
        TryCatchStatementContext _localctx = new TryCatchStatementContext(this._ctx, this.getState());
        this.enterRule(_localctx, 186, 93);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1117);
            this.match(57);
            this.setState(1119);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 115, this._ctx)) {
                case 1: {
                    this.setState(1118);
                    this.resources();
                    break;
                }
            }
            this.setState(1121);
            this.nls();
            this.setState(1122);
            this.block();
            this.setState(1128);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 116, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(1123);
                    this.nls();
                    this.setState(1124);
                    this.catchClause();
                }
                this.setState(1130);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 116, this._ctx);
            }
            this.setState(1134);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 117, this._ctx)) {
                case 1: {
                    this.setState(1131);
                    this.nls();
                    this.setState(1132);
                    this.finallyBlock();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final AssertStatementContext assertStatement() throws RecognitionException {
        AssertStatementContext _localctx = new AssertStatementContext(this._ctx, this.getState());
        this.enterRule(_localctx, 188, 94);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1136);
            this.match(15);
            this.setState(1137);
            _localctx.ce = this.expression(0);
            this.setState(1143);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 118, this._ctx)) {
                case 1: {
                    this.setState(1138);
                    this.nls();
                    this.setState(1139);
                    int _la = this._input.LA(1);
                    if (_la != 93 && _la != 101) {
                        this._errHandler.recoverInline(this);
                    } else {
                        if (this._input.LA(1) == -1) {
                            this.matchedEOF = true;
                        }
                        this._errHandler.reportMatch(this);
                        this.consume();
                    }
                    this.setState(1140);
                    this.nls();
                    this.setState(1141);
                    _localctx.me = this.expression(0);
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final StatementContext statement() throws RecognitionException {
        StatementContext _localctx = new StatementContext(this._ctx, this.getState());
        this.enterRule(_localctx, 190, 95);
        try {
            this.setState(1173);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 120, this._ctx)) {
                case 1: {
                    _localctx = new BlockStmtAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1145);
                    this.block();
                    return _localctx;
                }
                case 2: {
                    _localctx = new ConditionalStmtAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1146);
                    this.conditionalStatement();
                    return _localctx;
                }
                case 3: {
                    _localctx = new LoopStmtAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 3);
                    this.setState(1147);
                    this.loopStatement();
                    return _localctx;
                }
                case 4: {
                    _localctx = new TryCatchStmtAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 4);
                    this.setState(1148);
                    this.tryCatchStatement();
                    return _localctx;
                }
                case 5: {
                    _localctx = new SynchronizedStmtAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 5);
                    this.setState(1149);
                    this.match(52);
                    this.setState(1150);
                    this.expressionInPar();
                    this.setState(1151);
                    this.nls();
                    this.setState(1152);
                    this.block();
                    return _localctx;
                }
                case 6: {
                    _localctx = new ReturnStmtAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 6);
                    this.setState(1154);
                    this.match(46);
                    this.setState(1156);
                    this._errHandler.sync(this);
                    switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 119, this._ctx)) {
                        case 1: {
                            this.setState(1155);
                            this.expression(0);
                            return _localctx;
                        }
                    }
                    return _localctx;
                }
                case 7: {
                    _localctx = new ThrowStmtAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 7);
                    this.setState(1158);
                    this.match(54);
                    this.setState(1159);
                    this.expression(0);
                    return _localctx;
                }
                case 8: {
                    _localctx = new BreakStmtAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 8);
                    this.setState(1160);
                    this.breakStatement();
                    return _localctx;
                }
                case 9: {
                    _localctx = new ContinueStmtAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 9);
                    this.setState(1161);
                    this.continueStatement();
                    return _localctx;
                }
                case 10: {
                    _localctx = new YieldStmtAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 10);
                    this.setState(1162);
                    if (this.inSwitchExpressionLevel <= 0) {
                        throw this.createFailedPredicateException(" inSwitchExpressionLevel > 0 ");
                    }
                    this.setState(1163);
                    this.yieldStatement();
                    return _localctx;
                }
                case 11: {
                    _localctx = new LabeledStmtAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 11);
                    this.setState(1164);
                    this.identifier();
                    this.setState(1165);
                    this.match(101);
                    this.setState(1166);
                    this.nls();
                    this.setState(1167);
                    this.statement();
                    return _localctx;
                }
                case 12: {
                    _localctx = new AssertStmtAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 12);
                    this.setState(1169);
                    this.assertStatement();
                    return _localctx;
                }
                case 13: {
                    _localctx = new LocalVariableDeclarationStmtAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 13);
                    this.setState(1170);
                    this.localVariableDeclaration();
                    return _localctx;
                }
                case 14: {
                    _localctx = new ExpressionStmtAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 14);
                    this.setState(1171);
                    this.statementExpression();
                    return _localctx;
                }
                case 15: {
                    _localctx = new EmptyStmtAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 15);
                    this.setState(1172);
                    this.match(92);
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final CatchClauseContext catchClause() throws RecognitionException {
        CatchClauseContext _localctx = new CatchClauseContext(this._ctx, this.getState());
        this.enterRule(_localctx, 192, 96);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1175);
            this.match(19);
            this.setState(1176);
            this.match(86);
            this.setState(1177);
            this.variableModifiersOpt();
            this.setState(1179);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 121, this._ctx)) {
                case 1: {
                    this.setState(1178);
                    this.catchType();
                }
            }
            this.setState(1181);
            this.identifier();
            this.setState(1182);
            this.rparen();
            this.setState(1183);
            this.nls();
            this.setState(1184);
            this.block();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final CatchTypeContext catchType() throws RecognitionException {
        CatchTypeContext _localctx = new CatchTypeContext(this._ctx, this.getState());
        this.enterRule(_localctx, 194, 97);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1186);
            this.qualifiedClassName();
            this.setState(1191);
            this._errHandler.sync(this);
            int _la = this._input.LA(1);
            while (_la == 115) {
                this.setState(1187);
                this.match(115);
                this.setState(1188);
                this.qualifiedClassName();
                this.setState(1193);
                this._errHandler.sync(this);
                _la = this._input.LA(1);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final FinallyBlockContext finallyBlock() throws RecognitionException {
        FinallyBlockContext _localctx = new FinallyBlockContext(this._ctx, this.getState());
        this.enterRule(_localctx, 196, 98);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1194);
            this.match(29);
            this.setState(1195);
            this.nls();
            this.setState(1196);
            this.block();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final ResourcesContext resources() throws RecognitionException {
        ResourcesContext _localctx = new ResourcesContext(this._ctx, this.getState());
        this.enterRule(_localctx, 198, 99);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1198);
            this.match(86);
            this.setState(1199);
            this.nls();
            this.setState(1200);
            this.resourceList();
            this.setState(1202);
            this._errHandler.sync(this);
            int _la = this._input.LA(1);
            if (_la == 92 || _la == 135) {
                this.setState(1201);
                this.sep();
            }
            this.setState(1204);
            this.rparen();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final ResourceListContext resourceList() throws RecognitionException {
        ResourceListContext _localctx = new ResourceListContext(this._ctx, this.getState());
        this.enterRule(_localctx, 200, 100);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1206);
            this.resource();
            this.setState(1212);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 124, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(1207);
                    this.sep();
                    this.setState(1208);
                    this.resource();
                }
                this.setState(1214);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 124, this._ctx);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final ResourceContext resource() throws RecognitionException {
        ResourceContext _localctx = new ResourceContext(this._ctx, this.getState());
        this.enterRule(_localctx, 202, 101);
        try {
            this.setState(1217);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 125, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1215);
                    this.localVariableDeclaration();
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1216);
                    this.expression(0);
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final SwitchBlockStatementGroupContext switchBlockStatementGroup() throws RecognitionException {
        SwitchBlockStatementGroupContext _localctx = new SwitchBlockStatementGroupContext(this._ctx, this.getState());
        this.enterRule(_localctx, 204, 102);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1219);
            this.switchLabel();
            this.setState(1225);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 126, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(1220);
                    this.nls();
                    this.setState(1221);
                    this.switchLabel();
                }
                this.setState(1227);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 126, this._ctx);
            }
            this.setState(1228);
            this.nls();
            this.setState(1229);
            this.blockStatements();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RuleVersion(value=0)
    public final SwitchLabelContext switchLabel() throws RecognitionException {
        SwitchLabelContext _localctx = new SwitchLabelContext(this._ctx, this.getState());
        this.enterRule(_localctx, 206, 103);
        try {
            this.setState(1237);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 18: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1231);
                    this.match(18);
                    this.setState(1232);
                    this.expression(0);
                    this.setState(1233);
                    this.match(101);
                    return _localctx;
                }
                case 23: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1235);
                    this.match(23);
                    this.setState(1236);
                    this.match(101);
                    return _localctx;
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final ForControlContext forControl() throws RecognitionException {
        ForControlContext _localctx = new ForControlContext(this._ctx, this.getState());
        this.enterRule(_localctx, 208, 104);
        try {
            this.setState(1241);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 128, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1239);
                    this.enhancedForControl();
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1240);
                    this.classicalForControl();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final EnhancedForControlContext enhancedForControl() throws RecognitionException {
        EnhancedForControlContext _localctx = new EnhancedForControlContext(this._ctx, this.getState());
        this.enterRule(_localctx, 210, 105);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1243);
            this.variableModifiersOpt();
            this.setState(1245);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 129, this._ctx)) {
                case 1: {
                    this.setState(1244);
                    this.type();
                }
            }
            this.setState(1247);
            this.variableDeclaratorId();
            this.setState(1248);
            int _la = this._input.LA(1);
            if (_la != 9 && _la != 101) {
                this._errHandler.recoverInline(this);
            } else {
                if (this._input.LA(1) == -1) {
                    this.matchedEOF = true;
                }
                this._errHandler.reportMatch(this);
                this.consume();
            }
            this.setState(1249);
            this.expression(0);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final ClassicalForControlContext classicalForControl() throws RecognitionException {
        ClassicalForControlContext _localctx = new ClassicalForControlContext(this._ctx, this.getState());
        this.enterRule(_localctx, 212, 106);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1252);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 130, this._ctx)) {
                case 1: {
                    this.setState(1251);
                    this.forInit();
                    break;
                }
            }
            this.setState(1254);
            this.match(92);
            this.setState(1256);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 131, this._ctx)) {
                case 1: {
                    this.setState(1255);
                    this.expression(0);
                    break;
                }
            }
            this.setState(1258);
            this.match(92);
            this.setState(1260);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 132, this._ctx)) {
                case 1: {
                    this.setState(1259);
                    this.forUpdate();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final ForInitContext forInit() throws RecognitionException {
        ForInitContext _localctx = new ForInitContext(this._ctx, this.getState());
        this.enterRule(_localctx, 214, 107);
        try {
            this.setState(1264);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 133, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1262);
                    this.localVariableDeclaration();
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1263);
                    this.expressionList(false);
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final ForUpdateContext forUpdate() throws RecognitionException {
        ForUpdateContext _localctx = new ForUpdateContext(this._ctx, this.getState());
        this.enterRule(_localctx, 216, 108);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1266);
            this.expressionList(false);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final CastParExpressionContext castParExpression() throws RecognitionException {
        CastParExpressionContext _localctx = new CastParExpressionContext(this._ctx, this.getState());
        this.enterRule(_localctx, 218, 109);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1268);
            this.match(86);
            this.setState(1269);
            this.type();
            this.setState(1270);
            this.rparen();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final ParExpressionContext parExpression() throws RecognitionException {
        ParExpressionContext _localctx = new ParExpressionContext(this._ctx, this.getState());
        this.enterRule(_localctx, 220, 110);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1272);
            this.expressionInPar();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final ExpressionInParContext expressionInPar() throws RecognitionException {
        ExpressionInParContext _localctx = new ExpressionInParContext(this._ctx, this.getState());
        this.enterRule(_localctx, 222, 111);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1274);
            this.match(86);
            this.setState(1275);
            this.enhancedStatementExpression();
            this.setState(1276);
            this.rparen();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final ExpressionListContext expressionList(boolean canSpread) throws RecognitionException {
        ExpressionListContext _localctx = new ExpressionListContext(this._ctx, this.getState(), canSpread);
        this.enterRule(_localctx, 224, 112);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1278);
            this.expressionListElement(_localctx.canSpread);
            this.setState(1285);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 134, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(1279);
                    this.match(93);
                    this.setState(1280);
                    this.nls();
                    this.setState(1281);
                    this.expressionListElement(_localctx.canSpread);
                }
                this.setState(1287);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 134, this._ctx);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final ExpressionListElementContext expressionListElement(boolean canSpread) throws RecognitionException {
        ExpressionListElementContext _localctx = new ExpressionListElementContext(this._ctx, this.getState(), canSpread);
        this.enterRule(_localctx, 226, 113);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1289);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 135, this._ctx)) {
                case 1: {
                    this.setState(1288);
                    this.match(112);
                }
            }
            this.setState(1291);
            this.expression(0);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final EnhancedStatementExpressionContext enhancedStatementExpression() throws RecognitionException {
        EnhancedStatementExpressionContext _localctx = new EnhancedStatementExpressionContext(this._ctx, this.getState());
        this.enterRule(_localctx, 228, 114);
        try {
            this.setState(1295);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 136, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1293);
                    this.statementExpression();
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1294);
                    this.standardLambdaExpression();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final StatementExpressionContext statementExpression() throws RecognitionException {
        StatementExpressionContext _localctx = new StatementExpressionContext(this._ctx, this.getState());
        this.enterRule(_localctx, 230, 115);
        try {
            _localctx = new CommandExprAltContext(_localctx);
            this.enterOuterAlt(_localctx, 1);
            this.setState(1297);
            this.commandExpression();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final PostfixExpressionContext postfixExpression() throws RecognitionException {
        PostfixExpressionContext _localctx = new PostfixExpressionContext(this._ctx, this.getState());
        this.enterRule(_localctx, 232, 116);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1299);
            this.pathExpression();
            this.setState(1301);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 137, this._ctx)) {
                case 1: {
                    this.setState(1300);
                    _localctx.op = this._input.LT(1);
                    int _la = this._input.LA(1);
                    if (_la != 108 && _la != 109) {
                        _localctx.op = this._errHandler.recoverInline(this);
                        return _localctx;
                    }
                    if (this._input.LA(1) == -1) {
                        this.matchedEOF = true;
                    }
                    this._errHandler.reportMatch(this);
                    this.consume();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final SwitchExpressionContext switchExpression() throws RecognitionException {
        SwitchExpressionContext _localctx = new SwitchExpressionContext(this._ctx, this.getState());
        this.enterRule(_localctx, 234, 117);
        ++this.inSwitchExpressionLevel;
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1303);
            this.match(51);
            this.setState(1304);
            this.expressionInPar();
            this.setState(1305);
            this.nls();
            this.setState(1306);
            this.match(88);
            this.setState(1307);
            this.nls();
            this.setState(1311);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 138, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(1308);
                    this.switchBlockStatementExpressionGroup();
                }
                this.setState(1313);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 138, this._ctx);
            }
            this.setState(1314);
            this.nls();
            this.setState(1315);
            this.match(89);
            this._ctx.stop = this._input.LT(-1);
            --this.inSwitchExpressionLevel;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final SwitchBlockStatementExpressionGroupContext switchBlockStatementExpressionGroup() throws RecognitionException {
        SwitchBlockStatementExpressionGroupContext _localctx = new SwitchBlockStatementExpressionGroupContext(this._ctx, this.getState());
        this.enterRule(_localctx, 236, 118);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1320);
            this._errHandler.sync(this);
            int _alt = 1;
            do {
                switch (_alt) {
                    case 1: {
                        this.setState(1317);
                        this.switchExpressionLabel();
                        this.setState(1318);
                        this.nls();
                        break;
                    }
                    default: {
                        throw new NoViableAltException(this);
                    }
                }
                this.setState(1322);
                this._errHandler.sync(this);
            } while ((_alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 139, this._ctx)) != 2 && _alt != 0);
            this.setState(1324);
            this.blockStatements();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final SwitchExpressionLabelContext switchExpressionLabel() throws RecognitionException {
        SwitchExpressionLabelContext _localctx = new SwitchExpressionLabelContext(this._ctx, this.getState());
        this.enterRule(_localctx, 238, 119);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1329);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 18: {
                    this.setState(1326);
                    this.match(18);
                    this.setState(1327);
                    this.expressionList(true);
                    break;
                }
                case 23: {
                    this.setState(1328);
                    this.match(23);
                    break;
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
            this.setState(1331);
            _localctx.ac = this._input.LT(1);
            int _la = this._input.LA(1);
            if (_la != 83 && _la != 101) {
                _localctx.ac = this._errHandler.recoverInline(this);
            } else {
                if (this._input.LA(1) == -1) {
                    this.matchedEOF = true;
                }
                this._errHandler.reportMatch(this);
                this.consume();
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final ExpressionContext expression() throws RecognitionException {
        return this.expression(0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ExpressionContext expression(int _p) throws RecognitionException {
        ExpressionContext _localctx;
        ParserRuleContext _parentctx = this._ctx;
        int _parentState = this.getState();
        ExpressionContext _prevctx = _localctx = new ExpressionContext(this._ctx, _parentState);
        int _startState = 240;
        this.enterRecursionRule(_localctx, 240, 120, _p);
        try {
            int _la;
            this.enterOuterAlt(_localctx, 1);
            this.setState(1351);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 141, this._ctx)) {
                case 1: {
                    _localctx = new CastExprAltContext(_localctx);
                    this._ctx = _localctx;
                    _prevctx = _localctx;
                    this.setState(1334);
                    this.castParExpression();
                    this.setState(1335);
                    this.castOperandExpression();
                    break;
                }
                case 2: {
                    _localctx = new PostfixExprAltContext(_localctx);
                    this._ctx = _localctx;
                    _prevctx = _localctx;
                    this.setState(1337);
                    this.postfixExpression();
                    break;
                }
                case 3: {
                    _localctx = new SwitchExprAltContext(_localctx);
                    this._ctx = _localctx;
                    _prevctx = _localctx;
                    this.setState(1338);
                    this.switchExpression();
                    break;
                }
                case 4: {
                    _localctx = new UnaryNotExprAltContext(_localctx);
                    this._ctx = _localctx;
                    _prevctx = _localctx;
                    this.setState(1339);
                    _la = this._input.LA(1);
                    if (_la != 98 && _la != 99) {
                        this._errHandler.recoverInline(this);
                    } else {
                        if (this._input.LA(1) == -1) {
                            this.matchedEOF = true;
                        }
                        this._errHandler.reportMatch(this);
                        this.consume();
                    }
                    this.setState(1340);
                    this.nls();
                    this.setState(1341);
                    this.expression(18);
                    break;
                }
                case 5: {
                    _localctx = new UnaryAddExprAltContext(_localctx);
                    this._ctx = _localctx;
                    _prevctx = _localctx;
                    this.setState(1343);
                    ((UnaryAddExprAltContext)_localctx).op = this._input.LT(1);
                    _la = this._input.LA(1);
                    if ((_la - 108 & 0xFFFFFFC0) != 0 || (1L << _la - 108 & 0xFL) == 0L) {
                        ((UnaryAddExprAltContext)_localctx).op = this._errHandler.recoverInline(this);
                    } else {
                        if (this._input.LA(1) == -1) {
                            this.matchedEOF = true;
                        }
                        this._errHandler.reportMatch(this);
                        this.consume();
                    }
                    this.setState(1344);
                    this.expression(16);
                    break;
                }
                case 6: {
                    _localctx = new MultipleAssignmentExprAltContext(_localctx);
                    this._ctx = _localctx;
                    _prevctx = _localctx;
                    this.setState(1345);
                    ((MultipleAssignmentExprAltContext)_localctx).left = this.variableNames();
                    this.setState(1346);
                    this.nls();
                    this.setState(1347);
                    ((MultipleAssignmentExprAltContext)_localctx).op = this.match(95);
                    this.setState(1348);
                    this.nls();
                    this.setState(1349);
                    ((MultipleAssignmentExprAltContext)_localctx).right = this.statementExpression();
                }
            }
            this._ctx.stop = this._input.LT(-1);
            this.setState(1463);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 146, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    if (this._parseListeners != null) {
                        this.triggerExitRuleEvent();
                    }
                    _prevctx = _localctx;
                    this.setState(1461);
                    this._errHandler.sync(this);
                    switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 145, this._ctx)) {
                        case 1: {
                            _localctx = new PowerExprAltContext(new ExpressionContext(_parentctx, _parentState));
                            ((PowerExprAltContext)_localctx).left = _prevctx;
                            this.pushNewRecursionContext(_localctx, _startState, 120);
                            this.setState(1353);
                            if (!this.precpred(this._ctx, 17)) {
                                throw this.createFailedPredicateException("precpred(_ctx, 17)");
                            }
                            this.setState(1354);
                            ((PowerExprAltContext)_localctx).op = this.match(78);
                            this.setState(1355);
                            this.nls();
                            this.setState(1356);
                            ((PowerExprAltContext)_localctx).right = this.expression(18);
                            break;
                        }
                        case 2: {
                            _localctx = new MultiplicativeExprAltContext(new ExpressionContext(_parentctx, _parentState));
                            ((MultiplicativeExprAltContext)_localctx).left = _prevctx;
                            this.pushNewRecursionContext(_localctx, _startState, 120);
                            this.setState(1358);
                            if (!this.precpred(this._ctx, 15)) {
                                throw this.createFailedPredicateException("precpred(_ctx, 15)");
                            }
                            this.setState(1359);
                            this.nls();
                            this.setState(1360);
                            ((MultiplicativeExprAltContext)_localctx).op = this._input.LT(1);
                            _la = this._input.LA(1);
                            if ((_la - 112 & 0xFFFFFFC0) != 0 || (1L << _la - 112 & 0x23L) == 0L) {
                                ((MultiplicativeExprAltContext)_localctx).op = this._errHandler.recoverInline(this);
                            } else {
                                if (this._input.LA(1) == -1) {
                                    this.matchedEOF = true;
                                }
                                this._errHandler.reportMatch(this);
                                this.consume();
                            }
                            this.setState(1361);
                            this.nls();
                            this.setState(1362);
                            ((MultiplicativeExprAltContext)_localctx).right = this.expression(16);
                            break;
                        }
                        case 3: {
                            _localctx = new AdditiveExprAltContext(new ExpressionContext(_parentctx, _parentState));
                            ((AdditiveExprAltContext)_localctx).left = _prevctx;
                            this.pushNewRecursionContext(_localctx, _startState, 120);
                            this.setState(1364);
                            if (!this.precpred(this._ctx, 14)) {
                                throw this.createFailedPredicateException("precpred(_ctx, 14)");
                            }
                            this.setState(1365);
                            ((AdditiveExprAltContext)_localctx).op = this._input.LT(1);
                            _la = this._input.LA(1);
                            if (_la != 110 && _la != 111) {
                                ((AdditiveExprAltContext)_localctx).op = this._errHandler.recoverInline(this);
                            } else {
                                if (this._input.LA(1) == -1) {
                                    this.matchedEOF = true;
                                }
                                this._errHandler.reportMatch(this);
                                this.consume();
                            }
                            this.setState(1366);
                            this.nls();
                            this.setState(1367);
                            ((AdditiveExprAltContext)_localctx).right = this.expression(15);
                            break;
                        }
                        case 4: {
                            _localctx = new ShiftExprAltContext(new ExpressionContext(_parentctx, _parentState));
                            ((ShiftExprAltContext)_localctx).left = _prevctx;
                            this.pushNewRecursionContext(_localctx, _startState, 120);
                            this.setState(1369);
                            if (!this.precpred(this._ctx, 13)) {
                                throw this.createFailedPredicateException("precpred(_ctx, 13)");
                            }
                            this.setState(1370);
                            this.nls();
                            this.setState(1381);
                            this._errHandler.sync(this);
                            switch (this._input.LA(1)) {
                                case 96: 
                                case 97: {
                                    this.setState(1378);
                                    this._errHandler.sync(this);
                                    switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 142, this._ctx)) {
                                        case 1: {
                                            this.setState(1371);
                                            ((ShiftExprAltContext)_localctx).dlOp = this.match(97);
                                            this.setState(1372);
                                            this.match(97);
                                            break;
                                        }
                                        case 2: {
                                            this.setState(1373);
                                            ((ShiftExprAltContext)_localctx).tgOp = this.match(96);
                                            this.setState(1374);
                                            this.match(96);
                                            this.setState(1375);
                                            this.match(96);
                                            break;
                                        }
                                        case 3: {
                                            this.setState(1376);
                                            ((ShiftExprAltContext)_localctx).dgOp = this.match(96);
                                            this.setState(1377);
                                            this.match(96);
                                        }
                                    }
                                    break;
                                }
                                case 65: 
                                case 66: 
                                case 67: 
                                case 68: {
                                    this.setState(1380);
                                    ((ShiftExprAltContext)_localctx).rangeOp = this._input.LT(1);
                                    _la = this._input.LA(1);
                                    if ((_la - 65 & 0xFFFFFFC0) != 0 || (1L << _la - 65 & 0xFL) == 0L) {
                                        ((ShiftExprAltContext)_localctx).rangeOp = this._errHandler.recoverInline(this);
                                        break;
                                    }
                                    if (this._input.LA(1) == -1) {
                                        this.matchedEOF = true;
                                    }
                                    this._errHandler.reportMatch(this);
                                    this.consume();
                                    break;
                                }
                                default: {
                                    throw new NoViableAltException(this);
                                }
                            }
                            this.setState(1383);
                            this.nls();
                            this.setState(1384);
                            ((ShiftExprAltContext)_localctx).right = this.expression(14);
                            break;
                        }
                        case 5: {
                            _localctx = new RelationalExprAltContext(new ExpressionContext(_parentctx, _parentState));
                            ((RelationalExprAltContext)_localctx).left = _prevctx;
                            this.pushNewRecursionContext(_localctx, _startState, 120);
                            this.setState(1386);
                            if (!this.precpred(this._ctx, 11)) {
                                throw this.createFailedPredicateException("precpred(_ctx, 11)");
                            }
                            this.setState(1387);
                            this.nls();
                            this.setState(1388);
                            ((RelationalExprAltContext)_localctx).op = this._input.LT(1);
                            _la = this._input.LA(1);
                            if (_la != 9 && ((_la - 85 & 0xFFFFFFC0) != 0 || (1L << _la - 85 & 0xC1801L) == 0L)) {
                                ((RelationalExprAltContext)_localctx).op = this._errHandler.recoverInline(this);
                            } else {
                                if (this._input.LA(1) == -1) {
                                    this.matchedEOF = true;
                                }
                                this._errHandler.reportMatch(this);
                                this.consume();
                            }
                            this.setState(1389);
                            this.nls();
                            this.setState(1390);
                            ((RelationalExprAltContext)_localctx).right = this.expression(12);
                            break;
                        }
                        case 6: {
                            _localctx = new EqualityExprAltContext(new ExpressionContext(_parentctx, _parentState));
                            ((EqualityExprAltContext)_localctx).left = _prevctx;
                            this.pushNewRecursionContext(_localctx, _startState, 120);
                            this.setState(1392);
                            if (!this.precpred(this._ctx, 10)) {
                                throw this.createFailedPredicateException("precpred(_ctx, 10)");
                            }
                            this.setState(1393);
                            this.nls();
                            this.setState(1394);
                            ((EqualityExprAltContext)_localctx).op = this._input.LT(1);
                            _la = this._input.LA(1);
                            if ((_la - 80 & 0xFFFFFFC0) != 0 || (1L << _la - 80 & 0x2400007L) == 0L) {
                                ((EqualityExprAltContext)_localctx).op = this._errHandler.recoverInline(this);
                            } else {
                                if (this._input.LA(1) == -1) {
                                    this.matchedEOF = true;
                                }
                                this._errHandler.reportMatch(this);
                                this.consume();
                            }
                            this.setState(1395);
                            this.nls();
                            this.setState(1396);
                            ((EqualityExprAltContext)_localctx).right = this.expression(11);
                            break;
                        }
                        case 7: {
                            _localctx = new RegexExprAltContext(new ExpressionContext(_parentctx, _parentState));
                            ((RegexExprAltContext)_localctx).left = _prevctx;
                            this.pushNewRecursionContext(_localctx, _startState, 120);
                            this.setState(1398);
                            if (!this.precpred(this._ctx, 9)) {
                                throw this.createFailedPredicateException("precpred(_ctx, 9)");
                            }
                            this.setState(1399);
                            this.nls();
                            this.setState(1400);
                            ((RegexExprAltContext)_localctx).op = this._input.LT(1);
                            _la = this._input.LA(1);
                            if (_la != 76 && _la != 77) {
                                ((RegexExprAltContext)_localctx).op = this._errHandler.recoverInline(this);
                            } else {
                                if (this._input.LA(1) == -1) {
                                    this.matchedEOF = true;
                                }
                                this._errHandler.reportMatch(this);
                                this.consume();
                            }
                            this.setState(1401);
                            this.nls();
                            this.setState(1402);
                            ((RegexExprAltContext)_localctx).right = this.expression(10);
                            break;
                        }
                        case 8: {
                            _localctx = new AndExprAltContext(new ExpressionContext(_parentctx, _parentState));
                            ((AndExprAltContext)_localctx).left = _prevctx;
                            this.pushNewRecursionContext(_localctx, _startState, 120);
                            this.setState(1404);
                            if (!this.precpred(this._ctx, 8)) {
                                throw this.createFailedPredicateException("precpred(_ctx, 8)");
                            }
                            this.setState(1405);
                            this.nls();
                            this.setState(1406);
                            ((AndExprAltContext)_localctx).op = this.match(114);
                            this.setState(1407);
                            this.nls();
                            this.setState(1408);
                            ((AndExprAltContext)_localctx).right = this.expression(9);
                            break;
                        }
                        case 9: {
                            _localctx = new ExclusiveOrExprAltContext(new ExpressionContext(_parentctx, _parentState));
                            ((ExclusiveOrExprAltContext)_localctx).left = _prevctx;
                            this.pushNewRecursionContext(_localctx, _startState, 120);
                            this.setState(1410);
                            if (!this.precpred(this._ctx, 7)) {
                                throw this.createFailedPredicateException("precpred(_ctx, 7)");
                            }
                            this.setState(1411);
                            this.nls();
                            this.setState(1412);
                            ((ExclusiveOrExprAltContext)_localctx).op = this.match(116);
                            this.setState(1413);
                            this.nls();
                            this.setState(1414);
                            ((ExclusiveOrExprAltContext)_localctx).right = this.expression(8);
                            break;
                        }
                        case 10: {
                            _localctx = new InclusiveOrExprAltContext(new ExpressionContext(_parentctx, _parentState));
                            ((InclusiveOrExprAltContext)_localctx).left = _prevctx;
                            this.pushNewRecursionContext(_localctx, _startState, 120);
                            this.setState(1416);
                            if (!this.precpred(this._ctx, 6)) {
                                throw this.createFailedPredicateException("precpred(_ctx, 6)");
                            }
                            this.setState(1417);
                            this.nls();
                            this.setState(1418);
                            ((InclusiveOrExprAltContext)_localctx).op = this.match(115);
                            this.setState(1419);
                            this.nls();
                            this.setState(1420);
                            ((InclusiveOrExprAltContext)_localctx).right = this.expression(7);
                            break;
                        }
                        case 11: {
                            _localctx = new LogicalAndExprAltContext(new ExpressionContext(_parentctx, _parentState));
                            ((LogicalAndExprAltContext)_localctx).left = _prevctx;
                            this.pushNewRecursionContext(_localctx, _startState, 120);
                            this.setState(1422);
                            if (!this.precpred(this._ctx, 5)) {
                                throw this.createFailedPredicateException("precpred(_ctx, 5)");
                            }
                            this.setState(1423);
                            this.nls();
                            this.setState(1424);
                            ((LogicalAndExprAltContext)_localctx).op = this.match(106);
                            this.setState(1425);
                            this.nls();
                            this.setState(1426);
                            ((LogicalAndExprAltContext)_localctx).right = this.expression(6);
                            break;
                        }
                        case 12: {
                            _localctx = new LogicalOrExprAltContext(new ExpressionContext(_parentctx, _parentState));
                            ((LogicalOrExprAltContext)_localctx).left = _prevctx;
                            this.pushNewRecursionContext(_localctx, _startState, 120);
                            this.setState(1428);
                            if (!this.precpred(this._ctx, 4)) {
                                throw this.createFailedPredicateException("precpred(_ctx, 4)");
                            }
                            this.setState(1429);
                            this.nls();
                            this.setState(1430);
                            ((LogicalOrExprAltContext)_localctx).op = this.match(107);
                            this.setState(1431);
                            this.nls();
                            this.setState(1432);
                            ((LogicalOrExprAltContext)_localctx).right = this.expression(5);
                            break;
                        }
                        case 13: {
                            _localctx = new ConditionalExprAltContext(new ExpressionContext(_parentctx, _parentState));
                            ((ConditionalExprAltContext)_localctx).con = _prevctx;
                            this.pushNewRecursionContext(_localctx, _startState, 120);
                            this.setState(1434);
                            if (!this.precpred(this._ctx, 3)) {
                                throw this.createFailedPredicateException("precpred(_ctx, 3)");
                            }
                            this.setState(1435);
                            this.nls();
                            this.setState(1445);
                            this._errHandler.sync(this);
                            switch (this._input.LA(1)) {
                                case 100: {
                                    this.setState(1436);
                                    this.match(100);
                                    this.setState(1437);
                                    this.nls();
                                    this.setState(1438);
                                    ((ConditionalExprAltContext)_localctx).tb = this.expression(0);
                                    this.setState(1439);
                                    this.nls();
                                    this.setState(1440);
                                    this.match(101);
                                    this.setState(1441);
                                    this.nls();
                                    break;
                                }
                                case 73: {
                                    this.setState(1443);
                                    this.match(73);
                                    this.setState(1444);
                                    this.nls();
                                    break;
                                }
                                default: {
                                    throw new NoViableAltException(this);
                                }
                            }
                            this.setState(1447);
                            ((ConditionalExprAltContext)_localctx).fb = this.expression(3);
                            break;
                        }
                        case 14: {
                            _localctx = new RelationalExprAltContext(new ExpressionContext(_parentctx, _parentState));
                            ((RelationalExprAltContext)_localctx).left = _prevctx;
                            this.pushNewRecursionContext(_localctx, _startState, 120);
                            this.setState(1449);
                            if (!this.precpred(this._ctx, 12)) {
                                throw this.createFailedPredicateException("precpred(_ctx, 12)");
                            }
                            this.setState(1450);
                            this.nls();
                            this.setState(1451);
                            ((RelationalExprAltContext)_localctx).op = this._input.LT(1);
                            _la = this._input.LA(1);
                            if (_la != 7 && _la != 35 && _la != 84) {
                                ((RelationalExprAltContext)_localctx).op = this._errHandler.recoverInline(this);
                            } else {
                                if (this._input.LA(1) == -1) {
                                    this.matchedEOF = true;
                                }
                                this._errHandler.reportMatch(this);
                                this.consume();
                            }
                            this.setState(1452);
                            this.nls();
                            this.setState(1453);
                            this.type();
                            break;
                        }
                        case 15: {
                            _localctx = new AssignmentExprAltContext(new ExpressionContext(_parentctx, _parentState));
                            ((AssignmentExprAltContext)_localctx).left = _prevctx;
                            this.pushNewRecursionContext(_localctx, _startState, 120);
                            this.setState(1455);
                            if (!this.precpred(this._ctx, 1)) {
                                throw this.createFailedPredicateException("precpred(_ctx, 1)");
                            }
                            this.setState(1456);
                            this.nls();
                            this.setState(1457);
                            ((AssignmentExprAltContext)_localctx).op = this._input.LT(1);
                            _la = this._input.LA(1);
                            if ((_la - 79 & 0xFFFFFFC0) != 0 || (1L << _la - 79 & 0x7FF8000010001L) == 0L) {
                                ((AssignmentExprAltContext)_localctx).op = this._errHandler.recoverInline(this);
                            } else {
                                if (this._input.LA(1) == -1) {
                                    this.matchedEOF = true;
                                }
                                this._errHandler.reportMatch(this);
                                this.consume();
                            }
                            this.setState(1458);
                            this.nls();
                            this.setState(1459);
                            ((AssignmentExprAltContext)_localctx).right = this.enhancedStatementExpression();
                        }
                    }
                }
                this.setState(1465);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 146, this._ctx);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final ExpressionContext castOperandExpression() throws RecognitionException {
        ExpressionContext _localctx = new ExpressionContext(this._ctx, this.getState());
        this.enterRule(_localctx, 242, 121);
        try {
            this.setState(1476);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 147, this._ctx)) {
                case 1: {
                    _localctx = new CastExprAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1466);
                    this.castParExpression();
                    this.setState(1467);
                    this.castOperandExpression();
                    return _localctx;
                }
                case 2: {
                    _localctx = new PostfixExprAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1469);
                    this.postfixExpression();
                    return _localctx;
                }
                case 3: {
                    _localctx = new UnaryNotExprAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 3);
                    this.setState(1470);
                    int _la = this._input.LA(1);
                    if (_la != 98 && _la != 99) {
                        this._errHandler.recoverInline(this);
                    } else {
                        if (this._input.LA(1) == -1) {
                            this.matchedEOF = true;
                        }
                        this._errHandler.reportMatch(this);
                        this.consume();
                    }
                    this.setState(1471);
                    this.nls();
                    this.setState(1472);
                    this.castOperandExpression();
                    return _localctx;
                }
                case 4: {
                    _localctx = new UnaryAddExprAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 4);
                    this.setState(1474);
                    ((UnaryAddExprAltContext)_localctx).op = this._input.LT(1);
                    int _la = this._input.LA(1);
                    if ((_la - 108 & 0xFFFFFFC0) != 0 || (1L << _la - 108 & 0xFL) == 0L) {
                        ((UnaryAddExprAltContext)_localctx).op = this._errHandler.recoverInline(this);
                    } else {
                        if (this._input.LA(1) == -1) {
                            this.matchedEOF = true;
                        }
                        this._errHandler.reportMatch(this);
                        this.consume();
                    }
                    this.setState(1475);
                    this.castOperandExpression();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final CommandExpressionContext commandExpression() throws RecognitionException {
        CommandExpressionContext _localctx = new CommandExpressionContext(this._ctx, this.getState());
        this.enterRule(_localctx, 244, 122);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1478);
            _localctx.expression = this.expression(0);
            this.setState(1482);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 148, this._ctx)) {
                case 1: {
                    this.setState(1479);
                    if (SemanticPredicates.isFollowingArgumentsOrClosure(_localctx.expression)) {
                        throw this.createFailedPredicateException(" !SemanticPredicates.isFollowingArgumentsOrClosure($expression.ctx) ");
                    }
                    this.setState(1480);
                    this.argumentList();
                    break;
                }
            }
            this.setState(1487);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 149, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(1484);
                    this.commandArgument();
                }
                this.setState(1489);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 149, this._ctx);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final CommandArgumentContext commandArgument() throws RecognitionException {
        CommandArgumentContext _localctx = new CommandArgumentContext(this._ctx, this.getState());
        this.enterRule(_localctx, 246, 123);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1490);
            this.commandPrimary();
            this.setState(1497);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 151, this._ctx)) {
                case 1: {
                    this.setState(1492);
                    this._errHandler.sync(this);
                    int _alt = 1;
                    do {
                        switch (_alt) {
                            case 1: {
                                this.setState(1491);
                                this.pathElement();
                                break;
                            }
                            default: {
                                throw new NoViableAltException(this);
                            }
                        }
                        this.setState(1494);
                        this._errHandler.sync(this);
                        _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 150, this._ctx);
                        if (_alt == 2) return _localctx;
                    } while (_alt != 0);
                    return _localctx;
                }
                case 2: {
                    this.setState(1496);
                    this.argumentList();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final PathExpressionContext pathExpression() throws RecognitionException {
        PathExpressionContext _localctx = new PathExpressionContext(this._ctx, this.getState());
        this.enterRule(_localctx, 248, 124);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1502);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 152, this._ctx)) {
                case 1: {
                    this.setState(1499);
                    this.primary();
                    break;
                }
                case 2: {
                    this.setState(1500);
                    if (this._input.LT(2).getType() != 94) {
                        throw this.createFailedPredicateException(" _input.LT(2).getType() == DOT ");
                    }
                    this.setState(1501);
                    this.match(48);
                }
            }
            this.setState(1509);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 153, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(1504);
                    _localctx.pathElement = this.pathElement();
                    _localctx.t = _localctx.pathElement.t;
                }
                this.setState(1511);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 153, this._ctx);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final PathElementContext pathElement() throws RecognitionException {
        PathElementContext _localctx = new PathElementContext(this._ctx, this.getState());
        this.enterRule(_localctx, 250, 125);
        try {
            this.setState(1548);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 157, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1512);
                    this.nls();
                    this.setState(1537);
                    this._errHandler.sync(this);
                    switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 156, this._ctx)) {
                        case 1: {
                            this.setState(1513);
                            this.match(94);
                            this.setState(1514);
                            this.nls();
                            this.setState(1515);
                            this.match(38);
                            this.setState(1516);
                            this.creator(1);
                            _localctx.t = 6;
                            return _localctx;
                        }
                        case 2: {
                            this.setState(1529);
                            this._errHandler.sync(this);
                            block13 : switch (this._input.LA(1)) {
                                case 69: 
                                case 70: 
                                case 72: 
                                case 94: {
                                    this.setState(1519);
                                    int _la = this._input.LA(1);
                                    if ((_la - 69 & 0xFFFFFFC0) != 0 || (1L << _la - 69 & 0x200000BL) == 0L) {
                                        this._errHandler.recoverInline(this);
                                    } else {
                                        if (this._input.LA(1) == -1) {
                                            this.matchedEOF = true;
                                        }
                                        this._errHandler.reportMatch(this);
                                        this.consume();
                                    }
                                    this.setState(1520);
                                    this.nls();
                                    this.setState(1523);
                                    this._errHandler.sync(this);
                                    switch (this._input.LA(1)) {
                                        case 132: {
                                            this.setState(1521);
                                            this.match(132);
                                            break block13;
                                        }
                                        case 97: {
                                            this.setState(1522);
                                            this.nonWildcardTypeArguments();
                                            break block13;
                                        }
                                        case 1: 
                                        case 2: 
                                        case 7: 
                                        case 8: 
                                        case 9: 
                                        case 10: 
                                        case 11: 
                                        case 12: 
                                        case 13: 
                                        case 14: 
                                        case 15: 
                                        case 16: 
                                        case 17: 
                                        case 18: 
                                        case 19: 
                                        case 20: 
                                        case 21: 
                                        case 22: 
                                        case 23: 
                                        case 24: 
                                        case 25: 
                                        case 26: 
                                        case 27: 
                                        case 28: 
                                        case 29: 
                                        case 30: 
                                        case 31: 
                                        case 32: 
                                        case 33: 
                                        case 34: 
                                        case 35: 
                                        case 36: 
                                        case 37: 
                                        case 38: 
                                        case 39: 
                                        case 40: 
                                        case 41: 
                                        case 42: 
                                        case 43: 
                                        case 44: 
                                        case 45: 
                                        case 46: 
                                        case 47: 
                                        case 48: 
                                        case 49: 
                                        case 50: 
                                        case 51: 
                                        case 52: 
                                        case 53: 
                                        case 54: 
                                        case 55: 
                                        case 56: 
                                        case 57: 
                                        case 58: 
                                        case 59: 
                                        case 60: 
                                        case 63: 
                                        case 64: 
                                        case 86: 
                                        case 130: 
                                        case 131: {
                                            break block13;
                                        }
                                    }
                                    break;
                                }
                                case 74: {
                                    this.setState(1525);
                                    this.match(74);
                                    this.setState(1526);
                                    this.nls();
                                    break;
                                }
                                case 75: {
                                    this.setState(1527);
                                    this.match(75);
                                    this.setState(1528);
                                    this.nls();
                                    break;
                                }
                                default: {
                                    throw new NoViableAltException(this);
                                }
                            }
                            this.setState(1531);
                            this.namePart();
                            _localctx.t = 1;
                            return _localctx;
                        }
                        case 3: {
                            this.setState(1534);
                            this.closureOrLambdaExpression();
                            _localctx.t = 3;
                            return _localctx;
                        }
                    }
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1539);
                    this.arguments();
                    _localctx.t = 2;
                    return _localctx;
                }
                case 3: {
                    this.enterOuterAlt(_localctx, 3);
                    this.setState(1542);
                    this.indexPropertyArgs();
                    _localctx.t = 4;
                    return _localctx;
                }
                case 4: {
                    this.enterOuterAlt(_localctx, 4);
                    this.setState(1545);
                    this.namedPropertyArgs();
                    _localctx.t = 5;
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final NamePartContext namePart() throws RecognitionException {
        NamePartContext _localctx = new NamePartContext(this._ctx, this.getState());
        this.enterRule(_localctx, 252, 126);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1554);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 158, this._ctx)) {
                case 1: {
                    this.setState(1550);
                    this.identifier();
                    return _localctx;
                }
                case 2: {
                    this.setState(1551);
                    this.stringLiteral();
                    return _localctx;
                }
                case 3: {
                    this.setState(1552);
                    this.dynamicMemberName();
                    return _localctx;
                }
                case 4: {
                    this.setState(1553);
                    this.keywords();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RuleVersion(value=0)
    public final DynamicMemberNameContext dynamicMemberName() throws RecognitionException {
        DynamicMemberNameContext _localctx = new DynamicMemberNameContext(this._ctx, this.getState());
        this.enterRule(_localctx, 254, 127);
        try {
            this.setState(1558);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 86: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1556);
                    this.parExpression();
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1557);
                    this.gstring();
                    return _localctx;
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final IndexPropertyArgsContext indexPropertyArgs() throws RecognitionException {
        IndexPropertyArgsContext _localctx = new IndexPropertyArgsContext(this._ctx, this.getState());
        this.enterRule(_localctx, 256, 128);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1560);
            int _la = this._input.LA(1);
            if (_la != 71 && _la != 90) {
                this._errHandler.recoverInline(this);
            } else {
                if (this._input.LA(1) == -1) {
                    this.matchedEOF = true;
                }
                this._errHandler.reportMatch(this);
                this.consume();
            }
            this.setState(1562);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 160, this._ctx)) {
                case 1: {
                    this.setState(1561);
                    this.expressionList(true);
                }
            }
            this.setState(1564);
            this.match(91);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final NamedPropertyArgsContext namedPropertyArgs() throws RecognitionException {
        NamedPropertyArgsContext _localctx = new NamedPropertyArgsContext(this._ctx, this.getState());
        this.enterRule(_localctx, 258, 129);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1566);
            int _la = this._input.LA(1);
            if (_la != 71 && _la != 90) {
                this._errHandler.recoverInline(this);
            } else {
                if (this._input.LA(1) == -1) {
                    this.matchedEOF = true;
                }
                this._errHandler.reportMatch(this);
                this.consume();
            }
            this.setState(1569);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 1: 
                case 2: 
                case 7: 
                case 8: 
                case 9: 
                case 10: 
                case 11: 
                case 12: 
                case 13: 
                case 14: 
                case 15: 
                case 16: 
                case 17: 
                case 18: 
                case 19: 
                case 20: 
                case 21: 
                case 22: 
                case 23: 
                case 24: 
                case 25: 
                case 26: 
                case 27: 
                case 28: 
                case 29: 
                case 30: 
                case 31: 
                case 32: 
                case 33: 
                case 34: 
                case 35: 
                case 36: 
                case 37: 
                case 38: 
                case 39: 
                case 40: 
                case 41: 
                case 42: 
                case 43: 
                case 44: 
                case 45: 
                case 46: 
                case 47: 
                case 48: 
                case 49: 
                case 50: 
                case 51: 
                case 52: 
                case 53: 
                case 54: 
                case 55: 
                case 56: 
                case 57: 
                case 58: 
                case 59: 
                case 60: 
                case 61: 
                case 62: 
                case 63: 
                case 64: 
                case 86: 
                case 90: 
                case 112: 
                case 130: 
                case 131: {
                    this.setState(1567);
                    this.namedPropertyArgList();
                    break;
                }
                case 101: {
                    this.setState(1568);
                    this.match(101);
                    break;
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
            this.setState(1571);
            this.match(91);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final PrimaryContext primary() throws RecognitionException {
        PrimaryContext _localctx = new PrimaryContext(this._ctx, this.getState());
        this.enterRule(_localctx, 260, 130);
        try {
            this.setState(1590);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 163, this._ctx)) {
                case 1: {
                    _localctx = new IdentifierPrmrAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1573);
                    this.identifier();
                    this.setState(1575);
                    this._errHandler.sync(this);
                    switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 162, this._ctx)) {
                        case 1: {
                            this.setState(1574);
                            this.typeArguments();
                            return _localctx;
                        }
                    }
                    return _localctx;
                }
                case 2: {
                    _localctx = new LiteralPrmrAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1577);
                    this.literal();
                    return _localctx;
                }
                case 3: {
                    _localctx = new GstringPrmrAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 3);
                    this.setState(1578);
                    this.gstring();
                    return _localctx;
                }
                case 4: {
                    _localctx = new NewPrmrAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 4);
                    this.setState(1579);
                    this.match(38);
                    this.setState(1580);
                    this.nls();
                    this.setState(1581);
                    this.creator(0);
                    return _localctx;
                }
                case 5: {
                    _localctx = new ThisPrmrAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 5);
                    this.setState(1583);
                    this.match(53);
                    return _localctx;
                }
                case 6: {
                    _localctx = new SuperPrmrAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 6);
                    this.setState(1584);
                    this.match(50);
                    return _localctx;
                }
                case 7: {
                    _localctx = new ParenPrmrAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 7);
                    this.setState(1585);
                    this.parExpression();
                    return _localctx;
                }
                case 8: {
                    _localctx = new ClosureOrLambdaExpressionPrmrAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 8);
                    this.setState(1586);
                    this.closureOrLambdaExpression();
                    return _localctx;
                }
                case 9: {
                    _localctx = new ListPrmrAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 9);
                    this.setState(1587);
                    this.list();
                    return _localctx;
                }
                case 10: {
                    _localctx = new MapPrmrAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 10);
                    this.setState(1588);
                    this.map();
                    return _localctx;
                }
                case 11: {
                    _localctx = new BuiltInTypePrmrAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 11);
                    this.setState(1589);
                    this.builtInType();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final PrimaryContext namedPropertyArgPrimary() throws RecognitionException {
        PrimaryContext _localctx = new PrimaryContext(this._ctx, this.getState());
        this.enterRule(_localctx, 262, 131);
        try {
            this.setState(1598);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 164, this._ctx)) {
                case 1: {
                    _localctx = new IdentifierPrmrAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1592);
                    this.identifier();
                    return _localctx;
                }
                case 2: {
                    _localctx = new LiteralPrmrAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1593);
                    this.literal();
                    return _localctx;
                }
                case 3: {
                    _localctx = new GstringPrmrAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 3);
                    this.setState(1594);
                    this.gstring();
                    return _localctx;
                }
                case 4: {
                    _localctx = new ParenPrmrAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 4);
                    this.setState(1595);
                    this.parExpression();
                    return _localctx;
                }
                case 5: {
                    _localctx = new ListPrmrAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 5);
                    this.setState(1596);
                    this.list();
                    return _localctx;
                }
                case 6: {
                    _localctx = new MapPrmrAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 6);
                    this.setState(1597);
                    this.map();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RuleVersion(value=0)
    public final PrimaryContext namedArgPrimary() throws RecognitionException {
        PrimaryContext _localctx = new PrimaryContext(this._ctx, this.getState());
        this.enterRule(_localctx, 264, 132);
        try {
            this.setState(1603);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 7: 
                case 9: 
                case 10: 
                case 12: 
                case 17: 
                case 41: 
                case 45: 
                case 47: 
                case 130: 
                case 131: {
                    _localctx = new IdentifierPrmrAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1600);
                    this.identifier();
                    return _localctx;
                }
                case 1: 
                case 61: 
                case 62: 
                case 63: 
                case 64: {
                    _localctx = new LiteralPrmrAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1601);
                    this.literal();
                    return _localctx;
                }
                case 2: {
                    _localctx = new GstringPrmrAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 3);
                    this.setState(1602);
                    this.gstring();
                    return _localctx;
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RuleVersion(value=0)
    public final PrimaryContext commandPrimary() throws RecognitionException {
        PrimaryContext _localctx = new PrimaryContext(this._ctx, this.getState());
        this.enterRule(_localctx, 266, 133);
        try {
            this.setState(1608);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 7: 
                case 9: 
                case 10: 
                case 12: 
                case 17: 
                case 41: 
                case 45: 
                case 47: 
                case 130: 
                case 131: {
                    _localctx = new IdentifierPrmrAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1605);
                    this.identifier();
                    return _localctx;
                }
                case 1: 
                case 61: 
                case 62: 
                case 63: 
                case 64: {
                    _localctx = new LiteralPrmrAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1606);
                    this.literal();
                    return _localctx;
                }
                case 2: {
                    _localctx = new GstringPrmrAltContext(_localctx);
                    this.enterOuterAlt(_localctx, 3);
                    this.setState(1607);
                    this.gstring();
                    return _localctx;
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final ListContext list() throws RecognitionException {
        ListContext _localctx = new ListContext(this._ctx, this.getState());
        this.enterRule(_localctx, 268, 134);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1610);
            this.match(90);
            this.setState(1612);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 167, this._ctx)) {
                case 1: {
                    this.setState(1611);
                    this.expressionList(true);
                }
            }
            this.setState(1615);
            this._errHandler.sync(this);
            int _la = this._input.LA(1);
            if (_la == 93) {
                this.setState(1614);
                this.match(93);
            }
            this.setState(1617);
            this.match(91);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final MapContext map() throws RecognitionException {
        MapContext _localctx = new MapContext(this._ctx, this.getState());
        this.enterRule(_localctx, 270, 135);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1619);
            this.match(90);
            this.setState(1625);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 1: 
                case 2: 
                case 7: 
                case 8: 
                case 9: 
                case 10: 
                case 11: 
                case 12: 
                case 13: 
                case 14: 
                case 15: 
                case 16: 
                case 17: 
                case 18: 
                case 19: 
                case 20: 
                case 21: 
                case 22: 
                case 23: 
                case 24: 
                case 25: 
                case 26: 
                case 27: 
                case 28: 
                case 29: 
                case 30: 
                case 31: 
                case 32: 
                case 33: 
                case 34: 
                case 35: 
                case 36: 
                case 37: 
                case 38: 
                case 39: 
                case 40: 
                case 41: 
                case 42: 
                case 43: 
                case 44: 
                case 45: 
                case 46: 
                case 47: 
                case 48: 
                case 49: 
                case 50: 
                case 51: 
                case 52: 
                case 53: 
                case 54: 
                case 55: 
                case 56: 
                case 57: 
                case 58: 
                case 59: 
                case 60: 
                case 61: 
                case 62: 
                case 63: 
                case 64: 
                case 86: 
                case 88: 
                case 90: 
                case 112: 
                case 130: 
                case 131: {
                    this.setState(1620);
                    this.mapEntryList();
                    this.setState(1622);
                    this._errHandler.sync(this);
                    int _la = this._input.LA(1);
                    if (_la != 93) break;
                    this.setState(1621);
                    this.match(93);
                    break;
                }
                case 101: {
                    this.setState(1624);
                    this.match(101);
                    break;
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
            this.setState(1627);
            this.match(91);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final MapEntryListContext mapEntryList() throws RecognitionException {
        MapEntryListContext _localctx = new MapEntryListContext(this._ctx, this.getState());
        this.enterRule(_localctx, 272, 136);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1629);
            this.mapEntry();
            this.setState(1634);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 171, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(1630);
                    this.match(93);
                    this.setState(1631);
                    this.mapEntry();
                }
                this.setState(1636);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 171, this._ctx);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final MapEntryListContext namedPropertyArgList() throws RecognitionException {
        MapEntryListContext _localctx = new MapEntryListContext(this._ctx, this.getState());
        this.enterRule(_localctx, 274, 137);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1637);
            this.namedPropertyArg();
            this.setState(1642);
            this._errHandler.sync(this);
            int _la = this._input.LA(1);
            while (_la == 93) {
                this.setState(1638);
                this.match(93);
                this.setState(1639);
                this.namedPropertyArg();
                this.setState(1644);
                this._errHandler.sync(this);
                _la = this._input.LA(1);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RuleVersion(value=0)
    public final MapEntryContext mapEntry() throws RecognitionException {
        MapEntryContext _localctx = new MapEntryContext(this._ctx, this.getState());
        this.enterRule(_localctx, 276, 138);
        try {
            this.setState(1655);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 1: 
                case 2: 
                case 7: 
                case 8: 
                case 9: 
                case 10: 
                case 11: 
                case 12: 
                case 13: 
                case 14: 
                case 15: 
                case 16: 
                case 17: 
                case 18: 
                case 19: 
                case 20: 
                case 21: 
                case 22: 
                case 23: 
                case 24: 
                case 25: 
                case 26: 
                case 27: 
                case 28: 
                case 29: 
                case 30: 
                case 31: 
                case 32: 
                case 33: 
                case 34: 
                case 35: 
                case 36: 
                case 37: 
                case 38: 
                case 39: 
                case 40: 
                case 41: 
                case 42: 
                case 43: 
                case 44: 
                case 45: 
                case 46: 
                case 47: 
                case 48: 
                case 49: 
                case 50: 
                case 51: 
                case 52: 
                case 53: 
                case 54: 
                case 55: 
                case 56: 
                case 57: 
                case 58: 
                case 59: 
                case 60: 
                case 61: 
                case 62: 
                case 63: 
                case 64: 
                case 86: 
                case 88: 
                case 90: 
                case 130: 
                case 131: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1645);
                    this.mapEntryLabel();
                    this.setState(1646);
                    this.match(101);
                    this.setState(1647);
                    this.nls();
                    this.setState(1648);
                    this.expression(0);
                    return _localctx;
                }
                case 112: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1650);
                    this.match(112);
                    this.setState(1651);
                    this.match(101);
                    this.setState(1652);
                    this.nls();
                    this.setState(1653);
                    this.expression(0);
                    return _localctx;
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RuleVersion(value=0)
    public final MapEntryContext namedPropertyArg() throws RecognitionException {
        MapEntryContext _localctx = new MapEntryContext(this._ctx, this.getState());
        this.enterRule(_localctx, 278, 139);
        try {
            this.setState(1667);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 1: 
                case 2: 
                case 7: 
                case 8: 
                case 9: 
                case 10: 
                case 11: 
                case 12: 
                case 13: 
                case 14: 
                case 15: 
                case 16: 
                case 17: 
                case 18: 
                case 19: 
                case 20: 
                case 21: 
                case 22: 
                case 23: 
                case 24: 
                case 25: 
                case 26: 
                case 27: 
                case 28: 
                case 29: 
                case 30: 
                case 31: 
                case 32: 
                case 33: 
                case 34: 
                case 35: 
                case 36: 
                case 37: 
                case 38: 
                case 39: 
                case 40: 
                case 41: 
                case 42: 
                case 43: 
                case 44: 
                case 45: 
                case 46: 
                case 47: 
                case 48: 
                case 49: 
                case 50: 
                case 51: 
                case 52: 
                case 53: 
                case 54: 
                case 55: 
                case 56: 
                case 57: 
                case 58: 
                case 59: 
                case 60: 
                case 61: 
                case 62: 
                case 63: 
                case 64: 
                case 86: 
                case 90: 
                case 130: 
                case 131: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1657);
                    this.namedPropertyArgLabel();
                    this.setState(1658);
                    this.match(101);
                    this.setState(1659);
                    this.nls();
                    this.setState(1660);
                    this.expression(0);
                    return _localctx;
                }
                case 112: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1662);
                    this.match(112);
                    this.setState(1663);
                    this.match(101);
                    this.setState(1664);
                    this.nls();
                    this.setState(1665);
                    this.expression(0);
                    return _localctx;
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RuleVersion(value=0)
    public final MapEntryContext namedArg() throws RecognitionException {
        MapEntryContext _localctx = new MapEntryContext(this._ctx, this.getState());
        this.enterRule(_localctx, 280, 140);
        try {
            this.setState(1679);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 1: 
                case 2: 
                case 7: 
                case 8: 
                case 9: 
                case 10: 
                case 11: 
                case 12: 
                case 13: 
                case 14: 
                case 15: 
                case 16: 
                case 17: 
                case 18: 
                case 19: 
                case 20: 
                case 21: 
                case 22: 
                case 23: 
                case 24: 
                case 25: 
                case 26: 
                case 27: 
                case 28: 
                case 29: 
                case 30: 
                case 31: 
                case 32: 
                case 33: 
                case 34: 
                case 35: 
                case 36: 
                case 37: 
                case 38: 
                case 39: 
                case 40: 
                case 41: 
                case 42: 
                case 43: 
                case 44: 
                case 45: 
                case 46: 
                case 47: 
                case 48: 
                case 49: 
                case 50: 
                case 51: 
                case 52: 
                case 53: 
                case 54: 
                case 55: 
                case 56: 
                case 57: 
                case 58: 
                case 59: 
                case 60: 
                case 61: 
                case 62: 
                case 63: 
                case 64: 
                case 130: 
                case 131: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1669);
                    this.namedArgLabel();
                    this.setState(1670);
                    this.match(101);
                    this.setState(1671);
                    this.nls();
                    this.setState(1672);
                    this.expression(0);
                    return _localctx;
                }
                case 112: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1674);
                    this.match(112);
                    this.setState(1675);
                    this.match(101);
                    this.setState(1676);
                    this.nls();
                    this.setState(1677);
                    this.expression(0);
                    return _localctx;
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final MapEntryLabelContext mapEntryLabel() throws RecognitionException {
        MapEntryLabelContext _localctx = new MapEntryLabelContext(this._ctx, this.getState());
        this.enterRule(_localctx, 282, 141);
        try {
            this.setState(1683);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 176, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1681);
                    this.keywords();
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1682);
                    this.primary();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final MapEntryLabelContext namedPropertyArgLabel() throws RecognitionException {
        MapEntryLabelContext _localctx = new MapEntryLabelContext(this._ctx, this.getState());
        this.enterRule(_localctx, 284, 142);
        try {
            this.setState(1687);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 177, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1685);
                    this.keywords();
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1686);
                    this.namedPropertyArgPrimary();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final MapEntryLabelContext namedArgLabel() throws RecognitionException {
        MapEntryLabelContext _localctx = new MapEntryLabelContext(this._ctx, this.getState());
        this.enterRule(_localctx, 286, 143);
        try {
            this.setState(1691);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 178, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1689);
                    this.keywords();
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1690);
                    this.namedArgPrimary();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final CreatorContext creator(int t) throws RecognitionException {
        CreatorContext _localctx = new CreatorContext(this._ctx, this.getState(), t);
        this.enterRule(_localctx, 288, 144);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1693);
            this.createdName();
            this.setState(1709);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 182, this._ctx)) {
                case 1: {
                    this.setState(1694);
                    this.nls();
                    this.setState(1695);
                    this.arguments();
                    this.setState(1697);
                    this._errHandler.sync(this);
                    switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 179, this._ctx)) {
                        case 1: {
                            this.setState(1696);
                            this.anonymousInnerClassDeclaration(0);
                            return _localctx;
                        }
                    }
                    return _localctx;
                }
                case 2: {
                    this.setState(1700);
                    this._errHandler.sync(this);
                    int _alt = 1;
                    do {
                        switch (_alt) {
                            case 1: {
                                this.setState(1699);
                                this.dim();
                                break;
                            }
                            default: {
                                throw new NoViableAltException(this);
                            }
                        }
                        this.setState(1702);
                        this._errHandler.sync(this);
                    } while ((_alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 180, this._ctx)) != 2 && _alt != 0);
                    this.setState(1707);
                    this._errHandler.sync(this);
                    switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 181, this._ctx)) {
                        case 1: {
                            this.setState(1704);
                            this.nls();
                            this.setState(1705);
                            this.arrayInitializer();
                        }
                    }
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final DimContext dim() throws RecognitionException {
        DimContext _localctx = new DimContext(this._ctx, this.getState());
        this.enterRule(_localctx, 290, 145);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1711);
            this.annotationsOpt();
            this.setState(1712);
            this.match(90);
            this.setState(1714);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 183, this._ctx)) {
                case 1: {
                    this.setState(1713);
                    this.expression(0);
                }
            }
            this.setState(1716);
            this.match(91);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final ArrayInitializerContext arrayInitializer() throws RecognitionException {
        ArrayInitializerContext _localctx = new ArrayInitializerContext(this._ctx, this.getState());
        this.enterRule(_localctx, 292, 146);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1718);
            this.match(88);
            this.setState(1719);
            this.nls();
            this.setState(1723);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 184, this._ctx)) {
                case 1: {
                    this.setState(1720);
                    this.variableInitializers();
                    this.setState(1721);
                    this.nls();
                }
            }
            this.setState(1725);
            this.match(89);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final AnonymousInnerClassDeclarationContext anonymousInnerClassDeclaration(int t) throws RecognitionException {
        AnonymousInnerClassDeclarationContext _localctx = new AnonymousInnerClassDeclarationContext(this._ctx, this.getState(), t);
        this.enterRule(_localctx, 294, 147);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1727);
            this.classBody(0);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RuleVersion(value=0)
    public final CreatedNameContext createdName() throws RecognitionException {
        CreatedNameContext _localctx = new CreatedNameContext(this._ctx, this.getState());
        this.enterRule(_localctx, 296, 148);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1729);
            this.annotationsOpt();
            this.setState(1735);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
                case 13: {
                    this.setState(1730);
                    this.primitiveType();
                    return _localctx;
                }
                case 7: 
                case 8: 
                case 9: 
                case 10: 
                case 12: 
                case 17: 
                case 41: 
                case 45: 
                case 47: 
                case 130: 
                case 131: {
                    this.setState(1731);
                    this.qualifiedClassName();
                    this.setState(1733);
                    this._errHandler.sync(this);
                    switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 185, this._ctx)) {
                        case 1: {
                            this.setState(1732);
                            this.typeArgumentsOrDiamond();
                        }
                    }
                    return _localctx;
                }
                default: {
                    throw new NoViableAltException(this);
                }
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final NonWildcardTypeArgumentsContext nonWildcardTypeArguments() throws RecognitionException {
        NonWildcardTypeArgumentsContext _localctx = new NonWildcardTypeArgumentsContext(this._ctx, this.getState());
        this.enterRule(_localctx, 298, 149);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1737);
            this.match(97);
            this.setState(1738);
            this.nls();
            this.setState(1739);
            this.typeList();
            this.setState(1740);
            this.nls();
            this.setState(1741);
            this.match(96);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final TypeArgumentsOrDiamondContext typeArgumentsOrDiamond() throws RecognitionException {
        TypeArgumentsOrDiamondContext _localctx = new TypeArgumentsOrDiamondContext(this._ctx, this.getState());
        this.enterRule(_localctx, 300, 150);
        try {
            this.setState(1746);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 187, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1743);
                    this.match(97);
                    this.setState(1744);
                    this.match(96);
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1745);
                    this.typeArguments();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final ArgumentsContext arguments() throws RecognitionException {
        ArgumentsContext _localctx = new ArgumentsContext(this._ctx, this.getState());
        this.enterRule(_localctx, 302, 151);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1748);
            this.match(86);
            this.setState(1750);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 188, this._ctx)) {
                case 1: {
                    this.setState(1749);
                    this.enhancedArgumentListInPar();
                }
            }
            this.setState(1753);
            this._errHandler.sync(this);
            int _la = this._input.LA(1);
            if (_la == 93) {
                this.setState(1752);
                this.match(93);
            }
            this.setState(1755);
            this.rparen();
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final EnhancedArgumentListInParContext argumentList() throws RecognitionException {
        EnhancedArgumentListInParContext _localctx = new EnhancedArgumentListInParContext(this._ctx, this.getState());
        this.enterRule(_localctx, 304, 152);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1757);
            this.firstArgumentListElement();
            this.setState(1764);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 190, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(1758);
                    this.match(93);
                    this.setState(1759);
                    this.nls();
                    this.setState(1760);
                    this.argumentListElement();
                }
                this.setState(1766);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 190, this._ctx);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final EnhancedArgumentListInParContext enhancedArgumentListInPar() throws RecognitionException {
        EnhancedArgumentListInParContext _localctx = new EnhancedArgumentListInParContext(this._ctx, this.getState());
        this.enterRule(_localctx, 306, 153);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1767);
            this.enhancedArgumentListElement();
            this.setState(1774);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 191, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(1768);
                    this.match(93);
                    this.setState(1769);
                    this.nls();
                    this.setState(1770);
                    this.enhancedArgumentListElement();
                }
                this.setState(1776);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 191, this._ctx);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final EnhancedArgumentListElementContext firstArgumentListElement() throws RecognitionException {
        EnhancedArgumentListElementContext _localctx = new EnhancedArgumentListElementContext(this._ctx, this.getState());
        this.enterRule(_localctx, 308, 154);
        try {
            this.setState(1779);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 192, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1777);
                    this.expressionListElement(true);
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1778);
                    this.namedArg();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final EnhancedArgumentListElementContext argumentListElement() throws RecognitionException {
        EnhancedArgumentListElementContext _localctx = new EnhancedArgumentListElementContext(this._ctx, this.getState());
        this.enterRule(_localctx, 310, 155);
        try {
            this.setState(1783);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 193, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1781);
                    this.expressionListElement(true);
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1782);
                    this.namedPropertyArg();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RuleVersion(value=0)
    public final EnhancedArgumentListElementContext enhancedArgumentListElement() throws RecognitionException {
        EnhancedArgumentListElementContext _localctx = new EnhancedArgumentListElementContext(this._ctx, this.getState());
        this.enterRule(_localctx, 312, 156);
        try {
            this.setState(1788);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 194, this._ctx)) {
                case 1: {
                    this.enterOuterAlt(_localctx, 1);
                    this.setState(1785);
                    this.expressionListElement(true);
                    return _localctx;
                }
                case 2: {
                    this.enterOuterAlt(_localctx, 2);
                    this.setState(1786);
                    this.standardLambdaExpression();
                    return _localctx;
                }
                case 3: {
                    this.enterOuterAlt(_localctx, 3);
                    this.setState(1787);
                    this.namedPropertyArg();
                    return _localctx;
                }
            }
            return _localctx;
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
            return _localctx;
        }
        finally {
            this.exitRule();
        }
    }

    @RuleVersion(value=0)
    public final StringLiteralContext stringLiteral() throws RecognitionException {
        StringLiteralContext _localctx = new StringLiteralContext(this._ctx, this.getState());
        this.enterRule(_localctx, 314, 157);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1790);
            this.match(1);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final ClassNameContext className() throws RecognitionException {
        ClassNameContext _localctx = new ClassNameContext(this._ctx, this.getState());
        this.enterRule(_localctx, 316, 158);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1792);
            this.match(130);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final IdentifierContext identifier() throws RecognitionException {
        IdentifierContext _localctx = new IdentifierContext(this._ctx, this.getState());
        this.enterRule(_localctx, 318, 159);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1794);
            int _la = this._input.LA(1);
            if (((_la & 0xFFFFFFC0) != 0 || (1L << _la & 0xA20000021680L) == 0L) && _la != 130 && _la != 131) {
                this._errHandler.recoverInline(this);
            } else {
                if (this._input.LA(1) == -1) {
                    this.matchedEOF = true;
                }
                this._errHandler.reportMatch(this);
                this.consume();
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final BuiltInTypeContext builtInType() throws RecognitionException {
        BuiltInTypeContext _localctx = new BuiltInTypeContext(this._ctx, this.getState());
        this.enterRule(_localctx, 320, 160);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1796);
            int _la = this._input.LA(1);
            if (_la != 13 && _la != 58) {
                this._errHandler.recoverInline(this);
            } else {
                if (this._input.LA(1) == -1) {
                    this.matchedEOF = true;
                }
                this._errHandler.reportMatch(this);
                this.consume();
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final KeywordsContext keywords() throws RecognitionException {
        KeywordsContext _localctx = new KeywordsContext(this._ctx, this.getState());
        this.enterRule(_localctx, 322, 161);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1798);
            int _la = this._input.LA(1);
            if ((_la - 7 & 0xFFFFFFC0) != 0 || (1L << _la - 7 & 0x33FFFFFFFFFFFFFL) == 0L) {
                this._errHandler.recoverInline(this);
            } else {
                if (this._input.LA(1) == -1) {
                    this.matchedEOF = true;
                }
                this._errHandler.reportMatch(this);
                this.consume();
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final RparenContext rparen() throws RecognitionException {
        RparenContext _localctx = new RparenContext(this._ctx, this.getState());
        this.enterRule(_localctx, 324, 162);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1800);
            this.match(87);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @RuleVersion(value=0)
    public final NlsContext nls() throws RecognitionException {
        NlsContext _localctx = new NlsContext(this._ctx, this.getState());
        this.enterRule(_localctx, 326, 163);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1805);
            this._errHandler.sync(this);
            int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 195, this._ctx);
            while (_alt != 2 && _alt != 0) {
                if (_alt == 1) {
                    this.setState(1802);
                    this.match(135);
                }
                this.setState(1807);
                this._errHandler.sync(this);
                _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 195, this._ctx);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RuleVersion(value=0)
    public final SepContext sep() throws RecognitionException {
        SepContext _localctx = new SepContext(this._ctx, this.getState());
        this.enterRule(_localctx, 328, 164);
        try {
            this.enterOuterAlt(_localctx, 1);
            this.setState(1809);
            this._errHandler.sync(this);
            int _alt = 1;
            do {
                switch (_alt) {
                    case 1: {
                        this.setState(1808);
                        int _la = this._input.LA(1);
                        if (_la != 92 && _la != 135) {
                            this._errHandler.recoverInline(this);
                            break;
                        }
                        if (this._input.LA(1) == -1) {
                            this.matchedEOF = true;
                        }
                        this._errHandler.reportMatch(this);
                        this.consume();
                        break;
                    }
                    default: {
                        throw new NoViableAltException(this);
                    }
                }
                this.setState(1811);
                this._errHandler.sync(this);
            } while ((_alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 196, this._ctx)) != 2 && _alt != 0);
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            this._errHandler.reportError(this, re);
            this._errHandler.recover(this, re);
        }
        finally {
            this.exitRule();
        }
        return _localctx;
    }

    @Override
    public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
        switch (ruleIndex) {
            case 2: {
                return this.scriptStatement_sempred((ScriptStatementContext)_localctx, predIndex);
            }
            case 20: {
                return this.classBody_sempred((ClassBodyContext)_localctx, predIndex);
            }
            case 81: {
                return this.localVariableDeclaration_sempred((LocalVariableDeclarationContext)_localctx, predIndex);
            }
            case 95: {
                return this.statement_sempred((StatementContext)_localctx, predIndex);
            }
            case 120: {
                return this.expression_sempred((ExpressionContext)_localctx, predIndex);
            }
            case 122: {
                return this.commandExpression_sempred((CommandExpressionContext)_localctx, predIndex);
            }
            case 124: {
                return this.pathExpression_sempred((PathExpressionContext)_localctx, predIndex);
            }
        }
        return true;
    }

    private boolean scriptStatement_sempred(ScriptStatementContext _localctx, int predIndex) {
        switch (predIndex) {
            case 0: {
                return !SemanticPredicates.isInvalidMethodDeclaration(this._input);
            }
        }
        return true;
    }

    private boolean classBody_sempred(ClassBodyContext _localctx, int predIndex) {
        switch (predIndex) {
            case 1: {
                return 2 == _localctx.t;
            }
        }
        return true;
    }

    private boolean localVariableDeclaration_sempred(LocalVariableDeclarationContext _localctx, int predIndex) {
        switch (predIndex) {
            case 2: {
                return !SemanticPredicates.isInvalidLocalVariableDeclaration(this._input);
            }
        }
        return true;
    }

    private boolean statement_sempred(StatementContext _localctx, int predIndex) {
        switch (predIndex) {
            case 3: {
                return this.inSwitchExpressionLevel > 0;
            }
        }
        return true;
    }

    private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
        switch (predIndex) {
            case 4: {
                return this.precpred(this._ctx, 17);
            }
            case 5: {
                return this.precpred(this._ctx, 15);
            }
            case 6: {
                return this.precpred(this._ctx, 14);
            }
            case 7: {
                return this.precpred(this._ctx, 13);
            }
            case 8: {
                return this.precpred(this._ctx, 11);
            }
            case 9: {
                return this.precpred(this._ctx, 10);
            }
            case 10: {
                return this.precpred(this._ctx, 9);
            }
            case 11: {
                return this.precpred(this._ctx, 8);
            }
            case 12: {
                return this.precpred(this._ctx, 7);
            }
            case 13: {
                return this.precpred(this._ctx, 6);
            }
            case 14: {
                return this.precpred(this._ctx, 5);
            }
            case 15: {
                return this.precpred(this._ctx, 4);
            }
            case 16: {
                return this.precpred(this._ctx, 3);
            }
            case 17: {
                return this.precpred(this._ctx, 12);
            }
            case 18: {
                return this.precpred(this._ctx, 1);
            }
        }
        return true;
    }

    private boolean commandExpression_sempred(CommandExpressionContext _localctx, int predIndex) {
        switch (predIndex) {
            case 19: {
                return !SemanticPredicates.isFollowingArgumentsOrClosure(_localctx.expression);
            }
        }
        return true;
    }

    private boolean pathExpression_sempred(PathExpressionContext _localctx, int predIndex) {
        switch (predIndex) {
            case 20: {
                return this._input.LT(2).getType() == 94;
            }
        }
        return true;
    }

    static {
        for (int i = 0; i < tokenNames.length; ++i) {
            GroovyParser.tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                GroovyParser.tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }
            if (tokenNames[i] != null) continue;
            GroovyParser.tokenNames[i] = "<INVALID>";
        }
        _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    }

    public static class CompilationUnitContext
    extends GroovyParserRuleContext {
        public NlsContext nls() {
            return this.getRuleContext(NlsContext.class, 0);
        }

        public TerminalNode EOF() {
            return this.getToken(-1, 0);
        }

        public PackageDeclarationContext packageDeclaration() {
            return this.getRuleContext(PackageDeclarationContext.class, 0);
        }

        public ScriptStatementsContext scriptStatements() {
            return this.getRuleContext(ScriptStatementsContext.class, 0);
        }

        public SepContext sep() {
            return this.getRuleContext(SepContext.class, 0);
        }

        public CompilationUnitContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 0;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitCompilationUnit(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class NlsContext
    extends GroovyParserRuleContext {
        public List<? extends TerminalNode> NL() {
            return this.getTokens(135);
        }

        public TerminalNode NL(int i) {
            return this.getToken(135, i);
        }

        public NlsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 163;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitNls(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class PackageDeclarationContext
    extends GroovyParserRuleContext {
        public AnnotationsOptContext annotationsOpt() {
            return this.getRuleContext(AnnotationsOptContext.class, 0);
        }

        public TerminalNode PACKAGE() {
            return this.getToken(40, 0);
        }

        public QualifiedNameContext qualifiedName() {
            return this.getRuleContext(QualifiedNameContext.class, 0);
        }

        public PackageDeclarationContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 3;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitPackageDeclaration(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class SepContext
    extends GroovyParserRuleContext {
        public List<? extends TerminalNode> NL() {
            return this.getTokens(135);
        }

        public TerminalNode NL(int i) {
            return this.getToken(135, i);
        }

        public List<? extends TerminalNode> SEMI() {
            return this.getTokens(92);
        }

        public TerminalNode SEMI(int i) {
            return this.getToken(92, i);
        }

        public SepContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 164;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitSep(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ScriptStatementsContext
    extends GroovyParserRuleContext {
        public List<? extends ScriptStatementContext> scriptStatement() {
            return this.getRuleContexts(ScriptStatementContext.class);
        }

        public ScriptStatementContext scriptStatement(int i) {
            return this.getRuleContext(ScriptStatementContext.class, i);
        }

        public List<? extends SepContext> sep() {
            return this.getRuleContexts(SepContext.class);
        }

        public SepContext sep(int i) {
            return this.getRuleContext(SepContext.class, i);
        }

        public ScriptStatementsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 1;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitScriptStatements(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ScriptStatementContext
    extends GroovyParserRuleContext {
        public ImportDeclarationContext importDeclaration() {
            return this.getRuleContext(ImportDeclarationContext.class, 0);
        }

        public TypeDeclarationContext typeDeclaration() {
            return this.getRuleContext(TypeDeclarationContext.class, 0);
        }

        public MethodDeclarationContext methodDeclaration() {
            return this.getRuleContext(MethodDeclarationContext.class, 0);
        }

        public StatementContext statement() {
            return this.getRuleContext(StatementContext.class, 0);
        }

        public ScriptStatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 2;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitScriptStatement(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ImportDeclarationContext
    extends GroovyParserRuleContext {
        public IdentifierContext alias;

        public AnnotationsOptContext annotationsOpt() {
            return this.getRuleContext(AnnotationsOptContext.class, 0);
        }

        public TerminalNode IMPORT() {
            return this.getToken(34, 0);
        }

        public QualifiedNameContext qualifiedName() {
            return this.getRuleContext(QualifiedNameContext.class, 0);
        }

        public TerminalNode STATIC() {
            return this.getToken(48, 0);
        }

        public TerminalNode DOT() {
            return this.getToken(94, 0);
        }

        public TerminalNode MUL() {
            return this.getToken(112, 0);
        }

        public TerminalNode AS() {
            return this.getToken(7, 0);
        }

        public IdentifierContext identifier() {
            return this.getRuleContext(IdentifierContext.class, 0);
        }

        public ImportDeclarationContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 4;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitImportDeclaration(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class TypeDeclarationContext
    extends GroovyParserRuleContext {
        public ClassOrInterfaceModifiersOptContext classOrInterfaceModifiersOpt() {
            return this.getRuleContext(ClassOrInterfaceModifiersOptContext.class, 0);
        }

        public ClassDeclarationContext classDeclaration() {
            return this.getRuleContext(ClassDeclarationContext.class, 0);
        }

        public TypeDeclarationContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 5;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitTypeDeclaration(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class MethodDeclarationContext
    extends GroovyParserRuleContext {
        public int t;
        public int ct;

        public ModifiersOptContext modifiersOpt() {
            return this.getRuleContext(ModifiersOptContext.class, 0);
        }

        public MethodNameContext methodName() {
            return this.getRuleContext(MethodNameContext.class, 0);
        }

        public FormalParametersContext formalParameters() {
            return this.getRuleContext(FormalParametersContext.class, 0);
        }

        public TypeParametersContext typeParameters() {
            return this.getRuleContext(TypeParametersContext.class, 0);
        }

        public ReturnTypeContext returnType() {
            return this.getRuleContext(ReturnTypeContext.class, 0);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public TerminalNode DEFAULT() {
            return this.getToken(23, 0);
        }

        public ElementValueContext elementValue() {
            return this.getRuleContext(ElementValueContext.class, 0);
        }

        public TerminalNode THROWS() {
            return this.getToken(55, 0);
        }

        public QualifiedClassNameListContext qualifiedClassNameList() {
            return this.getRuleContext(QualifiedClassNameListContext.class, 0);
        }

        public MethodBodyContext methodBody() {
            return this.getRuleContext(MethodBodyContext.class, 0);
        }

        public MethodDeclarationContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public MethodDeclarationContext(ParserRuleContext parent, int invokingState, int t, int ct) {
            super(parent, invokingState);
            this.t = t;
            this.ct = ct;
        }

        @Override
        public int getRuleIndex() {
            return 25;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitMethodDeclaration(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class StatementContext
    extends GroovyParserRuleContext {
        public StatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 95;
        }

        public StatementContext() {
        }

        public void copyFrom(StatementContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class AnnotationsOptContext
    extends GroovyParserRuleContext {
        public List<? extends AnnotationContext> annotation() {
            return this.getRuleContexts(AnnotationContext.class);
        }

        public AnnotationContext annotation(int i) {
            return this.getRuleContext(AnnotationContext.class, i);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public AnnotationsOptContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 70;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitAnnotationsOpt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class QualifiedNameContext
    extends GroovyParserRuleContext {
        public List<? extends QualifiedNameElementContext> qualifiedNameElement() {
            return this.getRuleContexts(QualifiedNameElementContext.class);
        }

        public QualifiedNameElementContext qualifiedNameElement(int i) {
            return this.getRuleContext(QualifiedNameElementContext.class, i);
        }

        public List<? extends TerminalNode> DOT() {
            return this.getTokens(94);
        }

        public TerminalNode DOT(int i) {
            return this.getToken(94, i);
        }

        public QualifiedNameContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 52;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitQualifiedName(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class IdentifierContext
    extends GroovyParserRuleContext {
        public TerminalNode Identifier() {
            return this.getToken(131, 0);
        }

        public TerminalNode CapitalizedIdentifier() {
            return this.getToken(130, 0);
        }

        public TerminalNode VAR() {
            return this.getToken(12, 0);
        }

        public TerminalNode IN() {
            return this.getToken(9, 0);
        }

        public TerminalNode TRAIT() {
            return this.getToken(10, 0);
        }

        public TerminalNode AS() {
            return this.getToken(7, 0);
        }

        public TerminalNode YIELD() {
            return this.getToken(17, 0);
        }

        public TerminalNode PERMITS() {
            return this.getToken(41, 0);
        }

        public TerminalNode SEALED() {
            return this.getToken(47, 0);
        }

        public TerminalNode RECORD() {
            return this.getToken(45, 0);
        }

        public IdentifierContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 159;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitIdentifier(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ClassOrInterfaceModifiersOptContext
    extends GroovyParserRuleContext {
        public ClassOrInterfaceModifiersContext classOrInterfaceModifiers() {
            return this.getRuleContext(ClassOrInterfaceModifiersContext.class, 0);
        }

        public List<? extends TerminalNode> NL() {
            return this.getTokens(135);
        }

        public TerminalNode NL(int i) {
            return this.getToken(135, i);
        }

        public ClassOrInterfaceModifiersOptContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 9;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitClassOrInterfaceModifiersOpt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ClassDeclarationContext
    extends GroovyParserRuleContext {
        public int t;
        public TypeListContext scs;
        public TypeListContext is;
        public TypeListContext ps;

        public IdentifierContext identifier() {
            return this.getRuleContext(IdentifierContext.class, 0);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public ClassBodyContext classBody() {
            return this.getRuleContext(ClassBodyContext.class, 0);
        }

        public TerminalNode CLASS() {
            return this.getToken(20, 0);
        }

        public TerminalNode INTERFACE() {
            return this.getToken(36, 0);
        }

        public TerminalNode ENUM() {
            return this.getToken(26, 0);
        }

        public TerminalNode AT() {
            return this.getToken(132, 0);
        }

        public TerminalNode TRAIT() {
            return this.getToken(10, 0);
        }

        public TerminalNode RECORD() {
            return this.getToken(45, 0);
        }

        public TypeParametersContext typeParameters() {
            return this.getRuleContext(TypeParametersContext.class, 0);
        }

        public FormalParametersContext formalParameters() {
            return this.getRuleContext(FormalParametersContext.class, 0);
        }

        public TerminalNode EXTENDS() {
            return this.getToken(27, 0);
        }

        public TerminalNode IMPLEMENTS() {
            return this.getToken(33, 0);
        }

        public TerminalNode PERMITS() {
            return this.getToken(41, 0);
        }

        public List<? extends TypeListContext> typeList() {
            return this.getRuleContexts(TypeListContext.class);
        }

        public TypeListContext typeList(int i) {
            return this.getRuleContext(TypeListContext.class, i);
        }

        public ClassDeclarationContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 19;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitClassDeclaration(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ModifierContext
    extends GroovyParserRuleContext {
        public Token m;

        public ClassOrInterfaceModifierContext classOrInterfaceModifier() {
            return this.getRuleContext(ClassOrInterfaceModifierContext.class, 0);
        }

        public TerminalNode NATIVE() {
            return this.getToken(37, 0);
        }

        public TerminalNode SYNCHRONIZED() {
            return this.getToken(52, 0);
        }

        public TerminalNode TRANSIENT() {
            return this.getToken(56, 0);
        }

        public TerminalNode VOLATILE() {
            return this.getToken(59, 0);
        }

        public TerminalNode DEF() {
            return this.getToken(8, 0);
        }

        public TerminalNode VAR() {
            return this.getToken(12, 0);
        }

        public ModifierContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 6;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitModifier(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ClassOrInterfaceModifierContext
    extends GroovyParserRuleContext {
        public Token m;

        public AnnotationContext annotation() {
            return this.getRuleContext(AnnotationContext.class, 0);
        }

        public TerminalNode PUBLIC() {
            return this.getToken(44, 0);
        }

        public TerminalNode PROTECTED() {
            return this.getToken(43, 0);
        }

        public TerminalNode PRIVATE() {
            return this.getToken(42, 0);
        }

        public TerminalNode STATIC() {
            return this.getToken(48, 0);
        }

        public TerminalNode ABSTRACT() {
            return this.getToken(14, 0);
        }

        public TerminalNode SEALED() {
            return this.getToken(47, 0);
        }

        public TerminalNode NON_SEALED() {
            return this.getToken(39, 0);
        }

        public TerminalNode FINAL() {
            return this.getToken(28, 0);
        }

        public TerminalNode STRICTFP() {
            return this.getToken(49, 0);
        }

        public TerminalNode DEFAULT() {
            return this.getToken(23, 0);
        }

        public ClassOrInterfaceModifierContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 11;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitClassOrInterfaceModifier(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ModifiersOptContext
    extends GroovyParserRuleContext {
        public ModifiersContext modifiers() {
            return this.getRuleContext(ModifiersContext.class, 0);
        }

        public NlsContext nls() {
            return this.getRuleContext(NlsContext.class, 0);
        }

        public ModifiersOptContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 7;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitModifiersOpt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ModifiersContext
    extends GroovyParserRuleContext {
        public List<? extends ModifierContext> modifier() {
            return this.getRuleContexts(ModifierContext.class);
        }

        public ModifierContext modifier(int i) {
            return this.getRuleContext(ModifierContext.class, i);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public ModifiersContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 8;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitModifiers(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ClassOrInterfaceModifiersContext
    extends GroovyParserRuleContext {
        public List<? extends ClassOrInterfaceModifierContext> classOrInterfaceModifier() {
            return this.getRuleContexts(ClassOrInterfaceModifierContext.class);
        }

        public ClassOrInterfaceModifierContext classOrInterfaceModifier(int i) {
            return this.getRuleContext(ClassOrInterfaceModifierContext.class, i);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public ClassOrInterfaceModifiersContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 10;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitClassOrInterfaceModifiers(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class AnnotationContext
    extends GroovyParserRuleContext {
        public TerminalNode AT() {
            return this.getToken(132, 0);
        }

        public AnnotationNameContext annotationName() {
            return this.getRuleContext(AnnotationNameContext.class, 0);
        }

        public NlsContext nls() {
            return this.getRuleContext(NlsContext.class, 0);
        }

        public TerminalNode LPAREN() {
            return this.getToken(86, 0);
        }

        public RparenContext rparen() {
            return this.getRuleContext(RparenContext.class, 0);
        }

        public ElementValuesContext elementValues() {
            return this.getRuleContext(ElementValuesContext.class, 0);
        }

        public AnnotationContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 71;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitAnnotation(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class VariableModifierContext
    extends GroovyParserRuleContext {
        public Token m;

        public AnnotationContext annotation() {
            return this.getRuleContext(AnnotationContext.class, 0);
        }

        public TerminalNode FINAL() {
            return this.getToken(28, 0);
        }

        public TerminalNode DEF() {
            return this.getToken(8, 0);
        }

        public TerminalNode VAR() {
            return this.getToken(12, 0);
        }

        public TerminalNode PUBLIC() {
            return this.getToken(44, 0);
        }

        public TerminalNode PROTECTED() {
            return this.getToken(43, 0);
        }

        public TerminalNode PRIVATE() {
            return this.getToken(42, 0);
        }

        public TerminalNode STATIC() {
            return this.getToken(48, 0);
        }

        public TerminalNode ABSTRACT() {
            return this.getToken(14, 0);
        }

        public TerminalNode STRICTFP() {
            return this.getToken(49, 0);
        }

        public VariableModifierContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 12;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitVariableModifier(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class VariableModifiersOptContext
    extends GroovyParserRuleContext {
        public VariableModifiersContext variableModifiers() {
            return this.getRuleContext(VariableModifiersContext.class, 0);
        }

        public NlsContext nls() {
            return this.getRuleContext(NlsContext.class, 0);
        }

        public VariableModifiersOptContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 13;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitVariableModifiersOpt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class VariableModifiersContext
    extends GroovyParserRuleContext {
        public List<? extends VariableModifierContext> variableModifier() {
            return this.getRuleContexts(VariableModifierContext.class);
        }

        public VariableModifierContext variableModifier(int i) {
            return this.getRuleContext(VariableModifierContext.class, i);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public VariableModifiersContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 14;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitVariableModifiers(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class TypeParametersContext
    extends GroovyParserRuleContext {
        public TerminalNode LT() {
            return this.getToken(97, 0);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public List<? extends TypeParameterContext> typeParameter() {
            return this.getRuleContexts(TypeParameterContext.class);
        }

        public TypeParameterContext typeParameter(int i) {
            return this.getRuleContext(TypeParameterContext.class, i);
        }

        public TerminalNode GT() {
            return this.getToken(96, 0);
        }

        public List<? extends TerminalNode> COMMA() {
            return this.getTokens(93);
        }

        public TerminalNode COMMA(int i) {
            return this.getToken(93, i);
        }

        public TypeParametersContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 15;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitTypeParameters(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class TypeParameterContext
    extends GroovyParserRuleContext {
        public AnnotationsOptContext annotationsOpt() {
            return this.getRuleContext(AnnotationsOptContext.class, 0);
        }

        public ClassNameContext className() {
            return this.getRuleContext(ClassNameContext.class, 0);
        }

        public TerminalNode EXTENDS() {
            return this.getToken(27, 0);
        }

        public NlsContext nls() {
            return this.getRuleContext(NlsContext.class, 0);
        }

        public TypeBoundContext typeBound() {
            return this.getRuleContext(TypeBoundContext.class, 0);
        }

        public TypeParameterContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 16;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitTypeParameter(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ClassNameContext
    extends GroovyParserRuleContext {
        public TerminalNode CapitalizedIdentifier() {
            return this.getToken(130, 0);
        }

        public ClassNameContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 158;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitClassName(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class TypeBoundContext
    extends GroovyParserRuleContext {
        public List<? extends TypeContext> type() {
            return this.getRuleContexts(TypeContext.class);
        }

        public TypeContext type(int i) {
            return this.getRuleContext(TypeContext.class, i);
        }

        public List<? extends TerminalNode> BITAND() {
            return this.getTokens(114);
        }

        public TerminalNode BITAND(int i) {
            return this.getToken(114, i);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public TypeBoundContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 17;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitTypeBound(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class TypeContext
    extends GroovyParserRuleContext {
        public AnnotationsOptContext annotationsOpt() {
            return this.getRuleContext(AnnotationsOptContext.class, 0);
        }

        public EmptyDimsOptContext emptyDimsOpt() {
            return this.getRuleContext(EmptyDimsOptContext.class, 0);
        }

        public PrimitiveTypeContext primitiveType() {
            return this.getRuleContext(PrimitiveTypeContext.class, 0);
        }

        public ClassOrInterfaceTypeContext classOrInterfaceType() {
            return this.getRuleContext(ClassOrInterfaceTypeContext.class, 0);
        }

        public TerminalNode VOID() {
            return this.getToken(58, 0);
        }

        public TypeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 38;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitType(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class TypeListContext
    extends GroovyParserRuleContext {
        public List<? extends TypeContext> type() {
            return this.getRuleContexts(TypeContext.class);
        }

        public TypeContext type(int i) {
            return this.getRuleContext(TypeContext.class, i);
        }

        public List<? extends TerminalNode> COMMA() {
            return this.getTokens(93);
        }

        public TerminalNode COMMA(int i) {
            return this.getToken(93, i);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public TypeListContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 18;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitTypeList(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class FormalParametersContext
    extends GroovyParserRuleContext {
        public TerminalNode LPAREN() {
            return this.getToken(86, 0);
        }

        public RparenContext rparen() {
            return this.getRuleContext(RparenContext.class, 0);
        }

        public FormalParameterListContext formalParameterList() {
            return this.getRuleContext(FormalParameterListContext.class, 0);
        }

        public FormalParametersContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 47;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitFormalParameters(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ClassBodyContext
    extends GroovyParserRuleContext {
        public int t;

        public TerminalNode LBRACE() {
            return this.getToken(88, 0);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public TerminalNode RBRACE() {
            return this.getToken(89, 0);
        }

        public EnumConstantsContext enumConstants() {
            return this.getRuleContext(EnumConstantsContext.class, 0);
        }

        public List<? extends ClassBodyDeclarationContext> classBodyDeclaration() {
            return this.getRuleContexts(ClassBodyDeclarationContext.class);
        }

        public ClassBodyDeclarationContext classBodyDeclaration(int i) {
            return this.getRuleContext(ClassBodyDeclarationContext.class, i);
        }

        public List<? extends SepContext> sep() {
            return this.getRuleContexts(SepContext.class);
        }

        public SepContext sep(int i) {
            return this.getRuleContext(SepContext.class, i);
        }

        public TerminalNode COMMA() {
            return this.getToken(93, 0);
        }

        public ClassBodyContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public ClassBodyContext(ParserRuleContext parent, int invokingState, int t) {
            super(parent, invokingState);
            this.t = t;
        }

        @Override
        public int getRuleIndex() {
            return 20;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitClassBody(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class EnumConstantsContext
    extends GroovyParserRuleContext {
        public List<? extends EnumConstantContext> enumConstant() {
            return this.getRuleContexts(EnumConstantContext.class);
        }

        public EnumConstantContext enumConstant(int i) {
            return this.getRuleContext(EnumConstantContext.class, i);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public List<? extends TerminalNode> COMMA() {
            return this.getTokens(93);
        }

        public TerminalNode COMMA(int i) {
            return this.getToken(93, i);
        }

        public EnumConstantsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 21;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitEnumConstants(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ClassBodyDeclarationContext
    extends GroovyParserRuleContext {
        public int t;

        public BlockContext block() {
            return this.getRuleContext(BlockContext.class, 0);
        }

        public TerminalNode STATIC() {
            return this.getToken(48, 0);
        }

        public NlsContext nls() {
            return this.getRuleContext(NlsContext.class, 0);
        }

        public MemberDeclarationContext memberDeclaration() {
            return this.getRuleContext(MemberDeclarationContext.class, 0);
        }

        public ClassBodyDeclarationContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public ClassBodyDeclarationContext(ParserRuleContext parent, int invokingState, int t) {
            super(parent, invokingState);
            this.t = t;
        }

        @Override
        public int getRuleIndex() {
            return 23;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitClassBodyDeclaration(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class EnumConstantContext
    extends GroovyParserRuleContext {
        public AnnotationsOptContext annotationsOpt() {
            return this.getRuleContext(AnnotationsOptContext.class, 0);
        }

        public IdentifierContext identifier() {
            return this.getRuleContext(IdentifierContext.class, 0);
        }

        public ArgumentsContext arguments() {
            return this.getRuleContext(ArgumentsContext.class, 0);
        }

        public AnonymousInnerClassDeclarationContext anonymousInnerClassDeclaration() {
            return this.getRuleContext(AnonymousInnerClassDeclarationContext.class, 0);
        }

        public EnumConstantContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 22;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitEnumConstant(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ArgumentsContext
    extends GroovyParserRuleContext {
        public TerminalNode LPAREN() {
            return this.getToken(86, 0);
        }

        public RparenContext rparen() {
            return this.getRuleContext(RparenContext.class, 0);
        }

        public EnhancedArgumentListInParContext enhancedArgumentListInPar() {
            return this.getRuleContext(EnhancedArgumentListInParContext.class, 0);
        }

        public TerminalNode COMMA() {
            return this.getToken(93, 0);
        }

        public ArgumentsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 151;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitArguments(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class AnonymousInnerClassDeclarationContext
    extends GroovyParserRuleContext {
        public int t;

        public ClassBodyContext classBody() {
            return this.getRuleContext(ClassBodyContext.class, 0);
        }

        public AnonymousInnerClassDeclarationContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public AnonymousInnerClassDeclarationContext(ParserRuleContext parent, int invokingState, int t) {
            super(parent, invokingState);
            this.t = t;
        }

        @Override
        public int getRuleIndex() {
            return 147;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitAnonymousInnerClassDeclaration(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class BlockContext
    extends GroovyParserRuleContext {
        public TerminalNode LBRACE() {
            return this.getToken(88, 0);
        }

        public BlockStatementsOptContext blockStatementsOpt() {
            return this.getRuleContext(BlockStatementsOptContext.class, 0);
        }

        public TerminalNode RBRACE() {
            return this.getToken(89, 0);
        }

        public SepContext sep() {
            return this.getRuleContext(SepContext.class, 0);
        }

        public BlockContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 79;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitBlock(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class MemberDeclarationContext
    extends GroovyParserRuleContext {
        public int t;

        public MethodDeclarationContext methodDeclaration() {
            return this.getRuleContext(MethodDeclarationContext.class, 0);
        }

        public FieldDeclarationContext fieldDeclaration() {
            return this.getRuleContext(FieldDeclarationContext.class, 0);
        }

        public ModifiersOptContext modifiersOpt() {
            return this.getRuleContext(ModifiersOptContext.class, 0);
        }

        public ClassDeclarationContext classDeclaration() {
            return this.getRuleContext(ClassDeclarationContext.class, 0);
        }

        public CompactConstructorDeclarationContext compactConstructorDeclaration() {
            return this.getRuleContext(CompactConstructorDeclarationContext.class, 0);
        }

        public MemberDeclarationContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public MemberDeclarationContext(ParserRuleContext parent, int invokingState, int t) {
            super(parent, invokingState);
            this.t = t;
        }

        @Override
        public int getRuleIndex() {
            return 24;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitMemberDeclaration(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class FieldDeclarationContext
    extends GroovyParserRuleContext {
        public VariableDeclarationContext variableDeclaration() {
            return this.getRuleContext(VariableDeclarationContext.class, 0);
        }

        public FieldDeclarationContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 29;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitFieldDeclaration(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class CompactConstructorDeclarationContext
    extends GroovyParserRuleContext {
        public MethodNameContext methodName() {
            return this.getRuleContext(MethodNameContext.class, 0);
        }

        public NlsContext nls() {
            return this.getRuleContext(NlsContext.class, 0);
        }

        public MethodBodyContext methodBody() {
            return this.getRuleContext(MethodBodyContext.class, 0);
        }

        public CompactConstructorDeclarationContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 26;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitCompactConstructorDeclaration(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ReturnTypeContext
    extends GroovyParserRuleContext {
        public int ct;

        public TypeContext type() {
            return this.getRuleContext(TypeContext.class, 0);
        }

        public TerminalNode VOID() {
            return this.getToken(58, 0);
        }

        public ReturnTypeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public ReturnTypeContext(ParserRuleContext parent, int invokingState, int ct) {
            super(parent, invokingState);
            this.ct = ct;
        }

        @Override
        public int getRuleIndex() {
            return 28;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitReturnType(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class MethodNameContext
    extends GroovyParserRuleContext {
        public IdentifierContext identifier() {
            return this.getRuleContext(IdentifierContext.class, 0);
        }

        public StringLiteralContext stringLiteral() {
            return this.getRuleContext(StringLiteralContext.class, 0);
        }

        public MethodNameContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 27;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitMethodName(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ElementValueContext
    extends GroovyParserRuleContext {
        public ElementValueArrayInitializerContext elementValueArrayInitializer() {
            return this.getRuleContext(ElementValueArrayInitializerContext.class, 0);
        }

        public AnnotationContext annotation() {
            return this.getRuleContext(AnnotationContext.class, 0);
        }

        public ExpressionContext expression() {
            return this.getRuleContext(ExpressionContext.class, 0);
        }

        public ElementValueContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 77;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitElementValue(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class QualifiedClassNameListContext
    extends GroovyParserRuleContext {
        public List<? extends AnnotatedQualifiedClassNameContext> annotatedQualifiedClassName() {
            return this.getRuleContexts(AnnotatedQualifiedClassNameContext.class);
        }

        public AnnotatedQualifiedClassNameContext annotatedQualifiedClassName(int i) {
            return this.getRuleContext(AnnotatedQualifiedClassNameContext.class, i);
        }

        public List<? extends TerminalNode> COMMA() {
            return this.getTokens(93);
        }

        public TerminalNode COMMA(int i) {
            return this.getToken(93, i);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public QualifiedClassNameListContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 46;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitQualifiedClassNameList(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class MethodBodyContext
    extends GroovyParserRuleContext {
        public BlockContext block() {
            return this.getRuleContext(BlockContext.class, 0);
        }

        public MethodBodyContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 51;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitMethodBody(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class StringLiteralContext
    extends GroovyParserRuleContext {
        public TerminalNode StringLiteral() {
            return this.getToken(1, 0);
        }

        public StringLiteralContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 157;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitStringLiteral(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class VariableDeclarationContext
    extends GroovyParserRuleContext {
        public int t;

        public ModifiersContext modifiers() {
            return this.getRuleContext(ModifiersContext.class, 0);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public VariableDeclaratorsContext variableDeclarators() {
            return this.getRuleContext(VariableDeclaratorsContext.class, 0);
        }

        public TypeNamePairsContext typeNamePairs() {
            return this.getRuleContext(TypeNamePairsContext.class, 0);
        }

        public TerminalNode ASSIGN() {
            return this.getToken(95, 0);
        }

        public VariableInitializerContext variableInitializer() {
            return this.getRuleContext(VariableInitializerContext.class, 0);
        }

        public TypeContext type() {
            return this.getRuleContext(TypeContext.class, 0);
        }

        public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public VariableDeclarationContext(ParserRuleContext parent, int invokingState, int t) {
            super(parent, invokingState);
            this.t = t;
        }

        @Override
        public int getRuleIndex() {
            return 82;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitVariableDeclaration(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class VariableDeclaratorsContext
    extends GroovyParserRuleContext {
        public List<? extends VariableDeclaratorContext> variableDeclarator() {
            return this.getRuleContexts(VariableDeclaratorContext.class);
        }

        public VariableDeclaratorContext variableDeclarator(int i) {
            return this.getRuleContext(VariableDeclaratorContext.class, i);
        }

        public List<? extends TerminalNode> COMMA() {
            return this.getTokens(93);
        }

        public TerminalNode COMMA(int i) {
            return this.getToken(93, i);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public VariableDeclaratorsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 30;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitVariableDeclarators(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class VariableDeclaratorContext
    extends GroovyParserRuleContext {
        public VariableDeclaratorIdContext variableDeclaratorId() {
            return this.getRuleContext(VariableDeclaratorIdContext.class, 0);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public TerminalNode ASSIGN() {
            return this.getToken(95, 0);
        }

        public VariableInitializerContext variableInitializer() {
            return this.getRuleContext(VariableInitializerContext.class, 0);
        }

        public VariableDeclaratorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 31;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitVariableDeclarator(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class VariableDeclaratorIdContext
    extends GroovyParserRuleContext {
        public IdentifierContext identifier() {
            return this.getRuleContext(IdentifierContext.class, 0);
        }

        public VariableDeclaratorIdContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 32;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitVariableDeclaratorId(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class VariableInitializerContext
    extends GroovyParserRuleContext {
        public EnhancedStatementExpressionContext enhancedStatementExpression() {
            return this.getRuleContext(EnhancedStatementExpressionContext.class, 0);
        }

        public VariableInitializerContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 33;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitVariableInitializer(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class EnhancedStatementExpressionContext
    extends GroovyParserRuleContext {
        public StatementExpressionContext statementExpression() {
            return this.getRuleContext(StatementExpressionContext.class, 0);
        }

        public StandardLambdaExpressionContext standardLambdaExpression() {
            return this.getRuleContext(StandardLambdaExpressionContext.class, 0);
        }

        public EnhancedStatementExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 114;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitEnhancedStatementExpression(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class VariableInitializersContext
    extends GroovyParserRuleContext {
        public List<? extends VariableInitializerContext> variableInitializer() {
            return this.getRuleContexts(VariableInitializerContext.class);
        }

        public VariableInitializerContext variableInitializer(int i) {
            return this.getRuleContext(VariableInitializerContext.class, i);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public List<? extends TerminalNode> COMMA() {
            return this.getTokens(93);
        }

        public TerminalNode COMMA(int i) {
            return this.getToken(93, i);
        }

        public VariableInitializersContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 34;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitVariableInitializers(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class EmptyDimsContext
    extends GroovyParserRuleContext {
        public List<? extends AnnotationsOptContext> annotationsOpt() {
            return this.getRuleContexts(AnnotationsOptContext.class);
        }

        public AnnotationsOptContext annotationsOpt(int i) {
            return this.getRuleContext(AnnotationsOptContext.class, i);
        }

        public List<? extends TerminalNode> LBRACK() {
            return this.getTokens(90);
        }

        public TerminalNode LBRACK(int i) {
            return this.getToken(90, i);
        }

        public List<? extends TerminalNode> RBRACK() {
            return this.getTokens(91);
        }

        public TerminalNode RBRACK(int i) {
            return this.getToken(91, i);
        }

        public EmptyDimsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 35;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitEmptyDims(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class EmptyDimsOptContext
    extends GroovyParserRuleContext {
        public EmptyDimsContext emptyDims() {
            return this.getRuleContext(EmptyDimsContext.class, 0);
        }

        public EmptyDimsOptContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 36;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitEmptyDimsOpt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class PrimitiveTypeContext
    extends GroovyParserRuleContext {
        public TerminalNode BuiltInPrimitiveType() {
            return this.getToken(13, 0);
        }

        public PrimitiveTypeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 42;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitPrimitiveType(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ClassOrInterfaceTypeContext
    extends GroovyParserRuleContext {
        public QualifiedClassNameContext qualifiedClassName() {
            return this.getRuleContext(QualifiedClassNameContext.class, 0);
        }

        public QualifiedStandardClassNameContext qualifiedStandardClassName() {
            return this.getRuleContext(QualifiedStandardClassNameContext.class, 0);
        }

        public TypeArgumentsContext typeArguments() {
            return this.getRuleContext(TypeArgumentsContext.class, 0);
        }

        public ClassOrInterfaceTypeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 39;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitClassOrInterfaceType(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class QualifiedClassNameContext
    extends GroovyParserRuleContext {
        public QualifiedNameElementsContext qualifiedNameElements() {
            return this.getRuleContext(QualifiedNameElementsContext.class, 0);
        }

        public IdentifierContext identifier() {
            return this.getRuleContext(IdentifierContext.class, 0);
        }

        public QualifiedClassNameContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 55;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitQualifiedClassName(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class QualifiedStandardClassNameContext
    extends GroovyParserRuleContext {
        public QualifiedNameElementsContext qualifiedNameElements() {
            return this.getRuleContext(QualifiedNameElementsContext.class, 0);
        }

        public List<? extends ClassNameContext> className() {
            return this.getRuleContexts(ClassNameContext.class);
        }

        public ClassNameContext className(int i) {
            return this.getRuleContext(ClassNameContext.class, i);
        }

        public List<? extends TerminalNode> DOT() {
            return this.getTokens(94);
        }

        public TerminalNode DOT(int i) {
            return this.getToken(94, i);
        }

        public QualifiedStandardClassNameContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 56;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitQualifiedStandardClassName(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class TypeArgumentsContext
    extends GroovyParserRuleContext {
        public TerminalNode LT() {
            return this.getToken(97, 0);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public List<? extends TypeArgumentContext> typeArgument() {
            return this.getRuleContexts(TypeArgumentContext.class);
        }

        public TypeArgumentContext typeArgument(int i) {
            return this.getRuleContext(TypeArgumentContext.class, i);
        }

        public TerminalNode GT() {
            return this.getToken(96, 0);
        }

        public List<? extends TerminalNode> COMMA() {
            return this.getTokens(93);
        }

        public TerminalNode COMMA(int i) {
            return this.getToken(93, i);
        }

        public TypeArgumentsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 43;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitTypeArguments(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class TypeArgumentContext
    extends GroovyParserRuleContext {
        public TypeContext type() {
            return this.getRuleContext(TypeContext.class, 0);
        }

        public AnnotationsOptContext annotationsOpt() {
            return this.getRuleContext(AnnotationsOptContext.class, 0);
        }

        public TerminalNode QUESTION() {
            return this.getToken(100, 0);
        }

        public NlsContext nls() {
            return this.getRuleContext(NlsContext.class, 0);
        }

        public TerminalNode EXTENDS() {
            return this.getToken(27, 0);
        }

        public TerminalNode SUPER() {
            return this.getToken(50, 0);
        }

        public TypeArgumentContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 44;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitTypeArgument(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class AnnotatedQualifiedClassNameContext
    extends GroovyParserRuleContext {
        public AnnotationsOptContext annotationsOpt() {
            return this.getRuleContext(AnnotationsOptContext.class, 0);
        }

        public QualifiedClassNameContext qualifiedClassName() {
            return this.getRuleContext(QualifiedClassNameContext.class, 0);
        }

        public AnnotatedQualifiedClassNameContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 45;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitAnnotatedQualifiedClassName(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class FormalParameterListContext
    extends GroovyParserRuleContext {
        public List<? extends FormalParameterContext> formalParameter() {
            return this.getRuleContexts(FormalParameterContext.class);
        }

        public FormalParameterContext formalParameter(int i) {
            return this.getRuleContext(FormalParameterContext.class, i);
        }

        public ThisFormalParameterContext thisFormalParameter() {
            return this.getRuleContext(ThisFormalParameterContext.class, 0);
        }

        public List<? extends TerminalNode> COMMA() {
            return this.getTokens(93);
        }

        public TerminalNode COMMA(int i) {
            return this.getToken(93, i);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public FormalParameterListContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 48;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitFormalParameterList(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class RparenContext
    extends GroovyParserRuleContext {
        public TerminalNode RPAREN() {
            return this.getToken(87, 0);
        }

        public RparenContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 162;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitRparen(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class FormalParameterContext
    extends GroovyParserRuleContext {
        public VariableModifiersOptContext variableModifiersOpt() {
            return this.getRuleContext(VariableModifiersOptContext.class, 0);
        }

        public VariableDeclaratorIdContext variableDeclaratorId() {
            return this.getRuleContext(VariableDeclaratorIdContext.class, 0);
        }

        public TypeContext type() {
            return this.getRuleContext(TypeContext.class, 0);
        }

        public TerminalNode ELLIPSIS() {
            return this.getToken(133, 0);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public TerminalNode ASSIGN() {
            return this.getToken(95, 0);
        }

        public ExpressionContext expression() {
            return this.getRuleContext(ExpressionContext.class, 0);
        }

        public FormalParameterContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 50;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitFormalParameter(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ThisFormalParameterContext
    extends GroovyParserRuleContext {
        public TypeContext type() {
            return this.getRuleContext(TypeContext.class, 0);
        }

        public TerminalNode THIS() {
            return this.getToken(53, 0);
        }

        public ThisFormalParameterContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 49;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitThisFormalParameter(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ExpressionContext
    extends GroovyParserRuleContext {
        public ExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 120;
        }

        public ExpressionContext() {
        }

        public void copyFrom(ExpressionContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class QualifiedNameElementContext
    extends GroovyParserRuleContext {
        public IdentifierContext identifier() {
            return this.getRuleContext(IdentifierContext.class, 0);
        }

        public TerminalNode DEF() {
            return this.getToken(8, 0);
        }

        public TerminalNode IN() {
            return this.getToken(9, 0);
        }

        public TerminalNode AS() {
            return this.getToken(7, 0);
        }

        public TerminalNode TRAIT() {
            return this.getToken(10, 0);
        }

        public QualifiedNameElementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 53;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitQualifiedNameElement(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class QualifiedNameElementsContext
    extends GroovyParserRuleContext {
        public List<? extends QualifiedNameElementContext> qualifiedNameElement() {
            return this.getRuleContexts(QualifiedNameElementContext.class);
        }

        public QualifiedNameElementContext qualifiedNameElement(int i) {
            return this.getRuleContext(QualifiedNameElementContext.class, i);
        }

        public List<? extends TerminalNode> DOT() {
            return this.getTokens(94);
        }

        public TerminalNode DOT(int i) {
            return this.getToken(94, i);
        }

        public QualifiedNameElementsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 54;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitQualifiedNameElements(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class LiteralContext
    extends GroovyParserRuleContext {
        public LiteralContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 57;
        }

        public LiteralContext() {
        }

        public void copyFrom(LiteralContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class IntegerLiteralAltContext
    extends LiteralContext {
        public TerminalNode IntegerLiteral() {
            return this.getToken(61, 0);
        }

        public IntegerLiteralAltContext(LiteralContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitIntegerLiteralAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class FloatingPointLiteralAltContext
    extends LiteralContext {
        public TerminalNode FloatingPointLiteral() {
            return this.getToken(62, 0);
        }

        public FloatingPointLiteralAltContext(LiteralContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitFloatingPointLiteralAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class StringLiteralAltContext
    extends LiteralContext {
        public StringLiteralContext stringLiteral() {
            return this.getRuleContext(StringLiteralContext.class, 0);
        }

        public StringLiteralAltContext(LiteralContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitStringLiteralAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class BooleanLiteralAltContext
    extends LiteralContext {
        public TerminalNode BooleanLiteral() {
            return this.getToken(63, 0);
        }

        public BooleanLiteralAltContext(LiteralContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitBooleanLiteralAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class NullLiteralAltContext
    extends LiteralContext {
        public TerminalNode NullLiteral() {
            return this.getToken(64, 0);
        }

        public NullLiteralAltContext(LiteralContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitNullLiteralAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class GstringContext
    extends GroovyParserRuleContext {
        public TerminalNode GStringBegin() {
            return this.getToken(2, 0);
        }

        public List<? extends GstringValueContext> gstringValue() {
            return this.getRuleContexts(GstringValueContext.class);
        }

        public GstringValueContext gstringValue(int i) {
            return this.getRuleContext(GstringValueContext.class, i);
        }

        public TerminalNode GStringEnd() {
            return this.getToken(3, 0);
        }

        public List<? extends TerminalNode> GStringPart() {
            return this.getTokens(4);
        }

        public TerminalNode GStringPart(int i) {
            return this.getToken(4, i);
        }

        public GstringContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 58;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitGstring(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class GstringValueContext
    extends GroovyParserRuleContext {
        public GstringPathContext gstringPath() {
            return this.getRuleContext(GstringPathContext.class, 0);
        }

        public ClosureContext closure() {
            return this.getRuleContext(ClosureContext.class, 0);
        }

        public GstringValueContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 59;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitGstringValue(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class GstringPathContext
    extends GroovyParserRuleContext {
        public IdentifierContext identifier() {
            return this.getRuleContext(IdentifierContext.class, 0);
        }

        public List<? extends TerminalNode> GStringPathPart() {
            return this.getTokens(5);
        }

        public TerminalNode GStringPathPart(int i) {
            return this.getToken(5, i);
        }

        public GstringPathContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 60;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitGstringPath(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ClosureContext
    extends GroovyParserRuleContext {
        public TerminalNode LBRACE() {
            return this.getToken(88, 0);
        }

        public BlockStatementsOptContext blockStatementsOpt() {
            return this.getRuleContext(BlockStatementsOptContext.class, 0);
        }

        public TerminalNode RBRACE() {
            return this.getToken(89, 0);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public TerminalNode ARROW() {
            return this.getToken(83, 0);
        }

        public SepContext sep() {
            return this.getRuleContext(SepContext.class, 0);
        }

        public FormalParameterListContext formalParameterList() {
            return this.getRuleContext(FormalParameterListContext.class, 0);
        }

        public ClosureContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 66;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitClosure(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class StandardLambdaExpressionContext
    extends GroovyParserRuleContext {
        public StandardLambdaParametersContext standardLambdaParameters() {
            return this.getRuleContext(StandardLambdaParametersContext.class, 0);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public TerminalNode ARROW() {
            return this.getToken(83, 0);
        }

        public LambdaBodyContext lambdaBody() {
            return this.getRuleContext(LambdaBodyContext.class, 0);
        }

        public StandardLambdaExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 62;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitStandardLambdaExpression(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class StandardLambdaParametersContext
    extends GroovyParserRuleContext {
        public FormalParametersContext formalParameters() {
            return this.getRuleContext(FormalParametersContext.class, 0);
        }

        public VariableDeclaratorIdContext variableDeclaratorId() {
            return this.getRuleContext(VariableDeclaratorIdContext.class, 0);
        }

        public StandardLambdaParametersContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 64;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitStandardLambdaParameters(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class LambdaBodyContext
    extends GroovyParserRuleContext {
        public BlockContext block() {
            return this.getRuleContext(BlockContext.class, 0);
        }

        public StatementExpressionContext statementExpression() {
            return this.getRuleContext(StatementExpressionContext.class, 0);
        }

        public LambdaBodyContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 65;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitLambdaBody(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class StatementExpressionContext
    extends GroovyParserRuleContext {
        public StatementExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 115;
        }

        public StatementExpressionContext() {
        }

        public void copyFrom(StatementExpressionContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class BlockStatementsOptContext
    extends GroovyParserRuleContext {
        public BlockStatementsContext blockStatements() {
            return this.getRuleContext(BlockStatementsContext.class, 0);
        }

        public BlockStatementsOptContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 68;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitBlockStatementsOpt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ClosureOrLambdaExpressionContext
    extends GroovyParserRuleContext {
        public ClosureContext closure() {
            return this.getRuleContext(ClosureContext.class, 0);
        }

        public StandardLambdaExpressionContext standardLambdaExpression() {
            return this.getRuleContext(StandardLambdaExpressionContext.class, 0);
        }

        public ClosureOrLambdaExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 67;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitClosureOrLambdaExpression(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class BlockStatementsContext
    extends GroovyParserRuleContext {
        public List<? extends BlockStatementContext> blockStatement() {
            return this.getRuleContexts(BlockStatementContext.class);
        }

        public BlockStatementContext blockStatement(int i) {
            return this.getRuleContext(BlockStatementContext.class, i);
        }

        public List<? extends SepContext> sep() {
            return this.getRuleContexts(SepContext.class);
        }

        public SepContext sep(int i) {
            return this.getRuleContext(SepContext.class, i);
        }

        public BlockStatementsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 69;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitBlockStatements(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class BlockStatementContext
    extends GroovyParserRuleContext {
        public LocalVariableDeclarationContext localVariableDeclaration() {
            return this.getRuleContext(LocalVariableDeclarationContext.class, 0);
        }

        public StatementContext statement() {
            return this.getRuleContext(StatementContext.class, 0);
        }

        public BlockStatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 80;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitBlockStatement(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class AnnotationNameContext
    extends GroovyParserRuleContext {
        public QualifiedClassNameContext qualifiedClassName() {
            return this.getRuleContext(QualifiedClassNameContext.class, 0);
        }

        public AnnotationNameContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 73;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitAnnotationName(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ElementValuesContext
    extends GroovyParserRuleContext {
        public ElementValuePairsContext elementValuePairs() {
            return this.getRuleContext(ElementValuePairsContext.class, 0);
        }

        public ElementValueContext elementValue() {
            return this.getRuleContext(ElementValueContext.class, 0);
        }

        public ElementValuesContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 72;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitElementValues(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ElementValuePairsContext
    extends GroovyParserRuleContext {
        public List<? extends ElementValuePairContext> elementValuePair() {
            return this.getRuleContexts(ElementValuePairContext.class);
        }

        public ElementValuePairContext elementValuePair(int i) {
            return this.getRuleContext(ElementValuePairContext.class, i);
        }

        public List<? extends TerminalNode> COMMA() {
            return this.getTokens(93);
        }

        public TerminalNode COMMA(int i) {
            return this.getToken(93, i);
        }

        public ElementValuePairsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 74;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitElementValuePairs(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ElementValuePairContext
    extends GroovyParserRuleContext {
        public ElementValuePairNameContext elementValuePairName() {
            return this.getRuleContext(ElementValuePairNameContext.class, 0);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public TerminalNode ASSIGN() {
            return this.getToken(95, 0);
        }

        public ElementValueContext elementValue() {
            return this.getRuleContext(ElementValueContext.class, 0);
        }

        public ElementValuePairContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 75;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitElementValuePair(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ElementValuePairNameContext
    extends GroovyParserRuleContext {
        public IdentifierContext identifier() {
            return this.getRuleContext(IdentifierContext.class, 0);
        }

        public KeywordsContext keywords() {
            return this.getRuleContext(KeywordsContext.class, 0);
        }

        public ElementValuePairNameContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 76;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitElementValuePairName(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class KeywordsContext
    extends GroovyParserRuleContext {
        public TerminalNode ABSTRACT() {
            return this.getToken(14, 0);
        }

        public TerminalNode AS() {
            return this.getToken(7, 0);
        }

        public TerminalNode ASSERT() {
            return this.getToken(15, 0);
        }

        public TerminalNode BREAK() {
            return this.getToken(16, 0);
        }

        public TerminalNode CASE() {
            return this.getToken(18, 0);
        }

        public TerminalNode CATCH() {
            return this.getToken(19, 0);
        }

        public TerminalNode CLASS() {
            return this.getToken(20, 0);
        }

        public TerminalNode CONST() {
            return this.getToken(21, 0);
        }

        public TerminalNode CONTINUE() {
            return this.getToken(22, 0);
        }

        public TerminalNode DEF() {
            return this.getToken(8, 0);
        }

        public TerminalNode DEFAULT() {
            return this.getToken(23, 0);
        }

        public TerminalNode DO() {
            return this.getToken(24, 0);
        }

        public TerminalNode ELSE() {
            return this.getToken(25, 0);
        }

        public TerminalNode ENUM() {
            return this.getToken(26, 0);
        }

        public TerminalNode EXTENDS() {
            return this.getToken(27, 0);
        }

        public TerminalNode FINAL() {
            return this.getToken(28, 0);
        }

        public TerminalNode FINALLY() {
            return this.getToken(29, 0);
        }

        public TerminalNode FOR() {
            return this.getToken(30, 0);
        }

        public TerminalNode GOTO() {
            return this.getToken(32, 0);
        }

        public TerminalNode IF() {
            return this.getToken(31, 0);
        }

        public TerminalNode IMPLEMENTS() {
            return this.getToken(33, 0);
        }

        public TerminalNode IMPORT() {
            return this.getToken(34, 0);
        }

        public TerminalNode IN() {
            return this.getToken(9, 0);
        }

        public TerminalNode INSTANCEOF() {
            return this.getToken(35, 0);
        }

        public TerminalNode INTERFACE() {
            return this.getToken(36, 0);
        }

        public TerminalNode NATIVE() {
            return this.getToken(37, 0);
        }

        public TerminalNode NEW() {
            return this.getToken(38, 0);
        }

        public TerminalNode NON_SEALED() {
            return this.getToken(39, 0);
        }

        public TerminalNode PACKAGE() {
            return this.getToken(40, 0);
        }

        public TerminalNode PERMITS() {
            return this.getToken(41, 0);
        }

        public TerminalNode RECORD() {
            return this.getToken(45, 0);
        }

        public TerminalNode RETURN() {
            return this.getToken(46, 0);
        }

        public TerminalNode SEALED() {
            return this.getToken(47, 0);
        }

        public TerminalNode STATIC() {
            return this.getToken(48, 0);
        }

        public TerminalNode STRICTFP() {
            return this.getToken(49, 0);
        }

        public TerminalNode SUPER() {
            return this.getToken(50, 0);
        }

        public TerminalNode SWITCH() {
            return this.getToken(51, 0);
        }

        public TerminalNode SYNCHRONIZED() {
            return this.getToken(52, 0);
        }

        public TerminalNode THIS() {
            return this.getToken(53, 0);
        }

        public TerminalNode THROW() {
            return this.getToken(54, 0);
        }

        public TerminalNode THROWS() {
            return this.getToken(55, 0);
        }

        public TerminalNode TRANSIENT() {
            return this.getToken(56, 0);
        }

        public TerminalNode TRAIT() {
            return this.getToken(10, 0);
        }

        public TerminalNode THREADSAFE() {
            return this.getToken(11, 0);
        }

        public TerminalNode TRY() {
            return this.getToken(57, 0);
        }

        public TerminalNode VAR() {
            return this.getToken(12, 0);
        }

        public TerminalNode VOLATILE() {
            return this.getToken(59, 0);
        }

        public TerminalNode WHILE() {
            return this.getToken(60, 0);
        }

        public TerminalNode YIELD() {
            return this.getToken(17, 0);
        }

        public TerminalNode NullLiteral() {
            return this.getToken(64, 0);
        }

        public TerminalNode BooleanLiteral() {
            return this.getToken(63, 0);
        }

        public TerminalNode BuiltInPrimitiveType() {
            return this.getToken(13, 0);
        }

        public TerminalNode VOID() {
            return this.getToken(58, 0);
        }

        public TerminalNode PUBLIC() {
            return this.getToken(44, 0);
        }

        public TerminalNode PROTECTED() {
            return this.getToken(43, 0);
        }

        public TerminalNode PRIVATE() {
            return this.getToken(42, 0);
        }

        public KeywordsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 161;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitKeywords(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ElementValueArrayInitializerContext
    extends GroovyParserRuleContext {
        public TerminalNode LBRACK() {
            return this.getToken(90, 0);
        }

        public TerminalNode RBRACK() {
            return this.getToken(91, 0);
        }

        public List<? extends ElementValueContext> elementValue() {
            return this.getRuleContexts(ElementValueContext.class);
        }

        public ElementValueContext elementValue(int i) {
            return this.getRuleContext(ElementValueContext.class, i);
        }

        public List<? extends TerminalNode> COMMA() {
            return this.getTokens(93);
        }

        public TerminalNode COMMA(int i) {
            return this.getToken(93, i);
        }

        public ElementValueArrayInitializerContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 78;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitElementValueArrayInitializer(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class LocalVariableDeclarationContext
    extends GroovyParserRuleContext {
        public VariableDeclarationContext variableDeclaration() {
            return this.getRuleContext(VariableDeclarationContext.class, 0);
        }

        public LocalVariableDeclarationContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 81;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitLocalVariableDeclaration(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class TypeNamePairsContext
    extends GroovyParserRuleContext {
        public TerminalNode LPAREN() {
            return this.getToken(86, 0);
        }

        public List<? extends TypeNamePairContext> typeNamePair() {
            return this.getRuleContexts(TypeNamePairContext.class);
        }

        public TypeNamePairContext typeNamePair(int i) {
            return this.getRuleContext(TypeNamePairContext.class, i);
        }

        public RparenContext rparen() {
            return this.getRuleContext(RparenContext.class, 0);
        }

        public List<? extends TerminalNode> COMMA() {
            return this.getTokens(93);
        }

        public TerminalNode COMMA(int i) {
            return this.getToken(93, i);
        }

        public TypeNamePairsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 83;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitTypeNamePairs(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class TypeNamePairContext
    extends GroovyParserRuleContext {
        public VariableDeclaratorIdContext variableDeclaratorId() {
            return this.getRuleContext(VariableDeclaratorIdContext.class, 0);
        }

        public TypeContext type() {
            return this.getRuleContext(TypeContext.class, 0);
        }

        public TypeNamePairContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 84;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitTypeNamePair(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class VariableNamesContext
    extends GroovyParserRuleContext {
        public TerminalNode LPAREN() {
            return this.getToken(86, 0);
        }

        public List<? extends VariableDeclaratorIdContext> variableDeclaratorId() {
            return this.getRuleContexts(VariableDeclaratorIdContext.class);
        }

        public VariableDeclaratorIdContext variableDeclaratorId(int i) {
            return this.getRuleContext(VariableDeclaratorIdContext.class, i);
        }

        public RparenContext rparen() {
            return this.getRuleContext(RparenContext.class, 0);
        }

        public List<? extends TerminalNode> COMMA() {
            return this.getTokens(93);
        }

        public TerminalNode COMMA(int i) {
            return this.getToken(93, i);
        }

        public VariableNamesContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 85;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitVariableNames(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ConditionalStatementContext
    extends GroovyParserRuleContext {
        public IfElseStatementContext ifElseStatement() {
            return this.getRuleContext(IfElseStatementContext.class, 0);
        }

        public SwitchStatementContext switchStatement() {
            return this.getRuleContext(SwitchStatementContext.class, 0);
        }

        public ConditionalStatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 86;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitConditionalStatement(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class IfElseStatementContext
    extends GroovyParserRuleContext {
        public StatementContext tb;
        public StatementContext fb;

        public TerminalNode IF() {
            return this.getToken(31, 0);
        }

        public ExpressionInParContext expressionInPar() {
            return this.getRuleContext(ExpressionInParContext.class, 0);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public List<? extends StatementContext> statement() {
            return this.getRuleContexts(StatementContext.class);
        }

        public StatementContext statement(int i) {
            return this.getRuleContext(StatementContext.class, i);
        }

        public TerminalNode ELSE() {
            return this.getToken(25, 0);
        }

        public SepContext sep() {
            return this.getRuleContext(SepContext.class, 0);
        }

        public IfElseStatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 87;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitIfElseStatement(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class SwitchStatementContext
    extends GroovyParserRuleContext {
        public TerminalNode SWITCH() {
            return this.getToken(51, 0);
        }

        public ExpressionInParContext expressionInPar() {
            return this.getRuleContext(ExpressionInParContext.class, 0);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public TerminalNode LBRACE() {
            return this.getToken(88, 0);
        }

        public TerminalNode RBRACE() {
            return this.getToken(89, 0);
        }

        public List<? extends SwitchBlockStatementGroupContext> switchBlockStatementGroup() {
            return this.getRuleContexts(SwitchBlockStatementGroupContext.class);
        }

        public SwitchBlockStatementGroupContext switchBlockStatementGroup(int i) {
            return this.getRuleContext(SwitchBlockStatementGroupContext.class, i);
        }

        public SwitchStatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 88;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitSwitchStatement(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ExpressionInParContext
    extends GroovyParserRuleContext {
        public TerminalNode LPAREN() {
            return this.getToken(86, 0);
        }

        public EnhancedStatementExpressionContext enhancedStatementExpression() {
            return this.getRuleContext(EnhancedStatementExpressionContext.class, 0);
        }

        public RparenContext rparen() {
            return this.getRuleContext(RparenContext.class, 0);
        }

        public ExpressionInParContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 111;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitExpressionInPar(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class SwitchBlockStatementGroupContext
    extends GroovyParserRuleContext {
        public List<? extends SwitchLabelContext> switchLabel() {
            return this.getRuleContexts(SwitchLabelContext.class);
        }

        public SwitchLabelContext switchLabel(int i) {
            return this.getRuleContext(SwitchLabelContext.class, i);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public BlockStatementsContext blockStatements() {
            return this.getRuleContext(BlockStatementsContext.class, 0);
        }

        public SwitchBlockStatementGroupContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 102;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitSwitchBlockStatementGroup(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class LoopStatementContext
    extends GroovyParserRuleContext {
        public LoopStatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 89;
        }

        public LoopStatementContext() {
        }

        public void copyFrom(LoopStatementContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class ForStmtAltContext
    extends LoopStatementContext {
        public TerminalNode FOR() {
            return this.getToken(30, 0);
        }

        public TerminalNode LPAREN() {
            return this.getToken(86, 0);
        }

        public ForControlContext forControl() {
            return this.getRuleContext(ForControlContext.class, 0);
        }

        public RparenContext rparen() {
            return this.getRuleContext(RparenContext.class, 0);
        }

        public NlsContext nls() {
            return this.getRuleContext(NlsContext.class, 0);
        }

        public StatementContext statement() {
            return this.getRuleContext(StatementContext.class, 0);
        }

        public ForStmtAltContext(LoopStatementContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitForStmtAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ForControlContext
    extends GroovyParserRuleContext {
        public EnhancedForControlContext enhancedForControl() {
            return this.getRuleContext(EnhancedForControlContext.class, 0);
        }

        public ClassicalForControlContext classicalForControl() {
            return this.getRuleContext(ClassicalForControlContext.class, 0);
        }

        public ForControlContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 104;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitForControl(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class WhileStmtAltContext
    extends LoopStatementContext {
        public TerminalNode WHILE() {
            return this.getToken(60, 0);
        }

        public ExpressionInParContext expressionInPar() {
            return this.getRuleContext(ExpressionInParContext.class, 0);
        }

        public NlsContext nls() {
            return this.getRuleContext(NlsContext.class, 0);
        }

        public StatementContext statement() {
            return this.getRuleContext(StatementContext.class, 0);
        }

        public WhileStmtAltContext(LoopStatementContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitWhileStmtAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class DoWhileStmtAltContext
    extends LoopStatementContext {
        public TerminalNode DO() {
            return this.getToken(24, 0);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public StatementContext statement() {
            return this.getRuleContext(StatementContext.class, 0);
        }

        public TerminalNode WHILE() {
            return this.getToken(60, 0);
        }

        public ExpressionInParContext expressionInPar() {
            return this.getRuleContext(ExpressionInParContext.class, 0);
        }

        public DoWhileStmtAltContext(LoopStatementContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitDoWhileStmtAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ContinueStatementContext
    extends GroovyParserRuleContext {
        public TerminalNode CONTINUE() {
            return this.getToken(22, 0);
        }

        public IdentifierContext identifier() {
            return this.getRuleContext(IdentifierContext.class, 0);
        }

        public ContinueStatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 90;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitContinueStatement(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class BreakStatementContext
    extends GroovyParserRuleContext {
        public TerminalNode BREAK() {
            return this.getToken(16, 0);
        }

        public IdentifierContext identifier() {
            return this.getRuleContext(IdentifierContext.class, 0);
        }

        public BreakStatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 91;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitBreakStatement(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class YieldStatementContext
    extends GroovyParserRuleContext {
        public TerminalNode YIELD() {
            return this.getToken(17, 0);
        }

        public ExpressionContext expression() {
            return this.getRuleContext(ExpressionContext.class, 0);
        }

        public YieldStatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 92;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitYieldStatement(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class TryCatchStatementContext
    extends GroovyParserRuleContext {
        public TerminalNode TRY() {
            return this.getToken(57, 0);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public BlockContext block() {
            return this.getRuleContext(BlockContext.class, 0);
        }

        public ResourcesContext resources() {
            return this.getRuleContext(ResourcesContext.class, 0);
        }

        public List<? extends CatchClauseContext> catchClause() {
            return this.getRuleContexts(CatchClauseContext.class);
        }

        public CatchClauseContext catchClause(int i) {
            return this.getRuleContext(CatchClauseContext.class, i);
        }

        public FinallyBlockContext finallyBlock() {
            return this.getRuleContext(FinallyBlockContext.class, 0);
        }

        public TryCatchStatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 93;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitTryCatchStatement(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ResourcesContext
    extends GroovyParserRuleContext {
        public TerminalNode LPAREN() {
            return this.getToken(86, 0);
        }

        public NlsContext nls() {
            return this.getRuleContext(NlsContext.class, 0);
        }

        public ResourceListContext resourceList() {
            return this.getRuleContext(ResourceListContext.class, 0);
        }

        public RparenContext rparen() {
            return this.getRuleContext(RparenContext.class, 0);
        }

        public SepContext sep() {
            return this.getRuleContext(SepContext.class, 0);
        }

        public ResourcesContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 99;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitResources(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class CatchClauseContext
    extends GroovyParserRuleContext {
        public TerminalNode CATCH() {
            return this.getToken(19, 0);
        }

        public TerminalNode LPAREN() {
            return this.getToken(86, 0);
        }

        public VariableModifiersOptContext variableModifiersOpt() {
            return this.getRuleContext(VariableModifiersOptContext.class, 0);
        }

        public IdentifierContext identifier() {
            return this.getRuleContext(IdentifierContext.class, 0);
        }

        public RparenContext rparen() {
            return this.getRuleContext(RparenContext.class, 0);
        }

        public NlsContext nls() {
            return this.getRuleContext(NlsContext.class, 0);
        }

        public BlockContext block() {
            return this.getRuleContext(BlockContext.class, 0);
        }

        public CatchTypeContext catchType() {
            return this.getRuleContext(CatchTypeContext.class, 0);
        }

        public CatchClauseContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 96;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitCatchClause(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class FinallyBlockContext
    extends GroovyParserRuleContext {
        public TerminalNode FINALLY() {
            return this.getToken(29, 0);
        }

        public NlsContext nls() {
            return this.getRuleContext(NlsContext.class, 0);
        }

        public BlockContext block() {
            return this.getRuleContext(BlockContext.class, 0);
        }

        public FinallyBlockContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 98;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitFinallyBlock(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class AssertStatementContext
    extends GroovyParserRuleContext {
        public ExpressionContext ce;
        public ExpressionContext me;

        public TerminalNode ASSERT() {
            return this.getToken(15, 0);
        }

        public List<? extends ExpressionContext> expression() {
            return this.getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return this.getRuleContext(ExpressionContext.class, i);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public TerminalNode COLON() {
            return this.getToken(101, 0);
        }

        public TerminalNode COMMA() {
            return this.getToken(93, 0);
        }

        public AssertStatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 94;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitAssertStatement(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class BlockStmtAltContext
    extends StatementContext {
        public BlockContext block() {
            return this.getRuleContext(BlockContext.class, 0);
        }

        public BlockStmtAltContext(StatementContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitBlockStmtAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ConditionalStmtAltContext
    extends StatementContext {
        public ConditionalStatementContext conditionalStatement() {
            return this.getRuleContext(ConditionalStatementContext.class, 0);
        }

        public ConditionalStmtAltContext(StatementContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitConditionalStmtAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class LoopStmtAltContext
    extends StatementContext {
        public LoopStatementContext loopStatement() {
            return this.getRuleContext(LoopStatementContext.class, 0);
        }

        public LoopStmtAltContext(StatementContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitLoopStmtAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class TryCatchStmtAltContext
    extends StatementContext {
        public TryCatchStatementContext tryCatchStatement() {
            return this.getRuleContext(TryCatchStatementContext.class, 0);
        }

        public TryCatchStmtAltContext(StatementContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitTryCatchStmtAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class SynchronizedStmtAltContext
    extends StatementContext {
        public TerminalNode SYNCHRONIZED() {
            return this.getToken(52, 0);
        }

        public ExpressionInParContext expressionInPar() {
            return this.getRuleContext(ExpressionInParContext.class, 0);
        }

        public NlsContext nls() {
            return this.getRuleContext(NlsContext.class, 0);
        }

        public BlockContext block() {
            return this.getRuleContext(BlockContext.class, 0);
        }

        public SynchronizedStmtAltContext(StatementContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitSynchronizedStmtAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ReturnStmtAltContext
    extends StatementContext {
        public TerminalNode RETURN() {
            return this.getToken(46, 0);
        }

        public ExpressionContext expression() {
            return this.getRuleContext(ExpressionContext.class, 0);
        }

        public ReturnStmtAltContext(StatementContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitReturnStmtAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ThrowStmtAltContext
    extends StatementContext {
        public TerminalNode THROW() {
            return this.getToken(54, 0);
        }

        public ExpressionContext expression() {
            return this.getRuleContext(ExpressionContext.class, 0);
        }

        public ThrowStmtAltContext(StatementContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitThrowStmtAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class BreakStmtAltContext
    extends StatementContext {
        public BreakStatementContext breakStatement() {
            return this.getRuleContext(BreakStatementContext.class, 0);
        }

        public BreakStmtAltContext(StatementContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitBreakStmtAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ContinueStmtAltContext
    extends StatementContext {
        public ContinueStatementContext continueStatement() {
            return this.getRuleContext(ContinueStatementContext.class, 0);
        }

        public ContinueStmtAltContext(StatementContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitContinueStmtAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class YieldStmtAltContext
    extends StatementContext {
        public YieldStatementContext yieldStatement() {
            return this.getRuleContext(YieldStatementContext.class, 0);
        }

        public YieldStmtAltContext(StatementContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitYieldStmtAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class LabeledStmtAltContext
    extends StatementContext {
        public IdentifierContext identifier() {
            return this.getRuleContext(IdentifierContext.class, 0);
        }

        public TerminalNode COLON() {
            return this.getToken(101, 0);
        }

        public NlsContext nls() {
            return this.getRuleContext(NlsContext.class, 0);
        }

        public StatementContext statement() {
            return this.getRuleContext(StatementContext.class, 0);
        }

        public LabeledStmtAltContext(StatementContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitLabeledStmtAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class AssertStmtAltContext
    extends StatementContext {
        public AssertStatementContext assertStatement() {
            return this.getRuleContext(AssertStatementContext.class, 0);
        }

        public AssertStmtAltContext(StatementContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitAssertStmtAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class LocalVariableDeclarationStmtAltContext
    extends StatementContext {
        public LocalVariableDeclarationContext localVariableDeclaration() {
            return this.getRuleContext(LocalVariableDeclarationContext.class, 0);
        }

        public LocalVariableDeclarationStmtAltContext(StatementContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitLocalVariableDeclarationStmtAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ExpressionStmtAltContext
    extends StatementContext {
        public StatementExpressionContext statementExpression() {
            return this.getRuleContext(StatementExpressionContext.class, 0);
        }

        public ExpressionStmtAltContext(StatementContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitExpressionStmtAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class EmptyStmtAltContext
    extends StatementContext {
        public TerminalNode SEMI() {
            return this.getToken(92, 0);
        }

        public EmptyStmtAltContext(StatementContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitEmptyStmtAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class CatchTypeContext
    extends GroovyParserRuleContext {
        public List<? extends QualifiedClassNameContext> qualifiedClassName() {
            return this.getRuleContexts(QualifiedClassNameContext.class);
        }

        public QualifiedClassNameContext qualifiedClassName(int i) {
            return this.getRuleContext(QualifiedClassNameContext.class, i);
        }

        public List<? extends TerminalNode> BITOR() {
            return this.getTokens(115);
        }

        public TerminalNode BITOR(int i) {
            return this.getToken(115, i);
        }

        public CatchTypeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 97;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitCatchType(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ResourceListContext
    extends GroovyParserRuleContext {
        public List<? extends ResourceContext> resource() {
            return this.getRuleContexts(ResourceContext.class);
        }

        public ResourceContext resource(int i) {
            return this.getRuleContext(ResourceContext.class, i);
        }

        public List<? extends SepContext> sep() {
            return this.getRuleContexts(SepContext.class);
        }

        public SepContext sep(int i) {
            return this.getRuleContext(SepContext.class, i);
        }

        public ResourceListContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 100;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitResourceList(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ResourceContext
    extends GroovyParserRuleContext {
        public LocalVariableDeclarationContext localVariableDeclaration() {
            return this.getRuleContext(LocalVariableDeclarationContext.class, 0);
        }

        public ExpressionContext expression() {
            return this.getRuleContext(ExpressionContext.class, 0);
        }

        public ResourceContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 101;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitResource(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class SwitchLabelContext
    extends GroovyParserRuleContext {
        public TerminalNode CASE() {
            return this.getToken(18, 0);
        }

        public ExpressionContext expression() {
            return this.getRuleContext(ExpressionContext.class, 0);
        }

        public TerminalNode COLON() {
            return this.getToken(101, 0);
        }

        public TerminalNode DEFAULT() {
            return this.getToken(23, 0);
        }

        public SwitchLabelContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 103;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitSwitchLabel(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class EnhancedForControlContext
    extends GroovyParserRuleContext {
        public VariableModifiersOptContext variableModifiersOpt() {
            return this.getRuleContext(VariableModifiersOptContext.class, 0);
        }

        public VariableDeclaratorIdContext variableDeclaratorId() {
            return this.getRuleContext(VariableDeclaratorIdContext.class, 0);
        }

        public ExpressionContext expression() {
            return this.getRuleContext(ExpressionContext.class, 0);
        }

        public TerminalNode COLON() {
            return this.getToken(101, 0);
        }

        public TerminalNode IN() {
            return this.getToken(9, 0);
        }

        public TypeContext type() {
            return this.getRuleContext(TypeContext.class, 0);
        }

        public EnhancedForControlContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 105;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitEnhancedForControl(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ClassicalForControlContext
    extends GroovyParserRuleContext {
        public List<? extends TerminalNode> SEMI() {
            return this.getTokens(92);
        }

        public TerminalNode SEMI(int i) {
            return this.getToken(92, i);
        }

        public ForInitContext forInit() {
            return this.getRuleContext(ForInitContext.class, 0);
        }

        public ExpressionContext expression() {
            return this.getRuleContext(ExpressionContext.class, 0);
        }

        public ForUpdateContext forUpdate() {
            return this.getRuleContext(ForUpdateContext.class, 0);
        }

        public ClassicalForControlContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 106;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitClassicalForControl(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ForInitContext
    extends GroovyParserRuleContext {
        public LocalVariableDeclarationContext localVariableDeclaration() {
            return this.getRuleContext(LocalVariableDeclarationContext.class, 0);
        }

        public ExpressionListContext expressionList() {
            return this.getRuleContext(ExpressionListContext.class, 0);
        }

        public ForInitContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 107;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitForInit(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ForUpdateContext
    extends GroovyParserRuleContext {
        public ExpressionListContext expressionList() {
            return this.getRuleContext(ExpressionListContext.class, 0);
        }

        public ForUpdateContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 108;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitForUpdate(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ExpressionListContext
    extends GroovyParserRuleContext {
        public boolean canSpread;

        public List<? extends ExpressionListElementContext> expressionListElement() {
            return this.getRuleContexts(ExpressionListElementContext.class);
        }

        public ExpressionListElementContext expressionListElement(int i) {
            return this.getRuleContext(ExpressionListElementContext.class, i);
        }

        public List<? extends TerminalNode> COMMA() {
            return this.getTokens(93);
        }

        public TerminalNode COMMA(int i) {
            return this.getToken(93, i);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public ExpressionListContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public ExpressionListContext(ParserRuleContext parent, int invokingState, boolean canSpread) {
            super(parent, invokingState);
            this.canSpread = canSpread;
        }

        @Override
        public int getRuleIndex() {
            return 112;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitExpressionList(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class CastParExpressionContext
    extends GroovyParserRuleContext {
        public TerminalNode LPAREN() {
            return this.getToken(86, 0);
        }

        public TypeContext type() {
            return this.getRuleContext(TypeContext.class, 0);
        }

        public RparenContext rparen() {
            return this.getRuleContext(RparenContext.class, 0);
        }

        public CastParExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 109;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitCastParExpression(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ParExpressionContext
    extends GroovyParserRuleContext {
        public ExpressionInParContext expressionInPar() {
            return this.getRuleContext(ExpressionInParContext.class, 0);
        }

        public ParExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 110;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitParExpression(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ExpressionListElementContext
    extends GroovyParserRuleContext {
        public boolean canSpread;

        public ExpressionContext expression() {
            return this.getRuleContext(ExpressionContext.class, 0);
        }

        public TerminalNode MUL() {
            return this.getToken(112, 0);
        }

        public ExpressionListElementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public ExpressionListElementContext(ParserRuleContext parent, int invokingState, boolean canSpread) {
            super(parent, invokingState);
            this.canSpread = canSpread;
        }

        @Override
        public int getRuleIndex() {
            return 113;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitExpressionListElement(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class CommandExprAltContext
    extends StatementExpressionContext {
        public CommandExpressionContext commandExpression() {
            return this.getRuleContext(CommandExpressionContext.class, 0);
        }

        public CommandExprAltContext(StatementExpressionContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitCommandExprAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class CommandExpressionContext
    extends GroovyParserRuleContext {
        public ExpressionContext expression;

        public ExpressionContext expression() {
            return this.getRuleContext(ExpressionContext.class, 0);
        }

        public EnhancedArgumentListInParContext enhancedArgumentListInPar() {
            return this.getRuleContext(EnhancedArgumentListInParContext.class, 0);
        }

        public List<? extends CommandArgumentContext> commandArgument() {
            return this.getRuleContexts(CommandArgumentContext.class);
        }

        public CommandArgumentContext commandArgument(int i) {
            return this.getRuleContext(CommandArgumentContext.class, i);
        }

        public CommandExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 122;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitCommandExpression(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class PostfixExpressionContext
    extends GroovyParserRuleContext {
        public Token op;

        public PathExpressionContext pathExpression() {
            return this.getRuleContext(PathExpressionContext.class, 0);
        }

        public TerminalNode INC() {
            return this.getToken(108, 0);
        }

        public TerminalNode DEC() {
            return this.getToken(109, 0);
        }

        public PostfixExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 116;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitPostfixExpression(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class PathExpressionContext
    extends GroovyParserRuleContext {
        public int t;
        public PathElementContext pathElement;

        public PrimaryContext primary() {
            return this.getRuleContext(PrimaryContext.class, 0);
        }

        public TerminalNode STATIC() {
            return this.getToken(48, 0);
        }

        public List<? extends PathElementContext> pathElement() {
            return this.getRuleContexts(PathElementContext.class);
        }

        public PathElementContext pathElement(int i) {
            return this.getRuleContext(PathElementContext.class, i);
        }

        public PathExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 124;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitPathExpression(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class SwitchExpressionContext
    extends GroovyParserRuleContext {
        public TerminalNode SWITCH() {
            return this.getToken(51, 0);
        }

        public ExpressionInParContext expressionInPar() {
            return this.getRuleContext(ExpressionInParContext.class, 0);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public TerminalNode LBRACE() {
            return this.getToken(88, 0);
        }

        public TerminalNode RBRACE() {
            return this.getToken(89, 0);
        }

        public List<? extends SwitchBlockStatementExpressionGroupContext> switchBlockStatementExpressionGroup() {
            return this.getRuleContexts(SwitchBlockStatementExpressionGroupContext.class);
        }

        public SwitchBlockStatementExpressionGroupContext switchBlockStatementExpressionGroup(int i) {
            return this.getRuleContext(SwitchBlockStatementExpressionGroupContext.class, i);
        }

        public SwitchExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 117;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitSwitchExpression(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class SwitchBlockStatementExpressionGroupContext
    extends GroovyParserRuleContext {
        public BlockStatementsContext blockStatements() {
            return this.getRuleContext(BlockStatementsContext.class, 0);
        }

        public List<? extends SwitchExpressionLabelContext> switchExpressionLabel() {
            return this.getRuleContexts(SwitchExpressionLabelContext.class);
        }

        public SwitchExpressionLabelContext switchExpressionLabel(int i) {
            return this.getRuleContext(SwitchExpressionLabelContext.class, i);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public SwitchBlockStatementExpressionGroupContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 118;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitSwitchBlockStatementExpressionGroup(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class SwitchExpressionLabelContext
    extends GroovyParserRuleContext {
        public Token ac;

        public TerminalNode CASE() {
            return this.getToken(18, 0);
        }

        public ExpressionListContext expressionList() {
            return this.getRuleContext(ExpressionListContext.class, 0);
        }

        public TerminalNode DEFAULT() {
            return this.getToken(23, 0);
        }

        public TerminalNode ARROW() {
            return this.getToken(83, 0);
        }

        public TerminalNode COLON() {
            return this.getToken(101, 0);
        }

        public SwitchExpressionLabelContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 119;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitSwitchExpressionLabel(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class CastExprAltContext
    extends ExpressionContext {
        public CastParExpressionContext castParExpression() {
            return this.getRuleContext(CastParExpressionContext.class, 0);
        }

        public ExpressionContext expression() {
            return this.getRuleContext(ExpressionContext.class, 0);
        }

        public CastExprAltContext(ExpressionContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitCastExprAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class PostfixExprAltContext
    extends ExpressionContext {
        public PostfixExpressionContext postfixExpression() {
            return this.getRuleContext(PostfixExpressionContext.class, 0);
        }

        public PostfixExprAltContext(ExpressionContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitPostfixExprAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class SwitchExprAltContext
    extends ExpressionContext {
        public SwitchExpressionContext switchExpression() {
            return this.getRuleContext(SwitchExpressionContext.class, 0);
        }

        public SwitchExprAltContext(ExpressionContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitSwitchExprAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class UnaryNotExprAltContext
    extends ExpressionContext {
        public NlsContext nls() {
            return this.getRuleContext(NlsContext.class, 0);
        }

        public ExpressionContext expression() {
            return this.getRuleContext(ExpressionContext.class, 0);
        }

        public TerminalNode BITNOT() {
            return this.getToken(99, 0);
        }

        public TerminalNode NOT() {
            return this.getToken(98, 0);
        }

        public UnaryNotExprAltContext(ExpressionContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitUnaryNotExprAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class UnaryAddExprAltContext
    extends ExpressionContext {
        public Token op;

        public ExpressionContext expression() {
            return this.getRuleContext(ExpressionContext.class, 0);
        }

        public TerminalNode INC() {
            return this.getToken(108, 0);
        }

        public TerminalNode DEC() {
            return this.getToken(109, 0);
        }

        public TerminalNode ADD() {
            return this.getToken(110, 0);
        }

        public TerminalNode SUB() {
            return this.getToken(111, 0);
        }

        public UnaryAddExprAltContext(ExpressionContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitUnaryAddExprAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class MultipleAssignmentExprAltContext
    extends ExpressionContext {
        public VariableNamesContext left;
        public Token op;
        public StatementExpressionContext right;

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public VariableNamesContext variableNames() {
            return this.getRuleContext(VariableNamesContext.class, 0);
        }

        public TerminalNode ASSIGN() {
            return this.getToken(95, 0);
        }

        public StatementExpressionContext statementExpression() {
            return this.getRuleContext(StatementExpressionContext.class, 0);
        }

        public MultipleAssignmentExprAltContext(ExpressionContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitMultipleAssignmentExprAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class PowerExprAltContext
    extends ExpressionContext {
        public ExpressionContext left;
        public Token op;
        public ExpressionContext right;

        public NlsContext nls() {
            return this.getRuleContext(NlsContext.class, 0);
        }

        public List<? extends ExpressionContext> expression() {
            return this.getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return this.getRuleContext(ExpressionContext.class, i);
        }

        public TerminalNode POWER() {
            return this.getToken(78, 0);
        }

        public PowerExprAltContext(ExpressionContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitPowerExprAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class MultiplicativeExprAltContext
    extends ExpressionContext {
        public ExpressionContext left;
        public Token op;
        public ExpressionContext right;

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public List<? extends ExpressionContext> expression() {
            return this.getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return this.getRuleContext(ExpressionContext.class, i);
        }

        public TerminalNode MUL() {
            return this.getToken(112, 0);
        }

        public TerminalNode DIV() {
            return this.getToken(113, 0);
        }

        public TerminalNode MOD() {
            return this.getToken(117, 0);
        }

        public MultiplicativeExprAltContext(ExpressionContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitMultiplicativeExprAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class AdditiveExprAltContext
    extends ExpressionContext {
        public ExpressionContext left;
        public Token op;
        public ExpressionContext right;

        public NlsContext nls() {
            return this.getRuleContext(NlsContext.class, 0);
        }

        public List<? extends ExpressionContext> expression() {
            return this.getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return this.getRuleContext(ExpressionContext.class, i);
        }

        public TerminalNode ADD() {
            return this.getToken(110, 0);
        }

        public TerminalNode SUB() {
            return this.getToken(111, 0);
        }

        public AdditiveExprAltContext(ExpressionContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitAdditiveExprAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ShiftExprAltContext
    extends ExpressionContext {
        public ExpressionContext left;
        public Token dlOp;
        public Token tgOp;
        public Token dgOp;
        public Token rangeOp;
        public ExpressionContext right;

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public List<? extends ExpressionContext> expression() {
            return this.getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return this.getRuleContext(ExpressionContext.class, i);
        }

        public List<? extends TerminalNode> LT() {
            return this.getTokens(97);
        }

        public TerminalNode LT(int i) {
            return this.getToken(97, i);
        }

        public List<? extends TerminalNode> GT() {
            return this.getTokens(96);
        }

        public TerminalNode GT(int i) {
            return this.getToken(96, i);
        }

        public TerminalNode RANGE_INCLUSIVE() {
            return this.getToken(65, 0);
        }

        public TerminalNode RANGE_EXCLUSIVE_LEFT() {
            return this.getToken(66, 0);
        }

        public TerminalNode RANGE_EXCLUSIVE_RIGHT() {
            return this.getToken(67, 0);
        }

        public TerminalNode RANGE_EXCLUSIVE_FULL() {
            return this.getToken(68, 0);
        }

        public ShiftExprAltContext(ExpressionContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitShiftExprAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class RelationalExprAltContext
    extends ExpressionContext {
        public ExpressionContext left;
        public Token op;
        public ExpressionContext right;

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public TypeContext type() {
            return this.getRuleContext(TypeContext.class, 0);
        }

        public List<? extends ExpressionContext> expression() {
            return this.getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return this.getRuleContext(ExpressionContext.class, i);
        }

        public TerminalNode AS() {
            return this.getToken(7, 0);
        }

        public TerminalNode INSTANCEOF() {
            return this.getToken(35, 0);
        }

        public TerminalNode NOT_INSTANCEOF() {
            return this.getToken(84, 0);
        }

        public TerminalNode LE() {
            return this.getToken(103, 0);
        }

        public TerminalNode GE() {
            return this.getToken(104, 0);
        }

        public TerminalNode GT() {
            return this.getToken(96, 0);
        }

        public TerminalNode LT() {
            return this.getToken(97, 0);
        }

        public TerminalNode IN() {
            return this.getToken(9, 0);
        }

        public TerminalNode NOT_IN() {
            return this.getToken(85, 0);
        }

        public RelationalExprAltContext(ExpressionContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitRelationalExprAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class EqualityExprAltContext
    extends ExpressionContext {
        public ExpressionContext left;
        public Token op;
        public ExpressionContext right;

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public List<? extends ExpressionContext> expression() {
            return this.getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return this.getRuleContext(ExpressionContext.class, i);
        }

        public TerminalNode IDENTICAL() {
            return this.getToken(81, 0);
        }

        public TerminalNode NOT_IDENTICAL() {
            return this.getToken(82, 0);
        }

        public TerminalNode EQUAL() {
            return this.getToken(102, 0);
        }

        public TerminalNode NOTEQUAL() {
            return this.getToken(105, 0);
        }

        public TerminalNode SPACESHIP() {
            return this.getToken(80, 0);
        }

        public EqualityExprAltContext(ExpressionContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitEqualityExprAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class RegexExprAltContext
    extends ExpressionContext {
        public ExpressionContext left;
        public Token op;
        public ExpressionContext right;

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public List<? extends ExpressionContext> expression() {
            return this.getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return this.getRuleContext(ExpressionContext.class, i);
        }

        public TerminalNode REGEX_FIND() {
            return this.getToken(76, 0);
        }

        public TerminalNode REGEX_MATCH() {
            return this.getToken(77, 0);
        }

        public RegexExprAltContext(ExpressionContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitRegexExprAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class AndExprAltContext
    extends ExpressionContext {
        public ExpressionContext left;
        public Token op;
        public ExpressionContext right;

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public List<? extends ExpressionContext> expression() {
            return this.getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return this.getRuleContext(ExpressionContext.class, i);
        }

        public TerminalNode BITAND() {
            return this.getToken(114, 0);
        }

        public AndExprAltContext(ExpressionContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitAndExprAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ExclusiveOrExprAltContext
    extends ExpressionContext {
        public ExpressionContext left;
        public Token op;
        public ExpressionContext right;

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public List<? extends ExpressionContext> expression() {
            return this.getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return this.getRuleContext(ExpressionContext.class, i);
        }

        public TerminalNode XOR() {
            return this.getToken(116, 0);
        }

        public ExclusiveOrExprAltContext(ExpressionContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitExclusiveOrExprAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class InclusiveOrExprAltContext
    extends ExpressionContext {
        public ExpressionContext left;
        public Token op;
        public ExpressionContext right;

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public List<? extends ExpressionContext> expression() {
            return this.getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return this.getRuleContext(ExpressionContext.class, i);
        }

        public TerminalNode BITOR() {
            return this.getToken(115, 0);
        }

        public InclusiveOrExprAltContext(ExpressionContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitInclusiveOrExprAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class LogicalAndExprAltContext
    extends ExpressionContext {
        public ExpressionContext left;
        public Token op;
        public ExpressionContext right;

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public List<? extends ExpressionContext> expression() {
            return this.getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return this.getRuleContext(ExpressionContext.class, i);
        }

        public TerminalNode AND() {
            return this.getToken(106, 0);
        }

        public LogicalAndExprAltContext(ExpressionContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitLogicalAndExprAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class LogicalOrExprAltContext
    extends ExpressionContext {
        public ExpressionContext left;
        public Token op;
        public ExpressionContext right;

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public List<? extends ExpressionContext> expression() {
            return this.getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return this.getRuleContext(ExpressionContext.class, i);
        }

        public TerminalNode OR() {
            return this.getToken(107, 0);
        }

        public LogicalOrExprAltContext(ExpressionContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitLogicalOrExprAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ConditionalExprAltContext
    extends ExpressionContext {
        public ExpressionContext con;
        public ExpressionContext tb;
        public ExpressionContext fb;

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public List<? extends ExpressionContext> expression() {
            return this.getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return this.getRuleContext(ExpressionContext.class, i);
        }

        public TerminalNode QUESTION() {
            return this.getToken(100, 0);
        }

        public TerminalNode COLON() {
            return this.getToken(101, 0);
        }

        public TerminalNode ELVIS() {
            return this.getToken(73, 0);
        }

        public ConditionalExprAltContext(ExpressionContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitConditionalExprAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class AssignmentExprAltContext
    extends ExpressionContext {
        public ExpressionContext left;
        public Token op;
        public EnhancedStatementExpressionContext right;

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public ExpressionContext expression() {
            return this.getRuleContext(ExpressionContext.class, 0);
        }

        public EnhancedStatementExpressionContext enhancedStatementExpression() {
            return this.getRuleContext(EnhancedStatementExpressionContext.class, 0);
        }

        public TerminalNode ASSIGN() {
            return this.getToken(95, 0);
        }

        public TerminalNode ADD_ASSIGN() {
            return this.getToken(118, 0);
        }

        public TerminalNode SUB_ASSIGN() {
            return this.getToken(119, 0);
        }

        public TerminalNode MUL_ASSIGN() {
            return this.getToken(120, 0);
        }

        public TerminalNode DIV_ASSIGN() {
            return this.getToken(121, 0);
        }

        public TerminalNode AND_ASSIGN() {
            return this.getToken(122, 0);
        }

        public TerminalNode OR_ASSIGN() {
            return this.getToken(123, 0);
        }

        public TerminalNode XOR_ASSIGN() {
            return this.getToken(124, 0);
        }

        public TerminalNode RSHIFT_ASSIGN() {
            return this.getToken(127, 0);
        }

        public TerminalNode URSHIFT_ASSIGN() {
            return this.getToken(128, 0);
        }

        public TerminalNode LSHIFT_ASSIGN() {
            return this.getToken(126, 0);
        }

        public TerminalNode MOD_ASSIGN() {
            return this.getToken(125, 0);
        }

        public TerminalNode POWER_ASSIGN() {
            return this.getToken(79, 0);
        }

        public TerminalNode ELVIS_ASSIGN() {
            return this.getToken(129, 0);
        }

        public AssignmentExprAltContext(ExpressionContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitAssignmentExprAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class EnhancedArgumentListInParContext
    extends GroovyParserRuleContext {
        public List<? extends EnhancedArgumentListElementContext> enhancedArgumentListElement() {
            return this.getRuleContexts(EnhancedArgumentListElementContext.class);
        }

        public EnhancedArgumentListElementContext enhancedArgumentListElement(int i) {
            return this.getRuleContext(EnhancedArgumentListElementContext.class, i);
        }

        public List<? extends TerminalNode> COMMA() {
            return this.getTokens(93);
        }

        public TerminalNode COMMA(int i) {
            return this.getToken(93, i);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public EnhancedArgumentListInParContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 153;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitEnhancedArgumentListInPar(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class CommandArgumentContext
    extends GroovyParserRuleContext {
        public PrimaryContext primary() {
            return this.getRuleContext(PrimaryContext.class, 0);
        }

        public EnhancedArgumentListInParContext enhancedArgumentListInPar() {
            return this.getRuleContext(EnhancedArgumentListInParContext.class, 0);
        }

        public List<? extends PathElementContext> pathElement() {
            return this.getRuleContexts(PathElementContext.class);
        }

        public PathElementContext pathElement(int i) {
            return this.getRuleContext(PathElementContext.class, i);
        }

        public CommandArgumentContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 123;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitCommandArgument(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class PrimaryContext
    extends GroovyParserRuleContext {
        public PrimaryContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 130;
        }

        public PrimaryContext() {
        }

        public void copyFrom(PrimaryContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class PathElementContext
    extends GroovyParserRuleContext {
        public int t;

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public TerminalNode DOT() {
            return this.getToken(94, 0);
        }

        public TerminalNode NEW() {
            return this.getToken(38, 0);
        }

        public CreatorContext creator() {
            return this.getRuleContext(CreatorContext.class, 0);
        }

        public NamePartContext namePart() {
            return this.getRuleContext(NamePartContext.class, 0);
        }

        public ClosureOrLambdaExpressionContext closureOrLambdaExpression() {
            return this.getRuleContext(ClosureOrLambdaExpressionContext.class, 0);
        }

        public TerminalNode METHOD_POINTER() {
            return this.getToken(74, 0);
        }

        public TerminalNode METHOD_REFERENCE() {
            return this.getToken(75, 0);
        }

        public TerminalNode SPREAD_DOT() {
            return this.getToken(69, 0);
        }

        public TerminalNode SAFE_DOT() {
            return this.getToken(70, 0);
        }

        public TerminalNode SAFE_CHAIN_DOT() {
            return this.getToken(72, 0);
        }

        public TerminalNode AT() {
            return this.getToken(132, 0);
        }

        public NonWildcardTypeArgumentsContext nonWildcardTypeArguments() {
            return this.getRuleContext(NonWildcardTypeArgumentsContext.class, 0);
        }

        public ArgumentsContext arguments() {
            return this.getRuleContext(ArgumentsContext.class, 0);
        }

        public IndexPropertyArgsContext indexPropertyArgs() {
            return this.getRuleContext(IndexPropertyArgsContext.class, 0);
        }

        public NamedPropertyArgsContext namedPropertyArgs() {
            return this.getRuleContext(NamedPropertyArgsContext.class, 0);
        }

        public PathElementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 125;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitPathElement(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class CreatorContext
    extends GroovyParserRuleContext {
        public int t;

        public CreatedNameContext createdName() {
            return this.getRuleContext(CreatedNameContext.class, 0);
        }

        public NlsContext nls() {
            return this.getRuleContext(NlsContext.class, 0);
        }

        public ArgumentsContext arguments() {
            return this.getRuleContext(ArgumentsContext.class, 0);
        }

        public AnonymousInnerClassDeclarationContext anonymousInnerClassDeclaration() {
            return this.getRuleContext(AnonymousInnerClassDeclarationContext.class, 0);
        }

        public List<? extends DimContext> dim() {
            return this.getRuleContexts(DimContext.class);
        }

        public DimContext dim(int i) {
            return this.getRuleContext(DimContext.class, i);
        }

        public ArrayInitializerContext arrayInitializer() {
            return this.getRuleContext(ArrayInitializerContext.class, 0);
        }

        public CreatorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public CreatorContext(ParserRuleContext parent, int invokingState, int t) {
            super(parent, invokingState);
            this.t = t;
        }

        @Override
        public int getRuleIndex() {
            return 144;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitCreator(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class NonWildcardTypeArgumentsContext
    extends GroovyParserRuleContext {
        public TerminalNode LT() {
            return this.getToken(97, 0);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public TypeListContext typeList() {
            return this.getRuleContext(TypeListContext.class, 0);
        }

        public TerminalNode GT() {
            return this.getToken(96, 0);
        }

        public NonWildcardTypeArgumentsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 149;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitNonWildcardTypeArguments(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class NamePartContext
    extends GroovyParserRuleContext {
        public IdentifierContext identifier() {
            return this.getRuleContext(IdentifierContext.class, 0);
        }

        public StringLiteralContext stringLiteral() {
            return this.getRuleContext(StringLiteralContext.class, 0);
        }

        public DynamicMemberNameContext dynamicMemberName() {
            return this.getRuleContext(DynamicMemberNameContext.class, 0);
        }

        public KeywordsContext keywords() {
            return this.getRuleContext(KeywordsContext.class, 0);
        }

        public NamePartContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 126;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitNamePart(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class IndexPropertyArgsContext
    extends GroovyParserRuleContext {
        public TerminalNode RBRACK() {
            return this.getToken(91, 0);
        }

        public TerminalNode SAFE_INDEX() {
            return this.getToken(71, 0);
        }

        public TerminalNode LBRACK() {
            return this.getToken(90, 0);
        }

        public ExpressionListContext expressionList() {
            return this.getRuleContext(ExpressionListContext.class, 0);
        }

        public IndexPropertyArgsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 128;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitIndexPropertyArgs(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class NamedPropertyArgsContext
    extends GroovyParserRuleContext {
        public TerminalNode RBRACK() {
            return this.getToken(91, 0);
        }

        public TerminalNode SAFE_INDEX() {
            return this.getToken(71, 0);
        }

        public TerminalNode LBRACK() {
            return this.getToken(90, 0);
        }

        public MapEntryListContext mapEntryList() {
            return this.getRuleContext(MapEntryListContext.class, 0);
        }

        public TerminalNode COLON() {
            return this.getToken(101, 0);
        }

        public NamedPropertyArgsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 129;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitNamedPropertyArgs(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class DynamicMemberNameContext
    extends GroovyParserRuleContext {
        public ParExpressionContext parExpression() {
            return this.getRuleContext(ParExpressionContext.class, 0);
        }

        public GstringContext gstring() {
            return this.getRuleContext(GstringContext.class, 0);
        }

        public DynamicMemberNameContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 127;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitDynamicMemberName(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class MapEntryListContext
    extends GroovyParserRuleContext {
        public List<? extends MapEntryContext> mapEntry() {
            return this.getRuleContexts(MapEntryContext.class);
        }

        public MapEntryContext mapEntry(int i) {
            return this.getRuleContext(MapEntryContext.class, i);
        }

        public List<? extends TerminalNode> COMMA() {
            return this.getTokens(93);
        }

        public TerminalNode COMMA(int i) {
            return this.getToken(93, i);
        }

        public MapEntryListContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 136;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitMapEntryList(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class IdentifierPrmrAltContext
    extends PrimaryContext {
        public IdentifierContext identifier() {
            return this.getRuleContext(IdentifierContext.class, 0);
        }

        public TypeArgumentsContext typeArguments() {
            return this.getRuleContext(TypeArgumentsContext.class, 0);
        }

        public IdentifierPrmrAltContext(PrimaryContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitIdentifierPrmrAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class LiteralPrmrAltContext
    extends PrimaryContext {
        public LiteralContext literal() {
            return this.getRuleContext(LiteralContext.class, 0);
        }

        public LiteralPrmrAltContext(PrimaryContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitLiteralPrmrAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class GstringPrmrAltContext
    extends PrimaryContext {
        public GstringContext gstring() {
            return this.getRuleContext(GstringContext.class, 0);
        }

        public GstringPrmrAltContext(PrimaryContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitGstringPrmrAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class NewPrmrAltContext
    extends PrimaryContext {
        public TerminalNode NEW() {
            return this.getToken(38, 0);
        }

        public NlsContext nls() {
            return this.getRuleContext(NlsContext.class, 0);
        }

        public CreatorContext creator() {
            return this.getRuleContext(CreatorContext.class, 0);
        }

        public NewPrmrAltContext(PrimaryContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitNewPrmrAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ThisPrmrAltContext
    extends PrimaryContext {
        public TerminalNode THIS() {
            return this.getToken(53, 0);
        }

        public ThisPrmrAltContext(PrimaryContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitThisPrmrAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class SuperPrmrAltContext
    extends PrimaryContext {
        public TerminalNode SUPER() {
            return this.getToken(50, 0);
        }

        public SuperPrmrAltContext(PrimaryContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitSuperPrmrAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ParenPrmrAltContext
    extends PrimaryContext {
        public ParExpressionContext parExpression() {
            return this.getRuleContext(ParExpressionContext.class, 0);
        }

        public ParenPrmrAltContext(PrimaryContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitParenPrmrAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ClosureOrLambdaExpressionPrmrAltContext
    extends PrimaryContext {
        public ClosureOrLambdaExpressionContext closureOrLambdaExpression() {
            return this.getRuleContext(ClosureOrLambdaExpressionContext.class, 0);
        }

        public ClosureOrLambdaExpressionPrmrAltContext(PrimaryContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitClosureOrLambdaExpressionPrmrAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ListPrmrAltContext
    extends PrimaryContext {
        public ListContext list() {
            return this.getRuleContext(ListContext.class, 0);
        }

        public ListPrmrAltContext(PrimaryContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitListPrmrAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ListContext
    extends GroovyParserRuleContext {
        public TerminalNode LBRACK() {
            return this.getToken(90, 0);
        }

        public TerminalNode RBRACK() {
            return this.getToken(91, 0);
        }

        public ExpressionListContext expressionList() {
            return this.getRuleContext(ExpressionListContext.class, 0);
        }

        public TerminalNode COMMA() {
            return this.getToken(93, 0);
        }

        public ListContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 134;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitList(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class MapPrmrAltContext
    extends PrimaryContext {
        public MapContext map() {
            return this.getRuleContext(MapContext.class, 0);
        }

        public MapPrmrAltContext(PrimaryContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitMapPrmrAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class MapContext
    extends GroovyParserRuleContext {
        public TerminalNode LBRACK() {
            return this.getToken(90, 0);
        }

        public TerminalNode RBRACK() {
            return this.getToken(91, 0);
        }

        public MapEntryListContext mapEntryList() {
            return this.getRuleContext(MapEntryListContext.class, 0);
        }

        public TerminalNode COLON() {
            return this.getToken(101, 0);
        }

        public TerminalNode COMMA() {
            return this.getToken(93, 0);
        }

        public MapContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 135;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitMap(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class BuiltInTypePrmrAltContext
    extends PrimaryContext {
        public BuiltInTypeContext builtInType() {
            return this.getRuleContext(BuiltInTypeContext.class, 0);
        }

        public BuiltInTypePrmrAltContext(PrimaryContext ctx) {
            this.copyFrom(ctx);
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitBuiltInTypePrmrAlt(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class BuiltInTypeContext
    extends GroovyParserRuleContext {
        public TerminalNode BuiltInPrimitiveType() {
            return this.getToken(13, 0);
        }

        public TerminalNode VOID() {
            return this.getToken(58, 0);
        }

        public BuiltInTypeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 160;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitBuiltInType(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class MapEntryContext
    extends GroovyParserRuleContext {
        public MapEntryLabelContext mapEntryLabel() {
            return this.getRuleContext(MapEntryLabelContext.class, 0);
        }

        public TerminalNode COLON() {
            return this.getToken(101, 0);
        }

        public NlsContext nls() {
            return this.getRuleContext(NlsContext.class, 0);
        }

        public ExpressionContext expression() {
            return this.getRuleContext(ExpressionContext.class, 0);
        }

        public TerminalNode MUL() {
            return this.getToken(112, 0);
        }

        public MapEntryContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 138;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitMapEntry(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class MapEntryLabelContext
    extends GroovyParserRuleContext {
        public KeywordsContext keywords() {
            return this.getRuleContext(KeywordsContext.class, 0);
        }

        public PrimaryContext primary() {
            return this.getRuleContext(PrimaryContext.class, 0);
        }

        public MapEntryLabelContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 141;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitMapEntryLabel(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class CreatedNameContext
    extends GroovyParserRuleContext {
        public AnnotationsOptContext annotationsOpt() {
            return this.getRuleContext(AnnotationsOptContext.class, 0);
        }

        public PrimitiveTypeContext primitiveType() {
            return this.getRuleContext(PrimitiveTypeContext.class, 0);
        }

        public QualifiedClassNameContext qualifiedClassName() {
            return this.getRuleContext(QualifiedClassNameContext.class, 0);
        }

        public TypeArgumentsOrDiamondContext typeArgumentsOrDiamond() {
            return this.getRuleContext(TypeArgumentsOrDiamondContext.class, 0);
        }

        public CreatedNameContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 148;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitCreatedName(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class DimContext
    extends GroovyParserRuleContext {
        public AnnotationsOptContext annotationsOpt() {
            return this.getRuleContext(AnnotationsOptContext.class, 0);
        }

        public TerminalNode LBRACK() {
            return this.getToken(90, 0);
        }

        public TerminalNode RBRACK() {
            return this.getToken(91, 0);
        }

        public ExpressionContext expression() {
            return this.getRuleContext(ExpressionContext.class, 0);
        }

        public DimContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 145;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitDim(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class ArrayInitializerContext
    extends GroovyParserRuleContext {
        public TerminalNode LBRACE() {
            return this.getToken(88, 0);
        }

        public List<? extends NlsContext> nls() {
            return this.getRuleContexts(NlsContext.class);
        }

        public NlsContext nls(int i) {
            return this.getRuleContext(NlsContext.class, i);
        }

        public TerminalNode RBRACE() {
            return this.getToken(89, 0);
        }

        public VariableInitializersContext variableInitializers() {
            return this.getRuleContext(VariableInitializersContext.class, 0);
        }

        public ArrayInitializerContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 146;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitArrayInitializer(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class TypeArgumentsOrDiamondContext
    extends GroovyParserRuleContext {
        public TerminalNode LT() {
            return this.getToken(97, 0);
        }

        public TerminalNode GT() {
            return this.getToken(96, 0);
        }

        public TypeArgumentsContext typeArguments() {
            return this.getRuleContext(TypeArgumentsContext.class, 0);
        }

        public TypeArgumentsOrDiamondContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 150;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitTypeArgumentsOrDiamond(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class EnhancedArgumentListElementContext
    extends GroovyParserRuleContext {
        public ExpressionListElementContext expressionListElement() {
            return this.getRuleContext(ExpressionListElementContext.class, 0);
        }

        public MapEntryContext mapEntry() {
            return this.getRuleContext(MapEntryContext.class, 0);
        }

        public StandardLambdaExpressionContext standardLambdaExpression() {
            return this.getRuleContext(StandardLambdaExpressionContext.class, 0);
        }

        public EnhancedArgumentListElementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return 156;
        }

        public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
            if (visitor instanceof GroovyParserVisitor) {
                return ((GroovyParserVisitor)visitor).visitEnhancedArgumentListElement(this);
            }
            return visitor.visitChildren(this);
        }
    }

    public static class GroovyParserRuleContext
    extends ParserRuleContext
    implements NodeMetaDataHandler {
        private Map metaDataMap = null;

        public GroovyParserRuleContext() {
        }

        public GroovyParserRuleContext(ParserRuleContext parent, int invokingStateNumber) {
            super(parent, invokingStateNumber);
        }

        @Override
        public Map<?, ?> getMetaDataMap() {
            return this.metaDataMap;
        }

        @Override
        public void setMetaDataMap(Map<?, ?> metaDataMap) {
            this.metaDataMap = metaDataMap;
        }
    }
}

