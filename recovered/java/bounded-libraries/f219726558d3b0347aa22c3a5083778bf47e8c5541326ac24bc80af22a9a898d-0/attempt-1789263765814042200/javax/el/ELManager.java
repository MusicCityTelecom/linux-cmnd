/*
 * Decompiled with CFR 0.152.
 */
package javax.el;

import java.lang.reflect.Method;
import javax.el.BeanNameELResolver;
import javax.el.BeanNameResolver;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.ELUtil;
import javax.el.EvaluationListener;
import javax.el.ExpressionFactory;
import javax.el.StandardELContext;
import javax.el.ValueExpression;

public class ELManager {
    private StandardELContext elContext;

    public static ExpressionFactory getExpressionFactory() {
        return ELUtil.getExpressionFactory();
    }

    public StandardELContext getELContext() {
        if (this.elContext == null) {
            this.elContext = new StandardELContext(ELManager.getExpressionFactory());
        }
        return this.elContext;
    }

    public ELContext setELContext(ELContext context) {
        StandardELContext prev = this.elContext;
        this.elContext = new StandardELContext(context);
        return prev;
    }

    public void addBeanNameResolver(BeanNameResolver bnr) {
        this.getELContext().addELResolver(new BeanNameELResolver(bnr));
    }

    public void addELResolver(ELResolver elr) {
        this.getELContext().addELResolver(elr);
    }

    public void mapFunction(String prefix, String function, Method meth) {
        this.getELContext().getFunctionMapper().mapFunction(prefix, function, meth);
    }

    public void setVariable(String variable, ValueExpression expression) {
        this.getELContext().getVariableMapper().setVariable(variable, expression);
    }

    public void importStatic(String staticMemberName) throws ELException {
        this.getELContext().getImportHandler().importStatic(staticMemberName);
    }

    public void importClass(String className) throws ELException {
        this.getELContext().getImportHandler().importClass(className);
    }

    public void importPackage(String packageName) {
        this.getELContext().getImportHandler().importPackage(packageName);
    }

    public Object defineBean(String name, Object bean) {
        Object ret = this.getELContext().getBeans().get(name);
        this.getELContext().getBeans().put(name, bean);
        return ret;
    }

    public void addEvaluationListener(EvaluationListener listener) {
        this.getELContext().addEvaluationListener(listener);
    }
}

