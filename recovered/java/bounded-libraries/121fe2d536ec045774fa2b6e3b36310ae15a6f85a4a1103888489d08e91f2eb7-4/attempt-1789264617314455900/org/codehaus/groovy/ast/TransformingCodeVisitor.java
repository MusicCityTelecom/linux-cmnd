/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast;

import org.codehaus.groovy.ast.ClassCodeExpressionTransformer;
import org.codehaus.groovy.ast.CodeVisitorSupport;
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
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.ElvisOperatorExpression;
import org.codehaus.groovy.ast.expr.FieldExpression;
import org.codehaus.groovy.ast.expr.GStringExpression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.MapEntryExpression;
import org.codehaus.groovy.ast.expr.MapExpression;
import org.codehaus.groovy.ast.expr.MethodPointerExpression;
import org.codehaus.groovy.ast.expr.NotExpression;
import org.codehaus.groovy.ast.expr.PostfixExpression;
import org.codehaus.groovy.ast.expr.PrefixExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.RangeExpression;
import org.codehaus.groovy.ast.expr.SpreadExpression;
import org.codehaus.groovy.ast.expr.SpreadMapExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
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
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ForStatement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.SwitchStatement;
import org.codehaus.groovy.ast.stmt.SynchronizedStatement;
import org.codehaus.groovy.ast.stmt.ThrowStatement;
import org.codehaus.groovy.ast.stmt.TryCatchStatement;
import org.codehaus.groovy.ast.stmt.WhileStatement;
import org.codehaus.groovy.classgen.BytecodeExpression;

public class TransformingCodeVisitor
extends CodeVisitorSupport {
    private final ClassCodeExpressionTransformer trn;

    public TransformingCodeVisitor(ClassCodeExpressionTransformer trn) {
        this.trn = trn;
    }

    @Override
    public void visitBlockStatement(BlockStatement block) {
        super.visitBlockStatement(block);
        this.trn.visitBlockStatement(block);
    }

    @Override
    public void visitForLoop(ForStatement forLoop) {
        super.visitForLoop(forLoop);
        this.trn.visitForLoop(forLoop);
    }

    @Override
    public void visitWhileLoop(WhileStatement loop) {
        super.visitWhileLoop(loop);
        this.trn.visitWhileLoop(loop);
    }

    @Override
    public void visitDoWhileLoop(DoWhileStatement loop) {
        super.visitDoWhileLoop(loop);
        this.trn.visitDoWhileLoop(loop);
    }

    @Override
    public void visitIfElse(IfStatement ifElse) {
        super.visitIfElse(ifElse);
        this.trn.visitIfElse(ifElse);
    }

    @Override
    public void visitExpressionStatement(ExpressionStatement statement) {
        super.visitExpressionStatement(statement);
        this.trn.visitExpressionStatement(statement);
    }

    @Override
    public void visitReturnStatement(ReturnStatement statement) {
        super.visitReturnStatement(statement);
        this.trn.visitReturnStatement(statement);
    }

    @Override
    public void visitAssertStatement(AssertStatement statement) {
        super.visitAssertStatement(statement);
        this.trn.visitAssertStatement(statement);
    }

    @Override
    public void visitTryCatchFinally(TryCatchStatement statement) {
        super.visitTryCatchFinally(statement);
        this.trn.visitTryCatchFinally(statement);
    }

    @Override
    public void visitSwitch(SwitchStatement statement) {
        super.visitSwitch(statement);
        this.trn.visitSwitch(statement);
    }

    @Override
    public void visitCaseStatement(CaseStatement statement) {
        super.visitCaseStatement(statement);
        this.trn.visitCaseStatement(statement);
    }

    @Override
    public void visitBreakStatement(BreakStatement statement) {
        super.visitBreakStatement(statement);
        this.trn.visitBreakStatement(statement);
    }

    @Override
    public void visitContinueStatement(ContinueStatement statement) {
        super.visitContinueStatement(statement);
        this.trn.visitContinueStatement(statement);
    }

    @Override
    public void visitSynchronizedStatement(SynchronizedStatement statement) {
        super.visitSynchronizedStatement(statement);
        this.trn.visitSynchronizedStatement(statement);
    }

    @Override
    public void visitThrowStatement(ThrowStatement statement) {
        super.visitThrowStatement(statement);
        this.trn.visitThrowStatement(statement);
    }

    @Override
    public void visitStaticMethodCallExpression(StaticMethodCallExpression call) {
        super.visitStaticMethodCallExpression(call);
        this.trn.visitStaticMethodCallExpression(call);
    }

    @Override
    public void visitBinaryExpression(BinaryExpression expression) {
        super.visitBinaryExpression(expression);
        this.trn.visitBinaryExpression(expression);
    }

    @Override
    public void visitTernaryExpression(TernaryExpression expression) {
        super.visitTernaryExpression(expression);
        this.trn.visitTernaryExpression(expression);
    }

    @Override
    public void visitShortTernaryExpression(ElvisOperatorExpression expression) {
        super.visitShortTernaryExpression(expression);
        this.trn.visitShortTernaryExpression(expression);
    }

    @Override
    public void visitPostfixExpression(PostfixExpression expression) {
        super.visitPostfixExpression(expression);
        this.trn.visitPostfixExpression(expression);
    }

    @Override
    public void visitPrefixExpression(PrefixExpression expression) {
        super.visitPrefixExpression(expression);
        this.trn.visitPrefixExpression(expression);
    }

    @Override
    public void visitBooleanExpression(BooleanExpression expression) {
        super.visitBooleanExpression(expression);
        this.trn.visitBooleanExpression(expression);
    }

    @Override
    public void visitNotExpression(NotExpression expression) {
        super.visitNotExpression(expression);
        this.trn.visitNotExpression(expression);
    }

    @Override
    public void visitClosureExpression(ClosureExpression expression) {
        super.visitClosureExpression(expression);
        this.trn.visitClosureExpression(expression);
    }

    @Override
    public void visitTupleExpression(TupleExpression expression) {
        super.visitTupleExpression(expression);
        this.trn.visitTupleExpression(expression);
    }

    @Override
    public void visitListExpression(ListExpression expression) {
        super.visitListExpression(expression);
        this.trn.visitListExpression(expression);
    }

    @Override
    public void visitArrayExpression(ArrayExpression expression) {
        super.visitArrayExpression(expression);
        this.trn.visitArrayExpression(expression);
    }

    @Override
    public void visitMapExpression(MapExpression expression) {
        super.visitMapExpression(expression);
        this.trn.visitMapExpression(expression);
    }

    @Override
    public void visitMapEntryExpression(MapEntryExpression expression) {
        super.visitMapEntryExpression(expression);
        this.trn.visitMapEntryExpression(expression);
    }

    @Override
    public void visitRangeExpression(RangeExpression expression) {
        super.visitRangeExpression(expression);
        this.trn.visitRangeExpression(expression);
    }

    @Override
    public void visitSpreadExpression(SpreadExpression expression) {
        super.visitSpreadExpression(expression);
        this.trn.visitSpreadExpression(expression);
    }

    @Override
    public void visitSpreadMapExpression(SpreadMapExpression expression) {
        super.visitSpreadMapExpression(expression);
        this.trn.visitSpreadMapExpression(expression);
    }

    @Override
    public void visitMethodPointerExpression(MethodPointerExpression expression) {
        super.visitMethodPointerExpression(expression);
        this.trn.visitMethodPointerExpression(expression);
    }

    @Override
    public void visitUnaryMinusExpression(UnaryMinusExpression expression) {
        super.visitUnaryMinusExpression(expression);
        this.trn.visitUnaryMinusExpression(expression);
    }

    @Override
    public void visitUnaryPlusExpression(UnaryPlusExpression expression) {
        super.visitUnaryPlusExpression(expression);
        this.trn.visitUnaryPlusExpression(expression);
    }

    @Override
    public void visitBitwiseNegationExpression(BitwiseNegationExpression expression) {
        super.visitBitwiseNegationExpression(expression);
        this.trn.visitBitwiseNegationExpression(expression);
    }

    @Override
    public void visitCastExpression(CastExpression expression) {
        super.visitCastExpression(expression);
        this.trn.visitCastExpression(expression);
    }

    @Override
    public void visitConstantExpression(ConstantExpression expression) {
        super.visitConstantExpression(expression);
        this.trn.visitConstantExpression(expression);
    }

    @Override
    public void visitClassExpression(ClassExpression expression) {
        super.visitClassExpression(expression);
        this.trn.visitClassExpression(expression);
    }

    @Override
    public void visitVariableExpression(VariableExpression expression) {
        super.visitVariableExpression(expression);
        this.trn.visitVariableExpression(expression);
    }

    @Override
    public void visitDeclarationExpression(DeclarationExpression expression) {
        super.visitDeclarationExpression(expression);
        this.trn.visitDeclarationExpression(expression);
    }

    @Override
    public void visitPropertyExpression(PropertyExpression expression) {
        super.visitPropertyExpression(expression);
        this.trn.visitPropertyExpression(expression);
    }

    @Override
    public void visitAttributeExpression(AttributeExpression expression) {
        super.visitAttributeExpression(expression);
        this.trn.visitAttributeExpression(expression);
    }

    @Override
    public void visitFieldExpression(FieldExpression expression) {
        super.visitFieldExpression(expression);
        this.trn.visitFieldExpression(expression);
    }

    @Override
    public void visitGStringExpression(GStringExpression expression) {
        super.visitGStringExpression(expression);
        this.trn.visitGStringExpression(expression);
    }

    @Override
    public void visitCatchStatement(CatchStatement statement) {
        super.visitCatchStatement(statement);
        this.trn.visitCatchStatement(statement);
    }

    @Override
    public void visitArgumentlistExpression(ArgumentListExpression ale) {
        super.visitArgumentlistExpression(ale);
        this.trn.visitArgumentlistExpression(ale);
    }

    @Override
    public void visitClosureListExpression(ClosureListExpression cle) {
        super.visitClosureListExpression(cle);
        this.trn.visitClosureListExpression(cle);
    }

    @Override
    public void visitBytecodeExpression(BytecodeExpression cle) {
        super.visitBytecodeExpression(cle);
        this.trn.visitBytecodeExpression(cle);
    }
}

