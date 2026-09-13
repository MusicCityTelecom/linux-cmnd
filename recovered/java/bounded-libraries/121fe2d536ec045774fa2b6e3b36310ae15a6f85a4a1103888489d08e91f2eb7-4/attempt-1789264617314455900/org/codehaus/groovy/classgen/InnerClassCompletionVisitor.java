/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen;

import groovy.transform.CompileStatic;
import groovy.transform.stc.POJO;
import groovyjarjarasm.asm.MethodVisitor;
import java.util.List;
import java.util.function.BiConsumer;
import org.apache.groovy.ast.tools.AnnotatedNodeUtils;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.apache.groovy.ast.tools.ConstructorNodeUtils;
import org.apache.groovy.ast.tools.MethodNodeUtils;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.classgen.BytecodeInstruction;
import org.codehaus.groovy.classgen.BytecodeSequence;
import org.codehaus.groovy.classgen.InnerClassVisitorHelper;
import org.codehaus.groovy.classgen.asm.BytecodeHelper;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.SourceUnit;

public class InnerClassCompletionVisitor
extends InnerClassVisitorHelper {
    private ClassNode classNode;
    private FieldNode thisField;
    private final SourceUnit sourceUnit;
    private static final String CLOSURE_INTERNAL_NAME = BytecodeHelper.getClassInternalName(ClassHelper.CLOSURE_TYPE);
    private static final String CLOSURE_DESCRIPTOR = BytecodeHelper.getTypeDescription(ClassHelper.CLOSURE_TYPE);

    public InnerClassCompletionVisitor(CompilationUnit cu, SourceUnit su) {
        this.sourceUnit = su;
    }

    @Override
    protected SourceUnit getSourceUnit() {
        return this.sourceUnit;
    }

    @Override
    public void visitClass(ClassNode node) {
        boolean innerPojo;
        this.classNode = node;
        this.thisField = null;
        InnerClassNode innerClass = null;
        if (!node.isEnum() && !node.isInterface() && node instanceof InnerClassNode) {
            innerClass = (InnerClassNode)node;
            this.thisField = innerClass.getField("this$0");
            if (innerClass.getVariableScope() == null && innerClass.getDeclaredConstructors().isEmpty()) {
                ClassNodeUtils.addGeneratedConstructor(innerClass, 1, Parameter.EMPTY_ARRAY, null, null);
            }
        }
        if (node.isEnum() || node.isInterface()) {
            return;
        }
        if (node.getInnerClasses().hasNext()) {
            InnerClassCompletionVisitor.addDispatcherMethods(node);
        }
        if (innerClass == null) {
            return;
        }
        super.visitClass(node);
        boolean bl = innerPojo = AnnotatedNodeUtils.hasAnnotation(innerClass, ClassHelper.make(POJO.class)) && AnnotatedNodeUtils.hasAnnotation(innerClass, ClassHelper.make(CompileStatic.class));
        if (!innerPojo) {
            this.addMopMethods(innerClass);
        }
    }

    @Override
    public void visitConstructor(ConstructorNode node) {
        ConstructorNode superCtor;
        this.addThisReference(node);
        super.visitConstructor(node);
        if (((InnerClassNode)this.classNode).isAnonymous() && this.classNode.getOuterClasses().contains(this.classNode.getSuperClass()) && (superCtor = this.classNode.getSuperClass().getDeclaredConstructor(node.getParameters())) != null && superCtor.isPrivate()) {
            ClassNode superClass = this.classNode.getUnresolvedSuperClass();
            InnerClassCompletionVisitor.makeBridgeConstructor(superClass, node.getParameters());
            ConstructorCallExpression superCtorCall = ConstructorNodeUtils.getFirstIfSpecialConstructorCall(node.getCode());
            ((TupleExpression)superCtorCall.getArguments()).addExpression(GeneralUtils.castX(superClass, GeneralUtils.nullX()));
        }
    }

    private static void makeBridgeConstructor(ClassNode c, Parameter[] p) {
        Parameter[] newP = new Parameter[p.length + 1];
        for (int i = 0; i < p.length; ++i) {
            newP[i] = new Parameter(p[i].getType(), "p" + i);
        }
        newP[p.length] = new Parameter(c, "$anonymous");
        if (c.getDeclaredConstructor(newP) == null) {
            TupleExpression args = new TupleExpression();
            for (int i = 0; i < p.length; ++i) {
                args.addExpression(GeneralUtils.varX(newP[i]));
            }
            ClassNodeUtils.addGeneratedConstructor(c, 4096, newP, ClassNode.EMPTY_ARRAY, GeneralUtils.stmt(GeneralUtils.ctorThisX(args)));
        }
    }

    private static String getTypeDescriptor(ClassNode node, boolean isStatic) {
        return BytecodeHelper.getTypeDescription(InnerClassCompletionVisitor.getClassNode(node, isStatic));
    }

    private static String getInternalName(ClassNode node, boolean isStatic) {
        return BytecodeHelper.getClassInternalName(InnerClassCompletionVisitor.getClassNode(node, isStatic));
    }

    private static void addDispatcherMethods(ClassNode classNode) {
        int objectDistance = InnerClassCompletionVisitor.getObjectDistance(classNode);
        BlockStatement block = new BlockStatement();
        MethodNode method = classNode.addSyntheticMethod("this$dist$invoke$" + objectDistance, 1, ClassHelper.OBJECT_TYPE, GeneralUtils.params(GeneralUtils.param(ClassHelper.STRING_TYPE, "name"), GeneralUtils.param(ClassHelper.OBJECT_TYPE, "args")), ClassNode.EMPTY_ARRAY, block);
        InnerClassCompletionVisitor.setMethodDispatcherCode(block, VariableExpression.THIS_EXPRESSION, method.getParameters());
        block = new BlockStatement();
        method = classNode.addSyntheticMethod("this$dist$set$" + objectDistance, 1, ClassHelper.VOID_TYPE, GeneralUtils.params(GeneralUtils.param(ClassHelper.STRING_TYPE, "name"), GeneralUtils.param(ClassHelper.OBJECT_TYPE, "value")), ClassNode.EMPTY_ARRAY, block);
        InnerClassCompletionVisitor.setPropertySetterDispatcher(block, VariableExpression.THIS_EXPRESSION, method.getParameters());
        block = new BlockStatement();
        method = classNode.addSyntheticMethod("this$dist$get$" + objectDistance, 1, ClassHelper.OBJECT_TYPE, GeneralUtils.params(GeneralUtils.param(ClassHelper.STRING_TYPE, "name")), ClassNode.EMPTY_ARRAY, block);
        InnerClassCompletionVisitor.setPropertyGetterDispatcher(block, VariableExpression.THIS_EXPRESSION, method.getParameters());
    }

    private void getThis(MethodVisitor mv, String classInternalName, String outerClassDescriptor, String innerClassInternalName) {
        mv.visitVarInsn(25, 0);
        if (this.thisField != null && ClassHelper.CLOSURE_TYPE.equals(this.thisField.getType())) {
            mv.visitFieldInsn(180, classInternalName, "this$0", CLOSURE_DESCRIPTOR);
            mv.visitMethodInsn(182, CLOSURE_INTERNAL_NAME, "getThisObject", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(192, innerClassInternalName);
        } else {
            mv.visitFieldInsn(180, classInternalName, "this$0", outerClassDescriptor);
        }
    }

    private void addMopMethods(InnerClassNode node) {
        boolean isStatic = InnerClassCompletionVisitor.isStatic(node);
        ClassNode outerClass = node.getOuterClass();
        final int outerClassDistance = InnerClassCompletionVisitor.getObjectDistance(outerClass);
        final String classInternalName = BytecodeHelper.getClassInternalName(node);
        final String outerClassInternalName = InnerClassCompletionVisitor.getInternalName(outerClass, isStatic);
        final String outerClassDescriptor = InnerClassCompletionVisitor.getTypeDescriptor(outerClass, isStatic);
        this.addSyntheticMethod(node, "methodMissing", 1, ClassHelper.OBJECT_TYPE, GeneralUtils.params(GeneralUtils.param(ClassHelper.STRING_TYPE, "name"), GeneralUtils.param(ClassHelper.OBJECT_TYPE, "args")), (methodBody, parameters) -> {
            if (isStatic) {
                InnerClassCompletionVisitor.setMethodDispatcherCode(methodBody, GeneralUtils.classX(outerClass), parameters);
            } else {
                methodBody.addStatement(new BytecodeSequence(new BytecodeInstruction(){

                    @Override
                    public void visit(MethodVisitor mv) {
                        InnerClassCompletionVisitor.this.getThis(mv, classInternalName, outerClassDescriptor, outerClassInternalName);
                        mv.visitVarInsn(25, 1);
                        mv.visitVarInsn(25, 2);
                        mv.visitMethodInsn(182, outerClassInternalName, "this$dist$invoke$" + outerClassDistance, "(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;", false);
                        mv.visitInsn(176);
                    }
                }));
            }
        });
        this.addSyntheticMethod(node, "$static_methodMissing", 9, ClassHelper.OBJECT_TYPE, GeneralUtils.params(GeneralUtils.param(ClassHelper.STRING_TYPE, "name"), GeneralUtils.param(ClassHelper.OBJECT_TYPE, "args")), (methodBody, parameters) -> InnerClassCompletionVisitor.setMethodDispatcherCode(methodBody, GeneralUtils.classX(outerClass), parameters));
        this.addSyntheticMethod(node, "propertyMissing", 1, ClassHelper.VOID_TYPE, GeneralUtils.params(GeneralUtils.param(ClassHelper.STRING_TYPE, "name"), GeneralUtils.param(ClassHelper.OBJECT_TYPE, "value")), (methodBody, parameters) -> {
            if (isStatic) {
                InnerClassCompletionVisitor.setPropertySetterDispatcher(methodBody, GeneralUtils.classX(outerClass), parameters);
            } else {
                methodBody.addStatement(new BytecodeSequence(new BytecodeInstruction(){

                    @Override
                    public void visit(MethodVisitor mv) {
                        InnerClassCompletionVisitor.this.getThis(mv, classInternalName, outerClassDescriptor, outerClassInternalName);
                        mv.visitVarInsn(25, 1);
                        mv.visitVarInsn(25, 2);
                        mv.visitMethodInsn(182, outerClassInternalName, "this$dist$set$" + outerClassDistance, "(Ljava/lang/String;Ljava/lang/Object;)V", false);
                        mv.visitInsn(177);
                    }
                }));
            }
        });
        this.addSyntheticMethod(node, "$static_propertyMissing", 9, ClassHelper.VOID_TYPE, GeneralUtils.params(GeneralUtils.param(ClassHelper.STRING_TYPE, "name"), GeneralUtils.param(ClassHelper.OBJECT_TYPE, "value")), (methodBody, parameters) -> InnerClassCompletionVisitor.setPropertySetterDispatcher(methodBody, GeneralUtils.classX(outerClass), parameters));
        this.addSyntheticMethod(node, "propertyMissing", 1, ClassHelper.OBJECT_TYPE, GeneralUtils.params(GeneralUtils.param(ClassHelper.STRING_TYPE, "name")), (methodBody, parameters) -> {
            if (isStatic) {
                InnerClassCompletionVisitor.setPropertyGetterDispatcher(methodBody, GeneralUtils.classX(outerClass), parameters);
            } else {
                methodBody.addStatement(new BytecodeSequence(new BytecodeInstruction(){

                    @Override
                    public void visit(MethodVisitor mv) {
                        InnerClassCompletionVisitor.this.getThis(mv, classInternalName, outerClassDescriptor, outerClassInternalName);
                        mv.visitVarInsn(25, 1);
                        mv.visitMethodInsn(182, outerClassInternalName, "this$dist$get$" + outerClassDistance, "(Ljava/lang/String;)Ljava/lang/Object;", false);
                        mv.visitInsn(176);
                    }
                }));
            }
        });
        this.addSyntheticMethod(node, "$static_propertyMissing", 9, ClassHelper.OBJECT_TYPE, GeneralUtils.params(GeneralUtils.param(ClassHelper.STRING_TYPE, "name")), (methodBody, parameters) -> InnerClassCompletionVisitor.setPropertyGetterDispatcher(methodBody, GeneralUtils.classX(outerClass), parameters));
    }

    private void addSyntheticMethod(InnerClassNode innerClass, String methodName, int modifiers, ClassNode returnType, Parameter[] parameters, BiConsumer<BlockStatement, Parameter[]> consumer) {
        MethodNode method = innerClass.getDeclaredMethod(methodName, parameters);
        if (method == null) {
            BlockStatement methodBody = GeneralUtils.block(new Statement[0]);
            consumer.accept(methodBody, parameters);
            innerClass.addSyntheticMethod(methodName, modifiers, returnType, parameters, ClassNode.EMPTY_ARRAY, methodBody);
        } else if (InnerClassCompletionVisitor.isStatic(innerClass) && (method.getModifiers() & 0x1000) == 0) {
            this.addError("\"" + methodName + "\" implementations are not supported on static inner classes as a synthetic version of \"" + methodName + "\" is added during compilation for the purpose of outer class delegation.", method);
        }
    }

    private void addThisReference(ConstructorNode node) {
        Parameter thisPara;
        if (!InnerClassCompletionVisitor.shouldHandleImplicitThisForInnerClass(this.classNode)) {
            return;
        }
        Parameter[] params = node.getParameters();
        Parameter[] newParams = new Parameter[params.length + 1];
        System.arraycopy(params, 0, newParams, 1, params.length);
        String name = this.getUniqueName(params, node);
        newParams[0] = thisPara = new Parameter(this.classNode.getOuterClass().getPlainNodeReference(), name);
        node.setParameters(newParams);
        BlockStatement block = MethodNodeUtils.getCodeAsBlock(node);
        BlockStatement newCode = GeneralUtils.block(new Statement[0]);
        InnerClassCompletionVisitor.addFieldInit(thisPara, this.thisField, newCode);
        ConstructorCallExpression cce = ConstructorNodeUtils.getFirstIfSpecialConstructorCall(block);
        if (cce == null) {
            cce = GeneralUtils.ctorSuperX(new TupleExpression());
            block.getStatements().add(0, GeneralUtils.stmt(cce));
        }
        if (this.shouldImplicitlyPassThisPara(cce)) {
            TupleExpression args = (TupleExpression)cce.getArguments();
            List<Expression> expressions = args.getExpressions();
            VariableExpression ve = GeneralUtils.varX(thisPara.getName());
            ve.setAccessedVariable(thisPara);
            expressions.add(0, ve);
        }
        if (cce.isSuperCall()) {
            block.getStatements().add(1, newCode);
        }
        node.setCode(block);
    }

    private boolean shouldImplicitlyPassThisPara(ConstructorCallExpression cce) {
        InnerClassNode superInnerCN;
        boolean pass = false;
        ClassNode superCN = this.classNode.getSuperClass();
        if (cce.isThisCall()) {
            pass = true;
        } else if (cce.isSuperCall() && !superCN.isEnum() && !superCN.isInterface() && superCN instanceof InnerClassNode && !InnerClassCompletionVisitor.isStatic(superInnerCN = (InnerClassNode)superCN) && this.classNode.getOuterClass().isDerivedFrom(superCN.getOuterClass())) {
            pass = true;
        }
        return pass;
    }

    private String getUniqueName(Parameter[] params, ConstructorNode node) {
        String namePrefix = "$p";
        block0: for (int i = 0; i < 100; ++i) {
            namePrefix = namePrefix + "$";
            for (Parameter p : params) {
                if (p.getName().equals(namePrefix)) continue block0;
            }
            return namePrefix;
        }
        this.addError("unable to find a unique prefix name for synthetic this reference in inner class constructor", node);
        return namePrefix;
    }
}

