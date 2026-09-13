/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import javax.ws.rs.core.Application;
import org.glassfish.jersey.internal.ServiceFinder;
import org.glassfish.jersey.server.spi.ContainerProvider;

public final class ContainerFactory {
    private ContainerFactory() {
    }

    public static <T> T createContainer(Class<T> type, Application application) {
        for (ContainerProvider containerProvider : ServiceFinder.find(ContainerProvider.class)) {
            T container = containerProvider.createContainer(type, application);
            if (container == null) continue;
            return container;
        }
        throw new IllegalArgumentException("No container provider supports the type " + type);
    }
}

