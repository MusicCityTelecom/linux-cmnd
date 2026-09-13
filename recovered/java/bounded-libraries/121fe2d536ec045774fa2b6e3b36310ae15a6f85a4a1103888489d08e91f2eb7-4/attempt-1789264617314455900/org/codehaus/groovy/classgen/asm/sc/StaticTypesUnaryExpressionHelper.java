/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm.sc;

import groovyjarjarasm.asm.Label;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.expr.BitwiseNegationExpression;
import org.codehaus.groovy.ast.expr.EmptyExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.NotExpression;
import org.codehaus.groovy.ast.expr.UnaryMinusExpression;
import org.codehaus.groovy.ast.expr.UnaryPlusExpression;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.classgen.asm.TypeChooser;
import org.codehaus.groovy.classgen.asm.UnaryExpressionHelper;
import org.codehaus.groovy.classgen.asm.WriterController;

public class StaticTypesUnaryExpressionHelper
extends UnaryExpressionHelper {
    private static final BitwiseNegationExpression EMPTY_BITWISE_NEGATE = new BitwiseNegationExpression(EmptyExpression.INSTANCE);
    private static final UnaryMinusExpression EMPTY_UNARY_MINUS = new UnaryMinusExpression(EmptyExpression.INSTANCE);
    private static final UnaryPlusExpression EMPTY_UNARY_PLUS = new UnaryPlusExpression(EmptyExpression.INSTANCE);

    public StaticTypesUnaryExpressionHelper(WriterController controller) {
        super(controller);
    }

    @Override
    public void writeBitwiseNegate(BitwiseNegationExpression expression) {
        expression.getExpression().visit(this.controller.getAcg());
        ClassNode top = this.controller.getOperandStack().getTopOperand();
        if (ClassHelper.isPrimitiveInt(top) || ClassHelper.isPrimitiveLong(top) || ClassHelper.isPrimitiveShort(top) || ClassHelper.isPrimitiveByte(top) || ClassHelper.isPrimitiveChar(top)) {
            GeneralUtils.bytecodeX(mv -> {
                if (ClassHelper.isPrimitiveLong(top)) {
                    mv.visitLdcInsn(-1L);
                    mv.visitInsn(131);
                } else {
                    mv.visitInsn(2);
                    mv.visitInsn(130);
                    if (ClassHelper.isPrimitiveByte(top)) {
                        mv.visitInsn(145);
                    } else if (ClassHelper.isPrimitiveChar(top)) {
                        mv.visitInsn(146);
                    } else if (ClassHelper.isPrimitiveShort(top)) {
                        mv.visitInsn(147);
                    }
                }
            }).visit(this.controller.getAcg());
            this.controller.getOperandStack().remove(1);
        } else {
            super.writeBitwiseNegate(EMPTY_BITWISE_NEGATE);
        }
    }

    @Override
    public void writeNotExpression(NotExpression expression) {
        Expression subExpression = expression.getExpression();
        TypeChooser typeChooser = this.controller.getTypeChooser();
        if (ClassHelper.isPrimitiveBoolean(typeChooser.resolveType(subExpression, this.controller.getClassNode()))) {
            subExpression.visit(this.controller.getAcg());
            this.controller.getOperandStack().doGroovyCast(ClassHelper.boolean_TYPE);
            GeneralUtils.bytecodeX(mv -> {
                Label ne = new Label();
                mv.visitJumpInsn(154, ne);
                mv.visitInsn(4);
                Label out = new Label();
                mv.visitJumpInsn(167, out);
                mv.visitLabel(ne);
                mv.visitInsn(3);
                mv.visitLabel(out);
            }).visit(this.controller.getAcg());
            this.controller.getOperandStack().remove(1);
        } else {
            super.writeNotExpression(expression);
        }
    }

    @Override
    public void writeUnaryMinus(UnaryMinusExpression expression) {
        expression.getExpression().visit(this.controller.getAcg());
        ClassNode top = this.controller.getOperandStack().getTopOperand();
        if (ClassHelper.isPrimitiveInt(top) || ClassHelper.isPrimitiveLong(top) || ClassHelper.isPrimitiveShort(top) || ClassHelper.isPrimitiveFloat(top) || ClassHelper.isPrimitiveDouble(top) || ClassHelper.isPrimitiveByte(top) || ClassHelper.isPrimitiveChar(top)) {
            GeneralUtils.bytecodeX(mv -> {
                if (ClassHelper.isPrimitiveInt(top) || ClassHelper.isPrimitiveShort(top) || ClassHelper.isPrimitiveByte(top) || ClassHelper.isPrimitiveChar(top)) {
                    mv.visitInsn(116);
                    if (ClassHelper.isPrimitiveByte(top)) {
                        mv.visitInsn(145);
                    } else if (ClassHelper.isPrimitiveChar(top)) {
                        mv.visitInsn(146);
                    } else if (ClassHelper.isPrimitiveShort(top)) {
                        mv.visitInsn(147);
                    }
                } else if (ClassHelper.isPrimitiveLong(top)) {
                    mv.visitInsn(117);
                } else if (ClassHelper.isPrimitiveFloat(top)) {
                    mv.visitInsn(118);
                } else if (ClassHelper.isPrimitiveDouble(top)) {
                    mv.visitInsn(119);
                }
            }).visit(this.controller.getAcg());
            this.controller.getOperandStack().remove(1);
        } else {
            super.writeUnaryMinus(EMPTY_UNARY_MINUS);
        }
    }

    @Override
    public void writeUnaryPlus(UnaryPlusExpression expression) {
        expression.getExpression().visit(this.controller.getAcg());
        ClassNode top = this.controller.getOperandStack().getTopOperand();
        if (!(ClassHelper.isPrimitiveInt(top) || ClassHelper.isPrimitiveLong(top) || ClassHelper.isPrimitiveShort(top) || ClassHelper.isPrimitiveFloat(top) || ClassHelper.isPrimitiveDouble(top) || ClassHelper.isPrimitiveByte(top) || ClassHelper.isPrimitiveChar(top))) {
            super.writeUnaryPlus(EMPTY_UNARY_PLUS);
        }
    }
}

