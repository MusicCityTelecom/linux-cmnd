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
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ExpressionTransformer;
import org.codehaus.groovy.classgen.AsmClassGenerator;
import org.codehaus.groovy.classgen.asm.WriterController;
import org.codehaus.groovy.syntax.Token;

public class CompareIdentityExpression
extends BinaryExpression {
    public CompareIdentityExpression(Expression leftExpression, boolean eq, Expression rightExpression) {
        super(leftExpression, Token.newSymbol(eq ? "===" : "!==", -1, -1), rightExpression);
        super.setType(ClassHelper.boolean_TYPE);
    }

    public CompareIdentityExpression(Expression leftExpression, Expression rightExpression) {
        this(leftExpression, true, rightExpression);
    }

    public boolean isEq() {
        return this.getOperation().getText().charAt(0) == '=';
    }

    @Override
    public void setType(ClassNode type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Expression transformExpression(ExpressionTransformer transformer) {
        CompareIdentityExpression ret = new CompareIdentityExpression(transformer.transform(this.getLeftExpression()), this.isEq(), transformer.transform(this.getRightExpression()));
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
        Label no = new Label();
        Label yes = new Label();
        this.getLeftExpression().visit(visitor);
        controller.getOperandStack().box();
        this.getRightExpression().visit(visitor);
        controller.getOperandStack().box();
        mv.visitJumpInsn(this.isEq() ? 166 : 165, no);
        mv.visitInsn(4);
        mv.visitJumpInsn(167, yes);
        mv.visitLabel(no);
        mv.visitInsn(3);
        mv.visitLabel(yes);
        controller.getOperandStack().replace(ClassHelper.boolean_TYPE, 2);
    }
}

