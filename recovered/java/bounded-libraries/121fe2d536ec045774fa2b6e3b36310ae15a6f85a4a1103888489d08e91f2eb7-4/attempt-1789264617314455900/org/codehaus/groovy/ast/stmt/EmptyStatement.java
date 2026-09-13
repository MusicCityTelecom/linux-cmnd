/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.stmt;

import java.util.Map;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.stmt.Statement;

public class EmptyStatement
extends Statement {
    public static final EmptyStatement INSTANCE = new EmptyStatement(){

        private void throwUnsupportedOperationException() {
            throw new UnsupportedOperationException("EmptyStatement.INSTANCE is immutable");
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
        public void addStatementLabel(String label) {
            this.throwUnsupportedOperationException();
        }

        @Override
        public void setStatementLabel(String label) {
            this.throwUnsupportedOperationException();
        }
    };

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public void visit(GroovyCodeVisitor visitor) {
        visitor.visitEmptyStatement(this);
    }
}

