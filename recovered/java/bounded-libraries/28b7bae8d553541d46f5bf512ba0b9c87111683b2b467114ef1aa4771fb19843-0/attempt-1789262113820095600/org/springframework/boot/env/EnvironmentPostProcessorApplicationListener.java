/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.event.SmartApplicationListener
 *  org.springframework.core.Ordered
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.io.ResourceLoader
 */
package org.springframework.boot.env;

import java.util.List;
import java.util.function.Function;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.EnvironmentPostProcessorsFactory;
import org.springframework.boot.logging.DeferredLogs;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ResourceLoader;

public class EnvironmentPostProcessorApplicationListener
implements SmartApplicationListener,
Ordered {
    public static final int DEFAULT_ORDER = -2147483638;
    private final DeferredLogs deferredLogs;
    private int order = -2147483638;
    private final Function<ClassLoader, EnvironmentPostProcessorsFactory> postProcessorsFactory;

    public EnvironmentPostProcessorApplicationListener() {
        this(EnvironmentPostProcessorsFactory::fromSpringFactories, new DeferredLogs());
    }

    public EnvironmentPostProcessorApplicationListener(EnvironmentPostProcessorsFactory postProcessorsFactory) {
        this(classloader -> postProcessorsFactory, new DeferredLogs());
    }

    EnvironmentPostProcessorApplicationListener(Function<ClassLoader, EnvironmentPostProcessorsFactory> postProcessorsFactory, DeferredLogs deferredLogs) {
        this.postProcessorsFactory = postProcessorsFactory;
        this.deferredLogs = deferredLogs;
    }

    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return ApplicationEnvironmentPreparedEvent.class.isAssignableFrom(eventType) || ApplicationPreparedEvent.class.isAssignableFrom(eventType) || ApplicationFailedEvent.class.isAssignableFrom(eventType);
    }

    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            this.onApplicationEnvironmentPreparedEvent((ApplicationEnvironmentPreparedEvent)event);
        }
        if (event instanceof ApplicationPreparedEvent) {
            this.onApplicationPreparedEvent();
        }
        if (event instanceof ApplicationFailedEvent) {
            this.onApplicationFailedEvent();
        }
    }

    private void onApplicationEnvironmentPreparedEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        SpringApplication application = event.getSpringApplication();
        for (EnvironmentPostProcessor postProcessor : this.getEnvironmentPostProcessors(application.getResourceLoader(), event.getBootstrapContext())) {
            postProcessor.postProcessEnvironment(environment, application);
        }
    }

    private void onApplicationPreparedEvent() {
        this.finish();
    }

    private void onApplicationFailedEvent() {
        this.finish();
    }

    private void finish() {
        this.deferredLogs.switchOverAll();
    }

    List<EnvironmentPostProcessor> getEnvironmentPostProcessors(ResourceLoader resourceLoader, ConfigurableBootstrapContext bootstrapContext) {
        ClassLoader classLoader = resourceLoader != null ? resourceLoader.getClassLoader() : null;
        EnvironmentPostProcessorsFactory postProcessorsFactory = this.postProcessorsFactory.apply(classLoader);
        return postProcessorsFactory.getEnvironmentPostProcessors(this.deferredLogs, bootstrapContext);
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}

