/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Variant;
import javax.ws.rs.ext.RuntimeDelegate;
import org.glassfish.jersey.internal.ServiceFinder;
import org.glassfish.jersey.internal.util.PropertiesHelper;
import org.glassfish.jersey.spi.HeaderDelegateProvider;

public class RuntimeDelegateDecorator {
    public static RuntimeDelegate configured(Configuration configuration) {
        return new ConfigurableRuntimeDelegate(RuntimeDelegate.getInstance(), configuration);
    }

    private static class ConfigurableRuntimeDelegate
    extends RuntimeDelegate {
        private final RuntimeDelegate runtimeDelegate;
        private final Configuration configuration;
        private static final Set<HeaderDelegateProvider> headerDelegateProviders;

        private ConfigurableRuntimeDelegate(RuntimeDelegate runtimeDelegate, Configuration configuration) {
            this.runtimeDelegate = runtimeDelegate;
            this.configuration = configuration;
        }

        @Override
        public UriBuilder createUriBuilder() {
            return this.runtimeDelegate.createUriBuilder();
        }

        @Override
        public Response.ResponseBuilder createResponseBuilder() {
            return this.runtimeDelegate.createResponseBuilder();
        }

        @Override
        public Variant.VariantListBuilder createVariantListBuilder() {
            return this.runtimeDelegate.createVariantListBuilder();
        }

        @Override
        public <T> T createEndpoint(Application application, Class<T> endpointType) throws IllegalArgumentException, UnsupportedOperationException {
            return this.runtimeDelegate.createEndpoint(application, endpointType);
        }

        @Override
        public <T> RuntimeDelegate.HeaderDelegate<T> createHeaderDelegate(Class<T> type) throws IllegalArgumentException {
            RuntimeDelegate.HeaderDelegate<T> headerDelegate = null;
            if (this.configuration == null || PropertiesHelper.isMetaInfServicesEnabled(this.configuration.getProperties(), this.configuration.getRuntimeType())) {
                headerDelegate = this._createHeaderDelegate(type);
            }
            if (headerDelegate == null) {
                headerDelegate = this.runtimeDelegate.createHeaderDelegate(type);
            }
            return headerDelegate;
        }

        @Override
        public Link.Builder createLinkBuilder() {
            return this.runtimeDelegate.createLinkBuilder();
        }

        private <T> RuntimeDelegate.HeaderDelegate<T> _createHeaderDelegate(Class<T> type) {
            for (HeaderDelegateProvider hp : headerDelegateProviders) {
                if (!hp.supports(type)) continue;
                return hp;
            }
            return null;
        }

        static {
            HashSet<HeaderDelegateProvider> hps = new HashSet<HeaderDelegateProvider>();
            for (HeaderDelegateProvider provider : ServiceFinder.find(HeaderDelegateProvider.class, true)) {
                hps.add(provider);
            }
            headerDelegateProviders = Collections.unmodifiableSet(hps);
        }
    }
}

