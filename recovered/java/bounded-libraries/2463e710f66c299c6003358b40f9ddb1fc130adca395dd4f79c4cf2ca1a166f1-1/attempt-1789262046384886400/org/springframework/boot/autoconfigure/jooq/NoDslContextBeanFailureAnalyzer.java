/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jooq.DSLContext
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.boot.diagnostics.AbstractFailureAnalyzer
 *  org.springframework.boot.diagnostics.FailureAnalysis
 *  org.springframework.core.Ordered
 */
package org.springframework.boot.autoconfigure.jooq;

import org.jooq.DSLContext;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.core.Ordered;

class NoDslContextBeanFailureAnalyzer
extends AbstractFailureAnalyzer<NoSuchBeanDefinitionException>
implements Ordered {
    private final BeanFactory beanFactory;

    NoDslContextBeanFailureAnalyzer(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    protected FailureAnalysis analyze(Throwable rootFailure, NoSuchBeanDefinitionException cause) {
        if (DSLContext.class.equals((Object)cause.getBeanType()) && this.hasR2dbcAutoConfiguration()) {
            return new FailureAnalysis("jOOQ has not been auto-configured as R2DBC has been auto-configured in favor of JDBC and jOOQ auto-configuration does not yet support R2DBC. ", "To use jOOQ with JDBC, exclude R2dbcAutoConfiguration. To use jOOQ with R2DBC, define your own jOOQ configuration.", (Throwable)cause);
        }
        return null;
    }

    private boolean hasR2dbcAutoConfiguration() {
        try {
            this.beanFactory.getBean(R2dbcAutoConfiguration.class);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public int getOrder() {
        return 0;
    }
}

