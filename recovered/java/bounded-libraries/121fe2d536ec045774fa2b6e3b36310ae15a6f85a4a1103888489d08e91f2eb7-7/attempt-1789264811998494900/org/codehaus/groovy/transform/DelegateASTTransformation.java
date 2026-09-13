/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.lang.Delegate;
import groovy.lang.Lazy;
import groovy.lang.Reference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.tools.BeanUtils;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.ast.tools.ParameterUtils;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@GroovyASTTransformation(phase=CompilePhase.CANONICALIZATION)
public class DelegateASTTransformation
extends AbstractASTTransformation {
    private static final Class<?> MY_CLASS = Delegate.class;
    private static final ClassNode MY_TYPE = ClassHelper.make(MY_CLASS);
    private static final String MY_TYPE_NAME = "@" + MY_TYPE.getNameWithoutPackage();
    private static final ClassNode LAZY_TYPE = ClassHelper.make(Lazy.class);
    private static final String MEMBER_DEPRECATED = "deprecated";
    private static final String MEMBER_INTERFACES = "interfaces";
    private static final String MEMBER_INCLUDES = "includes";
    private static final String MEMBER_EXCLUDES = "excludes";
    private static final String MEMBER_INCLUDE_TYPES = "includeTypes";
    private static final String MEMBER_EXCLUDE_TYPES = "excludeTypes";
    private static final String MEMBER_PARAMETER_ANNOTATIONS = "parameterAnnotations";
    private static final String MEMBER_METHOD_ANNOTATIONS = "methodAnnotations";
    private static final String MEMBER_ALL_NAMES = "allNames";

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        this.init(nodes, source);
        AnnotatedNode parent = (AnnotatedNode)nodes[1];
        AnnotationNode node = (AnnotationNode)nodes[0];
        DelegateDescription delegate = null;
        if (parent instanceof FieldNode) {
            FieldNode fieldNode = (FieldNode)parent;
            delegate = new DelegateDescription();
            delegate.delegate = fieldNode;
            delegate.annotation = node;
            delegate.name = fieldNode.getName();
            delegate.type = fieldNode.getType();
            delegate.owner = fieldNode.getOwner();
            delegate.getOp = GeneralUtils.varX(fieldNode);
            delegate.origin = "field";
        } else if (parent instanceof MethodNode) {
            MethodNode methodNode = (MethodNode)parent;
            delegate = new DelegateDescription();
            delegate.delegate = methodNode;
            delegate.annotation = node;
            delegate.name = methodNode.getName();
            delegate.type = methodNode.getReturnType();
            delegate.owner = methodNode.getDeclaringClass();
            delegate.getOp = GeneralUtils.callThisX(delegate.name);
            delegate.origin = "method";
            if (methodNode.getParameters().length > 0) {
                this.addError("You can only delegate to methods that take no parameters, but " + delegate.name + " takes " + methodNode.getParameters().length + " parameters.", parent);
                return;
            }
        }
        if (delegate != null) {
            if (ClassHelper.isObjectType(delegate.type) || ClassHelper.isGroovyObjectType(delegate.type)) {
                this.addError(MY_TYPE_NAME + " " + delegate.origin + " '" + delegate.name + "' has an inappropriate type: " + delegate.type.getName() + ". Please add an explicit type but not java.lang.Object or groovy.lang.GroovyObject.", parent);
                return;
            }
            if (delegate.type.equals(delegate.owner)) {
                this.addError(MY_TYPE_NAME + " " + delegate.origin + " '" + delegate.name + "' has an inappropriate type: " + delegate.type.getName() + ". Delegation to own type not supported. Please use a different type.", parent);
                return;
            }
            boolean skipInterfaces = this.memberHasValue(node, MEMBER_INTERFACES, Boolean.FALSE);
            boolean includeDeprecated = this.memberHasValue(node, MEMBER_DEPRECATED, Boolean.TRUE) || delegate.type.isInterface() && !skipInterfaces;
            boolean allNames = this.memberHasValue(node, MEMBER_ALL_NAMES, Boolean.TRUE);
            delegate.excludes = DelegateASTTransformation.getMemberStringList(node, MEMBER_EXCLUDES);
            delegate.includes = DelegateASTTransformation.getMemberStringList(node, MEMBER_INCLUDES);
            delegate.excludeTypes = this.getMemberClassList(node, MEMBER_EXCLUDE_TYPES);
            delegate.includeTypes = this.getMemberClassList(node, MEMBER_INCLUDE_TYPES);
            this.checkIncludeExcludeUndefinedAware(node, delegate.excludes, delegate.includes, delegate.excludeTypes, delegate.includeTypes, MY_TYPE_NAME);
            if (!this.checkPropertyOrMethodList(delegate.type, delegate.excludes, MEMBER_EXCLUDES, node, MY_TYPE_NAME)) {
                return;
            }
            if (!this.checkPropertyOrMethodList(delegate.type, delegate.includes, MEMBER_INCLUDES, node, MY_TYPE_NAME)) {
                return;
            }
            List<MethodNode> ownerMethods = GeneralUtils.getAllMethods(delegate.owner);
            Collection<MethodNode> delegateMethods = DelegateASTTransformation.filterMethods(DelegateASTTransformation.collectMethods(delegate.type), delegate, allNames, includeDeprecated);
            for (MethodNode mn : delegateMethods) {
                this.addDelegateMethod(mn, delegate, ownerMethods);
            }
            for (PropertyNode prop : GeneralUtils.getAllProperties(delegate.type)) {
                if (prop.isStatic() || !prop.isPublic()) continue;
                String name = prop.getName();
                DelegateASTTransformation.addGetterIfNeeded(delegate, prop, name, allNames);
                DelegateASTTransformation.addSetterIfNeeded(delegate, prop, name, allNames);
            }
            if (delegate.type.isArray()) {
                boolean skipLength;
                boolean bl = skipLength = delegate.excludes != null && (delegate.excludes.contains("length") || delegate.excludes.contains("getLength"));
                if (!skipLength) {
                    ClassNodeUtils.addGeneratedMethod(delegate.owner, "getLength", 1, ClassHelper.int_TYPE, Parameter.EMPTY_ARRAY, null, GeneralUtils.returnS(GeneralUtils.propX(delegate.getOp, "length")));
                }
            }
            if (skipInterfaces) {
                return;
            }
            Set<ClassNode> addedInterfaces = GeneralUtils.getInterfacesAndSuperInterfaces(delegate.type);
            addedInterfaces.removeIf(i -> (i.getModifiers() & 0x1001) != 1 || i.isSealed());
            if (!addedInterfaces.isEmpty()) {
                Set<ClassNode> ownerInterfaces = GeneralUtils.getInterfacesAndSuperInterfaces(delegate.owner);
                for (ClassNode i2 : addedInterfaces) {
                    if (ownerInterfaces.contains(i2)) continue;
                    ClassNode[] faces = delegate.owner.getInterfaces();
                    faces = Arrays.copyOf(faces, faces.length + 1);
                    faces[faces.length - 1] = i2;
                    delegate.owner.setInterfaces(faces);
                }
            }
        }
    }

    private static Collection<MethodNode> collectMethods(ClassNode type) {
        LinkedList<MethodNode> methods = new LinkedList<MethodNode>(GeneralUtils.getAllMethods(type));
        ListIterator<MethodNode> it = methods.listIterator();
        while (it.hasNext()) {
            MethodNode next = (MethodNode)it.next();
            if (!next.isPublic() || next.isAbstract() || !next.hasDefaultValue()) continue;
            int n = 0;
            for (Parameter p : next.getParameters()) {
                if (!p.hasInitialExpression()) continue;
                ++n;
            }
            for (int i = 1; i <= n; ++i) {
                Parameter[] params = new Parameter[next.getParameters().length - i];
                int index = 0;
                int j = 1;
                for (Parameter parameter : next.getParameters()) {
                    if (j > n - i && parameter.hasInitialExpression()) {
                        ++j;
                        continue;
                    }
                    params[index++] = parameter;
                    if (!parameter.hasInitialExpression()) continue;
                    ++j;
                }
                if (!methods.stream().noneMatch(mn -> mn.getName().equals(next.getName()) && ParameterUtils.parametersEqual(mn.getParameters(), params))) continue;
                MethodNode mn2 = new MethodNode(next.getName(), next.getModifiers(), next.getReturnType(), params, next.getExceptions(), null);
                mn2.setDeclaringClass(next.getDeclaringClass());
                mn2.setGenericsTypes(next.getGenericsTypes());
                mn2.addAnnotations(next.getAnnotations());
                it.add(mn2);
            }
        }
        for (ClassNode face : type.getAllInterfaces()) {
            methods.addAll(face.getMethods());
        }
        return methods;
    }

    private static Collection<MethodNode> filterMethods(Collection<MethodNode> methods, DelegateDescription delegate, boolean allNames, boolean includeDeprecated) {
        Set groovyObjectMethods = ClassHelper.GROOVY_OBJECT_TYPE.getMethods().stream().map(MethodNode::getTypeDescriptor).collect(Collectors.toSet());
        Set javaObjectMethods = ClassHelper.OBJECT_TYPE.getMethods().stream().map(MethodNode::getTypeDescriptor).collect(Collectors.toSet());
        Set ownClassMethods = delegate.owner.getMethods().stream().map(MethodNode::getTypeDescriptor).collect(Collectors.toSet());
        methods.removeIf(candidate -> {
            if (!candidate.isPublic() || candidate.isStatic() || (candidate.getModifiers() & 0x1000) != 0) {
                return true;
            }
            if (DelegateASTTransformation.shouldSkip(candidate.getName(), delegate.excludes, delegate.includes, allNames)) {
                return true;
            }
            if (!includeDeprecated && !candidate.getAnnotations(ClassHelper.DEPRECATED_TYPE).isEmpty()) {
                return true;
            }
            if (groovyObjectMethods.contains(candidate.getTypeDescriptor())) {
                return true;
            }
            if (javaObjectMethods.contains(candidate.getTypeDescriptor())) {
                return true;
            }
            return ownClassMethods.contains(candidate.getTypeDescriptor());
        });
        return methods;
    }

    private boolean checkPropertyOrMethodList(ClassNode cNode, List<String> propertyNameList, String listName, AnnotationNode anno, String typeName) {
        if (propertyNameList == null || propertyNameList.isEmpty()) {
            return true;
        }
        HashSet<String> pNames = new HashSet<String>();
        HashSet<String> mNames = new HashSet<String>();
        for (PropertyNode pNode : BeanUtils.getAllProperties(cNode, false, false, false)) {
            String name = pNode.getField().getName();
            pNames.add(name);
            if ((pNode.getModifiers() & 0x10) == 0) {
                mNames.add(GeneralUtils.getSetterName(name));
            }
            mNames.add(GeneralUtils.getGetterName(name));
            if (!ClassHelper.isPrimitiveBoolean(pNode.getOriginType())) continue;
            mNames.add(DelegateASTTransformation.getPredicateName(name));
        }
        for (MethodNode mNode : cNode.getAllDeclaredMethods()) {
            mNames.add(mNode.getName());
        }
        boolean result = true;
        for (String name : propertyNameList) {
            if (pNames.contains(name) || mNames.contains(name)) continue;
            this.addError("Error during " + typeName + " processing: '" + listName + "' property or method '" + name + "' does not exist.", anno);
            result = false;
        }
        return result;
    }

    private static void addSetterIfNeeded(DelegateDescription delegate, PropertyNode prop, String name, boolean allNames) {
        String setterName = GeneralUtils.getSetterName(name);
        if ((prop.getModifiers() & 0x10) == 0 && delegate.owner.getSetterMethod(setterName) == null && delegate.owner.getProperty(name) == null && !DelegateASTTransformation.shouldSkipPropertyMethod(name, setterName, delegate.excludes, delegate.includes, allNames)) {
            ClassNodeUtils.addGeneratedMethod(delegate.owner, setterName, 1, ClassHelper.VOID_TYPE, GeneralUtils.params(new Parameter(GenericsUtils.nonGeneric(prop.getType()), "value")), null, GeneralUtils.assignS(GeneralUtils.propX(delegate.getOp, name), GeneralUtils.varX("value")));
        }
    }

    private static void addGetterIfNeeded(DelegateDescription delegate, PropertyNode prop, String name, boolean allNames) {
        boolean isPrimBool = ClassHelper.isPrimitiveBoolean(prop.getOriginType());
        boolean willHaveGetAccessor = true;
        boolean willHaveIsAccessor = isPrimBool;
        String getterName = GeneralUtils.getGetterName(name);
        String isserName = DelegateASTTransformation.getPredicateName(name);
        if (isPrimBool) {
            ClassNode cNode = prop.getDeclaringClass();
            if (cNode.getGetterMethod(isserName) != null && cNode.getGetterMethod(getterName) == null) {
                willHaveGetAccessor = false;
            }
            if (cNode.getGetterMethod(getterName) != null && cNode.getGetterMethod(isserName) == null) {
                willHaveIsAccessor = false;
            }
        }
        Reference<Boolean> ownerWillHaveGetAccessor = new Reference<Boolean>();
        Reference<Boolean> ownerWillHaveIsAccessor = new Reference<Boolean>();
        DelegateASTTransformation.extractAccessorInfo(delegate.owner, name, ownerWillHaveGetAccessor, ownerWillHaveIsAccessor);
        if (willHaveGetAccessor && !ownerWillHaveGetAccessor.get().booleanValue() && !DelegateASTTransformation.shouldSkipPropertyMethod(name, getterName, delegate.excludes, delegate.includes, allNames)) {
            ClassNodeUtils.addGeneratedMethod(delegate.owner, getterName, 1, GenericsUtils.nonGeneric(prop.getType()), Parameter.EMPTY_ARRAY, null, GeneralUtils.returnS(GeneralUtils.propX(delegate.getOp, name)));
        }
        if (willHaveIsAccessor && !ownerWillHaveIsAccessor.get().booleanValue() && !DelegateASTTransformation.shouldSkipPropertyMethod(name, getterName, delegate.excludes, delegate.includes, allNames)) {
            ClassNodeUtils.addGeneratedMethod(delegate.owner, isserName, 1, GenericsUtils.nonGeneric(prop.getType()), Parameter.EMPTY_ARRAY, null, GeneralUtils.returnS(GeneralUtils.propX(delegate.getOp, name)));
        }
    }

    private static void extractAccessorInfo(ClassNode owner, String name, Reference<Boolean> willHaveGetAccessor, Reference<Boolean> willHaveIsAccessor) {
        boolean hasGetAccessor = owner.getGetterMethod(GeneralUtils.getGetterName(name)) != null;
        boolean hasIsAccessor = owner.getGetterMethod(DelegateASTTransformation.getPredicateName(name)) != null;
        PropertyNode prop = owner.getProperty(name);
        willHaveGetAccessor.set(hasGetAccessor || prop != null && !hasIsAccessor);
        willHaveIsAccessor.set(hasIsAccessor || prop != null && !hasGetAccessor && ClassHelper.isPrimitiveBoolean(prop.getOriginType()));
    }

    private static boolean shouldSkipPropertyMethod(String propertyName, String methodName, List<String> excludes, List<String> includes, boolean allNames) {
        return !allNames && DelegateASTTransformation.deemedInternalName(propertyName) || excludes != null && (excludes.contains(propertyName) || excludes.contains(methodName)) || includes != null && !includes.isEmpty() && !includes.contains(propertyName) && !includes.contains(methodName);
    }

    private void addDelegateMethod(MethodNode candidate, DelegateDescription delegate, Iterable<MethodNode> ownMethods) {
        Map<String, ClassNode> genericsSpec = GenericsUtils.addMethodGenerics(candidate, GenericsUtils.createGenericsSpec(delegate.owner));
        GenericsUtils.extractSuperClassGenerics(delegate.type, candidate.getDeclaringClass(), genericsSpec);
        if (delegate.excludeTypes != null && !delegate.excludeTypes.isEmpty() || delegate.includeTypes != null) {
            MethodNode correctedMethodNode = GenericsUtils.correctToGenericsSpec(genericsSpec, candidate);
            boolean checkReturn = delegate.type.getMethods().contains(candidate);
            if (DelegateASTTransformation.shouldSkipOnDescriptorUndefinedAware(checkReturn, genericsSpec, correctedMethodNode, delegate.excludeTypes, delegate.includeTypes)) {
                return;
            }
        }
        MethodNode existingNode = null;
        for (MethodNode mn : ownMethods) {
            if (mn.isAbstract() || mn.isStatic() || !mn.getTypeDescriptor().equals(candidate.getTypeDescriptor())) continue;
            existingNode = mn;
            break;
        }
        if (existingNode == null || existingNode.getCode() == null) {
            ArgumentListExpression args = new ArgumentListExpression();
            Parameter[] params = candidate.getParameters();
            Parameter[] newParams = new Parameter[params.length];
            List<String> currentMethodGenPlaceholders = DelegateASTTransformation.getGenericPlaceholderNames(candidate);
            int n = newParams.length;
            for (int i = 0; i < n; ++i) {
                ClassNode newParamType = GenericsUtils.correctToGenericsSpecRecurse(genericsSpec, params[i].getType(), currentMethodGenPlaceholders);
                Parameter newParam = new Parameter(newParamType, DelegateASTTransformation.getParamName(params, i, delegate.name));
                if (this.memberHasValue(delegate.annotation, MEMBER_PARAMETER_ANNOTATIONS, Boolean.TRUE)) {
                    newParam.addAnnotations(this.copyAnnotatedNodeAnnotations(params[i], MY_TYPE_NAME));
                }
                newParams[i] = newParam;
                args.addExpression(GeneralUtils.varX(newParam));
            }
            boolean alsoLazy = !delegate.delegate.getAnnotations(LAZY_TYPE).isEmpty();
            MethodCallExpression mce = GeneralUtils.callX(alsoLazy ? GeneralUtils.propX((Expression)GeneralUtils.varX("this"), delegate.name.substring(1)) : delegate.getOp, candidate.getName(), (Expression)args);
            mce.setImplicitThis(false);
            mce.setSourcePosition(delegate.delegate);
            ClassNode returnType = GenericsUtils.correctToGenericsSpecRecurse(genericsSpec, candidate.getReturnType(), currentMethodGenPlaceholders);
            MethodNode newMethod = ClassNodeUtils.addGeneratedMethod(delegate.owner, candidate.getName(), candidate.getModifiers() & 0xFFFFFBFF & 0xFFFFFEFF, returnType, newParams, candidate.getExceptions(), candidate.isVoidMethod() ? GeneralUtils.stmt(mce) : GeneralUtils.returnS(mce));
            newMethod.setGenericsTypes(candidate.getGenericsTypes());
            if (this.memberHasValue(delegate.annotation, MEMBER_METHOD_ANNOTATIONS, Boolean.TRUE)) {
                newMethod.addAnnotations(this.copyAnnotatedNodeAnnotations(candidate, MY_TYPE_NAME, false));
            }
        }
    }

    private static List<String> getGenericPlaceholderNames(MethodNode candidate) {
        GenericsType[] candidateGenericsTypes = candidate.getGenericsTypes();
        ArrayList<String> names = new ArrayList<String>();
        if (candidateGenericsTypes != null) {
            for (GenericsType gt : candidateGenericsTypes) {
                names.add(gt.getName());
            }
        }
        return names;
    }

    private static String getParamName(Parameter[] params, int i, String fieldName) {
        String name = params[i].getName();
        while (name.equals(fieldName) || DelegateASTTransformation.clashesWithOtherParams(name, params, i)) {
            name = "_" + name;
        }
        return name;
    }

    private static boolean clashesWithOtherParams(String name, Parameter[] params, int i) {
        int n = params.length;
        for (int j = 0; j < n; ++j) {
            if (i == j || !params[j].getName().equals(name)) continue;
            return true;
        }
        return false;
    }

    private static String getPredicateName(String propertyName) {
        return "is" + org.apache.groovy.util.BeanUtils.capitalize(propertyName);
    }

    static class DelegateDescription {
        AnnotationNode annotation;
        AnnotatedNode delegate;
        String name;
        ClassNode type;
        ClassNode owner;
        Expression getOp;
        String origin;
        List<String> includes;
        List<String> excludes;
        List<ClassNode> includeTypes;
        List<ClassNode> excludeTypes;

        DelegateDescription() {
        }
    }
}

