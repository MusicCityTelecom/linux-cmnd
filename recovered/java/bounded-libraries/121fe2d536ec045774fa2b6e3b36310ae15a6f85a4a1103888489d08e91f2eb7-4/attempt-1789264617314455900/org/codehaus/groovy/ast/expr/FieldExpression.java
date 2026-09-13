/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.expr;

import java.util.Objects;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ExpressionTransformer;

public class FieldExpression
extends Expression {
    private final FieldNode field;
    private boolean useRef;

    public FieldExpression(FieldNode field) {
        this.field = Objects.requireNonNull(field);
    }

    @Override
    public void visit(GroovyCodeVisitor visitor) {
        visitor.visitFieldExpression(this);
    }

    @Override
    public Expression transformExpression(ExpressionTransformer transformer) {
        return this;
    }

    public FieldNode getField() {
        return this.field;
    }

    public String getFieldName() {
        return this.field.getName();
    }

    @Override
    public String getText() {
        return "this." + this.getFieldName();
    }

    @Override
    public ClassNode getType() {
        return this.field.getType();
    }

    @Override
    public void setType(ClassNode type) {
        super.setType(type);
        this.field.setType(type);
    }

    public boolean isDynamicTyped() {
        return this.field.isDynamicTyped();
    }

    public boolean isUseReferenceDirectly() {
        return this.useRef;
    }

    public void setUseReferenceDirectly(boolean useRef) {
        this.useRef = useRef;
    }

    public String toString() {
        return "field(" + this.getType() + " " + this.getFieldName() + ")";
    }
}

