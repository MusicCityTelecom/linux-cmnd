/*
 * Decompiled with CFR 0.152.
 */
package javax.el;

import java.beans.FeatureDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import javax.el.ELClass;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.ELUtil;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;

public class StaticFieldELResolver
extends ELResolver {
    @Override
    public Object getValue(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base instanceof ELClass && property instanceof String) {
            Class<?> klass = ((ELClass)base).getKlass();
            String fieldName = (String)property;
            try {
                context.setPropertyResolved(base, property);
                Field field = klass.getField(fieldName);
                int mod = field.getModifiers();
                if (Modifier.isPublic(mod) && Modifier.isStatic(mod)) {
                    return field.get(null);
                }
            }
            catch (NoSuchFieldException ex) {
            }
            catch (IllegalAccessException ex) {
                // empty catch block
            }
            throw new PropertyNotFoundException(ELUtil.getExceptionMessageString(context, "staticFieldReadError", new Object[]{klass.getName(), fieldName}));
        }
        return null;
    }

    @Override
    public void setValue(ELContext context, Object base, Object property, Object value) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base instanceof ELClass && property instanceof String) {
            Class<?> klass = ((ELClass)base).getKlass();
            String fieldName = (String)property;
            throw new PropertyNotWritableException(ELUtil.getExceptionMessageString(context, "staticFieldWriteError", new Object[]{klass.getName(), fieldName}));
        }
    }

    @Override
    public Object invoke(ELContext context, Object base, Object method, Class<?>[] paramTypes, Object[] params) {
        Object ret;
        if (context == null) {
            throw new NullPointerException();
        }
        if (!(base instanceof ELClass) || !(method instanceof String)) {
            return null;
        }
        Class<?> klass = ((ELClass)base).getKlass();
        String name = (String)method;
        if ("<init>".equals(name)) {
            Constructor<?> constructor = ELUtil.findConstructor(klass, paramTypes, params);
            ret = ELUtil.invokeConstructor(context, constructor, params);
        } else {
            Method meth = ELUtil.findMethod(klass, name, paramTypes, params, true);
            ret = ELUtil.invokeMethod(context, meth, null, params);
        }
        context.setPropertyResolved(base, method);
        return ret;
    }

    @Override
    public Class<?> getType(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base instanceof ELClass && property instanceof String) {
            Class<?> klass = ((ELClass)base).getKlass();
            String fieldName = (String)property;
            try {
                context.setPropertyResolved(true);
                Field field = klass.getField(fieldName);
                int mod = field.getModifiers();
                if (Modifier.isPublic(mod) && Modifier.isStatic(mod)) {
                    return field.getType();
                }
            }
            catch (NoSuchFieldException ex) {
                // empty catch block
            }
            throw new PropertyNotFoundException(ELUtil.getExceptionMessageString(context, "staticFieldReadError", new Object[]{klass.getName(), fieldName}));
        }
        return null;
    }

    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base instanceof ELClass && property instanceof String) {
            Class<?> klass = ((ELClass)base).getKlass();
            context.setPropertyResolved(true);
        }
        return true;
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

