/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.expr;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ExpressionTransformer;

public class ArrayExpression
extends Expression {
    private final List<Expression> initExpressions;
    private final List<Expression> sizeExpressions;
    private final ClassNode elementType;

    private static ClassNode makeArray(ClassNode base, List<Expression> sizeExpressions) {
        ClassNode ret = base.makeArray();
        if (sizeExpressions == null) {
            return ret;
        }
        int size = sizeExpressions.size();
        for (int i = 1; i < size; ++i) {
            ret = ret.makeArray();
        }
        return ret;
    }

    public ArrayExpression(ClassNode elementType, List<Expression> initExpressions, List<Expression> sizeExpressions) {
        super.setType(ArrayExpression.makeArray(elementType, sizeExpressions));
        this.elementType = elementType;
        this.sizeExpressions = sizeExpressions;
        List<Object> list = this.initExpressions = initExpressions == null ? Collections.emptyList() : initExpressions;
        if (initExpressions == null && (sizeExpressions == null || sizeExpressions.isEmpty())) {
            throw new IllegalArgumentException("Either an initializer or defined size must be given");
        }
        if (!this.initExpressions.isEmpty() && sizeExpressions != null && !sizeExpressions.isEmpty()) {
            throw new IllegalArgumentException("Both an initializer (" + this.formatInitExpressions() + ") and a defined size (" + this.formatSizeExpressions() + ") cannot be given");
        }
        for (Expression item : this.initExpressions) {
            if (item == null || item instanceof Expression) continue;
            throw new ClassCastException("Item: " + item + " is not an Expression");
        }
        if (!this.hasInitializer()) {
            for (Expression item : sizeExpressions) {
                if (item instanceof Expression) continue;
                throw new ClassCastException("Item: " + item + " is not an Expression");
            }
        }
    }

    public ArrayExpression(ClassNode elementType, List<Expression> initExpressions) {
        this(elementType, initExpressions, null);
    }

    public void addExpression(Expression initExpression) {
        this.initExpressions.add(initExpression);
    }

    public List<Expression> getExpressions() {
        return this.initExpressions;
    }

    @Override
    public void visit(GroovyCodeVisitor visitor) {
        visitor.visitArrayExpression(this);
    }

    public boolean isDynamic() {
        return false;
    }

    @Override
    public Expression transformExpression(ExpressionTransformer transformer) {
        List<Expression> exprList = this.transformExpressions(this.initExpressions, transformer);
        List<Expression> sizes = null;
        if (!this.hasInitializer()) {
            sizes = this.transformExpressions(this.sizeExpressions, transformer);
        }
        ArrayExpression ret = new ArrayExpression(this.elementType, exprList, sizes);
        ret.setSourcePosition(this);
        ret.copyNodeMetaData(this);
        return ret;
    }

    public Expression getExpression(int i) {
        return this.initExpressions.get(i);
    }

    public ClassNode getElementType() {
        return this.elementType;
    }

    @Override
    public String getText() {
        return "[" + this.formatInitExpressions() + "]";
    }

    private String formatInitExpressions() {
        return "{" + this.initExpressions.stream().map(ASTNode::getText).collect(Collectors.joining(", ")) + "}";
    }

    private String formatSizeExpressions() {
        return this.sizeExpressions.stream().map(e -> "[" + e.getText() + "]").collect(Collectors.joining());
    }

    public boolean hasInitializer() {
        return this.sizeExpressions == null;
    }

    public List<Expression> getSizeExpression() {
        return this.sizeExpressions;
    }

    public String toString() {
        if (this.hasInitializer()) {
            return super.toString() + "[elementType: " + this.getElementType() + ", init: {" + this.formatInitExpressions() + "}]";
        }
        return super.toString() + "[elementType: " + this.getElementType() + ", size: " + this.formatSizeExpressions() + "]";
    }
}

