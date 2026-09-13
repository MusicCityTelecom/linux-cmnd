/*
 * Decompiled with CFR 0.152.
 */
package javax.el;

public class MethodInfo {
    private String name;
    private Class<?> returnType;
    private Class<?>[] paramTypes;

    public MethodInfo(String name, Class<?> returnType, Class<?>[] paramTypes) {
        this.name = name;
        this.returnType = returnType;
        this.paramTypes = paramTypes;
    }

    public String getName() {
        return this.name;
    }

    public Class<?> getReturnType() {
        return this.returnType;
    }

    public Class<?>[] getParamTypes() {
        return this.paramTypes;
    }
}

