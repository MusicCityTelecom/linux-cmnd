/*
 * Decompiled with CFR 0.152.
 */
package javax.el;

import java.beans.FeatureDescriptor;
import java.util.Iterator;
import javax.el.ELContext;
import javax.el.ELResolver;

public class CompositeELResolver
extends ELResolver {
    private ELResolver[] elResolvers = new ELResolver[16];
    private int size = 0;

    public void add(ELResolver elResolver) {
        if (elResolver == null) {
            throw new NullPointerException();
        }
        if (this.size >= this.elResolvers.length) {
            ELResolver[] newResolvers = new ELResolver[this.size * 2];
            System.arraycopy(this.elResolvers, 0, newResolvers, 0, this.size);
            this.elResolvers = newResolvers;
        }
        this.elResolvers[this.size++] = elResolver;
    }

    @Override
    public Object getValue(ELContext context, Object base, Object property) {
        context.setPropertyResolved(false);
        Object value = null;
        for (int i = 0; i < this.size; ++i) {
            value = this.elResolvers[i].getValue(context, base, property);
            if (!context.isPropertyResolved()) continue;
            return value;
        }
        return null;
    }

    @Override
    public Object invoke(ELContext context, Object base, Object method, Class<?>[] paramTypes, Object[] params) {
        context.setPropertyResolved(false);
        for (int i = 0; i < this.size; ++i) {
            Object value = this.elResolvers[i].invoke(context, base, method, paramTypes, params);
            if (!context.isPropertyResolved()) continue;
            return value;
        }
        return null;
    }

    @Override
    public Class<?> getType(ELContext context, Object base, Object property) {
        context.setPropertyResolved(false);
        for (int i = 0; i < this.size; ++i) {
            Class<?> type = this.elResolvers[i].getType(context, base, property);
            if (!context.isPropertyResolved()) continue;
            return type;
        }
        return null;
    }

    @Override
    public void setValue(ELContext context, Object base, Object property, Object val) {
        context.setPropertyResolved(false);
        for (int i = 0; i < this.size; ++i) {
            this.elResolvers[i].setValue(context, base, property, val);
            if (!context.isPropertyResolved()) continue;
            return;
        }
    }

    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property) {
        context.setPropertyResolved(false);
        for (int i = 0; i < this.size; ++i) {
            boolean readOnly = this.elResolvers[i].isReadOnly(context, base, property);
            if (!context.isPropertyResolved()) continue;
            return readOnly;
        }
        return false;
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
        return new CompositeIterator(this.elResolvers, this.size, context, base);
    }

    @Override
    public Class<?> getCommonPropertyType(ELContext context, Object base) {
        Class<?> commonPropertyType = null;
        for (int i = 0; i < this.size; ++i) {
            Class<?> type = this.elResolvers[i].getCommonPropertyType(context, base);
            if (type == null) continue;
            if (commonPropertyType == null) {
                commonPropertyType = type;
                continue;
            }
            if (commonPropertyType.isAssignableFrom(type)) continue;
            if (type.isAssignableFrom(commonPropertyType)) {
                commonPropertyType = type;
                continue;
            }
            return null;
        }
        return commonPropertyType;
    }

    @Override
    public Object convertToType(ELContext context, Object obj, Class<?> targetType) {
        context.setPropertyResolved(false);
        Object value = null;
        for (int i = 0; i < this.size; ++i) {
            value = this.elResolvers[i].convertToType(context, obj, targetType);
            if (!context.isPropertyResolved()) continue;
            return value;
        }
        return null;
    }

    private static class CompositeIterator
    implements Iterator<FeatureDescriptor> {
        ELResolver[] resolvers;
        int size;
        int index = 0;
        Iterator<FeatureDescriptor> propertyIter = null;
        ELContext context;
        Object base;

        CompositeIterator(ELResolver[] resolvers, int size, ELContext context, Object base) {
            this.resolvers = resolvers;
            this.size = size;
            this.context = context;
            this.base = base;
        }

        @Override
        public boolean hasNext() {
            if (this.propertyIter == null || !this.propertyIter.hasNext()) {
                while (this.index < this.size) {
                    ELResolver elResolver = this.resolvers[this.index++];
                    this.propertyIter = elResolver.getFeatureDescriptors(this.context, this.base);
                    if (this.propertyIter == null) continue;
                    return this.propertyIter.hasNext();
                }
                return false;
            }
            return this.propertyIter.hasNext();
        }

        @Override
        public FeatureDescriptor next() {
            if (this.propertyIter == null || !this.propertyIter.hasNext()) {
                while (this.index < this.size) {
                    ELResolver elResolver = this.resolvers[this.index++];
                    this.propertyIter = elResolver.getFeatureDescriptors(this.context, this.base);
                    if (this.propertyIter == null) continue;
                    return this.propertyIter.next();
                }
                return null;
            }
            return this.propertyIter.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

