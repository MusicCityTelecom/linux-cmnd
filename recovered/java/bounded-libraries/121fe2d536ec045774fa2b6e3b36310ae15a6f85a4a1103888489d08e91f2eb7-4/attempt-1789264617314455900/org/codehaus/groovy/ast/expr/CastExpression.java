/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.expr;

import java.util.Objects;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ExpressionTransformer;

public class CastExpression
extends Expression {
    private Expression expression;
    private final boolean ignoreAutoboxing;
    private boolean coerce;
    private boolean strict;

    public static CastExpression asExpression(ClassNode type, Expression expression) {
        CastExpression answer = new CastExpression(type, expression);
        answer.setCoerce(true);
        return answer;
    }

    public CastExpression(ClassNode type, Expression expression) {
        this(type, expression, false);
    }

    public CastExpression(ClassNode type, Expression expression, boolean ignoreAutoboxing) {
        this.expression = expression;
        this.ignoreAutoboxing = ignoreAutoboxing;
        super.setType(Objects.requireNonNull(type));
    }

    public Expression getExpression() {
        return this.expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public boolean isIgnoringAutoboxing() {
        return this.ignoreAutoboxing;
    }

    public boolean isCoerce() {
        return this.coerce;
    }

    public void setCoerce(boolean coerce) {
        this.coerce = coerce;
    }

    public boolean isStrict() {
        return this.strict;
    }

    public void setStrict(boolean strict) {
        this.strict = strict;
    }

    public String toString() {
        return super.toString() + "[(" + this.getType().getName() + ") " + this.expression + "]";
    }

    @Override
    public void visit(GroovyCodeVisitor visitor) {
        visitor.visitCastExpression(this);
    }

    @Override
    public Expression transformExpression(ExpressionTransformer transformer) {
        CastExpression ret = new CastExpression(this.getType(), transformer.transform(this.expression), this.isIgnoringAutoboxing());
        ret.setCoerce(this.isCoerce());
        ret.setStrict(this.isStrict());
        ret.setSourcePosition(this);
        ret.copyNodeMetaData(this);
        return ret;
    }

    @Override
    public String getText() {
        return "(" + this.getType().toString(false) + ") " + this.expression.getText();
    }

    @Override
    public void setType(ClassNode type) {
        throw new UnsupportedOperationException();
    }
}

