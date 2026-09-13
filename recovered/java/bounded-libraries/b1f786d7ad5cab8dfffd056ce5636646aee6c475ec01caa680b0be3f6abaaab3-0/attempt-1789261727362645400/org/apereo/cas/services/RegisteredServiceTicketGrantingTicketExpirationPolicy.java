/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  org.apereo.cas.ticket.ExpirationPolicy
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.util.Optional;
import org.apereo.cas.ticket.ExpirationPolicy;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface RegisteredServiceTicketGrantingTicketExpirationPolicy
extends Serializable {
    public long getMaxTimeToLiveInSeconds();

    @JsonIgnore
    public Optional<ExpirationPolicy> toExpirationPolicy();
}

