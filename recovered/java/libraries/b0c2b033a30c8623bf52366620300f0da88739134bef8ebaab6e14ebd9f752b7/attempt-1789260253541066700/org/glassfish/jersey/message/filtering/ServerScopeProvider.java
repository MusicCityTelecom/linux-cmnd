/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Configuration;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.message.filtering.CommonScopeProvider;
import org.glassfish.jersey.server.ExtendedUriInfo;
import org.glassfish.jersey.server.model.Invocable;
import org.glassfish.jersey.server.model.ResourceMethod;

@Singleton
@Priority(value=4200)
@ConstrainedTo(value=RuntimeType.SERVER)
class ServerScopeProvider
extends CommonScopeProvider {
    @Inject
    private Provider<ExtendedUriInfo> uriInfoProvider;
    private final ConcurrentMap<String, Set<String>> uriToContexts = new ConcurrentHashMap<String, Set<String>>();

    @Inject
    public ServerScopeProvider(Configuration config, InjectionManager injectionManager) {
        super(config, injectionManager);
    }

    @Override
    public Set<String> getFilteringScopes(Annotation[] entityAnnotations, boolean defaultIfNotFound) {
        Set<String> filteringScope = super.getFilteringScopes(entityAnnotations, false);
        if (filteringScope.isEmpty()) {
            ExtendedUriInfo uriInfo = this.uriInfoProvider.get();
            String path = uriInfo.getPath();
            if (this.uriToContexts.containsKey(path)) {
                return (Set)this.uriToContexts.get(path);
            }
            for (ResourceMethod method : ServerScopeProvider.getMatchedMethods(uriInfo)) {
                Invocable invocable = method.getInvocable();
                this.mergeFilteringScopes(filteringScope, this.getFilteringScopes(invocable.getHandlingMethod(), invocable.getHandler().getHandlerClass()));
                if (filteringScope.isEmpty()) continue;
                this.uriToContexts.putIfAbsent(path, filteringScope);
                return filteringScope;
            }
        }
        return this.returnFilteringScopes(filteringScope, defaultIfNotFound);
    }

    protected Set<String> getFilteringScopes(Method resourceMethod, Class<?> resourceClass) {
        Set<String> scope = this.getFilteringScopes(resourceMethod.getAnnotations());
        if (scope.isEmpty()) {
            scope = this.getFilteringScopes(resourceClass.getAnnotations());
        }
        return scope;
    }

    private static List<ResourceMethod> getMatchedMethods(ExtendedUriInfo uriInfo) {
        List<ResourceMethod> matchedResourceLocators = uriInfo.getMatchedResourceLocators();
        ArrayList<ResourceMethod> methods = new ArrayList<ResourceMethod>(1 + matchedResourceLocators.size());
        methods.add(uriInfo.getMatchedResourceMethod());
        methods.addAll(matchedResourceLocators);
        return methods;
    }
}

