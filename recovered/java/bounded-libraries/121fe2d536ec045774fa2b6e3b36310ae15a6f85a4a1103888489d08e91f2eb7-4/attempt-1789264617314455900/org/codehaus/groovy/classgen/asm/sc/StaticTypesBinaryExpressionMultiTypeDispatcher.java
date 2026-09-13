/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm.sc;

import groovyjarjarasm.asm.Label;
import groovyjarjarasm.asm.MethodVisitor;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.groovy.ast.tools.ExpressionUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.AttributeExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.LambdaExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.MethodReferenceExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.ForStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.WideningCategories;
import org.codehaus.groovy.classgen.asm.BinaryExpressionMultiTypeDispatcher;
import org.codehaus.groovy.classgen.asm.BytecodeHelper;
import org.codehaus.groovy.classgen.asm.CompileStack;
import org.codehaus.groovy.classgen.asm.OperandStack;
import org.codehaus.groovy.classgen.asm.VariableSlotLoader;
import org.codehaus.groovy.classgen.asm.WriterController;
import org.codehaus.groovy.classgen.asm.sc.StaticInvocationWriter;
import org.codehaus.groovy.classgen.asm.sc.StaticPropertyAccessHelper;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.syntax.TokenUtil;
import org.codehaus.groovy.transform.sc.StaticCompilationMetadataKeys;
import org.codehaus.groovy.transform.sc.StaticCompilationVisitor;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingSupport;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingVisitor;
import org.codehaus.groovy.transform.stc.StaticTypesMarker;

public class StaticTypesBinaryExpressionMultiTypeDispatcher
extends BinaryExpressionMultiTypeDispatcher {
    private static final MethodNode CLOSURE_GETTHISOBJECT_METHOD = ClassHelper.CLOSURE_TYPE.getMethod("getThisObject", Parameter.EMPTY_ARRAY);
    private final AtomicInteger labelCounter = new AtomicInteger();

    public StaticTypesBinaryExpressionMultiTypeDispatcher(WriterController wc) {
        super(wc);
    }

    @Override
    protected void writePostOrPrefixMethod(int op, String method, Expression expression, Expression orig) {
        MethodNode mn = (MethodNode)orig.getNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET);
        if (mn != null) {
            this.controller.getOperandStack().pop();
            MethodCallExpression call = GeneralUtils.callX(expression, method);
            call.setMethodTarget(mn);
            call.visit(this.controller.getAcg());
            return;
        }
        ClassNode top = this.controller.getOperandStack().getTopOperand();
        if (ClassHelper.isPrimitiveType(top) && (ClassHelper.isNumberType(top) || ClassHelper.isPrimitiveChar(top))) {
            MethodVisitor mv = this.controller.getMethodVisitor();
            StaticTypesBinaryExpressionMultiTypeDispatcher.visitInsnByType(top, mv, 4, 10, 12, 15);
            if ("next".equals(method)) {
                StaticTypesBinaryExpressionMultiTypeDispatcher.visitInsnByType(top, mv, 96, 97, 98, 99);
            } else {
                StaticTypesBinaryExpressionMultiTypeDispatcher.visitInsnByType(top, mv, 100, 101, 102, 103);
            }
            return;
        }
        super.writePostOrPrefixMethod(op, method, expression, orig);
    }

    private static void visitInsnByType(ClassNode top, MethodVisitor mv, int iInsn, int lInsn, int fInsn, int dInsn) {
        if (WideningCategories.isIntCategory(top) || ClassHelper.isPrimitiveChar(top)) {
            mv.visitInsn(iInsn);
        } else if (ClassHelper.isPrimitiveLong(top)) {
            mv.visitInsn(lInsn);
        } else if (ClassHelper.isPrimitiveFloat(top)) {
            mv.visitInsn(fInsn);
        } else if (ClassHelper.isPrimitiveDouble(top)) {
            mv.visitInsn(dInsn);
        }
    }

    @Override
    protected void evaluateBinaryExpressionWithAssignment(String method, BinaryExpression expression) {
        Expression leftExpression = expression.getLeftExpression();
        if (leftExpression instanceof PropertyExpression) {
            PropertyExpression pexp = (PropertyExpression)leftExpression;
            BinaryExpression expressionWithoutAssignment = GeneralUtils.binX(leftExpression, Token.newSymbol(TokenUtil.removeAssignment(expression.getOperation().getType()), expression.getOperation().getStartLine(), expression.getOperation().getStartColumn()), expression.getRightExpression());
            expressionWithoutAssignment.copyNodeMetaData(expression);
            expressionWithoutAssignment.setSafe(expression.isSafe());
            expressionWithoutAssignment.setSourcePosition(expression);
            if (this.makeSetProperty(pexp.getObjectExpression(), pexp.getProperty(), expressionWithoutAssignment, pexp.isSafe(), pexp.isSpreadSafe(), pexp.isImplicitThis(), pexp instanceof AttributeExpression)) {
                return;
            }
        }
        super.evaluateBinaryExpressionWithAssignment(method, expression);
    }

    @Override
    public void evaluateEqual(BinaryExpression expression, boolean defineVariable) {
        Expression leftExpression = expression.getLeftExpression();
        if (!defineVariable) {
            PropertyExpression pexp;
            if (leftExpression instanceof PropertyExpression && this.makeSetProperty((pexp = (PropertyExpression)leftExpression).getObjectExpression(), pexp.getProperty(), expression.getRightExpression(), pexp.isSafe(), pexp.isSpreadSafe(), pexp.isImplicitThis(), pexp instanceof AttributeExpression)) {
                return;
            }
        } else {
            Expression rightExpression = expression.getRightExpression();
            if (rightExpression instanceof LambdaExpression || rightExpression instanceof MethodReferenceExpression) {
                rightExpression.putNodeMetaData((Object)StaticTypesMarker.INFERRED_FUNCTIONAL_INTERFACE_TYPE, leftExpression.getNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE));
            }
        }
        if (leftExpression instanceof PropertyExpression && ((PropertyExpression)leftExpression).isSpreadSafe() && StaticTypeCheckingSupport.isAssignment(expression.getOperation().getType())) {
            this.transformSpreadOnLHS(expression);
            return;
        }
        super.evaluateEqual(expression, defineVariable);
    }

    private void transformSpreadOnLHS(BinaryExpression expression) {
        PropertyExpression spreadExpression = (PropertyExpression)expression.getLeftExpression();
        Expression receiver = spreadExpression.getObjectExpression();
        int counter = this.labelCounter.incrementAndGet();
        CompileStack compileStack = this.controller.getCompileStack();
        OperandStack operandStack = this.controller.getOperandStack();
        VariableExpression result = GeneralUtils.varX(this.getClass().getSimpleName() + "$spreadresult" + counter, StaticCompilationVisitor.ARRAYLIST_CLASSNODE);
        ConstructorCallExpression newArrayList = GeneralUtils.ctorX(StaticCompilationVisitor.ARRAYLIST_CLASSNODE);
        newArrayList.setNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET, StaticCompilationVisitor.ARRAYLIST_CONSTRUCTOR);
        DeclarationExpression decl = GeneralUtils.declX(result, newArrayList);
        ((ASTNode)decl).visit(this.controller.getAcg());
        receiver.visit(this.controller.getAcg());
        Label ifnull = compileStack.createLocalLabel("ifnull_" + counter);
        MethodVisitor mv = this.controller.getMethodVisitor();
        mv.visitJumpInsn(198, ifnull);
        operandStack.remove(1);
        Label nonull = compileStack.createLocalLabel("nonull_" + counter);
        mv.visitLabel(nonull);
        ClassNode componentType = StaticTypeCheckingVisitor.inferLoopElementType(this.controller.getTypeChooser().resolveType(receiver, this.controller.getClassNode()));
        Parameter iterator = new Parameter(componentType, "for$it$" + counter);
        VariableExpression iteratorAsVar = GeneralUtils.varX(iterator);
        PropertyExpression pexp = spreadExpression instanceof AttributeExpression ? new AttributeExpression(iteratorAsVar, spreadExpression.getProperty(), true) : new PropertyExpression(iteratorAsVar, spreadExpression.getProperty(), true);
        pexp.setImplicitThis(spreadExpression.isImplicitThis());
        pexp.setSourcePosition(spreadExpression);
        BinaryExpression assignment = GeneralUtils.binX(pexp, expression.getOperation(), expression.getRightExpression());
        MethodCallExpression add = GeneralUtils.callX((Expression)result, "add", (Expression)assignment);
        add.setMethodTarget(StaticCompilationVisitor.ARRAYLIST_ADD_METHOD);
        ForStatement stmt = new ForStatement(iterator, receiver, GeneralUtils.stmt(add));
        stmt.visit(this.controller.getAcg());
        mv.visitLabel(ifnull);
        result.visit(this.controller.getAcg());
    }

    private boolean makeSetProperty(Expression receiver, Expression message, Expression arguments, boolean safe, boolean spreadSafe, boolean implicitThis, boolean isAttribute) {
        ClassNode receiverType = this.controller.getTypeChooser().resolveType(receiver, this.controller.getClassNode());
        String property = message.getText();
        boolean isThisExpression = ExpressionUtils.isThisExpression(receiver);
        if (isAttribute || isThisExpression && receiverType.getDeclaredField(property) != null) {
            ClassNode current = receiverType;
            FieldNode fn = null;
            while (fn == null && current != null) {
                fn = current.getDeclaredField(property);
                if (fn != null) continue;
                current = current.getSuperClass();
            }
            if (fn != null && receiverType != current && !fn.isPublic()) {
                if (!fn.isProtected()) {
                    return false;
                }
                if (!Objects.equals(receiverType.getPackageName(), current.getPackageName())) {
                    return false;
                }
                if (!fn.isStatic()) {
                    receiver.visit(this.controller.getAcg());
                }
                arguments.visit(this.controller.getAcg());
                OperandStack operandStack = this.controller.getOperandStack();
                operandStack.doGroovyCast(fn.getOriginType());
                MethodVisitor mv = this.controller.getMethodVisitor();
                mv.visitFieldInsn(fn.isStatic() ? 179 : 181, BytecodeHelper.getClassInternalName(fn.getOwner()), property, BytecodeHelper.getTypeDescription(fn.getOriginType()));
                operandStack.remove(fn.isStatic() ? 1 : 2);
                return true;
            }
        }
        if (!isAttribute) {
            PropertyNode propertyNode;
            ClassNode declaringClass;
            String setter = GeneralUtils.getSetterName(property);
            MethodNode setterMethod = receiverType.getSetterMethod(setter, false);
            ClassNode classNode = declaringClass = setterMethod != null ? setterMethod.getDeclaringClass() : null;
            if (isThisExpression && declaringClass != null && declaringClass.equals(this.controller.getClassNode())) {
                setterMethod = null;
            } else if (setterMethod == null && (propertyNode = receiverType.getProperty(property)) != null && !Modifier.isFinal(propertyNode.getModifiers())) {
                setterMethod = new MethodNode(setter, 1, ClassHelper.VOID_TYPE, new Parameter[]{new Parameter(propertyNode.getOriginType(), "value")}, ClassNode.EMPTY_ARRAY, EmptyStatement.INSTANCE);
                setterMethod.setDeclaringClass(receiverType);
            }
            if (setterMethod != null) {
                Expression call = StaticPropertyAccessHelper.transformToSetterCall(receiver, setterMethod, arguments, implicitThis, safe, spreadSafe, true, message);
                call.visit(this.controller.getAcg());
                return true;
            }
            if (isThisExpression && !this.controller.isInGeneratedFunction()) {
                receiverType = this.controller.getClassNode();
            }
            if (this.makeSetPrivateFieldWithBridgeMethod(receiver, receiverType, property, arguments, safe, spreadSafe, implicitThis)) {
                return true;
            }
        }
        return false;
    }

    private boolean makeSetPrivateFieldWithBridgeMethod(Expression receiver, ClassNode receiverType, String fieldName, Expression arguments, boolean safe, boolean spreadSafe, boolean implicitThis) {
        MethodNode methodNode;
        Map mutators;
        FieldNode field = receiverType.getField(fieldName);
        ClassNode outerClass = receiverType.getOuterClass();
        if (field == null && implicitThis && outerClass != null && !receiverType.isStaticClass()) {
            Expression pexp;
            if (this.controller.isInGeneratedFunction()) {
                MethodCallExpression mce = GeneralUtils.callThisX("getThisObject");
                mce.setImplicitThis(true);
                mce.setMethodTarget(CLOSURE_GETTHISOBJECT_METHOD);
                mce.putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, this.controller.getOutermostClass());
                pexp = GeneralUtils.castX(this.controller.getOutermostClass(), mce);
            } else {
                pexp = GeneralUtils.propX((Expression)GeneralUtils.classX(outerClass), "this");
                pexp.setImplicitThis(true);
            }
            pexp.putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, outerClass);
            pexp.setSourcePosition(receiver);
            return this.makeSetPrivateFieldWithBridgeMethod(pexp, outerClass, fieldName, arguments, safe, spreadSafe, true);
        }
        ClassNode classNode = this.controller.getClassNode();
        if (field != null && field.isPrivate() && !receiverType.equals(classNode) && (StaticInvocationWriter.isPrivateBridgeMethodsCallAllowed(receiverType, classNode) || StaticInvocationWriter.isPrivateBridgeMethodsCallAllowed(classNode, receiverType)) && (mutators = (Map)receiverType.redirect().getNodeMetaData((Object)StaticCompilationMetadataKeys.PRIVATE_FIELDS_MUTATORS)) != null && (methodNode = (MethodNode)mutators.get(fieldName)) != null) {
            MethodCallExpression call = GeneralUtils.callX(receiver, methodNode.getName(), (Expression)GeneralUtils.args(field.isStatic() ? GeneralUtils.nullX() : receiver, arguments));
            call.setImplicitThis(implicitThis);
            call.setMethodTarget(methodNode);
            call.setSafe(safe);
            call.setSpreadSafe(spreadSafe);
            call.visit(this.controller.getAcg());
            return true;
        }
        return false;
    }

    @Override
    protected void assignToArray(Expression enclosing, Expression receiver, Expression subscript, Expression rhsValueLoader, boolean safe) {
        ClassNode receiverType = this.controller.getTypeChooser().resolveType(receiver, this.controller.getClassNode());
        if (receiverType.isArray() && !safe && this.binExpWriter[this.getOperandType(receiverType.getComponentType())].arraySet(true)) {
            super.assignToArray(enclosing, receiver, subscript, rhsValueLoader, safe);
        } else {
            if (rhsValueLoader instanceof VariableSlotLoader && enclosing instanceof BinaryExpression) {
                rhsValueLoader.putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, this.controller.getTypeChooser().resolveType(enclosing, this.controller.getClassNode()));
            }
            receiver.visit(new StaticCompilationVisitor(this.controller.getSourceUnit(), this.controller.getClassNode()));
            MethodCallExpression call = GeneralUtils.callX(receiver, "putAt", (Expression)GeneralUtils.args(subscript, rhsValueLoader));
            call.setSafe(safe);
            call.setSourcePosition(enclosing);
            OperandStack operandStack = this.controller.getOperandStack();
            int height = operandStack.getStackLength();
            call.visit(this.controller.getAcg());
            operandStack.pop();
            operandStack.remove(operandStack.getStackLength() - height);
            rhsValueLoader.visit(this.controller.getAcg());
        }
    }
}

