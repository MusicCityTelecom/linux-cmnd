/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.expr;

import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ExpressionTransformer;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.syntax.Token;

public class DeclarationExpression
extends BinaryExpression {
    public DeclarationExpression(VariableExpression left, Token operation, Expression right) {
        this((Expression)left, operation, right);
    }

    public DeclarationExpression(Expression left, Token operation, Expression right) {
        super(left, Token.newSymbol("=", operation.getStartLine(), operation.getStartColumn()), right);
        DeclarationExpression.check(left);
    }

    private static void check(Expression left) {
        if (!(left instanceof VariableExpression)) {
            if (left instanceof TupleExpression) {
                TupleExpression tuple = (TupleExpression)left;
                if (tuple.getExpressions().isEmpty()) {
                    throw new GroovyBugError("one element required for left side");
                }
            } else {
                throw new GroovyBugError("illegal left expression for declaration: " + left);
            }
        }
    }

    @Override
    public void visit(GroovyCodeVisitor visitor) {
        visitor.visitDeclarationExpression(this);
    }

    public VariableExpression getVariableExpression() {
        Expression leftExpression = this.getLeftExpression();
        return leftExpression instanceof VariableExpression ? (VariableExpression)leftExpression : null;
    }

    public TupleExpression getTupleExpression() {
        Expression leftExpression = this.getLeftExpression();
        return leftExpression instanceof TupleExpression ? (TupleExpression)leftExpression : null;
    }

    @Override
    public String getText() {
        StringBuilder text = new StringBuilder();
        if (!this.isMultipleAssignmentDeclaration()) {
            VariableExpression v = this.getVariableExpression();
            if (v.isDynamicTyped()) {
                text.append("def");
            } else {
                text.append(ClassNodeUtils.formatTypeName(v.getType()));
            }
            text.append(' ').append(v.getText());
        } else {
            TupleExpression t = this.getTupleExpression();
            text.append("def (");
            for (Expression e : t.getExpressions()) {
                VariableExpression v;
                if (e instanceof VariableExpression && !(v = (VariableExpression)e).isDynamicTyped()) {
                    text.append(ClassNodeUtils.formatTypeName(v.getType())).append(' ');
                }
                text.append(e.getText()).append(", ");
            }
            text.setLength(text.length() - 2);
            text.append(')');
        }
        text.append(' ').append(this.getOperation().getText());
        text.append(' ').append(this.getRightExpression().getText());
        return text.toString();
    }

    @Override
    public void setLeftExpression(Expression leftExpression) {
        DeclarationExpression.check(leftExpression);
        super.setLeftExpression(leftExpression);
    }

    @Override
    public void setRightExpression(Expression rightExpression) {
        super.setRightExpression(rightExpression);
    }

    @Override
    public Expression transformExpression(ExpressionTransformer transformer) {
        DeclarationExpression ret = new DeclarationExpression(transformer.transform(this.getLeftExpression()), this.getOperation(), transformer.transform(this.getRightExpression()));
        ret.setSourcePosition(this);
        ret.addAnnotations(this.getAnnotations());
        ret.setDeclaringClass(this.getDeclaringClass());
        ret.copyNodeMetaData(this);
        return ret;
    }

    public boolean isMultipleAssignmentDeclaration() {
        return this.getLeftExpression() instanceof TupleExpression;
    }
}

