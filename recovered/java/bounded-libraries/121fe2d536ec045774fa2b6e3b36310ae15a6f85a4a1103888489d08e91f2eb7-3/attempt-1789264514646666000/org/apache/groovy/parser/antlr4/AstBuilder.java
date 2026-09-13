/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.parser.antlr4;

import groovy.lang.Tuple;
import groovy.lang.Tuple2;
import groovy.lang.Tuple3;
import groovy.transform.CompileStatic;
import groovy.transform.NonSealed;
import groovy.transform.Sealed;
import groovy.transform.Trait;
import groovy.transform.TupleConstructor;
import groovyjarjarantlr4.v4.runtime.ANTLRErrorListener;
import groovyjarjarantlr4.v4.runtime.CharStream;
import groovyjarjarantlr4.v4.runtime.CharStreams;
import groovyjarjarantlr4.v4.runtime.CodePointCharStream;
import groovyjarjarantlr4.v4.runtime.CommonTokenStream;
import groovyjarjarantlr4.v4.runtime.ParserRuleContext;
import groovyjarjarantlr4.v4.runtime.RecognitionException;
import groovyjarjarantlr4.v4.runtime.Recognizer;
import groovyjarjarantlr4.v4.runtime.TokenStream;
import groovyjarjarantlr4.v4.runtime.atn.ParserATNSimulator;
import groovyjarjarantlr4.v4.runtime.atn.PredictionMode;
import groovyjarjarantlr4.v4.runtime.misc.Interval;
import groovyjarjarantlr4.v4.runtime.misc.ParseCancellationException;
import groovyjarjarantlr4.v4.runtime.tree.ParseTree;
import groovyjarjarantlr4.v4.runtime.tree.TerminalNode;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.groovy.parser.antlr4.GroovyLangLexer;
import org.apache.groovy.parser.antlr4.GroovyLangParser;
import org.apache.groovy.parser.antlr4.GroovyParser;
import org.apache.groovy.parser.antlr4.GroovyParserBaseVisitor;
import org.apache.groovy.parser.antlr4.GroovySyntaxError;
import org.apache.groovy.parser.antlr4.GroovydocManager;
import org.apache.groovy.parser.antlr4.ModifierManager;
import org.apache.groovy.parser.antlr4.TryWithResourcesASTTransformation;
import org.apache.groovy.parser.antlr4.internal.DescriptiveErrorStrategy;
import org.apache.groovy.parser.antlr4.internal.atnmanager.AtnManager;
import org.apache.groovy.parser.antlr4.util.PositionConfigureUtils;
import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.apache.groovy.util.Maps;
import org.apache.groovy.util.SystemUtil;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.antlr.EnumHelper;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.CodeVisitorSupport;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.EnumConstantClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.ImportNode;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.ModifierNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.ast.NodeMetaDataHandler;
import org.codehaus.groovy.ast.PackageNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.AnnotationConstantExpression;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ArrayExpression;
import org.codehaus.groovy.ast.expr.AttributeExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.BitwiseNegationExpression;
import org.codehaus.groovy.ast.expr.BooleanExpression;
import org.codehaus.groovy.ast.expr.CastExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ClosureListExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.ElvisOperatorExpression;
import org.codehaus.groovy.ast.expr.EmptyExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.GStringExpression;
import org.codehaus.groovy.ast.expr.LambdaExpression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.MapEntryExpression;
import org.codehaus.groovy.ast.expr.MapExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.MethodPointerExpression;
import org.codehaus.groovy.ast.expr.MethodReferenceExpression;
import org.codehaus.groovy.ast.expr.NamedArgumentListExpression;
import org.codehaus.groovy.ast.expr.NotExpression;
import org.codehaus.groovy.ast.expr.PostfixExpression;
import org.codehaus.groovy.ast.expr.PrefixExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.RangeExpression;
import org.codehaus.groovy.ast.expr.SpreadExpression;
import org.codehaus.groovy.ast.expr.SpreadMapExpression;
import org.codehaus.groovy.ast.expr.TernaryExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.expr.UnaryMinusExpression;
import org.codehaus.groovy.ast.expr.UnaryPlusExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.AssertStatement;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.BreakStatement;
import org.codehaus.groovy.ast.stmt.CaseStatement;
import org.codehaus.groovy.ast.stmt.CatchStatement;
import org.codehaus.groovy.ast.stmt.ContinueStatement;
import org.codehaus.groovy.ast.stmt.DoWhileStatement;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ForStatement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.SwitchStatement;
import org.codehaus.groovy.ast.stmt.SynchronizedStatement;
import org.codehaus.groovy.ast.stmt.ThrowStatement;
import org.codehaus.groovy.ast.stmt.TryCatchStatement;
import org.codehaus.groovy.ast.stmt.WhileStatement;
import org.codehaus.groovy.ast.tools.ClosureUtils;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.classgen.Verifier;
import org.codehaus.groovy.classgen.asm.util.TypeUtil;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.messages.SyntaxErrorMessage;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.codehaus.groovy.syntax.Numbers;
import org.codehaus.groovy.syntax.SyntaxException;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.syntax.Types;

public class AstBuilder
extends GroovyParserBaseVisitor<Object> {
    private int switchExpressionVariableSeq;
    private static final Map<ClassNode, Object> TYPE_DEFAULT_VALUE_MAP = Maps.of(ClassHelper.int_TYPE, 0, ClassHelper.long_TYPE, 0L, ClassHelper.double_TYPE, 0.0, ClassHelper.float_TYPE, Float.valueOf(0.0f), ClassHelper.short_TYPE, (short)0, ClassHelper.byte_TYPE, (byte)0, ClassHelper.char_TYPE, Character.valueOf('\u0000'), ClassHelper.boolean_TYPE, Boolean.FALSE);
    private final ModuleNode moduleNode;
    private final SourceUnit sourceUnit;
    private final GroovyLangLexer lexer;
    private final GroovyLangParser parser;
    private final GroovydocManager groovydocManager;
    private final TryWithResourcesASTTransformation tryWithResourcesASTTransformation;
    private final List<ClassNode> classNodeList = new LinkedList<ClassNode>();
    private final Deque<ClassNode> classNodeStack = new ArrayDeque<ClassNode>();
    private final Deque<List<InnerClassNode>> anonymousInnerClassesDefinedInMethodStack = new ArrayDeque<List<InnerClassNode>>();
    private final Deque<GroovyParser.GroovyParserRuleContext> switchExpressionRuleContextStack = new ArrayDeque<GroovyParser.GroovyParserRuleContext>();
    private Tuple2<GroovyParser.GroovyParserRuleContext, Exception> numberFormatError;
    private int visitingClosureCount;
    private int visitingLoopStatementCount;
    private int visitingSwitchStatementCount;
    private int visitingAssertStatementCount;
    private int visitingArrayInitializerCount;
    private static final int SLL_THRESHOLD = SystemUtil.getIntegerSafe("groovy.antlr4.sll.threshold", -1);
    private static final String QUESTION_STR = "?";
    private static final String DOT_STR = ".";
    private static final String SUB_STR = "-";
    private static final String ASSIGN_STR = "=";
    private static final String VALUE_STR = "value";
    private static final String DOLLAR_STR = "$";
    private static final String CALL_STR = "call";
    private static final String THIS_STR = "this";
    private static final String SUPER_STR = "super";
    private static final String VOID_STR = "void";
    private static final String SLASH_STR = "/";
    private static final String SLASH_DOLLAR_STR = "/$";
    private static final String TDQ_STR = "\"\"\"";
    private static final String TSQ_STR = "'''";
    private static final String SQ_STR = "'";
    private static final String DQ_STR = "\"";
    private static final String DOLLAR_SLASH_STR = "$/";
    private static final Map<String, String> QUOTATION_MAP = Maps.of("\"", "\"", "'", "'", "\"\"\"", "\"\"\"", "'''", "'''", "/", "/", "$/", "/$");
    private static final String PACKAGE_INFO = "package-info";
    private static final String PACKAGE_INFO_FILE_NAME = "package-info.groovy";
    private static final String CLASS_NAME = "_CLASS_NAME";
    private static final String INSIDE_PARENTHESES_LEVEL = "_INSIDE_PARENTHESES_LEVEL";
    private static final String IS_INSIDE_INSTANCEOF_EXPR = "_IS_INSIDE_INSTANCEOF_EXPR";
    private static final String IS_SWITCH_DEFAULT = "_IS_SWITCH_DEFAULT";
    private static final String IS_NUMERIC = "_IS_NUMERIC";
    private static final String IS_STRING = "_IS_STRING";
    private static final String IS_INTERFACE_WITH_DEFAULT_METHODS = "_IS_INTERFACE_WITH_DEFAULT_METHODS";
    private static final String IS_INSIDE_CONDITIONAL_EXPRESSION = "_IS_INSIDE_CONDITIONAL_EXPRESSION";
    private static final String IS_COMMAND_EXPRESSION = "_IS_COMMAND_EXPRESSION";
    private static final String IS_BUILT_IN_TYPE = "_IS_BUILT_IN_TYPE";
    private static final String PATH_EXPRESSION_BASE_EXPR = "_PATH_EXPRESSION_BASE_EXPR";
    private static final String PATH_EXPRESSION_BASE_EXPR_GENERICS_TYPES = "_PATH_EXPRESSION_BASE_EXPR_GENERICS_TYPES";
    private static final String PATH_EXPRESSION_BASE_EXPR_SAFE_CHAIN = "_PATH_EXPRESSION_BASE_EXPR_SAFE_CHAIN";
    private static final String CMD_EXPRESSION_BASE_EXPR = "_CMD_EXPRESSION_BASE_EXPR";
    private static final String TYPE_DECLARATION_MODIFIERS = "_TYPE_DECLARATION_MODIFIERS";
    private static final String COMPACT_CONSTRUCTOR_DECLARATION_MODIFIERS = "_COMPACT_CONSTRUCTOR_DECLARATION_MODIFIERS";
    private static final String CLASS_DECLARATION_CLASS_NODE = "_CLASS_DECLARATION_CLASS_NODE";
    private static final String VARIABLE_DECLARATION_VARIABLE_TYPE = "_VARIABLE_DECLARATION_VARIABLE_TYPE";
    private static final String ANONYMOUS_INNER_CLASS_SUPER_CLASS = "_ANONYMOUS_INNER_CLASS_SUPER_CLASS";
    private static final String INTEGER_LITERAL_TEXT = "_INTEGER_LITERAL_TEXT";
    private static final String FLOATING_POINT_LITERAL_TEXT = "_FLOATING_POINT_LITERAL_TEXT";
    private static final String ENCLOSING_INSTANCE_EXPRESSION = "_ENCLOSING_INSTANCE_EXPRESSION";
    private static final String IS_YIELD_STATEMENT = "_IS_YIELD_STATEMENT";
    private static final String PARAMETER_MODIFIER_MANAGER = "_PARAMETER_MODIFIER_MANAGER";
    private static final String PARAMETER_CONTEXT = "_PARAMETER_CONTEXT";
    private static final String IS_RECORD_GENERATED = "_IS_RECORD_GENERATED";
    private static final String RECORD_HEADER = "_RECORD_HEADER";
    private static final String RECORD_TYPE_NAME = "groovy.transform.RecordType";
    private static final ClassNode RECORD_TYPE_CLASS = ClassHelper.makeWithoutCaching("groovy.transform.RecordType");

    public AstBuilder(SourceUnit sourceUnit, boolean groovydocEnabled, boolean runtimeGroovydocEnabled) {
        this.sourceUnit = sourceUnit;
        this.moduleNode = new ModuleNode(sourceUnit);
        CharStream charStream = this.createCharStream(sourceUnit);
        this.lexer = new GroovyLangLexer(charStream);
        this.parser = new GroovyLangParser(new CommonTokenStream(this.lexer));
        this.parser.setErrorHandler(new DescriptiveErrorStrategy(charStream));
        this.groovydocManager = new GroovydocManager(groovydocEnabled, runtimeGroovydocEnabled);
        this.tryWithResourcesASTTransformation = new TryWithResourcesASTTransformation(this);
    }

    private CharStream createCharStream(SourceUnit sourceUnit) {
        CodePointCharStream charStream;
        try {
            charStream = CharStreams.fromReader(new BufferedReader(sourceUnit.getSource().getReader()), sourceUnit.getName());
        }
        catch (IOException e) {
            throw new RuntimeException("Error occurred when reading source code.", e);
        }
        return charStream;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private GroovyParser.GroovyParserRuleContext buildCST() throws CompilationFailedException {
        GroovyParser.GroovyParserRuleContext result;
        try {
            AtnManager.READ_LOCK.lock();
            try {
                TokenStream tokenStream = this.parser.getInputStream();
                if (SLL_THRESHOLD >= 0 && tokenStream.size() > SLL_THRESHOLD) {
                    result = this.buildCST(PredictionMode.LL);
                } else {
                    try {
                        result = this.buildCST(PredictionMode.SLL);
                    }
                    catch (Throwable t) {
                        if (t instanceof GroovySyntaxError && 0 == ((GroovySyntaxError)((Object)t)).getSource()) {
                            throw t;
                        }
                        tokenStream.seek(0);
                        result = this.buildCST(PredictionMode.LL);
                    }
                }
            }
            finally {
                AtnManager.READ_LOCK.unlock();
            }
        }
        catch (Throwable t) {
            throw this.convertException(t);
        }
        return result;
    }

    private GroovyParser.GroovyParserRuleContext buildCST(PredictionMode predictionMode) {
        ((ParserATNSimulator)this.parser.getInterpreter()).setPredictionMode(predictionMode);
        if (PredictionMode.SLL.equals((Object)predictionMode)) {
            this.removeErrorListeners();
        } else {
            this.addErrorListeners();
        }
        return this.parser.compilationUnit();
    }

    private CompilationFailedException convertException(Throwable t) {
        CompilationFailedException cfe = t instanceof CompilationFailedException ? (CompilationFailedException)t : (t instanceof ParseCancellationException ? this.createParsingFailedException(t.getCause()) : this.createParsingFailedException(t));
        return cfe;
    }

    public ModuleNode buildAST() {
        try {
            return (ModuleNode)this.visit(this.buildCST());
        }
        catch (Throwable t) {
            throw this.convertException(t);
        }
    }

    @Override
    public ModuleNode visitCompilationUnit(GroovyParser.CompilationUnitContext ctx) {
        this.visit(ctx.packageDeclaration());
        Iterator<Object> iterator = this.visitScriptStatements(ctx.scriptStatements()).iterator();
        while (iterator.hasNext()) {
            ASTNode aSTNode = (ASTNode)iterator.next();
            if (aSTNode instanceof DeclarationListStatement) {
                for (Statement statement : ((DeclarationListStatement)aSTNode).getDeclarationStatements()) {
                    this.moduleNode.addStatement(statement);
                }
                continue;
            }
            if (aSTNode instanceof Statement) {
                this.moduleNode.addStatement((Statement)aSTNode);
                continue;
            }
            if (!(aSTNode instanceof MethodNode)) continue;
            this.moduleNode.addMethod((MethodNode)aSTNode);
        }
        for (ClassNode classNode : this.classNodeList) {
            this.moduleNode.addClass(classNode);
        }
        if (this.isPackageInfoDeclaration()) {
            ClassNode packageInfo = ClassHelper.make(this.moduleNode.getPackageName() + PACKAGE_INFO);
            if (!this.moduleNode.getClasses().contains(packageInfo)) {
                this.moduleNode.addClass(packageInfo);
            }
        } else if (this.isBlankScript()) {
            this.moduleNode.addStatement(ReturnStatement.RETURN_NULL_OR_VOID);
        }
        this.configureScriptClassNode();
        if (this.numberFormatError != null) {
            throw this.createParsingFailedException(this.numberFormatError.getV2().getMessage(), this.numberFormatError.getV1());
        }
        return this.moduleNode;
    }

    @Override
    public List<ASTNode> visitScriptStatements(GroovyParser.ScriptStatementsContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return Collections.emptyList();
        }
        return ctx.scriptStatement().stream().map(e -> (ASTNode)this.visit((ParseTree)e)).collect(Collectors.toList());
    }

    @Override
    public PackageNode visitPackageDeclaration(GroovyParser.PackageDeclarationContext ctx) {
        String packageName = this.visitQualifiedName(ctx.qualifiedName());
        this.moduleNode.setPackageName(packageName + DOT_STR);
        PackageNode packageNode = this.moduleNode.getPackage();
        packageNode.addAnnotations((List<AnnotationNode>)this.visitAnnotationsOpt(ctx.annotationsOpt()));
        return PositionConfigureUtils.configureAST(packageNode, ctx);
    }

    @Override
    public ImportNode visitImportDeclaration(GroovyParser.ImportDeclarationContext ctx) {
        ImportNode importNode;
        boolean hasStatic = DefaultGroovyMethods.asBoolean(ctx.STATIC());
        boolean hasStar = DefaultGroovyMethods.asBoolean(ctx.MUL());
        boolean hasAlias = DefaultGroovyMethods.asBoolean(ctx.alias);
        Object annotationNodeList = this.visitAnnotationsOpt(ctx.annotationsOpt());
        if (hasStatic) {
            if (hasStar) {
                String qualifiedName = this.visitQualifiedName(ctx.qualifiedName());
                ClassNode type = AstBuilder.makeClassNode(qualifiedName);
                PositionConfigureUtils.configureAST(type, ctx);
                this.moduleNode.addStaticStarImport(type.getText(), type, (List<AnnotationNode>)annotationNodeList);
                importNode = DefaultGroovyMethods.last(this.moduleNode.getStaticStarImports().values());
            } else {
                List<? extends GroovyParser.QualifiedNameElementContext> identifierList = ctx.qualifiedName().qualifiedNameElement();
                int identifierListSize = identifierList.size();
                String name = identifierList.get(identifierListSize - 1).getText();
                ClassNode classNode = AstBuilder.makeClassNode(identifierList.stream().limit(identifierListSize - 1).map(ParseTree::getText).collect(Collectors.joining(DOT_STR)));
                String alias = hasAlias ? ctx.alias.getText() : name;
                PositionConfigureUtils.configureAST(classNode, ctx);
                this.moduleNode.addStaticImport(classNode, name, alias, (List<AnnotationNode>)annotationNodeList);
                importNode = DefaultGroovyMethods.last(this.moduleNode.getStaticImports().values());
            }
        } else if (hasStar) {
            String qualifiedName = this.visitQualifiedName(ctx.qualifiedName());
            this.moduleNode.addStarImport(qualifiedName + DOT_STR, (List<AnnotationNode>)annotationNodeList);
            importNode = DefaultGroovyMethods.last(this.moduleNode.getStarImports());
        } else {
            String qualifiedName = this.visitQualifiedName(ctx.qualifiedName());
            String name = DefaultGroovyMethods.last(ctx.qualifiedName().qualifiedNameElement()).getText();
            ClassNode classNode = AstBuilder.makeClassNode(qualifiedName);
            String alias = hasAlias ? ctx.alias.getText() : name;
            PositionConfigureUtils.configureAST(classNode, ctx);
            this.moduleNode.addImport(alias, classNode, (List<AnnotationNode>)annotationNodeList);
            importNode = DefaultGroovyMethods.last(this.moduleNode.getImports());
        }
        return PositionConfigureUtils.configureAST(importNode, ctx);
    }

    private static AnnotationNode makeAnnotationNode(Class<? extends Annotation> type) {
        AnnotationNode node = new AnnotationNode(ClassHelper.make(type));
        return node;
    }

    private static ClassNode makeClassNode(String name) {
        ClassNode node = ClassHelper.make(name);
        return node;
    }

    @Override
    public AssertStatement visitAssertStatement(GroovyParser.AssertStatementContext ctx) {
        BinaryExpression binaryExpression;
        ++this.visitingAssertStatementCount;
        Expression conditionExpression = (Expression)this.visit(ctx.ce);
        if (conditionExpression instanceof BinaryExpression && (binaryExpression = (BinaryExpression)conditionExpression).getOperation().getType() == 100) {
            throw this.createParsingFailedException("Assignment expression is not allowed in the assert statement", conditionExpression);
        }
        BooleanExpression booleanExpression = PositionConfigureUtils.configureAST(new BooleanExpression(conditionExpression), conditionExpression);
        if (!DefaultGroovyMethods.asBoolean(ctx.me)) {
            return PositionConfigureUtils.configureAST(new AssertStatement(booleanExpression), ctx);
        }
        AssertStatement result = PositionConfigureUtils.configureAST(new AssertStatement(booleanExpression, (Expression)this.visit(ctx.me)), ctx);
        --this.visitingAssertStatementCount;
        return result;
    }

    @Override
    public Statement visitConditionalStatement(GroovyParser.ConditionalStatementContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.ifElseStatement())) {
            return PositionConfigureUtils.configureAST(this.visitIfElseStatement(ctx.ifElseStatement()), ctx);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.switchStatement())) {
            return PositionConfigureUtils.configureAST(this.visitSwitchStatement(ctx.switchStatement()), ctx);
        }
        throw this.createParsingFailedException("Unsupported conditional statement", ctx);
    }

    @Override
    public IfStatement visitIfElseStatement(GroovyParser.IfElseStatementContext ctx) {
        Expression conditionExpression = this.visitExpressionInPar(ctx.expressionInPar());
        BooleanExpression booleanExpression = PositionConfigureUtils.configureAST(new BooleanExpression(conditionExpression), conditionExpression);
        Statement ifBlock = this.unpackStatement((Statement)this.visit(ctx.tb));
        Statement elseBlock = this.unpackStatement(DefaultGroovyMethods.asBoolean(ctx.ELSE()) ? (Statement)this.visit(ctx.fb) : EmptyStatement.INSTANCE);
        return PositionConfigureUtils.configureAST(new IfStatement(booleanExpression, ifBlock, elseBlock), ctx);
    }

    @Override
    public Statement visitLoopStmtAlt(GroovyParser.LoopStmtAltContext ctx) {
        this.switchExpressionRuleContextStack.push(ctx);
        ++this.visitingLoopStatementCount;
        try {
            Statement statement = PositionConfigureUtils.configureAST((Statement)this.visit(ctx.loopStatement()), ctx);
            return statement;
        }
        finally {
            this.switchExpressionRuleContextStack.pop();
            --this.visitingLoopStatementCount;
        }
    }

    @Override
    public ForStatement visitForStmtAlt(GroovyParser.ForStmtAltContext ctx) {
        Object controlTuple = this.visitForControl(ctx.forControl());
        Statement loopBlock = this.unpackStatement((Statement)this.visit(ctx.statement()));
        return PositionConfigureUtils.configureAST(new ForStatement((Parameter)((Tuple2)controlTuple).getV1(), (Expression)((Tuple2)controlTuple).getV2(), DefaultGroovyMethods.asBoolean(loopBlock) ? loopBlock : EmptyStatement.INSTANCE), ctx);
    }

    @Override
    public Tuple2<Parameter, Expression> visitForControl(GroovyParser.ForControlContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.enhancedForControl())) {
            return this.visitEnhancedForControl(ctx.enhancedForControl());
        }
        if (DefaultGroovyMethods.asBoolean(ctx.classicalForControl())) {
            return this.visitClassicalForControl(ctx.classicalForControl());
        }
        throw this.createParsingFailedException("Unsupported for control: " + ctx.getText(), ctx);
    }

    @Override
    public Expression visitForInit(GroovyParser.ForInitContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return EmptyExpression.INSTANCE;
        }
        if (DefaultGroovyMethods.asBoolean(ctx.localVariableDeclaration())) {
            DeclarationListStatement declarationListStatement = this.visitLocalVariableDeclaration(ctx.localVariableDeclaration());
            List<Expression> declarationExpressions = declarationListStatement.getDeclarationExpressions();
            if (declarationExpressions.size() == 1) {
                return PositionConfigureUtils.configureAST((Expression)declarationExpressions.get(0), ctx);
            }
            return PositionConfigureUtils.configureAST(new ClosureListExpression(declarationExpressions), ctx);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.expressionList())) {
            return this.translateExpressionList(ctx.expressionList());
        }
        throw this.createParsingFailedException("Unsupported for init: " + ctx.getText(), ctx);
    }

    @Override
    public Expression visitForUpdate(GroovyParser.ForUpdateContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return EmptyExpression.INSTANCE;
        }
        return this.translateExpressionList(ctx.expressionList());
    }

    private Expression translateExpressionList(GroovyParser.ExpressionListContext ctx) {
        Object expressionList = this.visitExpressionList(ctx);
        if (expressionList.size() == 1) {
            return PositionConfigureUtils.configureAST((Expression)expressionList.get(0), ctx);
        }
        return PositionConfigureUtils.configureAST(new ClosureListExpression((List<Expression>)expressionList), ctx);
    }

    @Override
    public Tuple2<Parameter, Expression> visitEnhancedForControl(GroovyParser.EnhancedForControlContext ctx) {
        Parameter parameter = new Parameter(this.visitType(ctx.type()), this.visitVariableDeclaratorId(ctx.variableDeclaratorId()).getName());
        return Tuple.tuple(PositionConfigureUtils.configureAST(parameter, ctx.variableDeclaratorId()), (Expression)this.visit(ctx.expression()));
    }

    @Override
    public Tuple2<Parameter, Expression> visitClassicalForControl(GroovyParser.ClassicalForControlContext ctx) {
        ClosureListExpression closureListExpression = new ClosureListExpression();
        closureListExpression.addExpression(this.visitForInit(ctx.forInit()));
        closureListExpression.addExpression(DefaultGroovyMethods.asBoolean(ctx.expression()) ? (Expression)this.visit(ctx.expression()) : EmptyExpression.INSTANCE);
        closureListExpression.addExpression(this.visitForUpdate(ctx.forUpdate()));
        return Tuple.tuple(ForStatement.FOR_LOOP_DUMMY, closureListExpression);
    }

    @Override
    public WhileStatement visitWhileStmtAlt(GroovyParser.WhileStmtAltContext ctx) {
        Tuple2<BooleanExpression, Statement> conditionAndBlock;
        return PositionConfigureUtils.configureAST(new WhileStatement(conditionAndBlock.getV1(), DefaultGroovyMethods.asBoolean((conditionAndBlock = this.createLoopConditionExpressionAndBlock(ctx.expressionInPar(), ctx.statement())).getV2()) ? conditionAndBlock.getV2() : EmptyStatement.INSTANCE), ctx);
    }

    @Override
    public DoWhileStatement visitDoWhileStmtAlt(GroovyParser.DoWhileStmtAltContext ctx) {
        Tuple2<BooleanExpression, Statement> conditionAndBlock;
        return PositionConfigureUtils.configureAST(new DoWhileStatement(conditionAndBlock.getV1(), DefaultGroovyMethods.asBoolean((conditionAndBlock = this.createLoopConditionExpressionAndBlock(ctx.expressionInPar(), ctx.statement())).getV2()) ? conditionAndBlock.getV2() : EmptyStatement.INSTANCE), ctx);
    }

    private Tuple2<BooleanExpression, Statement> createLoopConditionExpressionAndBlock(GroovyParser.ExpressionInParContext eipc, GroovyParser.StatementContext sc) {
        Expression conditionExpression = this.visitExpressionInPar(eipc);
        BooleanExpression booleanExpression = PositionConfigureUtils.configureAST(new BooleanExpression(conditionExpression), conditionExpression);
        Statement loopBlock = this.unpackStatement((Statement)this.visit(sc));
        return Tuple.tuple(booleanExpression, loopBlock);
    }

    @Override
    public Statement visitTryCatchStatement(GroovyParser.TryCatchStatementContext ctx) {
        boolean resourcesExists = DefaultGroovyMethods.asBoolean(ctx.resources());
        boolean catchExists = DefaultGroovyMethods.asBoolean(ctx.catchClause());
        boolean finallyExists = DefaultGroovyMethods.asBoolean(ctx.finallyBlock());
        if (!(resourcesExists || catchExists || finallyExists)) {
            throw this.createParsingFailedException("Either a catch or finally clause or both is required for a try-catch-finally statement", ctx);
        }
        TryCatchStatement tryCatchStatement = new TryCatchStatement((Statement)this.visit(ctx.block()), this.visitFinallyBlock(ctx.finallyBlock()));
        if (resourcesExists) {
            this.visitResources(ctx.resources()).forEach(tryCatchStatement::addResource);
        }
        ctx.catchClause().stream().map(catchClauseContext -> this.visitCatchClause((GroovyParser.CatchClauseContext)catchClauseContext)).reduce(new LinkedList(), (r, e) -> {
            r.addAll(e);
            return r;
        }).forEach(tryCatchStatement::addCatch);
        return PositionConfigureUtils.configureAST(this.tryWithResourcesASTTransformation.transform(PositionConfigureUtils.configureAST(tryCatchStatement, ctx)), ctx);
    }

    @Override
    public List<ExpressionStatement> visitResources(GroovyParser.ResourcesContext ctx) {
        return this.visitResourceList(ctx.resourceList());
    }

    @Override
    public List<ExpressionStatement> visitResourceList(GroovyParser.ResourceListContext ctx) {
        return ctx.resource().stream().map(this::visitResource).collect(Collectors.toList());
    }

    @Override
    public ExpressionStatement visitResource(GroovyParser.ResourceContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.localVariableDeclaration())) {
            List<ExpressionStatement> declarationStatements = this.visitLocalVariableDeclaration(ctx.localVariableDeclaration()).getDeclarationStatements();
            if (declarationStatements.size() > 1) {
                throw this.createParsingFailedException("Multi resources can not be declared in one statement", ctx);
            }
            return declarationStatements.get(0);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.expression())) {
            BinaryExpression assignmentExpression;
            Expression expression = (Expression)this.visit(ctx.expression());
            boolean isVariableDeclaration = expression instanceof BinaryExpression && 100 == ((BinaryExpression)expression).getOperation().getType() && ((BinaryExpression)expression).getLeftExpression() instanceof VariableExpression;
            boolean isVariableAccess = expression instanceof VariableExpression;
            if (!isVariableDeclaration && !isVariableAccess) {
                throw this.createParsingFailedException("Only variable declarations or variable access are allowed to declare resource", ctx);
            }
            if (isVariableDeclaration) {
                assignmentExpression = (BinaryExpression)expression;
            } else if (isVariableAccess) {
                assignmentExpression = this.tryWithResourcesASTTransformation.transformResourceAccess(expression);
            } else {
                throw this.createParsingFailedException("Unsupported resource declaration", ctx);
            }
            return PositionConfigureUtils.configureAST(new ExpressionStatement(PositionConfigureUtils.configureAST(new DeclarationExpression(PositionConfigureUtils.configureAST(new VariableExpression(assignmentExpression.getLeftExpression().getText()), assignmentExpression.getLeftExpression()), assignmentExpression.getOperation(), assignmentExpression.getRightExpression()), ctx)), ctx);
        }
        throw this.createParsingFailedException("Unsupported resource declaration: " + ctx.getText(), ctx);
    }

    @Override
    public List<CatchStatement> visitCatchClause(GroovyParser.CatchClauseContext ctx) {
        return this.visitCatchType(ctx.catchType()).stream().map(e -> PositionConfigureUtils.configureAST(new CatchStatement(new Parameter((ClassNode)e, this.visitIdentifier(ctx.identifier())), this.visitBlock(ctx.block())), ctx)).collect(Collectors.toList());
    }

    @Override
    public List<ClassNode> visitCatchType(GroovyParser.CatchTypeContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return Collections.singletonList(ClassHelper.dynamicType());
        }
        return ctx.qualifiedClassName().stream().map(this::visitQualifiedClassName).collect(Collectors.toList());
    }

    @Override
    public Statement visitFinallyBlock(GroovyParser.FinallyBlockContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return EmptyStatement.INSTANCE;
        }
        return PositionConfigureUtils.configureAST(this.createBlockStatement((Statement)this.visit(ctx.block())), ctx);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public SwitchStatement visitSwitchStatement(GroovyParser.SwitchStatementContext ctx) {
        this.switchExpressionRuleContextStack.push(ctx);
        ++this.visitingSwitchStatementCount;
        try {
            List statementList = ctx.switchBlockStatementGroup().stream().map(switchBlockStatementGroupContext -> this.visitSwitchBlockStatementGroup((GroovyParser.SwitchBlockStatementGroupContext)switchBlockStatementGroupContext)).reduce(new LinkedList(), (r, e) -> {
                r.addAll(e);
                return r;
            });
            LinkedList<CaseStatement> caseStatementList = new LinkedList<CaseStatement>();
            LinkedList<Statement> defaultStatementList = new LinkedList<Statement>();
            for (Statement e2 : statementList) {
                if (e2 instanceof CaseStatement) {
                    caseStatementList.add((CaseStatement)e2);
                    continue;
                }
                if (!this.isTrue(e2, IS_SWITCH_DEFAULT)) continue;
                defaultStatementList.add(e2);
            }
            int defaultStatementListSize = defaultStatementList.size();
            if (defaultStatementListSize > 1) {
                throw this.createParsingFailedException("a switch must only have one default branch", (ASTNode)defaultStatementList.get(0));
            }
            if (defaultStatementListSize > 0 && DefaultGroovyMethods.last(statementList) instanceof CaseStatement) {
                throw this.createParsingFailedException("a default branch must only appear as the last branch of a switch", (ASTNode)defaultStatementList.get(0));
            }
            SwitchStatement switchStatement = PositionConfigureUtils.configureAST(new SwitchStatement(this.visitExpressionInPar(ctx.expressionInPar()), caseStatementList, defaultStatementListSize == 0 ? EmptyStatement.INSTANCE : (Statement)defaultStatementList.get(0)), ctx);
            return switchStatement;
        }
        finally {
            this.switchExpressionRuleContextStack.pop();
            --this.visitingSwitchStatementCount;
        }
    }

    @Override
    public List<Statement> visitSwitchBlockStatementGroup(GroovyParser.SwitchBlockStatementGroupContext ctx) {
        int labelCount = ctx.switchLabel().size();
        ArrayList firstLabelHolder = new ArrayList(1);
        return (List)ctx.switchLabel().stream().map(e -> this.visitSwitchLabel((GroovyParser.SwitchLabelContext)e)).reduce(new ArrayList(4), (r, e) -> {
            List statementList = (List)r;
            Tuple2 tuple = (Tuple2)e;
            switch (((groovyjarjarantlr4.v4.runtime.Token)tuple.getV1()).getType()) {
                case 18: {
                    if (!DefaultGroovyMethods.asBoolean(statementList)) {
                        firstLabelHolder.add((groovyjarjarantlr4.v4.runtime.Token)tuple.getV1());
                    }
                    CaseStatement statement = new CaseStatement((Expression)tuple.getV2(), statementList.size() == labelCount - 1 ? this.visitBlockStatements(ctx.blockStatements()) : EmptyStatement.INSTANCE);
                    statementList.add((Statement)PositionConfigureUtils.configureAST(statement, (groovyjarjarantlr4.v4.runtime.Token)firstLabelHolder.get(0)));
                    break;
                }
                case 23: {
                    BlockStatement statement = this.visitBlockStatements(ctx.blockStatements());
                    statement.putNodeMetaData(IS_SWITCH_DEFAULT, Boolean.TRUE);
                    statementList.add(statement);
                }
            }
            return statementList;
        });
    }

    @Override
    public Tuple2<groovyjarjarantlr4.v4.runtime.Token, Expression> visitSwitchLabel(GroovyParser.SwitchLabelContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.CASE())) {
            return Tuple.tuple(ctx.CASE().getSymbol(), (Expression)this.visit(ctx.expression()));
        }
        if (DefaultGroovyMethods.asBoolean(ctx.DEFAULT())) {
            return Tuple.tuple(ctx.DEFAULT().getSymbol(), EmptyExpression.INSTANCE);
        }
        throw this.createParsingFailedException("Unsupported switch label: " + ctx.getText(), ctx);
    }

    @Override
    public SynchronizedStatement visitSynchronizedStmtAlt(GroovyParser.SynchronizedStmtAltContext ctx) {
        return PositionConfigureUtils.configureAST(new SynchronizedStatement(this.visitExpressionInPar(ctx.expressionInPar()), this.visitBlock(ctx.block())), ctx);
    }

    @Override
    public ReturnStatement visitReturnStmtAlt(GroovyParser.ReturnStmtAltContext ctx) {
        if (this.switchExpressionRuleContextStack.peek() instanceof GroovyParser.SwitchExpressionContext) {
            throw this.createParsingFailedException("switch expression does not support `return`", ctx);
        }
        return PositionConfigureUtils.configureAST(new ReturnStatement(DefaultGroovyMethods.asBoolean(ctx.expression()) ? (Expression)this.visit(ctx.expression()) : ConstantExpression.EMPTY_EXPRESSION), ctx);
    }

    @Override
    public ThrowStatement visitThrowStmtAlt(GroovyParser.ThrowStmtAltContext ctx) {
        return PositionConfigureUtils.configureAST(new ThrowStatement((Expression)this.visit(ctx.expression())), ctx);
    }

    @Override
    public Statement visitLabeledStmtAlt(GroovyParser.LabeledStmtAltContext ctx) {
        Statement statement = (Statement)this.visit(ctx.statement());
        statement.addStatementLabel(this.visitIdentifier(ctx.identifier()));
        return statement;
    }

    @Override
    public BreakStatement visitBreakStatement(GroovyParser.BreakStatementContext ctx) {
        if (this.visitingLoopStatementCount == 0 && this.visitingSwitchStatementCount == 0) {
            throw this.createParsingFailedException("break statement is only allowed inside loops or switches", ctx);
        }
        if (this.switchExpressionRuleContextStack.peek() instanceof GroovyParser.SwitchExpressionContext) {
            throw this.createParsingFailedException("switch expression does not support `break`", ctx);
        }
        String label = DefaultGroovyMethods.asBoolean(ctx.identifier()) ? this.visitIdentifier(ctx.identifier()) : null;
        return PositionConfigureUtils.configureAST(new BreakStatement(label), ctx);
    }

    @Override
    public ReturnStatement visitYieldStatement(GroovyParser.YieldStatementContext ctx) {
        ReturnStatement returnStatement = (ReturnStatement)GeneralUtils.returnS((Expression)this.visit(ctx.expression()));
        returnStatement.putNodeMetaData(IS_YIELD_STATEMENT, Boolean.TRUE);
        return PositionConfigureUtils.configureAST(returnStatement, ctx);
    }

    @Override
    public ReturnStatement visitYieldStmtAlt(GroovyParser.YieldStmtAltContext ctx) {
        return PositionConfigureUtils.configureAST(this.visitYieldStatement(ctx.yieldStatement()), ctx);
    }

    @Override
    public ContinueStatement visitContinueStatement(GroovyParser.ContinueStatementContext ctx) {
        if (this.visitingLoopStatementCount == 0) {
            throw this.createParsingFailedException("continue statement is only allowed inside loops", ctx);
        }
        if (this.switchExpressionRuleContextStack.peek() instanceof GroovyParser.SwitchExpressionContext) {
            throw this.createParsingFailedException("switch expression does not support `continue`", ctx);
        }
        String label = DefaultGroovyMethods.asBoolean(ctx.identifier()) ? this.visitIdentifier(ctx.identifier()) : null;
        return PositionConfigureUtils.configureAST(new ContinueStatement(label), ctx);
    }

    @Override
    public Expression visitSwitchExprAlt(GroovyParser.SwitchExprAltContext ctx) {
        return PositionConfigureUtils.configureAST(this.visitSwitchExpression(ctx.switchExpression()), ctx);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public MethodCallExpression visitSwitchExpression(GroovyParser.SwitchExpressionContext ctx) {
        this.switchExpressionRuleContextStack.push(ctx);
        try {
            this.validateSwitchExpressionLabels(ctx);
            List statementInfoList = ctx.switchBlockStatementExpressionGroup().stream().map(e -> this.visitSwitchBlockStatementExpressionGroup((GroovyParser.SwitchBlockStatementExpressionGroupContext)e)).collect(Collectors.toList());
            if (statementInfoList.isEmpty()) {
                throw this.createParsingFailedException("`case` or `default` branches are expected", ctx.LBRACE());
            }
            Boolean isArrow = (Boolean)((Tuple3)statementInfoList.get(0)).getV2();
            if (!isArrow.booleanValue() && statementInfoList.stream().noneMatch(e -> {
                Boolean hasYieldOrThrowStatement = (Boolean)e.getV3();
                return hasYieldOrThrowStatement;
            })) {
                throw this.createParsingFailedException("`yield` or `throw` is expected", ctx);
            }
            List statementList = statementInfoList.stream().map(e -> (List)e.getV1()).reduce(new LinkedList(), (r, e) -> {
                r.addAll(e);
                return r;
            });
            LinkedList<CaseStatement> caseStatementList = new LinkedList<CaseStatement>();
            LinkedList defaultStatementList = new LinkedList();
            statementList.forEach(e -> {
                if (e instanceof CaseStatement) {
                    caseStatementList.add((CaseStatement)e);
                } else if (this.isTrue((NodeMetaDataHandler)e, IS_SWITCH_DEFAULT)) {
                    defaultStatementList.add(e);
                }
            });
            int defaultStatementListSize = defaultStatementList.size();
            if (defaultStatementListSize > 1) {
                throw this.createParsingFailedException("switch expression should have only one default case, which should appear at last", (ASTNode)defaultStatementList.get(0));
            }
            if (defaultStatementListSize > 0 && DefaultGroovyMethods.last(statementList) instanceof CaseStatement) {
                throw this.createParsingFailedException("default case should appear at last", (ASTNode)defaultStatementList.get(0));
            }
            String variableName = "__$$sev" + this.switchExpressionVariableSeq++;
            Statement declarationStatement = GeneralUtils.declS(GeneralUtils.localVarX(variableName), this.visitExpressionInPar(ctx.expressionInPar()));
            SwitchStatement switchStatement = PositionConfigureUtils.configureAST(new SwitchStatement(GeneralUtils.varX(variableName), caseStatementList, defaultStatementListSize == 0 ? EmptyStatement.INSTANCE : (Statement)defaultStatementList.get(0)), ctx);
            MethodCallExpression callClosure = GeneralUtils.callX(PositionConfigureUtils.configureAST(GeneralUtils.closureX(null, this.createBlockStatement(declarationStatement, switchStatement)), ctx), CALL_STR);
            callClosure.setImplicitThis(false);
            MethodCallExpression methodCallExpression = PositionConfigureUtils.configureAST(callClosure, ctx);
            return methodCallExpression;
        }
        finally {
            this.switchExpressionRuleContextStack.pop();
        }
    }

    @Override
    public Tuple3<List<Statement>, Boolean, Boolean> visitSwitchBlockStatementExpressionGroup(GroovyParser.SwitchBlockStatementExpressionGroupContext ctx) {
        int labelCnt = ctx.switchExpressionLabel().size();
        ArrayList firstLabelHolder = new ArrayList(1);
        int[] arrowCntHolder = new int[1];
        boolean[] isArrowHolder = new boolean[1];
        boolean[] hasResultStmtHolder = new boolean[1];
        List result = (List)ctx.switchExpressionLabel().stream().map(e -> this.visitSwitchExpressionLabel((GroovyParser.SwitchExpressionLabelContext)e)).reduce(new ArrayList(4), (r, e) -> {
            BlockStatement blockStatement;
            List<Statement> branchStatementList;
            Statement exprOrBlockStatement;
            boolean isArrow;
            List statementList = (List)r;
            Tuple3 tuple = (Tuple3)e;
            isArrowHolder[0] = isArrow = 83 == (Integer)tuple.getV3();
            if (isArrow && (arrowCntHolder[0] = arrowCntHolder[0] + 1) > 1 && !firstLabelHolder.isEmpty()) {
                throw this.createParsingFailedException("`case ... ->` does not support falling through cases", (groovyjarjarantlr4.v4.runtime.Token)firstLabelHolder.get(0));
            }
            boolean isLast = labelCnt - 1 == statementList.size();
            BlockStatement codeBlock = this.visitBlockStatements(ctx.blockStatements());
            List<Statement> statements = codeBlock.getStatements();
            int statementsCnt = statements.size();
            if (0 == statementsCnt) {
                throw this.createParsingFailedException("`yield` is expected", ctx.blockStatements());
            }
            if (isArrow && statementsCnt > 1) {
                throw this.createParsingFailedException("Expect only 1 statement, but " + statementsCnt + " statements found", ctx.blockStatements());
            }
            if (!isArrow) {
                final boolean[] hasYieldHolder = new boolean[1];
                final boolean[] hasThrowHolder = new boolean[1];
                codeBlock.visit(new CodeVisitorSupport(){

                    @Override
                    public void visitReturnStatement(ReturnStatement statement) {
                        if (AstBuilder.this.isTrue(statement, AstBuilder.IS_YIELD_STATEMENT)) {
                            hasYieldHolder[0] = true;
                            return;
                        }
                        super.visitReturnStatement(statement);
                    }

                    @Override
                    public void visitThrowStatement(ThrowStatement statement) {
                        hasThrowHolder[0] = true;
                    }
                });
                if (hasYieldHolder[0] || hasThrowHolder[0]) {
                    hasResultStmtHolder[0] = true;
                }
            }
            if ((exprOrBlockStatement = statements.get(0)) instanceof BlockStatement && 1 == (branchStatementList = (blockStatement = (BlockStatement)exprOrBlockStatement).getStatements()).size()) {
                exprOrBlockStatement = branchStatementList.get(0);
            }
            if (!(exprOrBlockStatement instanceof ReturnStatement) && !(exprOrBlockStatement instanceof ThrowStatement) && isArrow) {
                MethodCallExpression callClosure = GeneralUtils.callX(PositionConfigureUtils.configureAST(GeneralUtils.closureX(null, exprOrBlockStatement), exprOrBlockStatement), CALL_STR);
                callClosure.setImplicitThis(false);
                MethodCallExpression resultExpr = exprOrBlockStatement instanceof ExpressionStatement ? ((ExpressionStatement)exprOrBlockStatement).getExpression() : callClosure;
                codeBlock = PositionConfigureUtils.configureAST(this.createBlockStatement(PositionConfigureUtils.configureAST(GeneralUtils.returnS(resultExpr), exprOrBlockStatement)), exprOrBlockStatement);
            }
            switch (((groovyjarjarantlr4.v4.runtime.Token)tuple.getV1()).getType()) {
                case 18: {
                    if (!DefaultGroovyMethods.asBoolean(statementList)) {
                        firstLabelHolder.add((groovyjarjarantlr4.v4.runtime.Token)tuple.getV1());
                    }
                    int n = ((List)tuple.getV2()).size();
                    for (int i = 0; i < n; ++i) {
                        Expression expr = (Expression)((List)tuple.getV2()).get(i);
                        statementList.add((Statement)PositionConfigureUtils.configureAST(new CaseStatement(expr, isLast && i == n - 1 ? codeBlock : EmptyStatement.INSTANCE), (groovyjarjarantlr4.v4.runtime.Token)firstLabelHolder.get(0)));
                    }
                    break;
                }
                case 23: {
                    codeBlock.putNodeMetaData(IS_SWITCH_DEFAULT, true);
                    statementList.add(codeBlock);
                }
            }
            return statementList;
        });
        return Tuple.tuple(result, isArrowHolder[0], hasResultStmtHolder[0]);
    }

    private void validateSwitchExpressionLabels(GroovyParser.SwitchExpressionContext ctx) {
        Map<String, List<GroovyParser.SwitchExpressionLabelContext>> acMap = ctx.switchBlockStatementExpressionGroup().stream().flatMap(e -> e.switchExpressionLabel().stream()).collect(Collectors.groupingBy(e -> e.ac.getText()));
        if (acMap.size() > 1) {
            List lastSelcList = acMap.values().stream().reduce((prev, next) -> next).orElse(null);
            throw this.createParsingFailedException(acMap.keySet().stream().collect(Collectors.joining("` and `", "`", "`")) + " cannot be used together", ((GroovyParser.SwitchExpressionLabelContext)lastSelcList.get((int)0)).ac);
        }
    }

    @Override
    public Tuple3<groovyjarjarantlr4.v4.runtime.Token, List<Expression>, Integer> visitSwitchExpressionLabel(GroovyParser.SwitchExpressionLabelContext ctx) {
        Integer acType = ctx.ac.getType();
        if (DefaultGroovyMethods.asBoolean(ctx.CASE())) {
            return Tuple.tuple(ctx.CASE().getSymbol(), this.visitExpressionList(ctx.expressionList()), acType);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.DEFAULT())) {
            return Tuple.tuple(ctx.DEFAULT().getSymbol(), Collections.singletonList(EmptyExpression.INSTANCE), acType);
        }
        throw this.createParsingFailedException("Unsupported switch expression label: " + ctx.getText(), ctx);
    }

    @Override
    public ClassNode visitTypeDeclaration(GroovyParser.TypeDeclarationContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.classDeclaration())) {
            ctx.classDeclaration().putNodeMetaData(TYPE_DECLARATION_MODIFIERS, this.visitClassOrInterfaceModifiersOpt(ctx.classOrInterfaceModifiersOpt()));
            return PositionConfigureUtils.configureAST(this.visitClassDeclaration(ctx.classDeclaration()), ctx);
        }
        throw this.createParsingFailedException("Unsupported type declaration: " + ctx.getText(), ctx);
    }

    private void initUsingGenerics(ClassNode classNode) {
        if (classNode.isUsingGenerics()) {
            return;
        }
        if (!classNode.isEnum()) {
            classNode.setUsingGenerics(classNode.getSuperClass().isUsingGenerics());
        }
        if (!classNode.isUsingGenerics() && null != classNode.getInterfaces()) {
            for (ClassNode anInterface : classNode.getInterfaces()) {
                classNode.setUsingGenerics(classNode.isUsingGenerics() || anInterface.isUsingGenerics());
                if (classNode.isUsingGenerics()) break;
            }
        }
    }

    @Override
    public ClassNode visitClassDeclaration(GroovyParser.ClassDeclarationContext ctx) {
        boolean isInterfaceWithDefaultMethods;
        ClassNode classNode;
        boolean isInterface;
        boolean isEnum;
        String packageName = Optional.ofNullable(this.moduleNode.getPackageName()).orElse("");
        String className = this.visitIdentifier(ctx.identifier());
        if ("var".equals(className)) {
            throw this.createParsingFailedException("var cannot be used for type declarations", ctx.identifier());
        }
        boolean isAnnotation = DefaultGroovyMethods.asBoolean(ctx.AT());
        if (isAnnotation) {
            if (DefaultGroovyMethods.asBoolean(ctx.typeParameters())) {
                throw this.createParsingFailedException("annotation declaration cannot have type parameters", ctx.typeParameters());
            }
            if (DefaultGroovyMethods.asBoolean(ctx.EXTENDS())) {
                throw this.createParsingFailedException("No extends clause allowed for annotation declaration", ctx.EXTENDS());
            }
            if (DefaultGroovyMethods.asBoolean(ctx.IMPLEMENTS())) {
                throw this.createParsingFailedException("No implements clause allowed for annotation declaration", ctx.IMPLEMENTS());
            }
        }
        if (isEnum = DefaultGroovyMethods.asBoolean(ctx.ENUM())) {
            if (DefaultGroovyMethods.asBoolean(ctx.typeParameters())) {
                throw this.createParsingFailedException("enum declaration cannot have type parameters", ctx.typeParameters());
            }
            if (DefaultGroovyMethods.asBoolean(ctx.EXTENDS())) {
                throw this.createParsingFailedException("No extends clause allowed for enum declaration", ctx.EXTENDS());
            }
        }
        boolean bl = isInterface = DefaultGroovyMethods.asBoolean(ctx.INTERFACE()) && !isAnnotation;
        if (isInterface && DefaultGroovyMethods.asBoolean(ctx.IMPLEMENTS())) {
            throw this.createParsingFailedException("No implements clause allowed for interface declaration", ctx.IMPLEMENTS());
        }
        ModifierManager modifierManager = new ModifierManager(this, (List)ctx.getNodeMetaData(TYPE_DECLARATION_MODIFIERS));
        Optional<ModifierNode> finalModifier = modifierManager.get(28);
        Optional<ModifierNode> sealedModifier = modifierManager.get(47);
        Optional<ModifierNode> nonSealedModifier = modifierManager.get(39);
        boolean isFinal = finalModifier.isPresent();
        boolean isSealed = sealedModifier.isPresent();
        boolean isNonSealed = nonSealedModifier.isPresent();
        boolean isRecord = DefaultGroovyMethods.asBoolean(ctx.RECORD());
        boolean hasRecordHeader = DefaultGroovyMethods.asBoolean(ctx.formalParameters());
        if (isRecord) {
            if (DefaultGroovyMethods.asBoolean(ctx.EXTENDS())) {
                throw this.createParsingFailedException("No extends clause allowed for record declaration", ctx.EXTENDS());
            }
            if (!hasRecordHeader) {
                throw this.createParsingFailedException("header declaration of record is expected", ctx.identifier());
            }
            if (isSealed) {
                throw this.createParsingFailedException("`sealed` is not allowed for record declaration", sealedModifier.get());
            }
            if (isNonSealed) {
                throw this.createParsingFailedException("`non-sealed` is not allowed for record declaration", nonSealedModifier.get());
            }
        } else if (hasRecordHeader) {
            throw this.createParsingFailedException("header declaration is only allowed for record declaration", ctx.formalParameters());
        }
        if (isSealed && isNonSealed) {
            throw this.createParsingFailedException("type cannot be defined with both `sealed` and `non-sealed`", nonSealedModifier.get());
        }
        if (isFinal && (isSealed || isNonSealed)) {
            throw this.createParsingFailedException("type cannot be defined with both " + (isSealed ? "`sealed`" : "`non-sealed`") + " and `final`", finalModifier.get());
        }
        if ((isAnnotation || isEnum) && (isSealed || isNonSealed)) {
            ModifierNode mn = isSealed ? sealedModifier.get() : nonSealedModifier.get();
            throw this.createParsingFailedException("modifier `" + mn.getText() + "` is not allowed for " + (isEnum ? "enum" : "annotation definition"), mn);
        }
        boolean hasPermits = DefaultGroovyMethods.asBoolean(ctx.PERMITS());
        if (!isSealed && hasPermits) {
            throw this.createParsingFailedException("only sealed type declarations should have `permits` clause", ctx);
        }
        int modifiers = modifierManager.getClassModifiersOpValue();
        boolean syntheticPublic = (modifiers & 0x1000) != 0;
        modifiers &= 0xFFFFEFFF;
        ClassNode outerClass = this.classNodeStack.peek();
        if (isEnum) {
            classNode = EnumHelper.makeEnumNode(DefaultGroovyMethods.asBoolean(outerClass) ? className : packageName + className, modifiers, null, outerClass);
        } else if (DefaultGroovyMethods.asBoolean(outerClass)) {
            if (outerClass.isInterface()) {
                modifiers |= 8;
            }
            classNode = new InnerClassNode(outerClass, outerClass.getName() + DOLLAR_STR + className, modifiers, ClassHelper.OBJECT_TYPE.getPlainNodeReference());
        } else {
            classNode = new ClassNode(packageName + className, modifiers, ClassHelper.OBJECT_TYPE.getPlainNodeReference());
        }
        PositionConfigureUtils.configureAST(classNode, ctx);
        classNode.setSyntheticPublic(syntheticPublic);
        classNode.setGenericsTypes(this.visitTypeParameters(ctx.typeParameters()));
        boolean bl2 = isInterfaceWithDefaultMethods = isInterface && this.containsDefaultMethods(ctx);
        if (isSealed) {
            AnnotationNode sealedAnnotationNode = AstBuilder.makeAnnotationNode(Sealed.class);
            if (DefaultGroovyMethods.asBoolean(ctx.ps)) {
                ListExpression permittedSubclassesListExpression = GeneralUtils.listX(Arrays.stream(this.visitTypeList(ctx.ps)).map(ClassExpression::new).collect(Collectors.toList()));
                sealedAnnotationNode.setMember("permittedSubclasses", permittedSubclassesListExpression);
                PositionConfigureUtils.configureAST(sealedAnnotationNode, ctx.PERMITS());
                sealedAnnotationNode.setNodeMetaData("permits", true);
            }
            classNode.addAnnotation(sealedAnnotationNode);
        } else if (isNonSealed) {
            classNode.addAnnotation(AstBuilder.makeAnnotationNode(NonSealed.class));
        }
        if (isInterfaceWithDefaultMethods || DefaultGroovyMethods.asBoolean(ctx.TRAIT())) {
            classNode.addAnnotation(AstBuilder.makeAnnotationNode(Trait.class));
        }
        if (isRecord) {
            classNode.addAnnotation(new AnnotationNode(RECORD_TYPE_CLASS));
        }
        classNode.addAnnotations(modifierManager.getAnnotations());
        if (isInterfaceWithDefaultMethods) {
            classNode.putNodeMetaData(IS_INTERFACE_WITH_DEFAULT_METHODS, Boolean.TRUE);
        }
        classNode.putNodeMetaData(CLASS_NAME, className);
        if (DefaultGroovyMethods.asBoolean(ctx.CLASS()) || DefaultGroovyMethods.asBoolean(ctx.TRAIT())) {
            if (DefaultGroovyMethods.asBoolean(ctx.scs)) {
                ClassNode[] scs = this.visitTypeList(ctx.scs);
                if (scs.length > 1) {
                    throw this.createParsingFailedException("Cannot extend multiple classes", ctx.EXTENDS());
                }
                classNode.setSuperClass(scs[0]);
            }
            classNode.setInterfaces(this.visitTypeList(ctx.is));
            this.initUsingGenerics(classNode);
        } else if (isInterfaceWithDefaultMethods) {
            classNode.setInterfaces(this.visitTypeList(ctx.scs));
            this.initUsingGenerics(classNode);
        } else if (isInterface) {
            classNode.setModifiers(classNode.getModifiers() | 0x200 | 0x400);
            classNode.setInterfaces(this.visitTypeList(ctx.scs));
            this.initUsingGenerics(classNode);
            this.hackMixins(classNode);
        } else if (isEnum || isRecord) {
            classNode.setInterfaces(this.visitTypeList(ctx.is));
            this.initUsingGenerics(classNode);
            if (isRecord) {
                this.transformRecordHeaderToProperties(ctx, classNode);
            }
        } else if (isAnnotation) {
            classNode.setModifiers(classNode.getModifiers() | 0x200 | 0x400 | 0x2000);
            classNode.addInterface(ClassHelper.Annotation_TYPE);
            this.hackMixins(classNode);
        } else {
            throw this.createParsingFailedException("Unsupported class declaration: " + ctx.getText(), ctx);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.CLASS()) || DefaultGroovyMethods.asBoolean(ctx.TRAIT())) {
            this.classNodeList.add(classNode);
        }
        this.classNodeStack.push(classNode);
        ctx.classBody().putNodeMetaData(CLASS_DECLARATION_CLASS_NODE, classNode);
        this.visitClassBody(ctx.classBody());
        if (isRecord) {
            classNode.getFields().stream().filter(f -> !this.isTrue((NodeMetaDataHandler)f, IS_RECORD_GENERATED) && !f.isStatic()).findFirst().ifPresent(fn -> this.createParsingFailedException("Instance field is not allowed in `record`", (ASTNode)fn));
        }
        this.classNodeStack.pop();
        if (!DefaultGroovyMethods.asBoolean(ctx.CLASS()) && !DefaultGroovyMethods.asBoolean(ctx.TRAIT())) {
            this.classNodeList.add(classNode);
        }
        this.groovydocManager.handle(classNode, ctx);
        return classNode;
    }

    private void transformRecordHeaderToProperties(GroovyParser.ClassDeclarationContext ctx, ClassNode classNode) {
        Parameter[] parameters = this.visitFormalParameters(ctx.formalParameters());
        classNode.putNodeMetaData(RECORD_HEADER, parameters);
        int n = parameters.length;
        for (int i = 0; i < n; ++i) {
            Parameter parameter = parameters[i];
            GroovyParser.FormalParameterContext parameterCtx = (GroovyParser.FormalParameterContext)parameter.getNodeMetaData(PARAMETER_CONTEXT);
            ModifierManager parameterModifierManager = (ModifierManager)parameter.getNodeMetaData(PARAMETER_MODIFIER_MANAGER);
            ClassNode originType = parameter.getOriginType();
            PropertyNode propertyNode = this.declareProperty(parameterCtx, parameterModifierManager, originType, classNode, i, parameter, parameter.getName(), parameter.getModifiers(), parameter.getInitialExpression());
            propertyNode.getField().putNodeMetaData(IS_RECORD_GENERATED, Boolean.TRUE);
        }
    }

    private boolean containsDefaultMethods(GroovyParser.ClassDeclarationContext ctx) {
        List methodDeclarationContextList = (List)ctx.classBody().classBodyDeclaration().stream().map(GroovyParser.ClassBodyDeclarationContext::memberDeclaration).filter(Objects::nonNull).map(e -> e.methodDeclaration()).filter(Objects::nonNull).reduce(new LinkedList(), (r, e) -> {
            GroovyParser.MethodDeclarationContext methodDeclarationContext = (GroovyParser.MethodDeclarationContext)e;
            if (this.createModifierManager(methodDeclarationContext).containsAny(23)) {
                ((List)r).add(methodDeclarationContext);
            }
            return r;
        });
        return !methodDeclarationContextList.isEmpty();
    }

    @Override
    public Void visitClassBody(GroovyParser.ClassBodyContext ctx) {
        ClassNode classNode = (ClassNode)ctx.getNodeMetaData(CLASS_DECLARATION_CLASS_NODE);
        Objects.requireNonNull(classNode, "classNode should not be null");
        if (DefaultGroovyMethods.asBoolean(ctx.enumConstants())) {
            ctx.enumConstants().putNodeMetaData(CLASS_DECLARATION_CLASS_NODE, classNode);
            this.visitEnumConstants(ctx.enumConstants());
        }
        ctx.classBodyDeclaration().forEach(e -> {
            e.putNodeMetaData(CLASS_DECLARATION_CLASS_NODE, classNode);
            this.visitClassBodyDeclaration((GroovyParser.ClassBodyDeclarationContext)e);
        });
        return null;
    }

    @Override
    public List<FieldNode> visitEnumConstants(GroovyParser.EnumConstantsContext ctx) {
        ClassNode classNode = (ClassNode)ctx.getNodeMetaData(CLASS_DECLARATION_CLASS_NODE);
        Objects.requireNonNull(classNode, "classNode should not be null");
        return ctx.enumConstant().stream().map(e -> {
            e.putNodeMetaData(CLASS_DECLARATION_CLASS_NODE, classNode);
            return this.visitEnumConstant((GroovyParser.EnumConstantContext)e);
        }).collect(Collectors.toList());
    }

    @Override
    public FieldNode visitEnumConstant(GroovyParser.EnumConstantContext ctx) {
        ClassNode classNode = (ClassNode)ctx.getNodeMetaData(CLASS_DECLARATION_CLASS_NODE);
        Objects.requireNonNull(classNode, "classNode should not be null");
        InnerClassNode anonymousInnerClassNode = null;
        if (DefaultGroovyMethods.asBoolean(ctx.anonymousInnerClassDeclaration())) {
            ctx.anonymousInnerClassDeclaration().putNodeMetaData(ANONYMOUS_INNER_CLASS_SUPER_CLASS, classNode);
            anonymousInnerClassNode = this.visitAnonymousInnerClassDeclaration(ctx.anonymousInnerClassDeclaration());
        }
        FieldNode enumConstant = EnumHelper.addEnumConstant(classNode, this.visitIdentifier(ctx.identifier()), this.createEnumConstantInitExpression(ctx.arguments(), anonymousInnerClassNode));
        enumConstant.addAnnotations((List<AnnotationNode>)this.visitAnnotationsOpt(ctx.annotationsOpt()));
        this.groovydocManager.handle(enumConstant, ctx);
        return PositionConfigureUtils.configureAST(enumConstant, ctx);
    }

    private Expression createEnumConstantInitExpression(GroovyParser.ArgumentsContext ctx, InnerClassNode anonymousInnerClassNode) {
        if (!DefaultGroovyMethods.asBoolean(ctx) && !DefaultGroovyMethods.asBoolean(anonymousInnerClassNode)) {
            return null;
        }
        TupleExpression argumentListExpression = (TupleExpression)this.visitArguments(ctx);
        List<Expression> expressions = argumentListExpression.getExpressions();
        if (expressions.size() == 1) {
            Expression expression = expressions.get(0);
            if (expression instanceof NamedArgumentListExpression) {
                List<MapEntryExpression> mapEntryExpressionList = ((NamedArgumentListExpression)expression).getMapEntryExpressions();
                ListExpression listExpression = new ListExpression(mapEntryExpressionList.stream().map(e -> e).collect(Collectors.toList()));
                if (DefaultGroovyMethods.asBoolean(anonymousInnerClassNode)) {
                    listExpression.addExpression(PositionConfigureUtils.configureAST(new ClassExpression(anonymousInnerClassNode), anonymousInnerClassNode));
                }
                if (mapEntryExpressionList.size() > 1) {
                    listExpression.setWrapped(true);
                }
                return PositionConfigureUtils.configureAST(listExpression, ctx);
            }
            if (!DefaultGroovyMethods.asBoolean(anonymousInnerClassNode)) {
                if (expression instanceof ListExpression) {
                    ListExpression listExpression = new ListExpression();
                    listExpression.addExpression(expression);
                    return PositionConfigureUtils.configureAST(listExpression, ctx);
                }
                return expression;
            }
            ListExpression listExpression = new ListExpression();
            if (expression instanceof ListExpression) {
                ((ListExpression)expression).getExpressions().forEach(listExpression::addExpression);
            } else {
                listExpression.addExpression(expression);
            }
            listExpression.addExpression(PositionConfigureUtils.configureAST(new ClassExpression(anonymousInnerClassNode), anonymousInnerClassNode));
            return PositionConfigureUtils.configureAST(listExpression, ctx);
        }
        ListExpression listExpression = new ListExpression(expressions);
        if (DefaultGroovyMethods.asBoolean(anonymousInnerClassNode)) {
            listExpression.addExpression(PositionConfigureUtils.configureAST(new ClassExpression(anonymousInnerClassNode), anonymousInnerClassNode));
        }
        if (DefaultGroovyMethods.asBoolean(ctx)) {
            listExpression.setWrapped(true);
        }
        return DefaultGroovyMethods.asBoolean(ctx) ? (Expression)PositionConfigureUtils.configureAST(listExpression, ctx) : (Expression)PositionConfigureUtils.configureAST(listExpression, anonymousInnerClassNode);
    }

    @Override
    public Void visitClassBodyDeclaration(GroovyParser.ClassBodyDeclarationContext ctx) {
        ClassNode classNode = (ClassNode)ctx.getNodeMetaData(CLASS_DECLARATION_CLASS_NODE);
        Objects.requireNonNull(classNode, "classNode should not be null");
        if (DefaultGroovyMethods.asBoolean(ctx.memberDeclaration())) {
            ctx.memberDeclaration().putNodeMetaData(CLASS_DECLARATION_CLASS_NODE, classNode);
            this.visitMemberDeclaration(ctx.memberDeclaration());
        } else if (DefaultGroovyMethods.asBoolean(ctx.block())) {
            Statement statement = this.visitBlock(ctx.block());
            if (DefaultGroovyMethods.asBoolean(ctx.STATIC())) {
                classNode.addStaticInitializerStatements(Collections.singletonList(statement), false);
            } else {
                classNode.addObjectInitializerStatements(PositionConfigureUtils.configureAST(this.createBlockStatement(statement), statement));
            }
        }
        return null;
    }

    @Override
    public Void visitMemberDeclaration(GroovyParser.MemberDeclarationContext ctx) {
        ClassNode classNode = (ClassNode)ctx.getNodeMetaData(CLASS_DECLARATION_CLASS_NODE);
        Objects.requireNonNull(classNode, "classNode should not be null");
        if (DefaultGroovyMethods.asBoolean(ctx.methodDeclaration())) {
            ctx.methodDeclaration().putNodeMetaData(CLASS_DECLARATION_CLASS_NODE, classNode);
            this.visitMethodDeclaration(ctx.methodDeclaration());
        } else if (DefaultGroovyMethods.asBoolean(ctx.fieldDeclaration())) {
            ctx.fieldDeclaration().putNodeMetaData(CLASS_DECLARATION_CLASS_NODE, classNode);
            this.visitFieldDeclaration(ctx.fieldDeclaration());
        } else if (DefaultGroovyMethods.asBoolean(ctx.compactConstructorDeclaration())) {
            ctx.compactConstructorDeclaration().putNodeMetaData(COMPACT_CONSTRUCTOR_DECLARATION_MODIFIERS, this.visitModifiersOpt(ctx.modifiersOpt()));
            ctx.compactConstructorDeclaration().putNodeMetaData(CLASS_DECLARATION_CLASS_NODE, classNode);
            this.visitCompactConstructorDeclaration(ctx.compactConstructorDeclaration());
        } else if (DefaultGroovyMethods.asBoolean(ctx.classDeclaration())) {
            ctx.classDeclaration().putNodeMetaData(TYPE_DECLARATION_MODIFIERS, this.visitModifiersOpt(ctx.modifiersOpt()));
            ctx.classDeclaration().putNodeMetaData(CLASS_DECLARATION_CLASS_NODE, classNode);
            this.visitClassDeclaration(ctx.classDeclaration());
        }
        return null;
    }

    @Override
    public GenericsType[] visitTypeParameters(GroovyParser.TypeParametersContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return null;
        }
        return (GenericsType[])ctx.typeParameter().stream().map(this::visitTypeParameter).toArray(GenericsType[]::new);
    }

    @Override
    public GenericsType visitTypeParameter(GroovyParser.TypeParameterContext ctx) {
        ClassNode baseType = PositionConfigureUtils.configureAST(ClassHelper.make(this.visitClassName(ctx.className())), ctx);
        baseType.addTypeAnnotations((List<AnnotationNode>)this.visitAnnotationsOpt(ctx.annotationsOpt()));
        GenericsType genericsType = new GenericsType(baseType, this.visitTypeBound(ctx.typeBound()), null);
        return PositionConfigureUtils.configureAST(genericsType, ctx);
    }

    @Override
    public ClassNode[] visitTypeBound(GroovyParser.TypeBoundContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return null;
        }
        return (ClassNode[])ctx.type().stream().map(this::visitType).toArray(ClassNode[]::new);
    }

    @Override
    public Void visitFieldDeclaration(GroovyParser.FieldDeclarationContext ctx) {
        ClassNode classNode = (ClassNode)ctx.getNodeMetaData(CLASS_DECLARATION_CLASS_NODE);
        Objects.requireNonNull(classNode, "classNode should not be null");
        ctx.variableDeclaration().putNodeMetaData(CLASS_DECLARATION_CLASS_NODE, classNode);
        this.visitVariableDeclaration(ctx.variableDeclaration());
        return null;
    }

    private ConstructorCallExpression checkThisAndSuperConstructorCall(Statement statement) {
        if (!(statement instanceof BlockStatement)) {
            return null;
        }
        BlockStatement blockStatement = (BlockStatement)statement;
        List<Statement> statementList = blockStatement.getStatements();
        int n = statementList.size();
        for (int i = 0; i < n; ++i) {
            Expression expression;
            Statement s = statementList.get(i);
            if (!(s instanceof ExpressionStatement) || !((expression = ((ExpressionStatement)s).getExpression()) instanceof ConstructorCallExpression) || 0 == i) continue;
            return (ConstructorCallExpression)expression;
        }
        return null;
    }

    private ModifierManager createModifierManager(GroovyParser.MethodDeclarationContext ctx) {
        Object modifierNodeList = Collections.emptyList();
        if (DefaultGroovyMethods.asBoolean(ctx.modifiersOpt())) {
            modifierNodeList = this.visitModifiersOpt(ctx.modifiersOpt());
        }
        return new ModifierManager(this, (List<ModifierNode>)modifierNodeList);
    }

    private void validateParametersOfMethodDeclaration(Parameter[] parameters, ClassNode classNode) {
        if (!classNode.isInterface()) {
            return;
        }
        for (Parameter parameter : parameters) {
            if (!parameter.hasInitialExpression()) continue;
            throw this.createParsingFailedException("Cannot specify default value for method parameter '" + parameter.getName() + " = " + parameter.getInitialExpression().getText() + "' inside an interface", parameter);
        }
    }

    @Override
    public MethodNode visitCompactConstructorDeclaration(GroovyParser.CompactConstructorDeclarationContext ctx) {
        String className;
        ClassNode classNode = (ClassNode)ctx.getNodeMetaData(CLASS_DECLARATION_CLASS_NODE);
        Objects.requireNonNull(classNode, "classNode should not be null");
        if (!DefaultGroovyMethods.asBoolean(classNode.getAnnotations(RECORD_TYPE_CLASS))) {
            this.createParsingFailedException("Only record can have compact constructor", ctx);
        }
        if (new ModifierManager(this, (List)ctx.getNodeMetaData(COMPACT_CONSTRUCTOR_DECLARATION_MODIFIERS)).containsAny(12)) {
            throw this.createParsingFailedException("var cannot be used for compact constructor declaration", ctx);
        }
        String methodName = this.visitMethodName(ctx.methodName());
        if (!methodName.equals(className = (String)classNode.getNodeMetaData(CLASS_NAME))) {
            this.createParsingFailedException("Compact constructor should have the same name as record: " + className, ctx.methodName());
        }
        final Parameter[] header = (Parameter[])classNode.getNodeMetaData(RECORD_HEADER);
        Objects.requireNonNull(header, "record header should not be null");
        Statement code = this.visitMethodBody(ctx.methodBody());
        code.visit(new CodeVisitorSupport(){

            @Override
            public void visitPropertyExpression(PropertyExpression expression) {
                String propertyName = expression.getPropertyAsString();
                if (AstBuilder.THIS_STR.equals(expression.getObjectExpression().getText()) && Arrays.stream(header).anyMatch(p -> p.getName().equals(propertyName))) {
                    AstBuilder.this.createParsingFailedException("Cannot assign a value to final variable '" + propertyName + AstBuilder.SQ_STR, expression.getProperty());
                }
                super.visitPropertyExpression(expression);
            }
        });
        List<AnnotationNode> annos = classNode.getAnnotations(ClassHelper.make(TupleConstructor.class));
        AnnotationNode tupleConstructor = annos.isEmpty() ? AstBuilder.makeAnnotationNode(TupleConstructor.class) : annos.get(0);
        tupleConstructor.setMember("pre", GeneralUtils.closureX(code));
        if (annos.isEmpty()) {
            classNode.addAnnotation(tupleConstructor);
        }
        return null;
    }

    @Override
    public MethodNode visitMethodDeclaration(GroovyParser.MethodDeclarationContext ctx) {
        MethodNode methodNode;
        ModifierManager modifierManager = this.createModifierManager(ctx);
        if (modifierManager.containsAny(12)) {
            throw this.createParsingFailedException("var cannot be used for method declarations", ctx);
        }
        String methodName = this.visitMethodName(ctx.methodName());
        ClassNode returnType = this.visitReturnType(ctx.returnType());
        Parameter[] parameters = this.visitFormalParameters(ctx.formalParameters());
        ClassNode[] exceptions = this.visitQualifiedClassNameList(ctx.qualifiedClassNameList());
        this.anonymousInnerClassesDefinedInMethodStack.push(new LinkedList());
        Statement code = this.visitMethodBody(ctx.methodBody());
        List<InnerClassNode> anonymousInnerClassList = this.anonymousInnerClassesDefinedInMethodStack.pop();
        ClassNode classNode = (ClassNode)ctx.getNodeMetaData(CLASS_DECLARATION_CLASS_NODE);
        if (DefaultGroovyMethods.asBoolean(classNode)) {
            this.validateParametersOfMethodDeclaration(parameters, classNode);
            methodNode = this.createConstructorOrMethodNodeForClass(ctx, modifierManager, methodName, returnType, parameters, exceptions, code, classNode);
        } else {
            methodNode = this.createScriptMethodNode(modifierManager, methodName, returnType, parameters, exceptions, code);
        }
        anonymousInnerClassList.forEach(e -> e.setEnclosingMethod(methodNode));
        methodNode.setGenericsTypes(this.visitTypeParameters(ctx.typeParameters()));
        methodNode.setSyntheticPublic(this.isSyntheticPublic(this.isAnnotationDeclaration(classNode), classNode instanceof EnumConstantClassNode, DefaultGroovyMethods.asBoolean(ctx.returnType()), modifierManager));
        if (modifierManager.containsAny(48)) {
            for (Parameter parameter : methodNode.getParameters()) {
                parameter.setInStaticContext(true);
            }
            methodNode.getVariableScope().setInStaticContext(true);
        }
        PositionConfigureUtils.configureAST(methodNode, ctx);
        this.validateMethodDeclaration(ctx, methodNode, modifierManager, classNode);
        this.groovydocManager.handle(methodNode, ctx);
        return methodNode;
    }

    private void validateMethodDeclaration(GroovyParser.MethodDeclarationContext ctx, MethodNode methodNode, ModifierManager modifierManager, ClassNode classNode) {
        boolean hasMethodBody;
        if (!(1 != ctx.t && 2 != ctx.t && 3 != ctx.t || DefaultGroovyMethods.asBoolean(ctx.modifiersOpt().modifiers()) || DefaultGroovyMethods.asBoolean(ctx.returnType()))) {
            throw this.createParsingFailedException("Modifiers or return type is required", ctx);
        }
        if (1 == ctx.t && !DefaultGroovyMethods.asBoolean(ctx.methodBody())) {
            throw this.createParsingFailedException("Method body is required", ctx);
        }
        if (2 == ctx.t && DefaultGroovyMethods.asBoolean(ctx.methodBody())) {
            throw this.createParsingFailedException("Abstract method should not have method body", ctx);
        }
        boolean isAbstractMethod = methodNode.isAbstract();
        boolean bl = hasMethodBody = DefaultGroovyMethods.asBoolean(methodNode.getCode()) && !(methodNode.getCode() instanceof ExpressionStatement);
        if (9 == ctx.ct) {
            if (isAbstractMethod || !hasMethodBody) {
                throw this.createParsingFailedException("You cannot define " + (isAbstractMethod ? "an abstract" : "a") + " method[" + methodNode.getName() + "] " + (!hasMethodBody ? "without method body " : "") + "in the script. Try " + (isAbstractMethod ? "removing the 'abstract'" : "") + (isAbstractMethod && !hasMethodBody ? " and" : "") + (!hasMethodBody ? " adding a method body" : ""), methodNode);
            }
        } else {
            boolean isInterfaceOrAbstractClass;
            if (4 == ctx.ct && isAbstractMethod && hasMethodBody) {
                throw this.createParsingFailedException("Abstract method should not have method body", ctx);
            }
            if (3 == ctx.ct && hasMethodBody) {
                throw this.createParsingFailedException("Annotation type element should not have body", ctx);
            }
            if (!isAbstractMethod && !hasMethodBody) {
                throw this.createParsingFailedException("You defined a method[" + methodNode.getName() + "] without a body. Try adding a method body, or declare it abstract", methodNode);
            }
            boolean bl2 = isInterfaceOrAbstractClass = DefaultGroovyMethods.asBoolean(classNode) && classNode.isAbstract() && !classNode.isAnnotationDefinition();
            if (isInterfaceOrAbstractClass && !modifierManager.containsAny(23) && isAbstractMethod && hasMethodBody) {
                throw this.createParsingFailedException("You defined an abstract method[" + methodNode.getName() + "] with a body. Try removing the method body" + (classNode.isInterface() ? ", or declare it default" : ""), methodNode);
            }
        }
        modifierManager.validate(methodNode);
        if (methodNode instanceof ConstructorNode) {
            modifierManager.validate((ConstructorNode)methodNode);
        }
    }

    private MethodNode createScriptMethodNode(ModifierManager modifierManager, String methodName, ClassNode returnType, Parameter[] parameters, ClassNode[] exceptions, Statement code) {
        MethodNode methodNode = new MethodNode(methodName, modifierManager.containsAny(42) ? 2 : 1, returnType, parameters, exceptions, code);
        modifierManager.processMethodNode(methodNode);
        return methodNode;
    }

    private MethodNode createConstructorOrMethodNodeForClass(GroovyParser.MethodDeclarationContext ctx, ModifierManager modifierManager, String methodName, ClassNode returnType, Parameter[] parameters, ClassNode[] exceptions, Statement code, ClassNode classNode) {
        MethodNode methodNode;
        String className = (String)classNode.getNodeMetaData(CLASS_NAME);
        int modifiers = modifierManager.getClassMemberModifiersOpValue();
        boolean hasReturnType = DefaultGroovyMethods.asBoolean(ctx.returnType());
        boolean hasMethodBody = DefaultGroovyMethods.asBoolean(ctx.methodBody());
        if (!hasReturnType && hasMethodBody && methodName.equals(className)) {
            methodNode = this.createConstructorNodeForClass(methodName, parameters, exceptions, code, classNode, modifiers);
        } else {
            if (!hasReturnType && hasMethodBody && 0 == modifierManager.getModifierCount()) {
                throw this.createParsingFailedException("Invalid method declaration: " + methodName, ctx);
            }
            methodNode = this.createMethodNodeForClass(ctx, modifierManager, methodName, returnType, parameters, exceptions, code, classNode, modifiers);
        }
        modifierManager.attachAnnotations(methodNode);
        return methodNode;
    }

    private MethodNode createMethodNodeForClass(GroovyParser.MethodDeclarationContext ctx, ModifierManager modifierManager, String methodName, ClassNode returnType, Parameter[] parameters, ClassNode[] exceptions, Statement code, ClassNode classNode, int modifiers) {
        if (DefaultGroovyMethods.asBoolean(ctx.elementValue())) {
            code = PositionConfigureUtils.configureAST(new ExpressionStatement(this.visitElementValue(ctx.elementValue())), ctx.elementValue());
        }
        MethodNode methodNode = new MethodNode(methodName, modifiers |= !modifierManager.containsAny(48) && (classNode.isInterface() || this.isTrue(classNode, IS_INTERFACE_WITH_DEFAULT_METHODS) && !modifierManager.containsAny(23)) ? 1024 : 0, returnType, parameters, exceptions, code);
        classNode.addMethod(methodNode);
        methodNode.setAnnotationDefault(DefaultGroovyMethods.asBoolean(ctx.elementValue()));
        return methodNode;
    }

    private ConstructorNode createConstructorNodeForClass(String methodName, Parameter[] parameters, ClassNode[] exceptions, Statement code, ClassNode classNode, int modifiers) {
        ConstructorCallExpression thisOrSuperConstructorCallExpression = this.checkThisAndSuperConstructorCall(code);
        if (DefaultGroovyMethods.asBoolean(thisOrSuperConstructorCallExpression)) {
            throw this.createParsingFailedException(thisOrSuperConstructorCallExpression.getText() + " should be the first statement in the constructor[" + methodName + "]", thisOrSuperConstructorCallExpression);
        }
        return classNode.addConstructor(modifiers, parameters, exceptions, code);
    }

    @Override
    public String visitMethodName(GroovyParser.MethodNameContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.identifier())) {
            return this.visitIdentifier(ctx.identifier());
        }
        if (DefaultGroovyMethods.asBoolean(ctx.stringLiteral())) {
            return this.visitStringLiteral(ctx.stringLiteral()).getText();
        }
        throw this.createParsingFailedException("Unsupported method name: " + ctx.getText(), ctx);
    }

    @Override
    public ClassNode visitReturnType(GroovyParser.ReturnTypeContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return ClassHelper.dynamicType();
        }
        if (DefaultGroovyMethods.asBoolean(ctx.type())) {
            return this.visitType(ctx.type());
        }
        if (DefaultGroovyMethods.asBoolean(ctx.VOID())) {
            if (ctx.ct == 3) {
                throw this.createParsingFailedException("annotation method cannot have void return type", ctx);
            }
            return PositionConfigureUtils.configureAST(ClassHelper.VOID_TYPE.getPlainNodeReference(false), ctx.VOID());
        }
        throw this.createParsingFailedException("Unsupported return type: " + ctx.getText(), ctx);
    }

    @Override
    public Statement visitMethodBody(GroovyParser.MethodBodyContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return null;
        }
        return PositionConfigureUtils.configureAST(this.visitBlock(ctx.block()), ctx);
    }

    @Override
    public DeclarationListStatement visitLocalVariableDeclaration(GroovyParser.LocalVariableDeclarationContext ctx) {
        return PositionConfigureUtils.configureAST(this.visitVariableDeclaration(ctx.variableDeclaration()), ctx);
    }

    private DeclarationListStatement createMultiAssignmentDeclarationListStatement(GroovyParser.VariableDeclarationContext ctx, ModifierManager modifierManager) {
        return PositionConfigureUtils.configureAST(new DeclarationListStatement(PositionConfigureUtils.configureAST(modifierManager.attachAnnotations(new DeclarationExpression(new ArgumentListExpression(this.visitTypeNamePairs(ctx.typeNamePairs()).stream().peek(e -> modifierManager.processVariableExpression((VariableExpression)e)).collect(Collectors.toList())), this.createGroovyTokenByType(ctx.ASSIGN().getSymbol(), 100), this.visitVariableInitializer(ctx.variableInitializer()))), ctx)), ctx);
    }

    @Override
    public DeclarationListStatement visitVariableDeclaration(GroovyParser.VariableDeclarationContext ctx) {
        ModifierManager modifierManager = new ModifierManager(this, (List<ModifierNode>)(DefaultGroovyMethods.asBoolean(ctx.modifiers()) ? this.visitModifiers(ctx.modifiers()) : Collections.emptyList()));
        if (DefaultGroovyMethods.asBoolean(ctx.typeNamePairs())) {
            return this.createMultiAssignmentDeclarationListStatement(ctx, modifierManager);
        }
        ClassNode variableType = this.visitType(ctx.type());
        ctx.variableDeclarators().putNodeMetaData(VARIABLE_DECLARATION_VARIABLE_TYPE, variableType);
        Object declarationExpressionList = this.visitVariableDeclarators(ctx.variableDeclarators());
        ClassNode classNode = (ClassNode)ctx.getNodeMetaData(CLASS_DECLARATION_CLASS_NODE);
        if (DefaultGroovyMethods.asBoolean(classNode)) {
            return this.createFieldDeclarationListStatement(ctx, modifierManager, variableType, (List<DeclarationExpression>)declarationExpressionList, classNode);
        }
        declarationExpressionList.forEach(e -> {
            VariableExpression variableExpression = (VariableExpression)e.getLeftExpression();
            modifierManager.processVariableExpression(variableExpression);
            modifierManager.attachAnnotations(e);
        });
        int size = declarationExpressionList.size();
        if (size > 0) {
            DeclarationExpression declarationExpression = (DeclarationExpression)declarationExpressionList.get(0);
            if (1 == size) {
                PositionConfigureUtils.configureAST(declarationExpression, ctx);
            } else {
                declarationExpression.setLineNumber(ctx.getStart().getLine());
                declarationExpression.setColumnNumber(ctx.getStart().getCharPositionInLine() + 1);
            }
        }
        return PositionConfigureUtils.configureAST(new DeclarationListStatement((List<DeclarationExpression>)declarationExpressionList), ctx);
    }

    private DeclarationListStatement createFieldDeclarationListStatement(GroovyParser.VariableDeclarationContext ctx, ModifierManager modifierManager, ClassNode variableType, List<DeclarationExpression> declarationExpressionList, ClassNode classNode) {
        int n = declarationExpressionList.size();
        for (int i = 0; i < n; ++i) {
            DeclarationExpression declarationExpression = declarationExpressionList.get(i);
            VariableExpression variableExpression = (VariableExpression)declarationExpression.getLeftExpression();
            String fieldName = variableExpression.getName();
            int modifiers = modifierManager.getClassMemberModifiersOpValue();
            Expression initialValue = declarationExpression.getRightExpression() instanceof EmptyExpression ? null : declarationExpression.getRightExpression();
            Object defaultValue = this.findDefaultValueByType(variableType);
            if (classNode.isInterface()) {
                if (!DefaultGroovyMethods.asBoolean(initialValue)) {
                    initialValue = !DefaultGroovyMethods.asBoolean(defaultValue) ? null : new ConstantExpression(defaultValue, true);
                }
                modifiers |= 0x19;
            }
            if (this.isFieldDeclaration(modifierManager, classNode)) {
                this.declareField(ctx, modifierManager, variableType, classNode, i, variableExpression, fieldName, modifiers, initialValue);
                continue;
            }
            this.declareProperty(ctx, modifierManager, variableType, classNode, i, variableExpression, fieldName, modifiers, initialValue);
        }
        return null;
    }

    private PropertyNode declareProperty(GroovyParser.GroovyParserRuleContext ctx, ModifierManager modifierManager, ClassNode variableType, ClassNode classNode, int i, ASTNode startNode, String fieldName, int modifiers, Expression initialValue) {
        PropertyNode propertyNode;
        FieldNode fieldNode = classNode.getDeclaredField(fieldName);
        if (fieldNode != null && !classNode.hasProperty(fieldName)) {
            if (fieldNode.hasInitialExpression() && initialValue != null) {
                throw this.createParsingFailedException("The split property definition named '" + fieldName + "' must not have an initial value for both the field and the property", ctx);
            }
            if (!fieldNode.getType().equals(variableType)) {
                throw this.createParsingFailedException("The split property definition named '" + fieldName + "' must not have different types for the field and the property", ctx);
            }
            classNode.getFields().remove(fieldNode);
            propertyNode = new PropertyNode(fieldNode, modifiers | 1, null, null);
            classNode.addProperty(propertyNode);
            if (initialValue != null) {
                fieldNode.setInitialValueExpression(initialValue);
            }
            modifierManager.attachAnnotations(propertyNode);
            propertyNode.addAnnotation(AstBuilder.makeAnnotationNode(CompileStatic.class));
            PropertyExpander expander = new PropertyExpander(classNode);
            expander.visitProperty(propertyNode);
        } else {
            propertyNode = new PropertyNode(fieldName, modifiers | 1, variableType, classNode, initialValue, null, null);
            classNode.addProperty(propertyNode);
            fieldNode = propertyNode.getField();
            fieldNode.setModifiers(modifiers & 0xFFFFFFFE | 2);
            fieldNode.setSynthetic(!classNode.isInterface());
            modifierManager.attachAnnotations(fieldNode);
            modifierManager.attachAnnotations(propertyNode);
            if (i == 0) {
                PositionConfigureUtils.configureAST(fieldNode, ctx, (ASTNode)initialValue);
            } else {
                PositionConfigureUtils.configureAST(fieldNode, startNode, (ASTNode)initialValue);
            }
        }
        this.groovydocManager.handle(fieldNode, ctx);
        this.groovydocManager.handle(propertyNode, ctx);
        if (i == 0) {
            PositionConfigureUtils.configureAST(propertyNode, ctx, (ASTNode)initialValue);
        } else {
            PositionConfigureUtils.configureAST(propertyNode, startNode, (ASTNode)initialValue);
        }
        return propertyNode;
    }

    private void declareField(GroovyParser.VariableDeclarationContext ctx, ModifierManager modifierManager, ClassNode variableType, ClassNode classNode, int i, VariableExpression variableExpression, String fieldName, int modifiers, Expression initialValue) {
        FieldNode fieldNode;
        PropertyNode propertyNode = classNode.getProperty(fieldName);
        if (propertyNode != null && propertyNode.getField().isSynthetic()) {
            if (propertyNode.hasInitialExpression() && initialValue != null) {
                throw this.createParsingFailedException("The split property definition named '" + fieldName + "' must not have an initial value for both the field and the property", ctx);
            }
            if (!propertyNode.getType().equals(variableType)) {
                throw this.createParsingFailedException("The split property definition named '" + fieldName + "' must not have different types for the field and the property", ctx);
            }
            classNode.getFields().remove(propertyNode.getField());
            fieldNode = new FieldNode(fieldName, modifiers, variableType, classNode.redirect(), propertyNode.hasInitialExpression() ? propertyNode.getInitialExpression() : initialValue);
            propertyNode.setField(fieldNode);
            propertyNode.addAnnotation(AstBuilder.makeAnnotationNode(CompileStatic.class));
            classNode.addField(fieldNode);
            PropertyExpander expander = new PropertyExpander(classNode);
            expander.visitProperty(propertyNode);
        } else {
            fieldNode = classNode.addField(fieldName, modifiers, variableType, initialValue);
        }
        modifierManager.attachAnnotations(fieldNode);
        this.groovydocManager.handle(fieldNode, ctx);
        if (i == 0) {
            PositionConfigureUtils.configureAST(fieldNode, ctx, (ASTNode)initialValue);
        } else {
            PositionConfigureUtils.configureAST(fieldNode, variableExpression, (ASTNode)initialValue);
        }
    }

    private boolean isFieldDeclaration(ModifierManager modifierManager, ClassNode classNode) {
        return classNode.isInterface() || modifierManager.containsVisibilityModifier();
    }

    @Override
    public List<Expression> visitTypeNamePairs(GroovyParser.TypeNamePairsContext ctx) {
        return ctx.typeNamePair().stream().map(this::visitTypeNamePair).collect(Collectors.toList());
    }

    @Override
    public VariableExpression visitTypeNamePair(GroovyParser.TypeNamePairContext ctx) {
        return PositionConfigureUtils.configureAST(new VariableExpression(this.visitVariableDeclaratorId(ctx.variableDeclaratorId()).getName(), this.visitType(ctx.type())), ctx);
    }

    @Override
    public List<DeclarationExpression> visitVariableDeclarators(GroovyParser.VariableDeclaratorsContext ctx) {
        ClassNode variableType = (ClassNode)ctx.getNodeMetaData(VARIABLE_DECLARATION_VARIABLE_TYPE);
        Objects.requireNonNull(variableType, "variableType should not be null");
        return ctx.variableDeclarator().stream().map(e -> {
            e.putNodeMetaData(VARIABLE_DECLARATION_VARIABLE_TYPE, variableType);
            return this.visitVariableDeclarator((GroovyParser.VariableDeclaratorContext)e);
        }).collect(Collectors.toList());
    }

    @Override
    public DeclarationExpression visitVariableDeclarator(GroovyParser.VariableDeclaratorContext ctx) {
        ClassNode variableType = (ClassNode)ctx.getNodeMetaData(VARIABLE_DECLARATION_VARIABLE_TYPE);
        Objects.requireNonNull(variableType, "variableType should not be null");
        Token token = DefaultGroovyMethods.asBoolean(ctx.ASSIGN()) ? this.createGroovyTokenByType(ctx.ASSIGN().getSymbol(), 100) : new Token(100, ASSIGN_STR, ctx.start.getLine(), 1);
        return PositionConfigureUtils.configureAST(new DeclarationExpression(PositionConfigureUtils.configureAST(new VariableExpression(this.visitVariableDeclaratorId(ctx.variableDeclaratorId()).getName(), variableType), ctx.variableDeclaratorId()), token, this.visitVariableInitializer(ctx.variableInitializer())), ctx);
    }

    @Override
    public Expression visitVariableInitializer(GroovyParser.VariableInitializerContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return EmptyExpression.INSTANCE;
        }
        return PositionConfigureUtils.configureAST(this.visitEnhancedStatementExpression(ctx.enhancedStatementExpression()), ctx);
    }

    @Override
    public List<Expression> visitVariableInitializers(GroovyParser.VariableInitializersContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return Collections.emptyList();
        }
        return ctx.variableInitializer().stream().map(this::visitVariableInitializer).collect(Collectors.toList());
    }

    @Override
    public List<Expression> visitArrayInitializer(GroovyParser.ArrayInitializerContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return Collections.emptyList();
        }
        try {
            ++this.visitingArrayInitializerCount;
            Object object = this.visitVariableInitializers(ctx.variableInitializers());
            return object;
        }
        finally {
            --this.visitingArrayInitializerCount;
        }
    }

    @Override
    public Statement visitBlock(GroovyParser.BlockContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return this.createBlockStatement(new Statement[0]);
        }
        return PositionConfigureUtils.configureAST(this.visitBlockStatementsOpt(ctx.blockStatementsOpt()), ctx);
    }

    @Override
    public ExpressionStatement visitCommandExprAlt(GroovyParser.CommandExprAltContext ctx) {
        return PositionConfigureUtils.configureAST(new ExpressionStatement(this.visitCommandExpression(ctx.commandExpression())), ctx);
    }

    @Override
    public Expression visitCommandExpression(GroovyParser.CommandExpressionContext ctx) {
        boolean hasArgumentList = DefaultGroovyMethods.asBoolean(ctx.enhancedArgumentListInPar());
        boolean hasCommandArgument = DefaultGroovyMethods.asBoolean(ctx.commandArgument());
        if ((hasArgumentList || hasCommandArgument) && this.visitingArrayInitializerCount > 0) {
            throw this.createParsingFailedException("Command chain expression can not be used in array initializer", ctx);
        }
        Expression baseExpr = (Expression)this.visit(ctx.expression());
        if ((hasArgumentList || hasCommandArgument) && !this.isInsideParentheses(baseExpr) && baseExpr instanceof BinaryExpression && !"[".equals(((BinaryExpression)baseExpr).getOperation().getText())) {
            throw this.createParsingFailedException("Unexpected input: '" + this.getOriginalText(ctx.expression()) + SQ_STR, ctx.expression());
        }
        Expression methodCallExpression = null;
        if (hasArgumentList) {
            Expression arguments = this.visitEnhancedArgumentListInPar(ctx.enhancedArgumentListInPar());
            if (baseExpr instanceof PropertyExpression) {
                methodCallExpression = PositionConfigureUtils.configureAST(this.createMethodCallExpression((PropertyExpression)baseExpr, arguments), ctx.expression(), (ASTNode)arguments);
            } else if (baseExpr instanceof MethodCallExpression && !this.isInsideParentheses(baseExpr)) {
                if (DefaultGroovyMethods.asBoolean(arguments)) {
                    throw new GroovyBugError("When baseExpr is a instance of MethodCallExpression, which should follow NO argumentList");
                }
                methodCallExpression = (MethodCallExpression)baseExpr;
            } else if (!this.isInsideParentheses(baseExpr) && (baseExpr instanceof VariableExpression || baseExpr instanceof GStringExpression || baseExpr instanceof ConstantExpression && this.isTrue(baseExpr, IS_STRING))) {
                this.validateInvalidMethodDefinition(baseExpr, arguments);
                methodCallExpression = PositionConfigureUtils.configureAST(this.createMethodCallExpression(baseExpr, arguments), ctx.expression(), (ASTNode)arguments);
            } else {
                methodCallExpression = PositionConfigureUtils.configureAST(this.createCallMethodCallExpression(baseExpr, arguments), ctx.expression(), (ASTNode)arguments);
            }
            methodCallExpression.putNodeMetaData(IS_COMMAND_EXPRESSION, Boolean.TRUE);
            if (!hasCommandArgument) {
                return methodCallExpression;
            }
        }
        if (hasCommandArgument) {
            baseExpr.putNodeMetaData(IS_COMMAND_EXPRESSION, Boolean.TRUE);
        }
        return PositionConfigureUtils.configureAST((Expression)ctx.commandArgument().stream().map(e -> e).reduce(methodCallExpression != null ? methodCallExpression : baseExpr, (r, e) -> {
            GroovyParser.CommandArgumentContext commandArgumentContext = (GroovyParser.CommandArgumentContext)e;
            commandArgumentContext.putNodeMetaData(CMD_EXPRESSION_BASE_EXPR, r);
            return this.visitCommandArgument(commandArgumentContext);
        }), ctx);
    }

    private void validateInvalidMethodDefinition(Expression baseExpr, Expression arguments) {
        Expression lastArgumentExpression;
        List<Expression> methodCallArgumentExpressionList;
        int argumentCnt;
        MethodCallExpression mce;
        Expression methodCallArguments;
        Expression expression;
        List<Expression> expressionList;
        if (baseExpr instanceof VariableExpression && (this.isBuiltInType(baseExpr) || Character.isUpperCase(baseExpr.getText().codePointAt(0))) && arguments instanceof ArgumentListExpression && 1 == (expressionList = ((ArgumentListExpression)arguments).getExpressions()).size() && (expression = expressionList.get(0)) instanceof MethodCallExpression && (methodCallArguments = (mce = (MethodCallExpression)expression).getArguments()) instanceof ArgumentListExpression && (argumentCnt = (methodCallArgumentExpressionList = ((ArgumentListExpression)methodCallArguments).getExpressions()).size()) > 0 && (lastArgumentExpression = methodCallArgumentExpressionList.get(argumentCnt - 1)) instanceof ClosureExpression && ClosureUtils.hasImplicitParameter((ClosureExpression)lastArgumentExpression)) {
            throw this.createParsingFailedException("Method definition not expected here", Tuple.tuple(baseExpr.getLineNumber(), baseExpr.getColumnNumber()), Tuple.tuple(expression.getLastLineNumber(), expression.getLastColumnNumber()));
        }
    }

    @Override
    public Expression visitCommandArgument(GroovyParser.CommandArgumentContext ctx) {
        Expression baseExpr = (Expression)ctx.getNodeMetaData(CMD_EXPRESSION_BASE_EXPR);
        Expression primaryExpr = (Expression)this.visit(ctx.primary());
        if (DefaultGroovyMethods.asBoolean(ctx.enhancedArgumentListInPar())) {
            if (baseExpr instanceof PropertyExpression) {
                throw this.createParsingFailedException("Unsupported command argument: " + ctx.getText(), ctx);
            }
            MethodCallExpression methodCallExpression = new MethodCallExpression(baseExpr, this.createConstantExpression(primaryExpr), this.visitEnhancedArgumentListInPar(ctx.enhancedArgumentListInPar()));
            methodCallExpression.setImplicitThis(false);
            return PositionConfigureUtils.configureAST(methodCallExpression, ctx);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.pathElement())) {
            Expression pathExpression = this.createPathExpression(PositionConfigureUtils.configureAST(new PropertyExpression(baseExpr, this.createConstantExpression(primaryExpr)), primaryExpr), ctx.pathElement());
            return PositionConfigureUtils.configureAST(pathExpression, ctx);
        }
        return PositionConfigureUtils.configureAST(new PropertyExpression(baseExpr, primaryExpr instanceof VariableExpression ? this.createConstantExpression(primaryExpr) : primaryExpr), primaryExpr);
    }

    @Override
    public ClassNode visitCastParExpression(GroovyParser.CastParExpressionContext ctx) {
        return this.visitType(ctx.type());
    }

    @Override
    public Expression visitParExpression(GroovyParser.ParExpressionContext ctx) {
        Expression expression = this.visitExpressionInPar(ctx.expressionInPar());
        expression.getNodeMetaData(INSIDE_PARENTHESES_LEVEL, k -> new AtomicInteger()).getAndAdd(1);
        return PositionConfigureUtils.configureAST(expression, ctx);
    }

    @Override
    public Expression visitExpressionInPar(GroovyParser.ExpressionInParContext ctx) {
        return this.visitEnhancedStatementExpression(ctx.enhancedStatementExpression());
    }

    @Override
    public Expression visitEnhancedStatementExpression(GroovyParser.EnhancedStatementExpressionContext ctx) {
        Expression expression;
        if (DefaultGroovyMethods.asBoolean(ctx.statementExpression())) {
            expression = ((ExpressionStatement)this.visit(ctx.statementExpression())).getExpression();
        } else if (DefaultGroovyMethods.asBoolean(ctx.standardLambdaExpression())) {
            expression = this.visitStandardLambdaExpression(ctx.standardLambdaExpression());
        } else {
            throw this.createParsingFailedException("Unsupported enhanced statement expression: " + ctx.getText(), ctx);
        }
        return PositionConfigureUtils.configureAST(expression, ctx);
    }

    @Override
    public Expression visitPathExpression(GroovyParser.PathExpressionContext ctx) {
        TerminalNode staticTerminalNode = ctx.STATIC();
        Expression primaryExpr = DefaultGroovyMethods.asBoolean(staticTerminalNode) ? (Expression)PositionConfigureUtils.configureAST(new VariableExpression(staticTerminalNode.getText()), staticTerminalNode) : (Expression)this.visit(ctx.primary());
        return this.createPathExpression(primaryExpr, ctx.pathElement());
    }

    @Override
    public Expression visitPathElement(GroovyParser.PathElementContext ctx) {
        Expression baseExpr = (Expression)ctx.getNodeMetaData(PATH_EXPRESSION_BASE_EXPR);
        Objects.requireNonNull(baseExpr, "baseExpr is required!");
        if (DefaultGroovyMethods.asBoolean(ctx.namePart())) {
            Expression namePartExpr = this.visitNamePart(ctx.namePart());
            GenericsType[] genericsTypes = this.visitNonWildcardTypeArguments(ctx.nonWildcardTypeArguments());
            if (DefaultGroovyMethods.asBoolean(ctx.DOT())) {
                boolean isSafeChain = this.isTrue(baseExpr, PATH_EXPRESSION_BASE_EXPR_SAFE_CHAIN);
                return this.createDotExpression(ctx, baseExpr, namePartExpr, genericsTypes, isSafeChain);
            }
            if (DefaultGroovyMethods.asBoolean(ctx.SAFE_DOT())) {
                return this.createDotExpression(ctx, baseExpr, namePartExpr, genericsTypes, true);
            }
            if (DefaultGroovyMethods.asBoolean(ctx.SAFE_CHAIN_DOT())) {
                Expression expression = this.createDotExpression(ctx, baseExpr, namePartExpr, genericsTypes, true);
                expression.putNodeMetaData(PATH_EXPRESSION_BASE_EXPR_SAFE_CHAIN, true);
                return expression;
            }
            if (DefaultGroovyMethods.asBoolean(ctx.METHOD_POINTER())) {
                return PositionConfigureUtils.configureAST(new MethodPointerExpression(baseExpr, namePartExpr), ctx);
            }
            if (DefaultGroovyMethods.asBoolean(ctx.METHOD_REFERENCE())) {
                return PositionConfigureUtils.configureAST(new MethodReferenceExpression(baseExpr, namePartExpr), ctx);
            }
            if (DefaultGroovyMethods.asBoolean(ctx.SPREAD_DOT())) {
                if (DefaultGroovyMethods.asBoolean(ctx.AT())) {
                    AttributeExpression attributeExpression = new AttributeExpression(baseExpr, namePartExpr, true);
                    attributeExpression.setSpreadSafe(true);
                    return PositionConfigureUtils.configureAST(attributeExpression, ctx);
                }
                PropertyExpression propertyExpression = new PropertyExpression(baseExpr, namePartExpr, true);
                propertyExpression.putNodeMetaData(PATH_EXPRESSION_BASE_EXPR_GENERICS_TYPES, genericsTypes);
                propertyExpression.setSpreadSafe(true);
                return PositionConfigureUtils.configureAST(propertyExpression, ctx);
            }
        } else {
            if (DefaultGroovyMethods.asBoolean(ctx.creator())) {
                GroovyParser.CreatorContext creatorContext = ctx.creator();
                creatorContext.putNodeMetaData(ENCLOSING_INSTANCE_EXPRESSION, baseExpr);
                return PositionConfigureUtils.configureAST(this.visitCreator(creatorContext), ctx);
            }
            if (DefaultGroovyMethods.asBoolean(ctx.indexPropertyArgs())) {
                Object tuple = this.visitIndexPropertyArgs(ctx.indexPropertyArgs());
                boolean isSafeChain = this.isTrue(baseExpr, PATH_EXPRESSION_BASE_EXPR_SAFE_CHAIN);
                return PositionConfigureUtils.configureAST(new BinaryExpression(baseExpr, this.createGroovyToken((groovyjarjarantlr4.v4.runtime.Token)((Tuple2)tuple).getV1()), (Expression)((Tuple2)tuple).getV2(), isSafeChain || DefaultGroovyMethods.asBoolean(ctx.indexPropertyArgs().SAFE_INDEX())), ctx);
            }
            if (DefaultGroovyMethods.asBoolean(ctx.namedPropertyArgs())) {
                Expression firstKeyExpression;
                Expression right;
                Object mapEntryExpressionList = this.visitNamedPropertyArgs(ctx.namedPropertyArgs());
                int mapEntryExpressionListSize = mapEntryExpressionList.size();
                if (mapEntryExpressionListSize == 0) {
                    right = PositionConfigureUtils.configureAST(new SpreadMapExpression(PositionConfigureUtils.configureAST(new MapExpression(), ctx.namedPropertyArgs())), ctx.namedPropertyArgs());
                } else if (mapEntryExpressionListSize == 1 && (firstKeyExpression = ((MapEntryExpression)mapEntryExpressionList.get(0)).getKeyExpression()) instanceof SpreadMapExpression) {
                    right = firstKeyExpression;
                } else {
                    ListExpression listExpression = PositionConfigureUtils.configureAST(new ListExpression(mapEntryExpressionList.stream().map(e -> {
                        if (e.getKeyExpression() instanceof SpreadMapExpression) {
                            return e.getKeyExpression();
                        }
                        return e;
                    }).collect(Collectors.toList())), ctx.namedPropertyArgs());
                    listExpression.setWrapped(true);
                    right = listExpression;
                }
                GroovyParser.NamedPropertyArgsContext namedPropertyArgsContext = ctx.namedPropertyArgs();
                groovyjarjarantlr4.v4.runtime.Token token = (namedPropertyArgsContext.LBRACK() == null ? namedPropertyArgsContext.SAFE_INDEX() : namedPropertyArgsContext.LBRACK()).getSymbol();
                return PositionConfigureUtils.configureAST(new BinaryExpression(baseExpr, this.createGroovyToken(token), right), ctx);
            }
            if (DefaultGroovyMethods.asBoolean(ctx.arguments())) {
                String baseExprText;
                Expression argumentsExpr = this.visitArguments(ctx.arguments());
                PositionConfigureUtils.configureAST(argumentsExpr, ctx);
                if (this.isInsideParentheses(baseExpr)) {
                    return PositionConfigureUtils.configureAST(this.createCallMethodCallExpression(baseExpr, argumentsExpr), ctx);
                }
                if (baseExpr instanceof AttributeExpression) {
                    AttributeExpression attributeExpression = (AttributeExpression)baseExpr;
                    attributeExpression.setSpreadSafe(false);
                    return PositionConfigureUtils.configureAST(this.createCallMethodCallExpression(attributeExpression, argumentsExpr, true), ctx);
                }
                if (baseExpr instanceof PropertyExpression) {
                    MethodCallExpression methodCallExpression = this.createMethodCallExpression((PropertyExpression)baseExpr, argumentsExpr);
                    return PositionConfigureUtils.configureAST(methodCallExpression, ctx);
                }
                if (baseExpr instanceof VariableExpression) {
                    baseExprText = baseExpr.getText();
                    if (VOID_STR.equals(baseExprText)) {
                        return PositionConfigureUtils.configureAST(this.createCallMethodCallExpression(this.createConstantExpression(baseExpr), argumentsExpr), ctx);
                    }
                    if (TypeUtil.isPrimitiveType(baseExprText)) {
                        throw this.createParsingFailedException("Primitive type literal: " + baseExprText + " cannot be used as a method name", ctx);
                    }
                }
                if (baseExpr instanceof VariableExpression || baseExpr instanceof GStringExpression || baseExpr instanceof ConstantExpression && this.isTrue(baseExpr, IS_STRING)) {
                    baseExprText = baseExpr.getText();
                    if (THIS_STR.equals(baseExprText) || SUPER_STR.equals(baseExprText)) {
                        if (this.visitingClosureCount > 0) {
                            return PositionConfigureUtils.configureAST(new MethodCallExpression(baseExpr, baseExprText, argumentsExpr), ctx);
                        }
                        return PositionConfigureUtils.configureAST(new ConstructorCallExpression(SUPER_STR.equals(baseExprText) ? ClassNode.SUPER : ClassNode.THIS, argumentsExpr), ctx);
                    }
                    MethodCallExpression methodCallExpression = this.createMethodCallExpression(baseExpr, argumentsExpr);
                    return PositionConfigureUtils.configureAST(methodCallExpression, ctx);
                }
                return PositionConfigureUtils.configureAST(this.createCallMethodCallExpression(baseExpr, argumentsExpr), ctx);
            }
            if (DefaultGroovyMethods.asBoolean(ctx.closureOrLambdaExpression())) {
                MethodCallExpression methodCallExpression;
                ClosureExpression closureExpression = this.visitClosureOrLambdaExpression(ctx.closureOrLambdaExpression());
                if (baseExpr instanceof MethodCallExpression) {
                    methodCallExpression = (MethodCallExpression)baseExpr;
                    Expression argumentsExpression = methodCallExpression.getArguments();
                    if (argumentsExpression instanceof ArgumentListExpression) {
                        ArgumentListExpression argumentListExpression = (ArgumentListExpression)argumentsExpression;
                        argumentListExpression.getExpressions().add(closureExpression);
                        return PositionConfigureUtils.configureAST(methodCallExpression, ctx);
                    }
                    if (argumentsExpression instanceof TupleExpression) {
                        TupleExpression tupleExpression = (TupleExpression)argumentsExpression;
                        NamedArgumentListExpression namedArgumentListExpression = (NamedArgumentListExpression)tupleExpression.getExpression(0);
                        if (DefaultGroovyMethods.asBoolean(tupleExpression.getExpressions())) {
                            methodCallExpression.setArguments(PositionConfigureUtils.configureAST(new ArgumentListExpression(PositionConfigureUtils.configureAST(new MapExpression(namedArgumentListExpression.getMapEntryExpressions()), namedArgumentListExpression), closureExpression), tupleExpression));
                        } else {
                            methodCallExpression.setArguments(PositionConfigureUtils.configureAST(new ArgumentListExpression(closureExpression), tupleExpression));
                        }
                        return PositionConfigureUtils.configureAST(methodCallExpression, ctx);
                    }
                }
                if (baseExpr instanceof PropertyExpression) {
                    methodCallExpression = this.createMethodCallExpression((PropertyExpression)baseExpr, (Expression)PositionConfigureUtils.configureAST(new ArgumentListExpression(closureExpression), closureExpression));
                    return PositionConfigureUtils.configureAST(methodCallExpression, ctx);
                }
                if (baseExpr instanceof VariableExpression || baseExpr instanceof GStringExpression || baseExpr instanceof ConstantExpression && this.isTrue(baseExpr, IS_STRING)) {
                    methodCallExpression = this.createMethodCallExpression(baseExpr, (Expression)PositionConfigureUtils.configureAST(new ArgumentListExpression(closureExpression), closureExpression));
                    return PositionConfigureUtils.configureAST(methodCallExpression, ctx);
                }
                methodCallExpression = this.createCallMethodCallExpression(baseExpr, PositionConfigureUtils.configureAST(new ArgumentListExpression(closureExpression), closureExpression));
                return PositionConfigureUtils.configureAST(methodCallExpression, ctx);
            }
        }
        throw this.createParsingFailedException("Unsupported path element: " + ctx.getText(), ctx);
    }

    private Expression createDotExpression(GroovyParser.PathElementContext ctx, Expression baseExpr, Expression namePartExpr, GenericsType[] genericsTypes, boolean safe) {
        if (DefaultGroovyMethods.asBoolean(ctx.AT())) {
            return PositionConfigureUtils.configureAST(new AttributeExpression(baseExpr, namePartExpr, safe), ctx);
        }
        PropertyExpression propertyExpression = new PropertyExpression(baseExpr, namePartExpr, safe);
        propertyExpression.putNodeMetaData(PATH_EXPRESSION_BASE_EXPR_GENERICS_TYPES, genericsTypes);
        return PositionConfigureUtils.configureAST(propertyExpression, ctx);
    }

    private MethodCallExpression createCallMethodCallExpression(Expression baseExpr, Expression argumentsExpr) {
        return this.createCallMethodCallExpression(baseExpr, argumentsExpr, false);
    }

    private MethodCallExpression createCallMethodCallExpression(Expression baseExpr, Expression argumentsExpr, boolean implicitThis) {
        MethodCallExpression methodCallExpression = new MethodCallExpression(baseExpr, CALL_STR, argumentsExpr);
        methodCallExpression.setImplicitThis(implicitThis);
        return methodCallExpression;
    }

    @Override
    public GenericsType[] visitNonWildcardTypeArguments(GroovyParser.NonWildcardTypeArgumentsContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return null;
        }
        return (GenericsType[])Arrays.stream(this.visitTypeList(ctx.typeList())).map(this::createGenericsType).toArray(GenericsType[]::new);
    }

    @Override
    public ClassNode[] visitTypeList(GroovyParser.TypeListContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return ClassNode.EMPTY_ARRAY;
        }
        return (ClassNode[])ctx.type().stream().map(this::visitType).toArray(ClassNode[]::new);
    }

    @Override
    public Expression visitArguments(GroovyParser.ArgumentsContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx) && DefaultGroovyMethods.asBoolean(ctx.COMMA()) && !DefaultGroovyMethods.asBoolean(ctx.enhancedArgumentListInPar())) {
            throw this.createParsingFailedException("Expression expected", ctx.COMMA());
        }
        if (!DefaultGroovyMethods.asBoolean(ctx) || !DefaultGroovyMethods.asBoolean(ctx.enhancedArgumentListInPar())) {
            return new ArgumentListExpression();
        }
        return PositionConfigureUtils.configureAST(this.visitEnhancedArgumentListInPar(ctx.enhancedArgumentListInPar()), ctx);
    }

    @Override
    public Expression visitEnhancedArgumentListInPar(GroovyParser.EnhancedArgumentListInParContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return null;
        }
        LinkedList<Expression> expressionList = new LinkedList<Expression>();
        LinkedList<MapEntryExpression> mapEntryExpressionList = new LinkedList<MapEntryExpression>();
        ctx.enhancedArgumentListElement().stream().map(this::visitEnhancedArgumentListElement).forEach(e -> {
            if (e instanceof MapEntryExpression) {
                MapEntryExpression mapEntryExpression = (MapEntryExpression)e;
                this.validateDuplicatedNamedParameter(mapEntryExpressionList, mapEntryExpression);
                mapEntryExpressionList.add(mapEntryExpression);
            } else {
                expressionList.add((Expression)e);
            }
        });
        if (!DefaultGroovyMethods.asBoolean(mapEntryExpressionList)) {
            return PositionConfigureUtils.configureAST(new ArgumentListExpression(expressionList), ctx);
        }
        if (!DefaultGroovyMethods.asBoolean(expressionList)) {
            return PositionConfigureUtils.configureAST(new TupleExpression(PositionConfigureUtils.configureAST(new NamedArgumentListExpression(mapEntryExpressionList), ctx)), ctx);
        }
        if (DefaultGroovyMethods.asBoolean(mapEntryExpressionList) && DefaultGroovyMethods.asBoolean(expressionList)) {
            ArgumentListExpression argumentListExpression = new ArgumentListExpression(expressionList);
            argumentListExpression.getExpressions().add(0, PositionConfigureUtils.configureAST(new MapExpression(mapEntryExpressionList), ctx));
            return PositionConfigureUtils.configureAST(argumentListExpression, ctx);
        }
        throw this.createParsingFailedException("Unsupported argument list: " + ctx.getText(), ctx);
    }

    private void validateDuplicatedNamedParameter(List<MapEntryExpression> mapEntryExpressionList, MapEntryExpression mapEntryExpression) {
        Expression keyExpression = mapEntryExpression.getKeyExpression();
        if (keyExpression == null || this.isInsideParentheses(keyExpression)) {
            return;
        }
        String parameterName = keyExpression.getText();
        boolean isDuplicatedNamedParameter = mapEntryExpressionList.stream().anyMatch(m -> m.getKeyExpression().getText().equals(parameterName));
        if (!isDuplicatedNamedParameter) {
            return;
        }
        throw this.createParsingFailedException("Duplicated named parameter '" + parameterName + "' found", mapEntryExpression);
    }

    @Override
    public Expression visitEnhancedArgumentListElement(GroovyParser.EnhancedArgumentListElementContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.expressionListElement())) {
            return PositionConfigureUtils.configureAST(this.visitExpressionListElement(ctx.expressionListElement()), ctx);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.standardLambdaExpression())) {
            return PositionConfigureUtils.configureAST(this.visitStandardLambdaExpression(ctx.standardLambdaExpression()), ctx);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.mapEntry())) {
            return PositionConfigureUtils.configureAST(this.visitMapEntry(ctx.mapEntry()), ctx);
        }
        throw this.createParsingFailedException("Unsupported enhanced argument list element: " + ctx.getText(), ctx);
    }

    @Override
    public ConstantExpression visitStringLiteral(GroovyParser.StringLiteralContext ctx) {
        String text = this.parseStringLiteral(ctx.StringLiteral().getText());
        ConstantExpression constantExpression = new ConstantExpression(text);
        constantExpression.putNodeMetaData(IS_STRING, Boolean.TRUE);
        return PositionConfigureUtils.configureAST(constantExpression, ctx);
    }

    private String parseStringLiteral(String text) {
        int slashyType = this.getSlashyType(text);
        boolean startsWithSlash = false;
        if (text.startsWith(TSQ_STR) || text.startsWith(TDQ_STR)) {
            text = StringUtils.removeCR(text);
            text = StringUtils.trimQuotations(text, 3);
        } else if (text.startsWith(SQ_STR) || text.startsWith(DQ_STR) || (startsWithSlash = text.startsWith(SLASH_STR))) {
            if (startsWithSlash) {
                text = StringUtils.removeCR(text);
            }
            text = StringUtils.trimQuotations(text, 1);
        } else if (text.startsWith(DOLLAR_SLASH_STR)) {
            text = StringUtils.removeCR(text);
            text = StringUtils.trimQuotations(text, 2);
        }
        return StringUtils.replaceEscapes(text, slashyType);
    }

    private int getSlashyType(String text) {
        return text.startsWith(SLASH_STR) ? 1 : (text.startsWith(DOLLAR_SLASH_STR) ? 2 : 0);
    }

    @Override
    public Tuple2<groovyjarjarantlr4.v4.runtime.Token, Expression> visitIndexPropertyArgs(GroovyParser.IndexPropertyArgsContext ctx) {
        Object expressionList = this.visitExpressionList(ctx.expressionList());
        groovyjarjarantlr4.v4.runtime.Token token = (ctx.LBRACK() == null ? ctx.SAFE_INDEX() : ctx.LBRACK()).getSymbol();
        if (expressionList.size() == 1) {
            Expression indexExpr;
            Expression expr = (Expression)expressionList.get(0);
            if (expr instanceof SpreadExpression) {
                ListExpression listExpression = new ListExpression((List<Expression>)expressionList);
                listExpression.setWrapped(false);
                indexExpr = listExpression;
            } else {
                indexExpr = expr;
            }
            return Tuple.tuple(token, indexExpr);
        }
        ListExpression listExpression = new ListExpression((List<Expression>)expressionList);
        listExpression.setWrapped(true);
        return Tuple.tuple(token, (Expression)PositionConfigureUtils.configureAST(listExpression, ctx));
    }

    @Override
    public List<MapEntryExpression> visitNamedPropertyArgs(GroovyParser.NamedPropertyArgsContext ctx) {
        return this.visitMapEntryList(ctx.mapEntryList());
    }

    @Override
    public Expression visitNamePart(GroovyParser.NamePartContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.identifier())) {
            return PositionConfigureUtils.configureAST(new ConstantExpression(this.visitIdentifier(ctx.identifier())), ctx);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.stringLiteral())) {
            return PositionConfigureUtils.configureAST(this.visitStringLiteral(ctx.stringLiteral()), ctx);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.dynamicMemberName())) {
            return PositionConfigureUtils.configureAST(this.visitDynamicMemberName(ctx.dynamicMemberName()), ctx);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.keywords())) {
            return PositionConfigureUtils.configureAST(new ConstantExpression(ctx.keywords().getText()), ctx);
        }
        throw this.createParsingFailedException("Unsupported name part: " + ctx.getText(), ctx);
    }

    @Override
    public Expression visitDynamicMemberName(GroovyParser.DynamicMemberNameContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.parExpression())) {
            return PositionConfigureUtils.configureAST(this.visitParExpression(ctx.parExpression()), ctx);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.gstring())) {
            return PositionConfigureUtils.configureAST(this.visitGstring(ctx.gstring()), ctx);
        }
        throw this.createParsingFailedException("Unsupported dynamic member name: " + ctx.getText(), ctx);
    }

    @Override
    public Expression visitPostfixExpression(GroovyParser.PostfixExpressionContext ctx) {
        Expression pathExpr = this.visitPathExpression(ctx.pathExpression());
        if (DefaultGroovyMethods.asBoolean(ctx.op)) {
            PostfixExpression postfixExpression = new PostfixExpression(pathExpr, this.createGroovyToken(ctx.op));
            if (this.visitingAssertStatementCount > 0) {
                return PositionConfigureUtils.configureAST(postfixExpression, ctx.op);
            }
            return PositionConfigureUtils.configureAST(postfixExpression, ctx);
        }
        return PositionConfigureUtils.configureAST(pathExpr, ctx);
    }

    @Override
    public Expression visitUnaryNotExprAlt(GroovyParser.UnaryNotExprAltContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.NOT())) {
            return PositionConfigureUtils.configureAST(new NotExpression((Expression)this.visit(ctx.expression())), ctx);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.BITNOT())) {
            return PositionConfigureUtils.configureAST(new BitwiseNegationExpression((Expression)this.visit(ctx.expression())), ctx);
        }
        throw this.createParsingFailedException("Unsupported unary expression: " + ctx.getText(), ctx);
    }

    @Override
    public CastExpression visitCastExprAlt(GroovyParser.CastExprAltContext ctx) {
        Expression expr = (Expression)this.visit(ctx.expression());
        if (expr instanceof VariableExpression && ((VariableExpression)expr).isSuperExpression()) {
            this.createParsingFailedException("Cannot cast or coerce `super`", ctx);
        }
        CastExpression cast = new CastExpression(this.visitCastParExpression(ctx.castParExpression()), expr);
        return PositionConfigureUtils.configureAST(cast, ctx);
    }

    @Override
    public BinaryExpression visitPowerExprAlt(GroovyParser.PowerExprAltContext ctx) {
        return this.createBinaryExpression(ctx.left, ctx.op, ctx.right, ctx);
    }

    @Override
    public Expression visitUnaryAddExprAlt(GroovyParser.UnaryAddExprAltContext ctx) {
        Expression expression = (Expression)this.visit(ctx.expression());
        switch (ctx.op.getType()) {
            case 110: {
                if (this.isNonStringConstantOutsideParentheses(expression)) {
                    return PositionConfigureUtils.configureAST(expression, ctx);
                }
                return PositionConfigureUtils.configureAST(new UnaryPlusExpression(expression), ctx);
            }
            case 111: {
                if (this.isNonStringConstantOutsideParentheses(expression)) {
                    ConstantExpression constantExpression = (ConstantExpression)expression;
                    try {
                        String integerLiteralText = (String)constantExpression.getNodeMetaData(INTEGER_LITERAL_TEXT);
                        if (integerLiteralText != null) {
                            ConstantExpression result = new ConstantExpression(Numbers.parseInteger(SUB_STR + integerLiteralText), true);
                            this.numberFormatError = null;
                            return PositionConfigureUtils.configureAST(result, ctx);
                        }
                        String floatingPointLiteralText = (String)constantExpression.getNodeMetaData(FLOATING_POINT_LITERAL_TEXT);
                        if (floatingPointLiteralText != null) {
                            ConstantExpression result = new ConstantExpression(Numbers.parseDecimal(SUB_STR + floatingPointLiteralText), true);
                            this.numberFormatError = null;
                            return PositionConfigureUtils.configureAST(result, ctx);
                        }
                    }
                    catch (Exception e) {
                        throw this.createParsingFailedException(e.getMessage(), ctx);
                    }
                    throw new GroovyBugError("Failed to find the original number literal text: " + constantExpression.getText());
                }
                return PositionConfigureUtils.configureAST(new UnaryMinusExpression(expression), ctx);
            }
            case 108: 
            case 109: {
                return PositionConfigureUtils.configureAST(new PrefixExpression(this.createGroovyToken(ctx.op), expression), ctx);
            }
        }
        throw this.createParsingFailedException("Unsupported unary operation: " + ctx.getText(), ctx);
    }

    private boolean isNonStringConstantOutsideParentheses(Expression expression) {
        return expression instanceof ConstantExpression && !(((ConstantExpression)expression).getValue() instanceof String) && !this.isInsideParentheses(expression);
    }

    @Override
    public BinaryExpression visitMultiplicativeExprAlt(GroovyParser.MultiplicativeExprAltContext ctx) {
        return this.createBinaryExpression(ctx.left, ctx.op, ctx.right, ctx);
    }

    @Override
    public BinaryExpression visitAdditiveExprAlt(GroovyParser.AdditiveExprAltContext ctx) {
        return this.createBinaryExpression(ctx.left, ctx.op, ctx.right, ctx);
    }

    @Override
    public Expression visitShiftExprAlt(GroovyParser.ShiftExprAltContext ctx) {
        groovyjarjarantlr4.v4.runtime.Token antlrToken;
        Token op;
        Expression left = (Expression)this.visit(ctx.left);
        Expression right = (Expression)this.visit(ctx.right);
        if (DefaultGroovyMethods.asBoolean(ctx.rangeOp)) {
            return PositionConfigureUtils.configureAST(new RangeExpression(left, right, ctx.rangeOp.getText().startsWith("<"), ctx.rangeOp.getText().endsWith("<")), ctx);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.dlOp)) {
            op = this.createGroovyToken(ctx.dlOp, 2);
            antlrToken = ctx.dlOp;
        } else if (DefaultGroovyMethods.asBoolean(ctx.dgOp)) {
            op = this.createGroovyToken(ctx.dgOp, 2);
            antlrToken = ctx.dgOp;
        } else if (DefaultGroovyMethods.asBoolean(ctx.tgOp)) {
            op = this.createGroovyToken(ctx.tgOp, 3);
            antlrToken = ctx.tgOp;
        } else {
            throw this.createParsingFailedException("Unsupported shift expression: " + ctx.getText(), ctx);
        }
        BinaryExpression binaryExpression = new BinaryExpression(left, op, right);
        if (this.isTrue(ctx, IS_INSIDE_CONDITIONAL_EXPRESSION)) {
            return PositionConfigureUtils.configureAST(binaryExpression, antlrToken);
        }
        return PositionConfigureUtils.configureAST(binaryExpression, ctx);
    }

    @Override
    public Expression visitRelationalExprAlt(GroovyParser.RelationalExprAltContext ctx) {
        switch (ctx.op.getType()) {
            case 7: {
                Expression expr = (Expression)this.visit(ctx.left);
                if (expr instanceof VariableExpression && ((VariableExpression)expr).isSuperExpression()) {
                    this.createParsingFailedException("Cannot cast or coerce `super`", ctx);
                }
                CastExpression cast = CastExpression.asExpression(this.visitType(ctx.type()), expr);
                return PositionConfigureUtils.configureAST(cast, ctx);
            }
            case 35: 
            case 84: {
                ctx.type().putNodeMetaData(IS_INSIDE_INSTANCEOF_EXPR, Boolean.TRUE);
                return PositionConfigureUtils.configureAST(new BinaryExpression((Expression)this.visit(ctx.left), this.createGroovyToken(ctx.op), PositionConfigureUtils.configureAST(new ClassExpression(this.visitType(ctx.type())), ctx.type())), ctx);
            }
            case 9: 
            case 85: 
            case 96: 
            case 97: 
            case 103: 
            case 104: {
                return this.createBinaryExpression(ctx.left, ctx.op, ctx.right, ctx);
            }
        }
        throw this.createParsingFailedException("Unsupported relational expression: " + ctx.getText(), ctx);
    }

    @Override
    public BinaryExpression visitEqualityExprAlt(GroovyParser.EqualityExprAltContext ctx) {
        return PositionConfigureUtils.configureAST(this.createBinaryExpression(ctx.left, ctx.op, ctx.right), ctx);
    }

    @Override
    public BinaryExpression visitRegexExprAlt(GroovyParser.RegexExprAltContext ctx) {
        return PositionConfigureUtils.configureAST(this.createBinaryExpression(ctx.left, ctx.op, ctx.right), ctx);
    }

    @Override
    public BinaryExpression visitAndExprAlt(GroovyParser.AndExprAltContext ctx) {
        return this.createBinaryExpression(ctx.left, ctx.op, ctx.right, ctx);
    }

    @Override
    public BinaryExpression visitExclusiveOrExprAlt(GroovyParser.ExclusiveOrExprAltContext ctx) {
        return this.createBinaryExpression(ctx.left, ctx.op, ctx.right, ctx);
    }

    @Override
    public BinaryExpression visitInclusiveOrExprAlt(GroovyParser.InclusiveOrExprAltContext ctx) {
        return this.createBinaryExpression(ctx.left, ctx.op, ctx.right, ctx);
    }

    @Override
    public BinaryExpression visitLogicalAndExprAlt(GroovyParser.LogicalAndExprAltContext ctx) {
        return PositionConfigureUtils.configureAST(this.createBinaryExpression(ctx.left, ctx.op, ctx.right), ctx);
    }

    @Override
    public BinaryExpression visitLogicalOrExprAlt(GroovyParser.LogicalOrExprAltContext ctx) {
        return PositionConfigureUtils.configureAST(this.createBinaryExpression(ctx.left, ctx.op, ctx.right), ctx);
    }

    @Override
    public Expression visitConditionalExprAlt(GroovyParser.ConditionalExprAltContext ctx) {
        ctx.fb.putNodeMetaData(IS_INSIDE_CONDITIONAL_EXPRESSION, true);
        if (DefaultGroovyMethods.asBoolean(ctx.ELVIS())) {
            return PositionConfigureUtils.configureAST(new ElvisOperatorExpression((Expression)this.visit(ctx.con), (Expression)this.visit(ctx.fb)), ctx);
        }
        ctx.tb.putNodeMetaData(IS_INSIDE_CONDITIONAL_EXPRESSION, true);
        return PositionConfigureUtils.configureAST(new TernaryExpression(PositionConfigureUtils.configureAST(new BooleanExpression((Expression)this.visit(ctx.con)), ctx.con), (Expression)this.visit(ctx.tb), (Expression)this.visit(ctx.fb)), ctx);
    }

    @Override
    public BinaryExpression visitMultipleAssignmentExprAlt(GroovyParser.MultipleAssignmentExprAltContext ctx) {
        return PositionConfigureUtils.configureAST(new BinaryExpression(this.visitVariableNames(ctx.left), this.createGroovyToken(ctx.op), ((ExpressionStatement)this.visit(ctx.right)).getExpression()), ctx);
    }

    @Override
    public BinaryExpression visitAssignmentExprAlt(GroovyParser.AssignmentExprAltContext ctx) {
        Expression leftExpr = (Expression)this.visit(ctx.left);
        if (leftExpr instanceof VariableExpression && this.isInsideParentheses(leftExpr)) {
            if (((Number)leftExpr.getNodeMetaData(INSIDE_PARENTHESES_LEVEL)).intValue() > 1) {
                throw this.createParsingFailedException("Nested parenthesis is not allowed in multiple assignment, e.g. ((a)) = b", ctx);
            }
            return PositionConfigureUtils.configureAST(new BinaryExpression(PositionConfigureUtils.configureAST(new TupleExpression(leftExpr), ctx.left), this.createGroovyToken(ctx.op), (Expression)this.visit(ctx.right)), ctx);
        }
        if (!(leftExpr instanceof VariableExpression && !this.isInsideParentheses(leftExpr) || leftExpr instanceof PropertyExpression || leftExpr instanceof BinaryExpression && 30 == ((BinaryExpression)leftExpr).getOperation().getType())) {
            throw this.createParsingFailedException("The LHS of an assignment should be a variable or a field accessing expression", ctx);
        }
        return PositionConfigureUtils.configureAST(new BinaryExpression(leftExpr, this.createGroovyToken(ctx.op), (Expression)this.visit(ctx.right)), ctx);
    }

    @Override
    public Expression visitIdentifierPrmrAlt(GroovyParser.IdentifierPrmrAltContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.typeArguments())) {
            ClassNode classNode = ClassHelper.make(ctx.identifier().getText());
            classNode.setGenericsTypes(this.visitTypeArguments(ctx.typeArguments()));
            return PositionConfigureUtils.configureAST(new ClassExpression(classNode), ctx);
        }
        return PositionConfigureUtils.configureAST(new VariableExpression(this.visitIdentifier(ctx.identifier())), ctx);
    }

    @Override
    public Expression visitNewPrmrAlt(GroovyParser.NewPrmrAltContext ctx) {
        return PositionConfigureUtils.configureAST(this.visitCreator(ctx.creator()), ctx);
    }

    @Override
    public VariableExpression visitThisPrmrAlt(GroovyParser.ThisPrmrAltContext ctx) {
        return PositionConfigureUtils.configureAST(new VariableExpression(ctx.THIS().getText()), ctx);
    }

    @Override
    public VariableExpression visitSuperPrmrAlt(GroovyParser.SuperPrmrAltContext ctx) {
        return PositionConfigureUtils.configureAST(new VariableExpression(ctx.SUPER().getText()), ctx);
    }

    @Override
    public Expression visitCreator(GroovyParser.CreatorContext ctx) {
        ClassNode classNode = this.visitCreatedName(ctx.createdName());
        if (DefaultGroovyMethods.asBoolean(ctx.arguments())) {
            Expression arguments = this.visitArguments(ctx.arguments());
            Expression enclosingInstanceExpression = (Expression)ctx.getNodeMetaData(ENCLOSING_INSTANCE_EXPRESSION);
            if (enclosingInstanceExpression != null) {
                if (!(arguments instanceof ArgumentListExpression)) {
                    if (arguments instanceof TupleExpression) {
                        throw this.createParsingFailedException("Creating instance of non-static class does not support named parameters", arguments);
                    }
                    if (arguments instanceof NamedArgumentListExpression) {
                        throw this.createParsingFailedException("Unexpected arguments", arguments);
                    }
                    throw this.createParsingFailedException("Unsupported arguments", arguments);
                }
                ((ArgumentListExpression)arguments).getExpressions().add(0, enclosingInstanceExpression);
                if (enclosingInstanceExpression instanceof ConstructorCallExpression && classNode.getName().indexOf(46) < 0) {
                    classNode.setName(enclosingInstanceExpression.getType().getName() + '.' + classNode.getName());
                }
            }
            if (DefaultGroovyMethods.asBoolean(ctx.anonymousInnerClassDeclaration())) {
                ctx.anonymousInnerClassDeclaration().putNodeMetaData(ANONYMOUS_INNER_CLASS_SUPER_CLASS, classNode);
                InnerClassNode anonymousInnerClassNode = this.visitAnonymousInnerClassDeclaration(ctx.anonymousInnerClassDeclaration());
                List<InnerClassNode> anonymousInnerClassList = this.anonymousInnerClassesDefinedInMethodStack.peek();
                if (anonymousInnerClassList != null) {
                    anonymousInnerClassList.add(anonymousInnerClassNode);
                }
                ConstructorCallExpression constructorCallExpression = new ConstructorCallExpression(anonymousInnerClassNode, arguments);
                constructorCallExpression.setUsingAnonymousInnerClass(true);
                return PositionConfigureUtils.configureAST(constructorCallExpression, ctx);
            }
            ConstructorCallExpression constructorCallExpression = new ConstructorCallExpression(classNode, arguments);
            return PositionConfigureUtils.configureAST(constructorCallExpression, ctx);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.dim())) {
            ArrayExpression arrayExpression;
            List dimList = ctx.dim().stream().map(dimContext -> this.visitDim((GroovyParser.DimContext)dimContext)).collect(Collectors.toList());
            TerminalNode invalidDimLBrack = null;
            Boolean exprEmpty = null;
            LinkedList<Tuple3> emptyDimList = new LinkedList<Tuple3>();
            LinkedList<Tuple3> dimWithExprList = new LinkedList<Tuple3>();
            Tuple3 latestDim = null;
            for (Tuple3 dim : dimList) {
                if (null == dim.getV1()) {
                    emptyDimList.add(dim);
                    exprEmpty = Boolean.TRUE;
                } else {
                    if (Boolean.TRUE.equals(exprEmpty)) {
                        invalidDimLBrack = (TerminalNode)latestDim.getV3();
                    }
                    dimWithExprList.add(dim);
                    exprEmpty = Boolean.FALSE;
                }
                latestDim = dim;
            }
            if (DefaultGroovyMethods.asBoolean(ctx.arrayInitializer())) {
                if (!dimWithExprList.isEmpty()) {
                    throw this.createParsingFailedException("dimension should be empty", (TerminalNode)((Tuple3)dimWithExprList.get(0)).getV3());
                }
                ClassNode elementType = classNode;
                int n = emptyDimList.size() - 1;
                for (int i = 0; i < n; ++i) {
                    elementType = this.createArrayType(elementType);
                }
                arrayExpression = new ArrayExpression(elementType, (List<Expression>)this.visitArrayInitializer(ctx.arrayInitializer()));
            } else {
                Object[] empties;
                if (null != invalidDimLBrack) {
                    throw this.createParsingFailedException("dimension cannot be empty", invalidDimLBrack);
                }
                if (dimWithExprList.isEmpty() && !emptyDimList.isEmpty()) {
                    throw this.createParsingFailedException("dimensions cannot be all empty", (TerminalNode)((Tuple3)emptyDimList.get(0)).getV3());
                }
                if (DefaultGroovyMethods.asBoolean(emptyDimList)) {
                    empties = new Expression[emptyDimList.size()];
                    Arrays.fill(empties, ConstantExpression.EMPTY_EXPRESSION);
                } else {
                    empties = Expression.EMPTY_ARRAY;
                }
                arrayExpression = new ArrayExpression(classNode, null, Stream.concat(dimWithExprList.stream().map(Tuple3::getV1), Arrays.stream(empties)).collect(Collectors.toList()));
            }
            arrayExpression.setType(this.createArrayType(classNode, dimList.stream().map(Tuple3::getV2).collect(Collectors.toList())));
            return PositionConfigureUtils.configureAST(arrayExpression, ctx);
        }
        throw this.createParsingFailedException("Unsupported creator: " + ctx.getText(), ctx);
    }

    @Override
    public Tuple3<Expression, List<AnnotationNode>, TerminalNode> visitDim(GroovyParser.DimContext ctx) {
        return Tuple.tuple((Expression)this.visit(ctx.expression()), this.visitAnnotationsOpt(ctx.annotationsOpt()), ctx.LBRACK());
    }

    private static String nextAnonymousClassName(ClassNode outerClass) {
        int anonymousClassCount = 0;
        Iterator<InnerClassNode> it = outerClass.getInnerClasses();
        while (it.hasNext()) {
            InnerClassNode innerClass = it.next();
            if (!innerClass.isAnonymous()) continue;
            ++anonymousClassCount;
        }
        return outerClass.getName() + DOLLAR_STR + (anonymousClassCount + 1);
    }

    @Override
    public InnerClassNode visitAnonymousInnerClassDeclaration(GroovyParser.AnonymousInnerClassDeclarationContext ctx) {
        InnerClassNode anonymousInnerClass;
        ClassNode superClass = Objects.requireNonNull((ClassNode)ctx.getNodeMetaData(ANONYMOUS_INNER_CLASS_SUPER_CLASS), "superClass should not be null");
        ClassNode outerClass = Optional.ofNullable(this.classNodeStack.peek()).orElse(this.moduleNode.getScriptClassDummy());
        String innerClassName = AstBuilder.nextAnonymousClassName(outerClass);
        if (ctx.t == 1) {
            anonymousInnerClass = new EnumConstantClassNode(outerClass, innerClassName, superClass.getPlainNodeReference());
            superClass.setModifiers(superClass.getModifiers() & 0xFFFFFFEF);
        } else {
            anonymousInnerClass = new InnerClassNode(outerClass, innerClassName, 1, superClass);
        }
        anonymousInnerClass.setAnonymous(true);
        anonymousInnerClass.setUsingGenerics(false);
        anonymousInnerClass.putNodeMetaData(CLASS_NAME, innerClassName);
        PositionConfigureUtils.configureAST(anonymousInnerClass, ctx);
        this.classNodeList.add(anonymousInnerClass);
        this.classNodeStack.push(anonymousInnerClass);
        ctx.classBody().putNodeMetaData(CLASS_DECLARATION_CLASS_NODE, anonymousInnerClass);
        this.visitClassBody(ctx.classBody());
        this.classNodeStack.pop();
        return anonymousInnerClass;
    }

    @Override
    public ClassNode visitCreatedName(GroovyParser.CreatedNameContext ctx) {
        ClassNode classNode = null;
        if (DefaultGroovyMethods.asBoolean(ctx.qualifiedClassName())) {
            classNode = this.visitQualifiedClassName(ctx.qualifiedClassName());
            if (DefaultGroovyMethods.asBoolean(ctx.typeArgumentsOrDiamond())) {
                classNode.setGenericsTypes(this.visitTypeArgumentsOrDiamond(ctx.typeArgumentsOrDiamond()));
            }
            classNode = PositionConfigureUtils.configureAST(classNode, ctx);
        } else if (DefaultGroovyMethods.asBoolean(ctx.primitiveType())) {
            classNode = PositionConfigureUtils.configureAST(this.visitPrimitiveType(ctx.primitiveType()), ctx);
        }
        if (!DefaultGroovyMethods.asBoolean(classNode)) {
            throw this.createParsingFailedException("Unsupported created name: " + ctx.getText(), ctx);
        }
        classNode.addAnnotations((List<AnnotationNode>)this.visitAnnotationsOpt(ctx.annotationsOpt()));
        return classNode;
    }

    @Override
    public MapExpression visitMap(GroovyParser.MapContext ctx) {
        return PositionConfigureUtils.configureAST(new MapExpression((List<MapEntryExpression>)this.visitMapEntryList(ctx.mapEntryList())), ctx);
    }

    @Override
    public List<MapEntryExpression> visitMapEntryList(GroovyParser.MapEntryListContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return Collections.emptyList();
        }
        return this.createMapEntryList(ctx.mapEntry());
    }

    private List<MapEntryExpression> createMapEntryList(List<? extends GroovyParser.MapEntryContext> mapEntryContextList) {
        if (!DefaultGroovyMethods.asBoolean(mapEntryContextList)) {
            return Collections.emptyList();
        }
        return mapEntryContextList.stream().map(this::visitMapEntry).collect(Collectors.toList());
    }

    @Override
    public MapEntryExpression visitMapEntry(GroovyParser.MapEntryContext ctx) {
        Expression keyExpr;
        Expression valueExpr = (Expression)this.visit(ctx.expression());
        if (DefaultGroovyMethods.asBoolean(ctx.MUL())) {
            keyExpr = PositionConfigureUtils.configureAST(new SpreadMapExpression(valueExpr), ctx);
        } else if (DefaultGroovyMethods.asBoolean(ctx.mapEntryLabel())) {
            keyExpr = this.visitMapEntryLabel(ctx.mapEntryLabel());
        } else {
            throw this.createParsingFailedException("Unsupported map entry: " + ctx.getText(), ctx);
        }
        return PositionConfigureUtils.configureAST(new MapEntryExpression(keyExpr, valueExpr), ctx);
    }

    @Override
    public Expression visitMapEntryLabel(GroovyParser.MapEntryLabelContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.keywords())) {
            return PositionConfigureUtils.configureAST(this.visitKeywords(ctx.keywords()), ctx);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.primary())) {
            Expression expression = (Expression)this.visit(ctx.primary());
            if (expression instanceof VariableExpression && !this.isInsideParentheses(expression)) {
                expression = PositionConfigureUtils.configureAST(new ConstantExpression(((VariableExpression)expression).getName()), expression);
            }
            return PositionConfigureUtils.configureAST(expression, ctx);
        }
        throw this.createParsingFailedException("Unsupported map entry label: " + ctx.getText(), ctx);
    }

    @Override
    public ConstantExpression visitKeywords(GroovyParser.KeywordsContext ctx) {
        return PositionConfigureUtils.configureAST(new ConstantExpression(ctx.getText()), ctx);
    }

    @Override
    public VariableExpression visitBuiltInType(GroovyParser.BuiltInTypeContext ctx) {
        String text;
        if (DefaultGroovyMethods.asBoolean(ctx.VOID())) {
            text = ctx.VOID().getText();
        } else if (DefaultGroovyMethods.asBoolean(ctx.BuiltInPrimitiveType())) {
            text = ctx.BuiltInPrimitiveType().getText();
        } else {
            throw this.createParsingFailedException("Unsupported built-in type: " + ctx, ctx);
        }
        VariableExpression variableExpression = new VariableExpression(text);
        variableExpression.setNodeMetaData(IS_BUILT_IN_TYPE, Boolean.TRUE);
        return PositionConfigureUtils.configureAST(variableExpression, ctx);
    }

    @Override
    public ListExpression visitList(GroovyParser.ListContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.COMMA()) && !DefaultGroovyMethods.asBoolean(ctx.expressionList())) {
            throw this.createParsingFailedException("Empty list constructor should not contain any comma(,)", ctx.COMMA());
        }
        return PositionConfigureUtils.configureAST(new ListExpression((List<Expression>)this.visitExpressionList(ctx.expressionList())), ctx);
    }

    @Override
    public List<Expression> visitExpressionList(GroovyParser.ExpressionListContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return Collections.emptyList();
        }
        return this.createExpressionList(ctx.expressionListElement());
    }

    private List<Expression> createExpressionList(List<? extends GroovyParser.ExpressionListElementContext> expressionListElementContextList) {
        if (!DefaultGroovyMethods.asBoolean(expressionListElementContextList)) {
            return Collections.emptyList();
        }
        return expressionListElementContextList.stream().map(this::visitExpressionListElement).collect(Collectors.toList());
    }

    @Override
    public Expression visitExpressionListElement(GroovyParser.ExpressionListElementContext ctx) {
        Expression expression = (Expression)this.visit(ctx.expression());
        this.validateExpressionListElement(ctx, expression);
        if (DefaultGroovyMethods.asBoolean(ctx.MUL())) {
            if (!ctx.canSpread) {
                throw this.createParsingFailedException("spread operator is not allowed here", ctx.MUL());
            }
            return PositionConfigureUtils.configureAST(new SpreadExpression(expression), ctx);
        }
        return PositionConfigureUtils.configureAST(expression, ctx);
    }

    private void validateExpressionListElement(GroovyParser.ExpressionListElementContext ctx, Expression expression) {
        if (!(expression instanceof MethodCallExpression) || !this.isTrue(expression, IS_COMMAND_EXPRESSION)) {
            return;
        }
        MethodCallExpression methodCallExpression = (MethodCallExpression)expression;
        String methodName = methodCallExpression.getMethodAsString();
        if (methodCallExpression.isImplicitThis() && Character.isUpperCase(methodName.codePointAt(0)) || TypeUtil.isPrimitiveType(methodName)) {
            throw this.createParsingFailedException("Invalid method declaration", ctx);
        }
    }

    @Override
    public ConstantExpression visitIntegerLiteralAlt(GroovyParser.IntegerLiteralAltContext ctx) {
        String text = ctx.IntegerLiteral().getText();
        Number num = null;
        try {
            num = Numbers.parseInteger(text);
        }
        catch (Exception e) {
            this.numberFormatError = Tuple.tuple(ctx, e);
        }
        ConstantExpression constantExpression = new ConstantExpression(num, true);
        constantExpression.putNodeMetaData(INTEGER_LITERAL_TEXT, text);
        constantExpression.putNodeMetaData(IS_NUMERIC, Boolean.TRUE);
        return PositionConfigureUtils.configureAST(constantExpression, ctx);
    }

    @Override
    public ConstantExpression visitFloatingPointLiteralAlt(GroovyParser.FloatingPointLiteralAltContext ctx) {
        String text = ctx.FloatingPointLiteral().getText();
        Number num = null;
        try {
            num = Numbers.parseDecimal(text);
        }
        catch (Exception e) {
            this.numberFormatError = Tuple.tuple(ctx, e);
        }
        ConstantExpression constantExpression = new ConstantExpression(num, true);
        constantExpression.putNodeMetaData(FLOATING_POINT_LITERAL_TEXT, text);
        constantExpression.putNodeMetaData(IS_NUMERIC, Boolean.TRUE);
        return PositionConfigureUtils.configureAST(constantExpression, ctx);
    }

    @Override
    public ConstantExpression visitBooleanLiteralAlt(GroovyParser.BooleanLiteralAltContext ctx) {
        return PositionConfigureUtils.configureAST(new ConstantExpression("true".equals(ctx.BooleanLiteral().getText()), true), ctx);
    }

    @Override
    public ConstantExpression visitNullLiteralAlt(GroovyParser.NullLiteralAltContext ctx) {
        return PositionConfigureUtils.configureAST(new ConstantExpression(null), ctx);
    }

    @Override
    public GStringExpression visitGstring(GroovyParser.GstringContext ctx) {
        LinkedList<ConstantExpression> stringLiteralList = new LinkedList<ConstantExpression>();
        String begin = ctx.GStringBegin().getText();
        String beginQuotation = AstBuilder.beginQuotation(begin);
        stringLiteralList.add(PositionConfigureUtils.configureAST(new ConstantExpression(this.parseGStringBegin(ctx, beginQuotation)), ctx.GStringBegin()));
        List partStrings = ctx.GStringPart().stream().map(e -> PositionConfigureUtils.configureAST(new ConstantExpression(this.parseGStringPart((TerminalNode)e, beginQuotation)), e)).collect(Collectors.toList());
        stringLiteralList.addAll(partStrings);
        stringLiteralList.add(PositionConfigureUtils.configureAST(new ConstantExpression(this.parseGStringEnd(ctx, beginQuotation)), ctx.GStringEnd()));
        List<Expression> values = ctx.gstringValue().stream().map(this::visitGstringValue).collect(Collectors.toList());
        StringBuilder verbatimText = new StringBuilder(ctx.getText().length());
        int n = stringLiteralList.size();
        int s = values.size();
        for (int i = 0; i < n; ++i) {
            Expression value;
            verbatimText.append(((ConstantExpression)stringLiteralList.get(i)).getValue());
            if (i == s || !DefaultGroovyMethods.asBoolean(value = values.get(i))) continue;
            boolean isVariableExpression = value instanceof VariableExpression;
            verbatimText.append(DOLLAR_STR);
            if (!isVariableExpression) {
                verbatimText.append('{');
            }
            verbatimText.append(value.getText());
            if (isVariableExpression) continue;
            verbatimText.append('}');
        }
        return PositionConfigureUtils.configureAST(new GStringExpression(verbatimText.toString(), stringLiteralList, values), ctx);
    }

    private static boolean hasArrow(GroovyParser.GstringValueContext e) {
        return DefaultGroovyMethods.asBoolean(e.closure().ARROW());
    }

    private String parseGStringEnd(GroovyParser.GstringContext ctx, String beginQuotation) {
        StringBuilder text = new StringBuilder(ctx.GStringEnd().getText());
        text.insert(0, beginQuotation);
        return this.parseStringLiteral(text.toString());
    }

    private String parseGStringPart(TerminalNode e, String beginQuotation) {
        StringBuilder text = new StringBuilder(e.getText());
        text.deleteCharAt(text.length() - 1);
        text.insert(0, beginQuotation).append(QUOTATION_MAP.get(beginQuotation));
        return this.parseStringLiteral(text.toString());
    }

    private String parseGStringBegin(GroovyParser.GstringContext ctx, String beginQuotation) {
        StringBuilder text = new StringBuilder(ctx.GStringBegin().getText());
        text.deleteCharAt(text.length() - 1);
        text.append(QUOTATION_MAP.get(beginQuotation));
        return this.parseStringLiteral(text.toString());
    }

    private static String beginQuotation(String text) {
        if (text.startsWith(TDQ_STR)) {
            return TDQ_STR;
        }
        if (text.startsWith(DQ_STR)) {
            return DQ_STR;
        }
        if (text.startsWith(SLASH_STR)) {
            return SLASH_STR;
        }
        if (text.startsWith(DOLLAR_SLASH_STR)) {
            return DOLLAR_SLASH_STR;
        }
        return String.valueOf(text.charAt(0));
    }

    @Override
    public Expression visitGstringValue(GroovyParser.GstringValueContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.gstringPath())) {
            return PositionConfigureUtils.configureAST(this.visitGstringPath(ctx.gstringPath()), ctx);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.closure())) {
            ClosureExpression closureExpression = this.visitClosure(ctx.closure());
            if (!AstBuilder.hasArrow(ctx)) {
                List<Statement> statementList = ((BlockStatement)closureExpression.getCode()).getStatements();
                int size = statementList.size();
                if (1 == size) {
                    Expression expression;
                    Statement statement = statementList.get(0);
                    if (statement instanceof ExpressionStatement && !((expression = ((ExpressionStatement)statement).getExpression()) instanceof DeclarationExpression)) {
                        return expression;
                    }
                } else if (0 == size) {
                    return PositionConfigureUtils.configureAST(new ConstantExpression(null), ctx);
                }
                return PositionConfigureUtils.configureAST(this.createCallMethodCallExpression(closureExpression, new ArgumentListExpression(), true), ctx);
            }
            return PositionConfigureUtils.configureAST(closureExpression, ctx);
        }
        throw this.createParsingFailedException("Unsupported gstring value: " + ctx.getText(), ctx);
    }

    @Override
    public Expression visitGstringPath(GroovyParser.GstringPathContext ctx) {
        VariableExpression variableExpression = new VariableExpression(this.visitIdentifier(ctx.identifier()));
        if (DefaultGroovyMethods.asBoolean(ctx.GStringPathPart())) {
            Expression propertyExpression = ctx.GStringPathPart().stream().map(e -> PositionConfigureUtils.configureAST(new ConstantExpression(e.getText().substring(1)), e)).reduce(PositionConfigureUtils.configureAST(variableExpression, ctx.identifier()), (r, e) -> PositionConfigureUtils.configureAST(new PropertyExpression((Expression)r, (Expression)e), e));
            return PositionConfigureUtils.configureAST(propertyExpression, ctx);
        }
        return PositionConfigureUtils.configureAST(variableExpression, ctx);
    }

    @Override
    public LambdaExpression visitStandardLambdaExpression(GroovyParser.StandardLambdaExpressionContext ctx) {
        this.switchExpressionRuleContextStack.push(ctx);
        try {
            LambdaExpression lambdaExpression = PositionConfigureUtils.configureAST(this.createLambda(ctx.standardLambdaParameters(), ctx.lambdaBody()), ctx);
            return lambdaExpression;
        }
        finally {
            this.switchExpressionRuleContextStack.pop();
        }
    }

    private LambdaExpression createLambda(GroovyParser.StandardLambdaParametersContext standardLambdaParametersContext, GroovyParser.LambdaBodyContext lambdaBodyContext) {
        return new LambdaExpression(this.visitStandardLambdaParameters(standardLambdaParametersContext), this.visitLambdaBody(lambdaBodyContext));
    }

    @Override
    public Parameter[] visitStandardLambdaParameters(GroovyParser.StandardLambdaParametersContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.variableDeclaratorId())) {
            VariableExpression variable = this.visitVariableDeclaratorId(ctx.variableDeclaratorId());
            Parameter parameter = new Parameter(ClassHelper.dynamicType(), variable.getName());
            PositionConfigureUtils.configureAST(parameter, variable);
            return new Parameter[]{parameter};
        }
        Parameter[] parameters = this.visitFormalParameters(ctx.formalParameters());
        return parameters.length > 0 ? parameters : null;
    }

    @Override
    public Statement visitLambdaBody(GroovyParser.LambdaBodyContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.block())) {
            return PositionConfigureUtils.configureAST(this.visitBlock(ctx.block()), ctx);
        }
        return PositionConfigureUtils.configureAST((Statement)this.visit(ctx.statementExpression()), ctx);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ClosureExpression visitClosure(GroovyParser.ClosureContext ctx) {
        this.switchExpressionRuleContextStack.push(ctx);
        ++this.visitingClosureCount;
        try {
            Parameter[] parameters = DefaultGroovyMethods.asBoolean(ctx.formalParameterList()) ? this.visitFormalParameterList(ctx.formalParameterList()) : null;
            BlockStatement code = this.visitBlockStatementsOpt(ctx.blockStatementsOpt());
            if (!DefaultGroovyMethods.asBoolean(ctx.ARROW())) {
                parameters = Parameter.EMPTY_ARRAY;
                if (code.isEmpty()) {
                    PositionConfigureUtils.configureAST(code, ctx);
                }
            }
            ClosureExpression closureExpression = PositionConfigureUtils.configureAST(new ClosureExpression(parameters, code), ctx);
            return closureExpression;
        }
        finally {
            this.switchExpressionRuleContextStack.pop();
            --this.visitingClosureCount;
        }
    }

    @Override
    public Parameter[] visitFormalParameters(GroovyParser.FormalParametersContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return Parameter.EMPTY_ARRAY;
        }
        return this.visitFormalParameterList(ctx.formalParameterList());
    }

    @Override
    public Parameter[] visitFormalParameterList(GroovyParser.FormalParameterListContext ctx) {
        List<? extends GroovyParser.FormalParameterContext> formalParameterList;
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return Parameter.EMPTY_ARRAY;
        }
        LinkedList<Parameter> parameterList = new LinkedList<Parameter>();
        if (DefaultGroovyMethods.asBoolean(ctx.thisFormalParameter())) {
            parameterList.add(this.visitThisFormalParameter(ctx.thisFormalParameter()));
        }
        if (DefaultGroovyMethods.asBoolean(formalParameterList = ctx.formalParameter())) {
            this.validateVarArgParameter(formalParameterList);
            parameterList.addAll(formalParameterList.stream().map(this::visitFormalParameter).collect(Collectors.toList()));
        }
        this.validateParameterList(parameterList);
        return parameterList.toArray(Parameter.EMPTY_ARRAY);
    }

    private void validateVarArgParameter(List<? extends GroovyParser.FormalParameterContext> formalParameterList) {
        int n = formalParameterList.size();
        for (int i = 0; i < n - 1; ++i) {
            GroovyParser.FormalParameterContext formalParameterContext = formalParameterList.get(i);
            if (!DefaultGroovyMethods.asBoolean(formalParameterContext.ELLIPSIS())) continue;
            throw this.createParsingFailedException("The var-arg parameter strs must be the last parameter", formalParameterContext);
        }
    }

    private void validateParameterList(List<Parameter> parameterList) {
        int n = parameterList.size();
        for (int i = n - 1; i >= 0; --i) {
            Parameter parameter = parameterList.get(i);
            for (Parameter otherParameter : parameterList) {
                if (otherParameter == parameter || !otherParameter.getName().equals(parameter.getName())) continue;
                throw this.createParsingFailedException("Duplicated parameter '" + parameter.getName() + "' found.", parameter);
            }
        }
    }

    @Override
    public Parameter visitFormalParameter(GroovyParser.FormalParameterContext ctx) {
        return this.processFormalParameter(ctx, ctx.variableModifiersOpt(), ctx.type(), ctx.ELLIPSIS(), ctx.variableDeclaratorId(), ctx.expression());
    }

    @Override
    public Parameter visitThisFormalParameter(GroovyParser.ThisFormalParameterContext ctx) {
        return PositionConfigureUtils.configureAST(new Parameter(this.visitType(ctx.type()), THIS_STR), ctx);
    }

    @Override
    public List<ModifierNode> visitClassOrInterfaceModifiersOpt(GroovyParser.ClassOrInterfaceModifiersOptContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.classOrInterfaceModifiers())) {
            return this.visitClassOrInterfaceModifiers(ctx.classOrInterfaceModifiers());
        }
        return Collections.emptyList();
    }

    @Override
    public List<ModifierNode> visitClassOrInterfaceModifiers(GroovyParser.ClassOrInterfaceModifiersContext ctx) {
        return ctx.classOrInterfaceModifier().stream().map(this::visitClassOrInterfaceModifier).collect(Collectors.toList());
    }

    @Override
    public ModifierNode visitClassOrInterfaceModifier(GroovyParser.ClassOrInterfaceModifierContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.annotation())) {
            return PositionConfigureUtils.configureAST(new ModifierNode(this.visitAnnotation(ctx.annotation()), ctx.getText()), ctx);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.m)) {
            return PositionConfigureUtils.configureAST(new ModifierNode(ctx.m.getType(), ctx.getText()), ctx);
        }
        throw this.createParsingFailedException("Unsupported class or interface modifier: " + ctx.getText(), ctx);
    }

    @Override
    public ModifierNode visitModifier(GroovyParser.ModifierContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.classOrInterfaceModifier())) {
            return PositionConfigureUtils.configureAST(this.visitClassOrInterfaceModifier(ctx.classOrInterfaceModifier()), ctx);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.m)) {
            return PositionConfigureUtils.configureAST(new ModifierNode(ctx.m.getType(), ctx.getText()), ctx);
        }
        throw this.createParsingFailedException("Unsupported modifier: " + ctx.getText(), ctx);
    }

    @Override
    public List<ModifierNode> visitModifiers(GroovyParser.ModifiersContext ctx) {
        return ctx.modifier().stream().map(this::visitModifier).collect(Collectors.toList());
    }

    @Override
    public List<ModifierNode> visitModifiersOpt(GroovyParser.ModifiersOptContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.modifiers())) {
            return this.visitModifiers(ctx.modifiers());
        }
        return Collections.emptyList();
    }

    @Override
    public ModifierNode visitVariableModifier(GroovyParser.VariableModifierContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.annotation())) {
            return PositionConfigureUtils.configureAST(new ModifierNode(this.visitAnnotation(ctx.annotation()), ctx.getText()), ctx);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.m)) {
            return PositionConfigureUtils.configureAST(new ModifierNode(ctx.m.getType(), ctx.getText()), ctx);
        }
        throw this.createParsingFailedException("Unsupported variable modifier", ctx);
    }

    @Override
    public List<ModifierNode> visitVariableModifiersOpt(GroovyParser.VariableModifiersOptContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.variableModifiers())) {
            return this.visitVariableModifiers(ctx.variableModifiers());
        }
        return Collections.emptyList();
    }

    @Override
    public List<ModifierNode> visitVariableModifiers(GroovyParser.VariableModifiersContext ctx) {
        return ctx.variableModifier().stream().map(this::visitVariableModifier).collect(Collectors.toList());
    }

    @Override
    public List<List<AnnotationNode>> visitEmptyDims(GroovyParser.EmptyDimsContext ctx) {
        List<List<AnnotationNode>> dimList = ctx.annotationsOpt().stream().map(annotationsOptContext -> this.visitAnnotationsOpt((GroovyParser.AnnotationsOptContext)annotationsOptContext)).collect(Collectors.toList());
        Collections.reverse(dimList);
        return dimList;
    }

    @Override
    public List<List<AnnotationNode>> visitEmptyDimsOpt(GroovyParser.EmptyDimsOptContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx.emptyDims())) {
            return Collections.emptyList();
        }
        return this.visitEmptyDims(ctx.emptyDims());
    }

    @Override
    public ClassNode visitType(GroovyParser.TypeContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return ClassHelper.dynamicType();
        }
        ClassNode classNode = null;
        if (DefaultGroovyMethods.asBoolean(ctx.classOrInterfaceType())) {
            ctx.classOrInterfaceType().putNodeMetaData(IS_INSIDE_INSTANCEOF_EXPR, ctx.getNodeMetaData(IS_INSIDE_INSTANCEOF_EXPR));
            classNode = this.visitClassOrInterfaceType(ctx.classOrInterfaceType());
        } else if (DefaultGroovyMethods.asBoolean(ctx.primitiveType())) {
            classNode = this.visitPrimitiveType(ctx.primitiveType());
        }
        if (!DefaultGroovyMethods.asBoolean(classNode)) {
            if (VOID_STR.equals(ctx.getText())) {
                throw this.createParsingFailedException("void is not allowed here", ctx);
            }
            throw this.createParsingFailedException("Unsupported type: " + ctx.getText(), ctx);
        }
        classNode.addTypeAnnotations((List<AnnotationNode>)this.visitAnnotationsOpt(ctx.annotationsOpt()));
        Object dimList = this.visitEmptyDimsOpt(ctx.emptyDimsOpt());
        if (DefaultGroovyMethods.asBoolean((Collection)dimList)) {
            classNode = this.createArrayType(classNode, (List<List<AnnotationNode>>)dimList);
        }
        return PositionConfigureUtils.configureAST(classNode, ctx);
    }

    @Override
    public ClassNode visitClassOrInterfaceType(GroovyParser.ClassOrInterfaceTypeContext ctx) {
        ClassNode classNode;
        if (DefaultGroovyMethods.asBoolean(ctx.qualifiedClassName())) {
            ctx.qualifiedClassName().putNodeMetaData(IS_INSIDE_INSTANCEOF_EXPR, ctx.getNodeMetaData(IS_INSIDE_INSTANCEOF_EXPR));
            classNode = this.visitQualifiedClassName(ctx.qualifiedClassName());
        } else {
            ctx.qualifiedStandardClassName().putNodeMetaData(IS_INSIDE_INSTANCEOF_EXPR, ctx.getNodeMetaData(IS_INSIDE_INSTANCEOF_EXPR));
            classNode = this.visitQualifiedStandardClassName(ctx.qualifiedStandardClassName());
        }
        if (DefaultGroovyMethods.asBoolean(ctx.typeArguments())) {
            classNode.setGenericsTypes(this.visitTypeArguments(ctx.typeArguments()));
        }
        return PositionConfigureUtils.configureAST(classNode, ctx);
    }

    @Override
    public GenericsType[] visitTypeArgumentsOrDiamond(GroovyParser.TypeArgumentsOrDiamondContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.typeArguments())) {
            return this.visitTypeArguments(ctx.typeArguments());
        }
        if (DefaultGroovyMethods.asBoolean(ctx.LT())) {
            return GenericsType.EMPTY_ARRAY;
        }
        throw this.createParsingFailedException("Unsupported type arguments or diamond: " + ctx.getText(), ctx);
    }

    @Override
    public GenericsType[] visitTypeArguments(GroovyParser.TypeArgumentsContext ctx) {
        return (GenericsType[])ctx.typeArgument().stream().map(this::visitTypeArgument).toArray(GenericsType[]::new);
    }

    @Override
    public GenericsType visitTypeArgument(GroovyParser.TypeArgumentContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.QUESTION())) {
            ClassNode baseType = PositionConfigureUtils.configureAST(ClassHelper.makeWithoutCaching(QUESTION_STR), ctx.QUESTION());
            baseType.addTypeAnnotations((List<AnnotationNode>)this.visitAnnotationsOpt(ctx.annotationsOpt()));
            if (!DefaultGroovyMethods.asBoolean(ctx.type())) {
                GenericsType genericsType = new GenericsType(baseType);
                genericsType.setWildcard(true);
                genericsType.setName(QUESTION_STR);
                return PositionConfigureUtils.configureAST(genericsType, ctx);
            }
            ClassNode[] upperBounds = null;
            ClassNode lowerBound = null;
            ClassNode classNode = this.visitType(ctx.type());
            if (DefaultGroovyMethods.asBoolean(ctx.EXTENDS())) {
                upperBounds = new ClassNode[]{classNode};
            } else if (DefaultGroovyMethods.asBoolean(ctx.SUPER())) {
                lowerBound = classNode;
            }
            GenericsType genericsType = new GenericsType(baseType, upperBounds, lowerBound);
            genericsType.setWildcard(true);
            return PositionConfigureUtils.configureAST(genericsType, ctx);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.type())) {
            ClassNode baseType = this.visitType(ctx.type());
            return PositionConfigureUtils.configureAST(this.createGenericsType(baseType), ctx);
        }
        throw this.createParsingFailedException("Unsupported type argument: " + ctx.getText(), ctx);
    }

    @Override
    public ClassNode visitPrimitiveType(GroovyParser.PrimitiveTypeContext ctx) {
        return PositionConfigureUtils.configureAST(ClassHelper.make(ctx.getText()).getPlainNodeReference(false), ctx);
    }

    @Override
    public VariableExpression visitVariableDeclaratorId(GroovyParser.VariableDeclaratorIdContext ctx) {
        return PositionConfigureUtils.configureAST(new VariableExpression(this.visitIdentifier(ctx.identifier())), ctx);
    }

    @Override
    public TupleExpression visitVariableNames(GroovyParser.VariableNamesContext ctx) {
        return PositionConfigureUtils.configureAST(new TupleExpression(ctx.variableDeclaratorId().stream().map(this::visitVariableDeclaratorId).collect(Collectors.toList())), ctx);
    }

    @Override
    public ClosureExpression visitClosureOrLambdaExpression(GroovyParser.ClosureOrLambdaExpressionContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.closure())) {
            return PositionConfigureUtils.configureAST(this.visitClosure(ctx.closure()), ctx);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.standardLambdaExpression())) {
            return PositionConfigureUtils.configureAST(this.visitStandardLambdaExpression(ctx.standardLambdaExpression()), ctx);
        }
        throw this.createParsingFailedException("The node is not expected here" + ctx.getText(), ctx);
    }

    @Override
    public BlockStatement visitBlockStatementsOpt(GroovyParser.BlockStatementsOptContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.blockStatements())) {
            return PositionConfigureUtils.configureAST(this.visitBlockStatements(ctx.blockStatements()), ctx);
        }
        return PositionConfigureUtils.configureAST(this.createBlockStatement(new Statement[0]), ctx);
    }

    @Override
    public BlockStatement visitBlockStatements(GroovyParser.BlockStatementsContext ctx) {
        return PositionConfigureUtils.configureAST(this.createBlockStatement(ctx.blockStatement().stream().map(this::visitBlockStatement).filter(DefaultGroovyMethods::asBoolean).collect(Collectors.toList())), ctx);
    }

    @Override
    public Statement visitBlockStatement(GroovyParser.BlockStatementContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.localVariableDeclaration())) {
            return PositionConfigureUtils.configureAST(this.visitLocalVariableDeclaration(ctx.localVariableDeclaration()), ctx);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.statement())) {
            Object astNode = this.visit(ctx.statement());
            if (null == astNode) {
                return null;
            }
            if (astNode instanceof Statement) {
                return (Statement)astNode;
            }
            if (astNode instanceof MethodNode) {
                throw this.createParsingFailedException("Method definition not expected here", ctx);
            }
            if (astNode instanceof ImportNode) {
                throw this.createParsingFailedException("Import statement not expected here", ctx);
            }
            throw this.createParsingFailedException("The statement(" + astNode.getClass() + ") not expected here", ctx);
        }
        throw this.createParsingFailedException("Unsupported block statement: " + ctx.getText(), ctx);
    }

    @Override
    public List<AnnotationNode> visitAnnotationsOpt(GroovyParser.AnnotationsOptContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return Collections.emptyList();
        }
        return ctx.annotation().stream().map(this::visitAnnotation).collect(Collectors.toList());
    }

    @Override
    public AnnotationNode visitAnnotation(GroovyParser.AnnotationContext ctx) {
        String annotationName = this.visitAnnotationName(ctx.annotationName());
        AnnotationNode annotationNode = new AnnotationNode(ClassHelper.make(annotationName));
        Object annotationElementValues = this.visitElementValues(ctx.elementValues());
        annotationElementValues.forEach(e -> annotationNode.addMember((String)e.getV1(), (Expression)e.getV2()));
        PositionConfigureUtils.configureAST(annotationNode.getClassNode(), ctx.annotationName());
        return PositionConfigureUtils.configureAST(annotationNode, ctx);
    }

    @Override
    public List<Tuple2<String, Expression>> visitElementValues(GroovyParser.ElementValuesContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return Collections.emptyList();
        }
        LinkedList<Tuple2<String, Expression>> annotationElementValues = new LinkedList<Tuple2<String, Expression>>();
        if (DefaultGroovyMethods.asBoolean(ctx.elementValuePairs())) {
            this.visitElementValuePairs(ctx.elementValuePairs()).forEach((key, value) -> annotationElementValues.add(Tuple.tuple(key, value)));
        } else if (DefaultGroovyMethods.asBoolean(ctx.elementValue())) {
            annotationElementValues.add(Tuple.tuple(VALUE_STR, this.visitElementValue(ctx.elementValue())));
        }
        return annotationElementValues;
    }

    @Override
    public String visitAnnotationName(GroovyParser.AnnotationNameContext ctx) {
        return this.visitQualifiedClassName(ctx.qualifiedClassName()).getName();
    }

    @Override
    public Map<String, Expression> visitElementValuePairs(GroovyParser.ElementValuePairsContext ctx) {
        return ctx.elementValuePair().stream().map(elementValuePairContext -> this.visitElementValuePair((GroovyParser.ElementValuePairContext)elementValuePairContext)).collect(Collectors.toMap(Tuple2::getV1, Tuple2::getV2, (k, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", k));
        }, LinkedHashMap::new));
    }

    @Override
    public Tuple2<String, Expression> visitElementValuePair(GroovyParser.ElementValuePairContext ctx) {
        return Tuple.tuple(ctx.elementValuePairName().getText(), this.visitElementValue(ctx.elementValue()));
    }

    @Override
    public Expression visitElementValue(GroovyParser.ElementValueContext ctx) {
        if (DefaultGroovyMethods.asBoolean(ctx.expression())) {
            return PositionConfigureUtils.configureAST((Expression)this.visit(ctx.expression()), ctx);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.annotation())) {
            return PositionConfigureUtils.configureAST(new AnnotationConstantExpression(this.visitAnnotation(ctx.annotation())), ctx);
        }
        if (DefaultGroovyMethods.asBoolean(ctx.elementValueArrayInitializer())) {
            return PositionConfigureUtils.configureAST(this.visitElementValueArrayInitializer(ctx.elementValueArrayInitializer()), ctx);
        }
        throw this.createParsingFailedException("Unsupported element value: " + ctx.getText(), ctx);
    }

    @Override
    public ListExpression visitElementValueArrayInitializer(GroovyParser.ElementValueArrayInitializerContext ctx) {
        return PositionConfigureUtils.configureAST(new ListExpression(ctx.elementValue().stream().map(this::visitElementValue).collect(Collectors.toList())), ctx);
    }

    @Override
    public String visitClassName(GroovyParser.ClassNameContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitIdentifier(GroovyParser.IdentifierContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitQualifiedName(GroovyParser.QualifiedNameContext ctx) {
        return ctx.qualifiedNameElement().stream().map(ParseTree::getText).collect(Collectors.joining(DOT_STR));
    }

    @Override
    public ClassNode visitAnnotatedQualifiedClassName(GroovyParser.AnnotatedQualifiedClassNameContext ctx) {
        ClassNode classNode = this.visitQualifiedClassName(ctx.qualifiedClassName());
        classNode.addTypeAnnotations((List<AnnotationNode>)this.visitAnnotationsOpt(ctx.annotationsOpt()));
        return classNode;
    }

    @Override
    public ClassNode[] visitQualifiedClassNameList(GroovyParser.QualifiedClassNameListContext ctx) {
        if (!DefaultGroovyMethods.asBoolean(ctx)) {
            return ClassNode.EMPTY_ARRAY;
        }
        return (ClassNode[])ctx.annotatedQualifiedClassName().stream().map(this::visitAnnotatedQualifiedClassName).toArray(ClassNode[]::new);
    }

    @Override
    public ClassNode visitQualifiedClassName(GroovyParser.QualifiedClassNameContext ctx) {
        return this.createClassNode(ctx);
    }

    @Override
    public ClassNode visitQualifiedStandardClassName(GroovyParser.QualifiedStandardClassNameContext ctx) {
        return this.createClassNode(ctx);
    }

    private ClassNode createArrayType(ClassNode elementType, List<List<AnnotationNode>> dimAnnotationsList) {
        ClassNode arrayType = elementType;
        for (int i = dimAnnotationsList.size() - 1; i >= 0; --i) {
            arrayType = this.createArrayType(arrayType);
            arrayType.addAnnotations(dimAnnotationsList.get(i));
        }
        return arrayType;
    }

    private ClassNode createArrayType(ClassNode elementType) {
        if (ClassHelper.isPrimitiveVoid(elementType)) {
            throw this.createParsingFailedException("void[] is an invalid type", elementType);
        }
        return elementType.makeArray();
    }

    private ClassNode createClassNode(GroovyParser.GroovyParserRuleContext ctx) {
        ClassNode result = AstBuilder.makeClassNode(ctx.getText());
        if (!this.isTrue(ctx, IS_INSIDE_INSTANCEOF_EXPR)) {
            result = this.proxyClassNode(result);
        }
        return PositionConfigureUtils.configureAST(result, ctx);
    }

    private ClassNode proxyClassNode(ClassNode classNode) {
        if (!classNode.isUsingGenerics()) {
            return classNode;
        }
        ClassNode cn = ClassHelper.makeWithoutCaching(classNode.getName());
        cn.setRedirect(classNode);
        return cn;
    }

    @Override
    public Object visit(ParseTree tree) {
        if (!DefaultGroovyMethods.asBoolean(tree)) {
            return null;
        }
        return super.visit(tree);
    }

    private MethodCallExpression createMethodCallExpression(PropertyExpression propertyExpression, Expression arguments) {
        MethodCallExpression methodCallExpression = new MethodCallExpression(propertyExpression.getObjectExpression(), propertyExpression.getProperty(), arguments);
        methodCallExpression.setImplicitThis(false);
        methodCallExpression.setSafe(propertyExpression.isSafe());
        methodCallExpression.setSpreadSafe(propertyExpression.isSpreadSafe());
        if (propertyExpression.isSpreadSafe()) {
            methodCallExpression.setSafe(false);
        }
        methodCallExpression.setGenericsTypes((GenericsType[])propertyExpression.getNodeMetaData(PATH_EXPRESSION_BASE_EXPR_GENERICS_TYPES));
        return methodCallExpression;
    }

    private MethodCallExpression createMethodCallExpression(Expression baseExpr, Expression arguments) {
        VariableExpression thisExpr = new VariableExpression(THIS_STR);
        thisExpr.setColumnNumber(baseExpr.getColumnNumber());
        thisExpr.setLineNumber(baseExpr.getLineNumber());
        return new MethodCallExpression((Expression)thisExpr, baseExpr instanceof VariableExpression ? this.createConstantExpression(baseExpr) : baseExpr, arguments);
    }

    private Parameter processFormalParameter(GroovyParser.GroovyParserRuleContext ctx, GroovyParser.VariableModifiersOptContext variableModifiersOptContext, GroovyParser.TypeContext typeContext, TerminalNode ellipsis, GroovyParser.VariableDeclaratorIdContext variableDeclaratorIdContext, GroovyParser.ExpressionContext expressionContext) {
        ClassNode classNode = this.visitType(typeContext);
        if (DefaultGroovyMethods.asBoolean(ellipsis)) {
            classNode = this.createArrayType(classNode);
            if (!DefaultGroovyMethods.asBoolean(typeContext)) {
                PositionConfigureUtils.configureAST(classNode, ellipsis);
            } else {
                PositionConfigureUtils.configureAST(classNode, typeContext, (ASTNode)PositionConfigureUtils.configureAST(new ConstantExpression("..."), ellipsis));
            }
        }
        ModifierManager modifierManager = new ModifierManager(this, (List<ModifierNode>)this.visitVariableModifiersOpt(variableModifiersOptContext));
        Parameter parameter = modifierManager.processParameter(PositionConfigureUtils.configureAST(new Parameter(classNode, this.visitVariableDeclaratorId(variableDeclaratorIdContext).getName()), ctx));
        parameter.putNodeMetaData(PARAMETER_MODIFIER_MANAGER, modifierManager);
        parameter.putNodeMetaData(PARAMETER_CONTEXT, ctx);
        if (DefaultGroovyMethods.asBoolean(expressionContext)) {
            parameter.setInitialExpression((Expression)this.visit(expressionContext));
        }
        return parameter;
    }

    private Expression createPathExpression(Expression primaryExpr, List<? extends GroovyParser.PathElementContext> pathElementContextList) {
        return (Expression)pathElementContextList.stream().map(e -> e).reduce(primaryExpr, (r, e) -> {
            GroovyParser.PathElementContext pathElementContext = (GroovyParser.PathElementContext)e;
            pathElementContext.putNodeMetaData(PATH_EXPRESSION_BASE_EXPR, r);
            Expression expression = this.visitPathElement(pathElementContext);
            boolean isSafeChain = this.isTrue((Expression)r, PATH_EXPRESSION_BASE_EXPR_SAFE_CHAIN);
            if (isSafeChain) {
                expression.putNodeMetaData(PATH_EXPRESSION_BASE_EXPR_SAFE_CHAIN, true);
            }
            return expression;
        });
    }

    private GenericsType createGenericsType(ClassNode classNode) {
        return PositionConfigureUtils.configureAST(new GenericsType(classNode), classNode);
    }

    private ConstantExpression createConstantExpression(Expression expression) {
        if (expression instanceof ConstantExpression) {
            return (ConstantExpression)expression;
        }
        return PositionConfigureUtils.configureAST(new ConstantExpression(expression.getText()), expression);
    }

    private BinaryExpression createBinaryExpression(GroovyParser.ExpressionContext left, groovyjarjarantlr4.v4.runtime.Token op, GroovyParser.ExpressionContext right) {
        return new BinaryExpression((Expression)this.visit(left), this.createGroovyToken(op), (Expression)this.visit(right));
    }

    private BinaryExpression createBinaryExpression(GroovyParser.ExpressionContext left, groovyjarjarantlr4.v4.runtime.Token op, GroovyParser.ExpressionContext right, GroovyParser.ExpressionContext ctx) {
        BinaryExpression binaryExpression = this.createBinaryExpression(left, op, right);
        if (this.isTrue(ctx, IS_INSIDE_CONDITIONAL_EXPRESSION)) {
            return PositionConfigureUtils.configureAST(binaryExpression, op);
        }
        return PositionConfigureUtils.configureAST(binaryExpression, ctx);
    }

    private Statement unpackStatement(Statement statement) {
        if (statement instanceof DeclarationListStatement) {
            List<ExpressionStatement> expressionStatementList = ((DeclarationListStatement)statement).getDeclarationStatements();
            if (1 == expressionStatementList.size()) {
                return expressionStatementList.get(0);
            }
            return PositionConfigureUtils.configureAST(this.createBlockStatement(statement), statement);
        }
        return statement;
    }

    BlockStatement createBlockStatement(Statement ... statements) {
        return this.createBlockStatement(Arrays.asList(statements));
    }

    private BlockStatement createBlockStatement(List<Statement> statementList) {
        return this.appendStatementsToBlockStatement(new BlockStatement(), statementList);
    }

    public BlockStatement appendStatementsToBlockStatement(BlockStatement bs, Statement ... statements) {
        return this.appendStatementsToBlockStatement(bs, Arrays.asList(statements));
    }

    private BlockStatement appendStatementsToBlockStatement(BlockStatement bs, List<Statement> statementList) {
        return statementList.stream().reduce(bs, (r, e) -> {
            BlockStatement blockStatement = (BlockStatement)r;
            if (e instanceof DeclarationListStatement) {
                ((DeclarationListStatement)e).getDeclarationStatements().forEach(blockStatement::addStatement);
            } else {
                blockStatement.addStatement((Statement)e);
            }
            return blockStatement;
        });
    }

    private boolean isAnnotationDeclaration(ClassNode classNode) {
        return DefaultGroovyMethods.asBoolean(classNode) && classNode.isAnnotationDefinition();
    }

    private boolean isSyntheticPublic(boolean isAnnotationDeclaration, boolean isAnonymousInnerEnumDeclaration, boolean hasReturnType, ModifierManager modifierManager) {
        if (modifierManager.containsVisibilityModifier()) {
            return false;
        }
        if (isAnnotationDeclaration) {
            return true;
        }
        if (hasReturnType && modifierManager.containsAny(8, 12)) {
            return true;
        }
        if (!hasReturnType || modifierManager.containsNonVisibilityModifier() || modifierManager.containsAnnotations()) {
            return true;
        }
        return isAnonymousInnerEnumDeclaration;
    }

    private void hackMixins(ClassNode classNode) {
        classNode.setMixins(null);
    }

    private Object findDefaultValueByType(ClassNode type) {
        return TYPE_DEFAULT_VALUE_MAP.get(type);
    }

    private boolean isPackageInfoDeclaration() {
        String name = this.sourceUnit.getName();
        return name != null && name.endsWith(PACKAGE_INFO_FILE_NAME);
    }

    private boolean isBlankScript() {
        return this.moduleNode.getStatementBlock().isEmpty() && this.moduleNode.getMethods().isEmpty() && this.moduleNode.getClasses().isEmpty();
    }

    private boolean isInsideParentheses(NodeMetaDataHandler nodeMetaDataHandler) {
        Number insideParenLevel = (Number)nodeMetaDataHandler.getNodeMetaData(INSIDE_PARENTHESES_LEVEL);
        return insideParenLevel != null && insideParenLevel.intValue() > 0;
    }

    private boolean isBuiltInType(Expression expression) {
        return expression instanceof VariableExpression && this.isTrue(expression, IS_BUILT_IN_TYPE);
    }

    private Token createGroovyTokenByType(groovyjarjarantlr4.v4.runtime.Token token, int type) {
        if (token == null) {
            throw new IllegalArgumentException("token should not be null");
        }
        return new Token(type, token.getText(), token.getLine(), token.getCharPositionInLine());
    }

    private Token createGroovyToken(groovyjarjarantlr4.v4.runtime.Token token) {
        return this.createGroovyToken(token, 1);
    }

    private Token createGroovyToken(groovyjarjarantlr4.v4.runtime.Token token, int cardinality) {
        String text;
        String tokenText = token.getText();
        int tokenType = token.getType();
        String string = text = 1 == cardinality ? tokenText : StringGroovyMethods.multiply(tokenText, cardinality);
        return new Token(68 == tokenType || 66 == tokenType || 67 == tokenType || 65 == tokenType ? 1104 : (71 == tokenType ? 30 : Types.lookup(text, 1000)), text, token.getLine(), token.getCharPositionInLine() + 1);
    }

    private void configureScriptClassNode() {
        ClassNode scriptClassNode = this.moduleNode.getScriptClassDummy();
        if (!DefaultGroovyMethods.asBoolean(scriptClassNode)) {
            return;
        }
        List<Statement> statements = this.moduleNode.getStatementBlock().getStatements();
        if (!statements.isEmpty()) {
            Statement firstStatement = statements.get(0);
            Statement lastStatement = statements.get(statements.size() - 1);
            scriptClassNode.setSourcePosition(firstStatement);
            scriptClassNode.setLastColumnNumber(lastStatement.getLastColumnNumber());
            scriptClassNode.setLastLineNumber(lastStatement.getLastLineNumber());
        }
    }

    private String getOriginalText(ParserRuleContext context) {
        return this.lexer.getInputStream().getText(Interval.of(context.getStart().getStartIndex(), context.getStop().getStopIndex()));
    }

    private boolean isTrue(NodeMetaDataHandler nodeMetaDataHandler, String key) {
        Object nmd = nodeMetaDataHandler.getNodeMetaData(key);
        if (null == nmd) {
            return false;
        }
        if (!(nmd instanceof Boolean)) {
            throw new GroovyBugError(nodeMetaDataHandler + " node meta data[" + key + "] is not an instance of Boolean");
        }
        return (Boolean)nmd;
    }

    private CompilationFailedException createParsingFailedException(String msg, GroovyParser.GroovyParserRuleContext ctx) {
        return this.createParsingFailedException(new SyntaxException(msg, ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1, ctx.stop.getLine(), ctx.stop.getCharPositionInLine() + 1 + ctx.stop.getText().length()));
    }

    CompilationFailedException createParsingFailedException(String msg, Tuple2<Integer, Integer> start, Tuple2<Integer, Integer> end) {
        return this.createParsingFailedException(new SyntaxException(msg, start.getV1(), start.getV2(), end.getV1(), end.getV2()));
    }

    CompilationFailedException createParsingFailedException(String msg, ASTNode node) {
        Objects.requireNonNull(node, "node passed into createParsingFailedException should not be null");
        return this.createParsingFailedException(new SyntaxException(msg, node.getLineNumber(), node.getColumnNumber(), node.getLastLineNumber(), node.getLastColumnNumber()));
    }

    private CompilationFailedException createParsingFailedException(String msg, TerminalNode node) {
        return this.createParsingFailedException(msg, node.getSymbol());
    }

    private CompilationFailedException createParsingFailedException(String msg, groovyjarjarantlr4.v4.runtime.Token token) {
        return this.createParsingFailedException(new SyntaxException(msg, token.getLine(), token.getCharPositionInLine() + 1, token.getLine(), token.getCharPositionInLine() + 1 + token.getText().length()));
    }

    private CompilationFailedException createParsingFailedException(Throwable t) {
        if (t instanceof SyntaxException) {
            this.collectSyntaxError((SyntaxException)t);
        } else if (t instanceof GroovySyntaxError) {
            GroovySyntaxError groovySyntaxError = (GroovySyntaxError)((Object)t);
            this.collectSyntaxError(new SyntaxException(groovySyntaxError.getMessage(), (Throwable)((Object)groovySyntaxError), groovySyntaxError.getLine(), groovySyntaxError.getColumn()));
        } else if (t instanceof Exception) {
            this.collectException((Exception)t);
        }
        return new CompilationFailedException(CompilePhase.PARSING.getPhaseNumber(), this.sourceUnit, t);
    }

    private void collectSyntaxError(SyntaxException e) {
        this.sourceUnit.getErrorCollector().addFatalError(new SyntaxErrorMessage(e, this.sourceUnit));
    }

    private void collectException(Exception e) {
        this.sourceUnit.getErrorCollector().addException(e, this.sourceUnit);
    }

    private ANTLRErrorListener createANTLRErrorListener() {
        return new ANTLRErrorListener(){

            public void syntaxError(Recognizer recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                AstBuilder.this.collectSyntaxError(new SyntaxException(msg, line, charPositionInLine + 1));
            }
        };
    }

    private void removeErrorListeners() {
        this.lexer.removeErrorListeners();
        this.parser.removeErrorListeners();
    }

    private void addErrorListeners() {
        this.lexer.removeErrorListeners();
        this.lexer.addErrorListener(this.createANTLRErrorListener());
        this.parser.removeErrorListeners();
        this.parser.addErrorListener(this.createANTLRErrorListener());
    }

    private static class DeclarationListStatement
    extends Statement {
        private final List<ExpressionStatement> declarationStatements;

        public DeclarationListStatement(DeclarationExpression ... declarations) {
            this(Arrays.asList(declarations));
        }

        public DeclarationListStatement(List<DeclarationExpression> declarations) {
            this.declarationStatements = declarations.stream().map(e -> PositionConfigureUtils.configureAST(new ExpressionStatement((Expression)e), e)).collect(Collectors.toList());
        }

        public List<ExpressionStatement> getDeclarationStatements() {
            List<String> declarationListStatementLabels = this.getStatementLabels();
            this.declarationStatements.forEach(e -> {
                if (null != declarationListStatementLabels) {
                    if (null != e.getStatementLabels()) {
                        e.getStatementLabels().clear();
                    }
                    declarationListStatementLabels.forEach(e::addStatementLabel);
                }
            });
            return this.declarationStatements;
        }

        public List<DeclarationExpression> getDeclarationExpressions() {
            return this.declarationStatements.stream().map(e -> (DeclarationExpression)e.getExpression()).collect(Collectors.toList());
        }
    }

    private static class PropertyExpander
    extends Verifier {
        private PropertyExpander(ClassNode cNode) {
            this.setClassNode(cNode);
        }

        @Override
        protected Statement createSetterBlock(PropertyNode propertyNode, FieldNode field) {
            return GeneralUtils.stmt(GeneralUtils.assignX(GeneralUtils.varX(field), GeneralUtils.varX(AstBuilder.VALUE_STR, field.getType())));
        }

        @Override
        protected Statement createGetterBlock(PropertyNode propertyNode, FieldNode field) {
            return GeneralUtils.stmt(GeneralUtils.varX(field));
        }
    }
}

