/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  org.apereo.cas.authentication.principal.Service
 */
package org.apereo.cas.ticket;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.util.Map;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketGrantingTicketAwareTicket;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface TransientSessionTicket
extends Ticket,
TicketGrantingTicketAwareTicket {
    public static final String PREFIX = "TST";

    public Map<String, Object> getProperties();

    public <T> T getProperty(String var1, Class<T> var2);

    public Service getService();

    public void put(String var1, Serializable var2);

    public void putAll(Map<String, Serializable> var1);

    public boolean contains(String var1);

    public <T extends Serializable> T get(String var1, Class<T> var2);

    public <T extends Serializable> T get(String var1, Class<T> var2, T var3);
}

