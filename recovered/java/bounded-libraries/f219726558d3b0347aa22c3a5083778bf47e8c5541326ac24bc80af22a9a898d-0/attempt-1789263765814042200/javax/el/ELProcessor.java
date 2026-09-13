/*
 * Decompiled with CFR 0.152.
 */
package javax.el;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import javax.el.ELManager;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

public class ELProcessor {
    private ELManager elManager = new ELManager();
    private ExpressionFactory factory;

    public ELProcessor() {
        ELProcessor eLProcessor = this;
        this.factory = eLProcessor.elManager.getExpressionFactory();
    }

    public ELManager getELManager() {
        return this.elManager;
    }

    public Object eval(String expression) {
        return this.getValue(expression, Object.class);
    }

    public Object getValue(String expression, Class<?> expectedType) {
        ValueExpression exp = this.factory.createValueExpression(this.elManager.getELContext(), this.bracket(expression), expectedType);
        return exp.getValue(this.elManager.getELContext());
    }

    public void setValue(String expression, Object value) {
        ValueExpression exp = this.factory.createValueExpression(this.elManager.getELContext(), this.bracket(expression), Object.class);
        exp.setValue(this.elManager.getELContext(), value);
    }

    public void setVariable(String var, String expression) {
        ValueExpression exp = this.factory.createValueExpression(this.elManager.getELContext(), this.bracket(expression), Object.class);
        this.elManager.setVariable(var, exp);
    }

    public void defineFunction(String prefix, String function, String className, String method) throws ClassNotFoundException, NoSuchMethodException {
        if (prefix == null || function == null || className == null || method == null) {
            throw new NullPointerException("Null argument for defineFunction");
        }
        Method meth = null;
        ClassLoader loader = this.getClass().getClassLoader();
        Class<?> klass = Class.forName(className, false, loader);
        int j = method.indexOf(40);
        if (j < 0) {
            for (Method m : klass.getDeclaredMethods()) {
                if (!m.getName().equals(method)) continue;
                meth = m;
            }
            if (meth == null) {
                throw new NoSuchMethodException();
            }
        } else {
            int p = method.indexOf(32);
            if (p < 0) {
                throw new NoSuchMethodException("Bad method singnature: " + method);
            }
            String methodName = method.substring(p + 1, j).trim();
            p = method.indexOf(41, j + 1);
            if (p < 0) {
                throw new NoSuchMethodException("Bad method singnature: " + method);
            }
            String[] params = method.substring(j + 1, p).split(",");
            Class[] paramTypes = new Class[params.length];
            for (int i = 0; i < params.length; ++i) {
                paramTypes[i] = ELProcessor.toClass(params[i], loader);
            }
            meth = klass.getDeclaredMethod(methodName, paramTypes);
        }
        if (!Modifier.isStatic(meth.getModifiers())) {
            throw new NoSuchMethodException("The method specified in defineFunction must be static: " + meth);
        }
        if (function.equals("")) {
            function = method;
        }
        this.elManager.mapFunction(prefix, function, meth);
    }

    public void defineFunction(String prefix, String function, Method method) throws NoSuchMethodException {
        if (prefix == null || function == null || method == null) {
            throw new NullPointerException("Null argument for defineFunction");
        }
        if (!Modifier.isStatic(method.getModifiers())) {
            throw new NoSuchMethodException("The method specified in defineFunction must be static: " + method);
        }
        if (function.equals("")) {
            function = method.getName();
        }
        this.elManager.mapFunction(prefix, function, method);
    }

    public void defineBean(String name, Object bean) {
        this.elManager.defineBean(name, bean);
    }

    private static Class<?> toClass(String type, ClassLoader loader) throws ClassNotFoundException {
        Class<Serializable> c = null;
        int i0 = type.indexOf(91);
        int dims = 0;
        if (i0 > 0) {
            for (int i = 0; i < type.length(); ++i) {
                if (type.charAt(i) != '[') continue;
                ++dims;
            }
            type = type.substring(0, i0);
        }
        c = "boolean".equals(type) ? Boolean.TYPE : ("char".equals(type) ? Character.TYPE : ("byte".equals(type) ? Byte.TYPE : ("short".equals(type) ? Short.TYPE : ("int".equals(type) ? Integer.TYPE : ("long".equals(type) ? Long.TYPE : ("float".equals(type) ? Float.TYPE : ("double".equals(type) ? Double.TYPE : loader.loadClass(type))))))));
        if (dims == 0) {
            return c;
        }
        if (dims == 1) {
            return Array.newInstance(c, 1).getClass();
        }
        return Array.newInstance(c, new int[dims]).getClass();
    }

    private String bracket(String expression) {
        return "${" + expression + '}';
    }
}

