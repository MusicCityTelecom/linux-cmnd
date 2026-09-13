/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jaxb.internal;

import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.internal.spi.ForcedAutoDiscoverable;
import org.glassfish.jersey.jaxb.internal.JaxbMessagingBinder;
import org.glassfish.jersey.jaxb.internal.JaxbParamConverterBinder;

public final class JaxbAutoDiscoverable
implements ForcedAutoDiscoverable {
    @Override
    public void configure(FeatureContext context) {
        context.register(new JaxbMessagingBinder());
        if (RuntimeType.SERVER == context.getConfiguration().getRuntimeType()) {
            context.register(new JaxbParamConverterBinder());
        }
    }
}

