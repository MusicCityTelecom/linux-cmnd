/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.ticket.AuthenticationAwareTicket
 *  org.apereo.cas.ticket.ExpirationPolicy
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.ticket.TicketGrantingTicketAwareTicket
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.ticket.expiration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.ticket.AuthenticationAwareTicket;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketGrantingTicketAwareTicket;
import org.apereo.cas.ticket.expiration.AbstractCasExpirationPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public abstract class BaseDelegatingExpirationPolicy
extends AbstractCasExpirationPolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDelegatingExpirationPolicy.class);
    public static final String POLICY_NAME_DEFAULT = "DEFAULT";
    private static final long serialVersionUID = 5927936344949518688L;
    private final Map<String, ExpirationPolicy> policies = new LinkedHashMap<String, ExpirationPolicy>();

    public void addPolicy(ExpirationPolicy policy) {
        LOGGER.trace("Adding expiration policy [{}] with name [{}]", (Object)policy, (Object)policy.getName());
        this.policies.put(policy.getName(), policy);
    }

    public void addPolicy(String name, ExpirationPolicy policy) {
        LOGGER.trace("Adding expiration policy [{}] with name [{}]", (Object)policy, (Object)name);
        this.policies.put(name, policy);
    }

    @Override
    public boolean isExpired(TicketGrantingTicketAwareTicket ticketState) {
        Optional<ExpirationPolicy> match = this.getExpirationPolicyFor((AuthenticationAwareTicket)ticketState);
        if (match.isEmpty()) {
            LOGGER.warn("No expiration policy was found for ticket state [{}]. Consider configuring a predicate that delegates to an expiration policy.", (Object)ticketState);
            return super.isExpired(ticketState);
        }
        ExpirationPolicy policy = match.get();
        LOGGER.trace("Activating expiration policy [{}] for ticket [{}]", (Object)policy.getName(), (Object)ticketState);
        return policy.isExpired(ticketState);
    }

    public Long getTimeToLive(Ticket ticketState) {
        Optional<ExpirationPolicy> match = this.getExpirationPolicyFor((AuthenticationAwareTicket)ticketState);
        if (match.isEmpty()) {
            LOGGER.warn("No expiration policy was found for ticket state [{}]. Consider configuring a predicate that delegates to an expiration policy.", (Object)ticketState);
            return super.getTimeToLive(ticketState);
        }
        ExpirationPolicy policy = match.get();
        LOGGER.trace("Getting TTL from policy [{}] for ticket [{}]", (Object)policy.getName(), (Object)ticketState);
        return policy.getTimeToLive(ticketState);
    }

    @JsonIgnore
    public Long getTimeToLive() {
        return this.policies.get(POLICY_NAME_DEFAULT).getTimeToLive();
    }

    @JsonIgnore
    public Long getTimeToIdle() {
        return this.policies.get(POLICY_NAME_DEFAULT).getTimeToIdle();
    }

    protected Optional<ExpirationPolicy> getExpirationPolicyFor(AuthenticationAwareTicket ticketState) {
        String name = this.getExpirationPolicyNameFor(ticketState);
        LOGGER.trace("Received expiration policy name [{}] to activate", (Object)name);
        if (StringUtils.isNotBlank((CharSequence)name) && this.policies.containsKey(name)) {
            ExpirationPolicy policy = this.policies.get(name);
            LOGGER.trace("Located expiration policy [{}] by name [{}]", (Object)policy, (Object)name);
            return Optional.of(policy);
        }
        LOGGER.warn("No expiration policy could be found by the name [{}] for ticket state [{}]", (Object)name, (Object)ticketState);
        return Optional.empty();
    }

    protected abstract String getExpirationPolicyNameFor(AuthenticationAwareTicket var1);

    @Generated
    public Map<String, ExpirationPolicy> getPolicies() {
        return this.policies;
    }

    @Generated
    protected BaseDelegatingExpirationPolicy() {
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BaseDelegatingExpirationPolicy)) {
            return false;
        }
        BaseDelegatingExpirationPolicy other = (BaseDelegatingExpirationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Map<String, ExpirationPolicy> this$policies = this.policies;
        Map<String, ExpirationPolicy> other$policies = other.policies;
        return !(this$policies == null ? other$policies != null : !((Object)this$policies).equals(other$policies));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof BaseDelegatingExpirationPolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        Map<String, ExpirationPolicy> $policies = this.policies;
        result = result * 59 + ($policies == null ? 43 : ((Object)$policies).hashCode());
        return result;
    }

    @Override
    @Generated
    public String toString() {
        return "BaseDelegatingExpirationPolicy(super=" + super.toString() + ", policies=" + this.policies + ")";
    }
}

