/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.filter;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.client.filter.EncodingFilter;
import org.glassfish.jersey.internal.inject.Providers;
import org.glassfish.jersey.spi.ContentEncoder;

public class EncodingFeature
implements Feature {
    private final String useEncoding;
    private final Class<?>[] encodingProviders;

    public EncodingFeature(Class<?> ... encodingProviders) {
        this((String)null, encodingProviders);
    }

    public EncodingFeature(String useEncoding, Class<?> ... encoders) {
        this.useEncoding = useEncoding;
        Providers.ensureContract(ContentEncoder.class, encoders);
        this.encodingProviders = encoders;
    }

    @Override
    public boolean configure(FeatureContext context) {
        boolean enable;
        if (this.useEncoding != null && !context.getConfiguration().getProperties().containsKey("jersey.config.client.useEncoding")) {
            context.property("jersey.config.client.useEncoding", this.useEncoding);
        }
        for (Class<?> provider : this.encodingProviders) {
            context.register(provider);
        }
        boolean bl = enable = this.useEncoding != null || this.encodingProviders.length > 0;
        if (enable) {
            context.register(EncodingFilter.class);
        }
        return enable;
    }
}

