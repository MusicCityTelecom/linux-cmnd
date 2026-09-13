/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.r2dbc.spi.ConnectionFactory
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.boot.diagnostics.AbstractFailureAnalyzer
 *  org.springframework.boot.diagnostics.FailureAnalysis
 *  org.springframework.core.Ordered
 */
package org.springframework.boot.autoconfigure.r2dbc;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.core.Ordered;

class NoConnectionFactoryBeanFailureAnalyzer
extends AbstractFailureAnalyzer<NoSuchBeanDefinitionException>
implements Ordered {
    private final ClassLoader classLoader;

    NoConnectionFactoryBeanFailureAnalyzer() {
        this(NoConnectionFactoryBeanFailureAnalyzer.class.getClassLoader());
    }

    NoConnectionFactoryBeanFailureAnalyzer(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    protected FailureAnalysis analyze(Throwable rootFailure, NoSuchBeanDefinitionException cause) {
        if (ConnectionFactory.class.equals((Object)cause.getBeanType()) && this.classLoader.getResource("META-INF/services/io.r2dbc.spi.ConnectionFactoryProvider") == null) {
            return new FailureAnalysis("No R2DBC ConnectionFactory bean is available and no /META-INF/services/io.r2dbc.spi.ConnectionFactoryProvider resource could be found.", "Check that the R2DBC driver for your database is on the classpath.", (Throwable)cause);
        }
        return null;
    }

    public int getOrder() {
        return 0;
    }
}

