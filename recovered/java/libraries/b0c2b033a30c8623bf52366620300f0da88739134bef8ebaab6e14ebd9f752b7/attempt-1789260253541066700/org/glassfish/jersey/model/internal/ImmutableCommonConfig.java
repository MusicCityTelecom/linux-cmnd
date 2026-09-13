/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.model.internal;

import java.util.Map;
import javax.ws.rs.core.Configuration;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.model.internal.CommonConfig;

public class ImmutableCommonConfig
extends CommonConfig {
    private final String errorMessage;

    public ImmutableCommonConfig(CommonConfig config, String modificationErrorMessage) {
        super(config);
        this.errorMessage = modificationErrorMessage;
    }

    public ImmutableCommonConfig(CommonConfig config) {
        this(config, LocalizationMessages.CONFIGURATION_NOT_MODIFIABLE());
    }

    @Override
    public ImmutableCommonConfig property(String name, Object value) {
        throw new IllegalStateException(this.errorMessage);
    }

    @Override
    public ImmutableCommonConfig setProperties(Map<String, ?> properties) {
        throw new IllegalStateException(this.errorMessage);
    }

    @Override
    public ImmutableCommonConfig register(Class<?> componentClass) {
        throw new IllegalStateException(this.errorMessage);
    }

    @Override
    public ImmutableCommonConfig register(Class<?> componentClass, int bindingPriority) {
        throw new IllegalStateException(this.errorMessage);
    }

    @Override
    public ImmutableCommonConfig register(Class<?> componentClass, Class<?> ... contracts) {
        throw new IllegalStateException(this.errorMessage);
    }

    @Override
    public CommonConfig register(Class<?> componentClass, Map<Class<?>, Integer> contracts) {
        throw new IllegalStateException(this.errorMessage);
    }

    @Override
    public ImmutableCommonConfig register(Object component) {
        throw new IllegalStateException(this.errorMessage);
    }

    @Override
    public ImmutableCommonConfig register(Object component, int bindingPriority) {
        throw new IllegalStateException(this.errorMessage);
    }

    @Override
    public ImmutableCommonConfig register(Object component, Class<?> ... contracts) {
        throw new IllegalStateException(this.errorMessage);
    }

    @Override
    public CommonConfig register(Object component, Map<Class<?>, Integer> contracts) {
        throw new IllegalStateException(this.errorMessage);
    }

    @Override
    public CommonConfig loadFrom(Configuration config) {
        throw new IllegalStateException(this.errorMessage);
    }
}

