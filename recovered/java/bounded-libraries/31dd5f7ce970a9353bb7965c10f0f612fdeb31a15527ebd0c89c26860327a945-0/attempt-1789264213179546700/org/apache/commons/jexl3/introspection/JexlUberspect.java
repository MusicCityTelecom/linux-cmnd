/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.introspection;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.jexl3.JexlArithmetic;
import org.apache.commons.jexl3.JexlOperator;
import org.apache.commons.jexl3.introspection.JexlMethod;
import org.apache.commons.jexl3.introspection.JexlPropertyGet;
import org.apache.commons.jexl3.introspection.JexlPropertySet;

public interface JexlUberspect {
    public static final List<PropertyResolver> POJO = Collections.unmodifiableList(Arrays.asList(JexlResolver.PROPERTY, JexlResolver.MAP, JexlResolver.LIST, JexlResolver.DUCK, JexlResolver.FIELD, JexlResolver.CONTAINER));
    public static final List<PropertyResolver> MAP = Collections.unmodifiableList(Arrays.asList(JexlResolver.MAP, JexlResolver.LIST, JexlResolver.DUCK, JexlResolver.PROPERTY, JexlResolver.FIELD, JexlResolver.CONTAINER));
    public static final ResolverStrategy JEXL_STRATEGY = (op, obj) -> {
        if (op == JexlOperator.ARRAY_GET) {
            return MAP;
        }
        if (op == JexlOperator.ARRAY_SET) {
            return MAP;
        }
        if (op == null && obj instanceof Map) {
            return MAP;
        }
        return POJO;
    };
    public static final ResolverStrategy MAP_STRATEGY = (op, obj) -> {
        if (op == JexlOperator.ARRAY_GET) {
            return MAP;
        }
        if (op == JexlOperator.ARRAY_SET) {
            return MAP;
        }
        if (obj instanceof Map) {
            return MAP;
        }
        return POJO;
    };

    public List<PropertyResolver> getResolvers(JexlOperator var1, Object var2);

    public void setClassLoader(ClassLoader var1);

    public ClassLoader getClassLoader();

    public int getVersion();

    public JexlMethod getConstructor(Object var1, Object ... var2);

    public JexlMethod getMethod(Object var1, String var2, Object ... var3);

    public JexlPropertyGet getPropertyGet(Object var1, Object var2);

    public JexlPropertyGet getPropertyGet(List<PropertyResolver> var1, Object var2, Object var3);

    public JexlPropertySet getPropertySet(Object var1, Object var2, Object var3);

    public JexlPropertySet getPropertySet(List<PropertyResolver> var1, Object var2, Object var3, Object var4);

    public Iterator<?> getIterator(Object var1);

    public JexlArithmetic.Uberspect getArithmetic(JexlArithmetic var1);

    public static interface ResolverStrategy {
        public List<PropertyResolver> apply(JexlOperator var1, Object var2);
    }

    public static enum JexlResolver implements PropertyResolver
    {
        PROPERTY,
        MAP,
        LIST,
        DUCK,
        FIELD,
        CONTAINER;


        @Override
        public final JexlPropertyGet getPropertyGet(JexlUberspect uber, Object obj, Object identifier) {
            return uber.getPropertyGet(Collections.singletonList(this), obj, identifier);
        }

        @Override
        public final JexlPropertySet getPropertySet(JexlUberspect uber, Object obj, Object identifier, Object arg) {
            return uber.getPropertySet(Collections.singletonList(this), obj, identifier, arg);
        }
    }

    public static interface PropertyResolver {
        public JexlPropertyGet getPropertyGet(JexlUberspect var1, Object var2, Object var3);

        public JexlPropertySet getPropertySet(JexlUberspect var1, Object var2, Object var3, Object var4);
    }
}

