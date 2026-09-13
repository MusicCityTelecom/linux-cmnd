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
import lombok.Generated;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-tickets", automated=true)
@JsonFilter(value="HardTimeoutTicketExpirationPolicyProperties")
public class HardTimeoutTicketExpirationPolicyProperties
implements Serializable {
    private static final long serialVersionUID = 4160963910346416908L;
    @DurationCapable
    private String timeToKillInSeconds;

    @Generated
    public String getTimeToKillInSeconds() {
        return this.timeToKillInSeconds;
    }

    @Generated
    public HardTimeoutTicketExpirationPolicyProperties setTimeToKillInSeconds(String timeToKillInSeconds) {
        this.timeToKillInSeconds = timeToKillInSeconds;
        return this;
    }
}

