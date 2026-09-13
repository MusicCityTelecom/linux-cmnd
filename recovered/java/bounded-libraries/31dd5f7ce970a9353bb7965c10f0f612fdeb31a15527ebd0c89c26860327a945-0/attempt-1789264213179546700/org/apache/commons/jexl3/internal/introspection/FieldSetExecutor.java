/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal.introspection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.apache.commons.jexl3.internal.introspection.Introspector;
import org.apache.commons.jexl3.internal.introspection.MethodKey;
import org.apache.commons.jexl3.internal.introspection.Uberspect;
import org.apache.commons.jexl3.introspection.JexlPropertySet;

public final class FieldSetExecutor
implements JexlPropertySet {
    private final Field field;

    public static JexlPropertySet discover(Introspector is, Class<?> clazz, String identifier, Object value) {
        Field field;
        if (identifier != null && (field = is.getField(clazz, identifier)) != null && !Modifier.isFinal(field.getModifiers()) && (value == null || MethodKey.isInvocationConvertible(field.getType(), value.getClass(), false))) {
            return new FieldSetExecutor(field);
        }
        return null;
    }

    private FieldSetExecutor(Field theField) {
        this.field = theField;
    }

    @Override
    public Object invoke(Object obj, Object arg) throws Exception {
        this.field.set(obj, arg);
        return arg;
    }

    @Override
    public Object tryInvoke(Object obj, Object key, Object value) {
        if (obj.getClass().equals(this.field.getDeclaringClass()) && key.equals(this.field.getName()) && (value == null || MethodKey.isInvocationConvertible(this.field.getType(), value.getClass(), false))) {
            try {
                this.field.set(obj, value);
                return value;
            }
            catch (IllegalAccessException xill) {
                return Uberspect.TRY_FAILED;
            }
        }
        return Uberspect.TRY_FAILED;
    }

    @Override
    public boolean tryFailed(Object rval) {
        return rval == Uberspect.TRY_FAILED;
    }

    @Override
    public boolean isCacheable() {
        return true;
    }
}

