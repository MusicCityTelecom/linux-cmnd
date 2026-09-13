/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm;

import groovyjarjarasm.asm.MethodVisitor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.groovy.ast.tools.AnnotatedNodeUtils;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.CodeVisitorSupport;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.Variable;
import org.codehaus.groovy.ast.VariableScope;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.FieldExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.classgen.AsmClassGenerator;
import org.codehaus.groovy.classgen.Verifier;
import org.codehaus.groovy.classgen.asm.BytecodeHelper;
import org.codehaus.groovy.classgen.asm.BytecodeVariable;
import org.codehaus.groovy.classgen.asm.CompileStack;
import org.codehaus.groovy.classgen.asm.OperandStack;
import org.codehaus.groovy.classgen.asm.WriterController;
import org.codehaus.groovy.classgen.asm.WriterControllerFactory;
import org.codehaus.groovy.transform.stc.StaticTypesMarker;

public class ClosureWriter {
    public static final String OUTER_INSTANCE = "_outerInstance";
    public static final String THIS_OBJECT = "_thisObject";
    protected final WriterController controller;
    private final Map<Expression, ClassNode> closureClasses = new HashMap<Expression, ClassNode>();

    public ClosureWriter(WriterController controller) {
        this.controller = controller;
    }

    public void writeClosure(ClosureExpression expression) {
        CompileStack compileStack = this.controller.getCompileStack();
        MethodVisitor mv = this.controller.getMethodVisitor();
        ClassNode classNode = this.controller.getClassNode();
        AsmClassGenerator acg = this.controller.getAcg();
        int mods = 17;
        if (classNode.isInterface()) {
            mods |= 8;
        }
        ClassNode closureClass = this.getOrAddClosureClass(expression, mods);
        String closureClassinternalName = BytecodeHelper.getClassInternalName(closureClass);
        List<ConstructorNode> constructors = closureClass.getDeclaredConstructors();
        ConstructorNode node = constructors.get(0);
        Parameter[] localVariableParams = node.getParameters();
        mv.visitTypeInsn(187, closureClassinternalName);
        mv.visitInsn(89);
        if (this.controller.isStaticMethod() || compileStack.isInSpecialConstructorCall()) {
            new ClassExpression(classNode).visit(acg);
            new ClassExpression(this.controller.getOutermostClass()).visit(acg);
        } else {
            mv.visitVarInsn(25, 0);
            this.controller.getOperandStack().push(ClassHelper.OBJECT_TYPE);
            this.loadThis();
        }
        for (int i = 2; i < localVariableParams.length; ++i) {
            Parameter param = localVariableParams[i];
            String name = param.getName();
            ClosureWriter.loadReference(name, this.controller);
            if (param.getNodeMetaData(UseExistingReference.class) != null) continue;
            param.setNodeMetaData(UseExistingReference.class, Boolean.TRUE);
        }
        mv.visitMethodInsn(183, closureClassinternalName, "<init>", BytecodeHelper.getMethodDescriptor(ClassHelper.VOID_TYPE, localVariableParams), false);
        this.controller.getOperandStack().replace(ClassHelper.CLOSURE_TYPE, localVariableParams.length);
    }

    public static void loadReference(String name, WriterController controller) {
        CompileStack compileStack = controller.getCompileStack();
        MethodVisitor mv = controller.getMethodVisitor();
        ClassNode classNode = controller.getClassNode();
        AsmClassGenerator acg = controller.getAcg();
        if (!compileStack.containsVariable(name) && compileStack.getScope().isReferencedClassVariable(name)) {
            acg.visitFieldExpression(new FieldExpression(classNode.getDeclaredField(name)));
        } else {
            BytecodeVariable v = compileStack.getVariable(name, !ClosureWriter.classNodeUsesReferences(controller.getClassNode()));
            if (v == null) {
                FieldNode field = classNode.getDeclaredField(name);
                mv.visitVarInsn(25, 0);
                mv.visitFieldInsn(180, controller.getInternalClassName(), name, BytecodeHelper.getTypeDescription(field.getType()));
            } else {
                mv.visitVarInsn(25, v.getIndex());
            }
            controller.getOperandStack().push(ClassHelper.REFERENCE_TYPE);
        }
    }

    public ClassNode getOrAddClosureClass(ClosureExpression expression, int modifiers) {
        ClassNode closureClass = this.closureClasses.get(expression);
        if (closureClass == null) {
            closureClass = this.createClosureClass(expression, modifiers);
            this.closureClasses.put(expression, closureClass);
            this.controller.getAcg().addInnerClass(closureClass);
            closureClass.addInterface(ClassHelper.GENERATED_CLOSURE_Type);
            closureClass.putNodeMetaData(WriterControllerFactory.class, x -> this.controller);
        }
        return closureClass;
    }

    private static boolean classNodeUsesReferences(ClassNode classNode) {
        boolean ret = classNode.getSuperClass().equals(ClassHelper.CLOSURE_TYPE);
        if (ret) {
            return ret;
        }
        if (classNode instanceof InnerClassNode) {
            InnerClassNode inner = (InnerClassNode)classNode;
            return inner.isAnonymous();
        }
        return false;
    }

    protected ClassNode createClosureClass(ClosureExpression expression, int modifiers) {
        ClassNode classNode = this.controller.getClassNode();
        ClassNode outerClass = this.controller.getOutermostClass();
        String name = this.genClosureClassName();
        Parameter[] parameters = expression.getParameters();
        if (parameters == null) {
            parameters = Parameter.EMPTY_ARRAY;
        } else if (parameters.length == 0) {
            Parameter it = GeneralUtils.param(ClassHelper.OBJECT_TYPE, "it", GeneralUtils.nullX());
            parameters = new Parameter[]{it};
            Variable ref = expression.getVariableScope().getDeclaredVariable("it");
            if (ref != null) {
                it.setClosureSharedVariable(ref.isClosureSharedVariable());
            }
        }
        Parameter[] localVariableParams = this.getClosureSharedVariables(expression);
        ClosureWriter.removeInitialValues(localVariableParams);
        ClassNode returnType = (ClassNode)expression.getNodeMetaData((Object)StaticTypesMarker.INFERRED_RETURN_TYPE);
        if (returnType == null) {
            returnType = ClassHelper.OBJECT_TYPE;
        } else if (returnType.isPrimaryClassNode()) {
            returnType = returnType.getPlainNodeReference();
        } else if (ClassHelper.isPrimitiveType(returnType)) {
            returnType = ClassHelper.getWrapper(returnType);
        } else if (GenericsUtils.hasUnresolvedGenerics(returnType)) {
            returnType = GenericsUtils.nonGeneric(returnType);
        }
        InnerClassNode answer = new InnerClassNode(classNode, name, modifiers, ClassHelper.CLOSURE_TYPE.getPlainNodeReference());
        answer.setEnclosingMethod(this.controller.getMethodNode());
        answer.setScriptBody(this.controller.isInScriptBody());
        answer.setSourcePosition(expression);
        answer.setStaticClass(this.controller.isStaticMethod() || classNode.isStaticClass());
        answer.setSynthetic(true);
        answer.setUsingGenerics(outerClass.isUsingGenerics());
        MethodNode method = answer.addMethod("doCall", 1, returnType, parameters, ClassNode.EMPTY_ARRAY, expression.getCode());
        method.setSourcePosition(expression);
        VariableScope varScope = expression.getVariableScope();
        if (varScope == null) {
            throw new RuntimeException("Must have a VariableScope by now! for expression: " + expression + " class: " + name);
        }
        method.setVariableScope(varScope.copy());
        if (parameters.length > 1 || parameters.length == 1 && parameters[0].getType() != null && !ClassHelper.OBJECT_TYPE.equals(parameters[0].getType()) && !ClassHelper.OBJECT_TYPE.equals(parameters[0].getType().getComponentType())) {
            MethodNode call = new MethodNode("call", 1, returnType, parameters, ClassNode.EMPTY_ARRAY, GeneralUtils.returnS(GeneralUtils.callThisX("doCall", GeneralUtils.args(parameters))));
            ClassNodeUtils.addGeneratedMethod(answer, call, true);
            call.setSourcePosition(expression);
        }
        BlockStatement block = this.createBlockStatementForConstructor(expression, outerClass, classNode);
        this.addFieldsAndGettersForLocalVariables(answer, localVariableParams);
        this.addConstructor(expression, localVariableParams, answer, block);
        ClosureWriter.correctAccessedVariable(answer, expression);
        return answer;
    }

    protected ConstructorNode addConstructor(ClosureExpression expression, Parameter[] localVariableParams, InnerClassNode answer, BlockStatement block) {
        Parameter[] params = new Parameter[2 + localVariableParams.length];
        params[0] = GeneralUtils.param(ClassHelper.OBJECT_TYPE, OUTER_INSTANCE);
        params[1] = GeneralUtils.param(ClassHelper.OBJECT_TYPE, THIS_OBJECT);
        System.arraycopy(localVariableParams, 0, params, 2, localVariableParams.length);
        ConstructorNode constructorNode = answer.addConstructor(1, params, ClassNode.EMPTY_ARRAY, block);
        constructorNode.setSourcePosition(expression);
        return constructorNode;
    }

    protected void addFieldsAndGettersForLocalVariables(InnerClassNode answer, Parameter[] localVariableParams) {
        for (Parameter param : localVariableParams) {
            String paramName = param.getName();
            ClassNode type = param.getType();
            VariableExpression initialValue = GeneralUtils.varX(paramName);
            initialValue.setAccessedVariable(param);
            initialValue.setUseReferenceDirectly(true);
            ClassNode realType = type;
            type = ClassHelper.makeReference();
            param.setType(ClassHelper.makeReference());
            FieldNode paramField = answer.addField(paramName, 4098, type, initialValue);
            paramField.setOriginType(ClassHelper.getWrapper(param.getOriginType()));
            paramField.setHolder(true);
            String methodName = Verifier.capitalize(paramName);
            FieldExpression fieldExp = GeneralUtils.fieldX(paramField);
            AnnotatedNodeUtils.markAsGenerated(answer, answer.addMethod("get" + methodName, 1, realType.getPlainNodeReference(), Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, GeneralUtils.returnS(fieldExp)), true);
        }
    }

    protected BlockStatement createBlockStatementForConstructor(ClosureExpression expression, ClassNode outerClass, ClassNode thisClassNode) {
        BlockStatement block = new BlockStatement();
        VariableExpression outer = GeneralUtils.varX(OUTER_INSTANCE, outerClass);
        outer.setSourcePosition(expression);
        block.getVariableScope().putReferencedLocalVariable(outer);
        VariableExpression thisObject = GeneralUtils.varX(THIS_OBJECT, thisClassNode);
        thisObject.setSourcePosition(expression);
        block.getVariableScope().putReferencedLocalVariable(thisObject);
        TupleExpression conArgs = new TupleExpression(outer, thisObject);
        block.addStatement(GeneralUtils.stmt(GeneralUtils.ctorSuperX(conArgs)));
        return block;
    }

    private String genClosureClassName() {
        ClassNode classNode = this.controller.getClassNode();
        ClassNode outerClass = this.controller.getOutermostClass();
        MethodNode methodNode = this.controller.getMethodNode();
        return classNode.getName() + "$" + this.controller.getContext().getNextClosureInnerName(outerClass, classNode, methodNode);
    }

    private static void correctAccessedVariable(InnerClassNode closureClass, ClosureExpression ce) {
        new CorrectAccessedVariableVisitor(closureClass).visitClosureExpression(ce);
    }

    protected static void removeInitialValues(Parameter[] params) {
        for (int i = 0; i < params.length; ++i) {
            if (!params[i].hasInitialExpression()) continue;
            Parameter p = GeneralUtils.param(params[i].getType(), params[i].getName());
            p.setOriginType(p.getOriginType());
            params[i] = p;
        }
    }

    public boolean addGeneratedClosureConstructorCall(ConstructorCallExpression call) {
        ClassNode classNode = this.controller.getClassNode();
        if (!classNode.declaresInterface(ClassHelper.GENERATED_CLOSURE_Type)) {
            return false;
        }
        AsmClassGenerator acg = this.controller.getAcg();
        OperandStack operandStack = this.controller.getOperandStack();
        MethodVisitor mv = this.controller.getMethodVisitor();
        mv.visitVarInsn(25, 0);
        ClassNode callNode = classNode.getSuperClass();
        TupleExpression arguments = (TupleExpression)call.getArguments();
        if (arguments.getExpressions().size() != 2) {
            throw new GroovyBugError("expected 2 arguments for closure constructor super call, but got" + arguments.getExpressions().size());
        }
        arguments.getExpression(0).visit(acg);
        operandStack.box();
        arguments.getExpression(1).visit(acg);
        operandStack.box();
        Parameter p = GeneralUtils.param(ClassHelper.OBJECT_TYPE, "_p");
        String descriptor = BytecodeHelper.getMethodDescriptor(ClassHelper.VOID_TYPE, new Parameter[]{p, p});
        mv.visitMethodInsn(183, BytecodeHelper.getClassInternalName(callNode), "<init>", descriptor, false);
        operandStack.remove(2);
        return true;
    }

    protected Parameter[] getClosureSharedVariables(ClosureExpression ce) {
        VariableScope scope = ce.getVariableScope();
        Parameter[] ret = new Parameter[scope.getReferencedLocalVariablesCount()];
        int index = 0;
        Iterator<Variable> iter = scope.getReferencedLocalVariablesIterator();
        while (iter.hasNext()) {
            Variable element = iter.next();
            Parameter p = GeneralUtils.param(element.getType(), element.getName());
            p.setOriginType(element.getOriginType());
            p.setClosureSharedVariable(element.isClosureSharedVariable());
            ret[index] = p;
            ++index;
        }
        return ret;
    }

    protected void loadThis() {
        MethodVisitor mv = this.controller.getMethodVisitor();
        mv.visitVarInsn(25, 0);
        if (this.controller.isInGeneratedFunction()) {
            mv.visitMethodInsn(182, "groovy/lang/Closure", "getThisObject", "()Ljava/lang/Object;", false);
            this.controller.getOperandStack().push(ClassHelper.OBJECT_TYPE);
        } else {
            this.controller.getOperandStack().push(this.controller.getClassNode());
        }
    }

    protected static interface UseExistingReference {
    }

    protected static class CorrectAccessedVariableVisitor
    extends CodeVisitorSupport {
        private InnerClassNode icn;

        public CorrectAccessedVariableVisitor(InnerClassNode icn) {
            this.icn = icn;
        }

        @Override
        public void visitVariableExpression(VariableExpression expression) {
            Variable v = expression.getAccessedVariable();
            if (v == null) {
                return;
            }
            if (!(v instanceof FieldNode)) {
                return;
            }
            String name = expression.getName();
            FieldNode fn = this.icn.getDeclaredField(name);
            if (fn != null) {
                expression.setAccessedVariable(fn);
            }
        }
    }
}

