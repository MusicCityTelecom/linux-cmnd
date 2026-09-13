/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationHandlerResolver;
import org.apereo.cas.authentication.AuthenticationMetaDataPopulator;
import org.apereo.cas.authentication.AuthenticationPolicy;
import org.apereo.cas.authentication.AuthenticationPolicyResolver;
import org.apereo.cas.authentication.AuthenticationPostProcessor;
import org.apereo.cas.authentication.AuthenticationPreProcessor;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.principal.PrincipalResolver;

public interface AuthenticationEventExecutionPlan {
    public static final String DEFAULT_BEAN_NAME = "authenticationEventExecutionPlan";

    public boolean registerAuthenticationHandler(AuthenticationHandler var1);

    default public void registerAuthenticationHandlers(List<? extends AuthenticationHandler> handlers) {
        handlers.forEach(this::registerAuthenticationHandler);
    }

    public void registerAuthenticationMetadataPopulator(AuthenticationMetaDataPopulator var1);

    public void registerAuthenticationPostProcessor(AuthenticationPostProcessor var1);

    public void registerAuthenticationPreProcessor(AuthenticationPreProcessor var1);

    public void registerAuthenticationMetadataPopulators(Collection<AuthenticationMetaDataPopulator> var1);

    public void registerAuthenticationPolicy(AuthenticationPolicy var1);

    public void registerAuthenticationPolicies(Collection<AuthenticationPolicy> var1);

    public void registerAuthenticationHandlerResolver(AuthenticationHandlerResolver var1);

    public void registerAuthenticationPolicyResolver(AuthenticationPolicyResolver var1);

    public void registerAuthenticationHandlerWithPrincipalResolver(Map<AuthenticationHandler, PrincipalResolver> var1);

    public boolean registerAuthenticationHandlerWithPrincipalResolver(AuthenticationHandler var1, PrincipalResolver var2);

    public void registerAuthenticationHandlerWithPrincipalResolvers(Collection<AuthenticationHandler> var1, PrincipalResolver var2);

    public void registerAuthenticationHandlerWithPrincipalResolvers(List<AuthenticationHandler> var1, List<PrincipalResolver> var2);

    public Set<AuthenticationHandler> getAuthenticationHandlers(AuthenticationTransaction var1);

    public Set<AuthenticationHandler> getAuthenticationHandlers();

    default public Set<AuthenticationHandler> getAuthenticationHandlersBy(Predicate<AuthenticationHandler> filter) {
        return this.getAuthenticationHandlers().stream().filter(filter).collect(Collectors.toSet());
    }

    public Collection<AuthenticationMetaDataPopulator> getAuthenticationMetadataPopulators(AuthenticationTransaction var1);

    public Collection<AuthenticationPostProcessor> getAuthenticationPostProcessors(AuthenticationTransaction var1);

    public Collection<AuthenticationPreProcessor> getAuthenticationPreProcessors(AuthenticationTransaction var1);

    public PrincipalResolver getPrincipalResolver(AuthenticationHandler var1, AuthenticationTransaction var2);

    public Collection<AuthenticationPolicy> getAuthenticationPolicies(AuthenticationTransaction var1);

    public Collection<AuthenticationPolicy> getAuthenticationPolicies(Authentication var1);

    public Collection<AuthenticationPolicy> getAuthenticationPolicies();

    public Collection<AuthenticationHandlerResolver> getAuthenticationHandlerResolvers(AuthenticationTransaction var1);

    public Collection<AuthenticationPolicyResolver> getAuthenticationPolicyResolvers(AuthenticationTransaction var1);
}

