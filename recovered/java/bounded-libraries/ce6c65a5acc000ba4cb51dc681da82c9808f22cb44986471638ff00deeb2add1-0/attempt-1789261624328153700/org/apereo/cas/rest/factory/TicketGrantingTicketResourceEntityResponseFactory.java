/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.springframework.http.ResponseEntity
 */
package org.apereo.cas.rest.factory;

import javax.servlet.http.HttpServletRequest;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.springframework.http.ResponseEntity;

@FunctionalInterface
public interface TicketGrantingTicketResourceEntityResponseFactory {
    public ResponseEntity<String> build(TicketGrantingTicket var1, HttpServletRequest var2) throws Exception;
}

