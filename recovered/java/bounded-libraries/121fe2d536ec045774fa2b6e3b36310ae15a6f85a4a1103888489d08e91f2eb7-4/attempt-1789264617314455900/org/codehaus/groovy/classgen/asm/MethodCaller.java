/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm;

import groovyjarjarasm.asm.MethodVisitor;
import groovyjarjarasm.asm.Type;
import java.lang.reflect.Method;
import org.codehaus.groovy.classgen.ClassGeneratorException;

public class MethodCaller {
    private int opcode;
    private String internalName;
    private String name;
    private Class theClass;
    private String methodDescriptor;
    private int parameterCount;
    private static final int ANY_PARAMETER_COUNT = -1;

    public static MethodCaller newStatic(Class theClass, String name) {
        return new MethodCaller(184, theClass, name);
    }

    public static MethodCaller newStatic(Class theClass, String name, int parameterCount) {
        return new MethodCaller(184, theClass, name, parameterCount);
    }

    public static MethodCaller newInterface(Class theClass, String name) {
        return new MethodCaller(185, theClass, name);
    }

    public static MethodCaller newVirtual(Class theClass, String name) {
        return new MethodCaller(182, theClass, name);
    }

    protected MethodCaller() {
    }

    public MethodCaller(int opcode, Class theClass, String name) {
        this(opcode, theClass, name, -1);
    }

    public MethodCaller(int opcode, Class theClass, String name, int parameterCount) {
        this.opcode = opcode;
        this.internalName = Type.getInternalName(theClass);
        this.theClass = theClass;
        this.name = name;
        this.parameterCount = parameterCount;
    }

    public void call(MethodVisitor methodVisitor) {
        methodVisitor.visitMethodInsn(this.opcode, this.internalName, this.name, this.getMethodDescriptor(), this.opcode == 185);
    }

    public String getMethodDescriptor() {
        if (this.methodDescriptor == null) {
            Method method = this.getMethod();
            this.methodDescriptor = Type.getMethodDescriptor(method);
        }
        return this.methodDescriptor;
    }

    protected Method getMethod() {
        Method[] methods = this.theClass.getMethods();
        if (this.parameterCount != -1) {
            for (Method method : methods) {
                if (!method.getName().equals(this.name) || method.getParameterCount() != this.parameterCount) continue;
                return method;
            }
        } else {
            for (Method method : methods) {
                if (!method.getName().equals(this.name)) continue;
                return method;
            }
        }
        throw new ClassGeneratorException("Could not find method: " + this.name + (this.parameterCount >= 0 ? " with parameter count " + this.parameterCount : "") + " on class: " + this.theClass);
    }
}

