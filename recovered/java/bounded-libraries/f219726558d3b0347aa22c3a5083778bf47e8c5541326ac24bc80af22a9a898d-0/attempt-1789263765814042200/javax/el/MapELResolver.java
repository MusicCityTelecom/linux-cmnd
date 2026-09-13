/*
 * Decompiled with CFR 0.152.
 */
package javax.el;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.PropertyNotWritableException;

public class MapELResolver
extends ELResolver {
    private static Class<?> theUnmodifiableMapClass = Collections.unmodifiableMap(new HashMap()).getClass();
    private boolean isReadOnly;

    public MapELResolver() {
        this.isReadOnly = false;
    }

    public MapELResolver(boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }

    @Override
    public Class<?> getType(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base != null && base instanceof Map) {
            context.setPropertyResolved(true);
            return Object.class;
        }
        return null;
    }

    @Override
    public Object getValue(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base != null && base instanceof Map) {
            context.setPropertyResolved(base, property);
            Map map = (Map)base;
            return map.get(property);
        }
        return null;
    }

    @Override
    public void setValue(ELContext context, Object base, Object property, Object val) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base != null && base instanceof Map) {
            context.setPropertyResolved(base, property);
            Map map = (Map)base;
            if (this.isReadOnly || map.getClass() == theUnmodifiableMapClass) {
                throw new PropertyNotWritableException();
            }
            try {
                map.put(property, val);
            }
            catch (UnsupportedOperationException ex) {
                throw new PropertyNotWritableException();
            }
        }
    }

    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base != null && base instanceof Map) {
            context.setPropertyResolved(true);
            Map map = (Map)base;
            return this.isReadOnly || map.getClass() == theUnmodifiableMapClass;
        }
        return false;
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
        if (base != null && base instanceof Map) {
            Map map = (Map)base;
            Iterator iter = map.keySet().iterator();
            ArrayList<FeatureDescriptor> list = new ArrayList<FeatureDescriptor>();
            while (iter.hasNext()) {
                Object key = iter.next();
                FeatureDescriptor descriptor = new FeatureDescriptor();
                String name = key == null ? null : key.toString();
                descriptor.setName(name);
                descriptor.setDisplayName(name);
                descriptor.setShortDescription("");
                descriptor.setExpert(false);
                descriptor.setHidden(false);
                descriptor.setPreferred(true);
                if (key != null) {
                    descriptor.setValue("type", key.getClass());
                }
                descriptor.setValue("resolvableAtDesignTime", Boolean.TRUE);
                list.add(descriptor);
            }
            return list.iterator();
        }
        return null;
    }

    @Override
    public Class<?> getCommonPropertyType(ELContext context, Object base) {
        if (base != null && base instanceof Map) {
            return Object.class;
        }
        return null;
    }
}

