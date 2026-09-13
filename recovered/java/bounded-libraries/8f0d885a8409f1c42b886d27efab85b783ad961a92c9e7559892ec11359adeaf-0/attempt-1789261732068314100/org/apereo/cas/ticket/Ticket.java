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
import java.time.ZonedDateTime;
import org.apereo.cas.ticket.ExpirationPolicy;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface Ticket
extends Serializable,
Comparable<Ticket> {
    public String getId();

    public ZonedDateTime getCreationTime();

    public int getCountOfUses();

    public String getPrefix();

    public boolean isExpired();

    public ExpirationPolicy getExpirationPolicy();

    public void markTicketExpired();

    public ZonedDateTime getLastTimeUsed();

    public ZonedDateTime getPreviousTimeUsed();

    public void update();
}

