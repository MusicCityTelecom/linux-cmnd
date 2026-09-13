/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.expr;

import java.util.Map;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ExpressionTransformer;

public class EmptyExpression
extends Expression {
    public static final EmptyExpression INSTANCE = new EmptyExpression(){

        private void throwUnsupportedOperationException() {
            throw new UnsupportedOperationException("EmptyExpression.INSTANCE is immutable");
        }

        @Override
        public void setColumnNumber(int n) {
            this.throwUnsupportedOperationException();
        }

        @Override
        public void setLastColumnNumber(int n) {
            this.throwUnsupportedOperationException();
        }

        @Override
        public void setLastLineNumber(int n) {
            this.throwUnsupportedOperationException();
        }

        @Override
        public void setLineNumber(int n) {
            this.throwUnsupportedOperationException();
        }

        @Override
        public void setMetaDataMap(Map<?, ?> meta) {
            this.throwUnsupportedOperationException();
        }

        @Override
        public void setSourcePosition(ASTNode node) {
            this.throwUnsupportedOperationException();
        }

        @Override
        public void addAnnotation(AnnotationNode node) {
            this.throwUnsupportedOperationException();
        }

        @Override
        public void setDeclaringClass(ClassNode node) {
            this.throwUnsupportedOperationException();
        }

        @Override
        public void setHasNoRealSourcePosition(boolean b) {
            this.throwUnsupportedOperationException();
        }

        @Override
        public void setSynthetic(boolean b) {
            this.throwUnsupportedOperationException();
        }

        @Override
        public void setType(ClassNode node) {
            this.throwUnsupportedOperationException();
        }
    };

    @Override
    public Expression transformExpression(ExpressionTransformer transformer) {
        return this;
    }

    @Override
    public void visit(GroovyCodeVisitor visitor) {
        visitor.visitEmptyExpression(this);
    }
}

