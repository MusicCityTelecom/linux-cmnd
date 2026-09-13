/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import java.util.LinkedList;
import java.util.Optional;
import javax.ws.rs.WebApplicationException;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.ServiceFinder;
import org.glassfish.jersey.internal.inject.Binder;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.InjectionManagerFactory;
import org.glassfish.jersey.model.internal.RankedComparator;
import org.glassfish.jersey.model.internal.RankedProvider;

public class Injections {
    public static InjectionManager createInjectionManager() {
        return Injections.lookupInjectionManagerFactory().create();
    }

    public static InjectionManager createInjectionManager(Binder binder) {
        InjectionManagerFactory injectionManagerFactory = Injections.lookupInjectionManagerFactory();
        InjectionManager injectionManager = injectionManagerFactory.create();
        injectionManager.register(binder);
        return injectionManager;
    }

    public static InjectionManager createInjectionManager(Object parent) {
        return Injections.lookupInjectionManagerFactory().create(parent);
    }

    private static InjectionManagerFactory lookupInjectionManagerFactory() {
        return Injections.lookupService(InjectionManagerFactory.class).orElseThrow(() -> new IllegalStateException(LocalizationMessages.INJECTION_MANAGER_FACTORY_NOT_FOUND()));
    }

    private static <T> Optional<T> lookupService(Class<T> clazz) {
        LinkedList<RankedProvider<T>> providers = new LinkedList<RankedProvider<T>>();
        for (T provider : ServiceFinder.find(clazz)) {
            providers.add(new RankedProvider<T>(provider));
        }
        providers.sort(new RankedComparator(RankedComparator.Order.DESCENDING));
        return providers.isEmpty() ? Optional.empty() : Optional.ofNullable(((RankedProvider)providers.get(0)).getProvider());
    }

    public static <T> T getOrCreate(InjectionManager injectionManager, Class<T> clazz) {
        try {
            T component = injectionManager.getInstance(clazz);
            return component == null ? injectionManager.createAndInitialize(clazz) : component;
        }
        catch (RuntimeException e) {
            Throwable throwable = e.getCause();
            if (throwable != null && WebApplicationException.class.isAssignableFrom(throwable.getClass())) {
                throw (WebApplicationException)throwable;
            }
            throw e;
        }
    }
}

