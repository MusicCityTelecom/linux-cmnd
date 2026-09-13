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
public class ProxyTicketProperties
implements Serializable {
    private static final long serialVersionUID = -3690545027059561010L;
    private long numberOfUses = 1L;
    private long timeToKillInSeconds = 10L;

    @Generated
    public long getNumberOfUses() {
        return this.numberOfUses;
    }

    @Generated
    public long getTimeToKillInSeconds() {
        return this.timeToKillInSeconds;
    }

    @Generated
    public ProxyTicketProperties setNumberOfUses(long numberOfUses) {
        this.numberOfUses = numberOfUses;
        return this;
    }

    @Generated
    public ProxyTicketProperties setTimeToKillInSeconds(long timeToKillInSeconds) {
        this.timeToKillInSeconds = timeToKillInSeconds;
        return this;
    }
}

