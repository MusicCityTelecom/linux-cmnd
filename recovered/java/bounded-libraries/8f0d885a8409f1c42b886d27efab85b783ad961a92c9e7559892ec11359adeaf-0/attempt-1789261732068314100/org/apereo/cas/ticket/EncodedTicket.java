/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.ticket;

import org.apereo.cas.ticket.Ticket;

public interface EncodedTicket
extends Ticket {
    public byte[] getEncodedTicket();
}

