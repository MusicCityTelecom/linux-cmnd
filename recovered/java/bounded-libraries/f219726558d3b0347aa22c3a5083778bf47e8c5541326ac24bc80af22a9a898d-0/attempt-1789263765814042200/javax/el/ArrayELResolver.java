/*
 * Decompiled with CFR 0.152.
 */
package javax.el;

import java.beans.FeatureDescriptor;
import java.lang.reflect.Array;
import java.util.Iterator;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;

public class ArrayELResolver
extends ELResolver {
    private boolean isReadOnly;

    public ArrayELResolver() {
        this.isReadOnly = false;
    }

    public ArrayELResolver(boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }

    @Override
    public Class<?> getType(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base != null && base.getClass().isArray()) {
            context.setPropertyResolved(true);
            int index = this.toInteger(property);
            if (index < 0 || index >= Array.getLength(base)) {
                throw new PropertyNotFoundException();
            }
            return base.getClass().getComponentType();
        }
        return null;
    }

    @Override
    public Object getValue(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base != null && base.getClass().isArray()) {
            context.setPropertyResolved(base, property);
            int index = this.toInteger(property);
            if (index >= 0 && index < Array.getLength(base)) {
                return Array.get(base, index);
            }
        }
        return null;
    }

    @Override
    public void setValue(ELContext context, Object base, Object property, Object val) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base != null && base.getClass().isArray()) {
            context.setPropertyResolved(base, property);
            if (this.isReadOnly) {
                throw new PropertyNotWritableException();
            }
            Class<?> type = base.getClass().getComponentType();
            if (val != null && !type.isAssignableFrom(val.getClass())) {
                throw new ClassCastException();
            }
            int index = this.toInteger(property);
            if (index < 0 || index >= Array.getLength(base)) {
                throw new PropertyNotFoundException();
            }
            Array.set(base, index, val);
        }
    }

    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base != null && base.getClass().isArray()) {
            context.setPropertyResolved(true);
            int index = this.toInteger(property);
            if (index < 0 || index >= Array.getLength(base)) {
                throw new PropertyNotFoundException();
            }
        }
        return this.isReadOnly;
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
        return null;
    }

    @Override
    public Class<?> getCommonPropertyType(ELContext context, Object base) {
        if (base != null && base.getClass().isArray()) {
            return Integer.class;
        }
        return null;
    }

    private int toInteger(Object p) {
        if (p instanceof Integer) {
            return (Integer)p;
        }
        if (p instanceof Character) {
            return ((Character)p).charValue();
        }
        if (p instanceof Boolean) {
            return (Boolean)p != false ? 1 : 0;
        }
        if (p instanceof Number) {
            return ((Number)p).intValue();
        }
        if (p instanceof String) {
            return Integer.parseInt((String)p);
        }
        throw new IllegalArgumentException();
    }
}

