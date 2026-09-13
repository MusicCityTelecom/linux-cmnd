/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.benmanes.caffeine.cache.Cache
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ServiceRegistry
 *  org.apereo.cas.services.ServicesManagerRegisteredServiceLocator
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.apereo.cas.services;

import com.github.benmanes.caffeine.cache.Cache;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Generated;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServiceRegistry;
import org.apereo.cas.services.ServicesManagerRegisteredServiceLocator;
import org.springframework.context.ConfigurableApplicationContext;

public class ServicesManagerConfigurationContext {
    private final ServiceRegistry serviceRegistry;
    private final ConfigurableApplicationContext applicationContext;
    private final Set<String> environments;
    private final Cache<Long, RegisteredService> servicesCache;
    private final List<ServicesManagerRegisteredServiceLocator> registeredServiceLocators;

    @Generated
    private static Set<String> $default$environments() {
        return new HashSet<String>();
    }

    @Generated
    private static List<ServicesManagerRegisteredServiceLocator> $default$registeredServiceLocators() {
        return new ArrayList<ServicesManagerRegisteredServiceLocator>();
    }

    @Generated
    protected ServicesManagerConfigurationContext(ServicesManagerConfigurationContextBuilder<?, ?> b) {
        this.serviceRegistry = b.serviceRegistry;
        this.applicationContext = b.applicationContext;
        this.environments = b.environments$set ? b.environments$value : ServicesManagerConfigurationContext.$default$environments();
        this.servicesCache = b.servicesCache;
        this.registeredServiceLocators = b.registeredServiceLocators$set ? b.registeredServiceLocators$value : ServicesManagerConfigurationContext.$default$registeredServiceLocators();
    }

    @Generated
    public static ServicesManagerConfigurationContextBuilder<?, ?> builder() {
        return new ServicesManagerConfigurationContextBuilderImpl();
    }

    @Generated
    public ServiceRegistry getServiceRegistry() {
        return this.serviceRegistry;
    }

    @Generated
    public ConfigurableApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    @Generated
    public Set<String> getEnvironments() {
        return this.environments;
    }

    @Generated
    public Cache<Long, RegisteredService> getServicesCache() {
        return this.servicesCache;
    }

    @Generated
    public List<ServicesManagerRegisteredServiceLocator> getRegisteredServiceLocators() {
        return this.registeredServiceLocators;
    }

    @Generated
    private static final class ServicesManagerConfigurationContextBuilderImpl
    extends ServicesManagerConfigurationContextBuilder<ServicesManagerConfigurationContext, ServicesManagerConfigurationContextBuilderImpl> {
        @Generated
        private ServicesManagerConfigurationContextBuilderImpl() {
        }

        @Override
        @Generated
        protected ServicesManagerConfigurationContextBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public ServicesManagerConfigurationContext build() {
            return new ServicesManagerConfigurationContext(this);
        }
    }

    @Generated
    public static abstract class ServicesManagerConfigurationContextBuilder<C extends ServicesManagerConfigurationContext, B extends ServicesManagerConfigurationContextBuilder<C, B>> {
        @Generated
        private ServiceRegistry serviceRegistry;
        @Generated
        private ConfigurableApplicationContext applicationContext;
        @Generated
        private boolean environments$set;
        @Generated
        private Set<String> environments$value;
        @Generated
        private Cache<Long, RegisteredService> servicesCache;
        @Generated
        private boolean registeredServiceLocators$set;
        @Generated
        private List<ServicesManagerRegisteredServiceLocator> registeredServiceLocators$value;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B serviceRegistry(ServiceRegistry serviceRegistry) {
            this.serviceRegistry = serviceRegistry;
            return this.self();
        }

        @Generated
        public B applicationContext(ConfigurableApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
            return this.self();
        }

        @Generated
        public B environments(Set<String> environments) {
            this.environments$value = environments;
            this.environments$set = true;
            return this.self();
        }

        @Generated
        public B servicesCache(Cache<Long, RegisteredService> servicesCache) {
            this.servicesCache = servicesCache;
            return this.self();
        }

        @Generated
        public B registeredServiceLocators(List<ServicesManagerRegisteredServiceLocator> registeredServiceLocators) {
            this.registeredServiceLocators$value = registeredServiceLocators;
            this.registeredServiceLocators$set = true;
            return this.self();
        }

        @Generated
        public String toString() {
            return "ServicesManagerConfigurationContext.ServicesManagerConfigurationContextBuilder(serviceRegistry=" + this.serviceRegistry + ", applicationContext=" + this.applicationContext + ", environments$value=" + this.environments$value + ", servicesCache=" + this.servicesCache + ", registeredServiceLocators$value=" + this.registeredServiceLocators$value + ")";
        }
    }
}

