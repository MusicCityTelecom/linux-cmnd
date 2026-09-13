/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationBuilder
 *  org.apereo.cas.authentication.AuthenticationEventExecutionPlan
 *  org.apereo.cas.authentication.AuthenticationException
 *  org.apereo.cas.authentication.AuthenticationHandler
 *  org.apereo.cas.authentication.AuthenticationHandlerExecutionResult
 *  org.apereo.cas.authentication.AuthenticationManager
 *  org.apereo.cas.authentication.AuthenticationMetaDataPopulator
 *  org.apereo.cas.authentication.AuthenticationPolicyExecutionResult
 *  org.apereo.cas.authentication.AuthenticationPostProcessor
 *  org.apereo.cas.authentication.AuthenticationPreProcessor
 *  org.apereo.cas.authentication.AuthenticationSystemSupport
 *  org.apereo.cas.authentication.AuthenticationTransaction
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.CredentialMetaData
 *  org.apereo.cas.authentication.PreventedException
 *  org.apereo.cas.authentication.PrincipalElectionStrategy
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.PrincipalResolver
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.support.events.authentication.CasAuthenticationPolicyFailureEvent
 *  org.apereo.cas.support.events.authentication.CasAuthenticationPrincipalResolvedEvent
 *  org.apereo.cas.support.events.authentication.CasAuthenticationTransactionFailureEvent
 *  org.apereo.cas.support.events.authentication.CasAuthenticationTransactionStartedEvent
 *  org.apereo.cas.support.events.authentication.CasAuthenticationTransactionSuccessfulEvent
 *  org.apereo.cas.util.LoggingUtils
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.inspektr.audit.annotation.Audit
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.apereo.cas.authentication;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationBuilder;
import org.apereo.cas.authentication.AuthenticationCredentialsThreadLocalBinder;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.AuthenticationManager;
import org.apereo.cas.authentication.AuthenticationMetaDataPopulator;
import org.apereo.cas.authentication.AuthenticationPolicyExecutionResult;
import org.apereo.cas.authentication.AuthenticationPostProcessor;
import org.apereo.cas.authentication.AuthenticationPreProcessor;
import org.apereo.cas.authentication.AuthenticationSystemSupport;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.CredentialMetaData;
import org.apereo.cas.authentication.DefaultAuthenticationBuilder;
import org.apereo.cas.authentication.DefaultAuthenticationResultBuilder;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.PrincipalElectionStrategy;
import org.apereo.cas.authentication.exceptions.UnresolvedPrincipalException;
import org.apereo.cas.authentication.metadata.BasicCredentialMetaData;
import org.apereo.cas.authentication.principal.NullPrincipal;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalResolver;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.support.events.authentication.CasAuthenticationPolicyFailureEvent;
import org.apereo.cas.support.events.authentication.CasAuthenticationPrincipalResolvedEvent;
import org.apereo.cas.support.events.authentication.CasAuthenticationTransactionFailureEvent;
import org.apereo.cas.support.events.authentication.CasAuthenticationTransactionStartedEvent;
import org.apereo.cas.support.events.authentication.CasAuthenticationTransactionSuccessfulEvent;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.inspektr.audit.annotation.Audit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class DefaultAuthenticationManager
implements AuthenticationManager {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAuthenticationManager.class);
    private final AuthenticationEventExecutionPlan authenticationEventExecutionPlan;
    private final boolean principalResolutionFailureFatal;
    private final ConfigurableApplicationContext applicationContext;

    @Audit(action="AUTHENTICATION", actionResolverName="AUTHENTICATION_RESOLVER", resourceResolverName="AUTHENTICATION_RESOURCE_RESOLVER")
    public Authentication authenticate(AuthenticationTransaction transaction) throws AuthenticationException {
        boolean result = this.invokeAuthenticationPreProcessors(transaction);
        if (!result) {
            LOGGER.warn("An authentication pre-processor could not successfully process the authentication transaction");
            throw new AuthenticationException("Authentication pre-processor has failed to process transaction");
        }
        AuthenticationCredentialsThreadLocalBinder.bindCurrent(transaction.getCredentials());
        AuthenticationBuilder builder = this.authenticateInternal(transaction);
        AuthenticationCredentialsThreadLocalBinder.bindCurrent(builder);
        Authentication authentication = builder.build();
        this.addAuthenticationMethodAttribute(builder, authentication);
        this.populateAuthenticationMetadataAttributes(builder, transaction);
        this.invokeAuthenticationPostProcessors(builder, transaction);
        Authentication auth = builder.build();
        Principal principal = auth.getPrincipal();
        if (principal instanceof NullPrincipal) {
            throw new UnresolvedPrincipalException(auth);
        }
        LOGGER.info("Authenticated principal [{}] with attributes [{}] via credentials [{}].", new Object[]{principal.getId(), principal.getAttributes(), transaction.getCredentials()});
        AuthenticationCredentialsThreadLocalBinder.bindCurrent(auth);
        return auth;
    }

    protected void invokeAuthenticationPostProcessors(AuthenticationBuilder builder, AuthenticationTransaction transaction) {
        LOGGER.debug("Invoking authentication post processors for authentication transaction");
        Collection pops = this.authenticationEventExecutionPlan.getAuthenticationPostProcessors(transaction);
        pops.stream().filter(processor -> transaction.getCredentials().stream().anyMatch(arg_0 -> ((AuthenticationPostProcessor)processor).supports(arg_0))).forEach(processor -> processor.process(builder, transaction));
    }

    protected void populateAuthenticationMetadataAttributes(AuthenticationBuilder builder, AuthenticationTransaction transaction) {
        LOGGER.debug("Invoking authentication metadata populators for authentication transaction");
        Collection<AuthenticationMetaDataPopulator> pops = this.getAuthenticationMetadataPopulatorsForTransaction(transaction);
        pops.forEach(populator -> transaction.getCredentials().stream().filter(arg_0 -> ((AuthenticationMetaDataPopulator)populator).supports(arg_0)).forEach(credential -> populator.populateAttributes(builder, transaction)));
    }

    protected void addAuthenticationMethodAttribute(AuthenticationBuilder builder, Authentication authentication) {
        authentication.getSuccesses().values().forEach(result -> builder.addAttribute("authenticationMethod", (Object)result.getHandlerName()));
    }

    protected Principal resolvePrincipal(AuthenticationHandler handler, PrincipalResolver resolver, Credential credential, Principal principal) {
        if (resolver.supports(credential)) {
            try {
                Principal p = resolver.resolve(credential, Optional.ofNullable(principal), Optional.ofNullable(handler));
                LOGGER.debug("[{}] resolved [{}] from [{}]", new Object[]{resolver, p, credential});
                return p;
            }
            catch (Exception e) {
                LOGGER.error("[{}] failed to resolve principal from [{}]", (Object)resolver, (Object)credential);
                LoggingUtils.error((Logger)LOGGER, (Throwable)e);
            }
        } else {
            LOGGER.warn("[{}] is configured to use [{}] but it does not support [{}], which suggests a configuration problem.", new Object[]{handler.getName(), resolver, credential});
        }
        return null;
    }

    protected boolean invokeAuthenticationPreProcessors(AuthenticationTransaction transaction) {
        LOGGER.trace("Invoking authentication pre processors for authentication transaction");
        Collection pops = this.authenticationEventExecutionPlan.getAuthenticationPreProcessors(transaction);
        List supported = pops.stream().filter(processor -> transaction.getCredentials().stream().anyMatch(arg_0 -> ((AuthenticationPreProcessor)processor).supports(arg_0))).collect(Collectors.toList());
        boolean processed = true;
        Iterator it = supported.iterator();
        while (processed && it.hasNext()) {
            AuthenticationPreProcessor processor2 = (AuthenticationPreProcessor)it.next();
            processed = processor2.process(transaction);
        }
        return processed;
    }

    protected void authenticateAndResolvePrincipal(AuthenticationBuilder builder, Credential credential, PrincipalResolver resolver, AuthenticationHandler handler, Service service) throws GeneralSecurityException, PreventedException {
        this.publishEvent((ApplicationEvent)new CasAuthenticationTransactionStartedEvent((Object)this, credential));
        AuthenticationHandlerExecutionResult result = handler.authenticate(credential, service);
        String authenticationHandlerName = handler.getName();
        builder.addSuccess(authenticationHandlerName, result);
        LOGGER.debug("Authentication handler [{}] successfully authenticated [{}]", (Object)authenticationHandlerName, (Object)credential);
        this.publishEvent((ApplicationEvent)new CasAuthenticationTransactionSuccessfulEvent((Object)this, credential));
        Principal principal = result.getPrincipal();
        if (resolver != null) {
            principal = this.resolvePrincipal(handler, resolver, credential, principal);
        }
        if (principal == null) {
            String resolverName;
            String string = resolverName = resolver == null ? authenticationHandlerName : resolver.getName();
            if (this.principalResolutionFailureFatal) {
                LOGGER.warn("Principal resolution handled by [{}] produced a null principal for: [{}]CAS is configured to treat principal resolution failures as fatal.", (Object)resolverName, (Object)credential);
                throw new UnresolvedPrincipalException();
            }
            LOGGER.warn("Principal resolution handled by [{}] produced a null principal. This is likely due to misconfiguration or missing attributes; CAS will attempt to use the principal produced by the authentication handler, if any.", (Object)resolverName);
        } else {
            builder.setPrincipal(principal);
        }
        LOGGER.debug("Final principal resolved for this authentication event is [{}]", (Object)principal);
        this.publishEvent((ApplicationEvent)new CasAuthenticationPrincipalResolvedEvent((Object)this, principal));
    }

    protected PrincipalResolver getPrincipalResolverLinkedToHandlerIfAny(AuthenticationHandler handler, AuthenticationTransaction transaction) {
        return this.authenticationEventExecutionPlan.getPrincipalResolver(handler, transaction);
    }

    protected Collection<AuthenticationMetaDataPopulator> getAuthenticationMetadataPopulatorsForTransaction(AuthenticationTransaction transaction) {
        return this.authenticationEventExecutionPlan.getAuthenticationMetadataPopulators(transaction);
    }

    protected void publishEvent(ApplicationEvent event) {
        if (this.applicationContext != null) {
            this.applicationContext.publishEvent(event);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected AuthenticationBuilder authenticateInternal(AuthenticationTransaction transaction) throws AuthenticationException {
        Collection credentials = transaction.getCredentials();
        LOGGER.debug("Authentication credentials provided for this transaction are [{}]", (Object)credentials);
        if (credentials.isEmpty()) {
            LOGGER.error("Resolved authentication handlers for this transaction are empty");
            throw new AuthenticationException("Resolved credentials for this transaction are empty");
        }
        DefaultAuthenticationBuilder builder = new DefaultAuthenticationBuilder(NullPrincipal.getInstance());
        credentials.forEach(cred -> builder.addCredential((CredentialMetaData)new BasicCredentialMetaData((Credential)cred)));
        Set handlerSet = this.authenticationEventExecutionPlan.getAuthenticationHandlers(transaction);
        LOGGER.debug("Candidate resolved authentication handlers for this transaction are [{}]", (Object)handlerSet);
        try {
            Iterator it = credentials.iterator();
            AuthenticationCredentialsThreadLocalBinder.clearInProgressAuthentication();
            while (it.hasNext()) {
                Credential credential = (Credential)it.next();
                LOGGER.debug("Attempting to authenticate credential [{}]", (Object)credential);
                Iterator itHandlers = handlerSet.iterator();
                boolean proceedWithNextHandler = true;
                while (proceedWithNextHandler && itHandlers.hasNext()) {
                    AuthenticationHandler handler = (AuthenticationHandler)itHandlers.next();
                    if (handler.supports(credential)) {
                        try {
                            PrincipalResolver resolver = this.getPrincipalResolverLinkedToHandlerIfAny(handler, transaction);
                            LOGGER.debug("Attempting authentication of [{}] using [{}]", (Object)credential.getId(), (Object)handler.getName());
                            this.authenticateAndResolvePrincipal(builder, credential, resolver, handler, transaction.getService());
                            Authentication authnResult = builder.build();
                            AuthenticationCredentialsThreadLocalBinder.bindInProgress(authnResult);
                            ChainingAuthenticationPolicyExecutionResult executionResult = this.evaluateAuthenticationPolicies(authnResult, transaction, handlerSet);
                            proceedWithNextHandler = !executionResult.isSuccess();
                        }
                        catch (GeneralSecurityException e) {
                            this.handleAuthenticationException(e, handler.getName(), builder);
                            proceedWithNextHandler = this.shouldAuthenticationChainProceedOnFailure(transaction, e);
                        }
                        catch (Exception e) {
                            LOGGER.error("Authentication has failed. Credentials may be incorrect or CAS cannot find authentication handler that supports [{}] of type [{}]. Examine the configuration to ensure a method of authentication is defined and analyze CAS logs at DEBUG level to trace the authentication event.", (Object)credential, (Object)credential.getClass().getSimpleName());
                            this.handleAuthenticationException(e, handler.getName(), builder);
                            proceedWithNextHandler = this.shouldAuthenticationChainProceedOnFailure(transaction, e);
                        }
                        continue;
                    }
                    LOGGER.debug("Authentication handler [{}] does not support the credential type [{}].", (Object)handler.getName(), (Object)credential);
                }
            }
            this.evaluateFinalAuthentication(builder, transaction, handlerSet);
            DefaultAuthenticationBuilder defaultAuthenticationBuilder = builder;
            return defaultAuthenticationBuilder;
        }
        finally {
            AuthenticationCredentialsThreadLocalBinder.clearInProgressAuthentication();
        }
    }

    protected void evaluateFinalAuthentication(AuthenticationBuilder builder, AuthenticationTransaction transaction, Set<AuthenticationHandler> authenticationHandlers) throws AuthenticationException {
        if (builder.getSuccesses().isEmpty()) {
            this.publishEvent((ApplicationEvent)new CasAuthenticationTransactionFailureEvent((Object)this, builder.getFailures(), transaction.getCredentials()));
            throw new AuthenticationException(builder.getFailures(), builder.getSuccesses());
        }
        Authentication authentication = builder.build();
        ChainingAuthenticationPolicyExecutionResult executionResult = this.evaluateAuthenticationPolicies(authentication, transaction, authenticationHandlers);
        if (!executionResult.isSuccess()) {
            this.publishEvent((ApplicationEvent)new CasAuthenticationPolicyFailureEvent((Object)this, builder.getFailures(), transaction, authentication));
            executionResult.getFailures().forEach(e -> this.handleAuthenticationException((Throwable)e, e.getClass().getSimpleName(), builder));
            throw new AuthenticationException(builder.getFailures(), builder.getSuccesses());
        }
    }

    protected ChainingAuthenticationPolicyExecutionResult evaluateAuthenticationPolicies(Authentication authentication, AuthenticationTransaction transaction, Set<AuthenticationHandler> authenticationHandlers) {
        Collection policies = this.authenticationEventExecutionPlan.getAuthenticationPolicies(transaction);
        ChainingAuthenticationPolicyExecutionResult executionResult = new ChainingAuthenticationPolicyExecutionResult();
        DefaultAuthenticationResultBuilder resultBuilder = new DefaultAuthenticationResultBuilder();
        resultBuilder.collect(transaction.getAuthentications());
        resultBuilder.collect(authentication);
        AuthenticationSystemSupport authenticationSystemSupport = (AuthenticationSystemSupport)this.applicationContext.getBean("defaultAuthenticationSystemSupport", AuthenticationSystemSupport.class);
        PrincipalElectionStrategy principalElectionStrategy = authenticationSystemSupport.getPrincipalElectionStrategy();
        Authentication resultAuthentication = resultBuilder.build(principalElectionStrategy).getAuthentication();
        LOGGER.trace("Final authentication used for authentication policy evaluation is [{}]", (Object)resultAuthentication);
        policies.forEach(policy -> {
            try {
                String simpleName = policy.getClass().getSimpleName();
                LOGGER.debug("Executing authentication policy [{}]", (Object)simpleName);
                LinkedHashSet supportingHandlers = authenticationHandlers.stream().filter(handler -> transaction.getCredentials().stream().anyMatch(arg_0 -> ((AuthenticationHandler)handler).supports(arg_0))).collect(Collectors.toCollection(LinkedHashSet::new));
                AuthenticationPolicyExecutionResult result = policy.isSatisfiedBy(resultAuthentication, (Set)supportingHandlers, this.applicationContext, Optional.empty());
                executionResult.getResults().add(result);
                if (!result.isSuccess()) {
                    executionResult.getFailures().add((Throwable)new AuthenticationException("Unable to satisfy authentication policy " + simpleName));
                }
            }
            catch (GeneralSecurityException e) {
                LOGGER.debug(e.getMessage(), (Throwable)e);
                FunctionUtils.doIfNotNull((Object)e.getCause(), o -> executionResult.getFailures().add(e.getCause()));
            }
            catch (Exception e) {
                LOGGER.debug(e.getMessage(), (Throwable)e);
                executionResult.getFailures().add(e);
            }
        });
        return executionResult;
    }

    protected void handleAuthenticationException(Throwable ex, String name, AuthenticationBuilder builder) {
        LOGGER.trace(ex.getMessage(), ex);
        StringBuilder msg = new StringBuilder(StringUtils.defaultString((String)ex.getMessage()));
        if (ex.getCause() != null) {
            msg.append(" / ").append(ex.getCause().getMessage());
        }
        if (ex instanceof GeneralSecurityException) {
            LOGGER.info("[{}] exception details: [{}].", (Object)name, (Object)msg);
            builder.addFailure(name, ex);
        } else {
            LOGGER.error("[{}]: [{}]", (Object)name, (Object)msg);
            builder.addFailure(name, ex);
        }
    }

    private boolean shouldAuthenticationChainProceedOnFailure(AuthenticationTransaction transaction, Throwable failure) {
        Collection policies = this.authenticationEventExecutionPlan.getAuthenticationPolicies(transaction);
        return policies.stream().anyMatch(policy -> policy.shouldResumeOnFailure(failure));
    }

    @Generated
    public DefaultAuthenticationManager(AuthenticationEventExecutionPlan authenticationEventExecutionPlan, boolean principalResolutionFailureFatal, ConfigurableApplicationContext applicationContext) {
        this.authenticationEventExecutionPlan = authenticationEventExecutionPlan;
        this.principalResolutionFailureFatal = principalResolutionFailureFatal;
        this.applicationContext = applicationContext;
    }

    @Generated
    public AuthenticationEventExecutionPlan getAuthenticationEventExecutionPlan() {
        return this.authenticationEventExecutionPlan;
    }

    @Generated
    public boolean isPrincipalResolutionFailureFatal() {
        return this.principalResolutionFailureFatal;
    }

    @Generated
    public ConfigurableApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    private static class ChainingAuthenticationPolicyExecutionResult {
        private final List<AuthenticationPolicyExecutionResult> results = new ArrayList<AuthenticationPolicyExecutionResult>();
        private final Set<Throwable> failures = new HashSet<Throwable>();

        private ChainingAuthenticationPolicyExecutionResult() {
        }

        public boolean isSuccess() {
            return this.failures.isEmpty();
        }

        @Generated
        public List<AuthenticationPolicyExecutionResult> getResults() {
            return this.results;
        }

        @Generated
        public Set<Throwable> getFailures() {
            return this.failures;
        }
    }
}

