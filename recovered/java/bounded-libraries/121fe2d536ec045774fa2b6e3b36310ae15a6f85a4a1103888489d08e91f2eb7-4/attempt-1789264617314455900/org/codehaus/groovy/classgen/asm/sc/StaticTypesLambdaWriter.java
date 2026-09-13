/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm.sc;

import groovyjarjarasm.asm.MethodVisitor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.CodeVisitorSupport;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.builder.AstStringCompiler;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.LambdaExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.tools.ClosureUtils;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.classgen.BytecodeInstruction;
import org.codehaus.groovy.classgen.BytecodeSequence;
import org.codehaus.groovy.classgen.asm.BytecodeHelper;
import org.codehaus.groovy.classgen.asm.ClosureWriter;
import org.codehaus.groovy.classgen.asm.CompileStack;
import org.codehaus.groovy.classgen.asm.LambdaWriter;
import org.codehaus.groovy.classgen.asm.OperandStack;
import org.codehaus.groovy.classgen.asm.WriterController;
import org.codehaus.groovy.classgen.asm.WriterControllerFactory;
import org.codehaus.groovy.classgen.asm.sc.AbstractFunctionalInterfaceWriter;
import org.codehaus.groovy.classgen.asm.sc.StaticTypesClosureWriter;
import org.codehaus.groovy.transform.sc.StaticCompilationMetadataKeys;
import org.codehaus.groovy.transform.stc.StaticTypesMarker;

public class StaticTypesLambdaWriter
extends LambdaWriter
implements AbstractFunctionalInterfaceWriter {
    private static final String IS_GENERATED_CONSTRUCTOR = "__IS_GENERATED_CONSTRUCTOR";
    private static final String LAMBDA_SHARED_VARIABLES = "__LAMBDA_SHARED_VARIABLES";
    private final StaticTypesClosureWriter staticTypesClosureWriter;
    private final Map<Expression, ClassNode> lambdaClassNodes = new HashMap<Expression, ClassNode>();

    public StaticTypesLambdaWriter(WriterController controller) {
        super(controller);
        this.staticTypesClosureWriter = new StaticTypesClosureWriter(controller);
    }

    @Override
    public void writeLambda(LambdaExpression expression) {
        ClassNode functionalInterface = this.getFunctionalInterfaceType(expression);
        if (functionalInterface == null || !functionalInterface.isInterface()) {
            super.writeLambda(expression);
            return;
        }
        MethodNode abstractMethod = ClassHelper.findSAM(functionalInterface.redirect());
        if (abstractMethod == null) {
            super.writeLambda(expression);
            return;
        }
        if (!expression.isSerializable() && functionalInterface.implementsInterface(ClassHelper.SERIALIZABLE_TYPE)) {
            expression.setSerializable(true);
        }
        ClassNode enclosingClass = this.controller.getClassNode();
        int modifiers = 17;
        if (enclosingClass.isInterface()) {
            modifiers |= 8;
        }
        ClassNode lambdaClass = this.getOrAddLambdaClass(expression, modifiers, abstractMethod);
        MethodNode lambdaMethod = lambdaClass.getMethods("doCall").get(0);
        boolean canDeserialize = enclosingClass.hasMethod(StaticTypesLambdaWriter.createDeserializeLambdaMethodName(lambdaClass), StaticTypesLambdaWriter.createDeserializeLambdaMethodParams());
        if (!canDeserialize) {
            if (expression.isSerializable()) {
                this.addDeserializeLambdaMethodForEachLambdaExpression(expression, lambdaClass);
                this.addDeserializeLambdaMethod();
            }
            this.newGroovyLambdaWrapperAndLoad(lambdaClass, expression, StaticTypesLambdaWriter.isAccessingInstanceMembersOfEnclosingClass(lambdaMethod));
        }
        MethodVisitor mv = this.controller.getMethodVisitor();
        mv.visitInvokeDynamicInsn(abstractMethod.getName(), this.createAbstractMethodDesc(functionalInterface.redirect(), lambdaClass), this.createBootstrapMethod(enclosingClass.isInterface(), expression.isSerializable()), this.createBootstrapMethodArguments(this.createMethodDescriptor(abstractMethod), 5, lambdaClass, lambdaMethod, expression.isSerializable()));
        if (expression.isSerializable()) {
            mv.visitTypeInsn(192, "java/io/Serializable");
        }
        OperandStack operandStack = this.controller.getOperandStack();
        operandStack.replace(functionalInterface.redirect(), 1);
    }

    private static Parameter[] createDeserializeLambdaMethodParams() {
        return new Parameter[]{new Parameter(ClassHelper.SERIALIZEDLAMBDA_TYPE, "serializedLambda")};
    }

    private static boolean isAccessingInstanceMembersOfEnclosingClass(MethodNode lambdaMethod) {
        final boolean[] result = new boolean[1];
        final ClassNode enclosingClass = lambdaMethod.getDeclaringClass().getOuterClass();
        lambdaMethod.getCode().visit(new CodeVisitorSupport(){

            @Override
            public void visitVariableExpression(VariableExpression expression) {
                if (expression.isThisExpression() || enclosingClass.equals(expression.getNodeMetaData((Object)StaticCompilationMetadataKeys.PROPERTY_OWNER))) {
                    result[0] = true;
                }
            }
        });
        return result[0];
    }

    private void newGroovyLambdaWrapperAndLoad(ClassNode lambdaClass, LambdaExpression expression, boolean accessingInstanceMembers) {
        CompileStack compileStack = this.controller.getCompileStack();
        OperandStack operandStack = this.controller.getOperandStack();
        MethodVisitor mv = this.controller.getMethodVisitor();
        String lambdaClassInternalName = BytecodeHelper.getClassInternalName(lambdaClass);
        mv.visitTypeInsn(187, lambdaClassInternalName);
        mv.visitInsn(89);
        if (this.controller.isStaticMethod() || compileStack.isInSpecialConstructorCall() || !accessingInstanceMembers) {
            GeneralUtils.classX(this.controller.getThisType()).visit(this.controller.getAcg());
        } else {
            this.loadThis();
        }
        operandStack.dup();
        this.loadSharedVariables(expression);
        Optional<ConstructorNode> generatedConstructor = lambdaClass.getDeclaredConstructors().stream().filter(ctor -> Boolean.TRUE.equals(ctor.getNodeMetaData(IS_GENERATED_CONSTRUCTOR))).findFirst();
        if (!generatedConstructor.isPresent()) {
            throw new GroovyBugError("Failed to find the generated constructor");
        }
        Parameter[] lambdaClassConstructorParameters = generatedConstructor.get().getParameters();
        mv.visitMethodInsn(183, lambdaClassInternalName, "<init>", BytecodeHelper.getMethodDescriptor(ClassHelper.VOID_TYPE, lambdaClassConstructorParameters), lambdaClass.isInterface());
        operandStack.replace(ClassHelper.CLOSURE_TYPE, lambdaClassConstructorParameters.length);
    }

    private void loadSharedVariables(LambdaExpression expression) {
        Parameter[] lambdaSharedVariableParameters;
        for (Parameter parameter : lambdaSharedVariableParameters = (Parameter[])expression.getNodeMetaData(LAMBDA_SHARED_VARIABLES)) {
            StaticTypesLambdaWriter.loadReference(parameter.getName(), this.controller);
            if (parameter.getNodeMetaData(ClosureWriter.UseExistingReference.class) != null) continue;
            parameter.setNodeMetaData(ClosureWriter.UseExistingReference.class, Boolean.TRUE);
        }
    }

    private String createAbstractMethodDesc(ClassNode functionalInterface, ClassNode lambdaClass) {
        LinkedList<Parameter> lambdaSharedVariables = new LinkedList<Parameter>();
        this.prependParameter(lambdaSharedVariables, "__lambda_this", lambdaClass);
        return BytecodeHelper.getMethodDescriptor(functionalInterface, lambdaSharedVariables.toArray(Parameter.EMPTY_ARRAY));
    }

    private ClassNode getOrAddLambdaClass(LambdaExpression expression, int modifiers, MethodNode abstractMethod) {
        return this.lambdaClassNodes.computeIfAbsent(expression, key -> {
            ClassNode lambdaClass = this.createLambdaClass(expression, modifiers, abstractMethod);
            this.controller.getAcg().addInnerClass(lambdaClass);
            lambdaClass.addInterface(ClassHelper.GENERATED_LAMBDA_TYPE);
            lambdaClass.putNodeMetaData((Object)StaticCompilationMetadataKeys.STATIC_COMPILE_NODE, Boolean.TRUE);
            lambdaClass.putNodeMetaData(WriterControllerFactory.class, x -> this.controller);
            return lambdaClass;
        });
    }

    @Override
    protected ClassNode createClosureClass(ClosureExpression expression, int modifiers) {
        return this.staticTypesClosureWriter.createClosureClass(expression, modifiers);
    }

    protected ClassNode createLambdaClass(LambdaExpression expression, int modifiers, MethodNode abstractMethod) {
        ClassNode enclosingClass = this.controller.getClassNode();
        ClassNode outermostClass = this.controller.getOutermostClass();
        boolean staticMethodOrInStaticClass = this.controller.isStaticMethod() || enclosingClass.isStaticClass();
        InnerClassNode lambdaClass = new InnerClassNode(enclosingClass, this.nextLambdaClassName(), modifiers, ClassHelper.CLOSURE_TYPE.getPlainNodeReference());
        lambdaClass.setEnclosingMethod(this.controller.getMethodNode());
        lambdaClass.setSourcePosition(expression);
        lambdaClass.setSynthetic(true);
        if (this.controller.isInScriptBody()) {
            lambdaClass.setScriptBody(true);
        }
        if (staticMethodOrInStaticClass) {
            lambdaClass.setStaticClass(true);
        }
        if (expression.isSerializable()) {
            StaticTypesLambdaWriter.addSerialVersionUIDField(lambdaClass);
        }
        MethodNode syntheticLambdaMethodNode = this.addSyntheticLambdaMethodNode(expression, lambdaClass, abstractMethod);
        Parameter[] localVariableParameters = (Parameter[])expression.getNodeMetaData(LAMBDA_SHARED_VARIABLES);
        this.addFieldsAndGettersForLocalVariables(lambdaClass, localVariableParameters);
        ConstructorNode constructorNode = this.addConstructor(expression, localVariableParameters, lambdaClass, this.createBlockStatementForConstructor(expression, outermostClass, enclosingClass));
        constructorNode.putNodeMetaData(IS_GENERATED_CONSTRUCTOR, Boolean.TRUE);
        syntheticLambdaMethodNode.getCode().visit(new ClosureWriter.CorrectAccessedVariableVisitor(lambdaClass));
        return lambdaClass;
    }

    private String nextLambdaClassName() {
        ClassNode enclosingClass = this.controller.getClassNode();
        ClassNode outermostClass = this.controller.getOutermostClass();
        return enclosingClass.getName() + "$" + this.controller.getContext().getNextLambdaInnerName(outermostClass, enclosingClass, this.controller.getMethodNode());
    }

    private static void addSerialVersionUIDField(ClassNode lambdaClass) {
        lambdaClass.addFieldFirst("serialVersionUID", 26, ClassHelper.long_TYPE, GeneralUtils.constX(-1L, true));
    }

    private MethodNode addSyntheticLambdaMethodNode(LambdaExpression expression, ClassNode lambdaClass, MethodNode abstractMethod) {
        Parameter[] parametersWithExactType = this.createParametersWithExactType(expression, abstractMethod);
        Parameter[] localVariableParameters = this.getLambdaSharedVariables(expression);
        StaticTypesLambdaWriter.removeInitialValues(localVariableParameters);
        MethodNode doCallMethod = lambdaClass.addMethod("doCall", 1, abstractMethod.getReturnType(), (Parameter[])parametersWithExactType.clone(), ClassNode.EMPTY_ARRAY, expression.getCode());
        doCallMethod.putNodeMetaData("__ORIGINAL_PARAMETERS_WITH_EXACT_TYPE", parametersWithExactType);
        expression.putNodeMetaData(LAMBDA_SHARED_VARIABLES, localVariableParameters);
        doCallMethod.setSourcePosition(expression);
        return doCallMethod;
    }

    private Parameter[] createParametersWithExactType(LambdaExpression expression, MethodNode abstractMethod) {
        Parameter[] targetParameters = GeneralUtils.cloneParams(abstractMethod.getParameters());
        Parameter[] parameters = ClosureUtils.getParametersSafe(expression);
        int n = parameters.length;
        for (int i = 0; i < n; ++i) {
            Parameter targetParameter = targetParameters[i];
            Parameter parameter = parameters[i];
            ClassNode inferredType = (ClassNode)parameter.getNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE);
            if (inferredType == null) continue;
            ClassNode type = this.convertParameterType(targetParameter.getType(), parameter.getType(), inferredType);
            parameter.setOriginType(type);
            parameter.setType(type);
        }
        return parameters;
    }

    private void addDeserializeLambdaMethod() {
        Parameter[] parameters;
        ClassNode enclosingClass = this.controller.getClassNode();
        if (enclosingClass.hasMethod("$deserializeLambda$", parameters = StaticTypesLambdaWriter.createDeserializeLambdaMethodParams())) {
            return;
        }
        BlockStatement code = GeneralUtils.block(GeneralUtils.declS(GeneralUtils.localVarX("enclosingClass", ClassHelper.OBJECT_TYPE), GeneralUtils.classX(enclosingClass)), ((BlockStatement)new AstStringCompiler().compile("return enclosingClass.getDeclaredMethod(\"\\$deserializeLambda_${serializedLambda.getImplClass().replace('/', '$')}\\$\", serializedLambda.getClass()).invoke(null, serializedLambda)").get(0)).getStatements().get(0));
        enclosingClass.addSyntheticMethod("$deserializeLambda$", 10, ClassHelper.OBJECT_TYPE, parameters, ClassNode.EMPTY_ARRAY, code);
    }

    private void addDeserializeLambdaMethodForEachLambdaExpression(LambdaExpression expression, final ClassNode lambdaClass) {
        ClassNode enclosingClass = this.controller.getClassNode();
        BlockStatement code = GeneralUtils.block(new BytecodeSequence(new BytecodeInstruction(){

            @Override
            public void visit(MethodVisitor mv) {
                mv.visitVarInsn(25, 0);
                mv.visitInsn(3);
                mv.visitMethodInsn(182, "java/lang/invoke/SerializedLambda", "getCapturedArg", "(I)Ljava/lang/Object;", false);
                mv.visitTypeInsn(192, BytecodeHelper.getClassInternalName(lambdaClass));
                OperandStack operandStack = StaticTypesLambdaWriter.this.controller.getOperandStack();
                operandStack.push(lambdaClass);
            }
        }), GeneralUtils.returnS(expression));
        enclosingClass.addSyntheticMethod(StaticTypesLambdaWriter.createDeserializeLambdaMethodName(lambdaClass), 9, ClassHelper.OBJECT_TYPE, StaticTypesLambdaWriter.createDeserializeLambdaMethodParams(), ClassNode.EMPTY_ARRAY, code);
    }

    private static String createDeserializeLambdaMethodName(ClassNode lambdaClass) {
        return "$deserializeLambda_" + lambdaClass.getName().replace('.', '$') + "$";
    }
}

