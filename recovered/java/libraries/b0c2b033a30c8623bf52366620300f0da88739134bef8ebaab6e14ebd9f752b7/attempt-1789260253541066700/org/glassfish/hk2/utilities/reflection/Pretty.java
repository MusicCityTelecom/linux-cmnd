/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;

public class Pretty {
    private static final String DOT = ".";
    private static final String NULL_STRING = "null";
    private static final String CONSTRUCTOR_NAME = "<init>";

    public static String clazz(Class<?> clazz) {
        if (clazz == null) {
            return NULL_STRING;
        }
        String cn = clazz.getName();
        int index = cn.lastIndexOf(DOT);
        if (index < 0) {
            return cn;
        }
        return cn.substring(index + 1);
    }

    public static String pType(ParameterizedType pType) {
        StringBuffer sb = new StringBuffer();
        sb.append(Pretty.clazz(ReflectionHelper.getRawClass(pType)) + "<");
        boolean first = true;
        for (Type t : pType.getActualTypeArguments()) {
            if (first) {
                first = false;
                sb.append(Pretty.type(t));
                continue;
            }
            sb.append("," + Pretty.type(t));
        }
        sb.append(">");
        return sb.toString();
    }

    public static String type(Type t) {
        if (t == null) {
            return NULL_STRING;
        }
        if (t instanceof Class) {
            return Pretty.clazz((Class)t);
        }
        if (t instanceof ParameterizedType) {
            return Pretty.pType((ParameterizedType)t);
        }
        return t.toString();
    }

    public static String constructor(Constructor<?> constructor) {
        if (constructor == null) {
            return NULL_STRING;
        }
        return CONSTRUCTOR_NAME + Pretty.prettyPrintParameters(constructor.getParameterTypes());
    }

    public static String method(Method method) {
        if (method == null) {
            return NULL_STRING;
        }
        return method.getName() + Pretty.prettyPrintParameters(method.getParameterTypes());
    }

    public static String field(Field field) {
        if (field == null) {
            return NULL_STRING;
        }
        Type t = field.getGenericType();
        String baseString = t instanceof Class ? Pretty.clazz((Class)t) : Pretty.type(t);
        return "field(" + baseString + " " + field.getName() + " in " + field.getDeclaringClass().getName() + ")";
    }

    public static String array(Object[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        StringBuffer sb = new StringBuffer("{");
        boolean first = true;
        for (Object item : array) {
            if (item != null && item instanceof Class) {
                item = Pretty.clazz((Class)item);
            }
            if (first) {
                first = false;
                sb.append(item == null ? NULL_STRING : item.toString());
                continue;
            }
            sb.append("," + (item == null ? NULL_STRING : item.toString()));
        }
        sb.append("}");
        return sb.toString();
    }

    public static String collection(Collection<?> collection) {
        if (collection == null) {
            return NULL_STRING;
        }
        return Pretty.array(collection.toArray(new Object[collection.size()]));
    }

    private static String prettyPrintParameters(Class<?>[] params) {
        if (params == null) {
            return NULL_STRING;
        }
        StringBuffer sb = new StringBuffer("(");
        boolean first = true;
        for (Class<?> param : params) {
            if (first) {
                sb.append(Pretty.clazz(param));
                first = false;
                continue;
            }
            sb.append("," + Pretty.clazz(param));
        }
        sb.append(")");
        return sb.toString();
    }
}

