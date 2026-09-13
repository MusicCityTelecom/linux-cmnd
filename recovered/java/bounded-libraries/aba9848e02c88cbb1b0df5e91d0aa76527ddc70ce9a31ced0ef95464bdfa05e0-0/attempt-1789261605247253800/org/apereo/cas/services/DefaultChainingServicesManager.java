/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.ChainingServicesManager
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.inspektr.audit.annotation.Audit
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 */
package org.apereo.cas.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.ChainingServicesManager;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServicesManager;
import org.apereo.inspektr.audit.annotation.Audit;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

public class DefaultChainingServicesManager
implements ChainingServicesManager {
    private final List<ServicesManager> serviceManagers = new ArrayList<ServicesManager>();

    public void registerServiceManager(ServicesManager manager) {
        this.serviceManagers.add(manager);
        AnnotationAwareOrderComparator.sortIfNecessary(this.serviceManagers);
    }

    @Audit(action="SAVE_SERVICE", actionResolverName="SAVE_SERVICE_ACTION_RESOLVER", resourceResolverName="SAVE_SERVICE_RESOURCE_RESOLVER")
    public RegisteredService save(RegisteredService registeredService) {
        Optional<ServicesManager> manager = this.findServicesManager(registeredService);
        return manager.map(servicesManager -> servicesManager.save(registeredService)).orElse(null);
    }

    @Audit(action="SAVE_SERVICE", actionResolverName="SAVE_SERVICE_ACTION_RESOLVER", resourceResolverName="SAVE_SERVICE_RESOURCE_RESOLVER")
    public RegisteredService save(RegisteredService registeredService, boolean publishEvent) {
        Optional<ServicesManager> manager = this.findServicesManager(registeredService);
        return manager.map(servicesManager -> servicesManager.save(registeredService, publishEvent)).orElse(null);
    }

    public void save(Supplier<RegisteredService> supplier, Consumer<RegisteredService> andThenConsume, long countExclusive) {
        this.serviceManagers.forEach(servicesManager -> servicesManager.save(() -> this.lambda$save$2((Supplier)supplier), andThenConsume, countExclusive));
    }

    public void save(Stream<RegisteredService> toSave) {
        this.serviceManagers.forEach(mgr -> {
            Stream<RegisteredService> filtered = toSave.filter(arg_0 -> ((ServicesManager)mgr).supports(arg_0));
            mgr.save(filtered);
        });
    }

    public void deleteAll() {
        this.serviceManagers.forEach(ServicesManager::deleteAll);
    }

    @Audit(action="DELETE_SERVICE", actionResolverName="DELETE_SERVICE_ACTION_RESOLVER", resourceResolverName="DELETE_SERVICE_RESOURCE_RESOLVER")
    public RegisteredService delete(long id) {
        return this.serviceManagers.stream().map(s -> s.delete(id)).filter(Objects::nonNull).findFirst().orElse(null);
    }

    @Audit(action="DELETE_SERVICE", actionResolverName="DELETE_SERVICE_ACTION_RESOLVER", resourceResolverName="DELETE_SERVICE_RESOURCE_RESOLVER")
    public RegisteredService delete(RegisteredService svc) {
        Optional<ServicesManager> manager = this.findServicesManager(svc);
        return manager.map(servicesManager -> servicesManager.delete(svc)).orElse(null);
    }

    public RegisteredService findServiceBy(Service service) {
        Optional<ServicesManager> manager = this.findServicesManager(service);
        return manager.map(servicesManager -> servicesManager.findServiceBy(service)).orElse(null);
    }

    public Collection<RegisteredService> findServiceBy(Predicate<RegisteredService> clazz) {
        return this.serviceManagers.stream().flatMap(s -> s.findServiceBy(clazz).stream()).collect(Collectors.toList());
    }

    public <T extends RegisteredService> T findServiceBy(Service serviceId, Class<T> clazz) {
        Optional<ServicesManager> manager = this.findServicesManager(serviceId);
        return (T)((RegisteredService)manager.map(servicesManager -> servicesManager.findServiceBy(serviceId, clazz)).orElse(null));
    }

    public RegisteredService findServiceBy(long id) {
        return this.serviceManagers.stream().map(s -> s.findServiceBy(id)).filter(Objects::nonNull).findFirst().orElse(null);
    }

    public <T extends RegisteredService> T findServiceBy(long id, Class<T> clazz) {
        Optional<ServicesManager> manager = this.findServicesManager(clazz);
        return (T)((RegisteredService)manager.map(servicesManager -> servicesManager.findServiceBy(id, clazz)).orElse(null));
    }

    public RegisteredService findServiceByName(String name) {
        return this.serviceManagers.stream().map(s -> s.findServiceByName(name)).filter(Objects::nonNull).findFirst().orElse(null);
    }

    public <T extends RegisteredService> T findServiceByName(String name, Class<T> clazz) {
        Optional<ServicesManager> manager = this.findServicesManager(clazz);
        return (T)((RegisteredService)manager.map(servicesManager -> servicesManager.findServiceByName(name, clazz)).orElse(null));
    }

    public Collection<RegisteredService> getAllServices() {
        return this.serviceManagers.stream().flatMap(s -> s.getAllServices().stream()).collect(Collectors.toList());
    }

    public <T extends RegisteredService> Collection<T> getAllServicesOfType(Class<T> clazz) {
        return this.serviceManagers.stream().filter(s -> s.supports(clazz)).flatMap(s -> s.getAllServicesOfType(clazz).stream()).collect(Collectors.toList());
    }

    public Collection<RegisteredService> load() {
        return this.serviceManagers.stream().flatMap(s -> s.load().stream()).collect(Collectors.toList());
    }

    public long count() {
        return this.serviceManagers.stream().mapToLong(ServicesManager::count).sum();
    }

    public boolean supports(Service service) {
        return this.findServicesManager(service).isPresent();
    }

    public boolean supports(RegisteredService service) {
        return this.findServicesManager(service).isPresent();
    }

    public boolean supports(Class clazz) {
        return this.findServicesManager(clazz).isPresent();
    }

    public Stream<String> getDomains() {
        return this.serviceManagers.stream().flatMap(ServicesManager::getDomains);
    }

    public Collection<RegisteredService> getServicesForDomain(String domain) {
        return this.serviceManagers.stream().flatMap(d -> d.getServicesForDomain(domain).stream()).collect(Collectors.toList());
    }

    private Optional<ServicesManager> findServicesManager(RegisteredService service) {
        return this.serviceManagers.stream().filter(s -> s.supports(service)).findFirst();
    }

    private Optional<ServicesManager> findServicesManager(Service service) {
        return this.serviceManagers.stream().filter(s -> s.supports(service)).findFirst();
    }

    private Optional<ServicesManager> findServicesManager(Class<?> clazz) {
        return this.serviceManagers.stream().filter(s -> s.supports(clazz)).findFirst();
    }

    @Generated
    public List<ServicesManager> getServiceManagers() {
        return this.serviceManagers;
    }

    private /* synthetic */ RegisteredService lambda$save$2(Supplier supplier) {
        RegisteredService registeredService = (RegisteredService)supplier.get();
        return this.findServicesManager(registeredService).isPresent() ? registeredService : null;
    }
}

