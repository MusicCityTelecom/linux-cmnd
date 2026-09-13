/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.builder.CompareToBuilder
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.ticket.TicketDefinition
 *  org.apereo.cas.ticket.TicketDefinitionProperties
 */
package org.apereo.cas.ticket;

import lombok.Generated;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apereo.cas.ticket.DefaultTicketDefinitionProperties;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketDefinition;
import org.apereo.cas.ticket.TicketDefinitionProperties;

public class DefaultTicketDefinition
implements TicketDefinition {
    private final Class<? extends Ticket> implementationClass;
    private final Class<? extends Ticket> apiClass;
    private final String prefix;
    private final TicketDefinitionProperties properties = new DefaultTicketDefinitionProperties();
    private final int order;

    public int compareTo(TicketDefinition o) {
        return new CompareToBuilder().append((Object)this.prefix, (Object)o.getPrefix()).append(this.implementationClass, (Object)o.getImplementationClass()).append(this.apiClass, (Object)o.getApiClass()).build();
    }

    @Generated
    public String toString() {
        return "DefaultTicketDefinition(implementationClass=" + this.implementationClass + ", apiClass=" + this.apiClass + ", prefix=" + this.prefix + ", properties=" + this.properties + ", order=" + this.order + ")";
    }

    @Generated
    public Class<? extends Ticket> getImplementationClass() {
        return this.implementationClass;
    }

    @Generated
    public Class<? extends Ticket> getApiClass() {
        return this.apiClass;
    }

    @Generated
    public String getPrefix() {
        return this.prefix;
    }

    @Generated
    public TicketDefinitionProperties getProperties() {
        return this.properties;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultTicketDefinition)) {
            return false;
        }
        DefaultTicketDefinition other = (DefaultTicketDefinition)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.order != other.order) {
            return false;
        }
        Class<? extends Ticket> this$implementationClass = this.implementationClass;
        Class<? extends Ticket> other$implementationClass = other.implementationClass;
        if (this$implementationClass == null ? other$implementationClass != null : !this$implementationClass.equals(other$implementationClass)) {
            return false;
        }
        Class<? extends Ticket> this$apiClass = this.apiClass;
        Class<? extends Ticket> other$apiClass = other.apiClass;
        if (this$apiClass == null ? other$apiClass != null : !this$apiClass.equals(other$apiClass)) {
            return false;
        }
        String this$prefix = this.prefix;
        String other$prefix = other.prefix;
        if (this$prefix == null ? other$prefix != null : !this$prefix.equals(other$prefix)) {
            return false;
        }
        TicketDefinitionProperties this$properties = this.properties;
        TicketDefinitionProperties other$properties = other.properties;
        return !(this$properties == null ? other$properties != null : !this$properties.equals(other$properties));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultTicketDefinition;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.order;
        Class<? extends Ticket> $implementationClass = this.implementationClass;
        result = result * 59 + ($implementationClass == null ? 43 : $implementationClass.hashCode());
        Class<? extends Ticket> $apiClass = this.apiClass;
        result = result * 59 + ($apiClass == null ? 43 : $apiClass.hashCode());
        String $prefix = this.prefix;
        result = result * 59 + ($prefix == null ? 43 : $prefix.hashCode());
        TicketDefinitionProperties $properties = this.properties;
        result = result * 59 + ($properties == null ? 43 : $properties.hashCode());
        return result;
    }

    @Generated
    public DefaultTicketDefinition(Class<? extends Ticket> implementationClass, Class<? extends Ticket> apiClass, String prefix, int order) {
        this.implementationClass = implementationClass;
        this.apiClass = apiClass;
        this.prefix = prefix;
        this.order = order;
    }
}

