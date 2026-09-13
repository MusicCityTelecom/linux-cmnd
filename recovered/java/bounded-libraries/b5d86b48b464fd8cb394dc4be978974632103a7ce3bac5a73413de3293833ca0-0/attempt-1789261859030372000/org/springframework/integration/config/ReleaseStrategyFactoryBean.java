/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.FactoryBean
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config;

import java.lang.reflect.Method;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.aggregator.MethodInvokingReleaseStrategy;
import org.springframework.integration.aggregator.SimpleSequenceSizeReleaseStrategy;
import org.springframework.integration.annotation.ReleaseStrategy;
import org.springframework.integration.util.MessagingAnnotationUtils;
import org.springframework.util.StringUtils;

public class ReleaseStrategyFactoryBean
implements FactoryBean<org.springframework.integration.aggregator.ReleaseStrategy>,
InitializingBean {
    private static final Log LOGGER = LogFactory.getLog(ReleaseStrategyFactoryBean.class);
    private Object target;
    private String methodName;
    private org.springframework.integration.aggregator.ReleaseStrategy strategy = new SimpleSequenceSizeReleaseStrategy();

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void afterPropertiesSet() {
        if (this.target instanceof org.springframework.integration.aggregator.ReleaseStrategy && !StringUtils.hasText((String)this.methodName)) {
            this.strategy = (org.springframework.integration.aggregator.ReleaseStrategy)this.target;
            return;
        }
        if (this.target != null) {
            if (StringUtils.hasText((String)this.methodName)) {
                this.strategy = new MethodInvokingReleaseStrategy(this.target, this.methodName);
            } else {
                Method method = MessagingAnnotationUtils.findAnnotatedMethod(this.target, ReleaseStrategy.class);
                if (method != null) {
                    this.strategy = new MethodInvokingReleaseStrategy(this.target, method);
                } else if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn((Object)("No ReleaseStrategy annotated method found on " + this.target.getClass().getSimpleName() + "; falling back to SimpleSequenceSizeReleaseStrategy, target: " + this.target + ", methodName: " + this.methodName));
                }
            }
        } else {
            LOGGER.warn((Object)"No target supplied; falling back to SimpleSequenceSizeReleaseStrategy");
        }
    }

    public org.springframework.integration.aggregator.ReleaseStrategy getObject() {
        return this.strategy;
    }

    public Class<?> getObjectType() {
        return org.springframework.integration.aggregator.ReleaseStrategy.class;
    }

    public boolean isSingleton() {
        return true;
    }
}

