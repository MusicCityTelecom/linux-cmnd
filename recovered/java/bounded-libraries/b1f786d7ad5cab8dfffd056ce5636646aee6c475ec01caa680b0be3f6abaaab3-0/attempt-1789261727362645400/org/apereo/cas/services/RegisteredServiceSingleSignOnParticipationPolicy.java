/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  org.apereo.cas.ticket.AuthenticationAwareTicket
 *  org.apereo.cas.util.model.TriStateBoolean
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.ticket.AuthenticationAwareTicket;
import org.apereo.cas.util.model.TriStateBoolean;
import org.springframework.core.Ordered;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
@FunctionalInterface
public interface RegisteredServiceSingleSignOnParticipationPolicy
extends Serializable,
Ordered {
    @JsonIgnore
    public boolean shouldParticipateInSso(RegisteredService var1, AuthenticationAwareTicket var2);

    default public int getOrder() {
        return 0;
    }

    default public TriStateBoolean getCreateCookieOnRenewedAuthentication() {
        return TriStateBoolean.UNDEFINED;
    }
}

