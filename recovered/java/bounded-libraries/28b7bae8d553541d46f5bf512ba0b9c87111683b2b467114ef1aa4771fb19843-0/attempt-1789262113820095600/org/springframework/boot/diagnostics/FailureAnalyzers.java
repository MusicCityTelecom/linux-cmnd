/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.EnvironmentAware
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.support.SpringFactoriesLoader
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.diagnostics;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.SpringBootExceptionReporter;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.diagnostics.FailureAnalysisReporter;
import org.springframework.boot.diagnostics.FailureAnalyzer;
import org.springframework.boot.util.Instantiator;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.log.LogMessage;
import org.springframework.util.StringUtils;

final class FailureAnalyzers
implements SpringBootExceptionReporter {
    private static final Log logger = LogFactory.getLog(FailureAnalyzers.class);
    private final ClassLoader classLoader;
    private final List<FailureAnalyzer> analyzers;

    FailureAnalyzers(ConfigurableApplicationContext context) {
        this(context, SpringFactoriesLoader.loadFactoryNames(FailureAnalyzer.class, (ClassLoader)FailureAnalyzers.getClassLoader(context)));
    }

    FailureAnalyzers(ConfigurableApplicationContext context, List<String> classNames) {
        this.classLoader = FailureAnalyzers.getClassLoader(context);
        this.analyzers = this.loadFailureAnalyzers(classNames, context);
    }

    private static ClassLoader getClassLoader(ConfigurableApplicationContext context) {
        return context != null ? context.getClassLoader() : null;
    }

    private List<FailureAnalyzer> loadFailureAnalyzers(List<String> classNames, ConfigurableApplicationContext context) {
        Instantiator instantiator = new Instantiator(FailureAnalyzer.class, availableParameters -> {
            if (context != null) {
                availableParameters.add(BeanFactory.class, context.getBeanFactory());
                availableParameters.add(Environment.class, context.getEnvironment());
            }
        }, new LoggingInstantiationFailureHandler());
        List<FailureAnalyzer> analyzers = instantiator.instantiate(this.classLoader, classNames);
        return this.handleAwareAnalyzers(analyzers, context);
    }

    private List<FailureAnalyzer> handleAwareAnalyzers(List<FailureAnalyzer> analyzers, ConfigurableApplicationContext context) {
        List<FailureAnalyzer> awareAnalyzers = analyzers.stream().filter(analyzer -> analyzer instanceof BeanFactoryAware || analyzer instanceof EnvironmentAware).collect(Collectors.toList());
        if (!awareAnalyzers.isEmpty()) {
            String awareAnalyzerNames = StringUtils.collectionToCommaDelimitedString((Collection)awareAnalyzers.stream().map(analyzer -> analyzer.getClass().getName()).collect(Collectors.toList()));
            logger.warn((Object)LogMessage.format((String)"FailureAnalyzers [%s] implement BeanFactoryAware or EnvironmentAware. Support for these interfaces on FailureAnalyzers is deprecated, and will be removed in a future release. Instead provide a constructor that accepts BeanFactory or Environment parameters.", (Object)awareAnalyzerNames));
            if (context == null) {
                logger.trace((Object)LogMessage.format((String)"Skipping [%s] due to missing context", (Object)awareAnalyzerNames));
                return analyzers.stream().filter(analyzer -> !awareAnalyzers.contains(analyzer)).collect(Collectors.toList());
            }
            awareAnalyzers.forEach(analyzer -> {
                if (analyzer instanceof BeanFactoryAware) {
                    ((BeanFactoryAware)analyzer).setBeanFactory((BeanFactory)context.getBeanFactory());
                }
                if (analyzer instanceof EnvironmentAware) {
                    ((EnvironmentAware)analyzer).setEnvironment((Environment)context.getEnvironment());
                }
            });
        }
        return analyzers;
    }

    @Override
    public boolean reportException(Throwable failure) {
        FailureAnalysis analysis = this.analyze(failure, this.analyzers);
        return this.report(analysis, this.classLoader);
    }

    private FailureAnalysis analyze(Throwable failure, List<FailureAnalyzer> analyzers) {
        for (FailureAnalyzer analyzer : analyzers) {
            try {
                FailureAnalysis analysis = analyzer.analyze(failure);
                if (analysis == null) continue;
                return analysis;
            }
            catch (Throwable ex) {
                logger.trace((Object)LogMessage.format((String)"FailureAnalyzer %s failed", (Object)analyzer), ex);
            }
        }
        return null;
    }

    private boolean report(FailureAnalysis analysis, ClassLoader classLoader) {
        List reporters = SpringFactoriesLoader.loadFactories(FailureAnalysisReporter.class, (ClassLoader)classLoader);
        if (analysis == null || reporters.isEmpty()) {
            return false;
        }
        for (FailureAnalysisReporter reporter : reporters) {
            reporter.report(analysis);
        }
        return true;
    }

    static class LoggingInstantiationFailureHandler
    implements Instantiator.FailureHandler {
        LoggingInstantiationFailureHandler() {
        }

        @Override
        public void handleFailure(Class<?> type, String implementationName, Throwable failure) {
            logger.trace((Object)LogMessage.format((String)"Skipping %s: %s", (Object)implementationName, (Object)failure.getMessage()));
        }
    }
}

