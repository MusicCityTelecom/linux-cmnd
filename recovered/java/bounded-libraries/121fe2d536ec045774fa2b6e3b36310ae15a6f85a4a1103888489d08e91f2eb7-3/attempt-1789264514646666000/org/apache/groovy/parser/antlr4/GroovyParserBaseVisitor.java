/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.parser.antlr4;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.tree.AbstractParseTreeVisitor;
import org.apache.groovy.parser.antlr4.GroovyParser;
import org.apache.groovy.parser.antlr4.GroovyParserVisitor;

public class GroovyParserBaseVisitor<Result>
extends AbstractParseTreeVisitor<Result>
implements GroovyParserVisitor<Result> {
    @Override
    public Result visitIdentifierPrmrAlt(@NotNull GroovyParser.IdentifierPrmrAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitLiteralPrmrAlt(@NotNull GroovyParser.LiteralPrmrAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitGstringPrmrAlt(@NotNull GroovyParser.GstringPrmrAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitNewPrmrAlt(@NotNull GroovyParser.NewPrmrAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitThisPrmrAlt(@NotNull GroovyParser.ThisPrmrAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitSuperPrmrAlt(@NotNull GroovyParser.SuperPrmrAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitParenPrmrAlt(@NotNull GroovyParser.ParenPrmrAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitClosureOrLambdaExpressionPrmrAlt(@NotNull GroovyParser.ClosureOrLambdaExpressionPrmrAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitListPrmrAlt(@NotNull GroovyParser.ListPrmrAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitMapPrmrAlt(@NotNull GroovyParser.MapPrmrAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitBuiltInTypePrmrAlt(@NotNull GroovyParser.BuiltInTypePrmrAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitIntegerLiteralAlt(@NotNull GroovyParser.IntegerLiteralAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitFloatingPointLiteralAlt(@NotNull GroovyParser.FloatingPointLiteralAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitStringLiteralAlt(@NotNull GroovyParser.StringLiteralAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitBooleanLiteralAlt(@NotNull GroovyParser.BooleanLiteralAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitNullLiteralAlt(@NotNull GroovyParser.NullLiteralAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitCastExprAlt(@NotNull GroovyParser.CastExprAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitPostfixExprAlt(@NotNull GroovyParser.PostfixExprAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitSwitchExprAlt(@NotNull GroovyParser.SwitchExprAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitUnaryNotExprAlt(@NotNull GroovyParser.UnaryNotExprAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitPowerExprAlt(@NotNull GroovyParser.PowerExprAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitUnaryAddExprAlt(@NotNull GroovyParser.UnaryAddExprAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitMultiplicativeExprAlt(@NotNull GroovyParser.MultiplicativeExprAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitAdditiveExprAlt(@NotNull GroovyParser.AdditiveExprAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitShiftExprAlt(@NotNull GroovyParser.ShiftExprAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitRelationalExprAlt(@NotNull GroovyParser.RelationalExprAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitEqualityExprAlt(@NotNull GroovyParser.EqualityExprAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitRegexExprAlt(@NotNull GroovyParser.RegexExprAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitAndExprAlt(@NotNull GroovyParser.AndExprAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitExclusiveOrExprAlt(@NotNull GroovyParser.ExclusiveOrExprAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitInclusiveOrExprAlt(@NotNull GroovyParser.InclusiveOrExprAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitLogicalAndExprAlt(@NotNull GroovyParser.LogicalAndExprAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitLogicalOrExprAlt(@NotNull GroovyParser.LogicalOrExprAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitConditionalExprAlt(@NotNull GroovyParser.ConditionalExprAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitMultipleAssignmentExprAlt(@NotNull GroovyParser.MultipleAssignmentExprAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitAssignmentExprAlt(@NotNull GroovyParser.AssignmentExprAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitBlockStmtAlt(@NotNull GroovyParser.BlockStmtAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitConditionalStmtAlt(@NotNull GroovyParser.ConditionalStmtAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitLoopStmtAlt(@NotNull GroovyParser.LoopStmtAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitTryCatchStmtAlt(@NotNull GroovyParser.TryCatchStmtAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitSynchronizedStmtAlt(@NotNull GroovyParser.SynchronizedStmtAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitReturnStmtAlt(@NotNull GroovyParser.ReturnStmtAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitThrowStmtAlt(@NotNull GroovyParser.ThrowStmtAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitBreakStmtAlt(@NotNull GroovyParser.BreakStmtAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitContinueStmtAlt(@NotNull GroovyParser.ContinueStmtAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitYieldStmtAlt(@NotNull GroovyParser.YieldStmtAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitLabeledStmtAlt(@NotNull GroovyParser.LabeledStmtAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitAssertStmtAlt(@NotNull GroovyParser.AssertStmtAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitLocalVariableDeclarationStmtAlt(@NotNull GroovyParser.LocalVariableDeclarationStmtAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitExpressionStmtAlt(@NotNull GroovyParser.ExpressionStmtAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitEmptyStmtAlt(@NotNull GroovyParser.EmptyStmtAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitCommandExprAlt(@NotNull GroovyParser.CommandExprAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitForStmtAlt(@NotNull GroovyParser.ForStmtAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitWhileStmtAlt(@NotNull GroovyParser.WhileStmtAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitDoWhileStmtAlt(@NotNull GroovyParser.DoWhileStmtAltContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitCompilationUnit(@NotNull GroovyParser.CompilationUnitContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitScriptStatements(@NotNull GroovyParser.ScriptStatementsContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitScriptStatement(@NotNull GroovyParser.ScriptStatementContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitPackageDeclaration(@NotNull GroovyParser.PackageDeclarationContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitImportDeclaration(@NotNull GroovyParser.ImportDeclarationContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitTypeDeclaration(@NotNull GroovyParser.TypeDeclarationContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitModifier(@NotNull GroovyParser.ModifierContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitModifiersOpt(@NotNull GroovyParser.ModifiersOptContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitModifiers(@NotNull GroovyParser.ModifiersContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitClassOrInterfaceModifiersOpt(@NotNull GroovyParser.ClassOrInterfaceModifiersOptContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitClassOrInterfaceModifiers(@NotNull GroovyParser.ClassOrInterfaceModifiersContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitClassOrInterfaceModifier(@NotNull GroovyParser.ClassOrInterfaceModifierContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitVariableModifier(@NotNull GroovyParser.VariableModifierContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitVariableModifiersOpt(@NotNull GroovyParser.VariableModifiersOptContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitVariableModifiers(@NotNull GroovyParser.VariableModifiersContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitTypeParameters(@NotNull GroovyParser.TypeParametersContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitTypeParameter(@NotNull GroovyParser.TypeParameterContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitTypeBound(@NotNull GroovyParser.TypeBoundContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitTypeList(@NotNull GroovyParser.TypeListContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitClassDeclaration(@NotNull GroovyParser.ClassDeclarationContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitClassBody(@NotNull GroovyParser.ClassBodyContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitEnumConstants(@NotNull GroovyParser.EnumConstantsContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitEnumConstant(@NotNull GroovyParser.EnumConstantContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitClassBodyDeclaration(@NotNull GroovyParser.ClassBodyDeclarationContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitMemberDeclaration(@NotNull GroovyParser.MemberDeclarationContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitMethodDeclaration(@NotNull GroovyParser.MethodDeclarationContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitCompactConstructorDeclaration(@NotNull GroovyParser.CompactConstructorDeclarationContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitMethodName(@NotNull GroovyParser.MethodNameContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitReturnType(@NotNull GroovyParser.ReturnTypeContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitFieldDeclaration(@NotNull GroovyParser.FieldDeclarationContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitVariableDeclarators(@NotNull GroovyParser.VariableDeclaratorsContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitVariableDeclarator(@NotNull GroovyParser.VariableDeclaratorContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitVariableDeclaratorId(@NotNull GroovyParser.VariableDeclaratorIdContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitVariableInitializer(@NotNull GroovyParser.VariableInitializerContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitVariableInitializers(@NotNull GroovyParser.VariableInitializersContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitEmptyDims(@NotNull GroovyParser.EmptyDimsContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitEmptyDimsOpt(@NotNull GroovyParser.EmptyDimsOptContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitType(@NotNull GroovyParser.TypeContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitClassOrInterfaceType(@NotNull GroovyParser.ClassOrInterfaceTypeContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitPrimitiveType(@NotNull GroovyParser.PrimitiveTypeContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitTypeArguments(@NotNull GroovyParser.TypeArgumentsContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitTypeArgument(@NotNull GroovyParser.TypeArgumentContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitAnnotatedQualifiedClassName(@NotNull GroovyParser.AnnotatedQualifiedClassNameContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitQualifiedClassNameList(@NotNull GroovyParser.QualifiedClassNameListContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitFormalParameters(@NotNull GroovyParser.FormalParametersContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitFormalParameterList(@NotNull GroovyParser.FormalParameterListContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitThisFormalParameter(@NotNull GroovyParser.ThisFormalParameterContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitFormalParameter(@NotNull GroovyParser.FormalParameterContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitMethodBody(@NotNull GroovyParser.MethodBodyContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitQualifiedName(@NotNull GroovyParser.QualifiedNameContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitQualifiedNameElement(@NotNull GroovyParser.QualifiedNameElementContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitQualifiedNameElements(@NotNull GroovyParser.QualifiedNameElementsContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitQualifiedClassName(@NotNull GroovyParser.QualifiedClassNameContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitQualifiedStandardClassName(@NotNull GroovyParser.QualifiedStandardClassNameContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitLiteral(@NotNull GroovyParser.LiteralContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitGstring(@NotNull GroovyParser.GstringContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitGstringValue(@NotNull GroovyParser.GstringValueContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitGstringPath(@NotNull GroovyParser.GstringPathContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitStandardLambdaExpression(@NotNull GroovyParser.StandardLambdaExpressionContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitStandardLambdaParameters(@NotNull GroovyParser.StandardLambdaParametersContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitLambdaBody(@NotNull GroovyParser.LambdaBodyContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitClosure(@NotNull GroovyParser.ClosureContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitClosureOrLambdaExpression(@NotNull GroovyParser.ClosureOrLambdaExpressionContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitBlockStatementsOpt(@NotNull GroovyParser.BlockStatementsOptContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitBlockStatements(@NotNull GroovyParser.BlockStatementsContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitAnnotationsOpt(@NotNull GroovyParser.AnnotationsOptContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitAnnotation(@NotNull GroovyParser.AnnotationContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitElementValues(@NotNull GroovyParser.ElementValuesContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitAnnotationName(@NotNull GroovyParser.AnnotationNameContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitElementValuePairs(@NotNull GroovyParser.ElementValuePairsContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitElementValuePair(@NotNull GroovyParser.ElementValuePairContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitElementValuePairName(@NotNull GroovyParser.ElementValuePairNameContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitElementValue(@NotNull GroovyParser.ElementValueContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitElementValueArrayInitializer(@NotNull GroovyParser.ElementValueArrayInitializerContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitBlock(@NotNull GroovyParser.BlockContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitBlockStatement(@NotNull GroovyParser.BlockStatementContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitLocalVariableDeclaration(@NotNull GroovyParser.LocalVariableDeclarationContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitVariableDeclaration(@NotNull GroovyParser.VariableDeclarationContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitTypeNamePairs(@NotNull GroovyParser.TypeNamePairsContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitTypeNamePair(@NotNull GroovyParser.TypeNamePairContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitVariableNames(@NotNull GroovyParser.VariableNamesContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitConditionalStatement(@NotNull GroovyParser.ConditionalStatementContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitIfElseStatement(@NotNull GroovyParser.IfElseStatementContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitSwitchStatement(@NotNull GroovyParser.SwitchStatementContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitLoopStatement(@NotNull GroovyParser.LoopStatementContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitContinueStatement(@NotNull GroovyParser.ContinueStatementContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitBreakStatement(@NotNull GroovyParser.BreakStatementContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitYieldStatement(@NotNull GroovyParser.YieldStatementContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitTryCatchStatement(@NotNull GroovyParser.TryCatchStatementContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitAssertStatement(@NotNull GroovyParser.AssertStatementContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitStatement(@NotNull GroovyParser.StatementContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitCatchClause(@NotNull GroovyParser.CatchClauseContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitCatchType(@NotNull GroovyParser.CatchTypeContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitFinallyBlock(@NotNull GroovyParser.FinallyBlockContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitResources(@NotNull GroovyParser.ResourcesContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitResourceList(@NotNull GroovyParser.ResourceListContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitResource(@NotNull GroovyParser.ResourceContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitSwitchBlockStatementGroup(@NotNull GroovyParser.SwitchBlockStatementGroupContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitSwitchLabel(@NotNull GroovyParser.SwitchLabelContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitForControl(@NotNull GroovyParser.ForControlContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitEnhancedForControl(@NotNull GroovyParser.EnhancedForControlContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitClassicalForControl(@NotNull GroovyParser.ClassicalForControlContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitForInit(@NotNull GroovyParser.ForInitContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitForUpdate(@NotNull GroovyParser.ForUpdateContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitCastParExpression(@NotNull GroovyParser.CastParExpressionContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitParExpression(@NotNull GroovyParser.ParExpressionContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitExpressionInPar(@NotNull GroovyParser.ExpressionInParContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitExpressionList(@NotNull GroovyParser.ExpressionListContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitExpressionListElement(@NotNull GroovyParser.ExpressionListElementContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitEnhancedStatementExpression(@NotNull GroovyParser.EnhancedStatementExpressionContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitStatementExpression(@NotNull GroovyParser.StatementExpressionContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitPostfixExpression(@NotNull GroovyParser.PostfixExpressionContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitSwitchExpression(@NotNull GroovyParser.SwitchExpressionContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitSwitchBlockStatementExpressionGroup(@NotNull GroovyParser.SwitchBlockStatementExpressionGroupContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitSwitchExpressionLabel(@NotNull GroovyParser.SwitchExpressionLabelContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitExpression(@NotNull GroovyParser.ExpressionContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitCommandExpression(@NotNull GroovyParser.CommandExpressionContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitCommandArgument(@NotNull GroovyParser.CommandArgumentContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitPathExpression(@NotNull GroovyParser.PathExpressionContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitPathElement(@NotNull GroovyParser.PathElementContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitNamePart(@NotNull GroovyParser.NamePartContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitDynamicMemberName(@NotNull GroovyParser.DynamicMemberNameContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitIndexPropertyArgs(@NotNull GroovyParser.IndexPropertyArgsContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitNamedPropertyArgs(@NotNull GroovyParser.NamedPropertyArgsContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitPrimary(@NotNull GroovyParser.PrimaryContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitList(@NotNull GroovyParser.ListContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitMap(@NotNull GroovyParser.MapContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitMapEntryList(@NotNull GroovyParser.MapEntryListContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitMapEntry(@NotNull GroovyParser.MapEntryContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitMapEntryLabel(@NotNull GroovyParser.MapEntryLabelContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitCreator(@NotNull GroovyParser.CreatorContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitDim(@NotNull GroovyParser.DimContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitArrayInitializer(@NotNull GroovyParser.ArrayInitializerContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitAnonymousInnerClassDeclaration(@NotNull GroovyParser.AnonymousInnerClassDeclarationContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitCreatedName(@NotNull GroovyParser.CreatedNameContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitNonWildcardTypeArguments(@NotNull GroovyParser.NonWildcardTypeArgumentsContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitTypeArgumentsOrDiamond(@NotNull GroovyParser.TypeArgumentsOrDiamondContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitArguments(@NotNull GroovyParser.ArgumentsContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitEnhancedArgumentListInPar(@NotNull GroovyParser.EnhancedArgumentListInParContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitEnhancedArgumentListElement(@NotNull GroovyParser.EnhancedArgumentListElementContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitStringLiteral(@NotNull GroovyParser.StringLiteralContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitClassName(@NotNull GroovyParser.ClassNameContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitIdentifier(@NotNull GroovyParser.IdentifierContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitBuiltInType(@NotNull GroovyParser.BuiltInTypeContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitKeywords(@NotNull GroovyParser.KeywordsContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitRparen(@NotNull GroovyParser.RparenContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitNls(@NotNull GroovyParser.NlsContext ctx) {
        return this.visitChildren(ctx);
    }

    @Override
    public Result visitSep(@NotNull GroovyParser.SepContext ctx) {
        return this.visitChildren(ctx);
    }
}

