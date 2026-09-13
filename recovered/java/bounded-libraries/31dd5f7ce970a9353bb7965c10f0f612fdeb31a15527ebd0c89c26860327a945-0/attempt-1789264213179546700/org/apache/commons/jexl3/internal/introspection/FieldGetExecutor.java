/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal.introspection;

import java.lang.reflect.Field;
import org.apache.commons.jexl3.internal.introspection.Introspector;
import org.apache.commons.jexl3.internal.introspection.Uberspect;
import org.apache.commons.jexl3.introspection.JexlPropertyGet;

public final class FieldGetExecutor
implements JexlPropertyGet {
    private final Field field;

    public static JexlPropertyGet discover(Introspector is, Class<?> clazz, String identifier) {
        Field field;
        if (identifier != null && (field = is.getField(clazz, identifier)) != null) {
            return new FieldGetExecutor(field);
        }
        return null;
    }

    private FieldGetExecutor(Field theField) {
        this.field = theField;
    }

    @Override
    public Object invoke(Object obj) throws Exception {
        return this.field.get(obj);
    }

    @Override
    public Object tryInvoke(Object obj, Object key) {
        if (obj.getClass().equals(this.field.getDeclaringClass()) && key.equals(this.field.getName())) {
            try {
                return this.field.get(obj);
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

