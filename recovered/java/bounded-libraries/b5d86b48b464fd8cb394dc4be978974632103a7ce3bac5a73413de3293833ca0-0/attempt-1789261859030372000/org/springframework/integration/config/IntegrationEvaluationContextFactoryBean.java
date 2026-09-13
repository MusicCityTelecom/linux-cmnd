/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.FactoryBean
 *  org.springframework.context.expression.BeanFactoryResolver
 *  org.springframework.context.expression.MapAccessor
 *  org.springframework.expression.BeanResolver
 *  org.springframework.expression.PropertyAccessor
 *  org.springframework.expression.TypeLocator
 *  org.springframework.expression.spel.support.StandardEvaluationContext
 */
package org.springframework.integration.config;

import java.lang.reflect.Method;
import java.util.Map;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.TypeLocator;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.integration.config.AbstractEvaluationContextFactoryBean;

public class IntegrationEvaluationContextFactoryBean
extends AbstractEvaluationContextFactoryBean
implements FactoryBean<StandardEvaluationContext> {
    private volatile TypeLocator typeLocator;
    private BeanResolver beanResolver;

    public void setTypeLocator(TypeLocator typeLocator) {
        this.typeLocator = typeLocator;
    }

    public boolean isSingleton() {
        return false;
    }

    public void afterPropertiesSet() {
        if (this.getApplicationContext() != null) {
            this.beanResolver = new BeanFactoryResolver((BeanFactory)this.getApplicationContext());
        }
        this.initialize("integrationEvaluationContext");
    }

    public StandardEvaluationContext getObject() {
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        if (this.typeLocator != null) {
            evaluationContext.setTypeLocator(this.typeLocator);
        }
        evaluationContext.setBeanResolver(this.beanResolver);
        evaluationContext.setTypeConverter(this.getTypeConverter());
        for (PropertyAccessor propertyAccessor : this.getPropertyAccessors().values()) {
            evaluationContext.addPropertyAccessor(propertyAccessor);
        }
        evaluationContext.addPropertyAccessor((PropertyAccessor)new MapAccessor());
        for (Map.Entry entry : this.getFunctions().entrySet()) {
            evaluationContext.registerFunction((String)entry.getKey(), (Method)entry.getValue());
        }
        return evaluationContext;
    }

    public Class<?> getObjectType() {
        return StandardEvaluationContext.class;
    }
}

