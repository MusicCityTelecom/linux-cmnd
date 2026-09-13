/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanNameAware
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.beans.factory.SmartInitializingSingleton
 *  org.springframework.beans.factory.config.BeanPostProcessor
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.context.ApplicationListener
 *  org.springframework.context.event.ContextClosedEvent
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.util.Assert
 */
package org.springframework.integration.config;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.support.management.IntegrationManagement;
import org.springframework.integration.support.management.metrics.MeterFacade;
import org.springframework.integration.support.management.metrics.MetricsCaptor;
import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;

public class IntegrationManagementConfigurer
implements SmartInitializingSingleton,
ApplicationContextAware,
BeanNameAware,
BeanPostProcessor,
ApplicationListener<ContextClosedEvent> {
    public static final String MANAGEMENT_CONFIGURER_NAME = "integrationManagementConfigurer";
    private final Set<MeterFacade> gauges = new HashSet<MeterFacade>();
    private ApplicationContext applicationContext;
    private String beanName;
    private boolean defaultLoggingEnabled = true;
    private volatile boolean singletonsInstantiated;
    private MetricsCaptor metricsCaptor;
    private ObjectProvider<MetricsCaptor> metricsCaptorProvider;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setBeanName(String name) {
        this.beanName = name;
    }

    public void setDefaultLoggingEnabled(boolean defaultLoggingEnabled) {
        this.defaultLoggingEnabled = defaultLoggingEnabled;
    }

    public void setMetricsCaptor(@Nullable MetricsCaptor metricsCaptor) {
        this.metricsCaptor = metricsCaptor;
    }

    void setMetricsCaptorProvider(ObjectProvider<MetricsCaptor> metricsCaptorProvider) {
        this.metricsCaptorProvider = metricsCaptorProvider;
    }

    @Nullable
    MetricsCaptor obtainMetricsCaptor() {
        if (this.metricsCaptor == null && this.metricsCaptorProvider != null) {
            this.metricsCaptor = (MetricsCaptor)this.metricsCaptorProvider.getIfUnique();
        }
        return this.metricsCaptor;
    }

    public void afterSingletonsInstantiated() {
        Assert.state((this.applicationContext != null ? 1 : 0) != 0, (String)"'applicationContext' must not be null");
        Assert.state((boolean)MANAGEMENT_CONFIGURER_NAME.equals(this.beanName), (String)(this.getClass().getSimpleName() + " bean name must be " + MANAGEMENT_CONFIGURER_NAME));
        if (this.obtainMetricsCaptor() != null) {
            this.registerComponentGauges();
        }
        for (IntegrationManagement integrationManagement : this.applicationContext.getBeansOfType(IntegrationManagement.class).values()) {
            this.enhanceIntegrationManagement(integrationManagement);
        }
        this.singletonsInstantiated = true;
    }

    private void registerComponentGauges() {
        this.gauges.add(this.metricsCaptor.gaugeBuilder("spring.integration.channels", this, c -> this.applicationContext.getBeansOfType(MessageChannel.class).size()).description("The number of message channels").build());
        this.gauges.add(this.metricsCaptor.gaugeBuilder("spring.integration.handlers", this, c -> this.applicationContext.getBeansOfType(MessageHandler.class).size()).description("The number of message handlers").build());
        this.gauges.add(this.metricsCaptor.gaugeBuilder("spring.integration.sources", this, c -> this.applicationContext.getBeansOfType(MessageSource.class).size()).description("The number of message sources").build());
    }

    private void enhanceIntegrationManagement(IntegrationManagement integrationManagement) {
        if (!IntegrationManagementConfigurer.getOverrides((IntegrationManagement)integrationManagement).loggingConfigured) {
            integrationManagement.setLoggingEnabled(this.defaultLoggingEnabled);
        }
        if (this.metricsCaptor != null) {
            integrationManagement.registerMetricsCaptor(this.metricsCaptor);
        }
    }

    public Object postProcessAfterInitialization(Object bean, String name) throws BeansException {
        if (this.singletonsInstantiated && bean instanceof IntegrationManagement) {
            this.enhanceIntegrationManagement((IntegrationManagement)bean);
        }
        return bean;
    }

    public void onApplicationEvent(ContextClosedEvent event) {
        if (event.getApplicationContext().equals(this.applicationContext)) {
            this.gauges.forEach(MeterFacade::remove);
            this.gauges.clear();
        }
    }

    private static IntegrationManagement.ManagementOverrides getOverrides(IntegrationManagement bean) {
        return bean.getOverrides() != null ? bean.getOverrides() : new IntegrationManagement.ManagementOverrides();
    }
}

