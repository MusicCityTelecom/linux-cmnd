/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package org.apereo.cas.services;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.services.RegisteredService;

public interface ServiceRegistry {
    public static final String BEAN_NAME = "serviceRegistry";

    default public Long save(Supplier<RegisteredService> supplier, Consumer<RegisteredService> andThenConsume, long countExclusive) {
        return LongStream.range(0L, countExclusive).mapToObj(count -> (RegisteredService)supplier.get()).filter(Objects::nonNull).map(this::save).peek(andThenConsume).count();
    }

    default public Stream<RegisteredService> save(Stream<RegisteredService> toSave) {
        return toSave.map(this::save);
    }

    public RegisteredService save(RegisteredService var1);

    public boolean delete(RegisteredService var1);

    public void deleteAll();

    public Collection<RegisteredService> load();

    default public Stream<? extends RegisteredService> getServicesStream() {
        return this.load().stream();
    }

    public RegisteredService findServiceById(long var1);

    default public <T extends RegisteredService> T findServiceById(long id, Class<T> clazz) {
        RegisteredService service = this.findServiceById(id);
        if (service == null) {
            return null;
        }
        if (!clazz.isAssignableFrom(service.getClass())) {
            throw new ClassCastException("Object [" + service + " is of type " + service.getClass() + " when we were expecting " + clazz);
        }
        return (T)((RegisteredService)clazz.cast(service));
    }

    default public RegisteredService findServiceBy(String id) {
        return this.getServicesStream().sorted().filter(r -> r.matches(id)).findFirst().orElse(null);
    }

    default public RegisteredService findServiceByExactServiceId(String id) {
        return this.getServicesStream().sorted().filter(r -> StringUtils.isNotBlank((CharSequence)r.getServiceId()) && r.getServiceId().equals(id)).findFirst().orElse(null);
    }

    default public RegisteredService findServiceByExactServiceName(String name) {
        return this.getServicesStream().sorted().filter(r -> r.getName().equals(name)).findFirst().orElse(null);
    }

    default public <T extends RegisteredService> T findServiceByExactServiceName(String name, Class<T> clazz) {
        RegisteredService service = this.findServiceByExactServiceName(name);
        if (service == null) {
            return null;
        }
        if (!clazz.isAssignableFrom(service.getClass())) {
            throw new ClassCastException("Object [" + service + " is of type " + service.getClass() + " when we were expecting " + clazz);
        }
        return (T)((RegisteredService)clazz.cast(service));
    }

    default public Collection<RegisteredService> findServicePredicate(Predicate<RegisteredService> predicate) {
        return this.getServicesStream().sorted().filter(predicate).collect(Collectors.toList());
    }

    default public long size() {
        return this.load().size();
    }

    default public String getName() {
        return this.getClass().getSimpleName();
    }
}

