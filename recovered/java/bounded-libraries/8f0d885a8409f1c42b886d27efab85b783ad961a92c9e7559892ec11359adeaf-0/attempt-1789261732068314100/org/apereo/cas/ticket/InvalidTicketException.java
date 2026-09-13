/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.ticket;

import lombok.Generated;
import org.apereo.cas.ticket.AbstractTicketException;

public class InvalidTicketException
extends AbstractTicketException {
    private static final long serialVersionUID = 9141891414482490L;
    private static final String CODE = "INVALID_TICKET";
    private final String ticketId;

    public InvalidTicketException(String ticketId) {
        super(CODE);
        this.ticketId = ticketId;
    }

    public InvalidTicketException(Throwable throwable, String ticketId) {
        super(CODE, throwable);
        this.ticketId = ticketId;
    }

    @Generated
    public String getTicketId() {
        return this.ticketId;
    }
}

