/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIdentityInfo
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.ObjectIdGenerators$IntSequenceGenerator
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.ticket.AuthenticationAwareTicket
 *  org.apereo.cas.ticket.ExpirationPolicy
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.cas.ticket.TicketGrantingTicketAwareTicket
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.ticket;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.ZonedDateTime;
import java.util.Optional;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.ticket.AuthenticationAwareTicket;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.TicketGrantingTicketAwareTicket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
@JsonIgnoreProperties(ignoreUnknown=true)
public abstract class AbstractTicket
implements Ticket,
AuthenticationAwareTicket,
TicketGrantingTicketAwareTicket {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTicket.class);
    private static final long serialVersionUID = -8506442397878267555L;
    private ExpirationPolicy expirationPolicy;
    private String id;
    private ZonedDateTime lastTimeUsed;
    private ZonedDateTime previousTimeUsed;
    private ZonedDateTime creationTime;
    private int countOfUses;
    private Boolean expired = Boolean.FALSE;

    protected AbstractTicket(String id, ExpirationPolicy expirationPolicy) {
        this.id = id;
        this.lastTimeUsed = this.creationTime = ZonedDateTime.now(expirationPolicy.getClock());
        this.expirationPolicy = expirationPolicy;
    }

    public void update() {
        this.updateTicketState();
        this.updateTicketGrantingTicketState();
    }

    public boolean isExpired() {
        return this.expirationPolicy.isExpired((TicketGrantingTicketAwareTicket)this) || this.isExpiredInternal();
    }

    public int compareTo(Ticket o) {
        return this.getId().compareTo(o.getId());
    }

    public String toString() {
        return this.getId();
    }

    public Authentication getAuthentication() {
        TicketGrantingTicket ticketGrantingTicket = this.getTicketGrantingTicket();
        return Optional.ofNullable(ticketGrantingTicket).map(AuthenticationAwareTicket::getAuthentication).orElse(null);
    }

    public TicketGrantingTicket getTicketGrantingTicket() {
        return null;
    }

    public void markTicketExpired() {
        this.expired = Boolean.TRUE;
    }

    protected void updateTicketGrantingTicketState() {
        TicketGrantingTicket ticketGrantingTicket = this.getTicketGrantingTicket();
        if (ticketGrantingTicket != null && !ticketGrantingTicket.isExpired()) {
            Ticket state = (Ticket)Ticket.class.cast(ticketGrantingTicket);
            state.update();
        }
    }

    protected void updateTicketState() {
        LOGGER.trace("Before updating ticket [{}]\n\tPrevious time used: [{}]\n\tLast time used: [{}]\n\tUsage count: [{}]", new Object[]{this.getId(), this.previousTimeUsed, this.lastTimeUsed, this.countOfUses});
        this.previousTimeUsed = ZonedDateTime.from(this.lastTimeUsed);
        this.lastTimeUsed = ZonedDateTime.now(this.expirationPolicy.getClock());
        ++this.countOfUses;
        LOGGER.trace("After updating ticket [{}]\n\tPrevious time used: [{}]\n\tLast time used: [{}]\n\tUsage count: [{}]", new Object[]{this.getId(), this.previousTimeUsed, this.lastTimeUsed, this.countOfUses});
    }

    @JsonIgnore
    protected boolean isExpiredInternal() {
        return this.expired;
    }

    @Generated
    protected AbstractTicket() {
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AbstractTicket)) {
            return false;
        }
        AbstractTicket other = (AbstractTicket)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$id = this.id;
        String other$id = other.id;
        return !(this$id == null ? other$id != null : !this$id.equals(other$id));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof AbstractTicket;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $id = this.id;
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        return result;
    }

    @Generated
    public void setExpirationPolicy(ExpirationPolicy expirationPolicy) {
        this.expirationPolicy = expirationPolicy;
    }

    @Generated
    public void setId(String id) {
        this.id = id;
    }

    @Generated
    public void setLastTimeUsed(ZonedDateTime lastTimeUsed) {
        this.lastTimeUsed = lastTimeUsed;
    }

    @Generated
    public void setPreviousTimeUsed(ZonedDateTime previousTimeUsed) {
        this.previousTimeUsed = previousTimeUsed;
    }

    @Generated
    public void setCreationTime(ZonedDateTime creationTime) {
        this.creationTime = creationTime;
    }

    @Generated
    public void setCountOfUses(int countOfUses) {
        this.countOfUses = countOfUses;
    }

    @Generated
    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    @Generated
    public ExpirationPolicy getExpirationPolicy() {
        return this.expirationPolicy;
    }

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    public ZonedDateTime getLastTimeUsed() {
        return this.lastTimeUsed;
    }

    @Generated
    public ZonedDateTime getPreviousTimeUsed() {
        return this.previousTimeUsed;
    }

    @Generated
    public ZonedDateTime getCreationTime() {
        return this.creationTime;
    }

    @Generated
    public int getCountOfUses() {
        return this.countOfUses;
    }
}

