/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import org.glassfish.jersey.InjectionManagerProvider;
import org.glassfish.jersey.client.internal.LocalizationMessages;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.InjectionManagerSupplier;

public class InjectionManagerClientProvider
extends InjectionManagerProvider {
    public static InjectionManager getInjectionManager(ClientRequestContext clientRequestContext) {
        if (!(clientRequestContext instanceof InjectionManagerSupplier)) {
            throw new IllegalArgumentException(LocalizationMessages.ERROR_SERVICE_LOCATOR_PROVIDER_INSTANCE_REQUEST(clientRequestContext.getClass().getName()));
        }
        return ((InjectionManagerSupplier)((Object)clientRequestContext)).getInjectionManager();
    }

    public static InjectionManager getInjectionManager(ClientResponseContext clientResponseContext) {
        if (!(clientResponseContext instanceof InjectionManagerSupplier)) {
            throw new IllegalArgumentException(LocalizationMessages.ERROR_SERVICE_LOCATOR_PROVIDER_INSTANCE_RESPONSE(clientResponseContext.getClass().getName()));
        }
        return ((InjectionManagerSupplier)((Object)clientResponseContext)).getInjectionManager();
    }
}

