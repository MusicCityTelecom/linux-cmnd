/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.parser.antlr4;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.tree.ParseTreeVisitor;
import org.apache.groovy.parser.antlr4.GroovyParser;

public interface GroovyParserVisitor<Result>
extends ParseTreeVisitor<Result> {
    public Result visitIdentifierPrmrAlt(@NotNull GroovyParser.IdentifierPrmrAltContext var1);

    public Result visitLiteralPrmrAlt(@NotNull GroovyParser.LiteralPrmrAltContext var1);

    public Result visitGstringPrmrAlt(@NotNull GroovyParser.GstringPrmrAltContext var1);

    public Result visitNewPrmrAlt(@NotNull GroovyParser.NewPrmrAltContext var1);

    public Result visitThisPrmrAlt(@NotNull GroovyParser.ThisPrmrAltContext var1);

    public Result visitSuperPrmrAlt(@NotNull GroovyParser.SuperPrmrAltContext var1);

    public Result visitParenPrmrAlt(@NotNull GroovyParser.ParenPrmrAltContext var1);

    public Result visitClosureOrLambdaExpressionPrmrAlt(@NotNull GroovyParser.ClosureOrLambdaExpressionPrmrAltContext var1);

    public Result visitListPrmrAlt(@NotNull GroovyParser.ListPrmrAltContext var1);

    public Result visitMapPrmrAlt(@NotNull GroovyParser.MapPrmrAltContext var1);

    public Result visitBuiltInTypePrmrAlt(@NotNull GroovyParser.BuiltInTypePrmrAltContext var1);

    public Result visitIntegerLiteralAlt(@NotNull GroovyParser.IntegerLiteralAltContext var1);

    public Result visitFloatingPointLiteralAlt(@NotNull GroovyParser.FloatingPointLiteralAltContext var1);

    public Result visitStringLiteralAlt(@NotNull GroovyParser.StringLiteralAltContext var1);

    public Result visitBooleanLiteralAlt(@NotNull GroovyParser.BooleanLiteralAltContext var1);

    public Result visitNullLiteralAlt(@NotNull GroovyParser.NullLiteralAltContext var1);

    public Result visitCastExprAlt(@NotNull GroovyParser.CastExprAltContext var1);

    public Result visitPostfixExprAlt(@NotNull GroovyParser.PostfixExprAltContext var1);

    public Result visitSwitchExprAlt(@NotNull GroovyParser.SwitchExprAltContext var1);

    public Result visitUnaryNotExprAlt(@NotNull GroovyParser.UnaryNotExprAltContext var1);

    public Result visitPowerExprAlt(@NotNull GroovyParser.PowerExprAltContext var1);

    public Result visitUnaryAddExprAlt(@NotNull GroovyParser.UnaryAddExprAltContext var1);

    public Result visitMultiplicativeExprAlt(@NotNull GroovyParser.MultiplicativeExprAltContext var1);

    public Result visitAdditiveExprAlt(@NotNull GroovyParser.AdditiveExprAltContext var1);

    public Result visitShiftExprAlt(@NotNull GroovyParser.ShiftExprAltContext var1);

    public Result visitRelationalExprAlt(@NotNull GroovyParser.RelationalExprAltContext var1);

    public Result visitEqualityExprAlt(@NotNull GroovyParser.EqualityExprAltContext var1);

    public Result visitRegexExprAlt(@NotNull GroovyParser.RegexExprAltContext var1);

    public Result visitAndExprAlt(@NotNull GroovyParser.AndExprAltContext var1);

    public Result visitExclusiveOrExprAlt(@NotNull GroovyParser.ExclusiveOrExprAltContext var1);

    public Result visitInclusiveOrExprAlt(@NotNull GroovyParser.InclusiveOrExprAltContext var1);

    public Result visitLogicalAndExprAlt(@NotNull GroovyParser.LogicalAndExprAltContext var1);

    public Result visitLogicalOrExprAlt(@NotNull GroovyParser.LogicalOrExprAltContext var1);

    public Result visitConditionalExprAlt(@NotNull GroovyParser.ConditionalExprAltContext var1);

    public Result visitMultipleAssignmentExprAlt(@NotNull GroovyParser.MultipleAssignmentExprAltContext var1);

    public Result visitAssignmentExprAlt(@NotNull GroovyParser.AssignmentExprAltContext var1);

    public Result visitBlockStmtAlt(@NotNull GroovyParser.BlockStmtAltContext var1);

    public Result visitConditionalStmtAlt(@NotNull GroovyParser.ConditionalStmtAltContext var1);

    public Result visitLoopStmtAlt(@NotNull GroovyParser.LoopStmtAltContext var1);

    public Result visitTryCatchStmtAlt(@NotNull GroovyParser.TryCatchStmtAltContext var1);

    public Result visitSynchronizedStmtAlt(@NotNull GroovyParser.SynchronizedStmtAltContext var1);

    public Result visitReturnStmtAlt(@NotNull GroovyParser.ReturnStmtAltContext var1);

    public Result visitThrowStmtAlt(@NotNull GroovyParser.ThrowStmtAltContext var1);

    public Result visitBreakStmtAlt(@NotNull GroovyParser.BreakStmtAltContext var1);

    public Result visitContinueStmtAlt(@NotNull GroovyParser.ContinueStmtAltContext var1);

    public Result visitYieldStmtAlt(@NotNull GroovyParser.YieldStmtAltContext var1);

    public Result visitLabeledStmtAlt(@NotNull GroovyParser.LabeledStmtAltContext var1);

    public Result visitAssertStmtAlt(@NotNull GroovyParser.AssertStmtAltContext var1);

    public Result visitLocalVariableDeclarationStmtAlt(@NotNull GroovyParser.LocalVariableDeclarationStmtAltContext var1);

    public Result visitExpressionStmtAlt(@NotNull GroovyParser.ExpressionStmtAltContext var1);

    public Result visitEmptyStmtAlt(@NotNull GroovyParser.EmptyStmtAltContext var1);

    public Result visitCommandExprAlt(@NotNull GroovyParser.CommandExprAltContext var1);

    public Result visitForStmtAlt(@NotNull GroovyParser.ForStmtAltContext var1);

    public Result visitWhileStmtAlt(@NotNull GroovyParser.WhileStmtAltContext var1);

    public Result visitDoWhileStmtAlt(@NotNull GroovyParser.DoWhileStmtAltContext var1);

    public Result visitCompilationUnit(@NotNull GroovyParser.CompilationUnitContext var1);

    public Result visitScriptStatements(@NotNull GroovyParser.ScriptStatementsContext var1);

    public Result visitScriptStatement(@NotNull GroovyParser.ScriptStatementContext var1);

    public Result visitPackageDeclaration(@NotNull GroovyParser.PackageDeclarationContext var1);

    public Result visitImportDeclaration(@NotNull GroovyParser.ImportDeclarationContext var1);

    public Result visitTypeDeclaration(@NotNull GroovyParser.TypeDeclarationContext var1);

    public Result visitModifier(@NotNull GroovyParser.ModifierContext var1);

    public Result visitModifiersOpt(@NotNull GroovyParser.ModifiersOptContext var1);

    public Result visitModifiers(@NotNull GroovyParser.ModifiersContext var1);

    public Result visitClassOrInterfaceModifiersOpt(@NotNull GroovyParser.ClassOrInterfaceModifiersOptContext var1);

    public Result visitClassOrInterfaceModifiers(@NotNull GroovyParser.ClassOrInterfaceModifiersContext var1);

    public Result visitClassOrInterfaceModifier(@NotNull GroovyParser.ClassOrInterfaceModifierContext var1);

    public Result visitVariableModifier(@NotNull GroovyParser.VariableModifierContext var1);

    public Result visitVariableModifiersOpt(@NotNull GroovyParser.VariableModifiersOptContext var1);

    public Result visitVariableModifiers(@NotNull GroovyParser.VariableModifiersContext var1);

    public Result visitTypeParameters(@NotNull GroovyParser.TypeParametersContext var1);

    public Result visitTypeParameter(@NotNull GroovyParser.TypeParameterContext var1);

    public Result visitTypeBound(@NotNull GroovyParser.TypeBoundContext var1);

    public Result visitTypeList(@NotNull GroovyParser.TypeListContext var1);

    public Result visitClassDeclaration(@NotNull GroovyParser.ClassDeclarationContext var1);

    public Result visitClassBody(@NotNull GroovyParser.ClassBodyContext var1);

    public Result visitEnumConstants(@NotNull GroovyParser.EnumConstantsContext var1);

    public Result visitEnumConstant(@NotNull GroovyParser.EnumConstantContext var1);

    public Result visitClassBodyDeclaration(@NotNull GroovyParser.ClassBodyDeclarationContext var1);

    public Result visitMemberDeclaration(@NotNull GroovyParser.MemberDeclarationContext var1);

    public Result visitMethodDeclaration(@NotNull GroovyParser.MethodDeclarationContext var1);

    public Result visitCompactConstructorDeclaration(@NotNull GroovyParser.CompactConstructorDeclarationContext var1);

    public Result visitMethodName(@NotNull GroovyParser.MethodNameContext var1);

    public Result visitReturnType(@NotNull GroovyParser.ReturnTypeContext var1);

    public Result visitFieldDeclaration(@NotNull GroovyParser.FieldDeclarationContext var1);

    public Result visitVariableDeclarators(@NotNull GroovyParser.VariableDeclaratorsContext var1);

    public Result visitVariableDeclarator(@NotNull GroovyParser.VariableDeclaratorContext var1);

    public Result visitVariableDeclaratorId(@NotNull GroovyParser.VariableDeclaratorIdContext var1);

    public Result visitVariableInitializer(@NotNull GroovyParser.VariableInitializerContext var1);

    public Result visitVariableInitializers(@NotNull GroovyParser.VariableInitializersContext var1);

    public Result visitEmptyDims(@NotNull GroovyParser.EmptyDimsContext var1);

    public Result visitEmptyDimsOpt(@NotNull GroovyParser.EmptyDimsOptContext var1);

    public Result visitType(@NotNull GroovyParser.TypeContext var1);

    public Result visitClassOrInterfaceType(@NotNull GroovyParser.ClassOrInterfaceTypeContext var1);

    public Result visitPrimitiveType(@NotNull GroovyParser.PrimitiveTypeContext var1);

    public Result visitTypeArguments(@NotNull GroovyParser.TypeArgumentsContext var1);

    public Result visitTypeArgument(@NotNull GroovyParser.TypeArgumentContext var1);

    public Result visitAnnotatedQualifiedClassName(@NotNull GroovyParser.AnnotatedQualifiedClassNameContext var1);

    public Result visitQualifiedClassNameList(@NotNull GroovyParser.QualifiedClassNameListContext var1);

    public Result visitFormalParameters(@NotNull GroovyParser.FormalParametersContext var1);

    public Result visitFormalParameterList(@NotNull GroovyParser.FormalParameterListContext var1);

    public Result visitThisFormalParameter(@NotNull GroovyParser.ThisFormalParameterContext var1);

    public Result visitFormalParameter(@NotNull GroovyParser.FormalParameterContext var1);

    public Result visitMethodBody(@NotNull GroovyParser.MethodBodyContext var1);

    public Result visitQualifiedName(@NotNull GroovyParser.QualifiedNameContext var1);

    public Result visitQualifiedNameElement(@NotNull GroovyParser.QualifiedNameElementContext var1);

    public Result visitQualifiedNameElements(@NotNull GroovyParser.QualifiedNameElementsContext var1);

    public Result visitQualifiedClassName(@NotNull GroovyParser.QualifiedClassNameContext var1);

    public Result visitQualifiedStandardClassName(@NotNull GroovyParser.QualifiedStandardClassNameContext var1);

    public Result visitLiteral(@NotNull GroovyParser.LiteralContext var1);

    public Result visitGstring(@NotNull GroovyParser.GstringContext var1);

    public Result visitGstringValue(@NotNull GroovyParser.GstringValueContext var1);

    public Result visitGstringPath(@NotNull GroovyParser.GstringPathContext var1);

    public Result visitStandardLambdaExpression(@NotNull GroovyParser.StandardLambdaExpressionContext var1);

    public Result visitStandardLambdaParameters(@NotNull GroovyParser.StandardLambdaParametersContext var1);

    public Result visitLambdaBody(@NotNull GroovyParser.LambdaBodyContext var1);

    public Result visitClosure(@NotNull GroovyParser.ClosureContext var1);

    public Result visitClosureOrLambdaExpression(@NotNull GroovyParser.ClosureOrLambdaExpressionContext var1);

    public Result visitBlockStatementsOpt(@NotNull GroovyParser.BlockStatementsOptContext var1);

    public Result visitBlockStatements(@NotNull GroovyParser.BlockStatementsContext var1);

    public Result visitAnnotationsOpt(@NotNull GroovyParser.AnnotationsOptContext var1);

    public Result visitAnnotation(@NotNull GroovyParser.AnnotationContext var1);

    public Result visitElementValues(@NotNull GroovyParser.ElementValuesContext var1);

    public Result visitAnnotationName(@NotNull GroovyParser.AnnotationNameContext var1);

    public Result visitElementValuePairs(@NotNull GroovyParser.ElementValuePairsContext var1);

    public Result visitElementValuePair(@NotNull GroovyParser.ElementValuePairContext var1);

    public Result visitElementValuePairName(@NotNull GroovyParser.ElementValuePairNameContext var1);

    public Result visitElementValue(@NotNull GroovyParser.ElementValueContext var1);

    public Result visitElementValueArrayInitializer(@NotNull GroovyParser.ElementValueArrayInitializerContext var1);

    public Result visitBlock(@NotNull GroovyParser.BlockContext var1);

    public Result visitBlockStatement(@NotNull GroovyParser.BlockStatementContext var1);

    public Result visitLocalVariableDeclaration(@NotNull GroovyParser.LocalVariableDeclarationContext var1);

    public Result visitVariableDeclaration(@NotNull GroovyParser.VariableDeclarationContext var1);

    public Result visitTypeNamePairs(@NotNull GroovyParser.TypeNamePairsContext var1);

    public Result visitTypeNamePair(@NotNull GroovyParser.TypeNamePairContext var1);

    public Result visitVariableNames(@NotNull GroovyParser.VariableNamesContext var1);

    public Result visitConditionalStatement(@NotNull GroovyParser.ConditionalStatementContext var1);

    public Result visitIfElseStatement(@NotNull GroovyParser.IfElseStatementContext var1);

    public Result visitSwitchStatement(@NotNull GroovyParser.SwitchStatementContext var1);

    public Result visitLoopStatement(@NotNull GroovyParser.LoopStatementContext var1);

    public Result visitContinueStatement(@NotNull GroovyParser.ContinueStatementContext var1);

    public Result visitBreakStatement(@NotNull GroovyParser.BreakStatementContext var1);

    public Result visitYieldStatement(@NotNull GroovyParser.YieldStatementContext var1);

    public Result visitTryCatchStatement(@NotNull GroovyParser.TryCatchStatementContext var1);

    public Result visitAssertStatement(@NotNull GroovyParser.AssertStatementContext var1);

    public Result visitStatement(@NotNull GroovyParser.StatementContext var1);

    public Result visitCatchClause(@NotNull GroovyParser.CatchClauseContext var1);

    public Result visitCatchType(@NotNull GroovyParser.CatchTypeContext var1);

    public Result visitFinallyBlock(@NotNull GroovyParser.FinallyBlockContext var1);

    public Result visitResources(@NotNull GroovyParser.ResourcesContext var1);

    public Result visitResourceList(@NotNull GroovyParser.ResourceListContext var1);

    public Result visitResource(@NotNull GroovyParser.ResourceContext var1);

    public Result visitSwitchBlockStatementGroup(@NotNull GroovyParser.SwitchBlockStatementGroupContext var1);

    public Result visitSwitchLabel(@NotNull GroovyParser.SwitchLabelContext var1);

    public Result visitForControl(@NotNull GroovyParser.ForControlContext var1);

    public Result visitEnhancedForControl(@NotNull GroovyParser.EnhancedForControlContext var1);

    public Result visitClassicalForControl(@NotNull GroovyParser.ClassicalForControlContext var1);

    public Result visitForInit(@NotNull GroovyParser.ForInitContext var1);

    public Result visitForUpdate(@NotNull GroovyParser.ForUpdateContext var1);

    public Result visitCastParExpression(@NotNull GroovyParser.CastParExpressionContext var1);

    public Result visitParExpression(@NotNull GroovyParser.ParExpressionContext var1);

    public Result visitExpressionInPar(@NotNull GroovyParser.ExpressionInParContext var1);

    public Result visitExpressionList(@NotNull GroovyParser.ExpressionListContext var1);

    public Result visitExpressionListElement(@NotNull GroovyParser.ExpressionListElementContext var1);

    public Result visitEnhancedStatementExpression(@NotNull GroovyParser.EnhancedStatementExpressionContext var1);

    public Result visitStatementExpression(@NotNull GroovyParser.StatementExpressionContext var1);

    public Result visitPostfixExpression(@NotNull GroovyParser.PostfixExpressionContext var1);

    public Result visitSwitchExpression(@NotNull GroovyParser.SwitchExpressionContext var1);

    public Result visitSwitchBlockStatementExpressionGroup(@NotNull GroovyParser.SwitchBlockStatementExpressionGroupContext var1);

    public Result visitSwitchExpressionLabel(@NotNull GroovyParser.SwitchExpressionLabelContext var1);

    public Result visitExpression(@NotNull GroovyParser.ExpressionContext var1);

    public Result visitCommandExpression(@NotNull GroovyParser.CommandExpressionContext var1);

    public Result visitCommandArgument(@NotNull GroovyParser.CommandArgumentContext var1);

    public Result visitPathExpression(@NotNull GroovyParser.PathExpressionContext var1);

    public Result visitPathElement(@NotNull GroovyParser.PathElementContext var1);

    public Result visitNamePart(@NotNull GroovyParser.NamePartContext var1);

    public Result visitDynamicMemberName(@NotNull GroovyParser.DynamicMemberNameContext var1);

    public Result visitIndexPropertyArgs(@NotNull GroovyParser.IndexPropertyArgsContext var1);

    public Result visitNamedPropertyArgs(@NotNull GroovyParser.NamedPropertyArgsContext var1);

    public Result visitPrimary(@NotNull GroovyParser.PrimaryContext var1);

    public Result visitList(@NotNull GroovyParser.ListContext var1);

    public Result visitMap(@NotNull GroovyParser.MapContext var1);

    public Result visitMapEntryList(@NotNull GroovyParser.MapEntryListContext var1);

    public Result visitMapEntry(@NotNull GroovyParser.MapEntryContext var1);

    public Result visitMapEntryLabel(@NotNull GroovyParser.MapEntryLabelContext var1);

    public Result visitCreator(@NotNull GroovyParser.CreatorContext var1);

    public Result visitDim(@NotNull GroovyParser.DimContext var1);

    public Result visitArrayInitializer(@NotNull GroovyParser.ArrayInitializerContext var1);

    public Result visitAnonymousInnerClassDeclaration(@NotNull GroovyParser.AnonymousInnerClassDeclarationContext var1);

    public Result visitCreatedName(@NotNull GroovyParser.CreatedNameContext var1);

    public Result visitNonWildcardTypeArguments(@NotNull GroovyParser.NonWildcardTypeArgumentsContext var1);

    public Result visitTypeArgumentsOrDiamond(@NotNull GroovyParser.TypeArgumentsOrDiamondContext var1);

    public Result visitArguments(@NotNull GroovyParser.ArgumentsContext var1);

    public Result visitEnhancedArgumentListInPar(@NotNull GroovyParser.EnhancedArgumentListInParContext var1);

    public Result visitEnhancedArgumentListElement(@NotNull GroovyParser.EnhancedArgumentListElementContext var1);

    public Result visitStringLiteral(@NotNull GroovyParser.StringLiteralContext var1);

    public Result visitClassName(@NotNull GroovyParser.ClassNameContext var1);

    public Result visitIdentifier(@NotNull GroovyParser.IdentifierContext var1);

    public Result visitBuiltInType(@NotNull GroovyParser.BuiltInTypeContext var1);

    public Result visitKeywords(@NotNull GroovyParser.KeywordsContext var1);

    public Result visitRparen(@NotNull GroovyParser.RparenContext var1);

    public Result visitNls(@NotNull GroovyParser.NlsContext var1);

    public Result visitSep(@NotNull GroovyParser.SepContext var1);
}

