/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.ticket.InvalidTicketException
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.ticket.serialization.TicketSerializationExecutionPlan
 *  org.apereo.cas.util.LoggingUtils
 *  org.apereo.cas.util.serialization.StringSerializer
 *  org.jooq.lambda.Unchecked
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.ticket.serialization;

import java.util.Objects;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.ticket.InvalidTicketException;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.serialization.TicketSerializationExecutionPlan;
import org.apereo.cas.ticket.serialization.TicketSerializationManager;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.serialization.StringSerializer;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultTicketStringSerializationManager
implements TicketSerializationManager {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTicketStringSerializationManager.class);
    private final TicketSerializationExecutionPlan ticketSerializationExecutionPlan;

    @Override
    public String serializeTicket(Ticket ticket) {
        try {
            StringSerializer serializer = Objects.requireNonNull(this.ticketSerializationExecutionPlan.getTicketSerializer(ticket), () -> "Unable to find ticket serializer for " + ticket.getId());
            return serializer.toString((Object)ticket);
        }
        catch (Exception e) {
            LoggingUtils.error((Logger)LOGGER, (Throwable)e);
            throw e;
        }
    }

    @Override
    public Ticket deserializeTicket(String ticketContent, String type) {
        if (StringUtils.isBlank((CharSequence)type)) {
            throw new InvalidTicketException("Invalid ticket type [blank] specified");
        }
        StringSerializer serializer = this.ticketSerializationExecutionPlan.getTicketSerializer(type);
        if (serializer == null) {
            throw new IllegalArgumentException("Unable to find ticket deserializer for " + type);
        }
        return (Ticket)Unchecked.supplier(() -> {
            Class<?> clazz = Class.forName(type);
            return this.deserializeTicket(ticketContent, clazz);
        }).get();
    }

    @Override
    public <T extends Ticket> T deserializeTicket(String ticketContent, Class<T> clazz) {
        StringSerializer serializer = Objects.requireNonNull(this.ticketSerializationExecutionPlan.getTicketSerializer(clazz), () -> "Unable to find ticket deserializer for " + clazz.getSimpleName());
        LOGGER.trace("Unmarshalling ticket content from [{}]", (Object)ticketContent);
        Ticket ticket = (Ticket)serializer.from(ticketContent);
        if (ticket == null) {
            throw new InvalidTicketException(clazz.getName());
        }
        if (!clazz.isAssignableFrom(ticket.getClass())) {
            throw new ClassCastException("Ticket [" + ticket.getId() + " is of type " + ticket.getClass() + " when we were expecting " + clazz);
        }
        return (T)ticket;
    }

    @Generated
    public DefaultTicketStringSerializationManager(TicketSerializationExecutionPlan ticketSerializationExecutionPlan) {
        this.ticketSerializationExecutionPlan = ticketSerializationExecutionPlan;
    }
}

