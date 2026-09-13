/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationHandler
 *  org.apereo.cas.authentication.AuthenticationPolicyExecutionResult
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.ticket.registry.TicketRegistry
 *  org.apereo.cas.util.HttpRequestUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.apereo.cas.authentication.policy;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationPolicyExecutionResult;
import org.apereo.cas.authentication.exceptions.UniquePrincipalRequiredException;
import org.apereo.cas.authentication.policy.BaseAuthenticationPolicy;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.apereo.cas.util.HttpRequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class UniquePrincipalAuthenticationPolicy
extends BaseAuthenticationPolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(UniquePrincipalAuthenticationPolicy.class);
    private static final long serialVersionUID = 3974114391376732470L;
    private final TicketRegistry ticketRegistry;

    public AuthenticationPolicyExecutionResult isSatisfiedBy(Authentication authentication, Set<AuthenticationHandler> authenticationHandlers, ConfigurableApplicationContext applicationContext, Optional<Serializable> assertionResult) {
        Principal authPrincipal;
        long count;
        String renew;
        HttpServletRequest request = HttpRequestUtils.getHttpServletRequestFromRequestAttributes();
        String string = renew = request == null ? "" : request.getParameter("renew");
        if (assertionResult.isEmpty() && StringUtils.isBlank((CharSequence)renew) && (count = this.ticketRegistry.countSessionsFor((authPrincipal = authentication.getPrincipal()).getId())) > 0L) {
            LOGGER.warn("[{}] cannot be satisfied for [{}]; [{}] sessions currently exist", new Object[]{this.getName(), authPrincipal.getId(), count});
            throw new UniquePrincipalRequiredException();
        }
        return AuthenticationPolicyExecutionResult.success();
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UniquePrincipalAuthenticationPolicy)) {
            return false;
        }
        UniquePrincipalAuthenticationPolicy other = (UniquePrincipalAuthenticationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        TicketRegistry this$ticketRegistry = this.ticketRegistry;
        TicketRegistry other$ticketRegistry = other.ticketRegistry;
        return !(this$ticketRegistry == null ? other$ticketRegistry != null : !this$ticketRegistry.equals(other$ticketRegistry));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof UniquePrincipalAuthenticationPolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        TicketRegistry $ticketRegistry = this.ticketRegistry;
        result = result * 59 + ($ticketRegistry == null ? 43 : $ticketRegistry.hashCode());
        return result;
    }

    @Generated
    public TicketRegistry getTicketRegistry() {
        return this.ticketRegistry;
    }

    @Generated
    public UniquePrincipalAuthenticationPolicy(TicketRegistry ticketRegistry) {
        this.ticketRegistry = ticketRegistry;
    }
}

