/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.config;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.internal.config.ExternalPropertiesConfigurationFactory;

public class ExternalPropertiesConfigurationFeature
implements Feature {
    @Override
    public boolean configure(FeatureContext configurableContext) {
        return ExternalPropertiesConfigurationFactory.configure(configurableContext);
    }
}

