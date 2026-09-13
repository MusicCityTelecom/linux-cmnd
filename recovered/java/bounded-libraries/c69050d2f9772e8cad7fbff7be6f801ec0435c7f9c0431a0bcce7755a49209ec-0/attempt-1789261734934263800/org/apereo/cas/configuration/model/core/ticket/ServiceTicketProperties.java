/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.ticket;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-tickets", automated=true)
public class ServiceTicketProperties
implements Serializable {
    private static final long serialVersionUID = -7445209580598499921L;
    private long numberOfUses = 1L;
    private long timeToKillInSeconds = 10L;
    private int maxLength = 20;

    @Generated
    public long getNumberOfUses() {
        return this.numberOfUses;
    }

    @Generated
    public long getTimeToKillInSeconds() {
        return this.timeToKillInSeconds;
    }

    @Generated
    public int getMaxLength() {
        return this.maxLength;
    }

    @Generated
    public ServiceTicketProperties setNumberOfUses(long numberOfUses) {
        this.numberOfUses = numberOfUses;
        return this;
    }

    @Generated
    public ServiceTicketProperties setTimeToKillInSeconds(long timeToKillInSeconds) {
        this.timeToKillInSeconds = timeToKillInSeconds;
        return this;
    }

    @Generated
    public ServiceTicketProperties setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }
}

