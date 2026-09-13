/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package org.apereo.cas.ticket;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.Ticket;

@FunctionalInterface
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface ExpirationPolicyBuilder<T extends Ticket>
extends Serializable {
    public static final String BEAN_NAME_TICKET_GRANTING_TICKET_EXPIRATION_POLICY = "grantingTicketExpirationPolicy";
    public static final String BEAN_NAME_PROXY_GRANTING_TICKET_EXPIRATION_POLICY = "proxyGrantingTicketExpirationPolicy";
    public static final String BEAN_NAME_SERVICE_TICKET_EXPIRATION_POLICY = "serviceTicketExpirationPolicy";
    public static final String BEAN_NAME_PROXY_TICKET_EXPIRATION_POLICY = "proxyTicketExpirationPolicy";
    public static final String BEAN_NAME_TRANSIENT_SESSION_TICKET_EXPIRATION_POLICY = "transientSessionTicketExpirationPolicy";

    public ExpirationPolicy buildTicketExpirationPolicy();
}

