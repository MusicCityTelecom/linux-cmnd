/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm.sc;

import groovy.lang.Tuple;
import groovy.lang.Tuple2;
import groovyjarjarasm.asm.Handle;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ArrayExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.MethodReferenceExpression;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.ParameterUtils;
import org.codehaus.groovy.classgen.asm.BytecodeHelper;
import org.codehaus.groovy.classgen.asm.MethodReferenceExpressionWriter;
import org.codehaus.groovy.classgen.asm.WriterController;
import org.codehaus.groovy.classgen.asm.sc.AbstractFunctionalInterfaceWriter;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.syntax.RuntimeParserException;
import org.codehaus.groovy.transform.sc.StaticCompilationMetadataKeys;
import org.codehaus.groovy.transform.stc.ExtensionMethodNode;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingSupport;
import org.codehaus.groovy.transform.stc.StaticTypesMarker;

public class StaticTypesMethodReferenceExpressionWriter
extends MethodReferenceExpressionWriter
implements AbstractFunctionalInterfaceWriter {
    public StaticTypesMethodReferenceExpressionWriter(WriterController controller) {
        super(controller);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void writeMethodReferenceExpression(MethodReferenceExpression methodReferenceExpression) {
        Parameter[] parameterArray;
        MethodNode methodRefMethod;
        ClassNode functionalInterfaceType = this.getFunctionalInterfaceType(methodReferenceExpression);
        if (!ClassHelper.isFunctionalInterface(functionalInterfaceType)) {
            super.writeMethodReferenceExpression(methodReferenceExpression);
            return;
        }
        ClassNode redirect = functionalInterfaceType.redirect();
        MethodNode abstractMethod = ClassHelper.findSAM(redirect);
        String abstractMethodDesc = this.createMethodDescriptor(abstractMethod);
        ClassNode classNode = this.controller.getClassNode();
        Expression typeOrTargetRef = methodReferenceExpression.getExpression();
        boolean isClassExpression = typeOrTargetRef instanceof ClassExpression;
        boolean targetIsArgument = false;
        ClassNode typeOrTargetRefType = isClassExpression ? typeOrTargetRef.getType() : this.controller.getTypeChooser().resolveType(typeOrTargetRef, classNode);
        ClassNode[] methodReferenceParamTypes = (ClassNode[])methodReferenceExpression.getNodeMetaData((Object)StaticTypesMarker.CLOSURE_ARGUMENTS);
        Parameter[] parametersWithExactType = this.createParametersWithExactType(abstractMethod, methodReferenceParamTypes);
        String methodRefName = methodReferenceExpression.getMethodName().getText();
        boolean isConstructorReference = StaticTypesMethodReferenceExpressionWriter.isConstructorReference(methodRefName);
        if (isConstructorReference) {
            methodRefName = this.controller.getContext().getNextConstructorReferenceSyntheticMethodName(this.controller.getMethodNode());
            methodRefMethod = this.addSyntheticMethodForConstructorReference(methodRefName, typeOrTargetRefType, parametersWithExactType);
        } else {
            methodRefMethod = this.findMethodRefMethod(methodRefName, parametersWithExactType, typeOrTargetRef, typeOrTargetRefType);
        }
        this.validate(methodReferenceExpression, typeOrTargetRef, typeOrTargetRefType, methodRefName, parametersWithExactType, methodRefMethod);
        if (StaticTypesMethodReferenceExpressionWriter.isExtensionMethod(methodRefMethod)) {
            ExtensionMethodNode extensionMethodNode = (ExtensionMethodNode)methodRefMethod;
            methodRefMethod = extensionMethodNode.getExtensionMethodNode();
            boolean isStatic = extensionMethodNode.isStaticExtension();
            if (isStatic) {
                methodRefMethod = this.addSyntheticMethodForDGSM(methodRefMethod);
            }
            if (isStatic || isClassExpression) {
                typeOrTargetRefType = methodRefMethod.getDeclaringClass();
                typeOrTargetRef = StaticTypesMethodReferenceExpressionWriter.makeClassTarget(typeOrTargetRefType, typeOrTargetRef);
            } else {
                targetIsArgument = true;
            }
        } else if (ParameterUtils.isVargs(methodRefMethod.getParameters())) {
            int mParameters = abstractMethod.getParameters().length;
            int nParameters = methodRefMethod.getParameters().length;
            if (StaticTypesMethodReferenceExpressionWriter.isTypeReferringInstanceMethod(typeOrTargetRef, methodRefMethod)) {
                ++nParameters;
            }
            if (mParameters > nParameters || mParameters == nParameters - 1 || mParameters == nParameters && !StaticTypeCheckingSupport.isAssignableTo(DefaultGroovyMethods.last(parametersWithExactType).getType(), DefaultGroovyMethods.last(methodRefMethod.getParameters()).getType())) {
                if (!(isClassExpression || methodRefMethod.isStatic() || methodRefMethod.getDeclaringClass().equals(classNode))) {
                    targetIsArgument = true;
                    ++mParameters;
                }
                if ((methodRefMethod = this.addSyntheticMethodForVariadicReference(methodRefMethod, mParameters, isClassExpression || targetIsArgument)).isStatic() && !targetIsArgument) {
                    typeOrTargetRefType = methodRefMethod.getDeclaringClass();
                    typeOrTargetRef = StaticTypesMethodReferenceExpressionWriter.makeClassTarget(typeOrTargetRefType, typeOrTargetRef);
                }
            }
        }
        if (!isClassExpression) {
            if (isConstructorReference) {
                this.addFatalError("Constructor reference must be TypeName::new", methodReferenceExpression);
            } else if (methodRefMethod.isStatic() && !targetIsArgument) {
                typeOrTargetRef = StaticTypesMethodReferenceExpressionWriter.makeClassTarget(typeOrTargetRefType, typeOrTargetRef);
                isClassExpression = true;
            } else {
                typeOrTargetRef.visit(this.controller.getAcg());
            }
        }
        int referenceKind = isConstructorReference || methodRefMethod.isStatic() ? 6 : (methodRefMethod.getDeclaringClass().isInterface() ? 9 : 5);
        String methodName = abstractMethod.getName();
        ClassNode classNode2 = functionalInterfaceType.redirect();
        if (isClassExpression) {
            parameterArray = Parameter.EMPTY_ARRAY;
        } else {
            Parameter[] parameterArray2 = new Parameter[1];
            parameterArray = parameterArray2;
            parameterArray2[0] = new Parameter(typeOrTargetRefType, "__METHODREF_EXPR_INSTANCE");
        }
        String methodDesc = BytecodeHelper.getMethodDescriptor(classNode2, parameterArray);
        methodRefMethod.putNodeMetaData("__ORIGINAL_PARAMETERS_WITH_EXACT_TYPE", parametersWithExactType);
        try {
            Handle bootstrapMethod = this.createBootstrapMethod(classNode.isInterface(), false);
            Object[] bootstrapArgs = this.createBootstrapMethodArguments(abstractMethodDesc, referenceKind, methodRefMethod.getDeclaringClass(), methodRefMethod, false);
            this.controller.getMethodVisitor().visitInvokeDynamicInsn(methodName, methodDesc, bootstrapMethod, bootstrapArgs);
        }
        finally {
            methodRefMethod.removeNodeMetaData("__ORIGINAL_PARAMETERS_WITH_EXACT_TYPE");
        }
        if (isClassExpression) {
            this.controller.getOperandStack().push(redirect);
        } else {
            this.controller.getOperandStack().replace(redirect, 1);
        }
    }

    private void validate(MethodReferenceExpression methodReferenceExpression, Expression typeOrTargetRef, ClassNode typeOrTargetRefType, String methodRefName, Parameter[] parametersWithExactType, MethodNode methodRefMethod) {
        ClassNode firstParameterType;
        if (methodRefMethod == null) {
            this.addFatalError("Failed to find the expected method[" + methodRefName + "(" + Arrays.stream(parametersWithExactType).map(e -> e.getType().getText()).collect(Collectors.joining(",")) + ")] in the type[" + typeOrTargetRefType.getText() + "]", methodReferenceExpression);
        } else if (parametersWithExactType.length > 0 && StaticTypesMethodReferenceExpressionWriter.isTypeReferringInstanceMethod(typeOrTargetRef, methodRefMethod) && !StaticTypeCheckingSupport.isAssignableTo(firstParameterType = parametersWithExactType[0].getType(), typeOrTargetRefType)) {
            throw new RuntimeParserException("Invalid receiver type: " + firstParameterType.getText() + " is not compatible with " + typeOrTargetRefType.getText(), typeOrTargetRef);
        }
    }

    private MethodNode addSyntheticMethodForDGSM(MethodNode mn) {
        Parameter[] parameters = StaticTypesMethodReferenceExpressionWriter.removeFirstParameter(mn.getParameters());
        ArgumentListExpression args = new ArgumentListExpression(parameters);
        args.getExpressions().add(0, GeneralUtils.nullX());
        MethodCallExpression methodCall = GeneralUtils.callX((Expression)GeneralUtils.classX(mn.getDeclaringClass()), mn.getName(), (Expression)args);
        methodCall.setImplicitThis(false);
        methodCall.setMethodTarget(mn);
        methodCall.putNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET, mn);
        String methodName = "dgsm$$" + mn.getParameters()[0].getType().getName().replace('.', '$') + "$$" + mn.getName();
        MethodNode delegateMethod = this.addSyntheticMethod(methodName, mn.getReturnType(), methodCall, parameters, mn.getExceptions());
        delegateMethod.putNodeMetaData((Object)StaticCompilationMetadataKeys.STATIC_COMPILE_NODE, Boolean.TRUE);
        return delegateMethod;
    }

    private MethodNode addSyntheticMethodForVariadicReference(MethodNode mn, int samParameters, boolean isStaticTarget) {
        Expression receiver;
        ArgumentListExpression arguments;
        Parameter[] parameters = new Parameter[samParameters];
        if (mn.isStatic()) {
            int j = mn.getParameters().length - 1;
            for (int i = 0; i < samParameters; ++i) {
                ClassNode t = mn.getParameters()[Math.min(i, j)].getType();
                if (i >= j) {
                    t = t.getComponentType();
                }
                parameters[i] = new Parameter(t, "p" + i);
            }
            arguments = new ArgumentListExpression(parameters);
            receiver = GeneralUtils.classX(mn.getDeclaringClass());
        } else {
            int p = 0;
            if (isStaticTarget) {
                parameters[p++] = new Parameter(mn.getDeclaringClass(), "target");
            }
            int j = mn.getParameters().length - 1;
            int n = samParameters - p;
            for (int i = 0; i < n; ++i) {
                ClassNode t = mn.getParameters()[Math.min(i, j)].getType();
                if (i >= j) {
                    t = t.getComponentType();
                }
                parameters[p] = new Parameter(t, "p" + p);
                ++p;
            }
            if (isStaticTarget) {
                arguments = new ArgumentListExpression(StaticTypesMethodReferenceExpressionWriter.removeFirstParameter(parameters));
                receiver = GeneralUtils.varX(parameters[0]);
            } else {
                arguments = new ArgumentListExpression(parameters);
                receiver = GeneralUtils.varX("this", this.controller.getClassNode());
            }
        }
        MethodCallExpression methodCall = GeneralUtils.callX(receiver, mn.getName(), (Expression)arguments);
        methodCall.setImplicitThis(false);
        methodCall.setMethodTarget(mn);
        methodCall.putNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET, mn);
        String methodName = "adapt$" + mn.getDeclaringClass().getNameWithoutPackage() + "$" + mn.getName() + "$" + System.nanoTime();
        MethodNode delegateMethod = this.addSyntheticMethod(methodName, mn.getReturnType(), methodCall, parameters, mn.getExceptions());
        if (!isStaticTarget && !mn.isStatic()) {
            delegateMethod.setModifiers(delegateMethod.getModifiers() & 0xFFFFFFF7);
        }
        delegateMethod.putNodeMetaData((Object)StaticCompilationMetadataKeys.STATIC_COMPILE_NODE, Boolean.TRUE);
        delegateMethod.setGenericsTypes(mn.getGenericsTypes());
        return delegateMethod;
    }

    private MethodNode addSyntheticMethodForConstructorReference(String methodName, ClassNode returnType, Parameter[] parametersWithExactType) {
        ArgumentListExpression ctorArgs = new ArgumentListExpression(parametersWithExactType);
        Expression returnValue = returnType.isArray() ? new ArrayExpression(returnType.getComponentType(), null, ctorArgs.getExpressions()) : GeneralUtils.ctorX(returnType, ctorArgs);
        MethodNode delegateMethod = this.addSyntheticMethod(methodName, returnType, returnValue, parametersWithExactType, ClassNode.EMPTY_ARRAY);
        delegateMethod.putNodeMetaData((Object)StaticCompilationMetadataKeys.STATIC_COMPILE_NODE, Boolean.FALSE);
        return delegateMethod;
    }

    private MethodNode addSyntheticMethod(String methodName, ClassNode returnType, Expression returnValue, Parameter[] parameters, ClassNode[] exceptions) {
        return this.controller.getClassNode().addSyntheticMethod(methodName, 26, returnType, parameters, exceptions, GeneralUtils.returnS(returnValue));
    }

    private Parameter[] createParametersWithExactType(MethodNode abstractMethod, ClassNode[] inferredParamTypes) {
        Parameter[] parameters = GeneralUtils.cloneParams(abstractMethod.getParameters());
        if (inferredParamTypes != null) {
            int n = parameters.length;
            for (int i = 0; i < n; ++i) {
                Parameter parameter;
                ClassNode inferredParamType = inferredParamTypes[i];
                if (inferredParamType == null) continue;
                Parameter targetParameter = parameter = parameters[i];
                ClassNode type = this.convertParameterType(targetParameter.getType(), parameter.getType(), inferredParamType);
                parameter.setOriginType(type);
                parameter.setType(type);
            }
        }
        return parameters;
    }

    private MethodNode findMethodRefMethod(String methodName, Parameter[] samParameters, Expression typeOrTargetRef, ClassNode typeOrTargetRefType) {
        List<MethodNode> methods = this.findVisibleMethods(methodName, typeOrTargetRefType);
        methods.removeIf(method -> {
            Parameter[] parameters = method.getParameters();
            if (StaticTypesMethodReferenceExpressionWriter.isTypeReferringInstanceMethod(typeOrTargetRef, method)) {
                ClassNode firstParamType = method.getDeclaringClass();
                int n = parameters.length;
                Parameter[] plusOne = new Parameter[n + 1];
                plusOne[0] = new Parameter(firstParamType, "");
                System.arraycopy(parameters, 0, plusOne, 1, n);
                parameters = plusOne;
            }
            if (ParameterUtils.parametersCompatible(samParameters, parameters)) {
                return false;
            }
            if (ParameterUtils.isVargs(parameters)) {
                int nParameters = parameters.length;
                if (samParameters.length == nParameters - 1) {
                    if (ParameterUtils.parametersCompatible(samParameters, parameters = Arrays.copyOf(parameters, nParameters - 1))) {
                        return false;
                    }
                } else if (samParameters.length >= nParameters) {
                    Parameter p = new Parameter(parameters[nParameters - 1].getType().getComponentType(), "");
                    parameters = Arrays.copyOf(parameters, samParameters.length);
                    for (int i = nParameters - 1; i < parameters.length; ++i) {
                        parameters[i] = p;
                    }
                    if (ParameterUtils.parametersCompatible(samParameters, parameters)) {
                        return false;
                    }
                }
            }
            return true;
        });
        return StaticTypesMethodReferenceExpressionWriter.chooseMethodRefMethodCandidate(typeOrTargetRef, methods);
    }

    private List<MethodNode> findVisibleMethods(String name, ClassNode type) {
        List<MethodNode> methods = type.getMethods(name);
        methods.addAll(StaticTypeCheckingSupport.findDGMMethodsForClassNode(this.controller.getSourceUnit().getClassLoader(), type, name));
        methods = StaticTypeCheckingSupport.filterMethodsByVisibility(methods, this.controller.getClassNode());
        return methods;
    }

    private void addFatalError(String msg, ASTNode node) {
        this.controller.getSourceUnit().addFatalError(msg, node);
    }

    private static boolean isConstructorReference(String methodRefName) {
        return "new".equals(methodRefName);
    }

    private static boolean isExtensionMethod(MethodNode methodRefMethod) {
        return methodRefMethod instanceof ExtensionMethodNode;
    }

    private static boolean isTypeReferringInstanceMethod(Expression typeOrTargetRef, MethodNode mn) {
        return typeOrTargetRef instanceof ClassExpression && (mn != null && !mn.isStatic() || StaticTypesMethodReferenceExpressionWriter.isExtensionMethod(mn) && !((ExtensionMethodNode)mn).isStaticExtension());
    }

    private static Expression makeClassTarget(ClassNode target, Expression source) {
        ClassExpression expression = GeneralUtils.classX(target);
        expression.setSourcePosition(source);
        return expression;
    }

    private static Parameter[] removeFirstParameter(Parameter[] parameters) {
        return Arrays.copyOfRange(parameters, 1, parameters.length);
    }

    private static MethodNode chooseMethodRefMethodCandidate(Expression methodRef, List<MethodNode> candidates) {
        if (candidates.size() == 1) {
            return candidates.get(0);
        }
        return candidates.stream().map(e -> Tuple.tuple(e, StaticTypesMethodReferenceExpressionWriter.matchingScore(e, methodRef))).min((t1, t2) -> Integer.compare((Integer)t2.getV2(), (Integer)t1.getV2())).map(Tuple2::getV1).orElse(null);
    }

    private static Integer matchingScore(MethodNode mn, Expression typeOrTargetRef) {
        ClassNode typeOrTargetRefType = typeOrTargetRef.getType();
        int score = 9;
        for (ClassNode cn = mn.getDeclaringClass(); null != cn && !cn.equals(typeOrTargetRefType); cn = cn.getSuperClass()) {
            --score;
        }
        if (score < 0) {
            score = 0;
        }
        score *= 10;
        if (typeOrTargetRef instanceof ClassExpression == mn.isStatic()) {
            score += 9;
        }
        if (StaticTypesMethodReferenceExpressionWriter.isExtensionMethod(mn)) {
            score += 100;
        }
        return score;
    }
}

