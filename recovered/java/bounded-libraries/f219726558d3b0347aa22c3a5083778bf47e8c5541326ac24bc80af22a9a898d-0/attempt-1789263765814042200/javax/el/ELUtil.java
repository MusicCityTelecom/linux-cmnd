/*
 * Decompiled with CFR 0.152.
 */
package javax.el;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.el.MethodNotFoundException;

class ELUtil {
    public static ExpressionFactory exprFactory = ExpressionFactory.newInstance();
    private static ThreadLocal<Map<String, ResourceBundle>> instance = new ThreadLocal<Map<String, ResourceBundle>>(){

        @Override
        protected Map<String, ResourceBundle> initialValue() {
            return null;
        }
    };

    private ELUtil() {
    }

    private static Map<String, ResourceBundle> getCurrentInstance() {
        Map<String, ResourceBundle> result = instance.get();
        if (null == result) {
            result = new HashMap<String, ResourceBundle>();
            ELUtil.setCurrentInstance(result);
        }
        return result;
    }

    private static void setCurrentInstance(Map<String, ResourceBundle> context) {
        instance.set(context);
    }

    public static String getExceptionMessageString(ELContext context, String messageId) {
        return ELUtil.getExceptionMessageString(context, messageId, null);
    }

    public static String getExceptionMessageString(ELContext context, String messageId, Object[] params) {
        String result = "";
        Locale locale = null;
        if (null == context || null == messageId) {
            return result;
        }
        locale = context.getLocale();
        if (null == locale) {
            locale = Locale.getDefault();
        }
        if (null != locale) {
            Map<String, ResourceBundle> threadMap = ELUtil.getCurrentInstance();
            ResourceBundle rb = null;
            rb = threadMap.get(locale.toString());
            if (null == rb) {
                rb = ResourceBundle.getBundle("javax.el.PrivateMessages", locale);
                threadMap.put(locale.toString(), rb);
            }
            if (null != rb) {
                try {
                    result = rb.getString(messageId);
                    if (null != params) {
                        result = MessageFormat.format(result, params);
                    }
                }
                catch (IllegalArgumentException iae) {
                    result = "Can't get localized message: parameters to message appear to be incorrect.  Message to format: " + messageId;
                }
                catch (MissingResourceException mre) {
                    result = "Missing Resource in EL implementation: ???" + messageId + "???";
                }
                catch (Exception e) {
                    result = "Exception resolving message in EL implementation: ???" + messageId + "???";
                }
            }
        }
        return result;
    }

    static ExpressionFactory getExpressionFactory() {
        return exprFactory;
    }

    static Constructor<?> findConstructor(Class<?> klass, Class<?>[] paramTypes, Object[] params) {
        if (paramTypes != null) {
            try {
                Constructor<?> c = klass.getConstructor(paramTypes);
                if (Modifier.isPublic(c.getModifiers())) {
                    return c;
                }
            }
            catch (NoSuchMethodException ex) {
                // empty catch block
            }
            throw new MethodNotFoundException("The constructor for class " + klass + " not found or accessible");
        }
        int paramCount = params == null ? 0 : params.length;
        for (Constructor<?> c : klass.getConstructors()) {
            if (!c.isVarArgs() && c.getParameterTypes().length != paramCount) continue;
            return c;
        }
        throw new MethodNotFoundException("The constructor for class " + klass + " not found");
    }

    static Object invokeConstructor(ELContext context, Constructor<?> c, Object[] params) {
        Class<?>[] parameterTypes = c.getParameterTypes();
        Object[] parameters = null;
        if (parameterTypes.length > 0 && !c.isVarArgs()) {
            parameters = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; ++i) {
                parameters[i] = context.convertToType(params[i], parameterTypes[i]);
            }
        }
        try {
            return c.newInstance(parameters);
        }
        catch (IllegalAccessException iae) {
            throw new ELException(iae);
        }
        catch (InvocationTargetException ite) {
            throw new ELException(ite.getCause());
        }
        catch (InstantiationException ie) {
            throw new ELException(ie.getCause());
        }
    }

    static Method findMethod(Class<?> klass, String method, Class<?>[] paramTypes, Object[] params, boolean staticOnly) {
        if (paramTypes != null) {
            try {
                Method m = klass.getMethod(method, paramTypes);
                int mod = m.getModifiers();
                if (Modifier.isPublic(mod) && (!staticOnly || Modifier.isStatic(mod))) {
                    return m;
                }
            }
            catch (NoSuchMethodException ex) {
                // empty catch block
            }
            throw new MethodNotFoundException("Method " + method + "for class " + klass + " not found or accessible");
        }
        int paramCount = params == null ? 0 : params.length;
        for (Method m : klass.getMethods()) {
            if (!m.getName().equals(method) || !m.isVarArgs() && m.getParameterTypes().length != paramCount) continue;
            return m;
        }
        throw new MethodNotFoundException("Method " + method + " not found");
    }

    static Object invokeMethod(ELContext context, Method m, Object base, Object[] params) {
        Class<?>[] parameterTypes = m.getParameterTypes();
        Object[] parameters = null;
        if (parameterTypes.length > 0 && !m.isVarArgs()) {
            parameters = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; ++i) {
                parameters[i] = context.convertToType(params[i], parameterTypes[i]);
            }
        }
        try {
            return m.invoke(base, parameters);
        }
        catch (IllegalAccessException iae) {
            throw new ELException(iae);
        }
        catch (InvocationTargetException ite) {
            throw new ELException(ite.getCause());
        }
    }
}

