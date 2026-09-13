/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.logout.LogoutManager
 *  org.apereo.cas.logout.SingleLogoutExecutionRequest
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.cas.ticket.registry.TicketRegistry
 *  org.apereo.cas.ticket.registry.TicketRegistryCleaner
 *  org.apereo.cas.util.LoggingUtils
 *  org.apereo.cas.util.lock.LockRepository
 *  org.jooq.lambda.Unchecked
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.transaction.annotation.Transactional
 */
package org.apereo.cas.ticket.registry;

import java.util.Objects;
import java.util.stream.Stream;
import lombok.Generated;
import org.apereo.cas.logout.LogoutManager;
import org.apereo.cas.logout.SingleLogoutExecutionRequest;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.apereo.cas.ticket.registry.TicketRegistryCleaner;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.lock.LockRepository;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional(transactionManager="ticketTransactionManager")
public class DefaultTicketRegistryCleaner
implements TicketRegistryCleaner {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTicketRegistryCleaner.class);
    private final LockRepository lockRepository;
    private final LogoutManager logoutManager;
    private final TicketRegistry ticketRegistry;

    public int clean() {
        try {
            if (!this.isCleanerSupported()) {
                LOGGER.trace("Ticket registry cleaner is not supported by [{}]", (Object)this.getClass().getSimpleName());
                return 0;
            }
            return this.cleanInternal();
        }
        catch (Exception e) {
            LoggingUtils.error((Logger)LOGGER, (Throwable)e);
            return 0;
        }
    }

    public int cleanTicket(Ticket ticket) {
        return (Integer)this.lockRepository.execute((Object)ticket.getId(), Unchecked.supplier(() -> {
            if (ticket instanceof TicketGrantingTicket) {
                LOGGER.debug("Cleaning up expired ticket-granting ticket [{}]", (Object)ticket.getId());
                this.logoutManager.performLogout(SingleLogoutExecutionRequest.builder().ticketGrantingTicket((TicketGrantingTicket)ticket).build());
            }
            LOGGER.debug("Cleaning up expired ticket [{}]", (Object)ticket.getId());
            return this.ticketRegistry.deleteTicket(ticket);
        })).orElseThrow();
    }

    protected int cleanInternal() {
        try (Stream<Ticket> expiredTickets = this.ticketRegistry.stream().filter(Objects::nonNull).filter(Ticket::isExpired);){
            int ticketsDeleted = expiredTickets.mapToInt(this::cleanTicket).sum();
            LOGGER.info("[{}] expired tickets removed.", (Object)ticketsDeleted);
            int n = ticketsDeleted;
            return n;
        }
    }

    protected boolean isCleanerSupported() {
        return true;
    }

    @Generated
    public DefaultTicketRegistryCleaner(LockRepository lockRepository, LogoutManager logoutManager, TicketRegistry ticketRegistry) {
        this.lockRepository = lockRepository;
        this.logoutManager = logoutManager;
        this.ticketRegistry = ticketRegistry;
    }
}

