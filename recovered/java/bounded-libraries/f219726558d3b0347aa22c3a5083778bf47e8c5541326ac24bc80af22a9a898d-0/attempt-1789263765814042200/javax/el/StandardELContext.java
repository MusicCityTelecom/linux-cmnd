/*
 * Decompiled with CFR 0.152.
 */
package javax.el;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.el.ArrayELResolver;
import javax.el.BeanELResolver;
import javax.el.BeanNameELResolver;
import javax.el.BeanNameResolver;
import javax.el.CompositeELResolver;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.FunctionMapper;
import javax.el.ListELResolver;
import javax.el.MapELResolver;
import javax.el.ResourceBundleELResolver;
import javax.el.StaticFieldELResolver;
import javax.el.ValueExpression;
import javax.el.VariableMapper;

public class StandardELContext
extends ELContext {
    private ELResolver elResolver;
    private CompositeELResolver customResolvers;
    private ELResolver streamELResolver;
    private FunctionMapper functionMapper;
    private Map<String, Method> initFunctionMap;
    private VariableMapper variableMapper;
    private ELContext delegate = null;
    private Map<String, Object> beans = new HashMap<String, Object>();

    public StandardELContext(ExpressionFactory factory) {
        this.streamELResolver = factory.getStreamELResolver();
        this.initFunctionMap = factory.getInitFunctionMap();
    }

    public StandardELContext(ELContext context) {
        this.delegate = context;
        CompositeELResolver elr = new CompositeELResolver();
        elr.add(new BeanNameELResolver(new LocalBeanNameResolver()));
        this.customResolvers = new CompositeELResolver();
        elr.add(this.customResolvers);
        elr.add(context.getELResolver());
        this.elResolver = elr;
        this.functionMapper = context.getFunctionMapper();
        this.variableMapper = context.getVariableMapper();
        this.setLocale(context.getLocale());
    }

    @Override
    public void putContext(Class key, Object contextObject) {
        if (this.delegate != null) {
            this.delegate.putContext(key, contextObject);
        } else {
            super.putContext(key, contextObject);
        }
    }

    @Override
    public Object getContext(Class key) {
        if (this.delegate != null) {
            return this.delegate.getContext(key);
        }
        return super.getContext(key);
    }

    @Override
    public ELResolver getELResolver() {
        if (this.elResolver == null) {
            CompositeELResolver resolver = new CompositeELResolver();
            resolver.add(new BeanNameELResolver(new LocalBeanNameResolver()));
            this.customResolvers = new CompositeELResolver();
            resolver.add(this.customResolvers);
            if (this.streamELResolver != null) {
                resolver.add(this.streamELResolver);
            }
            resolver.add(new StaticFieldELResolver());
            resolver.add(new MapELResolver());
            resolver.add(new ResourceBundleELResolver());
            resolver.add(new ListELResolver());
            resolver.add(new ArrayELResolver());
            resolver.add(new BeanELResolver());
            this.elResolver = resolver;
        }
        return this.elResolver;
    }

    public void addELResolver(ELResolver cELResolver) {
        this.getELResolver();
        this.customResolvers.add(cELResolver);
    }

    Map<String, Object> getBeans() {
        return this.beans;
    }

    @Override
    public FunctionMapper getFunctionMapper() {
        if (this.functionMapper == null) {
            this.functionMapper = new DefaultFunctionMapper(this.initFunctionMap);
        }
        return this.functionMapper;
    }

    @Override
    public VariableMapper getVariableMapper() {
        if (this.variableMapper == null) {
            this.variableMapper = new DefaultVariableMapper();
        }
        return this.variableMapper;
    }

    private class LocalBeanNameResolver
    extends BeanNameResolver {
        private LocalBeanNameResolver() {
        }

        @Override
        public boolean isNameResolved(String beanName) {
            return StandardELContext.this.beans.containsKey(beanName);
        }

        @Override
        public Object getBean(String beanName) {
            return StandardELContext.this.beans.get(beanName);
        }

        @Override
        public void setBeanValue(String beanName, Object value) {
            StandardELContext.this.beans.put(beanName, value);
        }

        @Override
        public boolean isReadOnly(String beanName) {
            return false;
        }

        @Override
        public boolean canCreateBean(String beanName) {
            return true;
        }
    }

    private static class DefaultVariableMapper
    extends VariableMapper {
        private Map<String, ValueExpression> variables = null;

        private DefaultVariableMapper() {
        }

        @Override
        public ValueExpression resolveVariable(String variable) {
            if (this.variables == null) {
                return null;
            }
            return this.variables.get(variable);
        }

        @Override
        public ValueExpression setVariable(String variable, ValueExpression expression) {
            if (this.variables == null) {
                this.variables = new HashMap<String, ValueExpression>();
            }
            ValueExpression prev = null;
            prev = expression == null ? this.variables.remove(variable) : this.variables.put(variable, expression);
            return prev;
        }
    }

    private static class DefaultFunctionMapper
    extends FunctionMapper {
        private Map<String, Method> functions = null;

        DefaultFunctionMapper(Map<String, Method> initMap) {
            this.functions = initMap == null ? new HashMap<String, Method>() : new HashMap<String, Method>(initMap);
        }

        @Override
        public Method resolveFunction(String prefix, String localName) {
            return this.functions.get(prefix + ":" + localName);
        }

        @Override
        public void mapFunction(String prefix, String localName, Method meth) {
            this.functions.put(prefix + ":" + localName, meth);
        }
    }
}

