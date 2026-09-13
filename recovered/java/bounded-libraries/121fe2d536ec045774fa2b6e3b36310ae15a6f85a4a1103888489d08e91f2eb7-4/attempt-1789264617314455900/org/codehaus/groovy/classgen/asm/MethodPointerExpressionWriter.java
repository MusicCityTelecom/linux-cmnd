/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm;

import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.expr.MethodPointerExpression;
import org.codehaus.groovy.classgen.asm.MethodCaller;
import org.codehaus.groovy.classgen.asm.OperandStack;
import org.codehaus.groovy.classgen.asm.WriterController;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;

public class MethodPointerExpressionWriter {
    private static final MethodCaller getMethodPointer = MethodCaller.newStatic(ScriptBytecodeAdapter.class, "getMethodPointer");
    protected final WriterController controller;

    public MethodPointerExpressionWriter(WriterController controller) {
        this.controller = controller;
    }

    public void writeMethodPointerExpression(MethodPointerExpression pointerOrReference) {
        pointerOrReference.getExpression().visit(this.controller.getAcg());
        OperandStack operandStack = this.controller.getOperandStack();
        operandStack.box();
        operandStack.pushDynamicName(pointerOrReference.getMethodName());
        getMethodPointer.call(this.controller.getMethodVisitor());
        operandStack.replace(ClassHelper.CLOSURE_TYPE, 2);
    }
}

