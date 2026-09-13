/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  lombok.Generated
 *  org.apereo.cas.ticket.EncodedTicket
 *  org.apereo.cas.ticket.ExpirationPolicy
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.util.EncodingUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.ticket.registry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import lombok.Generated;
import org.apereo.cas.ticket.EncodedTicket;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.util.EncodingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultEncodedTicket
implements EncodedTicket {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEncodedTicket.class);
    private static final long serialVersionUID = -7078771807487764116L;
    private String id;
    private byte[] encodedTicket;
    private String prefix;

    @JsonCreator
    public DefaultEncodedTicket(@JsonProperty(value="encoded") String encodedTicket, @JsonProperty(value="id") String encodedTicketId, @JsonProperty(value="prefix") String prefix) {
        this.id = encodedTicketId;
        this.encodedTicket = EncodingUtils.decodeBase64((String)encodedTicket);
        this.prefix = prefix;
    }

    @JsonIgnore
    public int getCountOfUses() {
        this.getOpNotSupportedMessage("getCountOfUses");
        return 0;
    }

    @JsonIgnore
    public ZonedDateTime getCreationTime() {
        this.getOpNotSupportedMessage("getCreationTime");
        return null;
    }

    @JsonIgnore
    public boolean isExpired() {
        this.getOpNotSupportedMessage("getExpirationPolicy");
        return false;
    }

    @JsonIgnore
    public ExpirationPolicy getExpirationPolicy() {
        this.getOpNotSupportedMessage("getExpirationPolicy");
        return null;
    }

    @JsonIgnore
    public void markTicketExpired() {
    }

    @JsonIgnore
    public ZonedDateTime getLastTimeUsed() {
        return null;
    }

    @JsonIgnore
    public ZonedDateTime getPreviousTimeUsed() {
        return null;
    }

    @JsonIgnore
    public void update() {
    }

    @JsonIgnore
    public int compareTo(Ticket o) {
        return this.getId().compareTo(o.getId());
    }

    private void getOpNotSupportedMessage(String op) {
        LOGGER.trace("[{}] operation not supported on a [{}].", (Object)op, (Object)this.getClass().getSimpleName());
    }

    @Generated
    public String toString() {
        return "DefaultEncodedTicket(id=" + this.id + ")";
    }

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    public byte[] getEncodedTicket() {
        return this.encodedTicket;
    }

    @Generated
    public String getPrefix() {
        return this.prefix;
    }

    @Generated
    public DefaultEncodedTicket() {
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultEncodedTicket)) {
            return false;
        }
        DefaultEncodedTicket other = (DefaultEncodedTicket)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$id = this.id;
        String other$id = other.id;
        return !(this$id == null ? other$id != null : !this$id.equals(other$id));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultEncodedTicket;
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
    public DefaultEncodedTicket(String id, byte[] encodedTicket, String prefix) {
        this.id = id;
        this.encodedTicket = encodedTicket;
        this.prefix = prefix;
    }
}

