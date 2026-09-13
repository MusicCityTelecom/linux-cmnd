/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.expr;

import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ExpressionTransformer;

public class RangeExpression
extends Expression {
    private final Expression from;
    private final Expression to;
    private final boolean exclusiveLeft;
    private final boolean exclusiveRight;

    public RangeExpression(Expression from, Expression to, boolean inclusive) {
        this(from, to, false, !inclusive);
    }

    public RangeExpression(Expression from, Expression to, boolean exclusiveLeft, boolean exclusiveRight) {
        this.from = from;
        this.to = to;
        this.exclusiveLeft = exclusiveLeft;
        this.exclusiveRight = exclusiveRight;
        this.setType(ClassHelper.RANGE_TYPE.getPlainNodeReference());
    }

    @Override
    public void visit(GroovyCodeVisitor visitor) {
        visitor.visitRangeExpression(this);
    }

    @Override
    public Expression transformExpression(ExpressionTransformer transformer) {
        RangeExpression ret = new RangeExpression(transformer.transform(this.getFrom()), transformer.transform(this.getTo()), this.isExclusiveLeft(), this.isExclusiveRight());
        ret.setSourcePosition(this);
        ret.copyNodeMetaData(this);
        return ret;
    }

    public Expression getFrom() {
        return this.from;
    }

    public Expression getTo() {
        return this.to;
    }

    public boolean isInclusive() {
        return !this.isExclusiveRight();
    }

    public boolean isExclusiveLeft() {
        return this.exclusiveLeft;
    }

    public boolean isExclusiveRight() {
        return this.exclusiveRight;
    }

    @Override
    public String getText() {
        return "(" + this.getFrom().getText() + (this.isExclusiveLeft() ? "<" : "") + ".." + (this.isExclusiveRight() ? "<" : "") + this.getTo().getText() + ")";
    }
}

