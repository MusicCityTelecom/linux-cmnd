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
@JsonFilter(value="PrimaryTicketExpirationPolicyProperties")
public class PrimaryTicketExpirationPolicyProperties
implements Serializable {
    private static final long serialVersionUID = 3345179252583399336L;
    @DurationCapable
    private String maxTimeToLiveInSeconds = "PT8H";
    @DurationCapable
    private String timeToKillInSeconds = "PT2H";

    @Generated
    public String getMaxTimeToLiveInSeconds() {
        return this.maxTimeToLiveInSeconds;
    }

    @Generated
    public String getTimeToKillInSeconds() {
        return this.timeToKillInSeconds;
    }

    @Generated
    public PrimaryTicketExpirationPolicyProperties setMaxTimeToLiveInSeconds(String maxTimeToLiveInSeconds) {
        this.maxTimeToLiveInSeconds = maxTimeToLiveInSeconds;
        return this;
    }

    @Generated
    public PrimaryTicketExpirationPolicyProperties setTimeToKillInSeconds(String timeToKillInSeconds) {
        this.timeToKillInSeconds = timeToKillInSeconds;
        return this;
    }
}

