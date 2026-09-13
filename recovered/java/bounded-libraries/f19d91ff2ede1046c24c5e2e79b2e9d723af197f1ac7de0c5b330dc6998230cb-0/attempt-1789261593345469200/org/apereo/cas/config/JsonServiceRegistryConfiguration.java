/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.configuration.model.core.services.ServiceRegistryProperties
 *  org.apereo.cas.configuration.model.support.services.json.JsonServiceRegistryProperties
 *  org.apereo.cas.services.ServiceRegistry
 *  org.apereo.cas.services.ServiceRegistryExecutionPlanConfigurer
 *  org.apereo.cas.services.ServiceRegistryListener
 *  org.apereo.cas.services.replication.RegisteredServiceReplicationStrategy
 *  org.apereo.cas.services.resource.RegisteredServiceResourceNamingStrategy
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.util.io.WatcherService
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.AutoConfigureOrder
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.ScopedProxyMode
 */
package org.apereo.cas.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.model.core.services.ServiceRegistryProperties;
import org.apereo.cas.configuration.model.support.services.json.JsonServiceRegistryProperties;
import org.apereo.cas.services.JsonServiceRegistry;
import org.apereo.cas.services.ServiceRegistry;
import org.apereo.cas.services.ServiceRegistryExecutionPlanConfigurer;
import org.apereo.cas.services.ServiceRegistryListener;
import org.apereo.cas.services.replication.RegisteredServiceReplicationStrategy;
import org.apereo.cas.services.resource.RegisteredServiceResourceNamingStrategy;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.io.WatcherService;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.ServiceRegistry, module="json")
@AutoConfigureOrder(value=-2147483647)
@AutoConfiguration
public class JsonServiceRegistryConfiguration {
    @Bean
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    @ConditionalOnMissingBean(name={"jsonServiceRegistry"})
    public ServiceRegistry jsonServiceRegistry(@Qualifier(value="registeredServiceResourceNamingStrategy") RegisteredServiceResourceNamingStrategy resourceNamingStrategy, ConfigurableApplicationContext applicationContext, CasConfigurationProperties casProperties, @Qualifier(value="registeredServiceReplicationStrategy") RegisteredServiceReplicationStrategy registeredServiceReplicationStrategy, ObjectProvider<List<ServiceRegistryListener>> serviceRegistryListeners) throws Exception {
        ServiceRegistryProperties registry = casProperties.getServiceRegistry();
        JsonServiceRegistry json = new JsonServiceRegistry(registry.getJson().getLocation(), WatcherService.noOp(), applicationContext, registeredServiceReplicationStrategy, resourceNamingStrategy, Optional.ofNullable((List)serviceRegistryListeners.getIfAvailable()).orElseGet(ArrayList::new));
        if (registry.getJson().isWatcherEnabled()) {
            json.enableDefaultWatcherService();
        }
        return json;
    }

    @Bean
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    @ConditionalOnMissingBean(name={"jsonServiceRegistryExecutionPlanConfigurer"})
    public ServiceRegistryExecutionPlanConfigurer jsonServiceRegistryExecutionPlanConfigurer(CasConfigurationProperties casProperties, @Qualifier(value="jsonServiceRegistry") ServiceRegistry jsonServiceRegistry) {
        JsonServiceRegistryProperties registry = casProperties.getServiceRegistry().getJson();
        return plan -> FunctionUtils.doIfNotNull((Object)registry.getLocation(), input -> plan.registerServiceRegistry(jsonServiceRegistry));
    }
}

