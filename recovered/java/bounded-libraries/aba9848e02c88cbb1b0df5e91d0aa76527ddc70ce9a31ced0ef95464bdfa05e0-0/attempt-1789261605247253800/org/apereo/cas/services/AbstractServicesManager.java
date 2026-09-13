/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceAccessStrategyUtils
 *  org.apereo.cas.services.RegisteredServiceExpirationPolicy
 *  org.apereo.cas.services.ServiceRegistry
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.support.events.service.CasRegisteredServiceDeletedEvent
 *  org.apereo.cas.support.events.service.CasRegisteredServiceExpiredEvent
 *  org.apereo.cas.support.events.service.CasRegisteredServicePreDeleteEvent
 *  org.apereo.cas.support.events.service.CasRegisteredServicePreSaveEvent
 *  org.apereo.cas.support.events.service.CasRegisteredServiceSavedEvent
 *  org.apereo.cas.support.events.service.CasRegisteredServicesDeletedEvent
 *  org.apereo.cas.support.events.service.CasRegisteredServicesLoadedEvent
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationEvent
 */
package org.apereo.cas.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceAccessStrategyUtils;
import org.apereo.cas.services.RegisteredServiceExpirationPolicy;
import org.apereo.cas.services.ServiceRegistry;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.services.ServicesManagerConfigurationContext;
import org.apereo.cas.support.events.service.CasRegisteredServiceDeletedEvent;
import org.apereo.cas.support.events.service.CasRegisteredServiceExpiredEvent;
import org.apereo.cas.support.events.service.CasRegisteredServicePreDeleteEvent;
import org.apereo.cas.support.events.service.CasRegisteredServicePreSaveEvent;
import org.apereo.cas.support.events.service.CasRegisteredServiceSavedEvent;
import org.apereo.cas.support.events.service.CasRegisteredServicesDeletedEvent;
import org.apereo.cas.support.events.service.CasRegisteredServicesLoadedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;

public abstract class AbstractServicesManager
implements ServicesManager {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractServicesManager.class);
    protected final ServicesManagerConfigurationContext configurationContext;

    private static Predicate<RegisteredService> getRegisteredServicesFilteringPredicate(Predicate<RegisteredService> ... p) {
        ArrayList predicates = Stream.of(p).collect(Collectors.toCollection(ArrayList::new));
        return predicates.stream().reduce(x -> true, Predicate::and);
    }

    public RegisteredService save(RegisteredService registeredService) {
        return this.save(registeredService, true);
    }

    public synchronized RegisteredService save(RegisteredService registeredService, boolean publishEvent) {
        this.publishEvent((ApplicationEvent)new CasRegisteredServicePreSaveEvent((Object)this, registeredService));
        RegisteredService r = this.configurationContext.getServiceRegistry().save(registeredService);
        this.cacheRegisteredService(r);
        this.saveInternal(registeredService);
        if (publishEvent) {
            this.publishEvent((ApplicationEvent)new CasRegisteredServiceSavedEvent((Object)this, r));
        }
        return r;
    }

    public void save(Supplier<RegisteredService> supplier, Consumer<RegisteredService> andThenConsume, long countExclusive) {
        this.configurationContext.getServiceRegistry().save(() -> {
            RegisteredService registeredService = (RegisteredService)supplier.get();
            if (registeredService != null) {
                this.publishEvent((ApplicationEvent)new CasRegisteredServicePreSaveEvent((Object)this, registeredService));
                this.cacheRegisteredService(registeredService);
                this.saveInternal(registeredService);
                this.publishEvent((ApplicationEvent)new CasRegisteredServiceSavedEvent((Object)this, registeredService));
                return registeredService;
            }
            return null;
        }, andThenConsume, countExclusive);
    }

    public void save(Stream<RegisteredService> toSave) {
        Stream<RegisteredService> resultingStream = toSave.peek(registeredService -> this.publishEvent((ApplicationEvent)new CasRegisteredServicePreSaveEvent((Object)this, registeredService)));
        this.configurationContext.getServiceRegistry().save(resultingStream).forEach(r -> {
            this.cacheRegisteredService((RegisteredService)r);
            this.saveInternal((RegisteredService)r);
            this.publishEvent((ApplicationEvent)new CasRegisteredServiceSavedEvent((Object)this, r));
        });
    }

    public synchronized void deleteAll() {
        this.configurationContext.getServicesCache().asMap().forEach((k, v) -> this.delete((RegisteredService)v));
        this.configurationContext.getServicesCache().invalidateAll();
        this.publishEvent((ApplicationEvent)new CasRegisteredServicesDeletedEvent((Object)this));
    }

    public synchronized RegisteredService delete(long id) {
        RegisteredService service = this.findServiceBy(id);
        return this.delete(service);
    }

    public synchronized RegisteredService delete(RegisteredService service) {
        if (service != null) {
            this.publishEvent((ApplicationEvent)new CasRegisteredServicePreDeleteEvent((Object)this, service));
            this.configurationContext.getServiceRegistry().delete(service);
            this.configurationContext.getServicesCache().invalidate((Object)service.getId());
            this.deleteInternal(service);
            this.publishEvent((ApplicationEvent)new CasRegisteredServiceDeletedEvent((Object)this, service));
        }
        return service;
    }

    public RegisteredService findServiceBy(Service service) {
        if (service == null) {
            return null;
        }
        Collection<RegisteredService> candidates = this.getCandidateServicesToMatch(service.getId());
        Optional foundService = this.configurationContext.getRegisteredServiceLocators().stream().map(locator -> locator.locate(candidates, service)).filter(s -> this.validateRegisteredService((RegisteredService)s) != null).findFirst();
        if (foundService.isEmpty()) {
            ServiceRegistry serviceRegistry = this.configurationContext.getServiceRegistry();
            LOGGER.trace("Service [{}] is not cached; Searching [{}]", (Object)service.getId(), (Object)serviceRegistry.getName());
            foundService = Optional.ofNullable(serviceRegistry.findServiceBy(service.getId()));
            if (foundService.isPresent()) {
                RegisteredService registeredService = foundService.get();
                foundService = this.configurationContext.getRegisteredServiceLocators().stream().filter(locator -> locator.supports(registeredService, service)).findFirst().map(locator -> {
                    LOGGER.debug("Service [{}] is found in service registry and can be supported by [{}]", (Object)registeredService, (Object)locator.getName());
                    this.cacheRegisteredService(registeredService);
                    LOGGER.trace("Service [{}] is now cached from [{}]", (Object)service, (Object)serviceRegistry.getName());
                    return Optional.of(registeredService);
                }).orElseGet(Optional::empty);
            }
        }
        foundService.ifPresent(RegisteredService::initialize);
        return this.validateRegisteredService(foundService.orElse(null));
    }

    public Collection<RegisteredService> findServiceBy(Predicate<RegisteredService> predicate) {
        if (predicate == null) {
            return new ArrayList<RegisteredService>(0);
        }
        Map results = this.configurationContext.getServiceRegistry().findServicePredicate(predicate).stream().sorted().peek(RegisteredService::initialize).collect(Collectors.toMap(RegisteredService::getId, Function.identity(), (r, s) -> s));
        this.configurationContext.getServicesCache().putAll(results);
        return results.values();
    }

    public <T extends RegisteredService> T findServiceBy(Service requestedService, Class<T> clazz) {
        if (requestedService == null) {
            return null;
        }
        RegisteredService service = this.findServiceBy(requestedService);
        if (service != null && clazz.isAssignableFrom(service.getClass())) {
            return (T)service;
        }
        return null;
    }

    public RegisteredService findServiceBy(long id) {
        RegisteredService result = (RegisteredService)this.configurationContext.getServicesCache().get((Object)id, k -> this.configurationContext.getServiceRegistry().findServiceById(id));
        return this.validateRegisteredService(result);
    }

    public <T extends RegisteredService> T findServiceBy(long id, Class<T> clazz) {
        RegisteredService service = this.getService(registeredService -> registeredService.getId() == id);
        if (service != null && clazz.isAssignableFrom(service.getClass())) {
            return (T)service;
        }
        LOGGER.trace("The service with id [{}] and type [{}] is not found in the cache; trying to find it from [{}]", new Object[]{id, clazz, this.configurationContext.getServiceRegistry().getName()});
        service = (RegisteredService)this.configurationContext.getServicesCache().get((Object)id, k -> this.configurationContext.getServiceRegistry().findServiceById(id, clazz));
        return (T)this.validateRegisteredService(service);
    }

    public RegisteredService findServiceByName(String name) {
        if (StringUtils.isBlank((CharSequence)name)) {
            return null;
        }
        RegisteredService service = this.getService(registeredService -> registeredService.getName().equals(name));
        if (service == null) {
            ServiceRegistry registry = this.configurationContext.getServiceRegistry();
            LOGGER.trace("The service with name [{}] is not found in the cache; trying to find it from [{}]", (Object)name, (Object)registry.getName());
            service = registry.findServiceByExactServiceName(name);
            if (service != null) {
                this.cacheRegisteredService(service);
                LOGGER.trace("The service is found in [{}] and populated to the cache [{}]", (Object)registry.getName(), (Object)service);
            }
        }
        if (service != null) {
            service.initialize();
        }
        return this.validateRegisteredService(service);
    }

    public <T extends RegisteredService> T findServiceByName(String name, Class<T> clazz) {
        if (StringUtils.isBlank((CharSequence)name)) {
            return null;
        }
        RegisteredService service = this.getService(registeredService -> registeredService.getName().equals(name));
        if (service != null && clazz.isAssignableFrom(service.getClass())) {
            return (T)service;
        }
        LOGGER.trace("The service with name [{}] and type [{}] is not found in the cache; trying to find it from [{}]", new Object[]{name, clazz, this.configurationContext.getServiceRegistry().getName()});
        service = this.configurationContext.getServiceRegistry().findServiceByExactServiceName(name, clazz);
        if (service != null) {
            this.cacheRegisteredService(service);
            LOGGER.trace("The service is found in [{}] and populated to the cache [{}]", (Object)this.configurationContext.getServiceRegistry().getName(), (Object)service);
        }
        return (T)this.validateRegisteredService(service);
    }

    public Collection<RegisteredService> getAllServices() {
        return this.getCacheableServicesStream().get().filter(this::validateAndFilterServiceByEnvironment).filter(AbstractServicesManager.getRegisteredServicesFilteringPredicate(new Predicate[0])).sorted().peek(RegisteredService::initialize).peek(this::cacheRegisteredService).collect(Collectors.toList());
    }

    public Collection<RegisteredService> getAllServicesOfType(Class clazz) {
        if (this.supports(clazz)) {
            return this.getCacheableServicesStream().get().filter(s -> clazz.isAssignableFrom(s.getClass())).filter(this::validateAndFilterServiceByEnvironment).filter(AbstractServicesManager.getRegisteredServicesFilteringPredicate(new Predicate[0])).sorted().peek(RegisteredService::initialize).peek(this::cacheRegisteredService).collect(Collectors.toList());
        }
        return new ArrayList<RegisteredService>();
    }

    public Stream<? extends RegisteredService> stream() {
        return this.configurationContext.getServiceRegistry().getServicesStream();
    }

    public synchronized Collection<RegisteredService> load() {
        LOGGER.trace("Loading services from [{}]", (Object)this.configurationContext.getServiceRegistry().getName());
        Map servicesMap = this.configurationContext.getServiceRegistry().load().stream().filter(arg_0 -> ((AbstractServicesManager)this).supports(arg_0)).filter(this::validateAndFilterServiceByEnvironment).peek(this::loadInternal).collect(Collectors.toMap(r -> {
            LOGGER.trace("Adding registered service [{}] with name [{}] and internal identifier [{}]", new Object[]{r.getServiceId(), r.getName(), r.getId()});
            return r.getId();
        }, Function.identity(), (r, s) -> s));
        this.configurationContext.getServicesCache().invalidateAll();
        this.configurationContext.getServicesCache().putAll(servicesMap);
        this.loadInternal();
        this.publishEvent((ApplicationEvent)new CasRegisteredServicesLoadedEvent((Object)this, this.getAllServices()));
        this.evaluateExpiredServiceDefinitions();
        LOGGER.info("Loaded [{}] service(s) from [{}].", (Object)this.configurationContext.getServicesCache().asMap().size(), (Object)this.configurationContext.getServiceRegistry().getName());
        return this.configurationContext.getServicesCache().asMap().values();
    }

    public long count() {
        return this.configurationContext.getServiceRegistry().size();
    }

    protected abstract Collection<RegisteredService> getCandidateServicesToMatch(String var1);

    protected void deleteInternal(RegisteredService service) {
    }

    protected void saveInternal(RegisteredService service) {
    }

    protected void loadInternal() {
    }

    protected void loadInternal(RegisteredService service) {
    }

    private void cacheRegisteredService(RegisteredService service) {
        if (this.configurationContext.getServicesCache().getIfPresent((Object)service.getId()) == null) {
            this.configurationContext.getServicesCache().put((Object)service.getId(), (Object)service);
        }
    }

    private void evaluateExpiredServiceDefinitions() {
        this.getCacheableServicesStream().get().filter(RegisteredServiceAccessStrategyUtils.getRegisteredServiceExpirationPolicyPredicate().negate()).forEach(this::processExpiredRegisteredService);
    }

    private RegisteredService validateRegisteredService(RegisteredService registeredService) {
        RegisteredService result = this.checkServiceExpirationPolicyIfAny(registeredService);
        if (this.validateAndFilterServiceByEnvironment(result)) {
            return result;
        }
        return null;
    }

    private RegisteredService checkServiceExpirationPolicyIfAny(RegisteredService registeredService) {
        if (registeredService == null || RegisteredServiceAccessStrategyUtils.ensureServiceIsNotExpired((RegisteredService)registeredService)) {
            return registeredService;
        }
        return this.processExpiredRegisteredService(registeredService);
    }

    private RegisteredService processExpiredRegisteredService(RegisteredService registeredService) {
        RegisteredServiceExpirationPolicy policy = registeredService.getExpirationPolicy();
        LOGGER.warn("Registered service [{}] has expired on [{}]", (Object)registeredService.getServiceId(), (Object)policy.getExpirationDate());
        if (policy.isNotifyWhenExpired()) {
            LOGGER.debug("Contacts for registered service [{}] will be notified of service expiry", (Object)registeredService.getServiceId());
            this.publishEvent((ApplicationEvent)new CasRegisteredServiceExpiredEvent((Object)this, registeredService, false));
        }
        if (policy.isDeleteWhenExpired()) {
            LOGGER.debug("Deleting expired registered service [{}] from registry.", (Object)registeredService.getServiceId());
            if (policy.isNotifyWhenDeleted()) {
                LOGGER.debug("Contacts for registered service [{}] will be notified of service expiry and removal", (Object)registeredService.getServiceId());
                this.publishEvent((ApplicationEvent)new CasRegisteredServiceExpiredEvent((Object)this, registeredService, true));
            }
            this.delete(registeredService);
            return null;
        }
        return registeredService;
    }

    private void publishEvent(ApplicationEvent event) {
        if (this.configurationContext.getApplicationContext() != null) {
            this.configurationContext.getApplicationContext().publishEvent(event);
        }
    }

    private boolean validateAndFilterServiceByEnvironment(RegisteredService service) {
        if (this.configurationContext.getEnvironments().isEmpty()) {
            LOGGER.trace("No environments are defined by which services could be filtered");
            return true;
        }
        if (service == null) {
            LOGGER.trace("No service definition was provided");
            return true;
        }
        if (service.getEnvironments() == null || service.getEnvironments().isEmpty()) {
            LOGGER.trace("No environments are assigned to service [{}]", (Object)service.getName());
            return true;
        }
        return service.getEnvironments().stream().anyMatch(this.configurationContext.getEnvironments()::contains);
    }

    private RegisteredService getService(Predicate<RegisteredService> filter) {
        return this.getCacheableServicesStream().get().filter(filter).findFirst().orElse(null);
    }

    protected Supplier<Stream<RegisteredService>> getCacheableServicesStream() {
        this.configurationContext.getServicesCache().cleanUp();
        long size = this.configurationContext.getServicesCache().estimatedSize();
        if (size <= 0L) {
            return () -> this.configurationContext.getServiceRegistry().getServicesStream();
        }
        return () -> this.configurationContext.getServicesCache().asMap().values().stream();
    }

    @Generated
    protected AbstractServicesManager(ServicesManagerConfigurationContext configurationContext) {
        this.configurationContext = configurationContext;
    }

    @Generated
    public ServicesManagerConfigurationContext getConfigurationContext() {
        return this.configurationContext;
    }
}

