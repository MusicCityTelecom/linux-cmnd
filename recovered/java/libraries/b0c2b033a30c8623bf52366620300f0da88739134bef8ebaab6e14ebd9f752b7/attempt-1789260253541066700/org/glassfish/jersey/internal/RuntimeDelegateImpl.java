/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal;

import javax.ws.rs.core.Application;
import org.glassfish.jersey.internal.AbstractRuntimeDelegate;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.message.internal.MessagingBinders;

public class RuntimeDelegateImpl
extends AbstractRuntimeDelegate {
    public RuntimeDelegateImpl() {
        super(new MessagingBinders.HeaderDelegateProviders().getHeaderDelegateProviders());
    }

    @Override
    public <T> T createEndpoint(Application application, Class<T> endpointType) throws IllegalArgumentException, UnsupportedOperationException {
        throw new UnsupportedOperationException(LocalizationMessages.NO_CONTAINER_AVAILABLE());
    }
}

