/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.configuration.model.core.authentication.AuthenticationHandlerStates
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.authentication;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationHandlerStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

public interface AuthenticationHandlerResolver
extends Ordered {
    public static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationHandlerResolver.class);

    public static AuthenticationHandlerResolver noOp() {
        return new AuthenticationHandlerResolver(){

            @Override
            public boolean supports(Set<AuthenticationHandler> handlers, AuthenticationTransaction transaction) {
                return false;
            }

            @Override
            public Set<AuthenticationHandler> resolve(Set<AuthenticationHandler> candidateHandlers, AuthenticationTransaction transaction) {
                return new LinkedHashSet<AuthenticationHandler>();
            }
        };
    }

    default public Set<AuthenticationHandler> resolve(Set<AuthenticationHandler> candidateHandlers, AuthenticationTransaction transaction) {
        LinkedHashSet handlers = candidateHandlers.stream().filter(handler -> handler.getState() == AuthenticationHandlerStates.ACTIVE).collect(Collectors.toCollection(LinkedHashSet::new));
        LOGGER.debug("Default authentication handlers used for this transaction are [{}]", (Object)handlers.stream().map(AuthenticationHandler::getName).collect(Collectors.joining(",")));
        return handlers;
    }

    default public int getOrder() {
        return Integer.MIN_VALUE;
    }

    default public boolean supports(Set<AuthenticationHandler> handlers, AuthenticationTransaction transaction) {
        return !handlers.isEmpty() && transaction != null;
    }
}

