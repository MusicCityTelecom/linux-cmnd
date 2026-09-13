/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.properties.bind;

import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;

public interface BindContext {
    public Binder getBinder();

    public int getDepth();

    public Iterable<ConfigurationPropertySource> getSources();

    public ConfigurationProperty getConfigurationProperty();
}

