/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.expr;

import org.codehaus.groovy.ast.AstToTextHelper;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.stmt.Statement;

public class LambdaExpression
extends ClosureExpression {
    private boolean serializable;

    public LambdaExpression(Parameter[] parameters, Statement code) {
        super(parameters, code);
    }

    @Override
    public void visit(GroovyCodeVisitor visitor) {
        visitor.visitLambdaExpression(this);
    }

    @Override
    public String getText() {
        String paramText = AstToTextHelper.getParametersText(this.getParameters());
        if (paramText.length() > 0) {
            return "(" + paramText + ") -> { ... }";
        }
        return "() -> { ... }";
    }

    public boolean isSerializable() {
        return this.serializable;
    }

    public void setSerializable(boolean serializable) {
        this.serializable = serializable;
    }
}

