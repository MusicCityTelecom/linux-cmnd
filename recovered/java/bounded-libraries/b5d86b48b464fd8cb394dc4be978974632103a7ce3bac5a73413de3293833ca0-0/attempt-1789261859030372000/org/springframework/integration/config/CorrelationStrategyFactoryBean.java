/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.FactoryBean
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config;

import java.lang.reflect.Method;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.aggregator.CorrelationStrategy;
import org.springframework.integration.aggregator.HeaderAttributeCorrelationStrategy;
import org.springframework.integration.aggregator.MethodInvokingCorrelationStrategy;
import org.springframework.integration.util.MessagingAnnotationUtils;
import org.springframework.util.StringUtils;

public class CorrelationStrategyFactoryBean
implements FactoryBean<CorrelationStrategy>,
InitializingBean {
    private Object target;
    private String methodName;
    private CorrelationStrategy strategy = new HeaderAttributeCorrelationStrategy("correlationId");

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void afterPropertiesSet() {
        if (this.target instanceof CorrelationStrategy && !StringUtils.hasText((String)this.methodName)) {
            this.strategy = (CorrelationStrategy)this.target;
            return;
        }
        if (this.target != null) {
            if (StringUtils.hasText((String)this.methodName)) {
                this.strategy = new MethodInvokingCorrelationStrategy(this.target, this.methodName);
            } else {
                Method method = MessagingAnnotationUtils.findAnnotatedMethod(this.target, org.springframework.integration.annotation.CorrelationStrategy.class);
                if (method != null) {
                    this.strategy = new MethodInvokingCorrelationStrategy(this.target, method);
                }
            }
        }
    }

    public CorrelationStrategy getObject() {
        return this.strategy;
    }

    public Class<?> getObjectType() {
        return CorrelationStrategy.class;
    }

    public boolean isSingleton() {
        return true;
    }
}

