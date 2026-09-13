/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationBuilder
 *  org.apereo.cas.authentication.AuthenticationResult
 *  org.apereo.cas.authentication.AuthenticationResultBuilder
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.CredentialMetaData
 *  org.apereo.cas.authentication.PrincipalElectionStrategy
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.services.persondir.support.merger.IAttributeMerger
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationBuilder;
import org.apereo.cas.authentication.AuthenticationResult;
import org.apereo.cas.authentication.AuthenticationResultBuilder;
import org.apereo.cas.authentication.CoreAuthenticationUtils;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.CredentialMetaData;
import org.apereo.cas.authentication.DefaultAuthenticationBuilder;
import org.apereo.cas.authentication.DefaultAuthenticationResult;
import org.apereo.cas.authentication.PrincipalElectionStrategy;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.services.persondir.support.merger.IAttributeMerger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultAuthenticationResultBuilder
implements AuthenticationResultBuilder {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAuthenticationResultBuilder.class);
    private static final long serialVersionUID = 6180465589526463843L;
    private final Set<Authentication> authentications = Collections.synchronizedSet(new LinkedHashSet(0));
    private final List<Credential> providedCredentials = new ArrayList<Credential>(0);
    private final List<CredentialMetaData> providedCredentialMetadata = new ArrayList<CredentialMetaData>(0);

    private static Principal getPrimaryPrincipal(PrincipalElectionStrategy principalElectionStrategy, Set<Authentication> authentications, Map<String, List<Object>> principalAttributes) {
        return principalElectionStrategy.nominate(new LinkedHashSet<Authentication>(authentications), principalAttributes);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Optional<Authentication> getInitialAuthentication() {
        if (this.authentications.isEmpty()) {
            LOGGER.warn("Authentication chain is empty as no authentications have been collected");
        }
        Set<Authentication> set = this.authentications;
        synchronized (set) {
            return this.authentications.stream().findFirst();
        }
    }

    @CanIgnoreReturnValue
    public AuthenticationResultBuilder collect(Authentication authentication) {
        Optional.ofNullable(authentication).ifPresent(this.authentications::add);
        return this;
    }

    @CanIgnoreReturnValue
    public AuthenticationResultBuilder collect(Collection<Authentication> authentications) {
        this.authentications.addAll(authentications);
        return this;
    }

    @CanIgnoreReturnValue
    public AuthenticationResultBuilder collect(CredentialMetaData credential) {
        Optional.ofNullable(credential).ifPresent(this.providedCredentialMetadata::add);
        return this;
    }

    @CanIgnoreReturnValue
    public AuthenticationResultBuilder collect(Credential credential) {
        Optional.ofNullable(credential).ifPresent(this.providedCredentials::add);
        return this;
    }

    public Optional<Credential> getInitialCredential() {
        if (this.providedCredentials.isEmpty()) {
            LOGGER.warn("Provided credentials chain is empty as no credentials have been collected");
        }
        return this.providedCredentials.stream().findFirst();
    }

    public AuthenticationResult build(PrincipalElectionStrategy principalElectionStrategy) {
        return this.build(principalElectionStrategy, null);
    }

    public AuthenticationResult build(PrincipalElectionStrategy principalElectionStrategy, Service service) {
        Authentication authentication = this.buildAuthentication(principalElectionStrategy);
        if (authentication == null) {
            LOGGER.info("Authentication result cannot be produced because no authentication is recorded into in the chain. Returning null");
            return null;
        }
        LOGGER.trace("Building an authentication result for authentication [{}] and service [{}]", (Object)authentication, (Object)service);
        DefaultAuthenticationResult res = new DefaultAuthenticationResult(authentication, service);
        res.setCredentialProvided(!this.providedCredentials.isEmpty());
        return res;
    }

    protected void mergeAuthenticationAttributes(Map<String, List<Object>> authenticationAttributes, IAttributeMerger merger, Authentication authn) {
        authenticationAttributes.putAll(CoreAuthenticationUtils.mergeAttributes(authenticationAttributes, authn.getAttributes(), merger));
        LOGGER.debug("Finalized authentication attributes [{}] for inclusion in this authentication result", authenticationAttributes);
    }

    protected void mergePrincipalAttributes(Map<String, List<Object>> principalAttributes, IAttributeMerger merger, Authentication authn) {
        Principal authenticatedPrincipal = authn.getPrincipal();
        LOGGER.debug("Evaluating authentication principal [{}] for inclusion in result", (Object)authenticatedPrincipal);
        principalAttributes.putAll(CoreAuthenticationUtils.mergeAttributes(principalAttributes, authenticatedPrincipal.getAttributes(), merger));
        LOGGER.debug("Collected principal attributes [{}] for inclusion in this result for principal [{}]", principalAttributes, (Object)authenticatedPrincipal.getId());
    }

    private void buildAuthenticationHistory(Set<Authentication> authentications, Map<String, List<Object>> authenticationAttributes, Map<String, List<Object>> principalAttributes, AuthenticationBuilder authenticationBuilder, PrincipalElectionStrategy principalElectionStrategy) {
        IAttributeMerger merger = principalElectionStrategy.getAttributeMerger();
        LOGGER.trace("Collecting authentication history based on [{}] authentication events", (Object)authentications.size());
        authentications.forEach(authn -> {
            this.mergePrincipalAttributes(principalAttributes, merger, (Authentication)authn);
            this.mergeAuthenticationAttributes(authenticationAttributes, merger, (Authentication)authn);
            authenticationBuilder.addSuccesses(authn.getSuccesses()).addFailures(authn.getFailures()).addWarnings(authn.getWarnings()).addCredentials(authn.getCredentials()).addCredentials(this.providedCredentialMetadata);
        });
    }

    private boolean isEmpty() {
        return this.authentications.isEmpty();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Authentication buildAuthentication(PrincipalElectionStrategy principalElectionStrategy) {
        if (this.isEmpty()) {
            LOGGER.warn("No authentication event has been recorded; CAS cannot finalize the authentication result");
            return null;
        }
        HashMap<String, List<Object>> authenticationAttributes = new HashMap<String, List<Object>>();
        HashMap<String, List<Object>> principalAttributes = new HashMap<String, List<Object>>();
        AuthenticationBuilder authenticationBuilder = DefaultAuthenticationBuilder.newInstance();
        this.buildAuthenticationHistory(this.authentications, authenticationAttributes, principalAttributes, authenticationBuilder, principalElectionStrategy);
        Set<Authentication> set = this.authentications;
        synchronized (set) {
            Principal primaryPrincipal = DefaultAuthenticationResultBuilder.getPrimaryPrincipal(principalElectionStrategy, this.authentications, principalAttributes);
            authenticationBuilder.setPrincipal(primaryPrincipal);
        }
        LOGGER.debug("Determined primary authentication principal to be [{}]", (Object)authenticationBuilder.getPrincipal());
        authenticationBuilder.setAttributes(authenticationAttributes);
        LOGGER.trace("Collected authentication attributes for this result are [{}]", authenticationAttributes);
        authenticationBuilder.setAuthenticationDate(ZonedDateTime.now(ZoneOffset.UTC));
        Authentication auth = authenticationBuilder.build();
        LOGGER.trace("Authentication result commenced at [{}]", (Object)auth.getAuthenticationDate());
        return auth;
    }

    @Generated
    public DefaultAuthenticationResultBuilder() {
    }

    @Generated
    public Set<Authentication> getAuthentications() {
        return this.authentications;
    }

    @Generated
    public List<Credential> getProvidedCredentials() {
        return this.providedCredentials;
    }

    @Generated
    public List<CredentialMetaData> getProvidedCredentialMetadata() {
        return this.providedCredentialMetadata;
    }
}

