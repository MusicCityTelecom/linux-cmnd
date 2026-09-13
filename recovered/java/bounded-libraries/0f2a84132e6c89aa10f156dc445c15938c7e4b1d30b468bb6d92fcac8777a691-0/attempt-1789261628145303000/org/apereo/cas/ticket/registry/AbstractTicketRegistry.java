/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteSource
 *  lombok.Generated
 *  lombok.NonNull
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.ticket.AuthenticatedServicesAwareTicketGrantingTicket
 *  org.apereo.cas.ticket.AuthenticationAwareTicket
 *  org.apereo.cas.ticket.EncodedTicket
 *  org.apereo.cas.ticket.InvalidTicketException
 *  org.apereo.cas.ticket.ServiceTicket
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.cas.ticket.proxy.ProxyGrantingTicket
 *  org.apereo.cas.ticket.registry.TicketRegistry
 *  org.apereo.cas.util.DigestUtils
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.apereo.cas.util.serialization.SerializationUtils
 *  org.jooq.lambda.Unchecked
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.ticket.registry;

import com.google.common.io.ByteSource;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.ticket.AuthenticatedServicesAwareTicketGrantingTicket;
import org.apereo.cas.ticket.AuthenticationAwareTicket;
import org.apereo.cas.ticket.EncodedTicket;
import org.apereo.cas.ticket.InvalidTicketException;
import org.apereo.cas.ticket.ServiceTicket;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.proxy.ProxyGrantingTicket;
import org.apereo.cas.ticket.registry.DefaultEncodedTicket;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.apereo.cas.util.DigestUtils;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.apereo.cas.util.serialization.SerializationUtils;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractTicketRegistry
implements TicketRegistry {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTicketRegistry.class);
    private static final String MESSAGE = "Ticket encryption is not enabled. Falling back to default behavior";
    protected CipherExecutor cipherExecutor;

    public void addTicket(Ticket ticket) throws Exception {
        if (ticket != null && !ticket.isExpired()) {
            this.addTicketInternal(ticket);
        }
    }

    public Ticket getTicket(String ticketId) {
        long ticketAgeSeconds;
        Ticket returnTicket = this.getTicket(ticketId, ticket -> {
            if (ticket == null || ticket.isExpired()) {
                if (ticket == null) {
                    LOGGER.debug("Ticket [{}] not found but will be removed from the ticket registry just in case", (Object)ticketId);
                } else {
                    long ticketAgeSeconds = this.getTicketAgeSeconds((Ticket)ticket);
                    LOGGER.debug("Ticket [{}] has expired according to policy [{}] after [{}] seconds and [{}] uses and will be removed from the ticket registry", new Object[]{ticketId, ticket.getExpirationPolicy().getName(), ticketAgeSeconds, ticket.getCountOfUses()});
                }
                this.deleteSingleTicket(ticketId);
                return false;
            }
            return true;
        });
        if (returnTicket != null && (ticketAgeSeconds = this.getTicketAgeSeconds(returnTicket)) < -1L) {
            LOGGER.warn("Ticket created [{}] second(s) in the future. Check time synchronization on all servers.", (Object)(ticketAgeSeconds * -1L));
        }
        return returnTicket;
    }

    public <T extends Ticket> T getTicket(String ticketId, @NonNull Class<T> clazz) {
        if (clazz == null) {
            throw new NullPointerException("clazz is marked non-null but is null");
        }
        Ticket ticket = this.getTicket(ticketId);
        if (ticket == null) {
            LOGGER.debug("Ticket [{}] with type [{}] cannot be found", (Object)ticketId, (Object)clazz.getSimpleName());
            throw new InvalidTicketException(ticketId);
        }
        if (!clazz.isAssignableFrom(ticket.getClass())) {
            throw new ClassCastException("Ticket [" + ticket.getId() + " is of type " + ticket.getClass() + " when we were expecting " + clazz);
        }
        return (T)((Ticket)clazz.cast(ticket));
    }

    public long sessionCount() {
        long l;
        block8: {
            Stream<Ticket> tgtStream = this.stream().filter(TicketGrantingTicket.class::isInstance);
            try {
                l = tgtStream.count();
                if (tgtStream == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (tgtStream != null) {
                        try {
                            tgtStream.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (Exception t) {
                    LOGGER.trace("sessionCount() operation is not implemented by the ticket registry instance [{}]. Message is: [{}] Returning unknown as [{}]", new Object[]{this.getClass().getName(), t.getMessage(), Long.MIN_VALUE});
                    return Long.MIN_VALUE;
                }
            }
            tgtStream.close();
        }
        return l;
    }

    public long countSessionsFor(String principalId) {
        Predicate<Ticket> ticketPredicate = t -> {
            if (t instanceof TicketGrantingTicket) {
                TicketGrantingTicket ticket = (TicketGrantingTicket)TicketGrantingTicket.class.cast(t);
                return ticket.getAuthentication().getPrincipal().getId().equalsIgnoreCase(principalId);
            }
            return false;
        };
        return this.getTickets(ticketPredicate).count();
    }

    public long serviceTicketCount() {
        long l;
        block8: {
            Stream<Ticket> stStream = this.stream().filter(ServiceTicket.class::isInstance);
            try {
                l = stStream.count();
                if (stStream == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (stStream != null) {
                        try {
                            stStream.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (Exception t) {
                    LOGGER.trace("serviceTicketCount() operation is not implemented by the ticket registry instance [{}]. Message is: [{}] Returning unknown as [{}]", new Object[]{this.getClass().getName(), t.getMessage(), Long.MIN_VALUE});
                    return Long.MIN_VALUE;
                }
            }
            stStream.close();
        }
        return l;
    }

    public int deleteTicket(String ticketId) throws Exception {
        if (StringUtils.isBlank((CharSequence)ticketId)) {
            LOGGER.trace("No ticket id is provided for deletion");
            return 0;
        }
        Ticket ticket = this.getTicket(ticketId);
        if (ticket == null) {
            LOGGER.debug("Ticket [{}] could not be fetched from the registry; it may have been expired and deleted.", (Object)ticketId);
            return 0;
        }
        return this.deleteTicket(ticket);
    }

    public int deleteTicket(Ticket ticket) throws Exception {
        AtomicLong count = new AtomicLong(0L);
        if (ticket instanceof TicketGrantingTicket) {
            LOGGER.debug("Removing children of ticket [{}] from the registry.", (Object)ticket.getId());
            TicketGrantingTicket tgt = (TicketGrantingTicket)ticket;
            count.getAndAdd(this.deleteChildren(tgt));
            if (ticket instanceof ProxyGrantingTicket) {
                this.deleteProxyGrantingTicketFromParent((ProxyGrantingTicket)ticket);
            } else {
                this.deleteLinkedProxyGrantingTickets(count, tgt);
            }
        }
        LOGGER.debug("Removing ticket [{}] from the registry.", (Object)ticket);
        count.getAndAdd(this.deleteSingleTicket(ticket.getId()));
        int finalCount = count.intValue();
        return finalCount;
    }

    public abstract long deleteSingleTicket(String var1);

    protected abstract void addTicketInternal(Ticket var1) throws Exception;

    protected int deleteTickets(Set<String> tickets) {
        return this.deleteTickets(tickets.stream());
    }

    protected int deleteTickets(Stream<String> tickets) {
        return tickets.mapToInt(Unchecked.toIntFunction(this::deleteTicket)).sum();
    }

    protected int deleteChildren(TicketGrantingTicket ticket) {
        Map services;
        AtomicLong count = new AtomicLong(0L);
        if (ticket instanceof AuthenticatedServicesAwareTicketGrantingTicket && (services = ((AuthenticatedServicesAwareTicketGrantingTicket)ticket).getServices()) != null && !services.isEmpty()) {
            services.keySet().forEach(ticketId -> {
                long deleteCount = this.deleteSingleTicket((String)ticketId);
                if (deleteCount > 0L) {
                    LOGGER.debug("Removed ticket [{}]", ticketId);
                    count.getAndAdd(deleteCount);
                } else {
                    LOGGER.debug("Unable to remove ticket [{}]", ticketId);
                }
            });
        }
        return count.intValue();
    }

    protected String encodeTicketId(String ticketId) {
        if (!this.isCipherExecutorEnabled()) {
            LOGGER.trace(MESSAGE);
            return ticketId;
        }
        if (StringUtils.isBlank((CharSequence)ticketId)) {
            return ticketId;
        }
        String encodedId = DigestUtils.sha512((String)ticketId);
        LOGGER.debug("Encoded original ticket id [{}] to [{}]", (Object)ticketId, (Object)encodedId);
        return encodedId;
    }

    protected Ticket encodeTicket(Ticket ticket) throws Exception {
        if (!this.isCipherExecutorEnabled()) {
            LOGGER.trace(MESSAGE);
            return ticket;
        }
        if (ticket == null) {
            LOGGER.debug("Ticket passed is null and cannot be encoded");
            return null;
        }
        Ticket encodedTicket = this.createEncodedTicket(ticket);
        LOGGER.debug("Created encoded ticket [{}]", (Object)encodedTicket);
        return encodedTicket;
    }

    protected Ticket decodeTicket(Ticket ticketToProcess) {
        if (ticketToProcess instanceof EncodedTicket && !this.isCipherExecutorEnabled()) {
            LOGGER.warn("Found removable encoded ticket [{}]; cipher operations are disabled.", (Object)ticketToProcess.getId());
            this.deleteSingleTicket(ticketToProcess.getId());
            return null;
        }
        if (!this.isCipherExecutorEnabled()) {
            LOGGER.trace(MESSAGE);
            return ticketToProcess;
        }
        if (ticketToProcess == null) {
            LOGGER.warn("Ticket passed is null and cannot be decoded");
            return null;
        }
        if (!(ticketToProcess instanceof EncodedTicket)) {
            LOGGER.warn("Ticket passed is not an encoded ticket: [{}], no decoding is necessary.", (Object)ticketToProcess.getClass().getSimpleName());
            return ticketToProcess;
        }
        return this.decodeInternal(ticketToProcess);
    }

    protected Ticket decodeInternal(Ticket ticketToProcess) {
        LOGGER.debug("Attempting to decode [{}]", (Object)ticketToProcess);
        EncodedTicket encodedTicket = (EncodedTicket)ticketToProcess;
        Ticket ticket = (Ticket)SerializationUtils.decodeAndDeserializeObject((byte[])encodedTicket.getEncodedTicket(), (CipherExecutor)this.cipherExecutor, Ticket.class);
        LOGGER.debug("Decoded ticket to [{}]", (Object)ticket);
        return ticket;
    }

    protected Collection<Ticket> decodeTickets(Collection<Ticket> items) {
        return this.decodeTickets(items.stream()).collect(Collectors.toSet());
    }

    protected Stream<Ticket> decodeTickets(Stream<Ticket> items) {
        if (!this.isCipherExecutorEnabled()) {
            LOGGER.trace(MESSAGE);
            return items;
        }
        return items.map(this::decodeTicket);
    }

    protected static String getPrincipalIdFrom(Ticket ticket) {
        return ticket instanceof AuthenticationAwareTicket ? Optional.ofNullable(((AuthenticationAwareTicket)ticket).getAuthentication()).map(auth -> auth.getPrincipal().getId()).orElse("") : "";
    }

    protected boolean isCipherExecutorEnabled() {
        return this.cipherExecutor != null && this.cipherExecutor.isEnabled();
    }

    private Ticket createEncodedTicket(Ticket ticket) throws Exception {
        LOGGER.debug("Encoding ticket [{}]", (Object)ticket);
        byte[] encodedTicketObject = SerializationUtils.serializeAndEncodeObject((CipherExecutor)this.cipherExecutor, (Serializable)ticket);
        String encodedTicketId = this.encodeTicketId(ticket.getId());
        return new DefaultEncodedTicket(encodedTicketId, ByteSource.wrap((byte[])encodedTicketObject).read(), ticket.getPrefix());
    }

    private void deleteLinkedProxyGrantingTickets(AtomicLong count, TicketGrantingTicket tgt) throws Exception {
        LinkedHashSet<String> pgts = new LinkedHashSet<String>(tgt.getProxyGrantingTickets().keySet());
        boolean hasPgts = !pgts.isEmpty();
        count.getAndAdd(this.deleteTickets(pgts));
        if (hasPgts) {
            LOGGER.debug("Removing proxy-granting tickets from parent ticket-granting ticket");
            tgt.getProxyGrantingTickets().clear();
            this.updateTicket((Ticket)tgt);
        }
    }

    private void deleteProxyGrantingTicketFromParent(ProxyGrantingTicket ticket) throws Exception {
        ticket.getTicketGrantingTicket().getProxyGrantingTickets().remove(ticket.getId());
        this.updateTicket((Ticket)ticket.getTicketGrantingTicket());
    }

    private long getTicketAgeSeconds(@NonNull Ticket ticket) {
        if (ticket == null) {
            throw new NullPointerException("ticket is marked non-null but is null");
        }
        return ZonedDateTime.now(ticket.getExpirationPolicy().getClock()).toEpochSecond() - ticket.getCreationTime().toEpochSecond();
    }

    @Generated
    public void setCipherExecutor(CipherExecutor cipherExecutor) {
        this.cipherExecutor = cipherExecutor;
    }

    @Generated
    protected AbstractTicketRegistry() {
    }
}

