/*
 * Decompiled with CFR 0.152.
 */
package javax.el;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.ELUtil;
import javax.el.EvaluationListener;
import javax.el.FunctionMapper;
import javax.el.ImportHandler;
import javax.el.VariableMapper;

public abstract class ELContext {
    private Locale locale;
    private boolean resolved;
    private HashMap<Class<?>, Object> map = new HashMap();
    private transient List<EvaluationListener> listeners = null;
    private Stack<Map<String, Object>> lambdaArgs;
    private ImportHandler importHandler;

    public void setPropertyResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public void setPropertyResolved(Object base, Object property) {
        this.setPropertyResolved(true);
        this.notifyPropertyResolved(base, property);
    }

    public boolean isPropertyResolved() {
        return this.resolved;
    }

    public void putContext(Class key, Object contextObject) {
        if (key == null || contextObject == null) {
            throw new NullPointerException();
        }
        this.map.put(key, contextObject);
    }

    public Object getContext(Class key) {
        if (key == null) {
            throw new NullPointerException();
        }
        return this.map.get(key);
    }

    public abstract ELResolver getELResolver();

    public ImportHandler getImportHandler() {
        if (this.importHandler == null) {
            this.importHandler = new ImportHandler();
        }
        return this.importHandler;
    }

    public abstract FunctionMapper getFunctionMapper();

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public abstract VariableMapper getVariableMapper();

    public void addEvaluationListener(EvaluationListener listener) {
        if (this.listeners == null) {
            this.listeners = new ArrayList<EvaluationListener>();
        }
        this.listeners.add(listener);
    }

    public List<EvaluationListener> getEvaluationListeners() {
        return this.listeners;
    }

    public void notifyBeforeEvaluation(String expr) {
        if (this.getEvaluationListeners() == null) {
            return;
        }
        for (EvaluationListener listener : this.getEvaluationListeners()) {
            listener.beforeEvaluation(this, expr);
        }
    }

    public void notifyAfterEvaluation(String expr) {
        if (this.getEvaluationListeners() == null) {
            return;
        }
        for (EvaluationListener listener : this.getEvaluationListeners()) {
            listener.afterEvaluation(this, expr);
        }
    }

    public void notifyPropertyResolved(Object base, Object property) {
        if (this.getEvaluationListeners() == null) {
            return;
        }
        for (EvaluationListener listener : this.getEvaluationListeners()) {
            listener.propertyResolved(this, base, property);
        }
    }

    public boolean isLambdaArgument(String arg) {
        if (this.lambdaArgs == null) {
            return false;
        }
        for (int i = this.lambdaArgs.size() - 1; i >= 0; --i) {
            Map lmap = (Map)this.lambdaArgs.elementAt(i);
            if (!lmap.containsKey(arg)) continue;
            return true;
        }
        return false;
    }

    public Object getLambdaArgument(String arg) {
        if (this.lambdaArgs == null) {
            return null;
        }
        for (int i = this.lambdaArgs.size() - 1; i >= 0; --i) {
            Map lmap = (Map)this.lambdaArgs.elementAt(i);
            Object v = lmap.get(arg);
            if (v == null) continue;
            return v;
        }
        return null;
    }

    public void enterLambdaScope(Map<String, Object> args) {
        if (this.lambdaArgs == null) {
            this.lambdaArgs = new Stack();
        }
        this.lambdaArgs.push(args);
    }

    public void exitLambdaScope() {
        if (this.lambdaArgs != null) {
            this.lambdaArgs.pop();
        }
    }

    public Object convertToType(Object obj, Class<?> targetType) {
        boolean propertyResolvedSave = this.isPropertyResolved();
        try {
            this.setPropertyResolved(false);
            ELResolver elResolver = this.getELResolver();
            if (elResolver != null) {
                Object res = elResolver.convertToType(this, obj, targetType);
                if (this.isPropertyResolved()) {
                    Object object = res;
                    return object;
                }
            }
        }
        catch (ELException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new ELException(ex);
        }
        finally {
            this.setPropertyResolved(propertyResolvedSave);
        }
        return ELUtil.getExpressionFactory().coerceToType(obj, targetType);
    }
}

