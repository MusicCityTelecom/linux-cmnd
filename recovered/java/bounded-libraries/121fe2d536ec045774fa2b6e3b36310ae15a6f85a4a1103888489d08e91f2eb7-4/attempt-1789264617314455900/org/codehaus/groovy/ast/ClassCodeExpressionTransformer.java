/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast;

import java.util.Map;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.BooleanExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ExpressionTransformer;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.stmt.AssertStatement;
import org.codehaus.groovy.ast.stmt.CaseStatement;
import org.codehaus.groovy.ast.stmt.DoWhileStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ForStatement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.SwitchStatement;
import org.codehaus.groovy.ast.stmt.SynchronizedStatement;
import org.codehaus.groovy.ast.stmt.ThrowStatement;
import org.codehaus.groovy.ast.stmt.WhileStatement;

public abstract class ClassCodeExpressionTransformer
extends ClassCodeVisitorSupport
implements ExpressionTransformer {
    @Override
    public Expression transform(Expression expr) {
        if (expr == null) {
            return null;
        }
        return expr.transformExpression(this);
    }

    @Override
    protected void visitAnnotation(AnnotationNode node) {
        for (Map.Entry<String, Expression> entry : node.getMembers().entrySet()) {
            entry.setValue(this.transform(entry.getValue()));
        }
    }

    @Override
    protected void visitConstructorOrMethod(MethodNode node, boolean isConstructor) {
        for (Parameter p : node.getParameters()) {
            if (!p.hasInitialExpression()) continue;
            Expression init = p.getInitialExpression();
            p.setInitialExpression(this.transform(init));
        }
        super.visitConstructorOrMethod(node, isConstructor);
    }

    @Override
    public void visitField(FieldNode node) {
        this.visitAnnotations(node);
        Expression init = node.getInitialExpression();
        node.setInitialValueExpression(this.transform(init));
    }

    @Override
    public void visitProperty(PropertyNode node) {
        this.visitAnnotations(node);
        Statement statement = node.getGetterBlock();
        this.visitClassCodeContainer(statement);
        statement = node.getSetterBlock();
        this.visitClassCodeContainer(statement);
    }

    @Override
    public void visitAssertStatement(AssertStatement stmt) {
        stmt.setBooleanExpression((BooleanExpression)this.transform(stmt.getBooleanExpression()));
        stmt.setMessageExpression(this.transform(stmt.getMessageExpression()));
    }

    @Override
    public void visitCaseStatement(CaseStatement stmt) {
        stmt.setExpression(this.transform(stmt.getExpression()));
        stmt.getCode().visit(this);
    }

    @Override
    public void visitDoWhileLoop(DoWhileStatement stmt) {
        stmt.setBooleanExpression((BooleanExpression)this.transform(stmt.getBooleanExpression()));
        super.visitDoWhileLoop(stmt);
    }

    @Override
    public void visitExpressionStatement(ExpressionStatement stmt) {
        stmt.setExpression(this.transform(stmt.getExpression()));
    }

    @Override
    public void visitForLoop(ForStatement stmt) {
        stmt.setCollectionExpression(this.transform(stmt.getCollectionExpression()));
        super.visitForLoop(stmt);
    }

    @Override
    public void visitIfElse(IfStatement stmt) {
        stmt.setBooleanExpression((BooleanExpression)this.transform(stmt.getBooleanExpression()));
        stmt.getIfBlock().visit(this);
        stmt.getElseBlock().visit(this);
    }

    @Override
    public void visitReturnStatement(ReturnStatement stmt) {
        stmt.setExpression(this.transform(stmt.getExpression()));
    }

    @Override
    public void visitSwitch(SwitchStatement stmt) {
        Expression exp = stmt.getExpression();
        stmt.setExpression(this.transform(exp));
        for (CaseStatement caseStatement : stmt.getCaseStatements()) {
            caseStatement.visit(this);
        }
        stmt.getDefaultStatement().visit(this);
    }

    @Override
    public void visitSynchronizedStatement(SynchronizedStatement stmt) {
        stmt.setExpression(this.transform(stmt.getExpression()));
        super.visitSynchronizedStatement(stmt);
    }

    @Override
    public void visitThrowStatement(ThrowStatement stmt) {
        stmt.setExpression(this.transform(stmt.getExpression()));
    }

    @Override
    public void visitWhileLoop(WhileStatement stmt) {
        stmt.setBooleanExpression((BooleanExpression)this.transform(stmt.getBooleanExpression()));
        super.visitWhileLoop(stmt);
    }

    protected static void setSourcePosition(Expression toSet, Expression origNode) {
        toSet.setSourcePosition(origNode);
        if (toSet instanceof PropertyExpression) {
            ((PropertyExpression)toSet).getProperty().setSourcePosition(origNode);
        }
    }
}

