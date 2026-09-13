/*
 * Decompiled with CFR 0.152.
 */
package de.cronn.reflection.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

final class MethodSignature
implements Comparable<MethodSignature> {
    private final String name;
    private final Class<?> returnType;
    private final Class<?>[] parameterTypes;

    MethodSignature(Method method) {
        this.name = method.getName();
        this.returnType = method.getReturnType();
        this.parameterTypes = method.getParameterTypes();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        MethodSignature that = (MethodSignature)o;
        return Objects.equals(this.name, that.name) && Objects.equals(this.returnType, that.returnType) && Arrays.equals(this.parameterTypes, that.parameterTypes);
    }

    public int hashCode() {
        return Objects.hash(this.name, this.returnType, Arrays.hashCode(this.parameterTypes));
    }

    String getName() {
        return this.name;
    }

    Class<?> getReturnType() {
        return this.returnType;
    }

    private Class<?>[] getParameterTypes() {
        return this.parameterTypes;
    }

    @Override
    public int compareTo(MethodSignature other) {
        Comparator<MethodSignature> comparator = Comparator.comparing(MethodSignature::getName).thenComparing(MethodSignature::getReturnType, Comparator.comparing(Class::getName)).thenComparing(MethodSignature::getParameterTypes, Comparator.comparing(MethodSignature::mapToString));
        return comparator.compare(this, other);
    }

    private static String mapToString(Class<?>[] list) {
        return Arrays.stream(list).map(Class::getName).collect(Collectors.joining(", "));
    }

    public String toString() {
        return this.getReturnType().getName() + " " + this.getName() + "(" + MethodSignature.mapToString(this.getParameterTypes()) + ")";
    }
}

