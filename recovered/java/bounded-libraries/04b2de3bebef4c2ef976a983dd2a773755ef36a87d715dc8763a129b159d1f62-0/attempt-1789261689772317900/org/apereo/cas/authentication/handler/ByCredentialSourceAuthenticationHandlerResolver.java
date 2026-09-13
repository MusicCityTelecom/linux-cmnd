/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.AuthenticationHandler
 *  org.apereo.cas.authentication.AuthenticationHandlerResolver
 *  org.apereo.cas.authentication.AuthenticationTransaction
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.handler;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationHandlerResolver;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.credential.UsernamePasswordCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByCredentialSourceAuthenticationHandlerResolver
implements AuthenticationHandlerResolver {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(ByCredentialSourceAuthenticationHandlerResolver.class);

    public boolean supports(Set<AuthenticationHandler> handlers, AuthenticationTransaction transaction) {
        return transaction.hasCredentialOfType(UsernamePasswordCredential.class);
    }

    public Set<AuthenticationHandler> resolve(Set<AuthenticationHandler> candidateHandlers, AuthenticationTransaction transaction) {
        LinkedHashSet<AuthenticationHandler> finalHandlers = new LinkedHashSet<AuthenticationHandler>(candidateHandlers.size());
        Collection upcs = transaction.getCredentialsOfType(UsernamePasswordCredential.class);
        candidateHandlers.stream().filter(handler -> handler.supports(UsernamePasswordCredential.class)).filter(handler -> {
            String handlerName = handler.getName();
            LOGGER.debug("Evaluating authentication handler [{}] for eligibility", (Object)handlerName);
            return upcs.stream().anyMatch(c -> {
                LOGGER.debug("Comparing credential source [{}] against authentication handler [{}]", (Object)c.getSource(), (Object)handlerName);
                return StringUtils.isNotBlank((CharSequence)c.getSource()) && c.getSource().equalsIgnoreCase(handlerName);
            });
        }).forEach(finalHandlers::add);
        return finalHandlers;
    }

    @Generated
    public ByCredentialSourceAuthenticationHandlerResolver() {
    }
}

