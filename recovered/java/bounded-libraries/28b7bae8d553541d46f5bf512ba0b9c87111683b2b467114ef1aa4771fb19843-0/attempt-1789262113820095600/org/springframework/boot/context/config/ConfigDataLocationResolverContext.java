/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.config;

import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.context.config.ConfigDataResource;
import org.springframework.boot.context.properties.bind.Binder;

public interface ConfigDataLocationResolverContext {
    public Binder getBinder();

    public ConfigDataResource getParent();

    public ConfigurableBootstrapContext getBootstrapContext();
}

