/*
 * Decompiled with CFR 0.152.
 */
package javax.el;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.FactoryFinder;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

public abstract class ExpressionFactory {
    public static ExpressionFactory newInstance() {
        return ExpressionFactory.newInstance(null);
    }

    public static ExpressionFactory newInstance(Properties properties) {
        return (ExpressionFactory)FactoryFinder.find("javax.el.ExpressionFactory", "com.sun.el.ExpressionFactoryImpl", properties);
    }

    public abstract ValueExpression createValueExpression(ELContext var1, String var2, Class<?> var3);

    public abstract ValueExpression createValueExpression(Object var1, Class<?> var2);

    public abstract MethodExpression createMethodExpression(ELContext var1, String var2, Class<?> var3, Class<?>[] var4);

    public abstract Object coerceToType(Object var1, Class<?> var2);

    public ELResolver getStreamELResolver() {
        return null;
    }

    public Map<String, Method> getInitFunctionMap() {
        return null;
    }
}

