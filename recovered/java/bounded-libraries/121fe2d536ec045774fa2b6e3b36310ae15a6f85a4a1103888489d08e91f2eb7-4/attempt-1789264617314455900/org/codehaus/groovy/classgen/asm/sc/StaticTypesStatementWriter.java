/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm.sc;

import groovyjarjarasm.asm.Label;
import groovyjarjarasm.asm.MethodVisitor;
import java.util.Enumeration;
import java.util.Objects;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ForStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.classgen.AsmClassGenerator;
import org.codehaus.groovy.classgen.asm.BytecodeVariable;
import org.codehaus.groovy.classgen.asm.CompileStack;
import org.codehaus.groovy.classgen.asm.MethodCaller;
import org.codehaus.groovy.classgen.asm.OperandStack;
import org.codehaus.groovy.classgen.asm.StatementWriter;
import org.codehaus.groovy.classgen.asm.sc.StaticTypesWriterController;

public class StaticTypesStatementWriter
extends StatementWriter {
    private static final ClassNode ENUMERATION_CLASSNODE = ClassHelper.make(Enumeration.class);
    private static final MethodCaller ENUMERATION_NEXT_METHOD = MethodCaller.newInterface(Enumeration.class, "nextElement");
    private static final MethodCaller ENUMERATION_HASMORE_METHOD = MethodCaller.newInterface(Enumeration.class, "hasMoreElements");

    public StaticTypesStatementWriter(StaticTypesWriterController controller) {
        super(controller);
    }

    @Override
    public void writeBlockStatement(BlockStatement statement) {
        this.controller.switchToFastPath();
        super.writeBlockStatement(statement);
        this.controller.switchToSlowPath();
    }

    @Override
    protected void writeForInLoop(ForStatement loop) {
        this.controller.getAcg().onLineNumber(loop, "visitForLoop");
        this.writeStatementLabel(loop);
        CompileStack compileStack = this.controller.getCompileStack();
        OperandStack operandStack = this.controller.getOperandStack();
        compileStack.pushLoop(loop.getVariableScope(), loop.getStatementLabels());
        Expression collectionExpression = loop.getCollectionExpression();
        ClassNode collectionType = this.controller.getTypeChooser().resolveType(collectionExpression, this.controller.getClassNode());
        int mark = operandStack.getStackLength();
        Parameter loopVariable = loop.getVariable();
        if (collectionType.isArray() && loopVariable.getType().equals(collectionType.getComponentType())) {
            this.writeOptimizedForEachLoop(loop, loopVariable, collectionExpression, collectionType);
        } else if (GeneralUtils.isOrImplements(collectionType, ENUMERATION_CLASSNODE)) {
            this.writeEnumerationBasedForEachLoop(loop, collectionExpression, collectionType);
        } else {
            this.writeIteratorBasedForEachLoop(loop, collectionExpression, collectionType);
        }
        operandStack.popDownTo(mark);
        compileStack.pop();
    }

    private void writeOptimizedForEachLoop(ForStatement loop, Parameter loopVariable, Expression arrayExpression, ClassNode arrayType) {
        CompileStack compileStack = this.controller.getCompileStack();
        OperandStack operandStack = this.controller.getOperandStack();
        MethodVisitor mv = this.controller.getMethodVisitor();
        AsmClassGenerator acg = this.controller.getAcg();
        BytecodeVariable variable = compileStack.defineVariable(loopVariable, arrayType.getComponentType(), false);
        Label continueLabel = compileStack.getContinueLabel();
        Label breakLabel = compileStack.getBreakLabel();
        arrayExpression.visit(acg);
        mv.visitInsn(89);
        int array = compileStack.defineTemporaryVariable("$arr", arrayType, true);
        mv.visitJumpInsn(198, breakLabel);
        mv.visitVarInsn(25, array);
        mv.visitInsn(190);
        operandStack.push(ClassHelper.int_TYPE);
        int arrayLen = compileStack.defineTemporaryVariable("$len", ClassHelper.int_TYPE, true);
        mv.visitInsn(3);
        operandStack.push(ClassHelper.int_TYPE);
        int loopIdx = compileStack.defineTemporaryVariable("$idx", ClassHelper.int_TYPE, true);
        mv.visitLabel(continueLabel);
        mv.visitVarInsn(21, loopIdx);
        mv.visitVarInsn(21, arrayLen);
        mv.visitJumpInsn(162, breakLabel);
        StaticTypesStatementWriter.loadFromArray(mv, operandStack, variable, array, loopIdx);
        mv.visitIincInsn(loopIdx, 1);
        loop.getLoopBlock().visit(acg);
        mv.visitJumpInsn(167, continueLabel);
        mv.visitLabel(breakLabel);
        compileStack.removeVar(loopIdx);
        compileStack.removeVar(arrayLen);
        compileStack.removeVar(array);
    }

    private static void loadFromArray(MethodVisitor mv, OperandStack os, BytecodeVariable variable, int array, int index) {
        mv.visitVarInsn(25, array);
        mv.visitVarInsn(21, index);
        ClassNode varType = variable.getType();
        if (ClassHelper.isPrimitiveType(varType)) {
            if (ClassHelper.isPrimitiveInt(varType)) {
                mv.visitInsn(46);
            } else if (ClassHelper.isPrimitiveLong(varType)) {
                mv.visitInsn(47);
            } else if (ClassHelper.isPrimitiveByte(varType) || ClassHelper.isPrimitiveBoolean(varType)) {
                mv.visitInsn(51);
            } else if (ClassHelper.isPrimitiveChar(varType)) {
                mv.visitInsn(52);
            } else if (ClassHelper.isPrimitiveShort(varType)) {
                mv.visitInsn(53);
            } else if (ClassHelper.isPrimitiveFloat(varType)) {
                mv.visitInsn(48);
            } else if (ClassHelper.isPrimitiveDouble(varType)) {
                mv.visitInsn(49);
            }
        } else {
            mv.visitInsn(50);
        }
        os.push(varType);
        os.storeVar(variable);
    }

    private void writeEnumerationBasedForEachLoop(ForStatement loop, Expression collectionExpression, ClassNode collectionType) {
        CompileStack compileStack = this.controller.getCompileStack();
        OperandStack operandStack = this.controller.getOperandStack();
        MethodVisitor mv = this.controller.getMethodVisitor();
        BytecodeVariable variable = compileStack.defineVariable(loop.getVariable(), false);
        Label continueLabel = compileStack.getContinueLabel();
        Label breakLabel = compileStack.getBreakLabel();
        collectionExpression.visit(this.controller.getAcg());
        int enumeration = compileStack.defineTemporaryVariable("$enum", ENUMERATION_CLASSNODE, true);
        mv.visitVarInsn(25, enumeration);
        mv.visitJumpInsn(198, breakLabel);
        mv.visitLabel(continueLabel);
        mv.visitVarInsn(25, enumeration);
        ENUMERATION_HASMORE_METHOD.call(mv);
        mv.visitJumpInsn(153, breakLabel);
        mv.visitVarInsn(25, enumeration);
        ENUMERATION_NEXT_METHOD.call(mv);
        operandStack.push(ClassHelper.OBJECT_TYPE);
        operandStack.storeVar(variable);
        loop.getLoopBlock().visit(this.controller.getAcg());
        mv.visitJumpInsn(167, continueLabel);
        mv.visitLabel(breakLabel);
    }

    private void writeIteratorBasedForEachLoop(ForStatement loop, Expression collectionExpression, ClassNode collectionType) {
        MethodNode iterator = collectionType.getMethod("iterator", Parameter.EMPTY_ARRAY);
        if (iterator == null) {
            iterator = GeneralUtils.getInterfacesAndSuperInterfaces(collectionType).stream().map(in -> in.getMethod("iterator", Parameter.EMPTY_ARRAY)).filter(Objects::nonNull).findFirst().orElse(null);
        }
        if (iterator != null && iterator.getReturnType().equals(ClassHelper.Iterator_TYPE)) {
            MethodCallExpression call = GeneralUtils.callX(collectionExpression, "iterator");
            call.setImplicitThis(false);
            call.setMethodTarget(iterator);
            call.setSafe(true);
            call.visit(this.controller.getAcg());
        } else {
            collectionExpression.visit(this.controller.getAcg());
            this.controller.getMethodVisitor().visitMethodInsn(184, "org/codehaus/groovy/runtime/DefaultGroovyMethods", "iterator", "(Ljava/lang/Object;)Ljava/util/Iterator;", false);
            this.controller.getOperandStack().replace(ClassHelper.Iterator_TYPE);
        }
        this.writeForInLoopControlAndBlock(loop);
    }
}

