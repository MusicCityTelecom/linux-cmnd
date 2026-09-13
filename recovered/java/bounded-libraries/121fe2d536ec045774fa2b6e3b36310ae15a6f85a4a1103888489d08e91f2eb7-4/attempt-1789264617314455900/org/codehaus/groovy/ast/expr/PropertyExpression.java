/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.expr;

import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ExpressionTransformer;

public class PropertyExpression
extends Expression {
    private Expression objectExpression;
    private final Expression property;
    private boolean safe;
    private boolean spreadSafe;
    private boolean isStatic;
    private boolean implicitThis;

    public PropertyExpression(Expression objectExpression, String propertyName) {
        this(objectExpression, new ConstantExpression(propertyName), false);
    }

    public PropertyExpression(Expression objectExpression, Expression property) {
        this(objectExpression, property, false);
    }

    public PropertyExpression(Expression objectExpression, Expression property, boolean safe) {
        this.objectExpression = objectExpression;
        this.property = property;
        this.safe = safe;
    }

    @Override
    public Expression transformExpression(ExpressionTransformer transformer) {
        PropertyExpression ret = new PropertyExpression(transformer.transform(this.getObjectExpression()), transformer.transform(this.getProperty()), this.isSafe());
        ret.setImplicitThis(this.isImplicitThis());
        ret.setSpreadSafe(this.isSpreadSafe());
        ret.setStatic(this.isStatic());
        ret.setType(this.getType());
        ret.setSourcePosition(this);
        ret.copyNodeMetaData(this);
        return ret;
    }

    @Override
    public void visit(GroovyCodeVisitor visitor) {
        visitor.visitPropertyExpression(this);
    }

    public Expression getObjectExpression() {
        return this.objectExpression;
    }

    public void setObjectExpression(Expression objectExpression) {
        this.objectExpression = objectExpression;
    }

    public Expression getProperty() {
        return this.property;
    }

    public String getPropertyAsString() {
        return this.getProperty() instanceof ConstantExpression ? this.getProperty().getText() : null;
    }

    @Override
    public String getText() {
        StringBuilder sb = new StringBuilder(this.getObjectExpression().getText());
        if (this.isSpreadSafe()) {
            sb.append('*');
        }
        if (this.isSafe()) {
            sb.append('?');
        }
        sb.append('.');
        return sb.append(this.getProperty().getText()).toString();
    }

    public boolean isDynamic() {
        return true;
    }

    public boolean isImplicitThis() {
        return this.implicitThis;
    }

    public void setImplicitThis(boolean implicitThis) {
        this.implicitThis = implicitThis;
    }

    public boolean isSafe() {
        return this.safe;
    }

    public boolean isSpreadSafe() {
        return this.spreadSafe;
    }

    public void setSpreadSafe(boolean spreadSafe) {
        this.spreadSafe = spreadSafe;
    }

    public boolean isStatic() {
        return this.isStatic;
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    public String toString() {
        return super.toString() + "[object: " + this.getObjectExpression() + " property: " + this.getProperty() + "]";
    }
}

