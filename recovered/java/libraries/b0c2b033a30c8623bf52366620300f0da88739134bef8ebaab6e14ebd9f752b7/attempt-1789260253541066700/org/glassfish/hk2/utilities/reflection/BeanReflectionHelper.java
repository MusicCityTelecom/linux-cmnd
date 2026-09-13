/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.reflection;

import java.beans.Introspector;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import org.glassfish.hk2.utilities.general.GeneralUtilities;
import org.glassfish.hk2.utilities.reflection.ClassReflectionHelper;
import org.glassfish.hk2.utilities.reflection.MethodWrapper;

public class BeanReflectionHelper {
    private static final String GET = "get";
    private static final String IS = "is";

    public static String getBeanPropertyNameFromGetter(Method method) {
        return BeanReflectionHelper.isAGetter(method);
    }

    private static String isAGetter(MethodWrapper method) {
        return BeanReflectionHelper.isAGetter(method.getMethod());
    }

    private static String isAGetter(Method m) {
        int capIndex;
        String name = m.getName();
        if (Void.TYPE.equals(m.getReturnType())) {
            return null;
        }
        Class<?>[] params = m.getParameterTypes();
        if (params.length != 0) {
            return null;
        }
        if ((m.getModifiers() & 1) == 0) {
            return null;
        }
        if (name.startsWith(GET) && name.length() > GET.length()) {
            capIndex = GET.length();
        } else if (name.startsWith(IS) && name.length() > IS.length()) {
            capIndex = IS.length();
        } else {
            return null;
        }
        if (!Character.isUpperCase(name.charAt(capIndex))) {
            return null;
        }
        String rawPropName = name.substring(capIndex);
        return Introspector.decapitalize(rawPropName);
    }

    private static Method findMethod(Method m, Class<?> c) {
        String name = m.getName();
        Class[] params = new Class[]{};
        try {
            return c.getMethod(name, params);
        }
        catch (Throwable th) {
            return null;
        }
    }

    private static Object getValue(Object bean, Method m) {
        try {
            return m.invoke(bean, new Object[0]);
        }
        catch (Throwable th) {
            return null;
        }
    }

    private static PropertyChangeEvent[] getMapChangeEvents(Map<String, Object> oldBean, Map<String, Object> newBean) {
        LinkedList<PropertyChangeEvent> retVal = new LinkedList<PropertyChangeEvent>();
        HashSet<String> newKeys = new HashSet<String>(newBean.keySet());
        for (Map.Entry<String, Object> entry : oldBean.entrySet()) {
            String key = entry.getKey();
            Object oldValue = entry.getValue();
            Object newValue = newBean.get(key);
            newKeys.remove(key);
            if (GeneralUtilities.safeEquals(oldValue, newValue)) continue;
            retVal.add(new PropertyChangeEvent(newBean, key, oldValue, newValue));
        }
        for (String newKey : newKeys) {
            retVal.add(new PropertyChangeEvent(newBean, newKey, null, newBean.get(newKey)));
        }
        return retVal.toArray(new PropertyChangeEvent[retVal.size()]);
    }

    public static PropertyChangeEvent[] getChangeEvents(ClassReflectionHelper helper, Object oldBean, Object newBean) {
        if (oldBean instanceof Map) {
            return BeanReflectionHelper.getMapChangeEvents((Map)oldBean, (Map)newBean);
        }
        LinkedList<PropertyChangeEvent> retVal = new LinkedList<PropertyChangeEvent>();
        Set<MethodWrapper> methods = helper.getAllMethods(oldBean.getClass());
        for (MethodWrapper wrapper : methods) {
            Object newValue;
            Object oldValue;
            Method method;
            Method newMethod;
            String propName = BeanReflectionHelper.isAGetter(wrapper);
            if (propName == null || (newMethod = BeanReflectionHelper.findMethod(method = wrapper.getMethod(), newBean.getClass())) == null || GeneralUtilities.safeEquals(oldValue = BeanReflectionHelper.getValue(oldBean, method), newValue = BeanReflectionHelper.getValue(newBean, newMethod))) continue;
            retVal.add(new PropertyChangeEvent(newBean, propName, oldValue, newValue));
        }
        return retVal.toArray(new PropertyChangeEvent[retVal.size()]);
    }

    public static Map<String, Object> convertJavaBeanToBeanLikeMap(ClassReflectionHelper helper, Object bean) {
        HashMap<String, Object> retVal = new HashMap<String, Object>();
        Set<MethodWrapper> methods = helper.getAllMethods(bean.getClass());
        for (MethodWrapper wrapper : methods) {
            String propName = BeanReflectionHelper.isAGetter(wrapper);
            if (propName == null || "class".equals(propName)) continue;
            Method method = wrapper.getMethod();
            Object value = BeanReflectionHelper.getValue(bean, method);
            retVal.put(propName, value);
        }
        return retVal;
    }
}

