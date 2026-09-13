/*
 * Decompiled with CFR 0.152.
 */
package javax.el;

import java.beans.BeanInfo;
import java.beans.FeatureDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.ELUtil;
import javax.el.LambdaExpression;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;

public class BeanELResolver
extends ELResolver {
    private boolean isReadOnly;
    private static final SoftConcurrentHashMap properties = new SoftConcurrentHashMap();

    public BeanELResolver() {
        this.isReadOnly = false;
    }

    public BeanELResolver(boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }

    @Override
    public Class<?> getType(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base == null || property == null) {
            return null;
        }
        BeanProperty bp = this.getBeanProperty(context, base, property);
        context.setPropertyResolved(true);
        return bp.getPropertyType();
    }

    @Override
    public Object getValue(ELContext context, Object base, Object property) {
        Object value;
        if (context == null) {
            throw new NullPointerException();
        }
        if (base == null || property == null) {
            return null;
        }
        BeanProperty bp = this.getBeanProperty(context, base, property);
        Method method = bp.getReadMethod();
        if (method == null) {
            throw new PropertyNotFoundException(ELUtil.getExceptionMessageString(context, "propertyNotReadable", new Object[]{base.getClass().getName(), property.toString()}));
        }
        try {
            value = method.invoke(base, new Object[0]);
            context.setPropertyResolved(base, property);
        }
        catch (ELException ex) {
            throw ex;
        }
        catch (InvocationTargetException ite) {
            throw new ELException(ite.getCause());
        }
        catch (Exception ex) {
            throw new ELException(ex);
        }
        return value;
    }

    @Override
    public void setValue(ELContext context, Object base, Object property, Object val) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base == null || property == null) {
            return;
        }
        if (this.isReadOnly) {
            throw new PropertyNotWritableException(ELUtil.getExceptionMessageString(context, "resolverNotwritable", new Object[]{base.getClass().getName()}));
        }
        BeanProperty bp = this.getBeanProperty(context, base, property);
        Method method = bp.getWriteMethod();
        if (method == null) {
            throw new PropertyNotWritableException(ELUtil.getExceptionMessageString(context, "propertyNotWritable", new Object[]{base.getClass().getName(), property.toString()}));
        }
        try {
            method.invoke(base, val);
            context.setPropertyResolved(base, property);
        }
        catch (ELException ex) {
            throw ex;
        }
        catch (InvocationTargetException ite) {
            throw new ELException(ite.getCause());
        }
        catch (Exception ex) {
            if (null == val) {
                val = "null";
            }
            String message = ELUtil.getExceptionMessageString(context, "setPropertyFailed", new Object[]{property.toString(), base.getClass().getName(), val});
            throw new ELException(message, ex);
        }
    }

    @Override
    public Object invoke(ELContext context, Object base, Object method, Class<?>[] paramTypes, Object[] params) {
        if (base == null || method == null) {
            return null;
        }
        Method m = ELUtil.findMethod(base.getClass(), method.toString(), paramTypes, params, false);
        for (Object p : params) {
            if (!(p instanceof LambdaExpression)) continue;
            ((LambdaExpression)p).setELContext(context);
        }
        Object ret = ELUtil.invokeMethod(context, m, base, params);
        context.setPropertyResolved(base, method);
        return ret;
    }

    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base == null || property == null) {
            return false;
        }
        context.setPropertyResolved(true);
        if (this.isReadOnly) {
            return true;
        }
        BeanProperty bp = this.getBeanProperty(context, base, property);
        return bp.isReadOnly();
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
        if (base == null) {
            return null;
        }
        BeanInfo info = null;
        try {
            info = Introspector.getBeanInfo(base.getClass());
        }
        catch (Exception ex) {
            // empty catch block
        }
        if (info == null) {
            return null;
        }
        ArrayList<PropertyDescriptor> list = new ArrayList<PropertyDescriptor>(info.getPropertyDescriptors().length);
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            pd.setValue("type", pd.getPropertyType());
            pd.setValue("resolvableAtDesignTime", Boolean.TRUE);
            list.add(pd);
        }
        return list.iterator();
    }

    @Override
    public Class<?> getCommonPropertyType(ELContext context, Object base) {
        if (base == null) {
            return null;
        }
        return Object.class;
    }

    private static Method getMethod(Class<?> cl, Method method) {
        if (method == null) {
            return null;
        }
        if (Modifier.isPublic(cl.getModifiers())) {
            return method;
        }
        Class<?>[] interfaces = cl.getInterfaces();
        for (int i = 0; i < interfaces.length; ++i) {
            Class<?> c = interfaces[i];
            Method m = null;
            try {
                m = c.getMethod(method.getName(), method.getParameterTypes());
                c = m.getDeclaringClass();
                m = BeanELResolver.getMethod(c, m);
                if (m == null) continue;
                return m;
            }
            catch (NoSuchMethodException ex) {
                // empty catch block
            }
        }
        Class<?> c = cl.getSuperclass();
        if (c != null) {
            Method m = null;
            try {
                m = c.getMethod(method.getName(), method.getParameterTypes());
                c = m.getDeclaringClass();
                m = BeanELResolver.getMethod(c, m);
                if (m != null) {
                    return m;
                }
            }
            catch (NoSuchMethodException ex) {
                // empty catch block
            }
        }
        return null;
    }

    private BeanProperty getBeanProperty(ELContext context, Object base, Object prop) {
        BeanProperty bp;
        String property = prop.toString();
        Class<?> baseClass = base.getClass();
        BeanProperties bps = properties.get(baseClass);
        if (bps == null) {
            bps = new BeanProperties(baseClass);
            properties.put(baseClass, bps);
        }
        if ((bp = bps.getBeanProperty(property)) == null) {
            throw new PropertyNotFoundException(ELUtil.getExceptionMessageString(context, "propertyNotFound", new Object[]{baseClass.getName(), property}));
        }
        return bp;
    }

    static final class BeanProperties {
        private final Map<String, BeanProperty> propertyMap = new HashMap<String, BeanProperty>();

        public BeanProperties(Class<?> baseClass) {
            PropertyDescriptor[] descriptors;
            try {
                BeanInfo info = Introspector.getBeanInfo(baseClass);
                descriptors = info.getPropertyDescriptors();
            }
            catch (IntrospectionException ie) {
                throw new ELException(ie);
            }
            for (PropertyDescriptor pd : descriptors) {
                this.propertyMap.put(pd.getName(), new BeanProperty(baseClass, pd));
            }
        }

        public BeanProperty getBeanProperty(String property) {
            return this.propertyMap.get(property);
        }
    }

    static final class BeanProperty {
        private Method readMethod;
        private Method writeMethod;
        private PropertyDescriptor descriptor;

        public BeanProperty(Class<?> baseClass, PropertyDescriptor descriptor) {
            this.descriptor = descriptor;
            this.readMethod = BeanELResolver.getMethod(baseClass, descriptor.getReadMethod());
            this.writeMethod = BeanELResolver.getMethod(baseClass, descriptor.getWriteMethod());
        }

        public Class getPropertyType() {
            return this.descriptor.getPropertyType();
        }

        public boolean isReadOnly() {
            return this.getWriteMethod() == null;
        }

        public Method getReadMethod() {
            return this.readMethod;
        }

        public Method getWriteMethod() {
            return this.writeMethod;
        }
    }

    private static class SoftConcurrentHashMap
    extends ConcurrentHashMap<Class<?>, BeanProperties> {
        private static final int CACHE_INIT_SIZE = 1024;
        private ConcurrentHashMap<Class<?>, BPSoftReference> map = new ConcurrentHashMap(1024);
        private ReferenceQueue<BeanProperties> refQ = new ReferenceQueue();

        private SoftConcurrentHashMap() {
        }

        private void cleanup() {
            BPSoftReference BPRef = null;
            while ((BPRef = (BPSoftReference)this.refQ.poll()) != null) {
                this.map.remove(BPRef.key);
            }
        }

        @Override
        public BeanProperties put(Class<?> key, BeanProperties value) {
            this.cleanup();
            BPSoftReference prev = this.map.put(key, new BPSoftReference(key, value, this.refQ));
            return prev == null ? null : (BeanProperties)prev.get();
        }

        @Override
        public BeanProperties putIfAbsent(Class<?> key, BeanProperties value) {
            this.cleanup();
            BPSoftReference prev = this.map.putIfAbsent(key, new BPSoftReference(key, value, this.refQ));
            return prev == null ? null : (BeanProperties)prev.get();
        }

        @Override
        public BeanProperties get(Object key) {
            this.cleanup();
            BPSoftReference BPRef = this.map.get(key);
            if (BPRef == null) {
                return null;
            }
            if (BPRef.get() == null) {
                this.map.remove(key);
                return null;
            }
            return (BeanProperties)BPRef.get();
        }
    }

    private static class BPSoftReference
    extends SoftReference<BeanProperties> {
        final Class<?> key;

        BPSoftReference(Class<?> key, BeanProperties beanProperties, ReferenceQueue<BeanProperties> refQ) {
            super(beanProperties, refQ);
            this.key = key;
        }
    }
}

