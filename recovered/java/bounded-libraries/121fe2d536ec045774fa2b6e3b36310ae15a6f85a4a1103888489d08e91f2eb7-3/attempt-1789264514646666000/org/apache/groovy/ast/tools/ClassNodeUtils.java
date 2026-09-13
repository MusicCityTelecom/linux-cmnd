/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.ast.tools;

import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import org.apache.groovy.ast.tools.AnnotatedNodeUtils;
import org.apache.groovy.util.BeanUtils;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MapExpression;
import org.codehaus.groovy.ast.expr.SpreadExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.runtime.ArrayTypeUtils;
import org.codehaus.groovy.transform.AbstractASTTransformation;

public class ClassNodeUtils {
    private ClassNodeUtils() {
    }

    public static String formatTypeName(ClassNode cNode) {
        if (cNode.isArray()) {
            ClassNode it = cNode;
            int dim = 0;
            while (it.isArray()) {
                ++dim;
                it = it.getComponentType();
            }
            StringBuilder sb = new StringBuilder(it.getName().length() + 2 * dim);
            sb.append(it.getName());
            for (int i = 0; i < dim; ++i) {
                sb.append("[]");
            }
            return sb.toString();
        }
        return cNode.getName();
    }

    public static MethodNode addGeneratedMethod(ClassNode cNode, String name, int modifiers, ClassNode returnType, Parameter[] parameters, ClassNode[] exceptions, Statement code) {
        MethodNode existing = cNode.getDeclaredMethod(name, parameters);
        if (existing != null) {
            return existing;
        }
        MethodNode result = new MethodNode(name, modifiers, returnType, parameters, exceptions, code);
        ClassNodeUtils.addGeneratedMethod(cNode, result);
        return result;
    }

    public static void addGeneratedMethod(ClassNode cNode, MethodNode mNode) {
        cNode.addMethod(mNode);
        AnnotatedNodeUtils.markAsGenerated(cNode, mNode);
    }

    public static void addGeneratedMethod(ClassNode cNode, MethodNode mNode, boolean skipChecks) {
        cNode.addMethod(mNode);
        AnnotatedNodeUtils.markAsGenerated(cNode, mNode, skipChecks);
    }

    public static void addGeneratedInnerClass(ClassNode cNode, ClassNode inner) {
        cNode.getModule().addClass(inner);
        AnnotatedNodeUtils.markAsGenerated(cNode, inner);
    }

    public static ConstructorNode addGeneratedConstructor(ClassNode classNode, int modifiers, Parameter[] parameters, ClassNode[] exceptions, Statement code) {
        ConstructorNode consNode = classNode.addConstructor(modifiers, parameters, exceptions, code);
        AnnotatedNodeUtils.markAsGenerated(classNode, consNode);
        return consNode;
    }

    public static void addGeneratedConstructor(ClassNode classNode, ConstructorNode consNode) {
        classNode.addConstructor(consNode);
        AnnotatedNodeUtils.markAsGenerated(classNode, consNode);
    }

    public static Map<String, MethodNode> getDeclaredMethodsFromSuper(ClassNode cNode) {
        ClassNode parent = cNode.getSuperClass();
        if (parent == null) {
            return new HashMap<String, MethodNode>();
        }
        return parent.getDeclaredMethodsMap();
    }

    public static void addDeclaredMethodsFromInterfaces(ClassNode cNode, Map<String, MethodNode> methodsMap) {
        for (ClassNode iface : cNode.getInterfaces()) {
            Map<String, MethodNode> declaredMethods = iface.getDeclaredMethodsMap();
            for (Map.Entry<String, MethodNode> entry : declaredMethods.entrySet()) {
                if (!entry.getValue().getDeclaringClass().isInterface() || (entry.getValue().getModifiers() & 0x1000) != 0) continue;
                methodsMap.putIfAbsent(entry.getKey(), entry.getValue());
            }
        }
    }

    public static Map<String, MethodNode> getDeclaredMethodsFromInterfaces(ClassNode cNode) {
        HashMap<String, MethodNode> methodsMap = new HashMap<String, MethodNode>();
        ClassNodeUtils.addDeclaredMethodsFromInterfaces(cNode, methodsMap);
        return methodsMap;
    }

    public static void addDeclaredMethodsFromAllInterfaces(ClassNode cNode, Map<String, MethodNode> methodsMap) {
        List<ClassNode> cnInterfaces = Arrays.asList(cNode.getInterfaces());
        for (ClassNode parent = cNode.getSuperClass(); parent != null && !ClassHelper.isObjectType(parent); parent = parent.getSuperClass()) {
            ClassNode[] interfaces;
            for (ClassNode iface : interfaces = parent.getInterfaces()) {
                if (cnInterfaces.contains(iface)) continue;
                methodsMap.putAll(iface.getDeclaredMethodsMap());
            }
        }
    }

    public static boolean hasPossibleStaticMethod(ClassNode cNode, String name, Expression arguments, boolean trySpread) {
        int count = 0;
        boolean foundSpread = false;
        if (arguments instanceof TupleExpression) {
            TupleExpression tuple = (TupleExpression)arguments;
            for (Expression arg : tuple.getExpressions()) {
                if (arg instanceof SpreadExpression) {
                    foundSpread = true;
                    continue;
                }
                ++count;
            }
        } else if (arguments instanceof MapExpression) {
            count = 1;
        }
        for (MethodNode method : cNode.getMethods(name)) {
            if (!method.isStatic()) continue;
            Parameter[] parameters = method.getParameters();
            if (trySpread && foundSpread && parameters.length >= count) {
                return true;
            }
            if (parameters.length == count) {
                return true;
            }
            if (parameters.length > 0 && parameters[parameters.length - 1].getType().isArray()) {
                if (count >= parameters.length - 1) {
                    return true;
                }
                if (trySpread && foundSpread) {
                    return true;
                }
            }
            int nonDefaultParameters = 0;
            for (Parameter parameter : parameters) {
                if (parameter.hasInitialExpression()) continue;
                ++nonDefaultParameters;
            }
            if (count >= parameters.length || nonDefaultParameters > count) continue;
            return true;
        }
        return false;
    }

    public static boolean hasPossibleStaticProperty(ClassNode cNode, String methodName) {
        if (!methodName.startsWith("get") && !methodName.startsWith("is")) {
            return false;
        }
        String propName = ClassNodeUtils.getPropNameForAccessor(methodName);
        PropertyNode pNode = ClassNodeUtils.getStaticProperty(cNode, propName);
        return pNode != null && (methodName.startsWith("get") || ClassHelper.isPrimitiveBoolean(pNode.getType()));
    }

    public static String getPropNameForAccessor(String accessorName) {
        if (!ClassNodeUtils.isValidAccessorName(accessorName)) {
            return accessorName;
        }
        int prefixLength = accessorName.startsWith("is") ? 2 : 3;
        return String.valueOf(accessorName.charAt(prefixLength)).toLowerCase() + accessorName.substring(prefixLength + 1);
    }

    public static boolean isValidAccessorName(String accessorName) {
        if (accessorName.startsWith("get") || accessorName.startsWith("is") || accessorName.startsWith("set")) {
            int prefixLength = accessorName.startsWith("is") ? 2 : 3;
            return accessorName.length() > prefixLength;
        }
        return false;
    }

    public static boolean hasStaticProperty(ClassNode cNode, String propName) {
        PropertyNode found = ClassNodeUtils.getStaticProperty(cNode, propName);
        if (found == null) {
            found = ClassNodeUtils.getStaticProperty(cNode, BeanUtils.decapitalize(propName));
        }
        return found != null;
    }

    public static PropertyNode getStaticProperty(ClassNode cNode, String propName) {
        for (ClassNode classNode = cNode; classNode != null; classNode = classNode.getSuperClass()) {
            for (PropertyNode pn : classNode.getProperties()) {
                if (!pn.getName().equals(propName) || !pn.isStatic()) continue;
                return pn;
            }
        }
        return null;
    }

    public static boolean isInnerClass(ClassNode cNode) {
        return cNode.getOuterClass() != null && !Modifier.isStatic(cNode.getModifiers());
    }

    public static boolean isCompatibleWith(ClassNode source, ClassNode target) {
        if (source.equals(target)) {
            return true;
        }
        if (source.isArray() && target.isArray() && ArrayTypeUtils.dimension(source) == ArrayTypeUtils.dimension(target)) {
            source = ArrayTypeUtils.elementType(source);
            target = ArrayTypeUtils.elementType(target);
        }
        return !ClassHelper.isPrimitiveType(source) && !ClassHelper.isPrimitiveType(target) && (source.isDerivedFrom(target) || source.implementsInterface(target));
    }

    public static boolean hasNoArgConstructor(ClassNode cNode) {
        List<ConstructorNode> constructors = cNode.getDeclaredConstructors();
        for (ConstructorNode next : constructors) {
            if (next.getParameters().length != 0) continue;
            return true;
        }
        return false;
    }

    public static boolean hasExplicitConstructor(AbstractASTTransformation xform, ClassNode cNode) {
        List<ConstructorNode> declaredConstructors = cNode.getDeclaredConstructors();
        for (ConstructorNode constructorNode : declaredConstructors) {
            if (AnnotatedNodeUtils.isGenerated(constructorNode)) continue;
            if (xform != null) {
                xform.addError("Error during " + xform.getAnnotationName() + " processing. Explicit constructors not allowed for class: " + cNode.getNameWithoutPackage(), constructorNode);
            }
            return true;
        }
        return false;
    }

    public static boolean samePackageName(ClassNode first, ClassNode second) {
        return Objects.equals(first.getPackageName(), second.getPackageName());
    }

    public static FieldNode getField(ClassNode classNode, String fieldName) {
        return ClassNodeUtils.getField(classNode, fieldName, fieldNode -> true);
    }

    public static FieldNode getField(ClassNode classNode, String fieldName, Predicate<FieldNode> acceptability) {
        ClassNode next;
        ArrayDeque<ClassNode> todo = new ArrayDeque<ClassNode>(Collections.singletonList(classNode));
        HashSet<ClassNode> done = new HashSet<ClassNode>();
        while ((next = (ClassNode)todo.poll()) != null) {
            if (!done.add(next)) continue;
            FieldNode fieldNode = next.getDeclaredField(fieldName);
            if (fieldNode != null && acceptability.test(fieldNode)) {
                return fieldNode;
            }
            Collections.addAll(todo, next.getInterfaces());
            ClassNode superType = next.getSuperClass();
            if (superType == null) continue;
            todo.add(superType);
        }
        return null;
    }

    public static boolean isSubtype(ClassNode maybeSuperclassOrInterface, ClassNode candidateChild) {
        return maybeSuperclassOrInterface.isInterface() || candidateChild.isInterface() ? GeneralUtils.isOrImplements(candidateChild, maybeSuperclassOrInterface) : candidateChild.isDerivedFrom(maybeSuperclassOrInterface);
    }
}

