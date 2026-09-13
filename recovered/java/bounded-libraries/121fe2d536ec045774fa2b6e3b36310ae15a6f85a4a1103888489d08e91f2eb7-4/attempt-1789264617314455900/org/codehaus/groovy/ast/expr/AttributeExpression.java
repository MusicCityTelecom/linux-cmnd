/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.expr;

import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ExpressionTransformer;
import org.codehaus.groovy.ast.expr.PropertyExpression;

public class AttributeExpression
extends PropertyExpression {
    public AttributeExpression(Expression objectExpression, Expression property) {
        super(objectExpression, property, false);
    }

    public AttributeExpression(Expression objectExpression, Expression property, boolean safe) {
        super(objectExpression, property, safe);
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
        sb.append(".@");
        return sb.append(this.getProperty().getText()).toString();
    }

    @Override
    public Expression transformExpression(ExpressionTransformer transformer) {
        AttributeExpression ret = new AttributeExpression(transformer.transform(this.getObjectExpression()), transformer.transform(this.getProperty()), this.isSafe());
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
        visitor.visitAttributeExpression(this);
    }
}

