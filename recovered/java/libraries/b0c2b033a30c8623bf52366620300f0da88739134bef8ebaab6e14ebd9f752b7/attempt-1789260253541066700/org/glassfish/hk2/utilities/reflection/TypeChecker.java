/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.reflection;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;

public class TypeChecker {
    public static boolean isRawTypeSafe(Type requiredType, Type beanType) {
        Type[] beanTypeVariables;
        Class<?> requiredClass = ReflectionHelper.getRawClass(requiredType);
        if (requiredClass == null) {
            return false;
        }
        requiredClass = ReflectionHelper.translatePrimitiveType(requiredClass);
        Class<?> beanClass = ReflectionHelper.getRawClass(beanType);
        if (beanClass == null) {
            return false;
        }
        if (!requiredClass.isAssignableFrom(beanClass = ReflectionHelper.translatePrimitiveType(beanClass))) {
            return false;
        }
        if (requiredType instanceof Class || requiredType instanceof GenericArrayType) {
            return true;
        }
        if (!(requiredType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("requiredType " + requiredType + " is of unknown type");
        }
        ParameterizedType requiredPT = (ParameterizedType)requiredType;
        Type[] requiredTypeVariables = requiredPT.getActualTypeArguments();
        if (beanType instanceof Class) {
            beanTypeVariables = ((Class)beanType).getTypeParameters();
        } else if (beanType instanceof ParameterizedType) {
            beanTypeVariables = ((ParameterizedType)beanType).getActualTypeArguments();
        } else {
            throw new IllegalArgumentException("Uknown beanType " + beanType);
        }
        if (requiredTypeVariables.length != beanTypeVariables.length) {
            return false;
        }
        for (int lcv = 0; lcv < requiredTypeVariables.length; ++lcv) {
            TypeVariable<?> tv;
            WildcardType wt;
            Type requiredTypeVariable = requiredTypeVariables[lcv];
            Type beanTypeVariable = beanTypeVariables[lcv];
            if (TypeChecker.isActualType(requiredTypeVariable) && TypeChecker.isActualType(beanTypeVariable)) {
                if (TypeChecker.isRawTypeSafe(requiredTypeVariable, beanTypeVariable)) continue;
                return false;
            }
            if (TypeChecker.isArrayType(requiredTypeVariable) && TypeChecker.isArrayType(beanTypeVariable)) {
                Type beanArrayType;
                Type requiredArrayType = TypeChecker.getArrayType(requiredTypeVariable);
                if (TypeChecker.isRawTypeSafe(requiredArrayType, beanArrayType = TypeChecker.getArrayType(beanTypeVariable))) continue;
                return false;
            }
            if (TypeChecker.isWildcard(requiredTypeVariable) && TypeChecker.isActualType(beanTypeVariable)) {
                Class<?> beanActualType;
                wt = TypeChecker.getWildcard(requiredTypeVariable);
                if (TypeChecker.isWildcardActualSafe(wt, beanActualType = ReflectionHelper.getRawClass(beanTypeVariable))) continue;
                return false;
            }
            if (TypeChecker.isWildcard(requiredTypeVariable) && TypeChecker.isTypeVariable(beanTypeVariable)) {
                wt = TypeChecker.getWildcard(requiredTypeVariable);
                if (TypeChecker.isWildcardTypeVariableSafe(wt, tv = TypeChecker.getTypeVariable(beanTypeVariable))) continue;
                return false;
            }
            if (TypeChecker.isActualType(requiredTypeVariable) && TypeChecker.isTypeVariable(beanTypeVariable)) {
                Class<?> requiredActual = ReflectionHelper.getRawClass(requiredTypeVariable);
                if (TypeChecker.isActualTypeVariableSafe(requiredActual, tv = TypeChecker.getTypeVariable(beanTypeVariable))) continue;
                return false;
            }
            if (TypeChecker.isTypeVariable(requiredTypeVariable) && TypeChecker.isTypeVariable(beanTypeVariable)) {
                TypeVariable<?> btv;
                TypeVariable<?> rtv = TypeChecker.getTypeVariable(requiredTypeVariable);
                if (TypeChecker.isTypeVariableTypeVariableSafe(rtv, btv = TypeChecker.getTypeVariable(beanTypeVariable))) continue;
                return false;
            }
            return false;
        }
        return true;
    }

    private static boolean isTypeVariableTypeVariableSafe(TypeVariable<?> rtv, TypeVariable<?> btv) {
        Class<?> rtvBound = TypeChecker.getBound(rtv.getBounds());
        if (rtvBound == null) {
            return false;
        }
        Class<?> btvBound = TypeChecker.getBound(btv.getBounds());
        if (btvBound == null) {
            return false;
        }
        return btvBound.isAssignableFrom(rtvBound);
    }

    private static boolean isActualTypeVariableSafe(Class<?> actual, TypeVariable<?> tv) {
        Class<?> tvBound = TypeChecker.getBound(tv.getBounds());
        if (tvBound == null) {
            return false;
        }
        return actual.isAssignableFrom(tvBound);
    }

    private static boolean isWildcardTypeVariableSafe(WildcardType wildcard, TypeVariable<?> tv) {
        Class<?> tvBound = TypeChecker.getBound(tv.getBounds());
        if (tvBound == null) {
            return false;
        }
        Class<?> upperBound = TypeChecker.getBound(wildcard.getUpperBounds());
        if (upperBound == null) {
            return false;
        }
        if (!upperBound.isAssignableFrom(tvBound)) {
            return false;
        }
        Class<?> lowerBound = TypeChecker.getBound(wildcard.getLowerBounds());
        if (lowerBound == null) {
            return true;
        }
        return tvBound.isAssignableFrom(lowerBound);
    }

    private static Class<?> getBound(Type[] bounds) {
        if (bounds == null) {
            return null;
        }
        if (bounds.length < 1) {
            return null;
        }
        if (bounds.length > 1) {
            throw new AssertionError((Object)"Do not understand multiple bounds");
        }
        return ReflectionHelper.getRawClass(bounds[0]);
    }

    private static boolean isWildcardActualSafe(WildcardType wildcard, Class<?> actual) {
        Class<?> upperBound = TypeChecker.getBound(wildcard.getUpperBounds());
        if (upperBound == null) {
            return false;
        }
        if (!upperBound.isAssignableFrom(actual)) {
            return false;
        }
        Class<?> lowerBound = TypeChecker.getBound(wildcard.getLowerBounds());
        if (lowerBound == null) {
            return true;
        }
        return actual.isAssignableFrom(lowerBound);
    }

    private static WildcardType getWildcard(Type type) {
        if (type == null) {
            return null;
        }
        if (type instanceof WildcardType) {
            return (WildcardType)type;
        }
        return null;
    }

    private static TypeVariable<?> getTypeVariable(Type type) {
        if (type == null) {
            return null;
        }
        if (type instanceof TypeVariable) {
            return (TypeVariable)type;
        }
        return null;
    }

    private static boolean isWildcard(Type type) {
        if (type == null) {
            return false;
        }
        return type instanceof WildcardType;
    }

    private static boolean isTypeVariable(Type type) {
        if (type == null) {
            return false;
        }
        return type instanceof TypeVariable;
    }

    private static boolean isActualType(Type type) {
        if (type == null) {
            return false;
        }
        return type instanceof Class || type instanceof ParameterizedType;
    }

    private static boolean isArrayType(Type type) {
        if (type == null) {
            return false;
        }
        if (type instanceof Class) {
            Class clazz = (Class)type;
            return clazz.isArray();
        }
        return type instanceof GenericArrayType;
    }

    private static Type getArrayType(Type type) {
        if (type == null) {
            return null;
        }
        if (type instanceof Class) {
            Class clazz = (Class)type;
            return clazz.getComponentType();
        }
        if (type instanceof GenericArrayType) {
            GenericArrayType gat = (GenericArrayType)type;
            return gat.getGenericComponentType();
        }
        return null;
    }
}

