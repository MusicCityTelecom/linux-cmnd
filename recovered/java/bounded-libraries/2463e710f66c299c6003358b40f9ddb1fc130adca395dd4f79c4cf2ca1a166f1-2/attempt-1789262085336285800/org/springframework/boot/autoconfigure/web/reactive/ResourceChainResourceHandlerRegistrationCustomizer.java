/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.reactive.config.ResourceChainRegistration
 *  org.springframework.web.reactive.config.ResourceHandlerRegistration
 *  org.springframework.web.reactive.resource.EncodedResourceResolver
 *  org.springframework.web.reactive.resource.ResourceResolver
 *  org.springframework.web.reactive.resource.VersionResourceResolver
 */
package org.springframework.boot.autoconfigure.web.reactive;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.ResourceHandlerRegistrationCustomizer;
import org.springframework.web.reactive.config.ResourceChainRegistration;
import org.springframework.web.reactive.config.ResourceHandlerRegistration;
import org.springframework.web.reactive.resource.EncodedResourceResolver;
import org.springframework.web.reactive.resource.ResourceResolver;
import org.springframework.web.reactive.resource.VersionResourceResolver;

class ResourceChainResourceHandlerRegistrationCustomizer
implements ResourceHandlerRegistrationCustomizer {
    private final WebProperties.Resources resourceProperties;

    ResourceChainResourceHandlerRegistrationCustomizer(WebProperties.Resources resources) {
        this.resourceProperties = resources;
    }

    @Override
    public void customize(ResourceHandlerRegistration registration) {
        WebProperties.Resources.Chain properties = this.resourceProperties.getChain();
        this.configureResourceChain(properties, registration.resourceChain(properties.isCache()));
    }

    private void configureResourceChain(WebProperties.Resources.Chain properties, ResourceChainRegistration chain) {
        WebProperties.Resources.Chain.Strategy strategy = properties.getStrategy();
        if (properties.isCompressed()) {
            chain.addResolver((ResourceResolver)new EncodedResourceResolver());
        }
        if (strategy.getFixed().isEnabled() || strategy.getContent().isEnabled()) {
            chain.addResolver(this.getVersionResourceResolver(strategy));
        }
    }

    private ResourceResolver getVersionResourceResolver(WebProperties.Resources.Chain.Strategy properties) {
        VersionResourceResolver resolver = new VersionResourceResolver();
        if (properties.getFixed().isEnabled()) {
            String version = properties.getFixed().getVersion();
            String[] paths = properties.getFixed().getPaths();
            resolver.addFixedVersionStrategy(version, paths);
        }
        if (properties.getContent().isEnabled()) {
            String[] paths = properties.getContent().getPaths();
            resolver.addContentVersionStrategy(paths);
        }
        return resolver;
    }
}

