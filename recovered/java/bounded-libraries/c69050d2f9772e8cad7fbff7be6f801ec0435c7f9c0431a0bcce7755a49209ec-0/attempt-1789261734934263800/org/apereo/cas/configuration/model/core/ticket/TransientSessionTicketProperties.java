/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.ticket;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-tickets", automated=true)
@JsonFilter(value="TransientSessionTicketProperties")
public class TransientSessionTicketProperties
implements Serializable {
    private static final long serialVersionUID = -3690545027059561010L;
    private long numberOfUses = 1L;
    private long timeToKillInSeconds = TimeUnit.MINUTES.toSeconds(5L);

    @Generated
    public long getNumberOfUses() {
        return this.numberOfUses;
    }

    @Generated
    public long getTimeToKillInSeconds() {
        return this.timeToKillInSeconds;
    }

    @Generated
    public TransientSessionTicketProperties setNumberOfUses(long numberOfUses) {
        this.numberOfUses = numberOfUses;
        return this;
    }

    @Generated
    public TransientSessionTicketProperties setTimeToKillInSeconds(long timeToKillInSeconds) {
        this.timeToKillInSeconds = timeToKillInSeconds;
        return this;
    }
}

