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
@JsonFilter(value="ThrottledTimeoutTicketExpirationPolicyProperties")
public class ThrottledTimeoutTicketExpirationPolicyProperties
implements Serializable {
    private static final long serialVersionUID = -2370751379747804646L;
    @DurationCapable
    private String timeToKillInSeconds;
    @DurationCapable
    private String timeInBetweenUsesInSeconds;

    @Generated
    public String getTimeToKillInSeconds() {
        return this.timeToKillInSeconds;
    }

    @Generated
    public String getTimeInBetweenUsesInSeconds() {
        return this.timeInBetweenUsesInSeconds;
    }

    @Generated
    public ThrottledTimeoutTicketExpirationPolicyProperties setTimeToKillInSeconds(String timeToKillInSeconds) {
        this.timeToKillInSeconds = timeToKillInSeconds;
        return this;
    }

    @Generated
    public ThrottledTimeoutTicketExpirationPolicyProperties setTimeInBetweenUsesInSeconds(String timeInBetweenUsesInSeconds) {
        this.timeInBetweenUsesInSeconds = timeInBetweenUsesInSeconds;
        return this;
    }
}

