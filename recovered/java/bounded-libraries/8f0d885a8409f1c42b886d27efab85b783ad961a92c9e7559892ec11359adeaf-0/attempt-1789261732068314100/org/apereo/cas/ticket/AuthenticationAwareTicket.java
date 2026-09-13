/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  org.apereo.cas.authentication.Authentication
 */
package org.apereo.cas.ticket;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.ticket.Ticket;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface AuthenticationAwareTicket
extends Ticket {
    public Authentication getAuthentication();
}

