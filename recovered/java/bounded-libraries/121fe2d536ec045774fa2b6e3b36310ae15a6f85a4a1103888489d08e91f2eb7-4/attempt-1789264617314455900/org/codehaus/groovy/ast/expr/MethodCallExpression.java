/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.expr;

import java.util.Collections;
import java.util.List;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ExpressionTransformer;
import org.codehaus.groovy.ast.expr.MethodCall;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;

public class MethodCallExpression
extends Expression
implements MethodCall {
    private Expression objectExpression;
    private Expression method;
    private Expression arguments;
    private boolean implicitThis = true;
    private boolean spreadSafe;
    private boolean safe;
    private GenericsType[] genericsTypes;
    private boolean usesGenerics;
    private MethodNode target;
    public static final Expression NO_ARGUMENTS = new TupleExpression(){

        @Override
        public List<Expression> getExpressions() {
            return Collections.unmodifiableList(super.getExpressions());
        }

        @Override
        public TupleExpression addExpression(Expression e) {
            throw new UnsupportedOperationException();
        }
    };

    public MethodCallExpression(Expression objectExpression, String method, Expression arguments) {
        this(objectExpression, new ConstantExpression(method), arguments);
    }

    public MethodCallExpression(Expression objectExpression, Expression method, Expression arguments) {
        this.setMethod(method);
        this.setArguments(arguments);
        this.setObjectExpression(objectExpression);
    }

    @Override
    public void visit(GroovyCodeVisitor visitor) {
        visitor.visitMethodCallExpression(this);
    }

    @Override
    public Expression transformExpression(ExpressionTransformer transformer) {
        MethodCallExpression answer = new MethodCallExpression(transformer.transform(this.objectExpression), transformer.transform(this.method), transformer.transform(this.arguments));
        answer.setSafe(this.safe);
        answer.setSpreadSafe(this.spreadSafe);
        answer.setImplicitThis(this.implicitThis);
        answer.setGenericsTypes(this.genericsTypes);
        answer.setSourcePosition(this);
        answer.setMethodTarget(this.target);
        answer.copyNodeMetaData(this);
        return answer;
    }

    @Override
    public Expression getArguments() {
        return this.arguments;
    }

    public void setArguments(Expression arguments) {
        if (!(arguments instanceof TupleExpression)) {
            this.arguments = new TupleExpression(arguments);
            this.arguments.setSourcePosition(arguments);
        } else {
            this.arguments = arguments;
        }
    }

    public Expression getMethod() {
        return this.method;
    }

    public void setMethod(Expression method) {
        this.method = method;
    }

    @Override
    public String getMethodAsString() {
        return this.method instanceof ConstantExpression ? this.method.getText() : null;
    }

    public Expression getObjectExpression() {
        return this.objectExpression;
    }

    public void setObjectExpression(Expression objectExpression) {
        this.objectExpression = objectExpression;
    }

    @Override
    public ASTNode getReceiver() {
        return this.getObjectExpression();
    }

    @Override
    public String getText() {
        String object = this.objectExpression.getText();
        String meth = this.method.getText();
        String args = this.arguments.getText();
        String spread = this.spreadSafe ? "*" : "";
        String dereference = this.safe ? "?" : "";
        return object + spread + dereference + "." + meth + args;
    }

    public boolean isSafe() {
        return this.safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }

    public boolean isSpreadSafe() {
        return this.spreadSafe;
    }

    public void setSpreadSafe(boolean value) {
        this.spreadSafe = value;
    }

    public boolean isImplicitThis() {
        return this.implicitThis;
    }

    public void setImplicitThis(boolean implicitThis) {
        this.implicitThis = implicitThis;
    }

    public GenericsType[] getGenericsTypes() {
        return this.genericsTypes;
    }

    public void setGenericsTypes(GenericsType[] genericsTypes) {
        this.usesGenerics = this.usesGenerics || genericsTypes != null;
        this.genericsTypes = genericsTypes;
    }

    public boolean isUsingGenerics() {
        return this.usesGenerics;
    }

    public MethodNode getMethodTarget() {
        return this.target;
    }

    public void setMethodTarget(MethodNode mn) {
        this.target = mn;
        if (mn != null) {
            this.setType(this.target.getReturnType());
        } else {
            this.setType(ClassHelper.OBJECT_TYPE);
        }
    }

    @Override
    public void setSourcePosition(ASTNode node) {
        super.setSourcePosition(node);
        if (node instanceof MethodCall) {
            if (node instanceof MethodCallExpression) {
                this.method.setSourcePosition(((MethodCallExpression)node).getMethod());
            } else if (node.getLineNumber() > 0) {
                this.method.setLineNumber(node.getLineNumber());
                this.method.setColumnNumber(node.getColumnNumber());
                this.method.setLastLineNumber(node.getLineNumber());
                this.method.setLastColumnNumber(node.getColumnNumber() + this.getMethodAsString().length());
            }
            if (this.arguments != null) {
                this.arguments.setSourcePosition(((MethodCall)((Object)node)).getArguments());
            }
        } else if (node instanceof PropertyExpression) {
            this.method.setSourcePosition(((PropertyExpression)node).getProperty());
        }
    }

    public String toString() {
        return super.toString() + "[object: " + this.objectExpression + " method: " + this.method + " arguments: " + this.arguments + "]";
    }
}

