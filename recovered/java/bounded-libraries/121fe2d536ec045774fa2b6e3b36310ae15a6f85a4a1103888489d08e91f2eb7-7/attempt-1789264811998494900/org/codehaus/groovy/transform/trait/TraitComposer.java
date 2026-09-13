/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.trait;

import groovy.transform.CompileStatic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ArrayExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.CastExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.classgen.asm.BytecodeHelper;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.runtime.MetaClassHelper;
import org.codehaus.groovy.syntax.SyntaxException;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.transform.ASTTransformationCollectorCodeVisitor;
import org.codehaus.groovy.transform.sc.StaticCompileTransformation;
import org.codehaus.groovy.transform.stc.StaticTypesMarker;
import org.codehaus.groovy.transform.trait.SuperCallTraitTransformer;
import org.codehaus.groovy.transform.trait.TraitHelpersTuple;
import org.codehaus.groovy.transform.trait.Traits;

public abstract class TraitComposer {
    public static final ClassNode COMPILESTATIC_CLASSNODE = ClassHelper.make(CompileStatic.class);

    public static void doExtendTraits(ClassNode cn, SourceUnit su, CompilationUnit cu) {
        if (cn.isInterface()) {
            return;
        }
        if (Traits.isTrait(cn)) {
            TraitComposer.checkTraitAllowed(cn, su);
            return;
        }
        if (!cn.getNameWithoutPackage().endsWith("$Trait$Helper")) {
            ClassCodeVisitorSupport visitor = new SuperCallTraitTransformer(su);
            for (ClassNode trait : Traits.findTraits(cn)) {
                TraitComposer.applyTrait(trait, cn, Traits.findHelpers(trait), su);
                visitor.visitClass(cn);
            }
            if (su != null) {
                visitor = new ASTTransformationCollectorCodeVisitor(su, cu.getTransformLoader());
                visitor.visitClass(cn);
            }
        }
    }

    private static void checkTraitAllowed(ClassNode bottomTrait, SourceUnit unit) {
        ClassNode superClass = bottomTrait.getSuperClass();
        if (superClass == null || ClassHelper.isObjectType(superClass)) {
            return;
        }
        if (!Traits.isTrait(superClass)) {
            unit.addError(new SyntaxException("A trait can only inherit from another trait", superClass.getLineNumber(), superClass.getColumnNumber()));
        }
    }

    private static void applyTrait(ClassNode trait, ClassNode cNode, TraitHelpersTuple helpers, SourceUnit unit) {
        ClassNode helperClassNode = helpers.getHelper();
        ClassNode fieldHelperClassNode = helpers.getFieldHelper();
        ClassNode staticFieldHelperClassNode = helpers.getStaticFieldHelper();
        Map<String, ClassNode> genericsSpec = GenericsUtils.createGenericsSpec(trait, GenericsUtils.createGenericsSpec(cNode));
        for (MethodNode methodNode : helperClassNode.getAllDeclaredMethods()) {
            String name = methodNode.getName();
            Parameter[] helperMethodParams = methodNode.getParameters();
            int nParams = helperMethodParams.length;
            if (nParams <= 0 || methodNode.isAbstract() || (methodNode.getModifiers() & 8) == 0 || name.contains("$") && (methodNode.getModifiers() & 0x1000) != 0) continue;
            ArgumentListExpression argList = new ArgumentListExpression();
            argList.addExpression(new VariableExpression("this"));
            Parameter[] origParams = new Parameter[nParams - 1];
            Parameter[] params = new Parameter[nParams - 1];
            System.arraycopy(methodNode.getParameters(), 1, params, 0, params.length);
            MethodNode originalMethod = trait.getMethod(name, params);
            Map<String, ClassNode> methodGenericsSpec = GenericsUtils.addMethodGenerics(Optional.ofNullable(originalMethod).orElse(methodNode), genericsSpec);
            for (int i = 1; i < nParams; ++i) {
                Parameter parameter = helperMethodParams[i];
                ClassNode originType = parameter.getOriginType();
                ClassNode fixedType = GenericsUtils.correctToGenericsSpecRecurse(methodGenericsSpec, originType);
                Parameter newParam = new Parameter(fixedType, parameter.getName());
                LinkedList<AnnotationNode> copied = new LinkedList<AnnotationNode>();
                LinkedList<AnnotationNode> notCopied = new LinkedList<AnnotationNode>();
                GeneralUtils.copyAnnotatedNodeAnnotations(parameter, copied, notCopied);
                newParam.addAnnotations(copied);
                params[i - 1] = newParam;
                origParams[i - 1] = parameter;
                argList.addExpression(new VariableExpression(newParam));
            }
            TraitComposer.createForwarderMethod(trait, cNode, methodNode, originalMethod, helperClassNode, methodGenericsSpec, helperMethodParams, origParams, params, argList, unit);
        }
        MethodCallExpression staticInitCall = new MethodCallExpression((Expression)new ClassExpression(helperClassNode), "$static$init$", (Expression)new ArgumentListExpression(new ClassExpression(cNode)));
        MethodNode staticInitMethod = new MethodNode("$static$init$", 9, ClassHelper.VOID_TYPE, new Parameter[]{new Parameter(ClassHelper.CLASS_Type, "clazz")}, ClassNode.EMPTY_ARRAY, EmptyStatement.INSTANCE);
        staticInitMethod.setDeclaringClass(helperClassNode);
        staticInitCall.setMethodTarget(staticInitMethod);
        cNode.addStaticInitializerStatements(Collections.singletonList(new ExpressionStatement(staticInitCall)), false);
        if (fieldHelperClassNode != null && !cNode.declaresInterface(fieldHelperClassNode)) {
            cNode.addInterface(fieldHelperClassNode);
            LinkedList<MethodNode> declaredMethods = new LinkedList<MethodNode>();
            for (MethodNode declaredMethod : fieldHelperClassNode.getAllDeclaredMethods()) {
                if (declaredMethod.getName().endsWith("$get")) {
                    declaredMethods.add(0, declaredMethod);
                    continue;
                }
                declaredMethods.add(declaredMethod);
            }
            if (staticFieldHelperClassNode != null) {
                for (MethodNode declaredMethod : staticFieldHelperClassNode.getAllDeclaredMethods()) {
                    if (declaredMethod.getName().endsWith("$get")) {
                        declaredMethods.add(0, declaredMethod);
                        continue;
                    }
                    declaredMethods.add(declaredMethod);
                }
            }
            for (MethodNode methodNode : declaredMethods) {
                boolean finalSetter;
                Parameter[] newParams;
                String fieldName = methodNode.getName();
                if (!fieldName.endsWith("$get") && !fieldName.endsWith("$set")) continue;
                int suffixIdx = fieldName.lastIndexOf(36);
                fieldName = fieldName.substring(0, suffixIdx);
                String operation = methodNode.getName().substring(suffixIdx + 1);
                boolean getter = "get".equals(operation);
                ClassNode returnType = GenericsUtils.correctToGenericsSpecRecurse(genericsSpec, methodNode.getReturnType());
                int fieldMods = 0;
                int isStatic = 0;
                boolean publicField = true;
                FieldNode helperField = null;
                fieldMods = 0;
                isStatic = 0;
                for (Integer mod : Traits.FIELD_PREFIXES) {
                    helperField = fieldHelperClassNode.getField(String.format("$0x%04x", mod) + fieldName);
                    if (helperField == null) continue;
                    if ((mod & 8) != 0) {
                        isStatic = 8;
                    }
                    fieldMods |= mod.intValue();
                    break;
                }
                if (helperField == null) {
                    helperField = fieldHelperClassNode.getField("$ins$0" + fieldName);
                    if (helperField == null) {
                        publicField = false;
                        helperField = fieldHelperClassNode.getField("$ins$1" + fieldName);
                    }
                    if (helperField == null) {
                        publicField = true;
                        helperField = fieldHelperClassNode.getField("$static$0" + fieldName);
                        if (helperField == null) {
                            publicField = false;
                            helperField = fieldHelperClassNode.getField("$static$1" + fieldName);
                        }
                        fieldMods |= 8;
                        isStatic = 8;
                    }
                    fieldMods |= publicField ? 1 : 2;
                }
                if (getter && helperField != null) {
                    String baseName;
                    StaticMethodCallExpression mce;
                    LinkedList<AnnotationNode> copied = new LinkedList<AnnotationNode>();
                    LinkedList<AnnotationNode> notCopied = new LinkedList<AnnotationNode>();
                    GeneralUtils.copyAnnotatedNodeAnnotations(helperField, copied, notCopied);
                    FieldNode fieldNode = cNode.addField(fieldName, fieldMods, returnType, null);
                    fieldNode.addAnnotations(copied);
                    if (fieldNode.isFinal() && helperClassNode.hasPossibleStaticMethod((mce = GeneralUtils.callX(helperClassNode, (baseName = fieldNode.isStatic() ? "$static$init$" : "$init$") + fieldNode.getName(), (Expression)GeneralUtils.args(GeneralUtils.varX("this")))).getMethod(), mce.getArguments())) {
                        Statement stmt = GeneralUtils.stmt(GeneralUtils.assignX(GeneralUtils.varX(fieldNode.getName(), fieldNode.getType()), mce));
                        if (isStatic == 0) {
                            cNode.addObjectInitializerStatements(stmt);
                        } else {
                            ArrayList<Statement> staticStatements = new ArrayList<Statement>();
                            staticStatements.add(stmt);
                            cNode.addStaticInitializerStatements(staticStatements, true);
                        }
                    }
                }
                if (getter) {
                    newParams = Parameter.EMPTY_ARRAY;
                } else {
                    ClassNode originType = methodNode.getParameters()[0].getOriginType();
                    ClassNode fixedType = originType.isGenericsPlaceHolder() ? ClassHelper.OBJECT_TYPE : GenericsUtils.correctToGenericsSpecRecurse(genericsSpec, originType);
                    newParams = new Parameter[]{new Parameter(fixedType, "val")};
                }
                VariableExpression fieldExpr = GeneralUtils.varX(cNode.getField(fieldName));
                boolean bl = finalSetter = !getter && (fieldMods & 0x10) != 0;
                Statement body = getter ? GeneralUtils.returnS(fieldExpr) : (finalSetter ? null : GeneralUtils.stmt(new BinaryExpression(fieldExpr, Token.newSymbol(100, 0, 0), GeneralUtils.varX(newParams[0]))));
                MethodNode impl = new MethodNode(methodNode.getName(), 1 | isStatic, returnType, newParams, ClassNode.EMPTY_ARRAY, body);
                AnnotationNode an = new AnnotationNode(COMPILESTATIC_CLASSNODE);
                impl.addAnnotation(an);
                cNode.addTransform(StaticCompileTransformation.class, an);
                ClassNodeUtils.addGeneratedMethod(cNode, impl);
            }
        }
        cNode.addObjectInitializerStatements(new ExpressionStatement(new MethodCallExpression((Expression)new ClassExpression(helperClassNode), "$init$", (Expression)new ArgumentListExpression(new VariableExpression("this")))));
    }

    private static void createForwarderMethod(ClassNode trait, ClassNode targetNode, MethodNode helperMethod, MethodNode originalMethod, ClassNode helperClassNode, Map<String, ClassNode> genericsSpec, Parameter[] helperMethodParams, Parameter[] traitMethodParams, Parameter[] forwarderParams, ArgumentListExpression helperMethodArgList, SourceUnit unit) {
        MethodCallExpression mce = new MethodCallExpression((Expression)new ClassExpression(helperClassNode), helperMethod.getName(), (Expression)helperMethodArgList);
        mce.setImplicitThis(false);
        ClassNode[] exceptionNodes = GenericsUtils.correctToGenericsSpecRecurse(genericsSpec, TraitComposer.copyExceptions(helperMethod.getExceptions()));
        ClassNode fixedReturnType = GenericsUtils.correctToGenericsSpecRecurse(genericsSpec, helperMethod.getReturnType());
        boolean noCastRequired = genericsSpec.isEmpty() || fixedReturnType.getName().equals(ClassHelper.VOID_TYPE.getName());
        Expression forwardExpression = noCastRequired ? mce : new CastExpression(fixedReturnType, mce);
        boolean isHelperForStaticMethod = ClassHelper.isClassType(helperMethodParams[0].getOriginType());
        if (helperMethod.isPrivate() && !isHelperForStaticMethod) {
            return;
        }
        int modifiers = helperMethod.getModifiers();
        if (!isHelperForStaticMethod) {
            modifiers &= 0xFFFFFFF7;
        }
        MethodNode forwarder = new MethodNode(helperMethod.getName(), modifiers &= 0xFFFFFF7F, fixedReturnType, forwarderParams, exceptionNodes, new ExpressionStatement(forwardExpression));
        LinkedList<AnnotationNode> copied = new LinkedList<AnnotationNode>();
        List<AnnotationNode> notCopied = Collections.emptyList();
        GeneralUtils.copyAnnotatedNodeAnnotations(helperMethod, copied, notCopied);
        if (!copied.isEmpty()) {
            forwarder.addAnnotations(copied);
        }
        if (originalMethod != null) {
            GenericsType[] newGt = GenericsUtils.applyGenericsContextToPlaceHolders(genericsSpec, originalMethod.getGenericsTypes());
            newGt = TraitComposer.removeNonPlaceHolders(newGt);
            forwarder.setGenericsTypes(newGt);
        } else {
            GenericsType[] genericsTypes = helperMethod.getGenericsTypes();
            if (genericsTypes != null) {
                Map<String, ClassNode> methodSpec = GenericsUtils.addMethodGenerics(helperMethod, Collections.emptyMap());
                GenericsType[] newGt = GenericsUtils.applyGenericsContextToPlaceHolders(methodSpec, helperMethod.getGenericsTypes());
                forwarder.setGenericsTypes(newGt);
            }
        }
        AnnotationNode bridgeAnnotation = new AnnotationNode(Traits.TRAITBRIDGE_CLASSNODE);
        bridgeAnnotation.addMember("traitClass", new ClassExpression(trait));
        bridgeAnnotation.addMember("desc", new ConstantExpression(BytecodeHelper.getMethodDescriptor(helperMethod.getReturnType(), traitMethodParams)));
        forwarder.addAnnotation(bridgeAnnotation);
        MethodNode existingMethod = TraitComposer.findExistingMethod(targetNode, forwarder);
        if (existingMethod != null && !forwarder.isStatic() && existingMethod.isStatic()) {
            unit.addError(TraitComposer.createException(trait, targetNode, forwarder, existingMethod));
            return;
        }
        if (!TraitComposer.shouldSkipMethod(targetNode, forwarder.getName(), forwarderParams)) {
            targetNode.addMethod(forwarder);
        }
        TraitComposer.createSuperForwarder(targetNode, forwarder, genericsSpec);
    }

    private static SyntaxException createException(ClassNode trait, ClassNode targetNode, MethodNode forwarder, MethodNode existingMethod) {
        String middle;
        AnnotatedNode errorTarget;
        if (existingMethod.getLineNumber() == -1) {
            Expression traitClass;
            errorTarget = targetNode;
            List<AnnotationNode> allAnnos = existingMethod.getAnnotations(Traits.TRAITBRIDGE_CLASSNODE);
            AnnotationNode bridgeAnno = allAnnos == null ? null : allAnnos.get(0);
            String fromTrait = null;
            if (bridgeAnno != null && (traitClass = bridgeAnno.getMember("traitClass")) instanceof ClassExpression) {
                ClassExpression ce = (ClassExpression)traitClass;
                fromTrait = ce.getType().getNameWithoutPackage();
            }
            middle = "in '" + targetNode.getNameWithoutPackage();
            if (fromTrait != null) {
                middle = middle + "' from trait '" + fromTrait;
            }
        } else {
            errorTarget = existingMethod;
            middle = "declared in '" + targetNode.getNameWithoutPackage();
        }
        String message = "The static '" + forwarder.getName() + "' method " + middle + "' conflicts with the instance method having the same signature from trait '" + trait.getNameWithoutPackage() + "'";
        return new SyntaxException(message, errorTarget);
    }

    private static GenericsType[] removeNonPlaceHolders(GenericsType[] oldTypes) {
        if (oldTypes == null || oldTypes.length == 0) {
            return oldTypes;
        }
        ArrayList<GenericsType> l = new ArrayList<GenericsType>(Arrays.asList(oldTypes));
        Iterator<GenericsType> it = l.iterator();
        boolean modified = false;
        while (it.hasNext()) {
            GenericsType gt = it.next();
            if (gt.isPlaceholder()) continue;
            it.remove();
            modified = true;
        }
        if (!modified) {
            return oldTypes;
        }
        if (l.isEmpty()) {
            return null;
        }
        return l.toArray(GenericsType.EMPTY_ARRAY);
    }

    private static void createSuperForwarder(ClassNode targetNode, MethodNode forwarder, Map<String, ClassNode> genericsSpec) {
        ArrayList<ClassNode> interfaces = new ArrayList<ClassNode>(Traits.collectAllInterfacesReverseOrder(targetNode, new LinkedHashSet<ClassNode>()));
        String name = forwarder.getName();
        Parameter[] forwarderParameters = forwarder.getParameters();
        LinkedHashSet<ClassNode> traits = new LinkedHashSet<ClassNode>();
        LinkedList<MethodNode> superForwarders = new LinkedList<MethodNode>();
        for (ClassNode node : interfaces) {
            MethodNode method;
            if (!Traits.isTrait(node) || (method = node.getDeclaredMethod(name, forwarderParameters)) == null) continue;
            traits.add(node);
            superForwarders.add(method);
        }
        for (MethodNode superForwarder : superForwarders) {
            TraitComposer.doCreateSuperForwarder(targetNode, superForwarder, traits.toArray(ClassNode.EMPTY_ARRAY), genericsSpec);
        }
    }

    private static void doCreateSuperForwarder(ClassNode targetNode, MethodNode forwarderMethod, ClassNode[] interfacesToGenerateForwarderFor, Map<String, ClassNode> genericsSpec) {
        int i;
        Parameter[] parameters = forwarderMethod.getParameters();
        Parameter[] superForwarderParams = new Parameter[parameters.length];
        for (i = 0; i < parameters.length; ++i) {
            Parameter parameter = parameters[i];
            ClassNode originType = parameter.getOriginType();
            superForwarderParams[i] = new Parameter(GenericsUtils.correctToGenericsSpecRecurse(genericsSpec, originType), parameter.getName());
        }
        for (i = 0; i < interfacesToGenerateForwarderFor.length; ++i) {
            ClassNode current = interfacesToGenerateForwarderFor[i];
            ClassNode next = i < interfacesToGenerateForwarderFor.length - 1 ? interfacesToGenerateForwarderFor[i + 1] : null;
            String forwarderName = Traits.getSuperTraitMethodName(current, forwarderMethod.getName());
            if (targetNode.getDeclaredMethod(forwarderName, superForwarderParams) != null) continue;
            ClassNode returnType = GenericsUtils.correctToGenericsSpecRecurse(genericsSpec, forwarderMethod.getReturnType());
            Statement delegate = next == null ? TraitComposer.createSuperFallback(forwarderMethod, returnType) : TraitComposer.createDelegatingForwarder(forwarderMethod, next);
            MethodNode methodNode = ClassNodeUtils.addGeneratedMethod(targetNode, forwarderName, 4097, returnType, superForwarderParams, ClassNode.EMPTY_ARRAY, delegate);
            methodNode.setGenericsTypes(forwarderMethod.getGenericsTypes());
        }
    }

    private static Statement createSuperFallback(MethodNode forwarderMethod, ClassNode returnType) {
        ArgumentListExpression paramTuple = GeneralUtils.args((Expression[])Arrays.stream(forwarderMethod.getParameters()).map(p -> GeneralUtils.varX(p)).toArray(Expression[]::new));
        MethodCallExpression proxyTarget = GeneralUtils.callX(GeneralUtils.castX(Traits.GENERATED_PROXY_CLASSNODE, GeneralUtils.varX("this")), "getProxyTarget");
        proxyTarget.setImplicitThis(false);
        StaticMethodCallExpression proxyCall = GeneralUtils.callX(ClassHelper.make(InvokerHelper.class), "invokeMethod", (Expression)GeneralUtils.args(proxyTarget, GeneralUtils.constX(forwarderMethod.getName()), new ArrayExpression(ClassHelper.OBJECT_TYPE, paramTuple.getExpressions())));
        MethodCallExpression superCall = GeneralUtils.callX((Expression)GeneralUtils.varX("super"), forwarderMethod.getName(), (Expression)paramTuple);
        superCall.putNodeMetaData((Object)StaticTypesMarker.DYNAMIC_RESOLUTION, Boolean.TRUE);
        superCall.setImplicitThis(false);
        return GeneralUtils.ifElseS(GeneralUtils.isInstanceOfX(GeneralUtils.varX("this"), Traits.GENERATED_PROXY_CLASSNODE), GeneralUtils.stmt(GeneralUtils.castX(returnType, proxyCall)), GeneralUtils.stmt(superCall));
    }

    private static Statement createDelegatingForwarder(MethodNode forwarderMethod, ClassNode next) {
        ArgumentListExpression args = new ArgumentListExpression();
        args.addExpression(GeneralUtils.varX("this"));
        for (Parameter p : forwarderMethod.getParameters()) {
            args.addExpression(GeneralUtils.varX(p));
        }
        StaticMethodCallExpression delegateCall = GeneralUtils.callX(Traits.findHelper(next), forwarderMethod.getName(), (Expression)args);
        return forwarderMethod.isVoidMethod() ? GeneralUtils.block(GeneralUtils.stmt(delegateCall), GeneralUtils.returnS(GeneralUtils.nullX())) : GeneralUtils.returnS(delegateCall);
    }

    private static ClassNode[] copyExceptions(ClassNode[] sourceExceptions) {
        ClassNode[] exceptionNodes = new ClassNode[sourceExceptions == null ? 0 : sourceExceptions.length];
        System.arraycopy(sourceExceptions, 0, exceptionNodes, 0, exceptionNodes.length);
        return exceptionNodes;
    }

    private static MethodNode findExistingMethod(ClassNode cNode, MethodNode forwarder) {
        return TraitComposer.findExistingMethod(cNode, forwarder.getName(), forwarder.getParameters());
    }

    private static MethodNode findExistingMethod(ClassNode cNode, String name, Parameter[] params) {
        return cNode.getDeclaredMethod(name, params);
    }

    private static boolean shouldSkipMethod(ClassNode cNode, String name, Parameter[] params) {
        return TraitComposer.isExistingProperty(name, cNode, params) || TraitComposer.findExistingMethod(cNode, name, params) != null;
    }

    private static boolean isExistingProperty(String methodName, ClassNode cNode, Parameter[] params) {
        String propertyName = methodName;
        boolean getter = false;
        if (methodName.startsWith("get")) {
            propertyName = propertyName.substring(3);
            getter = true;
        } else if (methodName.startsWith("is")) {
            propertyName = propertyName.substring(2);
            getter = true;
        } else if (methodName.startsWith("set")) {
            propertyName = propertyName.substring(3);
        } else {
            return false;
        }
        if (getter && params.length > 0) {
            return false;
        }
        if (!getter && params.length != 1) {
            return false;
        }
        if (propertyName.length() == 0) {
            return false;
        }
        PropertyNode pNode = cNode.getProperty(propertyName = MetaClassHelper.convertPropertyName(propertyName));
        return pNode != null;
    }
}

