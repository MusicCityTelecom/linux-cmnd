/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.sc.transformers;

import groovyjarjarasm.asm.Label;
import groovyjarjarasm.asm.MethodVisitor;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ExpressionTransformer;
import org.codehaus.groovy.classgen.AsmClassGenerator;
import org.codehaus.groovy.classgen.asm.WriterController;
import org.codehaus.groovy.syntax.Token;

public class CompareToNullExpression
extends BinaryExpression {
    private final boolean equalsNull;

    public CompareToNullExpression(Expression expression, boolean equalsNull) {
        super(expression, Token.newSymbol(equalsNull ? "==" : "!=", -1, -1), ConstantExpression.NULL);
        super.setType(ClassHelper.boolean_TYPE);
        this.equalsNull = equalsNull;
    }

    public Expression getObjectExpression() {
        return this.getLeftExpression();
    }

    @Override
    public void setLeftExpression(Expression expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setRightExpression(Expression expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setType(ClassNode type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Expression transformExpression(ExpressionTransformer transformer) {
        CompareToNullExpression ret = new CompareToNullExpression(transformer.transform(this.getObjectExpression()), this.equalsNull);
        ret.setSourcePosition(this);
        ret.copyNodeMetaData(this);
        return ret;
    }

    @Override
    public void visit(GroovyCodeVisitor visitor) {
        if (!(visitor instanceof AsmClassGenerator)) {
            super.visit(visitor);
            return;
        }
        WriterController controller = ((AsmClassGenerator)visitor).getController();
        MethodVisitor mv = controller.getMethodVisitor();
        this.getObjectExpression().visit(visitor);
        if (ClassHelper.isPrimitiveType(controller.getOperandStack().getTopOperand())) {
            controller.getOperandStack().pop();
            mv.visitInsn(this.equalsNull ? 3 : 4);
            controller.getOperandStack().push(ClassHelper.boolean_TYPE);
        } else {
            Label no = new Label();
            Label yes = new Label();
            mv.visitJumpInsn(this.equalsNull ? 199 : 198, no);
            mv.visitInsn(4);
            mv.visitJumpInsn(167, yes);
            mv.visitLabel(no);
            mv.visitInsn(3);
            mv.visitLabel(yes);
            controller.getOperandStack().replace(ClassHelper.boolean_TYPE);
        }
    }
}

