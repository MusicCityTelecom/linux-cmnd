/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationContext
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.http.MediaType
 *  org.springframework.web.reactive.function.server.RequestPredicate
 *  org.springframework.web.reactive.function.server.RequestPredicates
 *  org.springframework.web.reactive.function.server.RouterFunction
 *  org.springframework.web.reactive.function.server.RouterFunctions
 *  org.springframework.web.reactive.function.server.ServerResponse
 */
package org.springframework.boot.autoconfigure.web.reactive;

import java.util.Arrays;
import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProviders;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

final class WelcomePageRouterFunctionFactory {
    private final String staticPathPattern;
    private final Resource welcomePage;
    private final boolean welcomePageTemplateExists;

    WelcomePageRouterFunctionFactory(TemplateAvailabilityProviders templateAvailabilityProviders, ApplicationContext applicationContext, String[] staticLocations, String staticPathPattern) {
        this.staticPathPattern = staticPathPattern;
        this.welcomePage = this.getWelcomePage((ResourceLoader)applicationContext, staticLocations);
        this.welcomePageTemplateExists = this.welcomeTemplateExists(templateAvailabilityProviders, applicationContext);
    }

    private Resource getWelcomePage(ResourceLoader resourceLoader, String[] staticLocations) {
        return Arrays.stream(staticLocations).map(location -> this.getIndexHtml(resourceLoader, (String)location)).filter(this::isReadable).findFirst().orElse(null);
    }

    private Resource getIndexHtml(ResourceLoader resourceLoader, String location) {
        return resourceLoader.getResource(location + "index.html");
    }

    private boolean isReadable(Resource resource) {
        try {
            return resource.exists() && resource.getURL() != null;
        }
        catch (Exception ex) {
            return false;
        }
    }

    private boolean welcomeTemplateExists(TemplateAvailabilityProviders templateAvailabilityProviders, ApplicationContext applicationContext) {
        return templateAvailabilityProviders.getProvider("index", applicationContext) != null;
    }

    RouterFunction<ServerResponse> createRouterFunction() {
        if (this.welcomePage != null && "/**".equals(this.staticPathPattern)) {
            return RouterFunctions.route((RequestPredicate)RequestPredicates.GET((String)"/").and(RequestPredicates.accept((MediaType[])new MediaType[]{MediaType.TEXT_HTML})), req -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).bodyValue((Object)this.welcomePage));
        }
        if (this.welcomePageTemplateExists) {
            return RouterFunctions.route((RequestPredicate)RequestPredicates.GET((String)"/").and(RequestPredicates.accept((MediaType[])new MediaType[]{MediaType.TEXT_HTML})), req -> ServerResponse.ok().render("index", new Object[0]));
        }
        return null;
    }
}

