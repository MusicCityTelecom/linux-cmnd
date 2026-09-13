/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.monitor.Monitorable
 *  org.jooq.lambda.Unchecked
 */
package org.apereo.cas.ticket.registry;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.apereo.cas.monitor.Monitorable;
import org.apereo.cas.ticket.AuthenticationAwareTicket;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.jooq.lambda.Unchecked;

@Monitorable
public interface TicketRegistry {
    public static final String BEAN_NAME = "ticketRegistry";

    public void addTicket(Ticket var1) throws Exception;

    default public void addTicket(Stream<? extends Ticket> toSave) throws Exception {
        toSave.forEach(Unchecked.consumer(this::addTicket));
    }

    public <T extends Ticket> T getTicket(String var1, Class<T> var2);

    public Ticket getTicket(String var1);

    public Ticket getTicket(String var1, Predicate<Ticket> var2);

    public int deleteTicket(String var1) throws Exception;

    public int deleteTicket(Ticket var1) throws Exception;

    public long deleteAll();

    public Collection<? extends Ticket> getTickets();

    default public Stream<? extends Ticket> getTickets(Predicate<Ticket> predicate) {
        return this.stream().filter(predicate);
    }

    public Ticket updateTicket(Ticket var1) throws Exception;

    public long sessionCount();

    public long serviceTicketCount();

    default public Stream<? extends Ticket> stream() {
        return this.getTickets().stream();
    }

    public long countSessionsFor(String var1);

    default public Stream<? extends Ticket> getSessionsFor(String principalId) {
        return this.getTickets(ticket -> ticket instanceof TicketGrantingTicket && !ticket.isExpired() && ((AuthenticationAwareTicket)ticket).getAuthentication().getPrincipal().getId().equals(principalId));
    }
}

