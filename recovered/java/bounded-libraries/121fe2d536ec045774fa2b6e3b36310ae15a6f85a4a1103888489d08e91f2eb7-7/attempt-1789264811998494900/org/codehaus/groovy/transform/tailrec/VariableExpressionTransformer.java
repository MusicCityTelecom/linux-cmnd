/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.tailrec;

import groovy.lang.Closure;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ExpressionTransformer;
import org.codehaus.groovy.ast.expr.VariableExpression;

public class VariableExpressionTransformer
implements ExpressionTransformer {
    private Closure<Boolean> when;
    private Closure<VariableExpression> replaceWith;

    public VariableExpressionTransformer(Closure<Boolean> when, Closure<VariableExpression> replaceWith) {
        this.when = when;
        this.replaceWith = replaceWith;
    }

    @Override
    public Expression transform(Expression expr) {
        if (expr instanceof VariableExpression && this.when.call((Object)expr).booleanValue()) {
            VariableExpression newExpr = this.replaceWith.call((Object)expr);
            newExpr.setSourcePosition(expr);
            newExpr.copyNodeMetaData(expr);
            return newExpr;
        }
        return expr.transformExpression(this);
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

