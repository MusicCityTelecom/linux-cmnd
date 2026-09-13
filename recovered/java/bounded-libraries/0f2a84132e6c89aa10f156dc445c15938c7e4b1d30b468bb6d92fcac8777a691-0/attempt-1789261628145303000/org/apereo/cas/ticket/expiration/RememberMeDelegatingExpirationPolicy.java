/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.ticket.AuthenticationAwareTicket
 *  org.apereo.cas.util.CollectionUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.ticket.expiration;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Map;
import java.util.Optional;
import lombok.Generated;
import org.apereo.cas.ticket.AuthenticationAwareTicket;
import org.apereo.cas.ticket.expiration.BaseDelegatingExpirationPolicy;
import org.apereo.cas.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class RememberMeDelegatingExpirationPolicy
extends BaseDelegatingExpirationPolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RememberMeDelegatingExpirationPolicy.class);
    public static final String POLICY_NAME_REMEMBER_ME = "REMEMBER_ME";
    private static final long serialVersionUID = -2735975347698196127L;

    @Override
    protected String getExpirationPolicyNameFor(AuthenticationAwareTicket ticketState) {
        Map attrs = ticketState.getAuthentication().getAttributes();
        Optional rememberMeRes = CollectionUtils.firstElement(attrs.get("org.apereo.cas.authentication.principal.REMEMBER_ME"));
        if (rememberMeRes.isEmpty()) {
            return "DEFAULT";
        }
        Boolean b = (Boolean)rememberMeRes.get();
        if (b.equals(Boolean.FALSE)) {
            LOGGER.trace("Ticket is not associated with a remember-me authentication.");
            return "DEFAULT";
        }
        return POLICY_NAME_REMEMBER_ME;
    }

    @Override
    @Generated
    public String toString() {
        return "RememberMeDelegatingExpirationPolicy(super=" + super.toString() + ")";
    }
}

