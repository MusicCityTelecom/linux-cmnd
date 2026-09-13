/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jackson.internal;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import javax.inject.Singleton;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.GenericType;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.ClassBinding;
import org.glassfish.jersey.jackson.internal.JacksonObjectProvider;
import org.glassfish.jersey.message.filtering.spi.ObjectGraphTransformer;
import org.glassfish.jersey.message.filtering.spi.ObjectProvider;

public final class JacksonFilteringFeature
implements Feature {
    @Override
    public boolean configure(FeatureContext context) {
        Configuration config = context.getConfiguration();
        if (!config.isRegistered(Binder.class)) {
            context.register(new Binder());
            return true;
        }
        return false;
    }

    private static final class Binder
    extends AbstractBinder {
        private Binder() {
        }

        @Override
        protected void configure() {
            ((ClassBinding)((ClassBinding)this.bindAsContract(JacksonObjectProvider.class).to(new GenericType<ObjectProvider<FilterProvider>>(){})).to(new GenericType<ObjectGraphTransformer<FilterProvider>>(){})).in(Singleton.class);
        }
    }
}

