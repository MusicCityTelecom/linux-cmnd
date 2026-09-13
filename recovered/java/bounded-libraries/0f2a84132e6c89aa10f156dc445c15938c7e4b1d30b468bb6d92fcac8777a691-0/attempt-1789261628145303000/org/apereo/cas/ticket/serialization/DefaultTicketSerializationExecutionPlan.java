/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.ticket.ServiceTicket
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.cas.ticket.proxy.ProxyGrantingTicket
 *  org.apereo.cas.ticket.proxy.ProxyTicket
 *  org.apereo.cas.ticket.serialization.TicketSerializationExecutionPlan
 *  org.apereo.cas.util.serialization.StringSerializer
 */
package org.apereo.cas.ticket.serialization;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Generated;
import org.apereo.cas.ticket.ServiceTicket;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.proxy.ProxyGrantingTicket;
import org.apereo.cas.ticket.proxy.ProxyTicket;
import org.apereo.cas.ticket.serialization.TicketSerializationExecutionPlan;
import org.apereo.cas.ticket.serialization.serializers.EncodedTicketStringSerializer;
import org.apereo.cas.ticket.serialization.serializers.ProxyGrantingTicketStringSerializer;
import org.apereo.cas.ticket.serialization.serializers.ProxyTicketStringSerializer;
import org.apereo.cas.ticket.serialization.serializers.ServiceTicketStringSerializer;
import org.apereo.cas.ticket.serialization.serializers.TicketGrantingTicketStringSerializer;
import org.apereo.cas.ticket.serialization.serializers.TransientSessionTicketStringSerializer;
import org.apereo.cas.util.serialization.StringSerializer;

public class DefaultTicketSerializationExecutionPlan
implements TicketSerializationExecutionPlan {
    private final Map<String, StringSerializer<? extends Ticket>> ticketSerializers = new ConcurrentHashMap<String, StringSerializer<? extends Ticket>>();

    public DefaultTicketSerializationExecutionPlan() {
        this.registerTicketSerializer((StringSerializer<? extends Ticket>)new EncodedTicketStringSerializer());
        this.registerTicketSerializer((StringSerializer<? extends Ticket>)new ProxyGrantingTicketStringSerializer());
        this.registerTicketSerializer((StringSerializer<? extends Ticket>)new ProxyTicketStringSerializer());
        this.registerTicketSerializer((StringSerializer<? extends Ticket>)new ServiceTicketStringSerializer());
        this.registerTicketSerializer((StringSerializer<? extends Ticket>)new TicketGrantingTicketStringSerializer());
        this.registerTicketSerializer((StringSerializer<? extends Ticket>)new TransientSessionTicketStringSerializer());
        this.registerTicketSerializer(TicketGrantingTicket.class.getName(), (StringSerializer<? extends Ticket>)new TicketGrantingTicketStringSerializer());
        this.registerTicketSerializer(ServiceTicket.class.getName(), (StringSerializer<? extends Ticket>)new ServiceTicketStringSerializer());
        this.registerTicketSerializer(ProxyTicket.class.getName(), (StringSerializer<? extends Ticket>)new ProxyTicketStringSerializer());
        this.registerTicketSerializer(ProxyGrantingTicket.class.getName(), (StringSerializer<? extends Ticket>)new ProxyGrantingTicketStringSerializer());
    }

    public void registerTicketSerializer(String typeToSerialize, StringSerializer<? extends Ticket> serializer) {
        this.ticketSerializers.put(typeToSerialize, serializer);
    }

    public void registerTicketSerializer(StringSerializer<? extends Ticket> serializer) {
        this.registerTicketSerializer(serializer.getTypeToSerialize().getName(), serializer);
    }

    public StringSerializer<Ticket> getTicketSerializer(Ticket ticket) {
        return this.getTicketSerializer(ticket.getClass().getName());
    }

    public StringSerializer<Ticket> getTicketSerializer(Class<? extends Ticket> clazz) {
        return this.getTicketSerializer(clazz.getName());
    }

    public StringSerializer<Ticket> getTicketSerializer(String clazz) {
        return this.ticketSerializers.get(clazz);
    }

    @Generated
    public Map<String, StringSerializer<? extends Ticket>> getTicketSerializers() {
        return this.ticketSerializers;
    }
}

