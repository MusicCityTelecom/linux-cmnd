/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm;

import groovyjarjarasm.asm.Label;
import groovyjarjarasm.asm.MethodVisitor;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.classgen.asm.BinaryExpressionWriter;
import org.codehaus.groovy.classgen.asm.MethodCaller;
import org.codehaus.groovy.classgen.asm.OperandStack;
import org.codehaus.groovy.classgen.asm.WriterController;
import org.codehaus.groovy.runtime.BytecodeInterface8;

public class BinaryIntExpressionHelper
extends BinaryExpressionWriter {
    private static final MethodCaller intArrayGet = MethodCaller.newStatic(BytecodeInterface8.class, "intArrayGet");
    private static final MethodCaller intArraySet = MethodCaller.newStatic(BytecodeInterface8.class, "intArraySet");
    private static final int[] stdCompareCodes = new int[]{159, 160, 159, 160, 162, 163, 164, 161};
    private static final int[] stdOperations = new int[]{96, 100, 104, 108, 108, 112};
    private static final int[] bitOp = new int[]{128, 126, 130};
    private static final int[] shiftOp = new int[]{120, 122, 124};

    public BinaryIntExpressionHelper(WriterController wc) {
        this(wc, intArraySet, intArrayGet);
    }

    public BinaryIntExpressionHelper(WriterController wc, MethodCaller arraySet, MethodCaller arrayGet) {
        super(wc, arraySet, arrayGet);
    }

    @Override
    protected boolean writeStdCompare(int type, boolean simulate) {
        if ((type -= 120) < 0 || type > 7) {
            return false;
        }
        if (!simulate) {
            MethodVisitor mv = this.getController().getMethodVisitor();
            OperandStack operandStack = this.getController().getOperandStack();
            int bytecode = stdCompareCodes[type];
            Label l1 = new Label();
            mv.visitJumpInsn(bytecode, l1);
            mv.visitInsn(4);
            Label l2 = new Label();
            mv.visitJumpInsn(167, l2);
            mv.visitLabel(l1);
            mv.visitInsn(3);
            mv.visitLabel(l2);
            operandStack.replace(ClassHelper.boolean_TYPE, 2);
        }
        return true;
    }

    @Override
    protected boolean writeSpaceship(int type, boolean simulate) {
        if (type != 128) {
            return false;
        }
        if (!simulate) {
            MethodVisitor mv = this.getController().getMethodVisitor();
            mv.visitInsn(92);
            Label l1 = new Label();
            mv.visitJumpInsn(162, l1);
            mv.visitInsn(88);
            mv.visitInsn(2);
            Label l2 = new Label();
            mv.visitJumpInsn(167, l2);
            mv.visitLabel(l1);
            Label l3 = new Label();
            mv.visitJumpInsn(160, l3);
            mv.visitInsn(3);
            mv.visitJumpInsn(167, l2);
            mv.visitLabel(l3);
            mv.visitInsn(4);
            this.getController().getOperandStack().replace(ClassHelper.int_TYPE, 2);
        }
        return true;
    }

    @Override
    protected void doubleTwoOperands(MethodVisitor mv) {
        mv.visitInsn(92);
    }

    @Override
    protected int getBitwiseOperationBytecode(int type) {
        return bitOp[type];
    }

    @Override
    protected int getCompareCode() {
        return -1;
    }

    @Override
    protected ClassNode getNormalOpResultType() {
        return ClassHelper.int_TYPE;
    }

    @Override
    protected int getShiftOperationBytecode(int type) {
        return shiftOp[type];
    }

    @Override
    protected int getStandardOperationBytecode(int type) {
        return stdOperations[type];
    }

    @Override
    protected void removeTwoOperands(MethodVisitor mv) {
        mv.visitInsn(88);
    }

    @Override
    protected void writeMinusMinus(MethodVisitor mv) {
        mv.visitInsn(4);
        mv.visitInsn(100);
    }

    @Override
    protected void writePlusPlus(MethodVisitor mv) {
        mv.visitInsn(4);
        mv.visitInsn(96);
    }

    @Override
    protected ClassNode getDevisionOpResultType() {
        return ClassHelper.int_TYPE;
    }

    @Override
    protected boolean supportsDivision() {
        return true;
    }
}

