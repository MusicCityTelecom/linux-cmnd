/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.tailrec;

import groovy.lang.Closure;
import java.lang.reflect.Method;
import org.apache.groovy.internal.util.UncheckedThrow;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.CodeVisitorSupport;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.BooleanExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ExpressionTransformer;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.AssertStatement;
import org.codehaus.groovy.ast.stmt.CaseStatement;
import org.codehaus.groovy.ast.stmt.DoWhileStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ForStatement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.SwitchStatement;
import org.codehaus.groovy.ast.stmt.SynchronizedStatement;
import org.codehaus.groovy.ast.stmt.ThrowStatement;
import org.codehaus.groovy.ast.stmt.WhileStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.transform.tailrec.VariableExpressionTransformer;

public class VariableExpressionReplacer
extends CodeVisitorSupport {
    private Closure<Boolean> when;
    private Closure<VariableExpression> replaceWith;
    private ExpressionTransformer transformer;

    public VariableExpressionReplacer(Closure<Boolean> when, Closure<VariableExpression> replaceWith) {
        this.when = when;
        this.replaceWith = replaceWith;
    }

    @Override
    public void visitReturnStatement(ReturnStatement statement) {
        this.replaceExpressionPropertyWhenNecessary(statement);
        super.visitReturnStatement(statement);
    }

    @Override
    public void visitIfElse(IfStatement ifElse) {
        this.replaceExpressionPropertyWhenNecessary(ifElse, "booleanExpression", BooleanExpression.class);
        super.visitIfElse(ifElse);
    }

    @Override
    public void visitForLoop(ForStatement forLoop) {
        this.replaceExpressionPropertyWhenNecessary(forLoop, "collectionExpression");
        super.visitForLoop(forLoop);
    }

    @Override
    public void visitBinaryExpression(BinaryExpression expression) {
        this.replaceExpressionPropertyWhenNecessary(expression, "rightExpression");
        expression.getRightExpression().visit(this);
        super.visitBinaryExpression(expression);
    }

    @Override
    public void visitWhileLoop(WhileStatement loop) {
        this.replaceExpressionPropertyWhenNecessary(loop, "booleanExpression", BooleanExpression.class);
        super.visitWhileLoop(loop);
    }

    @Override
    public void visitDoWhileLoop(DoWhileStatement loop) {
        this.replaceExpressionPropertyWhenNecessary(loop, "booleanExpression", BooleanExpression.class);
        super.visitDoWhileLoop(loop);
    }

    @Override
    public void visitSwitch(SwitchStatement statement) {
        this.replaceExpressionPropertyWhenNecessary(statement);
        super.visitSwitch(statement);
    }

    @Override
    public void visitCaseStatement(CaseStatement statement) {
        this.replaceExpressionPropertyWhenNecessary(statement);
        super.visitCaseStatement(statement);
    }

    @Override
    public void visitExpressionStatement(ExpressionStatement statement) {
        this.replaceExpressionPropertyWhenNecessary(statement);
        super.visitExpressionStatement(statement);
    }

    @Override
    public void visitThrowStatement(ThrowStatement statement) {
        this.replaceExpressionPropertyWhenNecessary(statement);
        super.visitThrowStatement(statement);
    }

    @Override
    public void visitAssertStatement(AssertStatement statement) {
        this.replaceExpressionPropertyWhenNecessary(statement, "booleanExpression", BooleanExpression.class);
        this.replaceExpressionPropertyWhenNecessary(statement, "messageExpression");
        super.visitAssertStatement(statement);
    }

    @Override
    public void visitSynchronizedStatement(SynchronizedStatement statement) {
        this.replaceExpressionPropertyWhenNecessary(statement);
        super.visitSynchronizedStatement(statement);
    }

    public synchronized void replaceIn(ASTNode root) {
        this.transformer = new VariableExpressionTransformer(this.when, this.replaceWith);
        root.visit(this);
    }

    private void replaceExpressionPropertyWhenNecessary(ASTNode node, String propName, Class propClass) {
        Expression expr = this.getExpression(node, propName);
        if (expr instanceof VariableExpression) {
            if (this.when.call((Object)expr).booleanValue()) {
                VariableExpression newExpr = this.replaceWith.call((Object)expr);
                this.replaceExpression(node, propName, propClass, expr, newExpr);
            }
        } else {
            Expression newExpr = this.transformer.transform(expr);
            this.replaceExpression(node, propName, propClass, expr, newExpr);
        }
    }

    private void replaceExpressionPropertyWhenNecessary(ASTNode node, String propName) {
        this.replaceExpressionPropertyWhenNecessary(node, propName, Expression.class);
    }

    private void replaceExpressionPropertyWhenNecessary(ASTNode node) {
        this.replaceExpressionPropertyWhenNecessary(node, "expression", Expression.class);
    }

    private void replaceExpression(ASTNode node, String propName, Class propClass, Expression oldExpr, Expression newExpr) {
        try {
            String setterName = GeneralUtils.getSetterName(propName);
            Method setExpressionMethod = node.getClass().getMethod(setterName, propClass);
            newExpr.copyNodeMetaData(oldExpr);
            newExpr.setSourcePosition(oldExpr);
            setExpressionMethod.invoke(node, newExpr);
        }
        catch (Throwable t) {
            UncheckedThrow.rethrow(t);
        }
    }

    private Expression getExpression(ASTNode node, String propName) {
        try {
            String getterName = GeneralUtils.getGetterName(propName);
            Method getExpressionMethod = node.getClass().getMethod(getterName, new Class[0]);
            return (Expression)getExpressionMethod.invoke(node, new Object[0]);
        }
        catch (Throwable t) {
            UncheckedThrow.rethrow(t);
            return null;
        }
    }

    public Closure<Boolean> getWhen() {
        return this.when;
    }

    public void setWhen(Closure<Boolean> when) {
        this.when = when;
    }

    public Closure<VariableExpression> getReplaceWith() {
        return this.replaceWith;
    }

    public void setReplaceWith(Closure<VariableExpression> replaceWith) {
        this.replaceWith = replaceWith;
    }
}

