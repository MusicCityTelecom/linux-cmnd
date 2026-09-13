/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.stmt;

import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;

public class ReturnStatement
extends Statement {
    public static final ReturnStatement RETURN_NULL_OR_VOID = new ReturnStatement(GeneralUtils.nullX());
    private Expression expression;

    public ReturnStatement(ExpressionStatement statement) {
        this(statement.getExpression());
        this.copyStatementLabels(statement);
    }

    public ReturnStatement(Expression expression) {
        this.setExpression(expression);
    }

    public Expression getExpression() {
        return this.expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String getText() {
        return "return " + this.expression.getText();
    }

    public boolean isReturningNullOrVoid() {
        return this.expression instanceof ConstantExpression && ((ConstantExpression)this.expression).isNullExpression();
    }

    public String toString() {
        return super.toString() + "[expression:" + this.expression + "]";
    }

    @Override
    public void visit(GroovyCodeVisitor visitor) {
        visitor.visitReturnStatement(this);
    }
}

