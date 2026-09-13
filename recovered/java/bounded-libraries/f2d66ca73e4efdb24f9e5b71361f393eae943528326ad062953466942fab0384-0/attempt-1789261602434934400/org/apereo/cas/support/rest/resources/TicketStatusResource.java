/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.text.StringEscapeUtils
 *  org.apereo.cas.ticket.InvalidTicketException
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.ticket.registry.TicketRegistry
 *  org.apereo.cas.util.LoggingUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RestController
 */
package org.apereo.cas.support.rest.resources;

import lombok.Generated;
import org.apache.commons.text.StringEscapeUtils;
import org.apereo.cas.ticket.InvalidTicketException;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.apereo.cas.util.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="ticketStatusResourceRestController")
public class TicketStatusResource {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketStatusResource.class);
    private final TicketRegistry ticketRegistry;

    @GetMapping(value={"/v1/tickets/{id:.+}"})
    public ResponseEntity<String> getTicketStatus(@PathVariable(value="id") String id) {
        try {
            Ticket ticket = this.ticketRegistry.getTicket(id, Ticket.class);
            return new ResponseEntity((Object)ticket.getId(), HttpStatus.OK);
        }
        catch (InvalidTicketException e) {
            return new ResponseEntity((Object)"Ticket could not be found", HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            LoggingUtils.error((Logger)LOGGER, (Throwable)e);
            return new ResponseEntity((Object)StringEscapeUtils.escapeHtml4((String)e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Generated
    public TicketStatusResource(TicketRegistry ticketRegistry) {
        this.ticketRegistry = ticketRegistry;
    }
}

