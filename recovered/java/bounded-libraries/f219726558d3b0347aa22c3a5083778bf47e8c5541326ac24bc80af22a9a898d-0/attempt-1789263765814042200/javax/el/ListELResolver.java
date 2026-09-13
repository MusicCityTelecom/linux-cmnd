/*
 * Decompiled with CFR 0.152.
 */
package javax.el;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;

public class ListELResolver
extends ELResolver {
    private static Class<?> theUnmodifiableListClass = Collections.unmodifiableList(new ArrayList()).getClass();
    private boolean isReadOnly;

    public ListELResolver() {
        this.isReadOnly = false;
    }

    public ListELResolver(boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }

    @Override
    public Class<?> getType(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base != null && base instanceof List) {
            context.setPropertyResolved(true);
            List list = (List)base;
            int index = this.toInteger(property);
            if (index < 0 || index >= list.size()) {
                throw new PropertyNotFoundException();
            }
            return Object.class;
        }
        return null;
    }

    @Override
    public Object getValue(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base != null && base instanceof List) {
            context.setPropertyResolved(base, property);
            List list = (List)base;
            int index = this.toInteger(property);
            if (index < 0 || index >= list.size()) {
                return null;
            }
            return list.get(index);
        }
        return null;
    }

    @Override
    public void setValue(ELContext context, Object base, Object property, Object val) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base != null && base instanceof List) {
            context.setPropertyResolved(base, property);
            List list = (List)base;
            int index = this.toInteger(property);
            if (this.isReadOnly) {
                throw new PropertyNotWritableException();
            }
            try {
                list.set(index, val);
            }
            catch (UnsupportedOperationException ex) {
                throw new PropertyNotWritableException();
            }
            catch (IndexOutOfBoundsException ex) {
                throw new PropertyNotFoundException();
            }
            catch (ClassCastException ex) {
                throw ex;
            }
            catch (NullPointerException ex) {
                throw ex;
            }
            catch (IllegalArgumentException ex) {
                throw ex;
            }
        }
    }

    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base != null && base instanceof List) {
            context.setPropertyResolved(true);
            List list = (List)base;
            int index = this.toInteger(property);
            if (index < 0 || index >= list.size()) {
                throw new PropertyNotFoundException();
            }
            return list.getClass() == theUnmodifiableListClass || this.isReadOnly;
        }
        return false;
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
        return null;
    }

    @Override
    public Class<?> getCommonPropertyType(ELContext context, Object base) {
        if (base != null && base instanceof List) {
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
        if (p instanceof Number) {
            return ((Number)p).intValue();
        }
        if (p instanceof String) {
            return Integer.parseInt((String)p);
        }
        throw new IllegalArgumentException();
    }
}

