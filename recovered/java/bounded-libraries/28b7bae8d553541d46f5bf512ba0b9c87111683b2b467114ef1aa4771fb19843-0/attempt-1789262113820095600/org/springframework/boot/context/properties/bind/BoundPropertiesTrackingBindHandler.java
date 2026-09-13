/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context.properties.bind;

import java.util.function.Consumer;
import org.springframework.boot.context.properties.bind.AbstractBindHandler;
import org.springframework.boot.context.properties.bind.BindContext;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.util.Assert;

public class BoundPropertiesTrackingBindHandler
extends AbstractBindHandler {
    private final Consumer<ConfigurationProperty> consumer;

    public BoundPropertiesTrackingBindHandler(Consumer<ConfigurationProperty> consumer) {
        Assert.notNull(consumer, (String)"Consumer must not be null");
        this.consumer = consumer;
    }

    @Override
    public Object onSuccess(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) {
        if (context.getConfigurationProperty() != null && name.equals(context.getConfigurationProperty().getName())) {
            this.consumer.accept(context.getConfigurationProperty());
        }
        return super.onSuccess(name, target, context, result);
    }
}

