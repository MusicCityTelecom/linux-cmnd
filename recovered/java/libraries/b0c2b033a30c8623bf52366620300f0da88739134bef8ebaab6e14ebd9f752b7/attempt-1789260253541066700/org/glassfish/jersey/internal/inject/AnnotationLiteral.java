/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.util.Arrays;

public abstract class AnnotationLiteral<T extends Annotation>
implements Annotation,
Serializable {
    private static final long serialVersionUID = -3645430766814376616L;
    private transient Class<T> annotationType;
    private transient Method[] members;

    protected AnnotationLiteral() {
        Class<?> thisClass = this.getClass();
        boolean foundAnnotation = false;
        for (Class<?> iClass : thisClass.getInterfaces()) {
            if (!iClass.isAnnotation()) continue;
            foundAnnotation = true;
            break;
        }
        if (!foundAnnotation) {
            throw new IllegalStateException("The subclass " + thisClass.getName() + " of AnnotationLiteral must implement an Annotation");
        }
    }

    private static Class<?> getAnnotationLiteralSubclass(Class<?> clazz) {
        Class<?> superclass = clazz.getSuperclass();
        if (superclass.equals(AnnotationLiteral.class)) {
            return clazz;
        }
        if (superclass.equals(Object.class)) {
            return null;
        }
        return AnnotationLiteral.getAnnotationLiteralSubclass(superclass);
    }

    private static <T> Class<T> getTypeParameter(Class<?> annotationLiteralSuperclass) {
        ParameterizedType parameterizedType;
        Type type = annotationLiteralSuperclass.getGenericSuperclass();
        if (type instanceof ParameterizedType && (parameterizedType = (ParameterizedType)type).getActualTypeArguments().length == 1) {
            return (Class)parameterizedType.getActualTypeArguments()[0];
        }
        return null;
    }

    private static void setAccessible(AccessibleObject ao) {
        AccessController.doPrivileged(() -> {
            ao.setAccessible(true);
            return null;
        });
    }

    private static Object invoke(Method method, Object instance) {
        try {
            if (!method.isAccessible()) {
                AnnotationLiteral.setAccessible(method);
            }
            return method.invoke(instance, new Object[0]);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException("Error checking value of member method " + method.getName() + " on " + method.getDeclaringClass(), e);
        }
    }

    private Method[] getMembers() {
        if (this.members == null) {
            this.members = AccessController.doPrivileged(this.annotationType()::getDeclaredMethods);
            if (this.members.length > 0 && !this.annotationType().isAssignableFrom(this.getClass())) {
                throw new RuntimeException(this.getClass() + " does not implement the annotation type with members " + this.annotationType().getName());
            }
        }
        return this.members;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        if (this.annotationType == null) {
            Class<?> annotationLiteralSubclass = AnnotationLiteral.getAnnotationLiteralSubclass(this.getClass());
            if (annotationLiteralSubclass == null) {
                throw new RuntimeException(this.getClass() + "is not a subclass of AnnotationLiteral");
            }
            this.annotationType = AnnotationLiteral.getTypeParameter(annotationLiteralSubclass);
            if (this.annotationType == null) {
                throw new RuntimeException(this.getClass() + " does not specify the type parameter T of AnnotationLiteral<T>");
            }
        }
        return this.annotationType;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Annotation) {
            Annotation that = (Annotation)other;
            if (this.annotationType().equals(that.annotationType())) {
                for (Method member : this.getMembers()) {
                    Object thisValue = AnnotationLiteral.invoke(member, this);
                    Object thatValue = AnnotationLiteral.invoke(member, that);
                    if (!(thisValue instanceof byte[] && thatValue instanceof byte[] ? !Arrays.equals((byte[])thisValue, (byte[])thatValue) : (thisValue instanceof short[] && thatValue instanceof short[] ? !Arrays.equals((short[])thisValue, (short[])thatValue) : (thisValue instanceof int[] && thatValue instanceof int[] ? !Arrays.equals((int[])thisValue, (int[])thatValue) : (thisValue instanceof long[] && thatValue instanceof long[] ? !Arrays.equals((long[])thisValue, (long[])thatValue) : (thisValue instanceof float[] && thatValue instanceof float[] ? !Arrays.equals((float[])thisValue, (float[])thatValue) : (thisValue instanceof double[] && thatValue instanceof double[] ? !Arrays.equals((double[])thisValue, (double[])thatValue) : (thisValue instanceof char[] && thatValue instanceof char[] ? !Arrays.equals((char[])thisValue, (char[])thatValue) : (thisValue instanceof boolean[] && thatValue instanceof boolean[] ? !Arrays.equals((boolean[])thisValue, (boolean[])thatValue) : (thisValue instanceof Object[] && thatValue instanceof Object[] ? !Arrays.equals((Object[])thisValue, (Object[])thatValue) : !thisValue.equals(thatValue))))))))))) continue;
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        for (Method member : this.getMembers()) {
            int memberNameHashCode = 127 * member.getName().hashCode();
            Object value = AnnotationLiteral.invoke(member, this);
            int memberValueHashCode = value instanceof boolean[] ? Arrays.hashCode((boolean[])value) : (value instanceof short[] ? Arrays.hashCode((short[])value) : (value instanceof int[] ? Arrays.hashCode((int[])value) : (value instanceof long[] ? Arrays.hashCode((long[])value) : (value instanceof float[] ? Arrays.hashCode((float[])value) : (value instanceof double[] ? Arrays.hashCode((double[])value) : (value instanceof byte[] ? Arrays.hashCode((byte[])value) : (value instanceof char[] ? Arrays.hashCode((char[])value) : (value instanceof Object[] ? Arrays.hashCode((Object[])value) : value.hashCode()))))))));
            hashCode += memberNameHashCode ^ memberValueHashCode;
        }
        return hashCode;
    }
}

