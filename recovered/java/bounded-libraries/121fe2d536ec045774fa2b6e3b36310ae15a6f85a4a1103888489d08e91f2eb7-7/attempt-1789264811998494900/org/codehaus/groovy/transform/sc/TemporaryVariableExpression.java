/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.sc;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ExpressionTransformer;
import org.codehaus.groovy.classgen.AsmClassGenerator;
import org.codehaus.groovy.classgen.asm.ExpressionAsVariableSlot;
import org.codehaus.groovy.classgen.asm.WriterController;
import org.codehaus.groovy.transform.stc.StaticTypesMarker;

public class TemporaryVariableExpression
extends Expression {
    private final Expression expression;
    private ExpressionAsVariableSlot[] variable = new ExpressionAsVariableSlot[]{null};

    public TemporaryVariableExpression(Expression expression) {
        this.expression = expression;
        this.putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, expression.getNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE));
    }

    @Override
    public ClassNode getType() {
        return this.expression.getType();
    }

    public void remove(WriterController controller) {
        controller.getCompileStack().removeVar(this.variable[0].getIndex());
        this.variable[0] = null;
    }

    @Override
    public Expression transformExpression(ExpressionTransformer transformer) {
        TemporaryVariableExpression result = new TemporaryVariableExpression(transformer.transform(this.expression));
        result.copyNodeMetaData(this);
        result.variable = this.variable;
        return result;
    }

    @Override
    public void visit(GroovyCodeVisitor visitor) {
        if (visitor instanceof AsmClassGenerator) {
            if (this.variable[0] == null) {
                WriterController controller = ((AsmClassGenerator)visitor).getController();
                this.variable[0] = new ExpressionAsVariableSlot(controller, this.expression);
            }
            this.variable[0].visit(visitor);
        } else {
            this.expression.visit(visitor);
        }
    }
}

