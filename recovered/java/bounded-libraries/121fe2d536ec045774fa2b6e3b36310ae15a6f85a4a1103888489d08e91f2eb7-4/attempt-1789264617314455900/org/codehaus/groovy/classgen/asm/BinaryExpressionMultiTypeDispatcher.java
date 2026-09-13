/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm;

import java.util.Map;
import org.apache.groovy.util.Maps;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.DynamicVariable;
import org.codehaus.groovy.ast.Variable;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.tools.WideningCategories;
import org.codehaus.groovy.classgen.AsmClassGenerator;
import org.codehaus.groovy.classgen.asm.BinaryBooleanExpressionHelper;
import org.codehaus.groovy.classgen.asm.BinaryDoubleExpressionHelper;
import org.codehaus.groovy.classgen.asm.BinaryExpressionHelper;
import org.codehaus.groovy.classgen.asm.BinaryExpressionWriter;
import org.codehaus.groovy.classgen.asm.BinaryFloatExpressionHelper;
import org.codehaus.groovy.classgen.asm.BinaryIntExpressionHelper;
import org.codehaus.groovy.classgen.asm.BinaryLongExpressionHelper;
import org.codehaus.groovy.classgen.asm.BinaryObjectExpressionHelper;
import org.codehaus.groovy.classgen.asm.CompileStack;
import org.codehaus.groovy.classgen.asm.MethodCaller;
import org.codehaus.groovy.classgen.asm.OperandStack;
import org.codehaus.groovy.classgen.asm.TypeChooser;
import org.codehaus.groovy.classgen.asm.WriterController;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.syntax.TokenUtil;

public class BinaryExpressionMultiTypeDispatcher
extends BinaryExpressionHelper {
    protected BinaryExpressionWriter[] binExpWriter = this.initializeDelegateHelpers();
    public static final Map<ClassNode, Integer> typeMap = Maps.of(ClassHelper.int_TYPE, 1, ClassHelper.long_TYPE, 2, ClassHelper.double_TYPE, 3, ClassHelper.char_TYPE, 4, ClassHelper.byte_TYPE, 5, ClassHelper.short_TYPE, 6, ClassHelper.float_TYPE, 7, ClassHelper.boolean_TYPE, 8);
    public static final String[] typeMapKeyNames = new String[]{"dummy", "int", "long", "double", "char", "byte", "short", "float", "boolean"};

    protected BinaryExpressionWriter[] initializeDelegateHelpers() {
        return new BinaryExpressionWriter[]{new BinaryObjectExpressionHelper(this.controller), new BinaryIntExpressionHelper(this.controller), new BinaryLongExpressionHelper(this.controller), new BinaryDoubleExpressionHelper(this.controller), new BinaryCharExpressionHelper(this.controller), new BinaryByteExpressionHelper(this.controller), new BinaryShortExpressionHelper(this.controller), new BinaryFloatExpressionHelper(this.controller), new BinaryBooleanExpressionHelper(this.controller)};
    }

    public BinaryExpressionMultiTypeDispatcher(WriterController wc) {
        super(wc);
    }

    private static int getOperandConversionType(ClassNode leftType, ClassNode rightType) {
        if (WideningCategories.isIntCategory(leftType) && WideningCategories.isIntCategory(rightType)) {
            return 1;
        }
        if (WideningCategories.isLongCategory(leftType) && WideningCategories.isLongCategory(rightType)) {
            return 2;
        }
        if (WideningCategories.isBigDecCategory(leftType) && WideningCategories.isBigDecCategory(rightType)) {
            return 0;
        }
        if (WideningCategories.isDoubleCategory(leftType) && WideningCategories.isDoubleCategory(rightType)) {
            return 3;
        }
        return 0;
    }

    protected int getOperandType(ClassNode type) {
        Integer ret = typeMap.get(type);
        if (ret == null) {
            return 0;
        }
        return ret;
    }

    @Deprecated
    protected boolean doPrimtiveCompare(ClassNode leftType, ClassNode rightType, BinaryExpression binExp) {
        return this.doPrimitiveCompare(leftType, rightType, binExp);
    }

    protected boolean doPrimitiveCompare(ClassNode leftType, ClassNode rightType, BinaryExpression binExp) {
        Expression leftExp = binExp.getLeftExpression();
        Expression rightExp = binExp.getRightExpression();
        int operation = binExp.getOperation().getType();
        int operationType = BinaryExpressionMultiTypeDispatcher.getOperandConversionType(leftType, rightType);
        BinaryExpressionWriter bew = this.binExpWriter[operationType];
        if (!bew.write(operation, true)) {
            return false;
        }
        AsmClassGenerator acg = this.controller.getAcg();
        OperandStack os = this.controller.getOperandStack();
        leftExp.visit(acg);
        os.doGroovyCast(bew.getNormalOpResultType());
        rightExp.visit(acg);
        os.doGroovyCast(bew.getNormalOpResultType());
        bew.write(operation, false);
        return true;
    }

    @Override
    protected void evaluateCompareExpression(MethodCaller compareMethod, BinaryExpression binExp) {
        Expression rightExp;
        ClassNode rightType;
        Expression leftExp;
        ClassNode current = this.controller.getClassNode();
        TypeChooser typeChooser = this.controller.getTypeChooser();
        ClassNode leftType = typeChooser.resolveType(leftExp = binExp.getLeftExpression(), current);
        if (!this.doPrimitiveCompare(leftType, rightType = typeChooser.resolveType(rightExp = binExp.getRightExpression(), current), binExp)) {
            super.evaluateCompareExpression(compareMethod, binExp);
        }
    }

    @Override
    protected void evaluateBinaryExpression(String message, BinaryExpression binExp) {
        ClassNode leftTypeOrig;
        int operation = TokenUtil.removeAssignment(binExp.getOperation().getType());
        ClassNode current = this.controller.getClassNode();
        Expression leftExp = binExp.getLeftExpression();
        ClassNode leftType = leftTypeOrig = this.controller.getTypeChooser().resolveType(leftExp, current);
        Expression rightExp = binExp.getRightExpression();
        ClassNode rightType = this.controller.getTypeChooser().resolveType(rightExp, current);
        AsmClassGenerator acg = this.controller.getAcg();
        OperandStack os = this.controller.getOperandStack();
        if (operation == 30) {
            leftType = leftTypeOrig.getComponentType();
            int operationType = this.getOperandType(leftType);
            BinaryExpressionWriter bew = this.binExpWriter[operationType];
            if (leftTypeOrig.isArray() && this.isIntCastableType(rightExp) && bew.arrayGet(operation, true) && !binExp.isSafe()) {
                leftExp.visit(acg);
                os.doGroovyCast(leftTypeOrig);
                rightExp.visit(acg);
                os.doGroovyCast(ClassHelper.int_TYPE);
                bew.arrayGet(operation, false);
                os.replace(bew.getArrayGetResultType(), 2);
            } else {
                super.evaluateBinaryExpression(message, binExp);
            }
        } else if (operation == 203) {
            int operationType = this.getOperandType(this.controller.getTypeChooser().resolveType(binExp, current));
            BinaryExpressionWriter bew = this.binExpWriter[operationType];
            if (bew.writeDivision(true)) {
                leftExp.visit(acg);
                os.doGroovyCast(bew.getDevisionOpResultType());
                rightExp.visit(acg);
                os.doGroovyCast(bew.getDevisionOpResultType());
                bew.writeDivision(false);
            } else {
                super.evaluateBinaryExpression(message, binExp);
            }
        } else {
            int operationType = BinaryExpressionMultiTypeDispatcher.getOperandConversionType(leftType, rightType);
            BinaryExpressionWriter bew = this.binExpWriter[operationType];
            if (BinaryExpressionMultiTypeDispatcher.isShiftOperation(operation) && this.isIntCastableType(rightExp) && bew.write(operation, true)) {
                leftExp.visit(acg);
                os.doGroovyCast(bew.getNormalOpResultType());
                rightExp.visit(acg);
                os.doGroovyCast(ClassHelper.int_TYPE);
                bew.write(operation, false);
            } else if (bew.write(operation, true)) {
                leftExp.visit(acg);
                os.doGroovyCast(bew.getNormalOpResultType());
                rightExp.visit(acg);
                os.doGroovyCast(bew.getNormalOpResultType());
                bew.write(operation, false);
            } else {
                super.evaluateBinaryExpression(message, binExp);
            }
        }
    }

    private boolean isIntCastableType(Expression rightExp) {
        ClassNode type = this.controller.getTypeChooser().resolveType(rightExp, this.controller.getClassNode());
        return WideningCategories.isNumberCategory(type);
    }

    private static boolean isShiftOperation(int operation) {
        return operation == 280 || operation == 281 || operation == 282;
    }

    private static boolean isAssignmentToArray(BinaryExpression binExp) {
        Expression leftExpression = binExp.getLeftExpression();
        if (!(leftExpression instanceof BinaryExpression)) {
            return false;
        }
        BinaryExpression leftBinExpr = (BinaryExpression)leftExpression;
        return leftBinExpr.getOperation().getType() == 30;
    }

    private boolean doAssignmentToArray(BinaryExpression binExp) {
        if (!BinaryExpressionMultiTypeDispatcher.isAssignmentToArray(binExp)) {
            return false;
        }
        int operation = TokenUtil.removeAssignment(binExp.getOperation().getType());
        ClassNode current = this.controller.getClassNode();
        Expression leftExp = binExp.getLeftExpression();
        ClassNode leftType = this.controller.getTypeChooser().resolveType(leftExp, current);
        Expression rightExp = binExp.getRightExpression();
        ClassNode rightType = this.controller.getTypeChooser().resolveType(rightExp, current);
        int operationType = this.getOperandType(leftType);
        BinaryExpressionWriter bew = this.binExpWriter[operationType];
        boolean simulationSuccess = bew.arrayGet(30, true);
        simulationSuccess = simulationSuccess && bew.write(operation, true);
        boolean bl = simulationSuccess = simulationSuccess && bew.arraySet(true);
        if (!simulationSuccess) {
            return false;
        }
        AsmClassGenerator acg = this.controller.getAcg();
        OperandStack operandStack = this.controller.getOperandStack();
        CompileStack compileStack = this.controller.getCompileStack();
        BinaryExpression arrayWithSubscript = (BinaryExpression)leftExp;
        Expression subscript = arrayWithSubscript.getRightExpression();
        subscript.visit(acg);
        operandStack.doGroovyCast(ClassHelper.int_TYPE);
        int subscriptValueId = compileStack.defineTemporaryVariable("$sub", ClassHelper.int_TYPE, true);
        arrayWithSubscript.getLeftExpression().visit(acg);
        operandStack.doGroovyCast(leftType.makeArray());
        operandStack.dup();
        operandStack.load(ClassHelper.int_TYPE, subscriptValueId);
        bew.arrayGet(30, false);
        operandStack.replace(leftType, 2);
        binExp.getRightExpression().visit(acg);
        if (!(bew instanceof BinaryObjectExpressionHelper)) {
            operandStack.doGroovyCast(leftType);
        }
        bew.write(operation, false);
        operandStack.dup();
        int resultValueId = compileStack.defineTemporaryVariable("$result", rightType, true);
        operandStack.load(ClassHelper.int_TYPE, subscriptValueId);
        operandStack.swap();
        bew.arraySet(false);
        operandStack.remove(3);
        operandStack.load(rightType, resultValueId);
        compileStack.removeVar(resultValueId);
        compileStack.removeVar(subscriptValueId);
        return true;
    }

    @Override
    protected void evaluateBinaryExpressionWithAssignment(String method, BinaryExpression binExp) {
        if (this.doAssignmentToArray(binExp)) {
            return;
        }
        if (this.doAssignmentToLocalVariable(method, binExp)) {
            return;
        }
        super.evaluateBinaryExpressionWithAssignment(method, binExp);
    }

    private boolean doAssignmentToLocalVariable(String method, BinaryExpression binExp) {
        Expression left = binExp.getLeftExpression();
        if (left instanceof VariableExpression) {
            VariableExpression ve = (VariableExpression)left;
            Variable v = ve.getAccessedVariable();
            if (v instanceof DynamicVariable) {
                return false;
            }
            if (v instanceof PropertyExpression) {
                return false;
            }
        } else {
            return false;
        }
        this.evaluateBinaryExpression(method, binExp);
        this.controller.getOperandStack().dup();
        this.controller.getCompileStack().pushLHS(true);
        binExp.getLeftExpression().visit(this.controller.getAcg());
        this.controller.getCompileStack().popLHS();
        return true;
    }

    @Override
    protected void assignToArray(Expression orig, Expression receiver, Expression index, Expression rhsValueLoader, boolean safe) {
        ClassNode current = this.controller.getClassNode();
        ClassNode arrayType = this.controller.getTypeChooser().resolveType(receiver, current);
        ClassNode arrayComponentType = arrayType.getComponentType();
        int operationType = this.getOperandType(arrayComponentType);
        BinaryExpressionWriter bew = this.binExpWriter[operationType];
        AsmClassGenerator acg = this.controller.getAcg();
        if (bew.arraySet(true) && arrayType.isArray() && !safe) {
            OperandStack operandStack = this.controller.getOperandStack();
            receiver.visit(acg);
            operandStack.doGroovyCast(arrayType);
            index.visit(acg);
            operandStack.doGroovyCast(ClassHelper.int_TYPE);
            rhsValueLoader.visit(acg);
            operandStack.doGroovyCast(arrayComponentType);
            bew.arraySet(false);
            operandStack.remove(3);
            rhsValueLoader.visit(acg);
        } else {
            super.assignToArray(orig, receiver, index, rhsValueLoader, safe);
        }
    }

    @Override
    protected void writePostOrPrefixMethod(int op, String method, Expression expression, Expression orig) {
        ClassNode type = this.controller.getTypeChooser().resolveType(orig, this.controller.getClassNode());
        int operationType = this.getOperandType(type);
        BinaryExpressionWriter bew = this.binExpWriter[operationType];
        if (bew.writePostOrPrefixMethod(op, true)) {
            OperandStack operandStack = this.controller.getOperandStack();
            operandStack.doGroovyCast(type);
            bew.writePostOrPrefixMethod(op, false);
            operandStack.replace(bew.getNormalOpResultType());
        } else {
            super.writePostOrPrefixMethod(op, method, expression, orig);
        }
    }

    private static class BinaryCharExpressionHelper
    extends BinaryIntExpressionHelper {
        private static final MethodCaller charArrayGet = MethodCaller.newStatic(BytecodeInterface8.class, "cArrayGet");
        private static final MethodCaller charArraySet = MethodCaller.newStatic(BytecodeInterface8.class, "cArraySet");

        public BinaryCharExpressionHelper(WriterController wc) {
            super(wc, charArraySet, charArrayGet);
        }

        @Override
        protected ClassNode getArrayGetResultType() {
            return ClassHelper.char_TYPE;
        }
    }

    private static class BinaryByteExpressionHelper
    extends BinaryIntExpressionHelper {
        private static final MethodCaller byteArrayGet = MethodCaller.newStatic(BytecodeInterface8.class, "bArrayGet");
        private static final MethodCaller byteArraySet = MethodCaller.newStatic(BytecodeInterface8.class, "bArraySet");

        public BinaryByteExpressionHelper(WriterController wc) {
            super(wc, byteArraySet, byteArrayGet);
        }

        @Override
        protected ClassNode getArrayGetResultType() {
            return ClassHelper.byte_TYPE;
        }
    }

    private static class BinaryShortExpressionHelper
    extends BinaryIntExpressionHelper {
        private static final MethodCaller shortArrayGet = MethodCaller.newStatic(BytecodeInterface8.class, "sArrayGet");
        private static final MethodCaller shortArraySet = MethodCaller.newStatic(BytecodeInterface8.class, "sArraySet");

        public BinaryShortExpressionHelper(WriterController wc) {
            super(wc, shortArraySet, shortArrayGet);
        }

        @Override
        protected ClassNode getArrayGetResultType() {
            return ClassHelper.short_TYPE;
        }
    }
}

