/*
 * Decompiled with CFR 0.152.
 */
package javax.el;

import java.beans.FeatureDescriptor;
import java.util.Iterator;
import javax.el.BeanNameResolver;
import javax.el.ELContext;
import javax.el.ELResolver;

public class BeanNameELResolver
extends ELResolver {
    private BeanNameResolver beanNameResolver;

    public BeanNameELResolver(BeanNameResolver beanNameResolver) {
        this.beanNameResolver = beanNameResolver;
    }

    @Override
    public Object getValue(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base == null && property instanceof String && this.beanNameResolver.isNameResolved((String)property)) {
            context.setPropertyResolved(base, property);
            return this.beanNameResolver.getBean((String)property);
        }
        return null;
    }

    @Override
    public void setValue(ELContext context, Object base, Object property, Object value) {
        String beanName;
        if (context == null) {
            throw new NullPointerException();
        }
        if (base == null && property instanceof String && (this.beanNameResolver.isNameResolved(beanName = (String)property) || this.beanNameResolver.canCreateBean(beanName))) {
            this.beanNameResolver.setBeanValue(beanName, value);
            context.setPropertyResolved(base, property);
        }
    }

    @Override
    public Class<?> getType(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base == null && property instanceof String && this.beanNameResolver.isNameResolved((String)property)) {
            context.setPropertyResolved(true);
            return this.beanNameResolver.getBean((String)property).getClass();
        }
        return null;
    }

    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base == null && property instanceof String && this.beanNameResolver.isNameResolved((String)property)) {
            context.setPropertyResolved(true);
            return this.beanNameResolver.isReadOnly((String)property);
        }
        return false;
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
        return null;
    }

    @Override
    public Class<?> getCommonPropertyType(ELContext context, Object base) {
        return String.class;
    }
}

