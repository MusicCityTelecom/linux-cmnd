/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.expr;

import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ExpressionTransformer;
import org.codehaus.groovy.ast.expr.MethodPointerExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;

public class MethodReferenceExpression
extends MethodPointerExpression {
    public MethodReferenceExpression(Expression expression, Expression methodName) {
        super(expression, methodName);
    }

    @Override
    public void visit(GroovyCodeVisitor visitor) {
        visitor.visitMethodReferenceExpression(this);
    }

    @Override
    public Expression transformExpression(ExpressionTransformer transformer) {
        Expression mname = transformer.transform(this.methodName);
        MethodReferenceExpression ret = this.expression == null ? new MethodReferenceExpression(VariableExpression.THIS_EXPRESSION, mname) : new MethodReferenceExpression(transformer.transform(this.expression), mname);
        ret.setSourcePosition(this);
        ret.copyNodeMetaData(this);
        return ret;
    }

    @Override
    public String getText() {
        Expression expression = this.getExpression();
        Expression methodName = this.getMethodName();
        if (expression == null) {
            return "::" + methodName;
        }
        return expression.getText() + "::" + methodName.getText();
    }
}

