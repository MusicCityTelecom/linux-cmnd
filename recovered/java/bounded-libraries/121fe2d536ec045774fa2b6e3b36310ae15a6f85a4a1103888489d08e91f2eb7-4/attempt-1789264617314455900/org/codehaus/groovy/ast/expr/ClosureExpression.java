/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.expr;

import org.codehaus.groovy.ast.AstToTextHelper;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.VariableScope;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ExpressionTransformer;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.ClosureUtils;

public class ClosureExpression
extends Expression {
    private final Parameter[] parameters;
    private Statement code;
    private VariableScope variableScope;

    public ClosureExpression(Parameter[] parameters, Statement code) {
        this.parameters = parameters;
        this.code = code;
        this.setType(ClassHelper.CLOSURE_TYPE.getPlainNodeReference());
    }

    public Statement getCode() {
        return this.code;
    }

    public void setCode(Statement code) {
        this.code = code;
    }

    public Parameter[] getParameters() {
        return this.parameters;
    }

    public boolean isParameterSpecified() {
        return this.parameters != null && this.parameters.length > 0;
    }

    public VariableScope getVariableScope() {
        return this.variableScope;
    }

    public void setVariableScope(VariableScope variableScope) {
        this.variableScope = variableScope;
    }

    @Override
    public String getText() {
        return this.toString("...");
    }

    public String toString() {
        return super.toString() + this.toString(this.code == null ? "<null>" : this.code.toString());
    }

    private String toString(String bodyText) {
        if (ClosureUtils.hasImplicitParameter(this)) {
            return "{ " + bodyText + " }";
        }
        return "{ " + AstToTextHelper.getParametersText(this.parameters) + " -> " + bodyText + " }";
    }

    @Override
    public Expression transformExpression(ExpressionTransformer transformer) {
        return this;
    }

    @Override
    public void visit(GroovyCodeVisitor visitor) {
        visitor.visitClosureExpression(this);
    }
}

