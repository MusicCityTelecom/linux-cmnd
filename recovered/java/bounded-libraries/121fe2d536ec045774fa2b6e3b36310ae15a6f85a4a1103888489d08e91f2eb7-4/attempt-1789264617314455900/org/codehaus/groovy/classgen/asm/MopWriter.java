/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm;

import groovyjarjarasm.asm.MethodVisitor;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.tools.ParameterUtils;
import org.codehaus.groovy.classgen.asm.BytecodeHelper;
import org.codehaus.groovy.classgen.asm.OperandStack;
import org.codehaus.groovy.classgen.asm.WriterController;

public class MopWriter {
    public static final Factory FACTORY = MopWriter::new;
    private final WriterController controller;

    public MopWriter(WriterController controller) {
        this.controller = Objects.requireNonNull(controller);
    }

    public void createMopMethods() {
        ClassNode classNode = this.controller.getClassNode();
        if (!ClassHelper.isGeneratedFunction(classNode)) {
            this.visitMopMethodList(classNode.getMethods(), true, Collections.emptySet(), Collections.emptyList());
            this.visitMopMethodList(MopWriter.getSuperMethods(classNode), false, classNode.getMethods().stream().map(mn -> new MopKey(mn.getName(), mn.getParameters())).collect(Collectors.toSet()), this.controller.getSuperMethodNames());
        }
    }

    private void visitMopMethodList(Iterable<MethodNode> methods, boolean isThis, Set<MopKey> onlyIfThis, List<String> orThis) {
        LinkedList<MethodNode> list = new LinkedList<MethodNode>();
        HashMap<MopKey, MethodNode> map = new HashMap<MopKey, MethodNode>();
        for (MethodNode mn : methods) {
            if ((mn.getModifiers() & 0x448) != 0 || isThis ^ mn.isPrivate()) continue;
            String methodName = mn.getName();
            Parameter[] parameters = mn.getParameters();
            if (MopWriter.isMopMethod(methodName)) {
                map.put(new MopKey(methodName, parameters), mn);
                continue;
            }
            if (methodName.startsWith("<") || !onlyIfThis.contains(new MopKey(methodName, parameters)) && !orThis.contains(methodName) || map.put(new MopKey(MopWriter.getMopMethodName(mn, isThis), parameters), mn) != null) continue;
            list.add(mn);
        }
        this.generateMopCalls(list, isThis);
    }

    protected void generateMopCalls(LinkedList<MethodNode> methods, boolean useThis) {
        for (MethodNode method : methods) {
            ClassNode returnType = method.getReturnType();
            Parameter[] parameters = method.getParameters();
            String mopName = MopWriter.getMopMethodName(method, useThis);
            String signature = BytecodeHelper.getMethodDescriptor(returnType, parameters);
            MethodVisitor mv = this.controller.getClassVisitor().visitMethod(4097, mopName, signature, null, null);
            this.controller.setMethodVisitor(mv);
            int stackIndex = 0;
            mv.visitVarInsn(25, stackIndex++);
            OperandStack operandStack = this.controller.getOperandStack();
            for (Parameter parameter : parameters) {
                ClassNode type = parameter.getType();
                operandStack.load(type, stackIndex++);
                if (!ClassHelper.isPrimitiveLong(type) && !ClassHelper.isPrimitiveDouble(type)) continue;
                ++stackIndex;
            }
            operandStack.remove(parameters.length);
            ClassNode receiverType = this.controller.getThisType();
            if (!useThis) {
                receiverType = receiverType.getSuperClass();
                ClassNode declaringType = method.getDeclaringClass();
                if (declaringType.isInterface() && !receiverType.implementsInterface(declaringType)) {
                    receiverType = declaringType;
                }
            }
            mv.visitMethodInsn(183, BytecodeHelper.getClassInternalName(receiverType), method.getName(), signature, receiverType.isInterface());
            BytecodeHelper.doReturn(mv, returnType);
            mv.visitMaxs(0, 0);
            mv.visitEnd();
            this.controller.getClassNode().addMethod(mopName, 4097, returnType, parameters, null, null);
        }
    }

    private static Iterable<MethodNode> getSuperMethods(ClassNode classNode) {
        Map<String, MethodNode> result = classNode.getSuperClass().getDeclaredMethodsMap();
        for (ClassNode in : classNode.getInterfaces()) {
            if (classNode.getSuperClass().implementsInterface(in)) continue;
            for (MethodNode mn : in.getMethods()) {
                if (!mn.isDefault()) continue;
                result.putIfAbsent(mn.getTypeDescriptor(), mn);
            }
        }
        return result.values();
    }

    public static String getMopMethodName(MethodNode method, boolean useThis) {
        ClassNode declaringClass = method.getDeclaringClass();
        int distance = 1;
        if (!declaringClass.isInterface()) {
            for (ClassNode sc = declaringClass.getSuperClass(); sc != null; sc = sc.getSuperClass()) {
                ++distance;
            }
        }
        return (useThis ? "this" : "super") + "$" + distance + "$" + method.getName();
    }

    public static boolean isMopMethod(String methodName) {
        return (methodName.startsWith("this$") || methodName.startsWith("super$")) && !methodName.contains("$dist$");
    }

    @Deprecated
    public static boolean equalParameterTypes(Parameter[] p1, Parameter[] p2) {
        return ParameterUtils.parametersEqual(p1, p2);
    }

    private static class MopKey {
        final int hash;
        final String name;
        final Parameter[] params;

        MopKey(String name, Parameter[] params) {
            this.name = name;
            this.params = params;
            this.hash = name.hashCode() << 2 + params.length;
        }

        public int hashCode() {
            return this.hash;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof MopKey)) {
                return false;
            }
            MopKey other = (MopKey)obj;
            return other.name.equals(this.name) && ParameterUtils.parametersEqual(other.params, this.params);
        }
    }

    @FunctionalInterface
    public static interface Factory {
        public MopWriter create(WriterController var1);
    }
}

