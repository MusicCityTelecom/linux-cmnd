/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.io.FileUtils
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.configuration.model.support.services.json.JsonServiceRegistryProperties
 *  org.apereo.cas.services.ChainingServiceRegistry
 *  org.apereo.cas.services.DefaultServiceRegistryInitializer
 *  org.apereo.cas.services.DefaultServiceRegistryInitializerEventListener
 *  org.apereo.cas.services.ServiceRegistry
 *  org.apereo.cas.services.ServiceRegistryExecutionPlanConfigurer
 *  org.apereo.cas.services.ServiceRegistryInitializer
 *  org.apereo.cas.services.ServiceRegistryInitializerEventListener
 *  org.apereo.cas.services.ServiceRegistryListener
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.services.resource.AbstractResourceBasedServiceRegistry
 *  org.apereo.cas.services.util.RegisteredServiceJsonSerializer
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.ResourceUtils
 *  org.apereo.cas.util.io.WatcherService
 *  org.apereo.cas.util.serialization.StringSerializer
 *  org.apereo.cas.util.spring.beans.BeanCondition
 *  org.apereo.cas.util.spring.beans.BeanSupplier
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.jooq.lambda.Unchecked
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnBean
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.EnableAspectJAutoProxy
 *  org.springframework.context.annotation.ScopedProxyMode
 *  org.springframework.core.env.PropertyResolver
 *  org.springframework.core.io.ClassPathResource
 *  org.springframework.core.io.FileSystemResource
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.io.support.ResourcePatternUtils
 *  org.springframework.scheduling.annotation.EnableAsync
 */
package org.apereo.cas.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.Generated;
import org.apache.commons.io.FileUtils;
import org.apereo.cas.config.CasCoreServicesConfiguration;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.model.support.services.json.JsonServiceRegistryProperties;
import org.apereo.cas.services.ChainingServiceRegistry;
import org.apereo.cas.services.DefaultServiceRegistryInitializer;
import org.apereo.cas.services.DefaultServiceRegistryInitializerEventListener;
import org.apereo.cas.services.ServiceRegistry;
import org.apereo.cas.services.ServiceRegistryExecutionPlanConfigurer;
import org.apereo.cas.services.ServiceRegistryInitializer;
import org.apereo.cas.services.ServiceRegistryInitializerEventListener;
import org.apereo.cas.services.ServiceRegistryListener;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.services.resource.AbstractResourceBasedServiceRegistry;
import org.apereo.cas.services.util.RegisteredServiceJsonSerializer;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.ResourceUtils;
import org.apereo.cas.util.io.WatcherService;
import org.apereo.cas.util.serialization.StringSerializer;
import org.apereo.cas.util.spring.beans.BeanCondition;
import org.apereo.cas.util.spring.beans.BeanSupplier;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@ConditionalOnMissingClass(value={"org.apereo.cas.services.JsonServiceRegistry", "org.apereo.cas.services.YamlServiceRegistry"})
@ConditionalOnBean(value={ServicesManager.class})
@EnableAspectJAutoProxy(proxyTargetClass=false)
@EnableAsync(proxyTargetClass=false)
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.ServiceRegistry)
@AutoConfiguration(after={CasCoreServicesConfiguration.class})
public class CasServiceRegistryInitializationConfiguration {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasServiceRegistryInitializationConfiguration.class);
    private static final BeanCondition CONDITION = BeanCondition.on((String)"cas.service-registry.core.init-from-json").isTrue();

    public static class EmbeddedResourceBasedServiceRegistry
    extends AbstractResourceBasedServiceRegistry {
        EmbeddedResourceBasedServiceRegistry(ConfigurableApplicationContext applicationContext, Resource location, Collection<ServiceRegistryListener> serviceRegistryListeners, WatcherService watcherService) throws Exception {
            super(location, (Collection)CollectionUtils.wrapList((Object[])new StringSerializer[]{new RegisteredServiceJsonSerializer(applicationContext)}), applicationContext, serviceRegistryListeners, watcherService);
        }

        protected String[] getExtensions() {
            return new String[]{"json"};
        }
    }

    @Configuration(value="CasServiceRegistryEmbeddedConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasServiceRegistryEmbeddedConfiguration {
        private static Resource getServiceRegistryInitializerServicesDirectoryResource(CasConfigurationProperties casProperties, ConfigurableApplicationContext applicationContext) throws Exception {
            JsonServiceRegistryProperties registry = casProperties.getServiceRegistry().getJson();
            if (ResourceUtils.doesResourceExist((Resource)registry.getLocation())) {
                LOGGER.debug("Using JSON service registry location [{}] for embedded service definitions", (Object)registry.getLocation());
                return registry.getLocation();
            }
            File parent = new File(FileUtils.getTempDirectory(), "cas");
            if (!parent.mkdirs()) {
                LOGGER.warn("Unable to create folder [{}]", (Object)parent);
            }
            Resource[] resources = ResourcePatternUtils.getResourcePatternResolver((ResourceLoader)applicationContext).getResources("classpath*:/services/*.json");
            Arrays.stream(resources).forEach(resource -> ResourceUtils.exportClasspathResourceToFile((File)parent, (Resource)resource));
            LOGGER.debug("Using service registry location [{}] for embedded service definitions", (Object)parent);
            return new FileSystemResource(parent);
        }

        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @Bean
        public ServiceRegistry embeddedJsonServiceRegistry(CasConfigurationProperties casProperties, ConfigurableApplicationContext applicationContext, ObjectProvider<List<ServiceRegistryListener>> serviceRegistryListeners) throws Exception {
            return (ServiceRegistry)BeanSupplier.of(ServiceRegistry.class).when(CONDITION.given((PropertyResolver)applicationContext.getEnvironment())).supply(Unchecked.supplier(() -> {
                Resource location = CasServiceRegistryEmbeddedConfiguration.getServiceRegistryInitializerServicesDirectoryResource(casProperties, applicationContext);
                EmbeddedResourceBasedServiceRegistry registry = new EmbeddedResourceBasedServiceRegistry(applicationContext, location, Optional.ofNullable((List)serviceRegistryListeners.getIfAvailable()).orElseGet(ArrayList::new), WatcherService.noOp());
                if (!(location instanceof ClassPathResource) && casProperties.getServiceRegistry().getJson().isWatcherEnabled()) {
                    registry.enableDefaultWatcherService();
                }
                return registry;
            })).otherwiseProxy().get();
        }

        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @ConditionalOnMissingBean(name={"embeddedJsonServiceRegistryExecutionPlanConfigurer"})
        public ServiceRegistryExecutionPlanConfigurer embeddedJsonServiceRegistryExecutionPlanConfigurer(ConfigurableApplicationContext applicationContext, @Qualifier(value="embeddedJsonServiceRegistry") ServiceRegistry embeddedJsonServiceRegistry) {
            return (ServiceRegistryExecutionPlanConfigurer)BeanSupplier.of(ServiceRegistryExecutionPlanConfigurer.class).when(CONDITION.given((PropertyResolver)applicationContext.getEnvironment())).supply(() -> plan -> plan.registerServiceRegistry(embeddedJsonServiceRegistry)).otherwiseProxy().get();
        }
    }

    @Configuration(value="CasServiceRegistryInitializationBaseConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasServiceRegistryInitializationBaseConfiguration {
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ServiceRegistryInitializer serviceRegistryInitializer(ConfigurableApplicationContext applicationContext, @Qualifier(value="embeddedJsonServiceRegistry") ServiceRegistry embeddedJsonServiceRegistry, @Qualifier(value="servicesManager") ServicesManager servicesManager, @Qualifier(value="serviceRegistry") ChainingServiceRegistry serviceRegistry) {
            return (ServiceRegistryInitializer)BeanSupplier.of(ServiceRegistryInitializer.class).when(CONDITION.given((PropertyResolver)applicationContext.getEnvironment())).supply(() -> new DefaultServiceRegistryInitializer(embeddedJsonServiceRegistry, serviceRegistry, servicesManager)).otherwiseProxy().get();
        }
    }

    @Configuration(value="CasServiceRegistryInitializationEventsConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasServiceRegistryInitializationEventsConfiguration {
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ServiceRegistryInitializerEventListener serviceRegistryInitializerConfigurationEventListener(ConfigurableApplicationContext applicationContext, @Qualifier(value="serviceRegistryInitializer") ObjectProvider<ServiceRegistryInitializer> serviceRegistryInitializer) {
            return (ServiceRegistryInitializerEventListener)BeanSupplier.of(ServiceRegistryInitializerEventListener.class).when(CONDITION.given((PropertyResolver)applicationContext.getEnvironment())).supply(() -> new DefaultServiceRegistryInitializerEventListener(serviceRegistryInitializer)).otherwiseProxy().get();
        }
    }
}

