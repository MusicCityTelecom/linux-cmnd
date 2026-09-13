/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import org.glassfish.jersey.client.inject.ParameterUpdaterProvider;
import org.glassfish.jersey.internal.BootstrapBag;

public class ClientBootstrapBag
extends BootstrapBag {
    private ParameterUpdaterProvider parameterUpdaterProvider;

    public ParameterUpdaterProvider getParameterUpdaterProvider() {
        ClientBootstrapBag.requireNonNull(this.parameterUpdaterProvider, ParameterUpdaterProvider.class);
        return this.parameterUpdaterProvider;
    }

    public void setParameterUpdaterProvider(ParameterUpdaterProvider provider) {
        this.parameterUpdaterProvider = provider;
    }
}

