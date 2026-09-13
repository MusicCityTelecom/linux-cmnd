/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.FactoryBean
 *  org.springframework.context.expression.MapAccessor
 *  org.springframework.expression.PropertyAccessor
 *  org.springframework.expression.spel.support.DataBindingPropertyAccessor
 *  org.springframework.expression.spel.support.SimpleEvaluationContext
 */
package org.springframework.integration.config;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.spel.support.DataBindingPropertyAccessor;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.integration.config.AbstractEvaluationContextFactoryBean;

public class IntegrationSimpleEvaluationContextFactoryBean
extends AbstractEvaluationContextFactoryBean
implements FactoryBean<SimpleEvaluationContext> {
    public boolean isSingleton() {
        return false;
    }

    public void afterPropertiesSet() {
        this.initialize("integrationSimpleEvaluationContext");
    }

    public SimpleEvaluationContext getObject() {
        Collection<PropertyAccessor> accessors = this.getPropertyAccessors().values();
        PropertyAccessor[] accessorArray = accessors.toArray(new PropertyAccessor[accessors.size() + 2]);
        accessorArray[accessors.size()] = new MapAccessor();
        accessorArray[accessors.size() + 1] = DataBindingPropertyAccessor.forReadOnlyAccess();
        SimpleEvaluationContext evaluationContext = SimpleEvaluationContext.forPropertyAccessors((PropertyAccessor[])accessorArray).withTypeConverter(this.getTypeConverter()).withInstanceMethods().build();
        for (Map.Entry<String, Method> functionEntry : this.getFunctions().entrySet()) {
            evaluationContext.setVariable(functionEntry.getKey(), (Object)functionEntry.getValue());
        }
        return evaluationContext;
    }

    public Class<?> getObjectType() {
        return SimpleEvaluationContext.class;
    }
}

