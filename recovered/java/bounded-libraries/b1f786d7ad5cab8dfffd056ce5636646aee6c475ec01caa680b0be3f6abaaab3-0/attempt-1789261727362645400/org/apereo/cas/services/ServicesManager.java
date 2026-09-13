/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.monitor.Monitorable
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.monitor.Monitorable;
import org.apereo.cas.services.RegisteredService;
import org.springframework.core.Ordered;

@Monitorable
public interface ServicesManager
extends Ordered {
    public static final String BEAN_NAME = "servicesManager";

    public void save(Stream<RegisteredService> var1);

    public RegisteredService save(RegisteredService var1);

    public RegisteredService save(RegisteredService var1, boolean var2);

    default public void save(RegisteredService ... services) {
        Arrays.stream(services).forEach(this::save);
    }

    public void save(Supplier<RegisteredService> var1, Consumer<RegisteredService> var2, long var3);

    public void deleteAll();

    public RegisteredService delete(long var1);

    public RegisteredService delete(RegisteredService var1);

    public RegisteredService findServiceBy(Service var1);

    public Collection<RegisteredService> findServiceBy(Predicate<RegisteredService> var1);

    public <T extends RegisteredService> T findServiceBy(Service var1, Class<T> var2);

    public RegisteredService findServiceBy(long var1);

    default public <T extends RegisteredService> T findServiceBy(long id, Class<T> clazz) {
        RegisteredService service = this.findServiceBy(id);
        if (service != null && clazz.isAssignableFrom(service.getClass())) {
            return (T)service;
        }
        return null;
    }

    public RegisteredService findServiceByName(String var1);

    default public <T extends RegisteredService> T findServiceByName(String name, Class<T> clazz) {
        RegisteredService service = this.findServiceByName(name);
        if (service != null && clazz.isAssignableFrom(service.getClass())) {
            return (T)service;
        }
        return null;
    }

    public Collection<RegisteredService> getAllServices();

    public <T extends RegisteredService> Collection<T> getAllServicesOfType(Class<T> var1);

    default public Stream<? extends RegisteredService> stream() {
        return this.getAllServices().stream();
    }

    public Collection<RegisteredService> load();

    default public long count() {
        return 0L;
    }

    default public String getName() {
        return this.getClass().getSimpleName();
    }

    default public boolean supports(Service service) {
        return true;
    }

    default public boolean supports(RegisteredService service) {
        return true;
    }

    default public boolean supports(Class clazz) {
        return true;
    }

    default public int getOrder() {
        return Integer.MAX_VALUE;
    }

    default public Stream<String> getDomains() {
        return Stream.of("default");
    }

    public Collection<RegisteredService> getServicesForDomain(String var1);
}

