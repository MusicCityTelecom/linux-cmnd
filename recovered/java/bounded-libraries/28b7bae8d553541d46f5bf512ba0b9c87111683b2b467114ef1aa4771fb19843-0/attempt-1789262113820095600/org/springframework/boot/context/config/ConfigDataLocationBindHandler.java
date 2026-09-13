/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.config;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.boot.context.config.ConfigDataLocation;
import org.springframework.boot.context.properties.bind.AbstractBindHandler;
import org.springframework.boot.context.properties.bind.BindContext;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.origin.Origin;

class ConfigDataLocationBindHandler
extends AbstractBindHandler {
    ConfigDataLocationBindHandler() {
    }

    @Override
    public Object onSuccess(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) {
        if (result instanceof ConfigDataLocation) {
            return this.withOrigin(context, (ConfigDataLocation)result);
        }
        if (result instanceof List) {
            List list = ((List)result).stream().filter(Objects::nonNull).collect(Collectors.toList());
            for (int i = 0; i < list.size(); ++i) {
                Object element = list.get(i);
                if (!(element instanceof ConfigDataLocation)) continue;
                list.set(i, this.withOrigin(context, (ConfigDataLocation)element));
            }
            return list;
        }
        if (result instanceof ConfigDataLocation[]) {
            ConfigDataLocation[] locations = (ConfigDataLocation[])Arrays.stream((ConfigDataLocation[])result).filter(Objects::nonNull).toArray(ConfigDataLocation[]::new);
            for (int i = 0; i < locations.length; ++i) {
                locations[i] = this.withOrigin(context, locations[i]);
            }
            return locations;
        }
        return result;
    }

    private ConfigDataLocation withOrigin(BindContext context, ConfigDataLocation result) {
        if (result.getOrigin() != null) {
            return result;
        }
        Origin origin = Origin.from(context.getConfigurationProperty());
        return result.withOrigin(origin);
    }
}

