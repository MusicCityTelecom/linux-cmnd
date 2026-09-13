/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen;

import groovyjarjarasm.asm.MethodVisitor;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ExpressionTransformer;

public abstract class BytecodeExpression
extends Expression {
    public static final BytecodeExpression NOP = new BytecodeExpression(){

        @Override
        public void visit(MethodVisitor visitor) {
        }
    };

    public BytecodeExpression() {
    }

    public BytecodeExpression(ClassNode type) {
        this.setType(type);
    }

    public abstract void visit(MethodVisitor var1);

    @Override
    public void visit(GroovyCodeVisitor visitor) {
        visitor.visitBytecodeExpression(this);
    }

    @Override
    public Expression transformExpression(ExpressionTransformer transformer) {
        return this;
    }
}

